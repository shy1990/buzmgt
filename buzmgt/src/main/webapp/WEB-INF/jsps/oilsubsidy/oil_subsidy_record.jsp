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
        <a class="btn btn-blue btn-sm" href="/oilCost/statistics/{{whatUserId parentId userId}}">查看</a>
      </td>
    </tr>      
	{{/each}}
</script>
<script id="abnormalCoord-table-template" type="text/x-handlebars-template">
	{{#each content}}
   <tr>
      <td class="">
        <img width="50" height="50" src="static/img/abnormal/user-head.png" class="img-circle" />
        <span class="yw-name"><b>{{salesManPart.truename}}</b>({{salesManPart.organizeName}})</span>
      </td>
      <td>
			{{#each oilRecordList}}
				{{{disposeRecordList type regionName}}}
			{{/each}}
      </td>
      <td>
        <p><span class="acu-km">{{distance}}km</span> &#47; <span class="acu-mny">{{oilCost}}元</span></p>
      </td>
      <td>2016.03.12 18:20</td>
      <td>
        <a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
      </td>
    </tr>
	{{/each}}
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
        <i class="ico icon-oil-detail"></i>油补记录
    </h4>
    <div class="row">
        <div class="col-md-9">
            <!--box-->
            <div class="abnormal-body box border blue">
                <!--title-->
                <div class="box-header">
                    <div class="search-date">
                        <div class="input-group input-group-sm">
                            <span class="input-group-addon " id="basic-addon1"><i class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                            <input type="text" class="form-control form_datetime input-sm" placeholder="请选择年-月" readonly="readonly">
                        </div>
                    </div>
                    <a class="screen" href="javascript:;">筛选</a>
                </div>
                <div class="box-body">
                    <div class="marg-t text-time clearfix">
                        <div class="oil-info">
                            <span>本月累计公里数：</span>
                            <span class="oil-km">362.00km</span>
                        </div>
                        <div class="oil-info">
                            <span>累计油补：</span>
                            <span class="oil-mny">100.00元</span>
                        </div>
                        <div class="link-posit pull-right">
                            <a class="table-export" href="javascript:void(0);">导出excel</a>
                        </div>
                    </div>
                    <!--列表内容-->
                    <div class="tab-content">
                        <!--油补记录-->
                        <div class="tab-pane fade in active" id="box_tab1">
                            <!--table-box-->
                            <div class="table-abnormal-list table-overflow">
                                <table class="table table-hover new-table abnormal-table">
                                    <thead>
                                    <tr>
                                        <th>姓名</th>
                                        <th>油补握手顺序</th>
                                        <th>公里数/金额</th>
                                        <th>日期</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tr>
                                        <td>李易峰</td>
                                        <td>
                                            <span>起点 <span class="abnormal-state">异常</span></span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">终点<span class="normal-state">家</span></span>
                                        </td>
                                        <td><span class="oil-km">1300km</span> / <span class="oil-mny">250.36元</span></td>
                                        <td>2016.04.09</td>
                                        <td>
                                            <a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>李易峰</td>
                                        <td>
                                            <span>起点 <span class="abnormal-state">异常</span></span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">终点<span class="normal-state">家</span></span>
                                        </td>
                                        <td><span class="oil-km">1300km</span> / <span class="oil-mny">250.36元</span></td>
                                        <td>2016.04.09</td>
                                        <td>
                                            <a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>李易峰</td>
                                        <td>
                                            <span>起点 <span class="abnormal-state">异常</span></span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">大桥镇</span>
                                            <span class="location">终点<span class="normal-state">家</span></span>
                                        </td>
                                        <td><span class="oil-km">1300km</span> / <span class="oil-mny">250.36元</span></td>
                                        <td>2016.04.09</td>
                                        <td>
                                            <a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <!--table-box-->
                        </div>
                        <!--油补记录-->
                    </div>
                    <!--列表内容-->
                    <!--分页-->
                    <div class="page-index">
                        <nav>
                            <ul class="pagination">
                                <li>
                                    <a href="#" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <li class="active"><a href="#">1</a></li>
                                <li><a href="#">2</a></li>
                                <li><a href="#">3</a></li>
                                <li><a href="#">4</a></li>
                                <li><a href="#">5</a></li>
                                <li>
                                    <a href="#" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    <!--分页-->
                </div>
            </div>
            <!--box-->
        </div>
        <!--col-md-9-->
        <div class="col-md-3">
					<%@ include file="../kaohe/right_member_det.jsp"%>
				</div>
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
		<script type="text/javascript" src="static/oil-subsidy/oil-subsidy-record.js" 
			charset="utf-8"></script>
		<script type="text/javascript">
			
		</script>
</body>

</html>