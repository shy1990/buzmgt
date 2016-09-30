<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查看进程</title>

    <link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/phone.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/bootStrapPager/css/page.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/css/section/comminssion.css">
    <script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"
            charset="utf-8"></script>
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <style>


        .text-lv {
            color: #02ae88;
        }

        .text-zi {
            color: #ee038d;
        }

        .text-hong {
            color: #7943dc;
        }

        .text-lan {
            color: #1aaefb;
        }

        .text-cao {
            color: #b2d66c;
        }

        .icon-x {
            display: inline-block;
            position: relative;
            z-index: 100;
            width: 14px;
            height: 13px;
            margin-top: 3px;
        }

        .ico-xx {
            background: url("<%=basePath%>static/earnings/img/start1.png") no-repeat center;
        }

        .icon-back {
            width: 26px;
            height: 26px;
            background: url("<%=basePath%>static/earnings/img/arrow.png") no-repeat center;
        }

        .icon-back:hover {
            width: 26px;
            background: url("<%=basePath%>static/earnings/img/Arrow 2 .png") no-repeat center;
        }

        .fl-right {
            float: right;
            margin-top: 25px;
            margin-right: 25px;
        }

        .ph-select {

            width: 180px;
            height: 30px;
            padding: 5px;
            border: 1px solid #c6c6c6;
            border-radius: 3px;
            background: #ffffff;
            color: #a7a7a7;
            margin-bottom: 20px;
        }

        .link-posit-t {
            display: inline-block;
            text-align: right;
            vertical-align: bottom;
        }

        .new-table-box {
            border-top: 3px solid #1e92d4;
            min-height: 600px;
            background: #FFF;
        }

        .new-table-box > table > tbody > tr:hover {
            border-left: 3px solid #1e92d4;
        }

        .text-gery {
            color: #999999;
        }

        .ph-search-date > input {
            display: inline-block;
            width: auto;
            height: 30px;
        }

        .inform {
            height: 280px;
            background: #ffffff;
            padding-top: 30px;
        }

        .info-zq {
            height: 70px;
            width: 190px;
            margin-left: 40px;

            background: #fafafa;
            padding-left: 0px;
            box-shadow: 5px 5px 5px #ededed;
        }

        .fl {
            float: left;
        }

        .text-16 {
            font-size: 16px;
        }

        .font-w {
            line-height: 30px;
        }

        .text-jv {
            color: #d95413;
            font-weight: bolder;
        }

        .text-gren {
            color: #accea6;
            font-weight: bolder;
        }

        .icon-ck {
            background: url("<%=basePath%>static/earnings/img/mingx.png") no-repeat center;
        }

        .text-red {
            color: #ee3636;
        }

        .text-huang {
            color: #fd9c3e;
        }

        .product {
            width: 500px;

        }

        .pro-2 {
            width: 300px;
            margin-left: -100px;
        }

        .pro-2-2 {
            width: 500px;
            margin-left: -100px;
        }

        .text-71 {
            color: #717171;

        }
    </style>
    <script id="list-template" type="text/handlebars-template">
        {{#each this}}
        <tr>
            <td>01</td>
            <td>{{trueName}}</td>
            <td>
                <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i>
                {{namePath}}
            </td>
            <td><span class="text-zi text-strong">大学生</span></td>
            <td><span class="text-red">
                {{taskOne}}台 /  {{taskTwo}}台 / {{taskThree}}台
            </span></td>
            <td><span class="text-lv text-strong">
                <%--{{#if name }}--%>
                    <%--没有分组--%>
                <%--{{/if}}--%>
               {{name}}
            </span></td>
            <td>{{nums}}</td>
            <td>{{implDate}} - {{endDate}}</td>
            <td><span class="text-lv text-strong">进行中</span></td>
            <td>
                <button class="btn btn-sm btn-blue" onclick="goDetail('{{userId}}')">查看明细</button>
            </td>
        </tr>
        {{/each}}

    </script>
</head>
<body>
<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-ck"></i>明细
        <!--区域选择按钮-->
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>

    </h4>
    <div class=" inform">


        <div class="row">
            <div class="col-sm-4 info-zq">
                <img src="<%=basePath%>static/earnings/img/pic1.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery text-strong font-w">周期销量</p>
                <p class="text-lv text-16 text-strong">1000台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="<%=basePath%>static/earnings/img/pic-2.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class=" text-strong text-gery  font-w ">退货冲减</p>
                <p class="text-jv text-16">50台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="<%=basePath%>static/earnings/img/pic3.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong">实际销量</p>
                <p class="text-gren text-16">950台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="<%=basePath%>static/earnings/img/redq.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong">预计收益</p>
                <p class="text-red text-16 text-strong">1230.05元</p>
            </div>
        </div>

        <hr class="hr-l">

        <div class="row" style="padding-left: 20px">
            <div class="col-sm-4 product">
                <span class="text-strong text-gery">达量产品：</span>
                <span class="text-lan text-strong">手机小米小米手机5  平板苹果苹果平板2</span>
            </div>
            <div class="col-sm-4 pro-2">
                <span class="text-strong text-gery">达量产品：</span>
                <span class="text-lv text-strong"> 150台</span>
            </div>
            <div class="col-sm-4 pro-2">
                <span class="text-strong text-gery">起止日期：</span>
                <span class="text-black text-strong"> 2016.09.02 - 2016.09.02</span>
            </div>
        </div>

        <div class="row" style="padding: 20px;">
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

        </div>

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
        <select class="ph-select">
            <option>山东省-德州市-武城县</option>
            <option>山东省-德州市-武城县</option>
            <option>山东省-德州市-武城县</option>
        </select>

        <select class="ph-select">
            <option>销量订单</option>
            <option>山东省-德州市-武城县</option>
            <option>山东省-德州市-武城县</option>
        </select>


        <button class="btn btn-blue btn-sm" style="margin-left: 10px">
            检索
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
                    <th>达量指标</th>
                    <th>分组</th>
                    <th>提货量</th>
                    <th>任务起止日期</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="list_progress">


                </tbody>

            </table>

        </div>
        <%--分页--%>
        <div id="callBackPager"></div>
    </div>

</div>

<![endif]-->
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
<script>
    var total = 0;
    var totalCount = 0;
    var limit = 0;
    var searchDate = {
        'page':'0',
        'size':'20'
    };
    searchDate.planId = '${planId}';
    function list(searchDate){
        $.ajax({
            url:'progress/${id}',
            type:'POST',
            dataType:'json',
            data:searchDate,
            success:function(data){
                console.log(data);
                var listArray = data.content;
                limit = data.size;
                totalCount = data.totalElements;
                handlebarsRegist(listArray);
                if (totalCount != total || totalCount == 0) {
                    total = totalCount;
                    initPaging();
                }
            },
            error:function(){
                alert('系统故障机,请稍后重试');
            }
        })
    }
    function handlebarsRegist(listArray) {
        var listTemplate = Handlebars.compile($("#list-template").html());
        $("#list_progress").html(listTemplate(listArray));
    }
    //分页
    function initPaging() {
        $('#callBackPager').extendPagination({
            totalCount: totalCount,//总条数
            showCount: 5,//下面小图标显示的个数
            limit: limit,//每页显示的条数
            callback: function (curr, limit, totalCount) {
                searchDate['page'] = curr - 1;
                searchDate['size'] = limit;
                list(searchDate);
            }
        });
    }

    list(searchDate);

    function goDetail(userId){
        window.location.href = 'detail?planId=${planId}&id=${id}&userId='+userId;
    }

</script>


</body>
</html>


