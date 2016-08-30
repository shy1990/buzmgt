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
<title>月任务扣罚设置</title>
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css" href="/static/task/task.css" />
<link rel="stylesheet" type="text/css" href="/static/oil/css/oil.css" />
<link rel="stylesheet" type="text/css"
	href="/static/zTree/css/zTreeStyle/zTreeStyle.css" />

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"
	href="../static/order-detail/order_detail.css" />
<link rel="stylesheet" type="text/css" href="/static/income/phone.css">
<link rel="stylesheet" type="text/css"
	href="/static/income/plan_index.css" />

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
								<select id="region" class="ph-select">
									<option value="">全部区域</option>
									<c:forEach var="region" items="${regions}">
										<option value="${region.name}">${region.name}</option>
									</c:forEach>
								</select>
							</dd>
						</dl>


					</li>


					<li>
						<dl class="dl-horizontal">
							<dt>填写方案标题：</dt>
							<dd>
								<select id="organization" class="ph-select">
									<c:forEach var="organization" items="${organizations}">
										<option value="${organization.name}">${organization.name}</option>
									</c:forEach>
								</select>


								<div class="ph-search-date">

									<span class=" " id="basic-addon1"></span> <input type="text"
										class="form-control form_datetime input-sm" placeholder="选择日期"
										readonly="readonly" style="background: #ffffff">

								</div>


								<select id="machineType" class="ph-select"
									style="margin-left: 25px;">
									<option value="">类别</option>
									<c:forEach var="organization" items="${machineTypes}">
										<option value="${organization.name}">${organization.name}</option>
									</c:forEach>
								</select> <select class="ph-select" id="organization">
									<option value="">品牌</option>
									<c:forEach var="organization" items="${allBrands}">
										<option value="${organization.name}">${organization.name}</option>
									</c:forEach>
								</select>
							</dd>
						</dl>


					</li>

					<li>
						<dl class="dl-horizontal">
							<dt>填写副标题：</dt>
							<dd>
								<div class="input-group">
									<span class="input-group-addon"><i
										class="icon-s icon-qb"></i></span> <input type="text"
										class="form-control" placeholder="请填写方案标题"
										aria-describedby="basic-addon1"
										style="width: 600px; margin-right: 10px;"> <span
										class="text-orger text-pronce">注：</span> <span
										class="text-gery text-pronce">50字以内</span>
								</div>

							</dd>
						</dl>
					</li>

					<li>
						<dl class="dl-horizontal">
							<dt>添加使用人员：</dt>

							<dd style="width: 750px; margin-bottom: 20px">


								<div class="col-sm-2"
									style="margin-bottom: 20px; margin-left: -20px">
									<div class="s-addperson ">
										李易峰 <i class="icon-s icon-close "></i>
									</div>
								</div>


								<div class="col-sm-2">
									<a
										class="J_addDire btn btn-default ph-btn-bluee icon-tj col-sm-6"
										href="javascript:;"></a>
								</div>

							</dd>
						</dl>
					</li>
				</ul>

				<button class="btn btn-primary col-sm-1" style="margin-left: 180px">确定</button>
			</div>
			<!--orderobx end-->
		</div>
		<!--col end-->
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
	<script src="/static/income/main/index.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript">
		findMainPlanList(0);
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

		$('.J_addDire')
				.click(
						function() {
							var dirHtml = '<div class="col-sm-2"  style="margin-bottom: 20px;margin-left: -20px">'
									+ ' <div class="s-addperson ">'
									+ ' 李易峰'
									+ '  <i class="icon-s icon-close"></i>'
									+ ' </div>' + ' </div>';
							$(this).parents('.col-sm-2').before(dirHtml);
						});
	</script>

</body>

</html>