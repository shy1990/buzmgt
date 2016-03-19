var membertotal=0,ywtotal=0;
$(function(){
	findYwOrderList();
	findMemberOrderList();
	$('#startTime').datetimepicker({
		format : "yyyy-mm-dd",
		language : 'zh-CN',
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
		console.info(endInputDateStr);
		if (endInputDateStr != "" && endInputDateStr != null) {
			var endInputDate = stringToDate(endInputDateStr).valueOf();
			console.info(endInputDate);
			if (ev.date.valueOf() - endInputDate > 0) {
				$('.form_date_start').addClass('has-error');
			}
		}
	});
	$('#endTime').datetimepicker({
		format : "yyyy-mm-dd",
		language : 'zh-CN',
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
});

function findYwOrderList(page){
	page=page==null||page==''? 0 :page;
	SearchData['page']=page;
	console.info(SearchData);
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

	var myTemplate = Handlebars.compile($("#memberSignfor-table-template").html());
	function createMemberTable(data) {
	$('#memberOrderList').html(myTemplate(data));
//	Paging(data);
}
function ywPaging(data) {
    var totalCount = data.totalElements,
            limit = data.size;
    createYwTable(data);
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
    $('#memberPager').extendPagination({
		totalCount: data.totalElements,
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
//送货时效
Handlebars.registerHelper('checkAging', function(value) {
	var aging="";
	var hour=parseInt(value/1000/3600);
	var minute=parseInt((value/1000/3600-hour)*60);
	return hour+"小时"+minute+"分钟";
});

function goSearch() {
	var tab =$('.abnormal-body .nav-tabs li.active').attr('data-tital');
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	if(checkDate(startTime,endTime)){
		SearchData['sc_GTE_creatTime'] = startTime;
		SearchData['sc_LTE_creatTime'] = endTime;
		if('ywtab'==tab){
			findYwOrderList();
		}else if('membertab'==tab){
			findMemberOrderList();
		}
	}
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









