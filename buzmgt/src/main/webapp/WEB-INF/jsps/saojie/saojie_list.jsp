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
<link href="/static/CloudAdmin/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<script src="../static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
<script type="text/javascript"src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
 <link rel="stylesheet" type="text/css" href="../static/zTree/css/zTreeStyle/organzTreeStyle.css" />
<link rel="stylesheet" type="text/css"	href="../static/yw-team-member/team-member.css" />
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
			<i class="icon icon-saojie-list"></i>扫街设置
			<!--区域选择按钮-->
			<div class="area-choose">
				选择区域：<span>${regionName}</span> <a class="are-line"
					href="javascript:;" onclick="getRegion(${regionId});">切换</a>
			</div>
			<!--/区域选择按钮-->
			<button class="btn btn-blue member-add-btn" type="button"
				onclick="javascript:window.location.href='/saojie/toAdd'">
				<i class="icon icon-add"></i>添加扫街
			</button>
			<small class="header-text">共<span class="text-red">${count }</span>个区域
			</small>
			<!-- <small class="header-text">今日新增<span class="text-red"> 0 +</span></small> -->

		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="team-member-body box border blue">
					<!--title-->
					<div class="box-title">
						<div class="row">

							<div class="col-sm-8 col-md-6">
								<!--菜单栏-->
								<input type="hidden" name="" value="${saojieStatus}"
									id="addClass" />
								<ul class="nav nav-tabs">
									<li title="全部"><a title="全部" name="status"
										onclick="getAllSaojieList(${regionId});" href="#box_tab1"
										data-toggle="tab"><i class="fa fa-circle-o"></i> <span
											class="hidden-inline-mobile">全部</span></a></li>
									<li title="扫街中"><a title="扫街中" name="status"
										onclick="getSaojieList(this.title,this.name,${regionId});"
										href="#box_tab1" data-toggle="tab"><i class="fa fa-laptop"></i>
											<span class="hidden-inline-mobile">扫街中</span></a></li>
									<li title="已完成"><a title="已完成" name="status"
										onclick="getSaojieList(this.title,this.name,${regionId});"
										href="#box_tab1" data-toggle="tab"><i
											class="fa fa-check"></i> <span
											class="hidden-inline-mobile">已完成</span></a></li>
								</ul>
								<!--/菜单栏-->
							</div>
							<div class="col-sm-4 col-md-3 col-md-offset-3 ">
								<div class="form-group title-form">
									<div class="input-group ">
										<input type="text" class="form-control" placeholder="请输入名称或工号"
											id="param" onkeypress="return check()"> <span class="input-group-addon"
											id="goSearch"
											onclick="getSaojieList(this.value,this.id,${regionId});"><i
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
									<div class="project-list table-responsive">
										<table class="table table-hover">
											<tbody>
												<c:if test="${not empty list.content}">
													<c:forEach var="saojie" items="${list.content}"
														varStatus="s">
														<tr>
															<td class="project-people"><a href=""><img
																	alt="image" class="img-circle"
																	src="../static/img/saojie/a.jpg"></a></td>
															<td class="project-title"><a href="javascript:toSalesManInfo('${saojie.id}','saojie');"><strong>${saojie.salesman.truename}</strong>(${saojie.salesman.user.organization.name})</a>
																<br /> <span>${saojie.salesman.region.name}</span></td>
															<c:if test="${saojie.status == 'PENDING' }">
																<td class="project-status"><span class="status-ing">${saojie.status.name}</span></td>
															</c:if>
															<c:if test="${saojie.status == 'AGREE' }">
																<td class="project-status"><span
																	class="status-finish">扫街完成</span></td>
															</c:if>
															<td class="project-title"><span class="l-h">${saojie.region.name}：<strong
																	class="shop-num">${saojie.minValue}家</strong></span></td>
															<td class="project-completion">
																<div>
																	<span class="completion-ing">当前进度：${saojie.percent}</span> <span
																		class="time-down"> 倒计时：${saojie.timing }天</span>
																</div>
																<div class="progress progress-mini">
																	<div style="width: <c:if test="${saojie.percent>='100%'}">100%;</c:if>
																	<c:if test="${saojie.percent<'100%'}">${saojie.percent};</c:if>
																		" class="progress-bar"></div>
																</div> <!-- 100%的用这个 --> <!-- <div>
																		<span class="completion-ing">当前进度： 100%</span> <span
																			class="time-finish"> 通过</span>
																	</div>
																	<div class="progress progress-mini">
                                                    					<div style="width: 100%;" class="progress-finish"></div>
                                                					</div>-->
															</td>
															<td class="project-actions"><a href="projects.html#"
																class="btn btn-white btn-sm"><span class="folder"></span>
																	查看 </a>
																<div class="btn-group"></div> <a
																href="/saojie/toSaojieInstall?id=+${saojie.salesman.id }+"
																class="btn btn-white btn-sm"><span class="folder"></span>
																	设置 </a></td>
														</tr>
													</c:forEach>
												</c:if>
											</tbody>
										</table>
									</div>
									<c:if test="${not empty list.content}">
										<div style="text-align: center; padding-bottom: 20px">
											<ul class="pagination box-page-ul">
												<li><a
													href="javascript:getPageList('${list.number > 0 ? list.number-1 : 0}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">&laquo;</a></li>
												<!-- 1.total<=7 -->
												<c:if test="${list.totalPages<=7 }">
													<c:forEach var="s" begin="1" end="${list.totalPages}"
														step="1">
														<c:choose>
															<c:when test="${list.number == s-1 }">
																<li class="active"><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">${s}</a></li>
															</c:when>
															<c:otherwise>
																<li><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">${s}</a></li>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
												<c:if test="${list.totalPages>7 && list.number<4 }">
													<c:forEach var="s" begin="1" end="6" step="1">
														<c:choose>
															<c:when test="${list.number == s-1 }">
																<li class="active"><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">${s}</a></li>
															</c:when>
															<c:otherwise>
																<li><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">${s}</a></li>
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
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">${s}</a></li>
															</c:when>
															<c:otherwise>
																<li><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">${s}</a></li>
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
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">${s}</a></li>
															</c:when>
															<c:otherwise>
																<li><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">${s}</a></li>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
												<li><a
													href="javascript:getPageList('${list.number+1 > list.totalPages-1 ? list.totalPages-1 : list.number+1}','${regionId}','${truename}','${jobNum }','${saojieStatus}')">&raquo;</a></li>
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
			<!-- end col-sm-9 -->
			<div class="col-md-3 ">
				<!--box-->
				<div class="member-district box border red">
					<!--title-->
					<div class="box-title">
						<i class="ico icon-district"></i>区域
					</div>
					<div class="box-body">
						<div style="height: 290px" id="allmap"></div>
						<div align="center">
							<!-- 							<a href="/salesman/showMap"><font color="#0099ff" size="3">查看完整地图</font></a> -->
						</div>
						<!-- 						地图 -->
						<!-- 						<img width="100%" src="/static/img/saojieap.png" /> -->
						<!-- 						/地图 -->
						<!-- 						组织结构 -->
								<div class="structure col-xs-12">
										<i class="icon icon-structure"></i> 组织结构
								</div>
						
								<div class=""  >
											<div class="role-list"  >
															<div
																style="width: 100%; height: 45px; border-right: 1px solid rgb(221, 221, 221);"></div>
															<ul id="organizationId" class="ztree" style="overflow:auto;overflow-x: auto"></ul>
											</div>
								</div>
						<!--/组织结构-->
					</div>
				</div>
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
	 <script src="../static/zTree/js/jquery.ztree.all-3.5.js" type="text/javascript" charset="utf-8"></script>
	<script src="../static/js/organization/teamOrganizationTree.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/static/js/common.js"></script>
	<script src="../static/yw-team-member/team-member.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="../static/js/saojie/saojie.js" type="text/javascript"
		charset="UTF-8"></script>
	<script type="text/javascript">
		<%String areaname = null;
			if (null != request.getAttribute("regionName")) {
				areaname = request.getAttribute("regionName").toString();
			}
			String parentid = null;
			if (null != request.getAttribute("parentid")) {
				parentid = request.getAttribute("parentid").toString();
			}%>
	 	var map = new BMap.Map("allmap");
		<%if (null != request.getAttribute("pcoordinates")) {%>
			<%String pcoordinates = request.getAttribute("pcoordinates").toString();
				String[] listCoordinates = pcoordinates.split("=");%> 
			 			var polygon = new BMap.Polygon([
			 	<%for (int x = 0; x < listCoordinates.length; x++) {
					String points = listCoordinates[x];
					double lng = Double.parseDouble(points.split("-")[0]);//经度 
					double lat = Double.parseDouble(points.split("-")[1]);//纬度%>				
	<%if (x == listCoordinates.length - 1) {%>
				 		  			new BMap.Point(<%=lng%>,<%=lat%>)
				 		  			<%} else {%>
				 		  			 new BMap.Point(<%=lng%>,<%=lat%>),
				 		  			<%}%>
				 <%}%>
							], {strokeColor:"blue", strokeWeight:2,fillColor: "", strokeOpacity:0.5});  //创建多边形
			 				map.addOverlay(polygon);
							<%String jlng = listCoordinates[1].split("-")[0];
							  String jlat = listCoordinates[1].split("-")[1];%>
							 var point = new BMap.Point(<%=jlng%>,<%=jlat%>);
							 map.centerAndZoom(point, 8);    
			 				//map.centerAndZoom(name, 8);
			 				map.enableScrollWheelZoom(true); 
			 				
			 				
			 				 var points =[
			 				   <%for (int y = 0; y < listCoordinates.length; y++) {
					String points = listCoordinates[y];
					double lng = Double.parseDouble(points.split("-")[0]);//经度 
					double lat = Double.parseDouble(points.split("-")[1]);//纬度%>          
					 		  		
			 					
				 		  		<%if (y == listCoordinates.length - 1) {%> 
				 		  		 		{"lng":<%=lng%>,"lat":<%=lat%>,"count":50}
				 		  			<%} else {%>
				 		  				{"lng":<%=lng%>,"lat":<%=lat%>,"count":50},
				 		  			<%}%> 
					 		  	 <%}%>
			 				              ];
	
			 				heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
			 				map.addOverlay(heatmapOverlay);
			 				heatmapOverlay.setDataSet({data:points,max:100});
			 				setTimeout(
			 						function(){
			 							  heatmapOverlay.show();
			 						},3000);
	//		 				polygon.addEventListener('click',function(e) {
	//		 				   var  point=JSON.stringify(e.pixel);
	//							  alert(point);
	<%-- 								  alert(<%=coordinates%>); --%>
	//						});
							<%} else {%>
			var bdary = new BMap.Boundary();
			bdary.get('<%=areaname%>', function(rs){ //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
	
			for(var i = 0; i < count; i++){
			var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight:1, strokeColor: "blue", fillColor: "", fillOpacity: 0.3}); //建立多边形覆盖物
			ply.setStrokeWeight(3);
			map.addOverlay(ply); //添加覆盖物
			map.setViewport(ply.getPath()); //调整视野 
			} 
			map.centerAndZoom('<%=areaname%>', 12);
			map.enableScrollWheelZoom(true); 
			}); 
		<%}%>
		
	
	
		/*区域 */
		function getRegion(id){
			window.location.href='/region/getPersonalRegion?id='+id+"&flag=saojie";
		}
	</script>
</body>
</html>
