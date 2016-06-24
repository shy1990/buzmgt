<%@ page language="java" import="java.util.*,com.wangge.buzmgt.region.entity.*,com.wangge.buzmgt.sys.vo.*,com.wangge.buzmgt.saojie.entity.*"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
      + "/";
%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>考核设置</title>
		<!-- Bootstrap -->
		<link href="../static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="../static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
		
		<!-- 树型结构 -->
		<link rel="stylesheet" type="text/css" href="/static/zTree/css/icon.css" />
		<link rel="stylesheet" type="text/css" href="/static/zTree/css/main.css" />
		<link rel="stylesheet" type="text/css" href="../static/zTree/css/zTreeStyle/organzTreeStyle.css" />
		<link rel="stylesheet" type="text/css" href="../static/css/organization/purview-organ-setting.css" />
		<link rel="stylesheet" type="text/css" href="../static/purview-setting/purview-setting.css" />
		
		
		<link rel="stylesheet" type="text/css" 	href="../static/saojie/saojie-set.css" />
		<script src="../static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
		<style>
		#allmap{width: 100%;height:100%; overflow: hidden;margin:0;font-family:"微软雅黑";}
		</style>
	</head>

	<body>
		<div class="content main">
			<h4 class="team-member-header page-header ">
				<i class="icon-update-saojiedata"></i>更改扫街数据 
			</h4>
			<div class="row">
				<div class="col-md-12">
					<!--box-->
					<div class="saojie-upd-body box border blue">
						<!--title-->
						<div class="box-title">
							<h4>更改扫街数据     ${man}</h4>
						</div>
						<!--title-->
						<!--box-body-->
						<div class="box-body form-horizontal">
							<div class="row">
								<div class="col-sm-4">
										<!--内容-->
									<div class="">
										<div class="role-list">
											<div
												style="width: 100%; height: 32px; border-right: 1px solid rgb(221, 221, 221);"></div>
											<ul id="saojiedataId" class="ztree"></ul>
											<input type="hidden" name="regionId" value="${parentid}" id="regionId">
										</div>
									</div>
								</div>
								
								<div class="col-sm-8">
									<div class="saojie-set-map col-sm-10  col-sm-offset-1 col-xs-12">
										<div class="map-box " id="allmap">
										<input >
									</div>
								</div>
							</div>
							
							
						</div>
						<!--/box-body-->
					</div>
					<!--/box-->
				</div>
				<!--col-->
			</div>
		</div>
		<!--row-->
		<!-- Bootstrap core JavaScript================================================== -->
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
		<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="../static/bootstrap/js/bootstrap.min.js"></script>
		<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="../static/zTree/js/jquery.ztree.all-3.5.js" type="text/javascript" charset="utf-8"></script>
		<script src="../static/js/region/saojiedataTree.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		/* 定位到区,以选择的区为中心点 */
		 <%String areaname = request.getAttribute("areaname").toString();%>
			 var map = new BMap.Map("allmap");
		 	if("山东省"=="<%=areaname%>"){
		 		map.centerAndZoom(new BMap.Point(117.010765,36.704194), 14);
		 	}else{
		 		
		 		
		 		var name ="<%=areaname%>";
		 		
		 		
		 		
		 		
		 		<%
				if(null!=request.getAttribute("pcoordinates")){%>
					<%
					String pcoordinates=request.getAttribute("pcoordinates").toString();
					String[] listCoordinates=pcoordinates.split("=");
					 %> 
					 			var polygon = new BMap.Polygon([
					 	<%
									for(int x=0;x<listCoordinates.length;x++){
										String points=listCoordinates[x];
										double lng=Double.parseDouble(points.split("-")[0]);//经度 
						 		  		double lat=Double.parseDouble(points.split("-")[1]);//纬度 
						 %>				
						 		  		<%
						 		  			if(x==listCoordinates.length-1){%>
						 		  			new BMap.Point(<%=lng%>,<%=lat%>)
						 		  			<%}else{%>
						 		  			 new BMap.Point(<%=lng%>,<%=lat%>),
						 		  			<%}
						 		  		%>
						 <%
									}%>
									], {strokeColor:"blue", strokeWeight:2,fillColor: "", strokeOpacity:0.5});  //创建多边形
					 				map.addOverlay(polygon);
									<%
										String jlng=listCoordinates[0].split("-")[0];
										String jlat=listCoordinates[0].split("-")[1];
									%>
					 				map.centerAndZoom(new BMap.Point(<%=jlng%>, <%=jlat%>), 12);
					 				map.enableScrollWheelZoom(true); 
									<%
				}else{%>
		 		
			 		var bdary = new BMap.Boundary();
			 		
			 		bdary.get(name, function(rs){ //获取行政区域
			 		var count = rs.boundaries.length; //行政区域的点有多少个
		
			 		for(var i = 0; i < count; i++){
			 		var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight:1, strokeColor: "", fillColor: "", fillOpacity: 0.3}); //建立多边形覆盖物
			 		map.addOverlay(ply); //添加覆盖物
			 		map.setViewport(ply.getPath()); //调整视野 
			 		} 
			 		map.centerAndZoom(name, 11);
			 		map.enableScrollWheelZoom(true); 
			 		}); 
		 		
		 		<%}%>
		 		
		 		  	/* 迭代每个镇轮廓 */
		 		 <%
					List<Region> listRegion = (List<Region>) request.getAttribute("regionData");
					if (listRegion != null && listRegion.size() > 0) {
						for (int i = 0; i < listRegion.size(); i++) {
							String coordinates = listRegion.get(i).getCoordinates();
							String name=listRegion.get(i).getName();
							String centerPoint=listRegion.get(i).getCenterPoint();
							String[] listCoordinates = null;
							if (null != coordinates) {
								listCoordinates = coordinates.split("=");
							}%> 
				 			var polygon = new BMap.Polygon([
				 	<%if (listCoordinates != null && listCoordinates.length > 0) {
								for (int x = 0; x < listCoordinates.length; x++) {
									String points = listCoordinates[x];
									double lng = Double.parseDouble(points.split("-")[0]);//经度 
									double lat = Double.parseDouble(points.split("-")[1]);//纬度%>				
					 		  		<%if (x == listCoordinates.length - 1) {%>
					 		  			new BMap.Point(<%=lng%>,<%=lat%>)
					 		  			<%} else {%>
					 		  			 new BMap.Point(<%=lng%>,<%=lat%>),
					 		  			<%}%>
					 
					 
					 <%}
							}%>
								], {strokeColor:"blue", strokeWeight:2,fillColor: "", strokeOpacity:0.5});  //创建多边形
				 				map.addOverlay(polygon); 	
								//色块上的文字shuomi
				 				<%
										if(null !=centerPoint){
										double lng = Double.parseDouble(centerPoint.split("-")[0]);//经度 
										double lat = Double.parseDouble(centerPoint.split("-")[1]);//纬度%>
										var secRingCenter = new BMap.Point(<%=lng%>,<%=lat%>)
										var secRingLabel2 = new BMap.Label("<%=name%>",{offset: new BMap.Size(10,-30), position: secRingCenter});
										secRingLabel2.setStyle({"line-height": "20px", "text-align": "center", "width": "80px", "height": "29px", "border": "none", "padding": "2px","background": "url(http://jixingjx.com/mapapi/ac.gif) no-repeat",});
										map.addOverlay(secRingLabel2);
								<%}
								
								}
					}
				%>
		 	}
		 			var marker;
		 			var arr = new Array(); //创建数组
		 	<%
		 		SaojieDataVo saojiedatalist=(SaojieDataVo)request.getAttribute("saojiedatalist");
		 		System.out.println(saojiedatalist.getAreaName());
		 		List<SaojieData> listsaojiedata=saojiedatalist.getList(); 
		 		
		 		for(SaojieData saojiedata:listsaojiedata){
		 			
		 			String coor = saojiedata.getCoordinate();
		 			String content=saojiedata.getName();
					if(coor != null && coor != ""){
		                for (int j = 0;j < coor.split("-").length;j++){%>
		                	marker = new BMap.Marker(new BMap.Point(<%=coor.split("-")[0]%>,<%=coor.split("-")[1]%>));// 拿到坐标点
		           <%     }%>
					       	var content = '<%=content%>';
				  			map.addOverlay(marker);               // 将标注添加到地图中
				  			addClickHandler(content,marker);
		           
					<%}
		 			
		 		}
		 	%>
		 	
		 	//监听事件
		 	function addClickHandler(content,marker){
		 		marker.addEventListener("click",function(e){
		 			openInfo(content,e)}
		 		);
		 	}
		 	var opts = {
					width : 250,     // 信息窗口宽度
					height: 80,     // 信息窗口高度
					title : "扫街信息" , // 信息窗口标题
					enableMessage:true//设置允许信息窗发送短息
				   };
		 	//开启窗口
		 	function openInfo(content,e){
		 		var p = e.target;
		 		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		 		var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
		 		map.openInfoWindow(infoWindow,point); //开启信息窗口
		 	}
		</script>
	</body>

</html>
