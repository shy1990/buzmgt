<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>账号管理</title>
    <!-- Bootstrap -->
    <link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="static/bootstrap/css/bootstrap-switch.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="static/css/common.css"/>
    <link rel="stylesheet" type="text/css" href="static/yw-team-member/team-member.css"/>
    <link rel="stylesheet" type="text/css" href="static/account-manage/account-list.css"/>
    <script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>


<div class="container-fluid content main">
    <h4 class="page-header">
        <i class="ico ico-account-manage"></i>账号管理
        <!--区域选择按钮-->
<!--         <div class="area-choose"> -->
<!--             选择区域：<span>山东省</span> -->
<!--             <a class="are-line" href="javascript:;" onclick="">切换</a> -->
<!--         </div> -->
        <!--/区域选择按钮-->
    </h4>

    <ol class="breadcrumb">
        <li><a href="/accountManage">账号管理</a></li>
        <li><a href="#">子账号</a></li>
    </ol>
    <h6>

        主账号：${fatherAccount.user.username}    姓名：${fatherAccount.truename}    负责区域：${fatherAccount.region.name} 
    </h6>



    <div>
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="all">
                <div class="table-responsive">
                    <table class="table table-hover new-table abnormal-order-table">
                        <thead>
                        <tr>
                            <th>子账号</th>
                            <th>账号状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>


                        <tbody>
                        
	                        <c:forEach var="ac" items="${listChild}" varStatus="s">
	                        <tr>
	                            <td>${ac.truename}</td>
	
	                            <td>
	                                <div class="switch switch-small" data-on="info" data-off="success">
	                                <c:choose>
	                                <c:when test="${ac.enable==0}">
	                                    <input type="checkbox" checked="checked" autocomplete="off" name="my-checkbox"
	                                           id="accountStatus" value="${ac.enable}"
	                                           data-on-color="info" data-off-color="success" data-size="mini"
	                                           data-on-text="正常" data-off-text="冻结" onchange="mofidyAccount('${ac.id}','1')"/>
									</c:when>
									<c:otherwise>
									 <input type="checkbox"  autocomplete="off" name="my-checkbox"
	                                           id="accountStatus" value="${ac.enable}"
	                                           data-on-color="info" data-off-color="success" data-size="mini"
	                                           data-on-text="正常" data-off-text="冻结" onchange="mofidyAccount('${ac.id}','0')"/>
									
									</c:otherwise>
									</c:choose>
	                                </div>
	                            </td>
	                            <td class="operation">
<!-- 	                                <a href="" data-toggle="modal" data-target="#resetPwdModal">重置密码</a> -->
	
	                                <a class="text-danger" href="" data-toggle="modal"
	                                   data-target="#gridSystemModal" onclick="mofidyAccount('${ac.id}','3');" >删除账号</a>
	                            </td>
	                        </tr>
	                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="static/js/jquery/jquery-1.11.3.min.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="static/bootstrap/js/bootstrap.min.js"></script>
        <script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
        <script src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"
                charset="utf-8"></script>
        <script type="text/javascript">
            $("[name='my-checkbox']").bootstrapSwitch();
            //checkbox点击事件回调函数
            $('input[name="my-checkbox"]').on('switchChange.bootstrapSwitch', function (event, state) {
                if (state) {
                    alert('冻结');
                } else {
                    alert('正常');
                }
            });
            
            //改变状态  0 正常 1 冻结账号 
            function mofidyAccount(userid,status){
            	if(status=='1'){//冻结账号
            		if (confirm("确定要冻结改账号?")) {
    					var url = "mofidyChildAccountStatus?userid="+userid+"&status="+status;
    					$.post(url, function(data) {
    						if (data === 'suc') {
    							alert("已冻结!");
    				        	location.reload();	
    						} else {
    							alert("系统异常,请重试");
    						}
    					});
    				}
            	}else if(status=='0'){//解封 
            		if (confirm("确定要解封/恢复该账号?")) {
    					var url = "mofidyChildAccountStatus?userid="+userid+"&status="+status;
    					$.post(url, function(data) {
    						if (data === 'suc') {
    							alert("已解封/恢复!");
    				        	location.reload();
    						} else {
    							alert("系统异常,请重试");
    						}
    					});
    				}
            	}else {//删除子账号 3
            		if (confirm("确定要删除该员工?删除后无法恢复")) {
    					var url = "mofidyChildAccountStatus?userid="+userid+"&status="+status;
    					$.post(url, function(data) {
    						if (data === 'suc') {
    							alert("已删除!");
    				        	location.reload();
    						} else {
    							alert("系统异常,请重试");
    						}
    					});
    				}
            	}
            }
        </script>
</body>

</html>
