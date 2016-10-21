/**
 * 初始化文件上传
 */
function initFileUpload() {
	// 文件上传
	$("#file-input").fileinput({
		language : 'zh',
		uploadUrl : '/hedge/upload',
		allowedFileExtensions : [ 'xls', 'xlsx' ],
		showPreview : false,
		showUpload : false,
		dropZoneEnabled : false,
	});
	$('#file-input').fileupload({
		dataType : 'json',
		add : function(e, data) {
			$("#uploadFileDiv").show();
			$("#uploadFile").on("click", function() {
				var importDate = $("#importDate").val();
				// 修改fileupload插件上传时的url，带参数。
				$("#file-input").fileupload('option', 'url', '/hedge/upload');
				data.submit();
			});
		},
		done : function(e, data) {
			if (data.result.result === "failure") {
				alert(data.result.message);
			} else {
				alert(data.result.message);
			}
			// window.location.reload();
		}
	});
}
// 初始化时间框
function initDateInput() {
	$('body input').val('');
	$(".form_datetime").datetimepicker({
		format : "yyyy-mm-dd",
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		pickerPosition : "bottom-right",
		forceParse : 0
	});
	var $_haohe_plan = $('.J_kaohebar').width();
	var $_haohe_planw = $('.J_kaohebar_parents').width();
	$(".J_btn").attr("disabled", 'disabled');
	if ($_haohe_planw === $_haohe_plan) {
		$(".J_btn").removeAttr("disabled");
	}
}

var WsearchData = {
	"page" : 0,
	"size" : 20,
	"order" : "id"
};
var itemTotal = 0;
// 加载页面
function findPlanUserList(flag,page) {
	page = page == null || page == '' ? WsearchData.page : page;
	WsearchData.page = page;
	if (flag == 0) {
		WsearchData["SC_EQ_shdate"] = $("#searchDate").val();
		WsearchData["SC_LK_orderno"] = "";
	} else if (flag == 1) {
		WsearchData["SC_LK_orderno"] = $("#orderNo").val();
		WsearchData["SC_EQ_shdate"] = "";
	}
	$.ajax({
		url : "/hedge/getData",
		type : "GET",
		data : WsearchData,
		dataType : "json",
		success : function(orderData) {
			createTaskTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != itemTotal || searchTotal == 0 || page == 0) {
				itemTotal = searchTotal;
				oilCostPaging(orderData);
			}
		},
		error : function(data) {
			alert("系统异常，请稍后重试！");
		}
	});
}

function createTaskTable(data) {
	Handlebars.registerHelper("regex", function(index) {
		// 返回+1之后的结果
		var number = index;
		return number < 10 ? "0" + number : number;
	});

	var myTemplate = Handlebars.compile($("#user-table-template").html());
	$('#userList').html(myTemplate(data));
}
/**
 * 报备的分页
 * 
 * @param data
 */
function oilCostPaging(data) {
	var totalCount = data.totalElements, limit = WsearchData.size;
	$('#usersPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findPlanUserList(2,curr - 1);
		}
	});
}