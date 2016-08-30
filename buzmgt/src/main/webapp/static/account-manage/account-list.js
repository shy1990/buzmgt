$(function() {
	findAccounts();
});

var totalElements = 0;
function initSwitch() {
	$("[name='my-checkbox']").bootstrapSwitch();
	// checkbox点击事件回调函数
	$('input[name="my-checkbox"]').on('switchChange.bootstrapSwitch',
			function(event, state) {
//				console.log(this); // DOM element
//				console.log(event); // jQuery event
//				console.log(state); // true | false
				if (state) {
//					alert('冻结');
				} else {
//					alert('正常');
				}
			});
}
function findAccounts(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : "/getAccountManage",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != totalElements || searchTotal == 0) {
				totalElements = searchTotal;
				initPaging(orderData);
			}
			initSwitch();
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
function findDismissAccounts(page) {
	page = page == null || page == '' ? 0 : page;
	$.get("/getAccountManage?orgName=allDis&status=2&page="+page, function(orderData,
			status) {
		if (status === "success") {
			createDismissTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != totalElements || searchTotal == 0) {
				totalElements = searchTotal;
				initDismissPaging(orderData);
			}
			initSwitch();
		} else {
			alert("系统异常，稍后重试！");
		}

	}, "json");
}
/**
 * 生成油补统计列表
 * 
 * @param data
 */

function createTable(data) {
	var myTemplate = Handlebars.compile($("#table-template").html());
	$('#tableList').html(myTemplate(data));
}
/**
 * 
 * @param data
 */
function createDismissTable(data) {
	var myTemplate = Handlebars.compile($("#dismiss-table-template").html());
	$('#dismissTableList').html(myTemplate(data));
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
			findAccounts(curr - 1);
		}
	});
}
function initDismissPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#initDismissPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findDismissAccounts(curr - 1);
		}
	});
}
Handlebars.registerHelper('switch', function(status, accountNum) {
	var html = "";
	switch (status) {
	case "2":
		html += '<input type="checkbox" checked="checked"'
				+ '	autocomplete="off" name="my-checkbox" id="accountStatus"'
				+ '	value="' + accountNum + '" data-on-color="info"'
				+ '	data-off-color="success" data-size="mini"'
				+ '	data-on-text="冻结" data-off-text="正常" readonly />';

		break;
	case "0":
		html += '<input type="checkbox" checked="checked"'
				+ '	autocomplete="off" name="my-checkbox" id="accountStatus"'
				+ '	value="' + accountNum + '" data-on-color="info"'
				+ '	data-on-color="info" data-off-color="success"'
				+ '	data-size="mini" data-off-text="冻结" data-on-text="正常"'
				+ '   onchange="mofidyAccount(\'' + accountNum
				+ '\',\'0\')" />';

		break;

	default:
		html += '<input type="checkbox" autocomplete="off"'
				+ '	name="my-checkbox" id="accountStatus"'
				+ '	value="'+status+'" data-on-color="info"'
				+ '	data-off-color="success" data-size="mini"'
				+ '	data-off-text="冻结" data-on-text="正常"'
				+ '	onchange="mofidyAccount(\'' + accountNum + '\',\'1\')" />';

		break;
	}
	return html;
});
// 注册索引+1的helper
Handlebars.registerHelper("addOne", function(index) {
	// 返回+1之后的结果
	return index + 1;
});
// 修改资料
function modifyAccount(accountNum, position) {
	var url = "modifyAccount?accountNum=" + accountNum + "&position="
			+ position;
	window.location = encodeURI(url);
}

// 根据职务查询
function selectByOrg(orgName, status) {
	if (status != '2') {
		SearchData['orgName'] = orgName;
		SearchData['status'] = status;
		findAccounts();
		delete SearchData['orgName'];
		delete SearchData['status'];
		return;
	}
	findDismissAccounts();

}
// 重置密码
function resetPwd(accountNum) {
	if (confirm("确定要重置密码？	初始密码123456")) {
		var url = "resetPwd?id=" + accountNum;
		$.post(url, function(data) {
			if (data === 'suc') {
				alert("重置成功!");
				setTimeout(function() {
					location.reload()
				}, 3000);
			} else {
				alert("系统异常,请重试");
			}
		});
	}
}
// 修改账号状态,0正常,1冻结,2辞退,3删除,4清空sim卡
function mofidyAccount(accountNum, status) {
	switch (status) {
	case '0':
		if (confirm("确定要冻结改账号?")) {
			var url = "mofidyAccountStatus?id=" + accountNum + "&status="
					+ status;
			$.post(url, function(data) {
				if (data === 'suc') {
					alert("已冻结!");
					location.reload();
				} else {
					alert("系统异常,请重试");
				}
			});
		}

		break;
	case '1':
		if (confirm("确定要解封/恢复该账号?")) {
			var url = "mofidyAccountStatus?id=" + accountNum + "&status="
					+ status;
			$.post(url, function(data) {
				if (data === 'suc') {
					alert("已解封/恢复!");
					location.reload();
				} else {
					alert("系统异常,请重试");
				}
			});
		}
		break;
	case '2':
		if (confirm("确定要辞退该员工")) {
			var url = "mofidyAccountStatus?id=" + accountNum + "&status="
					+ status;
			$.post(url, function(data) {
				if (data === 'suc') {
					alert("已辞退!");
					location.reload();
				} else {
					alert("系统异常,请重试");
				}
			});
		}

		break;
	case '3':
		if (confirm("确定要删除该员工?删除后无法恢复")) {
			var url = "mofidyAccountStatus?id=" + accountNum + "&status="
					+ status;
			$.post(url, function(data) {
				if (data === 'suc') {
					alert("已删除!");
					location.reload();
				} else {
					alert("系统异常,请重试");
				}
			});
		}

		break;

	default:
		if (confirm("确定要清空该员工sim卡?清空后无法恢复")) {
			var url = "mofidyAccountStatus?id=" + accountNum + "&status="
					+ status;
			$.post(url, function(data) {
				if (data === 'suc') {
					alert("已清空!");
					location.reload();
				} else {
					alert("系统异常,请重试");
				}
			});
		}
		break;
	}
}

/* 区域 */
function getRegion(id) {
	window.location.href = '/region/getPersonalRegion?flag=' + "account";
}

$("[name='my-checkbox']").bootstrapSwitch();
// checkbox点击事件回调函数
$('input[name="my-checkbox"]').on('switchChange.bootstrapSwitch',
		function(event, state) {
			// console.log(this); // DOM element
			// console.log(event); // jQuery event
			// console.log(state); // true | false
		});

// 弹出子账号model
function addAccount(id) {
	$("#userId").val(id);
	$('#addAccount').modal({
		keyboard : false
	})

}

// 添加子账号
function addChildAccount() {
	var truename = $("#truename").val();
	var userId = $("#userId").val();
	var url = "/addChildAccount?truename=" + truename + "&userId=" + userId;
	$.post(url, function(data) {
		if (data === 'suc') {
			location.reload();
		} else if (data === 'err') {
			alert("超过最大个数");
		} else {
			alert("系统异常,请重试");
		}
	});

}

// 子账号列表展示
function findChildAccount(userId) {
	window.location.href = "/findChildAccount?userId=" + userId;
}
// 根据名称查询
function getAccountList() {
	SearchData["searchParam"] = $("#param").val();
	findAccounts();
	delete SearchData["searchParam"];
	// window.location.href = "/accountManage?searchParam=" + $("#param").val();

}
