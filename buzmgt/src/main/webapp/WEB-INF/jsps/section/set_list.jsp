<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>财务设置记录</title>

    <link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/phone.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/comminssion.css">

    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>

    <style>
        .new-table-box {
            border-top: none;
            min-height: 600px;
            background: #FFF;
        }
    </style>
    <script type="text/javascript">
        function see(id) {
            window.location.href = '<%=basePath%>section/findOne/'+id;
        }

    </script>
</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-tcsz"></i>财务设置记录
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>


    <span class="text-gery text-strong ">按日期筛选：</span>

    <div class="search-date">
        <div class="input-group input-group-sm">
                        <span class="input-group-addon "><i
                                class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
            <input type="text" class="form-control form_datetime input-sm" placeholder="结束日期"
                   readonly="readonly" style="background: #ffffff">
        </div>

    </div>
    -
    <div class="search-date">
        <div class="input-group input-group-sm">
                        <span class="input-group-addon "><i
                                class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
            <input type="text" class="form-control form_datetime input-sm" placeholder="结束日期"
                   readonly="readonly  " style="background: #ffffff;">
        </div>

    </div>

    <hr class="hr-solid-sm" style="margin-top: 25px">



    <div style="padding-left: 0">
        <div class=" sidebar left-side" style="padding-top:0;margin-top:5px">
            <h5 class="line-h">
                <i class="ico ico-fl"></i>请选择类别
            </h5>
            <%--手机类型导航栏--%>
            <ul id="ul" class="nav nav-sidebar menu">
                <c:forEach items="${machineTypes}" var="machineType">
                    <li class="current">
                        <a href="<%=basePath%>section/toNotExpiredJsp?type=${machineType.id}"> ${machineType.name}</a>
                    </li>
                </c:forEach>
            </ul>

        </div>
    </div>





    <ul class="nav nav-pills  nav-top" id="myTab">
        <li class="active"><a data-toggle="tab" href="#newon"> &nbsp;当前进行 &nbsp;  </a></li>
        <li><a data-toggle="tab" href="#yguoq">  &nbsp; 已过期 &nbsp; </a></li>
        <li><a data-toggle="tab" href="#new_review">  &nbsp; 新建审核 &nbsp; </a></li>
        <li><a data-toggle="tab" href="#modify_review">  &nbsp; 修改审核 &nbsp; </a></li>

    </ul>


    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->

            <div class="order-box">

                <div class="tab-content">


                    <!--当前进行-->
                    <div class="tab-pane fade  in active  " id="newon">
                        <!--导航开始-->

                        <div class=" new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核人</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>修改日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${list.size() >=1}">
                                    <c:forEach items="${list}" var="production">
                                        <tr>
                                            <td><span class="ph-new">新建</span> ${production.implDate} 开始 方案区间</td>
                                            <td class="reason">${production.implDate}</td>
                                            <td><span class=""> ${production.endTime} </span></td>
                                            <td>${production.productionAuditor}</td>
                                            <c:if test="${production.productStatus=='1'}">
                                                <td><span class="text-hong text-strong">待审核</span></td>
                                                <td><span class="ph-on">---</span></td>
                                            </c:if>
                                            <c:if test="${production.productStatus=='3'}">
                                                <td><span class="text-lan text-strong">已审核</span></td>
                                                <td><span class="ph-on">---</span></td>
                                            </c:if>

                                            <td>2016.08.28-2016.08.29</td>
                                            <td>
                                                <button class="btn  bnt-sm bnt-ck"
                                                        onclick="see('${production.productionId}')">
                                                    查看
                                                </button>
                                            </td>
                                        </tr>

                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>

                    </div>


                    <!--已过期-->

                    <div class="tab-pane fade  " id="yguoq">
                        <!--导航开始-->

                        <div class=" new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核人</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>修改日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${listExpired.size() >=1}">
                                    <c:forEach items="${listExpired}" var="production">
                                        <tr>
                                            <td><span class="ph-new">新建</span> ${production.implDate} 开始 提成方案区间</td>
                                            <td class="reason">${production.implDate}</td>
                                            <td><span class=""> ${production.endTime}</span></td>
                                            <td>${production.productionAuditor}</td>
                                            <td><span class="text-lan text-strong">已审核</span></td>
                                            <td><span class="ph-weihes">已过期</span></td>
                                            <td>2016.08.28-2016.08.29</td>
                                            <td>
                                                <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">
                                                    查看
                                                </button>
                                            </td>
                                        </tr>

                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>

                    </div>


                    <!--新建审核-->

                    <div class="tab-pane fade  " id="new_review">
                        <!--导航开始-->

                        <div class=" new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核人</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>修改日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${list.size() >=1}">
                                    <c:forEach items="${list}" var="production">
                                        <c:if test="${production.productStatus=='1'}">
                                            <tr>
                                                <td><span class="ph-new">新建</span> ${production.implDate} 开始 方案区间</td>
                                                <td class="reason">${production.implDate}</td>
                                                <td><span class=""> ${production.endTime} </span></td>
                                                <td>${production.productionAuditor}</td>
                                                <td><span class="text-hong text-strong">待审核</span></td>
                                                <td><span class="ph-on">---</span></td>
                                                <td>2016.08.28-2016.08.29</td>
                                                <td>
                                                    <button class="btn  bnt-sm bnt-ck" data-toggle="modal"
                                                            data-target="#">
                                                        查看
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:if>


                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <!--修改审核-->

                    <div class="tab-pane fade  " id="modify_review">
                        <!--导航开始-->

                        <div class=" new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>开始日期</th>
                                    <th>提成</th>
                                    <th>审核人</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>修改日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${priceRanges.size() >=1}">
                                    <c:forEach items="${priceRanges}" var="priceRange">
                                        <tr>
                                            <td><span class="ph-new">修改</span> ${priceRange.priceRange}区间 修改提成</td>
                                            <td class="reason">${priceRange.implementationDate}</td>
                                            <td><span class=""> ${priceRange.percentage} 元</span></td>
                                            <td>${priceRange.priceRangeAuditor}</td>
                                            <c:if test="${priceRange.priceRangeStatus == 1}">
                                                <td><span class="text-lan text-strong">审核中</span></td>
                                            </c:if>
                                            <c:if test="${priceRange.priceRangeStatus == 2}">
                                                <td><span class="text-lan text-strong">驳回</span></td>
                                            </c:if>

                                            <td><span class="ph-weihes">----</span></td>
                                            <td>${priceRange.priceRangeCreateDate}</td>
                                            <td>
                                                <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">
                                                    查看
                                                </button>
                                            </td>
                                        </tr>

                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                        </div>

                    </div>


                </div>

            </div>


        </div>

    </div>


</div>
<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>
<script src="../static/bootstrap/js/bootstrap.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<%--<script src="../static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>--%>
<script>
    $('#tab li').click(function () {
        $(this).addClass('active');
        $(this).siblings('li').removeClass('active');
        $('#tab li:eq(1) a').tab('show');
    });

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
</body>
</html>
