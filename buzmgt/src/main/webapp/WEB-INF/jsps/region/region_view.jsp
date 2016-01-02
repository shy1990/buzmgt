<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>区域划分</title>
<base href="<%=basePath%>" />
<script type="text/javascript">
	

</script>






<style>
.top-titile{
	padding: 20px;
	color: red;
}

</style>
</head>
<body   >
		<link rel="stylesheet" type="text/css" href="static/zTree/css/icon.css" />
		<link rel="stylesheet" type="text/css" href="static/zTree/css/main.css" />
		<link rel="stylesheet" type="text/css" href="static/zTree/css/zTreeStyle/zTreeStyle.css" />
		
		<!--加载鼠标绘制工具-->
	 	<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script> 
	 	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" /> 
		<!--加载检索信息窗口-->
		<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
		<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />
<!-- 	<div align="center" > -->
<!-- 		sdfsf<ul id="treeDemo" class="ztree" >dfdfdf</ul> -->
<!-- 	</div> -->
			
			<div id="main">
				<div id="regiontree">
					<h4 class="page-header-region">区域划分        <button type="button" class="btn btn-warning">绘制地图</button></h4>  
					<div class="row">
								<div class="col-md-12">
									<div class="box border orange">
										<div class="row">
											<div class="col-md-12">
												<div class="role">
													<div class="role-region-title"><i class="title-icon"></i>区域结构</div>
													<div class="role-list">
														<div style="width: 100%;height: 32px; border-right: 1px solid rgb(221, 221, 221);"></div>
															<ul id="treeDemo" class="ztree" ></ul>
	<!-- 													<ul class="nav nav-tabs"> -->
	<!-- 													   <li class="active"> -->
	<!-- 													   	<a href="#tab_1_1" data-toggle="tab">区域总监  -->
	<!-- 													   		<i class="icon delete-icon"> -->
	<!-- 													   			<div class=""> -->
														   	
	<!-- 													   			</div> -->
	<!-- 													   		</i> -->
	<!-- 													   		<i class="icon query-icon"></i>  -->
	<!-- 													   	</a> -->
	<!-- 													   </li> -->
	<!-- 													   <li><a href="#tab_1_2" data-toggle="tab">财务总监</a></li> -->
	<!-- 													   <li><a href="#tab_1_3" data-toggle="tab">售后部</a></li> -->
	<!-- 													   <li><a href="#tab_1_3" data-toggle="tab">人资部</a></li> -->
	<!-- 													</ul> -->
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
					</div>
					
					<div id="regionmap" style="">
					xcvxcvc<div id="allmap" > sdfsd</div>
					</div>
				</div>
		</div>
		
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");    // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
	map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
	map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
	map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	
	</script>

</body>
</html>