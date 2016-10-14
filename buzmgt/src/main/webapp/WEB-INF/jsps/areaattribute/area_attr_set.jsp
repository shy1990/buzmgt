<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>提成区域属性设置</title>

    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/incomeCash/css/income-cash.css">
    <link rel="stylesheet" type="text/css" href="static/zTree/css/zTreeStyle/zTreeStyle.css" />

    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>static/js/common.js" type="text/javascript" charset="utf-8"></script>
    <style>
        .ico-quy {
            background: url("../static/img/sz.png") no-repeat center;
        }

        .text-green {
            font-size: 14px;
            color: #0c9b2a;
            font-weight: bold;
        }

        .ico-ss {
            background: url("../static/img/ss.png") no-repeat center;
        }

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

        .label-blue-s{
            display: inline-block;
            background-color: #2385ff;
            width:28px;
            height:23px;
            padding-top: 6px;
            padding-left: 5px;
            border-radius: inherit;
        }


        div span.ssh {
            display: block;
            width: 80px; /*对宽度的定义,根据情况修改*/
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            float: left;
            margin-left: 20px;
            margin-top: 3px;
        }

        /* FF 下的样式 */
        p {
            clear: both;
        }

        p:after {
            content: "...";
        }
    </style>
</head>
<body>
<!---扣罚设置表头-->
<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-quy"></i>提成区域属性设置
        <input type="hidden" id="planType" value="${planType}">
        <div class="area-choose">

        </div>
        <!--/区域选择按钮-->
        <a href="#" class="btn btn-blue"
           data-toggle="modal" data-target="#zdyqy">
            <i class="ico icon-add"></i>添加
        </a>
    </h4>

    <h4 class="text-hd">属性设置：</h4>

    <!----扣罚设置--->
    <div class="table-bordered bg-color">
        <div class="table-responsive  ">
            <!--公里系数表头-->
            <div class="text-tx row-d">
                <span class="text-gery">提成金额：</span>
                <c:if test="${!empty brandIncome}">
                    <span class="text-green ">&nbsp;${brandIncome.commissions}  </span>
                </c:if>
                <c:if test="${!empty priceRange}">
                    <span class="text-green ">&nbsp;${priceRange.percentage}  </span>
                </c:if>
                <span class="text-gery"> 元/台</span>
            </div>

            <c:forEach var="areaAttr" items="${areaAttributes }" varStatus="status">
            <div class="col-sm-3 cl-padd">
                <div class="ratio-box">
                    <div class="ratio-box-dd">
                        <span class="label  label-blue-s">${status.index + 1}</span>
                        <span class="text-black jll"> ${areaAttr.region.namepath } </span>
                        <a class="text-redd jll" href="" data-toggle="modal" data-target=""> ${areaAttr.commissions } <span></span></a>
                        <span class="text-gray">元</span>
                        <a class="text-blue-s jll" href="" data-toggle="modal"
                           data-target="" onclick="modify_attrSet(${areaAttr.id })">修改</a>
                        <a class="text-blue-s jll" href="" data-toggle="modal"
                           data-targ t="" onclick="deleteAttrSet(${areaAttr.id })">删除</a>
                    </div>
                </div>
            </div>

            </c:forEach>
        </div>

    </div><!--扣罚设置--->
</div><!---扣罚设置表头-->

<!-- / alert 修改--->
<div id="changed" class="modal fade" role="dialog">
    <div class="modal-dialog " role="document">
        <div class="modal-content modal-blue">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h3 class="modal-title">设置区域提成</h3>
            </div>

            <div class="modal-body">
                <div class="container-fluid">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">修改提成金额：</label>
                            <div class="col-sm-7">
                                <div class="input-group are-line">
                                    <span class="input-group-addon "><i class="ph-icon   icon-je"></i></span>
                                    <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                    <input name="a" id="input_modify" type="text" class="form-control input-h"
                                           aria-describedby="basic-addon1" placeholder="请修改提成金额">
                                    </input>
                                </div>
                                <span class="text-gery " style="float: right;margin-right: -45px;margin-top: -25px">元/台</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-4 ">
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
<!-- / alert 修改--->


<!---alert自定义添加---->
<div id="zdyqy" class="modal fade" role="dialog">
    <div class="modal-dialog " role="document">
        <div class="modal-content modal-blue">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h3 class="modal-title">设置区域提成</h3>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form id="addForm" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">选择区域：</label>
                            <div class="col-sm-7">
                                <div class="input-group are-line">
												<span class="input-group-addon"><i
                                                        class="icon icon-qy"></i></span>
                                    <select id="region" class="form-control input-h" name="regionId">
                                        <input id="n" type="hidden" value="${regionId}"/>
                                    </select>
                                    <div id="regionMenuContent" class="menuContent">

                                        <ul id="regionTree" class="ztree"></ul>
                                    </div>
                                    <c:if test="${!empty brandIncome}">
                                        <input type="hidden" id="ruleId" name="ruleId" value="${brandIncome.id}">
                                    </c:if>
                                    <c:if test="${!empty priceRange}">
                                        <input type="hidden" id="ruleId" name="ruleId" value="${priceRange.priceRangeId}">
                                    </c:if>
                                    <!-- /btn-group -->
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">设置提成金额：</label>
                            <div class="col-sm-7">
                                <div class="input-group are-line">
                                    <span class="input-group-addon "><i class="ph-icon   icon-je"></i></span>
                                    <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                    <input name="commission" type="text" class="form-control input-h"
                                           aria-describedby="basic-addon1" placeholder="请设置提成金额">
                                    </input>
                                </div>
                                <span class="text-gery " style="float: right;margin-right: -45px;margin-top: -25px">元/台</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-4 ">
                                <a herf="javascript:return 0;" onclick="add();"
                                   class="Zdy_add  col-sm-12 btn btn-primary">确定
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<!---alert自定义---->

<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-switch.min.js"></script>
<%--<script src="<%=basePath%>static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js" type="text/javascript" charset="utf-8"></script>--%>
<script src="<%=basePath%>js/jquery.spinner.js"></script>
<script src="<%=basePath%>static/zTree/js/jquery.ztree.all-3.5.js" type="text/javascript" charset="utf-8"></script>
<!-- 出来区域节点树 -->
<script src="<%=basePath%>static/oil/js/oil-set-region.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=basePath%>static/yw-team-member/team-tree.js" type="text/javascript" charset="utf-8"></script>
<script src="//cdn.bootcss.com/vue/1.0.26/vue.min.js"></script>
<script type="text/javascript">
    function addd(toil) {
        console.log($("#addd").serializeArray());
        addCustom($("#addd").serializeArray());
    }
    var i = 2;

    function addCustom(o) {

        /*var glxs = o[0]["value"];
         var html = '<div class="col-sm-3 cl-padd">' +
         '               <div class="ratio-box">' +
         '                <div class="ratio-box-dd">' +
         '                <span class="label  label-blue-s" style="float: left" >' + (i++) + '</span>' +
         '                <span class="text-black ssh">' + '山东省济南市啊啊啊山东省济南市啊啊啊啊啊啊' + '</span>' +
         '                <a class="text-redd jll" href="" data-toggle="modal"' +
         '        data-target="">' + glxs + '倍</a>' +
         '        <a class="text-blue-s jll" href="" data-toggle="modal"' +
         '        data-target="#changed">修改</a>' +
         '                <a class="text-blue-s jll" href="" data-toggle="modal" data-targ' +
         't="">删除</a>' +
         '                </div>' +
         '                </div>' +
         '                </div>';
         $("#acont").append(html);*/


    };


    var ruleId = $("#ruleId").val();//获取方案id
    var planType = $("#planType").val();//获取规则类别
    console.log(ruleId);
    /*添加*/
    function add() {
        var o = $("#addForm").serializeArray();
        var regionId = o[0]["value"].trim();// regionId
        var commission = o[2]["value"];//commission
        if (regionId.length > 8) {
            alert("请您只选择到：要选择区域的最后一级");
            return location.href = '/areaAttr/setting?ruleId='+ruleId +'&type='+planType;
        }
        $.ajax({
            url: "/areaAttr/save",
            type: "post",
            data: {
                commission: commission,
                regionId: regionId,
                ruleId: ruleId,
                type: planType
            },
            success: function (data) {
                alert(data);
                window.location.href = '/areaAttr/setting?ruleId='+ruleId +'&type='+planType;
                var i = 2;
                $(".label-blue-s").text(i++);
            }
        });
    }

    /*修改*/
    function modify_attrSet(id) {
        $('#changed').modal('show').on('shown.bs.modal', function () {
            $("#sure_update").click(function () {
                var commission = $("#input_modify").val();
                $.ajax({
                    url: '/areaAttr/' + id,
                    type: 'put',
                    data: {
                        commission: commission
                    },
                    success: function (data) {
                        alert(data);
                        window.location.href = '/areaAttr/setting?ruleId='+ruleId +'&type='+planType;
                    }

                });
            });
        })
    }

    /*删除*/
    function deleteAttrSet(id) {
        $.ajax({
            url: '/areaAttr/' + id,
            type: 'delete',
            success: function (data) {
                alert(data);
                window.location.href = '/areaAttr/setting?ruleId='+ruleId +'&type='+planType;
            }
        });
    }
</script>

</body>
</html>
