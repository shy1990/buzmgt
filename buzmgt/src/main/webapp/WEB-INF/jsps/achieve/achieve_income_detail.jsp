<%--
  Created by IntelliJ IDEA.
  User: ChenGuop
  Date: 2016/10/12
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>佣金明细</title>

    <!-- Bootstrap -->
    <link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="/static/achieve/achieve_income_detail.css">
    <link rel="stylesheet" type="text/css" href="static/bootStrapPager/css/page.css"/>
    <script src="static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <script type="text/x-handlebars-template" id="detail-table-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{order.shopName}}</td>
            <td>{{planUser.namepath}}</td>
            <td><span class="text-red">{{orderNo}}</span></td>
            <td><span class="text-lv text-strong">{{good.name}}</span></td>
            <td>{{num}}台</td>
            <td>{{formDate createDate}}</td>
            <td>
                <a class="btn btn-sm btn-blue" href="/achieveIncome/detail/{{order.id}}">查看订单</a>
            </td>
        </tr>
        {{/each}}
        {{else}}
        <tr>
            <td colspan="100">没有相关数据</td>
        </tr>
        {{/if}}
    </script>
    <script type="text/x-handlebars-template" id="aftersale-table-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{shopName}}</td>
            <td>{{namepath}}</td>
            <td><span class="text-red">{{orderno}}</span></td>
            <td><span class="text-lv text-strong">{{goodsName}}</span></td>
            <td>{{sum}}台</td>
            <td>{{formDate shdate}}</td>
            <td>
                <button class="btn btn-sm btn-blue">查看订单</button>
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
        };
        var SearchData_ = {
            'page': '0',
            'size': '20'
        };
        <%--var firstNum =${achieve.numberFirst}, secondNum =${achieve.numberSecond}, thirdNum =${achieve.numberThird==null};--%>
        var achieveJson=${achieveJson};//达量设置规则（全局变量）
    </script>
</head>
<body>
<body>
<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-ck"></i>明细
        <input type="hidden" value="${userId}" id="userId">
        <input type="hidden" value="${achieve.achieveId}" id="achieveId">
        <!--区域选择按钮-->
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>

    </h4>
    <div class=" inform">


        <div class="row">
            <div class="col-sm-4 info-zq">
                <img src="/static/earnings/img/pic1.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery text-strong font-w">周期销量</p>
                <p class="text-lv text-16 text-strong">${totalNumber}台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="static/earnings/img/pic-2.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class=" text-strong text-gery  font-w ">退货冲减</p>
                <p class="text-jv text-16">${retreatAmount}台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="static/earnings/img/pic3.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong">实际销量</p>
                <p class="text-gren text-16">${totalNumber-retreatAmount}台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="static/earnings/img/redq.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong">预计收益</p>
                <p class="text-red text-16 text-strong">${totalMoney}元</p>
            </div>
        </div>

        <hr class="hr-l">

        <div class="row" style="padding-left: 20px">
            <div class="col-sm-4 product">
                <span class="text-strong text-gery">达量产品：</span>
                <span class="text-lan text-strong">${achieve.machineType.name } ${achieve.good.name }</span>
            </div>
            <div class="col-sm-4 pro-2">
                <span class="text-strong text-gery">达量产品：</span>
                <span class="text-lv text-strong">
                    <c:if test="${achieve.numberFirst != null && achieve.numberFirst != ''}">
                    ${achieve.numberFirst}台/
                    </c:if>
                    <c:if test="${achieve.numberSecond != null && achieve.numberSecond != ''}">
                    ${achieve.numberSecond}台/
                    </c:if>
                    <c:if test="${achieve.numberThird != null && achieve.numberThird != ''}">
                    ${achieve.numberThird}台
                    </c:if>
                </span>
            </div>
            <div class="col-sm-4 pro-2">
                <span class="text-strong text-gery">起止日期：</span>
                <span class="text-black text-strong" id="srart-end-date">
                    <fmt:formatDate value="${achieve.startDate}" pattern="yyyy.MM.dd"/>-
                    <fmt:formatDate value="${achieve.endDate}" pattern="yyyy.MM.dd"/>
                </span>
            </div>
        </div>

        <div class="row" style="padding: 20px;">
            <div class="col-sm-11">
                <span class="text-strong text-gery">奖罚：</span>
                <c:forEach var="rule" items="${achieve.rewardPunishRules}">
                    <span style="background: #fafafa;padding: 5px">
                    (
                    <c:if test="${rule.min == 0}">
                        <span class="text-71 ">销量 &lt;</span>
                        <span class="text-lan">${rule.max}</span>
                        <span class="text-71">台 &nbsp; 奖励：</span>
                        <c:if test="${rule.money <0}">
                            <span class="text-red">${rule.money}</span>
                        </c:if>
                        <c:if test="${rule.money >= 0}">
                            <span class="text-gren">${rule.money}</span>
                        </c:if>
                        <span class="text-71">元/台</span></span>
                    </c:if>
                    <c:if test="${rule.max ==99999}">
                        <span class="text-lan">${rule.min}</span>
                        <span class="text-71 ">台 ≤ 销量 </span>
                        <span class="text-71">台 &nbsp; 奖励：</span>
                        <c:if test="${rule.money <0}">
                            <span class="text-red">${rule.money}</span>
                        </c:if>
                        <c:if test="${rule.money >= 0}">
                            <span class="text-gren">${rule.money}</span>
                        </c:if>
                        <span class="text-71">元/台</span></span>
                    </c:if>
                    <c:if test="${rule.min != 0 && rule.max !=99999 }">
                        <span class="text-lan">${rule.min}</span>
                        <span class="text-71 ">台 ≤ 销量 &LT;</span>
                        <span class="text-lan">${rule.max}</span>
                        <span class="text-71">台 &nbsp; 奖励：</span>
                        <c:if test="${rule.money <0}">
                            <span class="text-red">${rule.money}</span>
                        </c:if>
                        <c:if test="${rule.money >= 0}">
                            <span class="text-gren">${rule.money}</span>
                        </c:if>
                        <span class="text-71">元/台</span>
                    </c:if>
                    )
                    </span>
                </c:forEach>
            </div>
        </div>

        <div class="row" style="padding: 0px 20px  ">
            <div class="col-sm-4 " style="width: 1200px">
                <span class="text-strong text-gery">补充说明：</span>
                <span style="background: #fafafa;padding: 5px"> <span class="text-71 ">${achieve.remark}</span></span>
            </div>


        </div>


    </div>


    <br>


    <div class="clearfix"></div>
    <div>
        <%--<select class="ph-select">
            <option>山东省-德州市-武城县</option>
            <option>山东省-德州市-武城县</option>
            <option>山东省-德州市-武城县</option>
        </select>--%>

        <select class="ph-select" name="status" id="orderStatus">
            <option value="pay">销量订单</option>
            <option value="back">退货订单</option>
        </select>


        <button class="btn btn-blue btn-sm" style="margin-left: 10px" onclick="findStatus();">检索        </button>

        <div class="link-posit-t pull-right export">
            <input id="searchValue" class="ph-select text-gery-hs" placeholder="商家/订单号">
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
                    <th><span class="paydate">付款日期</span><span class="backdate">退货日期</span></th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="achieveDetailList"></tbody>
            </table>
            <div id="initPager"></div>
        </div>
        <!--table-box-->

        <!--待审核账单-->
    </div>

    <!--油补记录-->
</div>
<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="static/js/common.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="static/js/dateutil.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript" src="static/achieve/achieve_income_detail.js"></script>
</body>
</html>
