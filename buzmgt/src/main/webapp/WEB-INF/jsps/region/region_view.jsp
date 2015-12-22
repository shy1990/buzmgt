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



<link rel="stylesheet" type="text/css" href="static/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="static/zTree/css/icon.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/main.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="static/js/jquery.min.js"></script> 
<script type="text/javascript" src="static/zTree/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="static/js/region/regiontree.js"></script>




<style>

.main .page-header {
	height: 660px;
	line-height: 66px;
	margin-top: 0;
	margin-left: -260px;
	padding-left: 500px ;
	margin-right: -400px;
	font-size: 14px;
	font-weight: bold;
	background: url(../img/icon/purview-setting/ddqx.png) no-repeat 25px 22px #fff;
}


</style>
</head>
<body   >
<!-- 	<div align="center" > -->
<!-- 		sdfsf<ul id="treeDemo" class="ztree" >dfdfdf</ul> -->
<!-- 	</div> -->

<div id="main" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
						<h4 class="page-header">权限设置</h1>
</div>
	
	


</body>
</html>