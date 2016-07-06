<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>提成导入表</title>

    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/static/bootStrapPager/css/page.css" />
    <link href="/static/bootstrap/css/fileinput.css" rel="stylesheet">
    <link href="/static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="../static/css/common.css">

    <script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="content main">
    <h4 class="page-header ">
        <i class="ico icon-daoru"></i>提成导入表
    </h4>
    <div class="row text-time">

        <div class="salesman" style="margin-top: 5px">
            <%--<select class="box-sty-s">--%>
            <%--<option>山东省</option>--%>
            <%--<option>上海市</option>--%>
            <%--<option>北京市</option>--%>
            <%--</select>--%>
            <div class="search-date">
                <div class="input-group input-group-sm">
                <span class="input-group-addon " id="basic-addon1"><i
                        class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                    <input id="startTime" type="text" class="form-control form_datetime input-sm" placeholder="开始日期"
                           readonly="readonly">
                </div>
            </div>
            --
            <div class="search-date">
                <div class="input-group input-group-sm">
                        <span class="input-group-addon " id="basic-addon1"><i
                                class=" glyphicon glyphicon-remove glyphicon-calendar"></i></span>
                    <input id="endTime" type="text" class="form-control form_datetime input-sm" placeholder="结束日期"
                           readonly="readonly">
                </div>
            </div>
            <!--考核开始时间-->
            <button class="btn btn-blue btn-sm" style="height: 30px;margin-left: 10px" id="goSearch">
                筛选
            </button>

            <div class="H-daochu">
                <div class="salesman">
                    <input id="SearchName" class="cs-select  text-gery-hs" placeholder="  请输入业务员名称">
                    <button class="btn btn-blue btn-sm" id="goSearchByName">
                        检索
                    </button>
                </div>
                <div class="link-posit-t pull-right export">
                    <a class="table-export" data-toggle="modal" data-target="#daoru">导入excel</a>
                </div>
            </div>
        </div>

        <div class="clearfix"></div>
        <div class="tab-content">
            <!--待审核账单-->
            <div class="tab-pane fade in active" id="box_tab1">
                <!--table-box-->
                <div class="table-task-list new-table-box table-overflow">
                    <table class="table table-hover new-table">
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>提成</th>
                            <th>手机号</th>
                            <th>备注</th>
                            <th>日期</th>
                        </tr>
                        </thead>
                        <tbody id="tbody">
                        </tbody>
                    </table>
                </div>
                <div id="callBackPager"></div>
                <!--table-box-->
            </div>
            <!--待审核账单-->
        </div>
        <div id="daoru" class="modal fade bs-example-modal-lg" role="dialog">
            <div class="modal-dialog fang modal-lg" role="document">
                <div class="modal-content modal-blue yuan ">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h3 class="modal-title">导入表格</h3>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid">
                            <form enctype="multipart/form-data" class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">选择文件：</label>
                                    <div class="col-sm-10">
                                        <input id="file-input" name="file" type="file" class="">
                                    </div>
                                </div>

                                <div class="form-group" id="uploadFileDiv">
                                    <div class="col-sm-offset-4 col-sm-4 ">
                                        <a herf="javascript:return 0;" id="uploadFile" onclick=""
                                           class="Zdy_add  col-sm-12 btn btn-primary">上传文件 </a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <![endif]-->
            <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
            <![endif]-->
            <script src="../static/bootstrap/js/bootstrap.min.js"></script>
            <script src="../static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
            <script src="../static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
            <script type="text/javascript"
                    src="/static/bootstrap/js/fileinput.js"></script>
            <script type="text/javascript"
                    src="/static/bootstrap/js/fileinput_locale_zh.js"></script>
            <script src="/static/js/jqueryfileupload/js/vendor/jquery.ui.widget.js"></script>
            <script src="/static/js/jqueryfileupload/js/jquery.iframe-transport.js"></script>
            <script src="/static/js/jqueryfileupload/js/jquery.fileupload.js"></script>
            <script id="tbody-template" type="text/x-handlebars-template">
                {{#each this}}
                <tr>
                    <td>{{name}}</td>
                    <td>{{salary}}</td>
                    <td>{{tel}}</td>
                    <td>{{message}}</td>
                    <td>{{createTime}}</td>
                </tr>
                {{/each}}
            </script>
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
                var $_haohe_plan = $('.J_kaohebar').width();
                var $_haohe_planw = $('.J_kaohebar_parents').width();
                $(".J_btn").attr("disabled", 'disabled');
                if ($_haohe_planw === $_haohe_plan) {
                    $(".J_btn").removeAttr("disabled");
                }
            </script>
            <script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
            <script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
            <script type="text/javascript">
                $(function () {
                    //初始化
                    salary.detail.init();
                    //日期检索
                    $('#goSearch').click(function () {
                        salary.searchData.startTime = $("#startTime").val();
                        salary.searchData.endTime = $("#endTime").val();
                        salary.searchData.name = '';
                        salary.searchData.page = 0;
                        console.log(salary.searchData);
                        salary.findByDate(salary.searchData);
                    });
                    /**
                     *姓名检索
                     */
                    $('#goSearchByName').click(function () {
                        salary.searchData.name = $("#SearchName").val();
                        salary.searchData.page = 0;
                        console.log(salary.searchData);
                        salary.findByDate(salary.searchData);
                    });

                    initFileUpload();

                });

                /**
                 * 模块化的写法
                 * @type {{url: {all: salary.url.all}}}
                 */
                var salary = {
                    /**
                     * 查询条件
                     */
                    searchData: {
                        startTime: '2015-05-05',
                        endTime: '2200-05-06',
                        size: "20",
                        page: "0",
                        name: ''
                    },
                    /**
                     * 分页参数
                     */
                    _count: {
                        totalCount: 0,
                        limit: 0,
                        total: -1
                    },
                    url: {
                        all: function () {
                            return '/salary/salarys';
                        },
                        uploadUrl:function(){
                            return '/salary/upload';
                        }
                    },
                    /**
                     * 分页查询
                     * @param searchData
                     */
                    findAll: function (searchData) {
                        $.ajax({
                            url: salary.url.all(),
                            data: searchData,
                            success: function (result) {
                                var content = result.content;
                                salary._count.totalCount = result.totalElements;
                                salary._count.limit = result.size;
                                salary.handelerbars_register(content);
                                if (salary._count.totalCount != salary._count.total || salary._count.totalCount == 0) {
                                    salary._count.total = salary._count.totalCount;
                                    salary.initPaging();
                                }
                            },
                            error: function () {
                                alert('系统错误');
                            }

                        });
                    },
                    /**
                     * 分页工具
                     */
                    initPaging: function () {
                        $('#callBackPager').extendPagination({
                            totalCount: salary._count.totalCount,//总条数
                            showCount: 5,//下面小图标显示的个数
                            limit: salary._count.limit,//每页显示的条数
                            callback: function (curr, limit, totalCount) {
                                salary.searchData.page = curr - 1;
                                salary.searchData.size = limit;
                                salary.findAll(salary.searchData);
                            }
                        });
                    },
                    /**
                     * handerlebars填充数据
                     */
                    handelerbars_register: function (content) {
                        var driver_template = Handlebars.compile($("#tbody-template").html());//注册
                        $("#tbody").html(driver_template(content));
                    },
                    /**
                     *日期检索
                     */
                    findByDate: function (searchData) {
                        salary._count.total = -1;
                        salary.findAll(searchData);
                    },
                    /**
                     *  姓名检索
                     */
                    findByName: function (searchData) {
                        salary._count.total = -1;
                        salary.findAll(searchData);
                    },

                    /**
                     * 页面初始化
                     */
                    detail: {
                        init: function (args) {
                            salary.findAll(salary.searchData);
//                            initFileUpload();

                        }
                    }

                };

                /**
                 * 初始化文件上传
                 */
                function initFileUpload() {
                    // 文件上传
                    $("#file-input").fileinput({
                        language : 'zh',
                        uploadUrl :salary.url.uploadUrl(),
                        allowedFileExtensions : [ 'xls', 'xlsx' ],
                        showPreview : false,
                        showUpload: false,
                        dropZoneEnabled: false,
                    });
                    $('#file-input').fileupload({
                        dataType: 'json',
                        add: function (e, data) {
                            $("#uploadFileDiv").show();
                            $("#uploadFile").on("click", function () {
                                // 修改fileupload插件上传时的url，带参数。
                                $("#file-input").fileupload('option', 'url',salary.url.uploadUrl());

                                data.submit();
                            });
                        },
                        done: function (e, data) {
                            console.log(e);
                            console.log(data);
                            if (data.result.message != '上传成功') {
                                alert(data.result.message);
                                window.location.reload();
                                return;
                            }
                            $('#file-input').fileinput('clear');
                            $('#daoru').modal('hide');
                            salary.searchData.startTime = '2015-05-05';
                            salary.searchData.endTime = '2200-05-05';
                            salary.searchData.page = '0',
                            salary.searchData.size = '4',
                            salary.searchData.name = ''
//                            salary.findAll(salary.searchData);
                            alert("上传完成");
//                            window.location.href = "/salary/toSalary";
                            window.location.reload();
                        }
                    });
                }

            </script>
</body>
</html>


