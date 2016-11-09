var itemTotal = 0;// 补统计总条数
var searchData = {
	"page" : 0,
	"size" : 15,
};
function findMainPlanList(page) {
	page = page == null || page == '' ? 0 : page;
	searchData.page = page;
	searchData.regionId = $("#region").val();
	$.ajax({
		url : base + "mainPlan/queryPlan",
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
		error : function(data) {
			alert("系统异常，请稍后重试！");
		}
	});
}

function createTaskTable(data) {
	Handlebars.registerHelper("getImg", function(index) {
		return index % 3 + 1;
	});

	Handlebars.registerHelper("ifCkeck", function(planId) {
		return planId + "&check="+check;
	});
	Handlebars.registerHelper("ifNew", function(content, options) {
		if (check == 2) {
			return options.fn(this);
		} else {
			// 不满足条件执行{{else}}部分
			return options.inverse(this);
		}
	});
	var myTemplate = Handlebars.compile($("#task-table-template").html());
	$('#planList').html(myTemplate(data));
}
/**
 * 报备的分页
 * 
 * @param data
 */
function oilCostPaging(data) {
	var totalCount = data.number, limit = searchData.size;
	$('#abnormalCoordPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findMainPlanList(curr - 1);
		}
	});
}

function deletePlan() {
	$.ajax({
		url : "/mainPlan/delete/" + gloPlanId,
		type : "post",
		dataType : "json",
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		success : function(data) {
			alert("删除成功,今天已算收益将会重新计算!!");
		},
		error : function(data) {
			alert("系统异常，请稍后重试！");
		}
	});
}