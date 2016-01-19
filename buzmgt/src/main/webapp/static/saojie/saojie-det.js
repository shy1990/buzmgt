$(function(){
	$('a.title-btn').click(function(){
		$('.title-btn').removeClass('active');
		$(this).addClass('active');
		var $_btnText=$(this).text();
		console.info($_btnText);
		if("地图"===$_btnText){
			$('.saojie-list').hide(1);
			$('.saojie-map').slideDown(500);
		}else if("列表"===$_btnText){
			$('.saojie-map').hide(1);
			$('.saojie-list').slideDown(500);
		}
	})
});