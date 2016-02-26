<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>考核详情</title>
		<!-- Bootstrap -->
		<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="/static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="/static/kaohe/kaohe-det.css" />
		<link rel="stylesheet" type="text/css" href="/static/yw-team-member/ywmember.css" />
		<script src="/static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<div class="content main">
			<h4 class="page-header ">
				<i class="icon icon-khxq"></i>考核详情
			</h4>
			<div class="row">
				<div class="col-md-9">
					<!--box-->
					<div class="kaohe-det-body box border blue">
						<!--title-->
						<div class="box-title">
							<!--start row-->
							<div class="row">
								<div class="col-sm-12">
									<!--业务人员信息-->
									<i class="ico icon-ywmember"></i>${salesman.truename} 指标：
									<span>活跃客户${assess.assessActivenum}家</span>
									<span>提货量${assess.assessOrdernum}台</span>
									<!--/业务人员信息-->
									<div class="kaohe-time">
										开始时间： <span>${startDate}</span> 结束时间： <span>${endDate}</span>
									</div>
								</div>
							</div>
							<!--end row-->
						</div>
						<!--title-->
						<!--box-body-->
						<div class="box-body">
							<!--列表头部-->
							<div class="det-head ">
								<!--bar 布局-->
								<div class="col-sm-10">
									<div class="quiet-days marg-b-20">
										<span class="kaohe-stage">第一阶段考核</span>
										<span class="pull-right">
											<i class="ico icon-countdown"></i>倒计时:
											<span class="text-time">2</span>天
										</span>
									</div>
									<!--class style 沉寂:progress-bar-later ;活跃：progress-bar-ok ;未提货：-->
									<!--start 考核进度条-->
									<div class="J_kaohebar_parents progress progress-sm">
										<div style="width: 100%;" class="J_kaohebar progress-bar bar-kaohe">
											<div class="tag">
												<span class="icon-tag tag-kaohe">100%</span>
											</div>
										</div>
									</div>
									<!--end 考核进度条-->
								</div>
								<!--bar 布局-->
								<div class="col-xs-2">
									<button class="J_btn col-xs-12 btn btn-blue" onclick="toAssessStage('${salesman.id}','${assess.id}');">考核通过</button>
								</div>
							</div>
							<!--/列表头部-->
							<div class="hr-solid"></div>
							<!--start 操作区域-->
							<div class="search-box">
								<!--区域选择按钮-->
								<div class="btn-group btn-group-sm">
									<button type="button" class="btn btn-default " id="regionNameid">${salesman.region.name}</button>
									<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
										<span class="caret"></span>
										<span class="sr-only">Toggle Dropdown</span>
									</button>
									<ul class="dropdown-menu">
										<c:if test="${not empty regionList}">
												<c:forEach var="region" items="${regionList}" varStatus="s">
													<li><a  onclick="getRegionName('${region.name}')">${region.name}</a></li>
												</c:forEach>
										</c:if>
									</ul>
								</div>
								<!--/区域选择按钮-->
								<!--考核开始时间-->
								<div class="date-month">
									<div class="input-group">
										<span class="input-group-addon " id="basic-addon1"><i class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
										<input type="text" class="form-control form_datetime input-sm" placeholder="开始时间" readonly="readonly">
									</div>
								</div>--
								<div class="date-month ">
									<div class="input-group ">
										<span class="input-group-addon " id="basic-addon1"><i class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
										<input type="text" class="form-control form_datetime input-sm" placeholder="结束时间" readonly="readonly">
									</div>
								</div>
								<!--考核开始时间-->
								<button class="btn btn-blue btn-sm"><i class="ico ico-seach-wiath"></i>检索</button>
								<a class="link-export pull-right" href="javascript:void(0);">导出excel</a>
								<!--列表内容-->
							</div>
							<!--end 操作区域-->
							<div class="company-table table-responsive ">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>店铺名</th>
											<th>提货次数</th>
											<th>累计提货量 </th>
											<th>累计交易额</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="shop-name">天桥手机专卖店</td>
											<td class="tihuo-num">1次</td>
											<td class="total-num">121</td>
											<td class="sum">11112.45</td>
										</tr>
										<tr>
											<td class="shop-name">天桥手机专卖店</td>
											<td class="tihuo-num">1次</td>
											<td class="total-num">121</td>
											<td class="sum">11112.45</td>
										</tr>
										<tr>
											<td class="shop-name">天桥手机专卖店</td>
											<td class="tihuo-num">1次</td>
											<td class="total-num">121</td>
											<td class="sum">11112.45</td>
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
					<div class="ywmamber-msg box border pink">
						<!--title-->
						<div class="box-title">
							<i class="icon icon-time"></i>考核中
						</div>
						<div class="box-body">
							<!--ywmamber-body-->
							<div class="ywmamber-body">
								<img width="80" src="/static/img/user-head.png" alt="..." class="img-circle">
								<div class="msg-text">
									<h4>${salesman.truename}</h4>
									<p>ID: ${salesman.id}</p>
									<p>电话: ${salesman.mobile}</p>
								</div>
							</div>
							<!--/ywmamber-body-->
							<div class="stage">
								<span class="">第一阶段:60%      </span>
							</div>
							<div class="progress progress-sm">
								<div style="width: 60%;" class="progress-bar bar-kaohe"></div>
							</div>
							<div class="operation">
								<a href="javascript:;" class="">考核设置</a>
								<a href="javascript:;">辞退</a>
								<a href="javascript:;" class="pull-right">查看</a>
							</div>
							<div class="yw-text">
								入职时间:<span> ${salesman.regdate}</span>
								<br /> 负责区域:
								<span>${salesman.region.parent.parent.parent.name} ${salesman.region.parent.parent.name} ${salesman.region.parent.name} ${salesman.region.name}</span>
							</div>
							<!--拜访任务-->
							<div class="visit">
								<button class="col-xs-12 btn btn-visit" href="javascript:;"><i class="icon icon-add"></i>拜访</button>
							</div>
							<!--拜访任务-->
							<!--操作-->
							<div class="operation">
								<a href="javascript:;" class="">账户设置</a>
								<a href="javascript:;">冻结账户</a>
							</div>
							<!--操作-->
							<!--虚线-->
							<div class="hr"></div>
							<!--虚线-->
							<!--业务外部链接-->
							<div class="yw-link">
								<a class="link-oper" href="javascript:;"><i class="icon icon-user"></i>个人资料</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-income"></i>收益</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-task"></i>任务</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-log"></i>日志</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-footprint"></i>足迹</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-signin"></i>签收记录</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-saojie"></i>扫街记录</a>
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
			<script src="/static/bootstrap/js/bootstrap.min.js"></script>
			<script src="/static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
			<script src="/static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
			<script src="/static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>
			<script src="/static/kaohe/kaohe-det.js" type="text/javascript" charset="utf-8"></script>
			<script type="text/javascript">
				$('body input').val('');
				$(".form_datetime").datetimepicker({
					format: "yyyy-mm-dd",
					language: 'zh-CN',
					weekStart: 1,
					todayBtn: 1,
					autoclose: 1,
					todayHighlight: 1,
					startView: 2,
					minView: 2,
					pickerPosition: "bottom-left",
					forceParse: 0
				});
				var $_haohe_plan=$('.J_kaohebar').width();
				var $_haohe_planw=$('.J_kaohebar_parents').width();
				$(".J_btn").attr("disabled",'disabled');
				if($_haohe_planw===$_haohe_plan){
					$(".J_btn").removeAttr("disabled");
				}

				function getRegionName(name){
					$("#regionNameid").text(name);
				}
			</script>
	</body>

</html>
</html>
