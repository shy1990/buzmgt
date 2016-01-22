<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>扫街设置列表</title>
<!-- Bootstrap -->
<link href="../static/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="../static/saojie/saojie.css" />
<script src="../static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
		var status = $("#addClass").val();
		console.info(status);
		if (status != null && status != '') {
			$("li[title = '" + status + "']").addClass("active");
		} else {
			$("li[title = '全部']").addClass("active");
		}
	});
</script>
</head>
<body>
			<div class="main">
				<h4 class="team-member-header page-header ">
					<div class="row">
						<div class="col-sm-12">
							<i class="icon team-member-list-icon"></i>扫街设置
							<!--区域选择按钮-->
							<div class="btn-group sr-only">
								<button type="button" class="btn btn-default ">
									<i class="icon province-icon"></i>山东省
								</button>
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">
									<span class="caret"></span> <span class="sr-only">Toggle
										Dropdown</span>
								</button>
								<ul class="dropdown-menu">
									<li><a href="#">Action</a></li>
									<li><a href="#">Another action</a></li>
									<li><a href="#">Something else here</a></li>
									<li role="separator" class="divider"></li>
									<li><a href="#">Separated link</a></li>
								</ul>
							</div>
							<!--/区域选择按钮-->
							<button class="btn btn-warning member-add-btn" type="button"
								onclick="javascript:window.location.href='/saojie/toAdd'">
								<i class="icon icon-add"></i>添加扫街
							</button>
							<small class="header-text">共<span class="text-red">203</span>个区域
							</small>
							<!-- <small class="header-text">今日新增<span class="text-red"> 0 +</span></small> -->
						
						</div>
					</div>
				</h4>
				<div class="row">
					<div class="col-md-12">
						<!--box-->
						<div class="team-member-body box border red">
							<!--title-->
							<div class="box-title">
								<div class="row">
									<div class="col-sm-7 col-md-5">
										<!--菜单栏-->
										<input type="hidden" name="" value="${saojieStatus }"
											id="addClass" />
										<ul class="nav nav-tabs">
											<li title="全部"><a title="全部" name="status"
												onclick="getAllSaojieList();" href="#box_tab1"
												data-toggle="tab"><i class="fa fa-circle-o"></i> <span
													class="hidden-inline-mobile">全部</span></a></li>
											<li title="扫街中"><a title="扫街中" name="status"
												onclick="getSaojieList(this.title,this.name);"
												href="#box_tab1" data-toggle="tab"><i
													class="fa fa-laptop"></i> <span
													class="hidden-inline-mobile">扫街中</span></a></li>
											<li title="已完成"><a title="已完成" name="status"
												onclick="getSaojieList(this.title,this.name);"
												href="#box_tab1" data-toggle="tab"><i
													class="fa fa-calendar-o"></i> <span
													class="hidden-inline-mobile">已完成</span></a></li>
										</ul>
										<!--/菜单栏-->
									</div>
									<div
										class="col-sm-4 col-md-3 col-lg-2 col-md-offset-4 col-lg-offset-5">
										<div class="form-group title-form">
											<div class="input-group ">
												<input type="text" class="form-control"
													placeholder="请输入名称或工号" id="param"> <span
													class="input-group-addon" id="goSearch"
													onclick="getSaojieList(this.value,this.id);"><i
													class="icon icon-finds"></i></span>
											</div>
										</div>
									</div>
								</div>
								<!--<div class="title-form input-group ">
								<input class="form-control input-sm" type="text" name="" id="" value="" />
								<span class=""><i class="icon icon-finds"></i></span>
							</div>-->
								<!--from-->
								<!--<h4 style="text-align: right;"><i class="fa fa-columns"></i><span class="hidden-inline-mobile">Tabs on Color Header</span></h4>-->
							</div>
							<!--title-->
							<!--box-body-->
							<div class="box-body">
								<!--列表内容-->
								<div class="tab-content">
									<!--全部-->
									<div class="tab-pane fade in active" id="box_tab1">
										<!--box-list-->
										<div class="box-list">
											<div class="project-list">
												<table class="table table-hover">
													<tbody>
													<c:if test="${empty list.content}">
													<div style="text-align: center;">
														<ul class="pagination">
															<tr>
																<td colspan="100">没有相关数据</td>
															</tr>
														</ul>
													</div>
													</c:if>
													<c:if test="${not empty list.content}">
														<c:forEach var="saojie" items="${list.content}"
															varStatus="s">
															<tr>
																<td class="project-people"><a href=""><img
																		alt="image" class="img-circle"
																		src="../static/img/saojie/a.jpg"></a></td>
																<td class="project-title"><a
																	href=""><strong>${saojie.salesman.truename}</strong>(${saojie.salesman.user.organization.name})</a>
																	<br /> <span>${saojie.salesman.region.name}</span></td>
																<c:if test="${saojie.status == 'PENDING' }">
																	<td class="project-status"><span
																		class="status-ing">${saojie.status.name}</span></td>
																</c:if>
																<c:if test="${saojie.status == 'AGREE' }">
																	<td class="project-status"><span
																		class="status-finish">扫街完成</span></td>
																</c:if>
																<td class="project-title"><span class="l-h">${saojie.region.name}：<strong
																		class="shop-num">${saojie.minValue}家</strong></span></td>
																<td class="project-completion">
																	<div>
																		<span class="completion-ing">当前进度： 48%</span> <span
																			class="time-down"> 倒计时：2天</span>
																	</div>
																	<div class="progress progress-mini">
																		<div style="width: 48%;" class="progress-bar"></div>
																	</div>
																	<!-- 100%的用这个 -->
																	<!-- <div>
																		<span class="completion-ing">当前进度： 100%</span> <span
																			class="time-finish"> 通过</span>
																	</div>
																	<div class="progress progress-mini">
                                                    					<div style="width: 100%;" class="progress-finish"></div>
                                                					</div>-->
																</td>
																<td class="project-actions"><a
																	href="projects.html#" class="btn btn-white btn-sm"><span
																		class="folder"></span> 查看 </a>
																	<div class="btn-group"></div>
																	<a href="/saojie/toSaojieInstall?id=+${saojie.salesman.id }+" class="btn btn-white btn-sm"><span
																		class="folder"></span> 设置 </a>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
												</c:if>
											</div>
											<c:if test="${not empty list.content}">
												<div style="text-align: center;padding-bottom: 20px" >
													<ul class="pagination box-page-ul">
														<li><a
															href="javascript:getPageList('${list.number > 0 ? list.number-1 : 0}')">&laquo;</a></li>
														<!-- 1.total<=7 -->
														<c:if test="${list.totalPages<=7 }">
															<c:forEach var="s" begin="1" end="${list.totalPages}"
																step="1">
																<c:choose>
																	<c:when test="${list.number == s-1 }">
																		<li class="active"><a
																			href="javascript:getPageList('${s-1}')">${s}</a></li>
																	</c:when>
																	<c:otherwise>
																		<li><a href="javascript:getPageList('${s-1}')">${s}</a></li>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</c:if>
														<c:if test="${list.totalPages>7 && list.number<4 }">
															<c:forEach var="s" begin="1" end="6" step="1">
																<c:choose>
																	<c:when test="${list.number == s-1 }">
																		<li class="active"><a
																			href="javascript:getPageList('${s-1}')">${s}</a></li>
																	</c:when>
																	<c:otherwise>
																		<li><a href="javascript:getPageList('${s-1}')">${s}</a></li>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
															<li><a href="javascript:void(0)">...</a></li>
														</c:if>
														<c:if
															test="${list.totalPages>7&&list.number>=4&&list.totalPages-list.number>=3 }">
															<li><a href="javascript:void(0)">...</a></li>
															<c:forEach var="s" begin="${list.number-2 }"
																end="${list.number+2 }" step="1">
																<c:choose>
																	<c:when test="${list.number == s-1 }">
																		<li class="active"><a
																			href="javascript:getPageList('${s-1}')">${s}</a></li>
																	</c:when>
																	<c:otherwise>
																		<li><a href="javascript:getPageList('${s-1}')">${s}</a></li>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
															<li><a href="javascript:void(0)">...</a></li>
														</c:if>
														<c:if
															test="${list.totalPages>7&&list.number>=4&&list.totalPages-list.number<3 }">
															<li><a href="javascript:void(0)">...</a></li>
															<c:forEach var="s" begin="${list.totalPages-6 }"
																end="${list.totalPages }" step="1">
																<c:choose>
																	<c:when test="${list.number == s-1 }">
																		<li class="active"><a
																			href="javascript:getPageList('${s-1}')">${s}</a></li>
																	</c:when>
																	<c:otherwise>
																		<li><a href="javascript:getPageList('${s-1}')">${s}</a></li>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</c:if>
														<li><a
															href="javascript:getPageList('${list.number+1 > list.totalPages-1 ? list.totalPages-1 : list.number+1}')">&raquo;</a></li>
													</ul>
												</div>
											</c:if>
										</div>
										<!--/box-list-->
									</div>
									<!--扫街中-->
								</div>
								<!--/列表内容-->
							</div>
							<!--/box-body-->
						</div>
						<!--/box-->
					</div>
				</div>
				<!-- /CALENDAR -->
			</div>
	<!-- Bootstrap core JavaScript================================================== -->
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="../static/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/static/js/common.js"></script>
	<script src="../static/js/saojie/saojie.js" type="text/javascript"
		charset="UTF-8"></script>
</body>
</html>
