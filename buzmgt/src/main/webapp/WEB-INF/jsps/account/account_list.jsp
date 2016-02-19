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
		<link rel="stylesheet" type="text/css" href="static/yw-team-member/team-member.css" />
		<link rel="stylesheet" type="text/css" href="static/account-manage/account-list.css" />
		<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<div class="content main">
			<h4 class="page-header">
				<i class="ico ico-account-manage"></i>账号管理
				<!--区域选择按钮-->
				<div class="area-choose">
					选择区域：<span>山东省</span> 
					<a class="are-line" href="javascript:;" onclick="">切换</a>
				</div>
				<!--/区域选择按钮-->
			</h4>
			<div class="row">
				<div class="col-md-12">
					<div class="account-list  box border blue">
						<!-- box-title -->
						<div class="box-title">
							<div class="row">
								<div class="col-sm-12">
									<!--菜单栏-->
									<ul class="nav nav-tabs">
										<li class="active"><a href="#box_tab1" data-toggle="tab"><span class="hidden-inline-mobile">正在使用</span></a></li>
										<li><a href="#box_tab2" data-toggle="tab"><span class="hidden-inline-mobile">已辞退</span></a></li>
									</ul>
									<!--/菜单栏-->
								</div>
							</div>
						</div>
						<!-- box-title -->
						<!--box-body-->
						<div class="box-body tab-content">
							<div class="tab-pane fade in active" id="box_tab1">
								<div class="hr-solid-sm"></div>	
							<ul class="nav nav-job">
								<li class="active"><a href="javascript:;">全部</a></li>
								<li><a href="javascript:;">区域经理</a></li>
								<li><a href="javascript:;">大区经理</a></li>
								<li><a href="javascript:;">服务张经理</a></li>
							</ul>
							<script type="text/javascript">
								$('.nav-job li').click(function(){
									$(this).addClass('active');
									$(this).siblings('li').removeClass('active');
								});
							</script>
							<div class="hr-solid-sm"></div>
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
									<td class="operation">
										<a href="" data-toggle="modal" data-target="#resetPwdModal">重置密码</a>
										<a href="">修改资料</a>
										<a class="text-danger" href="" data-toggle="modal" data-target="#gridSystemModal">辞退</a>
										
									</td>
								</tr>
								<tr>
									<td>区域经理</td>
									<td>18456445446</td>
									<td>周星星</td>
									<td>大东北</td>
									<td>区域经理</td>
									<td>
										<div class="switch switch-small"  data-on="info" data-off="success">
											<input type="checkbox" autocomplete="off" name="my-checkbox" id="accountStatus"  value="" 
												data-on-color="info" data-off-color="success" data-size="mini" data-on-text="冻结"data-off-text="正常" />
										</div>
									</td>
									<td class="operation">
										<a href="" data-toggle="modal" data-target="#resetPwdModal">重置密码</a>
										<a href="">修改资料</a>
										<a class="text-danger" href="" data-toggle="modal" data-target="#gridSystemModal">辞退</a>
										
									</td>
								</tr>
							</table>
							</div>
							<div class="tab-pane fade" id="box_tab2">
								<table class="table table-hover">
									<tr>
										<th>职务</th>
										<th>账号</th>
										<th>姓名</th>
										<th>负责区域</th>
										<th>角色权限</th>
										<th>操作</th>
									</tr>
									<tr>
										<td>区域经理</td>
										<td>18456445446</td>
										<td>周星星</td>
										<td>大东北</td>
										<td>区域经理</td>
										<td class="operation">
											<a href="" data-toggle="modal" data-target="">恢复</a>
											<a class="text-danger" href="" data-toggle="modal" data-target="#deleteAccountModal">删除账号</a>
										</td>
									</tr>
									<tr>
										<td>区域经理</td>
										<td>18456445446</td>
										<td>周星星</td>
										<td>大东北</td>
										<td>区域经理</td>
										<td class="operation">
											<a href="" data-toggle="modal" data-target="">恢复</a>
											<a class="text-danger" href="" data-toggle="modal" data-target="#deleteAccountModal">删除账号</a>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<!-- box-body -->
					</div>
					<!--box-->
					<!-- alert html -->
					<div id="gridSystemModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
						<div class="modal-dialog " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									<h3 class="modal-title" id="gridSystemModalLabel">辞退</h4>
								</div>
								<div class="modal-body">
									<div class="container-fluid">
										<div class="row">
											<div class="col-md-12">
												<h4 class="text-danger">您确定要辞退该员工么？</h4>
												<p>1.若辞退该员工，2日后该员工将无法正常使用APP。</p>
												<p>2.区域经理请确保该员工无拖欠贷款，以及辞退后该区域的配送。</p>
												<p>3.辞退后该信息将会推送至人资，请做好月该员工的交接工作。</p>
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger" data-dismiss="modal">确定辞退</button>
									<button type="button" class="btn btn-success caution" data-dismiss="modal">稍后再说</button>
								</div>
							</div>
						</div>
					</div>
					<!-- /alert html -->
					<!-- alert html -->
					<div id="resetPwdModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
						<div class="modal-dialog " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									<h3 class="modal-title" id="gridSystemModalLabel">重置密码</h4>
								</div>
								<div class="modal-body">
									<div class="container-fluid">
										<div class="row">
											<div class="col-md-12">
												重置密码后将恢复初始状态！
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-success caution" data-dismiss="modal">取消</button>
									<button type="button" class="btn btn-danger" data-dismiss="modal">确定</button>
								</div>
							</div>
						</div>
					</div>
					<!-- /alert html -->
					<!-- alert html -->
					<div id="deleteAccountModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
						<div class="modal-dialog " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									<h3 class="modal-title" id="gridSystemModalLabel">提示</h4>
								</div>
								<div class="modal-body">
									<div class="container-fluid">
										<div class="row">
											<div class="col-md-12">
												您确定要删除此用户吗？删除后此用户的信息将不在保留！
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-success caution" data-dismiss="modal">不删除</button>
									<button type="button" class="btn btn-danger" data-dismiss="modal">确定删除</button>
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
  if(state){
  	alert('冻结');
  }else{
  	alert('正常');
  }
});
		</script>
	</body>

</html>