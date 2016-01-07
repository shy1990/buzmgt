<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.print(basePath);
%>
<!DOCTYPE html>
<html>
<head>
<title>区域划分</title>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/index.css" />

<!-- 树型结构 -->
<link rel="stylesheet" type="text/css" href="static/zTree/css/icon.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/main.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="static/css/region/purview-region-setting.css" />

<link rel="stylesheet" type="text/css" href="static/bootstrap/css/bootstrap-dialog.css" />
<link rel="stylesheet" type="text/css" href="static/bootstrap/css/jquery-confirm.min.css" />

<style>
.top-titile{
	padding: 20px;
	color: red;
}
</style>
</head>
<body >
		<%@ include file="../top.jsp"%>
			<div class="container-fluid">
				<div   class="row">
					<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
						<%@include file="../left.jsp"%>
			        </div>
			        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
				        <div id="j_page_main" class="content main">
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
						<div id="allmap">
						
						</div>
					</div>
				</div>
		</div>
	<!-- Bootstrap -->
	<script src="static/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/bootstrap/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
   <script src="static/zTree/js/jquery.ztree.all-3.5.js" type="text/javascript" charset="utf-8"></script>
<!--    <script type="text/javascript" src="static/bootstrap/js/bootstrap-dialog.js"></script> -->
    <script type="text/javascript" src="static/bootstrap/js/jquery-confirm.min.js"></script>
   <script src="static/js/region/regiontree.js" type="text/javascript" charset="utf-8"></script>
   
</body>
</html>
