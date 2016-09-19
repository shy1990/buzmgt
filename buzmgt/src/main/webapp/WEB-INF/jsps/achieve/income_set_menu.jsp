<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
	<h4 class="page-header">
			<i class="ico ico-tcsz"></i>提成设置 <a href="javascript:history.back();"><i
				class="ico icon-back fl-right"></i></a>
				<input id="planId" hidden="hidden" value="${planId }">
		</h4>

		<ul class="nav nav-pills  nav-top" id="myTab">
			<li><a data-toggle="tab" href="#ajgqj">按价格区间</a></li>
			<li><a data-toggle="tab" href="#ppxhao">品牌型号<span
					class="qipao">2</span></a></li>
			<li class="active"><a data-toggle="tab" href="#dlsz">达量设置</a></li>
			<li><a data-toggle="tab" href="#djsz">叠加设置</a></li>
			<li><a data-toggle="tab" href="#dljl">达量奖励</a></li>
		</ul>
</body>
</html>