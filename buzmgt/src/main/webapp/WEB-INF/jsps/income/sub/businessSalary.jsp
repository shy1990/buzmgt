<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<base href="<%=basePath%>" />
<title>业务佣金明细</title>
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/kaohe/kaohe-det.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/ywmember.css" />
<link href="static/bootStrapPager/css/page.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/static/incomeCash/css/income-cash.css">
<link rel="stylesheet" type="text/css"
	href="/static/month-task/findup-month-task.css">
<link rel="stylesheet" type="text/css"
	href="/static/abnormal/abnormal.css" />
<link rel="stylesheet" type="text/css" href="/static/income/income.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/static/income/busines.css" />
<script id="task-table-template" type="text/x-handlebars-template">
{{#if content}}
	{{#each content}}
		<tr>
											<td>{{addOne @index}}</td>
											<td>{{namepath}}</td>
											<td>{{shopName}}</td>
											<td><a href="mainIncome/detail/{{orderno}}">{{orderno}}</a></td>
											<td>{{phoneCount}}</td>
											<td><span class="text-{{adaptOS  orderStatus}} text-strong">{{orderStatus}}</span><span
												class="text-gery-s">{{orderPayType}}</span></td>
											<td><span class="ph-{{adaptRed payStatus}}">{{payStatus}}</span></td>
											<td><span class="text-reda">{{income}}</span></td>
											<td>{{createTime}}</td>
										</tr>		
	{{/each}}
{{/if}}

</script>
<style>
</style>
</head>
<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-grtc"></i>佣金明细
			 <a href="javascript:history.back();"><i
				class="ico icon-back fl-right"></i></a>
		</h4>
		</h4>


		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div>
					<!--title-->
					<div class="clearfix">
						<div class=" inform">
							<div class="row">
								<div class="col-sm-4 product">
									<span class="text-strong text-blue">${mainIncome.month}月佣金
										&nbsp;</span> <span class="text-cheng">注：</span> <span
										class="text-grey-s">此统计数值为价格区间计算收益的产品，且属于已付款状态。</span>
								</div>
							</div>

							<hr class="hr-l">

							<div class="row">
								<div class="col-sm-3 info-zq">
									<img src="static/phone-set/img/pic1.png" alt="" class="fl"
										style="margin-right: 15px;">
									<p class="text-gery text-strong font-w">周期销量</p>
									<p class="text-lv text-16 text-strong">${mainIncome.busiNums}台</p>
								</div>
								<div class="col-sm-3 info-zq">
									<img src="static/phone-set/img/pic-2.png" alt="" class="fl"
										style="margin-right: 15px;">
									<p class=" text-strong text-gery  font-w ">退货冲减</p>
									<p class="text-jv text-16">-${mainIncome.busiShouNums}台</p>
								</div>
								<div class="col-sm-3 info-zq">
									<img src="static/phone-set/img/pic3.png" alt="" class="fl"
										style="margin-right: 15px;">
									<p class="text-gery   font-w text-strong">实际销量</p>
									<p class="text-gren text-16">${mainIncome.busiNums-mainIncome.busiShouNums}台</p>
								</div>
								<div class="col-sm-3 info-zq">
									<img src="static/phone-set/img/pic4.png" alt="" class="fl"
										style="margin-right: 15px;">
									<p class="text-gery   font-w text-strong">预计收益</p>
									<p class="text-lan text-16">${mainIncome.rbusiSal}元</p>
								</div>
							</div>

						</div>

						<div class="inform-box">
							<div class="row">
								<div class="col-sm-4">
									<span class="text-gery text-strong">选择区域：</span> <select
										class="selectpicker show-tick" id='regionId'>
										<option value="">全部乡镇</option>
										<c:forEach var='region' items='${regions}'>
											<option value="${region.id}">${region.name}</option>
										</c:forEach>
									</select>
								</div>
								<div class="col-sm-4">
									<span class="text-gery text-strong">选择日期：</span>
									<div class="search-date">
										<div class="input-group input-group-sm" style="width: 220px">
											<span class="input-group-addon " id="basic-addon1"><i
												class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
											<input type="text" id="cmonth"
												class="form-control form_datetime input-sm"
												placeholder="请选择年-月" readonly="readonly">
										</div>
									</div>
								</div>
								<div class="col-sm-4">
									<span class="text-gery text-strong">收益类型：</span> <select
										id="incomeType" class="selectpicker show-tick  ">
										<option value=''>全部</option>
										<option value='1'>实时收益</option>
										<option value='0'>预计收益</option>
									</select>
								</div>
							</div>
							<div class="row" style="margin-top: 20px">
								<div class="col-sm-4">
									<span class="text-gery text-strong">支付状况：</span> <select
										class="selectpicker show-tick" id="payStatus">
										<option value="">全部</option>
										<option value='0'>未付款</option>
										<option value='1'>已付款</option>
										<option value='2'>待退款</option>
										<option value='3'>已退款</option>
										<option value='4'>卖家拒绝退款</option>
									</select>
								</div>
								<div class="col-sm-4">
									<span class="text-gery text-strong">签收状态：</span> <select
										class="selectpicker show-tick  " id="orderStatus">
										<option value="">全部</option>
										<option value="0">订单成功</option>
										<option value="1">订单取消</option>
										<option value="2">业务签收</option>
										<option value="3">客户签收</option>
										<option value="4">客户拒收</option>
									</select>
								</div>

								<div class="col-sm-4">
									<button class="btn btn-blue  col-sm-5"
										onclick="findMainPlanList(0);">确定</button>
								</div>
							</div>
							<hr>
							<span class="text-hei">佣金共计：</span> <span class="text-red">${mainIncome.rbusiSal}元</span>

							<div class="d-daochu" style="margin-top: 1px">
								<div class="salesman">
									<input class="cs-select  text-gery-hs" id="orderno" placeholder="请输入订单号" />
									<button class="btn btn-blue btn-sm" onclick="findMainPlanList(0)">
										检索</button>
								</div>
								<div class="link-posit-t pull-right export">
									<a class="table-export" href="javascript:void(0);" onclick="exportExcel();">导出excel</a>
								</div>
							</div>
						</div>
					</div>

					<div class="box-body-new">
						<!--列表内容-->
						<div class="tab-content" style="margin-top: -10px">
							<!--油补记录-->
							<div class="tab-pane fade in active">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table class="table table-hover new-table">
										<thead>
											<tr>
												<th>序号</th>
												<th>业务区域</th>
												<th>店铺名称</th>
												<th>订单编号</th>
												<th>台数</th>
												<th>签收状态</th>
												<th>付款状态</th>
												<th>佣金</th>
												<th>下单日期</th>
											</tr>
										</thead>
										<tbody id='userList'>
										</tbody>
									</table>

								</div>
								<!--table-box-->
							</div>
							<!--油补记录-->
						</div>
						<!--列表内容-->
						<!--分页-->
						<div class="page-index" id="usersPager"></div>
						<!--分页-->
					</div>
				</div>

				<!--box-->
			</div>
			<!--col-md-9-->
			<div class="col-md-3">
				<!--box-->
				<%@ include file="../../kaohe/right_member_det.jsp"%>
			</div>
		</div>
	</div>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="/static/js/common.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script src='/static/js/dateutil.js'></script>
	<script src="/static/income/main/business.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript">
		var	base="<%=basePath%>";
		var userId = "${mainIncome.salesman.id}";
		var month = "${mainIncome.month}";
		$(".form_datetime").datetimepicker({
			format : "yyyy-mm",
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 3,
			minView : 3,
			pickerPosition : "bottom-left",
			forceParse : 0,

		});
		$("#cmonth").val(month);
		$(function() {
			findMainPlanList(0);
		});
	</script>

</body>

</html>