<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>个人对账列表详情页</title>

	<link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="../static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../static/css/common.css">
	<link href="../static/bootstrap/css/bootstrap-datetimepicker.min.css"  rel="stylesheet">

	<link rel="stylesheet" type="text/css"  href="../static/bootStrapPager/css/page.css" />
	<script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	<style>


		.text-lv {
			color: #02ae88;
		}

		.text-zi {
			color: #ee038d;
		}

		.text-hong {
			color: #7943dc;
		}

		.text-lan {
			color: #1aaefb;
		}




		.icon-back {
			width: 26px;
			height: 26px;
			background: url("img/arrow.png") no-repeat center;
		}

		.icon-back:hover {
			width: 26px;
			background: url("img/Arrow 2 .png") no-repeat center;
		}

		.fl-right {
			float: right;
			margin-top: 25px;
			margin-right: 25px;
		}



		.link-posit-t {
			display: inline-block;
			text-align: right;
			vertical-align: bottom;
		}

		.new-table-box {
			border-top: 3px solid #1e92d4;
			min-height: 600px;
			background: #FFF;
		}

		.new-table-box > table > tbody > tr:hover {
			border-left: 3px solid #1e92d4;
		}

		.text-gery {
			color: #999999;
		}

		.ph-search-date > input {
			display: inline-block;
			width: auto;
			height: 30px;
		}

		.inform {
			height: 130px;
			background: #ffffff;
			padding-top: 25px;
		}

		.info-zq {
			height: 70px;
			width: 190px;
			margin-left: 40px;

			background: #fafafa;
			padding-left: 0px;
			box-shadow: 5px 5px 5px #ededed;
		}

		.fl {
			float: left;
		}

		.text-16 {
			font-size: 16px;
		}

		.font-w {
			line-height: 30px;
		}

		.text-jv {
			color: #d95413;
			font-weight: bolder;
		}

		.text-gren {
			color: #accea6;
			font-weight: bolder;
		}

		.icon-xq {
			background: url("img/grdzdd.png") no-repeat center;
		}

		.text-red {
			color: #ee3636;
		}


		.salesman {
			float: left;
			margin-left: 20px;
		}
	</style>


	<script id="bankTrade-table-template" type="text/x-handlebars-template">
		{{#if content}}
		{{#each content}}
		<tr>

			<td>{{orderNum}}</td>
			<td>{{shopName}}</td>
			<td>{{arrears}}</td>
			<td>{{payType orderPayStatus}}</td>
			<td>{{paybillStatus billStatus}}</td>
			<td>{{todayDate}}</td>
			<td>{{account isPrimaryAccount}}</td>
			<td>

				<button class="btn btn-green btn-sm btn-w" data-toggle="modal"
						data-target=""
						onclick="view('{{orderNum}}')">查看
				</button>
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
		<i class="ico icon-xq"></i>个人对账列表详情页
		<!--区域选择按钮-->
		<a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>

	</h4>
	<div class=" inform">
		<div class="row" s>
			<div class="col-sm-4 info-zq">
				<img src="img/pic1.png" alt="" class="fl" style=" margin-right: 15px;">
				<p class="text-gery text-strong font-w">当日应收总额</p>
				<p class="text-lv text-16 text-strong">${todayAllShouldPay}元</p>
			</div>
			<div class="col-sm-4 info-zq">
				<img src="img/pic-2.png" alt="" class="fl" style=" margin-right: 15px;">
				<p class=" text-strong text-gery  font-w ">当日待收</p>
				<p class="text-jv text-16">${todayShouldPay}元</p>
			</div>
			<div class="col-sm-4 info-zq">
				<img src="img/pic3.png" alt="" class="fl" style=" margin-right: 15px;">
				<p class="text-gery   font-w text-strong">历史拖欠金额</p>
				<p class="text-gren text-16">${historyShouldPay}元</p>

				<input type="hidden" id="userId" value="${userId}">
				<input type="hidden" id="todayDate" value="${todayDate}">
			</div>
		</div>

	</div>


	<br>

	<div class="row ">

		<div class="salesman">

			<%--<div class=" fl">

				<input type="text" class="form-control form_datetime  input-sm" id="orderPayDate" placeholder="选择日期"
					   readonly="readonly" style="background: #ffffff;margin: 0 10px 20px -10px;width: 220px ">

			</div>


			<button class="btn btn-blue btn-sm" onclick="goSearch('${salesman.id}','${assess.id}');">
				检索
			</button>--%>
		</div>

		<div class="link-posit-t pull-right export">
			<a class="table-export" href="javascript:void(0);">导出excel</a>
		</div>

	</div>

	<div class="tab-content ">
		<!--table-box-->

		<div class=" new-table-box table-overflow ">
			<table class="table table-hover new-table">
				<thead>
				<tr>
					<th>订单编号</th>
					<th>商家名称</th>
					<th>拖欠金额</th>
					<th>收款方式</th>
					<th>待收情况</th>
					<th>日期</th>
					<th>操作账号</th>
					<th>操作</th>
				</tr>
				</thead>
				<tbody id="orderPayList"></tbody>
			</table>
		</div>
		<!--table-box-->

		<!--待审核账单-->
	</div>

	<!--油补记录-->
</div>

<![endif]-->
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="/static/js/dateutil.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="../static/js/handlebars-v4.0.2.js" charset="utf-8"></script>
<script type="text/javascript" src="../static/bootStrapPager/js/extendPagination.js"></script>
<script src="../static/js/jqueryfileupload/js/vendor/jquery.ui.widget.js"></script>
<script src="../static/js/jqueryfileupload/js/jquery.iframe-transport.js"></script>
<script type="text/javascript" src="../static/receipt/person_bill_view.js" charset="utf-8"></script>
<script type="text/javascript">
	$(".form_datetime").datetimepicker({
		format: "yyyy-mm-dd",
		language: 'zh-CN',
		weekStart: 1,
		todayBtn: 1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		pickerPosition: "bottom-right",
		forceParse: 0
	});
</script>

</body>
</html>

