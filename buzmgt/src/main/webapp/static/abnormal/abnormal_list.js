var membertotal=0,ywtotal=0;
$(function(){
	nowTime();
	findYwOrderList();
	findMemberOrderList();
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
	$('.table-export').on('click',function(){
		var tab =$('.abnormal-body .nav-tabs li.active').attr('data-tital');
		var startTime=$('#startTime').val();
		var endTime=$('#endTime').val();
		if(checkDate(startTime,endTime)){
			SearchData['sc_GTE_createTime'] = startTime;
			SearchData['sc_LTE_createTime'] = endTime;
			if('ywtab'==tab){
				delete SearchData['sc_EQ_customSignforException']
				window.location.href = base + "ordersignfor/export?type=ywOrderSignfor&"
				+ conditionProcess() ;
			}else if('membertab'==tab){
				window.location.href = base + "ordersignfor/export?sc_EQ_customSignforException=1&"
				+ conditionProcess(); 
			}
		}

	});
});
/**
 * 处理检索条件
 * 
 * @returns {String}
 */
function conditionProcess() {
	var SearchData_ ="sc_GTE_createTime="
			+ (SearchData.sc_GTE_createTime == null ? ''
					: SearchData.sc_GTE_createTime)
			+ "&sc_LTE_createTime="
			+ (SearchData.sc_LTE_createTime == null ? ''
					: SearchData.sc_LTE_createTime);
			
	return SearchData_;
}
function findYwOrderList(page){
	page=page==null||page==''? 0 :page;
	SearchData['page']=page;
	delete SearchData['sc_EQ_customSignforException'];
	$.ajax({
		url:"ordersignfor/list?type=ywOrderSignfor",
		type:"GET",
		data: SearchData,
		dataType:"json",
		success:function(orderData){
			createYwTable(orderData);
			var searchTotal=orderData.totalElements;
			if (searchTotal != ywtotal || searchTotal == 0) {
				ywtotal = searchTotal;

                ywPaging(orderData);
            }
		},error:function(){
			alert("系统异常，请稍后重试！")
		}
	})
}

function createYwTable(data) {
	var myTemplate = Handlebars.compile($("#ywSignfor-table-template").html());
	$('#ywOrderList').html(myTemplate(data));
//	Paging(data);
}
function findMemberOrderList(page){
	page=page==null||page==''? 0 :page;
	SearchData['page']=page;
	SearchData['sc_EQ_customSignforException']=1;
	$.ajax({
		url:"ordersignfor/list?type=&page="+page,
		type:"GET",
		dataType:"json",
		data: SearchData,
		success:function(orderData){
			createMemberTable(orderData);
			var searchTotal=orderData.totalElements;
			if (searchTotal != membertotal || searchTotal == 0) {
				membertotal = searchTotal;

                memberPaging(orderData);
            }
		},error:function(){
			alert("系统异常，请稍后重试！")
		}
	})
}

	function createMemberTable(data) {
		var myTemplate = Handlebars.compile($("#memberSignfor-table-template").html());
	$('#memberOrderList').html(myTemplate(data));
//	Paging(data);
}
function ywPaging(data) {
    var totalCount = data.totalElements,
            limit = data.size;
    $('#ywPager').extendPagination({
        totalCount: totalCount,
        showCount: 5,
        limit: limit,
        callback: function (curr, limit, totalCount) {
        	findYwOrderList(curr-1);
        }
    });
}
function memberPaging(data) {
	var totalCount = data.totalElements,
    limit = data.size;
	createMemberTable(data);
    $('#memberPager').extendPagination({
		totalCount: totalCount,
		showCount: 5,
		limit: limit,
		callback: function (curr, limit, totalCount) {
			findMemberOrderList(curr-1);
		}
	});
}
//日期格式化
Handlebars.registerHelper('formDate', function(value) {
	return changeDateToString(new Date(value));
});
////送货时效
//Handlebars.registerHelper('checkAging', function(value) {
//	var aging="";
//	var hour=parseInt(value/1000/3600);
//	var minute=parseInt((value/1000/3600-hour)*60);
//	return hour+"小时"+minute+"分钟";
//});

function goSearch() {
	var tab =$('.abnormal-body .nav-tabs li.active').attr('data-tital');
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	if(checkDate(startTime,endTime)){
		SearchData['sc_GTE_createTime'] = startTime;
		SearchData['sc_LTE_createTime'] = endTime;
		if('ywtab'==tab){
			findYwOrderList();
		}else if('membertab'==tab){
			findMemberOrderList();
		}
	}
}
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

function checkDate(startTimeStr,endTimeStr){
	//当两个字段都不为空时进行校验;
	var fale=(startTimeStr != "" && startTimeStr != null)&&
	(endTimeStr != '' && endTimeStr != null);
	if(fale){
		var startDate= stringToDate(startTimeStr);
		var endDate= stringToDate(endTimeStr);
		if(endDate.valueOf()-startDate.valueOf()>=0){
			return true;
		}else{
			return false;
		}
	}else{
		return true;
	}
}









