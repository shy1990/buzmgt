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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<base href="<%=basePath%>" />
<title>月任务扣罚设置</title>
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/static/income/phone.css" />
<link rel="stylesheet" type="text/css" href="/static/income/commess.css" />

<script id="task-table-template" type="text/x-handlebars-template">
{{#if content}}
	{{#each content}}
     <div class="col-sm-5 tc-fangan"
				style="margin-right: 100px; margin-left: 30px;margin-bottom:7px">
				<div class="col-sm-2" style="margin-left: -30px">
					<img src="static/img/fan{{getImg @index}}.png" alt="">
				</div>
				<div class="col-sm-7">
					<span class="text-fa-1">{{maintitle}}</span>
				</div>
				<div class="col-sm-2 icon-fl-right">
					<a  href="/mainPlan/update/{{id}}" ><i
						class="icon-f icon-ren" data-toggle="tooltip"  title="适用人员"></i></a> <i
						class="icon-f icon-dele" data-toggle="modal"
						data-pid="{{id}}" data-target="#del"></i>
				</div>
	</div>
	{{/each}}
	
{{/if}}
	<div class="col-sm-5 tc-fangan" style="margin-right: 100px; margin-left: 30px;margin-bottom:7px">
				<div class="col-sm-2" style="margin-left: -30px">
					<img src="static/img/fan4.png" alt="">
				</div>
				<div class="col-sm-7">
					<span class="text-fa-4">添加方案</span>
				</div>
				<div class="col-sm-2 fa-tj ">
					<a href="/mainPlan/newPlan"><i class="icon-f icon-tj "></i></a>

				</div>

	</div>

</script>
</head>

<body>

	<div class="content main">
		<h4 class="page-header">
			<i class="ico icon-new"></i>提成方案 <a href="javascript:history.back();"><i
				class="ico icon-back fl-right"></i></a>
		</h4>


		<p style="margin-left: 20px">
			<span class="text-gery text-strong">选择大区：</span> <select id="region"
				class="ph-select">
				<option value="">全部区域</option>
				<c:forEach var="region" items="${regions}">
					<option value="${region.id}">${region.name}</option>
				</c:forEach>
			</select>
			<button class="btn btn-blue btn-sm" onclick="findMainPlanList();">区域检索</button>
		</p>


		<div class="row" id="planList"></div>
		<div id="abnormalCoordPager"></div>
	</div>

	<!--删除-->
	<div id="del" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">提示</h3>
				</div>

				<div class="modal-body">
					<div class="container-fluid">
						<div class="form-group">
							<p class="col-sm-12  ">当前还有使用人员，你确定要删除方案吗？？？</p>
							<p class="col-sm-12">删除后该方案将不复存在，所有提成规则及使用人员将使用到删除日期的0点为止！！</p>
						</div>


						<div class="btn-qx">
							<button class="btn btn-danger btn-d" onclick="deletePlan()">删除</button>
						</div>

						<div class="btn-dd">
							<button data-dismiss="modal" class="btn btn-primary btn-d">取消</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script
		src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
		type="text/javascript" charset="utf-8"></script>
	<script src="/static/js/common.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script src="/static/income/main/index.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript">
		var gloPlanId = null;
		var	base='<%=basePath%>';
		$(function() {
			$("[data-toggle='popover']").popover();
			findMainPlanList(0);
			$('.modal').on('show.bs.modal', function(event) {
				var button = $(event.relatedTarget); // Button that triggered the modal
				gloPlanId = button.data('pid'); // Extract info from data-* attributes
			});
		});
	</script>

</body>

</html>