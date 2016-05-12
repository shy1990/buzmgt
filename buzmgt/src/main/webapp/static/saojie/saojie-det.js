var total = 0;
var $_btnText;
$(function(){
	$('#callBackPager').hide();
	//地图和列表切换
	$('a.title-btn').click(function(){
		$('.title-btn').removeClass('active');
		$(this).addClass('active');
		$_btnText=$(this).text();
		if("地图"===$_btnText){
			$('.saojie-list').hide(1);
			$('#callBackPager').hide();
			$('.saojie-map').slideDown(500);
		}else if("列表"===$_btnText){
			$('.saojie-map').hide(1);
			$('#callBackPager').show();
			$('.saojie-list').slideDown(500);
			//执行列表
			var userId = $("#userId").html();
			var regionId = $("#regionId").val();
			searchData['userId'] = userId;
			searchData['regionId'] = regionId;
			
			ajaxSearchByRegion();
		}
	})
	//先加载地图数据
	$_btnText = "地图";
	ajaxSearchByRegion();
});

//选择地区下拉框触发
function ajaxSearchByRegion(saojieId){
	searchData['saojieId'] = $("#saojieId").val();
	var regionid = $("#regionId  option:selected").val();
	searchData['regionId'] = regionid;
	ajaxSearch(searchData);
}

var opts = {
			width : 250,     // 信息窗口宽度
			height: 80,     // 信息窗口高度
			title : "扫街信息" , // 信息窗口标题
			enableMessage:true//设置允许信息窗发送短息
		   };
var map = new BMap.Map("allmap");
function ajaxSearch(searchData) {
	if("地图"===$_btnText){
		$.ajax({
			url : base + "teammember/getSaojiedataMap",
			type : "GET",
			data : searchData,
			beforeSend : function(request) {
				request.setRequestHeader("Content-Type",
						"application/json; charset=UTF-8");
			},
			dataType : "json",
			success : function(data) {
				map.clearOverlays();
				$(".shopNum").text(data.shopNum);
				$(".percent").text(data.percent); 
				$("#percent").width(data.percent);
				map.centerAndZoom(data.areaName, 13);
				// map.centerAndZoom("上海",11);
				// 添加带有定位的导航控件
				var navigationControl = new BMap.NavigationControl({
					// 靠左上角位置
					anchor : BMAP_ANCHOR_TOP_LEFT,
					// LARGE类型
					type : BMAP_NAVIGATION_CONTROL_LARGE,
					// 启用显示定位
					enableGeolocation : true
				});
				map.addControl(navigationControl);
				map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
				var bdary = new BMap.Boundary();
				bdary.get(data.areaName, function(rs) { // 获取行政区域
					var count = rs.boundaries.length; // 行政区域的点有多少个

					for (var i = 0; i < count; i++) {
						var ply = new BMap.Polygon(rs.boundaries[i], {
							strokeWeight : 1,
							strokeColor : "blue",
							fillColor : "",
							fillOpacity : 0.3
						}); // 建立多边形覆盖物
						map.addOverlay(ply); // 添加覆盖物
						map.setViewport(ply.getPath()); // 调整视野
					}
				});
				//扫街数据循环
				var marker;
				var arr = new Array(); //创建数组
				$.each(data.list,function(n,items){
					var coor = items.coordinate;
					alert(coor);
					if(coor != null && coor != ""){
						arr = coor.split("-");
		                for (var j = 0;j < arr.length;j++){
		                	marker = new BMap.Marker(new BMap.Point(arr[0],arr[1]));// 拿到坐标点
		                }
	//					var desc="<%=desc%>";
			  			var content = items.name;
			  			map.addOverlay(marker);               // 将标注添加到地图中
			  			addClickHandler(content,marker);
			  			map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
					}
				  });
			},
			error : function() {
				alert("系统错误，请稍后再试");
			}
		});
	}else if("列表"===$_btnText){
		$.ajax({
			url : base + "teammember/getSaojiedataList",
			type : "GET",
			data : searchData,
			beforeSend : function(request) {
				request.setRequestHeader("Content-Type",
						"application/json; charset=UTF-8");
			},
			dataType : "json",
			success : function(data) {
				$(".shopNum").text(data.shopNum);
				$(".percent").text(data.percent); 
				$("#percent").width(data.percent);
					totalElements = data.page.totalElements;
					totalPages = data.page.totalPages;
					seachSuccessTable(data.page);
					var searchTotal = totalElements;

		            if (searchTotal != total || searchTotal == 0) {
		                total = searchTotal;
		                initPaging();
		            }
			},
			error : function() {
				alert("系统错误，请稍后再试");
			}
		});
	}
}

//监听事件
function addClickHandler(content,marker){
	marker.addEventListener("click",function(e){
		openInfo(content,e)}
	);
}
//开启窗口
function openInfo(content,e){
	var p = e.target;
	var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
	var infoWindow = new BMap.InfoWindow(content,opts);  // 创建信息窗口对象 
	map.openInfoWindow(infoWindow,point); //开启信息窗口
}

function initPaging(){
	var totalCount = totalElements; //总条数 
	showCount = totalPages, //显示分页个数
	limit =  6;//每页条数
	$('#callBackPager').extendPagination({
	totalCount : totalCount, 
	showCount : showCount,
	limit : limit,
	callback : function(curr, limit, totalCount) {
//		alert("当前是第"+curr+"页,每页"+ limit+"条,总共"+ totalCount+"条");
		searchData['page'] = curr - 1;
		searchData['size'] = limit;
		ajaxSearch(searchData);
	}
	});
}

/**
 * 检索成功后
 * 
 * @param data
 */
function seachSuccessTable(data) {
	var myTemplate = Handlebars.compile($("#table-template").html());

	$('#saojiedata').html(myTemplate(data));
	
}

var i = 5; 
var intervalid; 
function audit(id,regName) {
	$('#auditModal').modal({
		keyboard: false,
		backdrop: false
		})
	$("#salesmanId").val(id);
	var a=document.getElementById ("regName");
    a.innerHTML = regName;
    
    intervalid = setInterval("fun()", 1000); 
}
function fun() { 
    if (i == 0) { 
    	var id = $("#salesmanId").val();
    	window.location.href = "/assess/toAssessSet?id="+id;
    	clearInterval(intervalid); 
    } 
    document.getElementById("mes").innerHTML = i; 
    i--; 
} 

function auditSet(){
	var id = $("#salesmanId").val();
	window.location.href = "/assess/toAssessSet?id="+id;
}
