<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,com.wangge.buzmgt.region.entity.Region" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.print(basePath);
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
<link rel="stylesheet" type="text/css" href="static/css/index.css" />
<script src="static/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="static/bootstrap/js/bootstrap.min.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
<!--加载鼠标绘制工具-->
<!-- 	<script type="text/javascript" src="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js"></script>  -->
	<script type="text/javascript" src="static/js/region/drawingManager_min.js"></script> 
<!-- 	<link rel="stylesheet" href="http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.css" />  -->
	<link rel="stylesheet" type="text/css" href="static/css/region/drawingManager_min.css" />	
<!--加载检索信息窗口-->
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.css" />
<link rel="stylesheet" type="text/css" href="static/css/region/purview-region-setting.css" />	
<style>
.top-titile{
	padding: 20px;
	color: red;
}
body, html,#allmap,.container-fluid,.row{width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
</style>
</head>
<body >
		<%@ include file="../top.jsp"%>
			<div class="container-fluid">
				<div   class="row">
					<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
						<%@include file="../left.jsp"%>
			        </div>
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

		</div>
				</div>
</body>
<script type="text/javascript">
		/* 定位到区,以选择的区为中心点 */
	<%String areaname = request.getAttribute("regionName").toString();
		String parentid = null;
		if (null != request.getAttribute("parentid")) {
			parentid = request.getAttribute("parentid").toString();
		}
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
		var ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight:1, strokeColor: "blue", fillColor: "", fillOpacity: 0.3}); //建立多边形覆盖物
		ply.setStrokeWeight(3);
		map.addOverlay(ply); //添加覆盖物
		map.setViewport(ply.getPath()); //调整视野 
		} 
		map.centerAndZoom(name, 12);
		}); 
		
		
		
		  	/* 迭代每个镇轮廓 */
		 <%		
		 		if(null!=request.getAttribute("jsonData")){
		 		//String jsonString= request.getAttribute("jsonData").toString();
		 		List<Region> listRegion=(List<Region>)request.getAttribute("jsonData");
		 		//JSONArray jsonArr=JSONArray.fromObject(jsonString);
		 		for(Region region:listRegion){
		 			//JSONObject obj=JSONObject.fromObject(jsonArr.get(i));
					String coordinates=region.getCoordinates();
					if(null!=coordinates){
		 			String[] listCoordinates=coordinates.split("=");
		 			
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
						], {strokeColor:"blue", strokeWeight:2,fillColor: "red", strokeOpacity:0.5});  //创建多边形
		 				map.addOverlay(polygon); 	
//		 				polygon.addEventListener('click',function(e) {
//		 				   var  point=JSON.stringify(e.pixel);
//							  alert(point);
<%-- 								  alert(<%=coordinates%>); --%>
//						});
						<%
						 
		 			}
		 		}
		 		}%>
	}
	
	
	
	
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
   drawingManager.addEventListener('overlaycomplete',function(e) {
	   overlays.push(e.overlay);
       pointStr=JSON.stringify(e.overlay.getPath());
       jQuery.noConflict();
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

   		
	   function onConfirm(){
	 	  var parentid="<%=parentid%>";
	 	  var name=$("#regionName").val();
	   	  $.ajax({
	 		    url:'region/addPoints',
	 		    data:{    
	 		    		points:pointStr,
	 		    		parentid:parentid,
	 		    		name:name
	 		    },    
	 		    type:'post',    
	 		    /* cache:false,
	 		    dataType:'json',  */   
	 		    success:function(data) {
	 		    	$('#exampleModal').modal('hide');
	 		        if(data==='true' ){   
	 		        	alert("保存区域成功");
	 		        	location.reload();
	 		           return;
	 		        }    
	 		     },    
	 		     error : function() {    
	 		          // view("异常！");
	 		          alert("异常！");    
	 		     }    
	 		});   
	   }
	   
	   
	 function   onCancel(){
		 for(var i = 0; i < overlays.length; i++){
	            map.removeOverlay(overlays[i]);
	        }
	        overlays.length = 0  
	 }
   
</script>

			

</html>
