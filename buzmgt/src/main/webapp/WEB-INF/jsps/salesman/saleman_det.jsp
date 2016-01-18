<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>业务详情</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/ywmember.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="content main">
		<h4 class="team-member-header page-header ">
			<div class="row">
				<div class="col-sm-12">
					<i class="icon icon-ywdet"></i>业务详情 <small class="header-text">所属区域共<span
						class="text-red"> 226 </span> 家商家
					</small> <small class="header-text">今日新增<span class="text-bule">
							7 +</span></small>
				</div>
			</div>
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="member-det-body box border blue">
					<!--title-->
					<div class="box-title">
						<div class="row">
							<div class="col-sm-12">
								<!--公司列表-->
								<i class="icon icon-comp-list"></i>公司列表
								<!--/公司列表-->
							</div>
						</div>
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<!--列表头部-->
						<div class="det-head">
							<!--区域选择按钮-->
							<div class="btn-group btn-group-sm">
								<button type="button" class="btn btn-default ">
									<i class="icon province-icon"></i>山东省
								</button>
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									<span class="caret"></span> <span class="sr-only">Toggle
										Dropdown</span>
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
							<!--start 月份-->
							<div class="date-month">
								<span class=""><i class="glyphicon glyphicon-remove glyphicon-calendar"></i></span>
									<input type="text" value="" readonly class="form-control form_datetime">
									<span class=""><i class="icon-down"></i></span>
							</div>

							<!--end /月份-->
							<!--按钮组-->
							<div class="head-button">
								<div class="btn-group btn-group-sm" role="group"
									aria-label="...">
									<button type="button" class="btn btn-default active">全部</button>
									<button type="button" class="btn btn-default">沉寂客户</button>
									<button type="button" class="btn btn-default">活跃客户</button>
									<button type="button" class="btn btn-default">未提货客户</button>
								</div>
								<a class="link-export" href="javascript:void(0);">导出excel</a>
							</div>
							<!--/按钮组-->
						</div>
						<!--/列表头部-->
						<!--列表内容-->
						<div class="company-table table-responsive ">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td class="shop-name">天桥手机专卖店</td>
										<td class="addreass">山东省天桥区邹县桥头镇</td>
										<td class="">本月提货次数：1次 <span
											class="shop-status status-danger">沉寂客户</span> <span
											class="shop-status status-ok sr-only">活跃客户</span> <span
											class="shop-status status-no sr-only">未提货客户</span>
										</td>
										<td class="num-progress col-sm-4">
											<div class="quiet-days">
												<span>沉寂天数： 5天</span>
											</div> <!--class style 沉寂:progress-bar-later ;活跃：progress-bar-ok ;未提货：-->
											<div class="progress progress-sm bar-later">
												<div style="width: 54%;" class="progress-bar bar-static">
													<span class="icon-tag tag-static">7</span>
												</div>
												<div class="tag">
													<span class="icon-tag tag-later">12</span>
												</div>
											</div>
										</td>
										<td class="shop-operate">
											<div class="shop-link">
												<a href="" title="关联商家"><i class="icon icon-link"></i> <span
													class="link-span">5家</span></a>
											</div>
											<button class="btn btn-sm btn-visit">
												<i class="icon icon-add"></i>拜访
											</button>
										</td>
									</tr>
									<tr>
										<td class="shop-name">天桥手机专卖店</td>
										<td class="addreass">山东省天桥区邹县桥头镇</td>
										<td class="">本月提货次数：1次 <span
											class="shop-status status-danger sr-only">沉寂客户</span> <span
											class="shop-status status-ok ">活跃客户</span> <span
											class="shop-status status-no sr-only">未提货客户</span>
										</td>
										<td class="num-progress col-sm-3 col-md-4">
											<div class="quiet-days">
												<span>沉寂天数： 0天</span>
											</div> <!--class style 沉寂:progress-bar-later ;活跃：progress-bar-ok ;未提货：-->
											<div class="progress progress-sm bar-static">
												<div style="width: 14.4%;" class="progress-bar bar-ok">
													<div class="tag">
														<span class="icon-tag tag-ok">1</span>
													</div>
												</div>
												<span class="icon-tag tag-static">7</span>
											</div>
										</td>
										<td class="shop-operate">
											<div class="shop-link">
												<a href="" title="关联商家"><i class="icon icon-link"></i> <span
													class="link-span">5家</span></a>
											</div>
											<button class="btn btn-sm btn-visit">
												<i class="icon icon-add"></i>拜访
											</button>
										</td>
									</tr>
									<tr>
										<td class="shop-name">天桥手机专卖店</td>
										<td class="addreass">山东省天桥区邹县桥头镇</td>
										<td class="">本月提货次数：1次 <span
											class="shop-status status-danger sr-only">沉寂客户</span> <span
											class="shop-status status-ok sr-only">活跃客户</span> <span
											class="shop-status status-no">未提货客户</span>
										</td>
										<td class="num-progress col-md-3 col-lg-4">
											<div class="quiet-days">
												<span>沉寂天数： 0天</span>
											</div> <!--class style 沉寂:progress-bar-later ;活跃：progress-bar-ok ;未提货：-->
											<div class="progress progress-sm ">
												<span class="icon-tag tag-no">0</span>
											</div>
										</td>
										<td class="shop-operate">
											<div class="shop-link">
												<a href="" title="关联商家"><i class="icon icon-link"></i> <span
													class="link-span">5家</span></a>
											</div>
											<button class="btn btn-sm btn-visit">
												<i class="icon icon-add"></i>拜访
											</button>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!--/列表内容-->
					</div>
					<!--/box-body-->
				</div>
				<!--/box-->
			</div>
			<!--team-map-->
			<div class="col-md-3">
				<!--box-->
				<!--不同阶段颜色不同1：pink 2：yellow 3:violet 4:-->
				<div class="ywmamber-msg box border cyan">
					<!--title-->
					<div class="box-title">
						<i class="icon icon-time"></i>考核中
					</div>
					<div class="box-body">
						<!--ywmamber-body-->
						<div class="ywmamber-body">
							<img width="80" src="static/img/user-head.png" alt="..."
								class="img-circle">
							<div class="msg-text">
								<h4>易小星</h4>
								<p>ID: A236743252</p>
								<p>电话: 12547346455</p>
							</div>
						</div>
						<!--/ywmamber-body-->
						<div class="stage">
							<span class="">第一阶段:60% </span>
						</div>
						<div class="progress progress-sm">
							<div style="width: 60%;" class="progress-bar bar-stage"></div>
						</div>
						<div class="operation">
							<a href="javascript:;" class="">考核设置</a> <a href="javascript:;">辞退</a>
							<a href="javascript:;" class="pull-right">查看</a>
						</div>
						<div class="yw-text">
							入职时间:<span> 2015.09.21</span><br /> 负责区域:<span>山东省滨州市邹平县</span>
						</div>
						<!--拜访任务-->
						<div class="visit">
							<button class="col-xs-12 btn btn-visit" href="javascript:;">
								<i class="icon icon-add"></i>拜访
							</button>
						</div>
						<!--拜访任务-->
						<!--操作-->
						<div class="operation">
							<a href="javascript:;" class="">账户设置</a> <a href="javascript:;">冻结账户</a>
						</div>
						<!--操作-->
						<!--虚线-->
						<div class="hr"></div>
						<!--虚线-->
						<!--业务外部链接-->
						<div class="yw-link">
							<a class="link-oper" href="javascript:;"><i
								class="icon icon-user"></i>个人资料</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-income"></i>收益</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-task"></i>任务</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-log"></i>日志</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-footprint"></i>足迹</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-signin"></i>签收记录</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-saojie"></i>扫街记录</a>
						</div>


					</div>
				</div>
				<!--/team-map-->
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
		<script src="/static/bootstrap/js/bootstrap.min.js"></script>
		<script src="/static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="/static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="static/yw-team-member/team-member.js"
			type="text/javascript" charset="utf-8"></script>
</body>

</html>