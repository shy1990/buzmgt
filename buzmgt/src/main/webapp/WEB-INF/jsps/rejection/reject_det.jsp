<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<title>订单详情</title>
	<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="static/css/common.css" />
	<link rel="stylesheet" type="text/css" href="static/receipt/order_detail.css"/>
	<link rel="stylesheet" type="text/css" href="static/incomeCash/css/income-cash.css"/>

	<style>
		.ph-img{
			text-align: center;
			color: #a7a7a7;
		}
	</style>
</head>
<body>
<div class="content main">
	<h4 class="page-header">
		<i class="ico icon-order-detail"></i>订单详情
		<i class="ico icon-back fl-right"></i>
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

								<span class="text-wsk" style="margin-left: -10px">${order.orderStatus}</span>
							</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>付款方式:</dt>
							<dd>${order.orderPayType}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>所属区域:</dt>
							<dd>${rejection.salesMan.region.parent.parent.parent.name} ${rejection.salesMan.region.parent.parent.name} ${rejection.salesMan.region.parent.name} ${rejection.salesMan.region.name}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>负责人:</dt>
							<dd>${order.salesMan.truename}</dd>
						</dl>
					</li>

					<li>
						<h5>订单明细</h5>
						<dl class="dl-horizontal">
							<dt>订单号:</dt>
							<dd>${rejection.orderno}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>商家名称:</dt>
							<dd>${rejection.shopName}</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>商品明细:</dt>
							<dd>
								<br>
								<br>
								<div class="table-task-list new-table-box table-overflow" style="min-height: auto;">
									<table class="table table-hover new-table">
										<thead>
										<tr>
											<th>类别</th>
											<th>名称</th>
											<th>单价</th>
											<th>数量</th>
											<th>合计</th>
											<th>提成</th>
										</tr>
										</thead>
										<tbody>
										<c:if test="${not empty orderItems}">
											<c:forEach var="item" items="${orderItems}" varStatus="s">
												<tr>
													<c:if test="${item.type == 'sku'}">
														<td>单品</td>
													</c:if>
													<c:if test="${item.type == 'accessories'}">
														<td>配件</td>
													</c:if>
													<c:if test="${item.type == 'gift'}">
														<td>赠品</td>
													</c:if>
													<c:if test="${item.type == 'point'}">
														<td>积分商品</td>
													</c:if>
													<td>${item.name}</td>
													<td>
														<span class="text-blue-s">${item.price}元</span>
													</td>
													<td><span class="text-blue">${item.nums}</span>部</td>
													<td><span class="text-redd">${item.amount}元</span></td>
													<td>
														<span class="text-blue-s">50.00元</span>
													</td>

												</tr>
											</c:forEach>
										</c:if>
										</tbody>
									</table>
								</div>


							</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>数量:</dt>
							<dd>
								<p class="fl">手机<span class="num">${order.phoneCount}</span>部</p>
								<p class="fl">配件<span class="num">${order.partsCount}</span>件</p>
							</dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>订单金额:</dt>
							<dd class="money">
								<span>${order.orderPrice}元</span>
							</dd>
						</dl>
					</li>
					<li>
						<h5>订单踪迹</h5>
						<dl class="dl-horizontal">
							<dt>下单时间:</dt>
							<dd><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
						</dl>
						<dl class="dl-horizontal">
							<dt>发货时间:</dt>
							<dd><fmt:formatDate value="${order.fastmailTime}" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
						</dl>
						<div class="double-info clearfix">
							<dl class="dl-horizontal fl">
								<dt>业务拒收时间:</dt>
								<dd><fmt:formatDate value="${rejection.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></dd>
							</dl>
						</div>
						<div class="double-info clearfix">
							<dl class="dl-horizontal fl">
								<dt>预计到达日期:</dt>
								<dd><span ><fmt:formatDate value="${rejection.arriveTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span></dd>
							</dl>
						</div>
						<div class="double-info clearfix">
							<dl class="dl-horizontal fl">
								<dt>寄回物流单号:</dt>
								<dd><span >${rejection.trackingno}</span></dd>
							</dl>
						</div>
						<div class="double-info clearfix">
							<dl class="dl-horizontal fl">
								<dt>拒收原因:</dt>
								<dd><span >${rejection.remark}</span></dd>
							</dl>
						</div>
					</li>
					<li>
						<dl class="dl-horizontal">
							<dt>货物照片:</dt>
							<dd>
								<div class="row">
									<div class="col-sm-6 col-md-4" style="width: 260px;height: 175px">
										<div class="thumbnail">
											<img src="${rejection.frontImgUrl}" alt="">
											<div class="caption">
												<p class="ph-img">正面照</p>
											</div>
										</div>
									</div>
								</div>

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
</body>
</html>