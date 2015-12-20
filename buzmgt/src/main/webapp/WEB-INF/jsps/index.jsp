<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>主页</title>
		<!-- Bootstrap -->
		<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="static/css/index.css" />
		<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>

		<body>
			<nav class="navbar navbar-inverse navbar-fixed-top">
				<div class="container-fluid">
					<div class="row">
						<div class="navbar-header col-sm-3 col-md-2">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand logo " href="#"><img src="static/img/logo.png" /></a>
						</div>
						<div id="navbar" class="navbar-collapse collapse ">
							<div class="pull-left msg-alert">
								<p>
									<span class="msg-head"><i class="msg-alert-icon"></i>消息提醒</span>
									<span class="msg-content">李雅静申请山东省滨州市邹平县扫街审核</span>
								</p>
							</div>
							<ul class="nav navbar-nav navbar-right">
								<li role="presentation" class="dropdown">
									<a class="msg-icon-box" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
										<i class="msg-icon"></i>
										<span class="mark">4</span>
									</a>
									<ul class="dropdown-menu msg" aria-labelledby="dropdownMenu1">
										<li><a><i class="msg-count-icon"></i>四条消息提醒</a></li>
										<li><a href=""><i></i>曾志伟申请扫街审核<span class="pull-right">2015.12.19</span></a></li>
										<li><a href=""><i></i>退出登录</a></li>
									</ul>
								</li>
								<li role="presentation" class="dropdown">
									<a class="time-icon-box">
										<i class="time-icon"></i>
										<span class="mark-red">6</span>
									</a>
									<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
										<li><a href="">账户设置</a></li>
										<li><a href="">退出登录</a></li>
									</ul>
								</li>
								<li role="presentation" class="dropdown marg-r-40">
									<a class="user-icon-box" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
     							我叫李小龙<span class="caret"></span>
   								 </a>
									<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
										<li><a class="user upd-icon" href="">账户设置</a></li>
										<li><a class="user logout-icon" href="">退出登录</a></li>
									</ul>
								</li>
							</ul>
							<form class="navbar-form navbar-right">
								<div class="search-box">
									<input class="search-input" type="text" class="form-control" placeholder="Search...">
									<i class="search-icon"></i>
								</div>
							</form>
						</div>
					</div>
				</div>
			</nav>

			<div class="container-fluid">
				<div id="" class="row">
					<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
						<ul class="nav nav-sidebar menu">
							<li class="active"><a class="console" href="#">控制台 <span class="sr-only"></span></a></li>
							<li class="" role="presentation">
								<!--二级菜单添加menu-second-box-->
								<a class="menu-second-box management" href="" data-toggle="dropdown">业务管理<span class="pull-right down-icon"></span></a>
								<ul class="menu-second">
									<li class="active"><a class="team" href="test">团队成员</a></li>
									<li><a class="street-settings">扫街设置</a></li>
									<li><a href="" class="client-exploit">客户开发</a></li>
									<li><a href="" class="yw-examine">业务考核</a></li>
									<li><a href="" class="mission">任务</a></li>
								</ul>
							</li>
							<li class="">
								<a href="" class="menu-second-box cli-manage" data-toggle="dropdown">客户管理<span class="pull-right down-icon"></span></a>
								<ul class="menu-second">
									<li class="active"><a href="team">商家管理</a></li>
									<li><a href="">活跃度</a></li>
									<li><a href="">沉积客户</a></li>
									<li><a href="">二次提货</a></li>
									<li><a href="">关联查询</a></li>
								</ul>
							</li>
							<li><a href="" class="product-sales">产品销量</a></li>
							<li><a href="" class="inventory">库存</a></li>
							<li><a href="" class="active-note">活动通知</a></li>
							<li><a href="" class="statistics">统计</a></li>
							<li>
								<a href="#" class="menu-second-box basic-settings" data-toggle="dropdown">基础设置<span class="pull-right down-icon"></span></a>
								<ul class="menu-second">
									<li class="active"><a href="team">区域划分</a></li>
									<li><a href="">组织结构</a></li>
									<li><a href="">权限设置</a></li>
									<li><a href="">装好管理</a></li>
								</ul>
							</li>
						</ul>
					</div>
					<div id="main" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
						<h1 class="page-header">Dashboard</h1>
						<div class="row placeholders">
							<div class="col-xs-6 col-sm-3 placeholder">
								<img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
								<h4>Label</h4>
								<span class="text-muted">Something else</span>
							</div>
							<div class="col-xs-6 col-sm-3 placeholder">
								<img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
								<h4>Label</h4>
								<span class="text-muted">Something else</span>
							</div>
							<div class="col-xs-6 col-sm-3 placeholder">
								<img data-src="holder.js/200x200/auto/sky" class="img-responsive" alt="Generic placeholder thumbnail">
								<h4>Label</h4>
								<span class="text-muted">Something else</span>
							</div>
							<div class="col-xs-6 col-sm-3 placeholder">
								<img data-src="holder.js/200x200/auto/vine" class="img-responsive" alt="Generic placeholder thumbnail">
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
			<script src="http://echarts.baidu.com/build/dist/echarts-all.js"></script>
			<script src="static/js/index.js" type="text/javascript" charset="utf-8"></script>
		</body>

</html>