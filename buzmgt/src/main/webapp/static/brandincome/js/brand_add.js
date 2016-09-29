/*添加品牌型号方案*/

$(function() {
	initSelectBrand();
	initDate();
});

function initDate(){
	$('.J_startDate').datetimepicker({
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
		var endInputDateStr = $('.J_endDate').val();
		if (endInputDateStr != "" && endInputDateStr != null) {
			var endInputDate = stringToDate(endInputDateStr).valueOf();
			if (ev.date.valueOf() - endInputDate >= 0) {
				$('.form_date_start').addClass('has-error');
				alert("请输入正确的日期");
			}
		}
	});
	$('.J_endDate').datetimepicker({
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
		var startInputDateStr = $('.J_startDate').val();
		if (startInputDateStr != "" && startInputDateStr != null) {
			var startInputDate = stringToDate(startInputDateStr).valueOf();
			if (ev.date.valueOf() - startInputDate < 0) {
				$('.form_date_end').addClass('has-error');
				alert("请输入正确的日期");
			}
		}
	});
}

/**
 * 初始化选择品牌
 *
 */
function initSelectBrand() {
	$(".J_brand").change(function(){
		var brandId=$(".J_brand").val();
		if(brandId == ""){
			createGoods("");
			return false;
		}
		findGoods(brandId);
	});
}
/**
 * 查询商品
 * @param brandId
 */
function findGoods(brandId){
	$.ajax({
		url : base + 'goods/' + brandId,
		type : 'GET',
		dateType : 'JSON',
		success : function(data) {
			createGoods(data);
		},
		error : function(data) {
			alert("系统错误,请稍后重试!");
		}
	});
}
/**
 * createGoods
 * @param data
 */
function createGoods(data) {
	var myTemplate = Handlebars.compile($("#goods-template").html());
	$('#goodList').html(myTemplate(data));
}

function toSubmit() {
	var jsonStr = {
		"planId": $(".J_planId").val(),
		"machineTypeId" : $(".J_machineType").val(),
		"brandId" : $(".J_brand").val(),
		"goodId" : $(".J_goods").val(),
		"commissions": $(".J_commissions").val(),
		"startDate": $(".J_startDate").val(),
		"endDate": $(".J_endDate").val(),
		"auditor":  $(".J_auditor :selected").val()
	};
	/*jsonStr["brandIncome"] = rule;*/

//============需要转换成字符串的json格式传递参数==============================
	jsonStr = JSON.stringify(jsonStr);
	console.info(jsonStr);
	$.ajax({
		url : "/brandIncome",
		type : "POST",
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
				"application/json; charset=UTF-8");
		},
		dataType : "json",
		data : jsonStr,
		success : function(data) {
			if(data.status == "success"){
				alert(data.successMsg);
				window.location.href = base + "/areaAttr/setting?ruleId="+data.result.id + '&type=BRANDMODEL';
			}else {
				alert(data.errorMsg);
			}
		},
		error : function() {
			alert("系统错误,请稍后重试!");
		}
	});
}