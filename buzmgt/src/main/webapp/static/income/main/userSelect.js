var WsearchData = {
		"page" : 0,
		"size" : 10,
	};
function findPlanUserList(page) {
	page = page == null || page == '' ? 0 : page;
	WsearchData.page=page;
	$.ajax({
		url :"/mainPlanUsers/",
		type : "GET",
		data : WsearchData,
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
		return index%3+1;
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
	var totalCount = data.number, limit = WsearchData.size;
	$('#usersPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findMainPlanList(curr - 1);
		}
	});
}
