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
<title>子任务执行明细</title>
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
<link rel="stylesheet" type="text/css" href="static/task/detail.css">
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-task-detail"></i>明细
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="visit-body box border blue">
					<!--title-->
					<div class="box-title">
						<!--区域选择按钮-->
						<span class="">${goal}次拜访任务</span>
						<!--/区域选择按钮-->
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<span class="text-blue">${shopName} 已拜访： ${done}次</span>
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
												<th>拜访日期</th>
												<th>商家名称</th>
												<th>拜访动作</th>
												<th>拜访时间</th>
											</tr>
										</thead>
										<c:choose>
											<c:when test="${not empty visitList}">
												<c:forEach var="vs" items="${visitList}">
													<tr>
														<td class="">${vs.day}</td>
														<td>${vs.shopName}</td>
														<td><span class="text-bule">${vs.action}</span></td>
														<td>${vs.time}</td>
													</tr>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<tr>
													<td colspan="100">没有相关任务执行</td>
												</tr>
											</c:otherwise>
										</c:choose>
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
