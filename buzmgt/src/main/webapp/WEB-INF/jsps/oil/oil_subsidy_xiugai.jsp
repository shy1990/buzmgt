﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<body>

	<div class="content main">
		<h4 class="page-header">
			<i class="ico ico-account-manage-oil"></i>设置油补系数
		</h4>
		<div>
			<!-- Nav tabs -->
			<h4 class="text-hd">公里系数设置：</h4>
			<!-- Tab panes -->
			<!--全部--->
			<!--公里系数设置-->
			<div class="table-bordered bjc">
				<div class="tab-content-main">
					<div class="tab-pane active">
						<div class="table-responsive tb-b ">
							<!--公里系数表头-->
							<div class="text-tx row-d">
								<span class="text-gery">公里数系数：</span> <select id="default_km">
									<c:if test="${oilParameter != null }">
										<option selected="" value="${oilParameter.kmRatio }">${oilParameter.kmRatio }</option>
									</c:if>
									<option></option>
									<option value="0.5">0.5</option>
									<option value="0.6">0.6</option>
									<option value="0.7">0.7</option>
									<option value="0.8">0.8</option>
									<option value="0.9">0.9</option>
									<option value="1.0">1.0</option>
									<option value="1.1">1.1</option>
									<option value="1.2">1.2</option>
									<option value="1.3">1.3</option>
									<option value="1.4">1.4</option>
									<option value="1.5">1.5</option>
									<option value="1.6">1.6</option>
									<option value="1.7">1.7</option>
									<option value="1.8">1.8</option>
									<option value="1.9">1.9</option>
									<option value="2.0">2.0</option>
								</select> <span class="text-gery ">倍</span> <span class="text-blue-s jl-">注：</span><span
									class="text-gery-hs">系统默认所有区域均为改系数，自定义设置区域除外</span>
							</div>
							<!--设置公里系数表-->
							<%
							 	int s = 0;
							%>
							<div class="bs-example">
								<div id="acont" class="row">
									<!-- -------------------------------- -->

									<c:if test="${lists.size() > 0 }">
										<c:forEach var="oil" items="${lists }" varStatus="status">
											<c:if test="${oil.kmRatio !=null && oil.region.id != '0'}">

												<div class="col-sm-3 cl-padd">
													<div class="ratio-box">
														<div class="ratio-box-dd">
															<span class="label  label-blue"><%= ++s %></span> <span
																class="text-black jll">${oil.region.name }</span> <a
																class="text-redd jll" href="" data-toggle="modal"
																data-target="">${oil.kmRatio } 倍</a> <a
																class="text-blue-s jll" href="" data-toggle="modal"
																data-target=""
																onclick="modify('${oil.id}','${oil.kmOilSubsidy}','${oil.region.id }')">修改</a>
															<a class="text-blue-s jll" href="" data-toggle="modal"
																data-targt=""
																onclick="delete_byId('${oil.id}','${oil.region.id }')">删除</a>
														</div>
													</div>
												</div>
											</c:if>

										</c:forEach>
									</c:if>

									<!-- -------------------------------- -->
								</div>
							</div>
							<!--  -->
							<div class="row show-grid   row-jl ">
								<div class="col-md-5"></div>
								<div class="col-md-7 ">

									<button class=" col-sm-3 btn  btn btn-default" type="button"
										data-toggle="modal" data-target="#zdyqy">自定义设置区域</button>

								</div>
							</div>



						</div>
					</div>
				</div>
			</div>
			
			<div class="form-group ">
				<div class="row">
					<div class="col-sm-offset-4 col-sm-4" style="margin-top: 20px">
						<button type="submit" class="col-sm-12 btn btn-primary "
							id="button_bocun">保存</button>
					</div>
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
										<label class="col-sm-4 control-label">选择系数：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-lk"></i></span> <select type=""
													class="form-control input-h"
													aria-describedby="basic-addon1" id="select_modify">
													<option></option>
													<option value="0.5">0.5</option>
													<option value="0.6">0.6</option>
													<option value="0.7">0.7</option>
													<option value="0.8">0.8</option>
													<option value="0.9">0.9</option>
													<option selected="" value="1.0">1.0</option>
													<option value="1.1">1.1</option>
													<option value="1.2">1.2</option>
													<option value="1.3">1.3</option>
													<option value="1.4">1.4</option>
													<option value="1.5">1.5</option>
													<option value="1.6">1.6</option>
													<option value="1.7">1.7</option>
													<option value="1.8">1.8</option>
													<option value="1.9">1.9</option>
													<option value="2.0">2.0</option>
												</select>
												<!-- /btn-group -->
											</div>
										</div>
										<div class="col-sm-1 control-label">
											<span>倍</span>
										</div>
									</div>
									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-4 ">
											<!--  <button type="submit" class="col-sm-12 btn btn-primary " >确定</button>-->
											<a herf="javascript:return 0;" id="set_a"
												class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
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
										<label class="col-sm-4 control-label">选择区域：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-qy"></i></span> <select id="region"
													class="form-control" name="regionId">
													<input id="n" type="hidden" value="${regionId}" />
												</select>
												<div id="regionMenuContent" class="menuContent">

													<ul id="regionTree" class="ztree"></ul>
												</div>
												<input type="hidden" id="towns" name="regionPid">
												<!-- /btn-group -->
											</div>
										</div>
									</div>
									<div id="xiugai">
										<div class="form-group">
											<label class="col-sm-4 control-label">公里系数：</label>
											<div class="col-sm-7">
												<div class="input-group are-line">
													<span class="input-group-addon"><i
														class="icon icon-task-lk"></i></span> <select name="b" type=""
														class="form-control input-h"
														aria-describedby="basic-addon1" id="select">
														<option value="0.5">0.5</option>
														<option value="0.6">0.6</option>
														<option value="0.7">0.7</option>
														<option value="0.8">0.8</option>
														<option value="0.9">0.9</option>
														<option selected="" value="1.0">1.0</option>
														<option value="1.1">1.1</option>
														<option value="1.2">1.2</option>
														<option value="1.3">1.3</option>
														<option value="1.4">1.4</option>
														<option value="1.5">1.5</option>
														<option value="1.6">1.6</option>
														<option value="1.7">1.7</option>
														<option value="1.8">1.8</option>
														<option value="1.9">1.9</option>
														<option value="2.0">2.0</option>
													</select>
													<!-- /btn-group -->
												</div>
											</div>
											<div class="col-sm-1 control-label">
												<span>倍</span>
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

			<div id="zdyqyoil" class="modal fade" role="dialog"></div>
			<div id="changed1" class="modal fade" role="dialog">

			
			</div>








			<script src="static/js/jquery/jquery-1.11.3.min.js"></script>

			<!-- Include all compiled plugins (below), or include individual files as needed -->
			<script src="static/bootstrap/js/bootstrap.min.js"></script>
			<script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
			<script
				src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
				type="text/javascript" charset="utf-8"></script>


			<!------------------------------------------------------------------------------------------------------------------------ -->
			<script src="/static/zTree/js/jquery.ztree.all-3.5.js"
				type="text/javascript" charset="utf-8"></script>

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

			<script src="/static/oil/js/oil.js" type="text/javascript"
				charset="utf-8"></script>



			<script type="text/javascript">
				$(function resetTabullet() {
					$("#table").tabullet({
						data : source,
						action : function(mode, data) {
							console.dir(mode);
							if (mode === 'save') {
								source.push(data);
							}
							if (mode === 'edit') {
								for (var i = 0; i < source.length; i++) {
									if (source[i].id == data.id) {
										source[i] = data;
									}
								}
							}
							if (mode == 'delete') {
								for (var i = 0; i < source.length; i++) {
									if (source[i].id == data) {
										source.splice(i, 1);
										break;
									}
								}
							}
							resetTabullet();
						}
					});
					resetTabullet();
				});
			</script>
</body>

</html>