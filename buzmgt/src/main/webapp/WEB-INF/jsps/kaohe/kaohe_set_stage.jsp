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
																	<c:if test="${assess.assessStage =='1' }">
																		<label class="">第一阶段:</label>
																	</c:if>
																	<c:if test="${assess.assessStage =='2' }">
																		<label class="">第二阶段:</label>
																	</c:if>
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
																	<label class="">活跃数</label> <label class="">14</label>
																	<label class="">家</label>
																</div>
															</div>
															<div class="col-sm-7">
																<div class="form-group">
																	<label class="">提货量</label> <label class="">100</label>
																	<label class="">部</label>
																	<span class="tag-tg">通过</span>
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

						<!--考核内容-->
						<c:if test="${stage eq 1 or stage eq 2 }">
							<div class="form-group  marg-b-10">
								<c:if test="${stage eq 1 }">
									<label class="col-sm-2 control-label">二阶段考核设置:</label>
								</c:if>
								<c:if test="${stage eq 2 }">
									<label class="col-sm-2 control-label">三阶段考核设置:</label>
								</c:if>
							</div>
							<form class="member-from-box form-horizontal"
								action="/assess/saveAssess/${salesman.id}?stage=+${stage }+"
								name="form" method="post">
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
															</div> <c:if test="${stage eq 1 }">
															</c:if>
														</td>
														<td class="average-tr target form-inline">
															<div class="col-sm-5">
																<div class="form-group">
																	<label class="">活跃数</label> <input
																		class="form-control input-sm" type=""
																		name="assessActivenum" id="" value="" /> <label
																		class="">家</label>
																</div>
															</div>
															<div class="col-sm-5">
																<div class="form-group">
																	<label class="">提货量</label> <input
																		class="form-control input-sm" type=""
																		name="assessOrdernum" id="" value="" /> <label
																		class="">部</label>
																</div>
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
											class="form-control" aria-describedby="basic-addon1"
											name="assessCycle" value=""> <span
											class="input-group-addon">天</span>
									</div>
								</div>
								<!--考核周期-->
								<div class="hr"></div>
								<!--考核开始时间-->
								<div class="form-group kaohe-week-start">
									<label class="col-sm-2 control-label">考核开始时间:</label>
									<div class="input-group col-sm-2">
										<span class="input-group-addon" id="basic-addon1"><i
											class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
											type="text" class="form-control form_datetime"
											readonly="readonly" name="assessTime" value="">
									</div>
								</div>
								<!--考核开始时间-->
								<div class="hr"></div>
								<div class="form-group">
									<div class="col-sm-9 col-sm-offset-2 input-group">
										<button class="btn btn-audit col-sm-2" onclick="toSubmit();">确定</button>
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
					</c:if>
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