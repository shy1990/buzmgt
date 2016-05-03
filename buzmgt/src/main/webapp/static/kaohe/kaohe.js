/*考核列表*/
function getAllAssessList(regionId){
	
	window.location.href="/assess/getAssessList?regionid="+regionId;
}

function getAssessList(param,name,regionId){
    if(name == "goSearch"){
    	var value = $("#param").val();
    	window.location.href="/assess/getAssessList?salesman.truename="+value+"&salesman.jobNum="+value+"&regionid="+regionId;
    }else if(name == "status"){
    	window.location.href="/assess/getAssessList?assessStatus="+param+"&regionid="+regionId;
    }
}

/*enter键*/
function check() {
	var bt = document.getElementById("goSearch");
	var event = window.event || arguments.callee.caller.arguments[0];
    if (event.keyCode == 13)
    {
        bt.click();
    }
}

function getPageList(num,regionId,name,job,statu){
	
	window.location.href="/assess/getAssessList?page="+num+"&regionid="+regionId+"&salesman.jobNum="+job+"&salesman.truename="+name+"&assessStatus="+statu;
}