<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			System.out.print(basePath);
%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>拜访记录</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/kaohe/kaohe-det.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/ywmember.css" />
<link rel="stylesheet" type="text/css"
	href="static/abnormal/abnormal.css" />
<link rel="stylesheet" type="text/css" href="static/visit/visit.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-visit-record"></i>拜访记录
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="visit-body box border blue">
					<!--title-->
					<div class="box-title">
						<!--区域选择按钮-->
						<select id="regionId" class="form-control input-xs "
							onchange="getSaojieDataList();"
							style="width: 110px; position: relative; height: 25px; padding: 2px 5px; display: inline-block; font-size: 12px; color: #6d6d6d;">
							<option value="" selected="selected">全部区域</option>
							<c:forEach var="region" items="${rList}" varStatus="s">
								<option value="${region.id}">${region.name}</option>
							</c:forEach>
						</select>
						<!--/区域选择按钮-->
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="text-time">
							<span class="text-strong chang-time">请选择时间：</span>
							<div class="search-date">
								<div class="input-group input-group-sm">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" class="form-control form_datetime input-sm"
										placeholder="开始日期" readonly="readonly">
								</div>
							</div>
							--
							<div class="search-date">
								<div class="input-group input-group-sm">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" class="form-control form_datetime input-sm"
										placeholder="结束日期" readonly="readonly">
								</div>
							</div>
							<!--考核开始时间-->
							<button class="btn btn-blue btn-sm"
								onclick="goSearch('${salesman.id}','${assess.id}');">
								检索</button>
							<!---->
							<div class="abnormal-details">
								<span>共 <span class="text-bule">2580</span> 次拜访
								</span> <span>平均拜访周期 ：<span class="text-bule">1</span> 天<span
									class="text-bule">6</span> 小时<span class="text-bule">26</span>
									分/次
								</span>
							</div>
							<div class="link-posit pull-right">
								<a class="table-export" href="javascript:void(0);">导出excel</a>
							</div>
						</div>
						<!--列表内容-->
						<div class="tab-content">
							<!--业务揽收异常-->
							<div class="tab-pane fade in active" id="box_tab1">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table
										class="table table-hover new-table abnormal-record-table">
										<thead>
											<tr>
												<th>店铺名称</th>
												<th>拜访时间</th>
												<th>坐标点匹配</th>
												<th>距上次拜访</th>
												<th>操作</th>
											</tr>
										</thead>
										<tr>
											<td class="text-strong">小米手机专卖店</td>
											<td>201611.12 10:20</td>
											<td><span class="icon-tag-zc">正常</span> <span
												class="icon-tag-yc">异常</span> 山东省滨州市邹平县大桥镇223号</td>
											<td>15天</td>
											<td><a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
											</td>
										</tr>
										<tr>
											<td class="text-strong">小米手机专卖店</td>
											<td>201611.12 10:20</td>
											<td><span class="icon-tag-zc">正常</span> <span
												class="icon-tag-yc">异常</span> 山东省滨州市邹平县大桥镇223号</td>
											<td>15天</td>
											<td><a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
											</td>
										</tr>
									</table>
								</div>
								<!--table-box-->
							</div>
							<!--业务揽收异常-->

						</div>
						<!--列表内容-->
					</div>
					<!--box-body-->
				</div>
				<!--box-->
			</div>
			<!--col-md-9-->
			<div class="col-md-3">
				<!--box-->
				<!--不同阶段颜色不同1：pink 2：yellow 3:violet 4:-->
				<div class="ywmamber-msg box border pink">
					<!--title-->
					<div class="box-title">
						<i class="icon icon-time"></i>考核中
					</div>
					<!--box-body-->
					<div class="box-body">
						<!--ywmamber-body-->
						<div class="ywmamber-body">
							<img width="80" src="static/img/background/user-head.png"
								alt="..." class="img-circle">
							<div class="msg-text">
								<h4>易小星</h4>
								<p>ID: A236743252</p>
								<p>电话: 12547346455</p>
							</div>
						</div>
						<!--/ywmamber-body-->
						<div class="stage">
							<span class="kaohe-stage onekaohe-stage">第一阶段:60% </span>
						</div>
						<div class="progress progress-sm">
							<div style="width: 60%;" class="progress-bar bar-kaohe"></div>
						</div>
						<div class="operation">
							<a href="saojie_upd.html" class="">考核设置</a> <a
								href="kaohe_det.html" class="pull-right">查看</a>
						</div>
						<div class="yw-text">
							入职时间:<span> 2015.09.21</span> <br /> 负责区域: <span>山东省滨州市邹平县</span>
						</div>
						<!--拜访任务-->
						<div class="visit">
							<button class="col-xs-12 btn btn-visit" href="javascript:;">
								<i class="ico icon-add"></i>拜访任务
							</button>
						</div>
						<!--拜访任务-->
						<!--操作-->
						<div class="operation">
							<a href="javascript:;" class="">账户设置</a> <a href="javascript:;">冻结账户</a>
						</div>
						<!--操作-->
					</div>
					<!--box-body-->
				</div>
				<!--box-->
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
		<!--row-->
		<!-- Bootstrap core JavaScript================================================== -->
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="static/yw-team-member/team-member.js"
			type="text/javascript" charset="utf-8"></script>
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
					pickerPosition: "bottom-right",
					forceParse: 0
				});
				var $_haohe_plan = $('.J_kaohebar').width();
				var $_haohe_planw = $('.J_kaohebar_parents').width();
				$(".J_btn").attr("disabled", 'disabled');
				if ($_haohe_planw === $_haohe_plan) {　
					$(".J_btn").removeAttr("disabled");
				}
			</script>
</body>

</html>