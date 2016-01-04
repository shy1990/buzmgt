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
		<script type="text/javascript" src="static/js/jquery/jquery.min.js"></script> 
		<script type="text/javascript" src="static/zTree/js/jquery.ztree.all-3.5.js"></script>
		<script type="text/javascript" src="static/js/region/regiontree.js"></script>
	
<!-- 	<div align="center" > -->
<!-- 		sdfsf<ul id="treeDemo" class="ztree" >dfdfdf</ul> -->
<!-- 	</div> -->
			
			<div id="j_page_main" >
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
				
	
		</div>


</body>
</html>