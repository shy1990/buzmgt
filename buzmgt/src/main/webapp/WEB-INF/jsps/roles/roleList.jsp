<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<title>权限设置</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap -->
<link href="../static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="../static/purview-setting/character.css" />
<link rel="stylesheet" type="text/css" href="../static/css/menu.css" />
<link rel="stylesheet" type="text/css"
	href="../static/purview-setting/purview-setting.css" />
<!--div 滚动条-->
<link rel="stylesheet" type="text/css"
	href="../static/js/jquery/scroller/jquery.mCustomScrollbar.css" />
<script src="../static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<!-- page-main -->
	<div id="j_page_main" class="content main">
		<h4 class="page-header">
			<i class="icon pur-setting-icon"></i>权限设置 <a
				class="btn btn-blue marg-lef-10" data-toggle="modal"
				data-target=".j_create_role" data-whatever="@mdo"><i
				class="icon-add"></i>创建角色</a>
		</h4>
		<!-- start:row -->
		<div class="row">
			<!-- start:col -->
			<div class="col-md-12 ">
				<!-- start： 列表 -->
				<div class="character table-responsive">
					<table id="table_report"
						class="menu table table-striped table-bordered table-hover table-condensed">
						<thead>
							<th width="80">序号</th>
							<th>角色</th>
							<th colspan="3"  style="text-align:center">操作</th>
						</thead>
						<tbody>
							<c:choose>
								<c:when test="${not empty roles}">
									<c:forEach var="role" items="${roles}" varStatus="s">
										<tr class="am-active">
											<td>${s.index+1}</td>
											<td>${role.name}</td>
											<td><a
												href="javascript:selRole(${role.id},'${role.name}');"
												class="query-text">查看</a></td>
											<td><a href="javascript:delRole(${role.id});"
												class="delete-text">删除</a></td>
											<td><a href="javascript:editRole2Menus(${role.id});"
												class=" delete-text">菜单权限</a></td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="100">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				<div id="pageNav" class="scott" align="center">
					<font color="#88af3f">共${totalCount} 条数据， 共${totalPage} 页</font>
					<div class="page-link" >${pageNav}</div>
				</div>
				<!-- end 列表 -->
				<!-- start： 弹窗 -->
				<div class="j_create_role add-role modal fade " id="exampleModal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="exampleModalLabel">
									<i class="icon-add"></i>创建角色
								</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal">
									<div class="form-group">
										<label for="name" class="col-md-3 control-label">角色名称</label>
										<div class="col-md-9 ">
											<input type="text" placeholder="请填写角色名称" class="form-control" name="name" id="name" onchange="tocheckname()"/>
											<span id="gradeInfo"></span> 
										</div>
										<label class="pull-right col-md-3 control-label msg-error" id="nameError">请填写角色名称</label>
									</div>
									<div class="form-group">
										<label for="message-text" class="col-md-3 control-label">备注说明</label>
										<div class="col-md-9">
											<textarea class="form-control" rows="6" placeholder="请填写备注说明"
												name="description" id="description"></textarea>
										</div>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<div class="col-md-3 col-md-offset-8">
									<button type="submit" onclick="addRole()" id="bt"	class="btn col-xs-12 btn-danger" disabled >确定</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- end 弹窗 -->
			</div>
			<!-- end: col -->
		</div>
		<!-- end:row -->
	</div>
	<%@include file="/static/js/alert/alert.html" %>
</body>
<script src='/static/bootstrap/js/bootstrap.js'></script>
<script src='/static/js/common.js'></script>
<script type="text/javascript">
	window.jQuery
			|| document
					.write("<script src='../static/js/jquery.min.js'>\x3C/script>");
</script>
<script type="text/javascript">
	/*判断角色是否已存在 */
	function tocheckname(){
		var name = $('#name').val();
		var url = "checkByRoleName?name="+name;
		$.post(url, function(data) {
			
			if (data === 'err') {
				$("#gradeInfo").html("<font color=\"red\">您输入的角色存在！请重新输入！</font>"); 
				document.getElementById("bt").disabled=true;	
			} else {
				$("#gradeInfo").html(""); 
				document.getElementById("bt").disabled=false;	
			}
		});
		
	}




	/*	新增角色*/
	function addRole() {
		var $_name=$('#name');
		if($_name.val()==null||$_name.val()==""){
			$_name.parents('.form-group').addClass('has-error');
			return false;
		}
		var url = "addRole?name=" + $("#name").val() + "&description="
				+ $("#description").val();
		$.post(url, function(data) {
			if (data === 'suc') {
				$('#exampleModal').modal('hide');
				myAlert("添加成功");
				setTimeout(function(){ location.reload() },3000);
			} else {
				myAlert("添加失败!");
			}
		});
	}
	/* 查看 */
	function selRole(roleId, roleName) {
		var url = "selByRole?id=" + roleId + "&name=" + roleName;
		window.location = encodeURI(url);
	}
	/* 删除 角色*/
	function delRole(id) {
		if (confirm("确定要删除该角色？")) {
			var url = "delRole?id=" + id;
			$.post(url, function(data) {
				if (data === 'suc') {
					myAlert("删除成功!");
					setTimeout(function(){ location.reload() },2000);
				} else {
					myAlert("删除失败!,请先移除该角色下的所有人员");
				}
			});
		}
	}
	/*授权菜单	*/
	function editRole2Menus(roleId) {
		var url = "auth?rId=" + roleId;
		window.location = encodeURI(url);
	}
</script>
</html>

