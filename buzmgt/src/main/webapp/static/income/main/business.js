var itemTotal = 0;// 补统计总条数
var searchData = {
	"page" : 0,
	"size" : 14,
};
function getSearchParam() {
	var orderno = $("#orderno").val();
	searchData.SC_EQ_userId = userId;
	searchData.SC_EQ_incometype = $("#incomeType").val();
	if (orderno == null || orderno == "") {
		searchData.SC_EQ_cmonth = $("#cmonth").val();
		searchData.SC_EQ_payStatus = $("#payStatus").val();
		searchData.SC_EQ_orderStatus = $("#orderStatus").val();
		searchData.SC_EQ_regionId = $("#regionId").val();
	} else {
		searchData.SC_EQ_cmonth = "";
		searchData.SC_EQ_payStatus = "";
		searchData.SC_EQ_orderStatus = "";
		searchData.SC_EQ_regionId = "";
		searchData.SC_LK_orderno = orderno;
	}
}

function findMainPlanList(page) {
	page = page == null || page == '' ? 0 : page;
	searchData.page = page;
	getSearchParam();
	$.ajax({
		url : base + "mainIncome/businessList/findVolist",
		type : "GET",
		data : searchData,
		dataType : "json",
		success : function(orderData) {
			createTaskTable(orderData);
			var searchTotal = orderData.totalElements;
			// debugger;
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
	Handlebars.registerHelper("adaptOS", function(orderStatus) {
		if (orderStatus == '客户签收') {
			return "blue";
		} else if (orderStatus == '业务签收') {
			return "or";
		} else if (orderStatus == '客户拒收') {
			return 'jv';
		} else {
			return "reed";
		}
	});

	Handlebars.registerHelper("adaptRed", function(payStatus) {
		if (payStatus != null && payStatus == '已付款') {
			return 'use';
		} else {
			return 'use-red';
		}
	});
	//自定义序号小工具
	Handlebars.registerHelper("addOne", function(index) {
		//算出之前的总记录数
		var prenums=searchData.page*searchData.size;
		if (index < 9&&prenums<9) {
			return '0' + (index + 1);
		} else {
			return (prenums+index + 1);
		}
	});

	var myTemplate = Handlebars.compile($("#task-table-template").html());
	$('#userList').html(myTemplate(data));
}

/**
 * @param data
 */
function oilCostPaging(data) {
	var totalCount = data.totalElements, limit = searchData.size;
	$('#usersPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findMainPlanList(curr - 1);
		}
	});
}
function exportExcel() {
	window.location.href = base
			+ "mainIncome/exportBusiness?SC_EQ_incometype=1&SC_EQ_cmonth="
			+ searchData.SC_EQ_cmonth + "&SC_EQ_userId="
			+ searchData.SC_EQ_userId;
}