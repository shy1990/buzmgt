var total;
$(function() {
	
	var myDate = new Date();
	var tody = changeDateToString(myDate);

	//加载时根据业务id和考核id查询店铺数据
	searchData['salesmanId'] = $('.search-box').attr("data-sid");
	alert(searchData['salesmanId']);
	searchData['assessId'] = $('.search-box').attr("data-aid");
	ajaxSearch(searchData);
});

/**
 * 根据条件进行检索
 * @param salesmanId
 * @param assessId
 */
function goSearch() { // 业务id，考核id
	
	var beginTime = $('#beginTime').val();
	var endTime = $('#endTime').val();

	if (beginTime == "" || beginTime == null) {
		$('.form_date_start').addClass('has-error');
		return false;
	}

	if (endTime == "" || endTime == null) {
		$('.form_date_end').addClass('has-error');
		return false;
	}

	// 判断日期是否正确
	if (stringToDate(endTime).valueOf()
			- stringToDate(beginTime).valueOf() < 0) {
		return false;
	} else {
		diffTime(beginTime, endTime);
	}
	
}

/**
 * 设置时间
 * 
 * @param beginTime
 * @param endTime
 */
function diffTime(beginTime, endTime) {
	var regionId = $("#regionId  option:selected").val();
	alert(regionId);
	searchData['regionId'] = regionId; // 添加数据
	searchData['begin'] = beginTime;
	searchData['end'] = endTime;
	delete searchData['page'];// 清空当前页码，从第一页开始
	ajaxSearch(searchData);
}

//获取数据
function ajaxSearch(searchData) {
	$.ajax({
		url : base + "assess/getOrderStatistics",
		type : "GET",
		data : searchData,
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		dataType : "json",
		success : function(data) {
			totalElements = data.totalElements;
			totalPages = data.totalPages;
			seachSuccessTable(data);
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

	$('#tableList').html(myTemplate(data));
	
}

//跳转到考核设置
function toAssessStage(salesmanId,assessId){
	window.location.href="/assess/toAssessStage?id="+salesmanId+"&assessId="+assessId;
}
