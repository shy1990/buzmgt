<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap -->
<link href="../static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
<link rel="stylesheet" type="text/css" href="../static/css/menu.css" />
<link rel="stylesheet" type="text/css"
	href="../static/purview-setting/character.css" />
<link rel="stylesheet" type="text/css"
	href="../static/purview-setting/purview-setting.css" />
<!--div 滚动条-->
<link rel="stylesheet" type="text/css"
	href="../static/js/jquery/scroller/jquery.mCustomScrollbar.css" />
<link rel="stylesheet" type="text/css" href="static/task/task.css" />
<script src="../static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<title>菜单管理</title>
</head>
<body>
	<div id="j_page_main" class="content main">
		<h4 class="page-header">
			<i class="icon pur-setting-icon"></i> 菜单管理 <a
				class="btn btn-blue marg-lef-10" data-toggle="modal"
				data-target=".j_create_role" data-whatever="@mdo"> <i
				class="icon-add"></i>添加菜单
			</a>
		</h4>
		<!-- start:row -->
		<div class="row">
			<!-- start:col -->
			<div class="col-md-12">
				<!-- start： 列表 -->
				<div class="tab-box-border">
					<div class="table-responsive table-overflow">
						<table id="table_report"
							class="menu-table table table-hover new-table abnormal-order-table">
							<thead>
								<th width="20%" class="center">序号</th>
								<th width="20%" class="center">菜单名称</th>
								<th width="20%" class="center">菜单url</th>
								<th width="20%" class="center">操作</th>
							</thead>
							<tbody>
								<c:choose>
									<c:when test="${not empty menus}">
										<c:forEach var="menu" items="${menus}" varStatus="s">
											<tr class="am-active">
												<td width="20%" class="center">${s.index+1}</td>
												<td width="20%">${menu.name}</td>
												<td width="20%">${menu.url}</td>
												<td style="width: 10px;" class="operation"><a
													href="javascript:removeMenu(${menu.id});" >删除</a>
												</td>
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
						<font color="#88af3f">共${totalCount} 条数据，
							共${totalPage} 页</font> 
						<div class="page-link" >${pageNav}</div>
					</div>
				</div>
				<!-- start： 弹窗 -->
				<div class="j_create_role add-role modal fade" id="menu_modal" tabindex="-1"
					role="dialog" aria-labelledby="exampleModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="exampleModalLabel">
									<i class="icon-add"></i>添加菜单
								</h4>
							</div>
							<div class="modal-body">
								<form action="addMenu" method="post" class="form-horizontal">
									<div class="form-group">
										<label for="inputPassword" class="col-sm-3 control-label">父菜单
										</label>
										<div class="col-sm-9 ">
											<select class="form-control" name="parentid"  id="parentid">
												<c:forEach items="${menuList}" var="u">
													<option value="${u.id }"
														<c:if test="${user.user_id==u.id}"><c:out value="selected"/></c:if>>
														${u.name}</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="recipient-name" class="col-md-3 control-label">菜单名称</label>
										<div class="col-md-9">
											<input type="text" placeholder="请填写菜单名称" name="name"
												id="name" class="form-control" id="recipient-name">
										</div>
										<label class="pull-right col-md-3 control-label msg-error">请填写菜单名称</label>
									</div>
									<div class="form-group">
										<label for="message-text" class="col-md-3 control-label">菜单URL</label>
										<div class="col-md-9">
											<input type="text" placeholder="请填写菜单的URL地址" name="url"
												id="url" class="form-control" id="recipient-name">
										</div>
										<label class="pull-right col-md-3 control-label msg-error">请填写菜单的地址</label>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<div class="col-md-3 col-md-offset-8">
									<button type="button" onclick="addMenu()"
										class="btn col-xs-12 btn-danger ">确定</button>
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

<!-- 引入 -->
<!-- 		<script src="../static/js/jquery/jquery.min.js"></script> -->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="/static/js/jquery/jquery-1.11.3.min.js"></script>
<script src='/static/bootstrap/js/bootstrap.js'></script>
<script src="/static/js/common.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script
	src="../static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	window.jQuery
			|| document
					.write("<script src='/static/js/jquery/jquery.min.js'>\x3C/script>");
</script>
<script type="text/javascript">
	/* 删除 菜单*/
	function removeMenu(id) {
		if (confirm("确定要删除该菜单？")) {
			var url = "removeMenu?id=" + id;
			$.post(url, function(data) {
				if (data === 'suc') {
					myAlert("删除成功!");
					setTimeout(function(){
		        		location.reload()
		        		},3000);
				} else {
					myAlert("请先移除相关角色下该菜单的权限!");
				}
			});
		}
	}
	function addMenu() {
		var $_name=$('#name');
		if($_name.val()==null||$_name.val()==""){
			$_name.parents('.form-group').addClass('has-error');
			return false;
		}
		var $_prentid=$("#parentid");
		if($_prentid.val()==null||$_prentid.val()==""){
			$_prentid.parents('.form-group').addClass('has-error');
			return false;
		}
		var url = "addMenu?name=" + $("#name").val() + "&url="
				+ $("#url").val() + "&parentid=" + $("#parentid").val();
		$.post(url, function(data) {
			if (data === 'suc') {
				$('#menu_modal').modal('hide');
				myAlert("添加成功");
				setTimeout(function(){ location.reload() },2000);
			} else {
				myAlert("添加失败!");
			}
		});
	}

</script>
</html>
