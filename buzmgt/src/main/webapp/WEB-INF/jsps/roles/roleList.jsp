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
						<i class="icon pur-setting-icon"></i>权限设置
					</h4>
					<!-- start:row -->
					<div class="row">
						<!-- start:col -->
						<div class="col-md-12">
							<!-- start： 列表 -->
							<table id="table_report"
								class="table table-striped table-bordered table-hover table-condensed"
								style="font-size: 10px;">
								<tbody>
									<c:choose>
										<c:when test="${not empty roles}">
											<c:forEach var="role" items="${roles}" varStatus="s">
												<tr class="am-active">
													<td width="5%" class="center">${s.index+1}</td>
													<td width="20%">${role.name}</td>
													<td style="width: 10px;"><a
														href="javascript:selRole(${role.id},'${role.name}');"
														class="query-text">查看</a></td>
													<td style="width: 10px;"><a
														href="javascript:delRole(${role.id});" class="delete-text">删除</a></td>
													<td style="width: 10px;"><a
														href="javascript:editRole2Menus(${role.id});"
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
							<table class="page" cellpadding="0" cellspacing="5">
								<tr>
									<td>
										<div id="pageNav" class="scott" align="center">
											<font color="#88af3f">共${totalCount} 条数据，
												共${totalPage} 页</font> ${pageNav}
										</div>

									</td>
									<td></td>
								</tr>
							</table>
							<!-- end 列表 -->
							<a class="j_create_role btn btn-danger " data-toggle="modal"
								data-target="#exampleModal" data-whatever="@mdo"><i
								class="icon-add"></i>创建角色</a>
							<!-- start： 弹窗 -->
							<div class="add-role modal fade " id="exampleModal" tabindex="-1"
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
													<label for="recipient-name" class="col-md-3 control-label">角色名称</label>
													<div class="col-md-9 ">
														<input type="text" placeholder="请填写角色名称"
															class="form-control"  name="name" id="name">
													</div>
												</div>
												<div class="form-group">
													<label for="message-text" class="col-md-3 control-label">备注说明</label>
													<div class="col-md-9">
														<textarea class="form-control" rows="6"
															placeholder="请填写备注说明" name="description" id="description"></textarea>
													</div>
												</div>
											</form>
										</div>
										<div class="modal-footer">
											<div class="col-md-3 col-md-offset-8">
												<button type="button" onclick="addRole()" class="btn col-xs-12 btn-danger ">确定</button>
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
</body>
<script  src='../static/bootstrap/js/bootstrap.js'></script>
<script type="text/javascript">
	window.jQuery|| document.write("<script src='../static/js/jquery.min.js'>\x3C/script>");
</script>
<script type="text/javascript">
	/*	新增角色*/
	function addRole() {
		var url = "addRole?name=" + $("#name").val()+"&description="+$("#description").val();
		$.post(url, function(data) {
			if (data === 'suc') {
				alert("添加成功");
				location.reload();
			} else {
				alert("添加失败!");
			}
		});
	}
		/* 查看 */
		function selRole(roleId,roleName) {
			var url = "selByRole?id="+roleId+"&name="+ roleName;
			window.location = encodeURI(url);
		}
		/* 删除 角色*/
		function delRole(id) {
			if(confirm("确定要删除该角色？")){
				var url = "delRole?id=" + id;
				$.post(url, function(data) {
					if (data === 'suc') {
						alert("删除成功!");
						location.reload();
					}else{
						alert("删除失败!,请先移除该角色下的所有人员");
					}
				});
			}
		}
		/*授权菜单	*/
		function editRole2Menus(roleId) {
			var url = "auth?rId="+roleId;
			window.location = encodeURI(url);
		}
	
		
	</script>
</html>

