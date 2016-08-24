var pushMoneyTotal = 0;
$(function() {
	initModal();
//	initRegionId();
	findPushMoneyList();// 查询列表
})
function initModal() {
	$('.modal').on('hidden.bs.modal', function(e) {
		findPushMoneyList();
	})
	$('#updModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var money = button.data('money') // Extract info from data-* attributes
	  var modal = $(this);
	  modal.find('.modal-body #updMoney').val(money);
	})
	$('#delModal').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget) // Button that triggered the modal
		var id = button.data('id') // Extract info from data-* attributes
		var category = button.data('category') // Extract info from data-* attributes
		var priceSocpe = button.data('pricescope') // Extract info from data-* attributes
		var money = button.data('money') // Extract info from data-* attributes
		var modal = $(this);
		modal.find('.modal-body #delId').val(id);
		modal.find('.modal-body #delPriceSocpe').text(priceSocpe);
		modal.find('.modal-body #delCategory').text(category);
		modal.find('.modal-body #delMoney').text(money);
	});
	$('#setRegionModal').on('show.bs.modal', function(event){
		var button = $(event.relatedTarget) // Button that triggered the modal
		var priceSocpe = button.data('pricescope') // Extract info from data-* attributes
		var category = button.data('category') // Extract info from data-* attributes
		var modal = $(this);
		modal.find('.modal-title').text(category+" "+priceSocpe);
		
	});
}

function addPushMoney() {
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
		url : base + 'pushMoney',
		type : 'post',
		data : data,
		dataType : 'json',
		success : function(data) {
			if (data.status != 'failure'){
				alert(data.successMsg);
				findPushMoneyList();
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
function delPushMoney(){
	var id=$('#delId').val();
	if(isEmpty(id)){
		alert("操作失败");
		return false;
	}
	$.ajax({
		url:base+'pushMoney/'+id,
		type:'DELETE',
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
	var categoryId=$("#searchForm select:eq(0)").val();
	var priceScopeId=$("#searchForm select:eq(1)").val();
	SearchData['sc_EQ_categoryId']=categoryId;
	SearchData['sc_EQ_priceScopeId']=priceScopeId;
	findPushMoneyList();
	delete SearchData['sc_EQ_categoryId'];
	delete SearchData['sc_EQ_priceScopeId'];
	
}

/**
 * 导出
 */
$('#table-export').on('click', function() {
//	window.location.href = base + "baseSalary/export";
});
function findPushMoneyList(page) {
	page = page == null || page == '' ? 0 : page;
	SearchData['page'] = page;
	$.ajax({
		url : base+"/pushMoney",
		type : "GET",
		data : SearchData,
		dataType : "json",
		success : function(orderData) {
			createTable(orderData);
			var searchTotal = orderData.totalElements;
			if (searchTotal != pushMoneyTotal || searchTotal == 0) {
				pushMoneyTotal = searchTotal;

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
	var myTemplate = Handlebars.compile($("#pushMoney-table-template").html());
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
