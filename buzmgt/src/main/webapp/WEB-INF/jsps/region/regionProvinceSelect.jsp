<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>考核详情</title>
<!-- Bootstrap -->
<script src="/static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<style>
.ph-select {
	width: 90px;
	height: 30px;
	padding: 5px;
	border: 1px solid #c6c6c6;
	border-radius: 3px;
	background: #ffffff;
	color: #a7a7a7;
}
</style>
</head>
<body>

	<select id="provinceRegion" onchange="initCity()">
		<option value="">-请选择-</option>
	</select>-
	<select id="cityRegion" onchange="initCounty()">
		<option value="">-请选择-</option>
	</select>-
	<select id="countyRegion">
		<option value="">-请选择-</option>
	</select>
	<script type="text/javascript">
		getProvinceList();
		//得到省级区域
		function getProvinceList() {
			var options = document.getElementById("provinceRegion").options;
			$.ajax({
				url : "region/getAllRegion/2",
				type : "GET",
				dataType : "json",
				success : function(data) {
					for (var i = 0; i < data.length; i++) {
						options.add(new Option(data[i].name, data[i].id));
					}
				},
				error : function(data) {
					alert(data);
				}
			});
		}
		//初始化市下拉框
		function initCity() {
			var regId = $("#provinceRegion").val();
			var options = document.getElementById("cityRegion").options;
			initSelect(regId, options);
			initCounty();
		}
		//初始化县下拉框
		function initCounty() {
			var regId = $("#cityRegion").val();
			var options = document.getElementById("countyRegion").options;
			initSelect(regId, options);
		}
		/*通过区域id,opotions初始化select;
		 **目前为市select,区select
		 */
		function initSelect(regId, options) {
			options.length = 0;
			options.add(new Option("-请选择-", ""));
			if (regId == "") {
				return;
			}
			$.ajax({
				url : "region/getChlid/" + regId,
				type : "GET",
				dataType : "json",
				success : function(data) {
					
					for (var i = 0; i < data.length; i++) {
						options.add(new Option(data[i].name, data[i].id));
					}
				},
				error : function(data) {
					alert(data);
				}
			});
		}
		/**返回当前区域id
		 */
		function getRegionSelectVal() {
			var val = $("#countyRegion").val();
			if (val != "") {
				return val;
			}
			val = $("#cityRegion").val();
			if (val != "") {
				return val;
			}
			val = $("#provinceRegion").val();
			return val;
		}
	</script>
</body>

</html>
