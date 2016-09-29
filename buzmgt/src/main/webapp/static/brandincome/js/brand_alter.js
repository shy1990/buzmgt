/*修改品牌型号方案*/

$(function () {
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

/*修改*/
function modify_attrSet(id) {
    $('#changed').modal('show').on('shown.bs.modal', function () {
        $("#sure_update").click(function () {
            var commission = $("#input_modify").val();
            var ruleId = $("#ruleId").val();//获取方案id
            $.ajax({
                url: '/areaAttr/' + id,
                type: 'put',
                data: {
                    commission: commission
                },
                success: function (data) {
                    alert(data);
                    window.location.href = '/brandIncome/update/' + ruleId;
                }

            });
        });
    })
}

/*删除*/
function deleteAttrSet(id) {
    var ruleId = $("#ruleId").val();//获取方案id
    $.ajax({
        url: '/areaAttr/' + id,
        type: 'delete',
        success: function (data) {
            alert(data);
            window.location.href = '/brandIncome/update/' + ruleId;
        }
    });
}

function toSubmit() {
    var ruleId = $("#ruleId").val();//获取规则id
    var planId = $(".J_planId").val();//获取方案id
    var machineType = $(".J_machineType").val();//获取机器类别id
    var jsonStr = {
        "commissions": $(".J_commissions").val(),
        "startDate": $(".J_startDate").val(),
        "endDate": $(".J_endDate").val(),
        "auditor": $(".J_auditor :selected").val()
    };
    /*jsonStr["brandIncome"] = rule;*/

    //需要转换成字符串的json格式传递参数
    jsonStr = JSON.stringify(jsonStr);
    console.info(jsonStr);
    $.ajax({
        url: "/brandIncome/update/" + ruleId,
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
                window.location.href = base + "brandIncome/record?planId=" + planId + "&machineType=" + machineType;
            } else {
                alert(data.errorMsg);
            }
        },
        error: function () {
            alert("系统错误,请稍后重试!");
        }
    });
}