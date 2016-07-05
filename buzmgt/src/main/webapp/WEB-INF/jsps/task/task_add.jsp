<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>添加任务</title>
<!-- Bootstrap -->
<link href="<%=basePath%>static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=basePath%>static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/task/task.css" />
<link href="<%=basePath%>static/bootStrapPager/css/page.css" rel="stylesheet">
<script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var base = "<%=basePath%>";
	var number = '';//当前页数（从零开始）
	var totalPages = '';//总页数(个数)
	var searchData = {
		"size" : "20",
		"page" : "0",
	}
	var totalElements;//总条数
</script>
<script id="table-template" type="text/x-handlebars-template">
{{#each content}}
	<tr>
		<td class="text-strong">{{shopName}}</td>
		<td>{{address}}</td>
		<td>周期内提货次：{{orderTimes}}次</td>
		<td>{{period}}</td>
		<td><span class="ico icon-link"></span> <span
			class="link-span">5家</span></td>
		<td>{{ lastVisit }}</td>
		<td><a class="btn btn-blue btn-sm" href="javascript:;" onclick="addVisit('{{registId}}','{{userId}}','{{shopName}}')"><i
			class="ico icon-add"></i>拜访</a></td>
	</tr>
{{else}}
<div style="text-align: center;">
	<tr style="text-align: center;">没有相关数据!</tr>
</div>
{{/each}}
</script>
</head>

<body>
	<div class="content main task-add-page">
		<h4 class="page-header ">
			<i class="ico icon-saojie-add"></i>添加任务
			<!--区域选择按钮-->
			<div class="area-choose">
				选择区域：<span>${regionName}</span> <a class="are-line" href="javascript:;"
					onclick="getChooseRegion(${regionId});">切换</a>
				<input id="area" type="hidden" value="${regionId}">
			</div>
			<!--/区域选择按钮-->
		</h4>
		<div class="row">
			<div class="col-md-12">
				<div class="table-before">
				<div class="area-choose">状态:</div>
					<ul class="nav nav-task">
						<li class="active"><a href="javascript:;" onclick="searchStatusData('CHECKIN')">考核中</a></li>
						<li><a href="javascript:;" onclick="searchStatusData('PASSED')">已转正</a></li>
					</ul>
				</div>
				<div class="table-before">
				<div class="area-choose">筛选:</div>
					<ul class="nav nav-task">
						<li class="active"><a href="javascript:;" onclick="searchConditionData('TWO')">活跃客户</a></li>
						<li><a href="javascript:;" onclick="searchConditionData('ZERO')">未提货客户</a></li>
						<li><a href="javascript:;" onclick="searchConditionData('ONE')">一次提货客户</a></li>
					</ul>
					<span class="shop-num"><span class="text-red" id="totalShop"></span>家店铺</span>
					<script type="text/javascript">
						$('.nav-task li').click(function() {
							$(this).addClass('active');
							$(this).siblings('li').removeClass('active');
						});
					</script>
					<div class="link-posit pull-right">
						<a href="/task/addVisitMap" class="view">地图查看</a> <a class="table-export"
							href="javascript:void(0);">导出excel</a>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="table-task-list new-table-box table-overflow">
					<table class="table table-hover new-table">
						<thead>
							<tr>
								<th>店铺名</th>
								<th>地址</th>
								<th>周期内提货</th>
								<th>距离上次提货（天）</th>
								<th>关联商家</th>
								<th>上次拜访</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="tableList">
							
						</tbody>
					</table>
					<div id="callBackPager"></div>
				</div>
			</div>
		</div>
		<!--添加任务-->
		<div class="modal-blue modal fade " id="addVisit" tabindex="-1"
			role="dialog" aria-labelledby="exampleModalLabel">
			<div class="modal-dialog " style="width:500px;" role="document">
				<div class="modal-content">
					<!--modal-header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span class="glyphicon glyphicon-remove" aria-hidden="true">
								<!--&times;-->
							</span>
						</button>
						<h4 class="modal-top" style="font-size:none" id="exampleModalLabel">
							添加拜访记录
						</h4>
					</div>
					<!--modal-header-->
					<!--modal-body-->
					<div class="modal-body" style="padding-right:70px">
					<form class="member-from-box form-horizontal" role="form" id='addForm' type='post'>
					   <div class="form-group">
					   <input type="hidden" name="registId" id="registId" value="">
					   <input type="hidden" name="userId" id="userId" value="">
					      <label for="firstname" class="col-sm-4 control-label">任务名称:</label>
					      <div class="input-group col-sm-8">
					      	<span class="input-group-addon"><i class="member-icon member-name-icon"></i></span>
					        <input type="text" class="form-control" id="taskName" name="taskName" placeholder="请填写任务名称">
					      </div>
					   </div>
					   <div class="form-group">
					      <label for="lastname" class="col-sm-4 control-label">截止日期:</label>
					      <div class="input-group col-sm-8">
					      <span class="input-group-addon"><i class="member-icon member-name-icon"></i></span>
							<input id="expiredTime" type="text" class="form-control form_datetime" readonly="readonly" name="expiredTime" value="" placeholder="请选择任务截止日期(默认2天)">
					      </div>
					   </div>
					   <div class="form-group">
					      <div class="col-sm-offset-5 col-sm-6">
					         <button type="button" class="btn btn-blue" style="width:125px;height:40px;font-weight:bold" onclick="saveVisit();">确 定</button>
					      </div>
					   </div>
					</form>
					</div>
					<!--modal-body-->
				</div>
			</div>
		</div>
		<!--添加任务-->
		<!--温馨提示-->
		<div class="modal-blue modal fade " id="tips" tabindex="-1"
			role="dialog" aria-labelledby="exampleModalLabel">
			<div class="modal-dialog " style="width:500px;" role="document">
				<div class="modal-content">
					<!--modal-header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span class="glyphicon glyphicon-remove" aria-hidden="true">
								<!--&times;-->
							</span>
						</button>
						<h4 class="modal-top" style="font-size:none" id="exampleModalLabel">
							温馨提示
						</h4>
					</div>
					<!--modal-header-->
					<!--modal-body-->
					<div class="modal-body" style="padding-right:70px">
						<h4 style="text-align:center">该店铺上次拜访任务还未完成!</h4>
					</div>
					<!--modal-body-->
				</div>
			</div>
		</div>
		<!--温馨提示-->
		<!-- Bootstrap core JavaScript================================================== -->
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
		<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
		<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
		<script src="<%=basePath%>static/js/dateutil.js"></script>
		<script src="<%=basePath%>static/visit/add-visit.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
		<script type="text/javascript">
		/*区域 */
		function getChooseRegion(id){
			window.location.href='/region/getPersonalRegion?id='+id+"&flag=taskAddList";
		}
		</script>
</body>
</html>
