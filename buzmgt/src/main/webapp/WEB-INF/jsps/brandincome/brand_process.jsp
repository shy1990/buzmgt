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
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查看进程</title>
    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/brandincome/css/process.css"/>
    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <style>

    </style>
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
                <span class="text-lan text-strong">手机小米小米手机5  平板苹果苹果平板2</span>
            </div>
            <div class="col-sm-4 pro-2">
                <span class="text-strong text-gery">达量产品：</span>
                <span class="text-lv text-strong"> 150台/300台/500台</span>
            </div>
            <div class="col-sm-4 pro-2">
                <span class="text-strong text-gery">起止日期：</span>
                <span class="text-black text-strong"> 2016.09.02</span>
            </div>
        </div>

        <hr class="hr-l">

        <div class="row">
            <div class="col-sm-4 info-zq">
                <img src="img/pic1.png" alt="" class="fl" style=" margin-right: 15px;">
                <p class="text-gery text-strong font-w" >周期销量</p>
                <p class="text-lv text-16 text-strong">1000台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="img/pic-2.png" alt="" class="fl"  style=" margin-right: 15px;">
                <p class=" text-strong text-gery  font-w " >退货冲减</p>
                <p class="text-jv text-16">50台</p>
            </div>
            <div class="col-sm-4 info-zq">
                <img src="img/pic3.png" alt="" class="fl"  style=" margin-right: 15px;">
                <p class="text-gery   font-w text-strong" >实际销量</p>
                <p class="text-gren text-16">950台</p>
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
            <option>全部角色</option>
            <option>山东省-德州市-武城县</option>
            <option>山东省-德州市-武城县</option>
        </select>

        <select class="ph-select">
            <option>业务等级</option>
            <option>山东省-德州市-武城县</option>
            <option>山东省-德州市-武城县</option>
        </select>

        <select class="ph-select">
            <option>区域星级</option>
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
                    <td > <span class="text-red">200台 /  300台 / 400台</span></td>
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
                    <td > <span class="text-red">200台 /  300台 / 400台</span></td>
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
                    <td > <span class="text-red">200台 /  300台 / 400台</span></td>
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
                    <td > <span class="text-red">200台 /  300台 / 400台</span></td>
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
                    <td > <span class="text-red">200台 /  300台 / 400台</span></td>
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

<![endif]-->
<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>


</body>
</html>


