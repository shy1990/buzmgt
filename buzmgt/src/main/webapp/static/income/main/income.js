var WsearchData = {
	"page" : 0,
	"size" : 20,
	"order" : "id"
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
		//正式环境为此逻辑
		/*
		 * if (state == '已审核'&& yearMonth == month && days > 9) { return
		 * options.fn(this); } else { return options.inverse(this); }
		 */
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



// 发起复核
function sendFh(id) {
	var date = new Date();
	var days = date.getDate();
	var nyr = date.getDate();
}
function goSearch() {
	WsearchData.SC_EQ_month = getMonth();
	WsearchData.SC_LK_namepath = $("#region").val();
	WsearchData.SC_LK_truename = "";
	WsearchData.SC_EQ_roleId = $("#role").val();
	findPlanUserList();
}
function nameSearch() {
	WsearchData.SC_EQ_month = "";
	WsearchData.SC_LK_namepath = "";
	WsearchData.SC_LK_truename = $("#truename").val();
	WsearchData.SC_EQ_roleId = "";
	findPlanUserList();
}
function getMonth() {
	var time = $('body input').val();
	if (time.length > 1) {
		return time.substr(0, 7);
	} else {
		return getNextMonth(2);
	}
}
/**
 * 导出
 */
function dochu() {
	window.location.href = base + "mainIncome/export?SC_EQ_month=" + getMonth();
}
// 得到月份 reduceNum:0是下个月份,1是本月份
function getNextMonth(reduceNum) {
	var date = new Date();
	var month = date.getMonth() - reduceNum;
	var year = date.getFullYear();
	if (month < 8) {
		month = "0" + (month + 2);
	} else {
		month = (month + 2);
	}
	month = year + "-" + month;
	return month;
}