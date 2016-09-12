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
    <base href="<%=basePath%>" />
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查看</title>

    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/order-detail/order_detail.css"/>
    <link rel="stylesheet" href="static/earnings/css/phone.css">

    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <style>


        li {
            padding-top: 28px;
            border-bottom: 0px dashed #dedede;
        }

        .btn-dd{
            float: left;
            margin-left: 10px;
            margin-top: 20px;
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
            margin-top: 8px;
        }

        .text-pronce{
            line-height: 30px;
        }



        .icon-riz{
            background: url("../static/img/rizi.png") no-repeat center;
        }
        .ph-icon{
            height: 30px;
            padding: 5px 10px;
            font-size: 12px;
            line-height: 1.5;
            border-radius: 3px;
        }

        .btn-qx{
            float: left;
            margin-top: 20px;
            margin-left: 180px;
        }

        .icon-ck{
            background: url("../static/img/ck.png") no-repeat center;
        }

        .text-green{
            color: #00ac72;
        }

        .text-bg{
            padding: 5px;
            background: #f2f2f2;
            margin-right: 5px;
        }
    </style>

</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico icon-ck"></i>查看
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>


    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->
            <div class="order-box">
                <ul>
                    <li>
                        <dl class="dl-horizontal">
                            <dt>选择品牌型号：</dt>
                            <dd>
                                <span class="text-pronce">${brandIncome.brand.name}   &nbsp; ${brandIncome.good.name}</span>

                            </dd>
                        </dl>


                    </li>


                    <li>
                        <dl class="dl-horizontal">
                            <dt>设置提成金额：</dt>
                            <dd>
                                <span class="text-green">${brandIncome.commissions} </span><span class="text-pronce" style="margin-left: 10px"></span> 元/台
                            </dd>
                        </dl>


                    </li>

                    <li>
                        <dl class="dl-horizontal">
                            <dt>区域属性设置：</dt>
                            <dd>
                            <c:if test="${!empty areaAttributes}">
                                <c:forEach var="areaAttr" items="${areaAttributes }" varStatus="status">
                                    <span class="text-pronce text-bg">${areaAttr.region.namepath}  <span class="text-red text-bg">${areaAttr.commissions}</span>元/台</span>
                                </c:forEach>
                            </c:if>
                            </dd>
                        </dl>
                    </li>

                    <li>
                        <dl class="dl-horizontal">
                            <dt>方案起止日期：</dt>
                            <dd>
                                <span class="text-pronce text-blue ">  ${brandIncome.startDate} - ${brandIncome.endDate}</span>
                            </dd>

                        </dl>
                    </li>
                    <li>
                        <dl class="dl-horizontal">
                            <dt>指派审核人员：</dt>
                            <dd>
                                <span class="text-pronce  "> ${brandIncome.user.nickname}</span>
                            </dd>

                        </dl>
                    </li>
                </ul>

                <button class="btn btn-primary col-sm-1" style="margin-left: 180px" onclick="history.back();">返回</button>
            </div>
            <!--orderobx end-->
        </div>
        <!--col end-->
    </div>
    <!--删除-->
    <div id="del" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">删除提示</h3>
                </div>

                <div class="modal-body">
                    <div class="container-fluid">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <p class="col-sm-12 text-red ">当前还有使用人员，你确定要删除方案吗删除后提成讲不再按此方案计算...</p>
                            </div>
                            <hr>

                            <span class="text-gery text-strong">移除日期：</span>
                            <div style="margin-left: 80px;margin-top: -25px">
                                <div class="input-group are-line">
                                    <span class="input-group-addon "><i class=" ph-icon icon-riz"></i></span>
                                    <input type="text" class="form-control form_datetime  " placeholder="年-月-日" readonly="readonly  " style="background: #ffffff;width: 265px;">
                                </div>

                            </div>
                            <div class="btn-qx">
                                <button type="submit" class="btn btn-danger btn-d">确定</button>
                            </div>

                            <div class="btn-dd">
                                <button type="submit" data-dismiss="modal" class="btn btn-primary btn-d">取消</button>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<%=basePath%>static/bootstrap/js/bootstrap.js"></script>
</body>
</html>


