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
<title>明细</title>
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/kaohe/kaohe-det.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/ywmember.css" />
<link rel="stylesheet" type="text/css"
	href="static/abnormal/abnormal.css" />
<link rel="stylesheet" type="text/css"
	href="static/month-task/findup-month-task.css">
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="task-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		 <tr rowspan="{{sum}}">
				<td>{{level}}次任务拜访</td>
				 <td class="multi-row">
				{{#each shops}}
						 <p>{{name}}</p>
					{{/each}}	 
					</td>
				 <td>已设置：<span class="finish-percentage">{{rate}}</span></td>
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
			<i class="ico icon-month-task"></i>月任务设置明细
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div>
					<!--title-->
					<div class="clearfix">
						<div class="link-posit pull-right">
							<a class="table-export" href="javascript:void(0);">导出excel</a>
						</div>
					</div>
					<!--title-->

					<!--table-body-->
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
												<th>拜访次数</th>
												<th>已设置商家</th>
												<th>已设置占比</th>
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
						<div id="Pager"></div>
						<!--分页-->
					</div>
					<!--table-body-->
				</div>
				<!--box-->
			</div>
			<!--col-md-9-->

			<div class="col-md-3">
				<%@ include file="../kaohe/right_member_det.jsp"%>
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
		<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
			charset="utf-8"></script>
		<script type="text/javascript"
			src="static/bootStrapPager/js/extendPagination.js"></script>
		<script src="static/month-task/month_set.js"></script>
		<script type="text/javascript">
			var taskId = "${taskId}";
			findTaskList(0);
			$('.table-export').on(
					'click',
					function() {
						window.location.href = "/monthTask/export/" + taskId
								+ "?salesId=${salesMan.id}";
					});
		</script>
</body>

</html>
