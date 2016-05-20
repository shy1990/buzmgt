<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
<title>油补记录</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" /> 
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/ywmember.css" />
<link rel="stylesheet" type="text/css"
	href="static/abnormal/abnormal.css" />
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<link rel="stylesheet" type="text/css" href="static/oil-subsidy/oil_subsidy_detail.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
var	base='<%=basePath%>';
var oilCostId='${oilCost.id}';
</script>
</head>

<body>
	<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-oil-detail"></i>油补明细
    </h4>
    <div class="row">
        <div class="col-md-9">
            <!--box-->
            <div class="box-body">
                <div class="list-item">
                    <b>握手顺序：</b>
                    <span id="renduAbnormal"></span>
                    <c:forEach var="record" items="${oilCost.oilRecordList }" varStatus="status">
	                    <c:choose>
	                    	<c:when test="${status.index == 0 && record.exception == 0 }">
	    	                	<span>起点<span class="normal-state">${record.regionName }</span></span>
	                    	</c:when>
	                    	<c:when test="${status.index == 0 && record.exception == 1}">
	    	                	<span>起点<span class="abnormal-state">异常</span></span>
	                    	</c:when>
	                    	<c:when test="${status.last && record.exception == 0}">
			                    <span class="location">终点<span class="normal-state">${record.regionName }</span></span>
	                    	</c:when>
	                    	<c:when test="${status.last && record.exception == 1}">
			                    <span class="location">终点<span class="abnormal-state">异常</span></span>
	                    	</c:when>
	                    	<c:when test="${status.index != 0 && !status.last && record.exception == 1}">
			                    <span class="location">${record.regionName }<span class="abnormal-state">异常</span></span>
	                    	</c:when>
	                    	<c:otherwise>
			                    <span class="location">${record.regionName }</span>
	                    	</c:otherwise>
	                    </c:choose>
                		</c:forEach>
                    
                </div>
                <div class="list-item">
                    <b>日期：</b>
                    <span>${oilCost.dateTime}</span>
                </div>
                <div class="marg-t text-time clearfix">
                    <div class="oil-info">
                        <span>公里数：</span>
                        <span>${oilCost.distance}km</span>
                    </div>
                    <div class="oil-info">
                        <span>油补金额：</span>
                        <span>${oilCost.oilCost}元</span>
                    </div>
                    <div class="link-posit pull-right">
                        <a class="table-export" href="javascript:void(0);">导出excel</a>
                    </div>
                </div>
                <!--列表内容-->
                <div class="tab-content">
                    <!--油补明细-->
                    <div class="tab-pane fade in active" id="box_tab1">
                        <!--table-box-->
                        <div class="table-abnormal-list table-overflow">
                            <table class="table table-hover new-table abnormal-table">
                                <thead>
                                <tr>
                                    <th>顺序</th>
                                    <th>握手镇</th>
                                    <th>握手动作</th>
                                    <th>握手时间</th>
                                </tr>
                                </thead>
                                <tbody id="detailList"> </tbody>
                                <c:forEach var="record" items="${oilCost.oilRecordList }" varStatus="status">
                                <tr>
							     							  <td>
							     							  <c:choose>
								     							  <c:when test="${status.index ==0 }">
																			<span class="order begin">起点</span>
								     							  </c:when>
								     							  <c:when test="${status.last }">
																			<span class="order end">终点</span>
								     							  </c:when>
								     							  <c:otherwise>
																			<span class="order">${status.index}</span>
								     							  </c:otherwise>
							     							  </c:choose>
														      </td>
														      <td>${record.regionName}
														      <c:if test="${record.exception == 1}">(异常)</c:if>
														      </td>
														      <td>${record.shopName }（${record.missName}）</td>
														      <td>${record.missTime}</td>
														    </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!--table-box-->
                    </div>
                    <!--油补明细-->
                    <!-- <div class="map">
                        <div class="clearfix">
                            <h2>地图显示</h2>
                            <a class="find-up" href="#">查看轨迹 &gt;</a>
                        </div>
                        <div class="map-wrapper">
                            <img src="./static/img/oilSubsidy/map.jpg" alt="map">
                        </div>
                    </div> -->
                </div>
                <!--列表内容-->
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
		<script type="text/javascript" src="static/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script type="text/javascript" src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="/static/js/dateutil.js" type="text/javascript"
			charset="utf-8"></script>
		<script type="text/javascript" src="static/js/common.js" charset="utf-8"></script>
		<script type="text/javascript" src="static/js/handlebars-v4.0.2.js" charset="utf-8"></script>
		<script type="text/javascript" src="static/bootStrapPager/js/extendPagination.js"></script>
		<script type="text/javascript" src="static/oil-subsidy/oil-subsidy-detail.js" 
			charset="utf-8"></script>
		<script type="text/javascript">
			
		</script>
</body>

</html>