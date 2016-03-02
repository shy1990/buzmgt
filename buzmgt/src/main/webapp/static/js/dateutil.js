//日期格式（yyyy-MM-dd ）
function changeDateToString(DateIn) {
	var year = 0;
	var month = 0;
	var day = 0;
	var currentDate = "";
	// 初始化时间
	year = DateIn.getFullYear();
	month = DateIn.getMonth() + 1;
	day = DateIn.getDate();
	currentDate = year + "-";
	if (month >= 10) {
		currentDate = currentDate + month + "-";
	} else {
		currentDate = currentDate + "0" + month + "-";
	}
	if (day >= 10) {
		currentDate = currentDate + day;
	} else {
		currentDate = currentDate + "0" + day;
	}
	return currentDate;
}
// +---------------------------------------------------
// | 日期计算
// +---------------------------------------------------
Date.prototype.DateAdd = function(strInterval, number) {
	var dtTmp = this;
	switch (strInterval) {
	case 's':
		return new Date(Date.parse(dtTmp) + (1000 * number));
	case 'i':
		return new Date(Date.parse(dtTmp) + (60000 * number));
	case 'h':
		return new Date(Date.parse(dtTmp) + (3600000 * number));
	case 'd':
		return new Date(Date.parse(dtTmp) + (86400000 * number));
	case 'w':
		return new Date(Date.parse(dtTmp) + ((86400000 * 7) * number));
	case 'q':
		return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + number * 3, dtTmp.getDate(),
				dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
	case 'm':
		return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + number, dtTmp.getDate(), dtTmp
				.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
	case 'y':
		return new Date((dtTmp.getFullYear() + number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp
				.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
	}
}
// +---------------------------------------------------
// | 字符串转成日期类型
// | 格式 MM/dd/YYYY MM-dd-YYYY YYYY/MM/dd YYYY-MM-dd
// +---------------------------------------------------

function stringToDate(DateStr) {
	var converted = Date.parse(DateStr);
	var myDate = new Date(converted);
	if (isNaN(myDate)) {
		// var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';
		var arys = DateStr.split('-');
		myDate = new Date(arys[0], --arys[1], arys[2]);
	}
	return myDate;
}

/**
 * 获取当前日期格式(yyyy-MM-dd)
 */
function getTodayDate() {
	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	return year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
}

/**
 * 计算日期间隔Str
 * @param startDateStr
 * @param endDateStr
 * @returns {Number}
 */
function GetDateDiffStr(startDateStr,endDateStr)  
{  
    var startTime = new Date(Date.parse(startDateStr.replace(/-/g,   "/"))).getTime();     
    var endTime = new Date(Date.parse(endDateStr.replace(/-/g,   "/"))).getTime();     
    var dates = Math.abs((startTime - endTime))/(1000*60*60*24);     
    return  dates;    
}
/**
 * 计算日期间隔Str
 * @param startDate
 * @param endDate
 * @returns {Number}
 */
function GetDateDiff(startDate,endDate)  
{  
	    var startTime = startDate.getTime();     
	    var endTime = endDate.getTime();     
	    var dates = Math.floor((startTime - endTime)/(1000*60*60*24));     
	    return  dates;    
}

