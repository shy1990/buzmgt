

function getPageList(num){
	
	window.location.href="/salesman/getSalesManList?page="+num
}

function getList(param,name){
    if(name == "goSearch"){
    	var value = $("#param").val();
    	window.location.href="/salesman/getSalesManList?truename="+value+"&jobNum="+value
    }else if(name == "salesmanStatus"){
    	window.location.href="/salesman/getSalesManList?Status="+param
    }
}


var myDate = new Date();
var tody = changeDateToString(myDate);
$(function(){
    $(".form_datetime").datetimepicker({
        format: "yyyy年mm月",
        endDate : tody,
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 3,
		minView : 3,
		pickerPosition : "bottom-left",
		forceParse : 0
    });
	//getList(param)
	var status = $("#status").val();
	/*if(status == null ||  "".equals(status)){
		status = "扫街中";
	}*/
	$(" li[title = '"+status+"']").addClass('active');
});