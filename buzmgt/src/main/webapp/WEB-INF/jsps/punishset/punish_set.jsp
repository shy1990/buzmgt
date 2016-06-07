﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

<head>
    <base href="<%=basePath%>"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>设置油补系数</title>
    <!-- Bootstrap -->

    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-switch.min.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" type="text/css"
          href="static/incomeCash/css/income-cash.css">
    <script src="static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>


    <link rel="stylesheet" type="text/css"
          href="/static/zTree/css/zTreeStyle/zTreeStyle.css"/>
    <script src="/static/js/jquery/jquery.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src='/static/js/common.js'></script>

    <style type="text/css">
        .ztree {
            margin-top: 34px;
            border: 1px solid #ccc;
            background: #FFF;
            width: 100%;
            overflow-y: scroll;
            overflow-x: auto;
        }

        .menuContent {
            width: 100%;
            padding-right: 50px;
            display: none;
            position: absolute;
            z-index: 800;
        }
    </style>

</head>

<body>
<body>
<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-account-manage-oil"></i>扣罚设置
    </h4>
    <div>
        <!-- Nav tabs -->
        <h4 class="text-hd">扣罚设置：</h4>
        <!-- Tab panes -->
        <!--全部--->
        <!--公里系数设置-->
        <div class="table-bordered bjc">
            <div class="tab-content-main">
                <div class="tab-pane active">
                    <div class="table-responsive tb-b ">
                        <!--公里系数表头-->
                        <div class="text-tx row-d">
                            <span class="text-gery">扣罚设置系数：</span> <select id="default_set">
                            <c:if test="${punishSet1 != null }">
                                <option>${punishSet1.punishNumber}</option>
                                <%--<fmt:formatNumber var="c" value="${punishSet1.punishNumber*100}" pattern="#"/>--%>
                                <%--<option>${c}</option>--%>
                            </c:if>
                            <option value="0.1">0.1</option>
                            <option value="0.2">0.2</option>
                            <option value="0.3">0.3</option>
                            <option value="0.4">0.4</option>
                            <option value="0.5">0.5</option>
                            <option value="0.6">0.6</option>
                        </select> <span class="text-gery "></span> <span class="text-blue-s jl-">注：</span><span
                                class="text-gery-hs">系统默认所有区域均为改系数，自定义设置区域除外</span>
                        </div>
                        <!--设置公里系数表-->

                        <c:forEach var="punishSet" items="${list }" varStatus="status">

                            <div class="col-sm-3 cl-padd">
                                <div class="ratio-box">
                                    <div class="ratio-box-dd">
                                        <span class="label  label-blue"> ${status.index+1 }</span> <span
                                            class="text-black jll"> ${punishSet.region.name } </span> <a
                                            class="text-redd jll" href="" data-toggle="modal"
                                            data-target=""> ${punishSet.punishNumber }  <span></span></a> <a
                                            class="text-blue-s jll" href="" data-toggle="modal"
                                            data-target="" onclick="modify_punishSet(${punishSet.id })">修改</a>
                                        <a class="text-blue-s jll" href="" data-toggle="modal"
                                           data-targ t="" onclick="deletePunishSet(${punishSet.id })">删除</a>
                                    </div>
                                </div>
                            </div>

                        </c:forEach>


                    </div>
                </div>
            </div>
        </div>
        <div class="show-grid   row-jl ">
            <div class="col-md-5"></div>
            <div class="col-md-7  zdy-h ">
                <button class=" col-sm-3 btn  btn btn-default" type="button"
                        data-toggle="modal" data-target="#zdyqy">添加区域
                </button>
            </div>
        </div>
        <div class="form-group ">
            <div class="row">
                <div class="col-sm-offset-4 col-sm-4" style="margin-top: 20px">
                    <button type="submit" class="col-sm-12 btn btn-primary "
                            id="button_bocun" onclick="defaultPunishSet()">保存
                    </button>
                </div>
            </div>
        </div>

        <!-- /alert 弹出单个修改弹框 -->
        <div id="changed" class="modal fade" role="dialog">
            <div class="modal-dialog " role="document">
                <div class="modal-content modal-blue">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h3 class="modal-title">修改</h3>
                    </div>

                    <div class="modal-body">
                        <div class="container-fluid">
                            <form class="form-horizontal">


                                <div class="form-group">
                                    <label class="col-sm-4 control-label">选择系数：</label>
                                    <div class="col-sm-7">
                                        <div class="input-group are-line">
												<span class="input-group-addon"><i
                                                        class="icon icon-lk"></i></span> <select type=""
                                                                                                 class="form-control input-h"
                                                                                                 aria-describedby="basic-addon1"
                                                                                                 id="select_modify">
                                            <option></option>
                                            <option value="0.1">0.1</option>
                                            <option value="0.2">0.2</option>
                                            <option value="0.3">0.3</option>
                                            <option value="0.4">0.4</option>
                                            <option value="0.5">0.5</option>
                                            <option value="0.6">0.6</option>
                                        </select>
                                            <!-- /btn-group -->
                                        </div>
                                    </div>
                                    <div class="col-sm-1 control-label">
                                        <span></span>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-offset-4 col-sm-4 ">
                                        <!--  <button type="submit" class="col-sm-12 btn btn-primary " >确定</button>-->
                                        <a herf="javascript:return 0;" id="sure_update"
                                           class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- /alert 自定义扣罚弹框 -->

        <div id="zdyqy" class="modal fade" role="dialog">
            <div class="modal-dialog " role="document">
                <div class="modal-content modal-blue">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h3 class="modal-title">自定义区域</h3>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid">
                            <form id="addForm" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">选择区域：</label>
                                    <div class="col-sm-7">
                                        <div class="input-group are-line">
												<span class="input-group-addon"><i
                                                        class="icon icon-qy"></i></span> <select id="region"
                                                                                                 class="form-control input-h"
                                                                                                 name="regionId">
                                            <input id="n" type="hidden" value="${regionId}"/>
                                        </select>
                                            <div id="regionMenuContent" class="menuContent">

                                                <ul id="regionTree" class="ztree"></ul>
                                            </div>
                                            <input type="hidden" id="towns" name="regionPid">
                                            <!-- /btn-group -->
                                        </div>
                                    </div>
                                </div>
                                <div id="xiugai">
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">扣罚系数：</label>
                                        <div class="col-sm-7">
                                            <div class="input-group are-line">
													<span class="input-group-addon"><i
                                                            class="icon icon-task-lk"></i></span> <select name="b"
                                                                                                          type=""
                                                                                                          class="form-control input-h"
                                                                                                          aria-describedby="basic-addon1"
                                                                                                          id="select">
                                                <option value="0.1">0.1</option>
                                                <option value="0.2">0.2</option>
                                                <option value="0.3">0.3</option>
                                                <option value="0.4">0.4</option>
                                                <option value="0.5">0.5</option>
                                                <option value="0.6">0.6</option>
                                            </select>
                                                <!-- /btn-group -->
                                            </div>
                                        </div>
                                        <div class="col-sm-1 control-label">
                                            <span></span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-offset-4 col-sm-4 " id="href_div">
                                            <a herf="javascript:return 0;" onclick="addd()"
                                               class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script src="static/js/jquery/jquery-1.11.3.min.js"></script>

        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="static/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
        <script
                src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
                type="text/javascript" charset="utf-8"></script>


        <!------------------------------------------------------------------------------------------------------------------------ -->
        <script src="/static/zTree/js/jquery.ztree.all-3.5.js"
                type="text/javascript" charset="utf-8"></script>

        <!-- 出来区域节点树 -->
        <script src="/static/oil/js/oil-set-region.js" type="text/javascript"
                charset="utf-8"></script>


        <!--  暂时没有用
<script src="/static/yw-team-member/team-memberForm.js"
    type="text/javascript" charset="utf-8"></script>
     -->
        <script src="/static/yw-team-member/team-tree.js"
                type="text/javascript" charset="utf-8"></script>
        <script src="/static/js/common.js" type="text/javascript"
                charset="utf-8"></script>


        <!-- <script src="/static/oil/js/oil.js" type="text/javascript"
            charset="utf-8"></script> -->

        <script type="text/javascript">
            $(function resetTabullet() {
                $("#table").tabullet({
                    data: source,
                    action: function (mode, data) {
                        console.dir(mode);
                        if (mode === 'save') {
                            source.push(data);
                        }
                        if (mode === 'edit') {
                            for (var i = 0; i < source.length; i++) {
                                if (source[i].id == data.id) {
                                    source[i] = data;
                                }
                            }
                        }
                        if (mode == 'delete') {
                            for (var i = 0; i < source.length; i++) {
                                if (source[i].id == data) {
                                    source.splice(i, 1);
                                    break;
                                }
                            }
                        }
                        resetTabullet();
                    }
                });
                resetTabullet();
            });
        </script>
        <script type="text/javascript">
            /*添加*/
            function addd() {
                var o = $("#addForm").serializeArray();
                var regionId = o[0]["value"].trim();// regionId
                var punishNumber = o[2]["value"];//punishNumber
                console.log(o);
                if (regionId.length > 7) {
                    alert("请您只选择到：要选择区域的最后一级");
                    return location.href = '/punish/punishs';
                }
                $.ajax({
                    url: "punish/punishs",
                    type: "post",
                    data: {
                        punishNumber: punishNumber,
                        regionId: regionId
                    },
                    success: function (data) {
                        alert(data);
                        window.location.href = '/punish/punishs';
                    }

                });
            }
            /*修改*/
            function modify_punishSet(id) {
                console.log(id);
                $('#changed').modal('show').on('shown.bs.modal', function () {
                    $("#sure_update").click(function () {
                        var punishNumber = $("#select_modify").val();
                        console.log(id);
                        console.log(punishNumber);
                        $.ajax({
                            url: 'punish/punishs/' + id,
                            type: 'put',
                            data: {punishNumber: punishNumber},
                            success: function (data) {
                                alert(data);
                                window.location.href = '/punish/punishs';
                            }

                        });
                    });
                })
            }


            /*删除*/
            function deletePunishSet(id) {
                $.ajax({
                    url: 'punish/punishs/' + id,
                    type: 'delete',
                    success: function (data) {
                        alert(data);
                        window.location.href = '/punish/punishs';
                    }

                });
            }


            /*设置默认*/
            function defaultPunishSet(id) {
                var regionId = '0';
                var punishNumber = $("#default_set").val();
                console.log(punishNumber);
                $.ajax({
                    url: 'punish/punishs/modify/' + regionId,
                    data: {punishNumber: punishNumber},
                    type: "post",
                    success: function (data) {
                        alert(data);
                        window.location.href = '/punish/punishs';
                    }

                })
            }


            /*无用*/
            function update() {
                $.ajax({
                    url: 'punish/punishs/1',
                    type: 'put',
                    //	data:Json.stringify({punishNumber:'4545'}),
                    //data:JSON.stringify({
                    //punishNumber:'ceshi',
                    //email:'6575@qq.com'
                    //}),
                    data: {punishNumber: '0.8888'},
                    //data:JSON.stringify({
                    //punishNumber:'ceshi',
                    //email:'6575@qq.com'
                    //}),
                    //beforeSend:function(request){
                    //request.setRequestHeader("ContentType","application/json");
                    //},
                    success: function () {
                        window.location.href = '/punish/punishs';
                    }

                });
            }
        </script>
</body>

</html>