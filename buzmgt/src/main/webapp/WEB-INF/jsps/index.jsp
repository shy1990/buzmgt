<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>控制台</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/index.css" />
<link rel="stylesheet" type="text/css"
	href="static/purview-setting/purview-setting.css" />
<link rel="stylesheet" type="text/css"
	href="static/calender/clender.css" />
<link rel="stylesheet" type="text/css"
	href="static/calender/fullcalendar/fullcalendar.min.css" />

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<%@ include file="top_menu.jsp"%>
	<div class="container-fluid">
		<div id="" class="row">
			<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
				<%@include file="left_menu.jsp"%>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
				<h4 class="page-header">
					万年历
					</h4>
					<!-- CALENDAR -->
					<div class="row">
						<div class="col-md-12">
							<!-- BOX -->
							<div class="box border blue">
								<div class="box-title">
									<h4>
										<i class="fa fa-calendar"></i>Calendar
									</h4>
									<div class="tools">
										<a href="#box-config" data-toggle="modal" class="config">
											<i class="fa fa-cog"></i>
										</a> <a href="javascript:;" class="reload"> <i
											class="fa fa-refresh"></i>
										</a> <a href="javascript:;" class="collapse"> <i
											class="fa fa-chevron-up"></i>
										</a> <a href="javascript:;" class="remove"> <i
											class="fa fa-times"></i>
										</a>
									</div>
								</div>
								<div class="box-body">
									<div class="row">
										<div class="col-md-3">
											<div class="input-group">
												<input type="text" value="" class="form-control"
													placeholder="Event Event Title..." id="event-title" /> <span
													class="input-group-btn"> <a href="javascript:;"
													id="add-event" class="btn btn-success">Add Event</a>
												</span>
											</div>
											<div class="divide-20"></div>
											<div id='external-events'>
												<h4>Draggable Events</h4>
												<div id="event-box">
													<div class='external-event'>My Event 1</div>
													<div class='external-event'>My Event 2</div>
													<div class='external-event'>My Event 3</div>
													<div class='external-event'>My Event 4</div>
													<div class='external-event'>My Event 5</div>
												</div>
												<p>
													<input type='checkbox' id='drop-remove' class="uniform" />
													<label for='drop-remove'>remove after drop</label>
												</p>
											</div>
										</div>
										<div class="col-md-9">
											<div id='calendar'></div>
										</div>
									</div>
								</div>
							</div>
							<!-- /BOX -->
						</div>
					</div>
					<!-- /CALENDAR -->
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="static/js/jquery/jquery-1.11.3.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="http://echarts.baidu.com/build/dist/echarts-all.js"></script>
	<script type="text/javascript" src="static/js/ditu.js"></script>
	<script type="text/javascript" src="static/js/ditu2.js"></script>
	<script src="static/js/index.js" type="text/javascript" charset="utf-8"></script>
	<script
		src="static/calender/jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script type="text/javascript"
		src="static/calender/fullcalendar/fullcalendar.min.js"></script>
	<script src="static/calender/script.js"></script>
	<script>
		jQuery(document).ready(function() {
			App.setPage("calendar"); //Set current page
			App.init(); //Initialise plugins and elements
		});
	</script>

</body>

</html>
