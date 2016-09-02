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
<title>收益主方案修改人员</title>
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link href="static/bootStrapPager/css/page.css" rel="stylesheet">

<link rel="stylesheet" type="text/css" href="/static/income/phone.css">
<link rel="stylesheet" type="text/css"
	href="/static/income/plan_index.css" />
<link rel="stylesheet" type="text/css"
	href="/static/income/updatePlan.css" />
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
		<i class="ico icon-new"></i>适用人员 <a href="javascript:history.back();"><i
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
								<span class="text-pronce">${plan.regionname}</span>
							</dd>
						</dl>


					</li>


					<li>
						<dl class="dl-horizontal">
							<dt>填写方案标题：</dt>
							<dd>
								<span class="text-pronce">${plan.maintitle}</span>
							</dd>
						</dl>


					</li>

					<li>
						<dl class="dl-horizontal">
							<dt>填写副标题：</dt>
							<dd>
								<span class="text-pronce">${plan.subtitle}</span>

							</dd>
						</dl>
					</li>

					<li>
						<dl class="dl-horizontal">
							<dt>使用人员：</dt>

							<dd style="width: 750px; margin-bottom: 20px">

								<div class="col-sm-2">
									<a
										class="J_addDire btn btn-default ph-btn-bluee icon-tj col-sm-6"
										onclick="openUser();"></a>
								</div>

							</dd>
						</dl>
					</li>
				</ul>

				<button class="btn btn-primary col-sm-1" style="margin-left: 180px"
					onclick="commitUsers()">提交</button>
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
								<div class="form-group">
									<p class="col-sm-12 text-red ">你确定要将该业务员从方案中移除吗？</p>
									<p class="col-sm-12 text-red ">移除后提成将不再按此方案计算.... ...
								</div>
								<hr>

								<span class="text-gery text-strong">移除日期：</span>
								<div style="margin-left: 80px; margin-top: -25px">
									<div class="input-group are-line">
										<span class="input-group-addon "><i
											class=" ph-icon icon-riz"></i></span> <input type="text"
											class="form-control form_datetime " id="newDate"
											placeholder="年-月-日" readonly="readonly"
											style="background: #ffffff; width: 265px;">
									</div>

								</div>
								<div class="btn-qx">
									<button type="submit" class="btn btn-danger btn-d"
										onclick="deleteUser()">确定</button>
								</div>

								<div class="btn-dd">
									<button type="submit" data-dismiss="modal"
										class="btn btn-primary btn-d">取消</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 删除其他方案的人员 -->
	<div id="otherPlan" class="modal fade" role="dialog">
		<jsp:include flush="true" page="otherPlan.jsp"></jsp:include>
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
		var otherPlanFlag=false;
		$(function() {
			initUsers();
			initDateInput();
			findPlanUserList(0);
			//页面未知原因刷新
			$('#otherPlan').on('hide.bs.modal', function (e) {  
				otherPlanFlag=false;
			}); 
		});
	</script>

</body>

</html>