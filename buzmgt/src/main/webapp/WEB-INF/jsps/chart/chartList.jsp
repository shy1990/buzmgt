<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>总额列表</title>

    <link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" href="../static/css/chartlist/chart.css">
    <link href="//cdn.bootcss.com/easy-pie-chart/1.2.5/jquery.easy-pie-chart.css" rel="stylesheet">

    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
   
    <script src="//cdn.bootcss.com/easy-pie-chart/1.2.5/jquery.easy-pie-chart.js"></script>
    <!--	<script src="js/jquery.easy-pie-chart.js" type="text/javascript"></script>-->

</head>
<body>
<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-ze"></i>总额列表
        <!--区域选择按钮-->
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
			<div class="area-choose">
				选择区域：<span>${regionName}</span> <a class="are-line"
					href="javascript:;" onclick="getRegion(${regionId});">切换</a>
			</div>
			<!--/区域选择按钮-->
		</h4>

    <!--headtop-->

    <div class="headtop">
       <!--  <select class="fl ph-select" style="margin-right: 10px">
            <option>区域选择</option>
            <option>1</option>
            <option>1</option>
        </select> -->
        <%-- <input type="hidden" id="regionId" class="fl ph-select" style="margin-right: 10px" readonly="readonly" value="${regionId}"/> --%>
        
        <input type="hidden" id="regionId"  readonly="readonly" value="${regionId}"/>


        <input id="date" type="text" class="form-control form_datetime input-sm ph-select" placeholder="时间选择"
               readonly="readonly" style="background: #fff;">

        <button class="btn btn-blue btn-sm " style="margin: -30px 0 30px  200px" id="button">检索</button>
    </div>


    <div class="clearfix"></div>

    <!--chart start-->
    <div class="row">
        <!--chart1总金额-->
        <div class="col-sm-4">
            <div class="chart-container">
                <div class="heading ">
                    <span class="chartblue"> <span class="text-italic"> 01</span>   已出库订单</span>
                    <span class="chartred">注：区已出库订单</span>
                </div>
                <div class="chart-content clearfix">

                    <div class="col-sm-3">
                        <div id="amountPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title">
                            <span class="text-red">总金额：</span>
                        </div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="amount">0.00元</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="orderPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-purple">总单数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="orders">0单</span>
                        </div>
                    </div>
                </div>
                <!-- <div class="chart-foot">
                    <a href="">查看详情</a>
                </div> -->
            </div>
        </div>

        <!--chart2收现金-->
        <div class="col-sm-4">
            <div class="chart-container">
                <div class="heading ">
                    <span class="chartblue"> <span class="text-italic"> 02</span>   收现金</span>

                </div>
                <div class="chart-content clearfix">
                    <!--chart1总金额-->
                    <div class="col-sm-3">
                        <div id="cash_amountPercent">
                         <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title">
                            <span class="text-red">总金额：</span>
                        </div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="cash_amount">0.00元</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="cash_orderPercent">
                         <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-purple">总单数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="cash_orders">0单</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="cash_personPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-blue">人数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="cash_person">0人</span>
                        </div>
                    </div>
                </div>
               <!--  <div class="chart-foot">
                    <a href="">查看详情</a>
                </div> -->
            </div>
        </div>


        <!--chart3收现金结算-->

        <div class="col-sm-4">
            <div class="chart-container">
                <div class="heading ">
                    <span class="chartblue"> <span class="text-italic"> 03</span>   收现金结算</span>

                </div>
                <div class="chart-content clearfix">
                    <!--chart1总金额-->
                    <div class="col-sm-3">
                        <div id="statement_amountPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title">
                            <span class="text-red">总金额：</span>
                        </div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="statement_amount">0.00元</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="statement_orderPercent">
                         <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-purple">总单数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="statement_orders">0单</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="statement_personPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-blue">人数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="statement_person">0人</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="statement_serialPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-green">流水单：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="statement_serialNum">0单</span>
                        </div>
                    </div>
                </div>
                <!-- <div class="chart-foot">
                    <a href="">查看详情</a>
                </div> -->
            </div>
        </div>
    </div>

    <div class="row" style="margin-top: 50px">
        <!--chart4-->
        <div class="col-sm-4">
            <div class="chart-container">
                <div class="heading ">
                    <span class="chartblue"> <span class="text-italic"> 04</span>   收现金结算已付款</span>
                </div>
                <div class="chart-content clearfix">

                    <div class="col-sm-3">
                        <div id="paidStatement_amountPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title">
                            <span class="text-red">总金额：</span>
                        </div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="paidStatement_amount">0.00元</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="paidStatement_orderPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-purple">总单数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="paidStatement_orders">0单</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="paidStatement_personPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-blue">人数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="paidStatement_person">0人</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="paidStatement_serialPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-green">流水单：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="paidStatement_serialNum">0单</span>
                        </div>
                    </div>
                </div>
                <!-- <div class="chart-foot">
                    <a href="">查看详情</a>
                </div> -->
            </div>
        </div>
            <!--chart5-->
        <div class="col-sm-4">
            <div class="chart-container">
                <div class="heading ">
                    <span class="chartblue"> <span class="text-italic"> 05</span>   未收款、未报备</span>
                </div>
                <div class="chart-content clearfix">

                    <div class="col-sm-3">
                        <div id="unReport_amountPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title">
                            <span class="text-red">总金额：</span>
                        </div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="unReport_amount">0.00元</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="unReport_orderPercent">
                         <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-purple">总单数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="unReport_orders">0单</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="unReport_personPercent">
                         <div class="chart" data-percent="0"><span>70%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-blue">人数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="unReport_person">0人</span>
                        </div>
                    </div>
                </div>
               <!--  <div class="chart-foot">
                    <a href="">查看详情</a>
                </div> -->
            </div>
        </div>

        <!--chart6-->
        <div class="col-sm-4">
            <div class="chart-container">
                <div class="heading ">
                    <span class="chartblue"> <span class="text-italic"> 06</span>   报备</span>
                </div>
                <div class="chart-content clearfix">

                    <div class="col-sm-3">
                        <div id="report_amountPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title">
                            <span class="text-red">总金额：</span>
                        </div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="report_amount">0.00元</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="report_orderPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-purple">总单数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="report_orders">0单</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="report_personPercent">
                         <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-blue">人数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="report_person">0人</span>
                        </div>
                    </div>
                </div>
               <!--  <div class="chart-foot">
                    <a href="">查看详情</a>
                </div> -->
            </div>
        </div>
    </div>


    <div class="row" style="margin-top: 50px">
        <!--chart7-->
        <div class="col-sm-4">
            <div class="chart-container">
                <div class="heading ">
                    <span class="chartblue"> <span class="text-italic"> 07</span>   拒收</span>
                </div>
                <div class="chart-content clearfix">

                    <div class="col-sm-3">
                        <div id="refused_amountPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title">
                            <span class="text-red">总金额：</span>
                        </div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="refused_amount">0.00元</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="refused_orderPercent">
                          <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-purple">总单数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="refused_orders">0单</span>
                        </div>
                    </div>
                    <div class="col-sm-3">
                        <div id="refused_personPercent">
                         <div class="chart" data-percent="0"><span>0%</span></div>
                        </div>
                        <div class="chart-title"><span class="text-blue">人数：</span></div>
                        <div class="chart-secd-title">
                            <span class="text-gery" id="refused_person">0人</span>
                        </div>
                    </div>
                </div>
               <!--  <div class="chart-foot">
                    <a href="">查看详情</a>
                </div> -->
            </div>
        </div>

    </div>

</div>


<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script src="../static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>
<script src="../static/chartlist/regionTree.js"></script>
<script src="../static/chartlist/chartList.js"></script>
<script type="text/javascript">
    $(function(){
    	var regionId = $("#regionId").val();
		var date = $("#date").val();
    	chartList.chart.init(regionId,date);
    	$("#button").click(function(){
    		chartList.chart.queryChartByParam();
    	  });
    });
</script>
<script type="text/javascript">
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

   /*  $(function () {
        $('.chart').easyPieChart({
            animate: 2000
        });
    }); */

</script>
</body>
</html>
