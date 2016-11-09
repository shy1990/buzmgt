<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <li title="priceSection"><a href="/achieve/list?planId=${planId}#ajgqj">按价格区间</a></li>
    <li title="brand"><a href="/achieve/list?planId=${planId}#ppxhao">品牌型号<span
            class="qipao">2</span></a></li>
    <li title="achieve"><a href="/achieve/list?planId=${planId}">达量设置</a></li>
    <li title="superposition"><a data-toggle="tab" href="#djsz">叠加设置</a></li>
    <li title="award"><a href="/award/list?planId=${planId}">达量奖励</a></li>
</ul>
<script>
    var iframe = parent.document.getElementById("iframepage");
    if (null != iframe || '' != iframe) {
        var url = iframe.contentWindow.location.href;
        var start = url.indexOf("/", 7) + 1;
        var end = url.indexOf("/", start);
        var status = url.substring(start, end);
        $('#myTab li').each(function (i) {
            var tital = $(this).attr("title");
            if (status == tital) {
                $(this).addClass("active");
                $(this).siblings('li').removeClass("active");
            }
        });
    }

</script>
</body>
</html>