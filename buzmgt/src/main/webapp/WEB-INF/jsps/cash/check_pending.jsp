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
<title>待审核账单</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/incomeCash/css/income-cash.css">
<link rel="stylesheet" type="text/css"
	href="static/incomeCash/css/check-pending.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="checkPending-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		<tr>
      <td>{{userId}}</td>
      <td class="border-right-grey">{{cardName}}</td>
      <td class="multi-row">
				{{#each bankTrades}}
        <p>{{cardNo}}</p>
				{{/each}}	
      </td>
      <td class="border-right-grey multi-row">
				{{#each bankTrades}}
        <p>{{money}}</p>
				{{/each}}	
      </td>
      <td class="multi-row width-fixed multi-row-p">
				{{#each cashs}}
        <p> <span>{{serialNo}}</span> <a href="/waterOrder/show?serialNo={{serialNo}}" class="btn btn-sm btn-findup">查看</a> </p>
				{{/each}}
      </td>
      <td class="border-right-grey multi-row multi-row-p">
				{{#each cashs}}
        <p>{{cashMoney}}</p>
				{{/each}}
      </td>
      <td>{{cashMoney}}</td>
      <td>{{debtMoney}}</td>
      <td>{{shouldPayMoney}}</td>
      <td>{{incomeMoney}}</td>
      <td>{{{disposeStayMoney stayMoney}}}</td>
      <td>{{formDate createDate}}</td>
      <td>
				{{!--是否已审核--}}
				{{{isCheckStatus isCheck userId createDate}}}
      </td>
    </tr>
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
<script id="unCheck-table-template" type="text/x-handlebars-template">
		<tr>
			<td colspan="100" class="single-exception">请先处理未匹配交易记录，请不要先审核</td>
		</tr>
	{{#each content}}
		<tr>
      <td><span class="single-exception"> Error!</span></td>
      <td class="border-right-grey">{{cardName}}</td>
      <td class="multi-row">
        <p>{{cardNo}}</p>
      </td>
      <td class="border-right-grey multi-row">
        <p>{{money}}</p>
      </td>
      <td class="multi-row width-fixed multi-row-p">
				<p><span class="single-exception">匹配失败</span></p>
      </td>
      <td class="border-right-grey multi-row multi-row-p">
				<p><span class="single-exception">匹配失败</span></p>
      </td>
      <td>----</td>
      <td>----</td>
      <td>----</td>
      <td>{{money}}</td>
      <td>----</td>
      <td>{{payDate}}</td>
      <td>
				<button onclick="deleteUnCheck({{id}})" class="btn btn-sm wait-btn-delete">删除</button>
      </td>
    </tr>
	{{/each}}
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
				href="/punish/punishs"><i class="icon-wait-setting"></i>设置</a>
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
				onclick="goSearch();">检索</button>
			<!---->
			<div class="pull-right exc-hh">
				<input class="cs-select salesman-input" id="salesManName" placeholder="请输入业务员名称">
				<button class="btn btn-blue btn-sm check-btn"
					onclick="findBySalesManName();">检索</button>
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
								<th>收现总额</th>
								<th>昨日累加</th>
								<th>业务应付</th>
								<th>业务实付</th>
								<th>业务待付</th>
								<th>操作日期</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="unCheckList"> </tbody>
						<tbody id="checkPendingList"> </tbody>
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
					<h3 class="modal-title">文件归档<small style="color: #fff">(归档后数据不能再次修改)</small></h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form id="addd" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">归档日期：</label>
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
									<a herf="javascript:;" onclick="archiving()" class="col-sm-12 btn gd-btn-sure"
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
		src="static/incomeCash/js/chech-pending.js" charset="utf-8"></script>
	<script type="text/javascript">
		
	</script>
</body>

</html>