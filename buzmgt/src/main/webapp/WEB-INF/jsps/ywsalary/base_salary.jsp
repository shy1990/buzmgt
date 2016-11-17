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
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/ticheng/css/income-cash.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<link href="/static/customTask/searchSelect/css/jquery.multiselect.css"
	type="text/css" rel="stylesheet" />
	<link rel="stylesheet" type="text/css"
	href="/static/income/baicsalary.css">
<script id="baseSalary-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
	<tr>			
      <td>{{userName}}</td>
      <td>{{region}}</td>
			
      <td><span class="text-blue">{{salary}}</span></td>
      <td>{{newdate}}</td>
	  <td><span class="text-green">{{daySalary}}元/天</span></td>
	  <td>{{state}}</td>
     	{{#isvalid state}}
      		<td>
        	<button class="xiugai  btn btn-blue btn-bluee"
					onclick="openModify('{{id}}','{{salary}}','{{newdate}}');" >修改</button>
      		</td>
    	{{else}}
			<td>
	    	</td>
		{{/isvalid}}
    </tr> 
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>

</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-jcxz"></i>基础薪资表

			<!--/区域选择按钮-->
			<a href="" class="btn btn-blue" data-toggle="modal"
				data-target="#addModal" data-whatever="@mdo"> <i
				class="ico icon-add"></i>基础薪资
			</a>

		</h4>
		<div class="row text-time" id="searchDiv">
			<!--区域选择按钮-->
			<!--区域选择按钮-->
			<div class="area-choose">
				<%--<%@ include file="../region/regionProvinceSelect.jsp"%> --%>
				<select class="ph-select" id="region">
					<option value="">选择省区</option>
					<c:forEach var="region" items="${regions}">
						<option value="${region.name}">${region.name}</option>
					</c:forEach>
				</select>
				<button class="btn btn-blue btn-sm" onclick="goRegionSearch();">区域检索</button>
			</div>
			<!--区域选择按钮-->
			<div class="link-posit-t pull-right export">
				<input id="truename" class="cs-select  text-gery-hs"
					placeholder="  请输入业务员名称">
				<button class="btn btn-blue btn-sm" onclick="goNameSearch();">检索</button>
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
								<th>新增日期</th>
								<th>日薪资</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="table-list">
						</tbody>
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
						<div class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">待设置业务：</label>
								<div class="col-sm-7">
									<div class=" input-search"
										style="margin-left: 5px; height: 30px">
										<div class="input-group ">
											<span class="input-group-addon"><i
												class="icon-s icon-man"></i></span>

											<div class="inpt-search">
												<form>
													<select name="basic[]" multiple="multiple"
														class="form-control demo3" style="padding: 0px">
														<c:forEach var="user" items="${salaryUsers}">
															<option value="${user.userId }">${user.truename}</option>
														</c:forEach>
													</select>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">基础薪资：</label>
								<div class="col-sm-7">
									<div class="input-group are-line" style="margin-left: 5px;">
										<span class="input-group-addon"><i class="icon icon-xz"></i></span>
										<input id="salary" type="number" 
											class="form-control input-h" style="width: 200px;font-size:16px" aria-describedby="basic-addon1">
									</div>
									<div class="text-strong"
										style="float: right; margin-top: -20px">元</div>
								</div>

							</div>
							<div class="form-group">
								<label class="col-sm-4 control-label">添加日期：</label>
								<div class="col-sm-7">
									<div class=" input-search"
										style="margin-left: 5px; height: 30px">
										<div class="input-group ">
											<span class="input-group-addon " id="basic-addon1"><i
												class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
											<input id="newdate" type="text"
												class="form-control form_datetime input-sm" style="font-size:16px"
												placeholder="年-月-日" readonly="readonly" />
										</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a onclick="addSalary()"
										class="Zdy_add  col-sm-12 btn btn-primary">保存 </a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!---alert新增业务--->
	</div>


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
						<div class="form-horizontal">

							<div class="form-group">
								<label class="col-sm-4 control-label">基础薪资：</label>
								<div class="col-sm-7">
									<div class=" input-search"
										style="margin-left: 5px; height: 30px">
										<div class="input-group ">
											<span class="input-group-addon "><i
												class="icon-s icon-qian"></i></span>

											<div class="inpt-search">
												<input type="number" min="0" id="updSalary"
													placeholder="请输入基础薪资" class="form-control" />
											</div>

										</div>
										<div class="a-yuan">元</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label">生效日期：</label>
								<div class="col-sm-7">
									<div class=" input-search"
										style="margin-left: 5px; height: 30px">
										<div class="input-group ">
											<span class="input-group-addon "><i
												class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
											<input type="text" id="updateTime"
												class="form-control form_datetime input-sm"
												placeholder="选择日期" readonly="readonly" />
										</div>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a onclick="updateSalary();" class="col-sm-12 btn btn-primary">保存
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!---alert修改--->

	<!---alert---->
	<script type="text/javascript">
		var	base='<%=basePath%>';
		var salesId = '${salesId}';
		var month='${month}';
		var SearchData = {
			'page' : '0',
			'size' : '20',
			"sort" : 'flag'
		}
	</script>
	<script type="text/javascript" src="static/js/common.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap.min.js"></script>
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
	<script type="text/javascript"
		src="/static/multiselect/js/jquery.multiselect.js"></script>

	<script type="text/javascript" src="/static/customTask/add.js"></script>
	<script src="/static/bootstrap/js/bootstrap-select.min.js"></script>
	<script type="text/javascript" src="/static/ticheng/js/base-salary.js"
		charset="utf-8"></script>
</body>

</html>