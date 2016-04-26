/*任务地图*/

var total;
/*拜访任务列表*/
$(function() {
	var regionid = $('#area').attr("data-a");
	
	// 根据regionId获取坐标
	$.ajax({
		url : base + "task/gainPoint",
		type : "post",
		data : {regionid:regionid},
		dataType : "json",
		success : function(json) {
			$('#regionid').val(json.pcoordinates);
		},
	});
	
	getRegion(regionid); // 触发地域搜索
	
	$(".form_datetime").datetimepicker({
		format: "yyyy-mm-dd",
		language: 'zh-CN',
		weekStart: 1,
		todayBtn: 1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		pickerPosition: "bottom-left",
		forceParse: 0
	});
	$('.form_datetime').datetimepicker('setStartDate', getCurentTime(0));
	$('.form_datetime').datetimepicker('hide');
	
	function getCurentTime(AddDayCount) {
		var date = new Date();
		date.setDate(date.getDate()+AddDayCount);//获取AddDayCount天后的日期
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
});

/**
 * 根据地区进行检索
 * @param area
 */
function getRegion(regionid) { // 地区
	searchData['regionid'] = regionid; // 添加数据
	searchData['status'] = "1";
	searchData['condition'] = "2";
	ajaxSearch(searchData);
}

var map = new BMap.Map("allmap");
var opts = {
		width : 250,     // 信息窗口宽度
		height: 80,     // 信息窗口高度
		enableMessage:true//设置允许信息窗发送短息
	   };

function ajaxSearch(searchData) {
	//百度地图API功能
	var point = new BMap.Point(117.109808,36.667004);
	map.centerAndZoom(point, 8);
	map.enableScrollWheelZoom();
	map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
	map.clearOverlays();    //清除地图上所有覆盖物
	var pt;
	var arr = new Array(); //创建数组
	$.ajax({
		url : base + "task/shopMap",
		type : "GET",
		data : searchData,
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		success : function(shopData) {
			if(shopData.length > 0){
				$('#totalElements').text(shopData.length);
				
				$.each(shopData, function(i,item){
	                arr = item.coordinate.split(",");
	                for (i = 0;i < arr.length;i++){
	                	pt = new BMap.Point(arr[0],arr[1]);  // 拿到坐标点
	                }
					alert(pt.lng+","+pt.lat);
					var orderNum = item.orderNum;
					var uId = item.userId;
					var htm = "<div style='background:none;'> ";
						if(searchData['condition'] == "0" && item.avgOrderNum >= 20 && searchData['status'] == "2"){
							htm += "<img src='../static/img/task/zcwth1.png' border='0' /><div class='ltten'><font color='white'>";
						}
						if(searchData['condition'] == "0" && item.avgOrderNum < 20 && searchData['status'] == "2"){
							htm += "<img src='../static/img/task/zcwth2.png' border='0' /><div class='lticon'><font color='white'>";
						}
						if(searchData['condition'] == "1" && item.avgOrderNum >= 20 && searchData['status'] == "2"){
							htm += "<img src='../static/img/task/yicth1.png' border='0' /><div class='ltten'><font color='white'>";
						}
						if(searchData['condition'] == "1" && item.avgOrderNum < 20 && searchData['status'] == "2"){
							htm += "<img src='../static/img/task/yicth2.png' border='0' /><div class='lticon'><font color='white'>";
						}
						if(searchData['condition'] == "2" && item.avgOrderNum >= 20 && searchData['status'] == "2"){
							htm += "<img src='../static/img/task/ercth1.png' border='0' /><div class='ltten'><font color='white'>";
						}
						if(searchData['condition'] == "2" && item.avgOrderNum < 20 && searchData['status'] == "2"){
							htm += "<img src='../static/img/task/ercth2.png' border='0' /><div class='lticon'><font color='white'>";
						}
						if(searchData['condition'] == "0" && searchData['status'] == "1"){
							htm += "<img src='../static/img/task/zcwth2.png' border='0' /><div class='lticon'><font color='white'>";
						}
						if(searchData['condition'] == "1" && searchData['status'] == "1"){
							htm += "<img src='../static/img/task/yicth2.png' border='0' /><div class='lticon'><font color='white'>";
						}
						if(searchData['condition'] == "2" && searchData['status'] == "1"){
							htm += "<img src='../static/img/task/ercth2.png' border='0' /><div class='lticon'><font color='white'>";
						}
						if(orderNum > 99){
							htm += "99+";
						}else{
							htm += orderNum;
						}
						htm += "</font></div></div>";
					var myRichMarkerObject = new BMapLib.RichMarker(htm, pt, {"anchor": new BMap.Size(-17, -17), "enableDragging": false});
					var content = item.shopName;
					content += "<div style='padding-top:20px;'><a href='/task/addVisitList' class='view'>查看</a><a href='javascript:;' onclick=addVisit("+item.registId+",\'"+uId+"\',\'"+item.shopName+"\') class='view'>拜访</a></div>";
					map.addOverlay(myRichMarkerObject);               // 将标注添加到地图中
					alert(content);
					addClickHandler(content,myRichMarkerObject);
	            });
			}else{
				alert("暂无数据!");
			}
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
}

function addClickHandler(content,marker){
	marker.addEventListener("click",function(e){
		openInfo(content,e)}
	);
}
function openInfo(content,e){
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
	map.openInfoWindow(infoWindow,point); //开启信息窗口
}

/**
 * 根据状态进行检索
 * 
 * @param status
 */
function searchStatusData(status) { // 状态：考核中、已转正
	if (status == "CHECKIN") {
		delete searchData['page'];
		searchData['status'] = "1"; // 状态为考核
	} else {
		delete searchData['page'];
		searchData['status'] = "2"; // 状态为转正
	}
	ajaxSearch(searchData);
}

/**
 * 根据条件进行检索
 * 
 * @param condition
 */
function searchConditionData(condition) { // 条件：活跃、未提货、一次
	if (condition == "TWO") {
		delete searchData['page'];
		searchData['condition'] = "2"; // 条件为活跃
	}
	if (condition == "ZERO"){
		delete searchData['page'];
		searchData['condition'] = "0"; // 条件为未提货
	}
	if(condition == "ONE"){
		delete searchData['page'];
		searchData['condition'] = "1"; // 条件为一次
	}
	ajaxSearch(searchData);
}

//添加拜访
function addVisit(registId,userId,shopName) {
	$.ajax({
		url : base + "task/lastVisit",
		type : "post",
		data : {registId:registId},
		dataType : "json",
		success : function(result) {
			if("ok" == result.status){
				$('#addVisit').modal({
					keyboard: false
				})
			}else{
				$('#tips').modal({
					keyboard: true
				})
			}
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
	$('#registId').val(registId);
	$('#userId').val(userId);
	$('#taskName').val(shopName+"拜访");
}

function saveVisit(){
	var arr = $('#addForm').serializeArray();
	var param = $.param(arr);
	$.ajax({
		url : base + "task/saveVisit",
		type : "post",
		data : param,
		dataType : "json",
		success : function(result) {
			if("ok" == result.status){
				$('#addVisit').modal('hide');
				alert("添加成功!");
			}else{
				alert("保存失败!");
			}
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
}
