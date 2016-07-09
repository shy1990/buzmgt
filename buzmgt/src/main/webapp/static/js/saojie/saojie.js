$(function() {
		var value = document.getElementById("saojieMan").value;
   	     if(value == null || value == ""){
   	     	$.ajax({
			type:"post",
			url:"/saojie/gainSaojieMan",
			//data:formValue,
			success : function(obj){
				if (obj) {
					var saojieMan = $("#saojieMan");
					saojieMan.empty();
					saojieMan.append("<option value='' selected='selected'>待扫街人员</option>");
                    for(var i=0;i<obj.length;i++){
                    	saojieMan.append("<option value = '"+obj[i][0]+"'>"+obj[i][1]+"</option>");
					}
				}else {
					
				};
		 }
		});
   	     }
});

var strtown;
function queryTown(){
	var intLen = $("div[id^='selOrder']").length;
	var len = $("#btType");
	len.prevAll().remove();
	$("#btn").show();
	var userId = $("#saojieMan  option:selected").val();
	var map = new BMap.Map("allmap");    // 创建Map实例
	$.ajax({
		   type:"post",
		   url:"/saojie/getOrderNum",
		   data: {"id":userId},
		   success : function(obj){
			  orderNum=obj;
		   }
	   });	
	$.ajax({
	type:"post",
	url:"/saojie/gainSaojieTown",
	data: {"id":userId},
	dataType:"JSON",
	success : function(obj){
	   if (obj) {
		   map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
		   map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
		   var regionName;
		   strtown ='';
            strtown+='<option value = "" selected="selected">请选择</option>';
	        for(var i=0;i<obj.length;i++){
	        	strtown+="<option value = '"+obj[i].id+"'>"+obj[i].name+"</option>";
	        	if(null!=obj[i].coordinates){
	        		 var points=new Array();
	        		for(var j=0;j<obj[i].coordinates.split("=").length;j++){
	        			var lng=obj[i].coordinates.split("=")[j].split("-")[0];
	        			var lat=obj[i].coordinates.split("=")[j].split("-")[1];
	        			points[j]=new BMap.Point(lng,lat);
	        		}
	        		
	        		var polygon = new BMap.Polygon({strokeColor:"blue", strokeWeight:2,fillColor: "red", strokeOpacity:0.5});  //创建多边形
	        		polygon.setStrokeColor("blue");
	        		polygon.setPath(points);
	        		polygon.setStrokeWeight(2);
	        		polygon.setFillColor("red");
	        		polygon.setStrokeOpacity(0.5);
	        		map.addOverlay(polygon);   //增加多边形
	        		
	        		
	        		var name=obj[i].name;
	        		var centerPoint=obj[i].centerPoint;
	        		
	        		if(null!=centerPoint){
						var lng = centerPoint.split("-")[0];//经度 
						var lat = centerPoint.split("-")[1];//纬度%>
						var secRingCenter = new BMap.Point(lng,lat);
						var secRingLabel2 = new BMap.Label(name,{offset: new BMap.Size(10,-30), position: secRingCenter});
						secRingLabel2.setStyle({"line-height": "20px", "text-align": "center", "width": "80px", "height": "29px", "border": "none", "padding": "2px","background": "url(http://jixingjx.com/mapapi/ac.gif) no-repeat",});
						map.addOverlay(secRingLabel2);
	        		}
	        	}
			}
	}
 }
});
	
	$.ajax({
		   type:"post",
		   url:"/saojie/getRegionName",
		   data: {"id":userId},
		   success : function(obj){
			  if(null!=obj.split("|")[1]&&"null"!=obj.split("|")[1]){
				  	//var[] listCoordinates=obj.split("|")[1].split("=");
				  	var jlng=obj.split("|")[1].split("=")[0].split("-")[0];
					var jlat=obj.split("|")[1].split("=")[0].split("-")[1];
					map.centerAndZoom(new BMap.Point(jlng,jlat), 13);
			  }else{
				  map.centerAndZoom(obj.split("|")[0], 13);  // 初始化地图,设置中心点坐标和地图级别
			  }
			   
			   map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
			   map.setCurrentCity(obj);          // 设置地图显示的城市 此项是必须设置的
			   map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
		   }
	   });	
}
var orderNum;
var intNewApp;
function AddOrder(btType) {
	var userId = document.getElementById("saojieMan").value;
	if (!userId)
	{
	    alert("请先选择待扫街人员!");
	    return;
	}
	var intLen = $("div[id^='selOrder']").length;
	if (intLen == "undefined") intLen = 1;
	if (intLen <= 30) {
		intLen++;
		orderNum++;
		if (intLen != 1) {
			var intAppId = $("div[id^='selOrder']:last").attr("id");
			var intAppNum = intAppId.substring(8);
			intNewApp = parseInt(intAppNum) + 1;
		} else {
			intNewApp = 1;
		}
		var order="";
		if (orderNum < 10) {
			order = "0" + orderNum;
		} else {
			order = orderNum;
		}
		var strApp = '<div class="col-sm-6 p-n" id="selOrder' + orderNum + '">\
			  	<div class="input-group col-sm-7 ">\
				  <span class="input-group-btn" id="basic-addon1"><span class="order-icon saojie-number-icon"><input type="hidden" name="num" value="' + orderNum + '"/>' + order + '</span></span>\
				  <select class="form-control" name="region.id" id="town">\
				  ' + strtown + '\
					</select>\
				</div>\
				<div class="col-sm-5 clear-padd-l">\
					<div class="input-group clear-padd-l">\
						<span class="input-group-addon" id="basic-addon1">指标</span>\
						<input type="text" name="value" class="form-control" placeholder="家" id="minValue' + intNewApp + '">\
					</div>\
					<span id="delNode' + orderNum + '" class="del-order glyphicon glyphicon-remove" onclick="delNode(selOrder' + orderNum + ',' + intLen + ')"></span>\
				</div>\
			</div>';
		$(btType).before(strApp);
	}
	var options = document.getElementById("town").options.length;
	if ($("div[id^='selOrder']").length == options-1) $("#btn").hide();
}

function delNode(selOrder,order) {
	var intLen = $("div[id^='selOrder']").length;
	var options = document.getElementById("town").options.length;
	if(intLen === order){
		if (selOrder != null && selOrder != ''){
			selOrder.parentNode.removeChild(selOrder);
			orderNum--;
		}
	}else{
		alert("请先删除序号最大项!");
	}
	if(intLen <= options-1){
		 			$("#btn").show();
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
					$('#starttime').datetimepicker('setEndDate', endtime);
					$('#starttime').datetimepicker('hide');
				});
				$('#starttime').datetimepicker('setStartDate', getCurentTime());
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

function checkForm(){
	var saojieMan = $("#saojieMan").val()
	var town = $("#town").val()
	var begin = $("#begin").val()
	var end = $("#end").val()
	var minValue = $("#minValue" + intNewApp + "").val()
	
	if (saojieMan == null || saojieMan.trim() == "") {
		errorMsgShow($("#saojieMan"));
		return false;
	}
	if(typeof(town) == "undefined"){
		alert("请先选择扫街区域!");
		return false;
	}
	if (town == null || town.trim() == "") {
		$("#msgOrder").text("区域不能为空");
		errorMsgShow($("#town"));
		return false;
	}
	if (minValue == null || minValue.trim() == "") {
		$("#msgOrder").text("指标不能为空");
		errorMsgShow($("#minValue" + intNewApp + ""));
		return false;
	}
	if (begin == null || begin.trim() == "") {
		errorMsgShow($("#begin"));
		return false;
	}
	if (end == null || end.trim() == "") {
		errorMsgShow($("#end"));
		return false;
	}
	// 判断日期是否正确
	if (stringToDate(end).valueOf()
			- stringToDate(begin).valueOf() < 0) {
		alert("开始时间不得大于结束时间!");
		return false;
	}
	return true;
}

function errorMsgShow($option,msg){
	if($option==null||$option==""){
		$option=$(this);
	}
	$option.parents('.form-group').addClass('has-error');
	if(msg!=null&&msg!=""){
		$option.parents('.form-group').find('.msg-error').html(msg);
	}
}

/*enter键*/
function check() {
	var bt = document.getElementById("goSearch");
	var event = window.event || arguments.callee.caller.arguments[0];
    if (event.keyCode == 13)
    {
        bt.click();
    }
}

/*扫街列表*/
function getAllSaojieList(regionId){
	
	window.location.href="/saojie/getSaojieList?regionid="+regionId;
}

function getSaojieList(param,name,regionId){
    if(name == "goSearch"){
    	var value = $("#param").val();
    	window.location.href="/saojie/getSaojieList?salesman.truename="+value+"&salesman.jobNum="+value+"&regionid="+regionId;
    }else if(name == "status"){
    	window.location.href="/saojie/getSaojieList?saojieStatus="+param+"&regionid="+regionId;
    }
}

function getPageList(num,regionId,name,job,statu){
	
	window.location.href="/saojie/getSaojieList?page="+num+"&size=20&regionid="+regionId+"&salesman.jobNum="+job+"&salesman.truename="+name+"&saojieStatus="+statu;
}

