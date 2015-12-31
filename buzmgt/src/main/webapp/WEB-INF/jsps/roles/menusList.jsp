<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>菜单管理</title>
<!-- <script  src='../static/js/jquery/jquery.min.js'></script> -->
<!-- <script  src='../static/bootstrap/js/bootstrap.js'></script> -->

</head>
<body>
	<%@ include file="../top_menu.jsp"%>
	<div class="container-fluid">
		<div id="" class="row">
			<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
				<%@include file="../left_menu.jsp"%>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
				<!-- page-main -->
				<div id="j_page_main" class="content main">
					<h4 class="page-header">
						<i class="icon pur-setting-icon"></i> 权限设置
					</h4>
					<!-- start:row -->
					<div class="row">
						<!-- start:col -->
						<div class="col-md-12">
							<div class="box border blue">
								<!-- start： 列表 -->
								<div>
									<table id="table_report"
										class="table table-striped table-bordered table-hover table-condensed"
										style="font-size: 10px;">
										<span style="font-family: 华文中宋; color: red;">&nbsp菜单管理&nbsp</span>
										<thead>
											<th width="20%" class="center">序号</th>
											<th width="20%" class="center">菜单名称</th>
											<th width="20%" class="center">菜单url</th>
											<th width="20%" class="center">操作</th>
										</thead>
										</thead>
										<tbody>
											<c:choose>
												<c:when test="${not empty menus}">
													<c:forEach var="menu" items="${menus}" varStatus="s">
														<tr class="am-active">
															<td width="20%" class="center">${s.index+1}</td>
															<td width="20%">${menu.name}</td>
															<td width="20%">${menu.url}</td>
															<td style="width: 10px;"><a
																href="javascript:removeMenu(${menu.id});" class=" btn-success">删除</a></td>
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
													<font color="#88af3f">共${totalCount} 条数据， 共${totalPage} 页</font>
													${pageNav}
												</div>
							
											</td>
											<td></td>
										</tr>
									</table>
								</div>
								<!-- end 列表 -->
								<a class="j_create_role btn btn-danger " data-toggle="modal"
									data-target="#exampleModal" data-whatever="@mdo"><i
									class="icon-add"></i>添加菜单</a>
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
													<i class="icon-add"></i>添加菜单
												</h4>
											</div>
											<div class="modal-body">
												<form action="addMenu" method="post">
													<div class="form-group">
														<label for="inputPassword" class="col-sm-3 control-label">父菜单
														</label>
														<div class="input-group col-sm-9 ">
															<span class="input-group-addon"><i
																class="member-icon member-role-icon"></i></span> <select
																class="form-control" name="parentid" id="parentid">
																<c:forEach items="${menus}" var="u">
																	<option value="${u.id }"
																		<c:if test="${user.user_id==u.id}"><c:out value="selected"/></c:if>>
																		${u.name}</option>
																</c:forEach>
															</select>
														</div>
													</div>
													<div class="form-group">
														<label for="recipient-name" class="col-md-3 control-label">菜单名称</label>
														<div class="col-md-9 ">
															<input type="text" placeholder="请填写菜单名称" name="name" id="name"
																class="form-control" id="recipient-name">
														</div>
													</div>
													<div class="form-group">
														<label for="message-text" class="col-md-3 control-label">菜单URL</label>
														<div class="col-md-9">
															<input type="text" placeholder="请填写菜单的URL地址" name="url" id="url"
																class="form-control" id="recipient-name">
														</div>
													</div>
													<div class="modal-footer">
														<div class="col-md-3 col-md-offset-8">
															<input type="reset" value="确定" onclick="addMenu()"
																class="btn col-xs-12 btn-danger " />
														</div>
													</div>
												</form>
											</div>
										</div>
									</div>
								</div>
								<!-- end 弹窗 -->
							</div>
						</div>
						<!-- end: col -->
					</div>
					<!-- end:row -->
				</div>
			</div>
		</div>
	</div>


</body>

<!-- 引入 -->
<!-- 		<script src="../static/js/jquery/jquery.min.js"></script> -->
<script type="text/javascript">
	window.jQuery|| document.write("<script src='../static/js/jquery/jquery.min.js'>\x3C/script>");
</script>
<!-- 		<script src="../static/bootstrap/js/bootstrap.min.js"></script> -->
<script type="text/javascript">
	/* 删除 菜单*/
	function removeMenu(id) {
		var url = "removeMenu?id=" + id;
		$.post(url, function(data) {
			if (data === 'suc') {
				alert("删除成功!");
				location.reload();
			} else {
				alert("删除失败!");
			}
		});
	}
	function addMenu() {
		var url = "addMenu?name=" + $("#name").val() + "&url="
				+ $("#url").val() + "&parentid=" + $("#parentid").val();
		$.post(url, function(data) {
			if (data === 'suc') {
				alert("添加成功");
				location.reload();
			} else {
				alert("添加失败!");
			}
		});
	}
</script>
</html>