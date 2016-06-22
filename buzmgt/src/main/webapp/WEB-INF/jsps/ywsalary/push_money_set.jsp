<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>提成设置</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css">
<link rel="stylesheet" type="text/css"
	href="static/ticheng/css/income-cash.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
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
	min-height: 50px;
	max-height: 400px;
}

.menuContent {
	width: 100%;
	padding-right: 50px;
	display: none;
	position: absolute;
	z-index: 800;
}
</style>
<script id="pushMoney-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		<tr>
      <td>{{category.name}}</td>
      <td>{{priceScope.min}}-{{priceScope.max}}元</td>
      <td>{{money}}元</td>
      <td>{{createDate}}</td>
      <td>
        <button class="xiugai  btn btn-blue btn-bluee" data-toggle="modal" data-target="#setRegionModal"
					data-pricescope="{{priceScope.min}}-{{priceScope.max}}元" data-category="{{category.name}}">设置区域属性</button>
        <button class=" btn btn-green btn-bluee " data-toggle="modal" data-target="#updModal">修改</button>
        <button class="btn btn-warning btn-bluee" data-toggle="modal" data-id="{{id}}" 
					data-pricescope="{{priceScope.min}}-{{priceScope.max}}元" data-category="{{category.name}}"
					data-money="{{money}} 元" data-target="#delModal">删除</button>
      </td>
    </tr>
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
<script type="text/javascript">
var	base='<%=basePath%>';
	var SearchData = {
		'page' : '0',
		'size' : '10'
	}
</script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-khcs"></i>提成设置
		</h4>

		<div class="clearfix"></div>

		<div class="tab-null">
			<form id="addForm" onsubmit="return false">
				<div class="box-right">
					<span class="text-blue">高级设置</span>
				</div>
				<div class="box-head">
					<span class="chang-time">选择类别：</span> <input type="hidden"
						name="type" value="0"> <select name="categoryId"
						class="cs-select text-gery-hs">
						<c:forEach items="${categories }" var="category">
							<option value="${category.id }">${category.name }</option>
						</c:forEach>
					</select>
				</div>

				<div class="box-head-s">
					<span class="chang-time">价格区间：</span> <select name="priceScopeId"
						class="cs-select text-gery-hs">
						<c:forEach items="${priceScopes }" var="priceScope">
							<option value="${priceScope.id }">${priceScope.min }-${priceScope.max }</option>
						</c:forEach>
					</select>
				</div>

				<div class="box-head-s">
					<span class="chang-time ">提成基数：</span> <input name="money"
						class="cs-select text-gery-hs" type="number" style="width: 108px;">
					<span class="chang-time">元/台</span>
					<button onclick="addPushMoney();" class="btn btn-blue btn-sm ">确定</button>
				</div>
			</form>
		</div>

		<div class="row text-time">
			<form id="searchForm" onsubmit="return false">
				<div class="salesman" style="margin-top: 5px;">
					<select name="categoryId" class="cs-select text-gery-hs"
						style="margin-right: 14px">
						<option value="" selected="selected">全部类别</option>
						<c:forEach items="${categories }" var="category">
							<option value="${category.id }">${category.name }</option>
						</c:forEach>
					</select> <select name="priceScopeId" class="cs-select text-gery-hs"
						style="margin-right: 14px">
						<option value="" selected="selected">按价格区间</option>
						<c:forEach items="${priceScopes }" var="priceScope">
							<option value="${priceScope.id }">${priceScope.min }-${priceScope.max }</option>
						</c:forEach>
					</select>
					<button class="btn btn-blue btn-sm" onclick="goSearch();">检索</button>
				</div>
			</form>
			<div class="link-posit-t pull-right export" style="margin-top: 20px">
				<a id="table-export" class="table-export" href="javascript:void(0);">导出excel</a>
			</div>
		</div>

		<div class="tab-content-new">


			<!--待审核账单-->
			<div class="tab-pane fade in active" id="box_tab1">
				<!--table-box-->
				<div class="table-task-list new-table-box table-overflow">
					<table class="table table-hover new-table">
						<thead>
							<tr>
								<th>类别</th>
								<th>产品价格</th>
								<th>提成</th>
								<th>设置日期</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="table-list">
						</tbody>
					</table>
				</div>
				<!--table-box-->
				<div id="initPage"></div>
			</div>
			<!--待审核账单-->
		</div>
		<!--table-box-->
		<!--油补记录-->
	</div>

	<!---alert删除--->
	<div id="delModal" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">是否删除</h3>
				</div>

				<div class="modal-body">
					<div class="container-fluid">
						<form class="form-horizontal">
							<input id="delId" type="hidden">
							<div class="form-group">
								<label class="col-sm-4 control-label text-dk">类别：</label>
								<div class="col-sm-8">
									<p id="delCategory" class="form-control-static text-bg"></p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label text-dk">价格区间：</label>
								<div class="col-sm-8">
									<p id="delPriceSocpe" class="form-control-static text-bg"></p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label text-dk">提成：</label>
								<div class="col-sm-8">
									<p id="delMoney" class="form-control-static text-bg"></p>
								</div>
							</div>

							<div class="btn-qx">
								<button type="button" onclick="delPushMoney();"
									class="btn btn-danger btn-d">删除</button>
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
	<!---alert删除--->

	<!---alert新增--->

	<div id="setRegionModal" class="modal fade" role="dialog">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title"></h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form id="addd" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">选择区域：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i class="icon icon-qy"></i></span>
										<select id="region" type="text" class="form-control input-h">
										</select>
										<div id="regionMenuContent" class="menuContent">
											<ul id="regionTree" class="ztree"></ul>
										</div>
										<input type="hidden" id="towns" name="regionPid">
										<!-- /btn-group -->
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label">提成：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i
											class="icon icon-task-je"></i></span> <input name="money"
											type="number" class="form-control input-h"
											aria-describedby="basic-addon1">
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
					<!--  -->
				</div>

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
						<div id="acont" class="row">
							<div class="col-sm-3 cl-padd">
								<div class="ratio-box">
									<div class="ratio-box-dd">
										<span class="label  label-blue">1</span> <span
											class="text-black jll">山东省莱芜市莱城区</span> <a
											class="text-redd jll" href="" data-toggle="modal"
											data-target="">30%</a> <a class="text-blue-s jll" href=""
											data-toggle="modal" data-target="#changed">修改</a> <a
											class="text-blue-s jll" href="" data-toggle="modal"
											data-targt="">删除</a>
									</div>
								</div>
							</div>
							<div class="col-sm-3 cl-padd">
								<div class="ratio-box">
									<div class="ratio-box-dd">
										<span class="label  label-blue">2</span> <span
											class="text-black jll">山东省莱芜市莱城区</span> <a
											class="text-redd jll" href="" data-toggle="modal"
											data-target="">30%</a> <a class="text-blue-s jll" href=""
											data-toggle="modal" data-target="#changed">修改</a> <a
											class="text-blue-s jll" href="" data-toggle="modal"
											data-targt="">删除</a>
									</div>
								</div>
							</div>
							<div class="col-sm-3 cl-padd">
								<div class="ratio-box">
									<div class="ratio-box-dd">
										<span class="label  label-blue">3</span> <span
											class="text-black jll">山东省莱芜市莱城区</span> <a
											class="text-redd jll" href="" data-toggle="modal"
											data-target="">30%</a> <a class="text-blue-s jll" href=""
											data-toggle="modal" data-target="#changed">修改</a> <a
											class="text-blue-s jll" href="" data-toggle="modal"
											data-targt="">删除</a>
									</div>
								</div>
							</div>
							<div class="col-sm-3 cl-padd">
								<div class="ratio-box">
									<div class="ratio-box-dd">
										<span class="label  label-blue">4</span> <span
											class="text-black jll">山东省莱芜市莱城区</span> <a
											class="text-redd jll" href="" data-toggle="modal"
											data-target="">30%</a> <a class="text-blue-s jll" href=""
											data-toggle="modal" data-target="#changed">修改</a> <a
											class="text-blue-s jll" href="" data-toggle="modal"
											data-targt="">删除</a>
									</div>
								</div>
							</div>
							<div class="col-sm-3 cl-padd">
								<div class="ratio-box">
									<div class="ratio-box-dd">
										<span class="label  label-blue">5</span> <span
											class="text-black jll">山东省莱芜市莱城区</span> <a
											class="text-redd jll" href="" data-toggle="modal"
											data-target="">30%</a> <a class="text-blue-s jll" href=""
											data-toggle="modal" data-target="#changed">修改</a> <a
											class="text-blue-s jll" href="" data-toggle="modal"
											data-targt="">删除</a>
									</div>
								</div>
							</div>
							<div class="col-sm-3 cl-padd">
								<div class="ratio-box">
									<div class="ratio-box-dd">
										<span class="label  label-blue">6</span> <span
											class="text-black jll">山东省莱芜市莱城区</span> <a
											class="text-redd jll" href="" data-toggle="modal"
											data-target="">30%</a> <a class="text-blue-s jll" href=""
											data-toggle="modal" data-target="#changed">修改</a> <a
											class="text-blue-s jll" href="" data-toggle="modal"
											data-targt="">删除</a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="show-grid   row-jl ">
						<div class="col-md-5"></div>
						<div class="col-md-7  zdy-h ">
							<button class=" col-sm-3 btn  btn btn-default" type="button"
								data-toggle="modal" data-target="#zdyqy">添加区域</button>
						</div>
					</div>
				</div>
				<!-- modal-body -->
			</div>
		</div>
	</div>

	<!---alert修改--->
	<div id="updModal" class="modal fade" role="dialog">
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
						<form id="addd5" class="form-horizontal">

							<div class="form-group">
								<label class="col-sm-4 control-label">提成：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i class="icon icon-yh"></i></span>
										<input name="money" id="updMoney" type="number"
											class="form-control input-h" aria-describedby="basic-addon1"
											placeholder="3000.00"> </input>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a herf="javascript:return 0;" onclick=""
										class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
								</div>
							</div>
						</form>
					</div>
				</div>
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
	<script type="text/javascript" src="static/js/common.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="/static/js/dateutil.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript" src="static/ticheng/js/push-money.js">
		
	</script>
	<script src="static/zTree/js/jquery.ztree.all-3.5.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="static/yw-team-member/team-tree.js" type="text/javascript"
		charset="utf-8"></script>
	<!-- 出来区域节点树 -->
	<script src="static/ticheng/js/pushmoney-set-region.js"
		type="text/javascript" charset="utf-8"></script>

</body>

</html>