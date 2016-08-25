<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>个人月指标进度详情</title>
    <!-- Bootstrap -->
    <link href="<%=basePath%>static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="<%=basePath%>static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/kaohe/kaohe-det.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/yw-team-member/team-member.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/yw-team-member/ywmember.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/month-target/css/mouth.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>static/task/task.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/bootStrapPager/css/page.css" />
    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
    <script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>

</head>

<body>
<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-ywtc"></i>个人销售提成详情
    </h4>
    <div class="row">
        <div class="col-md-9">
            <!--box-->
            <div class="abnormal-body box border ">
                <!--指标表头-->
                <div class="head-box">
                    <div class="head-blue"></div>
                    <div class="box-infor">
                        <div class="box-left">
                            <div class="box-center">
                                <p><span class="text-box-in">${time}</span></p>
                                <p><span class="text-box-in">指标</span></p>
                            </div>

                            <div class="box-text">
                                <p>提货量指标： &nbsp; <span>${orderNum}台&nbsp;&nbsp;</span> &nbsp; &nbsp;已达成： ${order}台</p>
                                <p>商家指标： &nbsp; <span>${merchantNum}家&nbsp;&nbsp;</span> &nbsp; &nbsp;已达成： ${merchant}家</p>

                            </div>
                            <div class="box-text" style="margin-left: 400px;margin-top:-80px">
                                <p>活跃商家指标： <span class="text-blue">${activeNum} 家&nbsp;</span> &nbsp;&nbsp;&nbsp; 已达成：<span
                                        class="text-blue"> ${active}家</span></p>
                                <p>成熟商家指标： &nbsp; <span>${matureNum}家&nbsp;&nbsp;</span> &nbsp; &nbsp;已达成： ${mature}家</p>
                            </div>


                        </div>
                    </div>
                </div>
                <!--日期、筛选、导出-->
                <div class="input-m">
                    <div class="search-date">
                        <div class="input-group input-group-sm">
	                <span class="input-group-addon " id="basic-addon1"><i
                            class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                            <input id="searchTime" type="text" class="form-control form_datetime input-sm" placeholder="选择日期"
                                   readonly="readonly" style="background-color: #FFFFFF">
                        </div>
                    </div>

                    <button id="listByDate" class="btn btn-sx " style="padding:  3px 12px 3px 10px">筛选</button>
                </div>


                <div class="link-posit-t pull-right export" style="margin-top: -55px">
                    <input id="searchName" class="cs-select  text-gery-hs" placeholder="请输入商家名称">
                    <button class="btn btn-sx " style="padding:  3px 12px 3px 10px" id="listByName">
                        检索
                    </button>
                    <button class="table-export" onclick="exportExcel()">导出excel</button>
                </div>


                <div class="clearfix"></div>

                <div class="table-task-list new-table-box table-overflow">
                    <table class="table table-hover new-table">
                        <thead>
                        <tr>
                            <th>商家名称</th>
                            <th>提货量 <i class="ico  icon-sx icon-sx"></i> <i class="ico  icon-sx-a "></i> </th>
                            <th>提货次数 <i class="ico  icon-sx icon-sx"></i> <i class="ico  icon-sx-a "></i></th>
                            <th>联系方式</th>
                            <th>所属区域</th>
                            <th>日期</th>
                        </tr>
                        </thead>
                        <tbody id="tbody">
                        <%--<tr>--%>
                            <%--<td><a href="">历下区冠之霖卖场泉城店</a></td>--%>
                            <%--<td>200</td>--%>
                            <%--<td>2</td>--%>
                            <%--<td>--%>
                                <%--18855514445--%>
                            <%--</td>--%>
                            <%--<td>历下区泉城路</td>--%>
                            <%--<td>2016.06.01-2016.07.05</td>--%>
                        <%--</tr>--%>




                        </tbody>
                    </table>
                </div>
                <div id="callBackPager"></div>
                <!--title-->
                <!--box-body-->
                <div class="box-body-new">

                </div>
                <!--box-body-->
            </div>
            <!--box-->
        </div>
        <!--col-md-9-->
        <div class="col-md-3">
            <!--box-->
            <!--不同阶段颜色不同1：pink 2：yellow 3:violet 4:-->
            <div class="ywmamber-msg box border pink">
                <!--title-->
                <div class="box-title">
                    <i class="icon icon-time"></i>考核中
                </div>
                <!--box-body-->
                <div class="box-body">
                    <!--ywmamber-body-->
                    <div class="ywmamber-body">
                        <img width="80" src="<%=basePath%>static/img/user-head.png" alt="..." class="img-circle">
                        <div class="msg-text">
                            <h4>易小星</h4>
                            <p>ID: A236743252</p>
                            <p>电话: 12547346455</p>
                        </div>
                    </div>
                    <!--/ywmamber-body-->
                    <div class="stage">
                        <span class="kaohe-stage onekaohe-stage">第一阶段:60% </span>
                    </div>
                    <div class="progress progress-sm">
                        <div style="width: 60%;" class="progress-bar bar-kaohe"></div>
                    </div>
                    <div class="operation">
                        <a href="saojie_upd.html" class="">考核设置</a>
                        <a href="kaohe_det.html" class="pull-right">查看</a>
                    </div>
                    <div class="yw-text">
                        入职时间:<span> 2015.09.21</span>
                        <br/> 负责区域:
                        <span>山东省滨州市邹平县</span>
                    </div>
                    <!--拜访任务-->
                    <div class="visit">
                        <button class="col-xs-12 btn btn-visit" href="javascript:;"><i class="ico icon-add"></i>拜访任务
                        </button>
                    </div>
                    <!--拜访任务-->
                    <!--操作-->
                    <div class="operation">
                        <a href="javascript:;" class="">账户设置</a>
                        <a href="javascript:;">冻结账户</a>
                    </div>
                    <!--操作-->
                </div>
                <!--box-body-->
            </div>
            <!--box-->
            <!--业务外部链接-->
            <div class="yw-link">
                <a class="link-oper" href="javascript:;"><i class="icon icon-user"></i>个人资料</a>
                <a class="link-oper" href="javascript:;"><i class="icon icon-income"></i>收益</a>
                <a class="link-oper" href="javascript:;"><i class="icon icon-task"></i>任务</a>
                <a class="link-oper" href="javascript:;"><i class="icon icon-log"></i>日志</a>
                <a class="link-oper" href="javascript:;"><i class="icon icon-footprint"></i>足迹</a>
                <a class="link-oper" href="javascript:;"><i class="icon icon-signin"></i>签收记录</a>
                <a class="link-oper" href="javascript:;"><i class="icon icon-saojie"></i>扫街记录</a>
            </div>

        </div>
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
<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
    $(".form_datetime").datetimepicker({
        format: "yyyy-mm",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 3,
        minView: 3  ,
        pickerPosition: "bottom-right",
        forceParse: 0
    });

    var $_haohe_plan = $('.J_kaohebar').width();
    var $_haohe_planw = $('.J_kaohebar_parents').width();
    $(".J_btn").attr("disabled", 'disabled');
    if ($_haohe_planw === $_haohe_plan) {
        $(".J_btn").removeAttr("disabled");
    }

</script>
<script id="tbody-template" type="text/x-handlebars-template">
    {{#each this}}
    <tr>
        <td>{{shopName}}</td>
        <td>{{numsOne}}</td>
        <td>{{count}}</td>
        <td>{{phoneNmu}}</td>
        <%--<td>{{regionName}}</td>--%>
        <%--<td>{{region.parent.parent.parent.parent.name}}{{region.parent.parent.parent.name}}{{region.parent.parent.name}}{{region.name}}</td>--%>
        <td>{{region.name}}</td>
        <td>{{time}}</td>
        <td></td>
    </tr>
    {{/each}}
</script>
<script type="text/javascript">
    //日期格式化
    Date.prototype.format =function(format)
    {
        var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(), //day
            "h+" : this.getHours(), //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3), //quarter
            "S" : this.getMilliseconds() //millisecond
        }
        if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
                (this.getFullYear()+"").substr(4- RegExp.$1.length));
        for(var k in o)if(new RegExp("("+ k +")").test(format))
            format = format.replace(RegExp.$1,
                    RegExp.$1.length==1? o[k] :
                            ("00"+ o[k]).substr((""+ o[k]).length));
        return format;
    }
    var myDate = new Date().format('yyyy-MM');
    function exportExcel(){
        window.location.href = 'mothTargetData/export/'+'${regionId}'+'/'+'${time}';

    }

    $(function () {
        monthTargetData.searchData.time = '${time}';
        monthTargetData.searchData.regionId = '${regionId}';
        //页面初始化
        monthTargetData.detail.init(monthTargetData.searchData);

        //商家名称检索
        $("#listByName").click(function(){
            var name = $("#searchName").val();
            console.log(name+"========");
            monthTargetData.searchData.name = name;
            monthTargetData.searchData.page = 0;
            monthTargetData.findByName(monthTargetData.searchData);

        });

        //根据时间检索
        $("#listByDate").click(function(){
            var searchTime = $("#searchTime").val();
            monthTargetData.searchData.time = searchTime;
            monthTargetData.searchData.page = 0;
            console.log(monthTargetData.searchData);
            monthTargetData.findByDate(monthTargetData.searchData);

        });

    });

    var monthTargetData = {
        //初始化参数
        searchData: {
            page: 0,
            size: 20,
            time: '',
            name: '',
            regionId:''
        },
        //分页参数
        _count: {
            totalCount: 0,
            limit: 0,
            total: -1
        },

        //调用的url
        url: {
            listAll: function () {
                return '/mothTargetData/mothTargetDatas';
            }
        },
        //查询全部的方法
        findAll: function (searchData) {
            $.ajax({
                url: monthTargetData.url.listAll(),
                data: searchData,
                success: function (data) {
//                    console.log(123);
//                    console.log(data);
                    monthTargetData.handelerbars_register(data.content);
                    monthTargetData._count.totalCount = data.totalElements;//总页数
                    monthTargetData._count.limit = data.size;
                    if (monthTargetData._count.totalCount != monthTargetData._count.total || monthTargetData._count.totalCount == 0) {
                        monthTargetData._count.total = monthTargetData._count.totalCount;
                        monthTargetData.initPaging();
                    }

                }
            });
        },
        //根据商家名检索
        findByName:function(searchData){
            monthTargetData._count.total = -1;
            monthTargetData.findAll(searchData);

        },
        findByDate:function(searchData){
            monthTargetData._count.total = -1;
            monthTargetData.findAll(searchData);
        },

        //handelerbars填充数据
        handelerbars_register:function(content){
            var driver_template = Handlebars.compile($("#tbody-template").html());//注册
            $("#tbody").html(driver_template(content));//填充数据

        },
        //分页工具
        initPaging: function () {
            $('#callBackPager').extendPagination({
                totalCount: monthTargetData._count.totalCount,//总条数
                showCount: 5,//下面小图标显示的个数
                limit: monthTargetData._count.limit,//每页显示的条数
                callback: function (curr, limit, totalCount) {
                    monthTargetData.searchData.page = curr - 1;
                    monthTargetData.searchData.size = limit;
                    monthTargetData.findAll(monthTargetData.searchData);
                }
            });
        },


        //页面初始化
        detail: {
            init: function (args) {
//                console.log(args);
                monthTargetData.findAll(args);
            }

        }

    }
</script>
</body>

</html>