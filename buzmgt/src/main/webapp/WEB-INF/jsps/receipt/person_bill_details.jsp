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
	<title>个人对账单详情</title>

	<link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../static/css/common.css">
	<link rel="stylesheet" type="text/css" href="../static/receipt/order_detail.css"/>
	<link href="../static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
	<link rel="stylesheet" href="../static/multiselect/css/jquery.multiselect.css">
	<link rel="stylesheet" href="../phone-set/css/phone.css">


	<style>

		.new-li {
			padding-top: 28px;
			border-bottom: 0px dashed #dedede;
		}


		.dl-horizontal dt {
			float: left;
			width: 160px;
			overflow: hidden;
			clear: left;
			text-align: right;
			text-overflow: ellipsis;
			white-space: nowrap;
			margin-top: 8px;
		}


		.icon-dzd{
			background: url("img/dzd.png") no-repeat center;
		}
		.text-pronce{
			line-height: 30px;
		}
		.text-bg{
			padding: 5px;
			background: #f2f2f2;
			margin-right: 25px;
			width: 200px;
		}

		.text-bg-sk{
			padding: 5px;
			background: #f2f2f2;
			margin-right: 25px;
			width: 300px;
			margin-top: 10px;
		}

		.order-box-new {
			margin: 15px 10px;
			min-height: 680px;
			background-color: #fff;
			-webkit-text-size-adjust: none;
		}

		.dz-blue{
			font-size: 12px;
			font-weight: bold;
			color: #2096da;
		}

		.dz-grey{
			color: #b0afaf;
		}


		.pt-green{
			color:#02ae88 ;
		}
	</style>

</head>
<body>

<div class="content main">
	<h4 class="page-header">
		<i class="ico icon-dzd"></i>个人对账单详情
		<a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
	</h4>


	<div class="row">
		<!--col begin-->
		<div class="col-md-12">
			<!--orderbox begin-->
			<div class="order-box-new">
				<ul>
					<li class="new-li">
						<dl class="dl-horizontal">
							<dt><span class="dz-grey">商家名称：</span></dt>
							<dd>
								<span class="text-pronce dz-blue ">${order.shopName}</span>
							</dd>
						</dl>
					</li>

					<li class="new-li">
						<dl class="dl-horizontal">
							<dt><span class="dz-grey">地址：</span></dt>
							<dd>
								<span class="text-pronce"> ${order.address} </span>
							</dd>
						</dl>
					</li>

					<li class="new-li">
						<dl class="dl-horizontal">
							<dt><span class="dz-grey">联系电话：</span></dt>
							<dd>
								<span class="text-pronce">${order.mobile}</span>
							</dd>
						</dl>
					</li>

					<li class="new-li">
						<dl class="dl-horizontal">
							<dt><span class="dz-grey">签收日期：</span></dt>
							<dd>
								<span class="text-pronce">${orderSignfor.customSignforTime}</span>
							</dd>
						</dl>
					</li>


					<li class="new-li">
						<dl class="dl-horizontal">
							<dt><span class="dz-grey">订单编号：</span></dt>
							<dd>
								<span class="text-pronce ph-text-blue">${order.orderNum}</span>
							</dd>
						</dl>
					</li>
					<li class="new-li">
						<dl class="dl-horizontal">
							<dt><span class="dz-grey">订单详情：</span></dt>
							<dd>
								<div  class="text-bg fl"> <span class="text-pronce ">华为 华为畅享5S（限拍5台）x1</span></div>
								<div  class="text-bg fl"> <span class="text-pronce ">华为 华为畅享5S（限拍5台）x1</span></div>
								<div  class="text-bg fl"> <span class="text-pronce ">华为 华为畅享5S（限拍5台）x1</span></div>

							</dd>
						</dl>
					</li>


					<li class="new-li">
						<dl class="dl-horizontal">
							<dt><span class="dz-grey">订单合计：</span></dt>
							<dd>
								<span class="text-pronce ">手机 <span class="pt-green">${phoneCount}</span> 部   &nbsp; 配件 <span class="pt-green">${accessoryCount}</span> 件</span>
							</dd>
						</dl>
					</li>

					<li class="new-li">
						<dl class="dl-horizontal">
							<dt><span class="dz-grey">订单总价：</span></dt>
							<dd>
								<span class="text-pronce ph-text-blue">${order.actualPayNum}</span>
							</dd>
						</dl>
					</li>

					<li class="new-li">
						<dl class="dl-horizontal">
							<dt><span class="dz-grey">待付金额：</span></dt>
							<dd>
								<span class="text-pronce text-red">${orderSignfor.arrears}元</span>
							</dd>
						</dl>
					</li>


					<li class="new-li">
						<dl class="dl-horizontal">
							<dt style="margin-top:20px"><span class="dz-grey ">收款历史：</span></dt>
							<dd>
								<div  class="text-bg-sk fl"> <span class="text-pronce ">主账户收款 1000.00元   &nbsp;&nbsp;2016.09.18  08:30</span></div>
								<div  class="text-bg-sk fl"> <span class="text-pronce ">主账户收款 1000.00元   &nbsp;&nbsp;2016.09.18  08:30</span></div>
								<div  class="text-bg-sk fl"> <span class="text-pronce ">主账户收款 1000.00元   &nbsp;&nbsp;2016.09.18  08:30</span></div>
								<div  class="text-bg-sk fl"> <span class="text-pronce ">主账户收款 1000.00元   &nbsp;&nbsp;2016.09.18  08:30</span></div>
								<div  class="text-bg-sk fl"> <span class="text-pronce ">主账户收款 1000.00元   &nbsp;&nbsp;2016.09.18  08:30</span></div>
							</dd>
						</dl>
					</li>
				</ul>

			</div>
		</div>
	</div>
</div>
<script src="../static/bootstrap/js/bootstrap.js"></script>
</body>
</html>

