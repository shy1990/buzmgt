<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>提成设置</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/phone-set/css/phone.css">
<link rel="stylesheet" type="text/css"
	href="static/phone-set/css/comminssion.css">
<link rel="stylesheet" type="text/css"
	href="static/achieve/achieve.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="achieve-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
   <tr>
      <td>{{addOne @index}}</td>
      <td>{{brand.name}}</td>
      <td>{{good.name}}</td>
      <td class="reason">
				约定时间内达量{{numberFirst}} | {{numberSecond}} | {{numberThird}}
			</td>
      <td>
				<span class="text-blue">{{formDate startDate}}-{{formDate endDate}}</span>
      </td>
      <td><span class="text-blue">{{formDate issuingDate}}</span></td>
      <td><span class="ph-on">进行中</span></td>
      <td>{{formDate createDate}}</td>
      <td>
        <button class="btn bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看</button>
        <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程</button>
        <button class="btn btn-sm btn-sc " data-toggle="modal" data-target="#">删除</button>
      </td>
    </tr>
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
<script id="goods-template" type="text/x-handlebars-template">
{{#if this}}
<option value="{{id}}">型号</option>
{{#each this}}
<option value="{{id}}">{{name}}</option>
{{/each}}
{{else}}
<option value="{{id}}">型号</option>
{{/if}}
</script>
<script type="text/javascript">
var	base='<%=basePath%>';
</script>
</head>
<body>
    <form id="achieveForm" action="" onsubmit="return false;" method="post">
    	<input type="hidden" name="planId" value="${planId }">
    	<input type="hidden" name="machineType" value="${machineType }">
      <div class="">
        <label>请选择品牌型号</label>
        <select name="brand" class="J_brand">
          <option value="">品牌</option>
          <c:forEach var="brand" items="${brands }" varStatus="status">
	          <option value="${brand.brandId }">${brand.name }</option>
          </c:forEach>
        </select>
        <select id="goodList" name="good.id" >
          <option value="">型号</option>
        </select>
      </div>
      <div class="">
      	<label>周期任务量：</label>
        <input type="text" name="numberFirst" value="" />台
      	<input type="text" name="numberSecond" value="" />台
      	<input type="text" name="numberThird" value="" />台
      </div>
      <div>
      	<button type="button" onclick="addRule();" >添加奖罚</button>
        <label>一阶段提成奖罚：</label>
				<div class="J_RULE"></div>	
      </div>
      <div class="J_groupNumber">
        <p>人员分组设置</p>
        <dd>A</dd>组 一阶段达量：<input type="text" name="numberFirstAdd"/>
        二阶段达量：<input type ="text" name="numberSecondAdd"/>
        三阶段达量：<input type="text" name="numberThirdAdd"/>
                      人员：<input type="text" name=""><br />
        B组 一阶段达量：<input type="text" name="numberFirstAdd"/>
        二阶段达量：<input type ="text" name="numberSecondAdd"/>
        三阶段达量：<input type="text" name="numberThirdAdd"/>
                      人员：<input type="text" name=""><br />
        C组 一阶段达量：<input type="text" name="numberFirstAdd"/>
        二阶段达量：<input type ="text" name="numberSecondAdd"/>
        三阶段达量：<input type="text" name="numberThirdAdd"/>
                      人员：<input type="text" name=""><br />
      </div>
      <div class="">
        <label>补充说明：</label>
        <textarea class="J_remark" cols="4" name="remark"></textarea>
      </div>
      <div class="">
        <label>方案起止日期</label>
        <input type="date" name="startDate"/>-<input type="date" name="endDate" />
      </div>
      <div class="">
      	<label>提成发放日期</label>
        <input type="date" name="issuingDate"/>
      </div>
      <div class="">
      	<label>指派审核人</label>
      	<select class="J_auditor" name="auditor">
      		<option value="admin">老张</option>
      		<option value="admin">老张</option>
      	</select>
      </div>
      <input type="button" value="submit" onclick="toSubmit();"/>
    </form>
    
	<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<script type="text/javascript" src="static/js/common.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/js/dateutil.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript"
		src="static/achieve/achieve_add.js" charset="utf-8"></script>
	<script type="text/javascript">
// 		$(".J_MachineType li").on("click",function(){
// 			$(this).addClass("active");
// 			$(this).siblings("li").removeClass("active");
// 		});
		
	</script>
</body>

</html>