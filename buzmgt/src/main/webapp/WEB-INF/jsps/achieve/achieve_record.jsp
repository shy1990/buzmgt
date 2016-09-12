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
      <td>{{brand.name}}</td>
      <td>{{good.name}}</td>
      <td class="reason">
				<span class="text-red">{{numberFirst}} | {{numberSecond}} | {{numberThird}}</span>
			</td>
      <td>
				<span class="text-blue">{{formDate startDate}}-{{formDate endDate}}</span>
      </td>
      <td><span class="text-blue">{{formDate issuingDate}}</span></td>
      <td><span class="ph-on">进行中</span>
			<span class="ph-weihes">未使用</span></td>
			<td><span class="text-hong text-strong">待审核</span>
			<span class="text-zi text-strong">被驳回</span>
			<span class="text-lan text-strong">已审核</span></td>
      <td>{{formDate createDate}}</td>
      <td>
        <button class="btn bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看</button>
        <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程</button>
				<button class="btn bnt-sm btn-zz" data-toggle="modal" data-target="#del">修改 </button>
				<button class="btn bnt-sm btn-sc " data-toggle="modal" data-target="#xgywxx"> 删除 </button>
				<button class="btn  bnt-sm bnt-zza" data-toggle="modal" data-target="#">终止</button>
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
    </h4>


    <span class="text-gery text-strong ">按日期筛选：</span>

    <div class="search-date">
        <div class="input-group input-group-sm">
                        <span class="input-group-addon "><i
                                class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
            <input type="text" class="form-control form_datetime input-sm" placeholder="结束日期"
                   readonly="readonly" style="background: #ffffff">
        </div>

    </div>
    -
    <div class="search-date">
        <div class="input-group input-group-sm">
                        <span class="input-group-addon "><i
                                class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
            <input type="text" class="form-control form_datetime input-sm" placeholder="结束日期"
                   readonly="readonly  " style="background: #ffffff;">
        </div>

    </div>
		<button onclick="goSearch();" class="btn  btn-sm bnt-ss ">搜索</button>
    <hr class="hr-solid-sm" style="margin-top: 25px">


    <ul class="nav nav-pills  nav-top" id="myTab">
        <li class="active"><a data-toggle="tab" href="#newon"> &nbsp;当前进行 &nbsp;  </a></li>
        <li><a data-toggle="tab" href="#yguoq">  &nbsp; 已过期 &nbsp; </a></li>
        <li><a data-toggle="tab" href="#yguoq">  &nbsp; 审核 &nbsp; </a></li>
        <li><a data-toggle="tab" href="#yguoq">  &nbsp; 审核中 &nbsp; </a></li>
    </ul>


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
                                    <th>使用状态</th>
                                    <th>修改日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="">
                                <tr>
                                    <td><span class="ph-new">新建</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-hong text-strong">待审核</span></td>
                                    <td><span class="ph-on">进行中</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看
                                        </button>
                                    </td>
                                </tr>


                                <tr>
                                    <td><span class="ph-xinjian">修改</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-zi text-strong">被驳回</span></td>
                                    <td><span class="ph-on">进行中</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn bnt-sm btn-zz" data-toggle="modal" data-target="#del">修改
                                        </button>
                                        <button class="btn bnt-sm btn-sc " data-toggle="modal" data-target="#xgywxx">
                                            删除
                                        </button>

                                    </td>
                                </tr>

                                <tr>
                                    <td><span class="ph-xinjian">修改</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-lan text-strong">已审核</span></td>
                                    <td><span class="ph-weihes">未使用</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看
                                        </button>
                                        <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程
                                        </button>
                                    </td>
                                </tr>


                                <tr>
                                    <td><span class="ph-xinjian">修改</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-lan text-strong">已审核</span></td>
                                    <td><span class="ph-weihes">未使用</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看
                                        </button>
                                        <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程
                                        </button>
                                    </td>
                                </tr>


                                <tr>
                                    <td><span class="ph-xinjian">修改</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-zi text-strong">被驳回</span></td>
                                    <td><span class="ph-on">进行中</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn bnt-sm btn-zz" data-toggle="modal" data-target="#del">修改
                                        </button>
                                        <button class="btn bnt-sm btn-sc " data-toggle="modal" data-target="#xgywxx">
                                            删除
                                        </button>
                                        <button class="btn  bnt-sm bnt-zza" data-toggle="modal" data-target="#">终止</button>
                                    </td>
                                </tr>


                                </tbody>
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
                                    <th>名称</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>审核人</th>
                                    <th>审核状态</th>
                                    <th>使用状态</th>
                                    <th>修改日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td><span class="ph-new">新建</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-lan text-strong">已审核</span></td>
                                    <td><span class="ph-weihes">已过期</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程
                                        </button>
                                        <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看
                                        </button>
                                    </td>
                                </tr>


                                <tr>
                                    <td><span class="ph-xinjian">修改</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-lan text-strong">已审核</span></td>
                                    <td><span class="ph-weihes">已过期</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程
                                        </button>
                                        <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看
                                        </button>
                                    </td>
                                </tr>

                                <tr>
                                    <td><span class="ph-xinjian">修改</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-lan text-strong">已审核</span></td>
                                    <td><span class="ph-weihes">未使用</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程</button>
                                        <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看</button>

                                    </td>
                                </tr>


                                <tr>
                                    <td><span class="ph-xinjian">修改</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-lan text-strong">已审核</span></td>
                                    <td><span class="ph-weihes">已过期</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程
                                        </button>
                                        <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看
                                        </button>
                                    </td>
                                </tr>


                                <tr>
                                    <td><span class="ph-xinjian">修改</span> 渠道201608手机全品牌提成方案50~100区间</td>
                                    <td class="reason">2016.08.01</td>
                                    <td><span class=""> -- -- -- </span></td>
                                    <td>刘强</td>
                                    <td><span class="text-lan text-strong">已审核</span></td>
                                    <td><span class="ph-weihes">已过期</span></td>
                                    <td>2016.08.28-2016.08.29</td>
                                    <td>
                                        <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#">进程
                                        </button>
                                        <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#">查看
                                        </button>

                                    </td>
                                </tr>


                                </tbody>
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
		src="static/achieve/achieve_record.js" charset="utf-8"></script>
	<script type="text/javascript">
		$(".J_MachineType li").on("click",function(){
			$(this).addClass("active");
			$(this).siblings("li").removeClass("active");
		});
		
	</script>
</body>

</html>