var oilCostTotal = 0;//油补统计总条数 
var abnormalCoordTotal = 0;//油补统计总条数 
$(function() {
	nowTime();//初始化日期
	DispositRegionId();//处理区域ID
	findOilCostList();//油补统计
	abnormalCoordList();//异常坐标
})
$('#startTime').datetimepicker({
	format : "yyyy-mm-dd",
	language : 'zh-CN',
	endDate : new Date(),
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	minView : 2,
	pickerPosition : "bottom-right",
	forceParse : 0
}).on('changeDate', function(ev) {
	$('.form_date_start').removeClass('has-error');
	$('.form_date_end').removeClass('has-error');
	var endInputDateStr = $('#endTime').val();
	if (endInputDateStr != "" && endInputDateStr != null) {
		var endInputDate = stringToDate(endInputDateStr).valueOf();
		if (ev.date.valueOf() - endInputDate > 0) {
			$('.form_date_start').addClass('has-error');
		}
	}
});
$('#endTime').datetimepicker({
	format : "yyyy-mm-dd",
	language : 'zh-CN',
	endDate : new Date(),
	weekStart : 1,
	todayBtn : 1,
	autoclose : 1,
	todayHighlight : 1,
	startView : 2,
	minView : 2,
	pickerPosition : "bottom-right",
	forceParse : 0
}).on('changeDate', function(ev) {
	$('.form_date_start').removeClass('has-error');
	$('.form_date_end').removeClass('has-error');
	var startInputDateStr = $('#startTime').val();
	if (startInputDateStr != "" && startInputDateStr != null) {
		var startInputDate = stringToDate(startInputDateStr).valueOf();
		if (ev.date.valueOf() - startInputDate < 0) {
			$('.form_date_end').addClass('has-error');
		}
	}
});

/**
 * 初始化日期 前1天
 */
function nowTime() {
	var newDate=(new Date()).DateAdd('d', -1);
	var nowDate = changeDateToString(newDate);
	var beforeDate = changeDateToString(newDate);
	SearchData['sc_GTE_dateTime'] = beforeDate;
	SearchData['sc_LTE_dateTime'] = nowDate;
	$('#startTime').val(beforeDate);
	$('#endTime').val(nowDate)
}

/**
 * TODO 处理区域ID 根据Regiontype 
 */
function DispositRegionId(){
	var regionId=$('#regionId').val();
	var regionType=$('#regionType').val();
	SearchData['sc_regionId'] = regionId;
	SearchData['sc_regionType'] = regionType;
}

/**
 * 检索
 */
function goSearch() {
	var tab = findTab();
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	if (checkDate(startTime, endTime)) {
		SearchData['sc_GTE_dateTime'] = startTime;
		SearchData['sc_LTE_dateTime'] = endTime;
		switch (tab) {
		case 'all':
			findOilCostList();
			break;
		case 'coords':
			abnormalCoordList();
			break;
		default:
			break;
		}
	}
}
/**
 * 导出
 */
$('.table-export').on('click',function() {
			var tab = findTab();
			var startTime = $('#startTime').val();
			var endTime = $('#endTime').val();
			if (checkDate(startTime, endTime)) {
				
				SearchData['sc_GTE_dateTime'] = startTime;
				SearchData['sc_LTE_dateTime'] = endTime;
				switch (tab) {
				case "all":
					
					window.location.href = base + "oilCost/export/statistics?" + conditionProcess();
					break;

				default:
					
					window.location.href = base + "oilCost/export/abnormalCoord?" + conditionProcess();
					break;
				}
			}

		});
function findTab(){
	var tab = $('#oilCostStatus li.active').attr('data-tital');
	return tab;
}
/**
 * 处理检索条件
 * 
 * @returns {String}
 */
function conditionProcess() {
	var SearchData_ = "sc_GTE_dateTime="
			+ (SearchData.sc_GTE_dateTime == null ? ''
					: SearchData.sc_GTE_dateTime)
			+ "&sc_LTE_dateTime="
			+ (SearchData.sc_LTE_dateTime == null ? ''
					: SearchData.sc_LTE_dateTime);

	return SearchData_;
}
function findOilCostList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : "/oilCost/statistics",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createOilCostTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != oilCostTotal || searchTotal == 0) {
				oilCostTotal = searchTotal;

				oilCostPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
function abnormalCoordList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : "/oilCost/abnormalCoord",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createAbnormalCoordTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != abnormalCoordTotal || searchTotal == 0) {
				abnormalCoordTotal = searchTotal;
				abnormalCoordPaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
/**
 * 生成油补统计列表
 * @param data
 */

function createOilCostTable(data) {
	var myTemplate = Handlebars.compile($("#oilCost-table-template").html());
	$('#oilCostList').html(myTemplate(data));
}
/**
 * 生成异常坐标列表
 * @param data
 */
function createAbnormalCoordTable(data) {
	var myTemplate = Handlebars.compile($("#abnormalCoord-table-template").html());
	$('#abnormalCoordList').html(myTemplate(data));
}
/**
 * 报备的分页
 * @param data
 */
function oilCostPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#oilCostPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findOilCostList(curr - 1);
		}
	});
}
/**
 * 未报备的分页
 * @param data
 */
function abnormalCoordPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#abnormalCoordPager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			abnormalCoordList(curr - 1);
		}
	});
}
Handlebars.registerHelper('formDate', function(value) {
	if(value==null||value==""){
		return "----";
	}
	return changeTimeToString(new Date(value));
});
/**
 * 处理油补握手点
 * v1:type 
 * v2:regionName
 * 
 */
Handlebars.registerHelper('disposeRecordList', function(regionType,regionName,exception) {
	var html = "";
//	数据格式
/*	<span>起点<span class="abnormal-state">异常</span></span>
    <span class="location">大桥镇</span>
    <span class="location">小桥镇</span>
    <span class="location">小桥镇</span>
    <span class="location">小桥镇</span>
    <span class="location">小桥镇</span>
    <span class="location">小桥镇</span>
    <span class="location">终点<span class="normal-state">家</span></span>*/
	console.info(regionType+","+regionName+","+exception);
	//异常
	var tag="";
	if (!isEmpty(exception)) {
		tag = '<span class="abnormal-state">异常</span>';
	}else if (regionName.indexOf("家") >= 0) {
		tag = '<span class="normal-state">'+regionName+'</span>';
	}
	if (regionType==="0") {
		regionName = '<span>起点'+tag+'</span>';
	}else if (regionType==="3") {
		regionName = '<span class="location">终点'+tag+'</span>';
	}else {
		regionName = '<span class="location">'+regionName+tag+'</span>';
	}
	html += regionName;
	return html;
});
/**
 * parentId userId
 */
Handlebars.registerHelper('whatUserId', function(parentId, userId) {
	if (parentId !=null||parentId !="") {
		return parentId;
	}
	return userId;
});
/**
 * 选择区域
 * @param id
 */
function getRegion(id){
	window.location.href='/region/getPersonalRegion?id='+id+'&flag=oilCost';
}
/**
 * 根据订单号查询
 */
function findByOrderNo(){
	var tab=findTab();
	var orderNo=$('#orderNo').val();
	switch (tab) {
	case 'reported':
		$.ajax({
			url : "/receiptRemark/remarkList?sc_EQ_orderno="+orderNo,
			type : "GET",
			dataType : "json",
			success : function(orderData) {
				if(orderData.totalElements<1){
					alert("报备订单中，未查到地订单！");
					return false;
				}
				createRemarkedTable(orderData);
			},
			error : function() {
				alert("系统异常，请稍后重试！");
			}
		})
		break;
	case 'notreported':
		$.ajax({
			url : "/receiptRemark/notRemarkList?sc_EQ_orderNo="+orderNo,
			type : "GET",
			dataType : "json",
			success : function(orderData) {
				if(orderData.totalElements<1){
					alert("未报备订单中，未查到此订单！");
					return false;
				}
				createNotRemarkedTable(orderData);
			},
			error : function() {
				alert("系统异常，请稍后重试！");
			}
		})
		break;
	default:
		break;
	}
}
/**
 * 判读是否为空
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function isEmpty(value){
	return value ==""||value==null;
} 

function turnRecord(userId,oilTotalCost,totalDistance){
	var startTime = $('#startTime').val();
	var endTime = $('#endTime').val();
	window.location.href = base+"oilCost/record/"+userId+"?startTime=" +startTime+
			"&endTime="+endTime+"&oilTotalCost="+oilTotalCost+"&totalDistance="+totalDistance;
	
}
