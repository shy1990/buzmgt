$(function() {
	$('#left-menu a').click(function() {
		$(this).parent('li').addClass('active');
		$(this).parent('li').siblings().removeClass('active');
		$(this).parent('li').siblings().find('.menu-second').hide();
	})
	$('a.menu-second-box').click(function() {
		$(this).siblings('ul.menu-second').show();
	});

})
