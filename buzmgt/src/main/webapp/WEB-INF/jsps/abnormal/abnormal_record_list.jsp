<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			System.out.print(basePath);
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>签收记录</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/kaohe/kaohe-det.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/ywmember.css" />
<link rel="stylesheet" type="text/css"
	href="static/abnormal/abnormal.css" />
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="ywSignfor-table-template" type="text/x-handlebars-template">
{{#each content}}
	<tr>
		<td class="text-strong">{{shopName}}</td>
		<td>{{orderNo}}</td>
		<td>{{yewuSignforGeopoint}}</td>
		<td>
			{{{whatYwTag creatTime yewuSignforTime}}}
			{{formDate yewuSignforTime}}
		</td>
		<td class="text-strong">
			{{#if yewuSignforTime}}
			<span class="status-over">已签收</span>
			{{else}}
			<span class="status-not">未签收</span>
			{{/if}}	
		</td>
		<td>
			<a class="btn btn-blue btn-sm" href="/ordersignfor/toAbnormalDet/{{id}}?type=ywSignfor&abnormal={{parament creatTime yewuSignforTime}}">查看</a>
			{{{whatOperate creatTime yewuSignforTime}}}
		</td>
	</tr>
{{/each}}
</script>
<script id="memberSignfor-table-template" type="text/x-handlebars-template">
	{{#each content}}
      <tr>
        <td class="text-strong">{{shopName}}</td>
        <td>{{orderNo}}</td>
        <td>
		{{{whatMemberTag customSignforException}}}
		{{customSignforGeopoint}}</td>
        <td>{{whatAging customSignforTime yewuSignforTime}}</td>
        <td>{{whatPayType orderPayType}}</td>
        <td>{{formDate customSignforTime}}</td>
        <td class="text-strong">
		  {{#if customSignforTime}}
		  <span class="status-over">已签收</span>
		  {{else}}
          <span class="status-not">未签收</span>
		  {{/if}}
		</td>
        <td><a class="btn btn-blue btn-sm" href="/ordersignfor/toAbnormalDet/{{id}}">查看</a>
        </td>
      </tr>
	{{/each}}
</script>
<script type="text/javascript">
var	base='<%=basePath%>';
var SearchData = {
		"size" : "1",
		"page" : "0",
	};
</script>
</head>

<body>
<!-- {{{whatOperate yewuSignforTime}}} -->
	<div class="content main">
		<h4 class="page-header J_UserID" data-user="${userId }">
			<i class="ico icon-abnormal_record"></i>签收记录
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="abnormal-body box border blue">
					<!--title-->
					<div class="box-title">
						<!--菜单栏-->
						<ul class="nav nav-tabs J_URL" data-tabs="${tabs }">
							<li class="active" data-tital="ywtab"><a href="#box_tab1" data-toggle="tab"><span
									class="">业务揽收</span></a></li>
							<li data-tital="membertab"><a href="#box_tab2" data-toggle="tab"><span class="">客户签收</span></a></li>
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
										type="text" id="startTime" class="form-control form_datetime input-sm"
										placeholder="开始日期" readonly="readonly">
								</div>
							</div>
							--
							<div class="search-date">
								<div class="input-group input-group-sm form_date_end">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" id="endTime" class="form-control form_datetime input-sm"
										placeholder="结束日期" readonly="readonly">
								</div>
							</div>
							<!--考核开始时间-->
							<button class="btn btn-blue btn-sm"
								onclick="goSearch();">
								检索</button>
							<!---->
							<div class="abnormal-details">
								<span>共<span class="text-bule">2580</span>单</span>
								<span>客户签收<span class="text-bule">2500</span>单</span>
								<span>拒收 <span class="text-bule">80</span>单</span>
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
									<table
										class="table table-hover new-table abnormal-record-table">
										<thead>
											<tr>
												<th>店铺名称</th>
												<th>订单号</th>
												<th>揽收地点</th>
												<th class="J_Gap" data-time="${timesGap }">揽收时间</th>
												<th>状态</th>
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
									<table
										class="table table-hover new-table abnormal-record-table">
										<thead>
											<tr>
												<th>店铺名称</th>
												<th>订单号</th>
												<th>签收地点</th>
												<th>送货时效</th>
												<th>支付方式</th>
												<th>签收时间</th>
												<th>状态</th>
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
			<!--col-md-9-->
			<div class="col-md-3">
				<%@ include file="../kaohe/right_member_det.jsp"%>
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
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="static/js/common.js" type="text/javascript"
			charset="utf-8"></script>
		<script src="/static/js/dateutil.js" type="text/javascript"
			charset="utf-8"></script>
		<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"></script>
		<script src="static/abnormal/abnormal_record_list.js"></script>
		<script type="text/javascript" src="static/bootStrapPager/js/extendPagination.js"></script>
		<script type="text/javascript">
				
			</script>
</body>

</html>
