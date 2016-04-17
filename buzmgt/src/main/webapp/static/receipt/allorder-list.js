var remarkedTotal = 0;// 报备总条数
var notRemarkedTotal = 0;// 未报备总条数
$(function() {
//	nowTime();//初始化日期
	findAllOrderList();
})
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

/**
 * 初始化日期 最近3天
 */
function nowTime() {
	var nowDate = changeDateToString(new Date());
	var beforeDate = changeDateToString((new Date()).DateAdd('d', -3));
	SearchData['sc_GTE_creatTime'] = beforeDate;
	SearchData['sc_LTE_creatTime'] = nowDate;
	$('#startTime').val(beforeDate);
	$('#endTime').val(nowDate)
}
/**
 * 检索
 */
function goSearch() {
	var tab = findTab();
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if (checkDate(startTime, endTime)) {
		SearchData['sc_GTE_createTime'] = startTime;
		SearchData['sc_LTE_createTime'] = endTime;
		switch (tab) {
		case 'reported':
			findRemarked();
			break;
		case 'notreported':
			findNOTRemarked();
			break;
		default:
			break;
		}
	}
}
$('.table-export').on(
		'click',
		function() {
			var tab = findTab();
			var startTime = $('#startTime').val();
			var endTime = $('#endTime').val();
			if (checkDate(startTime, endTime)) {
				SearchData['sc_GTE_createTime'] = startTime;
				SearchData['sc_LTE_createTime'] = endTime;
				var jsonToString = JSON.stringify(SearchData);
				window.location.href = base + "receiptRemark/export?type="
						+ tab + "&"+ conditionProcess();
			}

		});
/**
 * 处理检索条件
 * 
 * @returns {String}
 */
function conditionProcess() {
	var SearchData_ = "sc_GTE_createTime="
			+ (SearchData.sc_GTE_creatTime == null ? ''
					: SearchData.sc_GTE_creatTime)
			+ "&sc_LTE_createTime="
			+ (SearchData.sc_LTE_creatTime == null ? ''
					: SearchData.sc_LTE_creatTime);

	return SearchData_;
}
function findAllOrderList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	// delete SearchData['sc_EQ_customSignforException'];
	$.ajax({
		url : "/receiptRemark/getAllOrderList",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createAllOrderTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != remarkedTotal || searchTotal == 0) {
				remarkedTotal = searchTotal;

				allOrderPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}

/**
 * 
 * @param data
 */

function createAllOrderTable(data) {
	var myTemplate = Handlebars.compile($("#allorder-table-template").html());
	$('#allOrderList').html(myTemplate(data));
}
function allOrderPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#allOrderPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findRemarked(curr - 1);
		}
	});
}
function notRemarkedPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#notRemarkedPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findNOTRemarked(curr - 1);
		}
	});
}
Handlebars.registerHelper('formDate', function(value) {
	if(value==null||value==""){
		return "----";
	}
	return changeTimeToString(new Date(value));
});
Handlebars.registerHelper('whatremarkStatus', function(value) {
	var html = "";
	if (value.indexOf("未付款") >= 0) {
		html += '<span class="pay-time icon-tag-wfk">未付款</span>';
	}
	if (value.indexOf("已付款") >= 0) {
		html += '<span class="pay-time icon-tag-yfk">已付款</span>';
	}
	if (value.indexOf("超时") >= 0) {
		html += '<span class="text-red">超时</span>';
	}
	return html;
});
Handlebars.registerHelper('whetherPunish', function(value) {
	var html = "";
	if (value.indexOf("超时") >= 0) {
		html += '<a class="btn btn-yellow btn-sm" href="javascrip:;">扣罚</a>';
	}
	return html;
});
Handlebars.registerHelper('whatPartsCount', function(value) {
	if (value== ""||value==null) { return 0; }
	return value;
});
Handlebars.registerHelper('whatOrderStatus', function(v1,v2) {
	return "";
});
Handlebars.registerHelper('whatCustomSignforStatus', function(value) {
	var html = "";
	if (value === 0) {
		html += '<span class="icon-tag-yc">异常</span>';
	}
	if (value === 1) {
		html += '<span class="icon-tag-zc">正常</span> ';
	}
	return html;
});
