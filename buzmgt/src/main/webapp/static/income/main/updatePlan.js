var salesArr = [];
// 添加业务员flag区分是否为临时用户
function addUser(salesId, salesName, flag, kid) {
	var salesman={
		'salesmanname' : salesName,
		'salesmanId' : salesId,
		"id" : kid
	};
	var index=salesArr.indexOf(salesman);
	if ( index> -1) {
		salesArr[index]=salesman;
		return;
	}
	salesArr.push(salesman);
	var dirHtml = '<div class="col-sm-2"  style="margin-bottom: 20px;margin-left: -20px"  id="saleDiv'
			+ salesArr.length
			+ '">'
			+ ' <div class="s-addperson ">'
			+ salesName
			+ '  <i class="icon-s icon-close" onclick="deletediv('
			+ salesArr.length
			+ ','
			+ flag
			+ ');" ></i>'
			+ ' </div>'
			+ ' </div>';
	$('.J_addDire').parents('.col-sm-2').before(dirHtml);

}
var globalUserId = "";
function deletediv(index, flag) {
	gloIndex = index;
	if ('' != salesArr[gloIndex-1].id&&undefined != salesArr[gloIndex-1].id) {
		if(otherPlanFlag){
			$('#otherPlan').modal('hide');
			otherPlanFlag=true;
		}
		$('#del').modal('show');
	} else {
		removeDiv();
	}
}
// 删除人员
function deleteUser() {
	var newDate = $("#newDate").val();
	if (newDate == '') {
		alert("必须选择日期!!");
		return;
	}
	salesArr[gloIndex - 1].fqtime = newDate;
	var user=JSON.stringify(salesArr[gloIndex - 1]);
	$.ajax({
		url : "mainPlanUsers/deleteUser",
		type : "post",
		data : user,
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		dataType : "json",
		success : function(orderData) {
			//alert("已删除!!");
			removeDiv();
			/*if(otherPlanFlag){
				$('#del').modal('hide');
				$('#otherPlan').modal('show');
			}*/
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});
	
}

function removeDiv() {
	salesArr[gloIndex -1] = "";
	$("#saleDiv" + gloIndex).empty();
	$("#saleDiv" + gloIndex).remove();
}

// 初始化使用人员列表
function initUsers() {
	$.ajax({
		url : "/mainPlan/getUserList/" + pId,
		type : "get",
		dataType : "json",

		success : function(orderData) {
			var data = orderData.data;
			for (var i = 0; i < data.length; i++) {
				addUser(data[i].salesId, data[i].name, true, data[i].id);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});
}


// 打开用户列表
function openUser() {
	$('#user').modal('show');
}
function commitUsers() {
	var planUsers = [];
	for (var i = 0; i < salesArr.length; i++) {
		if ('' != salesArr[i] && ('' == salesArr[i].id||undefined == salesArr[i].id)) {
			var salesman = {
				"salesmanId" : salesArr[i].salesmanId,
				"salesmanname" : salesArr[i].salesmanname
			}
			planUsers.push(salesman);
		}
	}
	$.ajax({
		url : "mainPlanUsers/addUsers/" + pId,
		type : "post",
		data : JSON.stringify(planUsers),
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		dataType : "json",
		success : function(orderData) {
			alert("新增" + planUsers.length + "个用户");
			initUsers();
			findPlanUserList();
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});
}