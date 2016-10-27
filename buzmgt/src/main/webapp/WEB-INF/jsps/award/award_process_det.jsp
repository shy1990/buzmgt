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
    <meta charset="UTF-8">
    <base href="<%=basePath%>"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查看进程</title>

    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/bootStrapPager/css/page.css" />
    <link rel="stylesheet" type="text/css" href="static/award/award_process_det.css" />
    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <style>
    </style>
    <%--品牌型号进程明细开始--%>
    <script id="detail-table-template" type="text/x-handlebars-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{shopName}}</td>
            <td>
                {{namepath}}
            </td>
            <td>{{orderNum}}</td>
            <td><span class="text-lv text-strong">{{goodsName}}</span></td>
            <td>{{nums}}台</td>
            <td>{{formDate payTime}}</td>
            <td>
                <button class="btn btn-sm btn-blue" onclick="orderDetail('{{orderNum}}');">查看订单</button>
            </td>
        </tr>
        {{/each}}
        {{else}}
        <tr>
            <td colspan="100">没有相关数据</td>
        </tr>
        {{/if}}
    </script>
    <%--品牌型号进程明细结束--%>
    <%--品牌型号售后明细开始--%>
    <script id="returnDetail-table-template" type="text/x-handlebars-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{shopName}}</td>
            <td>
                {{namepath}}
            </td>
            <td>{{orderno}}</td>
            <td><span class="text-lv text-strong">{{goodsName}}</span></td>
            <td>{{sum}}台</td>
            <td>{{formDate shdate}}</td>
            <td>
                <button class="btn btn-sm btn-blue" onclick="orderDetail('{{orderno}}');">查看订单</button>
            </td>
        </tr>
        {{/each}}
        {{else}}
        <tr>
            <td colspan="100">没有相关数据</td>
        </tr>
        {{/if}}
    </script>
    <%--品牌型号售后明细结束--%>
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
    <h4 class="page-header ">
        <i class="ico icon-ck"></i>明细
        <!--区域选择按钮-->
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
        <%--<input id="goodId" hidden="hidden" value="${goodId}">--%>
        <input id="awardId" hidden="hidden" value="${award.awardId}">
    </h4>
    <div class=" inform">


        <div class="row">
            <div class="col-sm-4 info-zq">
                <img src="../../static/img/pic1.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery text-strong font-w">周期销量</p>
                <p class="text-lv text-16 text-strong">台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="../../static/img/pic-2.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class=" text-strong text-gery  font-w ">退货冲减</p>
                <p class="text-jv text-16">台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="../../static/img/pic3.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong">实际销量</p>
                <p class="text-gren text-16">台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="../../static/img/redq.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong">预计收益</p>
                <p class="text-red text-16 text-strong">1230.05元</p>
            </div>
        </div>

        <hr class="hr-l">

        <div class="row" style="padding-left: 20px">
            <div class="col-sm-4 product">
                <span class="text-strong text-gery">达量奖励产品：</span>
                <c:if test="${!empty award.awardGoods}">
                    <c:forEach items="${award.awardGoods}" var="awardGood" varStatus="status">
                        <span class="text-lan text-strong">${awardGood.brand.name} ${awardGood.good.name}</span>
                    </c:forEach>
                </c:if>
            </div>
            <div class="col-sm-4 pro-2">
                <span class="text-strong text-gery">达量奖励指标：</span>
                <span class="text-lv text-strong"> ${award.numberFirst}台/${award.numberSecond}台/${award.numberThird}台</span>
            </div>
            <div class="col-sm-4 pro-2">
                <span class="text-strong text-gery">起止日期：</span>
                <span class="text-black text-strong J_date"> </span>
            </div>
        </div>

        <%--<div class="row" style="padding: 20px;">
            <div class="col-sm-4 product">
                <span class="text-strong text-gery">奖罚：</span>
                <span style="background: #fafafa;padding: 5px"> <span class="text-71 ">销售  <</span> <span
                        class="text-lan">300</span>  <span class="text-71">台 &nbsp; 奖励：</span> <span
                        class="text-red">-9.00</span> <span class="text-71">元/台</span></span>
            </div>
            <div class="col-sm-4 pro-2-2">
                 <span style="background: #fafafa;padding: 5px"> <span class="text-71 ">销售大于300台  奖励：</span> <span
                         class="text-lan">300</span>  <span class="text-71"> 台 &nbsp; 奖励：</span> <span
                         class="text-gren">+5.00</span> <span class="text-71">元/台</span></span>
            </div>

        </div>--%>

        <div class="row" style="padding: 20px; ">
            <div class="col-sm-4 " style="width: 1200px">
                <span class="text-strong text-gery">补充说明：</span>
                <span style="background: #fafafa;padding: 5px"> <span class="text-71 ">跨界，互联网常说的跨界方式无外乎两种：一.传统企业人员学习相关的技术，把业务进行系统化、流程化 ，从而进行数据数据分析统计，算出用户的潜在的行为需求。 </span></span>
            </div>


        </div>


    </div>


    <br>


    <div class="clearfix"></div>
    <div>
        <select class="ph-select J_regionId">
            <option value="${region.id}" selected="selected">${region.name}</option>
            <c:if test="${!empty regions}">
                <c:forEach items="${regions}" var="regions" varStatus="status">
                    <option value="${regions.id}">${regions.name}</option>
                </c:forEach>
            </c:if>
        </select>

        <select class="ph-select J_orderType">
            <option value="sales" selected="selected">销量订单</option>
            <option value="return">退货冲减订单</option>
        </select>


        <button class="btn btn-blue btn-sm" style="margin-left: 10px" onclick="goFilter();">
            筛选
        </button>

        <div class="link-posit-t pull-right export">
            <input class="ph-select text-gery-hs J_terms" placeholder="  请输入商家名称或订单号">
            <button class="btn btn-blue btn-sm" onclick="goSearch();">
                检索
            </button>
            <a class="table-export" href="javascript:void(0);">导出excel</a>
        </div>

    </div>

    <div class="tab-content ">
        <!--table-box-->

        <div class=" new-table-box table-overflow ">
            <table class="table table-hover new-table">
                <thead>
                <tr>
                    <th>商家名称</th>
                    <th>区域</th>
                    <th>订单号</th>
                    <th>产品</th>
                    <th>数量</th>
                    <th>付(退)款日期</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="detailList">

                </tbody>
            </table>
        </div>
        <div id="initDetailPager"></div>
        <!--table-box-->

        <!--待审核账单-->
    </div>

    <!--油补记录-->
</div>

<![endif]-->
<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/js/dateutil.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript" src="<%=basePath%>static/award/award_process_det.js" charset="utf-8"></script>

</body>
</html>




