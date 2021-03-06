<%@ page language="java"
	import="java.util.*,com.wangge.buzmgt.saojie.entity.*,com.wangge.buzmgt.region.entity.*,com.wangge.buzmgt.teammember.entity.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>考核设置</title>
<!-- Bootstrap -->
<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="/static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/kaohe/kaohe-set.css" />
<script src="/static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
</head>

<body>
	<div class="content main">
		<h4 class="team-member-header page-header ">
			<i class="icon icon-add-set"></i>添加设置
		</h4>
		<div class="row">
			<div class="col-md-12">
				<!--box-->
				<div class="saojie-upd-body box border blue">
					<!--title-->
					<div class="box-title">
						<h4>设置</h4>
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body form-horizontal">
						<!--内容-->
						<div class="form-group">
							<label class="col-sm-2 control-label">考核人:</label>
							<div class="col-sm-9" data-aa="${salesman.id}" id="salId">
								<p class="form-control-static">${salesman.truename }</p>
							</div>
						</div>
						<div class="hr"></div>
						<div class="form-group">
							<label class="col-sm-2 control-label">负责区域:</label>
							<div class="col-sm-9">
								<p class="form-control-static">${salesman.region.parent.parent.parent.name}
									${salesman.region.parent.parent.name}
									${salesman.region.parent.name} ${salesman.region.name}</p>
							</div>
						</div>
						<div class="hr"></div>
						<div class="form-group  marg-b-10">
							<label class="col-sm-2 control-label">考核设置:</label>
						</div>
						<!--一阶段考核设置内容-->
						<div class="form-group">
							<div class="saojie-upd-list col-sm-9  col-sm-offset-2 col-xs-12">
								<!--list-->
								<div class="table-responsive">
									<table class="table table-bordered kaoheset-stage">
										<c:if test="${not empty list}">
										<thead>
											<tr>
												<th>考核区域</th>
												<th>考核指标</th>
												<th>完成情况</th>
											</tr>
										</thead>
										<tbody>
												<c:forEach var="assess" items="${list}" varStatus="s">
													<tr>
														<td class="">
															<div class="col-sm-8">
																<div class="form-group">
																	<label class="">第${assess.assessStage }阶段:</label>
																	<label class="" id="regionlbl">${assess.regionName }</label>
																</div>
															</div>

														</td>
														<td class="target form-inline">
															<div class="col-sm-5">
																<div class="form-group">
																	<label class="">活跃数:</label> <label class="">${assess.assessActivenum }</label>
																	<label class="">家</label>
																</div>
															</div>
															<div class="col-sm-7">
																<div class="form-group">
																	<label class="">提货量:</label> <label class="">${assess.assessOrdernum }</label>
																	<label class="">部</label>
																</div>
															</div>
															
														</td>
														<td class="target form-inline">
															<div class="col-sm-5">
																<div class="form-group">
																	<label class="">活跃数</label> <label class="">${assess.activeNum }</label>
																	<label class="">家</label>
																</div>
															</div>
															<div class="col-sm-7">
																<div class="form-group">
																	<label class="">提货量</label> <label class="">${assess.orderNum }</label>
																	<label class="">部</label>
																	<c:if test="${assess.status eq 'AGREE' }">
																	<span class="tag-tg">通过</span>
																	</c:if>
																</div>
															</div>
														</td>
													</tr>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
									<!--/table-->
								</div>
								<!--/list-->
							</div>
						</div>
						<!--一阶段考核设置内容-->
						<div class="hr"></div>
						<c:if test="${stage lt assessStageSum}">
						<!--考核内容-->
							<div class="form-group  marg-b-10">
								<label class="col-sm-2 control-label">第${stage + 1}阶段考核设置:</label>
							</div>
							<form class="member-from-box form-horizontal"
								action="/assess/saveAssess/${salesman.id}?stage=+${stage+1 }+"
								name="form" method="post" onsubmit="javascript:return checkForm()">
								<div class="form-group">
									<div
										class="saojie-upd-list col-sm-9  col-sm-offset-2 col-xs-12">
										<input type="hidden" id="salesmanId" name="salesmanId"
											value="${salesman.id}" />
										<!--list-->
										<div class="table-responsive">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th>考核区域</th>
														<th>考核指标</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td class="average-tr ">

															<div class="col-sm-4">
																<a
																	class="J_addDire btn btn-default btn-kaohe-add col-sm-6"
																	href="javascript:;" id="btn"><i
																	class="icon-saojie-add"></i></a>
															</div> 
															<label class="pull-right col-md-6 control-label msg-error">请选择考核区域</label>
														</td>
														<td class="average-tr target form-inline">
															<div class="col-sm-5">
																<div class="form-group">
																	<label class="">活跃数</label> <input
																		class="form-control input-sm" type=""
																		name="assessActivenum" id="assessActivenum" value="" /> <label
																		class="">家</label>
																</div>
																<label class="pull-right col-md-8 control-label msg-error">请填写活跃数</label>
															</div>
															<div class="col-sm-5">
																<div class="form-group">
																	<label class="">提货量</label> <input
																		class="form-control input-sm" type=""
																		name="assessOrdernum" id="assessOrdernum" value="" /> <label
																		class="">部</label>
																</div>
																<label class="pull-right col-md-8 control-label msg-error">请填写提货量</label>
															</div>
														</td>
													</tr>
												</tbody>
											</table>
											<!--/table-->
										</div>
										<!--/list-->
									</div>
								</div>
								<!--考核设置内容-->

								<!--考核内容-->
								<!--分隔,清除浮动-->
								<div class="clearfix"></div>
								<div class="hr"></div>
								<!--考核周期-->
								<div class="form-group kaohe-week">
									<label class="col-sm-2 control-label">考核阶段周期:</label>
									<div class="input-group col-sm-2">
										<span class="input-group-addon" id="basic-addon1"><i
											class="ico icon-kaohe-week"></i></span> <input type="text"
											class="form-control"id="assessCycle" aria-describedby="basic-addon1"
											name="assessCycle" value=""> <span
											class="input-group-addon">天</span>
									</div>
									<label class="pull-right col-md-6 control-label msg-error">请填写考核周期</label>
								</div>
								<!--考核周期-->
								<div class="hr"></div>
								<!--考核开始时间-->
								<div class="form-group kaohe-week-start">
									<label class="col-sm-2 control-label">考核开始时间:</label>
									<div class="input-group col-sm-2">
										<span class="input-group-addon" id="basic-addon1"><i
											class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
											type="text" id="assessTime"  class="form-control form_datetime"
											readonly="readonly" name="assessTime" value="">
									</div>
								</div>
								<!--考核开始时间-->
								<div class="hr"></div>
								<div class="form-group">
									<div class="col-sm-9 col-sm-offset-2 input-group">
										<button class="btn btn-audit col-sm-2" type="submit">确定</button>
									</c:if>
										<c:if test="${salesman.status ne 'weihu' }">
											<c:if test="${stage eq assessStageSum}">
											<button id="passed" class="btn btn-audit col-sm-2" style="margin-left:150px" onclick="passed('${salesman.id}');">转正</button>
										</c:if>
										</c:if>
										<c:if test="${salesman.status eq 'weihu' }">
											<button id="passed" class="btn btn-audit col-sm-2" style="margin-left:150px" disabled="disabled">已转正</button>
										</c:if>
									</div>
								</div>
								<div class="clearfix"></div>

							</form>
							<!--考核内容-->

							<!--分隔,清除浮动-->
							<div class="clearfix"></div>
							<!--/内容-->
					</div>
					<!--/box-body-->
				</div>
				<!--/box-->
			</div>
			<!--col-->
		</div>
	</div>
	<!--row-->
	<!-- Bootstrap core JavaScript================================================== -->
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="/static/js/jquery/jquery-1.11.3.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="/static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="/static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="/static/kaohe/kaohe-stage.js" type="text/javascript"
		charset="utf-8"></script>
	<script src="/static/js/common.js" type="text/javascript"
		charset="utf-8"></script>
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
			pickerPosition : "bottom-left",
			forceParse : 0
		});
	</script>
</body>

</html>