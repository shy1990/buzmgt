var itemTotal = 0;// 补统计总条数
var page = 0;
var size = 20;

// 得到月份 reduceNum:0是下个月份,1是本月份
function getNextMonth(reduceNum) {
	var date = new Date();
	var month = date.getMonth()-reduceNum;
	var year = date.getFullYear();
	if (month < 9) {
		month = "0" + (month + 2);
	} else {
		month = (month + 2);
	}
	month = year + "-" + month;
	return month;
}
// 初始化时间框
function initDate() {
	$('body input').val('');
	$(".form_datetime").datetimepicker({
		format : "yyyy-mm",
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 3,
		minView : 3,
		pickerPosition : "bottom-left",
		forceParse : 0,
	});
	var $_haohe_plan = $('.J_kaohebar').width();
	var $_haohe_planw = $('.J_kaohebar_parents').width();
	$(".J_btn").attr("disabled", 'disabled');
	if ($_haohe_planw === $_haohe_plan) {
		$(".J_btn").removeAttr("disabled");
	}
}
// 查询数据
function getTask(flag) {
	var time = $('body input').val();
	if (month != null && month != '') {
		if (time != month) {
			page = 0;
		}
		month = time;
	}
	findTaskList(page, flag);
}

function findTaskList(cpage, flag) {
	cpage = cpage == null || cpage == '' ? 0 : cpage;
	page=cpage;
	// SearchData['page'] = page;
	var searchData = null;
	var salesManName = $('[name="truename"]').val();
	if (flag == 1) {
		searchData = {
			"page" : cpage,
			"size" : size,
			"salesManName" : salesManName,
			"flag" : 1
		};
	} else {
		searchData = {
			"page" : cpage,
			"size" : size,
			"sffb":$('#sffb').val(),
			"salesManName" : salesManName
		};
		
	}
	$.ajax({
		url : "/monthTask/search?month=" + month,
		type : "GET",
		data : searchData,
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

/**
 * 通过handlebar模板得到html片段并输出
 * 
 * @Param data
 *            得到带分页信息的obj对象
 */
function createTaskTable(data) {
	var myTemplate = Handlebars.compile($("#task-table-template").html());
	$('#taskList').html(myTemplate(data));
}

/**
 * 报备的分页
 * 
 * @Param data
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

// 发布主任务
function issueTask(taskId) {
	var task = {
		"status" : 1
	};
	$.ajax({
		url : "api/monthTasks/" + taskId,
		type : "PATCH",
		data : JSON.stringify(task),
		dataType : "json",
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		success : function(orderData) {
			alert("本任务已成功发布");
			findTaskList(page);
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
