<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<base href="<%=basePath%>" />
<title>子任务列表</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/kaohe/kaohe-det.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/ywmember.css" />
<link rel="stylesheet" type="text/css"
	href="static/abnormal/abnormal.css" />


<link rel="stylesheet" type="text/css"
	href="static/month-task/bootstrap-select.min.css">
<link rel="stylesheet" type="text/css"
	href="/static/month-task/findup-month-task.css">
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>

<script id="task-table-template" type="text/x-handlebars-template">

	{{#if content}}
	{{#each content}}
							<tr>
										<td>{{goal}}次拜访任务</td>
											<td>{{shopName}}</td>
											<td>已拜访：<span class="visit-num">{{done}}</span>次
											</td>
											<td>{{region}}</td>
											<td><button onclick="javascript:window.location.href='/monthTask/detail?shopId={{shopId}}&month={{month}}&subTaskId={{subTaskId}}'" class="btn btn-blue btn-sm">查看</button></td>
							</tr>
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
</head>
<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-month-task"></i>子任务列表
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div>
					<!--title-->
					<div class="clearfix">
						<div class="select-area fl">
							<label>选择月份：</label>
							<div class="search-date">
								<div class="input-group input-group-sm">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" class="form-control form_datetime input-sm"
										placeholder="请选择年-月" readonly="readonly">
								</div>
							</div>
							<button type="button" class="btn btn-blue btn-sm">检索</button>
						</div>
						<div class="select-area fl">
							<form class="form-horizontal" role="form">
								<div class="clearfix">
									<label for="basic" class="select-label">拜访次数：</label>
									<div class="visit-times">
										<select id="basic">
											<option value="">全部</option>
											<option value="20">20</option>
											<option value="15">15</option>
											<option value="10">10</option>
											<option value="7">7</option>
											<option value="4">4</option>
										</select>
									</div>
									<button type="button" class="btn btn-blue btn-sm btn-move"
										onclick="findTaskList();">检索</button>
								</div>
							</form>
						</div>
					</div>
					<div class="box-body">
						<!--列表内容-->
						<div class="tab-content">
							<!--油补记录-->
							<div class="tab-pane fade in active" id="box_tab1">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table class="table table-hover new-table">
										<thead>
											<tr>
												<th>任务次数</th>
												<th>商家名称</th>
												<th>已完成拜访次数</th>
												<th>所属区域</th>
												<th>明细</th>
											</tr>
										</thead>
										<tbody id="taskList"></tbody>
									</table>
								</div>
								<!--table-box-->
							</div>
							<!--油补记录-->
						</div>
						<!--列表内容-->
						<!--分页-->
						<div class="page-index" id="abnormalCoordPager"  style="margin-bottom: -100px"></div>
						<!--分页-->
					</div>
				</div>
				<!--box-->
				
			</div>
			<!--col-md-9-->
			<div class="col-md-3">
				<%@ include file="../kaohe/right_member_det.jsp"%>
			</div>
			<!--row-->
			<!-- Bootstrap core JavaScript================================================== -->
			<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
			<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
			<!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
			<script type="text/javascript">
				// goal,shopName,done,region,shopId,month,
				var parentId = "${taskId}";
			</script>
			<script src="static/bootstrap/js/bootstrap.min.js"></script>
			<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
			<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
			<script src="static/bootstrap/js/bootstrap-select.min.js"></script>
			<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
				charset="utf-8"></script>
			<script type="text/javascript"
				src="static/bootStrapPager/js/extendPagination.js"></script>
			<script src="static/month-task/month_findup.js"></script>
</body>

</html>
