$(function() {
		var value = document.getElementById("saojieMan").value;
   	     if(value == null || value == ""){
   	     	$.ajax({
			type:"post",
			url:"/saojie/gainSaojieMan",
			//data:formValue,
			dataType:"JSON",
			success : function(obj){
				if (obj) {
					var saojieMan = $("#saojieMan");
					saojieMan.empty();
					saojieMan.append("<option value='' selected='selected'>待扫街人员</option>");
                    for(var i=0;i<obj.length;i++){
                    	saojieMan.append("<option value = '"+obj[i].id+"'>"+obj[i].truename+"</option>");
					}
				}else {
//					alert(obj.length);
//					for(var i=0;i<obj.length;i++){
//						alert(obj[i].name);
//					}
				};
		 }
		});
   	     }
});

var strtown;
function queryTown(){
	var intLen = $("div[id^='selOrder']").length;
	if (intLen > 0 ){
		for(var l=1;l<=intLen;l++){
			var selOrder=$("div[id='selOrder"+ l +"']");
			var order=selOrder[0];
			order.parentNode.removeChild(order);
		}
	}
	var userId = document.getElementById("saojieMan").value;
	$.ajax({
	type:"post",
	url:"/saojie/gainSaojieTown",
	data: {"id":userId},
	dataType:"JSON",
	success : function(obj){
	   if (obj) {
//			var town = $("#town");
//			alert(town);
//            town.empty();
		   strtown ='';
            strtown+='<option value = "" selected="selected">请选择</option>';
        for(var i=0;i<obj.length;i++){
        	strtown+="<option value = '"+obj[i].id+"'>"+obj[i].name+"</option>";
		}
	}else {
			//alert(obj.length);
			//for(var i=0;i<obj.length;i++){
			//	alert(obj[i].name);
			//}
			
		};
 }
});
			
}

function AddOrder(btType) {
	var userId = document.getElementById("saojieMan").value;
	if (!userId)
	{
	    alert("请先选择待扫街人员!");
	    return;
	}
	var intLen = $("div[id^='selOrder']").length;
	if (intLen == "undefined") intLen = 1;
	if (intLen <= 20) {
		intLen++;
		var intNewApp;
		if (intLen != 1) {
			var intAppId = $("div[id^='selOrder']:last").attr("id");
			var intAppNum = intAppId.substring(8);
			intNewApp = parseInt(intAppNum) + 1;
		} else {
			intNewApp = 1;
		}
		var order;
		if (intLen < 10) {
			order = "0" + intLen;
		} else {
			order = intLen;
		}
		//					var arrName;
		//					if (1 == 1) {
		//						arrName = ["序号", "地区", "指标"];
		//						alert(arrName);
		//					}
		var strApp = '<div class="col-sm-6 col-xs-4 p-n" id="selOrder' + intLen + '">\
			  <div class="input-group col-sm-8 col-xs-4 ">\
		<span class="input-group-btn" id="basic-addon1"><i class="order-icon saojie-number-icon"><input type="hidden" name="num" value="' + intLen + '"/>' + order + '</i></span>\
				  <select class="form-control" name="region.id" id="town">\
				  ' + strtown + '\
					</select>\
				</div>\
				<div class="col-sm-4 clear-padd-l">\
					<div class="input-group clear-padd-l">\
						<span class="input-group-addon" id="basic-addon1"><i class="member-icon member-value-icon"></i></span>\
						<input type="text" name="value" class="form-control" placeholder="指标(家)" id="minValue' + intNewApp + '">\
					</div>\
					<span class="del-order glyphicon glyphicon-remove" onclick="delNode(selOrder' + intLen + ',' + order + ')"></span>\
				</div>\
			</div>';
		$(btType).before(strApp);
	}
	if ($("div[id^='selOrder']").length == 20) $("#btn").hide();
}

function delNode(selOrder,order) {
	var intLen = $("div[id^='selOrder']").length;
	if(intLen === order){
		if (selOrder != null && selOrder != ''){
			selOrder.parentNode.removeChild(selOrder);
		}
	}else{
		alert("请先删除序号最大项!");
	}
}

$(function() {
				$('#starttime').datetimepicker({
					language: 'zh-CN',
					showMeridian: true,
					weekStart: 1,
					todayBtn: 1,
					autoclose: 1,
					todayHighlight: 1,
					startView: 2,
					minView: 2,
					forceParse: 0,
					pickerPosition: "bottom-left"
				}).on('changeDate', function(ev) {
					var starttime = $('#starttime').val();
					$('#endtime').datetimepicker('setStartDate', starttime);
					$('#starttime').datetimepicker('hide');
				});
				$("#endtime").datetimepicker({
					language: 'zh-CN',
					showMeridian: true,
					weekStart: 1,
					todayBtn: 1,
					autoclose: 1,
					todayHighlight: 1,
					startView: 2,
					minView: 2,
					forceParse: 0,
					pickerPosition: "bottom-left"
				}).on('changeDate', function(ev) {
					var starttime = $('#starttime').val();
					var endtime = $('#endtime').val();
					if (starttime != "" && endtime != "") {
						if (!checkEndTime(starttime, endtime)) {
							$('#endtime').val('');
							alert("开始时间大于结束时间!");
							return;
						}
					}
					$('#starttime').datetimepicker('setEndDate', endtime);
					$('#starttime').datetimepicker('hide');
				});
				$('#starttime').datetimepicker('setEndDate', getCurentTime());
				$('#endtime').datetimepicker('setStartDate', getCurentTime());
				$('#starttime').val(getCurentTime());
				$('#endtime').val(getCurentTime());
			});

			function getCurentTime() {
				var date = new Date();
				var seperator1 = "-";
				var month = date.getMonth() + 1;
				var strDate = date.getDate();
				if (month >= 1 && month <= 9) {
					month = "0" + month;
				}
				if (strDate >= 0 && strDate <= 9) {
					strDate = "0" + strDate;
				}
				var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
				return currentdate;
			}
function toSubmit(){
	form.submit();
}

/*扫街列表*/
function getAllSaojieList(){
	
	window.location.href="/saojie/saojieList";
}

function getSaojieList(param,name){
    if(name == "goSearch"){
    	var value = $("#param").val();
    	window.location.href="/saojie/getSaojieList?truename="+value+"&jobNum="+value
    }else if(name == "status"){
    	window.location.href="/saojie/getSaojieList?Status="+param
    }
}
			