<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
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
<title>三际后台管理系统</title>

</head>
<body>

<div id="menu">
	<c:choose>
		<c:when test="${not empty sessionScope.menus}">
			<c:forEach items="${sessionScope.menus}" var="menu" >
				<c:if test="${menu.parentId==1}">
					<li id="lm${menu.id }">
					  <a style="cursor:pointer;" onclick="findControl('${menu.url }')" class="dropdown-toggle" >
						<span>${menu.name}</span>
					  </a>
					  <ul class="submenu">
				  	 </ul>
				  </li>
				</c:if>
			</c:forEach>
		</c:when>
		</c:choose>
</div>
</body>
	<script src="../static/js/jquery.min.js"></script>
	<script type="text/javascript">window.jQuery || document.write("<script src='../static/js/jquery.min.js'>\x3C/script>");</script>
	<script src="../static/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	/* 跳转 */
		function findControl(url) {
			var url = url;
			window.location = encodeURI(url);
		}
		</script>
</html>
