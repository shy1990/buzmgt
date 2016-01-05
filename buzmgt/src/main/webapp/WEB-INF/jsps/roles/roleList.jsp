<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<title>权限管理</title>
<link rel='stylesheet' type='text/css' href='static/bootstrap/css/bootstrap.css'>
<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<script  src='static/bootstrap/js/bootstrap.js'></script>

</head>
<body>
<div>
 <table id="table_report" class="table table-striped table-bordered table-hover table-condensed" style="font-size: 10px;">
		<thead>
			<span style="font-family:华文中宋; color:red;">&nbsp角&nbsp 色</span>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${not empty roles}">
			<c:forEach var="role" items="${roles}" varStatus="s">
				<tr class="am-active">
					<td width="20%" class="center">${s.index+1}</td>
					<td width="20%">${role.name}</td>
					<td style="width:10px;"><a href="javascript:selRole(${role.id},'${role.name}');" class=" btn-success">查看</a></td>
					<td style="width:10px;"><a href="javascript:delRole(${role.id});" class=" btn-success">删除</a></td>
					<td style="width:10px;"><a href="javascript:editRole2Menus(${role.id});" class=" btn-success">菜单权限</a></td>
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
	<table class="page" cellpadding="0" cellspacing="5">
                <tr>                    
                    <td>
                    <div  id="pageNav" class="scott" align="center">
						<font color="#88af3f">共${totalCount} 条数据，  共${totalPage} 页</font>  ${pageNav}
                    </div>
                    
                    </td>
                    <td></td>
                </tr>
      </table>
</div>
<div>

</div>
	<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="show();">新增角色</a></td>
	<div id="pic" style="border: 1;position: absolute;width: 200;height: 200; background:#00FF99;visibility: hidden"></div>
</div>
</body>
<!-- JQUERY -->
		
		
		<!-- 引入 -->
		<script src="static/js/jquery/jquery-1.11.3.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery/jquery-1.11.3.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/bootbox/bootbox.min.js"></script><!-- 确认窗口 -->
		
		
		
		
		<script src="static/js/script.js"></script>
		<script type="text/javascript">
			top.hangge();
			/* 更改 */
			function selRole(roleId,roleName) {
				var url = "role/selByRole?id="+roleId+"&name="+   roleName;
				window.location = encodeURI(url);
			}
			/*授权菜单	*/
			function editRole2Menus(roleId) {
				var url = "role/auth?rId="+roleId;
				window.location = encodeURI(url);
			}
			function show(){
				var x=event.clientX;
				var y=event.clientY;
				document.getElementById("pic").style.top=y+50;
				document.getElementById("pic").style.left=x;
				document.getElementById("pic").style.visibility="visible";
				var formDiv="<form action='role/addRole' method='post'>";
				formDiv+="角色名称: <input type='text' name='name' id='name' /><br>";
				formDiv+="备&nbsp注: &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type='text' name='description' id='des' /><br><br>";	 
	       		formDiv+="<input type='reset' value='提交' onclick='addCheck()'/>  <input type='reset' value='取消' onclick='hide()'/>  </form>";	 
				document.getElementById("pic").innerHTML=formDiv;
			}
			
			function hide(){
				
				document.getElementById("pic").style.visibility="hidden";
			}
			
			function addCheck() {
				var url = "role/addRole?name=" + $("#name").val()+"&description="+$("#des").val();
				$.post(url, function(data) {
					if (data === 'suc') {
						alert("添加成功");
						location.reload();
					}else{
						alert("添加失败,请检查角色是否重复添加!");
					}
				});
			}
			
			/* 删除 角色*/
			function delRole(id) {
				var url = "role/delRole?id=" + id;
				$.post(url, function(data) {
					if (data === 'suc') {
						alert("删除成功!");
						location.reload();
					}else{
						alert("删除失败!");
					}
				});
			}
			
			
	</script>
		</script>
</html>