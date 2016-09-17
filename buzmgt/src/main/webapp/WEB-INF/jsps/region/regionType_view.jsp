<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
			+ "/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>区域划分</title>
	<base href="<%=basePath%>" />
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Bootstrap -->
	<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../static/css/common.css" />

	<!-- 树型结构 -->
	<link rel="stylesheet" type="text/css" href="static/zTree/css/icon.css" />
	<link rel="stylesheet" type="text/css" href="static/zTree/css/main.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/zTree/css/zTreeStyle/zTreeStyle.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/css/region/purview-region-setting.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/purview-setting/purview-setting.css" />
	<link rel="stylesheet" type="text/css"
		  href="static/bootstrap/css/bootstrap-dialog.css" />
	<link rel="stylesheet" type="text/css"   href="static/bootstrap/css/jquery-confirm.min.css" />


	<link rel="stylesheet" type="text/css"   href="static/css/region/jquery.gridly.css" />
	<link rel="stylesheet" type="text/css"   href="static/css/region/sample.css" />

	<script src="static/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/js/region/jquery.gridly.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/js/region/sample.js" type="text/javascript" charset="utf-8"></script>
	<script src="static/js/region/rainbow.js" type="text/javascript" charset="utf-8"></script>

	<style>

		.maintype {
		width: 980px;
		margin: 80px auto; }

		.brick small {
			text-align:center;
			vertical-align:middle;
			line-height:135px;
			border:#000000 dotted 1px;
		}
	</style>
</head>
<body>
<div id="j_page_main" class="content main">
	<h4 class="page-header-region">
		<i class="icon region-setting-icon"></i>区域类型
	</h4>
	<div class="row">
		<div class="col-md-12">
			<div class="box border blue">
				<div class="box-title">
					<div class="row">
						<div class="col-md-12">
							<div class="role">
								<i class="title-icon"></i>区域类型

							</div>
						</div>
					</div>
				</div>
				<div  class="maintype">
					<section class='example'>
						<div class='gridly'>
							<c:forEach var="type" items="${listRetionType}" varStatus="s">
							  <div class='brick small'　id="typeid" style="text-align:center;vertical-align:middle;line-height:135px;border:#000000 dotted 1px;">
							     <a class='delete' href='#'>&times;</a>
								 <span style="font-size: large;color: #000000" >${type.name}</span>
							  </div>
							</c:forEach>
							<%--<div class='brick small'　id="typeid" style="text-align:center;vertical-align:middle;line-height:135px;border:#000000 dotted 1px;">--%>
								<%--<a class='delete' href='#'>&times;</a>--%>
								<%--<span style="font-size: large;color: #000000" >大区</span>--%>
							<%--</div>--%>

							<%--<div class='brick small'　id="typeid" style="text-align:center;vertical-align:middle;line-height:135px;border:#000000 dotted 1px;">--%>
								<%--<a class='delete' href='#'>&times;</a>--%>
								<%--<span style="font-size: large;color: #000000" >省</span>--%>
							<%--</div>--%>

							<%--<div class='brick small'　id="typeid" style="text-align:center;vertical-align:middle;line-height:135px;border:#000000 dotted 1px;">--%>
								<%--<a class='delete' href='#'>&times;</a>--%>
								<%--<span style="font-size: large;color: #000000" >市</span>--%>
							<%--</div>--%>

						</div>
						<p class='actions'>
						<a class='add button' href='#' data-toggle="modal" data-target="#myModal">添加</a>
						</p>
					</section>
				</div>


				<!-- start： 弹窗 -->
				<div class="j_create_role add-role modal fade " id="myModal"
					 tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
									<span aria-hidden="true">&times;</span>
								</button>
								<h4 class="modal-title" id="exampleModalLabel">
									<i class="icon-add"></i>创建类型
								</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal">
									<div class="form-group">
										<label for="name" class="col-md-3 control-label">类型名称</label>
										<div class="col-md-9 ">
											<input type="text" placeholder="请填写类型名称" class="form-control"
												   name="name" id="name" /> <span
												id="gradeInfo"></span>
										</div>
										<label class="pull-right col-md-3 control-label msg-error"
											   id="nameError">请填写类型名称</label>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<div class="col-md-3 col-md-offset-8">
									<button type="submit" onclick="addRegionType();" id="bt"
											class="btn col-xs-12 btn-danger" >确定</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- end 弹窗 -->
		</div>
	</div>
</div>


<!-- Bootstrap -->
<script src='static/js/common.js'></script>
<script src="static/bootstrap/js/bootstrap.min.js"
		type="text/javascript" charset="utf-8"></script>
<script type="text/javascript"
		src="static/bootstrap/js/jquery-confirm.min.js"></script>

</body>
<script type="text/javascript">
	/*	新增角色*/
	function addRegionType() {
		var $_name = $('#name');
		if ($_name.val() == null || $_name.val() == "") {
			$_name.parents('.form-group').addClass('has-error');
			return false;
		}
		var url = "region/addRegionType?name=" + $("#name").val();
		$.post(url, function(data) {
			if (data === 'suc') {
				$('#exampleModal').modal('hide');
				alert("添加成功");
				setTimeout(function() {
					location.reload()
				}, 2000);
			} else {
				alert("添加失败!");
			}
		});
	}
</script>
</html>