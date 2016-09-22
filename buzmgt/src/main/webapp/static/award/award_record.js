var awardTotal = 0;
$(function() {
	initFunction();
	initExcelExport();// 初始化导出excel
	initSearchData();
	findAchieveList();// 查询列表
})
/**
 * 删除
 * @param awardId
 */
function delAchieve(awardId){
	if(confirm("确定要删除数据吗？")){
		$.ajax({
			url : base + "award/"+awardId,
			type : "DELETE",
			dataType : "JSON",
			success : function(data){
				if(data.result =="success"){
					alert(data.message);
					window.location.reload();
					return false;
				}
				alert(data.message);
			},
			error : function(data){
				alert("网络异常，稍后重试！");
			}
		});
	}
}
/**
 * 审核操作
 * @param awardId
 * @param status
 */
function auditAchieve(awardId,status){
	if(confirm("确认操作？")){
		$.ajax({
			url : base + "award/" + awardId + "?status=" + status,
			type : "PATCH",
			dataType : "JSON",
			success : function(data){
				if(data.result =="success"){
					alert(data.message);
					window.location.reload();
					return false;
				}
				alert(data.message);
			},
			error : function(data){
				alert("网络异常，稍后重试！");
			}
		})
	}
	return ;
}
/**
 * 检索模糊查询
 */
function goSearch() {
	var goodName = $('#searchGoodsname').val();
	if (!isEmpty(goodName)) {
		$.ajax({
			url : "/goods/likeBrandName?name=" + goodName,
			type : "GET",
			dateType : "JSON",
			success : function(data) {
				console.info(data);
				if (data.length > 0) {
					var ids = data.join(',');
					SearchData['sc_IN_brand.id'] = ids;
					findAchieveList();
					delete SearchData['sc_IN_brand.id'];
					return false;
				}
				alert("没有此品牌");
			},
			error : function(data) {
				alert("网络异常，稍后重试！");
			}
		});
	} else {
		alert("请输入品牌名称");
	}
}
/**
 * 检索创建日期查询
 */
function goSearchByCreateDate() {
	var startDate = $(".J_startDate").val();
	var endDate = $(".J_endDate").val();
	if (!isEmpty(startDate) || !isEmpty(endDate)) {
		SearchData['sc_GTE_createDate'] = startDate;
		SearchData['sc_LTE_createDate'] = endDate;
		findAchieveList();
		delete SearchData['sc_GTE_createDate'];
		delete SearchData['sc_LTE_createDate'];

	} else {
		alert("请输入日期！");
	}
}
/**
 * 导出excel
 */
function initExcelExport() {
	$('.table-export').on('click', function() {
		var $planId = $("#planId").val();
		SearchData['sc_EQ_planId'] = $planId;
		var param = parseParam(SearchData);
		delete SearchData['sc_EQ_planId'];
		window.location.href = base + "award/export" + "?" + param;
	});
}
function initFunction() {
	$('.J_startDate').datetimepicker({
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
	}).on('changeDate', function(ev) {
		$('.form_date_start').removeClass('has-error');
		$('.form_date_end').removeClass('has-error');
		var endInputDateStr = $('.J_endDate').val();
		if (endInputDateStr != "" && endInputDateStr != null) {
			var endInputDate = stringToDate(endInputDateStr).valueOf();
			if (ev.date.valueOf() - endInputDate >= 0) {
				$('.form_date_start').addClass('has-error');
				alert("请输入正确的日期");
				return false;
			}
		}
	});
	$('.J_endDate').datetimepicker({
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
	}).on('changeDate', function(ev) {
		$('.form_date_start').removeClass('has-error');
		$('.form_date_end').removeClass('has-error');
		var startInputDateStr = $('.J_startDate').val();
		if (startInputDateStr != "" && startInputDateStr != null) {
			var startInputDate = stringToDate(startInputDateStr).valueOf();
			if (ev.date.valueOf() - startInputDate < 0) {
				$('.form_date_end').addClass('has-error');
				alert("请输入正确的日期");
				return false;
			}
		}
	});
	$("#myTab li").on('click', function() {
		var title = $(this).attr('title');
		checkTitle(title);
		findAchieveList();
	});
}
function initSearchData() {
	var title = $("#myTab li.active").attr("title");
	checkTitle(title);
}
function checkTitle(title) {

	if ("going" == title) {
		console.info(title);
		delete SearchData['sc_LTE_endDate'];
		SearchData['sc_GTE_endDate'] = getTodayDate();
		return false;
	}
	delete SearchData['sc_GTE_endDate'];
	SearchData['sc_LTE_endDate'] = getTodayDate();
	console.info(title);
	return false;
}

function findAchieveList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	console.info(SearchData);
	var $planId = $("#planId").val();
	$.ajax({
		url : "/award/" + $planId,
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createAchieveTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != awardTotal || searchTotal == 0) {
				awardTotal = searchTotal;
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
/**
 * 正在进行中列表
 * 
 * @param data
 */
function createAchieveTable(data) {
	var title = $("#myTab li.active").attr("title");
	if ("going" == title) {
		createGoingAchieveTable(data);
		return false;
	}
	createPastAchieveTable(data);
	return false;
}

function createGoingAchieveTable(data) {
	var myTemplate = Handlebars.compile($("#award-table-template").html());
	$('#goingAchieveList').html(myTemplate(data));
}
/**
 * 已过期的列表
 * 
 * @param data
 */
function createPastAchieveTable(data) {
	var myTemplate = Handlebars.compile($("#award-table-template").html());
	$('#pastAchieveList').html(myTemplate(data));
}
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
			findCheckCashList(curr - 1);
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
 * isAuditor
 */
Handlebars.registerHelper('isAuditor', function(value, options) {
	var auditor = $('#userId').val();
	if (auditor == value) {
		return options.fn(this);
	} else {
		return options.inverse(this);
	}
});
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
 * 待付金额
 */
Handlebars.registerHelper('disposeStayMoney', function(status, value, options) {
	if (value == 0 || value == 0.0 || value == 0.00) {
		return value;
	}
	return '<span class="single-exception">' + value + '</span>';
});
/**
 * 待付金额
 */
Handlebars
		.registerHelper(
				'isCheckStatus',
				function(isCheck, userId, createDate) {
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
