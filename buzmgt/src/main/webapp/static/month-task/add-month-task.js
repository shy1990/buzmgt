var lsdata = null;
var tagids = [ 'sy', 'ly', 'bf', 'goal', 'sysgive' ];
// 级别
var jbs = [ 20, 15, 10, 7, 4 ];
var oldSalemanid = "";
var task = null;
var newed = 0;
var salemanid = null;
function getMonthData(regionId) {
	var month = new Date().DateAdd('m', 1).format("yyyy-MM");
	salemanid = $("#basic").val();
	if (null == salemanid || salemanid == '') {
		alert("请选择业务员");
		reurn;
	}

	var searchData = {
		"month" : month,
		"salemanid" : salemanid,
		"regionid" : regionId
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
						$("#" + jb + tagid).attr("value", val);
					} else {
						$("#" + jb + tagid).html(val);
					}
				}
			}
			newed = 1;
		},
		error : function() {
			alert("系统错误，请稍后再试");
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
		val = lsdata["tal" + jb + "M3"];
		break;
	case 'ly':
		val = lsdata["tal" + jb + "M1"];
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
		"agentid" : $("#basic").val(),
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
		if (newed == 0) {
			alert("本次查询数据已被使用,请重新查询;");
			return;
		}
		taskObj.monthData = lsdata;
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
			cleanSelect();
			newed = 0;
		},
		error : function() {
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
