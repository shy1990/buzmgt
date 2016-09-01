var salesArr=[];
//添加业务员
function addUser(salesId,id, salesName) {
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
			+ salesArr.length + ','+id+');" ></i>' + ' </div>' + ' </div>';
	$('.J_addDire').parents('.col-sm-2').before(dirHtml);

}
var globalUserId="";
function deletediv(index,id){
	gloIndex= index;
	globalUserId=id;
	$('#del').modal('show');
}
//删除人员
function deleteUser(){
	var newDate=$("#newDate").val();
	if(newDate==''){
		alert("必须选择日期!!");
		return;
	}
	alert("已删除!!");
	salesArr[gloIndex - 1] = "";
	$("#saleDiv" + gloIndex).empty();
	$("#saleDiv" + gloIndex).remove();
}
//初始化使用人员列表
function initUsers(){
	$.ajax({
		url : "/mainPlan/getUserList/"+pId,
		type : "get",
		dataType : "json",
		success : function(orderData) {
			var data=orderData.data;
			for(var i=0;i<data.length;i++){
				addUser(data[i].salesId,data[i].id,data[i].name);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});
}

//初始化时间框
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
//打开任务列表
function openUser(){
	$('#user').modal('show');
}