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
function iFrameHeight() {   
	alert("fuck");
		var ifm= document.getElementById("iframepage");   
		var subWeb = document.frames ? document.frames["iframepage"].document:ifm.contentDocument;   
		if(ifm != null && subWeb != null) {
		   ifm.height = subWeb.body.scrollHeight;
		   ifm.width = subWeb.body.scrollWidth;
		}   
	}