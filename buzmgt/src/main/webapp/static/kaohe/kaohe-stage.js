var strtown;
$(function(){
	
	var rName = $('#regionlbl').text();
	$('#regiontxt').html(rName);
	
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
	            strtown+='<option value = "" selected="selected">请选择</option>';
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
	 <select class="form-control" name="assessArea" id="town">\
	  ' + strtown + '\
		</select>\
		</div>';
	$(this).parents('.col-sm-4').before(dirHtml);
	var options = document.getElementById("town").options.length;
	if ($("div[id^='selOrder']").length == options-1) $("#btn").hide();
});

function toSubmit(){
	form.submit();
}