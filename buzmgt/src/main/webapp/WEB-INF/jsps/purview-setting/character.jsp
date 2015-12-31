<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>角色查看</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/index.css" />
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/purview-setting/character.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<%@ include file="../top.jsp"%>
	<div class="container-fluid">
		<div id="" class="row">
			<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
				<%@include file="../left.jsp"%>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
				<div id="j_page_main" class="content main">
					<h4 class="page-header">
						<i class="icon role-check-icon"></i>角色查看
						</h1>
						<div class="row">
							<div class="col-md-12">
								<div class="character  box border green">
									<div class="box-title">
										<i class="icon role-icon"></i>区域总监
									</div>
									<div class="box-body">
										<table class="table">
											<tr>
												<th>匹配人员</th>
												<th>操作</th>
											</tr>
											<tr>
												<td>坦陈</td>
												<td><a href="" data-toggle="modal"
													data-target="#gridSystemModal" data-whatever="@mdo">取消配置</a></td>
											</tr>
											<tr>
												<td>坦陈</td>
												<td><a href="" data-toggle="modal"
													data-target="#gridSystemModal">取消配置</a></td>
											</tr>
											<tr>
												<td>坦陈</td>
												<td><a href="" data-toggle="modal"
													data-target="#gridSystemModal">取消配置</a></td>
											</tr>
											<tr>
												<td>坦陈</td>
												<td><a href="" data-toggle="modal"
													data-target="#gridSystemModal">取消配置</a></td>
											</tr>
										</table>
										<div class="remark">
											<div class="remark-title">备注</div>
											<div class="remark-body">此权限适用于总监以上级别的人员</div>
										</div>
									</div>
								</div>
								<!-- alert html -->
								<div id="gridSystemModal" class="modal fade" role="dialog"
									aria-labelledby="gridSystemModalLabel">
									<div class="modal-dialog " role="document">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal"
													aria-label="Close">
													<span aria-hidden="true">&times;</span>
												</button>
												<h4 class="modal-title" id="gridSystemModalLabel">警示</h4>
											</div>
											<div class="modal-body">
												<div class="container-fluid">
													<div class="row">
														<div class="col-md-12">如若取消此配置，此业务将失去雀舌对应的权限！</div>
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-success caution"
													data-dismiss="modal">不取消</button>
												<button type="button" class="btn btn-danger">确定取消</button>
											</div>
										</div>
									</div>
								</div>
								<!-- /alert html -->
							</div>
						</div>
				</div>
				<!-- row end -->
			</div>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="static/js/jquery/jquery-1.11.3.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
		type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript"
		src="static/purview_setting/purview_setting.js"></script>
</body>

</html>