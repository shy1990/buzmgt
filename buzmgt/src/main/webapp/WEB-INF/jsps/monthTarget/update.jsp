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
	<base href="<%=basePath%>" />
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>修改数据</title>

    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/month-target/css/mouth.css">
    <link rel="stylesheet" href="static/multiselect/css/jquery.multiselect.css">
    <script src="<%=basePath%>static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <style>
        ul li{
            list-style-type:none;
        }
        ul {
            column-count: 1;
            column-gap: 0px;
            padding-left: 0px;
        }

        .sj-ms-btn-ok{
            margin-top: 0px;
            margin-bottom: 0px;
            text-align: center;
            background-color: #eee;
            padding-top: 5px;
            border-bottom-width: 0px;
            padding-bottom: 5px;
        }
        .sj-ms-btn-ok>button{
            padding-right: 15px;
            padding-left: 15px;
            background-color: #fff;
            border: 1px solid #aaaaaa;
            color: #555555;
        }
        .text-blue{
            color: #555555;
        }
    </style>
</head>
<body>
<div class="content main">
    <h4 class="page-header">
        <i class="ico icon-xiug"></i>修改数据
    </h4>

    <!--地区-->

    <div class="text-blue text-strong" style="margin-top: 25px; margin-bottom: 25px ">
        <div style="width: 180px ; float: left;margin-right: 20px">
            <form>
                <select name="basic[]" multiple="multiple" class="demo3" >

                    <option value="UT" >胡老大</option>
                    <option value="VT">横额啊</option>
                    <option value="VA">张二啦</option>
                    <option value="VA">王晓晓</option>
                    <option value="WV">杭大大</option>
                    <option value="WV">曹大大</option>
                    <option value="WI">槽大小</option>
                </select>
            </form>
        </div>

        <button class="btn btn-blue btn-sm" onclick="goSearch('${salesman.id}','${assess.id}');">
            检索
        </button>
     <span style="color: #a6a6a6">  山东省  滨州市  邹平县</span> </div>
    <!--地区-->

    <div class="row" >
        <!--提货量-->
        <div class="col-sm-3" style="margin: 0; padding: 0">
             <div class="tab-pane fade in active" >
                 <div class="table-task-list half-box table-overflow">
                     <table class="table table-hover new-table" style="margin-bottom: 0">
                         <thead>
                         <tr>
                             <th>提货量</th>

                         </tr>
                         </thead>
                         <tr>
                             <td>
                                 近三个月月均提货量
                                 <div class="  text-right" style="margin-top: -15px;color: #00b7ee">720 台</div>
                             </td>
                         </tr>
                         <tr>
                             <td>上月提货量 <div class=" text-right"  style="margin-top: -15px;color: #00b7ee">800 台</div></td>

                         </tr>
                         <tr>
                             <td>系统建议 <div class=" text-right "   style="margin-top: -15px;color: #00b7ee">750 台</div></td>

                         </tr>
                         <tr>
                             <td>请录入 <input class="input-th" type="text" placeholder="提货量"> &nbsp;台</td>
                         </tr>

                         </table>
                     </div>


                 </div>
        </div>
        <!--提货量-->

        <!--提货商家-->
        <div class="col-sm-3" style="margin: 0; padding: 0">
            <div class="tab-pane fade in active" >
                <div class="table-task-list half-box table-overflow">
                    <table class="table table-hover new-table" style="margin-bottom: 0">
                        <thead>
                        <tr>
                            <th>提货商家</th>

                        </tr>
                        </thead>
                        <tr>
                            <td>
                                近三个月月均提货商家
                                <div class="  text-right" style="margin-top: -15px;color: #00b7ee">720 台</div>
                            </td>
                        </tr>
                        <tr>
                            <td>上月提货商家 <div class=" text-right"  style="margin-top: -15px;color: #00b7ee">800 台</div></td>

                        </tr>
                        <tr>
                            <td>系统建议 <div class=" text-right "   style="margin-top: -15px;color: #00b7ee">750 台</div></td>

                        </tr>
                        <tr>
                            <td>请录入 <input class="input-th" type="text" placeholder="提货商家"> &nbsp;家</td>
                        </tr>

                    </table>
                </div>


            </div>
        </div>
        <!--提货商家-->

        <!--活跃商家-->
        <div class="col-sm-3" style="margin: 0; padding: 0">
            <div class="tab-pane fade in active" >
                <div class="table-task-list half-box table-overflow">
                    <table class="table table-hover new-table" style="margin-bottom: 0">
                        <thead>
                        <tr>
                            <th>活跃商家</th>
                        </tr>
                        </thead>
                        <tr>
                            <td>
                                近三个月月均活跃商家
                                <div class="  text-right" style="margin-top: -15px;color: #00b7ee">20 家</div>
                            </td>
                        </tr>
                        <tr>
                            <td>上月活跃商家 <div class=" text-right"  style="margin-top: -15px;color: #00b7ee">15 家</div></td>

                        </tr>
                        <tr>
                            <td>系统建议 <div class=" text-right "   style="margin-top: -15px;color: #00b7ee">18 家</div></td>

                        </tr>
                        <tr>
                            <td>请录入 <input class="input-th" type="text" placeholder="活跃商家"> &nbsp;家</td>
                        </tr>

                    </table>
                </div>


            </div>
        </div>
        <!--活跃商家-->

        <!--成熟商家-->
        <div class="col-sm-3" style="margin: 0; padding: 0">
            <div class="tab-pane fade in active" >
                <div class="table-task-list half-box table-overflow">
                    <table class="table table-hover new-table" style="margin-bottom: 0">
                        <thead>
                        <tr>
                            <th>成熟商家</th>
                        </tr>
                        </thead>
                        <tr>
                            <td>
                                近三个月月均成熟商家
                                <div class="  text-right" style="margin-top: -15px;color: #00b7ee">20 家</div>
                            </td>
                        </tr>
                        <tr>
                            <td>上月成熟商家 <div class=" text-right"  style="margin-top: -15px;color: #00b7ee">15 家</div></td>

                        </tr>
                        <tr>
                            <td>系统建议 <div class=" text-right "   style="margin-top: -15px;color: #00b7ee">18 家</div></td>

                        </tr>
                        <tr>
                            <td>请录入 <input class="input-th" type="text" placeholder="成熟商家"> &nbsp;家</td>
                        </tr>

                    </table>
                </div>


            </div>
        </div>
        <!--成熟商家-->
    </div>
</div>


    <div style="text-align: center;background-color: #fafafa">
        <button class="btn btn-primary btn-blue btn-ok" >保存</button>
    </div>
<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>
<script src="../static/bootstrap/js/bootstrap.min.js"></script>
<script src="../static/multiselect/js/jquery.multiselect.js"></script>

<script>
    $('.menu a,.menu li.active a').click(function () {
        $(this).parent('li').toggleClass('active');
        $(this).parent('li').siblings().removeClass('active');
    });

    $('select[multiple].demo3').multiselect({
        columns: 1,
        placeholder: '选择业务员',
        search: true,
        selectGroup: true
    });

</script>

</body>
</html>
