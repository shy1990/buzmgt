$(function(){
	//开启div滚动条
	$('.j_scroller').mCustomScrollbar('');
	$('.j_dropdown').click(function(){
		$(this).parent('li').toggleClass('active');
	});
	$('.j_radio_setting').click(function(){
		$(this).addClass('visible-icon').removeClass('visible-no-icon');
		$(this).parent('label').siblings('label').find('i').removeClass('visible-icon').addClass('visible-no-icon');
	})
	$('.role-list .query-icon').click(function(){
		$("#main").load("/character");
	});
	
	
})