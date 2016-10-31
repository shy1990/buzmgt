var Totalempty = 0;// 油补统计总条数
$(function() {
	nowTime();// 初始化日期
	initfindList();
})

/**
 * 初始化日期 前1天
 */
function nowTime() {
	var newDate = (new Date()).DateAdd('d', -1);
	var nowDate = changeDateToString(newDate);
	var beforeDate = changeDateToString(newDate);
	SearchData['sc_GTE_createDate'] = beforeDate;
	SearchData['sc_LTE_createDate'] = nowDate;
	$('#startTime').val(beforeDate);
	$('#endTime').val(nowDate)
}
function initfindList(){
	var serialNo=$('#serialNo').val();
	if(isEmpty(serialNo)){
		findList();
		return false;
	}
	findBySerialNo();
	
}
/**
 * 检索
 */
function goSearch() {
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if (checkDate(startTime, endTime)) {
		SearchData['sc_GTE_createDate'] = startTime;
		SearchData['sc_LTE_createDate'] = endTime;
		findList();
	}
}
/**
 * 导出
 */
$('.table-export').on(
		'click',
		function() {
			var startTime = $('#startTime').val();
			var endTime = $('#endTime').val();
			if (checkDate(startTime, endTime)) {

				SearchData['sc_GTE_createDate'] = startTime;
				SearchData['sc_LTE_createDate'] = endTime;

				window.location.href= base + "waterOrder/export?"
						+ conditionProcess();
				}
		});
/**
 * 处理检索条件
 * 
 * @returns {String}
 */
function conditionProcess() {
	var SearchData_ = "sc_GTE_createDate="
			+ (SearchData.sc_GTE_createDate == null ? ''
					: SearchData.sc_GTE_createDate)
			+ "&sc_LTE_createDate="
			+ (SearchData.sc_LTE_createDate == null ? ''
					: SearchData.sc_LTE_createDate);

	return SearchData_;
}
function findList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : base+"waterOrder",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != Totalempty || searchTotal == 0) {
				Totalempty = searchTotal;

				addPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
/**
 * 生成列表
 * 
 * @param data
 */

function createTable(data) {
	var myTemplate = Handlebars.compile($("#table-template").html());
	$('#waterOrderList').html(myTemplate(data));
}
/**
 * 报备的分页
 * 
 * @param data
 */
function addPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#addPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findList(curr - 1);
		}
	});
}
/**
 * 未报备的分页
 * 
 * @param data
 */
function abnormalCoordPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#abnormalCoordPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			abnormalCoordList(curr - 1);
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
 * 处理油补握手点 v1:type v2:regionName
 * 
 */
Handlebars.registerHelper('disposePayStatus', function(payStatus) {
	var html = '<span class="icon-wfk">已付款</span>';
	var html_= '<span class="icon-dsh">待付款</span>';
	if(payStatus=='已付款'){
		return html;
	}
	return html_;
});
/**
 * 根据流水号查询
 */
function findBySerialNo() {
	var serialNo = $('#serialNo').val();
	delete SearchData['sc_GTE_createDate'];
	delete SearchData['sc_LTE_createDate'];
	SearchData['sc_EQ_serialNo'] = serialNo;
	findList();
	delete SearchData['sc_EQ_serialNo'];
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
$('#startTime').datetimepicker({
	format : "yyyy-mm-dd",
	language : 'zh-CN',
	endDate : new Date(),
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	minView : 2,
	pickerPosition : "bottom-right",
	forceParse : 0
}).on('changeDate', function(ev) {
	$('.form_date_start').removeClass('has-error');
	$('.form_date_end').removeClass('has-error');
	var endInputDateStr = $('#endTime').val();
	if (endInputDateStr != "" && endInputDateStr != null) {
		var endInputDate = stringToDate(endInputDateStr).valueOf();
		if (ev.date.valueOf() - endInputDate > 0) {
			$('.form_date_start').addClass('has-error');
		}
	}
});
$('#endTime').datetimepicker({
	format : "yyyy-mm-dd",
	language : 'zh-CN',
	endDate : new Date(),
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	minView : 2,
	pickerPosition : "bottom-right",
	forceParse : 0
}).on('changeDate', function(ev) {
	$('.form_date_start').removeClass('has-error');
	$('.form_date_end').removeClass('has-error');
	var startInputDateStr = $('#startTime').val();
	if (startInputDateStr != "" && startInputDateStr != null) {
		var startInputDate = stringToDate(startInputDateStr).valueOf();
		if (ev.date.valueOf() - startInputDate < 0) {
			$('.form_date_end').addClass('has-error');
		}
	}
});
