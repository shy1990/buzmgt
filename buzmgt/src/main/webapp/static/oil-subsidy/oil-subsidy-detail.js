var oilCostRecordTotal = 0;//油补统计总条数 
$(function() {
//	createRecordSort(detialData);
//	createOilCostDetialList(detialData);//油补详情
//	页面的渲染（关于异常坐标握手点）
	renduAbnormal();
})
function renduAbnormal(){
	var $('#renduAbnormal');
}
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
 * 生成油补记录详情列表
 * @param data
 */

function createOilCostDetialList(data) {
	console.info(data);
	var myTemplate = Handlebars.compile($("#detailList-table-template").html());
	$('#detailList').html(myTemplate(data));
}
/**
 * 生成油补记录地点
 * @param data
 */

function createRecordSort(data) {
	console.info(data);
	var myTemplate = Handlebars.compile($("#record-sort-table-template").html());
	$('#recordSort').html(myTemplate(data));
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
Handlebars.registerHelper('addOne', function(index) {
	var html='';
	if (index === 0) {
		return html ='<span class="order begin">起</span>';
	}
		html = '<span class="order">'+index+'</span>'
	return html;
});

/**
 * 判读是否为空
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function checkEmpty(value){
	return value ==""||value==null;
}
