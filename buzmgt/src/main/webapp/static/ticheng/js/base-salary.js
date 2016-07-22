var baseSalaryTotal = 0;
$(function() {
	initModal();
	initRegionId();
	findBaseSalaryList();// 查询列表
})
function initModal() {
	$('.modal').on('hidden.bs.modal', function(e) {
		window.location.reload();
	})
	$('#updModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var recipient = button.data('whatever') // Extract info from data-* attributes
	  var recipient1 = button.data('salary') // Extract info from data-* attributes
	  var modal = $(this)
	  modal.find('.modal-body #updateId').val(recipient)
	  modal.find('.modal-body #updSalary').val(recipient1)
	})
	$('#delModal').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget) // Button that triggered the modal
		var id = button.data('id') // Extract info from data-* attributes
		var salary = button.data('salary') // Extract info from data-* attributes
		var truename = button.data('truename') // Extract info from data-* attributes
		var modal = $(this)
		modal.find('.modal-body #delId').val(id);
		modal.find('.modal-body #delSalary').text(salary);
		modal.find('.modal-body #delName').text(truename);
	})
}

function addSalary() {
	var fmg = true;
	$('#addForm input').each(function() {
		if (isEmpty($(this).val())) {
			fmg = false;
			alert("信息不完整");
			return false;
		}
	});
	if (!fmg) {
		return false;
	}
	var data = $('#addForm').serializeArray();
	$.ajax({
		url : base + 'baseSalary',
		type : 'post',
		data : data,
		dataType : 'json',
		success : function(data) {
			if (data.status != 'failure'){
				alert(data.successMsg);
				$('#addModal').modal('hide');
			}
			else
				alert(data.errorMsg);
		},
		error : function() {
			alert('系统异常！');
		}
	})
}
function initRegionId(){
	var regionId=$('#regionId').val();
	var regionType=$('#regionType').val();
	SearchData['sc_regionId'] = regionId;
	SearchData['sc_regionType'] = regionType;
}
function updateSalary(){
	var id=$('#updateId').val();
	var salary=$('#updSalary').val();
	if(isEmpty(id)||isEmpty(salary)){
		alert("请填写薪资");
		return false;
	}
	$.ajax({
		url:base+'baseSalary/'+id+"?salary="+salary,
		type:'PUT',
		dateType:'json',
		success:function(data){
			if (data.status != 'failure'){
				alert(data.successMsg);
				$('#updModal').modal('hide');
			}
		},
		error:function(data){
			alert('系统异常，稍后重试');
		}
	})
}
function deleteSalary(){
	var id=$('#delId').val();
	if(isEmpty(id)){
		alert("操作失败");
		return false;
	}
	$.ajax({
		url:base+'baseSalary/delete?Id='+id,
		type:'get',
		dateType:'json',
		success:function(data){
			if (data.status != 'failure'){
				alert(data.successMsg);
				$('#delModal').modal('hide');
			}
		},
		error:function(data){
			alert('系统异常，稍后重试');
		}
	})
}
/**
 * 检索
 */
function goSearch() {
	var truename=$('#truename').val();
	$.ajax({
		url:base+'baseSalary/'+truename,
		type:'GET',
		dataType:'json',
		success:function(data){
			createTable(data);
		}
	})
}
/**
 * 选择区域
 * @param id
 */
function getRegion(id){
	window.location.href='/region/getPersonalRegion?id='+id+'&flag=baseSalary';
}
/**
 * 导出
 */
$('#table-export').on('click', function() {
	
	window.location.href = base + "baseSalary/export";
});
function findBaseSalaryList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : base+"/baseSalary",
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
