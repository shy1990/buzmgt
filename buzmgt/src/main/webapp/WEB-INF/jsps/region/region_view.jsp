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
	 	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" /> 
		<!--加载检索信息窗口-->
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
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
					</div>
				</div>
		</div>
		
		
		


</body>
</html>