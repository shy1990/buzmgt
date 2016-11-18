/**
 * 达量设置添加 2016/08/30 ChenGuop
 * 
 */
var $planId ='';
$(function() {
	$planId = $('.J_planId').val();
	initSelectMachineType();
	initFunction();
	
});
// 动态添加规则
function addRule() {
//	$('.J_RULE').html("");
	rule =new Array();
	var html = '';
	var first = $('.J_numberFirst').val();
	var second = $('.J_numberSecond').val();
	var third = $('.J_numberThird').val();
	var number = first + "," + second + "," + third;
	var numArr =number.split(",");
	//显示数据
	createNumber(numArr);
	//赋值
	var $numberInput = $(".J_number_box input");
	for(var i=0; i<numArr.length; i++){
		$numberInput[i].value = numArr[i];
	}
	//1.更改周期量显示
	//2.增加奖罚规则
//	var rule =new Array();
	var leng = 0; //rule = n(*number*)+1;
	if (third != "") {
		leng = 4;
	} else if (second != "") {
		leng = 3;
	} else {
		leng = 2;
	}
	
	var ave = "";
	for(var i=0;i<leng;i++){
		var min = "";
		var max = "";
		min = ave;
		max = numArr[i]==undefined ? '': numArr[i];
		ave = max;
		rule[i]= {'num':i,'min':min,'max':max,"type": "ACHIEVE",
		          "flag": "NORMAL"};
	}
	console.info(rule);
	createRules(rule);
	$('#zzrwul').modal('hide');
	createNumberAdd();
	
}

function addMoney(num,value){
	rule[num]["money"] = Number(value);
	console.info(rule);
}
function initFunction(){
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
	$('.J_issuingDate').datetimepicker({
		format : "yyyy-mm-03",
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 3,
		minView : 3,
		pickerPosition : "bottom-right",
		forceParse : 0
	});
	var groupName = "";
	$('#tjry').on('shown.bs.modal', function (event) {
		  var button = $(event.relatedTarget) // Button that triggered the modal
		  groupName = button.data('groupname') // Extract info from data-* attributes
		  var planId = $('.J_planId').val();
		  var modal = $(this);
		  modal.find('#groupName').val(groupName);
//		  modal.find('#planId').val(planId);
		  //初始化分组
		  createGroupSonByNameTable(userList);
		  groupList = new Array();
		  groupList = groupList.concat(userList);
		});
	$('.J_btnadd').on("click",function(){
		var html = $(".J_chose_goods li:first")[0].outerHTML;
		$('.J_chose_goods').append(html);
		initSelectMachineType();
		$(".J_chose_goods .J_remove:gt(0)").show();
	});	
	findAchieve();
}
function findAchieve(){
	var awardId = $('.J_awardId').val();
	$.ajax({
		url : base + "award/getAward/" +awardId,
		type : "GET",
		dataType : "JSON",
		success :function(data){
			award = data.result;
			console.info(award);
			rule = award['rewardPunishRules'];
			groupNumbers = award['groupNumbers'];
			disposeUserList(groupNumbers);
			//所有用户的UserId
		},
		error : function(data){
			alert("网络异常，稍后重试！");
		}
	})
}

function disposeUserList(groupNumbers){
	userList = new Array(); 
	for(var i=0;i<groupNumbers.length;i++){
		var groupName = groupNumbers[i]['groupName'];
		var groupUsers = groupNumbers[i]['groupUsers'];
		for(var j=0;j<groupUsers.length;j++){
			userList[j]={
				"userId" : groupUsers[j]['userId'], 
				"userName" : groupUsers[j]['planUser']['truename'],
				"groupName" : groupName
			}
		}
	}
	creategroupAll();
	return userList;
}
/**
 * 移出的goods型号
 * @param object
 */
function removeChoseGoods(object){
	$(object).parents('li').remove();
}
/**
 * 验证二阶段数量
 * @returns {Boolean}
 */
function chkScendValue(){
	var first = $('.J_numberFirst').val();
	var second = $('.J_numberSecond').val()
	if(first == "" ){
		alert("请输入阶段一任务量");
		return false;
	}
	if(second != "" && Number(second)<=Number(first)){
		alert("请输入正确的阶段二任务量");
		$('.J_numberSecond').val("");
		$('.J_numberSecond').focus();
		return false;
	}
}
/**
 * 验证三阶段数量
 * @returns {Boolean}
 */
function chkThirdValue(){
	var first = $('.J_numberFirst').val();
	var third = $(".J_numberThird").val();
	var second = $('.J_numberSecond').val();
	if(first == "" ){
		alert("请输入阶段一任务量");
		return false;
	}
	if(second == "" ){
		alert("请输入阶段二任务量");
		return false;
	}
	
	if( third != "" && Number(third)<=Number(second)){
		alert("请输入正确的阶段三任务量");
		$('.J_numberThird').val("");
		$('.J_numberThird').focus();
		return false;
	}
}
//初始化选择类别（机型）
function initSelectMachineType() {
	$(".J_machineType").change(function(){
		var machineType=$(this).val();
		var option = $(this).parents('li');
		if(machineType == ""){
			createBrandType("", option);
			createGoods("", option);
			return false;
		}
		findBrandType(machineType, option);
		initSelectBrand();
	});
}
//查询品牌
function findBrandType(machineType, option){
	$.ajax({
		url:"/goods/getBrand?code="+machineType,
		type : 'GET',
		dateType : 'JSON',
		success : function(data) {
			createBrandType(data, option);
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
function createBrandType(data,option) {
	var myTemplate = Handlebars.compile($("#brands-template").html());
	$(option).find('.J_brand').html(myTemplate(data));
}
/**
 * 初始化选择品牌
 * 
 */
function initSelectBrand() {
	$(".J_brand").change(function(){
	    var brandId=$(".J_brand").val();
	    var option = $(this).parents('li');
	    if(brandId == ""){
	    	createGoods("", option);
	    	return false;
	    }
	    findGoods(brandId, option);
	});
}
/**
 * 查询商品
 * @param brandId
 */
function findGoods(brandId, option){
	$.ajax({
		url : base + 'goods/' + brandId,
		type : 'GET',
		dateType : 'JSON',
		success : function(data) {
			createGoods(data, option);
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
function createGoods(data, option) {
	var myTemplate = Handlebars.compile($("#goods-template").html());
	$(option).find('.J_goods').html(myTemplate(data));
}
/**
 * createRules()
 * @param data
 */
function createRules(data) {
	var myTemplate = Handlebars.compile($("#rules-template").html());
	$('#ruleList').html(myTemplate(data));
}
function createNumber(data) {
	var myTemplate = Handlebars.compile($("#numbers-template").html());
	$('#numberList').html(myTemplate(data));
}
/**
 * 创建父窗口中的人员分组
 * @param data
 */
function creategroupAll(){
	var data = disposeGroupNumbers();
	var myTemplate = Handlebars.compile($("#group-all-template").html());
	$('#groupAllList').html(myTemplate(data));
}

/**
 * 处理多个型号
 */
function disAwardGoods(){
	var awardGoods = new Array();
	var machineTypeIds = new Array();
	var brandIds = new Array();
	var goodIds = new Array();
	$('.J_machineType').each(function(index){
		var item = $(this).val(); 
		machineTypeIds[index] = item ;
	})
	$('.J_brand').each(function(index){
		var item = $(this).val(); 
		brandIds[index] = item ;
	})
	$('.J_goods').each(function(index){
		var item = $(this).val(); 
		goodIds[index] = item ;
	})
	for(var i=0;i<machineTypeIds.length;i++){
		awardGoods[i] = 
		{
			"machineTypeId" : machineTypeIds[i],
			"brandId" : brandIds[i],
			"goodId" : goodIds[i]
		}
	}
	console.info(awardGoods);
	return awardGoods;
}
function toSubmit() {
//	var jsonStr = $("#awardForm").serialize();
	var jsonStr = {
		"awardId" : $(".J_awardId").val(),
		"planId": $(".J_planId").val(),
		"numberFirst": $(".J_numberFirst_").val(),
        "numberSecond": $(".J_numberSecond_").val(),
        "numberThird": $(".J_numberThird_").val(),
        "startDate": $(".J_startDate").val(),
        "endDate": $(".J_endDate").val(),
        "issuingDate": $(".J_issuingDate").val(),
        "auditor":  $(".J_auditor").val(),
        "auditorName":  $(".J_auditor").find("option:selected").text(),
        "remark":  $(".J_remark").val()
	};
	
	jsonStr["awardGoods"] = disAwardGoods();
	//奖罚规则
	jsonStr["rewardPunishRules"] = rule;
	//分组
	jsonStr["groupNumbers"] = disposePostGroup();
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
	if(checkForm(jsonStr)){
//============需要转换成字符串的json格式传递参数==============================	
		jsonStr = JSON.stringify(jsonStr);
		console.info(jsonStr);
		$.ajax({
			url : "/award",
			type : "PUT",
			contentType : 'application/json;charset=utf-8', // 设置请求头信息
			dataType : "json",
			data : jsonStr,
			success : function(data) {
				alert(data.message);
				if(data.result == "success"){
					window.location.href = base + "award/list?planId="+$(".J_planId").val();
				}
			},
			error : function(res) {
				alert("网络异常，稍后重试！");
			}
		});

	}
}
function checkForm(searchDate){
	console.info(searchDate);
	var flag = true;

	$.each(searchDate,function(name,value){
		switch (name) {
		case 'awardGoods':
			if(value.length>0){
				$.each(value,function(i){
					if(isEmpty(value[i]['goodId'])){
						flag = false;
						alert("请选择型号！");
						return flag;
					}
				})
			}
			break;
		case 'rewardPunishRules':
			if(value.length>0){
				$.each(value,function(i){
					if(value[i]['money']<=0){
						flag = false;
						alert("提成金额不能为空！");
						return flag;
					}
				})
			}
			break;
		case 'groupNumbers':
			if(value.length>0){
				$.each(value,function(i){
					if(isEmpty(value[i]['numberFirstAdd'])){
						flag = false;
						alert("请填写分组阶段达量增量！");
						return flag;
					}
				})
			}
			break;
		case 'numberFirst':
			if(isEmpty(value)){
				alert("请添加任务量！");
				flag = false;
				return flag;
			}
			break;
		case 'startDate':
		case 'endDate':
		case 'issuingDate':
			if(isEmpty(value)){
				alert("请选择日期！");
				flag = false;
				return flag;

			}
			break;
		case 'auditor':
			if(isEmpty(value)){
				alert("请选择审核人员！");
				flag = false;
				return flag;

			}
			break;

		default:
			break;
		}
	});
	return flag;
}
//下一步
function nextGroup(){
	$(".J_groupUser_show").hide();
	$("#awardForm").show();
	addRule();
}
/**
 * 每组人数
 */
Handlebars.registerHelper('countUser', function(groupUsers) {
	return groupUsers.length;
});
/**
 * 增强 if-else使用 
 * 比较长度
 */
Handlebars.registerHelper('compareCount', function(groupUsers, long, options) {
	if(groupUsers.length > long){
		//满足添加继续执行
		return options.fn(this);
	}
	//不满足条件执行{{else}}部分
	return options.inverse(this);
});

//注册索引+1的helper
var handleHelper = Handlebars.registerHelper("addOne", function(index) {
	// 返回+1之后的结果
	return (index + 1) + " ";
});
function createNumberAdd(){
	var pratGroupNum = new Array();
	pratGroupNum = 
	{
		"nums" : [$(".J_numberFirst_").val()	,$(".J_numberSecond_").val() ,$(".J_numberThird_").val()],
		"groupNumbers" : groupNumbers
	}
	var myTemplate = Handlebars.compile($("#number-add-template").html());
	$('#numberAddList').html(myTemplate(pratGroupNum));
}
/**
 * 处理父窗口中使用的分组信息
 */
function disposePostGroup(){
	var newGroup = new Array();
	for(var i=0;i<groupNumbers.length;i++){
		var groupUsers = groupNumbers[i]["groupUsers"];
		if(groupUsers.length > 0){
			newGroup.push(groupNumbers[i]);
		}
		continue;
	}
	return newGroup;
}

function numberAdd(opt, num){
	var x = $(opt).siblings('a.J_group').attr('data-index');
	var y = "";
	switch (num) {
	case 0:
		y = "numberFirstAdd";
		break;
	case 1:
		y = "numberScendAdd";
		break;
	case 2:
		y = "numberFirstAdd";
		break;

	default:
		break;
	}
	groupNumbers[x][y] = opt.value;
	console.info(groupNumbers[x][y]);// = opt.value;
	console.info(groupNumbers[x]);// = opt.value;
}