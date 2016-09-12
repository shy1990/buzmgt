function initOtherPlan(oherPlanid) {
	$.ajax({
		url : "/mainPlan/" + oherPlanid,
		type : "get",
		dataType : "json",
		success : function(orderData) {
			$("#otherRegionName").text(orderData.regionName);
			$("#otherMaintitle").text(orderData.mainTitle);
			$("#otherSubtitle").text(orderData.subTitle);
			var data = orderData.data;
			for (var i = 0; i < data.length; i++) {
				addOtherUser(data[i].salesId, data[i].name, true, data[i].id);
			}
		},
		error : function() {
			alert("系统异常，请稍后重试！");
		}
	});
}
// 添加业务员flag区分是否为临时用户
function addOtherUser(salesId, salesName, flag, kid) {
	salesArr.push({
		'salesmanname' : salesName,
		'salesmanId' : salesId,
		"id" : kid
	});
	var dirHtml = '<div class="col-sm-2"  style="margin-bottom: 20px;margin-left: -20px"  id="saleDiv'
			+ salesArr.length
			+ '">'
			+ ' <div class="s-addperson ">'
			+ salesName
			+ '  <i class="icon-s icon-close" onclick="deletediv('
			+ salesArr.length
			+ ','
			+ flag
			+ ');" ></i>'
			+ ' </div>'
			+ ' </div>';
	$('#otherUserList').before(dirHtml);

}