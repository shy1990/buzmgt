<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>查看</title>
    <link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.css">
    <link rel="stylesheet" href="<%=basePath%>static/multiselect/css/jquery.multiselect.css">
    <link rel="stylesheet" href="<%=basePath%>static/earnings/css/phone.css">
    <link rel="stylesheet" href="<%=basePath%>static/earnings/css/comminssion.css">

    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>

    <style>

        .text-gery {
            color: #757575;
        }

        .ph-search-date > input {
            display: inline-block;
            width: auto;
            height: 30px;
        }

        body {
            font-size: 14px;
            line-height: 1.42857143;
            color: #5D5D5D;
            background-color: #fff;
        }

        .text-heis {
            font-size: 14px;
            color: #5c5c5c;
            font-weight: bolder;
        }

        .text-fs {
            font-size: 12px;
        }

        .tp-marg {
            margin-left: 45px;
            margin-top: 20px;
        }

        .icon-ck {
            background: url("<%=basePath%>static/earnings/img/ck.png") no-repeat center;
        }

    </style>
</head>
<body>

<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-ck"></i>查看
        <!--区域选择按钮-->
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>

    <div class="row ">
        <!--选择-->
        <p class="tp-marg"><span class="text-gery text-strong text-fs">使用区域：</span> <span class="text-heis">山东省</span>
        </p>
        <p class="tp-marg"><span class="text-gery text-strong text-fs">叠加产品：</span>
            <c:forEach items="${superposition.goodsTypeList}" var="GoodsType">
            <span class="text-heis">${GoodsType.machineType.name}${GoodsType.good.name}</span></p>
        </c:forEach>

        <!--阶梯提成设置-->
        <div class="jttcsz">
            <i class="ico icon-jtsz"></i><span class="text-head text-strong">阶梯提成展示</span>
            <hr>
            <div class="rwzsql">
                <span class="text-gery text-strong  ">周期任务量：</span>
                <span class=" hden-rwl"> ${superposition.taskOne}</span>
                <span class=" hden-rwl">${superposition.taskTwo}</span>
                <span class=" hden-rwl">${superposition.taskThree}</span>


            </div>

            <div class="jfgz">
                <span class="text-gery text-strong  ">奖罚规则：</span>

                <div class="jfbox">
                    <c:choose>
                        <c:when test="${superposition.taskThree != null}">
                            <div class="col-sm-4 jfbox-box">
                                <span class="text-publ"> 实际销量 ＜</span> <span
                                    class="text-nub">${superposition.taskOne}</span><span
                                    class="text-publ">台</span> &nbsp;&nbsp; <span
                                    class="text-gery text-size-12">提成：</span><input
                                    type="text" class="ph-inpt" placeholder="5.00"
                                    value="${superposition.ruleList[0].percentage}"> <span class="text-publ">元/台</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.taskOne} </span><span
                                    class="text-publ">台 ≤实际销量＜ </span><span
                                    class="text-nub"> ${superposition.taskTwo}</span><span class="text-publ">台</span>
                                &nbsp;&nbsp;
                                <span class="text-gery text-size-12">提成：</span><input type="text" class="ph-inpt"
                                                                                      placeholder="5.00"
                                                                                      value="${superposition.ruleList[1].percentage}">
                                <span
                                        class="text-publ">元/台</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.taskTwo} </span><span class="text-publ">台 ≤ 实际销量 ＜ </span><span
                                    class="text-nub"> ${superposition.taskThree}</span> <span
                                    class="text-publ">元/台</span> &nbsp;&nbsp;
                                <span class="text-gery text-size-12">提成：</span><input type="text" class="ph-inpt"
                                                                                      placeholder="5.00"
                                                                                      value="${superposition.ruleList[2].percentage}">
                                <span
                                        class="text-publ">元/台</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.taskThree} </span><span class="text-publ">台 ≤ 实际销量</span>
                                &nbsp;&nbsp; <span
                                    class="text-gery text-size-12"> 提成：</span><input
                                    type="text" class="ph-inpt" placeholder="5.00"
                                    value="${superposition.ruleList[3].percentage}"> <span class="text-publ">元/台</span>
                            </div>

                        </c:when>
                        <c:when test="${superposition.taskTwo != null}">

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-publ"> 实际销量 ＜</span> <span
                                    class="text-nub">${superposition.taskOne}</span><span
                                    class="text-publ">台</span> &nbsp;&nbsp; <span
                                    class="text-gery text-size-12">提成：</span><input
                                    type="text" class="ph-inpt" placeholder="5.00"
                                    value="${superposition.ruleList[0].percentage}"> <span class="text-publ">元/台</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.taskOne} </span><span
                                    class="text-publ">台 ≤实际销量＜ </span><span
                                    class="text-nub"> ${superposition.taskTwo}</span><span class="text-publ">台</span>
                                &nbsp;&nbsp;
                                <span class="text-gery text-size-12">提成：</span><input type="text" class="ph-inpt"
                                                                                      placeholder="5.00"
                                                                                      value="${superposition.ruleList[1].percentage}">
                                <span
                                        class="text-publ">元/台</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.taskTwo} </span><span
                                    class="text-publ">台 ≤ 实际销量</span>
                                &nbsp;&nbsp;
                                <span class="text-gery text-size-12">提成：</span><input type="text" class="ph-inpt"
                                                                                      placeholder="5.00"
                                                                                      value="${superposition.ruleList[2].percentage}">
                                <span
                                        class="text-publ">元/台</span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-sm-4 jfbox-box">
                                <span class="text-publ"> 实际销量 ＜</span> <span
                                    class="text-nub">${superposition.taskOne}</span><span
                                    class="text-publ">台</span> &nbsp;&nbsp; <span
                                    class="text-gery text-size-12">提成：</span><input
                                    type="text" class="ph-inpt" placeholder="5.00"
                                    value="${superposition.ruleList[0].percentage}"> <span class="text-publ">元/台</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.taskOne} </span><span
                                    class="text-publ">台 ≤实际销量 </span>
                                &nbsp;&nbsp;
                                <span class="text-gery text-size-12">提成：</span><input type="text" class="ph-inpt"
                                                                                      placeholder="5.00"
                                                                                      value="${superposition.ruleList[1].percentage}">
                                <span
                                        class="text-publ">元/台</span>
                            </div>
                        </c:otherwise>

                    </c:choose>
                </div>
            </div>
        </div>

        <!--人员分组设置-->
        <div class="jttcsz">
            <i class="ico icon-ry"></i><span class="text-head text-strong">人员分组设置</span>
            <hr>

            <div class="jfbox">
                <c:forEach items="${superposition.groupList}" var="group">
                    <div class="col-sm-6 ryfz-box">

                        <a href="##"
                           title="<span style='color:#bebebe;font-weight:bolder'>${group.name} (${group.members.size()}人）</span>"
                           data-container="body" data-toggle="popover" data-trigger="hover" data-placement="right"
                           data-html="true"
                           data-content="
                                   <c:forEach items="${group.members}" var="member">
                                   <span style='color:#cdb6a8;font-weight:bolder;'>${member.salesmanName}</span>
                                   </c:forEach>
                        ">
                            <span class="text-big-green">${group.name}</span></a> &nbsp;
                        <span class="text-gery text-size-12">一阶段达量：</span> <span class="text-nub">${group.oneAdd}</span>
                            <%--<input type="text" class="ph-inpt"> --%>
                        <span class="text-gery text-size-12"> 台 </span>&nbsp; &nbsp;
                        <c:if test="${group.twoAdd != null}">
                            <span class="text-gery text-size-12">二阶段达量：</span> <span
                                class="text-nub">${group.twoAdd}</span>
                            <%--<input type="text" class="ph-inpt"> --%>
                            <span class="text-gery text-size-12">台</span>&nbsp; &nbsp;
                        </c:if>
                        <c:if test="${group.threeAdd != null}">
                            <span class="text-gery text-size-12">三阶段达量：</span> <span
                                class="text-nub">${group.threeAdd}</span>
                            <%--<input type="text" class="ph-inpt"> --%>
                            <span class="text-gery text-size-12">台</span>
                        </c:if>

                    </div>
                </c:forEach>
            </div>
        </div>
        <%-- 一单达量 --%>
        <div class="jttcsz">
            <i class="ico icon-jtsz"></i><span class="text-head text-strong">一单达量展示</span>
            <hr>
            <div class="rwzsql">
                <span class="text-gery text-strong  ">一单达量：</span>
                <span class=" hden-rwl"> ${superposition.singleOne}</span>
                <span class=" hden-rwl">${superposition.singleTwo}</span>
                <span class=" hden-rwl">${superposition.singleThree}</span>
            </div>
            <div class="jfgz">
                <span class="text-gery text-strong  ">奖罚规则：</span>

                <div class="jfbox">
                    <c:choose>
                        <c:when test="${superposition.singleThree != null}">
                            <div class="col-sm-4 jfbox-box">
                                <span class="text-publ"> 实际销量 ＜</span> <span
                                    class="text-nub">${superposition.singleOne}</span><span
                                    class="text-publ">台</span> &nbsp;&nbsp; <span
                                    class="text-gery text-size-12">奖励：</span><input
                                    type="text" class="ph-inpt" placeholder="5.00"
                                    value="${superposition.singleRules[0].reward}"> <span class="text-publ">元</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.singleOne} </span><span
                                    class="text-publ">台 ≤实际销量＜ </span><span
                                    class="text-nub"> ${superposition.singleTwo}</span><span class="text-publ">台</span>
                                &nbsp;&nbsp;
                                <span class="text-gery text-size-12">提成：</span><input type="text" class="ph-inpt"
                                                                                      placeholder="5.00"
                                                                                      value="${superposition.singleRules[1].reward}">
                                <span
                                        class="text-publ">元/台</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.singleTwo} </span><span class="text-publ">台 ≤ 实际销量 ＜ </span><span
                                    class="text-nub"> ${superposition.singleThree}</span> <span
                                    class="text-publ">元</span> &nbsp;&nbsp;
                                <span class="text-gery text-size-12">奖励：</span><input type="text" class="ph-inpt"
                                                                                      placeholder="5.00"
                                                                                      value="${superposition.singleRules[2].reward}">
                                <span
                                        class="text-publ">元</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.singleThree} </span><span class="text-publ">台 ≤ 实际销量</span>
                                &nbsp;&nbsp; <span
                                    class="text-gery text-size-12"> 奖励：</span><input
                                    type="text" class="ph-inpt" placeholder="5.00"
                                    value="${superposition.singleRules[3].reward}"> <span class="text-publ">元/台</span>
                            </div>

                        </c:when>
                        <c:when test="${superposition.taskTwo != null}">

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-publ"> 实际销量 ＜</span> <span
                                    class="text-nub">${superposition.singleOne}</span><span
                                    class="text-publ">台</span> &nbsp;&nbsp; <span
                                    class="text-gery text-size-12">奖励：</span><input
                                    type="text" class="ph-inpt" placeholder="5.00"
                                    value="${superposition.singleRules[0].reward}"> <span class="text-publ">元</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.singleOne} </span><span
                                    class="text-publ">台 ≤实际销量＜ </span><span
                                    class="text-nub"> ${superposition.singleTwo}</span><span class="text-publ">台</span>
                                &nbsp;&nbsp;
                                <span class="text-gery text-size-12">提成：</span><input type="text" class="ph-inpt"
                                                                                      placeholder="5.00"
                                                                                      value="${superposition.singleRules[1].reward}">
                                <span
                                        class="text-publ">元/台</span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="col-sm-4 jfbox-box">
                                <span class="text-publ"> 实际销量 ＜</span> <span
                                    class="text-nub">${superposition.singleOne}</span><span
                                    class="text-publ">台</span> &nbsp;&nbsp; <span
                                    class="text-gery text-size-12">奖励：</span><input
                                    type="text" class="ph-inpt" placeholder="5.00"
                                    value="${superposition.singleRules[0].reward}"> <span class="text-publ">元/台</span>
                            </div>

                            <div class="col-sm-4 jfbox-box">
                                <span class="text-nub">${superposition.singleOne} </span><span
                                    class="text-publ">台 ≤实际销量 </span>
                                &nbsp;&nbsp;
                                <span class="text-gery text-size-12">提成：</span><input type="text" class="ph-inpt"
                                                                                      placeholder="5.00"
                                                                                      value="${superposition.singleRules[1].reward}">
                                <span
                                        class="text-publ">元</span>
                            </div>
                        </c:otherwise>

                    </c:choose>
                </div>
            </div>
        </div>

        <!--补充说明-->
        <!--时间日期人员-->

        <ul>
            <li>
                <dl class="dl-horizontal">
                    <dt>补充说明：</dt>
                    <dd>
                        <div class="fl-left" style="width: 500px;height: 180px;">
                            ${superposition.remark}
                        </div>

                    </dd>
                </dl>


            </li>
            <li style="margin-top: 30px">
                <dl class="dl-horizontal">
                    <dt>方案起止时间：</dt>
                    <dd>
                        <div><span class="text-gery tex"> ${superposition.implDate} 至 ${superposition.endDate}</span>
                        </div>

                    </dd>
                </dl>
            </li>

            <li style="margin-bottom: 200px">
                <dl class="dl-horizontal">
                    <dt>奖罚发放日期：</dt>
                    <dd>
                        <div><span class="text-gery tex">${superposition.giveDate}</span></div>
                    </dd>
                </dl>
            </li>


        </ul>
    </div>
</div>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/earnings/js/H-select.js"></script>
<script>
    //选择
    $(document).ready(function () {
        $("#btnadd").click(function () {
            $("ul").append('<li>' + '<select class="visit-times-nei">' +
                    ' <option>类别</option>' +
                    '<option>上海市</option>' +
                    '<option>北京市</option>' + ' </select>' +
                    '<select class="visit-times">' +
                    ' <option>品牌</option>' +
                    '<option>上海市</option>' +
                    '<option>北京市</option>' + ' </select>'
                    + '<select class="visit-times-nei-s">' +
                    ' <option>型号</option>' +
                    '<option>上海市</option>' +
                    '<option>北京市</option>' + ' </select>' + '</li>');
        })
    })

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
    $('select[multiple].demo3').multiselect({
        columns: 1,
        placeholder: '请选择指派审核人员',
        search: true,
        selectGroup: true
    });

    $(function () {
        $("[data-toggle='popover']").popover();
    });
</script>
</body>
</html>
