<%@ page language="java" import="java.util.*,com.wangge.buzmgt.saojie.entity.*,com.wangge.buzmgt.region.entity.*,com.wangge.buzmgt.teammember.entity.*,net.sf.json.JSONArray" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>考核设置</title>
		<!-- Bootstrap -->
		
		<link href="/static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="/static/kaohe/kaohe-set.css" />
		<script src="/static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
		<script type="text/javascript" src="http://www.daimajiayuan.com/download/jquery/jquery-1.10.2.min.js"></script>
		<link   rel="stylesheet" type="text/css" href="/static/bootstrap/css/bootstrap-select.css">
		<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<script src="/static/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/static/bootstrap/js/bootstrap-select.js"></script>
		<script type="text/javascript">
        $(window).on('load', function () {

            $('.selectpicker').selectpicker({
                'selectedText': 'cat'
            });

            // $('.selectpicker').selectpicker('hide');
        });
    </script>
	</head>

	<body>
	
	
		<div class="content main">
			<h4 class="team-member-header page-header ">
				<i class="icon icon-add-set"></i>添加设置
			</h4>
			<div class="row">
				<div class="col-md-12">
					<!--box-->
					<div class="saojie-upd-body box border blue">
						<!--title-->
						<div class="box-title">
							<h4>设置</h4>
						</div>
						<!--title-->
						<!--box-body-->
						<form class="member-from-box form-horizontal"
									action="/assess/saveAssess/${salesman.id}?stage=+${stage=1 }+" name="form" method="post" onsubmit="javascript:return checkForm()">
						<div class="box-body form-horizontal">
							<!--内容-->
							<div class="form-group">
								<label class="col-sm-2 control-label">考核人:</label>
								<div class="col-sm-10" data-aa="${salesman.id}" id="salId">
									<p class="form-control-static">${salesman.truename }</p>
								</div>
							</div>
							<div class="hr"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">考核阶段:</label>
								<div class="col-sm-2">
									<input class="form-control input-sm" type="text" name="assessStageSum" id="assessStage" value="" />
								</div>
								<label class="" style="padding-top:3px">个</label>
								<label class="pull-right col-md-8 control-label msg-error">请填写考核阶段</label>
							</div>
							<div class="hr"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">负责区域:</label>
								<div class="col-sm-10">
									<p class="form-control-static">${salesman.region.parent.parent.parent.name} ${salesman.region.parent.parent.name} ${salesman.region.parent.name} ${salesman.region.name}</p>
								</div>
							</div>
							<div class="hr"></div>
							<div class="form-group  marg-b-10">
								<label class="col-sm-2 control-label">考核设置:</label>
							</div>
							<!--考核设置内容-->
							<div class="form-group">
								<div class="saojie-upd-list col-sm-10  col-sm-offset-2 col-xs-12">
								<input type="hidden" id="salesmanId" name="salesmanId" value="${salesman.id}"/>
								<input type="hidden" id="assesszh" name="assesszh" />
									<!--list-->
									<div class="table-responsive">
										<table class="table table-bordered">
											<thead>
												<tr>
													<th>考核区域</th>
													<th>考核指标</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td class="average-tr ">
													
<!-- 														<div class="col-sm-4" > -->
<!-- 															<select class="form-control selectpicker" id="assessAreaId" name="assessArea" multiple > -->
<!-- 																<option selected>行政区域</option> -->
<%-- <%-- 																	<c:foreach items="${list}" var ="li" varStatus="status"> --%> 
<%-- <%-- 																	<c:if text="${status.first}">我是第一个元素</c:if> --%> 
<%-- <%-- 																	<c:if text="${status.last}">我是最后一个元素</c:if> --%> 
<%-- <%-- 																	</c:foreach> --%> 
<%-- 																<%  --%>
<!-- // 																List<Object> listR=(List<Object>)request.getAttribute("listAdminDivision"); -->
<!-- // 																	if(null!=listR){ -->
<%-- 																	  for(int i=0;i<listR.size();i++){%> --%>
<%-- 																	  <option value="<%=JSONArray.fromObject(listR.get(i)).get(0) %>"><%=JSONArray.fromObject(listR.get(i)).get(1)%></option> --%>
<%-- 																	<%  } --%>
<!--  																	} -->
<%-- 																%> --%>
<!-- 															</select> -->
<!-- 														</div> -->
													
													
													
														<div class="col-sm-4">
															<a class="J_addDire btn btn-default btn-kaohe-add col-sm-4" id="btn"><i class="icon-saojie-add"></i></a>
														</div>
													<label class="pull-right col-md-6 control-label msg-error">请选择考核区域</label>
													</td>
													<td class="average-tr target form-inline">
														<div class="col-sm-5">
															<div class="form-group">
																<label class="">活跃数</label>
																<input class="form-control input-sm" type="" name="assessActivenum" id="activeNum" value="" />
																<label class="">家</label>
															</div>
															<label class="pull-right col-md-8 control-label msg-error">请填写活跃数</label>
														</div>
														<div class="col-sm-5">
															<div class="form-group">
																<label class="">提货量</label>
																<input class="form-control input-sm" type="" name="assessOrdernum" id="orderNum" value="" />
																<label class="">部</label>
															</div>
															<label class="pull-right col-md-8 control-label msg-error">请填写提货量</label>
														</div>
													</td>
												</tr>
											</tbody>
										</table>
										<!--/table-->
									</div>
									<!--/list-->
								</div>
							</div>
							<!--考核设置内容-->

							<!--考核内容-->
							<!--分隔,清除浮动-->
							<div class="clearfix"></div>
							<div class="hr"></div>
							<!--考核周期-->
							<div class="form-group kaohe-week">
								<label class="col-sm-2 control-label">考核阶段周期:</label>
								<div class="input-group col-sm-2">
									<span class="input-group-addon" id="basic-addon1"><i class="ico icon-kaohe-week"></i></span>
									<input id="assessCycle" type="text" class="form-control" aria-describedby="basic-addon1" name="assessCycle" value="">
									<span class="input-group-addon">天</span>
								</div>
								<label class="pull-right col-md-6 control-label msg-error">请填写考核周期</label>
							</div>
							<!--考核周期-->
							<div class="hr"></div>
							<!--考核开始时间-->
							<div class="form-group kaohe-week-start">
								<label class="col-sm-2 control-label">考核开始时间:</label>
								<div class="input-group col-sm-2">
									<span class="input-group-addon" id="basic-addon1"><i class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
									<input id="assessTime" type="text" class="form-control form_datetime" readonly="readonly" name="assessTime" value="">
								</div>
								<label class="pull-right col-md-6 control-label msg-error">请选择考核开始时间</label>
							</div>
							<!--考核开始时间-->
							<div class="hr"></div>
							<div class="form-group">
								<div class="col-sm-10 col-sm-offset-2 input-group">
									<button class="btn btn-audit col-sm-2" type="submit">确定</button>
								</div>
							</div>
							<div class="clearfix"></div>
							<div class="hr"></div>
							</form>
							<!--扫街设置地图-->
							<div class="saojie-set-map col-sm-10  col-sm-offset-1 col-xs-12">
								<div class="map-box " style="height: 700px" id="allmap">
<!-- 									<img style="width: 100%;" src="static/img/background/saojie-set-map.png" /> -->
								</div>
							</div>
							<!--/扫街设置地图-->
							<!--/内容-->
						</div>
						<!--/box-body-->
					</div>
					<!--/box-->
				</div>
				<!--col-->
			</div>
		</div>
		<!--row-->
		<!-- Bootstrap core JavaScript================================================== -->
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
		<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- 		<script src="/static/js/jquery/jquery-1.11.3.min.js"></script> -->
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="/static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="/static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="/static/kaohe/kaohe-set.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
			$('body input').val('');
			$(".form_datetime").datetimepicker({
				format: "yyyy-mm-dd",
				language: 'zh-CN',
				weekStart: 1,
				todayBtn: 1,
				autoclose: 1,
				todayHighlight: 1,
				startView: 2,
				minView: 2,
				pickerPosition: "bottom-left",
				forceParse: 0
			});
			
			
			
			/* 定位到区,以选择的区为中心点 */
			 <% String areaname=request.getAttribute("areaname").toString();
			 %>
				 var map = new BMap.Map("allmap");
			 	if("山东省"=="<%=areaname%>"){
			 		map.centerAndZoom(new BMap.Point(117.010765,36.704194), 14);
			 	}else{
			 		var name ="<%=areaname%>";
			 		
			 		
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
											String jlng=listCoordinates[0].split("-")[0];
											String jlat=listCoordinates[0].split("-")[1];
										%>
						 				map.centerAndZoom(new BMap.Point(<%=jlng%>, <%=jlat%>), 12);
						 				map.enableScrollWheelZoom(true); 
										<%
					}else{%>
			 		
			 		
			 		var bdary = new BMap.Boundary();
			 		
			 		bdary.get(name, function(rs){ //获取行政区域
			 		var count = rs.boundaries.length; //行政区域的点有多少个

			 		for(var i = 0; i < count; i++){
			 		var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight:1, strokeColor: "red", fillColor: "", fillOpacity: 0.3}); //建立多边形覆盖物
			 		map.addOverlay(ply); //添加覆盖物
			 		map.setViewport(ply.getPath()); //调整视野 
			 		} 
			 		map.centerAndZoom(name, 11);
			 		map.enableScrollWheelZoom(true); 
			 		}); 
			 		
			 		<%}%>
			 		
			 		  	/* 迭代每个镇轮廓 */
			 		 <%		
			 		 		if(null!=request.getAttribute("salesman")){
			 		 		List<Region> listRegion=(List<Region>)request.getAttribute("regionData");
			 		 		if(listRegion != null && listRegion.size()>0){
								for(int i=0;i<listRegion.size();i++){
								String coordinates=listRegion.get(i).getCoordinates();
								String name=listRegion.get(i).getName();
								String centerPoint=listRegion.get(i).getCenterPoint();
								String[] listCoordinates = null;
								if(null != coordinates){
								  listCoordinates=coordinates.split("=");
								}
					 			
					 %> 
					 			var polygon = new BMap.Polygon([
					 	<%		if(listCoordinates != null && listCoordinates.length>0){
									for(int x=0;x<listCoordinates.length;x++){
										String points=listCoordinates[x];
										  double lng = Double.parseDouble(points.split("-")[0]);//经度 
										  double lat = Double.parseDouble(points.split("-")[1]);//纬度 
						 %>				
						 		  		<%
						 		  			if(x==listCoordinates.length-1){%>
						 		  			new BMap.Point(<%=lng%>,<%=lat%>)
						 		  			<%}else{%>
						 		  			 new BMap.Point(<%=lng%>,<%=lat%>),
						 		  			<%}
						 		  		%>
						 
						 
						 <%
									}}%>
									], {strokeColor:"blue", strokeWeight:2,fillColor: "red", strokeOpacity:0.5});  //创建多边形
					 				map.addOverlay(polygon); 	
									
					 				//色块上的文字shuomi
					 				<%
											if(null !=centerPoint){
											double lng = Double.parseDouble(centerPoint.split("-")[0]);//经度 
											double lat = Double.parseDouble(centerPoint.split("-")[1]);//纬度%>
											var secRingCenter = new BMap.Point(<%=lng%>,<%=lat%>)
											var secRingLabel2 = new BMap.Label("<%=name%>",{offset: new BMap.Size(10,-30), position: secRingCenter});
											secRingLabel2.setStyle({"line-height": "20px", "text-align": "center", "width": "80px", "height": "29px", "border": "none", "padding": "2px","background": "url(http://jixingjx.com/mapapi/ac.gif) no-repeat",});
											map.addOverlay(secRingLabel2);
									<%}
									 
					 			}
					 		}}%>
			 	}
			
		</script>
	</body>

</html>
