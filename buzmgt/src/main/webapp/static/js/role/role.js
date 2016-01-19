$(document).ready(
		function() {
			$.ajax({
				type : "post",
				url : "/role/getRoleList",
				// data:formValue,
				dataType : "JSON",
				success : function(result) {
					if (result) {
						var organ = $("#organization");
						;
						organ.empty();
						for (var i = 0; i < result.length; i++) {
							organ.append("<option value = '" + result[i].id
									+ "'>" + result[i].name + "</option>");
						}

					} else {
						alert("数据加载异常！");
					}
					;
				}
			});
		});
