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
    <title>月任务</title>
    <!-- Bootstrap -->
    <link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="static/kaohe/kaohe-det.css"/>
    <link rel="stylesheet" type="text/css" href="static/yw-team-member/ywmember.css"/>
    <link rel="stylesheet" type="text/css" href="static/task/detail.css">
    <link rel="stylesheet" type="text/css" href="static/oil/css/oil.css">

</head>

<body>
<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-task-send"></i>月任务
        <!--区域选择按钮-->
        <a class="btn btn-setting" href="javascript:;"><i class="icon-task-sz"></i>设置</a>
        <!--区域选择按钮-->
        <div class="area-choose">
            选择区域：<span>山东省</span>
           <a class="are-line" href="javascript:;"
					onclick="getRegion(${regionId});">切换</a>
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
                        <div class="col-md-9 row-a">
                            <div class=" date-jl">

                                <div class=" date-wzz">
                                    <div class="input-group input-group-sm">
                                    <span class="input-group-addon " id="basic-addon1"><i
                                            class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                                        <input type="text" class="form-control form_datetime input-sm"
                                               placeholder="请选择年-月"
                                               readonly="readonly">
                                    </div>
                                </div>
                            </div>
                            <div class="date-jll"><a class="btn btn-default btn-sm" href="javascrip:;"><span
                                    class="text-bule">筛选</span></a>
                            </div>
                            <div class="date-jlll">
                                 <span class="text-bai">  已派发 &nbsp;<span class="text-bai-d">2</span> &nbsp; 个区域</span>
                            </div>
                        </div>

                        <div class="col-md-2 input-jl ">
                                <div class="input-group ">
                                    <input type="text" class="input-in form-control text-gery-s  " name="truename" id="param"
                                           placeholder="请输入名称或工号"
                                           onkeypress="return check()">
                                    <a class="input-ini input-group-addon " id="goSearch" onclick="">
                                        <i class="icon icon-finds"></i>
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
                    <div class="bs-example" >
                        <table class="table table-hover text-body table-my--">
                            <tr>
                                <th>
                                    <div class="row">
                                        <div class="col-sm-1 pic-jl ">
                                            <div class="bs-example bs-example-images " data-example-id="image-shapes">
                                                <img data-holder-rendered="true" src="static/img/task/mao.png"
                                                     style="width: 50px; height: 50px;" data-src="holder.js/40x40"
                                                     class="img-circle" alt="40x40">
                                            </div>
                                        </div>
                                        <div class="col-sm-11 text-jl">
                                            易小星<span class="text-body">（区域经理）</span><br>
                                            <span class="text-body">山东省滨州市邹县</span>
                                        </div>
                                    </div>

                                </th>
                                <th class="text-right text-body ">
                                    15次拜访： <span class="text-bule">3</span> 家<br>
                                    已完成： <span class="text-red">0</span> 家
                                </th>
                                <th class="text-right text-body">
                                    10次拜访： <span class="text-bule">3</span> 家<br>
                                    已完成： <span class="text-red">0</span> 家
                                </th>
                                <th class="text-right text-body">
                                    7次拜访： <span class="text-bule">3</span> 家<br>
                                    已完成： <span class="text-red">0</span> 家
                                </th>
                                <th class="text-right text-body">
                                    4次拜访： <span class="text-bule">3</span> 家<br>
                                    已完成： <span class="text-red">0</span> 家
                                </th>
                                <th class="text-right text-body">
                                    2次拜访： <span class="text-bule">3</span> 家<br>
                                    已完成： <span class="text-red">0</span> 家
                                </th>
                                <th class="text-right">
                                    <a class="btn btn-blue btn-sm" href="javascrip:;">查看</a>
                                </th>
                            </tr>

                            

                        </table>
                    </div>
                    <!--/列表内容-->
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
	<script src="static/js/jquery/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $('body input').val('');
    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-left",
        forceParse: 0
    });
    var $_haohe_plan = $('.J_kaohebar').width();
    var $_haohe_planw = $('.J_kaohebar_parents').width();
    $(".J_btn").attr("disabled", 'disabled');
    if ($_haohe_planw === $_haohe_plan) {
        $(".J_btn").removeAttr("disabled");
    }
    /*区域 */
	function getRegion(id){
		window.location.href='/region/getPersonalRegion?id='+id+"&flag=monthTaskList";
	}
</script>
</body>

</html>
