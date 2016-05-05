<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>考核详情</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link href="static/bootStrapPager/css/page.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/kaohe/kaohe-det.css" />
<link rel="stylesheet" type="text/css" href="static/yw-team-member/ywmember.css" />
<script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var base = "<%=basePath%>";
	var number = '';//当前页数（从零开始）
	var totalPages = '';//总页数(个数)
	var searchData = {
		"size" : "2",
		"page" : "0",
	}
	var totalElements;//总条数
</script>
<script id="table-template" type="text/x-handlebars-template">
{{#each content}}
	<tr>
		<td class="shop-name">{{shopName }}</td>
		<td class="tihuo-num">{{orderTimes }}次</td>
		<td class="total-num">{{orderNum }}</td>
		<td class="sum">{{orderTotalCost }}</td>
	</tr>
{{else}}
<div style="text-align: center;">
	<tr style="text-align: center;">没有相关数据!</tr>
</div>
{{/each}}
</script>
</head>
<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="icon icon-khxq"></i>考核详情
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="kaohe-det-body box border blue">
					<!--title-->
					<div class="box-title">
						<!--start row-->
						<div class="row">
							<div class="col-sm-12">
								<!--业务人员信息-->
								<i class="ico icon-ywmember"></i>${salesman.truename} 
								<!--/业务人员信息-->
								<div class="kaohe-time">
									开始时间： <span>${startDate}</span> 结束时间： <span>${endDate}</span>
								</div>
							</div>
						</div>
						<!--end row-->
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<!--列表头部-->
						<div class="det-head ">
							<!--bar 布局-->
							<div class="col-sm-10 clear-p-l">
								<div class="quiet-days marg-b-30">
									<c:if test="${assess.assessStage == '1'}">
										<span class="kaohe-stage onekaohe-stage">第一阶段考核</span> 
									</c:if>
									<c:if test="${assess.assessStage == '2' }">
										<span class="kaohe-stage twokaohe-stage ">第二阶段考核</span> 
									</c:if>
									<c:if test="${assess.assessStage == '3'}">
										<span class="kaohe-stage threekaohe-stage">第三阶段考核</span> 
									</c:if>
									<c:if test="${assess.assessStage == '3'&& assess.status == 'AGREE'}">
										<span class="kaohe-stage overkaohe-stage">已转正</span> 
									</c:if>
									<c:if test="${assess.status == 'FAIL'}">
										<span class="kaohe-stage failurekaohe-stage ">考核失败</span> 
									</c:if>
									<span class="count-down font-12"> <i class="ico icon-countdown"></i>倒计时:
										<span class="text-time">${timing }</span>天
									</span>
									
									<strong class="target font-12">指标：</strong><span class="font-12">活跃客户 <span class="text-blue"> ${assess.assessActivenum}</span>家 ，
									提货量<span class="text-blue">${assess.assessOrdernum}</span>台 </span>
								</div>
								<!--class style 沉寂:progress-bar-later ;活跃：progress-bar-ok ;未提货：-->
								<!--start 考核进度条-->
								<div class="J_kaohebar_parents progress progress-sm">
									<div style="width: ${percent }%;"
										class="J_kaohebar progress-bar bar-kaohe">
										<div class="tag">
											<span class="icon-tag tag-kaohe">${percent }%</span>
										</div>
									</div>
								</div>
								<!--end 考核进度条-->
							</div>
							<!--bar 布局-->
							<div class="col-xs-2 clear-p-r">
								<button class="J_btn col-xs-12 btn btn-blue marg-t-30"
									onclick="toAssessStage('${salesman.id}','${assess.id}');">考核通过</button>
							</div>
						</div>
						<!--/列表头部-->
						<div class="hr-solid"></div>
						<!--start 操作区域-->
						<div class="search-box " data-sid="${salesman.id}",data-aid="${assess.id}">
							<!--区域选择按钮-->
							请选择区域：
							<select id="regionId" class="form-control input-xs ad-select" style=""><!--  -->
								<c:if test="${not empty regionList}">
									<%-- <option value="" selected="selected">${salesman.region.name}</option> --%>
									<option value="" selected="selected">全部区域</option>
									<c:forEach var="region" items="${regionList}" varStatus="s">
										<option value="${region.id}">${region.name}</option>
								</c:forEach>
								</c:if>
							</select>
							<!--/区域选择按钮-->
							<strong class="targetover">已达成：</strong><span>活跃客户 <span class="text-blue"> ${active }</span>家 ，提货量<span class="text-blue">${orderNum }</span>台 </span>
							<!--考核开始时间-->
							<br>
							<div class="marg-t">
								请选择时间：
								<div class="search-date">
								<div class="input-group input-group-sm date form_date_start">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" class="form-control form_datetime input-sm"
										placeholder="开始日期" readonly="readonly" id="beginTime">
								</div>
							</div>
							--
							<div class="search-date">
								<div class="input-group input-group-sm date form_date_end">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" class="form-control form_datetime input-sm"
										placeholder="结束日期" readonly="readonly" id="endTime">
								</div>
							</div>
								<!--考核开始时间-->
								<%-- <input type="hidden" id="sid" value="${salesman.id}"/>
								<input type="hidden" id="aid" value="${assess.id}"/> --%>
								<button class="btn btn-blue btn-sm"
									onclick="goSearch();">
									<i class="ico ico-seach-wiath"></i>检索
								</button>
								<a class="link-export pull-right" href="javascript:void(0);">导出excel</a>
							</div>
						</div>
						<!--end 操作区域-->
						<!--列表内容-->
						<div class="company-table table-responsive ">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>店铺名</th>
										<th>提货次数</th>
										<th>累计提货量</th>
										<th>累计交易额</th>
									</tr>
								</thead>
								<tbody id="tableList">

								</tbody>
							</table>
							<div id="callBackPager"></div>
						</div>
						<!--/列表内容-->
					</div>
					<!--/box-body-->
				</div>
				<!--/box-->
			</div>
			<!--team-map-->
			<div class="col-md-3">
				<!--box-->
				<!--不同阶段颜色不同1：pink（一阶段） 2：yellow（二阶段）  3:green（三阶段）  4:cyan(转正) 5:red(失败)-->
				<div class="ywmamber-msg box border pink">
					<!--title-->
					<div class="box-title">
						<i class="icon icon-time"></i>考核中
					</div>
					<div class="box-body">
						<!--ywmamber-body-->
						<div class="ywmamber-body">
							<img width="80" src="/static/img/user-head.png" alt="..."
								class="img-circle">
							<div class="msg-text">
								<h4>${salesman.truename}</h4>
								<p>ID: ${salesman.id}</p>
								<p>电话: ${salesman.mobile}</p>
							</div>
						</div>
						<!--/ywmamber-body-->
						<div class="stage">
							<span class="kaohe-stage onekaohe-stage">第一阶段:60% </span>
						</div>
						<div class="progress progress-sm">
							<div style="width: 60%;" class="progress-bar bar-kaohe"></div>
						</div>
						<div class="operation">
<!-- 							<a href="javascript:;" class="">考核设置</a>  -->
							<a href="javascript:;">辞退</a>
							<a href="javascript:;" class="pull-right">查看</a>
						</div>
						<div class="yw-text">
							入职时间:<span> ${salesman.regdate}</span> <br /> 负责区域: <span>${salesman.region.parent.parent.parent.name}
								${salesman.region.parent.parent.name}
								${salesman.region.parent.name} ${salesman.region.name}</span>
						</div>
						<!--拜访任务-->
						<div class="visit">
							<button class="col-xs-12 btn btn-visit" href="javascript:;">
								<i class="icon icon-add"></i>拜访
							</button>
						</div>
						<!--拜访任务-->
						<!--操作-->
						<div class="operation">
							<a href="javascript:;" class="">账户设置</a> <a href="javascript:;">冻结账户</a>
						</div>
						<!--操作-->
						<!--虚线-->
						<div class="hr"></div>
						<!--虚线-->
						<!--业务外部链接-->
						<div class="yw-link">
							<a class="link-oper" href="javascript:;"><i
								class="icon icon-user"></i>个人资料</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-income"></i>收益</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-task"></i>任务</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-log"></i>日志</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-footprint"></i>足迹</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-signin"></i>签收记录</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-saojie"></i>扫街记录</a>
						</div>

					</div>
				</div>
				<!--/team-map-->
			</div>
		</div>
		<!-- Bootstrap core JavaScript================================================== -->
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
		<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
		<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
		<script src="<%=basePath%>static/js/dateutil.js"></script>
		<script src="<%=basePath%>static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>static/kaohe/kaohe-det.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>static/js/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
		<script type="text/javascript">
			$('body input').val('');
			$(".form_datetime").datetimepicker({
				format : "yyyy-mm-dd",
				language : 'zh-CN',
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2,
				pickerPosition : "bottom-right",
				forceParse : 0
			});
			var $_haohe_plan = $('.J_kaohebar').width();
			var $_haohe_planw = $('.J_kaohebar_parents').width();
			$(".J_btn").attr("disabled", 'disabled');
			if ($_haohe_planw === $_haohe_plan) {
				$(".J_btn").removeAttr("disabled");
			}
		</script>
</body>

</html>
</html>
