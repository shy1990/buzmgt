<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>提交价格区间</title>

    <link href="<%=basePath%>static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="<%=basePath%>static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>static/multiselect/css/jquery.multiselect.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>static/css/common.css">
    <link rel="stylesheet" href="<%=basePath%>/static/css/phone.css">


    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <style>

        .nav-pills > li.active > a, .nav-pills > li.active > a:focus, .nav-pills > li.active > a:hover {
            background: #f5f5f5;
            color: #2b86ba;
            font-weight: bold;
            border-left: 3px solid #1695de;
        }

        .nav-pills > li.active > a, .nav-pills > li.active > a:focus, .nav-pills > li.active > a:hover {
            background: #f5f5f5;
            color: #2b86ba;
            font-weight: bold;
        }

        .nav > li > a {
            position: relative;
            display: block;
            padding: 0 10px;
        }

        .sj-ms-btn-ok {
            margin-top: 0px;
            margin-bottom: 0px;
            text-align: center;
            background-color: #eee;
            padding-top: 5px;
            border-bottom-width: 0px;
            padding-bottom: 5px;
        }

        .sj-ms-btn-ok > button {
            padding-right: 15px;
            padding-left: 15px;
            background-color: #fff;
            border: 1px solid #aaaaaa;
            color: #555555;
        }

        .ph-icon {
            height: 30px;
            padding: 5px 10px;
            font-size: 12px;
            line-height: 1.5;
            border-radius: 3px;
        }

        .icon-riz {
            background: url("<%=basePath%>/static/phone-set/img/rizi.png") no-repeat center;
        }

        .ph-hr {
            clear: both;
            margin: 15px -20px;
            border-top: 1px solid #DBDBDB;
        }

        .ico-form-up {
            background: url("<%=basePath%>/static/phone-set/img/tjxx.png") no-repeat center;
        }
    </style>
    <script type="text/javascript">
        $(function(){
            //点击确定走到审核状态
            $("#sure").click(function () {
                var id = '${production.productionId}'.trim();
                var type = '${production.productionType}';
                console.log(id)
                var auditor = "345345345345";
                var status = '1';
                $.ajax({
                    url:'<%=basePath%>section/toReview',
                    type:'GET',
                    data:{id:id,auditor:auditor,status:status},
                    success:function(data){
                        window.location.href='<%=basePath%>section/toNotExpiredJsp?type='+type;
                    },
                    error:function () {
                      alert("系统故障:嘴哥给吃了");
                    }
                })
            });

        });

    </script>
</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-form-up"></i>提交信息
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>

    <div class="form-group" style="margin-top: 40px;">
        <label class="col-sm-2 control-label ph-text-headline">指派审核人员：</label>
        <div class="col-sm-6 input-search">
            <div class="input-group are-line">
                <span class="input-group-addon "><i class="icon-s icon-man"></i></span>
                <!--<input type="text" class="form-control" placeholder="请选择指派审核人员" aria-describedby="basic-addon1">-->
                <div class="inpt-search">
                    <form>
                        <select name="basic[]" multiple="multiple" class="form-control demo3">

                            <option value="UT">胡老大</option>
                            <option value="VT">横额啊</option>
                            <option value="VA">张二啦</option>
                            <option value="VA">王晓晓</option>
                            <option value="WV">杭大大</option>
                            <option value="WV">曹大大</option>
                            <option value="WI">槽大小</option>
                        </select>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <hr class="ph-hr">
    <p><span class="text-gery text-strong" style="margin-left: 12px;margin-bottom: 30px">类别：</span> <span
            class="text-blue text-strong">智能机</span></p>
    <div class="row">


        <div class="col-md-12">
            <!--orderbox begin-->
            <div class="order-box">
                <!--表格开始-->
                <div class="tab-content">
                    <!--待审核账单-->
                    <div class="tab-pane fade in active">
                        <!--table-box-->
                        <div class="table-task-list new-table-box table-overflow">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>价格区间</th>
                                    <th>提成额度</th>
                                    <th>实施日期</th>
                                    <th>结束日期</th>
                                    <th>区域属性</th>
                                    <th>设置日期</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${production.priceRanges}" var="priceRange">
                                    <tr>
                                        <td>${priceRange.serialNumber}</td>
                                        <td>${priceRange.priceRange}元</td>
                                        <td>
                                            <span class="text-red">${priceRange.percentage}</span> 元/台
                                        </td>
                                        <td>${priceRange.implementationDate}</td>
                                        <td>${priceRange.endTime}</td>
                                        <td><a href="">添加区域设置</a></td>
                                        <td>${priceRange.priceRangeCreateDate}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!--table-box-->
                    </div>
                    <!--待审核账单-->
                </div>
                <!--表格结束-->
                <!--右侧内容结束-->
            </div>
        </div>
    </div>


    <div class="widget-register widget-welcome-question mt20 hidden-xs widget-welcome widget-register-slideUp"
         style=" margin-left: -30px;height: 70px">
        <div class="container">
            <div class="row flex-vertical-center">
                <div class="form-group ">
                    <div class="row" style="height: 70px">
                        <div class="col-sm-offset-4 col-sm-4" style="margin-top: 50px;margin-bottom: 35px;">
                            <button type="submit" class="col-sm-12 btn btn-primary text-strong"
                                    style="margin-top: -25px" id="sure">确认提交
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath%>static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="<%=basePath%>static/js/H-select.js"></script>
<script>

    $('select[multiple].demo3').multiselect({
        columns: 1,
        placeholder: '请选择指派审核人员',
        search: true,
        selectGroup: true
    });


    $(".form_datetime").datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-right",
        forceParse: 0
    });

</script>
</body>
</html>
