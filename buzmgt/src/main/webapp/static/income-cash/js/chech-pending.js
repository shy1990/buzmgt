var checkPendingTotal = 0;
$(function() {
	initDateInput();// 初始化日期
	findCheckCashList();// 查询列表
	initExcelExport();//初始化导出excel
	
})
function initDateInput() {
	// 初始化日期 前1天
	var newDate = (new Date()).DateAdd('d', -1);
	var nowDate = changeDateToString(newDate);
	SearchData['sc_EQ_payDate'] = nowDate;
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
		SearchData['sc_EQ_payDate'] = Time;
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

			SearchData['sc_EQ_payDate'] = startTime;

			window.location.href = base + "" + conditionProcess();
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
	var SearchData_ = "sc_GTE_dateTime="
			+ (SearchData.sc_GTE_dateTime == null ? ''
					: SearchData.sc_GTE_dateTime)
			+ "&sc_LTE_dateTime="
			+ (SearchData.sc_LTE_dateTime == null ? ''
					: SearchData.sc_LTE_dateTime);

	return SearchData_;
}
function findCheckCashList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : "/cash",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createBankTradeTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != checkPendingTotal || searchTotal == 0) {
				checkPendingTotal = searchTotal;

				checkCashPaging(orderData);
			}
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

function createBankTradeTable(data) {
	var myTemplate = Handlebars.compile($("#bankTrade-table-template").html());
	$('#bankTradeList').html(myTemplate(data));
}
/**
 * 分页
 * 
 * @param data
 */
function checkCashPaging(data) {
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
	return changeTimeToString(new Date(value));
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
