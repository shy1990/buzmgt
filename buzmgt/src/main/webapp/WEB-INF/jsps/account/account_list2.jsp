<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/css/menu.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/account-manage/account-list.css" />
<link rel="stylesheet" type="text/css" href="static/task/task.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="table-template" type="text/x-handlebars-template">
{{#if content}}
{{#each content}}
<tr class="am-active">
	<td width="5%" class="center">{{addOne @index}}</td>
	<td width="10%">{{position}}</td>
	<td width="10%">{{accountNum}}</td>
	<td width="10%">{{name}}</td>
	<td width="10%">{{areaName}}</td>
	<td width="10%">{{roleName}}</td>
	<td width="10%">
		<div class="switch switch-small" data-on="info" data-off="success">
		{{{switch status accountNum}}}
		</div>
	</td>
	<td width="20%" class="operation">
		<a href="javascript:resetPwd('{{accountNum}}');">重置密码</a> 
		<a href="javascript:modifyAccount('{{accountNum}}','{{position}}');">修改资料</a> 
		<a class="text-danger" href="javascript:mofidyAccount('{{accountNum}}','2');">辞退</a>
		<a href="javascript:mofidyAccount('{{accountNum}}','4');"> 清空sim</a>
	</td>
	<td>
		<a class="" href="" data-toggle="modal"onclick="addAccount('{{accountNum}}');" >
			<img src="static/img/addcode/tj.png">添加</a> 
		<a href="javascript:findChildAccount('{{accountNum}}');"
			aria-controls="pofile" role="tab" data-target="tab">
			{{childCount}}个子账号<img src="static/img/addcode/jl.png"></a>
	</td>
</tr>
{{/each}}
{{else}}
<tr>
	<td colspan="100">没有相关数据</td>
</tr>
{{/if}}
</script>
<script id="dismiss-table-template" type="text/x-handlebars-template">
{{#if content}}
{{#each content}}
<tr class="am-active">
	<td width="5%" class="center">{{@index}}</td>
	<td width="10%">{{position}}</td>
	<td width="10%">{{accountNum}}</td>
	<td width="10%">{{name}}</td>
	<td width="10%">{{areaName}}</td>
	<td width="10%">{{roleName}}</td>
	<td width="10%">
		<div class="switch switch-small" data-on="info" data-off="success">
		{{{switch status}}}
		</div>
	</td>
	<td width="20%" class="operation">
		<a class="text-danger" href="javascript:mofidyAccount('{{accountNum}}','1');">恢复账号</a>
		<a class="text-danger" href="javascript:mofidyAccount('{{accountNum}}','3');">删除</a>
		<a href="javascript:mofidyAccount('{{accountNum}}','4');"> 清空sim</a>
	</td>
</tr>
{{/each}}
{{else}}
<tr>
	<td colspan="100">没有相关数据</td>
</tr>
{{/if}}
</script>

<script type="text/javascript">
var	base='<%=basePath%>';
var SearchData={
		"page":"0",
		"size":"4"
}
</script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header">
			<i class="ico ico-account-manage"></i>账号管理
			<!--区域选择按钮-->
			<div class="area-choose">
				选择区域：<span>${regionName}</span> <a class="are-line"
					href="javascript:;" onclick="getRegion(${regionId});">切换</a>
			</div>
			<!--/区域选择按钮-->
		</h4>

		<div class="table-before marg-b-10">
			<ul class="nav nav-task" role="tablist">
				<li class="active"><a href="#box_tab1" data-toggle="tab"
					onclick="selectByOrg('allUsed','used');"> 全部(在职)</a></li>
				<li><a href="#box_tab1" data-toggle="tab"
					onclick="selectByOrg('服务站经理','0');"> 服务站经理(在职)</a></li>
				<li><a href="#box_tab1" data-toggle="tab"
					onclick="selectByOrg('大区经理','0');"> 大区经理(在职)</a></li>
				<li><a href="#box_tab2" data-toggle="tab"
					onclick="selectByOrg('allDis','2');">已辞退</a></li>
				<!-- Nav tabs -->
			</ul>
			<ul class="nav nav-task" style="width: 30%;float: right;">
				<div class="input-group ">
					<input type="text" class="form-control" placeholder="请输入名称或用户名"
						id="param" value="${searchParam}"
						onkeyup="javascript:if(event.keyCode==13){getAccountList()}">
					<span class="input-group-addon" id="goSearch"
						onclick="getAccountList();"><i class="icon icon-finds"></i></span>
				</div>
			</ul>

		</div>


		<div class="tab-box-border">
			<!--tab-content-->
				<!--业务揽收异常-->
				<!--列表内容-->
				<div class="tab-content">
					<div class="tab-pane fade in active" id="box_tab1">
						<div class="table-responsive table-overflow">
							<table id="table_report"
								class="table table-hover new-table abnormal-order-table">
								<thead>
									<th width="5%" class="center">序号</th>
									<th width="10%" class="center">职务</th>
									<th width="10%" class="center">账号</th>
									<th width="10%" class="center">姓名</th>
									<th width="10%" class="center">负责区域</th>
									<th width="10%" class="center">角色权限</th>
									<th width="5%" class="center">账号状态</th>
									<th width="20%" class="center">操作</th>
									<th width="20%" class="center">子账号</th>
								</thead>
								<tbody id="tableList">
								</tbody>
							</table>
						</div>
						<div id="initPager"></div>
					</div>

					<div class="tab-pane fade " id="box_tab2">
						<div class="table-responsive table-overflow">
							<table id="table_report"
								class="table table-hover new-table abnormal-order-table">
								<thead>
									<th width="5%" class="center">序号</th>
									<th width="10%" class="center">职务</th>
									<th width="10%" class="center">账号</th>
									<th width="10%" class="center">姓名</th>
									<th width="10%" class="center">负责区域</th>
									<th width="10%" class="center">角色权限</th>
									<th width="5%" class="center">账号状态</th>
									<th width="20%" class="center">操作</th>
								</thead>
								<tbody id="dismissTableList">
								</tbody>
							</table>
						</div>
						<div id="initDismissPager"></div>
					</div>
				</div>
			<!-- tab-content -->
		</div>
	</div>
	<!--box-->
	<!-- alert resetPwd html -->
	<div id="resetPwdModal" class="modal fade" role="dialog"
		aria-labelledby="gridSystemModalLabel">
		<div class="modal-dialog " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title" id="gridSystemModalLabel">
						重置密码
						</h4>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-12">重置密码后将恢复初始状态！</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success caution"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						onclick="('${ac.accountNum}')";>确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /alert html -->

	<!-- alert 辞退 html -->
	<div id="gridSystemModal" class="modal fade" role="dialog"
		aria-labelledby="gridSystemModalLabel">
		<div class="modal-dialog " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title" id="gridSystemModalLabel">
						辞退
						</h4>
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
					<button type="button" class="btn btn-success caution"
						data-dismiss="modal">以后再说</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /alert html -->
	<!-- alert cleanSimId html -->
	<div id="cleanSimModal" class="modal fade" role="dialog"
		aria-labelledby="cleanSimModalLabel">
		<div class="modal-dialog " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title" id="cleanSimModalLabel">
						清空sim
						</h4>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-12">清空sim卡后将恢复初始状态！</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success caution"
						data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal"
						onclick="cleanSim('${ac.accountNum}')";>确定</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /alert html -->
	<!-- alert html -->
	<div id="deleteAccountModal" class="modal fade" role="dialog"
		aria-labelledby="gridSystemModalLabel">
		<div class="modal-dialog " role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title" id="gridSystemModalLabel">
						提示
						</h4>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="row">
							<div class="col-md-12">您确定要删除此用户吗？删除后此用户的信息将不在保留！</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success caution"
						data-dismiss="modal">不删除</button>
					<button type="button" class="btn btn-danger" data-dismiss="modal">确定删除</button>
				</div>
			</div>
		</div>
	</div>
	<!-- /alert html -->

	<!-- /alert html添加子账号 -->
	<div id="addAccount" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title" id="">添加子账号</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<div class="form-group">
							<label for="" class="col-sm-2 control-label">姓名：</label>
							<div class="col-sm-10">
								<div class="input-group are-line">
									<span class="input-group-addon" id=""><i
										class="icon icon-user"></i></span> <input type="text"
										class="form-control input-h" id="truename" name="truename"
										placeholder="请输入姓名" aria-describedby="basic-addon1"> <input
										type="hidden" id="userId" name="userId">
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-4">
								<button type="button" class="col-sm-12 btn btn-primary"
									onclick="addChildAccount();">确定</button>
							</div>
						</div>
						<div class="col-sm-offset-5 col-sm-7"></div>
					</div>
				</div>
			</div>
		</div>
	</div>



	</div>
	</div>
	</div>
</body>
<script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
<script
	src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
	type="text/javascript" charset="utf-8"></script>

<script src='/static/bootstrap/js/bootstrap.js'></script>
<script src='/static/js/common.js'></script>
<script src="/static/bootstrap/js/bootstrap-switch.min.js"></script>
<script type="text/javascript" src="/static/js/handlebars-v4.0.2.js"
	charset="utf-8"></script>
<script type="text/javascript"
	src="/static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript"
	src="/static/account-manage/account-list.js"></script>
</html>
