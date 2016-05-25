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
<script id="oilCost-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		<tr>
      <td>
        <img width="50" height="50" src="static/img/abnormal/user-head.png" class="img-circle" />
        <span class="yw-name"><b>{{salesManPart.truename}}</b>({{salesManPart.organizeName}})</span>
      </td>
      <td>{{salesManPart.regionName}}</td>
      <td>
        <p class="acu-km">{{totalDistance}}<span> km</span></p>
      </td>
      <td>
        <p class="acu-mny">{{oilTotalCost}}<span> 元</span></p>
      </td>
      <td>
        <a class="btn btn-blue btn-sm" href="javascript:void(0);" 
				onclick="turnRecord('{{whatUserId parentId userId}}','{{oilTotalCost}}','{{totalDistance}}');">查看</a>
      </td>
    </tr>      
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
<script id="abnormalCoord-table-template"
	type="text/x-handlebars-template">
{{#if content}}
	{{#each content}}
   <tr>
      <td class="">
        <img width="50" height="50" src="static/img/abnormal/user-head.png" class="img-circle" />
        <span class="yw-name"><b>{{salesManPart.truename}}</b>({{salesManPart.organizeName}})</span>
      </td>
      <td class="normal">
			{{#each oilRecordList}}
				{{{disposeRecordList regionType regionName exception}}}
			{{/each}}
      </td>
      <td>
        <p><span class="acu-km">{{distance}}km</span> &#47; <span class="acu-mny">{{oilCost}}元</span></p>
      </td>
      <td>2016.03.12 18:20</td>
      <td>
        <a class="btn btn-blue btn-sm" href="/oilCost/detail/{{id}}">查看</a>
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
				onclick="goSearch('${salesman.id}','${assess.id}');">检索</button>

			<div class="link-posit-t pull-right exc-hh">
				<input class="cs-select text-gery-hs" placeholder="请输入流水单号">
				<button class="btn btn-blue btn-sm"
					onclick="goSearch('${salesman.id}','${assess.id}');">检索</button>
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
							<th>订单编号</th>
							<th>需付金额</th>
							<th>总金额</th>
							<th>状态</th>
							<th>日期</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>258796124</td>
							<td class="bg-style">20160422655485</td>
							<td class="bg-style">2500.00</td>
							<td class="bg-style"><span class="text-redd">45000.00</span></td>
							<td><span class="icon-dsh">待审核</span></td>
							<td>2016.04.23 &nbsp;&nbsp;15:00</td>
						</tr>
						<tr>
							<td>258796124</td>
							<td class="bg-style">
								<p>20160422655485</p>
								<p>20160422655485</p>
								<p>20160422655485</p>
								<p>20160422655485</p>
								<p>20160422655485</p>
							</td>
							<td class="bg-style">
								<p>2500.00</p>
								<p>2500.00</p>
								<p>2500.00</p>
								<p>2500.00</p>
								<p>2500.00</p>
							</td>
							<td class="bg-style"><span class="text-redd">45000.00</span></td>
							<td><span class="icon-dsh">待审核</span></td>
							<td>2016.04.23 &nbsp;&nbsp;15:00</td>
						</tr>

						<tr>
							<td>258796124</td>
							<td class="bg-style">20160422655485</td>
							<td class="bg-style">2500.00</td>
							<td class="bg-style"><span class="text-redd">45000.00</span></td>
							<td><span class="icon-dsh">待审核</span></td>
							<td>2016.04.23 &nbsp;&nbsp;15:00</td>
						</tr>

						<tr>
							<td>258796124</td>
							<td class="bg-style">
								<p>20160422655485</p>
								<p>20160422655485</p>
								<p>20160422655485</p>
							</td>
							<td class="bg-style">
								<p>2500.00</p>
								<p>2500.00</p>
								<p>2500.00</p>
							</td>
							<td class="bg-style"><span class="text-redd">45000.00</span></td>
							<td><span class="icon-wfk">已审核</span></td>
							<td>2016.04.23 &nbsp;&nbsp;15:00</td>
						</tr>

					</tbody>
				</table>
			</div>
			<!--table-box-->
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
	<script type="text/javascript">
		
	</script>
</body>

</html>