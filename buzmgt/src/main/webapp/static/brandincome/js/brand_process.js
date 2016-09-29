/*品牌型号进程*/
var brandProcessDataTotal = 0;
$(function() {
	findProcessList();
	initExcelExport();//导出进程
})

// 注册索引+1的helper
var handleHelper = Handlebars.registerHelper("addOne", function(index) {
	// 返回+1之后的结果
	return index + 1;
});

/**
 * 根据区域星级和业务等级筛选
 */
function goFilter() {
	var levelName = $('.J_serviceLevel option:selected').text();
	var starsLevel = $('.J_regionLevel option:selected').val();
	if (!isEmpty(levelName) && !isEmpty(starsLevel)) {
		delete SearchData['trueName'];
		SearchData['levelName'] = levelName;
		SearchData['starsLevel'] = starsLevel;
		findProcessList();
	}else {
		alert("请选择区域等级和业务星级!");
	}
}

/**
 * 根据业务名字检索
 */
function goSearch() {
	var trueName = $('.text-gery-hs').val();//获取业务名字
	if (!isEmpty(trueName)) {
		delete SearchData['levelName'];
		delete SearchData['starsLevel'];
		SearchData['trueName'] = trueName;
		findProcessList();
	}else {
		alert("业务名字不能为空!");
	}
}

/**
 * 根据当前品牌型号查询业务进程
 * @param page
*/
function findProcessList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	var $brandId = $("#brandId").val();
	$.ajax({
		url : "/brandIncome/processList/" + $brandId,
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(processData) {
			createBrandProcessDataTable(processData);
			var searchTotal = processData.totalElements;
			if (searchTotal != brandProcessDataTotal || searchTotal == 0) {
				brandProcessDataTotal = searchTotal;

				brandProcessPaging(processData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}

/**
 * 生成品牌型号进程列表
 *
 * @param data
 */
function createBrandProcessDataTable(data) {
	var myTemplate = Handlebars.compile($("#process-table-template").html());
	$('#brandProcessList').html(myTemplate(data));
}

/**
 * 当前进行品牌型号分页
 *
 * @param data
 */
function brandProcessPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#brandProcessPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findProcessList(curr - 1);
		}
	});
}

/**
 * 导出excel
 */
function initExcelExport() {
	$('.table-export').on(
		'click',
		function() {
			var $brandId = $("#brandId").val();
			SearchData['brandId'] = $brandId;
			var param = parseParam(SearchData);
			delete SearchData['brandId'];
			window.location.href = base + "brandIncome/process/export" +"?" + param;
		});
}

var parseParam = function(param, key) {
	var paramStr = "";
	if (param instanceof String || param instanceof Number
		|| param instanceof Boolean) {
		paramStr += "&" + key + "=" + encodeURIComponent(param);
	} else {
		$.each(param, function(i) {
			var k = key == null ? i : key
			+ (param instanceof Array ? "[" + i + "]" : "." + i);
			paramStr += '&' + parseParam(this, k);
		});
	}
	return paramStr.substr(1);
};

/**
 * 日期格式转换helper
 */
Handlebars.registerHelper('formDate', function(value) {
	if (value == null || value == "") {
		return "----";
	}
	return changeDateToString_(new Date(value));
});

Handlebars.registerHelper('compareDate', function(startDate,endDate) {
	var html = "";
	var nowDate = getTodayDate();
	startDate = changeDateToString(new Date(startDate));
	endDate = changeDateToString(new Date(endDate));
	if (checkDate(startDate,nowDate) && checkDate(nowDate,endDate)) {
		html += '<span class="ph-on">进行中</span>';
	}
	if (compareDate(startDate,nowDate)) {
		html += '<span class="ph-weihes">未使用</span>';
	}
	if (checkDate(endDate,nowDate)) {
		html += '<span class="ph-gery">已过期</span>';
	}
	return new Handlebars.SafeString(html);
});

/**
 * 判读是否为空
 *
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function isEmpty(value) {
	return value == undefined || value == "" || value == null;
}

var processData = {

}
function detail(regionId,goodId) {
	console.log(regionId);
	processData['cycleSales'] = $('.info-zq .text-lv').text();
	processData['hedgeNums'] = $('.info-zq .text-jv').text();
	processData['realSales'] = $('.info-zq .text-gren').text();
	processData['brandName'] = $('.text-lan').text();
	processData['date'] = $('.text-black').text();

	window.name = JSON.stringify(processData);
	window.location = "/brandIncome/detail?regionId=" + regionId+"&goodId="+goodId;

}