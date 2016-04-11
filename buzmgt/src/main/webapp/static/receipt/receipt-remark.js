var remarkedTotal = 0;
$(function() {
	nowTime();//初始化日期
	findRemarked();
	findNOTRemarked();
})
$('.nav-task li').on("click", function() {
	$(this).addClass('active');
	$(this).siblings('li').removeClass('active');
	var item = $(this).data('item');
	deleteStatus()
	switch(item){
	case 'all': ; break;
	case 'unpay': SearchData['sc_EQ_status'] = "UnPay";break;
	case 'timeout': SearchData['sc_GTE_status'] = "UnPayLate";break;
	case 'payed': SearchData['sc_EQ_status'] = "OverPay";break;
	default:break;
	}
});
function deleteStatus(){
	delete SearchData['sc_EQ_status'];
	delete SearchData['sc_GTE_status'];
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

/**
 * 初始化日期
 * 最近3天
 */
function nowTime(){
	var nowDate=changeDateToString(new Date());
	var beforeDate=changeDateToString((new Date()).DateAdd('d',-3));
	SearchData['sc_GTE_createTime'] = beforeDate;
	SearchData['sc_LTE_createTime'] = nowDate;
	$('#startTime').val(beforeDate);
	$('#endTime').val(nowDate)
}
/**
 * 检索
 */
function goSearch() {
	var tab =$('.abnormal-body .nav-tabs li.active').attr('data-tital');
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	if(checkDate(startTime,endTime)){
		SearchData['sc_GTE_createTime'] = startTime;
		SearchData['sc_LTE_createTime'] = endTime;
		if('reported'==tab){
			findRemarked();
		}else if('membertab'==tab){
		}
	}
}
$('.table-export').on('click',function() {
	var tab = $('.abnormal-body .nav-tabs li.active').attr('data-tital');
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if (checkDate(startTime, endTime)) {
		SearchData['sc_GTE_creatTime'] = startTime;
		SearchData['sc_LTE_creatTime'] = endTime;
		if ('ywtab' == tab) {
			delete SearchData['sc_EQ_customSignforException']
		window.location.href = base + "ordersignfor/export?type=ywOrderSignfor&"
									+ conditionProcess();
						} else if ('membertab' == tab) {
							window.location.href = base
									+ "ordersignfor/export?sc_EQ_customSignforException=1&"
									+ conditionProcess();
						}
					}

				});

function findRemarked(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	// delete SearchData['sc_EQ_customSignforException'];
	$.ajax({
		url : "/receiptRemark/remarkList",
		type : "POST",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createRemarkedTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != remarkedTotal || searchTotal == 0) {
				remarkedTotal = searchTotal;

				 remarkedPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！")
		}
	})
}
function findNOTRemarked(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	// delete SearchData['sc_EQ_customSignforException'];
	$.ajax({
		url : "/receiptRemark/remarkList",
		type : "POST",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createRemarkedTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != remarkedTotal || searchTotal == 0) {
				remarkedTotal = searchTotal;
				
				remarkedPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！")
		}
	})
}
/**
 * 
 * @param data
 */

function createRemarkedTable(data) {
	var myTemplate = Handlebars.compile($("#remarked-table-template").html());
	$('#remarkedList').html(myTemplate(data));
}

function remarkedPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#remarkedPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findRemarked(curr - 1);
		}
	});
}
Handlebars.registerHelper('formDate', function(value) {
	return changeDateToString(new Date(value));
});
Handlebars.registerHelper('whatremarkStatus', function(value) {
	var html="";
	if(value.indexOf("未付款") >= 0){
		html+='<span class="pay-time icon-tag-wfk">未付款</span>';
	}
	if(value.indexOf("已付款") >= 0){
		html+='<span class="pay-time icon-tag-yfk">已付款</span>';
	}
	if(value.indexOf("超时") >= 0){
		html+='<span class="text-red">超时</span>';
	}
	return html;
});

