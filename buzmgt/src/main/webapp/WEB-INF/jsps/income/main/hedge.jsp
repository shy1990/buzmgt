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
<title>收益主方案添加</title>
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link href="/static/bootstrap/css/fileinput.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link href="static/bootStrapPager/css/page.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/static/incomeCash/css/income-cash.css">
<link rel="stylesheet" type="text/css" href="/static/income/hedge.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="user-table-template" type="text/x-handlebars-template">
{{#if content}}
	{{#each content}}
     			<tr>
							<td>{{regex rowind }}</td>
							<td>{{truename}}</td>
							<td>{{orderno}}</td>
							<td>{{goodsName}}</td>
							<td>{{sku}}</td>
							<td>{{uniquenumber}}</td>
							<td>{{sum}}</td>
							<td>{{namepath}}</td>
					</tr>
	{{/each}}
{{/if}}

</script>
</head>
<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-cj"></i>售后冲减表
			<!--区域选择按钮-->

			<a href="javascript:history.back();"><i
				class="ico icon-back fl-right"></i></a>

		</h4>
		<div class="row text-time">
			<div class="salesman">
				<input id="searchDate" type="text"
					class="form-control form_datetime input-sm" placeholder="选择日期"
					readonly="readonly" style="background: #ffffff;">
			</div>

			<button class="btn btn-blue btn-sm" onclick="findPlanUserList(0);"
				style="margin-left: 20px">检索</button>



			<div class="link-posit-t pull-right export">
				<input id="orderNo" class="cs-select  text-gery-hs" placeholder="  订单号">
				<button class="btn btn-blue btn-sm" onclick="findPlanUserList(1);">检索</button>
				<a class="table-export" href="javascript:void(0);"
					data-toggle="modal" data-target="#daoru">导入excel</a>
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
								<th>序号</th>
								<th>所属业务员</th>
								<th>订单号</th>
								<th>退货产品</th>
								<th>sku编码</th>
								<th>唯一码</th>
								<th>数量</th>
								<th>区域属性</th>
							</tr>
						</thead>
						<tbody id="userList">

						</tbody>
					</table>
				</div>
				<!--table-box-->
			</div>
			<!--待审核账单-->
		</div>
		<div id="usersPager" class="wait-page-index"></div>
		<!--table-box-->
		<!--油补记录-->
	</div>
	<div id="daoru" class="modal fade bs-example-modal-lg" role="dialog">
		<div class="modal-dialog fang modal-lg" role="document">
			<div class="modal-content modal-blue yuan ">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">导入表格</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form enctype="multipart/form-data" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">选择文件：</label>
								<div class="col-sm-10">
									<input id="file-input" name="file" type="file" class="">
								</div>
							</div>

							<div class="form-group" id="uploadFileDiv">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a  id="uploadFile" onclick=""
										class="Zdy_add  col-sm-12 btn btn-primary">上传文件 </a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="/static/bootstrap/js/fileinput.js"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/fileinput_locale_zh.js"></script>
	<script src="static/js/jqueryfileupload/js/vendor/jquery.ui.widget.js"></script>
	<script src="static/js/jqueryfileupload/js/jquery.fileupload.js"></script>
	<script src="/static/js/jqueryfileupload/js/jquery.iframe-transport.js"></script>
	<script src="/static/js/common.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script src="/static/income/main/hedge.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript">
		$(function() {
			initDateInput();
			initFileUpload();
			findPlanUserList();
		});
	</script>

</body>
</html>