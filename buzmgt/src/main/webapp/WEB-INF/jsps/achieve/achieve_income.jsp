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
    <link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="static/achieve/achieve_income.css">
    <link rel="stylesheet" type="text/css" href="static/bootStrapPager/css/page.css"/>
    <script src="static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <script id="achieveincome-table-template" type="text/x-handlebars-template">
        {{#if content}}
        {{#each content}}
        <tr>
            <td>{{addOne @index}}</td>
            <td>{{goodName}}</td>
            <td>{{formDate startDate}}-{{formDate endDate}}</td>
            <td>
                {{#compareDate startDate endDate}}
                <span class="ph-span ph-kuang-lv">进行中</span>
                {{else}}
                <span class="ph-span ph-kuang-red">已过期</span>
                {{/compareDate}}
            </td>
            <td>{{formDate issuingDate}}</td>
            <td> {{num}}台</td>
            <td><span class="ph-text-red"> {{shNum}}台</span></td>
            <td> <span class="text-blue">{{subtract num shNum}}台</span> </td>
            <td> <span class="text-green">{{money}}元</span> </td>
            <%--<td> <span class="text-gery">未核算</span><span class="text-blue">已核算</span></td>--%>
            <td>
                <button class="xiugai  btn btn-blue btn-bluee" onclick="CheckDetails({{achieveId}})">查看明细</button>
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
        <i class="ico icon-daliang"></i>达量收益
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
        <input id="userId" value="${userId}" type="hidden"/>
    </h4>
    <div class="clearfix"></div>
    <div class="form-inline" style="margin-bottom: 10px;">
        <div class="form-group">
            <div class="input-group">
                <div class="input-group-addon">活动</div>
                <input class="form-control J_activityDate input-sm" readonly="readonly" placeholder="活动日期" style="background: #ffffff;">
            </div>
        </div>
        <div class="form-group" >
            <div class="input-group">
                <div class="input-group-addon">发放</div>
                <input class="form-control J_issuingDate input-sm" readonly="readonly" placeholder="佣金发放日期" style="background: #ffffff;">
            </div>
        </div>

        <button class="btn btn-blue btn-sm" style="margin-left: 10px" onclick="goSearch()">
            检索
        </button>

        <div class="link-posit-t pull-right export">
            <input class="ph-select text-gery-hs" placeholder="  请输入业务员名称">
            <button class="btn btn-blue btn-sm" onclick="goSearch('${salesman.id}','${assess.id}');">
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
                    <th>标题</th>
                    <th>有效周期</th>
                    <th>状态</th>
                    <th>收益发放日期</th>
                    <th>周期内销量</th>
                    <th>退货冲减</th>
                    <th>实际销量</th>
                    <th>提成</th>
                    <%--<th>财务核算</th>--%>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="acheiveIncomeList"></tbody>
            </table>
        </div>
        <!--table-box-->
        <div id="initPager"></div>
        <!--待审核账单-->
    </div>

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
<script type="text/javascript"
        src="static/achieve/achieve_income.js" charset="utf-8"></script>
<script type="text/javascript">

</script>
</body>

</html>