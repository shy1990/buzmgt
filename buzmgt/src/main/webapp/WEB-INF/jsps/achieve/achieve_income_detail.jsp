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
        <%--var firstNum =${achieve.numberFirst}, secondNum =${achieve.numberSecond}, thirdNum =${achieve.numberThird==null};--%>
        var achieveJson=${achieveJson};//达量设置规则（全局变量）
    </script>
</head>
<body>
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
                <img src="img/pic1.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery text-strong font-w">周期销量</p>
                <p class="text-lv text-16 text-strong">1000台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="img/pic-2.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class=" text-strong text-gery  font-w ">退货冲减</p>
                <p class="text-jv text-16">50台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="img/pic3.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong">实际销量</p>
                <p class="text-gren text-16">950台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="img/redq.png" alt="" class="fl" style=" margin-right: 15px;">
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

        <div class="row" style="padding: 0px 20px  ">
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
                <tbody>
                <tr>
                    <td>01</td>
                    <td>李易峰</td>
                    <td>
                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i>
                        山东省济南市天桥区
                    </td>
                    <td><span class="text-zi text-strong">大学生</span></td>
                    <td><span class="text-red">200台 /  300台 / 400台</span></td>
                    <td><span class="text-lv text-strong">A组</span></td>
                    <td>350台</td>
                    <td>2016.08.09 - 2016.08.11</td>
                    <td><span class="text-lv text-strong">进行中</span></td>
                    <td>
                        <button class="btn btn-sm btn-blue">查看明细</button>
                    </td>
                </tr>

                <tr>
                    <td>01</td>
                    <td>李易峰</td>
                    <td>
                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i>
                        山东省济南市天桥区
                    </td>
                    <td><span class="text-zi text-strong">大学生</span></td>
                    <td><span class="text-red">200台 /  300台 / 400台</span></td>
                    <td><span class="text-lv text-strong">A组</span></td>
                    <td>350台</td>
                    <td>2016.08.09 - 2016.08.11</td>
                    <td><span class="text-lv text-strong">进行中</span></td>
                    <td>
                        <button class="btn btn-sm btn-blue">查看明细</button>
                    </td>
                </tr>


                <tr>
                    <td>01</td>
                    <td>李易峰</td>
                    <td>
                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i>
                        山东省济南市天桥区
                    </td>
                    <td><span class="text-hong text-strong">中学生</span></td>
                    <td><span class="text-red">200台 /  300台 / 400台</span></td>
                    <td><span class="text-lan text-strong">B组</span></td>
                    <td>350台</td>
                    <td>2016.08.09 - 2016.08.11</td>
                    <td><span class="text-hong text-strong">待审核</span></td>
                    <td>
                        <button class="btn btn-sm btn-blue">查看明细</button>
                    </td>
                </tr>

                <tr>
                    <td>01</td>
                    <td>李易峰</td>
                    <td>
                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i>
                        山东省济南市天桥区
                    </td>
                    <td><span class="text-lan text-strong">小学生</span></td>
                    <td><span class="text-red">200台 /  300台 / 400台</span></td>
                    <td><span class="text-huang text-strong">C组</span></td>
                    <td>350台</td>
                    <td>2016.08.09 - 2016.08.11</td>
                    <td><span class="text-lan text-strong">已核对</span></td>
                    <td>
                        <button class="btn btn-sm btn-blue">查看明细</button>
                    </td>
                </tr>

                <tr>
                    <td>01</td>
                    <td>李易峰</td>
                    <td>
                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i>
                        山东省济南市天桥区
                    </td>
                    <td><span class="text-lan text-strong">小学生</span></td>
                    <td><span class="text-red">200台 /  300台 / 400台</span></td>
                    <td><span class="text-cao text-strong">D组</span></td>
                    <td>350台</td>
                    <td>2016.08.09 - 2016.08.11</td>
                    <td><span class="text-lan text-strong">已核对</span></td>
                    <td>
                        <button class="btn btn-sm btn-blue">查看明细</button>
                    </td>
                </tr>


                </tbody>
            </table>
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
<script type="text/javascript" src="static/achieve/achieve_course.js"></script>
</body>
</html>
