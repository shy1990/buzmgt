$(function() {
	/**
	 * 导出
	 */
	$('.table-export').on('click',function() {
		window.location.href = base + "oilCost/export/detail?oilCostId="+oilCostId;
	});
	
	/**
	 * 地图加载
	 */
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	if(pcoordinates != null && pcoordinates != "" && pcoordinates != undefined){
		var arr = new Array();
		arr = pcoordinates.split("=");
		for (var x = 0; x < arr.length; x++) {
			var lng = arr[1].split("-")[0];
			var lat = arr[1].split("-")[1];
			map.centerAndZoom(new BMap.Point(lng, lat), 15);//用坐标点定位
		}
	}else{
		map.centerAndZoom(regionName,13);      // 初始化地图,用城市名设置地图中心点
	}
	map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
	var list = $.parseJSON(jsonstr);
	window.run = function (){
		map.clearOverlays();
//		var driving2 = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});    //驾车实例
	var driving = new BMap.DrivingRoute(map);    //驾车实例
	if(list != null && list.length > 0){
		for(var i = 0; i < list.length; i++){
			var slng = list[i].coordinate.split("-")[0];
			var slat = list[i].coordinate.split("-")[1];
			if(i < list.length - 1){
				var elng = list[i+1].coordinate.split("-")[0];
				var elat = list[i+1].coordinate.split("-")[1];
			}
			if(i < list.length - 1){
				console.log(list[i+1].regionName);
			}
			/*var start = i;
			var atart = new BMap.Point(slng,slat);    //起点
			var end = start+1;
			end = new BMap.Point(elng,elat);    //终点
*/
//			driving2.search(new BMap.Point(slng,slat), new BMap.Point(elng,elat));    //显示一条公交线路
			driving.search(new BMap.Point(slng,slat), new BMap.Point(elng,elat));//从起点到终点检索
		}

		driving.setSearchCompleteCallback(function(){
			var pts = driving.getResults().getPlan(0).getRoute(0).getPath();    //通过驾车实例，获得一系列点的数组
			var polyline = new BMap.Polyline(pts);   
		    map.addOverlay(polyline);
		    console.log("length:"+pts.length);
		    
		    setTimeout(function(){
				time(pts,map);
			},100);
		});
//		var paths = pts.length;
//		carMk = new BMap.Marker(pts[0],{icon:myIcon});
//		map.addOverlay(carMk);
//		console.log(pts[0].lng);
//		i=0;
//		function resetMkPoint(i){
//			carMk.setPosition(pts[i]);
//			if(i < paths){
//				setTimeout(function(){
//					i++;
//					resetMkPoint(i);
//				},100);
//			}
//		}
//		setTimeout(function(){
//			resetMkPoint(5);
//		},100)
//	}
	}
}
	setTimeout(function(){
		run();
	},1500);
});

function time(pts,map){
	var myIcon = new BMap.Icon('http://developer.baidu.com/map/jsdemo/img/car.png', new BMap.Size(52,26),{anchor : new BMap.Size(27, 13)});
	var carMk = new BMap.Marker(pts[0],{icon:myIcon});
	map.addOverlay(carMk);
	for(var kk = 0;kk<pts.length;kk++){
		carMk.setPosition(pts[kk]);
	}
//	console.log(pts[0].lng);
}

/**
 * 处理检索条件
 * 
 * @returns {String}
 */
function conditionProcess() {
	var SearchData_ = "sc_GTE_dateTime="
			+ (SearchData.sc_GTE_dateTime == null ? ''
					: SearchData.sc_GTE_dateTime)
			+ "&sc_LTE_dateTime="
			+ (SearchData.sc_LTE_dateTime == null ? ''
					: SearchData.sc_LTE_dateTime);

	return SearchData_;
}
