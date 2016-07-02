var itemTotal = 0;// 补统计总条数
var page = 0;
var size = 10;
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
	var salesName = $("#salesName").val();
	var searchParam = {
		"page" : page,
		"size" : size,
		"SC_salesName" : salesName,
		"sort" : "id,desc"
	};
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
var Customtype = [ "zc", "bf", "kf" ];
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