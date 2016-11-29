var remarkedTotal = 0;// 报备总条数
var notRemarkedTotal = 0;// 未报备总条数
$(function() {
	nowTime();//初始化日期
	findAllOrderList();
})
$('#startTime').datetimepicker({
	format : "yyyy-mm-dd hh:ii",
	language : 'zh-CN',
	endDate : new Date(),
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	minView : 0,
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
	format : "yyyy-mm-dd hh:ii",
	language : 'zh-CN',
	endDate : new Date(),
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	minView : 0,
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
 * 初始化日期 最近1天
 */
function nowTime() {
	var nowDate = changeTimeToString(new Date());
	var beforeDate = changeTimeToString((new Date()).DateAdd('d', -1));
	SearchData['sc_GTE_createTime'] = beforeDate;
	SearchData['sc_LTE_createTime'] = nowDate;
	$('#startTime').val(beforeDate);
	$('#endTime').val(nowDate)
}
/**
 * 检索
 */
function goSearch() {
//	var tab = findTab();
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if (checkDate(startTime, endTime)) {
		SearchData['sc_GTE_createTime'] = startTime;
		SearchData['sc_LTE_createTime'] = endTime;
			findAllOrderList();
	}
}
$('.table-export').on('click',
		function() {
			var startTime = $('#startTime').val();
			var endTime = $('#endTime').val();
			if (checkDate(startTime, endTime)) {
				SearchData['sc_GTE_createTime'] = startTime;
				SearchData['sc_LTE_createTime'] = endTime;
				window.location.href = base + "receiptRemark/export?type=allOrder&"+ conditionProcess();
			}

		});
/**
 * 处理检索条件
 * 
 * @returns {String}
 */
function conditionProcess() {
	var SearchData_ = "sc_GTE_createTime="
			+ (SearchData.sc_GTE_createTime == null ? ''
					: SearchData.sc_GTE_createTime)
			+ "&sc_LTE_createTime="
			+ (SearchData.sc_LTE_createTime == null ? ''
					: SearchData.sc_LTE_createTime);

	return SearchData_;
}
function findAllOrderList(page) {
	page = ((page == null || page == '') ? 0 : page);
	SearchData['page'] = page;
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
			findAllOrderList(curr - 1);
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
/*
 * 配件
 */
Handlebars.registerHelper('whatPartsCount', function(value) {
	if (value== ""||value==null) { return 0; }
	return value;
});
/**
 * v1:fastMailTime(发货时间)
 * v2:orderStatus
 * 订单状态判断
 */
Handlebars.registerHelper('whatOrderStatus', function(v1,v2) {
	if(checkEmpty(v2)){
		if(checkEmpty(v1)){
			return "未发货"
		}
		return "已发货";
	}
	return v2;
});
/**
 * 判读是否为空
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function checkEmpty(value){
	return value ==""||value==null;
} 
//<span class="icon-tag-wfka">未付款</span>
//<span class="icon-tag-wbb">未报备</span>
//<span class="icon-tag-ybb">已报备</span>
//<span class="icon-tag-yfka">收现金</span>
//<span class="text-sbluea">（已付款）</span>agentPayTime
//<span class="icon-tag-yfka">刷poss</span>
//<span class="icon-tag-yfka">网上支付</span>
/**
 * 客户付款状态判断
 */
Handlebars.registerHelper('whatOrderPayType', function (value, dealType) {
	var html = '';
	var tag = '';
	value = dealType;
	switch (value) {
		case '未支付':
			tag = 'wfka';
			break;
		case '未报备':
			tag = 'wbb';
			break;
		case '已报备':
			tag = 'ybb';
			break;
		case 'LH':
			tag = 'yfka';
			value = '联行支付';
			break;
		case 'yeePay':
			tag = 'yfka';
			value = '易宝支付';
			break;
		case 'XS':
			tag = 'yfka';
			value = '微信/支付宝';
			break;
		case 'YL':
			tag = 'yfka';
			value = '储蓄卡/信用卡';
			break;
		default:
			tag = 'yfka';
			break;
	}
	html = '<span class="icon-tag-' + tag + '">' + value + '</span>';
	return html;
});

/**
 * agentPayStatus(代理商付款状态)
 * 代理商付款状态判断
 */
Handlebars.registerHelper('whatAgentPayStatus', function(value) {
	var html='';
	var tag='';
	if (value == '已付款'){
		tag='yfka';
		html='<span class="icon-tag-'+tag+'">'+value+'</span>';
	}else {
		tag='wfka';
		html='<span class="icon-tag-'+tag+'">'+value+'</span>';
	}
	return html;
});

/**
 * 根据订单号查询订单
 */
function findByOrderNo(){
	var orderNo=$('#orderNo').val();
	$.ajax({
		url : "/receiptRemark/getAllOrderList?sc_EQ_orderNo="+orderNo,
		type : "GET",
		dataType : "json",
		success : function(orderData) {
			if(orderData.totalElements<1){
				alert("未查到相关订单信息");
				return false;
			}
			createAllOrderTable(orderData);
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}


