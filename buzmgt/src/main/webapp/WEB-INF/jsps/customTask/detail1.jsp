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
<html lang="en">
<head>
<meta charset="UTF-8" name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<base href="<%=basePath%>" />
<title>自定义事件详情</title>
<!-- Bootstrap -->
<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/kaohe/kaohe-det.css" />
<link rel="stylesheet" type="text/css"
	href="/static/abnormal/abnormal.css" />
<link rel="stylesheet" href="/static/customTask/type.css"
	type="text/css" />
<script id="task-table-template" type="text/x-handlebars-template">
	{{#if content}}
		{{#each content}}
					<div class="H-main">
						<div class="box-new">
									<p class="biat">
										<span class="text-blue">{{name}}</span> <span class="text-gery">
											消息条数{{size}},未读数{{unsize}}</span>
									</p>
									
								</div>
							</div>
				 {{#each mesList}}
						<div class="box-new">
								<img src="/static/image/yello.png" style="float: {{me status}}">
									<p class="biat">
										<span class="text-blue">{{../name}}</span> <span class="text-gery">
											{{time}}</span>
									</p>
									<br>
									<p class="p-style">{{content}}</p>
						  </div>
						
							<hr class="htrrr">
				{{/each}}
		{{/each}}
	{{else}}
		<tr>
			<td colspan="100">没有相关数据</td>
		</tr>
	{{/if}}
</script>
</head>
<body>

	<div class="row">
		<div class="container">
			<div class="col-xs-1"></div>

			<div class="col-xs-10">
				<div class="H-main">
					<div class="head-main">
						<span class="text-heda">${task.title}</span>
						<h5>
							类型：${taskType} &nbsp; &nbsp;&nbsp;<span class="text-red"
								id="punish">扣罚：${task.punishCount}元</span> <span
								class="text-right">${task.createTime}</span>
						</h5>
					</div>
					<hr class="htrr">
					<p class="text-body">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${task.content}</p>
					<br>

				</div>
				<div class="text-page">
					全部 <span class="red-two">${messagesCount}</span> 条
				</div>
			</div>
			<div class="col-xs-1"></div>
		</div>
		<div class="container">
			<p>收件人:</p>
			<c:if test="${not empty reSet}">
				<c:forEach var="salesman" items="${reSet}">
					<label style="color: green"> ${salesman.truename}</label>
				</c:forEach>
			</c:if>
			<c:if test="${not empty unreSet}">
				<c:forEach var="salesman" items="${unreSet}">
					<label style="color: red"> ${salesman.truename}</label>
				</c:forEach>
			</c:if>
		</div>

		<div class="container H-main-d">
			<div class="col-xs-1"></div>
			<div id="taskList" class="col-xs-10"></div>
			<div class="col-xs-1"></div>
		</div>
		<div class="container H-main-d" id="abnormalCoordPager"></div>

		<div class="send-new-user " id="J_sendNewUser" data-spm="918"
			style="display: block; left: 0px;">
			<div class="tishi">提示：本条信息需要回执单</div>
			<button class="btn btn-orage in" id="huizhi">回 执</button>
		</div>


		<div class="send-new " id="J_sendNew" data-spm="918">

			<div class="col-xs-1"></div>

			<div class="col-xs-10">
				<p class="text-white">回执</p>
				<textarea id="message" class="form-control" rows="5"
					placeholder="请输入回执内容"></textarea>
				<p class="text-white-w">
					可以输入 <span class="text-orage">2000</span> 个字符
				</p>
				<button class="btn btn-fab fr" onclick="sendMessage();">发布回执</button>

			</div>
			<div class="col-xs-1"></div>

		</div>

	</div>

	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript" src="/static/customTask/detail.js"></script>
	<script type="text/javascript">
		var btn1 = $('#huizhi');
		var box1 = $('#J_sendNewUser');
		$("#huizhi.in").on("click", function() {
			$("#J_sendNew").removeClass('active');
		})
		$("#huizhi").on("click", function() {
			$("#J_sendNew").addClass('active');
			$(this).addClass('in');
		})
		var taskId = "${task.id}";
		var type = "${task.type}";
		if (type != 2) {
			$("#punish").css("display", "none");
		}
		getMessage(taskId);
	</script>
</body>
</html>