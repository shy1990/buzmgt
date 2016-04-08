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
<title>异常签收</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/abnormal/abnormal.css" />
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="ywSignfor-table-template" type="text/x-handlebars-template">
	{{#each content}}
	<tr>
		<td>
			<img width="50" height="50"src="static/img/abnormal/user-head.png" class="img-circle" />
			<div class="business-name">
				{{#with salesMan}}<span class="text-strong">{{truename}} </span>
				{{#with user}}
					{{#with organization}}({{name}})
					{{/with}}
				{{/with}} 
				{{/with}}<br /> {{shopName}}
			</div>
		</td>
		<td>{{orderNo}}</td>
		<td>山东省滨州市邹平县大桥镇223号</td>
		<td><span class="icon-tag-yc">异常</span>{{formDate yewuSignforTime}}</td>
		<td><a class="btn btn-blue btn-sm" href="/ordersignfor/showrecord/{{#with salesMan}}{{id}}{{/with}}">查看</a></td>				
	</tr>
	{{/each}}
</script>
<script id="memberSignfor-table-template" type="text/x-handlebars-template">
	{{#each content}}
	<tr>
		<td>
			<img width="50" height="50"src="static/img/abnormal/user-head.png" class="img-circle" />
			<div class="business-name">
				{{#with salesMan}}<span class="text-strong">{{truename}} </span>
				{{#with user}}
					{{#with organization}}({{name}})
					{{/with}}
				{{/with}} 
				{{/with}}<br /> {{shopName}}
			</div>
		</td>
		<td>{{orderNo}}</td>
		<td>{{shopName}}</td>
		<td><span class="icon-tag-yc">异常</span> 山东省滨州市邹平县大桥镇223号</td>
		<td>{{formDate yewuSignforTime}}</td>
		<td>{{aging}}</td>
		<td><a class="btn btn-blue btn-sm" href="/ordersignfor/showrecord/{{#with salesMan}}{{id}}{{/with}}?tabs=box_tab2">查看</a></td>				
	</tr>
	{{/each}}
</script>
<script type="text/javascript">
var	base='<%=basePath%>';
var SearchData = {
		"size" : "10",
		"page" : "0",
	};
</script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-abnormal"></i>异常签收
			<!--区域选择按钮-->
			<div class="area-choose">
				选择区域：<span>山东省</span> <a class="are-line" href="javascript:;"
					onclick="">切换</a>
			</div>
			<!--/区域选择按钮-->
		</h4>
		<div class="row">
			<div class="col-md-12">
				<!--box-->
				<div class="abnormal-body box border blue">
					<!--title-->
					<div class="box-title">
						<!--菜单栏-->
						<ul class="nav nav-tabs">
							<li class="active" data-tital="ywtab"><a href="#box_tab1" data-toggle="tab"><span
									class="">业务揽收异常</span></a></li>
							<li data-tital="membertab"><a href="#box_tab2" data-toggle="tab"><span class="">客户签收异常</span></a></li>
						</ul>
						<!--/菜单栏-->
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="marg-t text-time">
							<span class="text-strong chang-time">请选择时间：</span>
							<div class="search-date">
								<div class="input-group input-group-sm form_date_start">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" id="startTime" class="form-control input-sm "
										placeholder="开始日期" readonly="readonly">
								</div>
							</div>
							--
							<div class="search-date">
								<div class="input-group input-group-sm form_date_end">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" id="endTime" class="form-control form_date_end input-sm"
										placeholder="结束日期" readonly="readonly">
								</div>
							</div>
							<!--考核开始时间-->
							<button class="btn btn-blue btn-sm"
								onclick="goSearch();">
								检索</button>
							<!---->
							<div class="abnormal-details">
								<span>共 <span class="text-bule">${totalCount }</span> 单
								</span> <span>客户签收 <span class="text-bule">2500</span> 单
								</span> <span>拒收 <span class="text-bule">80</span> 单
								</span>
							</div>
							<div class="link-posit pull-right">
								<a class="table-export" href="javascript:void(0);">导出excel</a>
							</div>
						</div>
						<!--列表内容-->
						<div class="tab-content">
							<!--业务揽收异常-->
							<div class="tab-pane fade in active" id="box_tab1">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table class="table table-hover new-table abnormal-table">
										<thead>
											<tr>
												<th>业务名称</th>
												<th>订单号</th>
												<th>签收地址</th>
												<th>签收时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="ywOrderList"></tbody>
									</table>
								</div>
								<!--table-box-->
								<!-- 分页 -->
								<div id="ywPager"></div>
								<!-- 分页 -->
							</div>
							<!--业务揽收异常-->

							<!--客户签收异常-->
							<div class="tab-pane fade" id="box_tab2">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table class="table table-hover new-table abnormal-table">
										<thead>
											<tr>
												<th>业务名称</th>
												<th>订单号</th>
												<th>客户名称</th>
												<th>签收地址</th>
												<th>签收时间</th>
												<th>送货时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="memberOrderList"></tbody>
									</table>
								</div>
								<!--table-box-->
								<!-- 分页 -->
								<div id="memberPager"></div>
								<!-- 分页 -->
							</div>
							<!--客户签收异常-->
						</div>
						<!--列表内容-->
					</div>
					<!--box-body-->
				</div>
				<!--box-->
			</div>
			<!--col-md-12-->
		</div>
		<!--row-->
		<!-- Bootstrap core JavaScript================================================== -->
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="static/js/common.js" type="text/javascript"
			charset="utf-8"></script>
		<script src="/static/js/dateutil.js" type="text/javascript"
			charset="utf-8"></script>
		<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"></script>
		<script type="text/javascript" src="static/abnormal/abnormal_list.js"></script>
		<script type="text/javascript" src="static/bootStrapPager/js/extendPagination.js"></script>
		<script type="text/javascript">
			
		</script>
		
</body>

</html>
