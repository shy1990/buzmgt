var lsdata = null;
var tagids = [ 'sy', 'ly', 'bf', 'goal', 'sysgive' ];
// 级别
var jbs = [ 20, 15, 10, 7, 4 ];
var oldSalemanid = "";
var task = null;
var newed = 0;
var salemanid = null;
var date = new Date();
// forbi();
function forbi() {
	var days = date.getDate();
	if (days < 16 && taskId == '') {
		alert("本月最终数据还未统计,请16号开始设置");
		window.location.href = "/monthTask/list";
	}
}
function getNextMonth() {
	var month = date.getMonth();
	var year = date.getFullYear();
	if (month < 9) {
		month = "0" + (month + 2);
	} else {
		month = (month + 2);
	}
	month = year + "-" + month;
	return month;
}
function getMonthData(regionId) {
	var month = getNextMonth();
	var salemanids = $("#basic").val();
	if (null == salemanids || salemanids == [] || salemanids == "") {
		alert("请选择业务员");
		return;
	}
	salemanid = salemanids[0];
	var searchData = {
		"month" : month,
		"salemanid" : salemanid,
		"regionid" : ""
	};
	$.ajax({
		url : base + "api/monthdata/search/defaultfinddata",
		type : "GET",
		data : searchData,
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		dataType : "json",
		success : function(data) {
			lsdata = data;
			for (var i = 0; i < 5; i++) {
				for (var j = 0; j < 5; j++) {
					var jb = jbs[i];
					var tagid = tagids[j];
					var val = getObjectVal(tagid, jb);
					if (tagid == 'goal') {
						//$("#" + jb + tagid).attr("value", val);
						//值无变化,很坑
						$("#" + jb + tagid).val(val);
					} else {
						$("#" + jb + tagid).html(val);
					}
				}
			}
			newed = 1;
			$("#taskContent").css("display", "");
		},
		error : function(data) {
			if (data.status = '404') {
				alert(month + "月未生成相应的统计数据,请16日之后再试");
			} else {
				alert("系统错误，请稍后再试");
			}
		}
	});
}
// 通过任务id得到任务设置
function getMonthDataById() {
	$.ajax({
		url : base + "monthTask/mainTasks/" + taskId,
		type : "GET",
		data : {},
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type",
					"application/json; charset=UTF-8");
		},
		dataType : "json",
		success : function(data) {
			lsdata = data.data;
			task = data.task;
			for (var i = 0; i < 5; i++) {
				for (var j = 0; j < 5; j++) {
					var jb = jbs[i];
					var tagid = tagids[j];
					if (tagid == 'goal') {
						var val = task["tal" + jb + "goal"];
						$("#" + jb + tagid).attr("value", val);
					} else {
						var val = getObjectVal(tagid, jb)
						$("#" + jb + tagid).html(val);
					}
				}
			}
			$("#taskContent").css("display", "");
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
}
/*
 * 根据tagid和jb从lsdata记录中取得数据 @Param tagid 标签id @Param jb 级别 1,2,3
 */
function getObjectVal(tagid, jb) {
	var val = 0;
	switch (tagid) {
	case 'sy':
		val = lsdata["tal" + jb + "m3"];
		break;
	case 'ly':
		val = lsdata["tal" + jb + "m1"];
		break;
	case 'bf':
		val = lsdata["visitCount" + jb];
		break;
	default:
		val = lsdata["sysgive" + jb];
		break;
	}
	return val;
}

function submit(flag) {
	var message = "保存";
	if (flag == 1) {
		message = "发布";
	}
	// lsdata.regionId,lsdata.month,//$("#basic").val(),
	var taskObj = {
		"regionid" : lsdata.regionId,
		"month" : lsdata.month,
		"tal7goal" : $("#7goal").val(),
		"tal15goal" : $("#15goal").val(),
		"tal10goal" : $("#10goal").val(),
		"tal20goal" : $("#20goal").val(),
		"tal4goal" : $("#4goal").val(),
		"status" : flag,
	};
	var type = "post";
	var url = base + "api/monthTasks";
	if (task != null) {
		type = "PATCH";
		url += "/" + task.id;
		if (flag == 1) {
			message = "修改并发布";
		} else {
			message = "修改并保存";
		}
	} else {
		taskObj.agentid = salemanid;
		if (newed == 0) {
			// 本次查询数据已被使用
			alert("数据未刷新,请重新检索!!");
			return;
		}
	}
	$.ajax({
		url : url,
		type : type,
		data : JSON.stringify(taskObj),
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		dataType : "json",
		success : function(data) {
			alert("批量任务已成功" + message);
			if (task != null) {
				location.href = '/monthTask/handleList';
			} else {
				cleanSelect();
				$("#taskContent").css("display", "none");
				newed = 0;
			}
		},
		error : function(data) {
			alert("系统错误，请稍后再试");
		}
	});
}
// 清除已选的业务员
function cleanSelect() {
	if (salemanid != null) {
		$("#basic option[value='" + salemanid + "']").remove();
		salemanid = null;
	}
}
