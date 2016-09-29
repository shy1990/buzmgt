<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>查看</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet"
	href="static/multiselect/css/jquery.multiselect.css">
<link rel="stylesheet" href="static/earnings/css/phone.css">
<link rel="stylesheet" href="static/earnings/css/comminssion.css">
<link rel="stylesheet" type="text/css" href="static/award/award.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
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

        .icon-ck{
            background:url("/static/earnings/img/ck.png") no-repeat center ;
        }
</style>
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
var	base='<%=basePath%>';
	var rule = new Array();//全局变量
	var groupNumbers = new Array();
	var userList = new Array();//所有用户的UserId
</script>
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
        <p class="tp-marg"><span class="text-gery text-strong text-fs">达量产品：</span> 
        <c:forEach var="awardGood" items="${award.awardGoods }" varStatus="status">
	        <span class="text-heis">${awardGood.machineType.name }——${awardGood.good.name }</span>
        </c:forEach>
        </p>
        <!--阶梯提成设置-->
        <div class="jttcsz">
            <i class="ico icon-jtsz"></i><span class="text-head text-strong">阶梯提成设置</span> <span
                class="text-red">*必填</span>
            <hr>
            <div class="rwzsql">
                <span class="text-gery text-strong  ">周期任务量：</span>
                <span class=" number-blue">${award.numberFirst }</span>
                <span class=" number-blue">${award.numberSecond }</span>
                <span class=" number-blue">${award.numberThird }</span>
                <!-- <i class="new-icon ph-jiaj" data-toggle="modal"
                   data-target="#zzrwul"></i> -->
            </div>

            <div class="jfgz">
                <span class="text-gery text-strong  ">奖罚规则：</span>

                <div class="jfbox">
                	<c:forEach items="${award.rewardPunishRules }" var="rule" varStatus="status">
                    <div class="col-sm-4 jfbox-box">
                    	<c:if test="${rule.min != '' && rule.min != null }">
                        <span class="text-nub">${rule.min } </span>
                        <span class="text-publ">台 ≤ </span>
                    	</c:if>
                        <span class="text-publ">实际销量 </span>
                    	<c:if test="${rule.max != ''&& rule.max != null  }">
                        <span class="text-publ">＜ </span>
                        <span class="text-nub"> ${rule.max }</span>
                        <span class="text-publ">台</span>
                    	</c:if>
                        <span class="text-gery text-size-12">提成：</span>
                        <input type="text" class="ph-inpt" value="${rule.money }" disabled="disabled"> <span class="text-publ">元/台</span>
                    </div>
                	</c:forEach>
                </div>
            </div>
        </div>

        <!--人员分组设置-->
        <div class="jttcsz">
            <i class="ico icon-ry"></i><span class="text-head text-strong">人员分组设置</span>
            <hr>

            <div class="jfbox">
            	<c:forEach items="${award.groupNumbers }" var="group" varStatus="status">
                <div class="col-sm-6 ryfz-box">
                    <a href="javascript:;" title="${group.groupName }组（${fn:length(group.groupUsers)}人）" data-container="body" data-toggle="popover" data-placement="right"
                       data-content="
                       <c:forEach items ='${group.groupUsers }' var='user'> ${user.planUser.truename } </c:forEach>">
                        <span class="text-big-green">${group.groupName }组（${fn:length(group.groupUsers)}人）</span>
                    </a> &nbsp;
                    <c:if test="${group.numberFirstAdd != '' && group.numberFirstAdd != null}">
		                  <span class="text-gery text-size-12">一阶段达量：</span> <span class="text-nub">${award.numberFirst } +</span>
	                    <input type="text" class="ph-inpt" disabled="disabled" value="${group.numberFirstAdd }"/> <span class="text-gery text-size-12"> 台 </span>&nbsp; &nbsp;
                    </c:if>
                    <c:if test="${group.numberSecondAdd != '' && group.numberSecondAdd != null}">
		                  <span class="text-gery text-size-12">二阶段达量：</span> <span class="text-nub">${award.numberSecond } +</span>
	                    <input type="text" class="ph-inpt" disabled="disabled" value="${group.numberSecondAdd }"/> <span class="text-gery text-size-12"> 台 </span>&nbsp; &nbsp;
                    </c:if>
                    <c:if test="${group.numberThirdAdd != '' && group.numberThirdAdd != null}">
		                  <span class="text-gery text-size-12">三阶段达量：</span> <span class="text-nub">${award.numberThird } +</span>
	                    <input type="text" class="ph-inpt" disabled="disabled" value="${group.numberThirdAdd }"/> <span class="text-gery text-size-12"> 台 </span>&nbsp; &nbsp;
                    </c:if>
                </div>
            	</c:forEach>
                <!-- <div class="col-sm-6 ryfz-box box-add tianjz">
                    <i class="ico ico-ph-tj "></i> <a href=""> <span class="text-gery">添加组</span></a>
                </div> -->
            </div>
        </div>

        <!--补充说明-->
        <!--时间日期人员-->

        <ul>
            <li>
                <dl class="dl-horizontal">
                    <dt>补充说明：</dt>
                    <dd>
                        <div  class="fl-left" style="width: 500px;min-height: 80px;">${award.remark }</div>
                    </dd>
                </dl>

            </li>
            <li style="margin-top: 30px">
                <dl class="dl-horizontal">
                    <dt>方案起止时间：</dt>
                    <dd>
                        <div> <span class="text-gery tex"><fmt:formatDate value="${award.startDate}" pattern="yyyy-MM-dd"/>
                        	至 <fmt:formatDate value="${award.endDate}" pattern="yyyy-MM-dd"/> </span></div>
                    </dd>
                </dl>
            </li>

            <li style="margin-bottom: 200px">
                <dl class="dl-horizontal">
                    <dt>奖罚发放日期：</dt>
                    <dd>
                        <div> <span class="text-gery tex"><fmt:formatDate value="${award.issuingDate}" pattern="yyyy-MM-dd"/></span></div>
                    </dd>
                </dl>
            </li>
            <li style="margin-bottom: 200px">
                <dl class="dl-horizontal">
                    <dt>审核人：</dt>
                    <dd>
                        <div> <span class="text-gery tex">${award.auditor }</span></div>
                    </dd>
                </dl>
            </li>
        </ul>
    </div>
</div>
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
                    <form id="addd" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">阶段一：</label>
                            <div class="col-sm-7">
                                <div class="input-group are-line">
                                    <span class="input-group-addon "><i class="ph-icon   ph-renwu"></i></span>
                                    <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                    <input name="a" type="text" class="form-control input-h"
                                           aria-describedby="basic-addon1" placeholder="请输入阶段一任务量">
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
                                    <input name="a" type="text" class="form-control input-h"
                                           aria-describedby="basic-addon1" placeholder="请输入阶段二任务量">
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
                                    <input name="a" type="text" class="form-control input-h"
                                           aria-describedby="basic-addon1" placeholder="请输入阶段三任务量">
                                    </input>
                                </div>
                                <span class="text-gery "
                                      style="float: right;margin-right: -30px;margin-top: -25px">台</span>
                            </div>
                        </div>
                    </form>
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
		<script type="text/javascript">
		$(function () {
	        $("[data-toggle='popover']").popover();
	    });
		</script>
</body>

</html>