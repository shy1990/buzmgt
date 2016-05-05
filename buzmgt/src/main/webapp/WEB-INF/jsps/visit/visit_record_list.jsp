<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>拜访记录</title>
<!-- Bootstrap -->
<link href="<%=basePath%>static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="<%=basePath%>static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>static/abnormal/abnormal.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/visit/visit.css" />
<link href="<%=basePath%>static/bootStrapPager/css/page.css" rel="stylesheet" />
<script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var base = "<%=basePath%>";
	var number = '';//当前页数（从零开始）
	var totalPages = '';//总页数(个数)
	var searchData = {
		"size" : "2",
		"page" : "0",
	}
	var totalElements;//总条数
</script>
<script id="table-template" type="text/x-handlebars-template">
{{#each content}}
	<tr>
		<td class=""><img width="50" height="50"
		src="static/img/abnormal/user-head.png" class="img-circle" />
		<div class="business-name">
		<span class="text-strong">{{name}}</span>({{roleName}}) <br /> {{area}}</div></td>
		<td><span class="text-bule">{{visitTimes}} </span>次</td>
		<td>{{overTimes}} 次</td>
		<td>{{lastVisit}}</td>
		<td><a class="btn btn-blue btn-sm" href="javascript:;" onclick="check('{{userId}}');">查看</a></td>
	</tr>
{{else}}
<div style="text-align: center;">
	<tr style="text-align: center;">没有相关数据!</tr>
</div>
{{/each}}
</script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-visit-record"></i>拜访记录
			<!--区域选择按钮-->
			<div class="area-choose" id="area" data-a="${regionId}">
				选择区域：<span>${regionName}</span> <a class="are-line" href="javascript:;"
					onclick="getRegionChoose(${regionId});">切换</a>
				<%-- <input id="area" type="hidden" value="${regionId}"> --%>
			</div>
			<!--/区域选择按钮-->
		</h4>
		<div class="row">
			<div class="col-md-12">
				<!--box-->
				<div class="abnormal-body box border blue">
					<!--title-->
					<div class="box-title">拜访列表</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<div class="marg-t text-time">
							<span class="text-strong chang-time">请选择时间：</span>
							<div class="search-date">
								<div class="input-group input-group-sm date form_date_start">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" class="form-control form_datetime input-sm"
										placeholder="开始日期" readonly="readonly" id="beginTime">
								</div>
							</div>
							--
							<div class="search-date">
								<div class="input-group input-group-sm date form_date_end">
									<span class="input-group-addon " id="basic-addon1"><i
										class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
										type="text" class="form-control form_datetime input-sm"
										placeholder="结束日期" readonly="readonly" id="endTime">
								</div>
							</div>
							<!--考核开始时间-->
							<button class="btn btn-blue btn-sm" onclick="goSearch();">检索</button>
							<!---->
							<div class="abnormal-details">
								<span>共 <span class="text-bule" id="totalElements"></span> 次拜访
								</span> <!-- <span>平均拜访周期 ：<span class="text-bule">1</span> 天<span
									class="text-bule">6</span> 小时<span class="text-bule">26</span>
									分/次
								</span> -->
							</div>
							<div class="link-posit pull-right">
								<a class="table-export" href="javascript:void(0);">导出excel</a>
							</div>
						</div>
						<!--列表内容-->
						<div class="tab-content">
							<!--拜访记录-->
							<div class="tab-pane fade in active" id="box_tab1">
								<!--table-box-->
								<div class="table-abnormal-list table-overflow">
									<table class="table table-hover new-table abnormal-table">
										<thead>
											<tr>
												<th>业务名称</th>
												<th>拜访次数</th>
												<th>超时次数</th>
												<th>上次拜访时间</th>
												<th>操作</th>
											</tr>
										</thead>
										<tbody id="tableList">
							
										</tbody>
									</table>
									<div id="callBackPager"></div>
								</div>
								<!--table-box-->
							</div>
							<!--拜访记录-->
						</div>
						<!--列表内容-->
					</div>
					<!--box-body-->
				</div>
				<!--box-->
			</div>
			<!--col-md-12-->
		</div>
		<!--row-->
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
		<script src="<%=basePath%>static/visit/visit-record.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
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
			
			/*区域 */
			function getRegionChoose(id){
				window.location.href='/region/getPersonalRegion?id='+id+"&flag=visit";
			}
		</script>
</body>

</html>