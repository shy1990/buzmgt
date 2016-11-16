var awardTotal = 0;
$(function() {
	initExcelExport();// 初始化导出excel
	initSearchData();
	findAwardList();// 查询列表
})
/**
 * 跳转添加页面 获取planId和machineType
 */
function add(){
	var planId = $("#planId").val();
	var machineType = $(".J_MachineType li.active").attr('title');
	window.location.href = base + "award/add?planId="+planId+"&machineType="+machineType  
}
/**
 * 跳转添加页面 获取planId和machineType
 */
function record(){
	var planId = $("#planId").val();
	window.location.href = base + "award/record?planId="+planId;
}
function recordForAudit(){
	var planId = $("#planId").val();
	window.location.href = base + "award/recordForAudit?planId="+planId;
}
/**
 * 删除
 * @param awardId
 */
function delAward(awardId){
	if(confirm("确定要删除数据吗？")){
		$.ajax({
			url : base + "award/"+awardId,
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
	var goodName = $('#searchGoodsname').val();
	if (!isEmpty(goodName)) {
		$.ajax({
			url:base + "goods/likeBrandName?name="+goodName,
			type:"GET",
			dateType:"JSON",
			success:function(data){
				console.info(data);
				if(data.length>0){
					var ids=data.join(',');
					SearchData['sc_IN_awardGoods.brand.id'] = ids;
					findAwardList();
					delete SearchData['sc_IN_awardGoods.brand.id'];
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
		findAwardList();
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
				window.location.href = base + "award/export" +"?" + param;
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
	SearchData['sc_GTE_endDate'] = nowDate;
	SearchData['sc_LTE_startDate'] = nowDate;
	SearchData['sc_EQ_status'] = "OVER";
}

function findAwardList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	var $planId = $("#planId").val();
	$.ajax({
		url : base + "award/" + $planId,
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createAwardTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != awardTotal || searchTotal == 0) {
				awardTotal = searchTotal;

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

function createAwardTable(data) {
	var myTemplate = Handlebars.compile($("#award-table-template").html());
	$('#awardList').html(myTemplate(data));
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
			findAwardList(curr - 1);
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
