<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>流水单号详情</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/incomeCash/css/income-cash.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		<tr>
      <td>{{serialNo}}</td>
      <td>{{userId}}</td>
      <td>{{salesMan.truename}}</td>
      <td class="bg-style">
				{{#each orderDetails}}
				{{#with cash}}{{#with order}}
        <p>{{orderNo}}</p>
				{{/with}}{{/with}}
				{{/each}}
      </td>
      <td class="bg-style">
        {{#each orderDetails}}
				{{#with cash}}{{#with order}}
        <p>{{orderPrice}}</p>
				{{/with}}{{/with}}
				{{/each}}
      </td>
      <td class="bg-style">
        {{#each orderDetails}}
				{{#with cash}}{{#with order}}
        <p>{{actualPayNum}}</p>
				{{/with}}{{/with}}
				{{/each}}
      </td>
      <td class="bg-style"><span class="text-redd">{{cashMoney}}</span></td>
      <td>{{{disposePayStatus payStatus}}}</td>
      <td>{{formDate createDate}}</td>
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
		'size' : '20'
	}
</script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header">
			<i class="ico icon-water"></i>流水单号详情
		</h4>
		<!---选择区域，选择日期-->
		<div class="row text-time">
			<span class="text-strong chang-time time-c">选择日期：</span>
			<div class="search-date">
				<div class="input-group input-group-sm form_date_start">
					<span class="input-group-addon " id="basic-addon1"><i
						class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
						type="text" id="startTime" class="form-control form_datetime input-sm"
						placeholder="开始日期" readonly="readonly">
				</div>
			</div>
			--
			<div class="search-date">
				<div class="input-group input-group-sm form_date_end">
					<span class="input-group-addon " id="basic-addon1"><i
						class="glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
						type="text" id="endTime" class="form-control form_datetime input-sm"
						placeholder="结束日期" readonly="readonly">
				</div>
			</div>
			<!--考核开始时间-->
			<button class="btn btn-blue btn-sm"
				onclick="goSearch();">检索</button>

			<div class="link-posit-t pull-right exc-hh">
				<input class="cs-select text-gery-hs" id="serialNo" value="${serialNo}" placeholder="请输入流水单号">
				<button class="btn btn-blue btn-sm"
					onclick="findBySerialNo();">检索</button>
				<a class="table-export" href="javascript:void(0);">导出excel</a>
			</div>

		</div>
		<!---选择区域，选择日期-->

		<div class="tab-content">

			<div class="table-task-list new-table-box table-overflow">
				<table class="table table-hover new-table tb-basin">
					<thead>
						<tr>
							<th>流水单号</th>
							<th>用户Id</th>
							<th>用户名称</th>
							<th>订单编号</th>
							<th>订单金额</th>
                            <th>实际金额</th>
                            <th>总金额</th>
							<th>状态</th>
							<th>日期</th>
						</tr>
					</thead>
					<tbody id="waterOrderList"> </tbody>
				</table>
			</div>
			<!--table-box-->
			<div id="addPager"></div>
			<!--油补记录-->
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
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="/static/js/dateutil.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/common.js"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript"
		src="/static/incomeCash/js/water-order-list.js" charset="utf-8"></script>
</body>

</html>