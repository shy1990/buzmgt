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
<title>提成设置</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/phone-set/css/phone.css">
<link rel="stylesheet" type="text/css"
	href="static/phone-set/css/comminssion.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="checkPending-table-template"
	type="text/x-handlebars-template">
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
<script id="checkDebt-table-template" type="text/x-handlebars-template">
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
        <p>暂无</p>
      </td>
      <td class="border-right-grey multi-row multi-row-p">
        <p>暂无</p>
      </td>
      <td>{{cashMoney}}</td>
      <td>{{debtMoney}}</td>
      <td>{{shouldPayMoney}}</td>
      <td>{{incomeMoney}}</td>
      <td>{{{disposeStayMoney stayMoney}}}</td>
      <td>{{formDate createDate}}</td>
      <td>
				{{!--是否已审核--}}
				{{{isCheckDebtStatus isCheck userId createDate}}}
      </td>
    </tr>
	{{/each}}
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
var	base='<%=basePath%>
	';
	var SearchData = {
		'page' : '0',
		'size' : '20'
	}
</script>
</head>
<body>

	<div class="content main">
		<h4 class="page-header">
			<i class="ico ico-tcsz"></i>提成设置 <a href="javascript:history.back();"><i
				class="ico icon-back fl-right"></i></a>
		</h4>

		<ul class="nav nav-pills  nav-top" id="myTab">
			<li class="active"><a data-toggle="tab" href="#ajgqj">按价格区间</a></li>
			<li><a data-toggle="tab" href="#ppxhao">品牌型号<span
					class="qipao">2</span></a></li>
			<li><a data-toggle="tab" href="#dlsz">达量设置</a></li>
			<li><a data-toggle="tab" href="#djsz">叠加设置</a></li>
			<li><a data-toggle="tab" href="#dljl">达量奖励</a></li>
		</ul>


		<div class="row">
			<!--col begin-->
			<div class="col-md-12">
				<!--orderbox begin-->
				<div class="order-box">
					<!--左侧导航开始-->
					<div style="padding-left: 0">
						<div class=" sidebar left-side"
							style="padding-top: 0; margin-top: 5px">
							<ul class="nav nav-sidebar menu">
								<li><i class="ico ico-fl"></i>请选择类别</li>
								<li>智能机</li>
								<li>定制机</li>
								<li>功能机</li>
								<li>平板</li>
								<li>智能生活</li>
								<li>配件
							</ul>
						</div>
					</div>
					<!--左侧导航结束-->
					<div class="tab-content">
						<!--右侧内容开始-->
						<!--价格区间-->
						<div class="tab-pane fade in active right-body" id="ajgqj">
							<!--导航开始-->

							<div class="ph-btn-set">
								<a href="" class="btn ph-blue"> <i class="ico icon-xj"></i>
									<span class="text-gery">新建区间值</span>
								</a> <a href="" class="btn ph-blue" style="margin-right: 30px">
									<i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
								</a> <a href="" class="btn ph-blue"> <i class="ico ico-tj"></i>添加
								</a> <a href="" class="btn ph-blue"> <i class="ico ico-qj"></i>设置记录
								</a>
								<div class="clearfix">
									<div class="link-posit pull-right" style="margin-top: -25px">
										<a class="table-export" href="javascript:void(0);">导出excel</a>
									</div>
								</div>
							</div>


							<div class="table-task-list new-table-box table-overflow"
								style="margin-left: 20px">
								<table class="table table-hover new-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>价格区间</th>
											<th>提成金额</th>
											<th>开始日期</th>
											<th>区域属性</th>
											<th>状态</th>
											<th>设置日期</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>


										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>


										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

									</tbody>
								</table>
							</div>

						</div>


						<!--品牌型号-->
						<div class="tab-pane fade right-body" id="ppxhao">
							<!--导航开始-->

							<div class="ph-btn-set">
								<a href="" class="btn ph-blue"> <i class="ico icon-xj"></i>
									<span class="text-gery">添加</span>
								</a> <a href="" class="btn ph-blue" style="margin-right: 30px">
									<i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
								</a>

								<div class="link-posit pull-right">
									<input class="input-search" type="text"
										placeholder="模糊查询请输入品牌型号">
									<button class="btn  btn-sm bnt-ss ">搜索</button>
									<a class="table-export" href="javascript:void(0);">导出excel</a>
								</div>


							</div>


							<div class="table-task-list new-table-box table-overflow"
								style="margin-left: 20px">
								<table class="table table-hover new-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>品牌</th>
											<th>型号</th>
											<th>提成金额</th>
											<th>起止日期</th>
											<th>区域属性</th>
											<th>状态</th>
											<th>设置日期</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.08-22-2016.08.29</td>
											<td><a href=""><span class="text-blue">添加区域属性</span></a></td>
											<td><span class="ph-on">进行中</span></td>

											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.08-22-2016.08.29</td>
											<td><a href=""><span class="text-blue">添加区域属性</span></a></td>
											<td><span class="ph-on">进行中</span></td>

											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.08-22-2016.08.29</td>
											<td><a href=""><span class="text-blue">添加区域属性</span></a></td>
											<td><span class="ph-on">进行中</span></td>

											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.08-22-2016.08.29</td>
											<td><a href=""><span class="text-blue">添加区域属性</span></a></td>
											<td><span class="ph-on">进行中</span></td>

											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.08-22-2016.08.29</td>
											<td><a href=""><span class="text-blue">添加区域属性</span></a></td>
											<td><span class="ph-on">进行中</span></td>

											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>






									</tbody>
								</table>
							</div>

						</div>


						<!--达量设置-->
						<div class="tab-pane fade right-body" id="dlsz">


							<div class="ph-btn-set">
								<a href="" class="btn ph-blue"> <i class="ico icon-xj"></i>
									<span class="text-gery">添加</span>
								</a> <a href="" class="btn ph-blue" style="margin-right: 30px">
									<i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
								</a>

								<div class="link-posit pull-right">
									<input class="input-search" type="text"
										placeholder="模糊查询请输入品牌型号">
									<button class="btn  btn-sm bnt-ss ">搜索</button>
									<a class="table-export" href="javascript:void(0);">导出excel</a>
								</div>


							</div>


							<div class="table-task-list new-table-box table-overflow"
								style="margin-left: 20px">
								<table class="table table-hover new-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>品牌</th>
											<th>型号</th>
											<th>达量规则</th>
											<th>方案起止日期</th>
											<th>佣金发放</th>
											<th>状态</th>
											<th>设置日期</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><a href="">2016.08-22-2016.08.29</a></td>
											<td><span class="text-blue">2016.08.28</span></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-sc " data-toggle="modal"
													data-target="#">删除</button>
											</td>
										</tr>


										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><a href="">2016.08-22-2016.08.29</a></td>
											<td><span class="text-blue">2016.08.28</span></td>
											<td><span class="ph-weihes">未核算</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-sc " data-toggle="modal"
													data-target="#">删除</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><a href="">2016.08-22-2016.08.29</a></td>
											<td><span class="text-blue">2016.08.28</span></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-sc " data-toggle="modal"
													data-target="#">删除</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><a href="">2016.08-22-2016.08.29</a></td>
											<td><span class="text-blue">2016.08.28</span></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-sc " data-toggle="modal"
													data-target="#">删除</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>小米/xiaomi</td>
											<td>小米手机MAX</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><a href="">2016.08-22-2016.08.29</a></td>
											<td><span class="text-blue">2016.08.28</span></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-sc " data-toggle="modal"
													data-target="#">删除</button>
											</td>
										</tr>






									</tbody>
								</table>
							</div>

						</div>

						<!--叠加设置-->
						<div class="tab-pane fade  right-body" id="djsz">
							<!--导航开始-->

							<div class="ph-btn-set">
								<a href="" class="btn ph-blue"> <i class="ico icon-xj"></i>
									<span class="text-gery">添加</span>
								</a> <a href="" class="btn ph-blue" style="margin-right: 30px">
									<i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
								</a>

								<div class="link-posit pull-right">
									<input class="input-search" type="text"
										placeholder="模糊查询请输入品牌型号">
									<button class="btn  btn-sm bnt-ss ">搜索</button>
									<a class="table-export" href="javascript:void(0);">导出excel</a>
								</div>


							</div>

							<div class="table-task-list new-table-box table-overflow"
								style="margin-left: 20px">
								<table class="table table-hover new-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>名称</th>
											<th>指标</th>
											<th>方案起止日期</th>
											<th>佣金发放</th>
											<th>状态</th>
											<th>设置日期</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>01</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><span class="text-red">200台/300台/400台</span></td>
											<td>2016.07.16-2016.08.28</td>
											<td><span class="text-blue">2016.08.22</span></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>


										<tr>
											<td>01</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><span class="text-red">200台/300台/400台</span></td>
											<td>2016.07.16-2016.08.28</td>
											<td><span class="text-blue">2016.08.22</span></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><span class="text-red">200台/300台/400台</span></td>
											<td>2016.07.16-2016.08.28</td>
											<td><span class="text-blue">2016.08.22</span></td>
											<td><span class="ph-weihes">未核算</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>
										<tr>
											<td>01</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><span class="text-red">200台/300台/400台</span></td>
											<td>2016.07.16-2016.08.28</td>
											<td><span class="text-blue">2016.08.22</span></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td class="reason">你说要500台我给你600台你不开心你说要500台我给你600台你不开心</td>
											<td><span class="text-red">200台/300台/400台</span></td>
											<td>2016.07.16-2016.08.28</td>
											<td><span class="text-blue">2016.08.22</span></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn  bnt-sm bnt-ck" data-toggle="modal"
													data-target="#">查看</button>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>








									</tbody>
								</table>
							</div>

						</div>


						<!--达量奖励-->
						<div class="tab-pane fade right-body" id="dljl">
							<!--导航开始-->

							<div class="ph-btn-set">
								<a href="" class="btn ph-blue"> <i class="ico icon-xj"></i>
									<span class="text-gery">添加</span>
								</a> <a href="" class="btn ph-blue" style="margin-right: 30px">
									<i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
								</a>

								<div class="link-posit pull-right">
									<input class="input-search" type="text"
										placeholder="模糊查询请输入品牌型号">
									<button class="btn  btn-sm bnt-ss ">搜索</button>
									<a class="table-export" href="javascript:void(0);">导出excel</a>
								</div>


							</div>



							<div class="table-task-list new-table-box table-overflow"
								style="margin-left: 20px">
								<table class="table table-hover new-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>价格区间</th>
											<th>提成金额</th>
											<th>开始日期</th>
											<th>区域属性</th>
											<th>状态</th>
											<th>设置日期</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>


										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>


										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

										<tr>
											<td>01</td>
											<td>0-50元</td>
											<td class="width-fixed"><span class="text-green">5.00元/台</span>
												<a href="javascript:;" class="btn btn-sm btn-findup">修改</a>
											</td>
											<td>2016.07.16</td>
											<td><a href="">添加区域设置</a></td>
											<td><span class="ph-on">进行中</span></td>
											<td>2016.08.28</td>
											<td>
												<button class="btn btn-sm bnt-jc " data-toggle="modal"
													data-target="#">进程</button>
												<button class="btn btn-sm btn-zz " data-toggle="modal"
													data-target="#">终止</button>
											</td>
										</tr>

									</tbody>
								</table>
							</div>

						</div>

					</div>


				</div>


			</div>

		</div>


	</div>
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