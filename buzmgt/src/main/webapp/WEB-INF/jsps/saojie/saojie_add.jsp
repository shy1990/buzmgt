<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>扫街添加</title>
<!-- Bootstrap -->
<link href="../static/bootstrap/saojie/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="../static/saojie/saojie.css" />
<link rel="stylesheet"
	href="../static/bootstrap/css/bootstrap-datetimepicker.min.css" />
<script src="../static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
</head>

<body>

	<div class="main">
		<h4 class="page-header">
			<i class="icon team-member-add-icon"></i>添加扫街
			<a href="/saojie/saojieList" class="btn btn-warning member-add-btn"
				type="button"> <i class="icon glyphicon glyphicon-share-alt"></i>
				返回列表
			</a>
		</h4>

		<div class="row">
			<div class="col-md-12">
				<div class="member-add-box box border red">
					<div class="box-title">
						<i class="icon member-add-icon"></i>添加
					</div>
					<div class="box-body">
						<!-- -->
						<!--列表内容-->
						<div class="tab-content">
							<!--全部-->
							<div class="tab-pane fade in active" id="box_tab1">
								<!--box-list-->
								<form class="member-from-box form-horizontal"
									action="/saojie/saveSaojie" name="form" method="post">
									<div class="member-from col-md-8 col-md-offset-1 col-sm-10">
										<div class="form-group">
											<label for="inputPassword" class="col-sm-3 control-label">姓名:</label>
											<div class="input-group col-sm-9 ">
												<span class="input-group-addon"><i
													class="member-icon member-name-icon"></i></span>
												<!--<input type="text" class="form-control" id="inputPassword" placeholder="">-->
												<select class="form-control" id="saojieMan" name="salesman.id"
													onchange="queryTown()">
													<option selected="selected" value="">待扫街人员</option>
												</select>
												<!-- <input type="hidden" class="form-control" id="pid" value="" name="pid"/> -->
											</div>
										</div>

										<div class="form-group">
											<label class="col-sm-3 col-xs-12 control-label">顺序:</label>
											<div class="col-sm-9" style="padding: 0;" id="pid">
												<div class="col-sm-6 col-xs-4 p-t" id="btType">
													<button id="btn" class="btn btn-white btn-w-m" text="点击"
														type="button" onclick="AddOrder(btType)"><img alt="添加" src="../static/img/saojie/tianjia2.png"></button>
												</div>
											</div>
										</div>
										<div class="form-group">
											<label for="dtp_input2" class="col-sm-3 control-label">时间:</label>
											<div style="padding: 0;" class="col-sm-9">
												<div class="input-group date form_date col-sm-6"
													data-date="" data-date-format="yyyy-mm-dd"
													data-link-field="dtp_input2" data-link-format="yyyy-mm-dd"
													id="starttime">
													<span class="input-group-addon" id="basic-addon1"><i
														class="member-icon saojie-time-icon"></i></span> <input
														class="form-control" size="16" type="text"
														name="beginTime" value="" readonly placeholder="请选择开始时间" />
													<span class="input-group-addon"><span
														class="glyphicon glyphicon-remove"></span></span> <span
														class="input-group-addon"><span
														class="glyphicon glyphicon-calendar"></span></span> <input
														type="hidden" id="dtp_input2" value="" /> <br />
												</div>
												<div class="input-group date form_date col-sm-6"
													data-date="" data-date-format="yyyy-mm-dd"
													data-link-field="dtp_input2" data-link-format="yyyy-mm-dd"
													id="endtime">
													<span class="input-group-addon" id="basic-addon1"><i
														class="member-icon saojie-time-icon"></i></span> <input
														class="form-control" size="16" type="text"
														name="expiredTime" value="" readonly placeholder="请选择结束时间" />
													<span class="input-group-addon"><span
														class="glyphicon glyphicon-remove"></span></span> <span
														class="input-group-addon"><span
														class="glyphicon glyphicon-calendar"></span></span> <input
														type="hidden" id="dtp_input2" value="" /> <br />
												</div>
											</div>
										</div>
									</div>
									<div class="member-from col-md-8 col-md-offset-2 col-sm-10">
										<div class="col-sm-11">
											<div class="box-map" id="allmap"></div>
										</div>
									</div>
									<div class="form-group">
										<div class="input-group col-sm-10 col-sm-offset-4">
											<button class="btn btn-danger col-sm-3"
												　onclick="toSubmit();">确定保存</button>
										</div>
									</div>
								</form>
							</div>
							<!--/box-list-->
						</div>
					</div>
					<!--/列表内容-->
					<!--/ -->
				</div>
			</div>
		</div>
	</div>
	<!-- /main -->

	<script src="../static/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="../static/bootstrap/js/bootstrap-datetimepicker.js"
		charset="UTF-8"></script>
	<script type="text/javascript"
		src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"
		charset="UTF-8"></script>
	<script src="../static/js/saojie/saojie.js" type="text/javascript"
		charset="UTF-8"></script>
	<script type="text/javascript">
		// 百度地图API功能
		var map = new BMap.Map("allmap");    // 创建Map实例
		map.centerAndZoom("济南", 11);  // 初始化地图,设置中心点坐标和地图级别
		map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
		map.setCurrentCity("济南");          // 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
	</script>
</body>
</html>
