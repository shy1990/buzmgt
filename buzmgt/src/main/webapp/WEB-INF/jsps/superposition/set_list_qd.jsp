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
    <title>渠道审核</title>

    <link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/phone.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/section/comminssion.css">
    <link rel="stylesheet" type="text/css" href="/static/bootStrapPager/css/page.css"/>
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"
            charset="utf-8"></script>

    <style>
        .new-table-box {
            border-top: none;
            min-height: 600px;
            background: #FFF;
        }

        .nav-sidebar > li.current > a, .nav-sidebar > li .current > a:focus, .nav-sidebar > li.current > a:hover {
            color: #2b86ba;
            border-left: 3px solid #44a6dd;
            background: #ffffff;
        }
    </style>
    <script type="text/javascript">
        function see(id) {
            window.location.href = 'find/'+id;
        }
        function seeProgress(planId, id) {
            window.location.href = 'progress?planId=' + planId + '&id=' + id;
        }
        function stop(id) {
            $.ajax({
                url: 'stop/' + id,
                data: {checkStatus: '4'},
                dataType: 'json',
                type: 'POST',
                success: function (data) {
                    if (data.result == 'success') {
                        alert("操作成功");
                    }
                    window.location.reload();
                },
                error: function (data) {

                }
            })

        }
    </script>


    <%--进行中的模板--%>
    <script id="list-template" type="text/handlebars-template">
        {{#each this}}
        <tr>
            <td>1</td>
            <td>
                {{#with goodsTypeList}}
                {{#each this}}
                {{#with machineType}}
                {{name}}
                {{/with}}
                {{#with brand}}
                {{name}}
                {{/with}}
                {{/each}}
                {{/with}}
            </td>
            <td>
                {{taskOne}}
                {{#if taskTwo}}
                |{{taskTwo}}
                {{/if}}
                {{#if taskThree}}
                |{{taskThree}}
                {{/if}}
            </td>
            <td>
                {{#if implDate}}
                {{implDate}} -- {{endDate}}
                {{/if}}
            </td>
            <td>{{giveDate}}</td>
            <td>
                {{transformat checkStatus}}
            </td>


            <td>{{endDate}}</td>
            <td>
                <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#" onclick="see('{{id}}')">查看
                </button>

                {{#compare checkStatus 3}}
                <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#" onclick="seeProgress('{{planId}}', '{{id}}')">进程
                </button>
                <button class="btn btn-sm btn-zz " data-toggle="modal" data-target="#" onclick="stop('{{id}}')">终止
                    {{else}}
                    {{/compare}}
                </button>
            </td>
        </tr>
        {{/each}}

    </script>


    <%--过期的模板--%>
    <script id="list-template1" type="text/handlebars-template">
        {{#each this}}
        <tr>
            <td>1</td>
            <td>
                {{#with goodsTypeList}}
                {{#each this}}
                {{#with machineType}}
                {{name}}
                {{/with}}
                {{#with brand}}
                {{name}}
                {{/with}}
                {{/each}}
                {{/with}}
            </td>
            <td>

                {{taskOne}}
                {{#if taskTwo}}
                |{{taskTwo}}
                {{/if}}
                {{#if taskThree}}
                |{{taskThree}}
                {{/if}}
            </td>
            <td>
                {{#if implDate}}
                {{implDate}} -- {{endDate}}
                {{/if}}
            </td>
            <td>{{giveDate}}</td>
            <td>
                <span class="text-lan text-strong">已审核</span>
            </td>
            <td>{{endDate}}</td>
            <td>
                <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#" onclick="see('{{id}}')">查看
                </button>
                <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#" onclick="seeProgress('{{planId}}', '{{id}}')">进程
                </button>
                    <%--</button>--%>
                    <%--<button class="btn btn-sm btn-zz " data-toggle="modal" data-target="#">终止--%>
                    <%--</button>--%>
            </td>
        </tr>
        {{/each}}



    </script>

</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-tcsz"></i>财务
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>


    <%--<span class="text-gery text-strong ">按日期筛选：</span>--%>

    <%--<div class="search-date">--%>
        <%--<div class="input-group input-group-sm">--%>
                        <%--<span class="input-group-addon "><i--%>
                                <%--class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>--%>
            <%--<input type="text" class="form-control form_datetime input-sm" placeholder="结束日期"--%>
                   <%--readonly="readonly" style="background: #ffffff">--%>
        <%--</div>--%>

    <%--</div>--%>
    <%-----%>
    <%--<div class="search-date">--%>
        <%--<div class="input-group input-group-sm">--%>
                        <%--<span class="input-group-addon "><i--%>
                                <%--class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>--%>
            <%--<input type="text" class="form-control form_datetime input-sm" placeholder="结束日期"--%>
                   <%--readonly="readonly  " style="background: #ffffff;">--%>
        <%--</div>--%>

    <%--</div>--%>

    <hr class="hr-solid-sm" style="margin-top: 25px">

    <ul class="nav nav-pills  nav-top" id="myTab">
        <li class="active"><a data-toggle="tab" href="#newon"> &nbsp;当前进行 &nbsp;  </a></li>
        <li><a data-toggle="tab" href="#yguoq">  &nbsp; 已过期 &nbsp; </a></li>
    </ul>


    <div class="row">
        <div class="col-md-12">
            <div class="order-box">

                <div class="tab-content">
                    <!--当前进行-->
                    <div class="tab-pane fade  in active  " id="newon">
                        <!--导航开始-->
                        <div class=" new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>名称</th>
                                    <th>指标</th>
                                    <th>方案起止日期</th>
                                    <th>区佣金发放日</th>
                                    <th>状态</th>
                                    <th>设置日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="list_now">

                                </tbody>
                            </table>
                        </div>
                        <%--分页--%>
                        <div id="callBackPager"></div>
                    </div>


                    <!--已过期-->

                    <div class="tab-pane fade  " id="yguoq">
                        <!--导航开始-->

                        <div class=" new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>名称</th>
                                    <th>指标</th>
                                    <th>方案起止日期</th>
                                    <th>区佣金发放日</th>
                                    <th>状态</th>
                                    <th>设置日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="list_expired">
                                </tbody>
                            </table>
                        </div>
                        <%--分页--%>
                        <div id="callBackPager1"></div>

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
<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript">
    var total = 0;
    var totalCount = 0;
    var limit = 0;
    var total1 = 0;
    var totalCount1 = 0;
    var limit1 = 0;
    var searchData1 = {
        "size": "20",
        "page": "0"
    }
    var searchData = {
        "size": "20",
        "page": "0"
    }

    function listNow(searchData) {
        //正在使用
        $.ajax({
            url: 'listAll?sign=pass&planId=${planId}',
            type: 'POST',
            dataType: 'json',
            data: searchData,
            success: function (data) {
                console.log(data);
                var listArray = data.content;
                totalCount = data.totalElements;
                limit = data.size;
                var listTemplate = Handlebars.compile($("#list-template").html());


                Handlebars.registerHelper("transformat", function (value) {
                    if (value == 1) {
                        return "审核中";
                    } else if (value == 2) {
                        return '驳回';
                    } else if (value == 3) {
                        return '审核通过';
                    }
                });
                Handlebars.registerHelper("compare", function (v1, v2, options) {
                    if (v1 == 3) {
                        //满足添加继续执行
                        return options.fn(this);
                    } else {
                        //不满足条件执行{{else}}部分
                        return options.inverse(this);
                    }
                });
                $("#list_now").html(listTemplate(listArray));
                if (totalCount != total || totalCount == 0) {
                    total = totalCount;
                    initPaging();
                }
            },
            error: function () {
            }
        });

    }
    //分页
    function initPaging() {
        $('#callBackPager').extendPagination({
            totalCount: totalCount,//总条数
            showCount: 5,//下面小图标显示的个数
            limit: limit,//每页显示的条数
            callback: function (curr, limit, totalCount) {
                searchData['page'] = curr - 1;
                searchData['size'] = limit;
                listNow(searchData);
            }
        });
    }


    function listExpired(searchData1) {
        //过期数据
        $.ajax({
            url: 'listAll?sign=expired&planId=${planId}',
            type: 'POST',
            dataType: 'json',
            data: searchData1,
            success: function (data) {
                console.log(data);
                var listArray = data.content;
                limit = data.size;
                var listTemplate = Handlebars.compile($("#list-template1").html());
                $("#list_expired").html(listTemplate(listArray));
//                state(data);
                if (totalCount1 != total1 || totalCount1 == 0) {
                    total1 = totalCount1;
                    initPaging1();
                }
            },
            error: function () {
            }
        });
    }

    //分页
    function initPaging1() {
        $('#callBackPager1').extendPagination({
            totalCount: totalCount1,//总条数
            showCount: 5,//下面小图标显示的个数
            limit: limit1,//每页显示的条数
            callback: function (curr, limit1, totalCount1) {
                searchData['page'] = curr - 1;
                searchData['size'] = limit1;
                listExpired(searchData1);
            }
        });
    }

    listExpired(searchData1);
    listNow(searchData);
</script>
</body>
</html>
