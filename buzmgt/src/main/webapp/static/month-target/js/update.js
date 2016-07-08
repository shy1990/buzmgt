/*月指标保存和修改*/
var regionId;
$(function() {
	getRegionName();
});

function getRegionName() {
	regionId = $("#regionId  option:selected").val();
	$("#region").val(regionId);
	$.ajax({
		url : base + "monthTarget/regionName",
		type : "GET",
		data : {
			"regionId" : regionId
		},
		dataType : "json",
		success : function(salesman) {
			if (salesman != null) {
				$("#regionName").text(
						salesman.region.parent.parent.name + " "
								+ salesman.region.parent.name + " "
								+ salesman.region.name);
			} else {
				$("#regionName").text("无");
			}
		}
	});
}

function goSearch(){
	$.ajax({
		url : base + "monthTarget/orderNum",
		data : {
			"regionId" : regionId
		},
		type : "GET",
		dataType : "json",
		success : function(map) {
			var name = " 台";
			$("#orderAvg").text(map.one + name);
			$("#orderLast").text(map.three + name);
			var ad = (map.one);
			$("#adviseOrder").text(ad + name);
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});

	$.ajax({
		url : base + "monthTarget/seller",
		data : {
			"regionId" : regionId
		},
		type : "GET",
		dataType : "json",
		success : function(map) {
			var name = " 家";
			$("#merAvg").text(map.merone + name);
			$("#merLast").text(map.merthree + name);
			var ad = (map.merone + map.merthree) / 2;
			$("#merAd").text(ad + name);

			$("#acAvg").text(map.activeone + name);
			$("#acLast").text(map.activethree + name);
			ad = (map.activeone + map.activethree) / 2;
			$("#acAd").text(ad + name);

			$("#maAvg").text(map.matureone + name);
			$("#maLast").text(map.maturethree + name);
			ad = (map.matureone + map.maturethree) / 2;
			$("#maAd").text(ad + name);
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});
}

function toSubmit(id,flag) {
	var orderNum = $("input[name=orderNum]").val();
	var merchantNum = $("input[name=merchantNum]").val();
	var activeNum = $("input[name=activeNum]").val();
	var matureNum = $("input[name=matureNum]").val();
	if(checkForm(orderNum,merchantNum,activeNum,matureNum)){ //校验表单
		var param = {
			orderNum : orderNum,
			merchantNum : merchantNum,
			activeNum : activeNum,
			matureNum : matureNum
		};
		if (flag != 'update'){
			$.ajax({
				url : base + "monthTarget/save/"+$("#region").val(),
				type : "post",
				data : JSON.stringify(param),
				dataType : "text",
				beforeSend:function(req){
					req.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
				},
				success : function(msg) {
					// 提交成功后处理
					if (msg == "ok") {
						alert("保存成功!");
						window.location.href = base + "monthTarget/monthSetting";
					} else if (msg == "exists") {
						alert("指标已存在!");
						$("input[type='text']").each(function(){
							$(this).val("");
						});
					}
				},
				error : function() {
					alert("系统错误，请稍后再试");
				}
			});
		}else {
			$.ajax({
				url : base + "monthTarget/update/"+id,
				type : "post",
				data : JSON.stringify(param),
				dataType : "text",
				beforeSend:function(req){
					req.setRequestHeader("Content-Type", "application/json; charset=UTF-8");
				},
				success : function(msg) {
					// 提交成功后处理
					if (msg == "ok") {
						alert("修改成功!");
						window.location.href = base + "monthTarget/monthSetting";
					}
				},
				error : function() {
					alert("系统错误，请稍后再试");
				}
			});
		}
	}
}

function checkForm(orderNum,merchantNum,activeNum,matureNum){

	if (orderNum == null || orderNum.trim() == "") {
		errorMsgShow($("input[name=orderNum]"),"请输入提货量");
		return false;
	}

	if (merchantNum == null || merchantNum.trim() == "") {
		errorMsgShow($("input[name=merchantNum]"),"请输入提货商家");
		return false;
	}

	if (activeNum == null || activeNum.trim() == "") {
		errorMsgShow($("input[name=activeNum]"),"请输入活跃商家");
		return false;
	}

	if (matureNum == null || matureNum.trim() == "") {
		errorMsgShow($("input[name=matureNum]"),"请输入成熟商家");
		return false;
	}

	return true;
}

function errorMsgShow($option,msg) {
	if ($option == null || $option == "") {
		$option = $(this);
	}
	$option.parents('.table-task-list').addClass('has-error');
	console.info(msg);
	if (msg != null && msg != "") {
		$option.parents('.table-task-list').find('.msg-error').html(msg);
	}
}