<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>叠加组人员设置</title>

    <link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/income-cash.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/phone.css">
    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script language="JavaScript" src="<%=basePath%>static/js/section/jquery.json.js"></script>
    <style>

        .name-list {
            margin-top: 20px;
            margin-left: 20px;
            color: #858585;
            min-height: 600px;
            border-top: 1px solid #eeeeee;
            border-bottom: 1px solid #eeeeee;
        }

        .name-list > li:hover {
            background: #e3f9ff;
        }

        li {
            padding: 15px;
            border-bottom: none;
        }

        .ph-search-date > input {
            display: inline-block;
            width: auto;
            height: 30px;
        }

        .dl-horizontal dt {
            float: left;
            width: 160px;
            overflow: hidden;
            clear: left;
            text-align: right;
            text-overflow: ellipsis;
            white-space: nowrap;

        }

        .icon-jiahui {
            background: url("<%=basePath%>static/img/jiahui.png") no-repeat center;
        }

        .icon-cj {
            background: url("<%=basePath%>static/img/tj.png") no-repeat center;
        }

        .text-zcy {
            margin-top: -30px;
            margin-left: 25px;

        }

        .text-green {
            color: #24cd8e;
            font-size: 16px;
        }

        .text-green-s {
            color: #24cd8e;
            font-size: 12px;
            font-weight: bold;
        }

        .notice {
            display: inline-block;
            position: relative;
            top: -10px;
            background: url("<%=basePath%>static/earnings/img/sc-b.png") no-repeat center;
            width: 95px;
            height: 33px;
            padding-top: 10px;
            text-align: center;
        }

        .notice:hover {
            background: url("<%=basePath%>static/earnings/img/sc-bg.png") no-repeat center;
            padding-top: 10px;
            color: #FFFFFF;
            cursor: pointer;
        }

        .text-b {
            color: #209ee5;
            font-size: 16px;
        }

        .text-b-s {
            color: #209ee5;
            font-size: 12px;
            font-weight: bold;
        }

        .text-c {
            color: #feb87f;
            font-size: 16px;
        }

        .text-c-s {
            color: #feb87f;
            font-size: 12px;
            font-weight: bold;
        }

        .text-d {
            color: #b2d66c;
            font-size: 16px;
        }

        .text-d-s {
            color: #b2d66c;
            font-size: 12px;
            font-weight: bold;
        }

        .icon-bg {
            display: inline-block;
            position: relative;
            z-index: 100;
            width: 35px;
            height: 35px;
            margin-right: 5px;
            margin-top: -8px;
        }

        .tj-body {
            width: 850px;
            border-right: 1px solid #eeeeee;
        }

        .icon-nam {
            display: inline-block;
            position: relative;
            width: 18px;
            height: 12px;
            margin-right: 20px;
            background: url("<%=basePath%>static/img/name.png") no-repeat center;
        }

        .icon-delee {
            display: inline-block;
            position: relative;
            width: 18px;
            height: 12px;
            margin-left: 40px;
            background: url("<%=basePath%>static/img/del.png") no-repeat center;
        }


    </style>
    <script type="text/javascript">

    </script>
</head>
<body>
<!---扣罚设置表头-->
<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-form-jage"></i>叠加组人员设置
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>

    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->
            <div class="order-box">
                <ul>
                    <li>
                        <dl class="dl-horizontal ">
                            <dt><span class="text-strong text-gery" style="font-size: 14px">人员分组：</span></dt>
                        </dl>

                    </li>
                    <dl class="dl-horizontal">
                        <dt>添加使用人员：</dt>

                        <dd style="width: 750px; margin-bottom: 20px">

                            <div class="col-sm-2">
                                <a
                                        class="J_addDire btn btn-default ph-btn-bluee icon-tj col-sm-6"
                                        onclick="openUser('${planId}');"></a>
                            </div>

                        </dd>
                    </dl>
                    <%--组人员--%>
                    <div class="col-sm-3 cl-padd" id="listGroup">


                    </div>
                </ul>
            </div>
        </div>
    </div>
    <div class="form-group ">
        <div class="row">
            <div class="col-sm-offset-4 col-sm-4" style="margin-top: 50px;margin-bottom: 35px;">
                <button id="create" type="submit" class="col-sm-12 btn btn-primary text-strong" onclick="go()">生成
                </button>
            </div>
        </div>
    </div>
</div>

</div><!--扣罚设置--->
</div><!---扣罚设置表头-->
<%--列表显示人员页面--%>
<div id="user" class="modal fade" role="dialog">
    <jsp:include flush="true" page="userSelect.jsp"></jsp:include>
</div>

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-switch.min.js"></script>

<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="../static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"
        charset="utf-8"></script>
<script src="../static/superposition/openUser.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript">
    var i = 1;

    $('.J_addDire').click(function () {
        var dirHtml = '<form class="frm"><div class="col-sm-3 cl-padd">' +
                '<div class="ratio-box">' +
                '<div class="ratio-box-dd">' +
                '<span class="label  label-blue">' + (i++) + '区间' + '</span>' +
                '<input type="text" class="ph-inptt" placeholder="" name="hh" onchange="kkk(this.value,this)" >' +
                '<span class="ph-text-blue">' + '至' + '</span>' +
                '<input type="text" class="ph-inptt"   placeholder="" name="hh" onchange="kkk(this.value,this)">' +
                '<span class="ph-primary ">' + '元' + '</span>' + '&nbsp; &nbsp; <span class="label  label-blue ">提成</span>' +
                '<input type="text" class="ph-inpt" placeholder="" name="hh"  onchange="kkk(this.value,this)">' +
                '</div>' +
                '</div>' +
                '</div></form>';
        $(this).parents('.col-sm-3').before(dirHtml);
    });
    $('body input').val('');


    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-right",
        forceParse: 0
    });
</script>
<script>
    function go() {
        console.log(groupList)
        window.name = JSON.stringify(groupList);
        window.location = "/superposition?planId=${planId}";
    }


</script>
</body>
</html>
