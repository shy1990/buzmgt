<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<base href="<%=basePath%>" />
<title>自定义模块</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />

<script src="/static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css"
		href="static/customTask/type.css">
<link rel="stylesheet" type="text/css"
		href="static/customTask/add.css">		
<link href="/static/customTask/searchSelect/css/jquery.multiselect.css"
		type="text/css" rel="stylesheet" />
</head>
<body>
	<div class="content main">
		<h4 class="team-member-header page-header ">
			<div class="row">
				<div class="col-sm-12">
					<i class="ico icon-new-type"></i> 新建类型
				</div>
			</div>
		</h4>
		<div class="row">
			<div class="col-md-12">
				<!--box-->
				<div class="saojie-set-body box border blue">
					<!--title-->
					<div class="box-title">
						<h4>新建</h4>
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body form-horizontal">
						<!--内容-->

						<div class="form-group">
							<label class="col-sm-2 control-label">标题：</label>
							<div class="col-sm-3 input-group left">
								<span class="input-group-addon"><i class="icon ico-bt"></i></span>
								<input type="text" id="title" class="form-control"
									placeholder="请输入标题" aria-describedby="basic-addon1">
							</div>
							<div style="float: left; margin-top: -35px; margin-left: 43%">
								<p class="form-control-static">
									<span class="text-orage">注：</span> <span class="text-gery">50字以内</span>
								</p>
							</div>
						</div>
						<div class="hr"></div>

						<div class="form-group">
							<label class="col-sm-2 control-label">新建类型：</label>
							<div class="col-sm-3 input-group left">
								<span class="input-group-addon" style=""><i
									class="icon ico-xinjian"></i></span> <select id="Customtype"
									class="form-control" onchange="changePunish(this.value)">
									<option value="">---请选择类型---</option>
									<option value="0">店铺注册</option>
									<option value="1">售后处理</option>
									<option value="2">扣罚通知</option>
									<option value="3">客户拜访</option>
								</select>
							</div>
						</div>
						<div class="hr"></div>

						<div id='punishDiv' class="form-group">
							<label class="col-sm-2 control-label">扣罚金额：</label>
							<div class="col-sm-3 input-group left">
								<span class="input-group-addon"><i class="icon icon-jine"></i></span>
								<input type="number" id="punish" onchange="testPunish(this);" class="form-control"
									placeholder="请输入金额" aria-describedby="basic-addon1">
							</div>
						</div>
						<div id='punishhr' class="hr"></div>


						<div class="form-group">
							<label class="col-sm-2 control-label">消息内容：</label>
							<div class="col-sm-3 input-group left">
								<textarea id="content" class="form-control" rows="10"
									placeholder="请输入内容"></textarea>
							</div>
						</div>
						<div class="hr"></div>

					<div class="form-group">
                        <label class="col-sm-2 control-label">接收人：</label>

                        <div class="table-responsive">
                            <div class="average-tr " >
                                <div class=" butn-tj">
                                    <a class="J_addDire btn btn-default btn-kaohe-add col-sm-6"  data-toggle="modal" data-target="#tjjsr"  ><i class="icon-saojie-add"></i>添加</a>
                                </div>

                            </div><br><br><br>

                            <div class=" table table-bordered"  id="acont">

                            </div>
                            <!--/table-->
                        </div>


                    </div>
                          
						<div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-2 input-group left">
								<button class="btn btn-primary" onclick="issue()"
									style="width: 180px; margin-top: 60px">发布</button>
							</div>
						</div>
						<!--分隔,清除浮动-->
						<div class="clearfix"></div>
					</div>
					<!--/box-body-->
				</div>
				<!--/box-->
			</div>
			<!--col-->
		</div>
	</div>


	<!---alert新增--->
	<div id="tjjsr" class="modal fade" role="dialog">
		<div class="modal-dialog " role="document">
			<div class="modal-content modal-blue">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h3 class="modal-title">添加接收人</h3>
				</div>
				<div class="modal-body">
					<div class="container-fluid">
						<form id="addd" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-4 control-label">请选择：</label>
								<div class="col-sm-7">
									<div class="input-group are-line">
										<span class="input-group-addon"><i
											class="icon icon-ren"></i></span> <select id="salesName"
											multiple="multiple" size="5" class="form-control input-h">
											<option vlue="">请选择接收人</option>
											<c:forEach items="${salesList}" var="saleman">
												<option value="${saleman[0]}">${saleman[1]}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-4 col-sm-4 ">
									<a onclick="addSales();"
										class="Zdy_add  col-sm-12 btn btn-primary">保存 </a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript"
		src="/static/multiselect/js/jquery.multiselect.js"></script>
	
	<script type="text/javascript" src="/static/customTask/add.js"></script>
	<script src="/static/bootstrap/js/bootstrap-select.min.js"></script>
	<script type="text/javascript">
		$('select#salesName').multiselect({
			columns : 1,
			placeholder : '选择业务员',
			search : true,
			selectGroup : true
		});
	</script>
</body>
</html>