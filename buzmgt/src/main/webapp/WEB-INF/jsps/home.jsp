<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>控制台</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link type="text/css" href="static/plugins/css/unical.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/css/index.css" />
<link rel="stylesheet" type="text/css"
	href="static/purview-setting/purview-setting.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<%@ include file="top_menu.jsp"%>
	<div class="container-fluid">
		<div id="" class="row">
			<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
				<%@include file="left_menu.jsp"%>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 clear-padd">
					<!-- CALENDAR -->
					<div class="row">
						<div class="col-md-12 index-bg">
							<iframe src="role/roleList" id="iframepage" name="iframepage" frameBorder=0 scrolling=no width="100%" onLoad="reinitIframeEND();"  ></iframe>
						</div>
					</div>
					<!-- /CALENDAR -->
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="static/js/jquery/jquery-1.11.3.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/js/index.js" type="text/javascript" charset="utf-8"></script>

</body>

</html>
