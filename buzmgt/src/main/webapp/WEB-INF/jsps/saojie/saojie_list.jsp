<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>扫街设置列表</title>
<!-- Bootstrap -->
<link href="../static/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="../static/saojie/saojie.css" />
<script src="../static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
 <script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
<script type="text/javascript">
	$(function() {
		var status = $("#addClass").val();
		console.info(status);
		if (status != null && status != '') {
			$("li[title = '" + status + "']").addClass("active");
		} else {
			$("li[title = '全部']").addClass("active");
		}
	});
</script>
</head>
<body>
			<div class="main">
				<h4 class="team-member-header page-header ">
					<div class="row">
						<div class="col-sm-12">
							<i class="icon team-member-list-icon"></i>扫街设置
							<!--区域选择按钮-->
							<button type="button" class="btn btn-default" onclick="getRegion(${regionId});">
								<i class="icon province-icon"></i>${regionName}
							</button>
							<!--/区域选择按钮-->
							<button class="btn btn-warning member-add-btn" type="button"
								onclick="javascript:window.location.href='/saojie/toAdd'">
								<i class="icon icon-add"></i>添加扫街
							</button>
							<small class="header-text">共<span class="text-red">203</span>个区域
							</small>
							<!-- <small class="header-text">今日新增<span class="text-red"> 0 +</span></small> -->
						
						</div>
					</div>
				</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="team-member-body box border red">
					<!--title-->
				
					<!--/box-body-->
				</div>
				<!--/box-->
			</div>
			<!-- end col-sm-9 -->
			<div class="col-md-3 ">
				<!--box-->
				<div class="member-district box border gray">
					<!--title-->
					<div class="box-title">
						<i class="icon icon-district"></i>区域
					</div>
					<div class="box-body">
						<div style="height: 290px" id="allmap"></div>
						<div align="center">
<!-- 							<a href="/salesman/showMap"><font color="#0099ff" size="3">查看完整地图</font></a> -->
						</div>
						<!-- 						地图 -->
						<!-- 						<img width="100%" src="/static/img/saojieap.png" /> -->
						<!-- 						/地图 -->
						<!-- 						组织结构 -->
						<!-- 						<div class="structure col-xs-12"> -->
						<!-- 							<i class="icon icon-structure"></i> 组织结构 -->
						<!-- 						</div> -->
						<!-- 						tree view -->
						<!-- 						<div id="tree3" class="tree"></div> -->
						<!--/组织结构-->
					</div>
				</div>
			</div>

		</div>
		<!-- /CALENDAR -->
	</div>
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
	<script type="text/javascript" src="/static/js/common.js"></script>
	<script src="../static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>
	<script src="../static/js/saojie/saojie.js" type="text/javascript"
		charset="UTF-8"></script>
	<script type="text/javascript">
		<%String areaname = request.getAttribute("regionName").toString();
		String parentid = null;
		if (null != request.getAttribute("parentid")) {
			parentid = request.getAttribute("parentid").toString();
		}
		%>
	 	var map = new BMap.Map("allmap");
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
								String jlng=listCoordinates[1].split("-")[0];
								String jlat=listCoordinates[1].split("-")[1];
							
							%>
							 var point = new BMap.Point(<%=jlng%>,<%=jlat%>);
							 map.centerAndZoom(point, 8);    
			 				//map.centerAndZoom(name, 8);
			 				map.enableScrollWheelZoom(true); 
			 				
			 				
			 				 var points =[
			 				   <%
			 				  for(int y=0;y<listCoordinates.length;y++){
									String points=listCoordinates[y];
									double lng=Double.parseDouble(points.split("-")[0]);//经度 
					 		  		double lat=Double.parseDouble(points.split("-")[1]);//纬度 
			 				   %>          
					 		  		
			 					
				 		  		<%
					 		  			if(y==listCoordinates.length-1){%> 
				 		  		 		{"lng":<%=lng%>,"lat":<%=lat%>,"count":50}
				 		  			<%}else{%>
				 		  				{"lng":<%=lng%>,"lat":<%=lat%>,"count":50},
				 		  			<%}
				 		  		%> 
					 		  	 <%
									}%>
			 				              ];
	
			 				heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
			 				map.addOverlay(heatmapOverlay);
			 				heatmapOverlay.setDataSet({data:points,max:100});
			 				setTimeout(
			 						function(){
			 							  heatmapOverlay.show();
			 						},3000);
	//		 				polygon.addEventListener('click',function(e) {
	//		 				   var  point=JSON.stringify(e.pixel);
	//							  alert(point);
	<%-- 								  alert(<%=coordinates%>); --%>
	//						});
							<%
		}else{%>
			var bdary = new BMap.Boundary();
			bdary.get('<%=areaname%>', function(rs){ //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
	
			for(var i = 0; i < count; i++){
			var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight:1, strokeColor: "blue", fillColor: "", fillOpacity: 0.3}); //建立多边形覆盖物
			ply.setStrokeWeight(3);
			map.addOverlay(ply); //添加覆盖物
			map.setViewport(ply.getPath()); //调整视野 
			} 
			map.centerAndZoom('<%=areaname%>', 12);
			map.enableScrollWheelZoom(true); 
			}); 
		<%}%>
		
	
	
		/*区域 */
		function getRegion(id){
			window.location.href='/region/getPersonalRegion?id='+id+"&flag=saojie";
		}
	</script>
</body>
</html>
