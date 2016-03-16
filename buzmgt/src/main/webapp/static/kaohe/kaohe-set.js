var strtown;
$(function(){
	var salId = $("#salId").attr("data-aa");
	$.ajax({
		type:"post",
		url:"/assess/gainAuditTown",
		data: {"id":salId},
		dataType:"JSON",
		success : function(obj){
		   if (obj) {
			   var regionName;
			   strtown ='';
	            strtown+='<option value = "" selected="selected">自定义区域</option>';
		        for(var i=0;i<obj.length;i++){
		        	strtown+="<option value = '"+obj[i].id+"'>"+obj[i].name+"</option>";
				}
		}
	 }
	});
});
var orderNum = 0;
$('.J_addDire').click(function() {
	var intLen = $("div[id^='selOrder']").length;
	if (intLen == "undefined"){
		intLen = 1;
	}
	if (intLen <= 30) {
		intLen++;
		orderNum++;
	}
	var dirHtml = '<div class="col-sm-4" id="selOrder' + orderNum + '">\
	 <select class="form-control" name="defineArea"  id="town">\
	  ' + strtown + '\
		</select>\
	</div>';
	$(this).parents('.col-sm-4').before(dirHtml);
	var options = document.getElementById("town").options.length;
	if ($("div[id^='selOrder']").length == options-1) $("#btn").hide();
});

function toSubmit(){
//	$("#assesszh").val($("#assessAreaId").find("option:selected").text());
	form.submit();
}

 /*function btnAudit() { 
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
  }*/
 
