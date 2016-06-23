var salesArr = [];
// 添加一名业务员
function addSales() {
	var salesIds = $("#salesName").val();
	if (salesIds == null) {
		alert("请选择业务员");
		return;
	}
	var salesId = salesIds[0];
	var saleName = $("#salesName").find('option:selected').text();
	var indx = jQuery.inArray(salesId, salesArr);
	if (jQuery.inArray(salesId, salesArr) > -1) {
		alert("请勿重复添加业务员");
		return;
	}
	salesArr.push(salesId);
	$("#tjjsr").css("display", "none");
	$('#salesList').append(
			'<input type="text" id="saleInput' + salesArr.length
					+ '" class="form-control" value="' + saleName + '">');
	$('#salesList').append(
			'<span class="input-group-addon" id="saleSpan' + salesArr.length
					+ '"><i class="icon icon-close" onclick="deletediv('
					+ salesArr.length + ');"></i></span>');
	$('#tjjsr').modal('hide');
}
// 删除选中的业务员
function deletediv(index) {
	salesArr[index - 1] = "";
	$("#saleInput" + index).remove();
	$("#saleSpan" + index).remove();
}
// 发布消息
function issue() {
	var customTask = getCustomTask();
	if (!customTask) {
		return;
	}
	$.ajax({
		url : '/customTask/create',
		type : 'post',
		data : JSON.stringify(customTask),
		dataType : "json",
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		success : function(data) {
			alert("新建已完成");
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});
}
// 拼接一个customTask
function getCustomTask() {
	var title = $("#title").val();
	var content = $("#content").val();
	var type = $("#Customtype").val();
	var punish = $("#punish").val();
	if (type == 2 && punish <= 0) {
		alert("扣罚金额要大于0");
		return false;
	}
	var salesSet = [];
	for (var i = 0; i < salesArr.length; i++) {
		if (salesArr[i] != '') {
			salesSet.push({
				"id" : salesArr[i]
			});
		}
	}
	var customTask = {
		"type" : type,
		"title" : title,
		"content" : content,
		"punishCount" : punish,
		"salesmanSet" : salesSet
	};
	return customTask;
}