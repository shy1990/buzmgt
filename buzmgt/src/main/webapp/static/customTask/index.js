var itemTotal = 0;// 补统计总条数
var page = 0;
var size = 20;
searchParam = {
		"page" : page,
		"size" : size,
		"sort" : "id,desc"
	};
function findTaskList(page1) {

	var searchParam = getSearchParam(page1);
	$.ajax({
		url : "/customTask/all",
		type : "GET",
		dataType : "json",
		data : searchParam,
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
// 获得查询参数
function getSearchParam(page1) {
	page = page1;
	searchParam.page=page;
	var salesName = $("#salesName").val();
	searchParam["SC_salesName"] = salesName;
	var startTime = $("#start-time").val();
	var endTime = $("#end-time").val();
	var type = $("#customType").val();
	if (startTime != "") {
		searchParam["SC_GT_createTime"] = startTime;
	}
	if (endTime != "") {
		searchParam["SC_LT_createTime"] = endTime;
	}
	if (type != "") {
		searchParam["SC_EQ_type"] = type;
	}
	return searchParam;
}
/**
 * 通过handlebar模板得到html片段并输出
 * 
 * @Param data
 *            得到带分页信息的obj对象
 */
var Customtype = [ "zc", "sh", "kf","bf","xm"];
function createTaskTable(data) {
	Handlebars.registerHelper("qfType", function(type) {
		return Customtype[type];
	});
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

function initDate(){
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
//判断是否只显示扣罚
function handlePunish(){
	if(glomonth.length>1){
		var monthstr=glomonth+"-01";
		var date=stringToDate(monthstr);		
		date.setMonth(date.getMonth()+1);
		date.setDate(date.getDate()-1);
		var endTime=changeDateToString(date);
		$("#end-time").val(endTime);
		date.setMonth(date.getMonth()-1);
		var startTime =changeDateToString(date);
		$("#start-time").val(startTime);
		$("#customType").val(2);
		searchParam["SC_salesId"] =salesId;
		$("#searchDiv").css("display","none");
	}
}