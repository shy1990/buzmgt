<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>top</title>
<link rel="stylesheet" type="text/css" href="../static/css/index.css" />
<script type="text/javascript"
	src="../static/js/jquery/jquery-2.1.4.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="row">
			<div class="navbar-header col-sm-3 col-md-2">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand logo " href="/"><img
					src="../static/img/logo.png" /></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse ">
				<div style="display: none;" class="pull-left msg-alert">
					<p>
						<span class="msg-head"><i class="msg-alert-icon"></i>消息提醒</span> <span
							class="msg-content">李雅静申请山东省滨州市邹平县扫街审核</span>
					</p>
				</div>
				<ul class="nav navbar-nav navbar-right">
					<!-- li hidden -->
					<li style="display: none;" role="presentation"  class="dropdown"><a
						class="msg-icon-box" data-toggle="dropdown" href="#" role="button"
						aria-haspopup="true" aria-expanded="false"> <i
							class="msg-icon"></i> <span class="mark">4</span>
					</a>
						<ul class="dropdown-menu msg" aria-labelledby="dropdownMenu1">
							<li><a><i class="msg-count-icon"></i>四条消息提醒</a></li>
							<li><a href=""><i></i>曾志伟申请扫街审核<span class="pull-right">2015.12.19</span></a></li>
							<li><a href=""><i></i>退出登录</a></li>
						</ul></li>
					<li  style="display: none;" role="presentation" class="dropdown"><a
						class="time-icon-box"> <i class="time-icon"></i> <span
							class="mark-red">6</span>
					</a>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
							<li><a href="">账户设置</a></li>
							<li><a href="">退出登录</a></li>
						</ul></li>
					<li role="presentation" class="dropdown marg-r-40"><a
						class="user-icon-box" data-toggle="dropdown" href="#"
						role="button" aria-haspopup="true" aria-expanded="false">
							${sessionScope.username}<span class="caret"></span>
					</a>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
							<li><a class="user upd-icon" href="">账户设置</a></li>
							<li><a class="user logout-icon" href="logout">退出登录</a></li>
						</ul></li>
				</ul>
				<form class="navbar-form navbar-right">
					<div class="search-box">
						<input class="search-input" type="text" class="form-control"
							placeholder="Search..."> <a class="search-icon" type="submit"></a>
					</div>
				</form>
			</div>
		</div>
	</div>
	</nav>
</body>
</html>
