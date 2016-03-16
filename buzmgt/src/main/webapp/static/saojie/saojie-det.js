$(function(){
	$('a.title-btn').click(function(){
		$('.title-btn').removeClass('active');
		$(this).addClass('active');
		var $_btnText=$(this).text();
		console.info($_btnText);
		if("地图"===$_btnText){
			$('.saojie-list').hide(1);
			$('.saojie-map').slideDown(500);
		}else if("列表"===$_btnText){
			$('.saojie-map').hide(1);
			$('.saojie-list').slideDown(500);
		}
	})
	getSaojieDataList();
});


function getSaojieDataList(){
	var userId = $("#userId").html();
	var regionId = $("#regionId").val();
	$.ajax({
		type:"post",
		url:"/teammember/getSaojiedataList",
		//data:formValue,
		dataType:"JSON",
		data :{
			"userId" : userId,
			"regionId" : regionId
		},
		success : function(result){
		   if (result) {
			   var saojiedata = $("#saojiedata");  
			   $(".shopNum").text(result.shopNum);
			   $(".percent").text(result.percent); 
			   $("#percent").width(result.percent);
			   saojiedata.empty();  
			   for(var i = 0;i<result.list.length;i++){
				   saojiedata.append("<div  class='list-tr'>"+
							"<img class='shop-img' style='height:80%;'  src='"+result.list[i].imageUrl+"' />"+
							"<div style='display: inline-block;' class='list-conter'>"+
								"<h4>"+result.list[i].name+"</h4>"+
								"<p>"+result.list[i].description+"</p>"+
								"<span class='pull-right'>"+result.list[i].saojieDate+"</span>"+
							"</div>"+
						"</div>     ");
			   }
			  
			}
		}
	});
}

var i = 5; 
var intervalid; 
function audit(id,regName) {
	$('#auditModal').modal({
		keyboard: false,
		backdrop: false
		})
	$("#salesmanId").val(id);
	var a=document.getElementById ("regName");
    a.innerHTML = regName;
    
    intervalid = setInterval("fun()", 1000); 
}
function fun() { 
    if (i == 0) { 
    	var id = $("#salesmanId").val();
    	window.location.href = "/assess/toAssessSet?id="+id;
    	clearInterval(intervalid); 
    } 
    document.getElementById("mes").innerHTML = i; 
    i--; 
} 

function auditSet(){
	var id = $("#salesmanId").val();
	window.location.href = "/assess/toAssessSet?id="+id;
}
