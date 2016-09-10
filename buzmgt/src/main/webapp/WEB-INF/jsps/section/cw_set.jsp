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
    <title>提成设置</title>

    <link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/phone.css">
    <link rel="stylesheet" href="<%=basePath%>static/css/comminssion.css">

    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>

    <style>
        /*#ul li{*/
            /*color: blue;*/
        /*}*/
        /*a:hover{*/
            /*color: red;*/
        /*}*/
        .new-table-box {
            border-top: none;
            min-height: 600px;
            background: #FFF;
        }

        .nav-sidebar > li.current > a, .nav-sidebar > li .current > a:focus, .nav-sidebar > li.current > a:hover {
            color: #2b86ba;
            border-left: 3px solid #44a6dd;
            background: #ffffff;
        }

        .icon-je {
            background: url("<%=basePath%>static/img/tcje.png") no-repeat center;
        }

        .ph-icon {
            height: 30px;
            padding: 5px 10px;
            font-size: 12px;
            line-height: 1.5;
            border-radius: 3px;
        }

        .icon-riz {
            background: url("<%=basePath%>static/img/rizi.png") no-repeat center;
        }

        .icon-reny {
            background: url("<%=basePath%>static/img/shry.png") no-repeat center;
        }


    </style>
    <script type="text/javascript">
        /**
         * 修改单个小区间
         * @param id
         * @param priceRange
         */
        function modify(id, priceRange) {
            console.log(id + '  ' + priceRange);

            $('#gaigai').modal('show').on('shown.bs.modal', function () {
                $("#priceRange").text(priceRange);
                $("#sure_save").click(function () {
                    var valueData = $("#addd").serializeArray();
                    console.log(valueData);
                    var percentage = valueData[0]['value'];
                    var implDate = valueData[1]['value'];
                    var auditorId = valueData[2]['value'];
                    var productionId = ${productionId};
                    console.log(percentage+" "+implDate+"  "+auditorId);
                    $.ajax({
                        url:'modifyPriceRange/'+id,
                        type:'post',
                        data:{percentage:percentage,implDate:implDate,auditorId:auditorId,productionId:productionId},
                        success:function(data){
                            alert("修改成功,正在审核");
                            refresh();
                        },
                        error:function(){

                        }
                    });

                });
            })

            /**
             * 当弹框消失的时候刷新页面
             */
            $('#gaigai').on('hidden.bs.modal', function () {
                refresh();
            })
            function refresh() {
                window.location.reload();//刷新当前页面.
            }

        }

        function kkk(v, a) {
            if (!(/^[\d]+\.?\d*$/.test(v))) {
                alert("请输入数字");
                $(a).val('');

            }
        }
    </script>
</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-tcsz"></i>提成设置
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>

    <%--<ul class="nav nav-pills  nav-top" id="myTab">--%>
    <%--<li class="active"><a data-toggle="tab" href="#ajgqj">按价格区间</a></li>--%>
    <%--<li><a data-toggle="tab" href="#ppxhao">品牌型号<span class="qipao">2</span></a></li>--%>
    <%--<li><a data-toggle="tab" href="#dlsz">达量设置</a></li>--%>
    <%--<li><a data-toggle="tab" href="#djsz">叠加设置</a></li>--%>
    <%--<li><a href="ti-daliang(达量奖励).html">达量奖励</a></li>--%>
    <%--</ul>--%>


    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->
            <div class="order-box">
                <!--左侧导航开始-->
                <div style="padding-left: 0">
                    <div class=" sidebar left-side" style="padding-top:0;margin-top:5px">
                        <h5 class="line-h">
                            <i class="ico ico-fl"></i>请选择类别
                        </h5>
                        <%--手机类型导航栏--%>
                        <ul id="ul" class="nav nav-sidebar menu">
                            <c:forEach items="${machineTypes}" var="machineType">
                                <li class="current">
                                    <a href="<%=basePath%>section/findNow?type=${machineType.id}"> ${machineType.name}</a>
                                </li>
                            </c:forEach>
                        </ul>

                    </div>
                </div>
                <%--<script>--%>
                    <%--$('a[data-toggle="phone"]').on('shown.bs.tab', function (e) {--%>
                        <%--e.target // newly activated tab--%>
                        <%--e.relatedTarget // previous active tab--%>
                    <%--})--%>

                <%--</script>--%>
                <!--左侧导航结束-->
                <div class="tab-content">
                    <!--右侧内容开始-->
                    <!--价格区间-->
                    <div class="tab-pane fade in active right-body" id="ajgqj">
                        <!--导航开始-->

                        <div class="ph-btn-set">
                            <a href="addPriceRanges?type=${type}" class="btn ph-blue">
                                <i class="ico ico-tj"></i>新建区间值
                            </a>

                            <a href="" class="btn ph-blue">
                                <i class="ico ico-qj"></i>设置记录
                            </a>
                            <div class="clearfix">
                                <div class="link-posit pull-right" style="margin-top: -25px">
                                    <a class="table-export" href="javascript:void(0);">导出excel</a>
                                </div>
                            </div>
                        </div>


                        <div class="table-task-list new-table-box table-overflow" style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>价格区间</th>
                                    <th>提成金额</th>
                                    <th>开始日期</th>
                                    <th>区域属性</th>
                                    <th>状态</th>
                                    <th>设置日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${list}" var="priceRange">
                                    <tr>
                                        <input hidden value="${priceRange.priceRangeId}"/>
                                        <td>${priceRange.serialNumber}</td>
                                        <td>${priceRange.priceRange}元</td>
                                        <td class="width-fixed">
                                            <span class="text-green">${priceRange.percentage}元/台</span>
                                            <a href="javascript:;" class="btn btn-sm btn-findup" data-toggle="modal"
                                               onclick="modify('${priceRange.priceRangeId}','${priceRange.priceRange}')">修改</a>
                                                <%-- data-target="#gaigai" --%>
                                        </td>
                                        <td>${priceRange.implementationDate}</td>
                                        <td><a href="">添加区域设置</a></td>
                                        <td><span class="ph-on">进行中</span></td>
                                        <td>${priceRange.priceRangeCreateDate}</td>
                                        <td>
                                            <button class="btn btn-sm btn-zz " data-toggle="modal" data-target="#">终止
                                            </button>
                                        </td>
                                    </tr>

                                </c:forEach>


                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>


            </div>


        </div>


    </div>

</div>
<!--修改-->
<div id="gaigai" class="modal fade" role="dialog">
    <div class="modal-dialog " role="document">
        <div class="modal-content modal-blue">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h3 class="modal-title">修改</h3>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form id="addd" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">价格区间：</label>
                            <div class="col-sm-7">
                                <span id="priceRange" class="text-gery"></span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">设置提成金额：</label>
                            <div class="col-sm-7">
                                <div class="input-group are-line">
                                    <span class="input-group-addon "><i class="ph-icon   icon-je"></i></span>
                                    <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                    <input name="a" type="text" class="form-control input-h"
                                           aria-describedby="basic-addon1" placeholder="请设置提成金额"  onchange="kkk(this.value,this)">
                                    </input>
                                </div>
                                <span class="text-gery "
                                      style="float: right;margin-right: -45px;margin-top: -25px">元/台</span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">方案实施日期：</label>
                            <div class="col-sm-7">
                                <div class="input-group are-line">
                                    <span class="input-group-addon "><i class=" ph-icon icon-riz"></i></span>
                                    <input type="text" class="form-control form_datetime " placeholder="请选择实施日期"
                                           readonly="readonly  " style="background: #ffffff;" name="implDate">
                                </div>
                                <span class="text-gery "
                                      style="float: right;margin-right: -30px;margin-top: -25px">起</span>
                            </div>
                        </div>


                        <div class="form-group">
                            <label class="col-sm-4 control-label">指派审核人员：</label>
                            <div class="col-sm-7">
                                <div class="input-group are-line">
                                    <span class="input-group-addon"><i class="ph-icon icon-reny"></i></span>
                                    <select name="b" type="" class="form-control input-h "
                                            aria-describedby="basic-addon1">
                                        <option></option>
                                        <option>中国银行</option>
                                        <option>农业银行</option>
                                        <option>工商银行</option>
                                        <option>亚细亚银行</option>
                                    </select>
                                    <!-- /btn-group -->
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-4 ">
                                <a herf="javascript:return 0;" id="sure_save"
                                   class="Zdy_add  col-sm-12 btn btn-primary">保存
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</div>
<%--<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>--%>
<script src="../static/bootstrap/js/bootstrap.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script>
    $('#tab li').click(function () {
        $(this).addClass('active');
        $(this).siblings('li').removeClass('active');
        $('#tab li:eq(1) a').tab('show');
    });

    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-right",
        forceParse: 0
    });
</script>
</body>
</html>
