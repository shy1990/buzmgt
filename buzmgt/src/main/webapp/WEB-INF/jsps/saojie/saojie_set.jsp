<%@ page language="java" import="java.util.*,com.wangge.buzmgt.region.entity.*"  contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>扫街设置</title>
		<!-- Bootstrap -->
		<link href="../static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="../static/saojie/saojie-set.css" />
		<script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
	</head>

	<body>
		<div class="content main">
			<h4 class="team-member-header page-header ">
				<div class="row">
					<div class="col-sm-12">
						<i class="icon icon-saojie-set"></i> 扫街设置
						<a href="/saojie/saojieList" class="btn btn-warning member-add-btn"
							type="button"> <i class="icon glyphicon glyphicon-share-alt"></i>
							返回列表
						</a>
					</div>
				</div>
			</h4>
			<div class="row">
				<div class="col-md-12">
					<!--box-->
					<div class="saojie-set-body box border blue">
						<!--title-->
						<div class="box-title">
							<h4>设置详情</h4>
						</div>
						<!--title-->
						<!--box-body-->
						<div class="box-body form-horizontal">
							<!--内容-->
							<div class="form-group">
								<label class="col-sm-2 control-label">姓名:</label>
								<div class="col-sm-10">
								<input type="hidden" id="userId" value="${salesman.id }"/>
									<p class="form-control-static">${salesman.truename }</p>
								</div>
							</div>
							<div class="hr"></div>
							<div class="form-group">
								<label class="col-sm-2 control-label">区域:</label>
								<div class="col-sm-10">
									<p class="form-control-static">${salesman.region.parent.parent.parent.name} ${salesman.region.parent.parent.name} ${salesman.region.parent.name} ${salesman.region.name}</p>
								</div>
							</div>
							<div class="hr"></div>
							<div class="form-group  marg-b-10">
								<label class="col-sm-2 control-label">扫街顺序:</label>
							</div>
							<div class="saojie-set-list col-sm-10  col-sm-offset-1 col-xs-12">
								<span class="sequence">调整顺序</span>
								<!--list-->
								<div class="table-responsive">
								<table class="table">
								<c:forEach var="saojie" items="${list}" varStatus="s">
									<tr>
										<td>
										<input type="hidden" id="orderlist" />
										<input type="hidden" id="saojieId${saojie.order-1 }" value="${saojie.id }"/>
											<span class="icon-tag-span">${saojie.order }</span>
											<span class="addreass">${saojie.region.name}</span>
										</td>
										<!--箭头-->
										<c:if test="${saojie.status == 'PENDING' }">
										<td>
											<span class="icon-arow arow-down" id="${saojie.id }"></span>
											<span class="icon-arow arow-up" id="${saojie.id }"></span>
										</td>
										</c:if>
										<c:if test="${saojie.status == 'AGREE' }">
										<td>
											<span></span>
											<span></span>
										</td>
										</c:if>
										<!--箭头-->
										<td>
											<span class="norm">指标：<span class="text-danger">${saojie.minValue}</span>家</span>
											<span class="td-span">已完成：<span class="text-primary">80%</span></span>
										</td>
										<c:if test="${saojie.status == 'PENDING' }">
										<td>
											<button class="btn btn-audit" onclick="agree('${saojie.id}');">审核通过</button>
										</td>
										</c:if>
										<c:if test="${saojie.status == 'AGREE' }">
										<td>
											<button class="btn btn-audit">已通过</button>
										</td>
										</c:if>
									</tr>
								</c:forEach>
								</table>
								</div>
								<!--/list-->
								<!--确定按钮-->
									<!-- <div class="col-sm-3 col-sm-offset-9 saojie-set-submit ">
										<button class="btn btn-danger col-sm-8">确定</button>
									</div> -->
								<!--确定按钮-->
							</div>
							<!--分隔,清除浮动-->
							<div class="clearfix"></div>
							<div class="hr"></div>
							<!--扫街设置地图-->
							<div class="saojie-set-map col-sm-10  col-sm-offset-1 col-xs-12">
								<!--map-title-->
								<p class="map-tital">开始时间: 2015.11.12 <span class="drive-row"></span> 结束时间: 2016.01.17</p>
								<!--map-title-->
								<div class="map-box " id="allmap">
<!-- 								<img style="width: 100%;" src="/static/img/saojie-set-map.png"/> -->
<!-- 									<div style="width: 100%;" ></div> -->
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
		<!-- 模态框（Modal） -->
<div class="modal fade" id="auditModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">
               审核通过
            </h4>
         </div>
         <div class="modal-body">
           <div class="form-group">
    <label for="name">点评：</label>
    <textarea class="form-control" rows="3" id="remark"></textarea>
  </div>
         </div>
         <div class="modal-footer">
         <input type="hidden" name="type" value="" id="saojie_id">
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">关闭
            </button>
            <button type="button" class="btn btn-primary" onclick="btnAudit();">
               审核通过
            </button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
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
			<!-- <script src="../static/js/jquery/jquery-1.11.3.min.js"></script> -->
			<!-- Include all compiled plugins (below), or include individual files as needed -->
			<script src="../static/bootstrap/js/bootstrap.min.js"></script>
			<script src="../static/saojie/saojie-set.js" type="text/javascript" charset="utf-8"></script>
			<script src="../static/js/common.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	/* 定位到区,以选择的区为中心点 */
	 <% String areaname=request.getAttribute("areaname").toString();
	 %>
		 var map = new BMap.Map("allmap");
	 	if("山东省"=="<%=areaname%>"){
	 		map.centerAndZoom(new BMap.Point(117.010765,36.704194), 14);
	 	}else{
	 		var name ="<%=areaname%>";
	 		
	 		
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
	 		
	 		
	 		
	 		  	/* 迭代每个镇轮廓 */
	 		 <%		
	 		 		if(null!=request.getAttribute("salesman")){
	 		 		List<Region> listRegion=(List<Region>)request.getAttribute("regionData");
	 		 		if(listRegion != null && listRegion.size()>0){
						for(int i=0;i<listRegion.size();i++){
						String coordinates=listRegion.get(i).getCoordinates();
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
							<%
							 
			 			}
			 		}}%>
	 	}
	</script>
	</body>
</html>
