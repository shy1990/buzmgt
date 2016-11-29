var bankTradeTotal = 0;
var firstImportDate;
$(function() {
    nowTime();// 初始化日期
    findBankTradeList();// 查询列表

    $('#importDate').datetimepicker({
        format : "yyyy-mm-dd",
        language : 'zh-CN',
        endDate : new Date(),
        weekStart : 1,
        todayBtn : false,
        autoclose : 1,
        todayHighlight : 1,
        startView : 2,
        minView : 2,
        pickerPosition : "bottom-right",
        forceParse : 0
    }).on('changeDate', function(ev) {
        var importDate = $('#importDate').val();
        firstImportDate=importDate;
    });

})


/**
 * 初始化日期 前1天
 */
function nowTime() {
    var newDate = (new Date()).DateAdd('d', -1);
    var nowDate = changeDateToString(newDate);
    SearchData['date'] = nowDate;
    $('#searchDate').val(nowDate)
    return nowDate;
}

/**
 * 检索
 */
function goSearch() {
    var Time = $('#searchDate').val();
    if (!isEmpty(Time)) {
        SearchData['date'] = Time;
        findBankTradeList();
    }
}
function findTab() {
    var tab = $('#oilCostStatus li.active').attr('data-tital');
    return tab;
}
/**
 * 处理检索条件
 *
 * @returns {String}
 */
function conditionProcess() {
    var SearchData_ = "date="
        + (SearchData.sc_GTE_dateTime == null ? ''
            : SearchData.sc_GTE_dateTime)
        + "&sc_LTE_dateTime="
        + (SearchData.sc_LTE_dateTime == null ? ''
            : SearchData.sc_LTE_dateTime);

    return SearchData_;
}
function findBankTradeList(page) {
    page = page == null || page == '' ? 0 : page;
    SearchData['page'] = page;
    $.ajax({
        url : "/bill/showBillList",
        type : "GET",
        data : SearchData,
        dataType : "json",
        success : function(orderData) {
            createBankTradeTable(orderData);
            var searchTotal = orderData.totalElements;
            if (searchTotal != bankTradeTotal || searchTotal == 0) {
                bankTradeTotal = searchTotal;

                bankTradePaging(orderData);
            }
        },
        error : function() {
            alert("系统异常，请稍后重试！");
        }
    })
}
/**
 * 生成油补统计列表
 *
 * @param data
 */

function createBankTradeTable(data) {
    var myTemplate = Handlebars.compile($("#bankTrade-table-template").html());
    $('#bankTradeList').html(myTemplate(data));
}
/**
 * 分页
 *
 * @param data
 */
function bankTradePaging(data) {
    var totalCount = data.totalElements, limit = data.size;
    $('#bankTradePager').extendPagination({
        totalCount : totalCount,
        showCount : 5,
        limit : limit,
        callback : function(curr, limit, totalCount) {
            findBankTradeList(curr - 1);
        }
    });
}
Handlebars.registerHelper('formDate', function(value) {
    if (value == null || value == "") {
        return "----";
    }
    return changeTimeToString(new Date(value));
});

Handlebars.registerHelper('todaypay', function(value) {
    if (value == null || value == "") {
        return "0";
    }
  return value;
});


Handlebars.registerHelper('historypay', function(value) {
    if (value == null || value == "") {
        return "0";
    }
    return value;
});

/**
 * 根据姓名查询
 */
function findBySalesManName() {
    /*page = page == null || page == '' ? 0 : page;
    SearchData['page'] = page;*/
    var Time = $('#searchDate').val();
    if (!isEmpty(Time)) {
        SearchData['date'] = Time;
    }
    var salesmanName = $('#salesmanName').val();
    SearchData['salesmanName'] = salesmanName;
    $.ajax({
        url : "bill/showBillList",
        type : "GET",
        data : SearchData,
        dataType : "json",
        success : function(orderData) {
            if (orderData.totalElements < 1) {
                alert("未查到相关信息！");
                return false;
            }
            createBankTradeTable(orderData);
        },
        error : function() {
            alert("系统异常，请稍后重试！");
        }
    })
}


//查看跳转页面
function view(userId,todayAllShouldPay,todayShouldPay,historyShouldPay,todayDate) {
    window.location.href= base + "bill/persionBillView?userId="+ userId+"&todayAllShouldPay="+todayAllShouldPay+"&todayShouldPay="+todayShouldPay+"&historyShouldPay="+historyShouldPay+"&todayDate="+todayDate;
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
$('#searchDate').datetimepicker({
    format : "yyyy-mm-dd",
    language : 'zh-CN',
    endDate : new Date(),
    weekStart : 1,
    todayBtn : 1,
    autoclose : 1,
    todayHighlight : 1,
    startView : 2,
    minView : 2,
    pickerPosition : "bottom-right",
    forceParse : 0
}).on('changeDate', function(ev) {
    $('.form_date_start').removeClass('has-error');
    $('.form_date_end').removeClass('has-error');
    var endInputDateStr = $('#endTime').val();
    if (endInputDateStr != "" && endInputDateStr != null) {
        var endInputDate = stringToDate(endInputDateStr).valueOf();
        if (ev.date.valueOf() - endInputDate > 0) {
            $('.form_date_start').addClass('has-error');
        }
    }
});
