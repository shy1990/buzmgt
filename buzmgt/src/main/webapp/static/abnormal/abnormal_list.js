$(function(){
	findYwOrderList(0);
	findMemberOrderList(0);
});

function findYwOrderList(page){
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	page=page==null||page==''? 0 :page;
	$.ajax({
		url:"ordersignfor/list?type='ywOrderSignfor'&page="+page+"&startTime="+startTime+"&endTime="+endTime,
		type:"GET",
		data: SearchData,
		success:function(orderData){
			createYwTable(orderData);
		}
	})
}

function createYwTable(data) {
	var myTemplate = Handlebars.compile($("#ywSignfor-table-template").html());
	$('#ywOrderList').html(myTemplate(data));
//	Paging(data);
}
function findMemberOrderList(page){
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	page=page==null||page==''? 0 :page;
	$.ajax({
		url:"ordersignfor/list?type=''&page="+page+"&startTime="+startTime+"&endTime="+endTime,
		type:"GET",
		data: SearchData,
		success:function(orderData){
			createMemberTable(orderData);
		}
	})
}

function createMemberTable(data) {
	var myTemplate = Handlebars.compile($("#memberSignfor-table-template").html());
	$('#memberOrderList').html(myTemplate(data));
//	Paging(data);
}
function Paging(data) {
    var totalCount = Number($('#totalCount1').val()) || 252, showCount = $('#showCount1').val(),
            limit = Number($('#limit').val()) || 10;
    createTable(1, limit, totalCount);
    $('#callBackPager').extendPagination({
        totalCount: data.totalElements,
        showCount: showCount,
        limit: limit,
        callback: function (curr, limit, totalCount) {
            createTable(curr, limit, totalCount);
        }
    });
}