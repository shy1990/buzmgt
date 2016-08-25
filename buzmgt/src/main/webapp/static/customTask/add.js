var salesArr = [];
// 添加一名业务员
function addSales() {
	var salesIds = [];	
	var options = $("#salesName").find('option:selected');
	if (options == null||options.length<1) {
		alert("请选择业务员");
		return;
	}
	var salesName=[];
	var html ='';
	for(var i=0;i<options.length;i++){
		var salesId=options[i].value;
		if (jQuery.inArray(salesId, salesArr) > -1) {
//			alert("请勿重复添加业务员");
			continue;
		}
		salesArr.push(options[i].value);
		html+= '<div class="col-sm-2"  id="saleDiv'
			+ salesArr.length
			+ '">'
			+ '<div class="input-group col-sm-2 input-peo">'
			+ '  <input class="form-control" name="" placeholder="'
			+ options[i].text
			+ '">'
			+ '  <span class="input-group-addon" id="basic-addon2" onclick="deletediv('
			+ salesArr.length + ');" >删除</span>' + '</div>' + '</div>';
	}
		
	$("#acont").append(html);

	$('#tjjsr').modal('hide');
}
// 删除选中的业务员
function deletediv(index) {
	salesArr[index - 1] = "";
	$("#saleDiv" + index).empty();
	$("#saleDiv" + index).remove();
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
			alert("事件已发布!!");
			location.href="/customTask/list";
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
	if(title==""||content==""){
		alert("事件的标题或消息内容不能为空");
		return false;
	}
	if(salesArr.length<1){
		alert("业务员数量不能为空!!");
		return false;
	}
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
//类型变更事件
function changePunish(type){
	if(type==2){
		$("#punishDiv").css("display","");
		$("#punishhr").css("display","");
	}else{
		$("#punishDiv").css("display","none");
		$("#punishhr").css("display","none");
	}
}
function testPunish(punish){
	if(punish.value<0){
		alert("扣罚金额要大于0!!");
		punish.value=0;
	}
}