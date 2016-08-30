/*月指标设置*/
var total;
$(function() {
	nowTime();
	goSearch();
});

function toUpdate(flag){
	window.location.href = "/monthtarget/toUpdate?flag="+flag;
}

/**
 * 初始化日期 前1天
 */
function nowTime() {
	var date = new Date();
	date.setMonth(date.getMonth()+1);//获取AddDayCount后的月
	var seperator = "-";
	var month = date.getMonth() + 1;
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	var currentdate = date.getFullYear() + seperator + month;
	$('.form_datetime').val(currentdate);
}

function goSearch() {
	var datetime = $(".form_datetime").val();
	var name = $(".cs-select").val();
	searchData['targetCycle'] = datetime;
	searchData['userName'] = name;
	ajaxSearch(searchData);
}

function ajaxSearch(searchData) {
	$.ajax({
		url : base + "monthtarget/findMonthTarget",
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
 * 检索成功后
 *
 * @param data
 */
function seachSuccessTable(data) {
	var myTemplate = Handlebars.compile($("#table-template").html());

	$('#tableList').html(myTemplate(data));
}

function update(id,flag){
	var btnId = $("#"+id).text();
	if (btnId == "已发布"){
		flag = "look";
	}
	window.location.href = "/monthtarget/toUpdate?flag="+flag+"&id="+id;
}

function publish(id) {
	$.ajax({
		url : base + "monthtarget/publish/"+id,
		type : "GET",
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
				"application/json; charset=UTF-8");
		},
		dataType : "text",
		success : function(data) {
			if(data == "ok"){
				alert("发布成功!");
				$("#"+id).text("已发布").attr('disabled',true);
			}
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
}

function publishAll() {
	$.ajax({
		url : base + "monthtarget/publishAll",
		type : "POST",
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
				"application/json; charset=UTF-8");
		},
		dataType : "text",
		success : function(data) {
			if(data == "ok"){
				alert("全部发布成功!");
				window.location.reload();
			}
			if (data == "non"){
				alert("您的指标不存在!");
			}
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
}