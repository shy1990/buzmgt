<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<!-- Bootstrap -->
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<base href="<%=basePath%>" />
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
<link href="static/CloudAdmin/css/cloud-admin.css" rel="stylesheet">
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="../static/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="../static/plugins/ckeditor/config.js"></script>
<script src='static/js/common.js'></script>

</head>
<body>
	<div class=" col-md-12 "
		style="display: inline-block; line-height: 30px;">
	</div>
	<div class="col-md-6 ">
		<div class="col-md-12 ">
			<!-- BOX -->
			<div class="box border pink ">
				<div class="box-title ">
					<div class="tools ">
						<a href="#box-config " data-toggle="modal " class="config "> <i
							class="fa fa-cog "></i>
						</a> <a href="javascript:; " class="reload "> <i
							class="fa fa-refresh "></i>
						</a> <a href="javascript:; " class="collapse "> <i
							class="fa fa-chevron-up "></i>
						</a> <a href="javascript:; " class="remove "> <i
							class="fa fa-times "></i>
						</a>
					</div>
				</div>
				<div class="box-body ">
					<div class="form-group "></div>
					<div class="form-group ">
						<label for="title ">活动标题</label> <input type="text "
							class="form-control " id="title1" placeholder="请输入标题 ">
					</div>
					<div id="showpicture"></div>
					
					<form name="imgForm" action="/activity/upload" method="post"
						enctype="multipart/form-data">
						<div class="form-group ">
							<label for="image ">活动图片</label> 
							<input type="file" id="exampleInputFile " name="file">
							<p class="help-block ">(图片建议尺寸：900像素 * 480像素)</p>
							<input type="submit" value="上传">
						</div>
					</form>
				</div>
			</div>
		</div>
			<!-- /BOX -->
		<div class="form-group ">
			<div class="col-md-20 ">
				<label class="col-md-1 control-label ">区域</label> <select id="e3"
					class="col-md-3 ">
					<option></option>
					<option>Alabama</option>
					<option>Alaska</option>
				</select> <label class="col-md-1 control-label ">对象</label> <select id="e6"
					class="col-md-3 " m>
					<option></option>
					<option>Alabama</option>
					<option>Alaska</option>
				</select>
			</div>
		</div>
		<div class="col-md-12 ">
			<div class="box border orange ">
				<div class="box-title ">
					<label for="content"> 活动内容</label> 
					<div class="tools hidden-xs ">
						<a href="javascript:; " class="reload "> <i
							class="fa fa-refresh "></i>
						</a> <a href="javascript:; " class="collapse "> <i
							class="fa fa-chevron-up "></i>
						</a> <a href="javascript:; " class="remove "> <i
							class="fa fa-times "></i>
						</a>
					</div>
				</div>
				<div class="box-body ">
					<textarea name="editor1 " id="editor1"> </textarea>
					<script type="text/javascript">
						CKEDITOR.replace('editor1');
					</script>
				</div>
			</div>
			<div class="box-body ">
				<h4>
					<input type="button" class="btn btn-large btn-block btn-primary "
						value="发布 " width="100%" onclick="showCon()">
			</div>
		</div>
	</div>


	<!-- </form>    -->

</body>

</html>
