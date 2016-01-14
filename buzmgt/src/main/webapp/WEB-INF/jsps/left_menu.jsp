<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>三际后台管理系统</title>
<script  src='/static/js/jquery/jquery.min.js'></script>
<script  src='/static/bootstrap/js/bootstrap.js'></script>
</head>
<body>
	<ul id="menu" class="nav nav-sidebar menu">
		<c:choose>
			<c:when test="${not empty sessionScope.menus}">
				<c:forEach items="${sessionScope.menus}" var="menu">
					<c:if test="${menu.parentId==1}">
						<li id="lm${menu.id }">
							<c:choose>
							<c:when test="${not empty menu.url}">
								<a href="${menu.url}"><i class="${menu.icon} menu-icon"></i>${menu.name} </a>
							</c:when>
							<c:otherwise>
							<a class="menu-second-box" href="" >
							<i class="${menu.icon} menu-icon"></i>${menu.name}
							<span class="pull-right down-icon"></span></a>
							</c:otherwise>
							</c:choose>		
					  	<ul class="menu-second">
							<c:forEach items="${menu.children}" var="sub">
								<c:choose>
									<c:when test="${not empty sub.url}">
									<li id="z${sub.id }">
									<a href="${sub.url }"><i class="${sub.icon } menu-icon"></i>${sub.name }</a>
									</c:when>
									<c:otherwise>
									<li> <a href=""><i class="${sub.icon } menu-icon"></i>${sub.name }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
				  		</ul>
						</li>
					</c:if>
				</c:forEach>
			</c:when>
		</c:choose>
</ul>
</body>
<script type="text/javascript">
	window.jQuery|| document.write("<script src='/static/js/jquery.min.js'>\x3C/script>");
</script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	/* 跳转 */
	function findControl(url) {
		var url = url;
		window.location = encodeURI(url);
	}
    $('#menu').mousedown(function(e){
        if (window.event) {
            if (event.button == 2) {
//                 alert('请不要点击鼠标右键！'); 
                return false;
            }
        }
    })
    
</script>
</html>
