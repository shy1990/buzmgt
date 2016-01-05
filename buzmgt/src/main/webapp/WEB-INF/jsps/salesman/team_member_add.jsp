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
		<link rel="stylesheet" type="text/css" href="static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="static/css/index.css" />
		 <link rel="stylesheet" type="text/css" href="static/zTree/css/zTreeStyle/zTreeStyle.css" /> 
		<link rel="stylesheet" type="text/css" href="static/yw-team-member/team-member.css" />
		<script src="static/js/jquery/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
		 <script type="text/javascript" src="static/zTree/js/jquery.ztree.all-3.5.min.js"></script> 
		 <style type="text/css">
			.ztree{margin-top: 34px;border: 1px solid #ccc;background: #FFF;width:100%;overflow-y:scroll;overflow-x:auto;}
			.menuContent{width: 100% ;padding-right: 61px;display:none; position: absolute;z-index:200;}
		</style>
	</head>

	<body>
		<div id="main" class="content main">
			<h4 class="page-header"><i class="icon team-member-add-icon"></i>添加成员</h1>
			<div class="row">
		    	<div class="col-md-12">
			  		<div class="member-add-box box border orange">
			  			<div class="box-title"><i class="icon member-add-icon"></i>添加</div>
			  			<div class="box-body">
			  				<!-- -->
			  				<div class="member-from col-md-6 col-md-offset-2 col-sm-10">
			  					<form class="member-from-box form-horizontal" action="/salesman/addTeamMember">
			  					  <div class="form-group">
								    	<label class="col-sm-3 control-label">用户名:</label>
								    	<div class="input-group col-sm-9">
										    <span class="input-group-addon" id="basic-addon1"><i class="member-icon member-name-icon"></i></span>
										    <input type="text" class="form-control" name="username" placeholder="请填写用户名">
										</div>
								    </div>
								    <div class="form-group">
								    	<label class="col-sm-3 control-label">姓名:</label>
								    	<div class="input-group col-sm-9">
										    <span class="input-group-addon" id="basic-addon1"><i class="member-icon member-name-icon"></i></span>
										    <input type="text" class="form-control" name = "trueName" placeholder="请填写个人名称">
										</div>
								    </div>
								    <div class="form-group">
								    	<label for="inputPassword" class="col-sm-3 control-label">职务:</label>
								    	<div class="input-group col-sm-9">
								    		<span class="input-group-addon"><i class="member-icon member-job-icon"></i></span>
								      		<!--<input type="text" class="form-control" id="inputPassword" placeholder="">-->
								      		<select id="organization" class="form-control" name="organizationId" >
											    <option  value="">请选择</option>
											</select>
									    </div>
									    
								 	</div>
								    <div class="form-group">
								    	<label for="inputPassword" class="col-sm-3 control-label">角色权限:</label>
								    	<div class="input-group col-sm-9 ">
								    		<span class="input-group-addon"><i class="member-icon member-role-icon"></i></span>
								      		<!--<input type="text" class="form-control" id="inputPassword" placeholder="">-->
								      		<select id="role" class="form-control" name="roleId">
											    <option selected="selected" value="">请选择</option>
											</select>	
								    	</div>
								 	</div>
								    <div class="form-group">
								    	<label class="col-sm-3 control-label">工号:</label>
								    	<div class="input-group col-sm-9 ">
										    <span class="input-group-addon" id="basic-addon1"><i class="member-icon job-number-icon"></i></span>
										    <input type="text" class="form-control" name="jobNum" placeholder="请填写个人共工号">
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
								        <label for="inputPassword" class="col-sm-3 control-label">负责区域:</label>
								    	<div class="input-group col-sm-9">
								    		<span class="input-group-addon"><i class="member-icon member-job-icon"></i></span>
								      		<!--<input type="text" class="form-control" id="inputPassword" placeholder="">-->
								      		<select id="region" class="form-control" name="regionId" onclick="showRegionTree(this.id)">
											    <option  value="">请选择</option>
											</select>
											<div id="regionMenuContent"  class="menuContent">
												 
												   <ul id="regionTree" class="ztree"></ul>
										    </div>
										    <input type="hidden" id="towns" name="regionPid">	
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
		<script src="static/yw-team-member/team-tree.js" type="text/javascript" charset="utf-8"></script>
	</body>
</html>