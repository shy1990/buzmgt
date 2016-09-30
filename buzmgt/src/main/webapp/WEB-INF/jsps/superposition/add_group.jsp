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
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>创建</title>

    <link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/income/order_detail.css"/>
    <link rel="stylesheet" href="<%=basePath%>static/phone-set/css/phone.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/comminssion.css">
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="../static/superposition/openUser.js" type="text/javascript"
            charset="utf-8"></script>
    <style>

        .name-list {
            margin-top: 20px;
            margin-left: 20px;
            color: #858585;
            min-height: 600px;
            border-top: 1px solid #eeeeee;
            border-bottom:1px solid #eeeeee ;
        }

        .name-list > li:hover {
            background: #e3f9ff;
        }

        li {
            padding: 15px;
            border-bottom: none;
        }

        .ph-search-date > input {
            display: inline-block;
            width: auto;
            height: 30px;
        }

        .dl-horizontal dt {
            float: left;
            width: 160px;
            overflow: hidden;
            clear: left;
            text-align: right;
            text-overflow: ellipsis;
            white-space: nowrap;

        }

        .icon-jiahui {
            background: url("<%=basePath%>static/img/jiahui.png") no-repeat center;
        }

        .icon-cj {
            background: url("<%=basePath%>static/img/tj.png") no-repeat center;
        }

        .text-zcy {
            margin-top: -30px;
            margin-left: 25px;

        }

        .text-green {
            color: #24cd8e;
            font-size: 16px;
        }

        .text-green-s {
            color: #24cd8e;
            font-size: 12px;
            font-weight: bold;
        }

        .notice {
            display: inline-block;
            position: relative;
            top: -10px;
            background: url("<%=basePath%>static/earnings/img/sc-b.png") no-repeat center;
            width: 95px;
            height: 33px;
            padding-top: 10px;
            text-align: center;
        }

        .notice:hover {
            background: url("<%=basePath%>static/earnings/img/sc-bg.png") no-repeat center;
            padding-top: 10px;
            color: #FFFFFF;
            cursor: pointer;
        }

        .text-b {
            color: #209ee5;
            font-size: 16px;
        }

        .text-b-s {
            color: #209ee5;
            font-size: 12px;
            font-weight: bold;
        }

        .text-c {
            color: #feb87f;
            font-size: 16px;
        }

        .text-c-s {
            color: #feb87f;
            font-size: 12px;
            font-weight: bold;
        }

        .text-d {
            color: #b2d66c;
            font-size: 16px;
        }

        .text-d-s {
            color: #b2d66c;
            font-size: 12px;
            font-weight: bold;
        }

        .icon-bg {
            display: inline-block;
            position: relative;
            z-index: 100;
            width: 35px;
            height: 35px;
            margin-right: 5px;
            margin-top: -8px;
        }

        .tj-body {
            width: 850px;
            border-right: 1px solid #eeeeee;
        }

        .icon-nam {
            display: inline-block;
            position: relative;
            width: 18px;
            height: 12px;
            margin-right: 20px;
            background: url("<%=basePath%>static/img/name.png") no-repeat center;
        }

        .icon-delee {
            display: inline-block;
            position: relative;
            width: 18px;
            height: 12px;
            margin-left: 40px;
            background: url("<%=basePath%>static/img/del.png") no-repeat center;
        }


    </style>

</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico icon-cj"></i>创建
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>


    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->
            <div class="order-box">
                <ul>
                    <li>
                        <dl class="dl-horizontal ">
                            <dt><span class="text-strong text-gery" style="font-size: 14px">人员分组：</span></dt>
                        </dl>

                    </li>
                    <dl class="dl-horizontal">
                        <dt>添加使用人员：</dt>

                        <dd style="width: 750px; margin-bottom: 20px">

                            <div class="col-sm-2">
                                <a
                                        class="J_addDire btn btn-default ph-btn-bluee icon-tj col-sm-6"
                                        onclick="openUser();"></a>
                            </div>

                        </dd>
                    </dl>
                    <%--<li>--%>
                        <%--<dl class="dl-horizontal">--%>
                            <%--<dt><span class="text-green">A组：</span></dt>--%>
                            <%--<dd>--%>
                                <%--<span class="notice"> 李易峰</span>--%>
                                <%--<span class="notice"> 李易峰</span>--%>
                                <%--<span class="notice"> 李易峰</span>--%>
                                <%--<span class="notice"> 李易峰</span>--%>
                                <%--<span class="notice"> 李易峰</span>--%>
                                <%--<a href="#" data-target="#tjry" data-toggle="modal"> <i class="icon-bg icon-jiahui"></i></a>--%>
                            <%--</dd>--%>
                        <%--</dl>--%>
                    <%--</li>--%>

                    <%--<li>--%>
                        <%--<dl class="dl-horizontal">--%>
                            <%--<dt><span class="text-b">B组：</span></dt>--%>
                            <%--<dd>--%>
                                <%--<span class="notice"> 李易峰</span>--%>
                                <%--<span class="notice"> 李易峰</span>--%>
                                <%--<span class="notice"> 李易峰</span>--%>
                                <%--<span class="notice"> 李易峰</span>--%>
                            <%--</dd>--%>
                        <%--</dl>--%>
                    <%--</li>--%>

                    <%--<li>--%>
                        <%--<dl class="dl-horizontal">--%>
                            <%--<dl class="dl-horizontal">--%>
                                <%--<dt><span class="text-c">C组：</span></dt>--%>
                                <%--<dd>--%>
                                    <%--<span class="notice"> 李易峰</span>--%>
                                    <%--<span class="notice"> 李易峰</span>--%>
                                    <%--<span class="notice"> 李易峰</span>--%>
                                    <%--<span class="notice"> 李易峰</span>--%>
                                    <%--<span class="notice"> 李易峰</span>--%>
                                    <%--<span class="notice"> 李易峰</span>--%>
                                <%--</dd>--%>
                            <%--</dl>--%>
                        <%--</dl>--%>
                    <%--</li>--%>

                    <%--<li>--%>
                        <%--<dl class="dl-horizontal">--%>
                            <%--<dl class="dl-horizontal">--%>
                                <%--<dt><span class="text-d">D组：</span></dt>--%>
                                <%--<dd>--%>
                                    <%--<a href="#" data-target="#tjry" data-toggle="modal"> <i class="icon-bg icon-jiahui"></i></a>--%>
                                <%--</dd>--%>
                            <%--</dl>--%>
                        <%--</dl>--%>
                    <%--</li>--%>
                </ul>

                <%--<button class="btn btn-primary col-sm-1" style="margin-left: 180px">下一步</button>--%>
            </div>
            <div><button class="btn btn-primary col-sm-1" style="margin-left: 180px">下一步</button></div>

            <!--orderobx end-->
        </div>
        <!--col end-->
    </div>

    <!--添加人员-->
    <div id="tjry" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document" style="width: 1080px; ">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">添加人员</h3>
                </div>

                <!DOCTYPE html>
                <html>

                <body>
                <div class="content tj-body">
                    <div class="clearfix"></div>
                    <div class="group-search">
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

                        <input type="text" class="col-sm-12 big-seach" placeholder="请搜索业务人员姓名">
                    </div>

                    <div class="tab-content ">
                        <!--table-box-->

                        <div class=" new-table-box table-overflow ">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>姓名</th>
                                    <th>角色</th>
                                    <th>负责区域</th>
                                    <th>业务等级</th>
                                    <th>所属分组</th>
                                    <th>操作</th>
                                </tr>
                                <tr>

                                </thead>
                                <tbody>
                                <tr>
                                    <td>01</td>
                                    <td>李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x ico-xx"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-zi text-strong">大学生</span></td>
                                    <td>
                                        <span class="text-green-s"> A组</span>
                                    </td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td>02</td>
                                    <td>李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x icon-hui"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-hong text-strong">中学生</span></td>
                                    <td><span class="text-b-s">B组</span></td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td>03</td>
                                    <td>李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x ico-xx"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-lan text-strong">小学生</span></td>
                                    <td><span class="text-c-s">C组</span></td>
                                    <td></td>
                                </tr>

                                <tr>
                                    <td>04</td>
                                    <td>李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x ico-xx"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-lan text-strong">小学生</span></td>
                                    <td><span class="text-d-s">D组</span></td>
                                    <td></td>
                                </tr>


                                <tr>
                                    <td>05</td>
                                    <td>李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x ico-xx"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-hong text-strong">中学生</span></td>
                                    <td><span class="text-gery text-strong">无</span></td>
                                    <td>
                                        <button class="btn bnt-sm bnt-ck J_addDire">添加</button>
                                    </td>
                                </tr>

                                <tr>
                                    <td>06</td>
                                    <td>李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x ico-xx"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-hong text-strong">中学生</span></td>
                                    <td><span class="text-gery text-strong">无</span></td>
                                    <td>
                                        <button class="btn bnt-sm bnt-ck">添加</button>
                                    </td>
                                </tr>


                                <tr>
                                    <td>07</td>
                                    <td>李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x ico-xx"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-hong text-strong">中学生</span></td>
                                    <td><span class="text-gery text-strong">无</span></td>
                                    <td>
                                        <button class="btn bnt-sm bnt-ck">添加</button>
                                    </td>
                                </tr>

                                <tr>
                                    <td>08</td>
                                    <td>李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x ico-xx"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-hong text-strong">中学生</span></td>
                                    <td><span class="text-gery text-strong">无</span></td>
                                    <td>
                                        <button class="btn bnt-sm bnt-ck">添加</button>
                                    </td>
                                </tr>

                                <tr>
                                    <td>09</td>
                                    <td>李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x ico-xx"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-hong text-strong">中学生</span></td>
                                    <td><span class="text-gery text-strong">无</span></td>
                                    <td>
                                        <button class="btn bnt-sm bnt-ck">添加</button>
                                    </td>
                                </tr>

                                <tr>
                                    <td>10</td>
                                    <td class="">李易峰</td>
                                    <td>服务站经理</td>
                                    <td>
                                        <i class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i><i
                                            class=" icon-x ico-xx"></i>
                                        山东省济南市天桥区
                                    </td>
                                    <td><span class="text-hong text-strong">中学生</span></td>
                                    <td><span class="text-gery text-strong">无</span></td>
                                    <td>
                                        <button class="btn bnt-sm bnt-ck">添加</button>
                                    </td>
                                </tr>


                                </tbody>
                            </table>


                            <!--<button class="btn btn-blue col-sm-3" style="margin-left: 40%">确定</button>-->
                        </div>
                        <!--table-box-->

                        <!--待审核账单-->
                    </div>

                    <!--油补记录-->
                </div>

                <!--右侧添加-->     
                <div class="fl-right" style="width: 200px;margin-top: -700px">
                    <div class="group-search">

                        <div class="text-gery text-strong text-zcy">
                            组成员：
                        </div>
                        <ul class="name-list" id="nameli">
                            <li><i class=" icon-nam"></i>李易峰<i class=" icon-delee"></i></li>
                            <li><i class=" icon-nam"></i>李易峰<i class=" icon-delee"></i></li>
                            <li><i class=" icon-nam"></i>李易峰<i class=" icon-delee"></i></li>
                            <li><i class=" icon-nam"></i>李易峰<i class=" icon-delee"></i></li>

                        </ul>

                        <div class="page-down">
                            <button class="btn btn-red col-sm-5" style="margin: 5px 10px;">取消</button>
                            <button class="btn btn-blue col-sm-5" style="margin: 5px;">确定</button>
                        </div>
                    </div>
                </div>

                <script>


                </script>
                </body>
z
                </html>


            </div>
        </div>

    </div>
</div>
<%--列表显示人员页面--%>
<div id="user" class="modal fade" role="dialog">
    <jsp:include flush="true" page="userSelect.jsp"></jsp:include>
</div>
<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>
<script src="../static/bootstrap/js/bootstrap.js"></script>
<script>

</script>
</body>
</html>