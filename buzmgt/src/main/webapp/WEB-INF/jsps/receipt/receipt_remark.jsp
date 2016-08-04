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
<title>收款异常订单</title>
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
<link rel="stylesheet" type="text/css" href="static/task/task.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
	<style>
		.mark-red{
			position: absolute;
			top: 2px;
			right:8px;
			display: block;
			border-radius: 2px;
			-moz-border-radius: 2px;
			-webkit-border-radius: 2px;
			font-size: 6px;
			line-height: 15px;
			padding: 0px 2px;
			background: #FF4647;
			color: #ffffff;
		}

	</style>
<script id="remarked-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
      <tr>
        <td class="">
		  <img width="108" height="72" src="{{aboveImgUrl}}" class="img-goods" />
          <img width="108" height="72" src="{{frontImgUrl}}" class="img-goods" />
          <img width="108" height="72" src="{{sideImgUrl}}" class="img-goods" />
        </td>
        <td class="">{{#with salesMan}}{{truename}}{{/with}}</td>
        <td class="">{{shopName}}</td>
        <td> {{orderno}}</td>
        <td>{{#with order}}{{orderPrice}}{{/with}}</td>
        <td>{{remark}}</td>
        <td>{{formDate createTime}}</td>
        <td>
          <div class="pay-time-box">
					{{#if order.customSignforTime}}	
						<span class="pay-time icon-tag-yfk">已付款</span>
					{{else}}
						<span class="pay-time icon-tag-wfk">未付款</span>
					{{/if}}
            <br /> <span class="text-bule">{{#with order}}{{formDate customSignforTime}}{{/with}}</span> 
          </div>
        </td>
        <td><a class="btn btn-blue btn-sm" href="/receiptRemark/remarkList/{{id}}">查看</a>
			{{{whetherPunish status}}}
          </td>
      </tr>
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
<script id="cash-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
    <tr>
			{{#with order}}
      <td class="">{{#with salesMan}}{{truename}}{{/with}}</td>
      <td class="">{{shopName}}</td>
      <td>{{orderNo}}</td>
      <td>{{orderPrice}}</td>
      <td>
				{{{isException customSignforException}}}
				{{customSignforGeopoint}}
			</td>
			{{/with}}
      <td>{{formDate createDate}}</td>
      <td>
        <div class="pay-time-box">
					{{#if payDate}}
					<span class="pay-time icon-tag-yfk">已付款</span> 
					{{else}}
     	    <span class="pay-time icon-tag-wfk">未付款</span> 
					{{/if}}	
					{{{isTimeOutPlant isTimeOut payDate}}}
					
     	  </div>
      <td>
				<a class="btn btn-blue btn-sm" href="/receiptRemark/cash/{{cashId}}">查看</a>
    </tr>
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
<script id="notremarked-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
	<tr>
		<td class="">{{#with salesMan}}{{truename}}{{/with}}</td>
		<td class="">{{shopName}}</td>
		<td>{{orderNo}}</td>
		<td>{{orderPrice}}</td>
		<td>{{formDate createTime}}</td>
		<td>{{formDate fastmailTime}}</td>
		<td>{{formDate yewuSignforTime}}</td>
		<td>
			<div class="pay-time-box">
				{{#if customSignforTime}}
				<span class="pay-time icon-tag-yfk">已付款</span>
				<span class="text-red">超时</span> <br />
				<span class="text-red">{{formDate customSignforTime}}</span>
				{{else}}
				<span class="pay-time icon-tag-wfk">未付款</span>
				<span class="text-red">超时</span>
				{{/if}}
			</div>
		</td>
		<td><a class="btn btn-blue btn-sm" href="/receiptRemark/notRemarkList/{{orderNo}}">查看</a>
			<a class="btn btn-yellow btn-sm" href="javascrip:;">扣罚</a></td>
	</tr>
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
<script id="rejected-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
	<tr>
		<td class="">
			<img width="108" height="72" src="{{frontImgUrl}}" class="img-goods" />
		</td>
		<td class="">
			{{shopName}}
		</td>
		<td>{{orderno}}</td>
		<td>{{formDate arriveTime}}</td>
		<td>{{trackingno}}</td>
		<td>{{remark}}</td>
		<td>{{formDate createTime}}</td>
		<td><a class="btn btn-blue btn-sm" href="/rejection/{{id}}">查看</a>
			{{#if view}}
			<a class="btn btn-green btn-sm" href="javascript:;">已收货</a>
			{{else}}
			<a class="btn btn-green btn-sm" href="javascript:;" onclick="confirm('{{id}}')" id="{{id}}">确认收货</a>
			{{/if}}
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
		'page':'0',
		'size':'20'
	}
</script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header">
			<i class="ico icon-abnormal_order"></i>收款异常订单
				<!--区域选择按钮-->
        <div class="area-choose">
            选择区域：<span>${regionName }</span>
            <a class="are-line" onclick="getRegion(${regionId});" href="javascript:;">切换</a>
           	<input type="hidden" id="regionId" value="${regionId }">
           	<input type="hidden" id="regionType" value="${regionType }">
        </div>
        <!--/区域选择按钮-->
 		</h4>
		<div class="row">
			<div class="col-md-12">
				<!--box-->
				<div class="abnormal-body box border blue">
					<!--title-->
					<div class="box-title">
						<div class="row">
							<div class="col-sm-8 col-md-6">
								<!--菜单栏-->
								<ul id="receiptOrderStatus" class="nav nav-tabs">
									<li class="active" data-tital="reported"><a href="#box_tab1" data-toggle="tab" onclick="show();"><span
											class="">报备</span></a></li>
									<li data-tital="cash"><a href="#box_tab2" data-toggle="tab" onclick="show();"><span
											class="">收现金</span></a></li>
									<li data-tital="notreported"><a href="#box_tab3" data-toggle="tab" onclick="show();"><span
											class="">未报备<!-- <span class="mark-red">4</span> --></span></a></li>
									<li data-tital="rejected"><a href="#box_tab4" data-toggle="tab" onclick="hide();"><span class="">拒收</span></a></li>
								</ul>
								<!--/菜单栏-->
							</div>
							<!--col-->
							<div class="col-sm-4 col-md-6">
								<!--form-group-->
								<a class="remark-title-link" href="/receiptRemark/allOrderList">所有订单列表</a>
								<div class="form-group title-form remark-title">
									<div class="input-group ">
										<input type="text" class="form-control"
											id="orderNo" placeholder="请输入订单号">
										<a class="input-group-addon"onclick="findByOrderNo();"> <i
											class="icon icon-finds"></i>
										</a>
									</div>
								</div>
								<!--form-group-->
							</div>
							<!--col-->
						</div>
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="table-before">
								<ul class="nav nav-task" id="term">
									<li class="active" data-item="all"><a href="javascript:;">全部</a></li>
									<li data-item="unpay"><a href="javascript:;">未支付</a></li>
									<li data-item="timeout"><a href="javascript:;">已超时</a></li>
									<li data-item="payed"><a href="javascript:;">已支付</a></li>
								</ul>
							<script type="text/javascript">
								$('.nav-task li').click(function() {
									$(this).addClass('active');
									$(this).siblings('li').removeClass('active');
								});
							</script>
							<div class="search-date-box">
								<span class="text-strong chang-time">请选择时间：</span>
								<div class="search-date">
									<div class="input-group input-group-sm">
										<span class="input-group-addon " id="basic-addon1"><i
											class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
											type="text" id="startTime" class="form-control form_datetime input-sm"
											placeholder="开始日期" readonly="readonly">
									</div>
								</div>
								--
								<div class="search-date">
									<div class="input-group input-group-sm">
										<span class="input-group-addon " id="basic-addon1"><i
											class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
											type="text" id="endTime" class="form-control form_datetime input-sm"
											placeholder="结束日期" readonly="readonly">
									</div>
								</div>
								<!--考核开始时间-->
								<button class="btn btn-blue btn-sm"
									onclick="goSearch('${salesman.id}','${assess.id}');">
									检索</button>
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
									<table class="table table-hover new-table abnormal-order-table">
										<thead>
											<tr>
												<th>包裹照片</th>
												<th>业务名称</th>
												<th>店铺名称</th>
												<th>订单号</th>
												<th>金额</th>
												<th>理由</th>
												<th>报备时间</th>
												<th>客户签收时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="remarkedList"></tbody>
									</table>
								</div>
								<!--table-box-->
								<!-- 分页 -->
								<div id="remarkedPager"></div>
								<!-- 分页 -->
							</div>
							<!--业务揽收异常-->

							<!--收现金-->
							<div class="tab-pane fade" id="box_tab2">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table class="table table-hover new-table ">
										<thead>
											<tr>
												<th>业务名称</th>
												<th>店铺名称</th>
												<th>订单号</th>
												<th>金额</th>
												<th>客户签收坐标</th>
												<th>收现金时间</th>
												<th>打款时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="cashList"></tbody>
									</table>
								</div>
								<!--table-box-->
								<!-- 分页 -->
								<div id="cashPager"></div>
								<!-- 分页 -->
							</div>
							<!--收现金-->
							<!--未报备-->
							<div class="tab-pane fade" id="box_tab3">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table class="table table-hover new-table ">
										<thead>
											<tr>
												<th>业务名称</th>
												<th>店铺名称</th>
												<th>订单号</th>
												<th>金额</th>
												<th>下单时间</th>
												<th>发货时间</th>
												<th>业务签收时间</th>
												<th>打款时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="notRemarkedList"></tbody>
									</table>
								</div>
								<!--table-box-->
								<!-- 分页 -->
								<div id="notRemarkedPager"></div>
								<!-- 分页 -->
							</div>
							<!--未报备-->
							<!--拒收-->
							<div class="tab-pane fade" id="box_tab4">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table class="table table-hover new-table abnormal-order-table">
										<thead>
										<tr>
											<th>货物照片</th>
											<th>商家名称</th>
											<th>订单号</th>
											<th>预计到达时间</th>
											<th>寄回物流单号</th>
											<th>拒收原因</th>
											<th>拒收时间</th>
											<th>操作</th>
										</tr>
										</thead>
										<tbody id="rejectedList"></tbody>
									</table>
								</div>
								<!--table-box-->
								<!-- 分页 -->
								<div id="rejectedPager"></div>
								<!-- 分页 -->
							</div>
							<!--拒收-->
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
		<script type="text/javascript" src="static/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script type="text/javascript" src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="/static/js/dateutil.js" type="text/javascript"
			charset="utf-8"></script>
		<script type="text/javascript" src="static/js/common.js" charset="utf-8"></script>
		<script type="text/javascript" src="static/js/handlebars-v4.0.2.js" charset="utf-8"></script>
		<script type="text/javascript" src="static/bootStrapPager/js/extendPagination.js"></script>
		<script type="text/javascript" src="static/receipt/receipt-remark.js" 
			charset="utf-8"></script>
		<script type="text/javascript">

		</script>
</body>

</html>