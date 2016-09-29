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
    <base href="<%=basePath%>"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>提成设置</title>

    <!-- Bootstrap -->
    <link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" href="static/earnings/css/phone.css">
    <link rel="stylesheet" href="static/earnings/css/comminssion.css">
    <link rel="stylesheet" type="text/css" href="static/achieve/achieve.css">
    <link rel="stylesheet" type="text/css" href="static/bootStrapPager/css/page.css"/>
    <script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <%--当前进行--%>
    <script id="brandUnderwayList-table-template" type="text/x-handlebars-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{addOne @index}}</td>
            <td>{{brand.name}}</td>
            <td>{{good.name}}</td>
            <td class="reason">
                <span class="text-red">{{commissions}}</span>
            </td>
            <td>
                <span class="text-blue">{{formDate startDate}}</span>
            </td>
            <td><span class="text-blue">{{formDate endDate}}</span></td>
            <td>{{user.nickname}}</td>
            <td>{{whatBrandIncomeStatus status}}</td>
            <td>{{compareDate startDate endDate status}}</td>
            <td>{{formDate createDate}}</td>
            <td>
                {{whatUnderwayButton startDate endDate status id}}
            </td>
        </tr>
        {{/each}}
        {{else}}
        <tr>
            <td colspan="100">没有相关数据</td>
        </tr>
        {{/if}}
    </script>
    <%--已过期--%>
    <script id="brandExpiredList-table-template" type="text/x-handlebars-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{addOne @index}}</td>
            <td>{{brand.name}}</td>
            <td>{{good.name}}</td>
            <td class="reason">
                <span class="text-red">{{commissions}}</span>
            </td>
            <td>
                <span class="text-blue">{{formDate startDate}}</span>
            </td>
            <td><span class="text-blue">{{formDate endDate}}</span></td>
            <td>{{user.nickname}}</td>
            <td>{{whatBrandIncomeStatus status}}</td>
            <td>{{compareDate startDate endDate status}}</td>
            <td>{{formDate createDate}}</td>
            <td>
                {{whatExpiredButton startDate endDate status id}}
            </td>
        </tr>
        {{/each}}
        {{else}}
        <tr>
            <td colspan="100">没有相关数据</td>
        </tr>
        {{/if}}
    </script>
    <script type="text/javascript">
        var base = '<%=basePath%>';
        var SearchData = {
            'page': '0',
            'size': '20'
        }
    </script>
</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-tcsz"></i>设置记录
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
        <input id="planId" hidden="hidden" value="${planId }">
        <input id="machineType" hidden="hidden" value="${machineType }">
    </h4>


    <span class="text-gery text-strong ">按日期筛选：</span>

    <div class="search-date form_date_start">
        <div class="input-group input-group-sm">
                        <span class="input-group-addon "><i
                                class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
            <input name="startDate" type="text" class="form-control form_datetime input-sm J_startDate" placeholder="开始日期"
                   readonly="readonly" style="background: #ffffff">
        </div>

    </div>
    -
    <div class="search-date form_date_end">
        <div class="input-group input-group-sm">
                        <span class="input-group-addon "><i
                                class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
            <input name="endDate" type="text" class="form-control form_datetime input-sm J_endDate" placeholder="结束日期"
                   readonly="readonly  " style="background: #ffffff;">
        </div>

    </div>
    <button onclick="goSearch();" class="btn  btn-sm bnt-ss ">搜索</button>
    <hr class="hr-solid-sm" style="margin-top: 25px">


    <ul class="nav nav-pills  nav-top" id="myTab">
        <li class="active" data-title="underway"><a data-toggle="tab" href="#newon"> &nbsp;当前进行 &nbsp;  </a></li>
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
                                    <th>品牌</th>
                                    <th>型号</th>
                                    <th>提成金额</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核人</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>修改日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="brandUnderwayList">

                                </tbody>
                            </table>
                        </div>
                        <%--当前进行分页--%>
                        <div id="brandUnderwayPager"></div>
                    </div>
                    <!--已过期-->

                    <div class="tab-pane fade  " id="yguoq">
                        <!--导航开始-->

                        <div class=" new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>品牌</th>
                                    <th>型号</th>
                                    <th>提成金额</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核人</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>修改日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="brandExpiredList">

                                </tbody>
                            </table>
                        </div>
                        <%--已过期分页--%>
                        <div id="brandExpiredPager"></div>
                    </div>

                </div>

            </div>
            </div>
        </div>

    </div>

    <!--品牌型号删除提示-->
    <div id="brandDel" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">提示</h3>
                </div>

                <div class="modal-body">
                    <div class="container-fluid">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <p class="col-sm-12  ">你确定要删除方案吗？删除后该方案将不复存在！</p>
                            </div>


                            <div class="btn-qx">
                                <input id="brandId" hidden="hidden" value="">
                                <button type="button" class="btn btn-danger btn-d" onclick="del();">删除</button>
                            </div>

                            <div class="btn-dd">
                                <button type="button" data-dismiss="modal" class="btn btn-primary btn-d">取消</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--品牌型号删除提示-->
</div>
<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="static/js/common.js" charset="utf-8"></script>
<script type="text/javascript" src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="static/js/dateutil.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="static/js/handlebars-v4.0.2.js" charset="utf-8"></script>
<script type="text/javascript" src="static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript" src="static/brandincome/js/brand_record_audit.js" charset="utf-8"></script>
<script type="text/javascript">


</script>
</body>

</html>