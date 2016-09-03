var WsearchData = {
	"page" : 0,
	"size" : 20,
	"order":"id"
};
var itemTotal = 0;
// 加载页面
function findPlanUserList(page) {
	page = page == null || page == '' ? WsearchData.page : page;
	WsearchData.page = page;
	$.ajax({
		url : "/mainIncome/getVoPage",
		type : "GET",
		data : WsearchData,
		dataType : "json",
		success : function(orderData) {
			createTaskTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != itemTotal || searchTotal == 0 || page == 0) {
				itemTotal = searchTotal;
				oilCostPaging(orderData);
			}
		},
		error : function(data) {
			alert("系统异常，请稍后重试！");
		}
	});
}

function createTaskTable(data) {
	Handlebars.registerHelper("isfh", function(state, options) {
		if (state == '已审核') {
			// 满足添加继续执行
			return options.fn(this);
		} else {
			// 不满足条件执行{{else}}部分
			return options.inverse(this);
		}
	});

	var date = new Date();
	var days = date.getDate();
	var month = date.getMonth();
	var yearMonth = date.getFullYear() + "-"
			+ (month < 10 ? "0" + month : month);
	Handlebars.registerHelper("canfh", function(state, month, options) {
		return options.fn(this);
		/*if (state == '已审核'&& yearMonth == month && days > 9) {
			return options.fn(this);
		} else {
			return options.inverse(this);
		}*/
	});

	var myTemplate = Handlebars.compile($("#user-table-template").html());
	$('#userList').html(myTemplate(data));
}
/**
 * 报备的分页
 * 
 * @param data
 */
function oilCostPaging(data) {
	var totalCount = data.totalElements, limit = WsearchData.size;
	$('#usersPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findPlanUserList(curr - 1);
		}
	});
}

// 查找数组下标
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i].salesmanId == val.salesmanId)
			return i;
	}
	return -1;
};
// 自定义删除数组
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
function openPlan(planId) {
	initOtherPlan(planId);
	otherPlanFlag = true;
	$('#otherPlan').modal('show');
	$('#user').modal('hide');
}

function getSearchData() {
	WsearchData.SC_LK_namepath = $("#namePath").val();
	WsearchData.SC_EQ_roleId = $("#roleId").val();
	WsearchData.SC_EQ_levelName = $("#levelName").val();
	WsearchData.SC_EQ_starsLevel = $("#starsLevel").val();
	WsearchData.SC_LK_truename = $("#trueName").val();
}

// 初始化时间框
function initDateInput() {
	$('body input').val('');
	$(".form_datetime").datetimepicker({
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
	});
	var $_haohe_plan = $('.J_kaohebar').width();
	var $_haohe_planw = $('.J_kaohebar_parents').width();
	$(".J_btn").attr("disabled", 'disabled');
	if ($_haohe_planw === $_haohe_plan) {
		$(".J_btn").removeAttr("disabled");
	}
}
// 发起复核
function sendFh(id) {
	var date = new Date();
	var days = date.getDate();
	var nyr = date.getDate();
}
function goSearch(){
	WsearchData.SC_EQ_month=getMonth();
	WsearchData.SC_LK_namepath=$("#region").val();
	WsearchData.SC_LK_truename="";
	WsearchData.SC_EQ_roleId=$("#role").val();
	findPlanUserList();
}
function nameSearch(){
	WsearchData.SC_EQ_month="";
	WsearchData.SC_LK_namepath="";
	WsearchData.SC_LK_truename=$("#truename").val();
	WsearchData.SC_EQ_roleId="";
	findPlanUserList();
}
function getMonth(){
	var time = $('body input').val();
	return time.substr(0,7);
}
/**
 * 导出
 */
function dochu(){
	window.location.href = base + "mainIncome/export?SC_EQ_month="+getMonth();
}