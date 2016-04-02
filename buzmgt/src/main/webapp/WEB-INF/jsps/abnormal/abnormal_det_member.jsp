<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
      + "/";
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
<title>客户签收</title>
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
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-abnormal_record"></i>客户签收
		</h4>
		<div class="row">
			<div class="col-md-9">    
				<!--box-->
				<div class="abnormal-body box border blue">
					<!--title-->
					<div class="box-title">
						<i class="ico icon-shop-name"></i>小米手机专卖店
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="text-hor">
							<label>订单号：</label>
							<p>${orderSignfor.orderNo }</p>
						</div>
						<div class="text-hor">
							<label>是否签收：</label>
							<p>
								<span class="accepted">
								<c:if test="${!empty  orderSignfor.customSignforTime}"> 已签收 </c:if>
								<c:if test="${empty  orderSignfor.customSignforTime}"> 未签收</c:if>
								</span>
							</p>
						</div>
						<div class="text-hor">
							<label>签收坐标：</label>
							<p>${orderSignfor.customSignforGeopoint }</p>
						</div>
						<div class="text-hor">
							<label>签收时间：</label>
							<p>
								<fmt:formatDate value="${orderSignfor.customSignforTime }" pattern="yyyy-MM-dd HH:mm"/>
							</p>
						</div>
						<div class="text-hor">
							<label>付款方式：</label>
							<p>
								<c:if test="${！empty orderSignfor.orderPayType }">${orderSignfor.orderPayType }</c:if>
								<c:if test="${empty orderSignfor.orderPayType }">未付款</c:if>
							</p>
						</div>
						<div class="text-hor">
							<label>足迹匹配：</label>
							<p>
								<c:if test="${orderSignfor.customSignforException == 1 }">
									<span class="icon-tag-yc">异常</span>
								</c:if>
								<c:if test="${orderSignfor.customSignforException == 0 }">
									<span class="icon-tag-zc">正常</span> 
								</c:if>
							</p>
						</div>
						<div class="hr"></div>
						<div class="text-hor">
							<label>轨迹查看：</label>
						</div>
						<div class="text-hor">
							<label></label>
							<!--地图位置-->
							<div class="map-box">
								<img width="100%" src="static/img/abnormal/map-img.png" />
							</div>
							<!--地图位置-->
						</div>
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
</body>

</html>
