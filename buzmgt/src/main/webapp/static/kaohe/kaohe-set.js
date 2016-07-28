var strtown;
$(function(){
	var salId = $("#salId").attr("data-aa");
	$.ajax({
		type:"post",
		url:"/assess/gainAuditTown",
		data: {"id":salId},
		dataType:"JSON",
		success : function(obj){
		   if (obj) {
			   var regionName;
			   strtown ='';
	            strtown+='<option value = "" selected="selected">自定义区域</option>';
		        for(var i=0;i<obj.length;i++){
		        	strtown+="<option value = '"+obj[i].id+"'>"+obj[i].name+"</option>";
				}
		}
	 }
	});
});
var orderNum = 0;
$('.J_addDire').click(function() {
	var intLen = $("div[id^='selOrder']").length;
	if (intLen == "undefined"){
		intLen = 1;
	}
	if (intLen <= 30) {
		intLen++;
		orderNum++;
	}
	var dirHtml = '<div class="col-sm-4" id="selOrder' + orderNum + '">\
	 <select class="form-control" name="defineArea"  id="town">\
	  ' + strtown + '\
		</select>\
	</div>';
	$(this).parents('.col-sm-4').before(dirHtml);
	var options = document.getElementById("town").options.length;
	if ($("div[id^='selOrder']").length == options-1) $("#btn").hide();
});

function toSubmit(){
//	$("#assesszh").val($("#assessAreaId").find("option:selected").text());
	form.submit();
}

function checkForm(){
	var activeNum = $("#activeNum").val();
	var orderNum = $("#orderNum").val();
	var assessCycle = $("#assessCycle").val();
	var assessTime = $("#assessTime").val();
	var town = $("#town").val();
	var reg = /^(\d{10})$/;
	if (activeNum == null || activeNum.trim() == "") {
		errorMsgShow($("#activeNum"));
		return false;
	}

	if(reg.test(activeNum) || activeNum < 0){
		alert("数量必须为大于0的数字!");
		return false;
	}
	
	if (orderNum == null || orderNum.trim() == "") {
		errorMsgShow($("#orderNum"));
		return false;
	}

	if(reg.test(orderNum) || orderNum < 0){
		alert("数量必须为大于0的数字!");
		return false;
	}
	
	if (assessCycle == null || assessCycle.trim() == "") {
		errorMsgShow($("#assessCycle"));
		return false;
	}

	if (assessTime == null || assessTime.trim() == "") {
		errorMsgShow($("#assessTime"));
		return false;
	}

	if(typeof(town) == "undefined"){
		alert("请先选择考核区域!");
		return false;
	}
	
	if (town == null || town.trim() == "") {
		errorMsgShow($("#town"));
		return false;
	}
	
	return true;
	
}

function errorMsgShow($option,msg){
	if($option==null||$option==""){
		$option=$(this);
	}
	$option.parents('.form-group').addClass('has-error');
	$option.parents('.col-sm-4').addClass('has-error');
	if(msg!=null&&msg!=""){
		$option.parents('.form-group').find('.msg-error').html(msg);
		$option.parents('.average-tr').find('.msg-error').html(msg);
	}
	
}
 
