<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<base href="<%=basePath%>" />
<title>自定义模块</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/customTask/type.css">

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>

<script id="task-table-template" type="text/x-handlebars-template">

	{{#if content}}
		{{#each content}}
					<tr>
						<td><span class="icon-{{qfType type}}">{{typeName}}</span> {{title}}</td>
						<td class="ssh" title="">
                             <p>
								<span> {{salesMan}}</span>
							</p>
						</td>									
						<td  onclick="openDetail({{id}});">{{content}}</td>
						<td>{{time}}</td>  
						<td>{{recieve}}</td>  
					</tr>
		{{/each}}
	{{else}}
		<tr>
			<td colspan="100">没有相关数据</td>
		</tr>
	{{/if}}
</script>

<style>

/* 省略号*/
.ssh {
	width: 200px; /*容器的基本定义*/
	height: auto;
}

/* IE下的样式 */
p span {
	display: block;
	width: 400px; /*对宽度的定义,根据情况修改*/
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

/* FF 下的样式 */
p {
	clear: both;
}
</style>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-dy"></i>自定义模块
			<div class="area-choose"></div>
			<!--/区域选择按钮-->
			<a href="<%=basePath%>customTask/add" class="btn btn-blue" > <i
				class="ico icon-add"></i>新建自定义事件
			</a>
		</h4>

		<div class="row text-time">
			<div style="margin-left: 20px">
				<div class="search-date">
					<div class="input-group input-group-sm">
						<span class="input-group-addon " id="basic-addon1"><i
							class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
							type="text" class="form-control form_datetime input-sm"
							placeholder="开始日期" readonly="readonly" id="start-time"
							style="background-color: #FFFFFF">
					</div>
				</div>
				--
				<div class="search-date">
					<div class="input-group input-group-sm">
						<span class="input-group-addon " id="basic-addon1"><i
							class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
							type="text" class="form-control form_datetime input-sm"
							placeholder="结束日期" readonly="readonly" id="end-time"
							style="background-color: #FFFFFF">
					</div>
				</div>

				<select class="box-sty-s" id="customType">
					<option value="">---请选择类型---</option>
					<option value="0">店铺注册</option>
					<option value="1">售后拜访</option>
					<option value="2">扣罚通知</option>
				</select>

				<button class="btn btn-blue btn-sm"
					style="height: 30px; margin-left: 10px" onclick="findTaskList(0)">检索</button>

				<div class="salesman">

					<input class="cs-select  text-gery-hs" id="salesName"
						placeholder="  请输入业务员名称">
					<button class="btn btn-blue btn-sm" onclick="findTaskList(0);">检索</button>
				</div>
			</div>
		</div>

		<div class="clearfix"></div>
		<div class="tab-content">
			<!--待审核账单-->
			<div class="tab-pane fade in active" id="box_tab1">
				<!--table-box-->
				<div class="table-task-list new-table-box table-overflow">
					<table class="table table-hover new-table">
						<thead>
							<tr>
								<th>任务标题</th>
								<th>业务员</th>
								<th>内容介绍</th>
								<th>发布日期</th>
								<th>回执状态</th>
							</tr>
						</thead>
						<tbody id="taskList"></tbody>

					</table>
				</div>
				<!--table-box-->
			</div>
			<!--待审核账单-->
		</div>
		<div id="abnormalCoordPager"></div>
	</div>
	</div>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript" src="/static/customTask/index.js"></script>
	
	<script type="text/javascript">
		$('body input').val('');
		$(".form_datetime").datetimepicker({
			format : "yyyy-mm-dd",
			language : 'zh-CN',
			weekStart : 1,
			todayBtn : 1,
			autoclose : 1,
			todayHighlight : 1,
			startView : 2,
			minView : 2,
			pickerPosition : "bottom-right",
			forceParse : 0
		});
		var $_haohe_plan = $('.J_kaohebar').width();
		var $_haohe_planw = $('.J_kaohebar_parents').width();
		$(".J_btn").attr("disabled", 'disabled');
		if ($_haohe_planw === $_haohe_plan) {
			$(".J_btn").removeAttr("disabled");
		}
		
		findTaskList(0);
		function openDetail(id){
			window.location.href="<%=basePath%>customTask/details/"+id;
		}
	</script>
</body>
</html>