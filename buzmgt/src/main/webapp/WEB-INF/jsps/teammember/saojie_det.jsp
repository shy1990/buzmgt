<%@ page language="java" import="java.util.*,com.wangge.buzmgt.sys.vo.*,com.wangge.buzmgt.saojie.entity.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>扫街明细</title>
<!-- Bootstrap -->
<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="/static/bootstrap/css/bootstrap-multiselect.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/saojie/saojie-det.css" />
<link rel="stylesheet" type="text/css"
	href="/static/yw-team-member/ywmember.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
</head>

<body>
	<div class="content main">
		<h4 class="team-member-header page-header ">
			<i class="icon icon-ywdet"></i>扫街明细
			<a href="/teammember/salesManList" class="btn btn-blue member-add-btn"
				type="button"> <i class="icon glyphicon glyphicon-share-alt"></i>
				返回列表
			</a>
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="saojie-det-body box border blue">
					<!--title-->
					<div class="box-title">
						<!--区域选择按钮-->
						<select id="regionId" class="form-control input-xs " onchange="getSaojieDataList();"
							style="width: 110px; position: relative;height:25px; padding: 2px 5px; display: inline-block; font-size: 12px; color: #6d6d6d;">
							<option value="" selected="selected">全部区域</option>
							<c:forEach var="region" items="${rList}" varStatus="s">
								<option value="${region.id}" >${region.name}</option>
							</c:forEach>
						</select>
						<!--/区域选择按钮-->
						<div class="det-msg">
							<span>扫街商家<span class="shopNum" id="shopNumId">0</span>家
							</span> <span>扫街已完成<span class="percent"id="percentId">0%</span></span>
						</div>
						<!--/row-->
						<div class="btn-group title-page">
							<a class="title-btn active" href="javascript:;"><i
								class="icon icon-map"></i>地图</a> <a class="title-btn"
								href="javascript:;"><i class="icon icon-list"></i>列表</a>
						</div>
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<!--地图-->
						<div class="saojie-map ">
							<!--地图位置-->
							<div style="height: 600px;" class="body-map">
								<!--地图位置-->
								<div style="height: 600px;" class="body-map" id="allmap">
<!-- 									<img src="static/img/saojie-map.png" /> -->
								</div>
							</div>
							 <c:if test="${salesStatus=='kaifa'}">
								<button class="btn btn-approve col-sm-2 col-sm-offset-5" onclick="audit('${salesMan.id}','${salesMan.region.name}')">审核通过</button>
							</c:if>
						</div>
						<!--/地图-->
						<!--列表-->
						<div class="saojie-list active" id="saojiedata">
							<!--tr-->
							<div class="list-tr">
								<img class="shop-img" src="/static/img/saojie-img.png" />
								<div style="display: inline-block;" class="list-conter">
									<h4>大鹏十年手机品质专卖店</h4>
									<p>备注：百度和携程两个难兄难弟，一个是市值580亿美元，
										制霸行业十余年的国内搜索龙头的老大，一个是国内OTA绝对龙头，
										市值120亿美元的旅行老大，在这一周都过得水深火热......</p>
									<span class="pull-right">2015.11.12 15:22</span>
								</div>
							</div>
							<!--/tr-->
						</div>
						<!--/列表-->
					</div>
					<!--/box-body-->
				</div>
				<!--/box-->
			</div>

			<!--team-map-->
			<div class="col-md-3">
				<!--box-->
				<!--不同阶段颜色不同1：pink 2：yellow 3:violet 4:-->
				<div class="ywmamber-msg box border pink">
					<!--title-->
					<div class="box-title">
						<i class="icon icon-time"></i>${salesMan.status.name }
					</div>
					<div class="box-body">
						<!--ywmamber-body-->
						<div class="ywmamber-body">
							<img width="80" src="../static/img/user-head.png" alt="..."
								class="img-circle">
							<div class="msg-text">
								<h4>${salesMan.truename}</h4>
								<span>ID: </span> <span id="userId">${salesMan.id}</span>
								<p>电话: ${salesMan.mobile}</p>
							</div>
						</div>
						<!--/ywmamber-body-->
						<div class="stage">
							<span class="stage">${salesMan.status.name}:<span
								class="percent">0%</span></span>
						</div>
						<div class="progress progress-sm">
							<div id="percent" style="width: 0%;"
								class="progress-bar bar-stage"></div>
						</div>
						<div class="operation">
							<a href="javascript:;" class="">设置</a> <a href="javascript:;">辞退</a>
							<a href="javascript:;" class="pull-right">查看</a>
						</div>
						<div class="yw-text">
							入职时间:<span> ${salesMan.regdate}</span><br /> 负责区域:<span>${salesMan.region.name}</span><input
								id="regionId" type="hidden" value="${salesMan.region.id}" />
						</div>
						<!--拜访任务-->
						<div class="visit">
							<button class="col-xs-12 btn btn-visit" href="javascript:;">
								<i class="ico icon-add"></i>拜访
							</button>
						</div>
						<!--拜访任务-->
						<!--操作-->
						<div class="operation">
							<a href="javascript:;" class="">账户设置</a> <a href="javascript:;">冻结账户</a>
						</div>
						<!--操作-->
						<!--虚线-->
						<div class="hr"></div>
						<!--虚线-->
						<!--业务外部链接-->
						<div class="yw-link">
							<a class="link-oper" href="javascript:;"><i
								class="icon icon-user"></i>个人资料</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-income"></i>收益</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-task"></i>任务</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-log"></i>日志</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-footprint"></i>足迹</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-signin"></i>签收记录</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-saojie"></i>扫街记录</a>
						</div>


					</div>
				</div>
				<!--/team-map-->
			</div>
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
		<script src="/static/js/jquery/jquery-1.11.3.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="/static/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/static/bootstrap/js/bootstrap-multiselect.js"></script>
        <script src="/static/saojie/saojie-det.js" type="text/javascript"
			charset="utf-8"></script>
		<script src="/static/js/common.js" type="text/javascript"
			charset="utf-8"></script>
			<script type="text/javascript">
			 // 百度地图API功能
			  var map = new BMap.Map("allmap");
			  <% String areaname=request.getAttribute("areaName").toString();
			  %>
			   map.centerAndZoom("<%=areaname%>", 13);
			   map.enableScrollWheelZoom(true);  
			   //  map.centerAndZoom("上海",11);   
			  // 添加带有定位的导航控件
			  var navigationControl = new BMap.NavigationControl({
			    // 靠左上角位置
			    anchor: BMAP_ANCHOR_TOP_LEFT,
			    // LARGE类型
			    type: BMAP_NAVIGATION_CONTROL_LARGE,
			    // 启用显示定位
			    enableGeolocation: true
			  });
			  map.addControl(navigationControl);
			  
				
				var bdary = new BMap.Boundary();
				
				bdary.get("<%=areaname%>", function(rs){ //获取行政区域
				var count = rs.boundaries.length; //行政区域的点有多少个

				for(var i = 0; i < count; i++){
				var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight:1, strokeColor: "blue", fillColor: "", fillOpacity: 0.3}); //建立多边形覆盖物
				map.addOverlay(ply); //添加覆盖物
				map.setViewport(ply.getPath()); //调整视野 
				} 
				}); 
			  
			  
			  
			  <%
			  	SaojieDataVo saojieDataVo=	(SaojieDataVo)request.getAttribute("saojiedatalist");
			  	if(saojieDataVo.getList().size()>0){
			  	  	for(SaojieData saojiedata:saojieDataVo.getList()){
			  		String pointStr=saojiedata.getCoordinate();
			  		String lag=pointStr.split("-")[0];
			  		String lat=pointStr.split("-")[1];
			  		String titile=saojiedata.getName();
			  		//String truename=store.getTruename();
			  		String desc=saojiedata.getDescription();
			  	%>	
			  	
			  	 $("#shopNumId").html(<%=saojieDataVo.getList().size()%>); 
	   			 var percent='<%=saojieDataVo.getPercent()%>';
	   			 $("#percentId").html(percent);
			  			var opts = {
			  					width : 250,     // 信息窗口宽度
			  					height: 80,     // 信息窗口高度
			  					title : "扫街信息" , // 信息窗口标题
			  					enableMessage:true//设置允许信息窗发送短息
			  				   };
			  			var desc="<%=desc%>";
			  			var marker = new BMap.Marker(new BMap.Point(<%=lag%>,<%=lat%>));  // 创建标注
			  			var content ="<%=titile%>";
			  			map.addOverlay(marker);               // 将标注添加到地图中
			  			addClickHandler(content,marker);
			  			map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
			  <%	}
			  	}
			  %>
			  
			  function addClickHandler(content,marker){
					marker.addEventListener("click",function(e){
						openInfo(content,e)}
					);
				}
				function openInfo(content,e){
					var p = e.target;
					var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
					var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
					map.openInfoWindow(infoWindow,point); //开启信息窗口
				}
				
		</script>
</body>

</html>
