<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=basePath%>" />
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>添加月任务</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/month-task/add-month-task.css"  >
<link rel="stylesheet" type="text/css"
	href="static/month-task/bootstrap-select.min.css">
</head>
<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-add"></i>添加月任务
			<!--区域选择按钮-->
			<div class="area-choose">
				选择区域：<span>${region.name}</span> <a class="are-line"
					href="javascript:;" onclick="getRegion(${region.id});">切换</a>
			</div>
			<!--/区域选择按钮-->
		</h4>
		<!-- person-select -->
		<div class="row">
			<div class="col-md-12">
				<form class="form-horizontal" role="form">
					<div class="form-group clearfix">
						<label for="basic" class="select-label fl">业务员：</label>
						<div class="select-area fl">
							<select id="basic" class="show-tick form-control fl">
								<c:if test="${not empty salesList}">
									<c:forEach var="sales" items="${salesList}">
										<option value="${sales.id}">${sales.truename}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
						<button type="button" class="btn btn-blue btn-sm"
							onclick="getMonthData('${region.id}');">检索</button>
					</div>
				</form>
			</div>
		</div>

		<!-- row-header -->
		<!-- content -->
		<div class="con-wrapper">
			<!-- row-task-section -->
			<div class="row">
				<div class="col-md-4">
					<div class="section-wrapper">
						<div class="task-header column-first">
							<h5>
								<span class="task-num">20</span>次拜访任务设置
							</h5>
						</div>
						<ul>
							<li class="clearfix">
								<p class="fl">上个月月均提货天数 ≥ 20天</p>
								<p class="fr">
									<span class="seller-num" id="20sy">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">上月提货天数 ≥ 20天</p>
								<p class="fr">
									<span class="seller-num" id="20ly">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">上月拜访次数 ≥ 20天</p>
								<p class="fr">
									<span class="seller-num" id="20bf">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<div class="fl task-info">
									<label>本月<span class="task-total">20</span>次拜访商家：
									</label> <input id="20goal" type="number" value="14">
								</div>
								<p class="fr suggestion">
									系统建议<span id="20sysgive">14</span>家
								</p>
							</li>
						</ul>
					</div>
				</div>
				<!-- section2 -->
				<!-- section3 -->
				<div class="col-md-4">
					<div class="section-wrapper">
						<div class="task-header column-third">
							<h5>
								<span class="task-num">15</span>次拜访任务设置
							</h5>
						</div>
						<ul>
							<li class="clearfix">
								<p class="fl">15天 ≤ 月均提货天数 &#60; 20天</p>
								<p class="fr">
									<span id="15sy" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">15天 ≤ 上月提货天数 &#60; 20天</p>
								<p class="fr">
									<span id="15ly" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">15天 ≤ 上月拜访天数 &#60; 20天</p>
								<p class="fr">
									<span id="15bf" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<div class="fl task-info">
									<label>本月<span class="task-total">15</span>次拜访商家：
									</label> <input id="15goal" type="number" value="14">
								</div>
								<p class="fr suggestion">
									系统建议<span id="15sysgive">14</span>家
								</p>
							</li>
						</ul>
					</div>
				</div>

				<!-- section3 -->
				<!-- section4 -->
				<div class="col-md-4">
					<div class="section-wrapper">
						<div class="task-header column-first">
							<h5>
								<span class="task-num">10</span>次拜访任务设置
							</h5>
						</div>
	
						<ul>
							<li class="clearfix">
								<p class="fl">10天 ≤ 月均提货天数&#60; 15天</p>
								<p class="fr">
									<span id="10sy" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">10天 ≤ 上月提货天数&#60; 15天</p>
								<p class="fr">
									<span id="10ly" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">10天 ≤ 上月拜访天数&#60; 15天</p>
								<p class="fr">
									<span id="10bf" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<div class="fl task-info">
									<label>本月<span class="task-total">10</span>次拜访商家：
									</label> <input id="10goal" type="number" value="14">
								</div>
								<p class="fr suggestion">
									系统建议<span id="10sysgive">14</span>家
								</p>
							</li>
						</ul>
					</div>
				</div>
				<!-- section4 -->
				<!-- section5 -->
				<div class="col-md-4">
					<div class="section-wrapper">
						<div class="task-header column-sec">
							<h5>
								<span class="task-num">7</span>次拜访任务设置
							</h5>
						</div>
						<ul>
							<li class="clearfix">
								<p class="fl">7天 ≤ 月均提货天数 &#60; 10天</p>
								<p class="fr">
									<span id="7sy" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">7天 ≤ 上月提货天数 &#60; 10天</p>
								<p class="fr">
									<span id="7ly" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">7天 ≤ 上月拜访天数 &#60; 10天</p>
								<p class="fr">
									<span id="7bf" class="">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<div class="fl task-info">
									<label>本月<span class="task-total">7</span>次拜访商家：
									</label> <input id="7goal" type="number" value="14">
								</div>
								<p class="fr suggestion">
									系统建议<span id="7sysgive">14</span>家
								</p>
							</li>
						</ul>
					</div>
				</div>
				<!-- section5 -->
				<!-- section6 -->
				<div class="col-md-4">
					<div class="section-wrapper">
						<div class="task-header column-third">
							<h5>
								<span class="task-num">4</span>次拜访任务设置
							</h5>
						</div>
						<ul>
							<li class="clearfix">
								<p class="fl">4天 ≤ 月均提货天数 &#60; 7天</p>
								<p class="fr">
									<span id="4th" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">4天 ≤ 上月提货天数 &#60; 7天</p>
								<p class="fr">
									<span id="4ly" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<p class="fl">4天 ≤ 上月拜访次数 &#60; 7天</p>
								<p class="fr">
									<span id="4bf" class="seller-num">10</span>商家
								</p>
							</li>
							<li class="clearfix">
								<div class="fl task-info">
									<label>本月<span  class="task-total">4</span>次拜访商家：
									</label> <input id="4goal" type="number" value="14">
								</div>
								<p class="fr suggestion">
									系统建议<span id="4sysgive">14</span>家
								</p>
							</li>
						</ul>
					</div>
				</div>
				<!-- section6 -->
			</div>
		</div>
	</div>
	<!-- content -->
	<!-- mask -->
	<div class="mask">
		<button type="button" onclick="submit(0);" class="btn btn-blue save-btn">保存</button>
		<button type="button" onclick="submit(1);" class="btn btn-deep-blue save-btn">发布</button>
	</div>
	<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript"
		charset="utf-8"></script>
	<script src="static/bootStrapPager/js/extendPagination.js"></script>
	<script src='static/js/common.js'></script>
	<script src='static/js/dateutil.js'></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/month-task/add-month-task.js"></script>
	<script src="static/bootstrap/js/bootstrap-select.min.js"></script>
	<script>
		/*区域 */
		var base="<%=basePath%>";
		function getRegion(id){
			window.location.href='/region/getPersonalRegion?id='+id+"&flag=monthTask";
		}
	</script>
</body>
</html>
