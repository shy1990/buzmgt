<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>基础薪资表</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/fileinput.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/ticheng/css/income-cash.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="baseSalary-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		<tr>
			{{#with user}}
      <td>{{truename}}</td>
      <td>{{region.name}}</td>
			{{/with}}
      <td>{{salary}}</td>
      <td>{{updateDate}}</td>
      <td>
        <button class="xiugai  btn btn-blue btn-bluee"
					 data-toggle="modal" data-target="#updModal" data-whatever="{{id}}" data-salary="{{salary}}">修改</button>
        <button class="btn btn-warning btn-bluee" data-toggle="modal"
					 data-target="#delModal" data-id="{{id}}" data-salary="{{salary}}",data-truename="{{user.truename}}">删除</button>
      </td>
    </tr> 
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
<script type="text/javascript">
var	base='<%=basePath%>';
var SearchData = {
	'page' : '0',
	'size' : '10'
}	
</script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-jcxz"></i>基础薪资表
			<!--区域选择按钮-->
			<div class="area-choose"></div>
			<!--/区域选择按钮-->
			<a href="" class="btn btn-blue" data-toggle="modal"
				data-target="#addModal" data-whatever="@mdo"> <i
				class="ico icon-add"></i>基础薪资
			</a>
			<!--区域选择按钮-->
        <div class="area-choose">
            选择区域：<span>${regionName }</span>
            <a class="are-line" onclick="getRegion(${regionId});" href="javascript:;">切换</a>
           	<input type="hidden" id="regionId" value="${regionId }">
           	<input type="hidden" id="regionType" value="${regionType }">
        </div>
        <!--/区域选择按钮-->
		</h4>
		<div class="row text-time">

			<!-- <div class="salesman" style="margin-top: 5px">
				<input class="cs-select  text-gery-hs" placeholder="  请选择区域">
				<button class="btn btn-blue btn-sm"
					onclick="">检索</button>
			</div> -->

			<div class="link-posit-t pull-right export">
				<input id="truename" class="cs-select  text-gery-hs" placeholder="  请输入业务员名称">
				<button class="btn btn-blue btn-sm"
					onclick="goSearch();">检索</button>
				<a id="table-export" class="table-export" href="javascript:void(0);">导出excel</a>
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
								<th>姓名</th>
								<th>所属区域</th>
								<th>基础薪资</th>
								<th>日期</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="table-list"> </tbody>
					</table>
				</div>
				<!--table-box-->
				<div id="initPage"></div>
			</div>
			<!--待审核账单-->
		</div>
		<!--table-box-->
		<!--油补记录-->
	</div>
	
	<!---alert删除--->
	<div id="delModal" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">是否删除</h3>
				</div>

				<div class="modal-body">
					<div class="container-fluid">
						<form class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label text-dk">姓 &nbsp;
									&nbsp; 名：</label>
								<div class="col-sm-8">
									<p id="delName" class="form-control-static text-bg">胡 老 大</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label text-dk">基础薪资：</label>
								<div class="col-sm-8">
									<p id="delSalary" class="form-control-static text-bg"></p>
								</div>
							</div>
							<input id="delId" type="hidden">
							<div class="btn-qx">
								<button type="button" onclick="deleteSalary();" class="btn btn-danger btn-d">删除</button>
							</div>

							<div class="btn-dd">
								<button type="button" data-dismiss="modal"
									class="btn btn-primary btn-d">取消</button>
							</div>

						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!---alert删除--->

	<!---alert新增--->
	<div id="addModal" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">设置业务基础薪资</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form id="addForm" class="form-horizontal" onsubmit="return false">
							<div class="form-group">
								<label class="col-sm-4 control-label">待设置业务：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i class="icon icon-sz"></i></span>
										<select name="userId" class="form-control input-h"
											aria-describedby="basic-addon1">
											<c:forEach var="user" items="${salaryUsers }">
												<option value="${user.userId }">${user.truename}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label">基础薪资：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i class="icon icon-xz"></i></span>
										<input name="salary" type="text" style="width: 200px"
											class="form-control input-h" aria-describedby="basic-addon1">
									</div>
									<div class="text-strong"
										style="float: right; margin-top: -20px">元</div>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a herf="javascript:return 0;" onclick="addSalary()"
										class="Zdy_add  col-sm-12 btn btn-primary">保存 </a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!---alert新增业务--->



	<!---alert修改--->
	<div id="updModal" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">修改</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form id="updateForm" class="form-horizontal">
							<input id="updateId" type="hidden" name="id">
							<div class="form-group">
								<label class="col-sm-4 control-label">基础薪资：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i
											class="icon icon-jcxz"></i></span> 
											<input id="updSalary" name="salary" type="text" class="form-control input-h" aria-describedby="basic-addon1">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a herf="javascript:return 0;" onclick="updateSalary()"
										class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!---alert修改--->
	</div>

	<!---alert---->

	<!-- Bootstrap core JavaScript================================================== -->
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<script type="text/javascript" src="static/js/common.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/static/bootstrap/js/fileinput.js"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/fileinput_locale_zh.js"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="/static/js/dateutil.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script src="static/js/jqueryfileupload/js/vendor/jquery.ui.widget.js"></script>
	<script src="static/js/jqueryfileupload/js/jquery.iframe-transport.js"></script>
	<script src="static/js/jqueryfileupload/js/jquery.fileupload.js"></script>
	<script type="text/javascript"
		src="static/ticheng/js/base-salary.js" charset="utf-8"></script>
	<script type="text/javascript">
		
	</script>
</body>

</html>