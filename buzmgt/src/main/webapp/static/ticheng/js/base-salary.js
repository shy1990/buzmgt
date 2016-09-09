var baseSalaryTotal = 0;
$('select[multiple].demo3').multiselect({
	columns : 1,
	placeholder : '请选择业务姓名',
	search : true,
	selectGroup : true
});

$(function() {
	initModal();
	initSalesId();
	initRegionId();
	findBaseSalaryList();// 查询列表
	initDateInput();
})
function initSalesId() {

	if (salesId.length > 1) {
		SearchData["sc_salesId"] = salesId;
		//删除日期>本月,有效期<下月
		SearchData["sc_GT_newdate"] = month+ "-01";
		var year = month.substr(0, 4);
		var monthstr = month.substr(5, 7);
		var nextmonth = "";
		if (month == "12") {
			nextmonth = (Number(year) + 1) + "-01";
		} else {
			monthstr=Number(monthstr)+1;
			monthstr=monthstr<10?'0'+monthstr:monthstr;
			nextmonth=year+"-"+monthstr;
		}
		SearchData["sc_LT_deldate"] = nextmonth+"-01";
		$("#searchDiv").css("display", "none");
	}
}
function initModal() {
	$('.modal').on('hidden.bs.modal', function(e) {
		window.location.reload();
	})

}

function addSalary() {

	var user = {
		"salary" : $("#salary").val(),
		"newdate" : $("#newdate").val()
	};
	if (user.salary == "" || user.newdate == "") {
		alert("数据不能为空!!");
		return;
	}
	var saleArr = $('select[multiple].demo3').val();
	if (saleArr.length < 1 || saleArr == "") {
		alert("数据不能为空!!");
		return;
	}
	var salarayArr = [];
	for (var i = 0; i < saleArr.length; i++) {
		var salary = {
			"salary" : user.salary,
			"newdate" : user.newdate,
			"userId" : saleArr[i]
		};
		salarayArr.push(salary);
	}
	$.ajax({
		url : base + 'baseSalary/',
		type : 'post',
		data : JSON.stringify(salarayArr),
		beforeSend : function(request) {
			request.setRequestHeader("Content-Type", "application/json");
		},
		dataType : 'json',
		success : function(data) {
			if (data.status != 'failure') {
				alert(data.successMsg);
				$('#addModal').modal('hide');
			} else {
				alert(data.errorMsg);
			}
		},
		error : function() {
			alert('系统异常！');
		}
	})
}

function initRegionId() {
	var regionId = $('#regionId').val();
	var regionType = $('#regionType').val();
	SearchData['sc_regionId'] = regionId;
	SearchData['sc_regionType'] = regionType;
}
var globalId = null;
var globalSalary = null;
var globalNewdate = null;
function openModify(id, salary, newdate) {
	globalId = id;
	globalSalary = salary;
	globalNewdate = newdate;
	$('#updSalary').val(salary);
	$("#updModal").modal("show");
}
function updateSalary() {
	var id = globalId;
	var salary = $('#updSalary').val();
	var update = $("#updateTime").val();
	if (salary < 1) {
		alert("薪资请输入自然数");
		return;
	} else if (salary == globalSalary) {
		alert("和原来薪资相同,无需修改");
		return;
	}
	if (update == '') {
		alert("修改起效日期不能为空!!");
		return;
	} else if (update < globalNewdate) {
		alert("修改起效日期早于新增日期,日期无效");
		return;
	}
	$.ajax({
		url : base + 'baseSalary/' + id + "?salary=" + salary + "&upDate="
				+ update,
		type : 'PUT',
		dateType : 'json',
		success : function(data) {
			if (data.status != 'failure') {
				alert(data.successMsg);
				$('#updModal').modal('hide');
			}
		},
		error : function(data) {
			alert('系统异常，稍后重试');
		}
	})
}

/**
 * 人名检索
 */
function goNameSearch() {
	var researchData = "";
	var truename = $('#truename').val();
	if (truename != "") {
		researchData += "?sc_trueName=" + truename;
	}
	$.ajax({
		url : base + 'baseSalary' + researchData,
		type : 'GET',
		dataType : 'json',
		success : function(data) {
			createTable(data);
		}
	});
}
/*
 * 区域检索
 */
function goRegionSearch() {
	var researchData = "";
	var regionName = $("#region").val();
	if (regionName != "") {
		SearchData.sc_regionName = regionName;
	}
	$.ajax({
		url : base + 'baseSalary/',
		type : 'GET',
		data : SearchData,
		dataType : 'json',
		success : function(data) {
			createTable(data);
		}
	});
}

/**
 * 导出
 */
$('#table-export').on('click', function() {

	window.location.href = base + "baseSalary/export?sort=flag";
});

function findBaseSalaryList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	SearchData["sc_regionName"] = "";
	$.ajax({
		url : base + "/baseSalary?sort=flag&sort=id",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != baseSalaryTotal || searchTotal == 0) {
				baseSalaryTotal = searchTotal;
				initPaging(orderData);
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

function createTable(data) {
	Handlebars.registerHelper("isvalid", function(state, options) {
		if (state == '正常') {
			// 满足添加继续执行
			return options.fn(this);
		} else {
			// 不满足条件执行{{else}}部分
			return options.inverse(this);
		}
	});
	var myTemplate = Handlebars.compile($("#baseSalary-table-template").html());
	$('#table-list').html(myTemplate(data));
}
/**
 * 分页
 * 
 * @param data
 */
function initPaging(data) {
	var totalCount = data.totalElements, limit = data.size;
	$('#initPage').extendPagination({
		totalCount : totalCount,
		showCount : 5,
		limit : limit,
		callback : function(curr, limit, totalCount) {
			findBaseSalaryList(curr - 1);
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
 * 判读是否为空
 * 
 * @param value
 * @returns 为空返回true 不为空返回false
 */
function isEmpty(value) {
	return value == undefined || value == "" || value == null;
}
// 初始化时间框
function initDateInput() {
	$(".form_datetime").datetimepicker({
		format : "yyyy-mm-dd",
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		minView : 2,
		pickerPosition : "bottom-right",
		forceParse : 0
	});
}
