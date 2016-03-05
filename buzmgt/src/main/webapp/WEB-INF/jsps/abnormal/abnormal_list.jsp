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
<meta charset="utf-8">
<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>异常签收</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/abnormal/abnormal.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-abnormal"></i>异常签收
			<!--区域选择按钮-->
			<div class="area-choose">
				选择区域：<span>山东省</span> <a class="are-line" href="javascript:;"
					onclick="">切换</a>
			</div>
			<!--/区域选择按钮-->
		</h4>
		<div class="row">
			<div class="col-md-12">
				<!--box-->
				<div class="abnormal-body box border blue">
					<!--title-->
					<div class="box-title">
						<!--菜单栏-->
						<ul class="nav nav-tabs">
							<li class="active"><a href="#box_tab1" data-toggle="tab"><span
									class="">业务揽收异常</span></a></li>
							<li><a href="#box_tab2" data-toggle="tab"><span class="">客户签收异常</span></a></li>
						</ul>
						<!--/菜单栏-->
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="marg-t text-time">
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
								<span>共 <span class="text-bule">2580</span> 单
								</span> <span>客户签收 <span class="text-bule">2500</span> 单
								</span> <span>拒收 <span class="text-bule">80</span> 单
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
									<table class="table table-hover new-table abnormal-table">
										<thead>
											<tr>
												<th>业务名称</th>
												<th>订单号</th>
												<th>签收地址</th>
												<th>签收时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tr>
											<td class=""><img width="50" height="50"
												src="static/img/abnormal/user-head.png" class="img-circle" />
												<div class="business-name">
													<span class="text-strong">易小川</span>(区域经理) <br /> 小米手机专卖店
												</div></td>
											<td>201603041256</td>
											<td>山东省滨州市邹平县大桥镇223号</td>
											<td><span class="icon-tag-yc">异常</span>2016.03.12 18:20</td>
											<td><a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
											</td>
										</tr>
										<tr>
											<td class=""><img width="50" height="50"
												src="static/img/abnormal/user-head.png" class="img-circle" />
												<div class="business-name">
													<span class="text-strong">易小川</span>(区域经理) <br /> 小米手机专卖店
												</div></td>
											<td>201603041256</td>
											<td>山东省滨州市邹平县大桥镇223号</td>
											<td><span class="icon-tag-yc">异常</span>2016.03.12 18:20</td>
											<td><a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
											</td>
										</tr>
										<tr>
											<td class=""><img width="50" height="50"
												src="static/img/abnormal/user-head.png" class="img-circle" />
												<div class="business-name">
													<span class="text-strong">易小川</span>(区域经理) <br /> 小米手机专卖店
												</div></td>
											<td>201603041256</td>
											<td>山东省滨州市邹平县大桥镇223号</td>
											<td><span class="icon-tag-yc">异常</span>2016.03.12 18:20</td>
											<td><a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
											</td>
										</tr>
									</table>
								</div>
								<!--table-box-->
							</div>
							<!--业务揽收异常-->

							<!--客户签收异常-->
							<div class="tab-pane fade" id="box_tab2">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table class="table table-hover new-table abnormal-table">
										<thead>
											<tr>
												<th>业务名称</th>
												<th>订单号</th>
												<th>签收地址</th>
												<th>签收时间</th>
												<th>送货时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tr>
											<td class=""><img width="50" height="50"
												src="static/img/abnormal/user-head.png" class="img-circle" />
												<div class="business-name">
													<span class="text-strong">易小川</span>(区域经理) <br /> 小米手机专卖店
												</div></td>
											<td>201603041256</td>
											<td><span class="icon-tag-yc">异常</span> 山东省滨州市邹平县大桥镇223号
											</td>
											<td>2016.03.12 18:20</td>
											<td>6小时20分钟</td>
											<td><a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
											</td>
										</tr>
										<tr>
											<td class=""><img width="50" height="50"
												src="static/img/abnormal/user-head.png" class="img-circle" />
												<div class="business-name">
													<span class="text-strong">易小川</span>(区域经理) <br /> 小米手机专卖店
												</div></td>
											<td>201603041256</td>
											<td><span class="icon-tag-yc">异常</span> 山东省滨州市邹平县大桥镇223号
											</td>
											<td>2016.03.12 18:20</td>
											<td>6小时20分钟</td>
											<td><a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
											</td>
										</tr>
									</table>
								</div>
								<!--table-box-->
							</div>
							<!--客户签收异常-->
						</div>
						<!--列表内容-->
					</div>
					<!--box-body-->
				</div>
				<!--box-->
			</div>
			<!--col-md-12-->
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
				format : "yyyy-mm-dd",
				language : 'zh-CN',
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2,
				pickerPosition : "bottom-right",
				forceParse : 0
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