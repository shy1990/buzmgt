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