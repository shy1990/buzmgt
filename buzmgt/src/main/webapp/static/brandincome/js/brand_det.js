/*品牌型号明细*/
var brandDetailTotal = 0;//分页初始值
var $regionId;//区域ID
var orderType;//订单类别
var goodId;//商品ID
$(function() {
	initData();//加载上部数据
	initSearchData();//加载检索数据
	initExcelExport();//初始化导出
	findDetailList();//加载明细列表
});

/**
 * 加载上部数据
 */
function initData() {
	var processData = JSON.parse(window.name);
	$('.info-zq .text-lv').text(processData['cycleSales']);
	$('.info-zq .text-jv').text(processData['hedgeNums']);
	$('.J_brand').text(processData['brandName']);
	$('.J_date').text(processData['date']);
	$('.info-zq .text-gren').text(processData['realSales']);
}

/**
 * 加载检索数据
 */
function initSearchData() {
	$regionId = $('.J_regionId option:selected').val();
	orderType = $('.J_orderType option:selected').val();
	goodId = $('#goodId').val();
	SearchData['goodId'] = goodId;
	SearchData['regionId'] = $regionId;//加载默认区域
	SearchData['orderType'] = orderType;//加载默认订单类别
	var date = $('.J_date').text();
	var str = new Array();
	str = date.split("至");
	SearchData['startDate'] = str[0].trim();//开始日期
	SearchData['endDate'] = str[1].trim();//结束日期
	console.log(str[0]);
	console.log(str[1]);
}

/**
 * 根据区域和订单类别筛选
 */
function goFilter() {
	$regionId = $('.J_regionId option:selected').val();
	orderType = $('.J_orderType option:selected').val();
	if (!isEmpty($regionId)) {
		delete SearchData['terms'];
		SearchData['regionId'] = $regionId;
		SearchData['orderType'] = orderType;
		findDetailList();
	}else {
		alert("请选择区域!");
	}
}

/**
 * 根据店铺名字或者订单号检索
 */
function goSearch() {
	var terms = $('.J_terms').val();//获取商家名称或订单号
	if (!isEmpty(terms)) {
		SearchData['terms'] = terms;
		findDetailList();
	}else {
		alert("名称或订单号不能为空!");
	}
}

/**
 * 查询明细列表
 * @param page
 */
function findDetailList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : "/brandIncome/detailList/" + $regionId,
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(detailData) {
			if (orderType == 'sales'){
				createBrandDetailTable(detailData);
				var searchTotal = detailData.totalElements;
				if (searchTotal != brandDetailTotal || searchTotal == 0) {
					brandDetailTotal = searchTotal;

					brandDetailPaging(detailData);
				}
			}else {
				createSHDetailTable(detailData);
				var searchTotal = detailData.totalElements;
				if (searchTotal != brandDetailTotal || searchTotal == 0) {
					brandDetailTotal = searchTotal;

					brandDetailPaging(detailData);
				}
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}

/**
 * 生成明细列表
 *
 * @param data
 */
function createBrandDetailTable(data) {
	var myTemplate = Handlebars.compile($("#detail-table-template").html());
	$('#detailList').html(myTemplate(data));
}

/**
 * 生成售后明细列表
 *
 * @param data
 */
function createSHDetailTable(data) {
	var myTemplate = Handlebars.compile($("#returnDetail-table-template").html());
	$('#detailList').html(myTemplate(data));
}

/**
 * 生成明细分页
 *
 * @param data
 */
function brandDetailPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#initDetailPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findDetailList(curr - 1);
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
			$regionId = $('.J_regionId option:selected').val();
			SearchData['regionId'] = $regionId;
			var param = parseParam(SearchData);
			delete SearchData['page'];
			delete SearchData['size'];
			window.location.href = base + "brandIncome/detail/export" +"?" + param;
		});
}

/**
 * 查看订单
 * @param orderId
 */
function orderDetail(orderId) {
	window.location.href = base + "brandIncome/detail/"+orderId;
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

/**
 * 判读是否为空
 *
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function isEmpty(value) {
	return value == undefined || value == "" || value == null;
}