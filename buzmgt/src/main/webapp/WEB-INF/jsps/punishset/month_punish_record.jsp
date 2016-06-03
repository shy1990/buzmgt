<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>月扣罚记录</title>

    <link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link href="static/bootStrapPager/css/page.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" type="text/css" href="css/income-cash.css">
    <link href="../static/fenye/css/fenye.css" rel="stylesheet">
    <%--分页样式--%>
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
    <script type="text/javascript" src="static/js/handlebars-v4.0.2.js" charset="utf-8"></script>

    <script type="text/javascript">
        var base = "<%=basePath%>";

    </script>
</head>
<body>
<button id="ceshi">ceshi</button>
<div id="callBackPager"></div>

<script>
    var searchData = {
        "size": "3",
        "page": "2"
    }
//    ajaxSearch(searchData);
    function ajaxSearch(searchData) {
        console.log("i do")
        console.log(searchData);
        $.ajax({
            url: 'MonthPunishUp/MonthPunishUps',
            type: 'get',
            data: searchData,
            success: function (data) {
                console.log(data);
//                initPaging(data);

            }
        });
    }
var i = 0;
    //    分页
            initPaging();
    function initPaging() {
        var totalCount1 = 0; //总条数
        var limit1 = 2;
        $('#callBackPager').extendPagination({
            totalCount: totalCount1,//总条数
            showCount: 5,//下面小图标显示的个数
            limit: limit1,//每页显示的条数
            callback: function (curr, limit, totalCount) {
                console.log(i++);
                console.log("当前页数："+curr+"   每页条数："+limit+"  总条目数："+totalCount);
//                searchData['page'] = curr - 1;
//                searchData['size'] = limit;
//                ajaxSearch(searchData);
            }
        });
    }

</script>
<script>


</script>
</body>
</html>


