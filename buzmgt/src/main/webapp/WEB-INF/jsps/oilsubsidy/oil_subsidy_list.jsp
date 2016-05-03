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
<base href="<%=basePath%>" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>油补统计列表</title>
<!-- Bootstrap -->
<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="static/yw-team-member/team-member.css" />
<link rel="stylesheet" type="text/css"
	href="static/abnormal/abnormal.css" />
<link rel="stylesheet" type="text/css"
	href="static/bootStrapPager/css/page.css" />
<link rel="stylesheet" type="text/css" href="static/oil-subsidy/oil_subsidy_list.css" />
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<script id="oilCost-table-template" type="text/x-handlebars-template">
	{{#if content}}
	{{#each content}}
		<tr>
      <td>
        <img width="50" height="50" src="static/img/abnormal/user-head.png" class="img-circle" />
        <span class="yw-name"><b>{{salesManPart.truename}}</b>({{salesManPart.organizeName}})</span>
      </td>
      <td>{{salesManPart.regionName}}</td>
      <td>
        <p class="acu-km">{{totalDistance}}<span> km</span></p>
      </td>
      <td>
        <p class="acu-mny">{{oilTotalCost}}<span> 元</span></p>
      </td>
      <td>
        <a class="btn btn-blue btn-sm" href="javascript:void(0);" 
				onclick="turnRecord('{{whatUserId parentId userId}}','{{oilTotalCost}}','{{totalDistance}}');">查看</a>
      </td>
    </tr>      
	{{/each}}
	{{else}}
	<tr>
		<td colspan="100">没有相关数据</td>
	</tr>
	{{/if}}
</script>
<script id="abnormalCoord-table-template" type="text/x-handlebars-template">
{{#if content}}
	{{#each content}}
   <tr>
      <td class="">
        <img width="50" height="50" src="static/img/abnormal/user-head.png" class="img-circle" />
        <span class="yw-name"><b>{{salesManPart.truename}}</b>({{salesManPart.organizeName}})</span>
      </td>
      <td class="normal">
			{{#each oilRecordList}}
				{{{disposeRecordList regionType regionName exception}}}
			{{/each}}
      </td>
      <td>
        <p><span class="acu-km">{{distance}}km</span> &#47; <span class="acu-mny">{{oilCost}}元</span></p>
      </td>
      <td>2016.03.12 18:20</td>
      <td>
        <a class="btn btn-blue btn-sm" href="/oilCost/detail/{{id}}">查看</a>
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
		'page':'0',
		'size':'10'
	}
</script>
</head>

<body>
	<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-oil"></i>油补统计
        <a class="btn btn-setting" href="/oil/toOilSetList"><i class="icon-ratio"></i>设置</a>
        <!--区域选择按钮-->
        <div class="area-choose">
            选择区域：<span>${regionName }</span>
            <a class="are-line" onclick="getRegion(${regionId});" href="javascript:;">切换</a>
           	<input type="hidden" id="regionId" value="${regionId }">
           	<input type="hidden" id="regionType" value="${regionType }">
        </div>
        <!--/区域选择按钮-->
    </h4>
    <div class="row">
        <div class="col-md-12">
            <!--box-->
            <div class="abnormal-body box border blue">
                <!--title-->
                <div class="box-title">
                    <!--菜单栏-->
                    <ul id="oilCostStatus" class="nav nav-tabs">
                        <li class="active" data-tital="all"><a href="#box_tab1" data-toggle="tab"><span class="">全部</span></a></li>
                        <li data-tital="coords"><a href="#box_tab2" data-toggle="tab"><span class="">异常坐标</span></a></li>
                    </ul>
                    <!--/菜单栏-->
                </div>
                <!--title-->
                <!--box-body-->
                <div class="box-body">
                    <div class="marg-t text-time">
                        <span class="text-strong chang-time">选择日期：</span>
                        <div class="search-date">
                            <div class="input-group input-group-sm form_date_start">
                                <span class="input-group-addon " id="basic-addon1"><i class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                                <input type="text" id="startTime" class="form-control form_datetime input-sm" placeholder="开始日期" readonly="readonly">
                            </div>
                        </div> --
                        <div class="search-date">
                            <div class="input-group input-group-sm form_date_end">
                                <span class="input-group-addon " id="basic-addon1"><i class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                                <input type="text" id="endTime" class="form-control form_datetime input-sm" placeholder="结束日期" readonly="readonly">
                            </div>
                        </div>
                        <!--考核开始时间-->
                        <button class="btn btn-blue btn-sm" onclick="goSearch();"> 检索 </button>
                        <!---->
                        <div class="link-posit pull-right">
                            <a class="table-export" href="javascript:void(0);">导出excel</a>
                        </div>
                    </div>
                    <!--列表内容-->
                    <div class="tab-content">
                        <!--油补列表全部-->
                        <div class="tab-pane fade in active" id="box_tab1">
                            <!--table-box-->
                            <div class="table-abnormal-list table-overflow">
                                <table class="table table-hover new-table abnormal-table">
                                    <thead>
                                    <tr>
                                        <th>业务名称</th>
                                        <th>负责区域</th>
                                        <th>累计公里数</th>
                                        <th>累计油补金额</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="oilCostList"></tbody>
                                </table>
                            </div>
                            <!--table-box-->
                            <!-- 分页 -->
														<div id="oilCostPager"></div>
														<!-- 分页 -->
                        </div>
                        <!--油补列表全部-->

                        <!--油补列表异常坐标-->
                        <div class="tab-pane fade" id="box_tab2">
                            <!--table-box-->
                            <div class="table-abnormal-list table-overflow">
                                <table class="table table-hover new-table abnormal-table">
                                    <thead>
                                    <tr>
                                        <th>业务名称</th>
                                        <th>油补握手顺序</th>
                                        <th>公里数/金额</th>
                                        <th>日期</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="abnormalCoordList"></tbody>
                                </table>
                            </div>
                            <!--table-box-->
                            <!-- 分页 -->
														<div id="abnormalCoordPager"></div>
														<!-- 分页 -->
                        </div>
                        <!--油补列表异常坐标-->
                    </div>
                    <!--列表内容-->
                </div>
                <!--box-body-->
            </div>
            <!--box-->
        </div>
        <!--col-md-12-->
    </div>
		<!--row-->
		<!-- Bootstrap core JavaScript================================================== -->
		<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
		<script type="text/javascript" src="static/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script type="text/javascript" src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script src="/static/js/dateutil.js" type="text/javascript"
			charset="utf-8"></script>
		<script type="text/javascript" src="static/js/common.js" charset="utf-8"></script>
		<script type="text/javascript" src="static/js/handlebars-v4.0.2.js" charset="utf-8"></script>
		<script type="text/javascript" src="static/bootStrapPager/js/extendPagination.js"></script>
		<script type="text/javascript" src="static/oil-subsidy/oil-subsidy-list.js" 
			charset="utf-8"></script>
		<script type="text/javascript">
			
		</script>
</body>

</html>