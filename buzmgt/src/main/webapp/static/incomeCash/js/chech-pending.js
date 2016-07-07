var checkPendingTotal = 0;
$(function() {
	initDateInput();// 初始化日期
	findCheckCashList();// 查询列表
	findCheckDebtList();//查询为没有流水单号的审核记录
	findUnCheckBankTread();//查询未匹配银行打款交易记录 
	initExcelExport();//初始化导出excel
	
})
function initDateInput() {
	// 初始化日期 前1天
	var newDate = (new Date()).DateAdd('d', -2);
	var nowDate = changeDateToString(newDate);
	SearchData['sc_EQ_createDate'] = nowDate;
	$('#searchDate').val(nowDate);
	$('#archivingDate').val(nowDate);

	$('.form_datetime').datetimepicker({
		format : "yyyy-mm-dd",
		language : 'zh-CN',
		endDate : new Date(),
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		pickerPosition : "bottom-right",
		forceParse : 0
	}).on('changeDate', function(ev) {
		var searchDate = $('#searchDate').val();
	});
}

/**
 * 检索
 */
function goSearch() {
	var Time = $('#searchDate').val();
	if (!isEmpty(Time)) {
		SearchData['sc_EQ_createDate'] = Time;
		findCheckCashList();
		findCheckDebtList();
	}
}
/**
 * 导出excel
 */
function initExcelExport() {
	$('.table-export').on('click', function() {
		var startTime = $('#searchDate').val();
		if (!isEmpty(startTime)) {

			SearchData['sc_EQ_createDate'] = startTime;

			window.location.href = base + "checkCash/export?sc_EQ_createDate="+startTime;
		}

	});
}
function archiving(){
	var archivingDate = $('#archivingDate').val();
	$.ajax({
		 url:base+'/checkCash/archiving?archivingDate='+archivingDate,
		 type:"POST",
		 dataType:"json",
		 success:function(data){
			 if(data.result == 'failure'){
				 alert(data.message);
				 return false;
			 }
			 alert("操作成功");
			 
		 },
		 error:function(data){
			 alert("操作失败！");
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
function findCheckCashList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : "/checkCash",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createCheckPendingTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != checkPendingTotal || searchTotal == 0) {
				checkPendingTotal = searchTotal;

				checkPendingPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
function findCheckDebtList() {
	var createDate = $('#searchDate').val();
	$.ajax({
		url : "/checkCash/debtCheck?createDate="+createDate,
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createCheckDebtTable(orderData);
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
/**
 * 生成油补统计列表
 * 
 * @param data
 */

function createCheckPendingTable(data) {
	var myTemplate = Handlebars.compile($("#checkPending-table-template").html());
	$('#checkPendingList').html(myTemplate(data));
}
/**
 * 
 * @param data
 */
function createCheckDebtTable(data) {
	var myTemplate = Handlebars.compile($("#checkDebt-table-template").html());
	$('#checkDebtList').html(myTemplate(data));
}
/**
 * 生成为匹配交易记录
 * @param data
 */

function createUnCheckTable(data) {
	var myTemplate = Handlebars.compile($("#unCheck-table-template").html());
	$('#unCheckList').html(myTemplate(data));
}
/**
 * 分页
 * 
 * @param data
 */
function checkPendingPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#checkCashPager').extendPagination({
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
	return changeDateToString(new Date(value));
});
/**
 * 待付金额
 */
Handlebars.registerHelper('disposeStayMoney', function(value) {
	if (value == 0 || value == 0.0 || value == 0.00) {
		return value;
	}
	return '<span class="single-exception">'+value+'</span>';
});
/**
 * 待付金额
 */
Handlebars.registerHelper('isCheckStatus', function(isCheck,userId,createDate) {
	var formcreateDate=changeDateToString(new Date(createDate));
	var html='<button class="btn btn-sm btn-blue" onClick="checkPending(\''+userId+'\',\''+formcreateDate+'\')">确认</button>'
	if (isCheck == '已审核') {
		return '<button class="btn btn-sm btn-blue" disabled>已审核</button> ';
	}
	return html;
});
/**
 * 审核没有流水单号的交易记录
 */
Handlebars.registerHelper('isCheckDebtStatus', function(isCheck,userId,createDate) {
	var formcreateDate=changeDateToString(new Date(createDate));
	var html='<button class="btn btn-sm btn-blue" onClick="checkDebt(\''+userId+'\',\''+formcreateDate+'\')">确认</button>'
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
		url : base+"/checkCash/salesmanName?salesmanName=" + salesmanName+"&createDate="+createDate,
		type : "GET",
		dataType : "json",
		success : function(data) {
			if (data.status=='success') {
				createCheckPendingTable(data);
				return false;
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
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
 * 确认审核
 * @param userId
 * @param createDate
 */
function checkPending(userId,createDate){
	$.ajax({
		url:base+"/checkCash/"+userId+"?createDate="+createDate,
		type:"POST",
		dataType:"json",
		success:function(data){
			if("success"===data.status){
				alert(data.successMsg);
				findCheckCashList();
				return ;
			}
			alert(data.errorMsg);
		},
		error:function(data){
			alert("");
		}
	})
}
/**
 * 确认审核木有流水单号的记录
 * @param userId
 * @param createDate
 */
function checkDebt(userId,createDate){
	$.ajax({
		url:base+"checkCash/debtCheck/"+userId+"?createDate="+createDate,
		type:"POST",
		dataType:"json",
		success:function(data){
			if("success"===data.status){
				alert(data.successMsg);
				findCheckDebtList();
				return ;
			}
			alert(data.errorMsg);
		},
		error:function(data){
			alert("系统异常，稍后重试！");
		}
	})
}
/**
 * 查询未匹配银行打款交易记录
 */
function findUnCheckBankTread(){
	$.ajax({
		url:base+"/checkCash/unCheck",
		type:"GET",
		dataType:"json",
		success:function(data){
			createUnCheckTable(data);
		},
		error:function(data){
			alert("查询失败！");
		}
	})
}
/**
 * 删除未匹配记录
 */
function deleteUnCheck(id){
	$.ajax({
		url:base+"/checkCash/delete/"+id,
		type:"GET",
		dataType:"json",
		success:function(data){
			if("success"===data.status){
				alert(data.successMsg);
				findUnCheckBankTread();
				return ;
			}
			alert(data.errorMsg);
		},
		error:function(data){
			alert("操作失败");
		}
	})
}

