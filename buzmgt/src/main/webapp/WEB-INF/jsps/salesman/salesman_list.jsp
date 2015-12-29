<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge">

		<!-- <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet"> -->
       
       
</head>
<body>
	<div id="main">
	      <link rel="stylesheet" href="static/bootstrap/css/bootstrap-multiselect.css" type="text/css"/>
        <link rel="stylesheet" href="static/css/salesman_list.css" type="text/css"/>
        <script src="static/js/jquery/jquery-2.1.4.min.js"></script>
		<script src="static/js/salesman_list.js"></script>
        <script type="text/javascript" src="static/bootstrap/js/bootstrap-multiselect.js"></script>
		<div class="container-fluid">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<!--<table>-->
					<!--<td><img src="img/ salesman.jpg"  class="img-circle"/></td>
				    			<td><img src="img/ salesman.jpg"  class="img-circle"/></td>-->
					<!--<div  class="row">-->
					<div style="margin-top: 30px;margin-bottom: 30px;">
						<span><strong style="font-family: '微软雅黑';font-size:larger;margin-left: 20px;">团队成员</strong></span>
						<span style="margin-left:50px ;"><button class="btn btn-default" type="button">中国</button></span>
						<span style="margin-left:50px ;"><a class="j_team_member_add btn btn-lg btn-default"  href="/toAdd" role="button">+添加成员</a></span>
						<span style="margin-left:50px ;margin-top: 200px;"><small>共 203 位成员     今日新增0+</small></span>
					</div>
					<!--</div>-->
					<!--</table>-->
				</div>
			</div>
			<div class="row">
				<!-- <div class="col-sm-8">-->
				<!--<div class="row">-->
				<div class="col-xs-6 col-md-8">
					<table class="table table-striped table-hover ">
						<tr class="active">
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
							<td>
								<!--<tr>
                              			<td>张明哲 (区域经理)</td>
                              		</tr>
                              		<tr>
                              			<td>山东省滨州市邹城县</td>
                              		</tr>-->
								<table style="margin-top: 5px;">
									<tr>
										<td>张明哲 (区域经理)</td>
									</tr>
									<tr>
										<td>山东省滨州市邹城县</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>提货量：263</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="thl"></span>10%</td>
									</tr>
									<tr>
										<td>活跃度：4.5分</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="hyd">12%</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>业务指标：85%</td>
										<td><span style="background-color: #419641;margin-left: 10px;">扫街完成</span></td>
									</tr>
									<tr>
										<td><span style="width: 20px;background-color: #419641;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>
											<a class="btn btn-default" href="#" role="button"> <i class="glyphicon glyphicon-pencil"></i> 查看</a>
										</td>
										<td>
											<a class="btn btn-default" href="#" role="button"><i class="glyphicon glyphicon-pencil"></i> 编辑</a>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
						</tr>
						<tr class="success">
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>张明哲 (区域经理)</td>
									</tr>
									<tr>
										<td>山东省滨州市邹城县</td>
									</tr>
								</table>
								<!--<img src="img/ salesman.jpg"  class="img-circle"/>-->
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<!--<td>
                              					<ul class="list-inline">
	                              					<li>提货量：263</li>
	                              					<li style="font-family: '微软雅黑';color: #419641;">10%</li>
                              				    </ul>
                              				</td>-->
										<td>提货量：263</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="thl"></span>10%</td>
									</tr>
									<tr>
										<!--<td> 
                              					<ul class="list-inline">
												  <li>活跃度：4.5分</li>
												  <li style="font-family: '微软雅黑';color: #419641;">12%</li>
												</ul>
                              				</td>-->
										<td>活跃度：4.5分</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="hyd">12%</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>业务指标：85%</td>
										<td><span style="background-color: #419641;margin-left: 10px;">扫街完成</span></td>
									</tr>
									<tr>
										<td><span style="width: 20px;background-color: #419641;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>
											<a class="btn btn-default" href="#" role="button"> <i class="glyphicon glyphicon-pencil"></i> 查看</a>
										</td>
										<td>
											<a class="btn btn-default" href="#" role="button"><i class="glyphicon glyphicon-pencil"></i> 编辑</a>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
						</tr>
						<tr class="warning">
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>张明哲 (区域经理)</td>
									</tr>
									<tr>
										<td>山东省滨州市邹城县</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>提货量：263</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="thl"></span>10%</td>
									</tr>
									<tr>
										<td>活跃度：4.5分</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="hyd">12%</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>业务指标：85%</td>
										<td><span style="background-color: #419641;margin-left: 10px;">扫街完成</span></td>
									</tr>
									<tr>
										<td><span style="width: 20px;background-color: #419641;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>
											<a class="btn btn-default" href="#" role="button"> <i class="glyphicon glyphicon-pencil"></i> 查看</a>
										</td>
										<td>
											<a class="btn btn-default" href="#" role="button"><i class="glyphicon glyphicon-pencil"></i> 编辑</a>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
						</tr>
						<tr class="danger">
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>张明哲 (区域经理)</td>
									</tr>
									<tr>
										<td>山东省滨州市邹城县</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>提货量：263</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="thl"></span>10%</td>
									</tr>
									<tr>
										<td>活跃度：4.5分</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="hyd">12%</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>业务指标：85%</td>
										<td><span style="background-color: #419641;margin-left: 10px;">扫街完成</span></td>
									</tr>
									<tr>
										<td><span style="width: 20px;background-color: #419641;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>
											<a class="btn btn-default" href="#" role="button"> <i class="glyphicon glyphicon-pencil"></i> 查看</a>
										</td>
										<td>
											<a class="btn btn-default" href="#" role="button"><i class="glyphicon glyphicon-pencil"></i> 编辑</a>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
						</tr>
						<tr class="info">
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>张明哲 (区域经理)</td>
									</tr>
									<tr>
										<td>山东省滨州市邹城县</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>提货量：263</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="thl"></span>10%</td>
									</tr>
									<tr>
										<td>活跃度：4.5分</td>
										<td style="font-family: '微软雅黑';color: #419641;"><span class="hyd">12%</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>业务指标：85%</td>
										<td><span style="background-color: #419641;margin-left: 10px;">扫街完成</span></td>
									</tr>
									<tr>
										<td><span style="width: 20px;background-color: #419641;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
									</tr>
								</table>
							</td>
							<td>
								<table style="margin-top: 5px;">
									<tr>
										<td>
											<a class="btn btn-default" href="#" role="button"> <i class="glyphicon glyphicon-pencil"></i> 查看</a>
										</td>
										<td>
											<a class="btn btn-default" href="#" role="button"><i class="glyphicon glyphicon-pencil"></i> 编辑</a>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<img src="static/img/salesman.jpg" class="img-circle" />
							</td>
						</tr>
					</table>
				</div>
				<div class="col-xs-6 col-md-4">
					wahahahahah
				</div>
				<!--</div>-->
			</div>
		</div>
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
							&times;
						</button>
						<h4 class="modal-title" id="myModalLabel">添加成员 </h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" role="form">
						<div class="form-group">
						   <label for="firstname" class="col-sm-2 control-label">用户名：</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="firstname" placeholder="请输入名字">
								</div>
							</div>
							<div class="form-group">
								<label for="firstname" class="col-sm-2 control-label">名字：</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="firstname" placeholder="请输入名字">
								</div>
							</div>
							<div class="form-group">
								<label for="disabledSelect" class="col-sm-2 control-label">职务：</label>
								<div class="col-sm-10">
									<select id="disabledSelect" class="form-control">
										<option>职务：</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="disabledSelect" class="col-sm-2 control-label">角色权限：</label>
								<div class="col-sm-10">
									<select id="disabledSelect" class="form-control">
										<option>服务站经理</option>
										<option>禁止选择</option>
										<option>禁止选择</option>
										<option>禁止选择</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="firstname" class="col-sm-2 control-label">工号：</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="firstname" placeholder="请输入名字">
								</div>
							</div>
							<div class="form-group">
								<label for="firstname" class="col-sm-2 control-label">电话：</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="firstname" placeholder="请输入名字">
								</div>
							</div>
							<div class="form-group">
								<label for="disabledSelect" class="col-sm-2 control-label">负责区域：</label>
								<div class="col-sm-10">
									<table>
										<tr>
											<td>
												<select id="provice" class="single-selected" style="width: 105px;height: 30px" onchange="getRegion(this.value,this.id)" >
												       <option value = '' selected='selected'>---请选择---</option>
												       <option value = '370000'>山东省</option>
												</select>
											</td>
											<td>
												<select id="city" class="single-selected" style="width: 105px;height: 30px" onchange="getRegion(this.value,this.id)">
													<option value="1" selected="selected">Option 0</option>
													<option value="1">Option 1</option>
													<option value="2">Option 2</option>
													<!-- Option 3 will be selected in advance ... -->
													<option value="3">Option 3</option>
													<option value="4">Option 4</option>
													<option value="5">Option 5</option>
													<option value="6">Option 6</option>
												</select>
											</td>
											<td>
												<select id="area" class="single-selected" style="width: 105px;height: 30px" onchange="create(this.value,this.id)">
													<option value="1" selected="selected">Option 0</option>
													<option value="1">Option 1</option>
													<option value="2">Option 2</option>
													<!-- Option 3 will be selected in advance ... -->
													<option value="3">Option 3</option>
													<option value="4">Option 4</option>
													<option value="5">Option 5</option>
													<option value="6">Option 6</option>
												</select>
											</td>

										</tr>
									</table>
								</div>
							</div>
							<div class="form-group">
								<label for="disabledTextInput" class="col-sm-2 control-label">
									ID编码：
								</label>
								<div class="col-sm-10">
									<input type="text" id="disabledTextInput" class="form-control" placeholder="禁止输入">
								</div>
							</div>

						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭
						</button>
						<button type="button" class="btn btn-primary">
							提交更改
						</button>
					</div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
</body>
</html>