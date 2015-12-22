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
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/index.css" />
<link rel="stylesheet" type="text/css" href="static/css/purview-setting.css"/>
<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>



<link rel="stylesheet" type="text/css" href="static/zTree/css/icon.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/main.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="static/zTree/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="static/js/region/regiontree.js"></script>




<style>


</style>
</head>
<body   >
<!-- 	<div align="center" > -->
<!-- 		sdfsf<ul id="treeDemo" class="ztree" >dfdfdf</ul> -->
<!-- 	</div> -->
			
			<div id="main" >
				<div >
							<div class="col-sm-10">
								<div >
									<div class="clearfix">
										<h4 class="content-title pull-left">Elements</h4>
									</div>
								</div>
							</div>
				</div>
	
		</div>


</body>
</html>