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
<title>订单列表</title>
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
<link rel="stylesheet" type="text/css" href="static/visit/visit.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="remarked-table-template" type="text/x-handlebars-template">
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
			{{{whatremarkStatus status}}}	
            <br /> <span class="text-bule">2016.03.12 18:20(缺少数据)</span> 
          </div>
        </td>
        <td><a class="btn btn-blue btn-sm" href="/receiptRemark/remarkList/{{id}}">查看</a>
			{{{whetherPunish status}}}
          </td>
      </tr>
	{{/each}}
</script>
<script id="allorder-table-template" type="text/x-handlebars-template">
	{{#each content}}
 		<tr>
      <td class="">{{orderNo}}</td>
      <td>{{#with salesMan}}{{truename}}{{/with}}</td>
      <td>{{shopName}}</td>
      <td>手机&nbsp;<span class="text-sblue">{{phoneCount}}</span>&nbsp;部 &nbsp; 
				配件<span class="text-sblue">&nbsp;{{whatPartsCount partsCount}}&nbsp;</span>件
      </td>
      <td><span class="text-sblue">11,268,39</span></td>
      <td>
			<span class="icon-tag-wfka">未付款</span>
			</td>
      <td><span class="text-sblues">{{whatOrderStatus fastmailTime orderStatus}}</span></td>
    </tr>
	{{/each}}
</script>
<script type="text/javascript">
var	base='<%=basePath%>';
	var SearchData = {
		'page' : '0',
		'size' : '10'
	}
</script>
<!-- <span class="icon-tag-wfka">未付款</span>
			<span class="icon-tag-wbb">未报备</span>
			<span class="icon-tag-ybb">已报备</span>
			<span class="icon-tag-yfka">收现金</span>
			<span class="text-sbluea">（已付款）</span>
			<span class="icon-tag-yfka">刷poss</span>
			<span class="icon-tag-yfka">网上支付</span> -->
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-visit-record"></i>订单列表
			<!--区域选择按钮-->
			<!--<div class="area-choose">
				选择区域：<span>山东省</span> <a class="are-line" href="javascript:;"
					onclick="">切换</a>
			</div>-->
			<!--/区域选择按钮-->
		</h4>
		<div class="row">
			<div class="col-md-12">
				<!--box-->
				<div class="abnormal-body box border blue">
					<!--title-->
					<div class="box-title">订单列表</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="marg-t text-time">
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
												<th>订单号</th>
												<th>业务名称</th>
												<th>商家名称</th>
												<th>商品数量</th>
												<th>交易额</th>
												<th>收款状况</th>
												<th>订单状态</th>
											</tr>
										</thead>
										<tbody id="allOrderList"></tbody>
										<!-- <tr>
											<td class="">20160112319160035</td>
											<td>易小星</td>
											<td>小米手机专卖店</td>
											<td>手机&nbsp;<span class="text-sblue">3</span>&nbsp;部
												&nbsp; 配件<span class="text-sblue">&nbsp;2&nbsp;</span>件
											</td>
											<td><span class="text-sblue">11,268,39</span></td>
											<td><span class="icon-tag-wfka">未付款</span></td>
											<td><span class="text-sblues">已出库</span></td>
										</tr>

										<tr>
											<td class="">20160112319160035</td>
											<td>易小星</td>
											<td>小米手机专卖店</td>
											<td>手机&nbsp;<span class="text-sblue">3</span>&nbsp;部
												&nbsp; 配件<span class="text-sblue">&nbsp;2&nbsp;</span>件
											</td>
											<td><span class="text-sblue">11,268,39</span></td>
											<td><span class="icon-tag-wbb">未报备</span></td>
											<td><span class="text-sblues">已出库</span></td>
										</tr>

										<tr>
											<td class="">20160112319160035</td>
											<td>易小星</td>
											<td>小米手机专卖店</td>
											<td>手机&nbsp;<span class="text-sblue">3</span>&nbsp;部
												&nbsp; 配件<span class="text-sblue">&nbsp;2&nbsp;</span>件
											</td>
											<td><span class="text-sblue">11,268,39</span></td>
											<td><span class="icon-tag-ybb">已报备</span></td>
											<td><span class="text-sblues">业务已揽收</span></td>
										</tr>

										<tr>
											<td class="">20160112319160035</td>
											<td>易小星</td>
											<td>小米手机专卖店</td>
											<td>手机&nbsp;<span class="text-sblue">3</span>&nbsp;部
												&nbsp; 配件<span class="text-sblue">&nbsp;2&nbsp;</span>件
											</td>
											<td><span class="text-sblue">11,268,39</span></td>
											<td><span class="icon-tag-yfka">收现金</span><span
												class="text-reda">（未付款）</span></td>
											<td><span class="text-sblues">客户已签收</span></td>
										</tr>

										<tr>
											<td class="">20160112319160035</td>
											<td>易小星</td>
											<td>小米手机专卖店</td>
											<td>手机&nbsp;<span class="text-sblue">3</span>&nbsp;部
												&nbsp; 配件<span class="text-sblue">&nbsp;2&nbsp;</span>件
											</td>
											<td><span class="text-sblue">11,268,39</span></td>
											<td><span class="icon-tag-yfka">收现金</span><span
												class="text-sbluea">（已付款）</span></td>
											<td><span class="text-sblues">客户已签收</span></td>
										</tr>


										<tr>
											<td class="">20160112319160035</td>
											<td>易小星</td>
											<td>小米手机专卖店</td>
											<td>手机&nbsp;<span class="text-sblue">3</span>&nbsp;部
												&nbsp; 配件<span class="text-sblue">&nbsp;2&nbsp;</span>件
											</td>
											<td><span class="text-sblue">11,268,39</span></td>
											<td><span class="icon-tag-yfka">刷poss</span></td>
											<td><span class="text-sblues">客户已签收</span></td>
										</tr>

										<tr>
											<td class="">20160112319160035</td>
											<td>易小星</td>
											<td>小米手机专卖店</td>
											<td>手机&nbsp;<span class="text-sblue">3</span>&nbsp;部
												&nbsp; 配件<span class="text-sblue">&nbsp;2&nbsp;</span>件
											</td>
											<td><span class="text-sblue">11,268,39</span></td>
											<td><span class="icon-tag-yfka">网上支付</span></td>
											<td><span class="text-sblues">客户已签收</span></td>
										</tr> -->
									</table>
								</div>
								<!--table-box-->
								<!-- 分页 -->
								<div id="allOrderPager"></div>
								<!-- 分页 -->
							</div>
							<!--业务揽收异常-->
						</div>
						<!--box-body-->
					</div>
					<!--box-->
				</div>
				<!--col-md-12-->
			</div>
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
			<script type="text/javascript" src="static/receipt/allorder-list.js"
				charset="utf-8"></script>
			<script type="text/javascript">
				
			</script>
</body>

</html>