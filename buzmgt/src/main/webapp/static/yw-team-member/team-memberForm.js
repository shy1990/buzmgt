function checkUsername(){
	
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
               alert("用户名已存在！");              
			}else {
				alert("数据加载异常！");
			};
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
	
	if (jobNum == null || jobNum.trim() == "") {
		return false;
	}
	
	if (username == null || username.trim() == "") {
		return false;
	}
	
	if (truename == null || truename.trim() == "") {
		return false;
	}
	
	if (mobile == null || mobile.trim() == "") {
		return false;
	}
	
	if (organizationId == null || organizationId.trim() == "") {
		return false;
	}
	
	
	if (regionId == null || regionId.trim() == "") {
		return false;
	}
	
	
	if (roleId == null || roleId.trim() == "") {
		return false;
	}
	
	return true;
	
}
function checkName(){
	var trueName = $("#trueName").val()
	if (trueName == null || trim(trueName) == "") {
		return false;
	}
	if(trueName.length<5 || trueName.length>20){
		alert("会员名的长度不正确,正确的长度为5-20位字符");
		document.redForm.truename.focus();
		return false;
	}
}



function checkJobNum(){
	
	var jobNum = $("#jobNum").val()
	if (jobNum == null || trim(jobNum) == "") {
		return false;
	}
	
}

function checkMobile(){
	var mobile = $("#mobile").val()
	if (mobile == null || trim(mobile) == "") {
		return false;
	}
}


