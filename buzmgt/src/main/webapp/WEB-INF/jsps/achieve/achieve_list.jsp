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
    <link rel="stylesheet" type="text/css"
          href="static/phone-set/css/phone.css">
    <link rel="stylesheet" type="text/css"
          href="static/phone-set/css/comminssion.css">
    <link rel="stylesheet" type="text/css"
          href="static/achieve/achieve.css">
    <link rel="stylesheet" type="text/css"
          href="static/bootStrapPager/css/page.css"/>
    <script src="static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <%--叠加模板开始--%>
    <script id="section-table-template" type="text/x-handlebars-template">
        {{#if this}}
        {{#each this}}
        <tr>
            <td>{{serialNumber}}</td>
            <td>{{priceRange}}元</td>
            <td class="width-fixed">
                <span class="text-green">{{percentage}}元/台</span>
                <a href="javascript:;" class="btn btn-sm btn-findup" data-toggle="modal"
                   onclick="modify('{{priceRangeId}}','{{priceRange}}','{{productionId}}')">修改</a>
                <%-- data-target="#gaigai" --%>
            </td>
            <td>{{implementationDate}}</td>

            <td>
                {{#if priceRange.endTime}}
                <span style="color: #6a0505">未设置结束日期</span>
                {{else}}
                {{endTime}}
                {{/if}}
            </td>


            <td><a href="javascript:void(0)" onclick="seeRegion('{{priceRangeId}}')">查看区域设置</a></td>
            <td><span class="ph-on">进行中</span></td>
            <td>{{priceRangeCreateDate}}</td>
            <td>
                <button class="btn btn-sm btn-zz " data-toggle="" data-target=""
                        onclick="del('{{priceRangeId}}')">终止
                </button>
            </td>
        </tr>
        {{/each}}
        {{else}}
        <tr>
            <td colspan="100">没有相关数据</td>
        </tr>
        {{/if}}


    </script>


    <%--品牌型号开始--%>
    <script id="brand-table-template" type="text/x-handlebars-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{addOne @index}}</td>
            <td>{{brand.name}}</td>
            <td>{{good.name}}</td>
            <td class="width-fixed">
                <span class="text-green">{{commissions}}元/台</span>
                <%--<a href="javascript:;" class="btn btn-sm btn-findup" data-toggle="modal"
                   data-target="#brand">修改</a>--%>
            </td>
            <td>{{formDate startDate}}-{{formDate endDate}}</td>
            <td><a href="javascript:void(0);" onclick="showArea('{{id}}');"><span class="text-blue">查看区域属性</span></a></td>
            <td><span class="ph-on">进行中</span></td>

            <td>{{formDate createDate}}</td>
            <td>
                <button class="btn bnt-sm bnt-ck" onclick="brandLook('{{id}}');">查看</button>
                <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#"
                        onclick="brandProcess('{{id}}');">进程
                </button>
                {{#ifNew id}}
                <button class="btn btn-sm btn-zz " data-toggle="modal" data-target="#brandStop"
                        onclick="brandStop('{{id}}');">终止
                </button>
                {{else}}
                {{/ifNew}}
            </td>
        </tr>
        {{/each}}
        {{else}}
        <tr>
            <td colspan="100">没有相关数据</td>
        </tr>
        {{/if}}
    </script>
    <%--品牌型号结束--%>
    <%--达量开始--%>
    <script id="achieve-table-template" type="text/x-handlebars-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{addOne @index}}</td>
            <td>{{brand.name}}</td>
            <td>{{good.name}}</td>
            <td class="reason">
				<span class="text-red">{{numberFirst}} 
			{{#if numberSecond}}
			| {{numberSecond}} 
			{{/if}}
			{{#if numberThird}}
			| {{numberThird}}
			{{/if}}
			</span>
            </td>
            <td>
                <span class="text-blue">{{formDate startDate}}-{{formDate endDate}}</span>
            </td>
            <td><span class="text-blue">{{formDate issuingDate}}</span></td>
            <td><span class="ph-on">进行中</span></td>
            <td>{{formDate createDate}}</td>
            <td>
                <a href="/achieve/list/{{achieveId}}" class="btn bnt-sm bnt-ck">查看</a>
                <a href="/achieve/course/{{achieveId}}" class="btn btn-sm bnt-jc">进程</a>
                {{#ifNew achieveId}}
                <button class="btn btn-sm btn-sc " onclick="delAchieve('{{achieveId}}')">删除</button>
                {{else}}
                {{/ifNew}}
            </td>
        </tr>
        {{/each}}
        {{else}}
        <tr>
            <td colspan="100">没有相关数据</td>
        </tr>
        {{/if}}
    </script>
    <%--达量结束--%>
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
        <i class="ico ico-tcsz"></i>提成设置 <a href="javascript:history.back();"><i
            class="ico icon-back fl-right"></i></a>
        <input id="planId" hidden="hidden" value="${planId }">
        <input id="checkId" hidden="hidden" value="${check }">
    </h4>

    <ul class="nav nav-pills  nav-top" id="myTab">
        <li title="priceSection"><a data-toggle="tab" href="#ajgqj">按价格区间</a></li>
        <li title="brandIncome"><a data-toggle="tab" href="#ppxhao">品牌型号<span
                class="qipao">2</span></a></li>
        <li class="active" title="achieve"><a data-toggle="tab" href="#dlsz">达量设置</a></li>
        <li title="superposition"><a data-toggle="tab" href="#djsz">叠加设置</a></li>
        <li title="award"><a href="/award/list?planId=${planId}">达量奖励</a></li>
    </ul>
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
                                    <c:when test="${status.index eq 0 }">
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
                <!--左侧导航结束-->
                <div class="tab-content">
                    <!--右侧内容开始-->

                    <%-- 按价格区间开始 --%>
                    <div class="tab-pane fade right-body" id="ajgqj">
                        <div class="ph-btn-set">
                            <c:if test="${check == '1'}"><!-- 渠道操作 -->
                                <a href="javascript:toReview();" class="btn ph-blue">
                                    <i class="ico icon-xj"></i> <span class="text-gery">审核</span>
                                </a>
                                <a href="javascript:setSectionRecord();" class="btn ph-blue" style="margin-right: 30px">
                                    <i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
                                </a>
                            </c:if>
                            <c:if test="${check == '2'}"><!-- 财务操作操作 -->
                                <a href="javascript:add_section();" class="btn ph-blue">
                                    <i class="ico icon-xj"></i> <span class="text-gery">添加</span>
                                </a>
                                <a href="javascript:setSectionRecord();" class="btn ph-blue" style="margin-right: 30px">
                                    <i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
                                </a>
                            </c:if>
                            <%--<a href="javascript:add_section();" class="btn ph-blue">--%>
                                <%--<i class="ico icon-xj"></i> <span class="text-gery">添加</span>--%>
                            <%--</a>--%>
                            <%--<a href="javascript:setSectionRecord();" class="btn ph-blue" style="margin-right: 30px">--%>
                                <%--<i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>--%>
                            <%--</a>--%>
                        </div>
                        <div class="table-task-list new-table-box table-overflow" style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>价格区间</th>
                                    <th>提成金额</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>区域属性</th>
                                    <th>状态</th>
                                    <th>设置日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="sectionList">

                                </tbody>
                            </table>
                        </div>

                    </div>
                    <%--终止弹出框--%>
                    <div id="del" class="modal fade" role="dialog">
                        <div class="modal-dialog " role="document">
                            <div class="modal-content modal-blue">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span></button>
                                    <h3 class="modal-title">是否终止</h3>
                                </div>

                                <div class="modal-body">
                                    <div class="container-fluid">
                                        <%--<form class="form-horizontal">--%>
                                        <div class="form-group">
                                            注意:此操作是终止到前一天晚上0点,即结束时间为前一天
                                        </div>

                                        <div class="btn-qx">
                                            <a type="submit" class="btn btn-danger btn-d" id="sure_del">确认</a>
                                        </div>

                                        <div class="btn-dd">
                                            <a type="submit" data-dismiss="modal" class="btn btn-primary btn-d">取消</a>
                                        </div>

                                        <%--</form>--%>
                                    </div>
                                </div>
                            </div>
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
                                        <form id="modifySection" class="form-horizontal">
                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">价格区间：</label>
                                                <div class="col-sm-7">
                                                    <span id="priceRange" class="text-gery"></span>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">设置提成金额：</label>
                                                <div class="col-sm-7">
                                                    <div class="input-group are-line">
                                                        <span class="input-group-addon "><i class="ph-icon   icon-je"></i></span>
                                                        <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                                        <input name="a" type="text" class="form-control input-h"
                                                               aria-describedby="basic-addon1" placeholder="请设置提成金额"
                                                               onchange="kkk(this.value,this)">
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
                                                        <span class="input-group-addon "><i class=" ph-icon icon-riz"></i></span>
                                                        <input type="text" class="form-control form_datetime " placeholder="请选择实施日期"
                                                               readonly="readonly  " style="background: #ffffff;" name="implDate">
                                                    </div>
                                                    <span class="text-gery "
                                                          style="float: right;margin-right: -30px;margin-top: -25px">起</span>
                                                </div>
                                            </div>


                                            <div class="form-group">
                                                <label class="col-sm-4 control-label">指派审核人员：</label>
                                                <div class="col-sm-7">
                                                    <div class="input-group are-line">
                                                        <span class="input-group-addon"><i class="ph-icon icon-reny"></i></span>
                                                        <select name="b" type="" class="form-control input-h "
                                                                aria-describedby="basic-addon1" id="select-auditor">

                                                        </select>
                                                        <!-- /btn-group -->
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <div class="col-sm-offset-4 col-sm-4 ">
                                                    <a herf="javascript:return 0;" id="sure_save"
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




                    <%--按价格区间结束--%>


                    <!--品牌型号-->
                    <div class="tab-pane fade right-body" id="ppxhao">
                        <!--导航开始-->

                        <div class="ph-btn-set">
                            <c:if test="${check eq '2'}">
                                <a href="javascript:add_brand();" class="btn ph-blue">
                                    <i class="ico icon-xj"></i> <span class="text-gery">添加</span>
                                </a>
                                <a href="javascript:setRecord();" class="btn ph-blue" style="margin-right: 30px">
                                    <i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
                                </a>
                            </c:if>
                            <c:if test="${check eq '1'}">
                                <a href="javascript:toAudit();" class="btn ph-blue" style="margin-right: 30px">
                                    <i class="ico icon-jl"></i> <span class="text-gery">渠道审核</span>
                                </a>
                            </c:if>
                            <div class="link-posit pull-right">
                                <input class="input-search" type="text" placeholder="模糊查询请输入品牌型号">
                                <button onclick="goSearch();" class="btn  btn-sm bnt-ss ">搜索</button>
                                <a class="table-export" href="javascript:void(0);">导出excel</a>
                            </div>


                        </div>


                        <div class="table-task-list new-table-box table-overflow" style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>品牌</th>
                                    <th>型号</th>
                                    <th>提成金额</th>
                                    <th>起止日期</th>
                                    <th>区域属性</th>
                                    <th>状态</th>
                                    <th>设置日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="brandIncomeList">

                                </tbody>
                            </table>
                        </div>
                        <div id="initBrandPager"></div>
                    </div>

                    <!--达量设置-->
                    <div class="tab-pane fade in active right-body" id="dlsz">
                        <div class="ph-btn-set">
                            <c:if test="${check != 1}">
                                <a href="javascript:add();" class="btn ph-blue"> <i class="ico icon-xj"></i>
                                    <span class="text-gery">添加</span>
                                </a>
                            </c:if>
                            <c:if test="${check == 1}">
                                <a href="JavaScript:recordForAudit();" class="btn ph-blue" style="margin-right: 30px">
                                    <i class="ico icon-xj"></i> <span class="text-gery">审核</span>
                                </a>
                            </c:if>
                            <a href="JavaScript:record();" class="btn ph-blue" style="margin-right: 30px">
                                <i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
                            </a>
                            <div class="link-posit pull-right">
                                <input id="searchGoodsname" class="input-search" type="text"
                                       placeholder="模糊查询请输入品牌型号">
                                <button onclick="goSearch();" class="btn  btn-sm bnt-ss ">搜索</button>
                                <a class="table-export" href="javascript:void(0);">导出excel</a>
                            </div>
                        </div>

                        <div class="table-task-list new-table-box table-overflow"
                             style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>品牌</th>
                                    <th>型号</th>
                                    <th>达量规则</th>
                                    <th>方案起止日期</th>
                                    <th>佣金发放</th>
                                    <th>状态</th>
                                    <th>设置日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="achieveList">
                                </tbody>
                            </table>
                        </div>
                        <div id="initPager"></div>

                    </div>
                    <!--叠加设置-->

                    <!--达量奖励-->

                </div>

            </div>

        </div>

    </div>
    <!--品牌型号修改开始-->
    <div id="brand" class="modal fade" role="dialog">
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
                                        <span class="input-group-addon "><i class="ph-icon   icon-je"></i></span>
                                        <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                        <input name="a" type="text" class="form-control input-h"
                                               aria-describedby="basic-addon1" placeholder="请设置提成金额">
                                        </input>
                                    </div>
                                    <span class="text-gery " style="float: right;margin-right: -45px;margin-top: -25px">元/台</span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">方案实施日期：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon "><i class=" ph-icon icon-riz"></i></span>
                                        <input type="text" class="form-control form_datetime " placeholder="请选择实施日期"
                                               readonly="readonly  " style="background: #ffffff;">
                                    </div>
                                    <span class="text-gery " style="float: right;margin-right: -30px;margin-top: -25px">起</span>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-4 control-label">指派审核人员：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon"><i class="ph-icon icon-reny"></i></span>
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
    <!--品牌型号修改结束-->
    <!--品牌型号终止提示-->
    <div id="brandStop" class="modal fade" role="dialog">
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
                                <p class="col-sm-12  ">该品牌方案当前正在使用，你确定要终止方案吗？</p>
                                <p class="col-sm-12">终止后该方案将不复存在，所有提成规则及使用人员将使用到终止日期为止！</p>
                            </div>


                            <div class="btn-qx">
                                <input id="brandId" hidden="hidden" value="">
                                <button type="button" class="btn btn-danger btn-d" onclick="stop();">终止</button>
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
    <!--品牌型号终止提示-->

</div>
<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="static/js/common.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="static/js/dateutil.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript"
        src="static/achieve/achieve_list.js" charset="utf-8"></script>
<script type="text/javascript">
    $(".J_MachineType li").on("click", function () {
        $(this).addClass("active");
        $(this).siblings("li").removeClass("active");
    });
    function toAudit() {
        var planId = $("#planId").val();
        window.location.href = base + "brandIncome/toAudit?planId=" + planId;
    }

    /*查看区域属性*/
    function showArea(id) {
        window.location.href = '/areaAttr/show?ruleId='+id +'&type=BRANDMODEL';
    }
    function toReview(){
        var planId = $("#planId").val();
        window.location.href = '/section/toReviewJsp?type=znj&planId='+ planId;

    }
</script>
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
<script id="auditor-template" type="text/x-handlebars-template">
    {{#each this}}
    <option value='{{userId}}'>{{name}}</option>
    {{/each}}

</script>
<script type="text/javascript">
    findChannelManager();
    //查找审核人员
    function findChannelManager() {
        $.ajax({
            url: '<%=basePath%>section/findChannelManager',
            type: 'GET',
            success: function (data) {
                console.log(data);
                var auditorTemplate = Handlebars.compile($("#auditor-template").html());
                $('#select-auditor').html(auditorTemplate(data));

            }

        })

    }
</script>
</body>

</html>