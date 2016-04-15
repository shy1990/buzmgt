/*任务*/
//添加任务
function addTask() {
	$('#addTask').modal({
		keyboard: false
	})
}

var total;
/*拜访任务列表*/
$(function() {
	var area = $('#area').attr("data-a");
	getRegion(area); // 触发地域搜索
	
});

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
		url : base + "task/visitDataList",
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

Handlebars.registerHelper('WhatStatus', function(value) {
	var subsitename = '';
	if (value === '1') {
		subsitename = '<span class="text-state-ok">已完成</span>';
	} else if (value === '0') {
		subsitename = '<span class="text-state-no">未完成</span>';
	}
	return subsitename;
});

Handlebars.registerHelper('Convert', function(value) {
	var subsitename = '';
	subsitename = changeDateToString(new Date(value));
	return subsitename;
});