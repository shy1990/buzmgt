<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>" />
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>新建</title>

    <link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/brandincome/css/brand_add.css"/>
    <link rel="stylesheet" href="static/multiselect/css/jquery.multiselect.css">
    <link rel="stylesheet" href="static/earnings/css/phone.css">
    <link rel="stylesheet" href="static/earnings/css/comminssion.css">

    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <style>

    </style>
    <script type="text/javascript">
        var	base='<%=basePath%>';
        var rule =new Array();//全局变量
    </script>
    <script id="goods-template" type="text/x-handlebars-template">
        {{#if this}}
        <option value="">型号</option>
        {{#each this}}
        <option value="{{id}}">{{name}}</option>
        {{/each}}
        {{else}}
        <option value="">型号</option>
        {{/if}}
    </script>
</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico icon-tjry"></i>新建
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>


    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->
            <form id="brandForm" onsubmit="return false;">
                <input type="hidden" class="J_planId" name="planId" value="${planId }">
                <input type="hidden" class="J_machineType" name="machineType" value="${machineType }">
                <input type="hidden" class="J_createTime" value="${createTime }">
                <input type="hidden" class="J_fqTime" value="${fqTime }">

            <div class="order-box">
                <ul>
                    <li class="new-li">
                        <dl class="dl-horizontal">
                            <dt>选择品牌型号：</dt>
                            <dd>
                                <select class="ph-select J_brand">
                                    <option value="">选择品牌</option>
                                    <c:forEach var="brandType" items="${brandTypes }" varStatus="status">
                                        <option value="${brandType.brandId }">${brandType.name }</option>
                                    </c:forEach>
                                </select>

                                <select id="goodList" class="ph-select J_goods">
                                    <option>选择型号</option>
                                </select>
                                <label class="pull-right col-md-8 control-label msg-error"></label>
                            </dd>
                        </dl>
                    </li>

                    <li class="new-li">
                        <dl class="dl-horizontal">
                            <dt>设置提成金额：</dt>
                            <dd>
                                <input type="text" placeholder="请输入提成金额" class="ph-select J_commissions">
                                <span class="text-strong text-gery">元/台</span>
                                <label class="pull-right col-md-8 control-label msg-error"></label>
                            </dd>
                        </dl>
                    </li>

                    <%--<li class="new-li">
                        <dl class="dl-horizontal">
                            <dt>添加区域属性：</dt>
                            <dd>
                                <a href=""><span class="text-strong text-line">区域设置</span></a>
                            </dd>
                        </dl>
                    </li>--%>

                    <li class="new-li">
                        <dl class="dl-horizontal">
                            <dt>方案起止日期：</dt>
                            <dd>

                                <div class="ph-search-date fl-left form_date_start">
                                    <input name="startDate" type="text" class="form-control form_datetime input-sm J_startDate" placeholder="选择日期"
                                           readonly="readonly" style="background: #ffffff; ">
                                </div>
                                <div class="text-strong text-gery fl-left" style="margin:10px" >至</div>


                                <div class="ph-search-date fl-left form_date_end">
                                    <input name="endDate" type="text" class="form-control form_datetime input-sm J_endDate" placeholder="选择日期"
                                           readonly="readonly" style="background: #ffffff">
                                </div>
                                <label class="pull-right col-md-8 control-label msg-error"></label>
                            </dd>
                        </dl>
                    </li>

                    <li class="new-li">
                        <dl class="dl-horizontal">
                            <dt>指派审核人员：</dt>
                            <dd>
                                <div class="col-sm-1 input-search" style="margin-left: 5px">
                                    <div class="input-group ">
                                        <span class="input-group-addon "><i class="icon-s icon-man"></i></span>
                                        <!--<input type="text" class="form-control" placeholder="请选择指派审核人员" aria-describedby="basic-addon1">-->
                                        <div class="inpt-search">
                                            <form >
                                                <select name="auditor" multiple="multiple" class="form-control demo3 J_auditor" style="padding: 0px" >
                                                    <c:if test="${!empty channelManagers}">
                                                        <c:forEach var="manager" items="${channelManagers }" varStatus="status">
                                                            <option value="${manager.userId}" >${manager.name}</option>
                                                        </c:forEach>
                                                    </c:if>
                                                </select>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </dd>
                        </dl>
                    </li>


                </ul>

                <button class="btn btn-primary col-sm-2" style="margin-left: 180px" onclick="toSubmit();">保存并设置区域属性</button>
            </div>
            </form>
            <!--orderobx end-->
        </div>
        <!--col end-->
    </div>

</div>
<script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath%>static/js/dateutil.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>static/js/H-select.js"></script>
<script type="text/javascript" src="static/js/handlebars-v4.0.2.js" charset="utf-8"></script>
<script type="text/javascript" src="static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript" src="static/brandincome/js/brand_add.js" charset="utf-8"></script>
<script>
    $('select[multiple].demo3').multiselect({
        columns: 1,
        placeholder: '请选择指派审核人员',
        search: true,
        selectGroup: true
    });



</script>
</body>
</html>

