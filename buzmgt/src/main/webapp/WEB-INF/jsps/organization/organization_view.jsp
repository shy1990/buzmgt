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
<title>组织结构</title>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />

<!-- 树型结构 -->
<link rel="stylesheet" type="text/css" href="static/zTree/css/icon.css" />
<link rel="stylesheet" type="text/css" href="/static/zTree/css/main.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/zTreeStyle/organzTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="static/css/organization/purview-organ-setting.css" />
<link rel="stylesheet" type="text/css" href="static/purview-setting/purview-setting.css" />
<link rel="stylesheet" type="text/css" href="static/bootstrap/css/bootstrap-dialog.css" />
<link rel="stylesheet" type="text/css" href="static/bootstrap/css/jquery-confirm.min.css" />
<link rel="stylesheet" type="text/css" href="static/css/alert.css" />

<style>
.top-titile{
	padding: 20px;
	color: red;
}
</style>
</head>
<body >
				        <div id="j_page_main" class=" content main">
							<h4 class="page-header-region"><i class="icon organization-setting-icon"></i>组织结构 </h4>  
							<div class="row">
							<div class="col-md-12">
								<div class="organization box border blue">
								<div class="box-title">
									<div class="row">
										<div class="col-md-12">
											<div class="role">
													<i class="title-icon"></i>组织结构
												
											</div>
										</div>
									</div>
								</div>
								<div class="">
									<div class="role-list">
													<div
														style="width: 100%; height: 32px; border-right: 1px solid rgb(221, 221, 221);"></div>
													<ul id="organizationId" class="ztree"></ul>
												</div>
								</div>
								</div>
							</div>
						</div>
							
							
							
							
						</div>
	<!-- Bootstrap -->
	<script src="static/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/bootstrap/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>
   <script src="static/zTree/js/jquery.ztree.all-3.5.js" type="text/javascript" charset="utf-8"></script>
   <script type="text/javascript" src="static/bootstrap/js/bootstrap-dialog.js"></script>
    <script type="text/javascript" src="static/bootstrap/js/jquery-confirm.min.js"></script>
   <script src="static/js/organization/organizationTree.js" type="text/javascript" charset="utf-8"></script>
   <script src='static/js/common.js'></script>
</body>
</html>
