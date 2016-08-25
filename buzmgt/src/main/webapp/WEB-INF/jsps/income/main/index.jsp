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
<style type="text/css">
.ztree {
	margin-top: 34px;
	border: 1px solid #ccc;
	background: #FFF;
	width: 100%;
	overflow-y: scroll;
	overflow-x: auto;
}

.menuContent {
	width: 100%;
	padding-right: 50px;
	display: none;
	position: absolute;
	z-index: 800;
}
</style>

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

	<div class="content main ">
		<h4 class="page-header">
			<i class="ico ico-account-manage-oil"></i>月任务扣罚设置
		</h4>
		<!--公里系数设置-->
		<div class="table-bordered bjc">
			<div class="table-responsive  ">
				<!--公里系数表头-->
				<div class="text-tx row-d">
					<span class="text-gery">缺少一次拜访扣罚：</span> <select id="defaultRate">
						<option value="20">20</option>
						<option value="30">30</option>
						<option value="40">40</option>
						<option value="50">50</option>
					</select> <span class="text-gery ">&nbsp;元</span> <span
						class="text-blue-s jl-">注：</span><span class="text-gery-hs">统默认所有区域均为该系数，自定义设置区域除外</span>
				</div>
				<div class="form-group">
					<label class="col-sm-4 control-label">选择区域：</label>
					<div class="col-sm-7">
						<select id="region" class="form-control input-h" name="regionId">
								<option value="">全部区域</option>
							<c:forEach var="region" items="${regions}">
								<option value="${region.id}">${region.name}</option>
							</c:forEach>
						</select>

					</div>
				</div>
				<!--设置扣罚系数表-->
				<div class="bs-example">
					<div id="acont" class="row"></div>
				</div>
				<div class="show-grid   row-jl ">
					<div class="col-md-5"></div>
					<div class="col-md-7  zdy-h ">
						<button class=" col-sm-3 btn  btn btn-default" type="button"
							data-toggle="modal" data-target="#zdyqy">添加区域</button>
					</div>
					<div id="abnormalCoordPager"></div>
				</div>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-offset-4 col-sm-4" style="margin-top: 20px">
				<button type="submit" onclick="saveDefault();"
					class="col-sm-12 btn btn-primary ">保存</button>
			</div>
		</div>

		<!-- /alert htmlg修改公里系数 -->
		<div id="changed" class="modal fade" role="dialog">
			<div class="modal-dialog " role="document">
				<div class="modal-content modal-blue">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h3 class="modal-title">修改</h3>
					</div>

					<div class="modal-body">
						<div class="container-fluid">
							<form class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-4 control-label">选择扣罚系数：</label>
									<div class="col-sm-7">
										<div class="input-group are-line">
											<span class="input-group-addon"><i
												class="icon icon-lk"></i></span> <select
												class="form-control input-h" id="select_modify"
												aria-describedby="basic-addon1">
												<option value="10">10</option>
												<option value="20">20</option>
												<option value="30">30</option>
												<option value="40">40</option>
											</select>
											<!-- /btn-group -->
										</div>
									</div>
									<div class="col-sm-1 control-label">
										<span>元</span>
									</div>
								</div>
								<div class="form-group">
									<div class="col-sm-offset-4 col-sm-4 ">
										<button id="set_a" type="submit"
											class="col-sm-12 btn btn-primary ">确定</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- /alert html -->


		<div id="zdyqy" class="modal fade" role="dialog">
			<div class="modal-dialog " role="document">
				<div class="modal-content modal-blue">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h3 class="modal-title">自定义区域</h3>
					</div>
					<div class="modal-body">
						<div class="container-fluid">
							<form id="addd" class="form-horizontal">
								<div class="form-group">
									<label class="col-sm-4 control-label">添加人员：</label>
									<div class="col-sm-7">
										<select id="region" class="form-control input-h"
											name="regionId">
											<c:forEach var="region" items="${regions}">
												<option value="${region.id}">${region.name}</option>
											</c:forEach>
										</select>

									</div>
								</div>
								<div id="xiugai">
									<div class="form-group">
										<label class="col-sm-4 control-label">扣罚系数：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-task-lk"></i></span> <select name="b" type=""
													class="form-control input-w"
													aria-describedby="basic-addon1" id="select">
													<option value="10">10</option>
													<option value="20">20</option>
													<option value="30">30</option>
													<option value="40">40</option>
													<option value="50">50</option>
												</select>
												<!-- /btn-group -->
											</div>
										</div>
										<div class="col-sm-1 control-label">
											<span>元</span>
										</div>
									</div>

									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-4 " id="href_div">
											<a herf="javascript:return 0;" onclick="addd(this)"
												class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<script src="static/js/jquery/jquery-1.11.3.min.js"></script>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
	<script
		src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
		type="text/javascript" charset="utf-8"></script>

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
	</script>

</body>

</html>