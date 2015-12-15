<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<html>
<head>
<title>业务管理</title>
<style>
</style>
</head>
<body>
	<h1>
		<shiro:principal property="username" />:你好。<a href="logout">注销</a>
		<shiro:hasRole name="admin">超级管理员</shiro:hasRole>
	</h1>
</body>
</html>