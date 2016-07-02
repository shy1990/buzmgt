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
<html lang="en">
<head>
<meta charset="UTF-8" name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<base href="<%=basePath%>" />
<title>自定义事件详情</title>
<!-- Bootstrap -->
<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="/static/kaohe/kaohe-det.css" />
<link rel="stylesheet" type="text/css"
	href="/static/abnormal/abnormal.css" />
<link rel="stylesheet" href="/static/customTask/type.css"
	type="text/css" />

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="task-table-template" type="text/x-handlebars-template">
	{{#if content}}
		{{#each content}}
			<div class="panel panel-default">
						<div class="panel-heading">
							<a data-toggle="collapse" data-parent="#accordion"
								href="{{dohref salesId}}">
								<h4 class="panel-title">
						
			<span class="text-bluue">{{name}} </span> &nbsp;&nbsp;&nbsp; <i
										class="icon icon-bk pull-right"></i>
								</h4>
							</a>
						</div>
						<div id="{{salesId}}" class="{{ doCollapse unsize }}"
							style="padding-bottom: 120px">
							<span class=" text-or-orange" style="font-size: 12px">会话详情：</span>
						{{#each mesList}}
						 {{# compare roletype}}
   							<div class="panel-body">
								<span class="text-gery">{{ctime}}</span> <br>								
								<span class="text-time">{{content}}</span>
							</div>
						{{else}}
							 <div   class="panel-body" style="margin-right: 30px ">
                             <div class="pull-right"><span class="text-gery">{{ctime}}</span> <span class="text-bluue">我</span></div><br><br>
                             <div class="pull-right" >{{content}}</div><br><br>
					        </div>
						{{/compare}}
						{{/each}}
                            <div class="input-group pull-right" style="width: 395px">
                                <input id='message{{salesId}}' type="text" class="form-control" placeholder="" aria-describedby="basic-addon2">
                                <span class="input-group-addon" id="basic-addon2" onclick="sendMessage('{{salesId}}')"
                                      style="background-color: #f4f4f4;color: #0C6DBA;">回复</span>
                            </div>
				       </div>
					</div>			
		{{/each}}
	{{else}}
		<tr>
			<td colspan="100">没有消息记录</td>
		</tr>
	{{/if}}
</script>
</head>
<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-xq"></i>详情
		</h4>

		<!--box-->
		<div class="abnormal-body box border blue">
			<!--title-->
			<div class="box-title">${task.title}</div>
			<!--title-->
			<!--box-body-->
			<div class="box-body">
				<div>
					<div class="text-hor">
						<label class="col-sm-2">任务类型：</label>
						<p class="col-sm-10 text-or-orange" style="line-height: 30px">${taskType}</p>
					</div>
					<div class="hr"></div>


					<div class="text-hor">
						<label class="col-sm-2">送达时间：</label>
						<p class="col-sm-10" style="line-height: 30px">${task.createTime}</p>
					</div>
					<div class="hr"></div>

					<div class="text-hor">
						<label class="col-sm-2">收件人：</label>
						<p class="col-sm-10" style="line-height: 30px">
							<c:if test="${not empty reSet}">
								<c:forEach var="salesman" items="${reSet}">
									<label style="color: green"> ${salesman.truename} &nbsp;&nbsp;&nbsp;&nbsp;</label>
								</c:forEach>
							</c:if>
							<c:if test="${not empty unreSet}">
								<c:forEach var="salesman" items="${unreSet}">
									<span class="text-reda"> ${salesman.truename}</span>
								</c:forEach>
							</c:if>
						</p>
					</div>
					<div class="hr"></div>

					<div class="text-hor" id="punish">
						<label class="col-sm-2">扣罚：</label>
						<p class="col-sm-10" style="line-height: 25px;"><span style="color:red;font-size:30;">${task.punishCount}</span>元</p>
					</div>
					<div class="hr" id="punish1"></div>

					<div class="text-hor">
						<label class="col-sm-2">消息主体：</label>
						<p class="col-sm-10">
							<textarea class="text-inp" readonly="readonly"
								placeholder="${task.content}"></textarea>
						</p>
					</div>

					<div class="hr"></div>

					<div class="text-hor">
						<label class="col-sm-2">快捷回复：</label>
						<p class="col-sm-10">
							<select id="fastSales" class="box-shuif">
								<option value='0'>所有人</option>
								<option value='1'>未回执人员</option>
								<option value='2'>已回执人员</option>
							</select>
						</p>
						<br> <label class="col-sm-2"></label>
						<p class="col-sm-10" style="margin-top: 35px; margin-bottom: 20px">
							<span class="text-zhu">注：</span> <span class="text-nei">此内容将发送选定人员</span>
						</p>

						<br> <label class="col-sm-2"></label>
						<p class="col-sm-10">
							<textarea id="fastConent" class="text-inp" placeholder="请输入回复内容"></textarea>
						</p>
						<br>

						<button onclick="fastresp()" class="col-sm-10 btn btn-primary btn-fs pull-right">发送</button>
					</div>
				</div>
				<div class="hr"></div>

				<div class="middle-body">
					<span id='recieve' class="text-ttile"></span>
				</div>

				<div class="panel-group" id="taskList"></div>
				<div class="pull-right wait-page-index" id="abnormalCoordPager">

				</div>
			</div>


		</div>
	</div>

	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript" src="/static/customTask/detail.js"></script>
	<script type="text/javascript">
		var taskId = "${task.id}";
		var unset="${unresalSet}";
		if(unset!="null"){
			unset=unset.split(",");
		}else{
			unset=[];
		}
		var reset="${resalSet}";
		if(reset!="null"){
			reset=reset.split(",");
		}else{
			reset=[];
		}
		var type = "${task.type}";
		if (type != 2) {
			$("#punish").css("display", "none");
			$("#punish1").css("display", "none");
		}
		getMessage(0);
		window.setInterval(testUpdate,1000*5);
	</script>
</body>
</html>