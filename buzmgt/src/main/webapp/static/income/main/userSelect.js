var WsearchData = {
	"page" : 0,
	"size" : 10,
};
var itemTotal = 0;
function findPlanUserList(page) {
	page = page == null || page == '' ? 0 : page;
	WsearchData.page = page;
	$.ajax({
		url : "/mainPlanUsers/",
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
	Handlebars.registerHelper("getImg", function(num, index) {
		var star = 'ico-xx';
		var nostar = 'icon-hui';
		var tb = "";
		switch (num) {
		case 0:
			tb = nostar;
			break;
		case 1:
			if (index > 1) {
				tb = nostar;
			} else {
				tb = star;
			}
			break;
		case 2:
			if (index > 2) {
				tb = nostar;
			} else {
				tb = star;
			}
			break;
		case 3:
			tb = star;
			break;
		default:
			break;
		}
		return tb;
	});
	var levelColor = {
		"大学生" : "zi",
		"中学生" : "hong",
		"小学生" : "lan"
	};
	Handlebars.registerHelper("getColore", function(level) {
		return levelColor[level] == null ? "lan" : levelColor[level];
	});
	Handlebars.registerHelper("addOne", function(index) {
		// 返回+1之后的结果
		var number = index + 1;
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
			findPlanUserList(curr - 1);
		}
	});
}

// 查找数组下标
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i].salesmanId == val.salesmanId)
			return i;
	}
	return -1;
};
// 自定义删除数组
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
function openPlan(planId){
	initOtherPlan(planId);
	otherPlanFlag=true;
	$('#otherPlan').modal('show');
	$('#user').modal('hide');
}