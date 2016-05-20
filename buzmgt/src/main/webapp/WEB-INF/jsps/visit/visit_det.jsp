<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>任务</title>
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
<link rel="stylesheet" type="text/css" href="static/visit/visit.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-visit-task"></i>任务
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="abnormal-body box border blue">
					<!--title-->
					<div class="box-title">
						<i class="ico icon-shop-name"></i>${visit.taskName }
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="text-hor">
							<label>拜访时间：</label>
							<p><fmt:formatDate value="${visit.finishTime }" pattern="yyyy-MM-dd HH:mm:ss"/></p>
						</div>
						<div class="text-hor">
							<label>拜访总结：</label>
							<c:if test="${visit.summary ne null }">
								<p>${visit.summary }</p>
							</c:if>
							<c:if test="${visit.summary eq null }">
								<p>无信息</p>
							</c:if>
						</div>
						<div class="text-hor">
							<label>坐标：</label>
							<p>${visit.address }</p>
						</div>
						<div class="text-hor">
							<label>照片：</label>
							<p></p>
						</div>
						<div class="text-hor">
							<label></label>
							<p>
							<div class="img-box">
								<img class="visit-img" src="${visit.imageurl1 }" alt="" />
							</div>
							<div class="img-box">
								<img class="visit-img" src="${visit.imageurl2 }" alt="" />
							</div>
							<div class="img-box">
								<img class="visit-img" src="${visit.imageurl3 }" alt="" />
							</div>
							</p>
						</div>
						<!-- <div class="text-hor">
							<label>足迹匹配：</label>
							<p>
								<span class="icon-tag-zc">正常</span> <span class="icon-tag-yc">异常</span>
							</p>
						</div> -->
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
				<!--box-->
				<!--不同阶段颜色不同1：pink 2：yellow 3:violet 4:-->
				<div class="ywmamber-msg box border pink">
					<!--title-->
					<div class="box-title">
						<i class="icon icon-time"></i>考核中
					</div>
					<!--box-body-->
					<div class="box-body">
						<!--ywmamber-body-->
						<div class="ywmamber-body">
							<img width="80" src="static/img/user-head.png" alt="..."
								class="img-circle">
							<div class="msg-text">
								<h4>易小星</h4>
								<p>ID: A236743252</p>
								<p>电话: 12547346455</p>
							</div>
						</div>
						<!--/ywmamber-body-->
						<div class="stage">
							<span class="kaohe-stage onekaohe-stage">第一阶段:60% </span>
						</div>
						<div class="progress progress-sm">
							<div style="width: 60%;" class="progress-bar bar-kaohe"></div>
						</div>
						<div class="operation">
							<a href="saojie_upd.html" class="">考核设置</a> <a
								href="kaohe_det.html" class="pull-right">查看</a>
						</div>
						<div class="yw-text">
							入职时间:<span> 2015.09.21</span> <br /> 负责区域: <span>山东省滨州市邹平县</span>
						</div>
						<!--拜访任务-->
						<div class="visit">
							<button class="col-xs-12 btn btn-visit" href="javascript:;">
								<i class="ico icon-add"></i>拜访任务
							</button>
						</div>
						<!--拜访任务-->
						<!--操作-->
						<div class="operation">
							<a href="javascript:;" class="">账户设置</a> <a href="javascript:;">冻结账户</a>
						</div>
						<!--操作-->
					</div>
					<!--box-body-->
				</div>
				<!--box-->
				<!--业务外部链接-->
				<div class="yw-link">
					<a class="link-oper" href="javascript:;"><i
						class="icon icon-user"></i>个人资料</a> <a class="link-oper"
						href="javascript:;"><i class="icon icon-income"></i>收益</a> <a
						class="link-oper" href="javascript:;"><i
						class="icon icon-task"></i>任务</a> <a class="link-oper"
						href="javascript:;"><i class="icon icon-log"></i>日志</a> <a
						class="link-oper" href="javascript:;"><i
						class="icon icon-footprint"></i>足迹</a> <a class="link-oper"
						href="javascript:;"><i class="icon icon-signin"></i>签收记录</a> <a
						class="link-oper" href="javascript:;"><i
						class="icon icon-saojie"></i>扫街记录</a>
				</div>

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
		<script src="static/yw-team-member/team-member.js"
			type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$('body input').val('');
			$(".form_datetime").datetimepicker({
				format : "yyyy-mm-dd",
				language : 'zh-CN',
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2,
				pickerPosition : "bottom-right",
				forceParse : 0
			});
		</script>
</body>

</html>