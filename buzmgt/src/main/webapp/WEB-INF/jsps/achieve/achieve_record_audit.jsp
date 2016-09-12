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
<title>提成设置</title>

<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" href="static/earnings/css/phone.css">
<link rel="stylesheet" href="static/earnings/css/comminssion.css">
<link rel="stylesheet" type="text/css" href="static/achieve/achieve.css">
<link rel="stylesheet" type="text/css" href="static/bootStrapPager/css/page.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="achieve-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
   <tr>
      <td>{{addOne @index}}</td>
      <td>{{good.name}}</td>
      <td class="reason">
				<span class="text-red">{{numberFirst}} | {{numberSecond}} | {{numberThird}}</span>
			</td>
      <td>
				<span class="text-blue">{{formDate startDate}}-{{formDate endDate}}</span>
      </td>
			<td>{{auditor}}</td>
			<td>
			{{#myIf status 'BACK'}}
			<span class="text-zi text-strong">被驳回</span>
			{{/myIf}}
			{{#myIf status 'WAIT'}}
			<span class="text-hong text-strong">待审核</span>
			{{/myIf}}
			{{#myIf status 'OVER'}}
			<span class="text-lan text-strong">已审核</span></td>
			{{/myIf}}
      <td><span class="text-blue">{{formDate issuingDate}}</span></td>
      <td>
			{{#compareDate startDate endDate}}	
			<span class="ph-on">进行中</span>
			{{else}}
			<span class="ph-weihes">未使用</span>
			{{/compareDate}}
			</td>
      <td>{{formDate createDate}}</td>
      <td>
        <a href="/achieve/list/{{achieveId}}" class="btn bnt-sm bnt-ck">查看</a>
				{{#isAuditor auditor}} 
				{{#myIf status 'WAIT'}}
        <button class="btn bnt-sm bnt-jc" onclick="auditAchieve({{achieveId}},'OVER')">审核</button>
        <button class="btn bnt-sm btn-sc" onclick="auditAchieve({{achieveId}},'BACK')">驳回</button>
				{{/myIf}}
				{{/isAuditor}}
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
    <h4 class="page-header">
        <i class="ico ico-tcsz"></i>设置记录
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
        <input id="planId" hidden="hidden" value="${planId }">
        <input id="userId" hidden="hidden" value="${userId }">
    </h4>


    <span class="text-gery text-strong ">创建日期：</span>

    <div class="search-date">
        <div class="input-group input-group-sm form_date_start">
                        <span class="input-group-addon "><i
                                class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
            <input type="text" class="form-control form_datetime input-sm J_startDate" placeholder="开始日期"
                   readonly="readonly" style="background: #ffffff">
        </div>

    </div>
    -
    <div class="search-date">
        <div class="input-group input-group-sm form_date_end">
                        <span class="input-group-addon "><i
                                class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
            <input type="text" class="form-control form_datetime input-sm J_endDate" placeholder="结束日期"
                   readonly="readonly  " style="background: #ffffff;">
        </div>

    </div>
		<button onclick="goSearchByCreateDate();" class="btn  btn-sm bnt-ss ">搜索</button>
    <hr class="hr-solid-sm" style="margin-top: 25px">


		<div class="ph-btn-set">
			<ul class="nav nav-pills  nav-top" id="myTab">
			
				<li class="active" title="going"><a data-toggle="tab" href="#newon">
						&nbsp;当前进行 &nbsp; </a></li>
				<li title="past"><a data-toggle="tab" href="#yguoq"> &nbsp; 已过期 &nbsp; </a></li>
				
				<div class="link-posit pull-right posit-xy">
					<input id="searchGoodsname" class="input-search" type="text"
						placeholder="模糊查询请输入品牌型号">
					<button onclick="goSearch();" class="btn  btn-sm bnt-ss ">搜索</button>
					<a class="table-export" href="javascript:void(0);">导出excel</a>
				</div>
			</ul>
		</div>

    <div class="row">
        <div class="col-md-12">
            <div class="order-box">
                <div class="tab-content">
                    <!--当前进行-->
                    <div class="tab-pane fade  in active  " id="newon">
                        <!--导航开始-->
                        <div class=" new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>名称</th>
                                    <th>指标</th>
                                    <th>有效期</th>
                                    <th>审核人</th>
                                    <th>审核状态</th>
                                    <th>奖金发放日期</th>
                                    <th>使用状态</th>
                                    <th>创建日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="goingAchieveList"> </tbody>
                            </table>
                        </div>

                    </div>


                    <!--已过期-->

                    <div class="tab-pane fade  " id="yguoq">
                        <!--导航开始-->

                        <div class=" new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>名称</th>
                                    <th>指标</th>
                                    <th>有效期</th>
                                    <th>审核人</th>
                                    <th>审核状态</th>
                                    <th>奖金发放日期</th>
                                    <th>使用状态</th>
                                    <th>创建日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="pastAchieveList"> </tbody>
                            </table>
                        </div>

                    </div>

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
	<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="static/js/dateutil.js" type="text/javascript"
		charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript"
<!-- 		src="static/achieve/achieve_record.js" charset="utf-8"></script> -->
	<script type="text/javascript">
		$(".J_MachineType li").on("click",function(){
			$(this).addClass("active");
			$(this).siblings("li").removeClass("active");
		});
		
	</script>
</body>

</html>