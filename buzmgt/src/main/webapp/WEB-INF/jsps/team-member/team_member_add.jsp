<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>团队成员添加</title>
		<!-- Bootstrap -->
		<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="static/css/index.css" />
		<link rel="stylesheet" type="text/css" href="static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="static/yw-team-member/team-member.css" />
		<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<div id="j_page_mian" class="content main">
			<h4 class="page-header"><i class="icon team-member-add-icon"></i>添加成员</h1>
			<div class="row">
		    	<div class="col-md-12">
			  		<div class="member-add-box box border orange">
			  			<div class="box-title"><i class="icon member-add-icon"></i>添加</div>
			  			<div class="box-body">
			  				<!-- -->
			  				<div class="member-from col-md-6 col-md-offset-2 col-sm-10">
			  					<form class="member-from-box form-horizontal">
								    <div class="form-group">
								    	<label class="col-sm-3 control-label">姓名:</label>
								    	<div class="input-group col-sm-9">
										    <span class="input-group-addon" id="basic-addon1"><i class="member-icon member-name-icon"></i></span>
										    <input type="text" class="form-control" placeholder="请填写个人名称">
										</div>
								    </div>
								    <div class="form-group">
								    	<label for="inputPassword" class="col-sm-3 control-label">职务:</label>
								    	<div class="input-group col-sm-9 ">
								    		<span class="input-group-addon"><i class="member-icon member-job-icon"></i></span>
								      		<!--<input type="text" class="form-control" id="inputPassword" placeholder="">-->
								      		<select class="form-control" name="job">
											    <option selected="selected" value="">请选择</option>
											    <option value="">大区经理</option>
											    <option value="">财务</option>
											    <option value="">业务1</option>
											    <option value="">业务2</option>
											    <option value="">业务3</option>
											</select>	
								    	</div>
								 	</div>
								    <div class="form-group">
								    	<label for="inputPassword" class="col-sm-3 control-label">角色权限:</label>
								    	<div class="input-group col-sm-9 ">
								    		<span class="input-group-addon"><i class="member-icon member-role-icon"></i></span>
								      		<!--<input type="text" class="form-control" id="inputPassword" placeholder="">-->
								      		<select class="form-control" name="job">
											    <option selected="selected" value="">请选择</option>
											    <option value="">大区经理</option>
											    <option value="">财务</option>
											    <option value="">业务1</option>
											    <option value="">业务2</option>
											    <option value="">业务3</option>
											</select>	
								    	</div>
								 	</div>
								    <div class="form-group">
								    	<label class="col-sm-3 control-label">工号:</label>
								    	<div class="input-group col-sm-9 ">
										    <span class="input-group-addon" id="basic-addon1"><i class="member-icon job-number-icon"></i></span>
										    <input type="text" class="form-control" placeholder="请填写个人共工号">
										</div>
								    </div>
								    <div class="form-group">
								    	<label class="col-sm-3 control-label">电话:</label>
								    	<div class="input-group col-sm-9 ">
										    <span class="input-group-addon" id="basic-addon1"><i class="member-icon member-phone-icon"></i></span>
										    <input type="text" class="form-control" placeholder="请填写电话号码">
										</div>
								    </div>
								    <div class="form-group">
								    	<label class="col-sm-3 col-xs-12 control-label">负责区域:</label>
								    	<div class="input-group col-sm-3 col-xs-4 ">
										    <span class="input-group-addon" id="basic-addon1"><i class="member-icon member-map-icon"></i></span>
										    <select class="form-control" name="job">
										    	<div class="">
												    <option value="">大区经理</option>
												    <option value="">财务</option>
												    <option value="">业务1</option>
												    <option value="">业务2</option>
												    <option value="">业务3</option>
												    <option selected="selected" value="">请选择</option>
										    	</div>
											</select>
										</div>
								    	<div class="col-sm-3 col-xs-4 ">
								    		<select class="form-control" name="job">
											    <option selected="selected" value="">请选择</option>
											    <option value="">大区经理</option>
											    <option value="">财务</option>
											    <option value="">业务1</option>
											    <option value="">业务2</option>
											    <option value="">业务3</option>
											</select>
										</div>
								    	<div class="col-sm-3 col-xs-4">
								    		<select class="form-control" name="job">
											    <option selected="selected" value="">请选择</option>
											    <option value="">大区经理</option>
											    <option value="">财务</option>
											    <option value="">业务1</option>
											    <option value="">业务2</option>
											    <option value="">业务3</option>
											</select>
										</div>
								    </div>
								 	<div class="form-group">
								    	<div class="input-group col-sm-9 col-sm-offset-3  ">
								    		<!--负责区域 territory-->
								    		<div class="j_territory territory box border default col-sm-12">
								    			<label><input type="checkbox" name="district"value="all" /><span class="j_district_all district">全部区域</span></label>
								    			<label><input type="checkbox" name="district"value="wz" /><span class="j_district district">乌镇</span></label>
								    			<label><input type="checkbox" name="district"value="wtz" /><span class="j_district district">梧桐镇</span></label>
								    			<label><input type="checkbox" name="district"value="btz" /><span class="j_district district">宝塔镇</span></label>
								    			<label><input type="checkbox" name="district"value="btz" /><span class="j_district district">大宝泥鳅镇</span></label>
								    			<label><input type="checkbox" name="district"value="btz" /><span class="j_district district">大宝泥鳅镇</span></label>
								    			<label><input type="checkbox" name="district"value="btz" /><span class="j_district district">泥潭镇</span></label>
								    			<label><input type="checkbox" name="district"value="btz" /><span class="j_district district">乌鸦镇</span></label>
								    		</div>
								    		<!--/territory-->
										</div>
								    </div>
								 	<div class="form-group">
								    	<label class="col-sm-3 control-label">ID编号:</label>
								    	<div class="input-group col-sm-9 ">
										    <span class="input-group-addon" id="basic-addon1"><i class="member-icon member-id-icon"></i></span>
										    <input type="text" class="form-control" placeholder="请填写ID编码">
										</div>
								    </div>
								 	<div class="form-group">
								    	<div class="input-group col-sm-9 col-sm-offset-4">
								    		<button class="btn btn-warning col-sm-3 ">确定保存</button>
										</div>
								    </div>
								</form>
			  				</div>
			  				<!--/ -->
			  				
			  			</div>
					</div>
		    	</div>
			</div>
		</div>		
		
		<script src="static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>
	</body>
</html>