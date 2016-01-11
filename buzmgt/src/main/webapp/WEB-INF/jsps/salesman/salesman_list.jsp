<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">


<title>业务管理后台项目</title>
<meta name="keywords" content="bootstrap响应式后台">
<meta name="description" content="">

<link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/static/yw-team-member/team-memberAdd.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css" href="/static/yw-team-member/team-member.css" />
</head>

<body>
			<div class="conter main">
				<h4 class="team-member-header page-header">
							<i class="icon team-member-list-icon"></i>团队成员
							<!--区域选择按钮-->
							<!-- 隐藏区域选择按钮 -->
							<div class="btn-group sr-only" >
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
							<a href="/salesman/toAdd" class="btn btn-warning " type="button">
								<i class="icon icon-add"></i>添加成员
							</a>
						<div class="col-sm-6 sr-only">
							<small class="header-text">共<span class="text-red">203</span>位成员
							</small> <small class="header-text">今日新增<span class="text-red">
									0 +</span></small>
						</div>
				</h4>
						<!--box-->
						<div class="team-member-body box border red">
							<!--title-->
							<div class="box-title">
								<div class="row">
									<div class="col-sm-7 col-md-5">
										<!--菜单栏-->
										<ul class="nav nav-tabs">
											<li><a title="扫街中" name="salesmanStatus" href="#box_tab3" onclick="getList(this.title,this.name);" data-toggle="tab"><i
													class="fa fa-circle-o"></i> <span
													class="hidden-inline-mobile">扫街中</span></a></li>
											<li><a title="考核中" name="salesmanStatus" href="#box_tab1" onclick="getList(this.title,this.name);" data-toggle="tab"><i
													class="fa fa-laptop"></i> <span
													class="hidden-inline-mobile">考核中</span></a></li>
											<li><a title="开发中" name="salesmanStatus" href="#box_tab1" onclick="getList(this.title,this.name);" data-toggle="tab"><i
													class="fa fa-calendar-o"></i> <span
													class="hidden-inline-mobile">开发中</span></a></li>
											<li><a title="已转正" name="salesmanStatus" href="#box_tab1" onclick="getList(this.title,this.name);" data-toggle="tab"><i
													class="fa fa-calendar-o"></i> <span
													class="hidden-inline-mobile">已转正</span></a></li>
										</ul>
										<!--/菜单栏-->
									</div>
									<div
										class="col-sm-4 col-md-3 col-lg-2 col-md-offset-4 col-lg-offset-5">
										<div class="form-group title-form">
											<div class="input-group ">
												<input type="text" class="form-control"
													id = "param" placeholder="请输入名称或工号"> <span
													class="input-group-addon" id="goSearch" onclick="getList(this.value,this.id)"><i class="icon icon-finds"></i></span>
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
									<!--扫街中-->
									<div class="tab-pane fade in active" id="box_tab1">
										<!--box-list-->
										<div class="box-list">
											<!-- 列表内容 -->

											<div class="ibox">
												<div class="ibox-content">
													<div class="project-list">
														<table  class="table table-hover">
															<tbody id="salemanlist">
															<c:forEach var="salesman" items="${list.content}" varStatus="s">
																<tr>
																	<td class="project-people"><a href="projects.html"><img
																			alt="image" class="img-circle"
																			src="../static/img/team-member/a.jpg"></a></td>
																	<td class="project-title"><a
																		href="project_detail.html"><strong>${salesman.truename}</strong>(${salesman.user.organization.name})</a>
																		<br /> <span>${salesman.region.name}</span></td>
																	<td class="project-status"><span
																		class="status-ing">${salesman.salesmanStatus.name}</span></td>
																	<td class="project-title"><span class="l-h">大桥镇：<strong
																			class="shop-num">20家</strong></span> <br /> <span>小桥镇：<strong
																			class="shop-num">10家</strong></span></td>
																	<td class="project-completion">
																		<div>
																			<span class="completion-ing">当前进度： 48%</span> <span
																				class="time-down"> 倒计时：2天</span>
																		</div>
																		<div class="progress progress-mini">
																			<div style="width: 48%;" class="progress-bar"></div>
																		</div>
																	</td>
																	<td class="project-actions"><a
																		href="projects.html#" class="btn btn-white btn-sm sr-only"><span
																			class="folder"></span> 查看 </a>
																		<div class="btn-group"></div></td>
																</tr>
															 </c:forEach>
															</tbody>
														</table>
														<c:if test="${not empty list.content}">
														<div style="text-align: center;">
															<ul class="pagination">
															  <li><a href="javascript:getPageList('${list.number > 0 ? list.number-1 : 0}')">&laquo;</a></li>
															    <c:forEach var="s" begin="1" end="${list.totalPages}" step="1">
																 <li><a href="javascript:getPageList('${s-1}')">${s}</a></li>
																</c:forEach>
																<li><a href="javascript:getPageList('${list.number+1 > list.totalPages-1 ? list.totalPages-1 : list.number+1}')">&raquo;</a></li> 
															</ul>
														</div>
														</c:if>
														<c:if test="${empty list.content}">
														<div style="text-align: center;">
															<ul class="pagination">
															  <tr>
															      <td colspan="100">没有相关数据</td>
															  </tr>
															</ul>
														</div>
														</c:if>
													</div>
												</div>
											</div>

											<!-- //列表内容 -->
										</div>
										<!--/box-list-->
									</div>
									
								</div>
								<!--/列表内容-->
							</div>
							<!--/box-body-->
						</div>
						<!--/box-->
			</div>

	<div class="wrapper wrapper-content animated fadeInUp">
		<div class="row">
			<div class="col-xs-12"></div>
			<!-- <div class="col-sm-3">
                <div class="ibox">
                	<div class="ibox-content">
	                       <div class="m-b-sm">
	                            <img alt="image" class="img-rounded" src="img/u83.png">
	                       </div>
                    </div>
                </div>
            </div>-->
		</div>
	</div>
	<%@include file="/static/js/alert/alert.html" %>
	<script src="/satic/js/jquery.min.js"></script>
	<script src="/static/bootstrap/js/bootstrap.min.js"></script>
    <script src="/static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script> 
	<!--<script>
       $(document).ready(function(){$("#loading-example-btn").click(function(){btn=$(this);simpleLoad(btn,true);simpleLoad(btn,false)})});function simpleLoad(btn,state){if(state){btn.children().addClass("fa-spin");btn.contents().last().replaceWith(" Loading")}else{setTimeout(function(){btn.children().removeClass("fa-spin");btn.contents().last().replaceWith(" Refresh")},2000)}};
    </script>-->
</body>
</html>
