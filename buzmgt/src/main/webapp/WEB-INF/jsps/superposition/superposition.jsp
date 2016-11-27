<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>提成设置</title>

    <link href="../static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">
    <link rel="stylesheet" href="../static/phone-set/css/phone.css">
    <link rel="stylesheet" href="../static/earnings/css/comminssion.css">
    <link rel="stylesheet" type="text/css" href="/static/bootStrapPager/css/page.css"/>
    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"
            charset="utf-8"></script>
    <style>
        .new-table-box {
            border-top: none;
            min-height: 600px;
            background: #FFF;
        }

        .nav-sidebar > li.current > a, .nav-sidebar > li .current > a:focus, .nav-sidebar > li.current > a:hover {
            color: #2b86ba;
            border-left: 3px solid #44a6dd;
            background: #ffffff;
        }

        .icon-je {
            background: url("img/tcje.png") no-repeat center;
        }

        .ph-icon {
            height: 30px;
            padding: 5px 10px;
            font-size: 12px;
            line-height: 1.5;
            border-radius: 3px;
        }

        .icon-riz {
            background: url("<%=basePath%>static/earnings/img/rizi.png") no-repeat center;
        }

        .icon-reny {
            background: url("<%=basePath%>static/earnings/img/shry.png") no-repeat center;
        }
    </style>
    <script>
        function seeProgress(planId, id) {
            window.location.href = 'progress?planId=' + planId + '&id=' + id;
        }
        function stop(id) {
            $.ajax({
                url: 'stop/' + id,
                data: {checkStatus: '4'},
                dataType: 'json',
                type: 'POST',
                success: function (data) {
                    if (data.result == 'success') {
                        alert("操作成功");
                    }
                    window.location.reload();
                },
                error: function (data) {

                }
            })

        }
    </script>


</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <i class="ico ico-tcsz"></i>提成设置
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>
    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->
            <div class="order-box">
                    <!--叠加设置-->
                    <div  id="djsz">
                        <!--导航开始-->

                        <div class="ph-btn-set">
                            <c:if test="${check eq '1'}">
                                <%--<a href="/superposition/add?planId=${planId}" class="btn ph-blue">--%>
                                    <%--<i class="ico icon-xj"></i> <span class="text-gery">添加</span>--%>
                                <%--</a>--%>
                                <a href="/superposition/listAll?planId=${planId}&check=${check}" class="btn ph-blue"
                                   style="margin-right: 30px">
                                    <i class="ico icon-jl"></i> <span class="text-gery">设置记录/审核</span>
                                </a>
                            </c:if>
                            <c:if test="${check eq '2'}">
                                <a href="/superposition/add?planId=${planId}&check=${check}" class="btn ph-blue">
                                    <i class="ico icon-xj"></i> <span class="text-gery">添加</span>
                                </a>
                                <a href="/superposition/listAll?planId=${planId}&check=${check}" class="btn ph-blue"
                                   style="margin-right: 30px">
                                    <i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>
                                </a>
                            </c:if>
                            <div class="link-posit pull-right">
                                <%--<input class="input-search" type="text" placeholder="模糊查询请输入品牌型号">--%>
                                <%--<button class="btn  btn-sm bnt-ss ">搜索</button>--%>
                                <a class="table-export" href="javascript:void(0);">导出excel</a>
                            </div>
                        </div>

                        <div class="table-task-list new-table-box table-overflow" style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>名称</th>
                                    <th>指标</th>
                                    <th>审核人</th>
                                    <th>方案起止日期</th>
                                    <th>佣金发放</th>
                                    <th>状态</th>
                                    <th>设置日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>

                                <tbody id="super-tbody">
                                <script id="list-template" type="text/handlebars-template">
                                    {{#each this}}
                                    <tr>
                                        <td>1</td>
                                        <td class="reason">
                                            <%--{{#with goodsTypeList}}--%>
                                            <%--{{#each this}}--%>
                                            <%--{{#with machineType}}--%>
                                            <%--{{name}}--%>
                                            <%--{{/with}}--%>
                                            <%--{{#with brand}}--%>
                                            <%--{{name}}--%>
                                            <%--{{/with}}--%>
                                            <%--{{/each}}--%>
                                            <%--{{/with}}--%>
                                            方案
                                        </td>
                                        <td>
                                            <span class="text-red">
                                            {{taskOne}}
                                            {{#if taskTwo}}
                                            |{{taskTwo}}
                                            {{/if}}
                                            {{#if taskThree}}
                                            |{{taskThree}}
                                            {{/if}}
                                            </span>
                                        </td>
                                        <td>{{auditorName}}</td>
                                        <td>
                                            {{#if implDate}}
                                            {{implDate}} -- {{endDate}}
                                            {{/if}}
                                        </td>

                                        <td>{{giveDate}}</td>
                                        <td>
                                            {{#if checkStatus}}
                                            {{checkStatus checkStatus}}
                                            {{/if}}
                                            </span>
                                        </td>
                                        <td>{{createDate}}</td>
                                        <td>
                                            {{#checkRecord checkStatus auditor}}
                                            <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#" onclick="see('{{id}}')">查看
                                            </button>
                                            <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#" onclick="seeProgress('{{planId}}', '{{id}}')">进程
                                            </button>
                                            <button class="btn  bnt-sm bnt-zza" data-toggle="modal" data-target="#" onclick="stop('{{id}}')">废弃</button>
                                            {{else}}
                                            <button class="btn  bnt-sm bnt-ck" data-toggle="modal" data-target="#" onclick="see('{{id}}')">查看
                                            </button>
                                            <button class="btn btn-sm bnt-jc " data-toggle="modal" data-target="#" onclick="seeProgress('{{planId}}', '{{id}}')">进程
                                            </button>
                                            {{checkDelete checkStatus id}}
                                            {{/checkRecord}}
                                        </td>
                                    </tr>
                                    {{/each}}

                                </script>
                                </tbody>
                            </table>
                        </div>
                        <%--分页--%>
                        <div id="callBackPager"></div>
                    </div>


                </div>


            </div>


        </div>

    </div>
    <!--修改-->
    <div id="gaigai" class="modal fade" role="dialog">
        <div class="modal-dialog " role="document">
            <div class="modal-content modal-blue">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title">修改</h3>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <form id="addd" class="form-horizontal">
                            <div class="form-group">
                                <label class="col-sm-4 control-label">选择价格区间：</label>
                                <div class="col-sm-7">
                                    <span class="text-gery">0-50</span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">设置提成金额：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon "><i class="ph-icon   icon-je"></i></span>
                                        <!--<span class="input-group-addon"><i class="ico icon-je"></i></span>-->
                                        <input name="a" type="text" class="form-control input-h"
                                               aria-describedby="basic-addon1" placeholder="请设置提成金额">
                                        </input>
                                    </div>
                                    <span class="text-gery " style="float: right;margin-right: -45px;margin-top: -25px">元/台</span>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-4 control-label">方案实施日期：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon "><i class=" ph-icon icon-riz"></i></span>
                                        <input type="text" class="form-control form_datetime " placeholder="请选择实施日期"
                                               readonly="readonly  " style="background: #ffffff;">
                                    </div>
                                    <span class="text-gery " style="float: right;margin-right: -30px;margin-top: -25px">起</span>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-4 control-label">指派审核人员：</label>
                                <div class="col-sm-7">
                                    <div class="input-group are-line">
                                        <span class="input-group-addon"><i class="ph-icon icon-reny"></i></span>
                                        <select name="b" type="" class="form-control input-h "
                                                aria-describedby="basic-addon1">
                                            <option></option>
                                            <option>中国银行</option>
                                            <option>农业银行</option>
                                            <option>工商银行</option>
                                            <option>亚细亚银行</option>
                                        </select>
                                        <!-- /btn-group -->
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-4 col-sm-4 ">
                                    <a herf="javascript:return 0;" onclick="addd(this)"
                                       class="Zdy_add  col-sm-12 btn btn-primary">保存
                                    </a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>
<script src="../static/bootstrap/js/bootstrap.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script>
    $('#tab li').click(function () {
        $(this).addClass('active');
        $(this).siblings('li').removeClass('active');
        $('#tab li:eq(1) a').tab('show');
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
<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
<script>
    var total = 0;
    var totalCount = 0;
    var limit = 0;
    var searchData = {
        "size": "20",
        "page": "0"
    };

    /**
     * 查看信息
     * @param id
     */
    function see(id) {
        window.location.href = id+'?check=' + ${check};
    }

    //查询正在使用的
    function listNow(searchData) {
        //正在使用
        $.ajax({
            url: 'findAll?sign=pass&planId=${planId}',
            type: 'POST',
            dataType: 'json',
            data: searchData,
            success: function (data) {
//                    var content = data.content;
//                    totalCount = data.totalElements;
//                    limit = data.size;
//                    handelerbars_register(content);//向模版填充数据
//                    if (totalCount != total || totalCount == 0) {
//                        total = totalCount;
//                        initPaging();
//                    }

                console.log(data);
                var listArray = data.content;
                totalCount = data.totalElements;
                limit = data.size;
                var listTemplate = Handlebars.compile($("#list-template").html());
                //判断审核权限
                Handlebars.registerHelper("checkRecord", function (status,auditor,options) {
                    if (status == '3' && '${check}' == '2' && (auditor == ${auditor} || ${auditor} == '1')) {
                        //满足添加继续执行
                        return options.fn(this);
                    } else {
                        //不满足条件执行{{else}}部分
                        return options.inverse(this);
                    }
                });
                //判断是否是驳回的
                Handlebars.registerHelper("checkDelete", function (status,id) {
                    if (status == '2' && '${check}' == '2') {
                        return new Handlebars.SafeString('<button class="btn  bnt-sm bnt-zza " data-toggle="modal" data-target="#" onclick="deleteReject('+id+')">删除</button>');
                    }
                });

                //注册状态模板
                //状态:0-创建中,1-审核中,2-驳回,3-审核通过,4-废弃(删除);
                Handlebars.registerHelper("checkStatus", function (status) {
                    if (status == 0) {
                        return new Handlebars.SafeString('<span>创建中</span>');
                    } else if (status == 1) {
                        return new Handlebars.SafeString('<span class="text-hong text-strong">待审核</span>');
                    } else if (status == 2) {
                        return new Handlebars.SafeString('<span class="text-zi text-strong">被驳回</span>');
                    } else if (status == 3) {
                        return new Handlebars.SafeString(' <span class="text-lan text-strong">已审核</span>');
                    }
                });
                $("#super-tbody").html(listTemplate(listArray));
                if (totalCount != total || totalCount == 0) {
                    total = totalCount;
                    initPaging();
                }
            },
            error: function () {
            }
        });

    }
    //分页
    function initPaging() {
        $('#callBackPager').extendPagination({
            totalCount: totalCount,//总条数
            showCount: 5,//下面小图标显示的个数
            limit: limit,//每页显示的条数
            callback: function (curr, limit, totalCount) {
                searchData['page'] = curr - 1;
                searchData['size'] = limit;
                listNow(searchData);
            }
        });
    }

    function deleteReject(id) {
        $.ajax({
            url:'delete/' + id,
            success:function(data){
                alert("删除成功");
                window.location.reload();
            },
            error:function(){
                alert('系统故障');
            }

        })
    }

    listNow(searchData);


</script>
</body>
</html>
