<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
      + "/";
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
<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />

<!-- 树型结构 -->
<link rel="stylesheet" type="text/css" href="static/zTree/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="static/zTree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="static/css/region/purview-region-setting.css" />
<link rel="stylesheet" type="text/css"
	href="static/purview-setting/purview-setting.css" />

<style>
.top-titile {
	padding: 20px;
	color: red;
}
</style>
</head>
<body>
	<div class="content main">
		<h4 class="page-header-region">
			<i class="icon region-switch-icon"></i>区域切换
		</h4>
		<div class="row">
			<div class="col-md-12">
				<div class="regional-switch box border orange">
					<!--title-->
					<div class="box-title">
						<div class="row">
							<div class="col-md-9">
									<i class="title-icon"></i>区域结构
							</div>

							<div class="col-md-3">
								<div class="form-group title-form">
									<form action="/salesman/getSalesManList">
										<div class="input-group ">
											<input type="text" class="form-control" name="regionName"
												id="regionName" placeholder="请输入区域名字"> <a
												type="sumbit" class="input-group-addon" id="goSearch"
												onclick="findRegionByName();"><i class="icon icon-finds"></i></a>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
					<div class="box-body">
						<div style="width: 100%; height: 32px; border-right: 1px solid rgb(221, 221, 221);"></div>
									<ul id="treeDemo" class="ztree"></ul>
									<input type="hidden" id="flag" name="flag" value="${flag}">
								</div>
					</div>
				</div>
			</div>



		</div>
	</div>
	<!-- Bootstrap -->
	<script src="static/js/jquery/jquery.min.js" type="text/javascript"
		charset="utf-8"></script>
	<script src='static/js/common.js'></script>
	<script src="static/bootstrap/js/bootstrap.min.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="static/zTree/js/jquery.ztree.all-3.5.js"
		type="text/javascript" charset="utf-8"></script>
	<!--    <script src="static/js/jquery/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script> -->
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap-dialog.js"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/jquery-confirm.min.js"></script>
	<script src="static/js/region/regionTreePersonal.js"
		type="text/javascript" charset="utf-8"></script>

</body>
</html>
