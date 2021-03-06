﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>月指标</title>
    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>static/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/month-target/css/mouth.css">
    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/bootStrapPager/css/page.css"/>
    <script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
</head>
<body>
<div class="content main">
    <h4 class="page-header">
        <i class="ico icon-yue"></i>月指标
        <div class="area-choose">

        </div>
        <a class="btn btn-setting" href="/monthTarget/monthSetting"><i class="icon-ratio"></i>月指标</a>
    </h4>
    <!---选择地区-->
    <div>
        <span class="text-gray">选择月份：</span>
        <div class="search-date" style="width: 187px">
            <div class="input-group input-group-sm">
                <span class="input-group-addon " id="basic-addon1"><i
                        class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                <input id="iuputtime" type="text" class="form-control form_datetime input-sm" placeholder="选择日期"
                       readonly="readonly">
            </div>
        </div>
    </div>

    <div class="box-szcs">
        <input id="inputname" class="cs-select  text-gery-hs" placeholder="  请输入业务员名称">
        <button class="btn btn-blue btn-sm" id="searchName">
            检索
        </button>
        <%--<span class="text-gray">选择区域：</span>--%>
        <%--<select class="box-sty-s" style="width: 187px">--%>
        <%--<option>省-市-县</option>--%>
        <%--<option>上海市</option>--%>
        <%--<option>北京市</option>--%>
        <%--</select>--%>

        <%--<button  class="btn btn-blue btn-sm" style="height: 30px;margin-left: 10px">--%>
        <%--检索--%>
        <%--</button>--%>

        <div class="link-posit-t pull-right export">

            <button class="table-export" id="export"  >导出excel</button>
        </div>

    </div>
    <div class="clearfix"></div>
    <div class="tab-content">
        <!--油补记录-->
        <div class="tab-pane fade in active" id="box_tab1">
            <!--table-box-->
            <div class="table-task-list new-table-box table-overflow">
                <table class="table table-hover new-table" style="margin-bottom: 0">
                    <thead>
                    <tr>
                        <th>区域</th>
                        <th>负责人</th>
                        <th>注册商家</th>
                        <th>提货量 <span class="th-td">（实际/指标）</span></th>
                        <th>提货商家 <span class="th-td">（实际/指标）</span></th>
                        <th>活跃商家 <span class="th-td">（实际/指标）</span></th>
                        <th>成熟商家 <span class="th-td">（实际/指标）</span></th>
                        <th>指标周期</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody">
                    <%--<tr>--%>
                    <%--<td>山东省滨州市哈哈县</td>--%>
                    <%--<td>胡老大</td>--%>
                    <%--<td>400</td>--%>
                    <%--<td>500 / 700</td>--%>
                    <%--<td>25 / 50</td>--%>
                    <%--<td>25 / 50</td>--%>
                    <%--<td>25 / 50</td>--%>
                    <%--<td>2016.05</td>--%>
                    <%--<td>--%>
                    <%--<button class="btn btn-blue btn-bn-style">查看</button>--%>
                    <%--</td>--%>
                    <%--</tr>--%>
                    </tbody>
                </table>
            </div>
            <div id="callBackPager"></div>
        </div>
        <!--油补记录-->
    </div>

</div>
<!---alert---->
<div id="daoru" class="modal fade" role="dialog">
    <div class="modal-dialog fang" role="document">
        <div class="modal-content modal-blue yuan ">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h3 class="modal-title">导入表格</h3>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form id="addd" class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">选择日期：</label>
                            <div class="col-sm-7">
                                <div class="input-group are-line">
                                    <span class="input-group-addon"><i class="icon icon-rq"></i></span>
                                    <input name="a" type="text" class="form-control input-hh  form_datetime"
                                           aria-describedby="basic-addon1" type="text" placeholder="请选择年-月-日"
                                           readonly="readonly">
                                    </input>
                                    <!-- /btn-group -->
                                </div>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4 control-label">选择文件：</label>
                            <div class="col-sm-7">
                                <div class="input-group are-line">
                                    <span class="input-group-addon"><i class="icon icon-wj"></i></span>
                                    <select name="b" type="" class="form-control input-h"
                                            aria-describedby="basic-addon1">
                                        <option></option>
                                        <option>20</option>
                                        <option>30</option>
                                        <option>40</option>
                                        <option>50</option>
                                    </select>
                                    <!-- /btn-group -->
                                </div>
                            </div>

                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-4 ">
                                <a herf="javascript:return 0;" onclick="addd(this)"
                                   class="Zdy_add  col-sm-12 btn btn-primary">确定
                                </a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!---alert---->

<![endif]-->
<script src="<%=basePath%>static/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<%--<script src="<%=basePath%>static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>--%>
<script type="text/javascript">
    $('body input').val('');
    $(".form_datetime").datetimepicker({
        format: "yyyy-mm",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 3,
        minView: 3,
        pickerPosition: "bottom-right",
        forceParse: 0
    });

    function select() {
        var $listcon = $('.abnormal-table tbody tr'),
                $unpay = $('.icon-tag-wfk'),
                $payed = $('.payed'),
                $timeout = $('.time-out'),
                $paystatus = $('.J-pay-staus');
        $paystatus.delegate('li', 'click', function () {
            var $target = $(this);
            $paystatus.children('li').removeClass('pay-status-active');
            $target.addClass('pay-status-active');
            $listcon.hide();
            switch ($target.data('item')) {
                case 'all':
                    $listcon.show();
                    break;
                case 'unpay':
                    for (var i = 0; i < $unpay.length; i++) {
                        $($unpay[i]).parents('tr').show();
                    }
                    ;
                    break;
                case 'timeout':
                    for (var i = 0; i < $timeout.length; i++) {
                        $($timeout[i]).parents('tr').show();
                    }
                    ;
                    break;
                case 'payed':
                    for (var i = 0; i < $payed.length; i++) {
                        $($payed[i]).parents('tr').show();
                    }
                    ;
                    break;
                default:
                    break;
            }
        });

    }

    select();
</script>
<script id="tbody-template" type="text/x-handlebars-template">
    {{#each this}}
    <tr>
        <td>
            {{region.parent.parent.parent.parent.name}}{{region.parent.parent.parent.name}}{{region.parent.parent.name}}{{region.name}}
        </td>
        <td>{{trueName}}</td>
        <td>{{matureAll}}</td>
        <td>{{order}} / {{orderNum}}</td>
        <td>{{merchant}} / {{merchantNum}}</td>
        <td>{{active}} / {{activeNum}}</td>
        <td>{{mature}} / {{matureNum}}</td>
        <td>{{targetCycle}}</td>
        <td>
            <button onclick="seeSalseman('{{region.id}}','{{targetCycle}}','{{order}}','{{orderNum}}','{{merchant}}','{{merchantNum}}','{{active}}','{{activeNum}}','{{mature}}','{{matureNum}}');"
                    class="btn btn-blue btn-bn-style">查看
            </button>

        </td>
    </tr>
    {{/each}}
</script>
<script>
    //日期格式化
    Date.prototype.format = function (format) {
        var o = {
            "M+": this.getMonth() + 1, //month
            "d+": this.getDate(), //day
            "h+": this.getHours(), //hour
            "m+": this.getMinutes(), //minute
            "s+": this.getSeconds(), //second
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
            "S": this.getMilliseconds() //millisecond
        }
        if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
                (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1,
                    RegExp.$1.length == 1 ? o[k] :
                            ("00" + o[k]).substr(("" + o[k]).length));
        return format;
    }
    var myDate = new Date().format('yyyy-MM');//今天日期
    //查看业务员的信息
    function seeSalseman(regionId, time, order, orderNum,merchant,merchantNum, active, activeNum,mature,matureNum) {
//        console.log(regionId + '  ' + time + '  ' + order + '  ' + orderNum + '  ' + active + '  ' + activeNum);
//        $.post("/mothTargetData", {regionId: regionId, time: time,order:order,orderNum:orderNum,merchant:merchant,merchantNum:merchantNum,active:active,activeNum:activeNum,mature:mature,matureNum:matureNum});
        window.location.href = '/mothTargetData?regionId='+regionId+'&time='+time+'&order='+order+'&orderNum='+orderNum+'&merchant='+merchant+'&merchantNum='+merchantNum+'&active='+active+'&activeNum='+activeNum+'&mature='+mature+'&matureNum='+matureNum;

//        $.ajax(function(){
//            type:"POST",
//            dataType:"json",
//            url:"default.aspx",//请求页面
//            data:"{id=1}",
//            complete:function(){location.href ="default.aspx"}//跳转页面
//        })


//        $.ajax({
//            type:"GET",
//            url:"/mothTargetData",
//            data:{regionId: regionId, time: time,order:order,orderNum:orderNum,merchant:merchant,merchantNum:merchantNum,active:active,activeNum:activeNum,mature:mature,matureNum:matureNum},
////            complete:function(){location.href ="/mothTargetData"}//跳转页面
//        });


    }
    $(function () {
        //页面初始化
//        console.log(myDate);
        monthTarget.searchData.time = myDate;
        monthTarget.detail.init(monthTarget.searchData);

        //根据姓名和日期检索
        $("#searchName").click(function () {
            var name = $("#inputname").val();
            var time = $("#iuputtime").val();
//            console.log(time+'  time');
            if (time == null || time == undefined || time == '') {
                time = myDate;
            }
            monthTarget.searchData.page = 0;
            monthTarget.searchData.name = name;
            monthTarget.searchData.time = time;
            monthTarget.findByName(monthTarget.searchData);


        });

        //导出
        $("#export").click(function(){
//            console.log(222222);
            window.location.href = "monthTarget/export?time="+monthTarget.searchData.time;
//            monthTarget.exportExcel(monthTarget.searchData.time)
//            $.get("monthTarget/export?time="+monthTarget.searchData.time);
        });


    });
    var monthTarget = {
        //查询条件
        searchData: {
            page: 0,
            size: 20,
            time: '',
            name: ''
        },
        //分页参数
        _count: {
            totalCount: 0,
            limit: 0,
            total: -1
        },

        url: function () {
            return 'monthTarget/monthTargets';
        },
        //查询全部
        findAll: function (searchData) {
//            console.log(searchData);
            $.ajax({
                url: monthTarget.url(),
                data: searchData,
                success: function (data) {
//                    console.log(data);
                    monthTarget.handelerbars_register(data.content);
                    monthTarget._count.totalCount = data.totalElements;//总页数
                    monthTarget._count.limit = data.size;
//                    console.log(monthTarget._count)
                    if (monthTarget._count.totalCount != monthTarget._count.total || monthTarget._count.totalCount == 0) {
                        monthTarget._count.total = monthTarget._count.totalCount;
//                        console.log("-------");
                        monthTarget.initPaging();
                    }

                }

            });
        },
        //根据姓名见检索
        findByName: function (searchData) {
            monthTarget._count.total = -1;
            monthTarget.findAll(searchData);

        },
        //handelerbars填充数据
        handelerbars_register: function (content) {
            var driver_template = Handlebars.compile($("#tbody-template").html());//注册
            $("#tbody").html(driver_template(content));//填充数据

        },
        exportExcel:function(time){
            $.get("monthTarget/export?time="+time);
        },

        //分页工具
        initPaging: function () {
            $('#callBackPager').extendPagination({
                totalCount: monthTarget._count.totalCount,//总条数
                showCount: 5,//下面小图标显示的个数
                limit: monthTarget._count.limit,//每页显示的条数
                callback: function (curr, limit, totalCount) {
                    monthTarget.searchData.page = curr - 1;
                    monthTarget.searchData.size = limit;
                    monthTarget.findAll(monthTarget.searchData);
                }
            });
        },

        detail: {
            init: function (searchData) {
                monthTarget.findAll(searchData);
            }
        }


    }


</script>
</body>
</html>
