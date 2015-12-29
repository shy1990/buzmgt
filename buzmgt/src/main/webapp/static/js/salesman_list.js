
			$(document).ready(function() {
			/*	$('.single-selected').multiselect();
		        $('.multiple-selected').multiselect({
		            includeSelectAllOption: true,
		            maxHeight: 300
		        });*/
				var id = id != null ? id : "0";
			    var type;
				getRegion(id,type);
		    });
			
			function getRegion(id,type){
				$.ajax({
					type:"post",
					url:"/region/getRegionById",
					//data:formValue,
					dataType:"JSON",
					data : {
						"id" : id
					},
					success : function(result){
					   if (result) {
						   var region;
						   if(type == null){
							   region = $("#provice");  
						   }else if(type == "provice"){
							   region = $("#city");  
						   }else if(type == "city"){
							   region = $("#area"); 
						   }
						   region.empty();  
						   region.append("<option value = '' selected='selected'>---请选择---</option>");
			               for(var i=0;i<result.length;i++){
			            	   region.append("<option value = '"+result[i].id+"'>"+result[i].name+"</option>");
							}
			              
						}else {
							alert("数据加载异常！");
						};
				   }
				});
			}
			$(".j_team_member_add").click(function(event) {
				event.preventDefault();
				var $href = $(this).attr("href");
				console.info($href);
				if ($href != '' && $href != null) {
					$("#main").load($href);
				}
			});
