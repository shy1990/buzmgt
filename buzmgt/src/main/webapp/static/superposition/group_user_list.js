var achieveTotal = 0;
var groupList ;//存储组成员数据
$(function() {
	initHandlerbars();
	findGroupUserList();// 查询列表
})
/**
 * 跳转添加页面 获取planId和machineType
 */
function add(){
	var planId = $("#planId").val();
	var machineType = $(".J_MachineType li.active").attr('title');
	window.location.href = base + "achieve/add?planId="+planId+"&machineType="+machineType  
}
function initHandlerbars(){
	// 注册索引+1的helper
	var handleHelper = Handlebars.registerHelper("addOne", function(index) {
		// 返回+1之后的结果
		return index + 1;
	});
	// 注册索引+1的helper
	var handleHelper = Handlebars.registerHelper("forStars", function(value) {
		var html = '';
		for(var i=1;i<=3;i++){
			if(i > Number(value)){
				html +='<i class=" icon-x icon-hui"></i>';
				continue;
			}
			html +='<i class=" icon-x ico-xx"></i>';
		}
		return html;
	});
	Handlebars.registerHelper('formDate', function(value) {
		if (value == null || value == "") {
			return "----";
		}
		return changeDateToString_(new Date(value));
	});
	/**
	 * 待付金额
	 */
	Handlebars.registerHelper('isHasGroup', function(value) {
		var html = '';
		if (value == '' || value == null || value == undefined) {
			return html;
		}
		return html += '<button class="btn bnt-sm bnt-ck J_addDire">添加</button>';
	});
	/**
	 * 模拟分组
	 */
	Handlebars.registerHelper('compare', function(userId) {
		var html = '';
		var resouse = userIterate(userId);
		html += resouse ?  resouse : "";
		return html;
	});

	/**
	 * 模拟分组
	 */
	Handlebars.registerHelper('showGroup', function(groupName,userName,userId) {
		var html = '';
		var $groupName = $("#groupName").val(); 
		if (groupName == $groupName ) {
			return html += '<li><i class=" icon-nam"></i>' + userName + '<i class="icon-delee" onClick="groupDelete(\''+userId+'\')"></i></li>';
		}
		return html;
	});
	
}
/**
 * 处理GroupNumbers
 */

function disposeGroupNumbers() {
	var arr = [ 'A', 'B', 'C', 'D' ];
	for (var s = 0; s < arr.length; s++) {
		groupNumbers[s] = {
			"groupName" : arr[s],
			"numberFirstAdd" : null,
			"numberSecondAdd" : null,
			"numberThirdAdd" : null,
			"type" : "ACHIEVE",
			"flag" : "NORMAL",
			"groupUsers" : disposeGroupUsers(arr[s]),
		}
	}
	return groupNumbers;
}
/**
 * 处理GroupUsers
 * @param groupName
 * @returns {Array}
 */
function disposeGroupUsers(groupName) {

	var groupUsers = new Array();
	for (var j = 0; j < userList.length; j++) {
		if (groupName == userList[j]["groupName"]) {

			groupUsers[groupUsers.length] = {
				"userId" : userList[j]["userId"],
				"userName" : userList[j]["userName"]
			}
		}
	}
	return groupUsers;
}
function groupDelete(userId){
	//查询人员所在行
	$("td.J_groupuserId").each(function(index,element){
		var oo = $(this).attr('data-userId');
		if(userId == oo){
			$(this).parents("tr").find('span.text-green-s').text("");
		}
	});
	//所在列表的下标
	var index = groupIterate(userId)
	//删除
	groupList.splice(index,1);
	createGroupSonByNameTable(groupList);
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
function initGroupFunction(){
	$(".J_addDire").on("click",function(){
		var userId = $(this).parents("tr").find("td.J_groupuserId").attr("data-userId");
		var userName = $(this).parents("tr").find("td.J_groupuserId").text();
		var groupName = $('#groupName').val();
		if(!groupIterate(userId)){
			groupList[groupList.length] = 
			{
				"userId" : userId, 
				"userName" : userName,
				"groupName" : groupName
			};
			//创建子窗口中人员组
			createGroupSonByNameTable(groupList);
			//修改子窗口人员分组status
			$(this).parents("tr").find("span.text-green-s").text(groupName);
		}
	});
}
function addUserList(){
	// groupList赋值数据userList
	userList = new Array();
	userList = userList.concat(groupList);
	//创建父窗口中的人员分组
	creategroupAll(userList);
	//更改父窗口人员分组结果
	createNumberAdd();
}
/**
 * 迭代userId是否存在userList中 
 * 不存在返回false，存在返回所在下标
 */
function userIterate(userId){
	
	for(var i = 0; i<userList.length; i++){
		if(userList[i]["userId"] == userId){
			return ""+userList[i]["groupName"];
		}
	}
	return false;
}
/**
 * 迭代userId是否存在groupList中 
 * 不存在返回false，存在返回所在下标
 */
function groupIterate(userId){
	
	for(var i = 0; i<groupList.length; i++){
		if(groupList[i]["userId"] == userId){
			return ""+i;
		}
	}
	return false;
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
	SearchData['sc_EQ_status'] = "OVER";
	SearchData['sc_EQ_machineType.id'] = $machineType;

}

function findGroupUserList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	var $planId = $("#planId").val();
	$.ajax({
		url : "/superposition/planUser?planId=" + $planId,
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createGroupUserTable(orderData);
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

/**
 * 生成油补统计列表
 * 
 * @param data
 */

function createGroupUserTable(data) {
	var myTemplate = Handlebars.compile($("#groupuser-table-template").html());
	$('#groupUserList').html(myTemplate(data));
	initGroupFunction();
}
function createGroupSonByNameTable(data) {
	var myTemplate = Handlebars.compile($("#groupbyname-table-template").html());
	$('#nameli').html(myTemplate(data));
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
			findGroupUserList(curr - 1);
		}
	});
}
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
