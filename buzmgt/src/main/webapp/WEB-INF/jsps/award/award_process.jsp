<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查看进程</title>
    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/brandincome/css/process.css"/>
    <link rel="stylesheet" type="text/css" href="static/bootStrapPager/css/page.css" />
    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <%--品牌型号进程开始--%>
    <script id="process-table-template" type="text/x-handlebars-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{addOne @index}}</td>
            <td>{{truename}}</td>
            <td>
                <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i>
                {{namepath}}
            </td>
            <td><span class="text-zi text-strong">{{levelName}}</span></td>
            <td > <span class="text-red">{{numberFirst}}台/{{numberSecond}}台/{{numberThird}}台</span></td>
            <td><span class="text-lv text-strong">{{groupName}}</span></td>
            <td>{{nums}}台</td>
            <td>{{formDate startDate}}-{{formDate endDate}}</td>
            <td>{{compareDate startDate endDate}}</td>
            <td>
                <button class="btn btn-sm btn-blue" onclick="detail('{{regionId}}','{{goodsId}}');">查看明细</button>
            </td>
        </tr>
        {{/each}}
        {{else}}
        <tr>
            <td colspan="100">没有相关数据</td>
        </tr>
        {{/if}}
    </script>
    <%--品牌型号进程结束--%>
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
        <i class="ico icon-ck"></i>查看进程
        <!--区域选择按钮-->
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
        <input id="awardId" hidden="hidden" value="${award.awardId}">
    </h4>
    <div class=" inform">
        <div class="row">
            <div class="col-sm-4 product">
                <span class="text-strong text-gery">达量奖励产品：</span>
                <c:if test="${!empty awardGoods}">
                    <c:forEach items="${awardGoods}" var="awardGood" varStatus="status">
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
                <span class="text-black text-strong"><fmt:formatDate value="${award.startDate}" pattern="yyyy-MM-dd"/> 至 <fmt:formatDate value="${award.endDate}" pattern="yyyy-MM-dd"/></span>
            </div>
        </div>

        <hr class="hr-l">

        <div class="row">
            <div class="col-sm-4 info-zq">
                <img src="<%=basePath%>static/img/pic1.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery text-strong font-w" >周期销量</p>
                <p class="text-lv text-16 text-strong">${cycleSales}台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="<%=basePath%>static/img/pic-2.png" alt="" class="fl"  style=" margin-right: 15px;">
                <p class=" text-strong text-gery  font-w " >退货冲减</p>
                <p class="text-jv text-16">${hedgeNums}台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="<%=basePath%>static/img/pic3.png" alt="" class="fl"  style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong" >实际销量</p>
                <p class="text-gren text-16">${cycleSales - hedgeNums}台</p>
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
        </select>

        <select class="ph-select">
            <option>全部角色</option>
            <option>山东省-德州市-武城县</option>
            <option>山东省-德州市-武城县</option>
        </select>--%>

        <select class="ph-select J_serviceLevel">
            <option>业务等级</option>
            <c:if test="${!empty salesmanLevels}">
                <c:forEach items="${salesmanLevels}" var="level" varStatus="status">
                    <option>${level.levelName}</option>
                </c:forEach>
            </c:if>
        </select>

        <select class="ph-select J_regionLevel">
            <option>区域星级</option>
            <option>一星</option>
            <option>二星</option>
        </select>

        <button class="btn btn-blue btn-sm" style="margin-left: 10px" onclick="goFilter();">
            筛选
        </button>

        <div class="link-posit-t pull-right export">
            <input class="ph-select text-gery-hs" placeholder="  请输入业务员名称">
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
                    <th>达量奖励指标</th>
                    <th>分组</th>
                    <th>当前完成量</th>
                    <th>任务起止日期</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="awardProcessList">

                </tbody>
            </table>
        </div>
        <div id="awardProcessPager"></div>
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
<script type="text/javascript" src="<%=basePath%>static/award/award_process.js" charset="utf-8"></script>

</body>
</html>


