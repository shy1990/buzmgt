$(function(){
	$(':checked').click();
	/**
	 * 区域选中样式切换
	 */
	$('.j_district').click(function(){
		$(this).toggleClass('active');
	});
	/**
	 * 区域全选
	 */
	$('.j_district_all').click(function(){
		$(this).toggleClass('active');
		//判读是否被选中
		var msg=$(this).hasClass('active');
		//样式修改
		if(msg){
			$('.j_district').addClass('active');
		}else{
			$('.j_district').removeClass('active');
		}
		//checkbox选中去除全选本身checked
		$('.j_territory input[type=checkbox]:gt(0)').each(function(index,eve){
			this.checked=msg;
		});
		//$('.j_district').click();//反选
	});
	
});

$(document).ready(function() {
	getOrgan()
	getRoles();
});


function getOrgan(){
	$.ajax({
		type:"post",
		url:"/organization/getOrganizationList",
		//data:formValue,
		dataType:"JSON",
		success : function(result){
		   if (result) {
			   var organ = $("#organization"); ;
			   organ.empty();  
			 //  organ.append("<option value = '' selected='selected'>请选择</option>");
               for(var i=0;i<result.length;i++){
            	   organ.append("<option value = '"+result[i].id+"'>"+result[i].name+"</option>");
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
$(".j_team_member_add").click(function(event) {
	event.preventDefault();
	var $href = $(this).attr("href");
	console.info($href);
	if ($href != '' && $href != null) {
		$("#main").load($href);
	}
});
