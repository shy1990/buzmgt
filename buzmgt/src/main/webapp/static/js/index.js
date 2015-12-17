$(function() {
				$("#sidebar ul li a").click(function(event) {
					event.preventDefault();
					$("#main-content").load($(this).attr("href"), {
						test : "sssssssssssssss"
					},function() {
						  $.getScript("static/js/ditu.js");
						  $.getScript("static/js/ditu2.js");
						  $.getScript("http://echarts.baidu.com/build/dist/echarts-all.js");
					});
				});
			});