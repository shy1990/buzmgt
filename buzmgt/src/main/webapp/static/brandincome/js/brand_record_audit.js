/*品牌型号设置记录*/
var brandUnderwayTotal = 0;
var brandExpiredTotal = 0;
$(function () {
    initDate();
    initSearchData();
    initFunction();
});

function initDate() {
    $('.J_startDate').datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-right",
        forceParse: 0
    }).on('changeDate', function (ev) {
        $('.form_date_start').removeClass('has-error');
        $('.form_date_end').removeClass('has-error');
        var endInputDateStr = $('.J_endDate').val();
        if (endInputDateStr != "" && endInputDateStr != null) {
            var endInputDate = stringToDate(endInputDateStr).valueOf();
            if (ev.date.valueOf() - endInputDate >= 0) {
                $('.form_date_start').addClass('has-error');
                alert("请输入正确的日期");
            }
        }
    });
    $('.J_endDate').datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-right",
        forceParse: 0
    }).on('changeDate', function (ev) {
        $('.form_date_start').removeClass('has-error');
        $('.form_date_end').removeClass('has-error');
        var startInputDateStr = $('.J_startDate').val();
        if (startInputDateStr != "" && startInputDateStr != null) {
            var startInputDate = stringToDate(startInputDateStr).valueOf();
            if (ev.date.valueOf() - startInputDate < 0) {
                $('.form_date_end').addClass('has-error');
                alert("请输入正确的日期");
            }
        }
    });
}

// 注册索引+1的helper
var handleHelper = Handlebars.registerHelper("addOne", function (index) {
    // 返回+1之后的结果
    return index + 1;
});

/**
 * 获取选中标签
 * @returns {*|jQuery}
 */
function findTab() {
    var tab = $('#myTab li.active').attr('data-title');
    return tab;
}

function initFunction() {
    $("#myTab li").on("click", function () {
        $(this).addClass("active");
        $(this).siblings("li").removeClass("active");
        var nowDate = getTodayDate();
        var tab = findTab();
        switch (tab) {
            case 'underway':
                $("#newon").addClass("in active");
                $("#yguoq").removeClass("in active");
                delete SearchData['sc_LTE_endDate'];
                SearchData['sc_GTE_endDate'] = nowDate;
                findBrandIncomeList();
                break;
            case 'expired':
                $("#yguoq").addClass("in active");
                $("#newon").removeClass("in active");
                delete SearchData['sc_GTE_endDate'];
                SearchData['sc_LTE_endDate'] = nowDate;
                findBrandExpiredList();
                break;
            default:
                break;
        }
    });
}

function initSearchData() {
    var $machineType = $("#machineType").val();
    SearchData['sc_EQ_machineType.id'] = $machineType;
    var nowDate = getTodayDate();
    SearchData['sc_GTE_endDate'] = nowDate;
    findBrandIncomeList();
}

/**
 * 查询当前进行品牌型号列表
 * @param page
 */
function findBrandIncomeList(page) {
    page = page == null || page == '' ? 0 : page;
    SearchData['page'] = page;
    var $planId = $("#planId").val();
    $.ajax({
        url: "/brandIncome/" + $planId,
        type: "GET",
        data: SearchData,
        dataType: "json",
        success: function (brandIncomeData) {
            createBrandUnderwayTable(brandIncomeData);
            var searchTotal = brandIncomeData.totalElements;
            if (searchTotal != brandUnderwayTotal || searchTotal == 0) {
                brandUnderwayTotal = searchTotal;

                brandUnderwayPaging(brandIncomeData);
            }
        },
        error: function () {
            alert("系统异常，请稍后重试！");
        }
    })
}

/**
 * 生成当前进行品牌型号统计列表
 *
 * @param data
 */
function createBrandUnderwayTable(data) {
    var myTemplate = Handlebars.compile($("#brandUnderwayList-table-template").html());
    $('#brandUnderwayList').html(myTemplate(data));
}

/**
 * 当前进行品牌型号分页
 *
 * @param data
 */
function brandUnderwayPaging(data) {
    var totalCount = data.totalElements, limit = data.size;
    $('#brandUnderwayPager').extendPagination({
        totalCount: totalCount,
        showCount: 5,
        limit: limit,
        callback: function (curr, limit, totalCount) {
            findBrandIncomeList(curr - 1);
        }
    });
}

/**
 * 查询已过期品牌型号列表
 * @param page
 */
function findBrandExpiredList(page) {
    page = page == null || page == '' ? 0 : page;
    SearchData['page'] = page;
    var $planId = $("#planId").val();
    $.ajax({
        url: "/brandIncome/" + $planId,
        type: "GET",
        data: SearchData,
        dataType: "json",
        success: function (brandExpiredData) {
            createBrandExpiredTable(brandExpiredData);
            var searchTotal = brandExpiredData.totalElements;
            if (searchTotal != brandExpiredTotal || searchTotal == 0) {
                brandExpiredTotal = searchTotal;

                brandExpiredPaging(brandExpiredData);
            }
        },
        error: function () {
            alert("系统异常，请稍后重试！");
        }
    })
}

/**
 * 生成已过期品牌型号统计列表
 *
 * @param data
 */
function createBrandExpiredTable(data) {
    var myTemplate = Handlebars.compile($("#brandExpiredList-table-template").html());
    $('#brandExpiredList').html(myTemplate(data));
}

/**
 * 已过期品牌型号分页
 *
 * @param data
 */
function brandExpiredPaging(data) {
    var totalCount = data.totalElements, limit = data.size;
    $('#brandExpiredPager').extendPagination({
        totalCount: totalCount,
        showCount: 5,
        limit: limit,
        callback: function (curr, limit, totalCount) {
            findBrandExpiredList(curr - 1);
        }
    });
}

/**
 * 根据时间检索
 */
function goSearch() {
    var tab = findTab();
    var startTime = $('.J_startDate').val();
    var endTime = $('.J_endDate').val();
    if (checkDate(startTime, endTime)) {
        SearchData['sc_GTE_createDate'] = startTime;
        SearchData['sc_LTE_createDate'] = endTime;
        switch (tab) {
            case 'underway':
                findBrandIncomeList();
                break;
            case 'expired':
                findBrandExpiredList();
                break;
            default:
                break;
        }
    }
}

/**
 * 品牌型号审核弹窗
 * @param brandIncomeId
 */
function brandAudit(brandIncomeId) {
    $('#brandAudit').modal({
        keyboard: false
    })
    $("#brandId").val(brandIncomeId);
}

/**
 * 品牌型号审核
 */
function pass() {
    var $brandIncomeId = $("#brandId").val();
    $.ajax({
        url: "/brandIncome/audit/" + $brandIncomeId,
        type: "put",
        dataType: "json",
        success: function (data) {
            $('#brandAudit').modal('hide');
            if (data.status == "success") {
                alert(data.successMsg);
                window.location.reload();
            } else {
                alert(data.errorMsg);
            }
        },
        error: function () {
            alert("系统异常，请稍后重试！");
        }
    })
}

/**
 * 品牌型号方案查看
 * @param brandId
 */
function brandLook(brandIncomeId) {
    window.location.href = base + "brandIncome/show/" + brandIncomeId;
}

/**
 * 品牌型号驳回弹窗
 * @param brandIncomeId
 */
function brandReject(brandIncomeId) {
    $('#brandReject').modal({
        keyboard: false
    })
    $("#brandId").val(brandIncomeId);
}

/**
 * 品牌型号驳回
 */
function reject() {
    var $brandIncomeId = $("#brandId").val();
    $.ajax({
        url : "/brandIncome/reject/" + $brandIncomeId,
        type : "put",
        dataType : "json",
        success : function(data) {
            $('#brandReject').modal('hide');
            if (data.status == "success"){
                alert(data.successMsg);
                window.location.reload();
            }else {
                alert(data.errorMsg);
            }
        },
        error : function() {
            alert("系统异常，请稍后重试！");
        }
    })
}

/**
 * 日期格式转换helper
 */
Handlebars.registerHelper('formDate', function (value) {
    if (value == null || value == "") {
        return "----";
    }
    return changeDateToString_(new Date(value));
});

/**
 * 审核状态helper
 */
Handlebars.registerHelper('whatBrandIncomeStatus', function (value) {
    var html = "";
    if (value === "BACK") {
        html += '<span class="text-hong text-strong">被驳回</span>';
    }
    if (value === "WAIT") {
        html += '<span class="text-zi text-strong">待审核</span>';
    }
    if (value === "OVER") {
        html += '<span class="text-lan text-strong">已审核</span>';
    }
    return new Handlebars.SafeString(html);
});

/**
 * 使用状态helper
 */
Handlebars.registerHelper('compareDate', function (startDate, endDate, status) {
    var html = "";
    var nowDate = getTodayDate();
    if (status === "OVER") {
        html += '<span class="ph-on">进行中</span>';
    }
    if (status === "BACK" || status === "WAIT") {
        html += '<span class="ph-weihes">未使用</span>';
    }
    if (checkDate(endDate, nowDate)) {
        html += '<span class="ph-gery">已过期</span>';
    }
    return new Handlebars.SafeString(html);
});

/**
 * 正在进行操作helper
 */
Handlebars.registerHelper('whatUnderwayButton', function (status,id,auditor) {
    var userId = $("#userId").val();
    var html = "";
    id = Handlebars.Utils.escapeExpression(id);
    html += '<button class="btn bnt-sm bnt-ck spc" data-toggle="modal" data-target="#" onclick="brandLook('+ id +');">查看</button>';
    if (status === "WAIT") {//待审核，未使用，当前登录审核人和指派审核人相符
        html += '<button class="btn bnt-sm bnt-ck spc" data-toggle="modal" data-target="#" onclick="brandAudit('+ id +');">审核</button>';
        html += '<button class="btn bnt-sm bnt-ck spc" data-toggle="modal" data-target="#" onclick="brandReject('+ id +');">驳回</button>';
    }
    return new Handlebars.SafeString(html);
});

/**
 * 已过期操作helper
 */
Handlebars.registerHelper('whatExpiredButton', function (startDate, endDate, status,id) {
    var html = "";
    var nowDate = getTodayDate();
    id = Handlebars.Utils.escapeExpression(id);
    if (checkDate(endDate, nowDate) && status === "OVER") {//已过期
        html += '<button class="btn bnt-sm bnt-ck spc" data-toggle="modal" data-target="#" onclick="brandLook('+ id +');">查看</button>';
    }
    return new Handlebars.SafeString(html);
});