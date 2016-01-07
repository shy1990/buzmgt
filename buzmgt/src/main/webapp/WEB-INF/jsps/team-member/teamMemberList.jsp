<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>团队成员列表</title>
		<!-- Bootstrap -->
		<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="static/yw-team-member/team-member.css" />
		<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<div class="content main">
			<h4 class="team-member-header page-header ">
				<div class="row">
					<div class="col-sm-4 col-md-3 ">
						<i class="icon team-member-list-icon"></i>团队成员
						<!--区域选择按钮-->
						<div class="btn-group">
						  <button type="button" class="btn btn-default "><i class="icon province-icon"></i>山东省</button>
						  <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						    <span class="caret"></span>
						    <span class="sr-only">Toggle Dropdown</span>
						  </button>
						  <ul class="dropdown-menu">
						    <li><a href="#">Action</a></li>
						    <li><a href="#">Another action</a></li>
						    <li><a href="#">Something else here</a></li>
						    <li role="separator" class="divider"></li>
						    <li><a href="#">Separated link</a></li>
						  </ul>
						</div>
						<!--/区域选择按钮-->
					</div>
					<div class="col-sm-6">
						<button class="btn btn-warning member-add-btn" type="button"> <i class="icon icon-add"></i>添加成员</button>
						<small class="header-text">共<span class="text-red">203</span>位成员</small>
						<small class="header-text">今日新增<span class="text-red"> 0 +</span></small>
					</div>
				</div>
			</h4>
			<div class="row">
		    	<div class="col-md-12">
		    		<!--box-->
		    		<div class="team-member-body box border red">
		    			<!--title-->
						<div class="box-title">
							<div class="row">
								<div class="col-sm-7 col-md-5">
									<!--菜单栏-->
									<ul class="nav nav-tabs">
										<li class="active"><a href="#box_tab1" data-toggle="tab"><i class="fa fa-circle-o"></i> <span class="hidden-inline-mobile">扫街中</span></a></li>
										<li><a href="#box_tab2" data-toggle="tab"><i class="fa fa-laptop"></i> <span class="hidden-inline-mobile">考核中</span></a></li>
										<li><a href="#box_tab3" data-toggle="tab"><i class="fa fa-calendar-o"></i> <span class="hidden-inline-mobile">开发中</span></a></li>
										<li><a href="#box_tab4" data-toggle="tab"><i class="fa fa-calendar-o"></i> <span class="hidden-inline-mobile">已转正</span></a></li>
									</ul>
									<!--/菜单栏-->
								</div>
								<div class="col-sm-4 col-md-3 col-lg-2 col-md-offset-4 col-lg-offset-5">
									<div class="form-group title-form">
										<div class="input-group ">
											<input type="text" class="form-control" placeholder="请输入名称或工号">
											<span class="input-group-addon"><i class="icon icon-finds"></i></span>
										</div>
								    </div>
								</div>
							</div>
							<!--<div class="title-form input-group ">
								<input class="form-control input-sm" type="text" name="" id="" value="" />
								<span class=""><i class="icon icon-finds"></i></span>
							</div>-->
							<!--from-->
							<!--<h4 style="text-align: right;"><i class="fa fa-columns"></i><span class="hidden-inline-mobile">Tabs on Color Header</span></h4>-->
						</div>
						<!--title-->
						<!--box-body-->
						<div class="box-body">
								<!--列表内容-->
								<div class="tab-content">
									<!--扫街中-->
									<div class="tab-pane fade in active" id="box_tab1">
										<!--box-list-->
										<div class="box-list">
											<ul>
												<li></li>
											</ul>
										</div>
										<!--/box-list-->
									</div>
									<!--考核中-->
									<div class="tab-pane fade" id="box_tab2">
										<p>Content #2</p>
										<p> There were flying cantaloupes, rainbows and songs of happiness near by, I mean I was a little frightened by the flying fruit but I'll take this any day over prison inmates. I skipped closer and closer to the festivities and when I arrived I seen all my friends I had went to high school with there were holding hands and singing Kumbayah around the camp ice.. Yes It was a giant block of ice situated on three wood logs. </p>
									</div>
									<!--开发中-->
									<div class="tab-pane fade" id="box_tab3">
										<div class="alert alert-info"><strong>Hello!</strong> I'm a cool tabbed box.</div>
										<p>Content #3</p>
										<p> The onion ring senses that the caper of your caper is swiped the fox.  </p>
									</div>
									<!--已转正-->
									<div class="tab-pane fade" id="box_tab4">
										<div class="alert alert-info"><strong>Hello!</strong> I'm a cool tabbed box.</div>
										<p>Content #3</p>
										<p> The onion ring senses that the caper of your caper is swiped the fox.  </p>
									</div>
								</div>
								<!--/列表内容-->
						</div>
						<!--/box-body-->
					</div>
					<!--/box-->
		    	</div>
		    </div>
		</div>
		<!-- Bootstrap core JavaScript================================================== -->
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
			<script src="static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>
	</body>
</html>
