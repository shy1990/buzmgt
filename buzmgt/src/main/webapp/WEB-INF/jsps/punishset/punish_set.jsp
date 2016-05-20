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
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>扣罚设置</title>

<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css">
<link rel="stylesheet" type="text/css"
	href="static/incomeCash/css/income-cash.css">

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<!---扣罚设置表头-->
	<div class="content main">
		<h4 class="page-header">
			<i class="ico icon-puish-record"></i>扣罚设置

		</h4>

		<h4 class="text-hd">扣罚系数设置：</h4>

		<!----扣罚设置--->
		<div class="table-bordered bg-color">
			<div class="table-responsive  ">
				<!--公里系数表头-->
				<div class="text-tx row-d">
					<span class="text-gery">扣罚系数：</span> <select>
						<option>20</option>
						<option>30</option>
						<option>40</option>
						<option>50</option>
					</select> <span class="text-gery ">&nbsp;%</span> <span
						class="text-blue-s jl-">注：</span><span class="text-gery-hs">统默认所有区域均为改系数，自定义设置区域除外</span>
				</div>
				<!--设置公里系数表-->
				<div class="bs-example">
					<div id="acont" class="row"></div>
				</div>
				<div class="show-grid   row-jl ">
					<div class="col-md-5"></div>
					<div class="col-md-7  zdy-h ">
						<button class=" col-sm-3 btn  btn btn-default" type="button"
							data-toggle="modal" data-target="#zdyqy">添加区域</button>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-4" style="margin-top: 40px">
					<button type="submit" class="col-sm-12 btn btn-primary ">保存</button>
				</div>
			</div>
		</div>
		<!--扣罚设置--->
	</div>
	<!---扣罚设置表头-->

	<!-- / alert 修改--->
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
								<label class="col-sm-4 control-label">扣罚系数：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i
											class="icon icon-task-je"></i></span> <select type=""
											class="form-control input-h" aria-describedby="basic-addon1">
											<option></option>
											<option>10</option>
											<option>20</option>
											<option>30</option>
											<option>40</option>
										</select>
										<!-- /btn-group -->
									</div>
								</div>
								<div class="col-sm-1 control-label">
									<span>%</span>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<button type="submit" class="col-sm-12 btn btn-primary ">确定</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- / alert 修改--->


	<!---alert自定义---->
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

									<span class="input-group-addon"><i class="icon icon-qy"></i></span>
									<select id="region" class="form-control input-h"
										name="regionId">
										<input id="n" type="hidden" value="${regionId}" />
									</select>
									</div>
									<div id="regionMenuContent" class="menuContent">

										<ul id="regionTree" class="ztree"></ul>
									</div>
									<input type="hidden" id="towns" name="regionPid">



									<!-- 
                                <div class="input-group are-line">
                                    <span class="input-group-addon"><i class="icon icon-qy"></i></span>
                                    <select name="a" type="" class="form-control input-h"
                                            aria-describedby="basic-addon1">
                                        <option></option>
                                        <option>山东省济南市天桥区</option>
                                        <option>山东省莱芜市莱城区</option>
                                        <option>山东省青岛市四方区</option>
                                        <option>山东省泰安新泰区</option>
                                    </select>
                                </div>
                                 -->
								</div>














							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label">扣罚金额：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i
											class="icon icon-task-je"></i></span> <select name="b" type=""
											class="form-control input-h" aria-describedby="basic-addon1">
											<option></option>
											<option>20%</option>
											<option>30%</option>
											<option>40%</option>
											<option>50%</option>
										</select>
										<!-- /btn-group -->
									</div>
								</div>
								<div class="col-sm-1 control-label">
									<span>%</span>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a herf="javascript:return 0;" onclick="addd(this)"
										class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!---alert自定义---->

	<!-- Include all compiled plugins (below), or include individual files as needed -->



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
	<script src="/static/js/common.js" type="text/javascript"
		charset="utf-8"></script>









	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
	<script
		src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
		type="text/javascript" charset="utf-8"></script>
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

		function addd(toil) {
			console.log($("#addd").serializeArray());
			addCustom($("#addd").serializeArray());
		}
		var i = 1;

		function addCustom(o) {
			var qy = o[0]["value"];
			var glxs = o[1]["value"];
			var html = '<div class="col-sm-3 cl-padd">'
					+ '                <div class="ratio-box">'
					+ '                <div class="ratio-box-dd">'
					+ '                <span class="label  label-blue">'
					+ (i++)
					+ '</span>'
					+ '                <span class="text-black jll">'
					+ qy
					+ '</span>'
					+ '                <a class="text-redd jll" href="" data-toggle="modal"' +
                '        data-target="">'
					+ glxs
					+ '</a>'
					+ '        <a class="text-blue-s jll" href="" data-toggle="modal"' +
                '        data-target="#changed">修改</a>'
					+ '                <a class="text-blue-s jll" href="" data-toggle="modal" data-targ' +
                't="">删除</a>'
					+ '                </div>' + '                </div>'
					+ '                </div>';
			$("#acont").append(html);
		};
	</script>

</body>
</html>
