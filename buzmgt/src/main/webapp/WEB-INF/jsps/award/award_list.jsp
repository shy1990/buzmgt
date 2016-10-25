<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>提成设置（达量设置）</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/phone-set/css/phone.css">
<link rel="stylesheet" type="text/css"
	href="static/phone-set/css/comminssion.css">
<link rel="stylesheet" type="text/css"
	href="static/award/award.css">
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="award-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
   <tr>
      <td>{{addOne @index}}</td>
      <td>{{#each awardGoods}}{{good.name}}+{{/each}}</td>
      <td class="reason">
				<span class="text-red">{{numberFirst}} 
			{{#if numberSecond}}
			| {{numberSecond}} 
			{{/if}}
			{{#if numberThird}}
			| {{numberThird}}
			{{/if}}
			</span>
			</td>
      <td>
				<span class="text-blue">{{formDate startDate}}-{{formDate endDate}}</span>
      </td>
      <td><span class="text-blue">{{formDate issuingDate}}</span></td>
      <td><span class="ph-on">进行中</span></td>
      <td>{{formDate createDate}}</td>
      <td>
        <a href="/award/list/{{awardId}}" class="btn bnt-sm bnt-ck">查看</a>
        <a href="/award/course/{{awardId}}" class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程</a>
        <button class="btn btn-sm btn-sc " onclick="delAchieve({{awardId}})">删除</button>
      </td>
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
		'size' : '20'
	}
</script>
</head>
<body>

	<div class="content main">
		<jsp:include page="../achieve/income_set_menu.jsp"></jsp:include>
		<div class="row">
			<!--col begin-->
			<div class="col-md-12">
				<!--orderbox begin-->
				<div class="order-box">
					<!--左侧导航开始-->
<!-- 					<div style="padding-left: 0"> -->
<!-- 						<div class=" sidebar left-side" -->
<!-- 							style="padding-top: 0; margin-top: 5px"> -->
<!-- 							<ul class="nav nav-sidebar menu J_MachineType"> -->
<!-- 								<li><i class="ico ico-fl"></i>请选择类别</li> -->
<%-- 								<c:forEach items="${machineTypes}" var="type" varStatus="status"> --%>
<%-- 								  <c:choose> --%>
<%-- 								  	<c:when test="${status.index eq 0 }"> --%>
<%-- 											<li class="active" title="${type.code }">${type.name }</li> --%>
<%-- 								  	</c:when> --%>
<%-- 								  	<c:otherwise> --%>
<%-- 											<li title="${type.code }">${type.name }</li> --%>
<%-- 								  	</c:otherwise> --%>
<%-- 								  </c:choose> --%>
<%-- 								</c:forEach> --%>
<!-- 							</ul> -->
<!-- 						</div> -->
<!-- 					</div> -->
					<!--左侧导航结束-->
					<div class="tab-content">
						<!--右侧内容开始-->
						<!--品牌型号-->

						<!--达量奖励设置-->
						<div class="tab-pane fade in active right-body" id="dljl">
							<div class="ph-btn-set">
								<a href="javascript:add();" class="btn ph-blue"> <i class="ico icon-xj"></i>
									<span class="text-gery">新建奖励</span>
								</a> <a href="JavaScript:record();" class="btn ph-blue" style="margin-right: 30px">
									<i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
								</a>
								<div class="link-posit pull-right">
									<input id="searchGoodsname" class="input-search" type="text"
										placeholder="模糊查询请输入品牌型号">
									<button onclick="goSearch();" class="btn  btn-sm bnt-ss ">搜索</button>
									<a class="table-export" href="javascript:void(0);">导出excel</a>
								</div>
							</div>

							<div class="table-task-list new-table-box table-overflow"
								style="margin-left: 20px">
								<table class="table table-hover new-table">
									<thead>
										<tr>
											<th>序号</th>
											<th>名称</th>
											<th>指标</th>
											<th>方案起止日期</th>
											<th>佣金发放</th>
											<th>状态</th>
											<th>设置日期</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="awardList">
									</tbody>
								</table>
							</div>
							<div id="initPager"></div>

						</div>

						<!--叠加设置-->

						<!--达量奖励-->

					</div>

				</div>

			</div>

		</div>

	</div>
	<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<script type="text/javascript" src="static/js/common.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/js/dateutil.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript"
		src="static/award/award_list.js" charset="utf-8"></script>
	<script type="text/javascript">
		$(".J_MachineType li").on("click",function(){
			$(this).addClass("active");
			$(this).siblings("li").removeClass("active");
		});
		
	</script>
</body>

</html>