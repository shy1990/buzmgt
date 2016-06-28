var itemTotal = 0;// 补统计总条数
var page = 0;
var size = 10;
//判断刷新请求用的
var newId = "";
//appserver接口地址
var appUrl="";
function getMessage(newPage) {
	$.ajax({
		url : '/customTask/messages/' + taskId,
		type : 'get',
		dataType : "json",
		data : {
			"page" : newPage,
			"size" : size
		},
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		success : function(orderData) {
			createTaskTable(orderData);
			page = newPage;
			$("#recieve").html(orderData.recieve);
			newId=orderData.newId;
			appUrl=orderData.appUrl;
			var searchTotal = orderData.number;

			if (searchTotal != itemTotal || searchTotal == 0 || page == 0) {
				itemTotal = searchTotal;
				oilCostPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试或联系管理员！");
		}
	});
}
// 获取html内容
function createTaskTable(data) {
	Handlebars.registerHelper("me", function(status) {
		if (status == 0) {
			return "left";
		} else {
			return "right";
		}
	});
	Handlebars.registerHelper("dohref", function(salesId) {
		return "#" + salesId;
	});
	Handlebars.registerHelper("doCollapse", function(size) {
		if (size < 1) {
			return "panel-collapse collapse";
		} else {
			return "right";
		}
	});
	// 此为判断函数
	Handlebars.registerHelper("compare", function(v1, options) {
		if (v1 == 1) {
			// 满足添加继续执行
			return options.fn(this);
		} else {
			// 不满足条件执行{{else}}部分
			return options.inverse(this);
		}
	});
	var myTemplate = Handlebars.compile($("#task-table-template").html());
	$('#taskList').html(myTemplate(data));
}

/**
 * 报备的分页
 * 
 * @Param data
 */
function oilCostPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#abnormalCoordPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			getMessage(curr - 1);
		}
	});
}
// 发送单条消息
function sendMessage(salesId) {
	var content = $("#message" + salesId).val();
	if (content.length < 1) {
		alert("请输入回复内容");
		return;
	}
	postMessage(content, [ salesId ]);
}
// 快速回复
function fastresp() {
	var content = $("#fastConent").val();
	if (content.length < 1) {
		alert("请输入回复内容");
		return;
	}
	var type = $("#fastSales").val();
	var saleIds = null;
	if (type == 0) {
		saleIds = reset.concat(unset);
	} else if (type == 1) {
		saleIds = unset;
	} else if (type == 2) {
		saleIds = reset;
	}
	if(saleIds.length<1){
		alert("该选项无业务员,请选择其他选项");
		return;
	}
	postMessage(content, saleIds);
}
// 具体执行,发送到后台
function postMessage(content, salesmanIds) {
	var messages = {
		"customtaskId" : taskId,
		"salesmanId" : salesmanIds,
		"content" : content
	};
	$.ajax({
		url : '/customTask/messages',
		type : 'post',
		dataType : "json",
		data : JSON.stringify(messages),
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		success : function(data) {
			if (data.code = 0) {
				alert("程序发生问题,请与管理员联系");
				return;
			}
			alert("已成功回复");
			getMessage(page);
		},
		error : function() {
			alert("系统异常，请稍后重试或联系管理员！");
		}
	});
}
 

function testUpdate() {
	$.ajax({
	url : '/customTask/checkUpdate/' + taskId,
		type : 'get',
		dataType : "json",
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		success : function(id) {
			if (newId != "") {
				if (id >newId) {
					newId = id;
					getMessage(page);
				}
			} else {
				newId = id;
			}
		},
		error : function() {
			alert("系统异常，请稍后重试或联系管理员！");
		}
	});
}