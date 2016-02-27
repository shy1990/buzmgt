
function toAssessStage(salesmanId,assessId){
	window.location.href="/assess/toAssessStage?id="+salesmanId+"&assessId="+assessId;
}

function goSearch(sId,aId){
	var regionId = $("#regionNameid").val();
	var begin = $("#beginTime").val();
	var end = $("#endTime").val();
	window.location.href="/assess/toAccessDet?salesmanId="+sId+"&asssessid="+aId+"&regionid="+regionId+"&begin="+begin+"&end="+end;
}

function getPageList(num,regionId,sId,aId){
	
	window.location.href="/assess/toAccessDet?page="+num+"&regionid="+regionId+"&salesmanId="+sId+"&asssessid="+aId+"&begin="+begin+"&end="+end;
}

