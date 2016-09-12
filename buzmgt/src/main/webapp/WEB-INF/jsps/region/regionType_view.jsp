<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
			+ "/";
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

	<!-- 树型结构 -->
	<link rel="stylesheet" type="text/css" href="static/zTree/css/icon.css" />
	<link rel="stylesheet" type="text/css" href="static/zTree/css/main.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/zTree/css/zTreeStyle/zTreeStyle.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/css/region/purview-region-setting.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/purview-setting/purview-setting.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/bootstrap/css/bootstrap-dialog.css" />
	<link rel="stylesheet" type="text/css"   href="static/bootstrap/css/jquery-confirm.min.css" />


	<link rel="stylesheet" type="text/css"   href="static/css/region/jquery.gridly.css" />
	<link rel="stylesheet" type="text/css"   href="static/css/region/sample.css" />

	<script src="static/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/js/region/jquery.gridly.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/js/region/sample.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/js/region/rainbow.js" type="text/javascript" charset="utf-8"></script>

	<style>

		.maintype {
		width: 980px;
		margin: 80px auto; }

		.brick small {
			text-align:center;
			vertical-align:middle;
			line-height:135px;
			border:#000000 dotted 1px;
		}
	</style>
</head>
<body>
<div id="j_page_main" class="content main">
	<h4 class="page-header-region">
		<i class="icon region-setting-icon"></i>区域类型
	</h4>
	<div class="row">
		<div class="col-md-12">
			<div class="box border blue">
				<div class="box-title">
					<div class="row">
						<div class="col-md-12">
							<div class="role">
								<i class="title-icon"></i>区域类型

							</div>
						</div>
					</div>
				</div>
				<div  class="maintype">
					<section class='example'>
						<div class='gridly'>
							<div class='brick small'　id="typeid" style="text-align:center;vertical-align:middle;line-height:135px;border:#000000 dotted 1px;">
							 <a class='delete' href='#'>&times;</a>
								<span style="font-size: large;color: #000000" >中国</span>
							</div>

							<div class='brick small'　id="typeid" style="text-align:center;vertical-align:middle;line-height:135px;border:#000000 dotted 1px;">
								<a class='delete' href='#'>&times;</a>
								<span style="font-size: large;color: #000000" >大区</span>
							</div>

							<div class='brick small'　id="typeid" style="text-align:center;vertical-align:middle;line-height:135px;border:#000000 dotted 1px;">
								<a class='delete' href='#'>&times;</a>
								<span style="font-size: large;color: #000000" >省</span>
							</div>

							<div class='brick small'　id="typeid" style="text-align:center;vertical-align:middle;line-height:135px;border:#000000 dotted 1px;">
								<a class='delete' href='#'>&times;</a>
								<span style="font-size: large;color: #000000" >市</span>
							</div>

						</div>
						<p class='actions'>
						<a class='add button' href='#'>添加</a>
						</p>
					</section>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- Bootstrap -->
<script src='static/js/common.js'></script>
<script src="static/bootstrap/js/bootstrap.min.js"
		type="text/javascript" charset="utf-8"></script>
<script type="text/javascript"
		src="static/bootstrap/js/jquery-confirm.min.js"></script>

</body>
</html>