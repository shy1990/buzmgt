<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<!-- Bootstrap -->
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<base href="<%=basePath%>" />


</head>
<body>
		<!-- end row -->
	</div>
	<!-- end main   -->

</body>

</html>

<div>
	<form id="atype_Ex_import" action="/import/images/upload" method="post"  enctype="multipart/form-data">
		地域类型表导入:<input type="file" name="myFile" id="myFile"/>
		<input type="button" value="提交">
	</form>
</div>