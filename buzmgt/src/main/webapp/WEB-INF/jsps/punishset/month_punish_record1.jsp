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
                <span class="input-group-addon " id="basic-addon1"><i
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
                    <c:forEach var="monthPunishUp" items="${page.content}">
                        <tr>
                            <td>${monthPunishUp.salesMan.id}</td>
                            <td>${monthPunishUp.salesMan.truename}</td>
                            <td>${monthPunishUp.salesMan.region.parent.parent.parent.parent.name}${monthPunishUp.salesMan.region.parent.parent.parent.name}${monthPunishUp.salesMan.region.parent.parent.name}${monthPunishUp.salesMan.region.name}</td>
                            <td>${monthPunishUp.debt}</td>
                            <td>${monthPunishUp.amerce}</td>
                            <td>${monthPunishUp.createDate}</td>
                        </tr>
                    </c:forEach>

                    </tbody>

                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><span class="text-hei">扣罚金额： </span> <span class="text-reddd"> ${sum} </span> <span
                                class="text-hei"> 元</span></td>
                    </tr>
                </table>
                <%--分页--%>
                <div class="tcdPageCode"></div>
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
<script src="../static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>
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
<%--分页工具--%>
<script src="static/salesmanData/jquery.min.js"></script>
<script src="static/salesmanData/jquery.page.js"></script>
<script>

    //分页
    var p = ${page.number};//当前页从0开始
    <%--var startTimeFen = ${startTimeFen}--%>
    <%--var endTimeFen = ${endTimeFen}--%>
    console.log("****:  "+p);
    $(".tcdPageCode").createPage({
        pageCount : ${page.totalPages},//总页数
        current : (p+1),//当前小标页数
        backFn : function(p) {
//            $("#tbody").empty();
//            list(p - 1);
            window.location.href='MonthPunishUp/list3?page='+(p-1)+'&startTime='+"${startTimeFen}"+'&endTime='+"${endTimeFen}";
        }
    });
    //条件查询
    $("#kkk").click(function(){
        var startTime = $("#aaa").val();
        var endTime = $("#bbb").val();
        console.log(startTime+":   "+endTime);
        window.location.href='MonthPunishUp/list3?page='+0+'&startTime='+startTime+'&endTime='+endTime;
    });

</script>
</body>
</html>


