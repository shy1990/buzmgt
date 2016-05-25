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
<title>银行导出数据</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/income-cash/css/income-cash.css">
<link rel="stylesheet" type="text/css"
	href="static/income-cash/css/check-pending.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="bankTrade-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		<tr>
      <td>{{cardName}}</td>
      <td>{{cardNo}}</td>
      <td>{{money}}</td>
      <td>{{formDate payDate}}</td>
      <td>{{bankName}}</td>
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
		<h4 class="page-header">
			<i class="ico icon-puish-back"></i>待审核账单 <a class="btn wait-setting"
				href="javascript:;"><i class="icon-wait-setting"></i>设置</a>
			<button class="btn btn-blue" type="button" data-toggle="modal"
				data-target="#guidang">数据归档</button>
		</h4>
		<!---选择日期-->
		<div class="text-time">
			<!--考核开始时间-->
			<span class="text-strong chang-time ">选择日期：</span>
			<div class="search-date">
				<div class="input-group input-group-sm">
					<span class="input-group-addon " id="basic-addon1"><i
						class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
						type="text" id="searchDate" class="form-control form_datetime input-sm"
						placeholder="选择日期" readonly="readonly">
				</div>
			</div>
			<button class="btn btn-blue btn-sm"
				onclick="goSearch('${salesman.id}','${assess.id}');">检索</button>
			<!---->
			<div class="pull-right exc-hh">
				<input class="cs-select salesman-input" placeholder="请输入业务员名称">
				<button class="btn btn-blue btn-sm check-btn"
					onclick="goSearch('${salesman.id}','${assess.id}');">检索</button>
				<a class="table-export" href="javascript:void(0);">导出excel</a>
			</div>
		</div>
		<!---选择日期-->
		<div class="clearfix"></div>
		<div class="tab-content">
			<!--待审核账单-->
			<div class="tab-pane fade in active" id="box_tab1">
				<!--table-box-->
				<div class="table-task-list new-table-box table-overflow">
					<table class="table table-hover new-table">
						<thead>
							<tr>
								<th>业务ID</th>
								<th>姓名</th>
								<th>付款卡号</th>
								<th>打款金额</th>
								<th>流水单号</th>
								<th>当日收现</th>
								<th>昨日累加</th>
								<th>业务应付</th>
								<th>业务实付</th>
								<th>业务待付</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="">
							<tr class="status-exception">
								<td>Error!</td>
								<td class="border-right-grey">李光洙</td>
								<td>6222****4184</td>
								<td class="border-right-grey">2500.00</td>
								<td>无数据</td>
								<td class="border-right-grey">0.00</td>
								<td>0.00</td>
								<td>0.00</td>
								<td>0.00</td>
								<td>0.00</td>
								<td>
									<button class="btn btn-sm wait-btn-delete">删除</button>
								</td>
							</tr>
							<tr class="status-exception">
								<td>Error!</td>
								<td class="border-right-grey">李光洙</td>
								<td>6222****4184</td>
								<td class="border-right-grey">2500.00</td>
								<td>无数据</td>
								<td class="border-right-grey">0.00</td>
								<td>0.00</td>
								<td>0.00</td>
								<td>0.00</td>
								<td>0.00</td>
								<td>
									<button class="btn btn-sm wait-btn-delete">删除</button>
								</td>
							</tr>
							<tr>
								<td>28458965</td>
								<td class="border-right-grey">李光洙</td>
								<td>6222****4184</td>
								<td class="border-right-grey">2500.00</td>
								<td><span>20160507154200704</span> <a href="javascript:;"
									class="btn btn-sm btn-findup">查看</a></td>
								<td class="border-right-grey">8000.00</td>
								<td>9000.00</td>
								<td>5000.00</td>
								<td>6500.00</td>
								<td>0.00</td>
								<td>
									<button class="btn btn-sm btn-blue">确认</button>
								</td>
							</tr>
							<tr>
								<td>28458965</td>
								<td class="border-right-grey">李光洙</td>
								<td class="multi-row">
									<p>6222****4184</p>
									<p>6422****4167</p>
								</td>
								<td class="border-right-grey multi-row">
									<p>2500.00</p>
									<p>2500.00</p>
								</td>
								<td class="multi-row width-fixed">
									<p class="border-bottom-grey">
										<span>20160507154200704</span> <a href="javascript:;"
											class="btn btn-sm btn-findup">查看</a>
									</p>
									<p>
										<span>20160507154200704</span> <a href="javascript:;"
											class="btn btn-sm btn-findup">查看</a>
									</p>
								</td>
								<td class="border-right-grey multi-row">
									<p class="border-bottom-grey">8000.00</p>
									<p>8000.00</p>
								</td>
								<td>9000.00</td>
								<td>5000.00</td>
								<td>6500.00</td>
								<td class="single-exception">200.00</td>
								<td>
									<button class="btn btn-sm btn-blue">确认</button>
								</td>
							</tr>
							<tr>
								<td>28458965</td>
								<td class="border-right-grey">李光洙</td>
								<td class="multi-row">
									<p>6422****4167</p>
									<p>6422****4167</p>
									<p>6422****4167</p>
								</td>
								<td class="border-right-grey multi-row">
									<p>2500.00</p>
									<p>2500.00</p>
									<p>2500.00</p>
								</td>
								<td class="multi-row width-fixed">
									<p class="border-bottom-grey">
										<span>20160507154200704</span> <a href="javascript:;"
											class="btn btn-sm btn-findup">查看</a>
									</p>
									<p class="border-bottom-grey">
										<span class="single-exception">20160507154200704</span> <a
											href="javascript:;" class="btn btn-sm btn-findup">查看</a>
									</p>
									<p class="border-bottom-grey">
										<span>20160507154200704</span> <a href="javascript:;"
											class="btn btn-sm btn-findup">查看</a>
									</p>
									<p>
										<span>20160507154200704</span> <a href="javascript:;"
											class="btn btn-sm btn-findup">查看</a>
									</p>
								</td>
								<td class="border-right-grey multi-row">
									<p class="border-bottom-grey">8000.00</p>
									<p class="border-bottom-grey">8000.00</p>
									<p class="border-bottom-grey">8000.00</p>
									<p>8000.00</p>
								</td>
								<td>9000.00</td>
								<td>5000.00</td>
								<td>6500.00</td>
								<td>0.00</td>
								<td>
									<button class="btn btn-sm btn-blue">确认</button>
								</td>
							</tr>
							<tr>
								<td>28458965</td>
								<td class="border-right-grey">李光洙</td>
								<td class="multi-row">
									<p>6222****4184</p>
									<p>6422****4167</p>
								</td>
								<td class="border-right-grey multi-row">
									<p>2500.00</p>
									<p>2500.00</p>
								</td>
								<td class="multi-row width-fixed">
									<p class="border-bottom-grey">
										<span>20160507154200704</span> <a href="javascript:;"
											class="btn btn-sm btn-findup">查看</a>
									</p>
									<p>
										<span>20160507154200704</span> <a href="javascript:;"
											class="btn btn-sm btn-findup">查看</a>
									</p>
								</td>
								<td class="border-right-grey multi-row">
									<p class="border-bottom-grey">8000.00</p>
									<p>8000.00</p>
								</td>
								<td>9000.00</td>
								<td>5000.00</td>
								<td>6500.00</td>
								<td>200.00</td>
								<td>
									<button class="btn btn-sm btn-blue">确认</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<!--table-box-->
				<div id="checkCashPager"></div>
			</div>
			<!--待审核账单-->
		</div>
	</div>
	<!--alert-->
	<div id="guidang" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">文件归档</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form id="addd" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">归档至：</label>
								<div class="col-sm-7 dialog-calender">
									<div class="search-date">
										<div class="input-group input-group-sm">
											<span class="input-group-addon " id="basic-addon1"> <i
												class=" glyphicon glyphicon-remove glyphicon-calendar"></i>
											</span> <input type="text" id="archivingDate"
												class="form-control form_datetime input-sm"
												placeholder="年-月-日" readonly="readonly">
										</div>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4">
									<a herf="javascript:;" class="col-sm-12 btn gd-btn-sure"
										data-dismiss="modal">确定</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--alert-->

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
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="static/js/dateutil.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript"
		src="static/income-cash/js/chech-pending.js" charset="utf-8"></script>
	<script type="text/javascript">
		
	</script>
</body>

</html>