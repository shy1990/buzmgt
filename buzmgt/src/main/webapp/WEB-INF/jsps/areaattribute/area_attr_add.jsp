<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>" />
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>添加提成区域属性</title>

	<link rel="stylesheet" href="static/bootstrap/css/bootstrap.css">
	<link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
	<link rel="stylesheet" href="static/multiselect/css/jquery.multiselect.css">
	<link rel="stylesheet" type="text/css" href="static/css/common.css">
	<link rel="stylesheet" href="static/phone-set/css/phone.css">


	<script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	<style>
		.form-control[readonly], fieldset[disabled] .form-control {
			background-color: #ffffff;
		}
		ul li {
			list-style-type: none;
		}

		ul {
			column-count: 1;
			column-gap: 0px;
			padding-left: 0px;
		}

		.sj-ms-btn-ok {
			margin-top: 0px;
			margin-bottom: 0px;
			text-align: center;
			background-color: #eee;
			padding-top: 5px;
			border-bottom-width: 0px;
			padding-bottom: 5px;
		}

		.sj-ms-btn-ok > button {
			padding-right: 15px;
			padding-left: 15px;
			background-color: #fff;
			border: 1px solid #aaaaaa;
			color: #555555;
		}


		.text-blue {
			color: #555555;
		}


	</style>

</head>
<body>

<div class="content main">
	<h4 class="page-header">
		<i class="ico ico-ph-tj"></i>添加提成区域属性
		<a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
	</h4>

	<div class="row">
		<div class="col-md-12" style="overflow-x: hidden">
			<div class="order-box">


				<div class=" form-horizontal">
					<!--内容-->

					<div class="form-group" style="margin-top: 40px">
						<label class=" col-sm-2 control-label ph-text-headline">选择添加区域：</label>

						<select class="form-control select-city" name="province">
							<option value="">省</option>
							<option value="">山东省</option>
							<option value="">广东省</option>
							<option value="">浙江省</option>
						</select>

						<select class="form-control select-city" name="city">
							<option value="">市</option>
							<option value="">济南市</option>
							<option value="">售后处理</option>
							<option value="">扣罚</option>
						</select>

						<select class="form-control select-city" name="county">
							<option value="">县</option>
							<option value="">注册用户</option>
							<option value="">售后处理</option>
							<option value="">扣罚</option>
						</select>
					</div>


					<div class="form-group" style="margin-top: 30px">
						<label class="col-sm-2 control-label ph-text-headline">设置提成金额：</label>
						<div class="col-sm-3" style="padding-left: 0px;margin-top: 5px;">
							<input type="text" class="spinner" style="width: 50px;text-align: center" name="tc">
						</div>
						<div class="col-sm-3" style="margin-top: 8px;margin-left: -250px;">
							<span class="text-gery text-strong"> 元/台</span>
						</div>
					</div>


					<%--<div class="form-group" style="margin-top: 40px">
						<label class="col-sm-2 control-label ph-text-headline">方案实施日期：</label>

						<div class="col-sm-3" style="padding-left: 0px;margin-top: -10px">
							<div class="search-date" style="width: 300px">
								<div class="input-group input-group-sm">
                                    <span class="input-group-addon " style="padding-top: 0"><i class="icon-s icon-time"
																							   style=" width: 16px;  height: 14px;"></i></span>
									<input type="text" class="form-control form_datetime input-sm" placeholder="选择日期"
										   readonly="readonly">
								</div>
							</div>
						</div>
						<div class="col-sm-3" style="margin-top: 8px; margin-left: -50px">
							<span class="text-gery text-strong"> 起</span>
						</div>
					</div>--%>


					<div class="form-group" style="margin-top: 40px">
						<%--<label class="col-sm-2 control-label ph-text-headline">指派审核人员：</label>

						<div class="col-sm-3" style="padding-left: 0px; width: 317px">
							<div class="input-group input-group-sm">
								<span class="input-group-addon "><i class="icon-s icon-man"></i></span>
								<div class="inpt-search">
									<form>
										<select name="basic[]" multiple="multiple" class="form-control demo3">

											<option value="UT">胡老大</option>
											<option value="VT">横额啊</option>
											<option value="VA">张二啦</option>
											<option value="VA">王晓晓</option>
											<option value="WV">杭大大</option>
											<option value="WV">曹大大</option>
											<option value="WI">槽大小</option>
										</select>
									</form>
								</div>
							</div>
						</div>--%>

						<div class="form-group" style="float: left; margin-left: -348px;margin-top: 40px">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-2 input-group ">
								<button class="btn btn-primary ph-true" onclick="addTicheng();">确定</button>
							</div>
						</div>
						<!--分隔,清除浮动-->
						<div class="clearfix"></div>
					</div>

				</div>
				<!--/box-body-->
			</div>

		</div>
	</div>
</div>
</div>


<script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap.js"></script>
<script src="<%=basePath%>js/jquery.spinner.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath%>js/H-select.js"></script>

<script>

	function addTicheng() {

		//收集数据.取到每个输入的值
		opener.addTicheng({sheng: '#province', shi: '#city', xian: '#county', jine: '.spinner'});
//
//        addCustom($("#addd").serializeArray());
		close();
	}
	//
	//    function  addCustom(o) {
	//        var cty =o[0]["value"];
	//        var shi =o[0]["value"];
	//        var xian =o[0]["value"];
	//        var shu=o[1]["value"];
	//        opener.addTicheng({sheng:'cty',shi:'shi',xian:'xian',jine:'shu'});
	//        close();
	//    }


	$('.spinner').spinner();

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

	$('.menu a,.menu li.active a').click(function () {
		$(this).parent('li').toggleClass('active');
		$(this).parent('li').siblings().removeClass('active');
	});

	$('select[multiple].demo3').multiselect({
		columns: 1,
		placeholder: '请选择指派审核人员',
		search: true,
		selectGroup: true
	});


</script>
</body>
</html>

