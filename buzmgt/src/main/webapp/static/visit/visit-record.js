/*拜访记录all*/

var total;
var condition = {};
/*拜访任务列表*/
$(function() {
	var area = $('#area').attr("data-a");
	getRegion(area); // 触发地域搜索
	
	condition['regionid'] = area;
	ajaxTotalVisit(condition);
});

function ajaxTotalVisit(condition) {
	$.ajax({
		url : base + "visit/totalVisit",
		type : "GET",
		data : condition,
		dataType : "json",
		success : function(data) {
			$('#totalElements').text(data);
		}
	});
}

/**
 * 根据地区进行检索
 * @param area
 */
function getRegion(area) { // 地区
	searchData['regionid'] = area; // 添加数据
	ajaxSearch(searchData);
}

function ajaxSearch(searchData) {
	$.ajax({
		url : base + "visit/visitRecordList",
		type : "GET",
		data : searchData,
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		dataType : "json",
		success : function(visitData) {
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
	//createTable(1, limit, totalCount);
	$('#callBackPager').extendPagination({
	totalCount : totalCount, 
	showCount : showCount,
	limit : limit,
	callback : function(curr, limit, totalCount) {
//		alert("当前是第"+curr+"页,每页"+ limit+"条,总共"+ totalCount+"条");
		searchData['page'] = curr - 1;
		searchData['size'] = limit;
		ajaxSearch(searchData);
//			createTable(1, limit, totalCount); //生成列表
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
	
//	paging(data);
}

/**
 * 检索
 */
function goSearch() {
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
	delete searchData['page'];// 清空当前页码，从第一页开始
	searchData['beginTime'] = beginTime;//设置时间
	searchData['endTime'] = endTime;
	condition['beginTime'] = beginTime;
	condition['endTime'] = endTime;
	ajaxTotalVisit(condition);
	ajaxSearch(searchData);

}

/**
 * 查看该业务拜访
 * @param userId
 */
function check(userId){
	window.location.href = "/visit/visitRecordYWPage?userId="+userId;
}