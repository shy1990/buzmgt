var achieveTotal = 0;
$(function () {
    // initFunction();
    initExcelExport();// 初始化导出excel
    findAchieveCourseList();// 查询列表
})
/**
 * 检索模糊查询
 */
function goSearchSalesman() {
    var userName = $('#searchSalesMan').val();
    if (!isEmpty(userName)) {
        SearchData['sc_EQ_userVo.truename'] = userName;
        findAchieveCourseList();
        delete SearchData['sc_EQ_userVo.truename'];
    } else {
        alert("请输入名称！");
    }
}
/**
 * 导出excel
 */
function initExcelExport() {
    $('.table-export').on('click', function () {
        var $planId = $("#planId").val();
        SearchData['sc_EQ_planId'] = $planId;
        var param = parseParam(SearchData);
        delete SearchData['sc_EQ_planId'];
        // window.location.href = base + "achieve/export" + "?" + param;
    });
}
/**
 * 查看明细
 * @param userId
 * @param achieveId
 * @constructor
 */
function CheckDetails(userId, achieveId) {
    window.location.href = base + "achieve/detail?userId=" + userId + "&achieveId=" + achieveId;
}
/**
 * 查询列表
 * @param page
 */
function findAchieveCourseList(page) {
    page = page == null || page == '' ? 0 : page;
    SearchData['page'] = page;
    SearchData['sc_EQ_status'] = 'PAY';
    $.ajax({
        url: "/achieveIncome/total",
        type: "GET",
        data: SearchData,
        dataType: "json",
        success: function (orderData) {
            createAchieveTable(orderData);
            var searchTotal = orderData.totalElements;
            if (searchTotal != achieveTotal || searchTotal == 0) {
                achieveTotal = searchTotal;
                initPaging(orderData);
            }
        },
        error: function () {
            alert("系统异常，请稍后重试！");
        }
    })
}
// 注册索引+1的helper
var handleHelper = Handlebars.registerHelper("addOne", function (index) {
    // 返回+1之后的结果
    return index + 1;
});
/**
 * 正在进行中列表
 *
 * @param data
 */
function createAchieveTable(data) {
    var myTemplate = Handlebars.compile($("#course-table-template").html());
    $('#achieveCourseList').html(myTemplate(data));
}
/**
 * 分页
 *
 * @param data
 */
function initPaging(data) {
    var totalCount = data.totalElements, limit = data.size;
    $('#initPager').extendPagination({
        totalCount: totalCount,
        showCount: 5,
        limit: limit,
        callback: function (curr, limit, totalCount) {
            findAchieveCourseList(curr - 1);
        }
    });
}
Handlebars.registerHelper('formDate', function (value) {
    if (value == null || value == "") {
        return "----";
    }
    return changeDateToString_(new Date(value));
});
/**
 * 自定义if
 */
Handlebars.registerHelper('myIf', function (status, value, options) {
    if (status == value) {
        return options.fn(this);
    } else {
        return options.inverse(this);
    }
});
/**
 * disposeStar 处理星***级
 */
Handlebars.registerHelper('disposeStar', function (starsLevel) {
    var html = "";
    for (var i = 0; i < starsLevel; i++) {
        html += '<i class=" icon-x ico-xx"></i>';
    }
    return html;
});
/**
 * start-end-Date
 * 起止结束日期
 */
Handlebars.registerHelper('start-end-Date', function (starsLevel) {
    var html = $('#srart-end-date').text();
    return html;
});
/**
 * disposeNum 处理达量台数
 */
Handlebars.registerHelper('disposeNum', function (userId) {
    var number = findUserIdForGroup(userId);
    var html = "";
    //{{groupName: string, numberFirstAdd: string, numberSecondAdd: string, numberThirdAdd: string}}
    $.each(number, function (name, value) {
        console.info(value);
        if (!isEmpty(value)) {
            html += value + "台 | ";
        }
    })
    return html.substr(0, html.length - 2);
});
/**
 * 分组查询
 */
Handlebars.registerHelper('disposeGroup', function (userId, options) {
    var group = findUserIdForGroup(userId);
    //{{groupName: string, numberFirstAdd: string, numberSecondAdd: string, numberThirdAdd: string}}
    if (isEmpty(group['groupName'])) {
        return "无分";
    }
    return group['groupName'];
});
/**
 * 查询userId是否存在achieveJson中
 * 同时对达量指标查询
 * @param userId
 * @returns {{groupName: string, numberFirst, numberSecond, numberThird}}
 */
function findUserIdForGroup(userId) {
    var numberFirst = achieveJson['numberFirst'];
    var numberSecond = achieveJson['numberSecond'];
    var numberThird = achieveJson['numberThird'];

    var newGroup = {
        "groupName": "",
        "numberFirst": numberFirst,
        "numberSecond": numberSecond,
        "numberThird": numberThird
    };
    //查询是否存在
    for (var i = 0; i < achieveJson['groupNumbers'].length; i++) {
        var groupNumber = achieveJson['groupNumbers'][i];
        for (var j = 0; j < groupNumber['groupUsers'].length; j++) {
            var groupUser = groupNumber['groupUsers'][j];
            if (groupUser['userId'] == userId) {
                var groupName = groupNumber['groupName'];
                var numberFirstAdd = groupNumber['numberFirstAdd'];
                var numberSecondAdd = groupNumber['numberSecondAdd'];
                var numberThirdAdd = groupNumber['numberThirdAdd'];
                newGroup = {
                    "groupName": groupName,
                    "numberFirst": numberFirst + numberFirstAdd,
                    "numberSecond": numberSecond + numberSecondAdd,
                    "numberThird": numberThird + numberThirdAdd
                }
                return newGroup;
            }
        }
    }
    return newGroup;
}
/**
 * 增强 if-else使用 比较日期
 */
Handlebars.registerHelper('compareDate', function (startDate, endDate, options) {
    var nowDate = new Date().getTime();
    if (startDate <= nowDate && endDate >= nowDate) {
        // 满足添加继续执行
        return options.fn(this);
    }
    // 不满足条件执行{{else}}部分
    return options.inverse(this);
});
/**
 * 待付金额
 */
Handlebars.registerHelper('isCheckStatus',
    function (isCheck, userId, createDate) {
        var formcreateDate = changeDateToString(new Date(createDate));
        var html = '<button class="btn btn-sm btn-blue" onClick="checkPending(\''
            + userId
            + '\',\''
            + formcreateDate
            + '\')">确认</button>'
        if (isCheck == '已审核') {
            return '<button class="btn btn-sm btn-blue" disabled>已审核</button> ';
        }
        return html;
    });



