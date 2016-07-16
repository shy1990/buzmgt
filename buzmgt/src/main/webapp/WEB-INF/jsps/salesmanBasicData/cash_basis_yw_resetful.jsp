﻿
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
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>业务员基础表</title>

    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/static/bootStrapPager/css/page.css"/>
    <link href="static/bootstrap/css/bootstrap-switch.min.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css"
          href="static/incomeCash/css/income-cash.css">
    <link rel="stylesheet" type="text/css"
          href="static/fenye/css/fenye.css">
    <script src="../static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <script src="../static/js/handlebars-v4.0.2.js"
            type="text/javascript" charset="utf-8"></script>

</head>
<body>
<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-yw"></i>业务员基础数据
        <!--区域选择按钮-->
        <div class="area-choose">

        </div>
        <!--/区域选择按钮-->
        <a href="" class="btn btn-blue"
           data-toggle="modal" data-target="#xzyw" data-whatever="@mdo">
            <i class="ico icon-add"></i>新增业务
        </a>
    </h4>
    <div class="row text-time">

        <div class="salesman">
            <input id="search" class="cs-select  text-gery-hs" placeholder="  请输入业务员名称">
            <button class="btn btn-blue btn-sm" onclick="goSearch();">
                检索
            </button>
        </div>

        <div class="link-posit-t pull-right export">
            <a class="table-export" href="salesmanData/export">导出excel</a>
        </div>

    </div>

    <div class="tab-content">

        <div class="table-task-list new-table-box table-overflow">
            <table class="table table-hover new-table tb-basin">
                <thead>
                <tr>
                    <th>业务ID</th>
                    <th>姓名</th>
                    <th>卡号</th>
                    <th>发卡行</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="tbody">


                </tbody>
            </table>


        </div>
        <%--分页--%>
        <div id="callBackPager"></div>


        <!--table-box-->
        <!--油补记录-->
    </div>
    <!---alert删除--->
    <div id="del" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">是否删除</h3>
                </div>

                <div class="modal-body">
                    <div class="container-fluid">
                        <form class="form-horizontal">
                            <input id="salesmanId" hidden/>
                            <input id="bankId" hidden/>
                            <div class="form-group">
                                <label class="col-sm-4 control-label text-dk">姓 &nbsp; &nbsp; 名：</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static text-bg" id="name1"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label text-dk">发卡银行：</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static text-bg" id="bankName1"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label text-dk">银行卡号：</label>
                                <div class="col-sm-8">
                                    <p class="form-control-static text-bg" id="cardNumber1"></p>
                                </div>
                            </div>

                            <%--<div class="btn-qx">--%>
                            <%--<a id="sure_delete" href="javascript:void(0);" class="btn btn-danger btn-d">删除</a>--%>
                            <%--</div>--%>
                            <div class="btn-qx">
                                <button type="submit" id="sure_delete" data-dismiss="modal"
                                        class="btn btn-danger btn-d">删除
                                </button>
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
    <!---alert删除--->

    <!---alert新增业务--->
    <div id="xzyw" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h3 class="modal-title">新增业务</h3>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <form id="addd" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">姓 &nbsp;名：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">

													<span class="input-group-addon"><i
                                                            class="icon icon-xm"></i></span> <input name="name"
                                                                                                    type="text"
                                                                                                    class="form-control input-h"
                                                                                                    aria-describedby="basic-addon1"> </input>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">业务ID：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
												<span class="input-group-addon"><i
                                                        class="icon icon-id"></i></span> <input name="userId"
                                                                                                type="text"
                                                                                                class="form-control input-h"
                                                                                                aria-describedby="basic-addon1"> </input>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">银行卡号：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
												<span class="input-group-addon"><i
                                                        class="icon icon-yh"></i></span> <input name="cardNumber"
                                                                                                type="text"
                                                                                                class="form-control input-h"
                                                                                                aria-describedby="basic-addon1"> </input>
                                    </div>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-4 control-label">发卡银行：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
												<span class="input-group-addon"><i
                                                        class="icon icon-fk"></i></span> <select name="bankName" type=""
                                                                                                 class="form-control input-h "
                                                                                                 aria-describedby="basic-addon1">
                                        <option></option>
                                        <option>中国银行</option>
                                        <option>农业银行</option>
                                        <option>工商银行</option>
                                        <option>建设银行</option>
                                        <option>邮政银行</option>
                                        <option>中信银行</option>
                                        <option>民生银行</option>
                                        <option>莱商银行</option>
                                        <option>亚细亚银行</option>
                                        <option>农村信用社</option>
                                        <option>交通银行</option>
                                        <option>其他</option>
                                    </select>
                                        <!-- /btn-group -->
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <%--<div class="col-sm-offset-4 col-sm-4 ">--%>
                                <%--<a herf="javascript:return 0;"--%>
                                <%--onclick="addSalesmanData(this)"--%>
                                <%--class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>--%>
                                <%--</div>--%>
                                <div class="col-sm-offset-4 col-sm-4">
                                    <button type="submit" onclick="addSalesmanData(this)" data-dismiss="modal"
                                            class="Zdy_add  col-sm-12 btn btn-primary">确定
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!---alert新增业务--->

    <!---alert新增银行卡--->
    <div id="xzyhk" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">新增银行卡业务</h3>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <form id="addCard" class="form-horizontal">
                            <input id="addCardId" name="id" hidden/>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">发卡银行：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon"><i class="icon icon-fk"></i></span>
                                        <select name="b" type="" class="form-control input-h"
                                                aria-describedby="basic-addon1">
                                            <option></option>
                                            <option>中国银行</option>
                                            <option>农业银行</option>
                                            <option>工商银行</option>
                                            <option>建设银行</option>
                                            <option>邮政银行</option>
                                            <option>中信银行</option>
                                            <option>民生银行</option>
                                            <option>莱商银行</option>
                                            <option>亚细亚银行</option>
                                            <option>农村信用社</option>
                                            <option>交通银行</option>
                                            <option>其他</option>
                                        </select>
                                        <!-- /btn-group -->
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">银行卡号：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon"><i class="icon icon-yh"></i></span>
                                        <input name="a" type="" class="form-control input-h"
                                               aria-describedby="basic-addon1">
                                        </input>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <%--<div class="col-sm-offset-4 col-sm-4 ">--%>
                                <%--<a herf="javascript:return 0;" onclick="sure_add_card(this)"--%>
                                <%--class="Zdy_add  col-sm-12 btn btn-primary">确定--%>
                                <%--</a>--%>
                                <%--</div>--%>
                                <div class="col-sm-offset-4 col-sm-4 ">
                                    <button type="submit" onclick="sure_add_card(this)" data-dismiss="modal"
                                            class="Zdy_add  col-sm-12 btn btn-primary">确定
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!---alert新增银行卡--->

    <!---alert修改业务信息--->
    <div id="xgywxx" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">修改信息</h3>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <form id="modify_form" class="form-horizontal">
                            <input name="id" id="modify_id" hidden/>
                            <input name="bankId" id="modify_bankId" hidden/>
                            <div class="form-group">
                                <label class="col-sm-4 control-label">姓 &nbsp;名：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon"><i class="icon icon-xm"></i></span>
                                        <input name="name" value="" class="form-control input-h"
                                               aria-describedby="basic-addon1" id="modify_name">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">银行卡号：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon"><i class="icon icon-yh"></i></span>
                                        <input name="a" type="" class="form-control input-h"
                                               aria-describedby="basic-addon1" id="modify_bankNumber">
                                        </input>
                                    </div>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-4 control-label">发卡银行：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon"><i class="icon icon-fk"></i></span>
                                        <select name="b" type="" class="form-control input-h"
                                                aria-describedby="basic-addon1">
                                            <option id="modify_bankName"></option>
                                            <option>中国银行</option>
                                            <option>农业银行</option>
                                            <option>工商银行</option>
                                            <option>建设银行</option>
                                            <option>邮政银行</option>
                                            <option>中信银行</option>
                                            <option>民生银行</option>
                                            <option>莱商银行</option>
                                            <option>亚细亚银行</option>
                                            <option>农村信用社</option>
                                            <option>交通银行</option>
                                            <option>其他</option>
                                        </select>
                                        <!-- /btn-group -->
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <%--<div class="col-sm-offset-4 col-sm-4 ">--%>
                                <%--<a herf="javascript:return 0;" onclick="modify_sure()"--%>
                                <%--class="Zdy_add  col-sm-12 btn btn-primary">确定--%>
                                <%--</a>--%>
                                <%--</div>--%>
                                <div class="col-sm-offset-4 col-sm-4 ">
                                    <button type="submit" onclick="modify_sure()" data-dismiss="modal"
                                            class="Zdy_add  col-sm-12 btn btn-primary">确定
                                    </button>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!---alert修改业务信息--->
</div>

<![endif]-->
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>

<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
<%--tbody模版--%>
<script id="tbody-template" type="text/x-handlebars-template">
    {{#each this}}
    <tr>
        <td hidden>{{id}}</td>
        <td>{{userId}}</td>
        <td class="name-posit">{{name}}
            <a class="btn btn-blue btn-sm btn-card-more" data-toggle="modal"
               data-target="" onclick="add_card('{{id}}')"><i class="ico icon-add"></i>卡</a>
        </td>

        <td class="bg-style">
            {{#each card}}
            <p>{{cardNumber}}</p>
            {{/each}}
        </td>
        <td>
            {{#each card}}
            <p>{{bankName}}</p>
            {{/each}}
        </td>
        <td>
            {{#each card}}
            <p>
                <span hidden>{{../id}}</span>
                <span hidden>{{bankId}}</span>
                <button class="btn btn-green btn-sm btn-w" data-toggle="modal"
                        data-target=""
                        onclick="modify('{{../id}}','{{bankId}}','{{../name}}','{{cardNumber}}','{{bankName}}')">修改
                </button>
                <button class="btn btn-warning btn-sm" data-toggle="modal"
                        data-target=""
                        onclick="deleteSalesmanData('{{../id}}','{{bankId}}','{{../name}}','{{cardNumber}}','{{bankName}}')">
                    删除
                </button>
            </p>
            {{/each}}

        </td>
    </tr>
    {{/each}}
</script>

</body>

<script src="<%=basePath%>static/salesmanData/salesmanBasicData.js"></script>
</html>


