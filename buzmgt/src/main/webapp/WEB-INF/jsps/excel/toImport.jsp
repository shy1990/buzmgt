<%--
  Created by IntelliJ IDEA.
  User: barton
  Date: 16-2-19
  Time: 下午3:13
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <title></title>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/js/jqueryfileupload/css/jquery.fileupload.css">
</head>
<body>

<form action="/import/images/upload" method="post"  enctype="multipart/form-data">
  <span class="btn btn-success fileinput-button">
        <i class="glyphicon glyphicon-plus"></i>
        <span>浏览文件</span>
    <!-- The file input field used as target for the file upload widget -->
        <input id="file" type="file" name="file">
    </span>
    <span><input type="submit" value="上传" /></span>
</form>
<p id="message"/>

<script src="/static/js/jquery/jquery-1.11.3.min.js"></script>
<script src="/static/js/jqueryfileupload/js/vendor/jquery.ui.widget.js"></script>
<script src="/static/js/jqueryfileupload/js/jquery.iframe-transport.js"></script>
<script src="/static/js/jqueryfileupload/js/jquery.fileupload.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="/static/js/businessadviser/`.js"></script>
</body>
</html>
