<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>设置油补系数</title>
<!-- Bootstrap -->

<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/account-manage/account-list.css" />
<link rel="stylesheet" type="text/css" href="static/task/task.css" />
<link rel="stylesheet" type="text/css" href="static/oil/css/oil.css" />

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>



<!-- ======================== -->
<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/zTree/css/zTreeStyle/zTreeStyle.css" />
<script src="/static/js/jquery/jquery.min.js" type="text/javascript"
	charset="utf-8"></script>
<script src='/static/js/common.js'></script>

<style type="text/css">
.ztree {
	margin-top: 34px;
	border: 1px solid #ccc;
	background: #FFF;
	width: 100%;
	overflow-y: scroll;
	overflow-x: auto;
}

.menuContent {
	width: 100%;
	padding-right: 61px;
	display: none;
	position: absolute;
	z-index: 800;
}
</style>

</head>

<body>
	<div class="container-fluid content main">
		<div>
			<h4 class="text-hd">每公里油补金额设置：</h4>
			<!--每公里油补金额设置-->
			<div class="table-bordered">
				<div class="tab-content-main">
					<div class="tab-pane active">
						<div class="table-responsive ">
							<!--公里系数表头-->
							<div class="text-tx row-d">
								<span class="text-strong">每公里油补金额：</span> <select>
									<option>1.5</option>
									<option>1.5</option>
									<option>1.5</option>
									<option>1.5</option>
								</select> <span class="text-strong">元/km</span> <span class="text-blue">注：</span><span
									class="text-small">系统默认所有区域均为该系数，自定义设置区域除外</span>
							</div>
							<!--设置金额-->
							<div class="bs-example">
								<div id="acontt" class="row"></div>
							</div>
							<div class="row show-grid   row-jl ">
								<div class="col-md-5"></div>
								<div class="col-md-7 ">

									<button class=" col-sm-3 btn  btn btn-default" type="button"
										data-toggle="modal" data-target="#zdyqyoil">自定义设置区域</button>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-4 col-sm-4" style="margin-top: 20px">
					<button type="submit" class="col-sm-12 btn btn-primary ">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 弹出自定义：油补金额设置 -->
	<div id="zdyqyoil" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">自定义区域</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form id="addoil" class="form-horizontal">
							<div class="form-group">
								<label for="" class="col-sm-4 control-label">选择区域：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i class="icon icon-qy"></i></span>
										<!-------------------- 调用区域树 ------------------------>
										<select id="region" class="form-control" name="regionId">
											<input id="n" type="hidden" value="${regionId}" />
										</select>
										<div id="regionMenuContent" class="menuContent">

											<ul id="regionTree" class="ztree"></ul>
										</div>
										<input type="hidden" id="towns" name="regionPid">
										<!-------------------- end ------------------------>
									</div>
								</div>
							</div>

							<div class="form-group">
								<label for="" class="col-sm-4 control-label">公里系数：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i
											class="icon icon-task-lk"></i></span> <select name="klmt" type=""
											class="form-control input-h" aria-describedby="basic-addon1">
											<option></option>
											<option>0.3</option>
											<option>0.5</option>
											<option>0.8</option>
											<option>1.0</option>
										</select>
										<!-- /btn-group -->
									</div>
								</div>
								<div class="col-sm-1 control-label">
									<span>元/km</span>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a herf="javascript:return 0;" onclick="addoil(this)"
										class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>


	<script src="static/js/jquery/jquery-1.11.3.min.js"></script>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
	<script
		src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js"
		type="text/javascript" charset="utf-8"></script>


	<!------------------------------------------------------------------------------------------------------------------------ -->
	<script src="/static/zTree/js/jquery.ztree.all-3.5.js"
		type="text/javascript" charset="utf-8"></script>

	<!-- 出来区域节点树 -->
	<script src="/static/oil/js/oil-set-region.js" type="text/javascript"
		charset="utf-8"></script>
	


	<!--  暂时没有用
	<script src="/static/yw-team-member/team-memberForm.js"
		type="text/javascript" charset="utf-8"></script>
		 -->
	<script src="/static/yw-team-member/team-tree.js"
		type="text/javascript" charset="utf-8"></script>
		

		
		
	<script src="/static/js/index.js" type="text/javascript"
		charset="utf-8"></script>

	<script src="/static/oil/js/oil.js" type="text/javascript"
		charset="utf-8"></script>



	<script type="text/javascript">
		$(function resetTabullet() {
			$("#table").tabullet({
				data : source,
				action : function(mode, data) {
					console.dir(mode);
					if (mode === 'save') {
						source.push(data);
					}
					if (mode === 'edit') {
						for (var i = 0; i < source.length; i++) {
							if (source[i].id == data.id) {
								source[i] = data;
							}
						}
					}
					if (mode == 'delete') {
						for (var i = 0; i < source.length; i++) {
							if (source[i].id == data) {
								source.splice(i, 1);
								break;
							}
						}
					}
					resetTabullet();
				}
			});
			resetTabullet();
		});
		/*
		 function addd(toil) {
		 console.log($("#addd").serializeArray());
		 addCustom($("#addd").serializeArray());
		 }
		 var i = 1;
		 function addCustom(o) {
		 var qy = o[0]["value"];//regionId
		 var glxs = o[1]["value"];//
		 var km_ratio = o[2]["value"];
		 console.log(qy.length);
		 if (qy.length > 7) {
		 alert("请您只选择到：要选择区域的最后一级");
		 return location.href = '/oil/toOilSetList';
		 ;

		 }
		 console.log(qy + '******' + km_ratio);
		 oilForm(km_ratio, qy)

		 };
		 */
		function addoil(t) {

			console.log($("#addoil").serializeArray());
			oilCustom($("#addoil").serializeArray());
		}
		var j = 1;
		function oilCustom(o) {
			var oqy = o[0]["value"];
			var okm = o[1]["value"];
			var html = '<div class="col-sm-3 cl-padd">'
					+ '                <div class="ratio-box">'
					+ '                <div class="ratio-box-dd">'
					+ '                <span class="label  label-blue">'
					+ (j++)
					+ '</span>'
					+ '                <span class="">'
					+ oqy
					+ '</span>'
					+ '                <a class="text-redd" href="" data-toggle="modal"' +
                '        data-target="">'
					+ okm
					+ '元/km</a>'
					+ '        <a class="text-bluee" href="" data-toggle="modal"' +
                '        data-target="#changed">修改</a>'
					+ '                <a class="text-bluee" href="" data-toggle="modal" data-targ' +
                't="">删除</a>'
					+ '                </div>' + '                </div>'
					+ '                </div>';

			$("#acontt").append(html);

		};
		/*
		function oilForm(km_ratio, qy) {
			//发送请求
			$.ajax({
				url : 'oil/toOilSet',//添加公里系数设置区域
				type : 'post',
				data : {
					ratio : km_ratio,
					regionId : qy.trim()
				},
				success : function(result) {
					alert(result);
					location.href = '/oil/toOilSetList';
				}
			});

		}

		function modify(id, kmOilSubsidy, regionId) {
			$('#changed').modal('show').on('shown.bs.modal', function() {
				$("#set_a").click(function() {
					var ratio = $("#select_modify").val();
					console.log(id + "  " + kmOilSubsidy + "  " + regionId);
					$.ajax({
						url : 'oil/modifyOilParameter',
						type : 'post',
						data : {
							id : id,
							kmRatio : ratio,
							regionId : regionId,
							kmOilSubsidy : kmOilSubsidy
						},
						success : function(data) {
							alert(data);
							location.href = '/oil/toOilSetList';
						}
					});
				});
			});

		}
		
		function delete_byId(id,regionId){
			$.ajax({
				url:'oil/delteOilParameterByRegionId',
				type:'post',
				data:{
					regionId:regionId
				},
				success:function(data){
					alert(data);
					location.href = '/oil/toOilSetList';
				}
			});	
			
		}
		 */

		
		 
		 
		 
		 
		 
		 
		function change() {
			window.showModalDialog("/oil/show");
		}
	</script>

</body>

</html>