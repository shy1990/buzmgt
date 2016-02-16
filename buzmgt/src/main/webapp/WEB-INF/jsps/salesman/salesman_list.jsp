<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css"
	href="/static/yw-team-member/team-member.css" />
<!-- tree view -->
<link href="/static/CloudAdmin/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/static/CloudAdmin/js/fuelux-tree/fuelux.min.css" />
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
 <script type="text/javascript" src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
</head>

<body>
	<div class="conter main">
		<h4 class="team-member-header page-header">
			<i class="icon team-member-list-icon"></i>团队成员
			<!--区域选择按钮-->
				<button type="button" class="btn btn-default" onclick="getRegion(${regionId});">
					<i class="icon province-icon"></i>${regionName}
				</button>
			<!--/区域选择按钮-->
			<a href="/teammember/toAdd" class="btn btn-warning " type="button">
				<i class="icon icon-add"></i>添加成员
			</a>
				<small class="header-text">共<span class="text-red">${list.totalElements}</span>位成员
				</small> <small style="display: none" class="header-text sr-only">今日新增<span class="text-red">
						0 +</span></small>
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
								<ul class="nav nav-tabs">
									<input id="status" type="hidden" value="${Status}">
									<li title="扫街中"><a title="扫街中" name="salesmanStatus"
										href="" onclick="getList(this.title,this.name,'${regionId}');"
										data-toggle="tab"><i class="fa fa-circle-o"></i> <span
											class="hidden-inline-mobile">扫街中</span></a></li>
									<li title="考核中"><a title="考核中" name="salesmanStatus"
										href="" onclick="getList(this.title,this.name,'${regionId}');"
										data-toggle="tab"><i class="fa fa-laptop"></i> <span
											class="hidden-inline-mobile">考核中</span></a></li>
									<li title="开发中"><a title="开发中" name="salesmanStatus"
										href="" onclick="getList(this.title,this.name,'${regionId}');"
										data-toggle="tab"><i class="fa fa-calendar-o"></i> <span
											class="hidden-inline-mobile">开发中</span></a></li>
									<li title="已转正"><a title="已转正" name="salesmanStatus"
										href="" onclick="getList(this.title,this.name,'${regionId}');"
										data-toggle="tab"><i class="fa fa-calendar-o"></i> <span
											class="hidden-inline-mobile">已转正</span></a></li>
								</ul>
								<!--/菜单栏-->
							</div>
							<div class="col-sm-4 col-md-3 col-md-offset-3 ">
								<div class="form-group title-form">
									<form action="/salesman/getSalesManList">
									<div class="input-group ">
										<input type="text" class="form-control" name="truename" id="param"
											placeholder="请输入名称或工号"> <a type="sumbit"
											class="input-group-addon" id="goSearch"
											onclick="getList(this.value,this.id)"><i
											class="icon icon-finds"></i></a>
									</div>
									</form>
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
											<div class="project-list table-responsive">
												<table class="table table-hover">
													<tbody id="salemanlist">
														<c:forEach var="salesman" items="${list.content}"
															varStatus="s">
															<tr>
																<td class="project-people"><a href=""><img
																		alt="image" class="img-circle"
																		src="../static/img/team-member/a.jpg"></a></td>
																<td class="project-title"><a href=""><strong>${salesman.truename}</strong>(${salesman.user.organization.name})</a>
																	<br /> <span>${salesman.region.name}</span></td>
																<td class="project-title"><span class="l-h">大桥镇：<strong
																		class="shop-num">20家</strong></span> <br /> <span>小桥镇：<strong
																		class="shop-num-d">10家</strong></span></td>
																<td class="project-completion col-md-5 col-sm-2">
																	<span class="completion-ing">当前进度： 88%</span> 
																	<span class="status-ing saojie-status-on">${salesman.salesmanStatus.name}</span>
																	<div class="progress progress-mini">
																		<div style="width: 88%;" class="progress-bar saojie-bar-on"></div>
																	</div>
																</td>
																<td class="project-actions"><a
																	href="projects.html#" class="btn btn-white btn-sm "><span
																		class="folder"></span> 查看 </a>
																	<!-- Single button -->
																	<div class="btn-group sr-only">
																		<button type="button"
																			class="btn btn-white btn-sm dropdown-toggle"
																			data-toggle="dropdown" aria-haspopup="true"
																			aria-expanded="false">
																			<i class="icon-edit"></i> 编辑 <span class="caret"></span>
																		</button>
																		<ul class="dropdown-menu saojie-edit">
																			<li><a href="#">扫街设置</a></li>
																			<li role="separator" class="divider"></li>
																			<li><a href="#">冻结账户</a></li>
																		</ul>
																	</div>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
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
			<div class="col-md-3 ">
				<!--box-->
				<div class="member-district box border gray">
					<!--title-->
					<div class="box-title">
						<i class="icon icon-district"></i>区域
					</div>
					<div class="box-body">
						<div style="height: 290px" id="allmap">
						</div>
<!-- 						<div align="center"><a href="/salesman/showMap"><font color="#0099ff" size="3">查看完整地图</font></a></div> -->
<!-- 						地图 -->
<!-- 						<img width="100%" src="/static/img/team-map.png" /> -->
<!-- 						/地图 -->
<!-- 						组织结构 -->
<!-- 						<div class="structure col-xs-12"> -->
<!-- 							<i class="icon icon-structure"></i> 组织结构 -->
<!-- 						</div> -->
<!-- 						tree view -->
<!-- 						<div id="tree3" class="tree"></div> -->
						<!--/组织结构-->
					</div>
				</div>
				<!--/team-map-->
			</div>
		</div>

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
	<%@include file="/static/js/alert/alert.html"%>
	<script src="/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="/static/yw-team-member/team-member.js"
		type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript"
		src="/static/CloudAdmin/js/fuelux-tree/fuelux.tree-sampledata.js"></script>
	<script type="text/javascript"
		src="/static/CloudAdmin/js/fuelux-tree/fuelux.tree.min.js"></script>
	<script src="/static/CloudAdmin/js/script.js"></script>
	<script src='/static/js/common.js'></script>
	<script>
		jQuery(document).ready(function() {
			App.setPage("treeview"); //Set current page
			App.init(); //Initialise plugins and elements
		});
	</script>
	<!--<script>
       $(document).ready(function(){$("#loading-example-btn").click(function(){btn=$(this);simpleLoad(btn,true);simpleLoad(btn,false)})});function simpleLoad(btn,state){if(state){btn.children().addClass("fa-spin");btn.contents().last().replaceWith(" Loading")}else{setTimeout(function(){btn.children().removeClass("fa-spin");btn.contents().last().replaceWith(" Refresh")},2000)}};
    </script>-->
      <script type="text/javascript">
	<%String areaname = request.getAttribute("regionName").toString();
		String parentid = null;
		if (null != request.getAttribute("parentid")) {
			parentid = request.getAttribute("parentid").toString();
		}
		%>
	 	var map = new BMap.Map("allmap");
		<%
		if(null!=request.getAttribute("pcoordinates")){%>
			<%
			String pcoordinates=request.getAttribute("pcoordinates").toString();
			String[] listCoordinates=pcoordinates.split("=");
			 %> 
			 			var polygon = new BMap.Polygon([
			 	<%
							for(int x=0;x<listCoordinates.length;x++){
								String points=listCoordinates[x];
								double lng=Double.parseDouble(points.split("-")[0]);//经度 
				 		  		double lat=Double.parseDouble(points.split("-")[1]);//纬度 
				 %>				
	<%
				 		  			if(x==listCoordinates.length-1){%>
				 		  			new BMap.Point(<%=lng%>,<%=lat%>)
				 		  			<%}else{%>
				 		  			 new BMap.Point(<%=lng%>,<%=lat%>),
				 		  			<%}
				 		  		%>
				 <%
							}%>
							], {strokeColor:"blue", strokeWeight:2,fillColor: "", strokeOpacity:0.5});  //创建多边形
			 				map.addOverlay(polygon);
							<%
								String jlng=listCoordinates[1].split("-")[0];
								String jlat=listCoordinates[1].split("-")[1];
							
							%>
							 var point = new BMap.Point(<%=jlng%>,<%=jlat%>);
							 map.centerAndZoom(point, 8);    
			 				//map.centerAndZoom(name, 8);
			 				map.enableScrollWheelZoom(true); 
			 				
			 				
			 				 var points =[
			 				   <%
			 				  for(int y=0;y<listCoordinates.length;y++){
									String points=listCoordinates[y];
									double lng=Double.parseDouble(points.split("-")[0]);//经度 
					 		  		double lat=Double.parseDouble(points.split("-")[1]);//纬度 
			 				   %>          
					 		  		
			 					
				 		  		<%
 				 		  			if(y==listCoordinates.length-1){%> 
				 		  		 		{"lng":<%=lng%>,"lat":<%=lat%>,"count":50}
				 		  			<%}else{%>
				 		  				{"lng":<%=lng%>,"lat":<%=lat%>,"count":50},
				 		  			<%}
				 		  		%> 
					 		  	 <%
									}%>
			 				              ];

			 				heatmapOverlay = new BMapLib.HeatmapOverlay({"radius":20});
			 				map.addOverlay(heatmapOverlay);
			 				heatmapOverlay.setDataSet({data:points,max:100});
			 				setTimeout(
			 						function(){
			 							  heatmapOverlay.show();
			 						},3000);
//			 				polygon.addEventListener('click',function(e) {
//			 				   var  point=JSON.stringify(e.pixel);
//								  alert(point);
	<%-- 								  alert(<%=coordinates%>); --%>
//							});
							<%
		}else{%>
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
			window.location.href='/region/getPersonalRegion?id='+id;
		}
		
		</script>
</body>
</html>
