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
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" type="text/css" href="css/income-cash.css">
    <link href="../static/fenye/css/fenye.css" rel="stylesheet">
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="content main">
    <h4 class="page-header">
        <i class="ico icon-puish-m"></i>月扣罚记录

        <!--区域选择按钮-->
        <%--<div class="area-choose">
            选择区域：<span>山东省</span>
            <a class="are-line" href="javascript:;">切换</a>
        </div>--%>


    </h4>
    <!---选择区域，选择日期-->
    <div class="row text-time">


        <span class="text-strong chang-time time-c">选择日期：</span>
        <div class="search-date">
            <div class="input-group input-group-sm">
                <span class="input-group-addon " id="basic-addon1"><i
                        class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                <input id='aaa' type="text" class="form-control form_datetime input-sm" placeholder="开始日期"
                       readonly="readonly">
            </div>
        </div>
        --
        <div class="search-date">
            <div class="input-group input-group-sm">
                <span class="input-group-addon " id="basic-addon2"><i
                        class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                <input id='bbb' type="text" class="form-control form_datetime input-sm" placeholder="结束日期"
                       readonly="readonly">
            </div>
        </div>
        <!--考核开始时间-->
        <%--<button class="btn btn-blue btn-sm" onclick="goSearch('${salesman.id}','${assess.id}');">--%>
        <button id="kkk" class="btn btn-blue btn-sm" >
            检索
        </button>
        <!---->
        <div class="link-posit-t pull-right exc-hh">
            <a class="table-export" href="javascript:void(0);">导出excel</a>
        </div>

    </div>
    <!---选择区域，选择日期-->
    <div class="clearfix"></div>
    <div class="tab-content">

        <div class="tab-pane fade in active" id="box_tab1">
            <!--table-box-->
            <div class="table-task-list new-table-box table-overflow">
                <table class="table table-hover new-table">
                    <thead>
                    <tr>
                        <th>业务ID</th>
                        <th>姓名</th>
                        <th>所属区域</th>
                        <th>欠款金额</th>
                        <th>扣罚金额</th>
                        <th>日期</th>
                    </tr>
                    </thead>
                    <tbody id="tbody">


                    </tbody>


                </table>
                <%--分页--%>
                <div id="callBackPager"></div>
            </div>
            <!--table-box-->
        </div>
        <!--油补记录-->
    </div>


</div>

<![endif]-->
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
<script type="text/javascript">
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
    var $_haohe_plan = $('.J_kaohebar').width();
    var $_haohe_planw = $('.J_kaohebar_parents').width();
    $(".J_btn").attr("disabled", 'disabled');
    if ($_haohe_planw === $_haohe_plan) {
        $(".J_btn").removeAttr("disabled");
    }

    function select() {
        var $listcon = $('.abnormal-table tbody tr'),
                $unpay = $('.icon-tag-wfk'),
                $payed = $('.payed'),
                $timeout = $('.time-out'),
                $paystatus = $('.J-pay-staus');
        $paystatus.delegate('li', 'click', function () {
            var $target = $(this);
            $paystatus.children('li').removeClass('pay-status-active');
            $target.addClass('pay-status-active');
            $listcon.hide();
            switch ($target.data('item')) {
                case 'all':
                    $listcon.show();
                    break;
                case 'unpay':
                    for (var i = 0; i < $unpay.length; i++) {
                        $($unpay[i]).parents('tr').show();
                    }
                    ;
                    break;
                case 'timeout':
                    for (var i = 0; i < $timeout.length; i++) {
                        $($timeout[i]).parents('tr').show();
                    }
                    ;
                    break;
                case 'payed':
                    for (var i = 0; i < $payed.length; i++) {
                        $($payed[i]).parents('tr').show();
                    }
                    ;
                    break;
                default:
                    break;
            }
        });

    }

    select();
</script>
<script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
<!-- 这是自定义显示数据的模板 -->
<script id="content-template" type="text/x-handlebars-template">
    {{#each data.content}}
        <tr>
            <td>{{salesMan.id}}</td>
            <td>{{salesMan.truename}}</td>
            <td>{{salesMan.region.parent.parent.parent.parent.name}}{{salesMan.region.parent.parent.parent.name}}{{salesMan.region.parent.parent.name}}{{salesMan.region.name}}</td>
            <td>{{debt}}</td>
            <td>{{amerce}}</td>
            <td>{{createDate}}</td>
        </tr>
    {{/each}}

    <tr>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><span class="text-hei">扣罚金额： </span> <span class="text-reddd"> {{sum}}</span> <span
                class="text-hei"> 元</span></td>
    </tr>

</script>


<script>

    var totalCount = 0;
    var limit = 0;
    var searchData = {
        "size": "3",
        "page": "0",
        "startTime":"2016-01-26",
        "endTime":"2200-01-26",
    }
    ajaxSearch(searchData);
    function ajaxSearch(searchData) {
        $.ajax({
            url: 'MonthPunishUp/MonthPunishUps',
            type: 'get',
            data: searchData,
            async: false,
            success: function (result) {
                console.log(result);
                var data = result.data;
//                console.log(result.data);
                totalCount = data.totalElements;
                limit = data.size;
                content = data.content;
                console.log(content);
                list(result);
            }
        });
    }
    initPaging();
    function initPaging() {
        searchData['startTime'] = $("#aaa").val() ;
        searchData['endTime'] = $("#bbb").val() ;
        $('#callBackPager').extendPagination({
            totalCount: totalCount,//总条数
            showCount: 5,//下面小图标显示的个数
            limit: limit,//每页显示的条数
            callback: function (curr, limit, totalCount) {
                searchData['page'] = curr - 1;
                searchData['size'] = limit;
                ajaxSearch(searchData);
            }
        });
    }
    $("#kkk").click(function(){
        ajaxSearch(searchData);
        initPaging();
    });

    function list(content) {
        var template = Handlebars.compile($("#content-template").html());//编译模版
        //将json数据用刚刚注册的Handlebars模版封装，得到最终的 html，插入到基础的table中
        $("#tbody").html(template(content));
    }
</script>
</body>
</html>


