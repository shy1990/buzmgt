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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<base href="<%=basePath%>" />
<title>月任务派发</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css" href="static/kaohe/kaohe-det.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/ywmember.css" />
<link rel="stylesheet" type="text/css" href="/static/task/detail.css">
<link rel="stylesheet" type="text/css" href="static/oil/css/oil.css">


<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>

<script id="task-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		  <tr >
                            <th class="th-with">
                                <div class="row ">
                                    <div class="col-sm-2 pic-jl">
                                        <div class="bs-example bs-example-images " data-example-id="image-shapes">
                                            <img data-holder-rendered="true" src="static/img/task/mao.png"
                                                 style="width: 50px; height: 50px;" data-src="holder.js/40x40"
                                                 class="img-circle" alt="40x40">
                                        </div>
                                    </div>
                                    <div class="col-sm-9 text-jl">
                                        {{name}}<span class="text-body">（区域经理）</span><br>
                                        <span class="text-body">{{region}}</span>
                                    </div>
                                </div>

                            </th>
      {{#each detail}}
                            <th class="text-right text-body ">
                                    {{level}} 次拜访： <span class="text-bule">{{goal}}</span> 家<br>
                            	    已设置： <span class="text-red">{{set}}</span> 家
                            </th>
         {{/each}}     
		 {{#if status }}  
							<th class=" text-right btn-jl">
									<div class="btn-null"></div> <a class="btn btn-blue btn-sm"
									onclick="javascript:window.location.href='/monthTask/lookup/set/{{taskId}}'">查看</a>
							</th> 
  		 {{else}} 
				      
                            <th class="text-right " >
                                <a class="btn btn-success btn-sm btn-jl" onclick="javascript:window.location.href='/monthTask/addTask?taskId={{taskId}}'">修改</a>
                                <a class="btn btn-blue btn-sm  "  onclick="issueTask({{taskId}})">发布</a>
                            </th>			                
  		 {{/if}}
    </tr>
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
</head>

<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-task-send"></i>月任务派发
			<!--区域选择按钮  onclick="addTask();"   href="monthTask/addTask"-->
			<div class="area-choose">
				<a class="btn btn-blue"
					onclick="javascript:window.location.href='/monthTask/addTask'">
					<i class="ico icon-add"></i>月任务添加
				</a>
			</div>
			<!--/区域选择按钮-->

		</h4>

		<div class="container-all">
			<!--box-->
			<div class="kaohe-det-body box border blue">
				<!--title-->
				<div class="box-title">
					<!--start row-->
					<div class="row row-h">
						<div class="col-md-8">
							<div class=" date-jl">

								<div class=" date-wzz">
									<div class="input-group input-group-sm">
										<span class="input-group-addon " id="basic-addon1"><i
											class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span> <input
											type="text" class="form-control form_datetime input-sm"
											placeholder="请选择年-月" readonly="readonly">
									</div>
								</div>
							</div>
							<div class="date-jll">
								<a class="btn btn-default btn-sm" onclick="getTask();"
									><span class="text-bule">筛选</span></a>
							</div>

							<div class="date-jlll">
								<span class="text-bai"> 已派发 &nbsp;<span
									class="text-bai-d">${doneCount}</span> &nbsp; 个区域
								</span>
							</div>
						</div>

						<div class="col-md-2 input-jl ">
							<div class="input-group ">
								<input type="text" class="input-in form-control text-gery-s  "
									name="truename" id="param" placeholder="请输入业务员姓名"
									onkeypress="return check()"> <a
									class="input-ini input-group-addon " id="goSearch"
									onclick="getTask();"> <i class="icon icon-finds"></i>
								</a>
							</div>




						</div>
					</div>
					<!--end row-->
				</div>
				<!--title-->
				<!--box-body-->
				<div class="box-body-a">
					<!--列表头部-->
					<div class="bs-example">
						<table class="table table-hover text-body table-my--">
							<tbody id="taskList">			
							<tr style="border:1px solid #CCC;">
								
							</tr>
							
							</tbody>
						</table>
					</div>
					<!--/列表内容-->
					<!-- 分页组 -->
					<div id="abnormalCoordPager"></div>
				</div>
				<!--/box-body-->
			</div>
			<!--/box-->
		</div>
		<!--team-map-->
	</div>
	<!-- Bootstrap core JavaScript================================================== -->
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script src="/static/month-task/month.js"></script>

	<script type="text/javascript">
		function addTask() {
			window.location.href = "/monthTask/addTask";
		}
		var month = getNextMonth(0);
		$(function() {
			initDate();// 初始化日期
			findTaskList(0);// 初始查询
		});
	</script>
</body>

</html>