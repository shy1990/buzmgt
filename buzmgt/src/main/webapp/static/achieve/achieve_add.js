/**
 * 达量设置添加 2016/08/30 ChenGuop
 * 
 */
$(function() {
	initSelectBrand();
	initSelectMachineType();
});
function addNumber() {
	var name = $(this).prev("input").attr("name");
	var html = "";
	var html_ = "";
	switch (name) {
	case "numberFirst":
		html += '<input type="text" name="numberSecond" value="" />台';
		break;

	case "numberSecond":
		html += '<input type="text" name="numberThird" value="" />台';
		break;
	default:

		break;
	}
	$(this).before(html);
}
// 动态添加规则
function addRule() {
	$('.J_RULE').html("");
	var html = '';
	var first = $('.J_numberFirst').val();
	var second = $('.J_numberSecond').val();
	var third = $('.J_numberThird').val();
	
	console.info(first + "," + second + "," + third);
	if (third != "") {
		
	} else if (second != "") {

		html += '<input type="text" data-min="" data-max="'
				+ first
				+ '" name="money" '
				+ 'onkeyup="this.value=this.value.replace(/[^\\d,.]/g,\'\') " '
				+ 'onafterpaste="this.value=this.value.replace(/[^\\d,.]/g,\'\') " />';
		html += '<input type="text" data-min="'
				+ first
				+ '" data-max="'
				+ second
				+ '" name="money" '
				+ 'onkeyup="this.value=this.value.replace(/[^\\d,.]/g,\'\') " '
				+ 'onafterpaste="this.value=this.value.replace(/[^\\d,.]/g,\'\') " />';
		html += '<input type="text" data-min="'
				+ second
				+ '" data-max="" name="money" '
				+ 'onkeyup="this.value=this.value.replace(/[^\\d,.]/g,\'\') " '
				+'onafterpaste="this.value=this.value.replace(/[^\d,\.]/g,\'\') " />';
	} else {
		html += '<input type="text" data-min="" data-max="'
				+ first
				+ '" name="money" '
				+ 'onkeyup="this.value=this.value.replace(/[^\\d,.]/g,\'\') " '
				+ 'onafterpaste="this.value=this.value.replace(/[^\\d,.]/g,\'\') " />';

		html += '<input type="text" data-min="'
				+ first
				+ '" data-max="" name="money" '
				+ 'onkeyup="this.value=this.value.replace(/[^\\d,.]/g,\'\') " '
				+ 'onafterpaste="this.value=this.value.replace(/[^\\d,.]/g,\'\')" />';
	}
//	$('.J_RULE').append(html);
}
function chkFirstValue(){
	var first = $('.J_numberFirst').val();
	var second = $(this).val()
	console.info(first+","+second);
	if(Number(second)<=Number(first)){
		alert("请输入正确的阶段二任务量");
		return false;
	}
}
function chkScendValue(){
	var third = $(this).val();
	var second = $('.J_numberSecond').val();
	console.info(third+","+second);
	if(Number(third)<=Number(second)){
		alert("请输入正确的阶段二任务量");
		return false;
	}
}
//初始化选择类别（机型）
function initSelectMachineType() {
	$(".J_machineType").change(function(){
		var machineType=$(this).val();
		if(machineType == ""){
			createBrandType("");
			createGoods("");
			return false;
		}
		findBrandType(machineType);
	});
}
//查询品牌
function findBrandType(machineType){
	$.ajax({
		url:"/goods/getBrand?code="+machineType,
		type : 'GET',
		dateType : 'JSON',
		success : function(data) {
			createBrandType(data);
		},
		error : function(data) {
			alert("网络异常，稍后重试！");
		}
			
	})
}
/**
 * 创建品牌
 * @param data
 */
function createBrandType(data) {
	var myTemplate = Handlebars.compile($("#brands-template").html());
	$('#brandList').html(myTemplate(data));
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
			alert("网络异常，稍后重试！");
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
//	var jsonStr = $("#achieveForm").serialize();
	var jsonStr = {
		"planId": $("#achieveForm input[name='planId']").val(),
		"machineTypeId" : $("#achieveForm input[name='machineType']").val(),
		"brandId" : $(".J_brand").val(),
		"goodId" : $("#goodList").val(),
		"numberFirst": $("#achieveForm input[name='numberFirst']").val(),
        "numberSecond": $("#achieveForm input[name='numberSecond']").val(),
        "numberThird": $("#achieveForm input[name='numberThird']").val(),
        "startDate": $("#achieveForm input[name='startDate']").val(),
        "endDate": $("#achieveForm input[name='endDate']").val(),
        "issuingDate": $("#achieveForm input[name='issuingDate']").val(),
        "auditor":  $(".J_auditor").val(),
        "remark":  $(".J_remark").val()
	};
/*	
	'rewardPunishRules':[
        {
          "min": null,
          "max": 500,
          "money": 2,
          "type": "ACHIEVE",
          "flag": "NORMAL"
        },
        {
          "min": 500,
          "max": 1000,
          "money": 4,
          "type": "ACHIEVE",
          "flag": "NORMAL"
        },
        {
          "min": 1000,
          "money": 6,
          "type": "ACHIEVE",
          "flag": "NORMAL"
        }
      ]"
      "groupNumbers": [
        {
          "numberFirstAdd": 10,
          "numberSecondAdd": 10,
          "numberThirdAdd": null,
          "type": "ACHIEVE",
          "groupName": "A",
          "flag": "NORMAL",
          "groupUsers": [
            {
              "userId": "A122523523"
            },
            {
              "userId": "A12252352323"
            }
          ]
        }
      ] 
*/
	
//============需要转换成字符串的json格式传递参数==============================	
	jsonStr = JSON.stringify(jsonStr);
	$.ajax({
		url : "/achieve",
		type : "POST",
		contentType : 'application/json;charset=utf-8', // 设置请求头信息
		dataType : "json",
		data : jsonStr,
		success : function(data) {
			alert(data);
		},
		error : function(res) {
			alert(res.responseText);
		}
	});
}
