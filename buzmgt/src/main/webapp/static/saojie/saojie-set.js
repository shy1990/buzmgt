$(document).ready(function() {
	var userId = $("#userId").val();
	var flag = 1;
	var trLength = $("span.arow-down").length;
	// 上移
	$("span.arow-up").each(function() {
		$(this).click(function() {
			var id=$(this).attr('id');
			var $tr = $(this).parents("tr");
			var index = $tr.index();
			if (index != 0 && trLength-1 != 0) {
				$tr.prev().before($tr);
				var ordernum=index;
				$.ajax({
					type:"post",
					url:"/saojie/changeOrder",
					data:{id:id,ordernum:ordernum,userId:userId,flag:flag},
					//dataType:"JSON",
					success : function(data){
						if (data === 'ok') {
							location.reload();
						}
				 }
				});
			}
		});
		
	});
	
	// 下移
	var trLength = $("span.arow-down").length;
	$("span.arow-down").each(function() {
		$(this).click(function() {
			var id=$(this).attr('id');
			var $tr = $(this).parents("tr");
			var index = $tr.index();
			if ($tr.index() != trLength-1) {
				if(trLength-1 != 0){
					$tr.next().after($tr);
					var ordernum=index+1;
					$.ajax({
						type:"post",
						url:"/saojie/changeOrder",
						data:{id:id,ordernum:ordernum,userId:userId,flag:-1},
						//dataType:"JSON",
						success : function(data){
							if (data === 'ok') {
								location.reload();
							}
					 }
					});
				}
			}
		});
	});
	
});


 function btnAudit() { 
	 var id = $("#saojie_id").val();
	 var remark =document.getElementById("remark").value; 
	 $.ajax({ type : "post", 
		 url :"/saojie/auditPass", 
		 data : {"saojieId" : id,"description" : remark }, 
		 success :function(data) {
			 if (data === 'ok') {
				location.reload();
			}
		}
	 });
  }
 

function agree(id) {
	$('#auditModal').modal({
		keyboard: false
		})
	$("#saojie_id").val(id);
}

$('.sequence').click(function(){
	$('.icon-arow').toggleClass('active');
});