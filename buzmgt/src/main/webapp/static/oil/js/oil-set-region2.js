$(function(){
	
	$("#region1").click(function(){
		alert(11111111);
		var ztreeNodes;
		var regionObj = $("#region1");
		var cityOffset = $("#region1").offset();//设置文档偏移
		//获取权限，区域列表从哪一级开始
		var id = $("#n1").val();
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
				console.log("data  "+data);
				ztreeNodes = eval("(" + data + ")"); // 将string类型转换成json对象
				console.log("ztreeNodes"+ztreeNodes);
				// zNodes = zNodes.concat(ztreeNodes);
				$.fn.zTree.init($("#regionTree1"), regionSetting, ztreeNodes);
				$("#regionMenuContent1").slideDown("fast");
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
			   var role = $("#dialog1"); ;
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
	
	
	
