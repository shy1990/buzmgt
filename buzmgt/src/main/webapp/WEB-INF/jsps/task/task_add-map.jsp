<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			System.out.print(basePath);
%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>添加任务</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/task/task.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
<script type="text/javascript"src="http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/RichMarker/1.2/src/RichMarker_min.js "></script>
<style type="text/css">
	.ltten{
		position:absolute;
		z-index:2;
		left:13px;
		top:10px
	}
	.mtten{
		position:absolute;
		z-index:2;
		left:13px;
		top:10px
	}
	.lticon{
		position:absolute;
		z-index:2;
		left:4px;
		top:3px
	}
</style>
<script type="text/javascript">
	var base = "<%=basePath%>";
	var searchData = {
		
	}
	var totalElements;//总条数
</script>
</head>

<body>
	<div class="content main task-add-page">
		<h4 class="page-header ">
			<i class="ico icon-saojie-add"></i>添加任务
			<!--区域选择按钮-->
			<div class="area-choose" id="area" data-a="${regionId}">
				选择区域：<span>${regionName}</span> <a class="are-line" href="javascript:;"
					onclick="getRegion(${regionId});">切换</a>
				<input id="regionid" type="hidden" value="">
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
					<span class="shop-num"><span class="text-red" id="totalElements"></span> 家店铺</span>
					<script type="text/javascript">
						$('.nav-task li').click(function() {
							$(this).addClass('active');
							$(this).siblings('li').removeClass('active');
						});
					</script>
					<div class="link-posit pull-right">
						<a href="/task/addVisitList" class="view">列表查看</a>
					</div>
				</div>
				<div class="clearfix"></div>
				<!--地图位置 width: 100%;height: 600px;-->
				<div class="map-box" id="allmap">
					<!-- <img width="100%" height="100%"
						src="static/img/background/saojie-map.png" /> -->
					
				</div>
				<!--地图位置-->
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
			<div class="modal-dialog " style="width: 500px;" role="document">
				<div class="modal-content">
					<!--modal-header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span class="glyphicon glyphicon-remove" aria-hidden="true">
								<!--&times;-->
							</span>
						</button>
						<h4 class="modal-top" style="font-size: none"
							id="exampleModalLabel">温馨提示</h4>
					</div>
					<!--modal-header-->
					<!--modal-body-->
					<div class="modal-body" style="padding-right: 70px">
						<h4 style="text-align: center">该店铺上次拜访任务还未完成!</h4>
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
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
		<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="<%=basePath%>static/task/task-map.js" type="text/javascript" charset="utf-8"></script>
</body>

</html>