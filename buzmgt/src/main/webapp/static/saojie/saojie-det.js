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
		success : function(data){
		   if (data) {
			   var saojiedata = $("#saojiedata");  
			   $(".shopNum").text(data.shopNum);
			   $(".percent").text(data.percent); 
			   $("#percent").width(data.percent);
			   saojiedata.empty();
			   
			   saojiedata.append("<div  class='list-tr'>"+
						"<img class='shop-img' style='height:80%;'  src='"+data[0].imageUrl+"' />"+
						"<div style='display: inline-block;' class='list-conter'>"+
							"<h4>"+data[0].name+"</h4>"+
							"<p>"+data[0].description+"</p>"+
							"<span class='pull-right'>"+data[0].saojieDate+"</span>"+
						"</div>"+
					"</div>     ");
			 if(data[0].saojie.saojiedata.length>1){
			
				for(var i = 1;i<data[0].saojie.saojiedata.length;i++){
						   saojiedata.append("<div  class='list-tr'>"+
									"<img class='shop-img' style='height:80%;'  src='"+data[0].saojie.saojiedata[i].imageUrl+"' />"+
									"<div style='display: inline-block;' class='list-conter'>"+
										"<h4>"+data[0].saojie.saojiedata[i].name+"</h4>"+
										"<p>"+data[0].saojie.saojiedata[i].description+"</p>"+
										"<span class='pull-right'>"+data[0].saojie.saojiedata[i].saojieDate+"</span>"+
									"</div>"+
								"</div>     ");
				}
				   
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
