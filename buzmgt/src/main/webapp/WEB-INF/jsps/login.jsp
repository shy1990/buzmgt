<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta charset="utf-8">
	<title>登陆</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
	<meta name="description" content="">
	<meta name="author" content="">
	<!-- STYLESHEETS --><!--[if lt IE 9]><script src="js/flot/excanvas.min.js"></script><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script><![endif]-->
	<link rel="stylesheet" type="text/css" href="static/CloudAdmin/css/cloud-admin.css" >
	
	<link href="static/CloudAdmin/font-awesome/css/font-awesome.min.css" rel="stylesheet">
	<!-- DATE RANGE PICKER -->
	<link rel="stylesheet" type="text/css" href="static/CloudAdmin/js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
	<!-- UNIFORM -->
	<link rel="stylesheet" type="text/css" href="static/CloudAdmin/js/uniform/css/uniform.default.min.css" />
	<!-- ANIMATE -->
	<link rel="stylesheet" type="text/css" href="static/CloudAdmin/css/animatecss/animate.min.css" />
	<!-- FONTS -->
	<link href='http://fonts.useso.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css'>
	<style>
	.login-box input{
		padding-top:8px;
	}
	</style>
</head>
<body class="login">	
	<!-- PAGE -->
	<section id="page">
			<!-- HEADER -->
			<header>
				<!-- NAV-BAR -->
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div id="logo">
								<a href="index.html"><img src="static/CloudAdmin/img/logo/logo.png" height="40" alt="logo name" /></a>
							</div>
						</div>
					</div>
				</div>
				<!--/NAV-BAR -->
			</header>
			<!--/HEADER -->
			<!-- LOGIN -->
			<section id="login_bg" class="visible">
				<div class="container">
					<div class="row">
						<div class="col-md-4 col-md-offset-4">
							<div class="login-box">
								<h2 class="bigintro">Sign In</h2>
								<div class="divide-20"></div>
								<form action="" method="post">
								  <div class="form-group">
									<i class="fa fa-envelope"></i>
									<input type="text" class="form-control" name="username" placeholder="用户名">
								  </div>
								  <div class="divide-20"></div>
								  <div class="form-group"> 
									<i class="fa fa-lock"></i>
									<input type="password" class="form-control" name="password" placeholder="密码">
								  </div>
								  <div>
									<label class="checkbox"> <input type="checkbox" class="uniform" value=""> Remember me</label>
									<button type="submit" class="btn btn-danger">登录</button>
								  </div>
								</form>
								<!-- SOCIAL LOGIN -->
								<div class="divide-20"></div>
								<div class="center">
									<strong style="color: rgba(110, 47, 47, 0.77)">${error }</strong>
								</div>
								<div class="divide-40"></div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<!--/LOGIN -->
	<!--/PAGE -->
	<!-- JAVASCRIPTS -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- JQUERY -->
	<script src="static/CloudAdmin/js/jquery/jquery-2.0.3.min.js"></script>
	<!-- JQUERY UI-->
	<script src="static/CloudAdmin/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<!-- BOOTSTRAP -->
	<script src="static/CloudAdmin/bootstrap-dist/js/bootstrap.min.js"></script>
	
	
	<!-- UNIFORM -->
	<script type="text/javascript" src="static/CloudAdmin/js/uniform/jquery.uniform.min.js"></script>
	<!-- BACKSTRETCH -->
	<script type="text/javascript" src="static/CloudAdmin/js/backstretch/jquery.backstretch.min.js"></script>
	<!-- CUSTOM SCRIPT -->
	<script src="static/CloudAdmin/js/script.js"></script>
	<script>
		jQuery(document).ready(function() {		
			App.setPage("login_bg");  //Set current page
			App.init(); //Initialise plugins and elements
		});
	</script>
	<script type="text/javascript">
		function swapScreen(id) {
			jQuery('.visible').removeClass('visible animated fadeInUp');
			jQuery('#'+id).addClass('visible animated fadeInUp');
		}
	</script>
	<!-- /JAVASCRIPTS -->
</body>
</html>
