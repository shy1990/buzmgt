var salesArr = [];

/*
 * 类别与品牌的联动
 */
function changeBranch() {
	var machineType = $("#machineType").val();
	var options = document.getElementById("allBrand").options;
	options.length = 0;
	options.add(new Option("-品牌-", ""));
	for (var i = 0; i < branchs.length; i++) {
		if (branchs[i].machineType == machineType) {
			options.add(new Option(branchs[i].name, branchs[i].name));
		}
	}
}
// 初始化时间框
function initDateInput() {
	$(".form_datetime").datetimepicker({
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
	});
}
// 添加业务员
function addUser(salesId, salesName) {
	if (jQuery.inArray(salesId, salesArr) > -1) {
		alert("请勿重复添加业务员");
		return;
	}
	salesArr.push(salesId);
	var dirHtml = '<div class="col-sm-2"  style="margin-bottom: 20px;margin-left: -20px"  id="saleDiv'
			+ salesArr.length
			+ '">'
			+ ' <div class="s-addperson ">'
			+ salesName
			+ '  <i class="icon-s icon-close" onclick="deletediv('
			+ salesArr.length + ');" ></i>' + ' </div>' + ' </div>';
	$('.J_addDire').parents('.col-sm-2').before(dirHtml);

}

// 删除选中的业务员
function deletediv(index) {
	salesArr[index - 1] = "";
	$("#saleDiv" + index).empty();
	$("#saleDiv" + index).remove();
}

// 新增一个收益主计划
function newPlan() {
	var newplan = getPlan();
	if (newPlan == null) {
		return;
	}
	// 开始提交
	$.ajax({
		url : "/mainPlan/newPlan",
		type : "post",
		data : JSON.stringify(newplan),
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		dataType : "json",
		success : function(orderData) {
			alert("新增成功!!");
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});
}

// 获取页面元素
function getPlan() {
	var region = document.getElementById("region").value;
	if (region == "") {
		alert("大区不能为空!!");
		return;
	}
	var regionName = $("#region").find("option:selected").text();
	var organization = document.getElementById("organization").value;
	if (organization == "") {
		alert("组织机构不能为空!!");
		return;
	}
	var newDate = document.getElementById("newDate").value;
	if (newDate == "") {
		alert("时间不能为空!!");
		return;
	}
	var machineType = document.getElementById("machineType").value;
	if (machineType == "") {
		alert("类别不能为空!!");
		return;
	}
	machineType = $("#machineType").find("option:selected").text();
	var allBrand = document.getElementById("allBrand").value;
	if (allBrand == "") {
		alert("品牌不能为空!!");
		return;
	}
	var subtitle = document.getElementById("subtitle").value;
	if (subtitle == "") {
		alert("副标题不能为空!!");
		return;
	}
	organization = organization.substring(0, 2);
	var salesSet = [];
	if (salesArr.length < 1) {
		alert("业务员数量不能为空!!");
		return;
	}
	for (var i = 0; i < salesArr.length; i++) {
		if (salesArr[i] != '') {
			salesSet.push({
				"salesmanId" : salesArr[i]
			});
		}
	}
	if (salesSet.length < 1) {
		alert("业务员数量不能为空!!");
		return;
	}

	var newplan = {
		"regionId" : region,
		"regionname" : regionName,
		"maintitle" : organization + newDate + machineType + allBrand,
		"users" : null,
		"subtitle" : subtitle,
		"users" : salesSet
	};
	return newplan;
}