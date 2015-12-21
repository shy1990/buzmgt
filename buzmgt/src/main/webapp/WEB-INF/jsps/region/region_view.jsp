<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.println(basePath);
%>
<!DOCTYPE html>
<html>
<head>
<title>区域划分</title>
<base href="<%=basePath%>" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="static/zTree/css/icon.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/main.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="static/js/jquery.min.js"></script> 
<script type="text/javascript" src="static/zTree/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="static/js/region/regiontree.js"></script>
</head>
<body id="main"  >
	<div align="center" >
		<ul id="treeDemo" class="ztree" ></ul>
	</div>
</body>
</html>