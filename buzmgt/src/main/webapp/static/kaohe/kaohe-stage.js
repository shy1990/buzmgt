var strtown;
$(function(){
	
	var rName = $('#regionlbl').text();
	$('#regiontxt').html(rName);
	
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
	            strtown+='<option value = "" selected="selected">请选择</option>';
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
	 <select class="form-control" name="assessArea" id="town">\
	  ' + strtown + '\
		</select>\
		</div>';
	$(this).parents('.col-sm-4').before(dirHtml);
	var options = document.getElementById("town").options.length;
	if ($("div[id^='selOrder']").length == options-1) $("#btn").hide();
});

function toSubmit(){
	form.submit();
}
function checkForm(){
	var activeNum = $("#assessActivenum").val()
	var orderNum = $("#assessOrdernum").val()
	var assessCycle = $("#assessCycle").val()
	var assessTime = $("#assessTime").val()
	var town = $("#town").val()
	
	if (activeNum == null || activeNum.trim() == "") {
		errorMsgShow($("#assessActivenum"));
		return false;
	}
	
	if (orderNum == null || orderNum.trim() == "") {
		errorMsgShow($("#assessOrdernum"));
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

function passed(salesmanId){
	$.ajax({
		type:"GET",
		url:"/assess/passed",
		data: {"salesmanId":salesmanId},
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		dataType:"text",
		success : function(obj){
			if(obj == "ok"){
				alert("该业务经理转正成功!");
				window.location.reload();
			}
	 },error : function() {
			alert("系统错误，请稍后再试");
	 }
	});
}
