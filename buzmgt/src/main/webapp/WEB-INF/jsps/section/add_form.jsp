<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>添加价格区间</title>

    <link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" type="text/css" href="../static/css/income-cash.css">
    <link rel="stylesheet" href="../static/css/phone.css">
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script language="JavaScript" src="../static/js/section/jquery.json.js" ></script>
    <style>
        .table-bordered {
            border: none;
        }

        .ratio-box {
            display: inline-block;
            width: 100%;
            padding: 15px;
            padding-left: 30px;
            border: 1px solid #EEEEEE;

        }
    </style>
    <script type="text/javascript">
        $(function () {
            /**
             *
             */
            $("#create").click(function () {
                var impldate = $("#impldate").val().trim();
                var productionName = $("#productionName").val().trim();
                if (impldate == null || impldate == undefined || impldate == '') {
                    alert('请输入实施日期');
                    return;
                }
                if(productionName == null || productionName == undefined || productionName == ''){
                    alert('请输入方案名称');
                    return;
                }

                var priceRangeArray = new Array();
                var form = $("#acont .frm");
                var type = '${type}';
                var planId = '${planId}';
                console.log(form);

                for (var i = 0; i < form.size(); i++) {
                    var f = $(form[i]).serializeArray();
                    console.log(f);
                    var a = f[0]["value"].trim();
                    if (a == null || a == undefined || "" == a) {
                        alert("输入有误,请核对是否有空值");
                        return
                    }
                    var b = f[1]["value"].trim();
                    if (b == null || b == undefined || "" == b) {
                        alert("输入有误,请核对是否有空值");
                        return
                    }
                    var c = f[2]["value"].trim();
                    if (c == null || c == undefined || "" == c) {
                        alert("输入有误,请核对是否有空值");
                        return
                    }
                    priceRangeArray.push({
                        priceRange: a + "-" + b,
                        percentage: c,
                        priceRangeStatus: "0",
                        serialNumber: i + 1
                    });
                }
                console.log(priceRangeArray);
                $.ajax({
                    url: 'addPriceRanges?implementationDate=' + impldate + '&productionType=' + type +'&planId='+planId + '&productionName=' + productionName,
                    type: "POST",
                    contentType: 'application/json;charset=utf-8',//这是请求头信息
                    dataType: "json",
                    data: $.toJSON(priceRangeArray),//将Json对象序列化成Json字符串，toJSON()需要引用jquery.json.min.js
                    success: function (data) {
                        alert("创建成功");
                        window.location.href = "production/" + data.id+'?planId='+data.planId + '&check=' + ${check} + '&machineName=' + '${machineName}';
                    },
                    error: function () {
                        alert("系统故障");
                    }
                });


            });
        });

        function kkk(v, a) {
            if (!(/^[\d]+\.?\d*$/.test(v))) {
                alert("请输入数字");
                $(a).val('');

            }
        }
    </script>
</head>
<body>
<!---扣罚设置表头-->
<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-form-jage"></i>添加价格区间
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>


    <!-- 扣罚设置 -->
    <div class="table-bordered bg-color">
        <div class="table-responsive " style="overflow-x: hidden">
            <!--公里系数表头-->
            <div class="text-tx row-d" style="margin-top: 10px;margin-bottom: 40px">
                <div>
                    <span class="text-gery">实施日期：</span>

                    <div class="search-date" style="width: 300px">
                        <div class="input-group input-group-sm">
	                <span class="input-group-addon " id="basic-addon1"><i
                            class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                            <input id="impldate" type="text" class="form-control form_datetime input-sm"
                                   placeholder="选择日期"
                                   readonly="readonly">
                        </div>
                    </div>
                    <span class="ph-sm-orange">&nbsp; 注：</span>
                    <span class="ph-sm-gery">0≤区间值＜50</span>
                    &nbsp; &nbsp;
                    <span class="text-gery">方案名称：</span>
                    <input type="text" id="productionName"/>
                </div>
            </div>
            <!--设置提成区间-->
            <div id="acont" class="row">
                <%--<form class="frm">--%>
                <%--<div class="col-sm-3 cl-padd">--%>
                <%--<div class="ratio-box">--%>
                <%--<div class="ratio-box-dd">--%>
                <%--<span class="label  label-blue ">1区间</span>--%>

                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh">--%>
                <%--<span class="ph-text-blue">至</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="50" name="hh">--%>
                <%--<span class="ph-primary ">元</span></div>--%>
                <%--<span class="label  label-blue ">提成</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh">--%>

                <%--</div>--%>
                <%--</div>--%>
                <%--</form>--%>

                <%--<form class="frm">--%>
                <%--<div class="col-sm-3 cl-padd">--%>
                <%--<div class="ratio-box">--%>
                <%--<div class="ratio-box-dd">--%>
                <%--<span class="label  label-blue">2区间</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh" id="hhh" onchange="kkk(this.value)">--%>
                <%--<span class="ph-text-blue">至</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="50" name="hh">--%>
                <%--<span class="ph-primary ">元</span></div>--%>
                <%--<span class="label  label-blue ">提成</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh">--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</form>--%>
                <%--<form class="frm">--%>
                <%--<div class="col-sm-3 cl-padd">--%>
                <%--<div class="ratio-box">--%>
                <%--<div class="ratio-box-dd">--%>
                <%--<span class="label  label-blue">3区间</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh">--%>
                <%--<span class="ph-text-blue">至</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="50" name="hh">--%>
                <%--<span class="ph-primary ">元</span></div>--%>
                <%--<span class="label  label-blue ">提成</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh">--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</form>--%>
                <%--<form class="frm">--%>
                <%--<div class="col-sm-3 cl-padd">--%>
                <%--<div class="ratio-box">--%>
                <%--<div class="ratio-box-dd">--%>
                <%--<span class="label  label-blue">4区间</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh">--%>
                <%--<span class="ph-text-blue">至</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="50" name="hh">--%>
                <%--<span class=" ph-primary">元</span></div>--%>
                <%--<span class="label  label-blue ">提成</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh">--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</form>--%>
                <%--<form class="frm">--%>
                <%--<div class="col-sm-3 cl-padd">--%>
                <%--<div class="ratio-box">--%>
                <%--<div class="ratio-box-dd">--%>
                <%--<span class="label  label-blue">5区间</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh">--%>
                <%--<span class="ph-text-blue">至</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="50" name="hh">--%>
                <%--<span class="ph-primary ">元</span></div>--%>
                <%--<span class="label  label-blue ">提成</span>--%>
                <%--<input type="text" class="ph-inpt" placeholder="0" name="hh">--%>
                <%--</div>--%>
                <%--</div>--%>
                <%--</form>--%>

                <div class="col-sm-3 cl-padd">
                    <div class="ratio-box" style=" padding-bottom: 10px;  padding-top: 12px;">
                        <button class="btn  ph-btn-white ph-primary J_addDire " type="button" href="javascript:;">
                            添加价格区间
                        </button>
                    </div>
                </div>
            </div>
            <div class="form-group ">
                <div class="row">
                    <div class="col-sm-offset-4 col-sm-4" style="margin-top: 50px;margin-bottom: 35px;">
                        <button id="create" type="submit" class="col-sm-12 btn btn-primary text-strong">生成</button>
                    </div>
                </div>
            </div>
        </div>

    </div><!--扣罚设置--->
</div><!---扣罚设置表头-->


<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-switch.min.js"></script>

<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="../static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript">
    var i = 1;

    $('.J_addDire').click(function () {
        var dirHtml = '<form class="frm"><div class="col-sm-3 cl-padd" style="width: 28%">' +
                '<div class="ratio-box">' +
                '<div class="ratio-box-dd">' +
                '<span class="label  label-blue">' + (i++) + '区间' + '</span>' +
                '<input type="text" class="ph-inptt" placeholder="" name="hh" onchange="kkk(this.value,this)" >' +
                '<span class="ph-text-blue">' + '至' + '</span>' +
                '<input type="text" class="ph-inptt"   placeholder="" name="hh" onchange="kkk(this.value,this)">' +
                '<span class="ph-primary ">' + '元' + '</span>' + '&nbsp; &nbsp; <span class="label  label-blue ">提成</span>' +
                '<input type="text" class="ph-inpt" placeholder="" name="hh"  onchange="kkk(this.value,this)">' +
                '</div>' +
                '</div>' +
                '</div></form>';
        $(this).parents('.col-sm-3').before(dirHtml);
    });
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
        pickerPosition: "bottom-right",
        forceParse: 0
    });
</script>

</body>
</html>
