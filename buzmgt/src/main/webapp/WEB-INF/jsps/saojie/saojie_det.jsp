<%@ page language="java" import="java.util.*,com.wangge.buzmgt.sys.vo.*,com.wangge.buzmgt.saojie.entity.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>扫街明细</title>
<!-- Bootstrap -->
<link href="/static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="/static/bootstrap/css/bootstrap-multiselect.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/static/css/common.css" />
<link href="static/bootStrapPager/css/page.css" rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="/static/saojie/saojie-det.css" />
<link rel="stylesheet" type="text/css"
	href="/static/yw-team-member/ywmember.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
<script type="text/javascript">
	var base = "<%=basePath%>";
	var number = '';//当前页数（从零开始）
	var totalPages = '';//总页数(个数)
	var searchData = {
		"size" : "6",
		"page" : "0",
	}
	var totalElements;//总条数
</script>
<script id="table-template" type="text/x-handlebars-template">
{{#each content}}
	<div class="list-tr">
	<img class="shop-img" src="{{imageUrl}}" />
	<div style="display: inline-block;" class="list-conter">
	<h4>{{name}}</h4>
	<p>{{description}}</p>
	<span class="pull-right">{{saojieDate}}</span>
	</div>
	</div>
{{else}}
<div style="text-align: center;">
	<tr style="text-align: center;">没有相关数据!</tr>
</div>
{{/each}}
</script>
</head>

<body>
	<div class="content main">
		<h4 class="team-member-header page-header ">
			<i class="icon icon-ywdet"></i>扫街明细
			<a href="/saojie/saojieList" class="btn btn-blue member-add-btn"
				type="button"> <i class="icon glyphicon glyphicon-share-alt"></i>
				返回列表
			</a>
		</h4>
		<div class="row">
			<div class="col-md-9">
				<!--box-->
				<div class="saojie-det-body box border blue">
					<!--title-->
					<div class="box-title">
						<!--区域选择按钮-->
						<select id="regionId" class="form-control input-xs " onchange="ajaxSearchByRegion();"
							style="width: 110px; position: relative;height:25px; padding: 2px 5px; display: inline-block; font-size: 12px; color: #6d6d6d;">
							<option value="" selected="selected">全部区域</option>
							<c:forEach var="region" items="${rList}" varStatus="s">
								<option value="${region.id}" >${region.name}</option>
							</c:forEach>
						</select>
						<!--/区域选择按钮-->
						<input id="saojieId" type="hidden" value="${saojieId}">
						<div class="det-msg">
							<span>扫街商家  <span class="shopNum"> </span> 家
							</span> 
							<span style="margin-left: 10px;">扫街已完成     <span class="percent"> </span>  </span>
						</div>
						<!--/row-->
						<div class="btn-group title-page">
							<a class="title-btn active" href="javascript:;"><i
								class="icon icon-map"></i>地图</a> <a class="title-btn"
								href="javascript:;"><i class="icon icon-list"></i>列表</a>
						</div>
					</div>
					<!--title-->
					<!--box-body-->
					<div class="box-body">
						<!--地图-->
						<div class="saojie-map ">
							<!--地图位置-->
							<div style="height: 600px;" class="body-map">
								<!--地图位置-->
								<div style="height: 600px;" class="body-map" id="allmap">
<!-- 									<img src="static/img/saojie-map.png" /> -->
								</div>
							</div>
							<c:if test="${salesStatus=='kaifa'}">
								<button class="btn btn-approve col-sm-2 col-sm-offset-5" onclick="audit('${salesMan.id}','${salesMan.region.name}')">审核通过</button>
							</c:if>
						</div>
						<!--/地图-->
						<!--列表-->
						<table class="table saojie-list">
							<tbody id="saojiedata">
		
							</tbody>
						</table>
						<!--/列表-->
					</div>
						<div id="callBackPager"></div>
					<!--/box-body-->
				</div>
				<!--/box-->
			</div>

			<!--team-map-->
			<div class="col-md-3">
				<!--box-->
				<!--不同阶段颜色不同1：pink 2：yellow 3:violet 4:-->
				<div class="ywmamber-msg box border pink">
					<!--title-->
					<div class="box-title">
						<i class="icon icon-time"></i>${salesMan.status.name }
					</div>
					<div class="box-body">
						<!--ywmamber-body-->
						<div class="ywmamber-body">
							<img width="80" src="../static/img/user-head.png" alt="..."
								class="img-circle">
							<div class="msg-text">
								<h4>${salesMan.truename}</h4>
								<span>ID: </span> <span id="userId">${salesMan.id}</span>
								<p>电话: ${salesMan.mobile}</p>
							</div>
						</div>
						<!--/ywmamber-body-->
						<div class="stage">
							<span class="stage">${salesMan.status.name}:<span
								class="percent">60%</span></span>
						</div>
						<div class="progress progress-sm">
							<div id="percent" style="width: 60%;"
								class="progress-bar bar-stage"></div>
						</div>
						<div class="operation">
							<a href="javascript:;" class="">设置</a> <a href="javascript:;">辞退</a>
							<a href="javascript:;" class="pull-right">查看</a>
						</div>
						<div class="yw-text">
							入职时间:<span> ${salesMan.regdate}</span><br /> 负责区域:<span>${salesMan.region.name}</span><input
								id="regionId" type="hidden" value="${salesMan.region.id}" />
						</div>
						<!--拜访任务-->
						<div class="visit">
							<button class="col-xs-12 btn btn-visit" href="javascript:;">
								<i class="ico icon-add"></i>拜访
							</button>
						</div>
						<!--拜访任务-->
						<!--操作-->
						<div class="operation">
							<a href="javascript:;" class="">账户设置</a> <a href="javascript:;">冻结账户</a>
						</div>
						<!--操作-->
						<!--虚线-->
						<div class="hr"></div>
						<!--虚线-->
						<!--业务外部链接-->
						<div class="yw-link">
							<a class="link-oper" href="javascript:;"><i
								class="icon icon-user"></i>个人资料</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-income"></i>收益</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-task"></i>任务</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-log"></i>日志</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-footprint"></i>足迹</a> <a class="link-oper"
								href="javascript:;"><i class="icon icon-signin"></i>签收记录</a> <a
								class="link-oper" href="javascript:;"><i
								class="icon icon-saojie"></i>扫街记录</a>
						</div>


					</div>
				</div>
				<!--/team-map-->
			</div>
		</div>
		<!-- 模态框（Modal） -->
<div class="modal fade" id="auditModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <h4 class="modal-title" id="myModalLabel">
               扫街审核已通过
            </h4>
         </div>
         <div class="modal-body">
         <input type="hidden" value="" id="salesmanId">
	         <div class="form-group">
	           <span id="regName"></span> 扫街审核已通过，请立即进行考核设置
	         </div>
	         <div class="form-group">
	         将在 <span id="mes">5</span> 秒钟后进入<a href="javascript:auditSet();">考核设置</a>
	         </div>
         </div>
         <div class="modal-footer">
		 </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>
		<!-- Bootstrap core JavaScript================================================== -->
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
		<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="/static/js/jquery/jquery-1.11.3.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="/static/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/static/bootstrap/js/bootstrap-multiselect.js"></script>

		<script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
        <script src="<%=basePath%>static/saojie/saojie-det.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>static/js/common.js" type="text/javascript" charset="utf-8"></script>
		<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
</body>

</html>
