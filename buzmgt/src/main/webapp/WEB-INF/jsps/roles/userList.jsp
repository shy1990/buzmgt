<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>角色查看</title>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script type="text/javascript" src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>
<div>
 <table id="table_report" class="table table-striped table-bordered table-hover table-condensed" style="font-size: 10px;">
		<span style="font-family:华文中宋; color:red;">${name}</span>
		<thead>
			<th width="20%" class="center">序号</th>
			<th width="20%" class="center" >匹配人员</th>
			<th width="20%" class="center" >操作</th>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${not empty users}">
			<c:forEach var="user" items="${users}" varStatus="s">
				<tr>
					<td width="20%" class="center">${s.index+1}</td>
					<td width="20%">${user.username}</td>
					<td style="width:10px;"><a href="javascript:removeUser(${user.id});" class=" btn-success">取消匹配</a></td>
				</tr>
			</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="100">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
		</tbody>
	</table>
</div>

<!-- JQUERY -->
		<script src="static/js/jquery/jquery-2.0.3.min.js"></script>
		<!-- JQUERY UI-->
		<script src="static/js/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
		<!-- BOOTSTRAP -->
		<script src="static/bootstrap-dist/js/bootstrap.min.js"></script>
		<!-- CUSTOM SCRIPT -->
		<script src="static/js/script.js"></script>
		<script type="text/javascript">
			top.hangge();
			/* 取消匹配 */
			function removeUser(userId){
				var flag = false;
					if(confirm("如若取消此配置,该人员将失去角色对应的权限!")){
						flag = true;
					}
				if(flag){
					top.jzts();
					var url = "role/removeUser?id="+userId;
					$.get(url,function(data){
						top.jzts();
						document.location.reload();
					});
				}
			}
		</script>
</body>
</html>