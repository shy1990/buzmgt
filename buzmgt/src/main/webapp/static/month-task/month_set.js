var itemTotal = 0;// 补统计总条数
var month = '';
var page = 0;
var size = 20;

function findTaskList(page) {
	page = page == null || page == '' ? 0 : page;
	// SearchData['page'] = page;
	var searchData = {
		"page" : page,
		"size" : size
	};

	$.ajax({
		url : "/monthTask/lookup/setData/" + taskId,
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
	console.info(data);
	$('#Pager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findTaskList(curr - 1);
		}
	});
}
