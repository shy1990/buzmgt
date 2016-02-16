<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>账号管理</title>
		<!-- Bootstrap -->
		<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="static/account-manage/account-list.css" />
		<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<div class="content main">
			<h4 class="page-header">
				<i class="ico ico-account-manage"></i>账号管理
				<!--区域选择按钮-->
				<button type="button" class="btn btn-default" >
					<i class="ico ico-province"></i>山东省
				</button>
				<!--/区域选择按钮-->
			</h4>
			<div class="row">
				<div class="col-md-12">
					<div class="account-list  box border blue">
						<div class="box-title">
							<div class="btn-group btn-group-sm">
								<button type="button" class="btn btn-default"><i class="ico icon-role"></i>区域总监</button>
								<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									<span class="caret"></span>
									<span class="sr-only">Toggle Dropdown</span>
								</button>
								<ul class="dropdown-menu">
									<li><a href="#">Action</a></li>
									<li><a href="#">Another action</a></li>
									<li><a href="#">Something else here</a></li>
									<li role="separator" class="divider"></li>
									<li><a href="#">Separated link</a></li>
								</ul>
							</div>
							
						</div>
						<div class="box-body">
							<table class="table table-hover">
								<tr>
									<th>职务</th>
									<th>账号</th>
									<th>姓名</th>
									<th>负责区域</th>
									<th>角色权限</th>
									<th>账号状态</th>
									<th>操作</th>
								</tr>
								<tr>
									<td>区域经理</td>
									<td>18456445446</td>
									<td>周星星</td>
									<td>大东北</td>
									<td>区域经理</td>
									<td>
										<div class="switch switch-small"  data-on="info" data-off="success">
											<input type="checkbox" checked="checked" autocomplete="off" name="my-checkbox" id="accountStatus"  value="" 
												data-on-color="info" data-off-color="success" data-size="mini" data-on-text="冻结"data-off-text="正常" />
										</div>
									</td>
									<td><a href=""><i class="ico ico-upd"></i> </a><a href="" data-toggle="modal" data-target="#gridSystemModal"><i class="ico ico-delete"></i> </a></td>
								</tr>
								<tr>
									<td>区域经理</td>
									<td>18456445446</td>
									<td>周星星</td>
									<td>大东北</td>
									<td>区域经理</td>
									<td>
										<div class="switch switch-small"  data-on="info" data-off="success">
											<input type="checkbox" checked="checked" autocomplete="off" name="my-checkbox" id="accountStatus"  value="" 
												data-on-color="info" data-off-color="success" data-size="mini" data-on-text="冻结"data-off-text="正常" />
										</div>
									</td>
									<td><a href=""><i class="ico ico-upd"></i> </a><a href="" data-toggle="modal" data-target="#gridSystemModal"><i class="ico ico-delete"></i> </a></td>
								</tr>
							</table>
						</div>
					</div>
					<!-- alert html -->
					<div id="gridSystemModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
						<div class="modal-dialog " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
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
									<button type="button" class="btn btn-success caution" data-dismiss="modal">不取消</button>
									<button type="button" class="btn btn-danger">确定取消</button>
								</div>
							</div>
						</div>
					</div>
					<!-- /alert html -->
				</div>
			</div>
		</div>
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="static/js/jquery/jquery-1.11.3.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
		<script src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$("[name='my-checkbox']").bootstrapSwitch();
			//checkbox点击事件回调函数
			$('input[name="my-checkbox"]').on('switchChange.bootstrapSwitch', function(event, state) {
  console.log(this); // DOM element
  console.log(event); // jQuery event
  console.log(state); // true | false
  if(state){//true-->checked
  	alert('冻结');
  }else{//false-->checked
  	alert('正常');
  }
});
		</script>
	</body>

</html>