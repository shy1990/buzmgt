$(function(){
	
	$("#region").click(function(){
		var ztreeNodes;
		var regionObj = $("#region");
		var cityOffset = $("#region").offset();//设置文档偏移
		//获取权限，区域列表从哪一级开始
		var id = $("#n").val();
		console.log("***********"+id);
		$.ajax({
			async : true, // 是否异步
			cache : false, // 是否使用缓存
			type : 'post', // 请求方式,post
			dataType : "text", // 数据传输格式
			url : "/region/getRegionById", // 请求链接
			data :{
//				id : $("#region").val()
				id:id
			},
			error : function() {
				alert('访问服务器出错');
			},
			success : function(data) {
				ztreeNodes = eval("(" + data + ")"); // 将string类型转换成json对象
				console.log(ztreeNodes);
				// zNodes = zNodes.concat(ztreeNodes);
				$.fn.zTree.init($("#regionTree"), regionSetting, ztreeNodes);
				$("#regionMenuContent").slideDown("fast");
			}
		});
		
		$("body").bind("mousedown", onBodyDown);
	});
	getOrgan();
	getRoles();
})

/*$(document).ready(function() {
	getOrgan()
	getRoles();
});*/


function getOrgan(){
	$.ajax({
		type:"post",
		url:"/organization/getOrganizationList",
		//data:formValue,
		dataType:"JSON",
		success : function(result){
		   if (result) {
			   var organization = $("#organization"); ;
			   organization.empty();  
			   organization.append("<option value = '' selected='selected'>请选择</option>");
               for(var i=0;i<result.length;i++){
            	   organization.append("<option value = '"+result[i].id+"'>"+result[i].name+"</option>");
				}
              
			}else {
				alert("数据加载异常！");
			};
	   }
	});
}


function getRoles(){
	$.ajax({
		type:"post",
		url:"/role/getRolelist",
		//data:formValue,
		dataType:"JSON",
		success : function(result){
		   if (result) {
			   var role = $("#role"); ;
			  // role.empty();  
			//   role.append("<option value = '' selected='selected'>请选择</option>");
               for(var i=0;i<result.length;i++){
            	   role.append("<option value = '"+result[i].id+"'>"+result[i].name+"</option>");
				}
              
			}else {
				alert("数据加载异常！");
			};
	   }
	});
	
}	
	
	
	
