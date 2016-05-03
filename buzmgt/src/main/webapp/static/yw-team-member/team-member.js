
function getPageList(num,regionId){
	var status=$("#status").val();
	window.location.href="/teammember/getSalesManList?salesmanStatus="+status+"&page="+num+"&regionId="+regionId
}

function getList(param,name,regionId){
    if(name == "goSearch" || name == "param"){
    	var value = $("#param").val();
    	window.location.href="/teammember/getSalesManList?truename="+value+"&jobNum="+value+"&regionId="+regionId
    }else if(name == "salesmanStatus"){
    	window.location.href="/teammember/getSalesManList?Status="+param+"&regionId="+regionId
    }
}

function toSalesManInfo(id,flag){
	window.location.href="/teammember/toSalesManInfo?saojieId="+id+"&flag="+flag;
}

function searchForm(event,value,regionId){
	if(event.keyCode==13){

		window.location.href="/teammember/getSalesManList?truename="+value+"&jobNum="+value+"&regionId="+regionId
	}
}
    
$(function(){
//	$(':checked').click();
//	/**
//	 * 区域选中样式切换
//	 */
//	$('.j_district').click(function(){
//		$(this).toggleClass('active');
//	});
//	/**
//	 * 区域全选
//	 */
//	$('.j_district_all').click(function(){
//		$(this).toggleClass('active');
//		//判读是否被选中
//		var msg=$(this).hasClass('active');
//		//样式修改
//		if(msg){
//			$('.j_district').addClass('active');
//		}else{
//			$('.j_district').removeClass('active');
//		}
//		//checkbox选中去除全选本身checked
//		$('.j_territory input[type=checkbox]:gt(0)').each(function(index,eve){
//			this.checked=msg;
//		});
//		//$('.j_district').click();//反选
//	});

	

	//getList(param)
	var status = $("#status").val();
	/*if(status == null ||  "".equals(status)){
		status = "扫街中";
	}*/
	$(" li[title = '"+status+"']").addClass('active');
});


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
	
});
