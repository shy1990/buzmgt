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
	
	var userId = $("#userId").html();
	var regionId = $("#regionId").val();
	searchData['userId'] = userId;
	searchData['regionid'] = regionId;
	
	ajaxSearch(searchData);
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
			}
		}
	});
}

function ajaxSearch(searchData) {
	$.ajax({
		url : base + "teammember/getSaojiedataList",
		type : "POST",
		data : searchData,
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		dataType : "json",
		success : function(visitData) {
			$(".shopNum").text(data.shopNum);
			$(".percent").text(data.percent); 
			$("#percent").width(data.percent);
			totalElements = visitData.totalElements;
			totalPages = visitData.totalPages;
			seachSuccessTable(visitData);
			var searchTotal = totalElements;

            if (searchTotal != total || searchTotal == 0) {
                total = searchTotal;
                initPaging();
            }
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
}

function initPaging(){
	var totalCount = totalElements; //总条数 
	showCount = totalPages, //显示分页个数
	limit =  2;//每页条数
	$('#callBackPager').extendPagination({
	totalCount : totalCount, 
	showCount : showCount,
	limit : limit,
	callback : function(curr, limit, totalCount) {
//		alert("当前是第"+curr+"页,每页"+ limit+"条,总共"+ totalCount+"条");
		searchData['page'] = curr - 1;
		searchData['size'] = limit;
		ajaxSearch(searchData);
	}
	});
}

/**
 * 检索成功后
 * 
 * @param data
 */
function seachSuccessTable(data) {
	var myTemplate = Handlebars.compile($("#table-template").html());

	$('#saojiedata').html(myTemplate(data));
	
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
