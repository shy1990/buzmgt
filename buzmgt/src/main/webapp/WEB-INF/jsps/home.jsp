<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>业务管理</title>
<link rel="stylesheet"
	href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

</head>
<style>/*
 * Base structure
 */

/* Move down content because we have a fixed navbar that is 50px tall */
body {
	padding-top: 50px;
}

/*
 * Global add-ons
 */
.sub-header {
	padding-bottom: 10px;
	border-bottom: 1px solid #eee;
}

/*
 * Top navigation
 * Hide default border to remove 1px line.
 */
.navbar-fixed-top {
	border: 0;
}

/*
 * Sidebar
 */

/* Hide for mobile, show later */
.sidebar {
	display: none;
}

@media ( min-width : 768px) {
	.sidebar {
		position: fixed;
		top: 51px;
		bottom: 0;
		left: 0;
		z-index: 1000;
		display: block;
		padding: 20px;
		overflow-x: hidden;
		overflow-y: auto;
		/* Scrollable contents if viewport is shorter than content. */
		background-color: #f5f5f5;
		border-right: 1px solid #eee;
	}
}

/* Sidebar navigation */
.nav-sidebar {
	margin-right: -21px; /* 20px padding + 1px border */
	margin-bottom: 20px;
	margin-left: -20px;
}

.nav-sidebar>li>a {
	padding-right: 20px;
	padding-left: 20px;
}

.nav-sidebar>.active>a, .nav-sidebar>.active>a:hover, .nav-sidebar>.active>a:focus
	{
	color: #fff;
	background-color: #428bca;
}

/*
 * Main content
 */
.main {
	padding: 20px;
}

@media ( min-width : 768px) {
	.main {
		padding-right: 40px;
		padding-left: 40px;
	}
}

.main .page-header {
	margin-top: 0;
}

/*
 * Placeholder dashboard ideas
 */
.placeholders {
	margin-bottom: 30px;
	text-align: center;
}

.placeholders h4 {
	margin-bottom: 0;
}

.placeholder {
	margin-bottom: 20px;
}

.placeholder img {
	display: inline-block;
	border-radius: 50%;
}
</style>
<body>
	<%-- <div id="content">
		<h1>hello</h1>
	</div>
	<h1>
		<shiro:principal property="username" />
		:你好。<a href="logout">注销</a>
		<shiro:hasRole name="admin">超级管理员</shiro:hasRole>
	</h1>
	<ul id="menu">
		<li><a href="test">这是一个测试菜单</a></li>
		<li><a href="/">aaaa</a></li>
	</ul> --%>

	<!-- <div id="main"></div> -->
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Project name</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">Dashboard</a></li>
					<li><a href="#">Settings</a></li>
					<li><a href="#">Profile</a></li>
					<li><a href="#">Help</a></li>
				</ul>
				<form class="navbar-form navbar-right">
					<input type="text" class="form-control" placeholder="Search...">
				</form>
			</div>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul id="menu" class="nav nav-sidebar">
					<li class="active"><a href="test">测试啊亲 <span
							class="sr-only">(current)</span></a></li>
					<li><a href="test">测试啊亲</a></li>
					<li><a href="test">测试啊亲</a></li>
					<li><a href="test">测试啊亲</a></li>
					<li><a href="test">测试啊亲</a></li>
				</ul>
			</div>
			<div id="main">
			 
				<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
					<h1 class="page-header">Dashboard</h1>

					<div class="row placeholders">
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/vine"
								class="img-responsive" alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/sky" class="img-responsive"
								alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
						<div class="col-xs-6 col-sm-3 placeholder">
							<img data-src="holder.js/200x200/auto/vine"
								class="img-responsive" alt="Generic placeholder thumbnail">
							<h4>Label</h4>
							<span class="text-muted">Something else</span>
						</div>
					</div>

					<h2 class="sub-header">Section title</h2>
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>#</th>
									<th>Header</th>
									<th>Header</th>
									<th>Header</th>
									<th>Header</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>1,001</td>
									<td>Lorem</td>
									<td>ipsum</td>
									<td>dolor</td>
									<td>sit</td>
								</tr>
								<tr>
									<td>1,002</td>
									<td>amet</td>
									<td>consectetur</td>
									<td>adipiscing</td>
									<td>elit</td>
								</tr>
								<tr>
									<td>1,003</td>
									<td>Integer</td>
									<td>nec</td>
									<td>odio</td>
									<td>Praesent</td>
								</tr>
								<tr>
									<td>1,003</td>
									<td>libero</td>
									<td>Sed</td>
									<td>cursus</td>
									<td>ante</td>
								</tr>
								<tr>
									<td>1,004</td>
									<td>dapibus</td>
									<td>diam</td>
									<td>Sed</td>
									<td>nisi</td>
								</tr>
								<tr>
									<td>1,005</td>
									<td>Nulla</td>
									<td>quis</td>
									<td>sem</td>
									<td>at</td>
								</tr>
								<tr>
									<td>1,006</td>
									<td>nibh</td>
									<td>elementum</td>
									<td>imperdiet</td>
									<td>Duis</td>
								</tr>
								<tr>
									<td>1,007</td>
									<td>sagittis</td>
									<td>ipsum</td>
									<td>Praesent</td>
									<td>mauris</td>
								</tr>
								<tr>
									<td>1,008</td>
									<td>Fusce</td>
									<td>nec</td>
									<td>tellus</td>
									<td>sed</td>
								</tr>
								<tr>
									<td>1,009</td>
									<td>augue</td>
									<td>semper</td>
									<td>porta</td>
									<td>Mauris</td>
								</tr>
								<tr>
									<td>1,010</td>
									<td>massa</td>
									<td>Vestibulum</td>
									<td>lacinia</td>
									<td>arcu</td>
								</tr>
								<tr>
									<td>1,011</td>
									<td>eget</td>
									<td>nulla</td>
									<td>Class</td>
									<td>aptent</td>
								</tr>
								<tr>
									<td>1,012</td>
									<td>taciti</td>
									<td>sociosqu</td>
									<td>ad</td>
									<td>litora</td>
								</tr>
								<tr>
									<td>1,013</td>
									<td>torquent</td>
									<td>per</td>
									<td>conubia</td>
									<td>nostra</td>
								</tr>
								<tr>
									<td>1,014</td>
									<td>per</td>
									<td>inceptos</td>
									<td>himenaeos</td>
									<td>Curabitur</td>
								</tr>
								<tr>
									<td>1,015</td>
									<td>sodales</td>
									<td>ligula</td>
									<td>in</td>
									<td>libero</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>

			</div>
		</div>
	</div>

	<script type="text/javascript"
		src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
	<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	  <script src="http://echarts.baidu.com/build/dist/echarts-all.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#menu>li>a").click(function(event) {
				event.preventDefault();
				$("#main").load($(this).attr("href") + " #main", {
					test : "sssssssssssssss"
				},function() {
					  $.getScript("js/ditu.js");
					  $.getScript("js/ditu2.js");
				});
			});

		});
		
		
	</script>
</body>
</html>