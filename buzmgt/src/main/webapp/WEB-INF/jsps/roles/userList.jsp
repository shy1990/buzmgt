<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>角色查看</title>
		<!-- Bootstrap -->
		<link href="../static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="../static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="../static/css/index.css" />
		<link rel="stylesheet" type="text/css" href="../static/purview-setting/character.css" />
		<script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<%@ include file="../top_menu.jsp"%>
		<div class="container-fluid">
		<div id="" class="row">
		<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
			<%@include file="../left_menu.jsp"%>
		</div>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 padd-0">
		<div id="main" class="content main">
			<div class="row">
			    <div class="col-md-12">
					<div class="character  box border green">
						<div class="box-title"><i class="icon role-icon"></i>${name}</div>
						<div class="box-body">
							<table class="table">
								<thead>
									<th width="20%" class="center">序号</th>
									<th width="20%" class="center" >匹配人员</th>
									<th width="20%" class="center" >操作</th>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${not empty users}">
											<c:forEach var="user" items="${users}" varStatus="s">
												<tr>
													<td width="20%" class="center">${s.index+1}</td>
													<td width="20%">${user.username}</td>
													<td style="width:10px;"><a href="javascript:removeUser(${user.id})" data-toggle="modal"  data-target="#gridSystemModal" data-whatever="@mdo">取消配置</a></td>
												</tr>
											</c:forEach>
										</c:when>
										
										<c:otherwise>
											<tr>
											<td colspan="100">没有相关数据</td>
											</tr>
										</c:otherwise>
									 </c:choose>
								 </tbody>
							</table>
						</div>
					</div>
				  	<!-- alert html -->
				  	<div id="gridSystemModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
  					<div class="modal-dialog "role="document">
    					<div class="modal-content">
      					<div class="modal-header">
        					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
       						<h4 class="modal-title" id="gridSystemModalLabel">警示</h4>
						</div>
						<div class="modal-body">
							<div class="container-fluid">
								<div class="row">
									<div class="col-md-12">如若取消此配置，此业务将失去雀舌对应的权限！</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-success caution" data-dismiss="modal">不取消</button>
							<button type="button" class="btn btn-danger">确定取消</button>
						</div>
						</div>
					</div>
					</div>
					<!-- /alert html -->
				</div>
			</div>
			</div>
		</div>
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="../static/bootstrap/js/bootstrap.min.js"></script>
		<script src="../static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js" type="text/javascript" charset="utf-8"></script>
			<script src="../static/js/jquery/jquery-1.11.3.min.js"></script>
		<!-- JQUERY UI-->
		<!-- BOOTSTRAP -->
		<script src="../static/bootstrap/js/bootstrap.min.js"></script>
		<!-- CUSTOM SCRIPT -->
		<script src="../static/js/script.js"></script>
		<script type="text/javascript">
			/* 取消匹配 */
			function removeUser(userId){
				if(confirm("确定要移除该人员？")){
					var url = "delRole?id=" + id;
					$.post(url, function(data) {
						if (data === 'suc') {
							alert("删除成功!");
							location.reload();
						}else{
							alert("删除失败!,请先移除该角色下的所有人员");
						}
					});
				}
			}
		</script>
	</body>

</html>