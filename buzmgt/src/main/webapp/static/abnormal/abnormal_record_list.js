var membertotal=0,ywtotal=0;
var userId;
$(function(){
	//先获取业务员的id
	userId=getUserId();
	findYwOrderList();
	findMemberOrderList();
	showTab();
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
			SearchData['sc_GTE_creatTime'] = startTime;
			SearchData['sc_LTE_creatTime'] = endTime;
			if('ywtab'==tab){
				delete SearchData['sc_EQ_customSignforException']
//				window.location.href = base + "ordersignfor/export?type=ywOrderSignfor&"
//				+ conditionProcess() ;
			}else if('membertab'==tab){
//				window.location.href = base + "ordersignfor/export?sc_EQ_customSignforException=1&"
//				+ conditionProcess(); 
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
	var strSearchData ="sc_GTE_creatTime="
			+ (SearchData.sc_GTE_creatTime == null ? ''
					: SearchData.sc_GTE_creatTime)
			+ "&sc_LTE_creatTime="
			+ (SearchData.sc_LTE_creatTime == null ? ''
					: SearchData.sc_LTE_creatTime);
			
	return strSearchData;
}
function showTab(){
	var _$tabs = $('.J_URL');
	if("box_tab2"==_$tabs.attr('data-tabs')){
		var tabs = _$tabs.find('li:last-child a');
		tabs.click();//展开客户签收列表
	}
}
/**
 * 获取userId
 * @returns
 */
function getUserId(){
	var userId = $('.J_UserID').attr('data-user');
	return userId;
}
function findYwOrderList(page){
	page=page==null||page==''? 0 :page;
	SearchData['page']=page;
	delete SearchData['sc_EQ_customSignforException'];
	$.ajax({
		url:"ordersignfor/getrecord/"+userId,
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
//	SearchData['sc_EQ_customSignforException']=1;
	var url_="ordersignfor/getrecord/"+userId;
	console.info(url_);
	$.ajax({
		url:url_,
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
	if(value != ""&&value!= null){
		return changeDateToString(new Date(value));
	}else{
		return "0000-00-00 00:00";
	}
});
//
Handlebars.registerHelper('whatStatus', function(value) {
	var html='';
	if(value === 2 || value === 3 || value ===0){
		html='<span class="status-over">已签收</span>';
	}else {
		html='<span class="status-not">未签收</span>';
	}
	return html;
});

/**
 * 业务揽收是否异常
 * @param v1 creatTime
 * @param v2 yewuSignforTime
 */
Handlebars.registerHelper('whatYwTag', function(v1,v2) {
	var html='';
	if(checkTag(v1,v2)){
		html='<span class="icon-tag-zc">正常</span>';
	}else {
		html='<span class="icon-tag-yc">异常</span>';
	}
	return html;
});
/**
 * 业务揽收操作
 * @param v1 creatTime
 * @param v2 yewuSignforTime
 */
Handlebars.registerHelper('whatOperate', function(v1,v2) {
	var html='';
	if(checkTag(v1,v2)){
		html='';
	}else {
		html='<a class="btn btn-yellow btn-sm" href="javascrip:;">扣罚</a>';
	}
	return html;
});

/**
 * 用于传递参数
 */
Handlebars.registerHelper('parament', function(v1,v2) {
	return checkTag(v1,v2);
});

/**
 * 客户签收是否异常
 * @param v1 creatTime
 */
Handlebars.registerHelper('whatMemberTag', function(value) {
	var html='';
	if(value ===1){
		html='<span class="icon-tag-yc">异常</span>';
	}else {
		html='<span class="icon-tag-zc">正常</span>';
	}
	return html;
});
/**
 * 业务给-- 客户-- 送货失效计算
 * @param v1 customSignforTime
 * @param v2 yewuSignforTime
 */
Handlebars.registerHelper('whatAging', function(v1,v2) {
	if(v1==''||v1==null||v2==''||v2==null){
		return "--小时--分钟";
	}
	var value=v1-v2;
	var hour=parseInt(value/1000/3600);
	var minute=parseInt((value/1000/3600-hour)*60);
	return hour+"小时"+minute+"分钟";
});

/**
 * 客户付款方式
 * @param value orderPayType
 */
Handlebars.registerHelper('whatPayType', function(value) {
	var html='';
	if(value == null || value==''){return '未付款';}
	if(value ===0){
		html='线上付款';
	}else if(value ===1){
		html='pos付款';
	}else {
		html='现金';
	}
	return html;
});

/**
 * 判断是否异常
 * @param v1 creatTime
 * @param v2 yewuSignforTime
 */
function checkTag(v1,v2){
	//依据判定时间
	var gap=$('.J_Gap').attr("data-time");
	var grps=gap.split(":");
	var nowDate=new Date();
	var createDate=new Date(v1);
	var gapDate=createDate;
	
	gapDate.setDate(createDate.getDate()+1);
	gapDate.setHours(grps[0]);
	gapDate.setMinutes(grps[1]);
	if(v2 == '' || v2 ==null ){
		if(nowDate.getTime()>gapDate.getTime()){
			return false;
		}else{
			return true;
		}
	}
	var ywSignforDate=new Date(v2);
	if(ywSignforDate.getTime()>gapDate.getTime()){
		return false;
	}else{
		return true;
	}
}
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









