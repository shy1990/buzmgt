var total;
$(function() {
	
	ajaxSearch(searchData);
});

function saveTimes(){
	var regionId = $("#regionId  option:selected").val();
	var times = $(".box-cs").val();
	$.ajax({
		url : base + "assess/saveAssessTime/"+regionId,
		type : "POST",
		data : {"times":times},
		dataType : "text",
		success : function(data) {
			if(data == "ok"){
				$(".box-cs").val("");
				alert("设置成功!");
				window.location.reload();
			}
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
}

//获取数据
function ajaxSearch(searchData) {
	$.ajax({
		url : base + "assess/assessTimes",
		type : "GET",
		data : searchData,
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		dataType : "json",
		success : function(data) {
			totalElements = data.result.totalElements;
			totalPages = data.result.totalPages;
			searchData["size"] = data.result.size;
			seachSuccessTable(data.result);
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

//初始化分页
function initPaging(){
	var totalCount = totalElements; //总条数 
	showCount = 10, //显示分页个数
	limit =  searchData["size"];//每页条数
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

	$('#tableList').html(myTemplate(data));
	
}
