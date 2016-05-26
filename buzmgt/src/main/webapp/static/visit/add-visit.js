var total;
/*添加拜访任务*/
$(function() {
	var area = $('#area').val();
	getRegion(area); // 触发地域搜索
	
	$(".form_datetime").datetimepicker({
		format: "yyyy-mm-dd",
		language: 'zh-CN',
		weekStart: 1,
		todayBtn: 1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		pickerPosition: "bottom-left",
		forceParse: 0
	});
	$('.form_datetime').datetimepicker('setStartDate', getCurentTime(0));
	$('.form_datetime').datetimepicker('hide');
	
	function getCurentTime(AddDayCount) {
		var date = new Date();
		date.setDate(date.getDate()+AddDayCount);//获取AddDayCount天后的日期
		var seperator1 = "-";
		var month = date.getMonth() + 1;
		var strDate = date.getDate();
		if (month >= 1 && month <= 9) {
			month = "0" + month;
		}
		if (strDate >= 0 && strDate <= 9) {
			strDate = "0" + strDate;
		}
		var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
		return currentdate;
	}
});

/**
 * 根据地区进行检索
 * 
 * @param area
 */
function getRegion(area) { // 地区
	searchData['regionid'] = area; // 添加数据
	searchData['status'] = "1";
	searchData['condition'] = "2";
	ajaxSearch(searchData);
}

function ajaxSearch(searchData) {
	$.ajax({
		url : base + "task/shopList",
		type : "GET",
		data : searchData,
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		dataType : "json",
		success : function(shopData) {
			totalElements = shopData.totalElements;
			totalPages = shopData.totalPages;
			$('#totalShop').text(totalElements);
			seachSuccessTable(shopData);
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
	var showCount = 10, //显示分页个数
	limit =  searchData['size'];//每页条数
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
 * 根据状态进行检索
 * 
 * @param status
 */
function searchStatusData(status) { // 状态：考核中、已转正
	if (status == "CHECKIN") {
		delete searchData['page'];
		searchData['status'] = "1"; // 状态为考核
	} else {
		delete searchData['page'];
		searchData['status'] = "2"; // 状态为转正
	}
	ajaxSearch(searchData);
}

/**
 * 根据条件进行检索
 * 
 * @param condition
 */
function searchConditionData(condition) { // 条件：活跃、未提货、一次
	if (condition == "TWO") {
		delete searchData['page'];
		searchData['condition'] = "2"; // 条件为活跃
	}
	if (condition == "ZERO"){
		delete searchData['page'];
		searchData['condition'] = "0"; // 条件为未提货
	}
	if(condition == "ONE"){
		delete searchData['page'];
		searchData['condition'] = "1"; // 条件为一次
	}
	ajaxSearch(searchData);
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

//添加拜访
function addVisit(registId,userId,shopName) {
	$.ajax({
		url : base + "task/lastVisit",
		type : "post",
		data : {registId:registId},
		dataType : "json",
		success : function(result) {
			if("ok" == result.status){
				$('#addVisit').modal({
					keyboard: false
				})
			}else{
				$('#tips').modal({
					keyboard: true
				})
			}
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
	$('#registId').val(registId);
	$('#userId').val(userId);
	$('#taskName').val(shopName+"拜访");
}

function saveVisit(){
	var arr = $('#addForm').serializeArray();
	var param = $.param(arr);
	$.ajax({
		url : base + "task/saveVisit",
		type : "post",
		data : param,
		dataType : "json",
		success : function(result) {
			if("ok" == result.status){
				$('#addVisit').modal('hide');
				alert("添加成功!");
				ajaxSearch(searchData);
			}else{
				alert("保存失败!");
			}
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
}
