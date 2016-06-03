$(function() {
	initDate();// 初始化日期
	findTaskList();// 初始查询
});

var itemTotal = 0;// 补统计总条数
var month = '';
var page = 0;
var size = 7;

function initDate() {
	$('body input').val('');
	$(".form_datetime").datetimepicker({
		format : "yyyy-mm",
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		pickerPosition : "bottom-left",
		forceParse : 0
	});
	var $_haohe_plan = $('.J_kaohebar').width();
	var $_haohe_planw = $('.J_kaohebar_parents').width();
	$(".J_btn").attr("disabled", 'disabled');
	if ($_haohe_planw === $_haohe_plan) {
		$(".J_btn").removeAttr("disabled");
	}
}
// 查询数据
function getTask() {
	var time = $('body input').val();
	if (time == '') {
		alert("请选择月份");
		return;
	} else {
		if (time != month) {
			page = 0;
		}
		month = time;
		findTaskList(page);
	}
}

function findTaskList(page) {
	page = page == null || page == '' ? 0 : page;
	// SearchData['page'] = page;
	var select=$("#basic");
	 var goals=select.val();
	$.ajax({
		url : "/monthTask/subTasks",
		type : "GET",
		data : {
			"page" : page,
			"size" : size,
			"goals":goals,
			"parentId":parentId
		},
		dataType : "json",
		success : function(orderData) {
			createTaskTable(orderData);
			var searchTotal = orderData.number;
			if (searchTotal != itemTotal || searchTotal == 0 || page == 0) {
				itemTotal = searchTotal;
				oilCostPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}

function createTaskTable(data) {
	var myTemplate = Handlebars.compile($("#task-table-template").html());
	$('#taskList').html(myTemplate(data));
}

/**
 * 报备的分页
 * 
 * @param data
 */
function oilCostPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#abnormalCoordPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findTaskList(curr - 1);
		}
	});
}
