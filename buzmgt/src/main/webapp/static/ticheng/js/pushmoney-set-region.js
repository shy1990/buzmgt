$(function(){	
	$("#region").on('click',function(){
		var ztreeNodes;
		var regionObj = $("#region");
		var cityOffset = $("#region").offset();//设置文档偏移
		//获取权限，区域列表从哪一级开始
		var id = $("#n").val();
		$.ajax({
			async : true, // 是否异步
			cache : false, // 是否使用缓存
			type : 'post', // 请求方式,post
			dataType : "text", // 数据传输格式
			url : "/region/getRegionById", // 请求链接
			data :{
				id:id
			},
			error : function() {
				alert('访问服务器出错');
			},
			success : function(data) {
				ztreeNodes = eval("(" + data + ")"); // 将string类型转换成json对象
				// zNodes = zNodes.concat(ztreeNodes);
				$.fn.zTree.init($("#regionTree"), regionSetting, ztreeNodes);
				$("#regionMenuContent").slideDown("fast");
			}
		});
		
		$("body").bind("mousedown", onBodyDown);
	});
})



	
	
	
