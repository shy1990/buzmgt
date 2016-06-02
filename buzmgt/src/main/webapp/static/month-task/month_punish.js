var itemTotal = 0;// 补统计总条数
var page = 0;
var size = 10;
// 已自定义区域数组
var regionArr = new Array();
function findTaskList(page) {
	page = page == null || page == '' ? 0 : page;
	var searchData = {
		"page" : page,
		"size" : size,
	};

	$.ajax({
		url : "/monthTask/punish/data",
		type : "GET",
		data : searchData,
		dataType : "json",
		success : function(orderData) {
			createTaskTable(orderData);
			initRegionArr(orderData.content);
			var searchTotal = orderData.number;
			if (searchTotal != itemTotal || searchTotal == 0 || page == 0) {
				itemTotal = searchTotal;
				oilCostPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
/**
 * 初始化区域列表
 */
function initRegionArr(punishArr) {
	for (var i = 0; i < punishArr.length; i++) {
		regionArr.push(punishArr[i].regionId);
	}
}

function createTaskTable(data) {
	Handlebars.registerHelper("addOne", function(index) {
		// 返回+1之后的结果
		return index + 1;
	});
	// 大于
	Handlebars.registerHelper("gt", function(num1, num2) {
		if (num1 > num2) {
			return "是";
		} else {
			return "否";
		}
	});
	var myTemplate = Handlebars.compile($("#task-table-template").html());
	$('#acont').html(myTemplate(data));
}

/**
 * 报备的分页
 * 
 * @param data
 */
function oilCostPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#abnormalCoordPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findTaskList(curr - 1);
		}
	});
}

// 当弹出框消失的时候执行的方法
$('#zdyqy').on('hidden.bs.modal', function(e) {
	location.href = '/monthTask/punish';
})

/* 设置公里系数 */

// 添加
function addd(toil) {
	console.log($("#addd").serializeArray());
	addCustom($("#addd").serializeArray());
}

function addCustom(o) {
	var qy = o[0]["value"];// regionId
	qy = qy.replace(" ", "");
	var glxs = o[1]["value"];//
	var km_ratio = o[2]["value"];
	if (km_ratio == "") {
		alert("请设置扣罚系数");
		return;
	} else if (qy == "") {
		alert("请设置扣罚区域");
		return;
	}
	if (jQuery.inArray(qy, regionArr) > -1) {
		alert("请勿重复添加自定义区域!");
	} else if (qy.length > 5 && qy.length < 8) {
		var num = (qy + "").substring(4);
		if (num != '00') {
			oilForm(km_ratio, qy);// 执行添加
		} else {
			alert("请选择县级区域!!");
		}

	} else {
		alert("请选择县级区域!!");
	}

	location.href = '/monthTask/punish';

}
/*
 * 添加自定义扣罚系数区域
 */
function oilForm(km_ratio, qy) {
	var newPunish = {
		"regionId" : qy,
		"rate" : km_ratio
	}
	// 发送请求
	$.ajax({
		url : 'monthTask/punish',// 添加公里系数设置区域
		type : 'post',
		data : newPunish,
		success : function(result) {
			alert("已成功添加");
			location.href = '/monthTask/punish';
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});

}
/*
 * 修改自定义扣罚系数区域
 */
function modify(id, rate) {
	$('#changed').modal('show').on('shown.bs.modal', function() {
		$("#set_a").click(function() {
			var ratio = $("#select_modify").val();
			patch(id, rate, ratio)
		});
	});

}
/**
 * 更新设置
 */
function patch(id, rate, ratio) {
	$.ajax({
		url : '/api/monthTaskPunishes/' + id,
		type : 'patch',
		data : JSON.stringify({
			"rate" : ratio
		}),
		dataType : "json",
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		success : function(data) {
			alert("修改已完成");
			location.href = '/monthTask/punish';
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});
}
/*
 * 保存默认区域默认设置
 */
function saveDefault() {
	var newRate = $("#defaultRate").val();
	patch(0, defaultRate, newRate);

}
/*
 * 删除自定义区域扣罚系数
 */
function deletePunish(id) {
	$.ajax({
		url : '/api/monthTaskPunishes/' + id,
		type : 'delete',
		dataType : "json",
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		success : function(data) {
			alert("删除成功");
			location.href = '/monthTask/punish';
		}
	});

}
