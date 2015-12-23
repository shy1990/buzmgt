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
<link rel="stylesheet" type="text/css" href="static/css/bun-admin.css"/>
<link rel="stylesheet" type="text/css" href="static/css/default.css"/>
<link rel="stylesheet" type="text/css" href="static/css/responsive.css"/>
<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>



<link rel="stylesheet" type="text/css" href="static/zTree/css/icon.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/main.css" />
<link rel="stylesheet" type="text/css" href="static/zTree/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="static/zTree/js/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="static/js/region/regiontree.js"></script>




<style>
.top-titile{
	padding: 20px;
	color: red;
}

</style>
</head>
<body   >
<!-- 	<div align="center" > -->
<!-- 		sdfsf<ul id="treeDemo" class="ztree" >dfdfdf</ul> -->
<!-- 	</div> -->
			
			<div id="main" >
				<div class="top-titile col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 row breadcrumb main " >
							<div class="col-sm-10">
								<div >
									<div >
										 <h4 class="content-title pull-left"><img alt="" src="static/img/region/quyuhuafen.png" /> 区域划分                   	<button type="button" class="btn btn-warning">绘制地图</button></h4>   
									</div>
								</div>
							</div>
				</div>
				
				
					<!-- BOX TABS -->
						<div class="separator"></div>
						<!-- INLINE TABS -->
						<div class="row">
							<div class="col-md-12">
								<!-- BOX -->
								<div class="box border orange">
									<div class="box-title">
										<h4><i class="fa fa-columns"></i>区域结构</h4>
										<div class="tools">
											<a href="#box-config" data-toggle="modal" class="config">
												<i class="fa fa-cog"></i>
											</a>
											<a href="javascript:;" class="reload">
												<i class="fa fa-refresh"></i>
											</a>
											<a href="javascript:;" class="collapse">
												<i class="fa fa-chevron-up"></i>
											</a>
											<a href="javascript:;" class="remove">
												<i class="fa fa-times"></i>
											</a>
										</div>
									</div>
									<div class="box-body big">
										<div class="alert alert-success">
										  <h4>Tabs on Left, Right and Bottom</h4>
										  <p>Support for left, right and bottom is removed from Bootstrap 3.</p> 
										  <p>But with <strong>Cloud Admin</strong> you still cal use <code>.tabbable .tabs-left</code>, <code>.tabbable .tabs-right</code> and <code>.tabbable .tabs-below</code> as you did with Bootstrap 2.</p>
										</div>
									</div>
								</div>
								<!-- /BOX -->
							</div>
						</div>
				
				
	
		</div>


</body>
</html>