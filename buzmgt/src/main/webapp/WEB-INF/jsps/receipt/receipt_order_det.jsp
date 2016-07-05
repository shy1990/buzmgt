<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
      + "/";
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>订单详情</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/receipt/order_detail.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header">
			<i class="ico icon-order-detail"></i>订单详情
		</h4>
		<!--row begin-->
		<div class="row">
			<!--col begin-->
			<div class="col-md-12">
				<!--orderbox begin-->
				<div class="order-box">
					<ul>
						<li>
							<dl class="dl-horizontal">
								<dt>订单状态:</dt>
								<dd>
									<c:if test="${order.orderStatus =='客户签收' }">
										<span class="signed">客户已签收</span>
									</c:if>
									<c:if test="${order.orderStatus !='客户签收' }">
										<span class="wait-sign">等待客户签收</span>
									</c:if>
								</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>付款方式:</dt>
								<dd>${order.orderPayType }</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>所属区域:</dt>
								<dd>${order.salesMan.region.parent.name }${order.salesMan.region.name }</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>负责人:</dt>
								<dd>${order.salesMan.truename }</dd>
							</dl>
						</li>
						<li>
							<h5>订单明细</h5>
							<dl class="dl-horizontal">
								<dt>订单号:</dt>
								<dd>${order.orderNo }</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>商家名称:</dt>
								<dd>${order.shopName }</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>商品明细:</dt>
								<dd>
									<div class="table-task-list new-table-box table-overflow" style="min-height: 80px;">
										<table class="table table-hover new-table" style="margin-bottom: 0">
											<thead>
												<tr>
													<th>名称</th>
													<th>单价</th>
													<th>数量</th>
													<th>合计</th>
												</tr>
											</thead>
											<tbody>
											<c:forEach var="item" items="${order.items }" varStatus="i">
												<tr>
													<td>${item.name }</td>
													<td><span class="text-blue-s">${item.price }元</span></td>
													<td><span class="text-blue">${item.nums }</span>部</td>
													<td><span class="text-redd">${item.amount }元</span></td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
									</div>
								</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>数量:</dt>
								<dd>
									<p class="fl">
										手机<span class="num">${order.phoneCount }</span>部
									</p>
									<p class="fl">
										配件<span class="num">${order.partsCount }</span>件
									</p>
								</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>订单金额:</dt>
								<dd class="money">
									<span>${order.orderPrice }元</span>
								</dd>
							</dl>
						</li>
						<li>
							<h5>订单踪迹</h5>
							<dl class="dl-horizontal">
								<dt>下单时间:</dt>
								<dd>${order.createTime }</dd>
							</dl>
							<dl class="dl-horizontal">
								<dt>发货时间:</dt>
								<dd>${order.fastmailTime}</dd>
							</dl>
							<div class="double-info clearfix">
								<dl class="dl-horizontal fl">
									<dt>业务签收时间:</dt>
									<dd>${order.yewuSignforTime }</dd>
								</dl>
								<dl class="dl-horizontal fl">
									<dt>签收地点：</dt>
									<dd>
										<c:if test="${empty order.yewuSignforGeopoint }">
											<span class="temp-no">暂无</span>
										</c:if>
										<c:if test="${!empty order.yewuSignforGeopoint }">${order.yewuSignforGeopoint }</c:if>
									</dd>
								</dl>
							</div>
							<div class="double-info clearfix">
								<dl class="dl-horizontal fl">
									<dt>报备时间:</dt>
									<dd>
										<span class="temp-no">暂无</span>
									</dd>
								</dl>
								<dl class="dl-horizontal fl">
									<dt>报备地点：</dt>
									<dd>
										<span class="temp-no">暂无</span>
									</dd>
								</dl>
							</div>
							<div class="double-info clearfix">
								<dl class="dl-horizontal fl">
									<dt>客户签收时间:</dt>
									<dd>
										<c:if test="${empty order.customSignforTime }">
											<span class="temp-no">暂无</span>
										</c:if>
										<c:if test="${!empty order.customSignforTime }">${order.customSignforTime }</c:if>
									</dd>
								</dl>
								<dl class="dl-horizontal fl">
									<dt>签收地点：</dt>
									<dd>
										<c:if test="${empty order.customSignforGeopoint }">
											<span class="temp-no">暂无</span>
										</c:if>
										<c:if test="${!empty order.customSignforGeopoint }">${order.customSignforGeopoint }</c:if>
									</dd>
								</dl>
							</div>
							<div class="double-info clearfix">
								<dl class="dl-horizontal fl">
									<dt>付款时间:</dt>
									<dd>
										<span class="temp-no">暂无</span>
									</dd>
								</dl>
								<dl class="dl-horizontal fl">
									<dt>支付方式：</dt>
									<dd>
										<c:if test="${order.orderPayType=='未支付' }">
											<span class="temp-no">暂无</span>
										</c:if>
										<c:if test="${order.orderPayType!='未支付' }">
											<span>${order.orderPayType }</span>
										</c:if>
									</dd>
								</dl>
							</div>
						</li>
						<li>
							<dl class="dl-horizontal">
								<dt>业务送货时效:</dt>
								<dd>
									<c:if test="${empty order.aging }">
										<span class="temp-no">暂无</span>
									</c:if>
									<c:if test="${!empty order.aging }">${order.aging }</c:if>
								</dd>
							</dl>
						</li>
					</ul>
				</div>
				<!--orderobx end-->
			</div>
			<!--col end-->
		</div>
		<!--row end-->
	</div>
	<!--main end-->

	<!-- Bootstrap core JavaScript================================================== -->
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="/static/js/dateutil.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/common.js"
		charset="utf-8"></script>
</body>

</html>