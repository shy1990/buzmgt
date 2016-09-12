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
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link href="static/bootStrapPager/css/page.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/static/incomeCash/css/income-cash.css">
<link rel="stylesheet" type="text/css" href="/static/income/income.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="user-table-template" type="text/x-handlebars-template">
{{#if content}}
	{{#each content}}
     <tr>
								<td class="th-with">
									<div class="row">
										<div class="col-sm-1 pic-jl">
											<div class="bs-example bs-example-images "
												data-example-id="image-shapes">
												<img data-holder-rendered="true"
													src="../static/img/task/mao.png"
													style="width: 50px; height: 50px;"
													data-src="holder.js/40x40" class="img-circle" alt="40x40">
											</div>
										</div>
										<div class="col-sm-11 text-jl">

											<span class="text-strong">{{truename}}</span>（{{rolename}}）<br>
											{{namepath}}
										</div>
									</div>
								</td>
								<td>{{month}}</td>
								<td><a href="/baseSalary/show?month={{month}}&&salesmanId={{userId}}"><span class="text-blue">{{basicSalary}}</span></a></td>
								<td><a href=""><span class="text-blue">{{busiIncome}}</span></a></td>
								<td><a href=""><span class="text-blue">{{oilIncome}}</span></a></td>
								<td><a href="/customTask/list?month={{month}}&&salesId={{userId}}"><span class="text-redd">{{punish}}</span></a></td>
								<td><a href=""><span class="text-blue">{{reachIncome}}</span></a></td>
								<td><a href=""><span class="text-blue">{{overlyingIncome}}</span></a></td>
								<td><a href=""><span class="text-green text-strong">{{allresult}}</span></a></td>
			{{#isfh state}}		
				<td><span class="yfh">已复核</span></td>						
			{{else}}
				<td><span class="wfh">未复核</span></td>
			{{/isfh}}
			{{#canfh state month}}	
								<td>
									<button class=" btn btn-blue btn-bluee" onclick="sendFh('{{id}}')">审核确定</button>
								</td>
			{{else}}
				<td></td>
			{{/canfh}}
							</tr>
	{{/each}}
{{/if}}

</script>
</head>
<body>
	<div class="content main">
		<h4 class="page-header">
			<i class="ico icon-shouyi"></i>收益 <a
				href="javascript:history.back();"><i
				class="ico icon-back fl-right"></i></a>

		</h4>

		<div class="row text-time">

			<div class="salesman" style="margin-top: 5px; overflow-x: hidden">
				<select class="box-sty-s" id="region">
					<option value="">-省区-</option>
					<c:forEach var="region" items="${regions}">
						<option value="${region.name}">${region.name}</option>
					</c:forEach>
				</select> <select class="box-sty-s" id="role">
					<option value=''>业务角色</option>
					<option value="262144">服务站经理</option>
					<option value="294914">扩展经理</option>
				</select>
				<div class="search-date">
					<div class="input-group input-group-sm">
						<span class="input-group-addon " id="basic-addon1"><i
							class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
							type="text" class="form-control form_datetime input-sm"
							placeholder="查询年月" readonly="readonly" />
					</div>
				</div>
				<button class="btn btn-blue btn-sm"
					style="height: 30px; margin-left: 10px" onclick="goSearch()">筛选</button>

				<div class="H-daochu">
					<div class="salesman">
						<input class="cs-select  text-gery-hs" id="truename"
							placeholder="请输入业务员名称" />
						<button class="btn btn-blue btn-sm" onclick="nameSearch()">检索</button>
					</div>
					<div class="link-posit-t pull-right export">
						<a class="table-export" href="javascript:dochu();">导出excel</a>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		<div class="tab-content">
			<!--待审核账单-->
			<div class="tab-pane fade in active">
				<!--table-box-->
				<div class="table-task-list new-table-box table-overflow">
					<table class="table table-hover new-table">
						<thead>
							<tr>
								<th>业务员</th>
								<th>月份</th>
								<th>保底佣金</th>
								<th>业务佣金</th>
								<th>油补佣金</th>
								<th>扣罚金额</th>
								<th>达量收益</th>
								<th>叠加收益</th>
								<th>合计</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id='userList'>
						</tbody>
					</table>
				</div>
				<!--table-box-->
			</div>
			<!--待审核账单-->
		</div>
		<div class="wait-page-index" id="usersPager"></div>
		<!--table-box-->
		<!--油补记录-->
	</div>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="/static/js/common.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script src='/static/js/dateutil.js'></script>
	<script src="/static/income/main/income.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript">
	var	base='<%=basePath%>';
	$(".form_datetime").datetimepicker({
        format: "yyyy-mm",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 3,
        minView: 3,
        pickerPosition: "bottom-left",
        forceParse: 0,

    });
		$(function() {
			//initDateInput();
			goSearch();
		});
	</script>

</body>

</html>