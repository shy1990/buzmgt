/*添加品牌型号方案*/

$(function () {
    initSelectBrand();
    initDate();
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
    var createTime = $('.J_createTime').val();
    createTime = changeDateToString(stringToDate(createTime));
    $('.J_startDate').datetimepicker('setStartDate', createTime);
    $('.J_startDate').datetimepicker('hide');

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
    var fqTime = $('.J_fqTime').val();
    if (isEmpty(fqTime)){
        fqTime = changeDateToString(stringToDate(fqTime));
        $('.J_endDate').datetimepicker('setEndDate', fqTime);
        $('.J_endDate').datetimepicker('hide');
    }
}

/**
 * 初始化选择品牌
 *
 */
function initSelectBrand() {
    $(".J_brand").change(function () {
        var brandId = $(".J_brand").val();
        if (brandId == "") {
            createGoods("");
            return false;
        }
        findGoods(brandId);
    });
}
/**
 * 查询商品
 * @param brandId
 */
function findGoods(brandId) {
    $.ajax({
        url: base + 'goods/' + brandId,
        type: 'GET',
        dateType: 'JSON',
        success: function (data) {
            createGoods(data);
        },
        error: function (data) {
            alert("系统错误,请稍后重试!");
        }
    });
}
/**
 * createGoods
 * @param data
 */
function createGoods(data) {
    var myTemplate = Handlebars.compile($("#goods-template").html());
    $('#goodList').html(myTemplate(data));
}

function toSubmit() {
    var planId = $(".J_planId").val();
    var machineType = $(".J_machineType").val();
    var brand = $(".J_brand").val();
    var goods = $(".J_goods").val();
    var commissions = $(".J_commissions").val();
    var startDate = $(".J_startDate").val();
    var endDate = $(".J_endDate").val();
    var auditor = $(".J_auditor :selected").val();
    if (checkForm(brand, goods, commissions, startDate, endDate, auditor)) {
        var jsonStr = {
            "planId": planId,
            "machineTypeId": machineType,
            "brandId": brand,
            "goodId": goods,
            "commissions": commissions,
            "startDate": startDate,
            "endDate": endDate,
            "auditor": auditor
        };
        /*jsonStr["brandIncome"] = rule;*/

//============需要转换成字符串的json格式传递参数==============================
        jsonStr = JSON.stringify(jsonStr);
        console.info(jsonStr);
        $.ajax({
            url: "/brandIncome",
            type: "POST",
            beforeSend: function (request) {
                request.setRequestHeader("Content-Type",
                    "application/json; charset=UTF-8");
            },
            dataType: "json",
            data: jsonStr,
            success: function (data) {
                if (data.status == "success") {
                    alert(data.successMsg);
                    window.location.href = base + "/areaAttr/setting?ruleId=" + data.result.id + '&type=BRANDMODEL';
                } else {
                    alert(data.errorMsg);
                }
            },
            error: function () {
                alert("系统错误,请稍后重试!");
            }
        });
    }
}

function checkForm(brand, goods, commissions, startDate, endDate, auditor) {

    if (brand == null || brand.trim() == "") {
        errorMsgShow($(".J_brand"), "请选择品牌");
        return false;
    }

    if (goods == null || goods.trim() == "") {
        errorMsgShow($(".J_goods"), "请选择型号");
        return false;
    }

    if (commissions == null || commissions.trim() == "") {
        errorMsgShow($(".J_commissions"), "请输入提成金额");
        return false;
    }

    if (startDate == null || startDate.trim() == "") {
        errorMsgShow($(".J_startDate"), "请选择开始时间");
        return false;
    }

    if (endDate == null || endDate.trim() == "") {
        errorMsgShow($(".J_endDate"), "请选择结束时间");
        return false;
    }

    if (auditor == null || auditor.trim() == "") {
        alert("请指派审核人!");
        return false;
    }

    return true;
}

function errorMsgShow($option, msg) {
    if ($option == null || $option == "") {
        $option = $(this);
    }
    $option.parents('.new-li').addClass('has-error');
    console.info(msg);
    if (msg != null && msg != "") {
        $option.parents('.new-li').find('.msg-error').html(msg);
    }
}

/**
 * 判读是否为空
 *
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function isEmpty(value) {
    return value == undefined || value == "" || value == null;
}