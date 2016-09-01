<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<base href="<%=basePath%>" />
<title>收益主方案添加</title>
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />

<link rel="stylesheet" type="text/css" href="/static/income/phone.css">
<link rel="stylesheet" type="text/css"
	href="/static/income/plan_index.css" />

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="task-table-template" type="text/x-handlebars-template">
{{#if content}}
	{{#each content}}
     <div class="col-sm-3 cl-padd" >
		<div class="ratio-box">
			<div class="ratio-box-dd">
				 <span
					class="text-black jll">{{regionName}} </span> <a
					class="text-blue-s jll"  data-toggle="modal"
					 onclick="modify('{{id}}','{{rate}}')">修改</a>
 				<a class="text-blue-s jll" data-toggle="modal" onclick="deletePunish('{{id}}')">删除</a>
			</div>
		</div>
	</div>
	{{/each}}
{{/if}}
</script>
</head>

<body>
	<h4 class="page-header">
		<i class="ico icon-new"></i>创建方案 <a href="javascript:history.back();"><i
			class="ico icon-back fl-right"></i></a>
	</h4>

	<div class="row">
		<!--col begin-->
		<div class="col-md-12">
			<!--orderbox begin-->
			<div class="order-box">
				<ul>
					<li>
						<dl class="dl-horizontal">
							<dt>选择所属省份：</dt>
							<dd>
								<p class="plan_p">${plan.regionname}</p>
							</dd>
						</dl>


					</li>


					<li>
						<dl class="dl-horizontal">
							<dt>填写方案标题：</dt>
							<dd>
								<p class="plan_p">${plan.maintitle}</p>
							</dd>
						</dl>


					</li>

					<li>
						<dl class="dl-horizontal">
							<dt>填写副标题：</dt>
							<dd>
								<p class="plan_p">${plan.subtitle}</p>

							</dd>
						</dl>
					</li>

					<li>
						<dl class="dl-horizontal">
							<dt>使用人员：</dt>

							<dd style="width: 750px; margin-bottom: 20px">

								<div class="col-sm-2">
									<%--<c:forEach var="user" items="${userList}">
										<div class="col-sm-2"
											style="margin-bottom: 20px; margin-left: -20px">
											<div class="s-addperson ">
												${user.salesmanname} <i class="icon-s icon-close"
													data-toggle="modal" data-pid="${user.salesmanId}" data-target="#del"></i>
											</div>
										</div>
									</c:forEach>
									 --%>
									<a
										class="J_addDire btn btn-default ph-btn-bluee icon-tj col-sm-6"
										onclick="openUser();"></a>
								</div>

							</dd>
						</dl>
					</li>
				</ul>

				<button class="btn btn-primary col-sm-1" style="margin-left: 180px"
					onclick="newPlan()">提交</button>
			</div>
			<!--orderobx end-->
		</div>
		<!--col end-->
	</div>

	<!--删除-->
	<div id="del" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">删除提示</h3>
				</div>

				<div class="modal-body">
					<div class="container-fluid">
						<form class="form-horizontal">
							<div class="form-group">
								<p class="col-sm-12  ">你确定要将该业务员从方案中移除吗？</p>
								<p class="col-sm-12">移除后提成将不再按此方案计算....</p>
								<div class="ph-search-date">

									<p>
										<label class=" " id="basic-addon1">选择日期</label> <input
											type="text" id="newDate"
											class="form-control form_datetime input-sm"
											placeholder="选择日期" readonly="readonly"
											style="background: #ffffff">
									</p>
								</div>
							</div>


							<div class="btn-qx">
								<button type="submit" class="btn btn-danger btn-d"
									onclick="deleteUser()">删除</button>
							</div>

							<div class="btn-dd">
								<button type="submit" data-dismiss="modal"
									class="btn btn-primary btn-d">取消</button>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
<!--添加人员-->
	<div id="user" class="modal fade" role="dialog">
		<jsp:include flush="true" page="userSelect.jsp"></jsp:include>
	</div>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
	<script
		src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="/static/js/common.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script src="/static/income/main/updatePlan.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript">
		var pId = "${plan.id}";
		var	base='<%=basePath%>';
		var gloIndex = null;
		$(function() {
			initUsers();
			initDateInput();
			findPlanUserList(0);
		});
	</script>

</body>

</html>