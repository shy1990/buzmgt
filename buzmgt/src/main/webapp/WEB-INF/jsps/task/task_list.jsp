<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>任务列表</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link href="static/bootStrapPager/css/page.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/task/task.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var base = "<%=basePath%>";
	var number = '';//当前页数（从零开始）
	var totalPages = '';//总页数(个数)
	var searchData = {
		"size" : "13",
		"page" : "0",
	}
	var totalElements;//总条数
</script>
<script id="table-template" type="text/x-handlebars-template">
{{#each content}}
	<tr>
		<td class="text-strong"><span class="text-bule">【拜访】</span>{{taskName}}</td>
		<td>{{executor}}</td>
		<td>{{shopAddress }}</td>
		<td>{{Convert expiredTime }}</td>
		<td class="text-strong">{{{WhatStatus visitStatus}}}</td>
		<td><a class="btn btn-blue btn-sm" href="javascript:;" onclick="seeDetails('{{id}}')">查看</a></td>
		<!--<td><a class="btn btn-red btn-sm" href="javascript:;">警告</a>
		<a class="btn btn-yellow btn-sm" href="javascript:;">扣罚</a></td>-->
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
			<i class="ico icon-task-list"></i>任务
			<!--区域选择按钮-->
			<div class="area-choose" id="area" data-a="${regionId}">
				选择区域：<span>${regionName}</span> <a class="are-line" href="javascript:;"
					onclick="getRegionChoose(${regionId});">切换</a>
				<%-- <input id="area" type="hidden" value="${regionId}"> --%>
			</div>
			<!--/区域选择按钮-->
			<a href="javascript:addTask();" class="btn btn-blue" data-toggle="modal"
				data-target="#addTask" data-whatever="@mdo"> <i
				class="ico icon-add"></i>添加任务
			</a>
		</h4>
		<!--添加任务-->
		<div class="modal-blue modal fade " id="addTask" tabindex="-1"
			role="dialog" aria-labelledby="exampleModalLabel">
			<div class="modal-dialog " role="document">
				<div class="modal-content">
					<!--modal-header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span class="glyphicon glyphicon-remove" aria-hidden="true">
								<!--&times;-->
							</span>
						</button>
						<h4 class="modal-title" id="exampleModalLabel">
							<i class="ico icon-add"></i>添加任务
						</h4>
					</div>
					<!--modal-header-->
					<!--modal-body-->
					<div class="modal-body">
						<a href="/task/addVisitMap" class="btn btn-blue">拜访任务</a> <a
							href="javascript:;" class="btn btn-blue">任务1</a>
							<a href="/monthTask/initMonthTask" class="btn btn-blue">批量任务</a>
					</div>
					<div class="modal-footer">
		 			</div>
					<!--modal-body-->
				</div>
			</div>
		</div>
		<!--添加任务-->
		<div class="row">
			<div class="col-md-12">
				<div class="link-posit pull-right">
					<a class="table-export" href="javascript:void(0);">导出excel</a>
				</div>
				<div class="clearfix"></div>
				<div class="table-task-list new-table-box table-overflow">
					<table class="table table-hover new-table">
						<thead>
							<tr>
								<th>任务名称</th>
								<th>执行人</th>
								<th>地址</th>
								<th>截止时间</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tableList">
							
						</tbody>
					</table>
					<div id="callBackPager"></div>
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
		<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
		<script src="<%=basePath%>static/js/dateutil.js"></script>
		<script src="<%=basePath%>static/task/task.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
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
				var $_haohe_plan = $('.J_kaohebar').width();
				var $_haohe_planw = $('.J_kaohebar_parents').width();
				$(".J_btn").attr("disabled", 'disabled');
				if ($_haohe_planw === $_haohe_plan) {
					$(".J_btn").removeAttr("disabled");
				}
				
				/*区域 */
				function getRegionChoose(id){
					window.location.href='/region/getPersonalRegion?id='+id+"&flag=task";
				}
			</script>
</body>

</html>
