<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>扫街添加</title>
    <script src="../static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <script language="JavaScript" src="../static/js/section/jquery.json.js"></script>
    <script src="../static/js/handlebars-v4.0.2.js"
            type="text/javascript" charset="utf-8"></script>
</head>

<body>
<div id="liebiao">
    <c:forEach items="${production.priceRanges}" var="priceRange">
        <tr>
            <input type="text" value="${priceRange.priceRangeId}" hidden/>
            <td>序列号:${priceRange.serialNumber}</td>
            <td>价格区间:${priceRange.priceRange}</td>
            <td>提成:${priceRange.percentage}</td>
            <td>实施日期:${priceRange.implementationDate}</td>
        </tr><br>
    </c:forEach>
</div>

</body>
</html>
