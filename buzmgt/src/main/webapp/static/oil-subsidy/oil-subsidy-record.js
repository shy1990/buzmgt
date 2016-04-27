var oilCostRecordTotal = 0;//油补统计总条数 
$(function() {
//	nowTime();//初始化日期
	findOilCostRecord();//油补记录
})

/**
 * 检索
 */
function goSearch() {
	var month = $('#monthDate').val();
	//初始化月份string
	var arr=month.split("-");
	var monthSet=Number(arr[1]);
	var monthTime =arr[0]+"-"+monthSet+"-0";
	if (!checkEmpty(month)) {
		//new Date(2016,02,0) 实际-->2016年03月
		var day=new Date(arr[0],monthSet,0).getDate();
		SearchData['sc_GTE_dateTime'] = month+"-01";
		SearchData['sc_LTE_dateTime'] = month+"-"+day;
		findOilCostRecord();
		$('#searchDate').text('');
	}
}
/**
 * 导出
 */
$('.table-export').on(
		'click',
		function() {
			var tab = findTab();
			var startTime = $('#startTime').val();
			var endTime = $('#endTime').val();
			if (checkDate(startTime, endTime)) {
				SearchData['sc_GTE_dateTime'] = startTime;
				SearchData['sc_LTE_dateTime'] = endTime;
//				window.location.href = base + "receiptRemark/export?type="
//						+ tab + "&"+ conditionProcess();
			}

		});
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
function findOilCostRecord(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	var recordUserID=$('#recordUserID').val();
	
	$.ajax({
		url : base+"oilCost/statistics/"+recordUserID,
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(oilCostData) {
			createOilCostRecordList(oilCostData);
			var searchTotal = oilCostData.totalElements;
			if (searchTotal != oilCostRecordTotal || searchTotal == 0) {
				oilCostRecordTotal = searchTotal;
				totalRecord();
				oilCostRecordPaging(oilCostData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
function totalRecord(){
	var distance = $('.J_oil-km');
	var oilCost = $('.J_oil-mny');
	var totalDistance = 0;
	var totalOilCost = 0;
	$.each(distance,function(i,item){
		totalDistance+=Number($(item).text());
	});
	$.each(oilCost,function(i,item){
		totalOilCost+=Number($(item).text());
	});
	$('.J_oil-km-all').text(totalDistance);
	$('.J_oil-mny-all').text(totalOilCost);
	
}
/**
 * 生成油补统计列表
 * @param data
 */

function createOilCostRecordList(data) {
	var myTemplate = Handlebars.compile($("#oilCostRecord-table-template").html());
	$('#oilCostRecordList').html(myTemplate(data));
}
/**
 * 报备的分页
 * @param data
 */
function oilCostRecordPaging(data) {
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
Handlebars.registerHelper('formDate', function(value) {
	if(value==null||value==""){
		return "----";
	}
	return changeDateToString(new Date(value));
});
/**
 * 处理油补握手点
 * v1:type 
 * v2:regionName
 * 
 */
Handlebars.registerHelper('disposeRecordList', function(type,regionName) {
	var html = "";
//	
//    <span class="location">{{regionName}}</span>
//    <span class="location">终点<span class="normal-state">家</span></span>
	if (regionName.indexOf("异常") >= 0) {
		regionName = '<span class="abnormal-state">'+regionName+'</span>';
	}
	if (regionName.indexOf("家") >= 0) {
		regionName = '<span class="normal-state">'+regionName+'</span>';
	}
	if (type==="上班") {
		regionName = '<span>起点'+regionName+'</span>';
	}
	if (type==="下班") {
		regionName = '<span class="location">终点'+regionName+'</span>';
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
 * 判读是否为空
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function checkEmpty(value){
	return value ==""||value==null;
}

$('#monthDate').datetimepicker({
	format : "yyyy-mm",
	language : 'zh-CN',
	weekStart : 1,
	todayBtn : 1,
	autoclose : true,
	startView : 3,
	minView : 3,
	viewSelect : 3,
	forceParse : false
})
$('#monthDate').on("click",function(){
	$('.today').text('本月');
});
