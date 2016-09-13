var achieveTotal = 0;
$(function() {
	initExcelExport();// 初始化导出excel
	initSearchData();
	findAchieveList();// 查询列表
	initFunction();
	initExcelExport();
	findBrandIncomeList();
})
/**
 * 跳转添加页面 获取planId和machineType
 */
function add(){
	var planId = $("#planId").val();
	var machineType = findMachineType();
	window.location.href = base + "achieve/add?planId="+planId+"&machineType="+machineType  
}

/**
 * 跳转品牌型号添加页面 获取planId和machineType
 */
function add_brand(){
	var planId = $("#planId").val();
	var machineType = findMachineType();
	window.location.href = base + "brandIncome/add?planId="+planId+"&machineType="+machineType;
}

function findTab(){
	var tab = $('#myTab li.active').attr('data-title');
	return tab;
}

function findMachineType(){
	var machineType = $(".J_MachineType li.active").attr('title');
	return machineType;
}

/**
 * 跳转添加页面 获取planId和machineType
 */
function record(){
	var planId = $("#planId").val();
	window.location.href = base + "achieve/record?planId="+planId;
}
/**
 * 删除
 * @param achieveId
 */
function delAchieve(achieveId){
	if(confirm("确定要删除数据吗？")){
		$.ajax({
			url : base + "achieve/"+achieveId,
			type : "DELETE",
			dataType : "JSON",
			success : function(data){
				if(data.result =="success"){
					alert(data.message);
					window.location.reload();
					return false;
				}
				alert(data.message);
			},
			error : function(data){
				alert("网络异常，稍后重试！");
			}
		});
	}
}
/**
 * 检索模糊查询
 */
function goSearch() {
	var tab = findTab();
	var goodName = $('.input-search').val();
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
					switch (tab) {
						case 'achieve':
							findAchieveList();
							break;
						case 'brandIncome':
							findBrandIncomeList();
							break;
						default:
							break;
					}
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
		var $machineType=findMachineType();
		SearchData['sc_EQ_machineType.id'] = $machineType;
		findAchieveList();
		findBrandIncomeList();
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
				SearchData['sc_EQ_planId'] = $planId;
				var param = parseParam(SearchData);
				delete SearchData['sc_EQ_planId'];
				window.location.href = base + "achieve/export" +"?" + param;
				delete SearchData['planId'];
				var tab = findTab();
				switch (tab) {
					case 'achieve':
						window.location.href = base + "achieve/export" +"?" + param;
						break;
					case 'brandIncome':
						window.location.href = base + "brandIncome/export" +"?" + param;
						break;
					default:
						break;
				}
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
	SearchData['sc_EQ_status'] = "OVER";
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
 * 生成达量统计列表
 * 
 * @param data
 */

function createAchieveTable(data) {
	var myTemplate = Handlebars.compile($("#achieve-table-template").html());
	$('#achieveList').html(myTemplate(data));
}

/**
 * 达量分页
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

/**
 * 查询进行中的品牌型号列表
 * @param page
 */
function findBrandIncomeList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	var $planId = $("#planId").val();
	$.ajax({
		url : "/brandIncome/" + $planId,
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(brandIncomeData) {
			createBrandIncomeTable(brandIncomeData);
			var searchTotal = brandIncomeData.totalElements;
			if (searchTotal != brandTotal || searchTotal == 0) {
				brandTotal = searchTotal;

				initBrandPaging(brandIncomeData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}

/**
 * 生成品牌型号统计列表
 *
 * @param data
 */
function createBrandIncomeTable(data) {
	var myTemplate = Handlebars.compile($("#brand-table-template").html());
	$('#brandIncomeList').html(myTemplate(data));
}

/**
 * 品牌型号分页
 *
 * @param data
 */
function initBrandPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#initBrandPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findBrandIncomeList(curr - 1);
		}
	});
}

/**
 * 品牌型号方案查看
 * @param brandId
 */
function brandLook(brandIncomeId) {
	window.location.href = base + "brandIncome/show/" + brandIncomeId;
}

/**
 * 品牌型号终止弹窗
 * @param brandIncomeId
 */
function brandStop(brandIncomeId) {
	$('#addTask').modal({
		keyboard: false
	})
	$("#brandId").val(brandIncomeId);
}

/**
 * 品牌型号终止
 */
function stop() {
	var $brandIncomeId = $("#brandId").val();
	$.ajax({
		url : "/brandIncome/stop/" + $brandIncomeId,
		type : "put",
		dataType : "json",
		success : function(data) {
			$('#brandStop').modal('hide');
			if (data.status == "success"){
				alert(data.successMsg);
			}else {
				alert(data.errorMsg);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}

/**
 * 品牌型号设置记录
 */
function setRecord() {
	var planId = $("#planId").val();
	var machineType=findMachineType();
	window.location.href = base + "brandIncome/record?planId="+planId+"&machineType="+machineType;
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
