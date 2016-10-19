<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
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
	<title>业务对账单</title>

	<!-- Bootstrap -->
	<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="static/bootstrap/css/fileinput.css" rel="stylesheet">
	<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
		  rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="static/css/common.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/incomeCash/css/income-cash.css">
	<link rel="stylesheet" type="text/css"
		  href="static/bootStrapPager/css/page.css" />
	<script src="static/js/jquery/jquery-1.11.3.min.js"
			type="text/javascript" charset="utf-8"></script>
	<script id="bankTrade-table-template" type="text/x-handlebars-template">
		{{#if content}}
		{{#each content}}
		<tr>
			<td>{{salemanName}}</td>
			<td>{{regionName}}</td>
			<td>{{todayAllShouldPay}}</td>
			<td>{{todayShouldPay}}</td>
			<td>{{historyShouldPay}}</td>
			<td>{{todayDate}}</td>
			<td>

				<button class="btn btn-green btn-sm btn-w" data-toggle="modal"
						  data-target=""
						  onclick="view('{{userId}}','{{todayAllShouldPay}}','{{../name}}','{{cardNumber}}','{{bankName}}')">查看
			    </button>
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
	<h4 class="page-header">
		<i class="ico icon-puish-back"></i>业务对账单
	</h4>
	<!---选择日期-->
	<div class=" text-time">

		<span class="text-strong chang-time ">选择日期：</span>
		<div class="search-date">
			<div class="input-group input-group-sm">
					<span class="input-group-addon " id="basic-addon1"><i
							class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
					type="text" id="searchDate" class="form-control form_datetime input-sm"
					placeholder=""  readonly="readonly">
			</div>
		</div>

		<!--考核开始时间-->
		<button class="btn btn-blue btn-sm"
				onclick="goSearch();">检索</button>
		<!---->
		<div class="link-posit-t pull-right exc-hh">
			<input id="salesmanName" class="cs-select text-gery-hs" placeholder="请输入业务员名称">
			<button class="btn btn-blue btn-sm"
					onclick="findBySalesManName();">检索</button>
			<a id="table-export" class="table-export" href="javascript:void(0);">导出excel</a>
		</div>

	</div>
	<!---选择日期-->
	<div class="clearfix"></div>
	<div class="tab-content">
		<!--油补记录-->
		<div class="tab-pane fade in active" id="box_tab1">
			<!--table-box-->
			<div class="table-task-list new-table-box table-overflow">
				<table class="table table-hover new-table">
					<thead>
					<tr>
						<th>姓名</th>
						<th>所属区域</th>
						<th>当日应收总额</th>
						<th>当日待收</th>
						<th>历史拖欠金额</th>
						<th>日期</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody id="bankTradeList"></tbody>
				</table>
			</div>
			<!--table-box-->
			<div id="bankTradePager"></div>
		</div>
		<!--油补记录-->
	</div>

</div>
	<!---alert---->

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
	<script type="text/javascript"
			src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript"
			src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="/static/js/dateutil.js" type="text/javascript"
			charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
			charset="utf-8"></script>
	<script type="text/javascript"
			src="static/bootStrapPager/js/extendPagination.js"></script>
	<script src="static/js/jqueryfileupload/js/vendor/jquery.ui.widget.js"></script>
	<script src="static/js/jqueryfileupload/js/jquery.iframe-transport.js"></script>
	<script type="text/javascript"
			src="../static/receipt/bill_list.js" charset="utf-8"></script>
	<script type="text/javascript">

	</script>
</body>

</html>