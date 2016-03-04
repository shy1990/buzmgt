<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>添加任务</title>
		<!-- Bootstrap -->
		<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="static/task/task.css" />
		<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<div class="content main task-add-page">
			<h4 class="page-header ">
				<i class="ico icon-saojie-add"></i>添加任务
				<!--区域选择按钮-->
			<div class="area-choose">
				选择区域：<span>山东省</span> <a class="are-line" href="javascript:;"
					onclick="">切换</a>
			</div>
			<!--/区域选择按钮-->
			</h4>
			<div class="row">
				<div class="col-md-12">
					<div class="table-before">
						<ul class="nav nav-task">
							<li class="active"><a href="javascript:;">活跃客户</a></li>
							<li><a href="javascript:;">未提货客户</a></li>
							<li><a href="javascript:;">7天内未提货</a></li>
							<li><a href="javascript:;">15天内未提货</a></li>
							<li><a href="javascript:;">一个月内未提货</a></li>
						</ul>
						<span class="shop-num"><span class="text-red">301</span>家店铺</span>
						<script type="text/javascript">
							$('.nav-task li').click(function(){
								$(this).addClass('active');
								$(this).siblings('li').removeClass('active');
							});
						</script>
						<div class="link-posit pull-right">
							<a href="" class="view">地图查看</a>
							<a class="table-export" href="javascript:void(0);">导出excel</a>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="table-task-list new-table-box table-overflow">
						<table class="table table-hover new-table">
							<thead>
								<tr>
									<th>店铺名</th>
									<th>地址</th>
									<th>本月提货</th>
									<th>距离上次提货（天）</th>
									<th>关联商家</th>
									<th>上次拜访</th>
									<th>操作</th>
								</tr>
							</thead>
							<tr>
								<td class="text-strong">小米手机专卖店</td>
								<td>山东省滨州市邹平县大桥镇223号</td>
								<td>本月提货次：1次</td>
								<td>3</td>
								<td>
									<span class="ico icon-link"></span>
									<span class="link-span">5家</span>
								</td>
								<td>2016.11.12</td>
								<td>
									<a class="btn btn-blue btn-sm" href="javascrip:;"><i class="ico icon-add"></i>拜访</a>
								</td>
							</tr>
							<tr>
								<td class="text-strong">小米手机专卖店</td>
								<td>山东省滨州市邹平县大桥镇223号</td>
								<td>本月提货次：1次</td>
								<td>3</td>
								<td>
									<span class="ico icon-link"></span>
									<span class="link-span">5家</span>
								</td>
								<td>2016.11.12</td>
								<td>
									<a class="btn btn-blue btn-sm" href="javascrip:;"><i class="ico icon-add"></i>拜访</a>
								</td>
							</tr>
						</table>
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
			<script src="static/bootstrap/js/bootstrap.min.js"></script>
			<script type="text/javascript">
			</script>
	</body>
</html>