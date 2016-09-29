<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>"/>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>业务等级设置</title>

    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/order-detail/order_detail.css"/>
    <link rel="stylesheet" href="phone-set/css/phone.css">

    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <style>
        .ico-dj {
            background: url("../static/img/level/dj.png") no-repeat center;
        }

        .grd {
            background: #ffffff;
            height: 240px;
        }

        .xxs {
            color: #EEEEEE;
            text-align: center;
            padding: 30px 40px 30px 30px;
            background: url("../static/img/level/xxs.png") no-repeat center;
            font-size: 12px;

        }

        .zxs {
            color: #EEEEEE;
            text-align: center;
            padding: 30px 40px 30px 30px;
            background: url("../static/img/level/zxs.png") no-repeat center;
            font-size: 12px;
        }

        .dxs {
            color: #EEEEEE;
            text-align: center;
            padding: 30px 40px 30px 30px;
            background: url("../static/img/level/dxs.png") no-repeat center;
            font-size: 12px;
        }

        .ini-put {
            width: 63px;
            height: 25px;
            color: #919191;
            font-size: 12px;
        }

        .line-xss {
            height: 87px;
            border-right: 1px solid #eeeeee;
            border-bottom: 1px solid #eeeeee;
        }

        .line-left {
            height: 87px;
            border-bottom: 1px solid #EEEEEE;
        }

        .heigth-m {
            margin-top: 30px;
        }

        .font-s {
            font-size: 12px;
        }

        .ph-center {
            margin-left: 38%;
            margin-top: -80px;
        }
    </style>

</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-dj"></i>业务等级设置
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
        <!--测试用-->
        <a href="javascript:void(0);" class="btn btn-blue" onclick="toAreaSet('1','BRANDMODEL');">
            <i class="ico icon-add"></i>区域设置
        </a>
    </h4>

    <div class="row  grd">
        <div class="col-sm-4 line-xss ">
            <div class="col-sm-4 heigth-m">
                <span class="xxs ">小学生</span>
            </div>

            <div class="col-sm-8 heigth-m ">
                <span class="text-gery font-s">上月销量  < </span>
                <input type="text" class="ini-put" name="salesRange" id="smallSales">
                <span class="text-blue font-s">台</span>
            </div>

        </div>
        <div class="col-sm-4 line-xss">
            <div class="col-sm-4 heigth-m">
                <span class="zxs">中学生</span>
            </div>

            <div class="col-sm-8 heigth-m ">
                <input type="text" class="ini-put" name="salesRange" id="stuSalesMin"><span
                    class="text-blue font-s"> 台</span>
                <span class="text-gery font-s">≤ 上月销量  < </span>
                <input type="text" class="ini-put" name="salesRange" id="stuSalesMax"><span
                    class="text-blue font-s"> 台</span>

            </div>
        </div>
        <div class="col-sm-4 line-left">
            <div class="col-sm-4 heigth-m">
                <span class="dxs">大学生</span>
            </div>
            <div class="col-sm-8 heigth-m ">
                <input type="text" class="ini-put" name="salesRange" id="bigStuSales">
                <span class="text-blue font-s">台</span>
                <span class="text-gery font-s">≤ 上月销量  </span>

            </div>
        </div>


    </div>

    <div class="ph-center">
        <input type="button" class="btn btn-primary col-sm-5" value="确定" onclick="saveLevel();"/>
    </div>
</div>
<script type="text/javascript">
    var base = "<%=basePath%>";
    function saveLevel() {
        $.ajax({
            url: base + "teammember/level/save",
            data: {
                smallSales: $('#smallSales').val(),
                stuSalesMin: $('#stuSalesMin').val(),
                stuSalesMax: $('#stuSalesMax').val(),
                bigStuSales: $('#bigStuSales').val()
            },
            type: "POST",
            dataType: "text",
            success: function (data) {
                if (data == "ok") {
                    alert("设置成功!");
                } else {
                    alert("等级已存在!");
                }
                window.location.href = base + "teammember/salesManList";
            },
            error: function () {
                alert("系统异常，请稍后重试！");
            }
        });
    }

    function toAreaSet(id,type){
        window.location.href = base + "areaAttr/setting?ruleId="+id +"&type=BRANDMODEL";
    }
</script>
</body>
</html>
