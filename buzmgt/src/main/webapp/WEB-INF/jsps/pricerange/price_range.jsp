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
    <meta charset="utf-8">
    <base href="<%=basePath%>"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>价格区间详情</title>

    <!-- Bootstrap -->
    <link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" type="text/css"
          href="static/phone-set/css/phone.css">
    <link rel="stylesheet" type="text/css"
          href="static/phone-set/css/comminssion.css">
    <link rel="stylesheet" type="text/css"
          href="static/award/award.css">
    <link rel="stylesheet" type="text/css"
          href="static/bootStrapPager/css/page.css"/>
    <script src="static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <%--<script id="award-table-template" type="text/x-handlebars-template">--%>
    <%--{{#if content}}--%>
    <%--{{#each content}}--%>
    <%--<tr>--%>
    <%--<td>{{addOne @index}}</td>--%>
    <%--<td>{{#each awardGoods}}{{good.name}}+{{/each}}</td>--%>
    <%--<td class="reason">--%>
    <%--<span class="text-red">{{numberFirst}} --%>
    <%--{{#if numberSecond}}--%>
    <%--| {{numberSecond}} --%>
    <%--{{/if}}--%>
    <%--{{#if numberThird}}--%>
    <%--| {{numberThird}}--%>
    <%--{{/if}}--%>
    <%--</span>--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<span class="text-blue">{{formDate startDate}}-{{formDate endDate}}</span>--%>
    <%--</td>--%>
    <%--<td><span class="text-blue">{{formDate issuingDate}}</span></td>--%>
    <%--<td><span class="ph-on">进行中</span></td>--%>
    <%--<td>{{formDate createDate}}</td>--%>
    <%--<td>--%>
    <%--<a href="/award/list/{{awardId}}" class="btn bnt-sm bnt-ck">查看</a>--%>
    <%--<a href="/award/process/{{awardId}}" class="btn btn-sm bnt-jc ">进程</a>--%>
    <%--<button class="btn btn-sm btn-sc " onclick="delAchieve({{awardId}})">删除</button>--%>
    <%--</td>--%>
    <%--</tr>--%>
    <%--{{/each}}--%>
    <%--{{else}}--%>
    <%--<tr>--%>
    <%--<td colspan="100">没有相关数据</td>--%>
    <%--</tr>--%>
    <%--{{/if}}--%>
    <%--</script>--%>


    <script type="text/javascript">
        var base = '<%=basePath%>';
        var SearchData = {
            'page': '0',
            'size': '20'
        }
    </script>
</head>
<body>

<div class="content main">
    <h4 class="page-header">
        <c:if test="${check == '1'}">
            <i class="ico ico-tcsz"></i>渠道审核
        </c:if>
        <c:if test="${check == '2'}">
            <i class="ico ico-tcsz"></i>财务设置
        </c:if>
        <a href="javascript:history.back();"><i class="ico icon-back fl-right"></i></a>
    </h4>
    <div class="row">
        <!--col begin-->
        <div class="col-md-12">
            <!--orderbox begin-->
            <div class="order-box">
                <div class="tab-content">
                    <!--右侧内容开始-->
                    <div class="tab-pane fade in active right-body" id="dljl">
                        <%--<div class="ph-btn-set">--%>
                        <%--<a href="javascript:add();" class="btn ph-blue"> <i class="ico icon-xj"></i>--%>
                        <%--<span class="text-gery">新建奖励</span>--%>
                        <%--</a> <a href="JavaScript:record();" class="btn ph-blue" style="margin-right: 30px">--%>
                        <%--<i class="ico icon-jl"></i> <span class="text-gery">设置记录</span>--%>
                        <%--</a>--%>
                        <%--<div class="link-posit pull-right">--%>
                        <%--<input id="searchGoodsname" class="input-search" type="text"--%>
                        <%--placeholder="模糊查询请输入品牌型号">--%>
                        <%--<button onclick="goSearch();" class="btn  btn-sm bnt-ss ">搜索</button>--%>
                        <%--<a class="table-export" href="javascript:void(0);">导出excel</a>--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="table-task-list new-table-box table-overflow"
                             style="margin-left: 20px">
                            <table class="table table-hover new-table">
                                <%--<input id="production_id" type="text" hidden value="${productionId}">--%>
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>价格区间</th>
                                    <th>提成金额</th>
                                    <th>开始日期</th>
                                    <th>结束日期</th>
                                    <th>区域属性</th>
                                    <th>状态</th>
                                    <th>设置日期</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="price_range_tbody">
                                </tbody>

                            </table>
                            <c:if test="${(managerId eq userId || '1' eq managerId) && priceRangeStatus eq '1' && check eq '1'}">
                                <div style="border:1px; width:150px; height:25px;right:150px;">
                                    <button class="btn btn-sm bnt-jc "
                                            onclick="reviewProduction('${productionId}','3')">通过
                                    </button>
                                    <button class="btn btn-sm btn-zz "
                                            onclick="reviewProduction('${productionId}','2')">驳回
                                    </button>
                                </div>
                            </c:if>
                            <c:if test="${(managerId eq userId || '1' eq managerId) && priceRangeStatus eq '0' && check eq '2'}">
                                <td>
                                    <button class="btn btn-sm btn-zz " data-toggle="modal" data-target="#" onclick="go('${productionId}','${planId}')">继续创建
                                    </button>
                                    <button class="btn btn-sm btn-zz " data-toggle="modal" data-target="#" onclick="delete1('${productionId}','${planId}','${production.productionType}')">删除
                                    </button>
                                </td>
                            </c:if>
                        </div>
                        <div id="initPager">
                        </div>
                    </div>

                </div>
            </div>

        </div>

    </div>

</div>
<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script type="text/javascript" src="static/js/common.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="static/bootstrap/js/bootstrap.min.js"></script>
<script src="static/js/dateutil.js" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
        charset="utf-8"></script>
<script type="text/javascript"
        src="static/bootStrapPager/js/extendPagination.js"></script>
<script type="text/javascript">
    $(".J_MachineType li").on("click", function () {
        $(this).addClass("active");
        $(this).siblings("li").removeClass("active");
    });

</script>
<%--显示价格区间详情模板--%>
<script id="price-range-template" type="text/x-handlebars-template">
    {{#if this}}
    {{#each this}}
    <tr>
        <td>{{addOne @index}}</td>
        <td>{{priceRange}}</td>
        <td>{{percentage}}</td>
        <td>{{implementationDate}}</td>
        <td>
            {{#if endTime}}
            {{endTime}}
            {{else}}
            <span style="color: #6a0505">未设置结束日期</span>
            {{/if}}
        </td>
        <td><a href="javascript:seeRegion('{{priceRangeId}}')">查看</a></td>
        <td>{{checkStatus priceRangeStatus}}</td>
        <td>{{priceRangeCreateDate}}</td>
        <td>
            {{#if oldId}}
            {{#checkReview userId oldId priceRangeStatus}}
            <button class="btn  bnt-sm bnt-ck"
                    onclick="reviewPriceRange('{{priceRangeId}}','3')">
                通过
            </button>
            <button class="btn  bnt-sm bnt-ck"
                    onclick="reviewPriceRange('{{priceRangeId}}','2')">
                驳回
            </button>
            {{else}}
            <button class="btn  bnt-sm bnt-ck">
                --
            </button>
            <button class="btn  bnt-sm bnt-ck">
                --
            </button>
            {{/checkReview}}
            {{else}}
            <button class="btn  bnt-sm bnt-ck">
                --
            </button>
            <button class="btn  bnt-sm bnt-ck">
                --
            </button>
            {{/if}}
        </td>
    </tr>
    {{/each}}
    {{else}}
    <tr>
        <td colspan="100">没有相关数据</td>
    </tr>
    {{/if}}
</script>
<script type="text/javascript">
    $(function () {
        details();

    });
    //索引加1
    Handlebars.registerHelper("addOne", function (index) {
        return (index + 1) + "";
    });
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
    //判断有没有审核修改功能
    Handlebars.registerHelper("checkReview", function (auditor, oldId, status, options) {
        if ((auditor == ${managerId} || ${managerId} == '1') && oldId != null && status == 1) {
            return options.fn(this);
        } else {
            return options.inverse(this);
        }
    });
    function details() {
        $.ajax({
            url: '/priceRange/details/' +${productionId},
            dataType: 'JSON',
            type: 'GET',
            success: function (data) {
                console.log(data);
                var priceRangeTemplate = Handlebars.compile($("#price-range-template").html());
                $("#price_range_tbody").html(priceRangeTemplate(data));
            },
            error: function () {
                alert("系统故障");
            }
        });

    }
    /*
     审核production :status 3-审核通过,2-驳回
     */
    function reviewProduction(id, status) {
        $.ajax({
            url: '<%=basePath%>section/review',
            type: 'POST',
            data: {id: id, status: status},
            success: function (data) {
                alert('操作成功');
                window.location.href = '<%=basePath%>priceRange/record?&planId=' + data.planId + '&check=' + ${check};
            },
            error: function () {
                alert('操作失败');
            }
        })
    }
    /*
     * 审核修改的priceRange
     * */
    function reviewPriceRange(id, status) {
        $.ajax({
            url: '<%=basePath%>priceRange/reviewPrice/' + id,
            type: 'post',
            data: {status: status},
            success: function (data) {
                if (data.result == 'success') {
                    alert(data.msg);
                } else {
                    alert(data.msg);
                }
                refresh();
            },
            error: function () {
                alert('操作失败');
            }
        })
    }
    /*页面刷新*/
    function refresh() {
        window.location.reload();//刷新当前页面.
    }
    /**
     * 查看区域属性
     * @param id
     */
    function seeRegion(id){
        window.location.href = "/areaAttr/show?type=PRICERANGE&ruleId="+id;
    }
    /**
     * 继续创建
     * @param id
     * @param planId
     */
    function go(id,planId){
        <%--window.location.href = "<%=basePath%>section/production/" + id+'?planId='+planId;--%>
        window.location.href = "<%=basePath%>section/production/" +id+'?planId='+planId + '&check=' + ${check};

    }
    /**
     * 删除创建中的
     * @param id
     * @param planId
     * @param type
     */
    function delete1(id,planId) {
        $.ajax({
            url:'<%=basePath%>section/delete/'+id,
            type:'GET',
            dataType:'json',
            success:function (data) {
                if(data.result == 'success'){
                    alert(data.msg);
                    <%--window.location.href = '<%=basePath%>section/toNotExpiredJsp?type='+type+'&planId='+planId;--%>
                    window.location.href = '<%=basePath%>priceRange/record?&planId=' + planId + '&check=' + ${check};
                }else{
                    alert(data.msg);
                    refresh();
                }
            }
        });
    }
    <%--/* 被驳回 */--%>
    <%--function oNo(id) {--%>
    <%--$.ajax({--%>
    <%--url: '<%=basePath%>section/review',--%>
    <%--type: 'POST',--%>
    <%--data: {id: id, status: '2'},--%>
    <%--success: function (data) {--%>
    <%--alert('操作成功');--%>
    <%--window.location.href = '<%=basePath%>priceRange/record?&planId=' + data.planId + '&check = ' + ${check};--%>
    <%--},--%>
    <%--error: function () {--%>
    <%--alert('操作失败');--%>
    <%--}--%>
    <%--})--%>
    <%--}--%>

</script>
</body>

</html>