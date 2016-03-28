<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>考核详情</title>
<!-- Bootstrap -->
<script src="/static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<div class="J_right_member">
		<!--box-->
		<!--不同阶段颜色不同1：pink 2：yellow 3:violet 4:-->
		<c:choose>
		<c:when test="${salesMan.salesmanStatus.name == '考核中'}">
			<div class="ywmamber-msg box border pink">
		</c:when>
		<c:otherwise>
			<div class="ywmamber-msg box border blue">
		</c:otherwise>
		</c:choose>
			<!--title-->
			<div class="box-title">
				<i class="icon icon-time"></i>${salesMan.salesmanStatus.name }
			</div>
			<!--box-body-->
			<div class="box-body">
				<!--ywmamber-body-->
				<div class="ywmamber-body">
					<img width="80" src="../static/img/user-head.png" alt="..."
						class="img-circle">
					<div class="msg-text">
						<h4>${salesMan.truename}</h4>
						<p>ID: ${salesMan.id}</p>
						<p>电话: ${salesMan.mobile}</p>
					</div>
				</div>
				<!--/ywmamber-body-->
				<div class="stage">
				<c:choose>
				<c:when test="${salesMan.salesmanStatus.name == '考核中'}">
					<span class="kaohe-stage onekaohe-stage">${salesMan.salesmanStatus.name}:60% </span>
				</c:when>
				<c:otherwise>
					<span class="kaohe-stage default-stage">${salesMan.salesmanStatus.name}:60% </span>
				</c:otherwise>
				</c:choose>
				</div>
				<div class="progress progress-sm">
					<div style="width: 60%;" class="progress-bar bar-kaohe"></div>
				</div>
				<div class="operation">
					<a href="javascript:;" class="">考核设置</a> <a
						href="javascript:;" class="pull-right">查看</a>
				</div>
				<div class="yw-text">
					入职时间:<span> ${salesMan.regdate}</span> <br /> 负责区域: <span>${salesMan.region.name}</span>
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
				class="link-oper" href="javascript:;"><i class="icon icon-task"></i>任务</a>
			<a class="link-oper" href="javascript:;"><i class="icon icon-log"></i>日志</a>
			<a class="link-oper" href="javascript:;"><i
				class="icon icon-footprint"></i>足迹</a> <a class="link-oper"
				href="javascript:;"><i class="icon icon-signin"></i>签收记录</a> <a
				class="link-oper" href="javascript:;"><i
				class="icon icon-saojie"></i>扫街记录</a>
		</div>
	</div>
	<script type="text/javascript">
		
	</script>
</body>

</html>
</html>
