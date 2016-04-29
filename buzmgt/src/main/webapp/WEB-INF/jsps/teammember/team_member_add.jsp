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
<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/zTree/css/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="/static/yw-team-member/team-member.css" />
<script src="/static/js/jquery/jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src='/static/js/common.js'></script>
<!-- 		 <script type="text/javascript" src="../static/zTree/js/jquery.ztree.all-3.5.min.js"></script>  -->
<style type="text/css">
.ztree {
	margin-top: 34px;
	border: 1px solid #ccc;
	background: #FFF;
	width: 100%;
	overflow-y: scroll;
	overflow-x: auto;
}

.menuContent {
	width: 100%;
	padding-right: 61px;
	display: none;
	position: absolute;
	z-index: 800;
}
</style>

</head>

<body>
	<div class="main">
		<h4 class="page-header">
			<i class="icon team-member-add-icon"></i>添加成员 <a
				href="/teammember/salesManList" class="btn btn-blue member-add-btn"
				type="button"> <i class="icon glyphicon glyphicon-share-alt"></i>
				返回列表
			</a>
		</h4>
		<div class="row">
			<div class="col-md-12">
				<div class="member-add-box box border blue">
					<div class="box-title">
						<i class="icon member-add-icon"></i>添加
					</div>
					<div class="box-body">
						<!-- -->
						<div class="member-from col-md-6 col-md-offset-2 col-sm-10">
							<form id="addSalesManForm" name="regForm"
								class="member-from-box form-horizontal"
								action="/teammember/addTeamMember" method="post"
								onsubmit="javascript:return checkForm()">
								<div class="form-group">
									<label class="col-sm-3 control-label">用户名:</label>
									<div class="input-group col-sm-9">
										<span class="input-group-addon" id="basic-addon1"><i
											class="member-icon member-name-icon"></i></span> <input type="text"
											class="form-control" id="username" name="username"
											value="${userName}" placeholder="请填写5-20位字符的用户名"
											onblur="checkUsername()" />
									</div>
									<label class="pull-right col-md-6 control-label msg-error">请填写用户名,5-20位字符</label>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">姓名:</label>
									<div class="input-group col-sm-9">
										<span class="input-group-addon" id="basic-addon1"><i
											class="member-icon member-name-icon"></i></span> <input type="text"
											class="form-control" id="truename"
											value="${salesman.truename}" name="truename"
											placeholder="请填写个人真实姓名" onblur="checkName()">
									</div>
									<label class="pull-right col-md-6 control-label msg-error">请填写个人真实姓名</label>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">职务:</label>
									<div class="input-group col-sm-9">
										<span class="input-group-addon"><i
											class="member-icon member-job-icon"></i></span>
										<!--<input type="text" class="form-control" id="inputPassword" placeholder="">-->
										<select id="organization" class="form-control"
											name="organizationId">
											<option value="">请选择</option>
										</select>
									</div>
									<label class="pull-right col-md-6 control-label msg-error">请选择职务</label>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">角色权限:</label>
									<div class="input-group col-sm-9 ">
										<span class="input-group-addon"><i
											class="member-icon member-role-icon"></i></span>
										<!--<input type="text" class="form-control" id="inputPassword" placeholder="">-->
										<select id="role" class="form-control" name="roleId">
											<option selected="selected" value="">请选择</option>
										</select>
									</div>
									<label class="pull-right col-md-6 control-label msg-error">请选择角色权限</label>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">工号:</label>
									<div class="input-group col-sm-9 ">
										<span class="input-group-addon" id="basic-addon1"><i
											class="member-icon job-number-icon"></i></span> <input type="text"
											class="form-control" id="jobNum" name="jobNum"
											value="${salesman.jobNum}" placeholder="请填写个人共工号"
											onblur="checkJobNum()">
									</div>
									<label class="pull-right col-md-3 control-label msg-error">请填写个人共工号</label>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">电话:</label>
									<div class="input-group col-sm-9 ">
										<span class="input-group-addon" id="basic-addon1"><i
											class="member-icon member-phone-icon"></i></span> <input type="text"
											class="form-control" id="mobile" name="mobile"
											value="${salesman.mobile}" placeholder="请填写电话号码"
											onblur="checkMobile()">
									</div>
									<label class="pull-right col-md-3 control-label msg-error">请填写个人电话</label>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">负责区域:</label>
									<div class="input-group col-sm-9">
										<span class="input-group-addon"><i
											class="member-icon member-job-icon"></i></span>
										<!--<input type="text" class="form-control" id="inputPassword" placeholder="">-->
										<select id="region" class="form-control" name="regionId">
										</select>
										<div id="regionMenuContent" class="menuContent">

											<ul id="regionTree" class="ztree"></ul>
										</div>
										<input type="hidden" id="towns" name="regionPid">
									</div>
									<label class="pull-right col-md-3 control-label msg-error">请选择负责区域</label>
								</div>
								<div class="form-group">
									<label for="inputPassword" class="col-sm-3 control-label">是否老业务:</label>
									<div class="input-group col-sm-9 ">
										<span class="input-group-addon"><i
											class="member-icon member-role-icon"></i></span> <select
											id="isOldSalesman" class="form-control" name="isOldSalesman">
											<option selected="selected" value="">请选择</option>
											<option value="0">新业务</option>
											<option value="1">老业务</option>
										</select>
									</div>
									<label class="pull-right col-md-6 control-label msg-error">请选择角色权限</label>
								</div>
								<!-- <div class="form-group">
								    	<label class="col-sm-3 control-label">ID编号:</label>
								    	<div class="input-group col-sm-9 ">
										    <span class="input-group-addon" id="basic-addon1"><i class="member-icon member-id-icon"></i></span>
										    <input type="text" class="form-control" placeholder="请填写ID编码">
										</div>
								    </div> -->
								<div class="form-group">
									<div class="input-group col-sm-9 col-sm-offset-4">
										<button class="btn btn-blue col-sm-3 " type="submit">确定保存</button>
									</div>
								</div>
							</form>
						</div>
						<!--/ -->
					</div>
				</div>
			</div>
		</div>
		<!-- /CALENDAR -->
	</div>
	<!-- 自定义弹出窗 -->
	<!------------------------------------------------------------------------------------------------------------------------ -->
	<%@include file="/static/js/alert/alert.html"%>
	<script src="/static/zTree/js/jquery.ztree.all-3.5.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="/static/yw-team-member/team-memberAdd.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="/static/yw-team-member/team-memberForm.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="/static/yw-team-member/team-tree.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="/static/js/index.js" type="text/javascript"
		charset="utf-8"></script>
</body>
</html>
