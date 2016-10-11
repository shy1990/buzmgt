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
    <meta charset="utf-8">
    <base href="<%=basePath%>"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>提成设置</title>

    <!-- Bootstrap -->
    <link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" href="static/multiselect/css/jquery.multiselect.css">
    <link rel="stylesheet" href="static/earnings/css/phone.css">
    <link rel="stylesheet" href="static/earnings/css/comminssion.css">
    <link rel="stylesheet" type="text/css"
          href="static/achieve/achieve.css">
    <link rel="stylesheet" type="text/css"
          href="static/bootStrapPager/css/page.css"/>
    <style type="text/css">
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
    </style>
    <script src="static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <script language="JavaScript" src="../static/js/section/jquery.json.js"></script>
    <script id="goods-template" type="text/x-handlebars-template">
        {{#if this}}
        <option value="">型号</option>
        {{#each this}}
        <option value="{{id}}">{{name}}</option>
        {{/each}}
        {{else}}
        <option value="">型号</option>
        {{/if}}
    </script>
    <script id="brands-template" type="text/x-handlebars-template">
        {{#if this}}
        <option value="">品牌</option>
        {{#each this}}
        <option value="{{brandId}}">{{name}}</option>
        {{/each}}
        {{else}}
        <option value="">品牌</option>
        {{/if}}
    </script>
    <script id="rules-template" type="text/x-handlebars-template">
        {{#each this}}
        <div class="col-sm-4 jfbox-box">
            {{#if min}}
            <span class="text-nub">{{min}}</span>
            <span class="text-publ"> 台 ≤</span>
            {{/if}}
            <span class="text-publ">实际销量 </span>
            {{#if max}}
            <span class="text-publ">＜ </span>
            <span class="text-nub">{{max}}</span>
            <span class="text-publ">台</span> &nbsp;&nbsp;
            {{/if}}
            <span class="text-gery text-size-12">提成：</span>
            <input type="text" class="ph-inpt" placeholder="0.00"
                   onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
                   onafterpaste="this.value=this.value.replace(/[^\d]/g,'')"
                   onblur="addMoney('{{num}}',this.value);"/>
            <span class="text-publ">元/台</span>
        </div>
        {{/each}}
    </script>
    <script id="rules-template-one" type="text/x-handlebars-template">
        {{#each this}}
        <div class="col-sm-4 jfbox-box">
            {{#if min}}
            <span class="text-nub">{{min}}</span>
            <span class="text-publ"> 台 ≤</span>
            {{/if}}
            <span class="text-publ">实际销量 </span>
            {{#if max}}
            <span class="text-publ">＜ </span>
            <span class="text-nub">{{max}}</span>
            <span class="text-publ">台</span> &nbsp;&nbsp;
            {{/if}}
            <span class="text-gery text-size-12">奖励：</span>
            <input type="text" class="ph-inpt" placeholder="0.00"
                   onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
                   onafterpaste="this.value=this.value.replace(/[^\d]/g,'')"
                   onblur="addMoneyOne('{{num}}',this.value);"/>
            <span class="text-publ">元/台</span>
        </div>
        {{/each}}
    </script>


    <script id="numbers-template" type="text/x-handlebars-template">
        {{#each this}}
        {{#if this}}
        <span class=" hden-rwl" data-toggle="modal" data-target="#zzrwul">{{this}}台</span>
        {{/if}}
        {{/each}}
    </script>


    <script id="show-goods-template" type="text/x-handlebars-template">
       {{#each this}}
       <tr>
           <th>{{machineType}}</th>
           <th>{{brand}}</th>
           <th>{{good}}</th>
       </tr>
        {{/each}}
    </script>

    <script type="text/javascript">
        var base = '<%=basePath%>';
        var rule = new Array();//全局变量
        var singleRules = new Array();
        var planId = '${planId}';
    </script>
<body>

<div class="content main">
    <h4 class="page-header ">
        <i class="ico ico-ph-tj"></i>创建
        <!--区域选择按钮-->
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>
    <form id="achieveForm" onsubmit="return false;">
        <input type="hidden" class="J_planId" name="planId" value="${planId }">
        <div class="row ">
            <!--选择-->
            <ul class="mmr-l">
                <li>
                    <span class="text-gery text-strong">请选择：</span>
                    <select class="visit-times J_machineType">
                        <option value="">类别</option>
                        <c:forEach var="machineType" items="${machineTypes }" varStatus="status">
                            <option value="${machineType.id }">${machineType.name }</option>
                        </c:forEach>
                    </select>

                    <select id="brandList" class="visit-times J_brand">
                        <option>品牌</option>
                    </select>

                    <select id="goodList" class="visit-times J_goods">
                        <option>型号</option>
                    </select>
                </li>
                <button class="btn  bnt-sm ph-btn-add" onclick="addGood()"><span class="text-strong">添加</span></button>
                <button class="btn  bnt-sm ph-btn-add" onclick="showGoods()"><span class="text-strong">
                 已添加商品
                </span></button>
            </ul>

            <!--阶梯提成设置-->
            <div class="jttcsz">
                <i class="ico icon-jtsz"></i><span class="text-head text-strong">阶梯提成设置</span> <span
                    class="text-red">*必填</span>
                <hr>
                <div class="rwzsql J_number_box">
                    <span class="text-gery text-strong  ">周期任务量：</span>
                    <!-- 周期任务量 -->
                    <span id="numberList" class="J_number">
	            	</span>
                    <i class="new-icon ph-jiaj" data-toggle="modal"
                       data-target="#zzrwul"></i>
                    <input type="hidden" class="J_numberFirst_" name="numberFirst" value=""/>
                    <input type="hidden" class="J_numberSecond_" name="numberSecond" value=""/>
                    <input type="hidden" class="J_numberThird_" name="numberThird" value=""/>
                </div>

                <div class="jfgz">
                    <span class="text-gery text-strong  ">奖罚规则：</span>
                    <div id="ruleList" class="jfbox J_RULE"></div>
                </div>
            </div>


            <script id="ceshi-template" type="text/x-handlebars-template">
                {{#each this}}
                <div class="col-sm-6 ryfz-box">
                    <a href=""><span class="text-big-green">{{name}}</span></a> &nbsp;

                    {{#if oneAdd}}
                    <span class="text-gery text-size-12">一阶段达量：</span> <span class="text-nub">{{oneAdd}}+</span> <input
                        type="text" class="ph-inpt" onblur="addCount1('{{num}}',this.value);"> <span
                        class="text-gery text-size-12"> 台 </span>&nbsp; &nbsp;
                    {{/if}}
                    {{#if twoAdd}}
                    <span class="text-gery text-size-12">二阶段达量：</span> <span class="text-nub">{{twoAdd}}+</span> <input
                        type="text" class="ph-inpt" onblur="addCount2('{{num}}',this.value);"> <span
                        class="text-gery text-size-12">台</span>
                    {{/if}}
                    {{#if threeAdd}}
                    <span class="text-gery text-size-12">三阶段达量：</span> <span class="text-nub">{{threeAdd}}+</span>
                    <input
                            type="text" class="ph-inpt" onblur="addCount3('{{num}}',this.value);"> <span
                        class="text-gery text-size-12">台</span>
                    {{/if}}


                </div>
                {{/each}}
                <%--{{#each groups}}--%>
                <%--<tr>--%>
                <%--<td>{{name}}</td>--%>
                <%--</tr>--%>
                <%--{{/each}}--%>
            </script>


            <!--人员分组设置-->
            <div class="jttcsz">
                <i class="ico icon-ry"></i><span class="text-head text-strong">人员分组设置</span>
                <hr>
                <div class="jfbox" id="groupJoe">
                </div>
            </div>
            <%-- 一单达量设置 --%>
            <div class="jttcsz">
                <div class="rwzsql O_number_box">
                    <span class="text-gery text-strong  ">一单达量：</span>
                    <!-- 周期任务量 -->
                    <span id="numberListOne" class="J_number">
	            	</span>
                    <i class="new-icon ph-jiaj" data-toggle="modal"
                       data-target="#o_zzrwul"></i>
                    <input type="hidden" class="O_numberFirst_" name="numberFirst" value=""/>
                    <input type="hidden" class="O_numberSecond_" name="numberSecond" value=""/>
                    <input type="hidden" class="O_numberThird_" name="numberThird" value=""/>
                </div>

                <div class="jfgz">
                    <span class="text-gery text-strong  ">奖罚规则：</span>
                    <div id="ruleListOne" class="jfbox J_RULE"></div>
                </div>
            </div>

            <%-- 设置一单任务量 --%>
            <div id="o_zzrwul" class="modal fade" role="dialog">
                <div class="modal-dialog " role="document">
                    <div class="modal-content modal-blue">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span></button>
                            <h3 class="modal-title">设置任务量</h3>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <form id="addNumberFormOne" class="form-horizontal">

                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">达量一：</label>
                                        <div class="col-sm-7">
                                            <div class="input-group are-line">
                                                <span class="input-group-addon "><i
                                                        class="ph-icon   ph-renwu"></i></span>
                                                <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                                <input type="text" class="form-control input-h O_numberFirst"
                                                       aria-describedby="basic-addon1" placeholder="请输入达量"
                                                       onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
                                                       onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
                                                </input>
                                            </div>
                                            <span class="text-gery "
                                                  style="float: right;margin-right: -30px;margin-top: -25px">台</span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">达量二：</label>
                                        <div class="col-sm-7">
                                            <div class="input-group are-line">
                                                <span class="input-group-addon "><i
                                                        class=" ph-icon ph-renwu"></i></span>
                                                <input type="text" class="form-control input-h O_numberSecond"
                                                       onblur="chkScendValue();"
                                                       aria-describedby="basic-addon1" placeholder="请输入达量"
                                                       onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
                                                       onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
                                                </input>
                                            </div>
                                            <span class="text-gery "
                                                  style="float: right;margin-right: -30px;margin-top: -25px">台</span>
                                        </div>
                                    </div>


                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">达量三：</label>
                                        <div class="col-sm-7">
                                            <div class="input-group are-line">
                                                <span class="input-group-addon "><i
                                                        class=" ph-icon ph-renwu"></i></span>
                                                <input type="text" class="form-control input-h O_numberThird"
                                                       onblur="chkThirdValue();"
                                                       aria-describedby="basic-addon1" placeholder="请输入达量"
                                                       onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
                                                       onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
                                                </input>
                                            </div>
                                            <span class="text-gery "
                                                  style="float: right;margin-right: -30px;margin-top: -25px">台</span>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-offset-4 col-sm-4 ">
                                            <a herf="javascript:return 0;" onclick="addRuleOne(this)"
                                               class="Zdy_add  col-sm-12 btn btn-primary ">保存
                                            </a>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
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
                            <textarea rows="10" cols="150" class="fl-left J_remark"></textarea>

                        </dd>
                    </dl>


                </li>
                <li style="margin-top: 30px">
                    <dl class="dl-horizontal">
                        <dt>方案起止时间：</dt>
                        <dd>
                            <div class="ph-search-date fl-left form_date_start">
                                <input name="startDate" type="text"
                                       class="form-control form_datetime input-sm J_startDate" placeholder="开始日期"
                                       readonly="readonly" style="background: #ffffff;margin-right: 20px">
                            </div>

                            <div class="ph-search-date fl-left form_date_end">
                                <input name="endDate" type="text" class="form-control form_datetime input-sm J_endDate"
                                       placeholder="结束日期" readonly="readonly"
                                       style="background: #ffffff;margin-right: 20px">
                            </div>
                            <span class="text-red" style="line-height: 10px">*必填</span>

                        </dd>
                    </dl>
                </li>

                <li>
                    <dl class="dl-horizontal">
                        <dt>提成发放日期：</dt>
                        <dd>
                            <div class="ph-search-date fl-left">
                                <input name="issuingDate" type="text"
                                       class="form-control form_datetime input-sm J_issuingDate" placeholder="选择日期"
                                       readonly="readonly" style="background: #ffffff;margin-right: 20px">
                                <span class="text-red">注：</span> <span
                                    class="text-gery text-size-12">该提成将在该月计算到总体收益内</span>
                            </div>

                        </dd>
                    </dl>
                </li>

                <li style="margin-bottom: 200px">
                    <dl class="dl-horizontal">
                        <dt>指派审核人员：</dt>
                        <dd>
                            <div class="input-group ">
                                <span class="input-group-addon "><i class="icon-s icon-man"></i></span>
                                <!--<input type="text" class="form-control" placeholder="请选择指派审核人员" aria-describedby="basic-addon1">-->
                                <div class="inpt-search">
                                    <select name="basic[]"
                                            class="form-control demo3 J_auditor">

                                        <option value="UT">胡老大</option>
                                        <option value="VT">横额啊</option>
                                        <option value="VA">张二啦</option>
                                        <option value="VA">王晓晓</option>
                                        <option value="WV">杭大大</option>
                                        <option value="WV">曹大大</option>
                                        <option value="WI">槽大小</option>
                                    </select>
                                </div>
                            </div>
                        </dd>
                    </dl>
                </li>
            </ul>

            <div class="widget-register widget-welcome-question mt20 hidden-xs widget-welcome widget-register-slideUp"
                 style="margin-left: -10px;height: 70px">
                <div class="container">
                    <div class="row flex-vertical-center">
                        <div class="form-group ">
                            <div class="row" style="height: 70px">
                                <div class="col-sm-offset-4 col-sm-4" style="margin-top: 50px;margin-bottom: 35px;">
                                    <button type="button" onclick="toSubmit();"
                                            class="col-sm-12 btn btn-primary text-strong" style="margin-top: -25px">确认提交
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- row -->
    </form>
    <!-- end form -->

    <!--设置任务量-->
    <div id="zzrwul" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">设置任务量</h3>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <form id="addNumberForm" class="form-horizontal">

                            <div class="form-group">
                                <label class="col-sm-4 control-label">阶段一：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon "><i class="ph-icon   ph-renwu"></i></span>
                                        <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                        <input type="text" class="form-control input-h J_numberFirst"
                                               aria-describedby="basic-addon1" placeholder="请输入阶段一任务量"
                                               onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
                                               onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
                                        </input>
                                    </div>
                                    <span class="text-gery "
                                          style="float: right;margin-right: -30px;margin-top: -25px">台</span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">阶段二：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon "><i class=" ph-icon ph-renwu"></i></span>
                                        <input type="text" class="form-control input-h J_numberSecond"
                                               onblur="chkScendValue();"
                                               aria-describedby="basic-addon1" placeholder="请输入阶段二任务量"
                                               onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
                                               onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
                                        </input>
                                    </div>
                                    <span class="text-gery "
                                          style="float: right;margin-right: -30px;margin-top: -25px">台</span>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-4 control-label">阶段三：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon "><i class=" ph-icon ph-renwu"></i></span>
                                        <input type="text" class="form-control input-h J_numberThird"
                                               onblur="chkThirdValue();"
                                               aria-describedby="basic-addon1" placeholder="请输入阶段三任务量"
                                               onkeyup="this.value=this.value.replace(/[^\d]/g,'')"
                                               onafterpaste="this.value=this.value.replace(/[^\d]/g,'')">
                                        </input>
                                    </div>
                                    <span class="text-gery "
                                          style="float: right;margin-right: -30px;margin-top: -25px">台</span>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-4 col-sm-4 ">
                                    <a herf="javascript:return 0;" onclick="addRule(this)"
                                       class="Zdy_add  col-sm-12 btn btn-primary ">保存
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--产品列表-->
    <div id="show_goods" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">已添加产品列表</h3>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <table >
                            <thead>
                                <tr>
                                    <th>类型</th>
                                    <th>品牌</th>
                                    <th>型号</th>
                                </tr>
                            </thead>
                            <tbody id="showGoods">


                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
        </div>
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
    <script type="text/javascript" src="static/js/H-select.js"></script>
    <script src="static/js/dateutil.js" type="text/javascript"
            charset="utf-8"></script>
    <script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="static/bootStrapPager/js/extendPagination.js"></script>
    <script type="text/javascript"
            src="static/superposition/superposition_add.js" charset="utf-8"></script>
    <script type="text/javascript">
        // 	$('.J_auditor').multiselect({
        //         columns: 1,
        //         placeholder: '请选择指派审核人员',
        //         search: true,
        // //         selectGroup: true
        //     });
    </script>
</body>

</html>