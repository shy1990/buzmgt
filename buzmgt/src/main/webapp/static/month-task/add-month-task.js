var lsdata = null;
var tagids = [ 'sy', 'ly', 'bf', 'goal', 'sysgive' ];
// 级别
var jbs = [ 20, 15, 10, 7, 4 ];
function getMonthData(regionId) {
	var month = new Date().DateAdd('m', 1).format("yyyy-MM");
	var salemanid = $("#basic").val();
	if (null == salemanid || salemanid == '') {
		salemanid = '0';
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
					var val = getObjectVal(tagid, jb)
					if (tagid == 'goal') {
						$("#" + jb + tagid).attr("value", val);
					} else {
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
var oldSalemanid = "";
function submit(flag) {
	var message = "保存";
	if (flag == 1) {
		message = "发布";
	}
	// lsdata.regionId,lsdata.month,//$("#basic").val(),
	var task = {
		"town" : lsdata.regionId,
		"month" : lsdata.month,
		"agentid" : $("#basic").val(),
		"tal7goal" : 0,
		"tal15goal" : 0,
		"tal10goal" : 0,
		"tal20goal" : 0,
		"tal4goal" : 0,
		"status" : flag,
	};
	$.ajax({
		url : base + "api/monthTasks",
		// "/api/monthTasks",
		type : "post",
		data : JSON.stringify(task),
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		dataType : "json",
		success : function(data) {
			alert("批量任务已成功" + message);
		},
		error : function() {
			alert("系统错误，请稍后再试");
		}
	});
}
