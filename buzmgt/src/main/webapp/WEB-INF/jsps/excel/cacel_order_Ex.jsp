<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link rel="stylesheet" href="static/bootstrap/css/bootstrap.css">
    <style>
        body{
            background:#fafafa ;
        }


        p{
            margin-top:150px;
            text-align: center;
            font-weight: 700;
        }
    </style>
    <script type="text/javascript">
        var base = '<%=basePath%>';
    </script>
</head>
<body>

<p>
    <span>取消订单商家导出：</span> <a class="table-export" href="javascript:void(0);" onclick="initExcelExport();">导出excel</a>
</p>

<script src="/static/js/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
    /**
     * 导出excel
     */
    function initExcelExport() {
        $('.table-export').on(
                'click',
                function () {
                    window.location.href = base + "export/cacelOrder";
                });
    }
</script>
</body>
</html>