var achieveTotal = 0;
var brandTotal = 0;
$(function() {
	initExcelExport();// 初始化导出excel
	initSearchData();
	findAchieveList();// 查询列表
	initFunction();
	initExcelExport();
	findBrandIncomeList();
	findSectionList();//叠加查询
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
	var tab = $('#myTab li.active').attr('title');
	return tab;
}

function findMachineType(){
	var machineType = $(".J_MachineType li.active").attr('title');
	return machineType;
}

/**
 * 跳转设置记录页面 获取planId和machineType
 */
function record(){
	var planId = $("#planId").val();
	window.location.href = base + "achieve/record?planId="+planId;
}
/**
 * 跳转审核页面 获取planId和machineType
 */
function recordForAudit(){
	var planId = $("#planId").val();
	window.location.href = base + "achieve/recordForAudit?planId="+planId;
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
		findSectionList();
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
	$('#brandStop').modal({
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

/**
 * 品牌型号查看进程
 */
function brandProcess(brandId) {
	window.location.href = base + "/brandIncome/process/"+brandId;
}

Handlebars.registerHelper('formDate', function(value) {
	if (value == null || value == "") {
		return "----";
	}
	return changeDateToString_(new Date(value));
});

//----------------------价格区间的操作---------------------------------------------
/**
 * 查找正在使用的价格区间
 * @param type
 * @param planId
 */
function findSectionList(){
	var $machineType=$(".J_MachineType li.active").attr('title');
	var $planId = $("#planId").val();
	$.ajax({
		url : "/section/listNow?planId=" + $planId+"&type="+$machineType,
		type : "GET",
		dataType : "json",
		success:function(data){
			console.log(data);
			createSectionTable(data)
		},
		error:function(){
			alert('系统错误');

		}
	})
}

/**
 * 生成价格区间列表
 *
 * @param data
 */
function createSectionTable(content) {
	var myTemplate = Handlebars.compile($("#section-table-template").html());
	$('#sectionList').html(myTemplate(content));
}

/**
 * 终止小区间
 */
function del(id){
	$('#del').modal('show').on('show.bs.modal',function(){

	})
	$('#sure_del').on('click',function(){
		console.log(id);
		$.ajax({
			url:'/section/modify/'+id,
			type:'GET',
			success:function(data){
				console.log(data);
				alert('终止成功');
				window.location.reload();//刷新当前页面.
			},
			error:function(){

			}
		});
	});
}
/**
 * 修改单个小区间
 * @param id
 * @param priceRange
 */
function modify(id, priceRange,productionId) {
	console.log(id + '  ' + priceRange+'    '+productionId);
	$('#gaigai').modal('show').on('shown.bs.modal', function () {
		$("#priceRange").text(priceRange);
		$("#sure_save").click(function () {
			var valueData = $("#modifySection").serializeArray();
			console.log(valueData);
			var percentage = valueData[0]['value'];
			var implDate = valueData[1]['value'];
			var auditorId = valueData[2]['value'];
			var auditor =   $("#select-auditor").find("option:selected").text();
			var userId = $("#select-auditor").find("option:selected").val();
			console.log(percentage + " " + implDate + "  " + auditorId +'  '+id);
			$.ajax({
				url:'/section/modifyPriceRange/' + id,
				type: 'post',
				data: {
					percentage: percentage,
					implDate: implDate,
					userId: userId,
					productionId: productionId,
					auditor:auditor
				},
				success: function (data) {
					alert("修改成功,正在审核");
					refresh();
				},
				error: function () {

				}
			});

		});
	})
}

/**
 * 当弹框消失的时候刷新页面
 */
$('#gaigai').on('hidden.bs.modal', function () {
	refresh();
})
function refresh() {
	window.location.reload();//刷新当前页面.
}

/**
 * 价格区间设置
 */
function add_section(){
	var planId = $("#planId").val();
	var machineType = findMachineType();
	var checkId = $('#checkId').val();
	window.location.href = '/section/addPriceRanges?type='+machineType+'&planId='+planId + '&check=' + checkId;

}
/**
 * 查询记录
 */
function setSectionRecord(check){
	var planId = $("#planId").val();
	var machineType = findMachineType();
	// window.location.href = '/section/toNotExpiredJsp?type='+machineType+'&planId='+planId;
	window.location.href = '/priceRange/record?&planId=' + planId + '&check=' + check;
}
/**
 * 查看区域属性
 * @param id
 */
function seeRegion(id){
	window.location.href = "/areaAttr/show?type=PRICERANGE&ruleId="+id;
}


//------------------------ 价格区间操作结束 -------------------------------

/**
 * 增强 if-else使用
 * 比较长度
 */
Handlebars.registerHelper("ifNew", function(content, options) {
    var check = $('#checkId').val();
    if(check != 1){
        //满足添加继续执行
        return options.fn(this);
    }
    //不满足条件执行{{else}}部分
    return options.inverse(this);
});

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