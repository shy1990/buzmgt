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
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>价格区间</title>

    <link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" href="../static/phone-set/css/phone.css">
    <link rel="stylesheet" href="../static/earnings/css/comminssion.css">
    <link rel="stylesheet" type="text/css" href="/static/bootStrapPager/css/page.css"/>
    <link rel="stylesheet" type="text/css"
          href="/static/achieve/achieve.css">
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js" charset="utf-8"></script>
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

        .icon-je {
            background: url("img/tcje.png") no-repeat center;
        }

        .ph-icon {
            height: 30px;
            padding: 5px 10px;
            font-size: 12px;
            line-height: 1.5;
            border-radius: 3px;
        }

        .icon-riz {
            background: url("<%=basePath%>static/earnings/img/rizi.png") no-repeat center;
        }

        .icon-reny {
            background: url("<%=basePath%>static/earnings/img/shry.png") no-repeat center;
        }
    </style>
</head>
<body>

<div class="content main">
    <input id="planId" hidden="hidden" value="${planId }">
    <input id="checkId" hidden="hidden" value="${check }">
    <h4 class="page-header">
        <i class="ico ico-tcsz"></i>提成设置
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>
    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->
            <div class="order-box">
                <!--左侧导航开始-->
                <div style="padding-left: 0">
                    <div class=" sidebar left-side"
                         style="padding-top: 0; margin-top: 5px">
                        <ul class="nav nav-sidebar menu J_MachineType">
                            <li><i class="ico ico-fl"></i>请选择类别</li>
                            <c:forEach items="${machineTypes}" var="type" varStatus="status">
                                <c:choose>
                                    <c:when test="${status.index eq 1 }">
                                        <li class="active" title="${type.code }">${type.name }</li>
                                    </c:when>
                                    <c:otherwise>
                                        <li title="${type.code }">${type.name }</li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div style="margin-left: 260px;">
                    <ul class="nav nav-pills  nav-top" id="myTab" >
                        <li class="active"><a data-toggle="tab" href="#tab_now">正在进行</a></li>
                        <li><a data-toggle="tab" href="#tab_over">已过期</a></li>
                        <li><a data-toggle="tab" href="#tab_new">新建审核</a></li>
                        <%--<li><a data-toggle="tab" href="#tab_modify">修改审核</a></li>--%>
                    </ul>
                </div>
                <script>
                    $('a[data-toggle="phone"]').on('shown.bs.tab', function (e) {
                        e.target // newly activated tab
                        e.relatedTarget // previous active tab
                    })

                </script>
                <!--左侧导航结束-->
                <div class="tab-content">
                    <!--右侧内容开始-->
                    <!--正在进行-->
                    <div class="tab-pane fade in active right-body" id="tab_now">
                        <div class="table-task-list new-table-box table-overflow" style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>方案</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>设置日期</th>
                                    <th>审核人</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="on_going_tbody">
                                </tbody>
                            </table>
                        </div>

                    </div>
                    <!--过期-->
                    <div class="tab-pane fade in  right-body" id="tab_over">
                        <div class="table-task-list new-table-box table-overflow" style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>方案</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>设置日期</th>
                                    <th>审核人</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="over_tbody">
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <%--修改审核--%>
                    <div class="tab-pane fade in  right-body" id="tab_modify">
                        <div class="table-task-list new-table-box table-overflow" style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>价格区间</th>
                                    <th>提成</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核状态</th>
                                    <th>设置日期</th>
                                    <th>审核人</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="modify_tbody">
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <!--新建审核-->
                    <div class="tab-pane fade in  right-body" id="tab_new">
                        <div class="table-task-list new-table-box table-overflow" style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>方案</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>设置日期</th>
                                    <th>审核人</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="new_tbody">
                                </tbody>
                            </table>
                        </div>

                    </div>
                    <!--修改-->
                    <div id="gaigai" class="modal fade" role="dialog">
                        <div class="modal-dialog " role="document">
                            <div class="modal-content modal-blue">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                    <h3 class="modal-title">修改</h3>
                                </div>
                                <div class="modal-body">
                                    <div class="container-fluid">
                                        <form id="addd" class="form-horizontal">
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">选择价格区间：</label>
                                                <div class="col-sm-7">
                                                    <span class="text-gery">0-50</span>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">设置提成金额：</label>
                                                <div class="col-sm-7">
                                                    <div class="input-group are-line">
                                                        <span class="input-group-addon "><i
                                                                class="ph-icon   icon-je"></i></span>
                                                        <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                                        <input name="a" type="text" class="form-control input-h"
                                                               aria-describedby="basic-addon1" placeholder="请设置提成金额">
                                                        </input>
                                                    </div>
                                                    <span class="text-gery "
                                                          style="float: right;margin-right: -45px;margin-top: -25px">元/台</span>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">方案实施日期：</label>
                                                <div class="col-sm-7">
                                                    <div class="input-group are-line">
                                                        <span class="input-group-addon "><i
                                                                class=" ph-icon icon-riz"></i></span>
                                                        <input type="text" class="form-control form_datetime "
                                                               placeholder="请选择实施日期"
                                                               readonly="readonly  " style="background: #ffffff;">
                                                    </div>
                                                    <span class="text-gery "
                                                          style="float: right;margin-right: -30px;margin-top: -25px">起</span>
                                                </div>
                                            </div>


                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">指派审核人员：</label>
                                                <div class="col-sm-7">
                                                    <div class="input-group are-line">
                                                        <span class="input-group-addon"><i
                                                                class="ph-icon icon-reny"></i></span>
                                                        <select name="b" type="" class="form-control input-h "
                                                                aria-describedby="basic-addon1">
                                                            <option></option>
                                                            <option>中国银行</option>
                                                            <option>农业银行</option>
                                                            <option>工商银行</option>
                                                            <option>亚细亚银行</option>
                                                        </select>
                                                        <!-- /btn-group -->
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-sm-offset-4 col-sm-4 ">
                                                    <a herf="javascript:return 0;" onclick="addd(this)"
                                                       class="Zdy_add  col-sm-12 btn btn-primary">保存
                                                    </a>
                                                </div>
                                            </div>
                                        </form>
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
                <script type="text/javascript">
                    $('#tab li').click(function () {
                        $(this).addClass('active');
                        $(this).siblings('li').removeClass('active');
                        $('#tab li:eq(1) a').tab('show');
                    });
                    $(".J_MachineType li").on("click", function () {
                        $(this).addClass("active");
                        $(this).siblings("li").removeClass("active");
                        nowList();
                        overList();
//                        modifyList();
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

                <%-- 进行中的模板 --%>
                <script id="on-going-template" type="text/x-handlebars-template">
                    {{#each this}}
                    <tr>
                        <td>{{addOne @index}}</td>
                        <td>
                            {{#if productionName}}
                                {{productionName}}
                            {{else}}
                                <span style="color: red">没有指定方案名</span>
                            {{/if}}
                        </td>
                        <td>{{implDate}}</td>

                        <td>
                            {{#if endTime}}
                                {{endTime}}
                            {{else}}
                                <span style="color: #6a0505">未设置结束日期</span>
                            {{/if}}
                        </td>
                        <td>{{checkStatus productStatus}}</td><!-- 审核状态 -->
                        <td>
                            {{checkUseStatus productStatus implDate endTime}}
                        </td><!-- 使用状态 -->
                        <td>{{createTime}}</td>
                        <td>{{productionAuditor}}</td><!-- 审核人 -->
                        <td>
                            <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#"
                                    onclick="showDetails('{{productionId}}')">详情
                            </button>
                            {{checkDelete productionId productStatus}}
                        </td>
                    </tr>
                    {{/each}}
                </script>

                <%-- 过期的模板 --%>
                <script id="over-template" type="text/x-handlebars-template">
                    {{#each this}}
                    <tr>
                        <td>{{addOne @index}}</td>
                        <td>
                            {{#if productionName}}
                            {{productionName}}
                            {{else}}
                            <span style="color: red">没有指定方案名</span>
                            {{/if}}
                        </td>
                        <td>{{implDate}}</td>
                        <td>
                            {{#if endTime}}
                            {{endTime}}
                            {{else}}
                            <span style="color: #6a0505">未设置结束日期</span>
                            {{/if}}
                        </td>
                        <td>{{checkStatus productStatus}}</td><!-- 使用状态 -->
                        <td><span class="ph-on">--</span></td><!-- 审核状态 -->
                        <td>{{createTime}}</td>
                        <td>{{productionAuditor}}</td><!-- 审核人 -->
                        <td>
                            <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#"
                                    onclick="showDetails('{{productionId}}')">详情
                            </button>
                        </td>
                    </tr>
                    {{/each}}
                </script>
                    <%-- 新建审核的模板 --%>
                    <script id="new-template" type="text/x-handlebars-template">
                        {{#each this}}
                        {{#show productStatus}}
                        <tr>
                            <td>{{addOne @index}}</td>
                            <td>
                                {{#if productionName}}
                                {{productionName}}
                                {{else}}
                                <span style="color: red">没有指定方案名</span>
                                {{/if}}
                            </td>
                            <td>{{implDate}}</td>

                            <td>
                                {{#if endTime}}
                                {{endTime}}
                                {{else}}
                                <span style="color: #6a0505">未设置结束日期</span>
                                {{/if}}
                            </td>
                            <td>{{checkStatus productStatus}}</td><!-- 审核状态 -->
                            <td>
                                {{checkUseStatus productStatus implDate endTime}}
                            </td><!-- 使用状态 -->
                            <td>{{createTime}}</td>
                            <td>{{productionAuditor}}</td><!-- 审核人 -->
                            <td>
                                <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#"
                                        onclick="showDetails('{{productionId}}')">详情
                                </button>
                            </td>
                        </tr>
                        {{/show}}
                        {{/each}}
                    </script>

                <%--&lt;%&ndash;修改审核模板&ndash;%&gt;--%>
                <%--<script id="modify-template" type="text/x-handlebars-template">--%>
                    <%--{{#if this}}--%>
                    <%--{{#each this}}--%>
                    <%--{{#show priceRangeStatus}}--%>
                    <%--<tr>--%>
                        <%--<td>{{addOne @index}}</td>--%>
                        <%--<td>{{priceRange}}</td>--%>
                        <%--<td>{{percentage}}</td>--%>
                        <%--<td>{{implementationDate}}</td>--%>
                        <%--<td>--%>
                            <%--{{#if endTime}}--%>
                            <%--{{endTime}}--%>
                            <%--{{else}}--%>
                            <%--<span style="color: #6a0505">未设置结束日期</span>--%>
                            <%--{{/if}}--%>
                        <%--</td>--%>
                        <%--<td><a href="#">查看</a></td>--%>
                        <%--<td>{{checkStatus priceRangeStatus}}</td>--%>
                        <%--<td>{{priceRangeCreateDate}}</td>--%>
                        <%--<td>{{priceRangeAuditor}}</td>--%>
                        <%--<td>--%>
                            <%--{{#if oldId}}--%>
                            <%--{{#checkReview userId}}--%>
                            <%--<button class="btn  bnt-sm bnt-ck"--%>
                                    <%--onclick="reviewPriceRange('{{priceRangeId}}','3')">--%>
                                <%--通过--%>
                            <%--</button>--%>
                            <%--<button class="btn  bnt-sm bnt-ck"--%>
                                    <%--onclick="reviewPriceRange('{{priceRangeId}}','2')">--%>
                                <%--驳回--%>
                            <%--</button>--%>
                            <%--{{/checkReview}}--%>
                            <%--{{else}}--%>
                            <%--<button class="btn  bnt-sm bnt-ck">--%>
                                <%------%>
                            <%--</button>--%>
                            <%--<button class="btn  bnt-sm bnt-ck">--%>
                                <%------%>
                            <%--</button>--%>
                            <%--{{/if}}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--{{/show}}--%>
                    <%--{{/each}}--%>
                    <%--{{else}}--%>
                    <%--<tr>--%>
                        <%--<td colspan="100">没有相关数据</td>--%>
                    <%--</tr>--%>
                    <%--{{/if}}--%>
                <%--</script>--%>
                <script type="text/javascript">
                    var total = 0;
                    var totalCount = 0;
                    var limit = 0;
                    var searchData = {
                        "size": "20",
                        "page": "0"
                    }
                    $(function () {
                        nowList();
                        overList();
//                        modifyList();
                    });
                    //注册下坐标加1
                    Handlebars.registerHelper("addOne", function (index) {
                        return (index + 1) + '';
                    });
                    //被驳回的有删除功能
                    Handlebars.registerHelper("checkDelete", function (productionId,productStatus) {
                        if ((productStatus == '2')&& '${check}' == '2') {
                            return new Handlebars.SafeString('<button class="btn  bnt-sm bnt-zza " data-toggle="modal" data-target="#"onclick="deleteReject(' + productionId + ')">删除 </button>');
                        }
                    });

                    //判断有没有审核修改功能
                    <%--Handlebars.registerHelper("checkReview",function (auditor,options) {--%>
                        <%--if((auditor == ${managerId} || ${managerId} == '1') && oldId != null){--%>
                            <%--return options.fn(this);--%>
                        <%--}--%>
                    <%--});--%>
                    //注册审核状态
                    Handlebars.registerHelper("checkStatus",function(status){
                        if(status == 0){
                            return new Handlebars.SafeString('<span>创建中</span>');
                        }else if(status == 1){
                            return new Handlebars.SafeString('<span class="text-hong text-strong">待审核</span>');
                        }else if(status == 2){
                            return new Handlebars.SafeString('<span class="text-zi text-strong">被驳回</span>');
                        }else if(status == 3) {
                            return new Handlebars.SafeString(' <span class="text-lan text-strong">已审核</span>');
                        }
                    });
                    //注册使用状态
                    Handlebars.registerHelper("checkUseStatus",function(status,implDate,endTime){
                        var today  = '${today}';
                        var today1 = new Date(today.replace("-","/").replace("-","/"));
                        var implDate1 = new Date(implDate.replace("-","/").replace("-","/"));
                        if(status == 3){
                            if(endTime != null && '' != endTime ){
                                var endTime1 = new Date(endTime.replace("-","/").replace("-","/"));
                                if( today1 <= endTime1 && today1 >= implDate1){
                                    return new Handlebars.SafeString('<span class="ph-on">使用中</span>');
                                }else {
                                    return new Handlebars.SafeString('<span class="ph-on">未使用</span>');
                                }
                            }
                            if(endTime == null || '' == endTime ){
                                if(today1 >= implDate1){
                                    return  new Handlebars.SafeString('<span class="ph-on">使用中</span>');
                                }else {
                                    return new Handlebars.SafeString('<span class="ph-on">未使用</span>');
                                }
                            }
                        }else {
                            return new Handlebars.SafeString('<span class="ph-on">--</span>');
                        }
                    });
                    Handlebars.registerHelper("show",function (status,options) {
                        if(status == '1'){
                            return options.fn(this);
                        }
                    });
                    //查询正在进行的数据
                    function nowList() {
                        var $machineType=$(".J_MachineType li.active").attr('title');
                        var $planId = $("#planId").val();
                        $.ajax({
                            url: '/priceRange/now',
                            data:{
                                planId:$planId,
                                type:$machineType},
                            type: 'POST',
                            success: function (data) {
                                console.log(data);
                                //注册进行中的模板
                                var onGoingTemplate = Handlebars.compile($("#on-going-template").html());
                                $("#on_going_tbody").html(onGoingTemplate(data));

                                //注册新建审核的模板
                                var newTemplate = Handlebars.compile($("#new-template").html());
                                $("#new_tbody").html(newTemplate(data));
                            },
                            error: function () {
                            }

                        });

                    }
                    //查询过期的数据
                    function overList(){
                        searchData.planId = $("#planId").val();
                        searchData.type = $(".J_MachineType li.active").attr('title');
                        $.ajax({
                            url: 'over',
                            type: 'POST',
                            data: searchData,
                            success: function (data) {
                                var content = data.content;
                                totalCount = data.totalElements;
                                limit = data.size;
                                handelerbars_register(content);//向模版填充数据
                                if (totalCount != total || totalCount == 0) {
                                    total = totalCount;
                                    initPaging();
                                }
                            },
                            error: function () {
                                alert('系统故障');
                            }

                        });

                    }

                    //修改审核信息(productionId)
//                    function modifyList(){
//                        var $machineType=$(".J_MachineType li.active").attr('title');
//                        var $planId = $("#planId").val();
//                        $.ajax({
//                            url:'/priceRange/findUse',
//                            type:'POST',
//                            data:{planId:$planId,type:$machineType},
//                            success:function(data){
//                                var modifyTemplate = Handlebars.compile($("#modify-template").html());
//                                $("#modify_tbody").html(modifyTemplate(data));
//                            },
//                            error:function(){
//                                alert("系统故障");
//                            }
//                        });
//                    }

                    //用于展示具体价格区间
                    function showDetails(productionId) {
                        console.log(productionId);
                        window.location.href = '/priceRange/details?productionId=' + productionId + '&check=' + ${check} + '&planId=' + ${planId};
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
                                listExpired(searchData);
                            }
                        });
                    }
                    //handelerbars填充数据
                    function handelerbars_register(content) {
                        var over_template = Handlebars.compile($("#over-template").html());//注册
                        $("#over_tbody").html(over_template(content));
                    }
                    function deleteReject(id) {
                        $.ajax({
                            url:'/section/realDelete/' + id,
                            success:function(data){
                                alert(data.msg);
                                window.location.reload();
                            },
                            error:function(){
                                alert('系统故障');
                            }

                        })
                    }
                </script>

</body>
</html>
