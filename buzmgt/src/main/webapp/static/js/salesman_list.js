			$(".j_team_member_add").click(function(event) {
				event.preventDefault();
				var $href = $(this).attr("href");
				console.info($href);
				if ($href != '' && $href != null) {
					$("#main").load($href);
				}
			});
