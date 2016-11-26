var achieveTotal = 0;
$(function() {
	initFunction();
	initExcelExport();// 初始化导出excel
    findAchieveIncomeList();// 查询列表
})
/**
 * 跳转添加页面 获取planId和machineType
 */
function add(planId) {
	var userId = $("#userId").val();
	window.location.href = base + "achieve/add?planId=" + planId
			+ "&machineType=" + machineType
}

/**
 * 检索创建日期查询
 */
function goSearch() {
	var activityDate = $(".J_activityDate").val();
	var issuingDate = $(".J_issuingDate").val();
	if (!isEmpty(activityDate) || !isEmpty(issuingDate)) {
		SearchData['sc_GTE_endDate'] = activityDate;
		SearchData['sc_LTE_startDate'] = activityDate;
		SearchData['sc_EQ_issuingDate'] = issuingDate;

		findAchieveIncomeList();
		delete SearchData['sc_GTE_endDate'];
		delete SearchData['sc_GTE_endDate'];
		delete SearchData['sc_EQ_issuingDate'];

	} else {
		alert("请输入日期！");
	}
}
/**
 * 导出excel
 */
function initExcelExport() {
	$('.table-export').on('click', function() {
		var $planId = $("#userId").val();
		SearchData['sc_EQ_planId'] = $planId;
		var param = parseParam(SearchData);
		delete SearchData['sc_EQ_planId'];
		window.location.href = base + "achieveIncome/export" + "?" + param;
	});
}
function initFunction() {
	$('.J_activityDate').datetimepicker({
		format : "yyyy-mm-dd",
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		pickerPosition : "bottom-right",
		forceParse : 0
	})
	$('.J_issuingDate').datetimepicker({
        format : "yyyy-mm-07",
        language : 'zh-CN',
        weekStart : 1,
        todayBtn : 1,
        autoclose : 1,
        todayHighlight : 1,
        startView : 3,
        minView : 3,
        pickerPosition : "bottom-right",
        forceParse : 0
	})
}
/**
 * 查看明细
 * @param userId
 * @param achieveId
 * @constructor
 */
function CheckDetails(achieveId) {
    var userId = $('#userId').val();
    window.location.href = base + "achieve/detail?userId=" + userId + "&achieveId=" + achieveId;
}

function findAchieveIncomeList(page) {
	page = page == null || page == '' ? 0 : page;
    var userId = $("#userId").val();
    var yearMonth = $("#yearMonth").val();
    SearchData['page'] = page;
    SearchData['sc_EQ_status'] = "PAY";//查询已支付的
    SearchData['sc_EQ_userId'] = userId;//查询用户
    SearchData['sc_GTE_issuingDate'] = yearMonth+"-01";//查询用户
    SearchData['sc_LTE_issuingDate'] = yearMonth+"-28";//查询用户

	console.info(SearchData);
	$.ajax({
		url : "/achieveIncome/total",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
            createAchieveIncomeTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != achieveTotal || searchTotal == 0) {
				achieveTotal = searchTotal;
				initPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
// 注册索引+1的helper
var handleHelper = Handlebars.registerHelper("addOne", function(index) {
	// 返回+1之后的结果
	return index + 1;
});

//减法
var handleHelper = Handlebars.registerHelper("subtract", function(num,shNum) {
	return num -shNum;
});
//创建
function createAchieveIncomeTable(data) {
	var myTemplate = Handlebars.compile($("#achieveincome-table-template").html());
	$('#acheiveIncomeList').html(myTemplate(data));
}
/**
 * 增强 if-else使用 比较日期
 */
Handlebars.registerHelper('compareDate', function(startDate, endDate, options) {
    var nowDate = new Date().getTime();
    console.info(startDate + "," + endDate)
    if (startDate <= nowDate && endDate >= nowDate) {
        // 满足添加继续执行
        return options.fn(this);
    }
    // 不满足条件执行{{else}}部分
    return options.inverse(this);
});
/**
 * 分页
 * 
 * @param data
 */
function initPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#initPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findAchieveIncomeList(curr - 1);
		}
	});
}
Handlebars.registerHelper('formDate', function(value) {
	if (value == null || value == "") {
		return "----";
	}
	return changeDateToString_(new Date(value));
});

/**
 * 自定义if
 */
Handlebars.registerHelper('myIf', function(status, value, options) {
	if (status == value) {
		return options.fn(this);
	} else {
		return options.inverse(this);
	}
});

/**
 * 解析json参数为url参数使用&连接
 */
var parseParam = function(param, key) {
	var paramStr = "";
	if (param instanceof String || param instanceof Number
			|| param instanceof Boolean) {
		paramStr += "&" + key + "=" + encodeURIComponent(param);
	} else {
		$.each(param, function(i) {
			var k = key == null ? i : key
					+ (param instanceof Array ? "[" + i + "]" : "." + i);
			paramStr += '&' + parseParam(this, k);
		});
	}
	return paramStr.substr(1);
};
/**
 * 判读是否为空
 * 
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function isEmpty(value) {
	return value == undefined || value == "" || value == null;
}
/**
 * 查询未匹配银行打款交易记录
 */
function findUnCheckBankTread() {
	$.ajax({
		url : base + "/checkCash/unCheck",
		type : "GET",
		dataType : "json",
		success : function(data) {
			createUnCheckTable(data);
		},
		error : function(data) {
			alert("查询失败！");
		}
	})
}
/**
 * 删除未匹配记录
 */
function deleteUnCheck(id) {
	$.ajax({
		url : base + "/checkCash/delete/" + id,
		type : "GET",
		dataType : "json",
		success : function(data) {
			if ("success" === data.status) {
				alert(data.successMsg);
				findUnCheckBankTread();
				return;
			}
			alert(data.errorMsg);
		},
		error : function(data) {
			alert("操作失败");
		}
	})
}
