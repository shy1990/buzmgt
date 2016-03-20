var params = {};
var total = 0;
$(function () {

    initFileUpload();

    initSearchButton();

    initUI();

    initLastUpdateDate();

    initDateInput();

    initExcelExport();

    search();

    initPaging();
});

/**
 * 初始化文件上传
 */
function initFileUpload() {
    // 文件上传
    $('#file').fileupload({
        dataType: 'json',
        add: function (e, data) {
            $("#uploadFileDiv").show();
            $("#uploadFile").on("click", function () {
                if (!checkDataDate()) {
                    alert("请选择数据时间!");
                    return;
                }

                $('#message').text('上传中');

                // 修改fileupload插件上传时的url，带参数。
                $("#file").fileupload('option', 'url', '/businessAdviser/upload?type=' + $("#type" +
                        " option:selected").val() + "&dataDate=" + $("#dataDate").val());

                data.submit();
            });
        },
        done: function (e, data) {
            if (data.result.result == "failure") {
                $('#message').text(data.result.message);
            }

            $('#message').text("上传完成");
        }
    });
}

/**
 * 初始化检索按钮
 */
function initSearchButton() {
    $("#filter").on("click", function () {
        // 统一处理检索条件
        searchConditionProcess();
        // 如果是点击筛选，则不需要商品ID的检索条件
        delete params["sc_EQ_goodId"];
        // 初始化page
        params["page"] = 0;
        // 执行检索
        search();
    });

    $("#iconSearch").on("click", function () {
        // 统一处理检索条件
        searchConditionProcess();
        // 初始化page
        params["page"] = 0;
        // 执行检索
        search();
    });
}

/**
 * 初始化各种效果
 */
function initUI() {
    // 品牌/类别 检索条件高亮
    $('.condition li').click(function () {
        $(this).addClass('active');
        $(this).siblings('li').removeClass('active');
    });

}

/**
 * 初始化数据最新更新时间
 */
function initLastUpdateDate() {
    $.ajax({
        url: "/businessAdviser/getLastUpdateDate",
        type: "GET",
        beforeSend: function (request) {
            request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        },
        success: function (data) {
            if (data)
                $(".text-date").text(data);
        },
        error: function () {
            alert("系统错误");
        }
    });
}

/**
 * 取得检索条件
 * @param ulIndex
 * @returns {*|jQuery}
 */
function findSearchParam(ulIndex) {
    return $(".search-box ul:eq(" + ulIndex + ") .active").find("a").text();
}

/**
 * 初始化时间控件
 */
function initDateInput() {
    var date = new Date();
    var today = changeDateToString(date);
    $(".form_date_start").datetimepicker({
        endDate: today,
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-left",
        forceParse: 0
    });

    $("#dataDate").datetimepicker({
        endDate: today,
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-left",
        forceParse: 0
    });
}

/**
 * 处理检索条件
 */
function searchConditionProcess() {
    delete params["sc_EQ_type"];

    // 获得类别
    var type = findSearchParam(0);
    switch (type) {
        case "手机" :
            params["sc_EQ_type"] = "MOBILE";
            break;
        case "电脑" :
            params["sc_EQ_type"] = "PC";
            break;
        case "平板" :
            break;
        case "电视" :
            break;
    }

    // 导入时间
    var dataDate = $("#searchDate").val();
    params["sc_EQ_dataDate"] = dataDate;

    // 商品ID
    var goodId = $("#goodId").val();
    params["sc_EQ_goodId"] = goodId;
}

/**
 * 填充列表数据
 * @param data
 */
function dataForTable(data) {
    var content = Handlebars.compile($("#table-template").html());
    $('#businessAdviserDataList').html(content(data));
}

/**
 * 检索数据
 */
function search() {
    $.ajax({
        url: "/businessAdviser",
        type: "GET",
        data: params,
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        },
        success: function (data) {
            dataForTable(data);
            var searchTotal = data.totalElements;

            if (searchTotal != total || searchTotal == 0) {
                total = searchTotal;
                initPaging();
            }

        },
        error: function () {
            alert("系统错误");
        }
    });
}

/**
 * 导出excel
 */
function initExcelExport() {
    $("#export").on("click", function () {

        searchConditionProcess();

        var condition = "";

        $.each(params, function (n, v) {
            var field = "";

            if ("" != v) {
                field = "&" + n + "=" + v;
            }

            condition += field;
        });

        condition = condition.substring(1);

        window.location.href = "/businessAdviser/export?" + condition;
    });
}

/**
 * 校验数据时间是否为空
 * @returns {boolean}
 */
function checkDataDate() {
    var dataDate = $("#dataDate").val();
    if ("" === dataDate || null === dataDate || "undefined" === dataDate) {
        return false;
    }
    return true;
}

/**
 * 初始化分页
 */
function initPaging() {
    $('#callBackPager').extendPagination({
        totalCount: total,
        showCount: 11,
        limit: 20,
        callback: function (curr, limit, totalCount) {

            total = totalCount;

            searchConditionProcess();

            params["page"] = curr - 1;
            params["size"] = limit;

            delete params["sc_EQ_goodId"];

            search();
        }
    });
}