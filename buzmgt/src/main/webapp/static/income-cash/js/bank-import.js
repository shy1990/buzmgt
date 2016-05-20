var bankTradeTotal = 0;
$(function() {
	nowTime();// 初始化日期
	findBankTradeList();// 查询列表
	initFileUpload();
	$("#file-input").fileinput();
    $('#file-input').on('filebrowse', function(event) {
    	var importDate=$('#importDate').val();
    	if(isEmpty(importDate)){
    		alert("请选择导入日期");
    		return false;
    	}
    	
    	$("#file-input").fileinput({
    		language: 'zh',
    		uploadUrl: '/bankTrade/upload?importDate='+importDate, // you must set a valid URL here else you will get an error
    		allowedFileExtensions : ['xls','xlsx'],
    		overwriteInitial: false,
    		showUpload: false, //是否显示上传按钮
    		showCaption: false,//是否显示标题
    		slugCallback: function(filename) {
    			return filename.replace('(', '_').replace(']', '_');
    		}
    	});
    });
    
    $('#file-input').on('filepreupload', function(event, data, previewId, index) {
    	var importDate=$('#importDate').val();
    	if(isEmpty(importDate)){
    		alert("请选择导入日期");
    		return false;
    	}
    });
    
    $("#file-input").on("fileuploaded",function(event, data, previewId, index){
    	$('#daoru').model('hide');
    	alert("ok");
    });
})

/**
 * 初始化文件上传
 */
function initFileUpload() {
    // 文件上传
//    $('.file').fileupload({
//        dataType: 'json',
//        add: function (e, data) {
//            $("#uploadFileDiv").show();
//            $("#uploadFile").on("click", function () {
//                if (!checkDataDate()) {
//                    alert("请选择数据时间!");
//                    return;
//                }
//
//                $('#message').text('上传中');
//
//                // 修改fileupload插件上传时的url，带参数。
//                $("#file").fileupload('option', 'url', '/bankTrade/upload?&importDate=' + $("#importDate").val());
//
//                data.submit();
//            });
//        },
//        done: function (e, data) {
//            if (data.result.result == "failure") {
//                $('#message').text(data.result.message);
//            }
//
//            $('#message').text("上传完成");
//        }
//    });
}

/**
 * 初始化日期 前1天
 */
function nowTime() {
	var newDate = (new Date()).DateAdd('d', -1);
	var nowDate = changeDateToString(newDate);
	SearchData['sc_EQ_payDate'] = nowDate;
	$('#searchDate').val(nowDate)
}


/**
 * 检索
 */
function goSearch() {
	var Time = $('#searchDate').val();
	if (!isEmpty(Time)) {
		SearchData['sc_EQ_payDate'] = Time;
		findBankTradeList();
	}
}
/**
 * 导出
 */
$('#table-export').on(
		'click',
		function() {
			var startTime = $('#startTime').val();
			if (!isEmpty(startTime)) {

				SearchData['sc_EQ_payDate'] = startTime;

					window.location.href = base + ""
							+ conditionProcess();
			}

		});
function findTab() {
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
function findBankTradeList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : "/bankTrade",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createBankTradeTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != bankTradeTotal || searchTotal == 0) {
				bankTradeTotal = searchTotal;

				bankTradePaging(orderData);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	})
}
/**
 * 生成油补统计列表
 * 
 * @param data
 */

function createBankTradeTable(data) {
	var myTemplate = Handlebars.compile($("#bankTrade-table-template").html());
	$('#bankTradeList').html(myTemplate(data));
}
/**
 * 分页
 * 
 * @param data
 */
function bankTradePaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#bankTradePager').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findBankTradeList(curr - 1);
		}
	});
}
Handlebars.registerHelper('formDate', function(value) {
	if (value == null || value == "") {
		return "----";
	}
	return changeTimeToString(new Date(value));
});
/**
 * 根据流水号查询
 */
function findBySalesManName() {
	var salesmanName = $('#salesmanName').val();
		$.ajax({
//			url : base+"/bankTrade?sc_EQ_salesManName=" + salesmanName,
			type : "GET",
			dataType : "json",
			success : function(orderData) {
				if (orderData.totalElements < 1) {
					alert("未查到相关信息！");
					return false;
				}
				createBankTradeTable(orderData);
			},
			error : function() {
				alert("系统异常，请稍后重试！");
			}
		})
}
/**
 * 判读是否为空
 * 
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function isEmpty(value) {
	return value == undefined || value == "" || value == null;
}
$('#searchDate').datetimepicker({
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
$('#importDate').datetimepicker({
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
	var startInputDateStr = $('#startTime').val();
	if (startInputDateStr != "" && startInputDateStr != null) {
		var startInputDate = stringToDate(startInputDateStr).valueOf();
		if (ev.date.valueOf() - startInputDate < 0) {
			$('.form_date_end').addClass('has-error');
		}
	}
});
