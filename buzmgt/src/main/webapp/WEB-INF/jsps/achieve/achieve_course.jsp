<%--
  Created by IntelliJ IDEA.
  User: ChenGuop
  Date: 2016/10/8
  Time: 15:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--<%@ taglib prefix="json" uri="/static/tld"%>--%>
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
    <link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="/static/achieve/achieve_course.css">
    <link rel="stylesheet" type="text/css" href="static/bootStrapPager/css/page.css"/>
    <script src="static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <script type="text/x-handlebars-template" id="course-table-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{addOne @index}}</td>
            <td>{{userVo.truename}}</td>
            <td>
                {{{disposeStar starsLevel}}}
                {{userVo.namepath}}
            </td>
            <td><span class="text-zi text-strong">{{userVo.levelName}}</span></td>
            <td>
                <span class="text-red">{{{disposeNum userId}}}</span>
            </td>
            <td>
                <span class="text-lv text-strong">{{{disposeGroup userId}}}组</span>
            </td>
            <td>{{num}}台</td>
            <td>{{start-end-Date}}</td>
            <td><span class="text-lv text-strong">进行中</span></td>
            <td>
                <button class="btn btn-sm btn-blue">查看明细</button>
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
        var firstNum =${achieve.numberFirst}, secondNum =${achieve.numberSecond}, thirdNum =${achieve.numberThird};
        var achieveJson=${achieveJson};//达量设置规则（全局变量）
    </script>
</head>
<body>
<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-ck"></i>查看进程
        <!--区域选择按钮-->
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>

    </h4>
    <div class=" inform">
        <div class="row">
            <div class="col-sm-4 product">
                <span class="text-strong text-gery">达量产品：</span>
                <span class="text-lan text-strong">${achieve.good.name }</span>
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
            <div class="col-sm-4 product">
                <span class="text-strong text-gery">起止日期：</span>
                <span class="text-black text-strong" id="srart-end-date"> <fmt:formatDate value="${achieve.startDate}"
                                                                      pattern="yyyy.MM.dd"/>-
                    <fmt:formatDate value="${achieve.endDate}" pattern="yyyy.MM.dd"/></span>
            </div>
        </div>

        <hr class="hr-l">

        <div class="row">
            <div class="col-sm-4 info-zq">
                <img src="/static/earnings/img/pic1.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery text-strong font-w">周期销量</p>
                <p class="text-lv text-16 text-strong">${totalNumber }台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="/static/earnings/img/pic-2.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class=" text-strong text-gery  font-w ">退货冲减</p>
                <p class="text-jv text-16">${retreatAmount }台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="/static/earnings/img/pic3.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong">实际销量</p>
                <p class="text-gren text-16">${totalNumber-retreatAmount }台</p>
            </div>
        </div>

    </div>


    <br>


    <div class="clearfix"></div>
    <div>
        <select class="ph-select">
            <option>区域星级</option>
            <option>一星</option>
            <option>二星</option>
            <option>三星</option>
            <option>四星</option>
        </select>

        <select class="ph-select">
            <option>业务等级</option>
            <option>小学生</option>
            <option>中学生</option>
            <option>高中生</option>
            <option>大学生</option>
        </select>

        <select class="ph-select">
            <option>所有组</option>
            <option value="A">A组</option>
            <option value="B">B组</option>
            <option value="C">C组</option>
            <option value="D">D组</option>
        </select>

        <button class="btn btn-blue btn-sm" style="margin-left: 10px">
            检索
        </button>

        <div class="link-posit-t pull-right export">
            <input class="ph-select text-gery-hs" placeholder="请输入业务员名称" id="searchSalesMan">
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
                    <th>序号</th>
                    <th>姓名</th>
                    <th>负责区域</th>
                    <th>业务等级</th>
                    <th>达量指标</th>
                    <th>分组</th>
                    <th>查看进程</th>
                    <th>任务起止日期</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="achieveCourseList"></tbody>
            </table>
        </div>
        <!--table-box-->
        <div id="initPager"></div>
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
<script type="text/javascript" src="static/achieve/achieve_course.js"></script>
</body>
</html>
