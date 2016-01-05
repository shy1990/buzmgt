﻿<%@ page language="java" import="java.util.*,com.wangge.buzmgt.sys.service.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.sf.json.JSONArray"%>
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
<title>菜单管理</title>
<link rel='stylesheet' type='text/css' href='../static/bootstrap/css/bootstrap.css'>
<script  src='../static/js/jquery/jquery.min.js'></script>
<script  src='../static/bootstrap/js/bootstrap.js'></script>

</head>
<body>
<div>
 <table id="table_report" class="table table-striped table-bordered table-hover table-condensed" style="font-size: 10px;">
		<span style="font-family:华文中宋; color:red;">&nbsp菜单管理&nbsp</span>
		<thead>
			<th width="20%" class="center">序号</th>
			<th width="20%" class="center" >菜单名称</th>
			<th width="20%" class="center" >菜单url</th>
			<th width="20%" class="center" >操作</th>
		</thead>
		</thead>
		<tbody>
		<c:choose>
			<c:when test="${not empty menus}">
			<c:forEach var="menu" items="${menus}" varStatus="s">
				<tr class="am-active">
					<td width="20%" class="center">${s.index+1}</td>
					<td width="20%">${menu.name}</td>
					<td width="20%">${menu.url}</td>
					<td style="width:10px;"><a href="javascript:removeMenu(${menu.id});" class=" btn-success">删除</a></td>
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
	<td style="vertical-align:top;"><a class="btn btn-small btn-success" onclick="show();">添加菜单</a></td>
	<div id="pic" style="border: 1;position: absolute;width: 200;height: 200; background:#00FF99;visibility: hidden"></div>
							</div>
								<a class="j_create_role btn btn-danger " data-toggle="modal"
										data-target="#exampleModal" data-whatever="@mdo"><i
										class="icon-add"></i>添加菜单</a>
									<div class="add-role modal fade " id="exampleModal"
										tabindex="-1" role="dialog"
										aria-labelledby="exampleModalLabel">
										<div class="modal-dialog" role="document">
											<div class="modal-content">
												<div class="modal-header">
													<button type="button" class="close" data-dismiss="modal"
														aria-label="Close">
														<span aria-hidden="true">&times;</span>
													</button>
													<h4 class="modal-title" id="exampleModalLabel">
														<i class="icon-add"></i>添加菜单
													</h4>
												</div>
												<div class="modal-body">
													<form action='role/addRole' method='post'>
													 <div class="form-group">
												    	<label for="inputPassword" class="col-sm-3 control-label">父菜单 </label>
												    	<div class="input-group col-sm-9 ">
												    		<span class="input-group-addon"><i class="member-icon member-role-icon"></i></span>
												      		<select class="form-control"  name="parentid">
															      <option value="0">请选择</option> 
																	  <%		
														 		 		if(null!=request.getAttribute("menuList")){
														 		 		String jsonString= request.getAttribute("menuList").toString();
																 		JSONArray jsonArr=JSONArray.fromObject(jsonString);
																 		
																 		for(int i=0;i<jsonArr.size();i++){
																 			JSONArray arr=JSONArray.fromObject(jsonArr.get(i));
																 				String id=arr.get(0).toString();
																 				String name=arr.get(1).toString();
															 		%> 
												 					  <option value="<%=id%>"><%=name%></option> 
																	 	<%
															 				
															 		}
															 				}%>
															</select>	
												    	  </div>
							 					    	</div>
														<div class="form-group">
															<label for="recipient-name"
																class="col-md-3 control-label">菜单名称</label>
															<div class="col-md-9 ">
																<input type="text" placeholder="请填写菜单名称" name ="name" class="form-control" id="recipient-name">
															</div>
														</div>
														<div class="form-group">
															<label for="message-text" class="col-md-3 control-label">菜单URL</label>
															<div class="col-md-9">
																<input type="text" placeholder="请填写菜单的URL地址" name ="name" class="form-control" id="recipient-name">
															</div>
														</div>
														<div class="modal-footer">
															<div class="col-md-3 col-md-offset-8"><input type="submit" value="确定" class="btn col-xs-12 btn-danger "/></div>
														</div>
													</form>
												</div>
											</div>
										</div>
									</div>

</body>
<!-- JQUERY -->
		
		
		<!-- 引入 -->
		<script src="../static/js/jquery/jquery.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='../static/js/jquery/jquery.min.js'>\x3C/script>");</script>
		<script src="../static/bootstrap/js/bootstrap.min.js"></script>
		<script src="../static/js/script.js"></script>
		<script type="text/javascript">
			function selectAllMenus(){
				 $.ajax({
			           type:"POST",  
			           url:'getAllMenus',
			       		dataType:"json",
			           success:function(data){
			        		$.each(data , function(i,menu){
								$("#pid").append("<option value='"+menu.id+"'>"+menu.name+"</option>");
							});	
			           },  
			       });
			}
			function show(){
				var x=event.clientX;
				var y=event.clientY;
				document.getElementById("pic").style.top=y+50;
				document.getElementById("pic").style.left=x;
				document.getElementById("pic").style.visibility="visible";
				var formDiv="<form action='addMenu' method='post'>";
				formDiv+="父菜单: <select name='parentid' id='pid' onclick='selectAllMenus()'><option value='0'>请选择</option></select><br>";
				formDiv+="菜单名称: <input type='text' name='name' id='name' /><br>";
				formDiv+="菜单URL: &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type='text' name='url' id='url' /><br><br>";	 
	       		formDiv+="<input type='submit' value='提交' oncheck='hide()'/>  <input type='reset' value='取消' onclick='hide()'/>  </form>";	 
				document.getElementById("pic").innerHTML=formDiv;
			}
			
			function hide(){
				
				document.getElementById("pic").style.visibility="hidden";
			}
			
			function addCheck() {
				var url = "addMenu?name=" + $("#name").val()+"&url="+$("#url").val()+"&pid="+$("#pid").val();
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
			function removeMenu(id) {
				var url = "removeMenu?id=" + id;
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