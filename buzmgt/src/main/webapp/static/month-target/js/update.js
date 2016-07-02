/*月指标保存和修改*/
var userId;
$(function() {
	getRegionName();
	goSearch();
});

function getRegionName() {
	userId = $("#regionId  option:selected").val();
	$("#userId").val(userId);
	$.ajax({
		url : base + "monthTarget/regionName",
		type : "GET",
		data : {
			"userId" : userId
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
			"userId" : userId
		},
		type : "GET",
		dataType : "json",
		success : function(map) {
			var name = "台";
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
			"userId" : userId
		},
		type : "GET",
		dataType : "json",
		success : function(map) {
			var name = "家";
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

function toSubmit() {
	var param = {
			orderNum:$("input[name=orderNum]").val(),
			merchantNum:$("input[name=merchantNum]").val(),
			activeNum:$("input[name=activeNum]").val(),
			matureNum:$("input[name=activeNum]").val()
	};
	
	$.ajax({
		url : base + "monthTarget/save/"+$("#userId").val(),
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
}
