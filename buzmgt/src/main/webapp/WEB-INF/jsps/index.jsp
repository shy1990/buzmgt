<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-cn">

	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>业务管理系统</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no">
		<link rel="stylesheet" type="text/css" href="static/css/cloud-admin.min.css">
		<link rel="stylesheet" type="text/css" href="static/css/themes/default.css" id="skin-switcher">
		<link rel="stylesheet" type="text/css" href="static/css/responsive.css">
		<link href="static/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="static/css/animatecss/animate.min.css" />
		<link rel="stylesheet" type="text/css" href="static/js/bootstrap-daterangepicker/daterangepicker-bs3.css" />
		<link rel="stylesheet" type="text/css" href="static/js/jquery-todo/css/styles.css" />
		<link rel="stylesheet" type="text/css" href="static/js/fullcalendar/fullcalendar.min.css" />
		<link rel="stylesheet" type="text/css" href="static/js/gritter/css/jquery.gritter.css" />
		<link rel="stylesheet" type="text/css" href="static/css/index.css" />
	</head>

	<body>
		<!-- HEADER -->
		<header class="navbar clearfix" id="header">
			<div class="container">
				<div class="navbar-brand">
					<!-- COMPANY LOGO -->
					<a href="index.html">
						<img src="static/img/logo/logo.png" alt="Cloud Admin Logo" class="img-responsive" height="30" width="120">
					</a>
					<!-- /COMPANY LOGO -->
					<!-- TEAM STATUS FOR MOBILE -->
					<div class="visible-xs">
						<a href="#" class="team-status-toggle switcher btn dropdown-toggle">
							<i class="fa fa-users"></i>
						</a>
					</div>
					<!-- /TEAM STATUS FOR MOBILE -->
					<!-- SIDEBAR COLLAPSE -->
					<div id="sidebar-collapse" class="sidebar-collapse btn">
						<i class="fa fa-bars" data-icon1="fa fa-bars" data-icon2="fa fa-bars"></i>
					</div>
					<!-- /SIDEBAR COLLAPSE -->
				</div>
				<!-- NAVBAR LEFT -->
				<ul class="nav navbar-nav pull-left hidden-xs" id="navbar-left">
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<i class="fa fa-cog"></i>
							<span class="name">皮肤</span>
							<i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu skins">
							<li class="dropdown-title">
								<span><i class="fa fa-leaf"></i> 皮肤主题</span>
							</li>
							<li><a href="#" data-skin="default">Subtle (default)</a></li>
							<li><a href="#" data-skin="night">Night</a></li>
							<li><a href="#" data-skin="earth">Earth</a></li>
							<li><a href="#" data-skin="utopia">Utopia</a></li>
							<li><a href="#" data-skin="nature">Nature</a></li>
							<li><a href="#" data-skin="graphite">Graphite</a></li>
						</ul>
					</li>
				</ul>
				<!-- /NAVBAR LEFT -->
				<!-- BEGIN TOP NAVIGATION MENU -->
				<ul class="nav navbar-nav pull-right">
					<!-- search -->
					<li class="li-search">
						<div id="search-bar">
							<input class="search" type="text" placeholder="Search"><i class="fa fa-search search-icon"></i>
						</div>
					</li>

					<!-- BEGIN NOTIFICATION DROPDOWN -->
					<li class="dropdown" id="header-notification">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<i class="fa fa-bell"></i>
							<span class="badge">7</span>
						</a>
						<ul class="dropdown-menu notification">
							<li class="dropdown-title">
								<span><i class="fa fa-bell"></i> 7 Notifications</span>
							</li>
							<li>
								<a href="#">
									<span class="label label-success"><i class="fa fa-user"></i></span>
									<span class="body">
										<span class="message">5 users online. </span>
									<span class="time">
											<i class="fa fa-clock-o"></i>
											<span>Just now</span>
									</span>
									</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="label label-primary"><i class="fa fa-comment"></i></span>
									<span class="body">
										<span class="message">Martin commented.</span>
									<span class="time">
											<i class="fa fa-clock-o"></i>
											<span>19 mins</span>
									</span>
									</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="label label-warning"><i class="fa fa-lock"></i></span>
									<span class="body">
										<span class="message">DW1 server locked.</span>
									<span class="time">
											<i class="fa fa-clock-o"></i>
											<span>32 mins</span>
									</span>
									</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="label label-info"><i class="fa fa-twitter"></i></span>
									<span class="body">
										<span class="message">Twitter connected.</span>
									<span class="time">
											<i class="fa fa-clock-o"></i>
											<span>55 mins</span>
									</span>
									</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="label label-danger"><i class="fa fa-heart"></i></span>
									<span class="body">
										<span class="message">Jane liked. </span>
									<span class="time">
											<i class="fa fa-clock-o"></i>
											<span>2 hrs</span>
									</span>
									</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="label label-warning"><i class="fa fa-exclamation-triangle"></i></span>
									<span class="body">
										<span class="message">Database overload.</span>
									<span class="time">
											<i class="fa fa-clock-o"></i>
											<span>6 hrs</span>
									</span>
									</span>
								</a>
							</li>
							<li class="footer">
								<a href="#">See all notifications <i class="fa fa-arrow-circle-right"></i></a>
							</li>
						</ul>
					</li>
					<!-- END NOTIFICATION DROPDOWN -->
					<!-- BEGIN INBOX DROPDOWN -->
					<li class="dropdown" id="header-message">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<i class="fa fa-envelope"></i>
							<span class="badge">3</span>
						</a>
						<ul class="dropdown-menu inbox">
							<li class="dropdown-title">
								<span><i class="fa fa-envelope-o"></i> 3 Messages</span>
								<span class="compose pull-right tip-right" title="Compose message"><i class="fa fa-pencil-square-o"></i></span>
							</li>
							<li>
								<a href="#">
									<img src="static/img/avatars/avatar2.jpg" alt="" />
									<span class="body">
										<span class="from">Jane Doe</span>
									<span class="message">
										Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse mole ...
										</span>
									<span class="time">
											<i class="fa fa-clock-o"></i>
											<span>Just Now</span>
									</span>
									</span>

								</a>
							</li>
							<li>
								<a href="#">
									<img src="static/img/avatars/avatar1.jpg" alt="" />
									<span class="body">
										<span class="from">Vince Pelt</span>
									<span class="message">
										Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse mole ...
										</span>
									<span class="time">
											<i class="fa fa-clock-o"></i>
											<span>15 min ago</span>
									</span>
									</span>

								</a>
							</li>
							<li>
								<a href="#">
									<img src="static/img/avatars/avatar8.jpg" alt="" />
									<span class="body">
										<span class="from">Debby Doe</span>
									<span class="message">
										Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse mole ...
										</span>
									<span class="time">
											<i class="fa fa-clock-o"></i>
											<span>2 hours ago</span>
									</span>
									</span>

								</a>
							</li>
							<li class="footer">
								<a href="#">See all messages <i class="fa fa-arrow-circle-right"></i></a>
							</li>
						</ul>
					</li>
					<!-- END INBOX DROPDOWN -->
					<!-- BEGIN TODO DROPDOWN -->
					<li class="dropdown" id="header-tasks">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<i class="fa fa-tasks"></i>
							<span class="badge">3</span>
						</a>
						<ul class="dropdown-menu tasks">
							<li class="dropdown-title">
								<span><i class="fa fa-check"></i> 6 tasks in progress</span>
							</li>
							<li>
								<a href="#">
									<span class="header clearfix">
										<span class="pull-left">Software Update</span>
									<span class="pull-right">60%</span>
									</span>
									<div class="progress">
										<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
											<span class="sr-only">60% Complete</span>
										</div>
									</div>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="header clearfix">
										<span class="pull-left">Software Update</span>
									<span class="pull-right">25%</span>
									</span>
									<div class="progress">
										<div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100" style="width: 25%;">
											<span class="sr-only">25% Complete</span>
										</div>
									</div>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="header clearfix">
										<span class="pull-left">Software Update</span>
									<span class="pull-right">40%</span>
									</span>
									<div class="progress progress-striped">
										<div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%;">
											<span class="sr-only">40% Complete</span>
										</div>
									</div>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="header clearfix">
										<span class="pull-left">Software Update</span>
									<span class="pull-right">70%</span>
									</span>
									<div class="progress progress-striped active">
										<div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="70" aria-valuemin="0" aria-valuemax="100" style="width: 70%;">
											<span class="sr-only">70% Complete</span>
										</div>
									</div>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="header clearfix">
										<span class="pull-left">Software Update</span>
									<span class="pull-right">65%</span>
									</span>
									<div class="progress">
										<div class="progress-bar progress-bar-success" style="width: 35%">
											<span class="sr-only">35% Complete (success)</span>
										</div>
										<div class="progress-bar progress-bar-warning" style="width: 20%">
											<span class="sr-only">20% Complete (warning)</span>
										</div>
										<div class="progress-bar progress-bar-danger" style="width: 10%">
											<span class="sr-only">10% Complete (danger)</span>
										</div>
									</div>
								</a>
							</li>
							<li class="footer">
								<a href="#">See all tasks <i class="fa fa-arrow-circle-right"></i></a>
							</li>
						</ul>
					</li>
					<!-- END TODO DROPDOWN -->
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown user" id="header-user">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
							<img alt="" src="static/img/avatars/avatar3.jpg" />
							<span class="username">我是张三</span>
							<i class="fa fa-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li><a href="#"><i class="fa fa-user"></i> My Profile</a></li>
							<li><a href="#"><i class="fa fa-cog"></i> Account Settings</a></li>
							<li><a href="#"><i class="fa fa-eye"></i> Privacy Settings</a></li>
							<li><a href="login.html"><i class="fa fa-power-off"></i> Log Out</a></li>
						</ul>
					</li>
					<!-- END USER LOGIN DROPDOWN -->
				</ul>
				<!-- END TOP NAVIGATION MENU -->
			</div>
			<!-- TEAM STATUS -->
			<div class="container team-status" id="team-status">
				<div id="scrollbar">
					<div class="handle">
					</div>
				</div>
				<div id="teamslider">
					<ul class="team-list">
						<li class="current">
							<a href="javascript:void(0);">
								<span class="image">
					  <img src="img/avatars/avatar3.jpg" alt="" />
				  </span>
								<span class="title">
					You
				  </span>
								<div class="progress">
									<div class="progress-bar progress-bar-success" style="width: 35%">
										<span class="sr-only">35% Complete (success)</span>
									</div>
									<div class="progress-bar progress-bar-warning" style="width: 20%">
										<span class="sr-only">20% Complete (warning)</span>
									</div>
									<div class="progress-bar progress-bar-danger" style="width: 10%">
										<span class="sr-only">10% Complete (danger)</span>
									</div>
								</div>
								<span class="status">
						<div class="field">
							<span class="badge badge-green">6</span> completed
								<span class="pull-right fa fa-check"></span>
				</div>
				<div class="field">
					<span class="badge badge-orange">3</span> in-progress
					<span class="pull-right fa fa-adjust"></span>
				</div>
				<div class="field">
					<span class="badge badge-red">1</span> pending
					<span class="pull-right fa fa-list-ul"></span>
				</div>
				</span>
				</a>
				</li>
				<li>
					<a href="javascript:void(0);">
						<span class="image">
					  <img src="static/img/avatars/avatar1.jpg" alt="" />
				  </span>
						<span class="title">
					Max Doe
				  </span>
						<div class="progress">
							<div class="progress-bar progress-bar-success" style="width: 15%">
								<span class="sr-only">35% Complete (success)</span>
							</div>
							<div class="progress-bar progress-bar-warning" style="width: 40%">
								<span class="sr-only">20% Complete (warning)</span>
							</div>
							<div class="progress-bar progress-bar-danger" style="width: 20%">
								<span class="sr-only">10% Complete (danger)</span>
							</div>
						</div>
						<span class="status">
						<div class="field">
							<span class="badge badge-green">2</span> completed
						<span class="pull-right fa fa-check"></span>
			</div>
			<div class="field">
				<span class="badge badge-orange">8</span> in-progress
				<span class="pull-right fa fa-adjust"></span>
			</div>
			<div class="field">
				<span class="badge badge-red">4</span> pending
				<span class="pull-right fa fa-list-ul"></span>
			</div>
			</span>
			</a>
			</li>
			<li>
				<a href="javascript:void(0);">
					<span class="image">
					  <img src="static/img/avatars/avatar2.jpg" alt="" />
				  </span>
					<span class="title">
					Jane Doe
				  </span>
					<div class="progress">
						<div class="progress-bar progress-bar-success" style="width: 65%">
							<span class="sr-only">35% Complete (success)</span>
						</div>
						<div class="progress-bar progress-bar-warning" style="width: 10%">
							<span class="sr-only">20% Complete (warning)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width: 15%">
							<span class="sr-only">10% Complete (danger)</span>
						</div>
					</div>
					<span class="status">
						<div class="field">
							<span class="badge badge-green">10</span> completed
					<span class="pull-right fa fa-check"></span>
					</div>
					<div class="field">
						<span class="badge badge-orange">3</span> in-progress
						<span class="pull-right fa fa-adjust"></span>
					</div>
					<div class="field">
						<span class="badge badge-red">4</span> pending
						<span class="pull-right fa fa-list-ul"></span>
					</div>
					</span>
				</a>
			</li>
			<li>
				<a href="javascript:void(0);">
					<span class="image">
					  <img src="static/img/avatars/avatar4.jpg" alt="" />
				  </span>
					<span class="title">
					Ellie Doe
				  </span>
					<div class="progress">
						<div class="progress-bar progress-bar-success" style="width: 5%">
							<span class="sr-only">35% Complete (success)</span>
						</div>
						<div class="progress-bar progress-bar-warning" style="width: 48%">
							<span class="sr-only">20% Complete (warning)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width: 27%">
							<span class="sr-only">10% Complete (danger)</span>
						</div>
					</div>
					<span class="status">
						<div class="field">
							<span class="badge badge-green">1</span> completed
					<span class="pull-right fa fa-check"></span>
					</div>
					<div class="field">
						<span class="badge badge-orange">6</span> in-progress
						<span class="pull-right fa fa-adjust"></span>
					</div>
					<div class="field">
						<span class="badge badge-red">2</span> pending
						<span class="pull-right fa fa-list-ul"></span>
					</div>
					</span>
				</a>
			</li>
			<li>
				<a href="javascript:void(0);">
					<span class="image">
					  <img src="static/img/avatars/avatar5.jpg" alt="" />
				  </span>
					<span class="title">
					Lisa Doe
				  </span>
					<div class="progress">
						<div class="progress-bar progress-bar-success" style="width: 21%">
							<span class="sr-only">35% Complete (success)</span>
						</div>
						<div class="progress-bar progress-bar-warning" style="width: 20%">
							<span class="sr-only">20% Complete (warning)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width: 40%">
							<span class="sr-only">10% Complete (danger)</span>
						</div>
					</div>
					<span class="status">
						<div class="field">
							<span class="badge badge-green">4</span> completed
					<span class="pull-right fa fa-check"></span>
					</div>
					<div class="field">
						<span class="badge badge-orange">5</span> in-progress
						<span class="pull-right fa fa-adjust"></span>
					</div>
					<div class="field">
						<span class="badge badge-red">9</span> pending
						<span class="pull-right fa fa-list-ul"></span>
					</div>
					</span>
				</a>
			</li>
			<li>
				<a href="javascript:void(0);">
					<span class="image">
					  <img src="static/img/avatars/avatar6.jpg" alt="" />
				  </span>
					<span class="title">
					Kelly Doe
				  </span>
					<div class="progress">
						<div class="progress-bar progress-bar-success" style="width: 45%">
							<span class="sr-only">35% Complete (success)</span>
						</div>
						<div class="progress-bar progress-bar-warning" style="width: 21%">
							<span class="sr-only">20% Complete (warning)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width: 10%">
							<span class="sr-only">10% Complete (danger)</span>
						</div>
					</div>
					<span class="status">
						<div class="field">
							<span class="badge badge-green">6</span> completed
					<span class="pull-right fa fa-check"></span>
					</div>
					<div class="field">
						<span class="badge badge-orange">3</span> in-progress
						<span class="pull-right fa fa-adjust"></span>
					</div>
					<div class="field">
						<span class="badge badge-red">1</span> pending
						<span class="pull-right fa fa-list-ul"></span>
					</div>
					</span>
				</a>
			</li>
			<li>
				<a href="javascript:void(0);">
					<span class="image">
					  <img src="static/img/avatars/avatar7.jpg" alt="" />
				  </span>
					<span class="title">
					Jessy Doe
				  </span>
					<div class="progress">
						<div class="progress-bar progress-bar-success" style="width: 7%">
							<span class="sr-only">35% Complete (success)</span>
						</div>
						<div class="progress-bar progress-bar-warning" style="width: 30%">
							<span class="sr-only">20% Complete (warning)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width: 10%">
							<span class="sr-only">10% Complete (danger)</span>
						</div>
					</div>
					<span class="status">
						<div class="field">
							<span class="badge badge-green">1</span> completed
					<span class="pull-right fa fa-check"></span>
					</div>
					<div class="field">
						<span class="badge badge-orange">6</span> in-progress
						<span class="pull-right fa fa-adjust"></span>
					</div>
					<div class="field">
						<span class="badge badge-red">2</span> pending
						<span class="pull-right fa fa-list-ul"></span>
					</div>
					</span>
				</a>
			</li>
			<li>
				<a href="javascript:void(0);">
					<span class="image">
					  <img src="static/img/avatars/avatar8.jpg" alt="" />
				  </span>
					<span class="title">
					Debby Doe
				  </span>
					<div class="progress">
						<div class="progress-bar progress-bar-success" style="width: 70%">
							<span class="sr-only">35% Complete (success)</span>
						</div>
						<div class="progress-bar progress-bar-warning" style="width: 20%">
							<span class="sr-only">20% Complete (warning)</span>
						</div>
						<div class="progress-bar progress-bar-danger" style="width: 5%">
							<span class="sr-only">10% Complete (danger)</span>
						</div>
					</div>
					<span class="status">
						<div class="field">
							<span class="badge badge-green">13</span> completed
					<span class="pull-right fa fa-check"></span>
					</div>
					<div class="field">
						<span class="badge badge-orange">7</span> in-progress
						<span class="pull-right fa fa-adjust"></span>
					</div>
					<div class="field">
						<span class="badge badge-red">1</span> pending
						<span class="pull-right fa fa-list-ul"></span>
					</div>
					</span>
				</a>
			</li>
			</ul>
			</div>
			</div>
			<!-- /TEAM STATUS -->
		</header>
		<!--/HEADER -->
		<!-- PAGE -->
		<section id="page">
			<!-- SIDEBAR -->
				<div id="sidebar" class="sidebar">
				<div class="sidebar-menu nav-collapse">
					<!-- SIDEBAR MENU -->
					<ul>
						<li class="active">
							<a href="index.html">
								<i class="fa fa-tachometer fa-fw"></i> <span class="menu-text">控制台</span>
								<span class="selected"></span>
							</a>
						</li>
						<li class="has-sub">
							<a href="javascript:;" class="">
								<i class="fa fa-bookmark-o fa-fw"></i> <span class="menu-text">业务管理</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="elements.html"><span class="sub-menu-text">团队成员</span></a></li>
								<li><a class="" href="notifications.html"><span class="sub-menu-text">扫街设置</span></a></li>
								<li><a class="" href="buttons_icons.html"><span class="sub-menu-text">客户开发</span></a></li>
								<li><a class="" href="sliders_progress.html"><span class="sub-menu-text">业务考核</span></a></li>
								<li><a class="" href="typography.html"><span class="sub-menu-text">任务</span></a></li>
							</ul>
						</li>
						<li><a class="" href="region/initRegion"><i class="fa fa-desktop fa-fw"></i> <span class="menu-text">区域划分</span></a></li>
						
						
						
						
						<li><a class="" href="inbox.html"><i class="fa fa-envelope-o fa-fw"></i> <span class="menu-text">Inbox</span></a></li>
						<li class="has-sub">
							<a href="javascript:;" class="">
								<i class="fa fa-table fa-fw"></i> <span class="menu-text">Tables</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="simple_table.html"><span class="sub-menu-text">Simple Tables</span></a></li>
								<li><a class="" href="dynamic_tables.html"><span class="sub-menu-text">Dynamic Tables</span></a></li>
								<li><a class="" href="jqgrid_plugin.html"><span class="sub-menu-text">jqGrid Plugin</span></a></li>
							</ul>
						</li>
						<li class="has-sub">
							<a href="javascript:;" class="">
								<i class="fa fa-pencil-square-o fa-fw"></i> <span class="menu-text">Form Elements</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="forms.html"><span class="sub-menu-text">Forms</span></a></li>
								<li><a class="" href="wizards_validations.html"><span class="sub-menu-text">Wizards & Validations</span></a></li>
								<li><a class="" href="rich_text_editors.html"><span class="sub-menu-text">Rich Text Editors</span></a></li>

								<li><a class="" href="dropzone_file_upload.html"><span class="sub-menu-text">Dropzone File Upload</span></a></li>
							</ul>
						</li>
						<li><a class="" href="widgets_box.html"><i class="fa fa-th-large fa-fw"></i> <span class="menu-text">Widgets & Box</span></a></li>
						<li class="has-sub">
							<a href="javascript:;" class="">
								<i class="fa fa-bar-chart-o fa-fw"></i> <span class="menu-text">Visual Charts</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="flot_charts.html"><span class="sub-menu-text">Flot Charts</span></a></li>
								<li><a class="" href="xcharts.html"><span class="sub-menu-text">xCharts</span></a></li>

								<li><a class="" href="others.html"><span class="sub-menu-text">Others</span></a></li>
							</ul>
						</li>
						<li class="has-sub">
							<a href="javascript:;" class="">
								<i class="fa fa-columns fa-fw"></i> <span class="menu-text">Layouts</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="mini_sidebar.html"><span class="sub-menu-text">Mini Sidebar</span></a></li>
								<li><a class="" href="fixed_header.html"><span class="sub-menu-text">Fixed Header</span></a></li>

								<li><a class="" href="fixed_header_sidebar.html"><span class="sub-menu-text">Fixed Header & Sidebar</span></a></li>
							</ul>
						</li>
						<li><a class="" href="calendar.html"><i class="fa fa-calendar fa-fw"></i> 
								<span class="menu-text">Calendar 
									<span class="tooltip-error pull-right" title="" data-original-title="3 New Events">
										<span class="label label-success">New</span>
									</span>
								</span>
								</a>
						</li>
						<li class="has-sub">
							<a href="javascript:;" class="">
								<i class="fa fa-map-marker fa-fw"></i> <span class="menu-text">Maps</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="google_maps.html"><span class="sub-menu-text">Google Maps</span></a></li>
								<li><a class="" href="vector_maps.html"><span class="sub-menu-text">Vector Maps</span></a></li>
							</ul>
						</li>
						<li><a class="" href="gallery.html"><i class="fa fa-picture-o fa-fw"></i> <span class="menu-text">Gallery</span></a></li>
						<li class="has-sub">
							<a href="javascript:;" class="">
								<i class="fa fa-file-text fa-fw"></i> <span class="menu-text">More Pages</span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="login.html"><span class="sub-menu-text">Login & Register Option 1</span></a></li>
								<li><a class="" href="login_bg.html"><span class="sub-menu-text">Login & Register Option 2</span></a></li>
								<li><a class="" href="user_profile.html"><span class="sub-menu-text">User profile</span></a></li>

								<li><a class="" href="chats.html"><span class="sub-menu-text">Chats</span></a></li>
								<li><a class="" href="todo_timeline.html"><span class="sub-menu-text">Todo & Timeline</span></a></li>
								<li><a class="" href="address_book.html"><span class="sub-menu-text">Address Book</span></a></li>

								<li><a class="" href="pricing.html"><span class="sub-menu-text">Pricing</span></a></li>
								<li><a class="" href="invoice.html"><span class="sub-menu-text">Invoice</span></a></li>
								<li><a class="" href="orders.html"><span class="sub-menu-text">Orders</span></a></li>
							</ul>
						</li>
						<li class="has-sub">
							<a href="javascript:;" class="">
								<i class="fa fa-briefcase fa-fw"></i> <span class="menu-text">Other Pages <span class="badge pull-right">9</span></span>
								<span class="arrow"></span>
							</a>
							<ul class="sub">
								<li><a class="" href="search_results.html"><span class="sub-menu-text">Search Results</span></a></li>
								<li><a class="" href="email_templates.html"><span class="sub-menu-text">Email Templates</span></a></li>

								<li><a class="" href="error_404.html"><span class="sub-menu-text">Error 404 Option 1</span></a></li>
								<li><a class="" href="error_404_2.html"><span class="sub-menu-text">Error 404 Option 2</span></a></li>
								<li><a class="" href="error_404_3.html"><span class="sub-menu-text">Error 404 Option 3</span></a></li>
								<li><a class="" href="error_500.html"><span class="sub-menu-text">Error 500 Option 1</span></a></li>
								<li><a class="" href="error_500_2.html"><span class="sub-menu-text">Error 500 Option 2</span></a></li>
								<li><a class="" href="faq.html"><span class="sub-menu-text">FAQ</span></a></li>
								<li><a class="" href="blank_page.html"><span class="sub-menu-text">Blank Page</span></a></li>
							</ul>
						</li>
					</ul>
					<!-- /SIDEBAR MENU -->
				</div>
			</div>
			<!-- /SIDEBAR -->
			<div id="main-content">
				<!-- SAMPLE BOX CONFIGURATION MODAL FORM-->
				<div class="modal fade" id="box-config" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h4 class="modal-title">Box Settings</h4>
							</div>
							<div class="modal-body">
								Here goes box setting content.
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
								<button type="button" class="btn btn-primary">Save changes</button>
							</div>
						</div>
					</div>
				</div>
				<!-- /SAMPLE BOX CONFIGURATION MODAL FORM-->
				<div class="container">
					<div class="row">
						<div id="content" class="col-lg-12">
						<div></div>
						<!-- /CALENDAR & CHAT -->
						<div class="footer-tools">
							<span class="go-top">
								<i class="fa fa-chevron-up"></i> Top
							</span>
						</div>
					</div>
					<!-- /CONTENT-->
				</div>
			</div>
			</div>
		</section>
		<!--/PAGE -->
		<!-- STYLESHEETS -->
		<!--[if lt IE 9]><script src="js/flot/excanvas.min.js"></script><script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script><script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script><![endif]-->
		<!-- JAVASCRIPTS -->
		<!-- Placed at the end of the document so the pages load faster -->
		<!-- JQUERY -->
		<script src="static/js/jquery/jquery-2.0.3.min.js"></script>
		<!-- JQUERY UI-->
		<script src="static/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
		<!-- BOOTSTRAP -->
		<script src="static/bootstrap-dist/js/bootstrap.min.js"></script>

		<!-- DATE RANGE PICKER -->
		<script src="static/js/bootstrap-daterangepicker/moment.min.js"></script>

		<script src="static/js/bootstrap-daterangepicker/daterangepicker.min.js"></script>
		<!-- SLIMSCROLL -->
		<script type="text/javascript" src="static/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.min.js"></script>
		<!-- SLIMSCROLL -->
		<script type="text/javascript" src="static/js/jQuery-slimScroll-1.3.0/jquery.slimscroll.min.js"></script>
		<script type="text/javascript" src="static/js/jQuery-slimScroll-1.3.0/slimScrollHorizontal.min.js"></script>
		<!-- BLOCK UI -->
		<script type="text/javascript" src="static/js/jQuery-BlockUI/jquery.blockUI.min.js"></script>
		<!-- SPARKLINES -->
		<script type="text/javascript" src="static/js/sparklines/jquery.sparkline.min.js"></script>
		<!-- EASY PIE CHART -->
		<script src="static/js/jquery-easing/jquery.easing.min.js"></script>
		<script type="text/javascript" src="static/js/easypiechart/jquery.easypiechart.min.js"></script>
		<!-- FLOT CHARTS -->
		<script src="static/js/flot/jquery.flot.min.js"></script>
		<script src="static/js/flot/jquery.flot.time.min.js"></script>
		<script src="static/js/flot/jquery.flot.selection.min.js"></script>
		<script src="static/js/flot/jquery.flot.resize.min.js"></script>
		<script src="static/js/flot/jquery.flot.pie.min.js"></script>
		<script src="static/js/flot/jquery.flot.stack.min.js"></script>
		<script src="static/js/flot/jquery.flot.crosshair.min.js"></script>
		<!-- TODO -->
		<script type="text/javascript" src="static/js/jquery-todo/js/paddystodolist.js"></script>
		<!-- TIMEAGO -->
		<script type="text/javascript" src="static/js/timeago/jquery.timeago.min.js"></script>
		<!-- FULL CALENDAR -->
		<script type="text/javascript" src="static/js/fullcalendar/fullcalendar.min.js"></script>
		<!-- COOKIE -->
		<script type="text/javascript" src="static/js/jQuery-Cookie/jquery.cookie.min.js"></script>
		<!-- GRITTER -->
		<script type="text/javascript" src="static/js/gritter/js/jquery.gritter.min.js"></script>
		<!-- CUSTOM SCRIPT -->
		<script src="static/js/script.js"></script>
		<script>
			jQuery(document).ready(function() {		
				App.setPage("index");  //Set current page
				App.init(); //Initialise plugins and elements
			});
			
		</script>
		<script src="static/js/index.js"></script>
		
		<!-- /JAVASCRIPTS -->
	</body>

</html>