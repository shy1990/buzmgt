var total = 0;
$(function(){
	$('#callBackPager').hide();
	$('a.title-btn').click(function(){
		$('.title-btn').removeClass('active');
		$(this).addClass('active');
		var $_btnText=$(this).text();
		console.info($_btnText);
		if("地图"===$_btnText){
			$('.saojie-list').hide(1);
			$('#callBackPager').hide();
			$('.saojie-map').slideDown(500);
		}else if("列表"===$_btnText){
			$('.saojie-map').hide(1);
			$('#callBackPager').show();
			$('.saojie-list').slideDown(500);
			var userId = $("#userId").html();
			var regionId = $("#regionId").val();
			searchData['userId'] = userId;
			searchData['regionid'] = regionId;
			
			ajaxSearch(searchData);
		}
	})
	
	
});


function ajaxSearch(searchData) {
	$.ajax({
		url : base + "teammember/getSaojiedataList",
		type : "GET",
		data : searchData,
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		dataType : "json",
		success : function(data) {
			$(".shopNum").text(data.shopNum);
			$(".percent").text(data.percent); 
			$("#percent").width(data.percent);
			totalElements = data.list.totalElements;
			totalPages = data.list.totalPages;
			seachSuccessTable(data.list);
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
	limit =  1;//每页条数
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
