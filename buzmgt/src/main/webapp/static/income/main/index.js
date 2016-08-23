var itemTotal = 0;// 补统计总条数
var page = 0;
var size = 20;
// 已自定义区域数组
var regionArr = new Array();
function findMainPlanList(page) {
	page = page == null || page == '' ? 0 : page;
	var searchData = {
		"page" : page,
		"size" : size,
	};
  var region = $("#region").val();
	$.ajax({
		url : "/mainPlan/queryPlan",
		type : "GET",
		data : {regionId:region},
		dataType : "json",
		success : function(orderData) {
			createTaskTable(orderData);
//			initRegionArr(orderData.content);
			var searchTotal = orderData.number;
			if (searchTotal != itemTotal || searchTotal == 0 || page == 0) {
				itemTotal = searchTotal;
				oilCostPaging(orderData);
			}
		},
		error : function(data) {
			alert("系统异常，请稍后重试！");
		}
	})
}

function createTaskTable(data) {
	Handlebars.registerHelper("addOne", function(index) {
		// 返回+1之后的结果
		return index + 1;
	});
	// 大于
	Handlebars.registerHelper("gt", function(num1, num2) {
		if (num1 > num2) {
			return "是";
		} else {
			return "否";
		}
	});
	var myTemplate = Handlebars.compile($("#task-table-template").html());
	$('#acont').html(myTemplate(data));
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