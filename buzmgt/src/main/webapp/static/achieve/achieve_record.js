var achieveTotal = 0;
$(function() {
	initExcelExport();// 初始化导出excel
	initSearchData();
	findAchieveList();// 查询列表
	initFunction();
	initExcelExport();
})
/**
 * 跳转添加页面 获取planId和machineType
 */
function add(){
	var planId = $("#planId").val();
	var machineType = $(".J_MachineType li.active").attr('title');
	window.location.href = base + "achieve/add?planId="+planId+"&machineType="+machineType  
}
/**
 * 检索模糊查询
 */
function goSearch() {
	var goodName = $('#searchGoodsname').val();
	if (!isEmpty(goodName)) {
		$.ajax({
			url:"/goods/likeBrandName?name="+goodName,
			type:"GET",
			dateType:"JSON",
			success:function(data){
				console.info(data);
				if(data.length>0){
					var ids=data.join(',');
					SearchData['sc_IN_brand.id'] = ids;
					findAchieveList();
					delete SearchData['sc_IN_brand.id'];
					return false;
				}
				alert("没有此品牌");
			},
			error:function(data){
				alert("网络异常，稍后重试！");
			}
		});
	}else{
		alert("请输入品牌名称");
	}
}
function initFunction(){
	$(".J_MachineType li").on("click",function(){
		$(this).addClass("active");
		$(this).siblings("li").removeClass("active");
		var $machineType=$(".J_MachineType li.active").attr('title');
		SearchData['sc_EQ_machineType.id'] = $machineType;
		findAchieveList();
	});
}
/**
 * 导出excel
 */
function initExcelExport() {
	$('.table-export').on(
			'click',
			function() {
				var $planId = $("#planId").val();
				SearchData['planId'] = $planId;
				var param = parseParam(SearchData);
				delete SearchData['planId'];
				window.location.href = base + "achieve/export" +"?" + param;
			});
}
/**
 * 处理检索条件
 * 
 * @returns {String}
 */
function conditionProcess() {
	var SearchData_ = "sc_EQ_createDate="
			+ (SearchData.sc_GTE_dateTime == null ? ''
					: SearchData.sc_GTE_dateTime)
			+ "&sc_EQ_createDate="
			+ (SearchData.sc_LTE_dateTime == null ? ''
					: SearchData.sc_LTE_dateTime);

	return SearchData_;
}
function initSearchData() {
	var nowDate = getTodayDate();
	var $machineType=$(".J_MachineType li.active").attr('title');
	SearchData['sc_GTE_endDate'] = nowDate;
	SearchData['sc_LTE_startDate'] = nowDate;
//	SearchData['sc_EQ_status'] = "OVER";
	SearchData['sc_EQ_machineType.id'] = $machineType;
}

function findAchieveList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	var $planId = $("#planId").val();
	$.ajax({
		url : "/achieve/" + $planId,
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createAchieveTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != achieveTotal || searchTotal == 0) {
				achieveTotal = searchTotal;

				initPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
// 注册索引+1的helper
var handleHelper = Handlebars.registerHelper("addOne", function(index) {
	// 返回+1之后的结果
	return index + 1;
});
/**
 * 生成油补统计列表
 * 
 * @param data
 */

function createAchieveTable(data) {
	var myTemplate = Handlebars.compile($("#achieve-table-template").html());
	$('#achieveList').html(myTemplate(data));
}
/**
 * 分页
 * 
 * @param data
 */
function initPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#initPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findCheckCashList(curr - 1);
		}
	});
}
Handlebars.registerHelper('formDate', function(value) {
	if (value == null || value == "") {
		return "----";
	}
	return changeDateToString_(new Date(value));
});
/**
 * 待付金额
 */
Handlebars.registerHelper('disposeStayMoney', function(value) {
	if (value == 0 || value == 0.0 || value == 0.00) {
		return value;
	}
	return '<span class="single-exception">' + value + '</span>';
});
/**
 * 待付金额
 */
Handlebars
		.registerHelper(
				'isCheckStatus',
				function(isCheck, userId, createDate) {
					var formcreateDate = changeDateToString(new Date(createDate));
					var html = '<button class="btn btn-sm btn-blue" onClick="checkPending(\''
							+ userId
							+ '\',\''
							+ formcreateDate
							+ '\')">确认</button>'
					if (isCheck == '已审核') {
						return '<button class="btn btn-sm btn-blue" disabled>已审核</button> ';
					}
					return html;
				});
/**
 * 审核没有流水单号的交易记录
 */
Handlebars.registerHelper('isCheckDebtStatus', function(isCheck, userId,
		createDate) {
	var formcreateDate = changeDateToString(new Date(createDate));
	var html = '<button class="btn btn-sm btn-blue" onClick="checkDebt(\''
			+ userId + '\',\'' + formcreateDate + '\')">确认</button>'
	if (isCheck == '已审核') {
		return '<button class="btn btn-sm btn-blue" disabled>已审核</button> ';
	}
	return html;
});
/**
 * 根据流水号查询
 */
function findBySalesManName() {
	var salesmanName = $('#salesManName').val();
	var createDate = $('#searchDate').val();
	$.ajax({
		url : base + "/checkCash/salesmanName?salesmanName=" + salesmanName
				+ "&createDate=" + createDate,
		type : "GET",
		dataType : "json",
		success : function(data) {
			if (data.status == 'success') {
				createCheckPendingTable(data);
				return false;
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
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
 * 判读是否为空
 * 
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function isEmpty(value) {
	return value == undefined || value == "" || value == null;
}
/**
 * 查询未匹配银行打款交易记录
 */
function findUnCheckBankTread() {
	$.ajax({
		url : base + "/checkCash/unCheck",
		type : "GET",
		dataType : "json",
		success : function(data) {
			createUnCheckTable(data);
		},
		error : function(data) {
			alert("查询失败！");
		}
	})
}
/**
 * 删除未匹配记录
 */
function deleteUnCheck(id) {
	$.ajax({
		url : base + "/checkCash/delete/" + id,
		type : "GET",
		dataType : "json",
		success : function(data) {
			if ("success" === data.status) {
				alert(data.successMsg);
				findUnCheckBankTread();
				return;
			}
			alert(data.errorMsg);
		},
		error : function(data) {
			alert("操作失败");
		}
	})
}
