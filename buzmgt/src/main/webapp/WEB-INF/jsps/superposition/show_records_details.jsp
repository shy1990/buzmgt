<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <base href="<%=basePath%>"/>
    <title>收益主方案添加</title>
    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
    <link href="/static/bootstrap/css/fileinput.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link href="static/bootStrapPager/css/page.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="/static/incomeCash/css/income-cash.css">
    <link rel="stylesheet" type="text/css" href="/static/income/hedge.css"/>
    <script src="static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>

    <script id="record-table-template" type="text/x-handlebars-template">
        {{#if this}}
        {{#each this}}
        <tr>
            <td>{{addIndex @index}}</td>
            <td>{{shopName}}</td>
            <td>{{namePath}}</td>
            <td>{{orderNo}}</td>
            <td>{{productionName}}</td>
            <td>{{amount}}</td>
            <td>{{payTime}}</td>
        </tr>
        {{/each}}
        {{/if}}
    </script>
</head>
<body>
<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-cj"></i>
        <!--区域选择按钮-->

        <a href="javascript:history.back();"><i
                class="ico icon-back fl-right"></i></a>

    </h4>
    <div class="row text-time">
        <%--<div class="salesman">--%>
            <%--<input id="searchDate" type="text"--%>
                   <%--class="form-control form_datetime input-sm" placeholder="选择日期"--%>
                   <%--readonly="readonly" style="background: #ffffff;">--%>
        <%--</div>--%>

        <%--<button id="findByTime" class="btn btn-blue btn-sm" style="margin-left: 20px">筛选--%>
        </button>
        <%--<div class="link-posit-t pull-right export">--%>
        <%--<input id="orderNo" class="cs-select  text-gery-hs" placeholder="  订单号">--%>
        <%--<button class="btn btn-blue btn-sm" onclick="">检索</button>--%>
        <%--<a class="table-export" href="javascript:void(0);"--%>
        <%--data-toggle="modal" data-target="#daoru">导入excel</a>--%>
        <%--</div>--%>
    </div>
    <div class="clearfix"></div>
    <div class="tab-content">
        <div class="tab-pane fade in active" id="box_tab1">
            <!--table-box-->
            <div class="table-task-list new-table-box table-overflow">
                <table class="table table-hover new-table">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>商家名称</th>
                        <th>区域</th>
                        <th>订单号</th>
                        <th>产品</th>
                        <th>数量</th>
                        <th>付款日期</th>
                    </tr>
                    </thead>
                    <tbody id="record-tbody">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%--分页--%>
    <div id="callBackPager" class="wait-page-index"></div>
</div>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="/static/bootstrap/js/fileinput.js"></script>
<script type="text/javascript"
        src="static/bootstrap/js/fileinput_locale_zh.js"></script>
<script src="static/js/jqueryfileupload/js/vendor/jquery.ui.widget.js"></script>
<script src="static/js/jqueryfileupload/js/jquery.fileupload.js"></script>
<script src="/static/js/jqueryfileupload/js/jquery.iframe-transport.js"></script>
<script src="/static/js/common.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="static/bootStrapPager/js/extendPagination.js"></script>
<script src="/static/income/main/hedge.js" type="text/javascript"
        charset="utf-8"></script>
<script>
    function initDateInput() {
        $('body input').val('');
        $(".form_datetime").datetimepicker({
            format : "yyyy-mm-dd",
            language : 'zh-CN',
            weekStart : 1,
            todayBtn : 1,
            autoclose : 1,
            todayHighlight : 1,
            startView : 2,
            minView : 2,
            pickerPosition : "bottom-right",
            forceParse : 0
        });
        var $_haohe_plan = $('.J_kaohebar').width();
        var $_haohe_planw = $('.J_kaohebar_parents').width();
        $(".J_btn").attr("disabled", 'disabled');
        if ($_haohe_planw === $_haohe_plan) {
            $(".J_btn").removeAttr("disabled");
        }
    }

</script>

<script type="text/javascript">
    $(document).ready(function () {
        getRecords("","");
    });

    function getRecords(time,salesmanId){
        $.ajax({
            url:'superposition/showRecordsDetails',
//            data:{time:time,salesmanId:salesmanId},
            type:'POST',
            success:function (data) {
                console.log(data);
                handelebarsRegister(data);
            }
        });
    }
    function handelebarsRegister(data){
        var recordTemplate = Handlebars.compile($("#record-table-template").html());
        //下标 + 1
        Handlebars.registerHelper("addIndex", function (index) {
            return (index + 1) + '';
        });
        $("#record-tbody").html(recordTemplate(data));
    }

</script>

</body>
</html>
