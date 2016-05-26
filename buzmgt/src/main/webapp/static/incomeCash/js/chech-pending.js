var checkPendingTotal = 0;
$(function() {
	initDateInput();// 初始化日期
	findCheckCashList();// 查询列表
//	initExcelExport();//初始化导出excel
	
})
function initDateInput() {
	// 初始化日期 前1天
	var newDate = (new Date()).DateAdd('d', -2);
	var nowDate = changeDateToString(newDate);
	SearchData['sc_EQ_createDate'] = nowDate;
	$('#searchDate').val(nowDate);
	$('#archivingDate').val(nowDate);

	$('.form_datetime').datetimepicker({
		format : "yyyy-mm-dd",
		language : 'zh-CN',
		endDate : newDate,
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		pickerPosition : "bottom-right",
		forceParse : 0
	}).on('changeDate', function(ev) {
		var searchDate = $('#searchDate').val();
	});
}

/**
 * 检索
 */
function goSearch() {
	var Time = $('#searchDate').val();
	if (!isEmpty(Time)) {
		SearchData['sc_EQ_createDate'] = Time;
		findCheckCashList();
	}
}
/**
 * 导出excel
 */
function initExcelExport() {
	$('#table-export').on('click', function() {
		var startTime = $('#startTime').val();
		if (!isEmpty(startTime)) {

			SearchData['sc_EQ_createDate'] = startTime;

			window.location.href = base + "/export";
		}

	});
}
function findTab() {
	var tab = $('#oilCostStatus li.active').attr('data-tital');
	return tab;
}
/**
 * 处理检索条件
 * 
 * @returns {String}
 */
function conditionProcess() {
	var SearchData_ = "sc_EQ_createDate="
			+ (SearchData.sc_GTE_dateTime == null ? ''
					: SearchData.sc_GTE_dateTime)
			+ "&sc_EQ_createDate="
			+ (SearchData.sc_LTE_dateTime == null ? ''
					: SearchData.sc_LTE_dateTime);

	return SearchData_;
}
function findCheckCashList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : "/checkCash",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createCheckPendingTable(orderData);
			console.info(orderData);
//			var searchTotal = orderData.totalElements;
//			if (searchTotal != checkPendingTotal || searchTotal == 0) {
//				checkPendingTotal = searchTotal;
//
//				checkPendingPaging(orderData);
//			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
/**
 * 生成油补统计列表
 * 
 * @param data
 */

function createCheckPendingTable(data) {
	var myTemplate = Handlebars.compile($("#checkPending-table-template").html());
	$('#checkPendingList').html(myTemplate(data));
}
/**
 * 分页
 * 
 * @param data
 */
function checkPendingPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#checkCashPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findCheckCashList(curr - 1);
		}
	});
}
Handlebars.registerHelper('formDate', function(value) {
	if (value == null || value == "") {
		return "----";
	}
	return changeDateToString(new Date(value));
});
/**
 * 待付金额
 */
Handlebars.registerHelper('disposeStayMoney', function(value) {
	if (value == 0 || value == 0.0 || value == 0.00) {
		return value;
	}
	return '<span class="single-exception">'+value+'</span>';
});
/**
 * 根据流水号查询
 */
function findBySalesManName() {
	var salesmanName = $('#salesmanName').val();
	$.ajax({
		// url : base+"/bankTrade?sc_EQ_salesManName=" + salesmanName,
		type : "GET",
		dataType : "json",
		success : function(orderData) {
			if (orderData.totalElements < 1) {
				alert("未查到相关信息！");
				return false;
			}
			createBankTradeTable(orderData);
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
/**
 * 判读是否为空
 * 
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function isEmpty(value) {
	return value == undefined || value == "" || value == null;
}
