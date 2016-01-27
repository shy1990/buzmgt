
function getPageList(num,regionId){
	alert(regionId);
	window.location.href="/teammember/getSalesManList?page="+num+"&regionId="+regionId
}

function getList(param,name,regionId){
    if(name == "goSearch"){
    	var value = $("#param").val();
    	window.location.href="/teammember/getSalesManList?truename="+value+"&jobNum="+value+"&regionid="+regionId
    }else if(name == "salesmanStatus"){
    	window.location.href="/teammember/getSalesManList?Status="+param+"&regionid="+regionId
    }
}

function toSalesManInfo(id){
	window.location.href="/teammember/toSalesManInfo?userId="+id;
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

	getList()

	//getList(param)
	//var status = $("#status").val();
	/*if(status == null ||  "".equals(status)){
		status = "扫街中";
	}*/
	//$(" li[title = '"+status+"']").addClass('active');
});

function getList(){
	/*var userId = $("#userId").html();
	var regionId = $("#regionId").val();*/
	$.ajax({
		type:"post",
		url:"/teammember/getSalesManList",
		//data:formValue,
		dataType:"JSON",
		data :{
			"page" : userId,
			"jobNum" : regionId,
			"truename" : regionId,
			"Status" : regionId
			
		},
		success : function(result){
		   if (result) {
			   alert(result.number);
			  /* var saojiedata = $("#saojiedata");  
			   saojiedata.empty();  
			   for(var i = 0;i<result.list.length;i++){
				   saojiedata.append("<div  class='list-tr'>"+
							"<img class='shop-img' src='"+result.list[i].imageUrl+"' />"+
							"<div style='display: inline-block;' class='list-conter'>"+
								"<h4>"+result.list[i].name+"</h4>"+
								"<p>"+result.list[i].discription+"</p>"+
								"<span class='pull-right'>"+result.list[i].saojieDate+"</span>"+
							"</div>"+
						"</div>     ");
			   }
			  */
			}
		}
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
