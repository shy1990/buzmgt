<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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
<title>报备明细</title>
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
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-visit-task"></i>报备明细
		</h4>
		<div class="row">
			<div class="col-md-12">
				<!--box-->
				<div class="abnormal-body box border blue">
					<!--title-->
					<div class="box-title">
						<i class="ico icon-shop-name"></i>${receiptRemark.shopName }
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="text-hor">
							<label>业务名称：</label>
							<p>${receiptRemark.salesMan.truename }</p>
						</div>
						<div class="text-hor">
							<label>报备时间：</label>
							<p>${receiptRemark.createTime}</p>
						</div>
						<div class="text-hor">
							<label>收款时间：</label>
							<p> 
							<c:if test="${!empty receiptRemark.order.customSignforTime }">${receiptRemark.order.customSignforTime }</c:if>
							<c:if test="${empty receiptRemark.order.customSignforTime }">暂无</c:if>
							 </p>
						</div>
						<%-- <div class="text-hor">
							<label>所属区域：</label>
							<p>${receiptRemark.createTime}</p>
						</div> --%>
						<div class="text-hor">
							<label>报备订单：</label>
							<p>${receiptRemark.orderno}
							<c:if test="${receiptRemark.status == '未付款' }">
									<span class="icon-tag-wfk">未付款</span>
							</c:if>
							<c:if test="${receiptRemark.status == '已付款' }">
									<span class="icon-tag-yfk">已付款</span>
							</c:if>
							<c:if test="${receiptRemark.status == '超时未付款' }">
									<span class="icon-tag-wfk">未付款</span><span class="text-reed">超时</span>
							</c:if>
							<c:if test="${receiptRemark.status == '超时已付款' }">
									<span class="icon-tag-yfk">已付款</span><span class="text-reed">超时</span>
							</c:if>
							</p>
						</div>
						<div class="text-hor">
							<label>订单金额：</label>
							<p class="text-blue">${receiptRemark.order.orderPrice} 元</p>
						</div>
						<div class="text-hor">
							<label>包裹照片：</label>
							<p></p>
						</div>
						<div class="text-hor">
							<label></label>
							<p>
							<div class="img-box"> 
								<img class="visit-img" src="${receiptRemark.aboveImgUrl}" alt="上面" /> 
							</div>
							<div class="img-box">
								<img class="visit-img" src="${receiptRemark.frontImgUrl}" alt="前面" />
							</div>
							<div class="img-box">
								<img class="visit-img" src="${receiptRemark.sideImgUrl}" alt="侧面" />
							</div>
							</p>
						</div>
						<div class="text-hor">
							<label>报备信息：</label>
							<p>${receiptRemark.remark}</p>
						</div>
						<div class="hr"></div>
					</div>
					<!--box-body-->
				</div>
				<!--box-->
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
		<script src="/static/js/dateutil.js" type="text/javascript"
			charset="utf-8"></script>
		<script type="text/javascript" src="static/js/common.js"
			charset="utf-8"></script>
</body>

</html>