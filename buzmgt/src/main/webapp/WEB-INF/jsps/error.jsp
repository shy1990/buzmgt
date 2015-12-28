<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="http://cdn.amazeui.org/amazeui/2.4.2/css/amazeui.min.css">
<style type="text/css">
.admin-content {
width: auto;
overflow: hidden;
height: 100%;
background: #fff;
}
.page-404 {
background: #fff;
border: none;
width: 200px;
margin: 0 auto;
}
</style>
<title>外星人来了</title>
</head>
<body>
<!-- 
	错误页面
	<div>类型=${error}, status=${status}</div>
	<div>${message}</div>
	<br />
 -->
	<!-- content start -->
  <div class="admin-content">

    <div class="am-cf am-padding">
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">404</strong> / <small>That’s an error</small></div>
    </div>

    <div class="am-g">
      <div class="am-u-sm-12">
        <h2 class="am-text-center am-text-xxxl am-margin-top-lg">${status}. ${error}</h2>
        <p class="am-text-center">${message}</p>
        <pre class="page-404">
          .----.
       _.'__    `.
   .--($)($$)---/#\
 .' @          /###\
 :         ,   #####
  `-..__.-' _.-\###/
        `;_:    `"'
      .'"""""`.
     /,  ya ,\\
    //  ${status}!  \\
    `-._______.-'
    ___`. | .'___
   (______|______)
        </pre>
      </div>
    </div>
  </div>
  <!-- content end -->
</body>
</html>