//日期格式(yyyy.MM.dd)
function changeDateToString_(DateIn){
	var date = changeDateToString(DateIn);
	return date.replace(/-/g, ".");
}

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
//日期格式（yyyy-MM-dd hh:mm）
function changeTimeToString(DateIn) {
	var year = 0;
	var month = 0;
	var day = 0;
	var currentDate = "";
	var hour = 0;
	var minute = 0;
	// 初始化时间
	year = DateIn.getFullYear();
	month = DateIn.getMonth() + 1;
	day = DateIn.getDate();
	hour = DateIn.getHours();
	minute = DateIn.getMinutes();
	currentDate = year + "-";
	if (month >= 10) {
		currentDate = currentDate + month + "-";
	} else {
		currentDate = currentDate + "0" + month + "-";
	}
	if (day >= 10) {
		currentDate = currentDate + day +" ";
	} else {
		currentDate = currentDate + "0" + day+ " ";
	}
	if (hour >= 10) {
		currentDate = currentDate + hour + ":";
	} else {
		currentDate = currentDate + "0" + hour + ":";
	}
	if (minute >= 10) {
		currentDate = currentDate + minute;
	} else {
		currentDate = currentDate + "0" + minute;
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
    return dates;   
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
/**
 * 两个字段都不为空时进行校验日期查询
 * @param startTimeStr
 * @param endTimeStr
 * @returns {Boolean}
 */
function checkDate(startTimeStr,endTimeStr){
	//当两个字段都不为空时进行校验;
	var fale=(startTimeStr != "" && startTimeStr != null)&&
	(endTimeStr != '' && endTimeStr != null);
	if(fale){
		var startDate= stringToDate(startTimeStr);
		var endDate= stringToDate(endTimeStr);
		if(endDate.valueOf()-startDate.valueOf()>=0){
			return true;
		}else{
			return false;
		}
	}else{
		return true;
	}
}

/**
 * 两个字段都不为空时进行日期比较判断
 * @param startTimeStr
 * @param endTimeStr
 * @returns {Boolean}
 */
function compareDate(startTimeStr,endTimeStr){
	//当两个字段都不为空时进行校验;
	var fale=(startTimeStr != "" && startTimeStr != null)&&
		(endTimeStr != '' && endTimeStr != null);
	if(fale){
		var startDate= stringToDate(startTimeStr);
		var endDate= stringToDate(endTimeStr);
		if(endDate.valueOf()-startDate.valueOf()<0){
			return true;
		}else{
			return false;
		}
	}else{
		return true;
	}
}

//转化时间格式的
/*示例代码:
 *将返回本月的date
 * var month = new Date().format("yyyy-MM");
 * 
 * */
Date.prototype.format = function (format) {
	var o = {
		"M+" : this.getMonth() + 1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter
		"S" : this.getMilliseconds()
		//millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					 : ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}
Date.prototype.setFullTime =function(hour,minute,second){
	this.setHour(0);
	this.setMinutes(0);
	this.setSeconds(0);
}
//得到月份 reduceNum:0是下个月份,1是本月份
function getNextMonth(date,reduceNum) {
	var month = date.getMonth()-reduceNum;
	var year = date.getFullYear();
	if (month < 9) {
		month = "0" + (month + 2);
	} else {
		month = (month + 2);
	}
	month = year + "-" + month;
	return month;
}
/**
 * 增加月份
 * @param date
 * @param addNum
 * @returns {Date}
 */
function addMonth(date,addNum ) {
    var month = date.getMonth()+addNum;
    return  new Date(date.setMonth(month));
}