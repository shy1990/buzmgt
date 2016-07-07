<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>" />
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>月指标</title>

    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="static/bootstrap/css/bootstrap-datetimepicker.min.css" />
    <link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/month-target/css/mouth.css">
    <link href="static/bootStrapPager/css/page.css" rel="stylesheet">
    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        var base = "<%=basePath%>";
        var number = '';//当前页数（从零开始）
        var totalPages = '';//总页数(个数)
        var searchData = {
            "size" : "20",
            "page" : "0",
        }
        var totalElements;//总条数
    </script>
    <script id="table-template" type="text/x-handlebars-template">
        {{#each content}}
        <tr>
            <td>{{region.parent.parent.parent.parent.name}} {{region.parent.parent.parent.name}} {{region.parent.parent.name}} {{region.name}}</td>
            <td>{{salesman.truename}}</td>
            <td>{{matureAll}}</td>
            <td>-- / {{orderNum}}</td>
            <td>-- / {{merchantNum}}</td>
            <td>-- / {{activeNum}}</td>
            <td>-- / {{matureNum}}</td>
            <td>{{targetCycle}}</td>
            <td>
                <button class="btn btn-blue btn-bn-style" onclick="update('{{id}}','update');">查看</button>
                {{#if view}}
                <button class="btn btn-green btn-bn-style" onclick="publish('{{id}}');" id="{{id}}">发布</button>
                {{else}}
                <button class="btn btn-green btn-bn-style" disabled="disabled">已发布</button>
                {{/if}}
            </td>
        </tr>
        {{else}}
        <div style="text-align: center;">
            <tr style="text-align: center;">没有相关数据!</tr>
        </div>
        {{/each}}
    </script>
</head>
<body>
<div class="content main">
    <h4 class="page-header">
        <i class="ico icon-set"></i>月指标设置
        <div class="area-choose">

        </div>
        <!--/区域选择按钮-->
        <a href="javascript:;" onclick="toUpdate();" class="btn btn-blue"
           data-toggle="modal" data-target="#xzyw" data-whatever="@mdo">
            <i class="ico icon-add"></i>添加
        </a>
    </h4>
    
    <!---选择地区-->
    <div>
        <span class="text-gray">选择月份：</span>
        <div class="search-date" style="width: 187px">
            <div class="input-group input-group-sm">
                <span class="input-group-addon " id="basic-addon1"><i
                        class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                <input type="text" class="form-control form_datetime input-sm" placeholder="选择日期" readonly="readonly">
            </div>
        </div>
    </div>
    <!---选择地区-->

    <!---设置次数-->
    <div style="margin-top: -29px">
        <div class="pull-right export">
            <input class="cs-select  text-gery-hs" placeholder="请输入业务员名称">
            <button class="btn btn-blue btn-sm" onclick="goSearch();">
                检索
            </button>
            <a href="javascript:;" onclick="publishAll();"><span class="text-blue-s" style="margin-left: 30px">一键全部发布</span></a>
        </div>

    </div>


    <!---设置次数-->

    <div class="clearfix"></div>
    <div class="tab-content">
        <!--油补记录-->
        <div class="tab-pane fade in active" id="box_tab1">
            <!--table-box-->
            <div class="table-task-list new-table-box table-overflow">
                <table class="table table-hover new-table">
                    <thead>
                    <tr>
                        <th>区域</th>
                        <th>负责人</th>
                        <th>注册商家</th>
                        <th>提货量  <span class="th-td">（实际/指标）</span></th>
                        <th>提货商家 <span class="th-td">（实际/指标）</span></th>
                        <th>活跃商家  <span class="th-td">（实际/指标）</span></th>
                        <th>成熟商家  <span class="th-td">（实际/指标）</span></th>
                        <th>指标周期</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="tableList">

                    </tbody>
                </table>
                <div id="callBackPager"></div>
            </div>
            <!--table-box-->
        </div>
        <!--油补记录-->
    </div>

</div>

<![endif]-->
<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
<script src="<%=basePath%>static/month-target/js/monthSetting.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript">
    $('body input').val('');

    $(".form_datetime").datetimepicker({
        format: "yyyy-mm",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 3,
        minView: 3,
        pickerPosition: "bottom-right",
        forceParse: 0
    });

</script>

</body>
</html>
