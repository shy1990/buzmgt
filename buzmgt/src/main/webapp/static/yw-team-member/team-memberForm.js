function checkUsername(){
	var $userName = $("#username");
	var userName = $("#username").val()
	if (userName == null || userName.trim() == "") {
		
		return false;
	}
	if(userName.length<5 || userName.length>20){
		alert("会员名的长度不正确,正确的长度为5-20位字符");
		document.getElementById("username").focus();
		return false;
	}
	$.ajax({
		type:"post",
		url:"/user/checkUsername",
		//data:formValue,
		dataType:"JSON",
		data :{
			"username" : userName
		},
		success : function(result){
		   if (result) {
			   errorMsgShow($userName,"用户名已存在！");              
			}
		}
	});
	
}

function checkForm(){
	var organizationId = $("#organization").val()
	var regionId = $("#region").val()
	var roleId = $("#role").val()
	var username = $("#username").val()
	var truename = $("#truename").val()
	var mobile = $("#mobile").val()
	var jobNum = $("#jobNum").val()
	
	
	
	if (username == null || username.trim() == "") {
		errorMsgShow($("#username"));
		return false;
	}
	
	if (truename == null || truename.trim() == "") {
		errorMsgShow($("#truename"),"请填写5-20位字符的会员名。");
		return false;
	}
	
	if (organizationId == null || organizationId.trim() == "") {
		errorMsgShow($("#organization"));
		return false;
	}

	
	if (roleId == null || roleId.trim() == "") {
		errorMsgShow($("#role"));
		return false;
	}
	
	if (jobNum == null || jobNum.trim() == "") {
		errorMsgShow($("#jobNum"));
		return false;
	}
	
	if (mobile == null || mobile.trim() == "") {
		errorMsgShow($("#mobile"));
		return false;
	}
	if (regionId == null || regionId.trim() == "") {
		errorMsgShow($("#region"));
		return false;
	}
	
	return true;
	
}
function checkName(){
	var trueName = $("#trueName").val()
	if (trueName == null || trim(trueName) == "") {
		errorMsgShow();
		return false;
	}
	if(trueName.length<5 || trueName.length>20){
//		alert("会员名的长度不正确,正确的长度为5-20位字符");
		document.redForm.truename.focus();
		return false;
	}
}



function checkJobNum(){
	var jobNum = $("#jobNum").val();
	if (jobNum == null || trim(jobNum) == "") {
		return false;
	}
	
}

function checkMobile(){
	var mobile = $("#mobile").val();
	if (mobile == null || trim(mobile) == "") {
		return false;
	}
}

function errorMsgShow($option,msg){
	if($option==null||$option==""){
		$option=$(this);
	}
	$option.parents('.form-group').addClass('has-error');
	console.info(msg);
	if(msg!=null&&msg!=""){
		$option.parents('.form-group').find('.msg-error').html(msg);
	}
	
}

