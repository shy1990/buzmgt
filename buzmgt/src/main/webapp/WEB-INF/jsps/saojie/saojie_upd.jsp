<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>考核设置</title>
		<!-- Bootstrap -->
		<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="static/saojie/saojie-upd.css" />
		<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
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
								<div class="col-sm-10">
									<p class="form-control-static">易小星</p>
								</div>
							</div>
							<div class="hr"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">负责区域:</label>
								<div class="col-sm-10">
									<p class="form-control-static">山东省泰安市邹平县大桥镇</p>
								</div>
							</div>
							<div class="hr"></div>
							<div class="form-group  marg-b-10">
								<label class="col-sm-2 control-label">考核设置:</label>
							</div>
							<!--考核设置内容-->
							<div class="form-group">
								<div class="saojie-upd-list col-sm-10  col-sm-offset-2 col-xs-12">
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
															<select class="form-control" name="">
																<option value="">景德镇</option>
																<option value="">山炮镇</option>
																<option value="">大桥镇</option>
																<option value="">东湖镇</option>
															</select>
														</div>
														<div class="col-sm-4">
															<select class="form-control" name="">
																<option value="">景德镇</option>
																<option value="">山炮镇</option>
																<option value="">大桥镇</option>
																<option value="">东湖镇</option>
															</select>
														</div>

														<div class="col-sm-4">
															<a class="J_addDire btn btn-default btn-kaohe-add col-sm-6" href="javascript:;"><i class="icon-saojie-add"></i></a>
														</div>

													</td>
													<td class="average-tr target form-inline">
														<div class="col-sm-5">
															<div class="form-group">
																<label class="">活跃数</label>
																<input class="form-control input-sm" type="" name="" id="" value="" />
																<label class="">家</label>
															</div>
														</div>
														<div class="col-sm-5">
															<div class="form-group">
																<label class="">提货量</label>
																<input class="form-control input-sm" type="" name="" id="" value="" />
																<label class="">部</label>
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
									<span class="input-group-addon" id="basic-addon1"><i class="ico icon-kaohe-week"></i></span>
									<input type="text" class="form-control" aria-describedby="basic-addon1">
									<span class="input-group-addon">天</span>
								</div>
							</div>
							<!--考核周期-->
							<div class="hr"></div>
							<!--考核开始时间-->
							<div class="form-group kaohe-week-start">
								<label class="col-sm-2 control-label">考核开始时间:</label>
								<div class="input-group col-sm-2">
									<span class="input-group-addon" id="basic-addon1"><i class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
									<input type="text" class="form-control form_datetime" readonly="readonly">
								</div>
							</div>
							<!--考核开始时间-->
							<div class="hr"></div>
							<div class="form-group">
								<div class="col-sm-10 col-sm-offset-2 input-group">
									<button class="btn btn-audit col-sm-2">确定</button>
								</div>
							</div>
							<div class="hr"></div>
							<!--扫街设置地图-->
							<div class="saojie-set-map col-sm-10  col-sm-offset-1 col-xs-12">
								<div class="map-box ">
									<img style="width: 100%;" src="static/img/background/saojie-set-map.png" />
								</div>
							</div>
							<!--/扫街设置地图-->
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
		<script src="static/js/jquery/jquery-1.11.3.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script type="text/javascript">
			$('.J_addDire').click(function() {
				var dirHtml = '<div class="col-sm-4">' +
					'<select class="form-control" name="">' +
					'<option value="">景德镇</option>' +
					'<option value="">山炮镇</option>' +
					'<option value="">大桥镇</option>' +
					'<option value="">东湖镇</option>' +
					'</select>' +
					'</div>';
				$(this).parents('.col-sm-4').before(dirHtml);
			});
			$('body input').val('');
			$(".form_datetime").datetimepicker({
				format: "yyyy-mm-dd",
				language: 'zh-CN',
				weekStart: 1,
				todayBtn: 1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2,
				pickerPosition: "bottom-left",
				forceParse: 0
			});
		</script>
	</body>

</html>