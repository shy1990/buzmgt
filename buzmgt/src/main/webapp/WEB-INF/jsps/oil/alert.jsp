<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>设置油补系数</title>
<!-- Bootstrap -->

<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/account-manage/account-list.css" />
<link rel="stylesheet" type="text/css" href="static/task/task.css" />
<link rel="stylesheet" type="text/css" href="static/oil/css/oil.css" />

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>



<!-- ======================== -->
<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/zTree/css/zTreeStyle/zTreeStyle.css" />
<script src="/static/js/jquery/jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src='/static/js/common.js'></script>

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
	padding-right: 61px;
	display: none;
	position: absolute;
	z-index: 800;
}
</style>

</head>

<body>
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
					<form id="addoil" class="form-horizontal">
						<div class="form-group">
							<label for="" class="col-sm-4 control-label">选择区域：</label>
							<div class="col-sm-7">
								<div class="input-group are-line">
									<span class="input-group-addon"><i class="icon icon-qy"></i></span>

									<select id="region" class="form-control" name="regionId">
										<input id="n" type="hidden" value="${regionId}" />
									</select>
									<div id="regionMenuContent" class="menuContent">

										<ul id="regionTree" class="ztree"></ul>
									</div>
									<input type="hidden" id="towns" name="regionPid">
								</div>
							</div>
						</div>

						<div class="form-group">
							<label for="" class="col-sm-4 control-label">公里系数：</label>
							<div class="col-sm-7">
								<div class="input-group are-line">
									<span class="input-group-addon"><i
										class="icon icon-task-lk"></i></span> <select name="klmt" type=""
										class="form-control input-h" aria-describedby="basic-addon1">
										<option></option>
										<option>0.3</option>
										<option>0.5</option>
										<option>0.8</option>
										<option>1.0</option>
									</select>
									<!-- /btn-group -->
								</div>
							</div>
							<div class="col-sm-1 control-label">
								<span>元/km</span>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-4 ">
								<a herf="javascript:return 0;" onclick="addoil(this)"
									class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<!-- 出来区域节点树 -->
	<script src="/static/oil/js/oil-set-region.js" type="text/javascript"
		charset="utf-8"></script>
		
	


	<!--  暂时没有用
	<script src="/static/yw-team-member/team-memberForm.js"
		type="text/javascript" charset="utf-8"></script>
		 -->
	<script src="/static/yw-team-member/team-tree.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="/static/js/index.js" type="text/javascript"
		charset="utf-8"></script>

</html>