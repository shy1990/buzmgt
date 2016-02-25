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
<link href="/static/bootStrapPager/css/page.css" rel="stylesheet">
<link href="/static/yw-team-member/team-memberAdd.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="/static/kaohe/kaohe-list.css" />
<!-- tree view -->
<link href="/static/CloudAdmin/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>
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
	<div class="conter main">
		<h4 class="team-member-header page-header">
			<i class="ico ico-kaohe-list"></i>考核开发
			<!--区域选择按钮-->
			<div class="area-choose">
				选择区域：<span>${regionName}</span> <a class="are-line" href="javascript:;"
					onclick="getRegion(${regionId});">切换</a>
			</div>
			<!--/区域选择按钮-->
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
									<input id="addClass" type="hidden" value="${assessStatus}">
									<li title="全部"><a title="全部"
										name="status" onclick="getAllAssessList(${regionId});" href="#box_tab1" data-toggle="tab"><i
											class="fa fa-circle-o"></i> <span
											class="hidden-inline-mobile">全部</span></a></li>
									<li title="考核中"><a title="考核中" name="status" onclick="getAssessList(this.title,this.name,${regionId});"
										href="#box_tab1" data-toggle="tab"><i class="fa fa-laptop"></i>
											<span class="hidden-inline-mobile">考核中</span></a></li>
									<li title="考核失败"><a title="考核失败" name="status" onclick="getAssessList(this.title,this.name,${regionId});"
										href="#box_tab1" data-toggle="tab"><i
											class="fa fa-calendar-o"></i> <span
											class="hidden-inline-mobile">考核失败</span></a></li>
								</ul>
								<!--/菜单栏-->
							</div>
							<div class="col-sm-4 col-md-3 col-md-offset-3 ">
								<div class="form-group title-form">
									<div class="input-group ">
										<input type="text" class="form-control" name="truename"
											id="param" placeholder="请输入名称或工号" onkeypress="return check()"> <a
											class="input-group-addon" id="goSearch"
											onclick="getAssessList(this.value,this.id,${regionId});"><i
											class="icon icon-finds"></i></a>
									</div>
								</div>
							</div>
						</div>
						<!-- row -->
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
													<tbody id="assesslist">
													<c:if test="${not empty list.content}">
													<c:forEach var="assess" items="${list.content}"
														varStatus="s">
														<!-- 
																考核状态标签:
																	一阶段考核: onekaohe-status-on/onekaohe-status-out
																	二阶段考核: twokaohe-status-on/twokaohe-status-out
																	三阶段考核: threekaohe-status-on/threekaohe-status-out
																	转正: overkaohe-status-on/overkaohe-status-out
																	失败: bustkaohe-status
																考核进度条:
																	一阶段考核: onekaohe-bar
																	二阶段考核: twokaohe-bar
																	三阶段考核: threekaohe-bar
																	转正进度条: overkaohe-bar
																	考核失败: bustkaohe-bar
																 -->
														<tr>
															<td class="project-people"><a href=""><img
																	alt="image" class="img-circle"
																	src="../static/img/team-member/a.jpg"></a></td>
															<td class="project-title"><a href=""><strong>${assess.salesman.truename}</strong>(${assess.salesman.user.organization.name})</a>
																<br /> <span>${assess.salesman.region.name}</span></td>
															<td class="project-title"><span class="l-h">提货量：<strong
																	class="shop-num">${assess.assessOrdernum }部</strong></span> <br /> <span>活跃客户：<strong
																	class="shop-num-d">${assess.assessActivenum }家</strong></span></td>
															<td class="project-completion"><span
																class="completion-ing">业务指标： 88%</span> <span><i
																	class="ico ico-time-down"></i> 倒计时：<span class="">2天</span></span>
																<c:if test="${assess.status == 'PENDING' && assess.assessStage == '1' }">
																<span class="kaohe-status onekaohe-status-on">一阶段考核中</span>
																</c:if>
																<c:if test="${assess.status == 'AGREE' && assess.assessStage == '1' }">
																<span class="kaohe-status onekaohe-status-out">一阶段考核通过</span>
																</c:if>
																<c:if test="${assess.status == 'FAIL' && assess.assessStage == '1' }">
																<span class="kaohe-status bustkaohe-status">一阶段考核失败</span>
																</c:if>
																<c:if test="${assess.status == 'PENDING' && assess.assessStage == '2' }">
																<span class="kaohe-status twokaohe-status-on">二阶段考核中</span>
																</c:if>
																<c:if test="${assess.status == 'AGREE' && assess.assessStage == '2' }">
																<span class="kaohe-status twokaohe-status-out">二阶段考核通过</span>
																</c:if>
																<c:if test="${assess.status == 'FAIL' && assess.assessStage == '2' }">
																<span class="kaohe-status bustkaohe-status">二阶段考核失败</span>
																</c:if>
																<c:if test="${assess.status == 'PENDING' && assess.assessStage == '3' }">
																<span class="kaohe-status threekaohe-status-on">三阶段考核中</span>
																</c:if>
																<c:if test="${assess.status == 'AGREE' && assess.assessStage == '3' }">
																<span class="kaohe-status threekaohe-status-out">三阶段考核通过</span>
																</c:if>
																<c:if test="${assess.status == 'FAIL' && assess.assessStage == '3' }">
																<span class="kaohe-status bustkaohe-status">三阶段考核失败</span>
																</c:if>
																<c:if test="${assess.assessStage == '1' }">
																<div class="progress progress-mini">
																	<div style="width: 88%;"
																		class="progress-bar onekaohe-bar"></div>
																</div></td>
																</c:if>
																<c:if test="${assess.assessStage == '2' }">
																<div class="progress progress-mini">
																	<div style="width: 88%;"
																		class="progress-bar twokaohe-bar"></div>
																</div></td>
																</c:if>
																<c:if test="${assess.assessStage == '3' }">
																<div class="progress progress-mini">
																	<div style="width: 88%;"
																		class="progress-bar threekaohe-bar"></div>
																</div></td>
																</c:if>
															<td class="project-actions"><a href="projects.html#"
																class="btn btn-white btn-sm "> <span class="folder"></span>
																	查看
															</a> <!-- Single button --></td>
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
													href="javascript:getPageList('${list.number > 0 ? list.number-1 : 0}','${regionId}','${truename}','${jobNum }','${assessStatus}')">&laquo;</a></li>
												<!-- 1.total<=7 -->
												<c:if test="${list.totalPages<=7 }">
													<c:forEach var="s" begin="1" end="${list.totalPages}"
														step="1">
														<c:choose>
															<c:when test="${list.number == s-1 }">
																<li class="active"><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${assessStatus}')">${s}</a></li>
															</c:when>
															<c:otherwise>
																<li><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${assessStatus}')">${s}</a></li>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
												<c:if test="${list.totalPages>7 && list.number<4 }">
													<c:forEach var="s" begin="1" end="6" step="1">
														<c:choose>
															<c:when test="${list.number == s-1 }">
																<li class="active"><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${assessStatus}')">${s}</a></li>
															</c:when>
															<c:otherwise>
																<li><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${assessStatus}')">${s}</a></li>
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
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${assessStatus}')">${s}</a></li>
															</c:when>
															<c:otherwise>
																<li><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${assessStatus}')">${s}</a></li>
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
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${assessStatus}')">${s}</a></li>
															</c:when>
															<c:otherwise>
																<li><a
																	href="javascript:getPageList('${s-1}','${regionId}','${truename}','${jobNum }','${assessStatus}')">${s}</a></li>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</c:if>
												<li><a
													href="javascript:getPageList('${list.number+1 > list.totalPages-1 ? list.totalPages-1 : list.number+1}','${regionId}','${truename}','${jobNum }','${assessStatus}')">&raquo;</a></li>
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
				<div class="member-district box border red">
					<!--title-->
					<div class="box-title">
						<i class="icon icon-district"></i>区域
					</div>
					<div class="box-body">
						<div style="height: 290px" id="allmap"></div>
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
	<script src="/static/bootstrap/js/bootstrap.min.js"></script>
	<!-- bootstrapPaeger -->
	<script src="/static/bootStrapPager/js/extendPagination.js"></script>
	<script src='/static/js/common.js'></script>
	<script src="/static/kaohe/kaohe.js" type="text/javascript" charset="utf-8"></script>
	<script>
		/*区域 */
		function getRegion(id){
			window.location.href='/region/getPersonalRegion?id='+id;
		}
//分页生成
		var regionId = $('#regionId').val();
        		var job = $('#truename').val();
        		var name = $('#jobNum').val();
        		var statu = $('#assessStatus').val();
		var totalCount = $('#total').val(); //总条数 
			showCount = 10, //显示分页个数
			limit =  1;//每页条数
// 		createTable(1, limit, totalCount);
		$('#callBackPager').extendPagination({
			totalCount : totalCount, 
			showCount : showCount,
			limit : limit,
			callback : function(curr, limit, totalCount) {
				alert("当前是第"+curr+"页,每页"+ limit+"条,总共"+ totalCount+"条");
					
		// 		createTable(1, limit, totalCount); //生成列表
			}
		});
	</script>
</body>
</html>
