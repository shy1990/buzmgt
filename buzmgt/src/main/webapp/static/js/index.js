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
					$.getScript("/static/js/jquery/jquery.min.js");
					$.getScript("/static/zTree/js/jquery.ztree.all-3.5.js");
					$.getScript("/static/js/region/regiontree.js");
				}
				$.getScript("/static/js/ditu.js");
				$.getScript("/static/js/ditu2.js");
			});
		}
	});

})
