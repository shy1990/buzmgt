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
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<base href="<%=basePath%>" />
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-theme.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/zTree/css/zTreeStyle/zTreeStyle.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript"
	src="../static/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript"
	src="../static/plugins/ckeditor/config.js"></script>
<script type="text/javascript" src="../static/plugins/uploadPreview.js"></script>
<script type="text/javascript" src="../static/plugins/ajaxfileupload.js"></script>

<script src="/static/zTree/js/jquery.ztree.all-3.5.js"
	type="text/javascript" charset="utf-8"></script>
<script src="/static/yw-team-member/team-memberAdd.js"
	type="text/javascript" charset="utf-8"></script>
<script src="/static/yw-team-member/team-tree.js" type="text/javascript"
	charset="utf-8"></script>
<script src="/static/js/common.js" type="text/javascript"
	charset="utf-8"></script>

<script type="text/javascript">
	window.onload = function() {
		new uploadPreview({
			UpBtn : "myBlogImage",
			DivShow : "imgdiv",
			ImgShow : "imgShow"
		});
	}

	function show() {
		$("#imgdiv").show();
		$('#result').html("");
	}

	function ajaxFileUpload() {

		var filename = $("#myBlogImage").val();
		if ($("#myBlogImage").val() == "") {
			$('#result').html("请选择图片上传!<br/>");
			return;
		}
		$.ajaxFileUpload({
			//处理文件上传操作的服务器端地址(可以传参数,已亲测可用)
			url : 'activity/upload',
			secureuri : false, //是否启用安全提交,默认为false
			fileElementId : 'myBlogImage', //文件选择框的id属性
			dataType : 'text', //服务器返回的格式,可以是json或xml等
			success : function(data) { //服务器响应成功时的处理函数
				if (data == 'suc') { //0表示上传成功(后跟上传后的文件路径),1表示失败(后跟失败描述)
					$('#result').html("图片上传成功<br/>");
					$("#imgdiv").hide();
				} else {
					$('#result').html('图片上传失败，请刷新重试！！');
				}
			},
			error : function(data, status, e) { //服务器响应失败时的处理函数
				$('#result').html('图片上传失败，请重试！！');
			}
		});
	}

	function submit() {
		var title = $("#title1").val();
		var content = CKEDITOR.instances.content1.document.getBody().getText();
		var region = $("#region").val();
		if (title == "" || content == "" || region == "") {
			alert("不能为空!");
			return;
		}

		alert(title + "==" + content + "==" + region);
		$.ajax({
			url : "activity/sendActivity",
			type : "post",
			data : "title=" + title + "&content=" + content + "&region="
					+ region,
			dataType : "json",
			success : function(data) {

			}
		});
	}
</script>
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
	z-index: 200;
}
</style>
</head>
<body>
	<!-- main -->
	<div class="content main">
		<h4 class="team-member-header page-header ">
			<i class="icon icon-saojie-set"></i> 活动通知
		</h4>
		<!--strat row -->
		<div class="row">
			<div class="col-md-12 ">
				<!-- BOX -->
				<div class="box border blue ">
					<div class="box-title ">
						<h4>新建通知</h4>
					</div>
					<!-- start box-body -->
					<div class="box-body form-horizontal">
						<div class="col-sm-7">
							<div class="form-group ">
								<label for="inputPassword" class="col-sm-3 control-label">选择区域</label>
								<div class="col-sm-9">
									<select id="region" class="form-control" name="regionId">
									</select>
									<div id="regionMenuContent" class="menuContent">
										<ul id="regionTree" class="ztree"></ul>
									</div>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>	
						<div class="hr"></div>
						<!-- start 活动标题 -->
						<div class="col-sm-7">
							<div class="form-group "></div>
							<div class="form-group  ">
								<label class="col-sm-3 control-label" for="title ">活动标题</label> 
								<div class="col-sm-6">
									<input type="text" class="form-control " id="title1" placeholder="请输入标题 ">
								</div>
							</div>
							<div class="form-group ">
								<label class="col-sm-3 control-label" for="image ">活动图片</label> 
								<div class="col-sm-9 col-xs-12">
									<input class="marg-b-10" type="file" id="myBlogImage" name="myfiles" onclick="show()" />
									<div id="imgdiv" style="display: none">
										<img id="imgShow" width="309" height="150" alt="请选择图片" />
									</div>
									<p class="help-block ">(图片建议尺寸：900像素 * 480像素)</p>
									<div id="result"></div>
									<input type="button" value="上传图片" onclick="ajaxFileUpload()" />
								</div>
							</div>
							<div class="form-group ">
								<label class="col-sm-3 control-label">活动内容</label>
								<div class="col-sm-9 marg-b-20">
									<textarea name="editor1 " id="content1"></textarea>
									<script type="text/javascript">
										CKEDITOR.replace('content1');
									</script>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-9 col-sm-offset-3 marg-b-10">
									<input type="button" class="btn btn-large btn-block btn-primary"
										value="发布 " width="100%" onclick="submit()">
								</div>
							</div>
							
							
						</div>
						<!-- end 活动标题 -->
						<!-- start 活动内容 -->
						<div class="col-sm-5">
							<div class="box-body ">
								
							</div>
						</div>
						<!-- end 活动内容 -->
					</div>
					<!-- end box-body -->
				</div>
				<!-- end box -->
			</div>
		</div>
		<!-- end row -->
	</div>
	<!-- end main   -->

</body>

</html>
