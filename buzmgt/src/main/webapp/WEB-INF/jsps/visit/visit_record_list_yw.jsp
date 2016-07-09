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
<link href="<%=basePath%>static/bootStrapPager/css/page.css" rel="stylesheet" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var base = "<%=basePath%>";
	var number = '';//当前页数（从零开始）
	var totalPages = '';//总页数(个数)
	var searchData = {
		"size" : "20",
		"page" : "0",
	}
	var totalElements;//总条数
</script>
<script id="table-template" type="text/x-handlebars-template">
{{#each content}}
	<tr>
		<td class="text-strong">{{taskName}}</td>
		<td>{{Convert expiredTime}}</td>
		<td>  {{address}}</td>
		<td>{{period}}天</td>
		<td><a class="btn btn-blue btn-sm" href="javascript:;" onclick="seeDetails('{{id}}')">查看</a>
		</td>
	</tr>
{{else}}
<div style="text-align: center;">
	<tr style="text-align: center;">没有相关数据!</tr>
</div>
{{/each}}
</script>
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
					<div class="box-title" id="box-title" data-a="${sm.id}">
						<!--区域选择按钮-->
						<select id="area" class="form-control input-xs "
							onchange="getSaojieDataList();"
							style="width: 110px; position: relative; height: 25px; padding: 2px 5px; display: inline-block; font-size: 12px; color: #6d6d6d;">
							<option value="${sm.region.id}" selected="selected">${sm.region.name}</option>
							<%-- <c:forEach var="region" items="${rList}" varStatus="s">
								<option value="${region.id}">${region.name}</option>
							</c:forEach> --%>
						</select>
						<!--/区域选择按钮-->
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="text-time">
							<span class="text-strong chang-time">请选择时间：</span>
							<div class="search-date">
								<div class="input-group input-group-sm date form_date_start">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" class="form-control form_datetime input-sm"
										placeholder="开始日期" readonly="readonly" id="beginTime">
								</div>
							</div>
							--
							<div class="search-date">
								<div class="input-group input-group-sm date form_date_end">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" class="form-control form_datetime input-sm"
										placeholder="结束日期" readonly="readonly" id="endTime">
								</div>
							</div>
							<!--考核开始时间-->
							<button class="btn btn-blue btn-sm" onclick="goSearch();">检索</button>
							<!---->
							<div class="abnormal-details">
								<span>共 <span class="text-bule" id="totalElements"></span> 次拜访
								</span> <!-- <span>平均拜访周期 ：<span class="text-bule">1</span> 天<span
									class="text-bule">6</span> 小时<span class="text-bule">26</span>
									分/次
								</span> -->
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
										<tbody id="tableList">
							
										</tbody>
									</table>
									<div id="callBackPager"></div>
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
		<script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
		<script src="<%=basePath%>static/js/common.js" type="text/javascript"></script>
		<script src="<%=basePath%>static/js/dateutil.js"></script>
		<script src="<%=basePath%>static/visit/visit-record-yw.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
		<script type="text/javascript">
				$('body input').val('');
				$(".form_datetime").datetimepicker({
					format: "yyyy-mm-dd hh:ii:ss",
					language: 'zh-CN',
					weekStart: 1,
					todayBtn: 1,
					autoclose: 1,
					todayHighlight: 1,
					startView: 2,
					minView: 'hour',
					pickerPosition: "bottom-right",
					forceParse: 0
				});
				var $_haohe_plan = $('.J_kaohebar').width();
				var $_haohe_planw = $('.J_kaohebar_parents').width();
				$(".J_btn").attr("disabled", 'disabled');
				if ($_haohe_planw === $_haohe_plan){　
					$(".J_btn").removeAttr("disabled");
				}
			</script>
</body>

</html>
