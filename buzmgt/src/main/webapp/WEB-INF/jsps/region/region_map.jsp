<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,com.wangge.buzmgt.region.entity.Region" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<title>区域划分</title>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
<script src="static/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="static/bootstrap/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
<!--加载鼠标绘制工具-->
<script type="text/javascript" src="static/js/region/drawingManager_min.js"></script> 
<link rel="stylesheet" type="text/css" href="static/css/region/drawingManager_min.css" />	
<!--加载检索信息窗口-->
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />
<link rel="stylesheet" type="text/css" href="static/css/region/purview-region-setting.css" />
<link rel="stylesheet" type="text/css" href="static/bootstrap/css/bootstrap-dialog.css" />
<script type="text/javascript" src="static/bootstrap/js/bootstrap-dialog.js"></script>
<style>
.top-titile{
	padding: 20px;
	color: red;
}
body, html,#allmap,.container-fluid,.row{width: 100%;height:100%; overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
</head>
<body >
					<div id="allmap">
					</div>

			<!-- 创建区域  modal start -->
			<div class="add-region modal fade " id="exampleModal" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalLabel">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title" id="exampleModalLabel">
								<i class="icon-add"></i>创建区域
							</h4>
						</div>
						<div class="modal-body">
							<form class="form-horizontal">
								<div class="form-group">
									<label for="recipient-name" class="col-md-3 control-label">区域名字</label>
									<div class="col-md-9 ">
										<input type="text" placeholder="请填写区域名字" class="form-control"
											id="regionName">
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
						 <button type="button" class="btn btn-danger" 
				               data-dismiss="modal" onclick="onCancel();">取消
							 </button>
				            <button type="button" class="btn btn-danger" onclick="onConfirm();">
				               			提交
				            </button>
						</div>
					</div>
				</div>
			</div>
				<!-- 创建区域  modalend -->
				
				
</body>
<script src="static/js/jquery/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src='static/js/common.js' charset="utf-8"></script>
<script type="text/javascript">


		/* 定位到区,以选择的区为中心点 */
	<%String areaname = request.getAttribute("regionName").toString();
		String parentid = null;
		if (null != request.getAttribute("parentid")) {
			parentid = request.getAttribute("parentid").toString();
		}
		%>
	 	var map = new BMap.Map("allmap");
		var name ="<%=areaname%>";
		var polygon=null;
		<%
		if(null!=request.getAttribute("pcoordinates")){%>
			<%
			String pcoordinates=request.getAttribute("pcoordinates").toString();
			String[] listCoordinates=pcoordinates.split("=");
			 %> 
			 			 polygon = new BMap.Polygon([
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
		}else{
		String parentName = request.getAttribute("parentName").toString();%>
		var parentname ="<%=parentName%>";
		map.centerAndZoom(parentname, 12);
		map.enableScrollWheelZoom(true); 
			var bdary = new BMap.Boundary();
			bdary.get(name, function(rs){ //获取行政区域
			var count = rs.boundaries.length; //行政区域的点有多少个
	
			for(var i = 0; i < count; i++){
			polygon = new BMap.Polygon(rs.boundaries[i], {strokeWeight:1, strokeColor: "blue", fillColor: "", fillOpacity: 0.3}); //建立多边形覆盖物
			polygon.setStrokeWeight(3);
			map.addOverlay(polygon); //添加覆盖物
			map.setViewport(polygon.getPath()); //调整视野 
			} 
			
			}); 
		<%}%>
		
		
		
		  	/* 迭代每个镇轮廓 */
		 <%		
		 		if(null!=request.getAttribute("jsonData")){
		 		//String jsonString= request.getAttribute("jsonData").toString();
		 		List<Region> listRegion=(List<Region>)request.getAttribute("jsonData");
		 		//JSONArray jsonArr=JSONArray.fromObject(jsonString);
		 		for(Region region:listRegion){
		 			//JSONObject obj=JSONObject.fromObject(jsonArr.get(i));
					String coordinates=region.getCoordinates();
					String centerPoint=region.getCenterPoint();
					String name=region.getName();
					if(null!=coordinates){
		 			String[] listCoordinates=coordinates.split("=");
		 			
		 %> 
		 			var polygon = new BMap.Polygon([
		 	<%
						for(int x=0;x<listCoordinates.length;x++){
							String points=listCoordinates[x];
						//	System.out.println(points);
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
						], {strokeColor:"blue", strokeWeight:2,fillColor: "red", strokeOpacity:0.5});  //创建多边形
		 				map.addOverlay(polygon); 	
						<%
						 
		 			}%>
					
					
		 			//色块上的文字shuomi
	 				<%		
	 					if(null!=centerPoint){
							double lng = Double.parseDouble(centerPoint.split("-")[0]);//经度 
							double lat = Double.parseDouble(centerPoint.split("-")[1]);//纬度%>
							var secRingCenter = new BMap.Point(<%=lng%>,<%=lat%>)
							var secRingLabel2 = new BMap.Label(name,{offset: new BMap.Size(10,-30), position: secRingCenter});
							secRingLabel2.setStyle({"line-height": "20px", "text-align": "center", "width": "80px", "height": "29px", "border": "none", "padding": "2px","background": "url(http://jixingjx.com/mapapi/ac.gif) no-repeat",});
							map.addOverlay(secRingLabel2);
		 		<%}}
		 		}%>
	
	
	
	
	/* 自定义画笔 */
	var overlays = [];
	var overlaycomplete = function(e){
       overlays.push(e.overlay);
   };
   var styleOptions = {
       strokeColor:"red",    //边线颜色。
       fillColor:"red",      //填充颜色。当参数为空时，圆形将没有填充效果。
       strokeWeight: 3,       //边线的宽度，以像素为单位。
       strokeOpacity: 0.8,	   //边线透明度，取值范围0 - 1。
       fillOpacity: 0.6,      //填充的透明度，取值范围0 - 1。
       strokeStyle: 'solid' //边线的样式，solid或dashed。
   }
   //实例化鼠标绘制工具
   var drawingManager = new BMapLib.DrawingManager(map, {
       isOpen: false, //是否开启绘制模式
       enableDrawingTool: true, //是否显示工具栏
       drawingToolOptions: {
           anchor: BMAP_ANCHOR_TOP_RIGHT, //位置
           offset: new BMap.Size(5, 5), //偏离值
       },
       circleOptions: styleOptions, //圆的样式
       polylineOptions: styleOptions, //线的样式
       polygonOptions: styleOptions, //多边形的样式
       rectangleOptions: styleOptions //矩形的样式
   });  

   //添加鼠标绘制工具监听事件，用于获取绘制结果
   var pointStr=null;
   centerPoint=null;
   drawingManager.addEventListener('overlaycomplete',function(e) {
	   overlays.push(e.overlay);
       pointStr=JSON.stringify(e.overlay.getPath());
       jQuery.noConflict();
	});

   		
   		//中心点坐标
   		map.addEventListener("click",function(e){
			//alert(e.point.lng + "," + e.point.lat);
			centerPoint=e.point.lng + "-" + e.point.lat;
			$(function(){
	    	   $('#exampleModal').modal({
	    		   
	    	   }).css({
	    	   	    	"margin-top": function () {  
	    	   	         return ($(this).height() / 3 );  
	    	   	      },  
	    	   	      "margin-left": function () {  
	    	   	         return ($(this).width() / 7 );  
	    	   	       }
	    	   	    });
	    	   
	   		})
	   		
	   		
	   		
		});
   		
   		
   		var updateFlag=false;
	   	 $("a[title='编辑']").click(function(){
	 		//var id = $("#regionId").val();
	//  		URL='/region/initRegion';
	//  		 location.replace(URL)   
	 		  polygon.enableEditing();
	 		 updateFlag=true;
	 	 }) 
   		
   		
   		
	   		//更改区域坐标
	   		map.addEventListener("dblclick",function(e){
				//alert(e.point.lng + "," + e.point.lat);
				polygon.disableEditing();
				
				centerPoint=e.point.lng + "-" + e.point.lat;
				pointStr=JSON.stringify(polygon.getPath());
				 var parentid="<%=parentid%>";
			 	  var name=$("#regionName").val();
			 	  
			 	  
			 	 URL='/region/updateYewuData?points='+pointStr+'&parentid='+parentid+'&centerPoint='+centerPoint;
				 location.replace(URL)   
			 	  
			 	  
			 	  
			 	  
// 			   	  $.ajax({
// 			 		    url:'region/updateYewuData',
// 			 		    data:{    
// 			 		    		points:pointStr,
// 			 		    		parentid:parentid,
// 			 		    		centerPoint:centerPoint
// 			 		    },    
// 			 		    type:'post',    
// 			 		    /* cache:false,
// 			 		    dataType:'json',  */   
// 			 		    success:function(data) {
// 			 		    	$('#exampleModal').modal('hide');
// 			 		        if(data===true){
// 			 		        	BootstrapDialog.alert('保存区域成功');
// 			 		        	setTimeout(function(){
// 			 		        		location.reload()
// 			 		        		},4000);
// 			 		           return;
// 			 		        }    
// 			 		     },    
// 			 		     error : function() {    
// 			 		          // view("异常！");
// 			 		         BootstrapDialog.alert('请求异常!');
// 			 		     }    
// 			 		});   
				
				
			});
   		
   
   
	   function onConfirm(){
	 	  var parentid="<%=parentid%>";
	 	  var name=$("#regionName").val();
	   	  $.ajax({
	 		    url:'region/addPoints',
	 		    data:{    
	 		    		points:pointStr,
	 		    		parentid:parentid,
	 		    		centerPoint:centerPoint,
	 		    		name:name
	 		    },    
	 		    type:'post',    
	 		    /* cache:false,
	 		    dataType:'json',  */   
	 		    success:function(data) {
	 		    	$('#exampleModal').modal('hide');
	 		        if(data===true){
	 		        	BootstrapDialog.alert('保存区域成功');
	 		        	setTimeout(function(){
	 		        		location.reload()
	 		        		},4000);
	 		           return;
	 		        }    
	 		     },    
	 		     error : function() {    
	 		          // view("异常！");
	 		         BootstrapDialog.alert('请求异常!');
	 		     }    
	 		});   
	   }
	   
	   
	 function   onCancel(){
		 for(var i = 0; i < overlays.length; i++){
	            map.removeOverlay(overlays[i]);
	        }
	        overlays.length = 0  
	 }
	 
	  $("a[title='返回']").click(function(){
		//var id = $("#regionId").val();
		URL='/region/initRegion';
		location.replace(URL)   
	 }) 
</script>
</html>
