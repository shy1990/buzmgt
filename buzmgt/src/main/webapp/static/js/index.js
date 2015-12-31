$(function() {
	$('#left-menu a').click(function() {
		$(this).parent('li').addClass('active');
		$(this).parent('li').siblings().removeClass('active');
		$(this).parent('li').siblings().find('.menu-second').hide();
	})
	$('a.menu-second-box').click(function() {
		$(this).siblings('ul.menu-second').show();
	})
	$(".menu li a").click(function(event) {
		event.preventDefault();
		var $href = $(this).attr("href");
		console.info($href);
		if ($href != '' && $href != null) {
			$("#main").load($href+" #main", {
				test : "sssssssssssssss"
			}, function() {
				if($href=="/region/initRegion"){
					$.getScript("/static/zTree/js/jquery.ztree.all-3.5.js");
					$.getScript("/static/js/region/regiontree.js");
					$.getScript("http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT");
					$.getScript("http://api.map.baidu.com/library/DrawingManager/1.4/src/DrawingManager_min.js");
					$.getScript("http://api.map.baidu.com/library/SearchInfoWindow/1.4/src/SearchInfoWindow_min.js");
				}
//				$.getScript("/static/js/ditu.js");
//				$.getScript("/static/js/ditu2.js");
			});
		}
	});

})
