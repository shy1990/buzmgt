<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>提成设置</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/ticheng/css/income-cash.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="push-money-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		<tr>
      <td>{{cardName}}</td>
      <td>{{cardNo}}</td>
      <td>{{money}}</td>
      <td>{{formDate payDate}}</td>
      <td>{{formDate importDate}}</td>
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
		<h4 class="page-header ">
			<i class="ico icon-tcsz"></i>提成设置
		</h4>

		<div class="clearfix"></div>

		<div class="tab-null">
			<div class="box-right">
				<span class="text-blue">高级设置</span>
			</div>
			<div class="box-head">
				<span class="chang-time">选择类别：</span> <select
					class="cs-select text-gery-hs">
					<option>暂无类别</option>
					<option>暂无类别</option>
					<option>暂无类别</option>
					<option>暂无类别</option>
				</select>
			</div>

			<div class="box-head-s">
				<span class="chang-time">价格区间：</span> <select
					class="cs-select text-gery-hs">
					<option>0-50元</option>
					<option>11111</option>
					<option>222</option>
					<option>333</option>
				</select>
			</div>

			<div class="box-head-s">
				<span class="chang-time ">提成基数：</span> <input
					class="cs-select  text-gery-hs" type="text" placeholder="4.00">
				<span class="chang-time">元/台</span>
				<button class="btn btn-blue btn-sm ">确定</button>
			</div>
		</div>
		<div class="tab-content-new">

			<div class="row text-time">

				<div class="salesman" style="margin-top: 5px">
					<select class="cs-select text-gery-hs">
						<option>按价格区间</option>
						<option>暂无类别</option>
						<option>暂无类别</option>
						<option>暂无类别</option>
					</select> <select class="cs-select text-gery-hs">
						<option>全部类别</option>
						<option>暂无类别</option>
						<option>暂无类别</option>
						<option>暂无类别</option>
					</select>
					<button class="btn btn-blue btn-sm">检索</button>
				</div>
				<div class="link-posit-t pull-right export" style="margin-top: 20px">
					<a class="table-export" href="javascript:void(0);">导出excel</a>
				</div>
			</div>


			<!--待审核账单-->
			<div class="tab-pane fade in active" id="box_tab1">
				<!--table-box-->
				<div class="table-task-list new-table-box table-overflow">
					<table class="table table-hover new-table">
						<thead>
							<tr>
								<th>类别</th>
								<th>产品价格</th>
								<th>提成</th>
								<th>设置日期</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>手机</td>
								<td>0-50元</td>
								<td>5.00元</td>
								<td>2016.06.07</td>
								<td>
									<button class="xiugai  btn btn-blue btn-bluee"
										data-toggle="modal" data-target="#zdyqy">设置区域属性</button>
									<button class=" btn btn-green btn-bluee " data-toggle="modal"
										data-target="#xgywxx">修改</button>
									<button class="btn btn-warning btn-bluee" data-toggle="modal"
										data-target="#del">删除</button>
								</td>
							</tr>

							<tr>
								<td>手机</td>
								<td>0-50元</td>
								<td>5.00元</td>
								<td>2016.06.07</td>
								<td>
									<button class="xiugai  btn btn-blue btn-bluee"
										data-toggle="modal" data-target="#zdyqy">设置区域属性</button>
									<button class=" btn btn-green btn-bluee " data-toggle="modal"
										data-target="#xgywxx">修改</button>
									<button class="btn btn-warning btn-bluee" data-toggle="modal"
										data-target="#del">删除</button>
								</td>
							</tr>

							<tr>
								<td>手机</td>
								<td>0-50元</td>
								<td>5.00元</td>
								<td>2016.06.07</td>
								<td>
									<button class="xiugai  btn btn-blue btn-bluee"
										data-toggle="modal" data-target="#zdyqy">设置区域属性</button>
									<button class=" btn btn-green btn-bluee " data-toggle="modal"
										data-target="#xgywxx">修改</button>
									<button class="btn btn-warning btn-bluee" data-toggle="modal"
										data-target="#del">删除</button>
								</td>
							</tr>

							<tr>
								<td>手机</td>
								<td>0-50元</td>
								<td>5.00元</td>
								<td>2016.06.07</td>
								<td>
									<button class="xiugai  btn btn-blue btn-bluee"
										data-toggle="modal" data-target="#zdyqy">设置区域属性</button>
									<button class=" btn btn-green btn-bluee " data-toggle="modal"
										data-target="#xgywxx">修改</button>
									<button class="btn btn-warning btn-bluee" data-toggle="modal"
										data-target="#del">删除</button>
								</td>
							</tr>

							<tr>
								<td>手机</td>
								<td>0-50元</td>
								<td>5.00元</td>
								<td>2016.06.07</td>
								<td>
									<button class="xiugai  btn btn-blue btn-bluee"
										data-toggle="modal" data-target="#zdyqy">设置区域属性</button>
									<button class=" btn btn-green btn-bluee " data-toggle="modal"
										data-target="#xgywxx">修改</button>
									<button class="btn btn-warning btn-bluee" data-toggle="modal"
										data-target="#del">删除</button>
								</td>
							</tr>

							<tr>
								<td>手机</td>
								<td>0-50元</td>
								<td>5.00元</td>
								<td>2016.06.07</td>
								<td>
									<button class="xiugai  btn btn-blue btn-bluee"
										data-toggle="modal" data-target="#zdyqy">设置区域属性</button>
									<button class=" btn btn-green btn-bluee " data-toggle="modal"
										data-target="#xgywxx">修改</button>
									<button class="btn btn-warning btn-bluee" data-toggle="modal"
										data-target="#del">删除</button>
								</td>
							</tr>

						</tbody>
					</table>
				</div>
				<!--table-box-->
			</div>
			<!--待审核账单-->
		</div>
		<div class="wait-page-index">
			<ul class="pagination">
				<li><a href="#" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
		</div>
		<!--table-box-->
		<!--油补记录-->
	</div>
	<!---alert删除--->
	<div id="del" class="modal fade" role="dialog">
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
									<p class="form-control-static text-bg">胡 老 大</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label text-dk">所属区域：</label>
								<div class="col-sm-8">
									<p class="form-control-static text-bg">山东省济南市天桥一区</p>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label text-dk">基础薪资：</label>
								<div class="col-sm-8">
									<p class="form-control-static text-bg">3000.00</p>
								</div>
							</div>

							<div class="btn-qx">
								<button type="submit" class="btn btn-danger btn-d">删除</button>
							</div>

							<div class="btn-dd">
								<button type="submit" data-dismiss="modal"
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

	<div id="zdyqy" class="modal fade" role="dialog">
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
						<form id="addd" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">选择区域：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i class="icon icon-qy"></i></span>
										<select name="a" type="" class="form-control input-h"
											aria-describedby="basic-addon1">
											<option></option>
											<option>山东省济南市天桥区</option>
											<option>山东省莱芜市莱城区</option>
											<option>山东省青岛市四方区</option>
											<option>山东省泰安新泰区</option>
										</select>
										<!-- /btn-group -->
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label">扣罚金额：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i
											class="icon icon-task-je"></i></span> <select name="b" type=""
											class="form-control input-h" aria-describedby="basic-addon1">
											<option></option>
											<option>20%</option>
											<option>30%</option>
											<option>40%</option>
											<option>50%</option>
										</select>
										<!-- /btn-group -->
									</div>
								</div>
								<div class="col-sm-1 control-label">
									<span>%</span>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a herf="javascript:return 0;" onclick="addd(this)"
										class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!--<div id="xzyw" class="modal fade" role="dialog">-->
	<!--<div class="modal-dialog " role="document">-->
	<!--<div class="modal-content modal-blue">-->
	<!--<div class="modal-header">-->
	<!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span-->
	<!--aria-hidden="true">&times;</span></button>-->
	<!--<h3 class="modal-title">设置业务基础薪资</h3>-->
	<!--</div>-->
	<!--<div class="modal-body">-->
	<!--<div class="container-fluid">-->
	<!--<form id="addd" class="form-horizontal">-->
	<!--<div class="form-group">-->
	<!--<label class="col-sm-4 control-label">待设置业务：</label>-->
	<!--<div class="col-sm-7">-->
	<!--<div class="input-group are-line">-->
	<!--<span class="input-group-addon"><i class="icon icon-sz"></i></span>-->
	<!--<select name="a" type="" class="form-control input-h"-->
	<!--aria-describedby="basic-addon1">-->
	<!--<option></option>-->
	<!--<option>花千骨</option>-->
	<!--<option>白子画</option>-->
	<!--<option>杀阡陌</option>-->
	<!--</select>-->
	<!--</div>-->
	<!--</div>-->
	<!--</div>-->

	<!--<div class="form-group">-->
	<!--<label class="col-sm-4 control-label">基础薪资：</label>-->
	<!--<div class="col-sm-7">-->
	<!--<div class="input-group are-line">-->
	<!--<span class="input-group-addon"><i class="icon icon-xz"></i></span>-->
	<!--<input name="a" type="text" style="width: 200px" class="form-control input-h"-->
	<!--aria-describedby="basic-addon1">-->
	<!--</input>-->

	<!--</div>-->
	<!--<div class="text-strong" style="float: right;margin-top: -20px">元</div>-->
	<!--</div>-->
	<!--</div>-->

	<!--<div class="form-group">-->
	<!--<div class="col-sm-offset-4 col-sm-4 ">-->
	<!--<a herf="javascript:return 0;" onclick="addd(this)"-->
	<!--class="Zdy_add  col-sm-12 btn btn-primary">保存-->
	<!--</a>-->
	<!--</div>-->
	<!--</div>-->
	<!--</form>-->
	<!--</div>-->
	<!--</div>-->
	<!--</div>-->
	<!--</div>-->
	<!--</div>-->
	<!---alert新增业务--->

	<!---alert新增--->
	<div id="xzyhk" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">新增银行卡业务</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form id="4" class="form-horizontal">

							<div class="form-group">
								<label class="col-sm-4 control-label">发卡银行：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i class="icon icon-fk"></i></span>
										<select name="b" type="" class="form-control input-h"
											aria-describedby="basic-addon1">
											<option></option>
											<option>中国银行</option>
											<option>农业银行</option>
											<option>工商银行</option>
											<option>亚细亚银行</option>
										</select>
										<!-- /btn-group -->
									</div>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-4 control-label">银行卡号：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i class="icon icon-yh"></i></span>
										<input name="a" type="" class="form-control input-h"
											aria-describedby="basic-addon1"> </input>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a herf="javascript:return 0;" onclick="addd(this)"
										class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!---alert新增银行卡--->

	<!---alert修改--->
	<div id="xgywxx" class="modal fade" role="dialog">
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
						<form id="addd5" class="form-horizontal">

							<div class="form-group">
								<label class="col-sm-4 control-label">基础薪资：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i class="icon icon-yh"></i></span>
										<input name="a" type="" class="form-control input-h"
											aria-describedby="basic-addon1" placeholder="3000.00">
										</input>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a herf="javascript:return 0;" onclick="addd(this)"
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
		src="static/incomeCash/js/bank-import.js" charset="utf-8"></script>
	<script type="text/javascript">
		
	</script>
</body>

</html>