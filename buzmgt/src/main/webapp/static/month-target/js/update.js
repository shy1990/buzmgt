/*月指标保存和修改*/
$(function() {
	
	/*$.ajax({
		url : base+"monthTarget/statistics",
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
	})*/
});

function toUpdate(){
	window.location.href = "/monthTarget/toUpdate";
}
