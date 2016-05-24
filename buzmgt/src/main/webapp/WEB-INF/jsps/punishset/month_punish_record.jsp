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
    <style>
        * {
            margin: 0;
            padding: 0;
            list-style: none;
        }

        a {
            text-decoration: none;
        }

        a:hover {
            text-decoration: none;
        }

        .tcdPageCode {
            padding: 15px 20px;
            text-align: left;
            color: #ccc;
            margin-left: 600px;
        }

        .tcdPageCode a {
            display: inline-block;
            color: #428bca;
            display: inline-block;
            height: 25px;
            line-height: 25px;
            padding: 0 10px;
            border: 1px solid #ddd;
            margin: 0 2px;
            border-radius: 4px;
            vertical-align: middle;
        }

        .tcdPageCode a:hover {
            text-decoration: none;
            border: 1px solid #428bca;
        }

        .tcdPageCode span.current {
            display: inline-block;
            height: 25px;
            line-height: 25px;
            padding: 0 10px;
            margin: 0 2px;
            color: #fff;
            background-color: #428bca;
            border: 1px solid #428bca;
            border-radius: 4px;
            vertical-align: middle;
        }

        .tcdPageCode span.disabled {
            display: inline-block;
            height: 25px;
            line-height: 25px;
            padding: 0 10px;
            margin: 0 2px;
            color: #bfbfbf;
            background: #f2f2f2;
            border: 1px solid #bfbfbf;
            border-radius: 4px;
            vertical-align: middle;
        }
    </style>
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<button id="ceshi">ceshi</button>

<p>${page.content}</p>
<c:forEach var="o" items="${page.content}">
    ${o.salesMan.region.parent.name}<br/>
</c:forEach>

<div class="content main">
    <h4 class="page-header">
        <i class="ico icon-puish-m"></i>月扣罚记录

<%--
        <!--区域选择按钮-->
        <div class="area-choose">
            选择区域：<span>山东省</span>
            <a class="are-line" href="javascript:;">切换</a>
        </div>
--%>
    </h4>
    <!---选择区域，选择日期-->
    <div class="row text-time">


        <span class="text-strong chang-time time-c">选择日期：</span>
        <div class="search-date">
            <div class="input-group input-group-sm">
                <span class="input-group-addon " id="basic-addon1"><i
                        class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                <input type="text" class="form-control form_datetime input-sm" placeholder="开始日期"
                       readonly="readonly">
            </div>
        </div>
        --
        <div class="search-date">
            <div class="input-group input-group-sm">
                <span class="input-group-addon " id="basic-addon1"><i
                        class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                <input type="text" class="form-control form_datetime input-sm" placeholder="结束日期"
                       readonly="readonly">
            </div>
        </div>
        <!--考核开始时间-->
        <button class="btn btn-blue btn-sm" onclick="goSearch('${salesman.id}','${assess.id}');">
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
<!--
                    <tr>
                        <td>258796124</td>
                        <td>李光洙</td>
                        <td>山东省济南市历下区二区</td>
                        <td>500.00</td>
                        <td>10.00</td>
                        <td>2016.04.23</td>
                    </tr>
                    -->

                    </tbody>

                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td><span class="text-hei">扣罚金额： </span> <span class="text-reddd"> 2300.00 </span> <span
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
<%--分页工具--%>
<script src="static/salesmanData/jquery.min.js"></script>
<script src="static/salesmanData/jquery.page.js"></script>
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
<script>
    $(function(){
        zhixing();
    });
    function zhixing(){
        console.log('zou le jin lai');
        var p = 0;//当前页数
        var totalPages1 = 0;//总页数
        var cntent = 0;
        list(p);
        function list(p){
            $.ajax({
                url: 'MonthPunishUp/MonthPunishUps?page='+ p,
                data: {
                    size: '2',
                    page: 0
                },
                type: 'get',
                async : false,//加载完毕再往下执行，不然也是还没有获得
                success: function (data) {
                    console.log(data);
                    //获取数据
                    totalPages1 = data.totalPages;
                    var MonthPunishUps = data.content;//获取所有的MonthPunishUp数据
//                var a = MonthPunishUps[0].region.parent();
//                console.log(a);
                    console.log("1111111111111:      "+totalPages1);
                    console.log(MonthPunishUps[0].salesMan.region.parent);
                    var str = '';
                    for(var i=0;i<MonthPunishUps.length;i++){
                        var userId = MonthPunishUps[i].salesMan.id;
                        var name = MonthPunishUps[i].salesMan.truename;
                        var debt = MonthPunishUps[i].debt;
                        var amerce = MonthPunishUps[i].amerce;
                        var createDate = MonthPunishUps[i].createDate;

                        str += '<tr>';
                        str +=  '<td>'+userId+'</td>';
                        str +=   '   <td>'+name+'</td>';
                        str +=  '    <td>山东省济南市历下区二区</td>';
                        str +=   '    <td>'+debt+'</td>';
                        str +=   '    <td>'+debt+'</td>';
                        str +=   '    <td>'+createDate+'</td>';
                        str +=   '</tr>';
                    }
                    $str = $(str);
                    $("#tbody").append($str);
                }

            });


        }
        //分页

        $(".tcdPageCode").createPage({
            pageCount : totalPages1,//总页数
            current : 1,//当前页数
            backFn : function(p) {
                $("#tbody").empty();
                list(p - 1);
            }
        });
    }

</script>
</body>
</html>


