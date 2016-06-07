<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
			String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>" />
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>设置考核次数</title>
    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css">
    <link rel="stylesheet" type="text/css" href="static/kaohe/income-cash.css">
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="content main">
    <h4 class="page-header">
        <i class="ico icon-khcs"></i>设置考核次数
    </h4>
    <!---选择地区-->
    <div>
        <span class="text-gray">选择大区：</span>
        <select class="box-sty" >
            <option>山东省</option>
            <option>上海市</option>
            <option>北京市</option>
        </select>
    </div>
    <!---选择地区-->

    <!---设置次数-->
    <div class="box-szcs">
        <span class="text-gray">设置次数：</span>
        <input class="box-cs" type="text" placeholder="5"> &nbsp;次
        <button class="btn btn-blue btn-b-style">确定</button>
    </div>
    <!---设置次数-->

    <div class="clearfix"></div>
    <div class="tab-content">
        <!--油补记录-->
        <div class="tab-pane fade in active" id="box_tab1">
            <!--table-box-->
            <div class="table-task-list new-table-box table-overflow">
                <table class="table table-hover new-table">
                    <thead>
                    <tr>
                        <th>区域</th>
                        <th>考核次数</th>
                        <th>最新创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tr>
                        <td>山东省</td>
                        <td>10次</td>
                        <td>2016.06.01</td>
                        <td>
                            <button class="btn btn-blue btn-bn-style">查看更多</button>
                        </td>
                    </tr>
                    <tr>
                        <td>山东省</td>
                        <td>10次</td>
                        <td>2016.06.01</td>
                        <td>
                            <button class="btn btn-blue btn-bn-style">查看更多</button>
                        </td>
                    </tr>
                    <tr>
                        <td>山东省</td>
                        <td>10次</td>
                        <td>2016.06.01</td>
                        <td>
                            <button class="btn btn-blue btn-bn-style ">查看更多</button>
                        </td>
                    </tr>
                    <tr>
                        <td>山东省</td>
                        <td>10次</td>
                        <td>2016.06.01</td>
                        <td>
                            <button class="btn btn-blue btn-bn-style ">查看更多</button>
                        </td>
                    </tr>
                    <tr>
                        <td>山东省</td>
                        <td>10次</td>
                        <td>2016.06.01</td>
                        <td>
                            <button class="btn btn-blue btn-bn-style ">查看更多</button>
                        </td>
                    </tr>
                    <tr>
                        <td>山东省</td>
                        <td>10次</td>
                        <td>2016.06.01</td>
                        <td>
                            <button class="btn btn-blue  btn-bn-style">查看更多</button>
                        </td>
                    </tr>
                    <tr>
                        <td>山东省</td>
                        <td>10次</td>
                        <td>2016.06.01</td>
                        <td>
                            <button class="btn btn-blue btn-bn-style">查看更多</button>
                        </td>
                    </tr>
                    <tr>
                        <td>山东省</td>
                        <td>10次</td>
                        <td>2016.06.01</td>
                        <td>
                            <button class="btn btn-blue btn-bn-style">查看更多</button>
                        </td>
                    </tr>
                    <tr>
                        <td>山东省</td>
                        <td>10次</td>
                        <td>2016.06.01</td>
                        <td>
                            <button class="btn btn-blue btn-bn-style">查看更多</button>
                        </td>
                    </tr>

                </table>
            </div>
            <!--table-box-->
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
<script src="<%=basePath%>static/yw-team-member/team-member.js" type="text/javascript" charset="utf-8"></script>
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
</body>
</html>
