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
    <title>修改</title>

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
        <i class="ico icon-tjry"></i>修改
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>


    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->
            <form id="brandForm" onsubmit="return false;">
                <input type="hidden" class="J_planId" name="planId" value="${planId }">
                <input type="hidden" class="J_machineType" name="machineType" value="${machineType }">
            <div class="order-box">
                <ul>
                    <li class="new-li">
                        <dl class="dl-horizontal">
                            <dt>修改品牌型号：</dt>
                            <dd>
                                <span class="text-pronce">${brandIncome.brand.name}   &nbsp; ${brandIncome.good.name}</span>
                            </dd>
                        </dl>
                    </li>

                    <li class="new-li">
                        <dl class="dl-horizontal">
                            <dt>设置提成金额：</dt>
                            <dd>
                                <input type="text" placeholder="请输入提成金额" class="ph-select J_commissions">
                                <span class="text-strong text-gery">元/台</span>
                            </dd>
                        </dl>
                    </li>
                    <li class="new-li">
                        <dl class="dl-horizontal">
                            <dt>修改区域属性：</dt>
                            <dd class="text-bg"><c:if test="${!empty areaAttributes}">
                                <c:forEach var="areaAttr" items="${areaAttributes }" varStatus="status">
                                    <span class="text-pronce text-bg">${areaAttr.region.namepath}  <span class="text-red text-bg">${areaAttr.commissions}</span>元/台</span>
                                    <a href="" onclick="modify_attrSet(${areaAttr.id })"><span class="text-line">修改</span></a> <a href="" onclick="deleteAttrSet(${areaAttr.id })"><span class="text-line">删除</span></a>
                                </c:forEach>
                            </c:if>
                                <c:if test="${empty areaAttributes}">
                                    <span class="text-pronce text-bg">暂无</span>
                                </c:if>
                                <input type="hidden" id="ruleId" name="ruleId" value="${brandIncome.id}">
                            </dd>
                        </dl>
                    </li>
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

                                                    <option value="A37028706270" >胡老大</option>
                                                    <option value="A37028706270">横额啊</option>
                                                    <option value="VA">张二啦</option>
                                                    <option value="VA">王晓晓</option>
                                                    <option value="WV">杭大大</option>
                                                    <option value="WV">曹大大</option>
                                                    <option value="WI">槽大小</option>
                                                </select>
                                            </form>
                                        </div>
                                    </div>
                                </div>

                            </dd>
                        </dl>
                    </li>


                </ul>

                <button class="btn btn-primary col-sm-2" style="margin-left: 180px" onclick="toSubmit();">修改</button>
            </div>
            </form>
            <!--orderobx end-->
        </div>
        <!--col end-->
    </div>
    <!-- / alert 区域修改--->
    <div id="changed" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">设置区域提成</h3>
                </div>

                <div class="modal-body">
                    <div class="container-fluid">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">修改提成金额：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon "><i class="ph-icon   icon-je"></i></span>
                                        <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                        <input name="a" id="input_modify" type="text" class="form-control input-h"
                                               aria-describedby="basic-addon1" placeholder="请修改提成金额">
                                        </input>
                                    </div>
                                    <span class="text-gery " style="float: right;margin-right: -45px;margin-top: -25px">元/台</span>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-4 col-sm-4 ">
                                    <a herf="javascript:return 0;" id="sure_update"
                                       class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- / alert 区域修改--->
</div>
<script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="static/js/dateutil.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>static/js/H-select.js"></script>
<script type="text/javascript" src="static/js/handlebars-v4.0.2.js" charset="utf-8"></script>
<script type="text/javascript" src="static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript" src="static/brandincome/js/brand_alter.js" charset="utf-8"></script>
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

