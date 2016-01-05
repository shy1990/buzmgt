<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<base href="<%=basePath%>" />
<meta charset="UTF-8">
<title>权限设置</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/index.css" />
<!--div 滚动条-->
<link rel="stylesheet" type="text/css" href="static/js/jquery/scroller/jquery.mCustomScrollbar.css" />
<link rel="stylesheet" type="text/css" href="static/purview-setting/purview-setting.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js" ></script>
</head>

<body>
	<%@ include file="../top.jsp"%>
	<div class="container-fluid">
		<div id="" class="row">
			<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
				<%@include file="../left.jsp"%>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
				<!-- page-main -->
				<div id="j_page_main" class="content main">
					<h4 class="page-header">
						<i class="icon pur-setting-icon"></i> 权限设置
					</h4>
					<!-- 		row -->
					<div class="row">
<<<<<<< HEAD
						<div class="col-md-12">
							<div class="box border blue">
								<div class="row">
									<div class="col-xs-4 col-md-5 re-padd-right">
										<div class="role">
											<div class="role-title">
												<i class="title-icon"></i>角色
											</div>
											<div class="role-list">
												<div
													style="width: 100%; height: 32px; border-right: 1px solid rgb(221, 221, 221);"></div>
												<ul class="nav nav-tabs">
													<li class="active"><a href="#tab_1_1"
														data-toggle="tab"> 区域总监 <i class="icon delete-icon"><span
																class="delete-text">删除</span></i> <i class="icon query-icon"><span
																class="query-text">查看</span></i>
													</a></li>
													<li><a href="#tab_1_2" data-toggle="tab">财务总监<i
															class="icon delete-icon"><span class="delete-text">删除</span></i>
															<i class="icon query-icon"><span class="query-text">查看</span></i>
													</a></li>
													<li><a href="#tab_1_3" data-toggle="tab">售后部<i
															class="icon delete-icon"><span class="delete-text">删除</span></i>
															<i class="icon query-icon"><span class="query-text">查看</span></i>
													</a></li>
													<li><a href="#tab_1_3" data-toggle="tab">人资部<i
															class="icon delete-icon"><span class="delete-text">删除</span></i>
															<i class="icon query-icon"><span class="query-text">查看</span></i>
													</a></li>
												</ul>
												<a class="j_create_role btn btn-danger " data-toggle="modal"
													data-target="#exampleModal" data-whatever="@mdo"><i
													class="icon-add"></i>创建角色</a>
												<div class="add-role modal fade " id="exampleModal"
													tabindex="-1" role="dialog"
													aria-labelledby="exampleModalLabel">
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
																		<label for="recipient-name"
																			class="col-md-3 control-label">角色名称</label>
																		<div class="col-md-9 ">
																			<input type="text" placeholder="请填写角色名称"
																				class="form-control" id="recipient-name">
																		</div>
																	</div>
																	<div class="form-group">
																		<label for="message-text"
																			class="col-md-3 control-label">备注说明</label>
																		<div class="col-md-9">
																			<textarea class="form-control" rows="6"
																				placeholder="请填写备注说明" id="message-text"></textarea>
																		</div>
																	</div>
																</form>
															</div>
															<div class="modal-footer">
																<div class="col-md-3 col-md-offset-8">
																	<button type="button" class="btn col-xs-12 btn-danger ">确定</button>
																</div>
=======
						<div class="col-xs-4 col-md-5 re-padd-right">
							<div class="role">
								<div class="role-title">
									<i class="title-icon"></i>角色
								</div>
								<div class="role-list">
								<table id="table_report" class="table table-striped table-bordered table-hover table-condensed" style="font-size: 10px;">
									<tbody>
									<c:choose>
										<c:when test="${not empty roles}">
										<c:forEach var="role" items="${roles}" varStatus="s">
											<tr class="am-active">
												<td width="5%" class="center">${s.index+1}</td>
												<td width="20%">${role.name}</td>
												<td style="width:10px;" ><a href="javascript:selRole(${role.id},'${role.name}');" class="query-text">查看</a></td>
												<td style="width:10px;"><a href="javascript:delRole(${role.id});" class="delete-text">删除</a></td>
												<td style="width:10px;"><a href="javascript:editRole2Menus(${role.id});" class=" delete-text">菜单权限</a></td>
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
			                    <div  id="pageNav" class="scott" align="center">
									<font color="#88af3f">共${totalCount} 条数据，  共${totalPage} 页</font>  ${pageNav}
			                    </div>
			                    
			                    </td>
			                    <td></td>
			                </tr>
			      </table>
									<a class="j_create_role btn btn-danger " data-toggle="modal"
										data-target="#exampleModal" data-whatever="@mdo"><i
										class="icon-add"></i>创建角色</a>
									<div class="add-role modal fade " id="exampleModal"
										tabindex="-1" role="dialog"
										aria-labelledby="exampleModalLabel">
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
													<form action='role/addRole' method='post'>
														<div class="form-group">
															<label for="recipient-name"
																class="col-md-3 control-label">角色名称</label>
															<div class="col-md-9 ">
																<input type="text" placeholder="请填写角色名称" name ="name" class="form-control" id="recipient-name">
<<<<<<< HEAD
=======
>>>>>>> refs/heads/baozhen
>>>>>>> refs/heads/ChenGuop
															</div>
														</div>
<<<<<<< HEAD
													</div>
=======
														<div class="form-group">
															<label for="message-text" class="col-md-3 control-label">备注说明</label>
															<div class="col-md-9">
																<textarea class="form-control" rows="6"
																	placeholder="请填写备注说明"  name="description"  id="message-text"></textarea>
															</div>
														</div>
														<div class="modal-footer">
															<div class="col-md-3 col-md-offset-8"><input type="submit" value="确定" class="btn col-xs-12 btn-danger "/></div>
														</div>
														<div class="modal-footer">
															<div class="col-md-3 col-md-offset-8"><input type="submit" value="确定" class="btn col-xs-12 btn-danger "/></div>
														</div>
													</form>
<<<<<<< HEAD
=======
>>>>>>> refs/heads/baozhen
>>>>>>> refs/heads/ChenGuop
												</div>
											</div>
										</div>
									</div>
<<<<<<< HEAD
=======
<<<<<<< HEAD
									<div class="col-xs-8 col-md-7 re-padd-left">
										<div class="setting">
											<div class="setting-title">
												<i class="title-icon"></i> 权限设置 <i
													class="visible-no pull-right">不可见</i> <i
													class="visible pull-right">可见</i>
											</div>
											<div class="setting-list tab-content">
												<div class=" tab-pane  active" id="tab_1_1">
													<!--div滚动条 class='j_scroller'-->
													<div class="j_scroller"
														style="overflow: hidden; width: auto; height: 500px;">
														<div class="divide-32"></div>
														<ul class="nav nav-purview">
															<li class="active"><a
																class="j_dropdown qx-second-box"><i
																	class="icon dropdown-icon"></i> 基础设置模块</a>
																<ul class="qx-second">
																	<li class=""><a> <i
																			class="icon second-icon qxhf-icon"></i>区域划分 <label
																			class="pull-right"> <input type="radio"
																				name="qyhf" checked value="no-visible" /> <i
																				class="j_radio_setting pull-right icon visible-icon"></i>
																		</label> <label class="pull-right"> <input
																				type="radio" name="qyhf" value="visible" /> <i
																				class="j_radio_setting pull-right icon visible-no-icon"></i>
																		</label>
																	</a></li>
																	<li><a><i class="icon second-icon zzjg-icon"></i>组织结构
																			<label class="pull-right"> <input
																				type="radio" name="qyhf" checked value="no-visible" />
																				<i
																				class="j_radio_setting pull-right icon visible-icon"></i>
																		</label> <label class="pull-right"> <input
																				type="radio" name="qyhf" value="visible" /> <i
																				class="j_radio_setting pull-right icon visible-no-icon"></i>
																		</label> </a></li>
																	<li><a><i class="icon second-icon qxse-icon"></i>权限设置
																			<label class="pull-right"> <input
																				type="radio" name="qyhf" checked value="no-visible" />
																				<i
																				class="j_radio_setting pull-right icon visible-icon"></i>
																		</label> <label class="pull-right"> <input
																				type="radio" name="qyhf" value="visible" /> <i
																				class="j_radio_setting pull-right icon visible-no-icon"></i>
																		</label> </a></li>
																	<li><a><i class="icon second-icon zhgl-icon"></i>账号管理
																			<label class="pull-right"> <input
																				type="radio" name="qyhf" checked value="no-visible" />
																				<i
																				class="j_radio_setting pull-right icon visible-icon"></i>
																		</label> <label class="pull-right"> <input
																				type="radio" name="qyhf" value="visible" /> <i
																				class="j_radio_setting pull-right icon visible-no-icon"></i>
																		</label> </a></li>
																</ul></li>
															<li class=""><a class="qx-second-box j_dropdown"><i
																	class="icon dropdown-icon"></i> 业务管理模块</a>
																<ul class="qx-second">
																	<li class=""><a> <i
																			class="icon second-icon qxhf-icon"></i>区域划分 <label
																			class="pull-right"> <input type="radio"
																				name="qyhf" checked value="no-visible" /> <i
																				class="j_radio_setting pull-right icon visible-icon"></i>
																		</label> <label class="pull-right"> <input
																				type="radio" name="qyhf" value="visible" /> <i
																				class="j_radio_setting pull-right icon visible-no-icon"></i>
																		</label>
																	</a></li>
																	<li><a><i class="icon second-icon zzjg-icon"></i>组织结构
																			<label class="pull-right"> <input
																				type="radio" name="qyhf" checked value="no-visible" />
																				<i
																				class="j_radio_setting pull-right icon visible-icon"></i>
																		</label> <label class="pull-right"> <input
																				type="radio" name="qyhf" value="visible" /> <i
																				class="j_radio_setting pull-right icon visible-no-icon"></i>
																		</label> </a></li>
																	<li><a><i class="icon second-icon qxse-icon"></i>权限设置
																			<label class="pull-right"> <input
																				type="radio" name="qyhf" checked value="no-visible" />
																				<i
																				class="j_radio_setting pull-right icon visible-icon"></i>
																		</label> <label class="pull-right"> <input
																				type="radio" name="qyhf" value="visible" /> <i
																				class="j_radio_setting pull-right icon visible-no-icon"></i>
																		</label> </a></li>
																	<li><a><i class="icon second-icon zhgl-icon"></i>账号管理
																			<label class="pull-right"> <input
																				type="radio" name="qyhf" checked value="no-visible" />
																				<i
																				class="j_radio_setting pull-right icon visible-icon"></i>
																		</label> <label class="pull-right"> <input
																				type="radio" name="qyhf" value="visible" /> <i
																				class="j_radio_setting pull-right icon visible-no-icon"></i>
																		</label> </a></li>
																</ul></li>
															<li><a class="qx-second-box"><i
																	class="icon cpxlmk-icon"></i> 产品销量模块 <label
																	class="pull-right"> <input type="radio"
																		name="qyhf" checked value="no-visible" /> <i
																		class="j_radio_setting pull-right icon visible-icon"></i>
																</label> <label class="pull-right"> <input type="radio"
																		name="qyhf" value="visible" /> <i
																		class="j_radio_setting pull-right icon visible-no-icon"></i>
																</label> </a></li>
														</ul>
													</div>
												</div>

												<div class="tab-pane fade" id="tab_1_2">第二个</div>
												<div class="tab-pane fade" id="tab_1_3">
													<div class="divide-32"></div>
													<p>Yesterday I was on my way to class, when a black cat
														fell from the sky. I didn't really know what that nonsense
														was about so I asked him if I could step around him
														because he was bad luck, but he simply meowed and then
														disappeared. I was a bit worried that maybe he'd
														teleported to somewhere dangerous, but a wizard came and
														assured me that it was alright. I threw my Zune at him
														because I was 78% sure he was lying. The wizard roared at
														me and sentenced my mother to thirty five years of chain
														smoking. I was sad. [do]</p>
												</div>
											</div>
										</div>
									</div>
=======
>>>>>>> refs/heads/baozhen
>>>>>>> refs/heads/ChenGuop
								</div>

							</div>
						</div>
					</div>
					<!-- 		/row -->

				</div>
				<!-- row end -->
			</div>
		</div>
<<<<<<< HEAD
=======
<<<<<<< HEAD
=======
>>>>>>> refs/heads/ChenGuop
	</div>
	</div>
>>>>>>> refs/heads/baozhen
	</div>

</body>
	<script src="../static/js/jquery.min.js"></script>
	<script type="text/javascript">window.jQuery || document.write("<script src='../static/js/jquery.min.js'>\x3C/script>");</script>
	<script src="../static/js/script.js"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		/* 查看 */
		function selRole(roleId,roleName) {
			alert(111);
			var url = "role/selByRole?id="+roleId+"&name="+ roleName;
			window.location = encodeURI(url);
		}
		/* 删除 角色*/
		function delRole(id) {
			var url = "role/delRole?id=" + id;
			$.post(url, function(data) {
				if (data === 'suc') {
					alert("删除成功!");
					location.reload();
				}else{
					alert("删除失败!");
				}
			});
		}
		/*授权菜单	*/
		function editRole2Menus(roleId) {
			var url = "role/auth?rId="+roleId;
			window.location = encodeURI(url);
		}
	</script>
<<<<<<< HEAD
</html>
=======
</html>
>>>>>>> refs/heads/ChenGuop
