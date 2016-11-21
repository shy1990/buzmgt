<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!-- 注意：这个是一个引用jsp，使用<-%@include%>进行引用，应为可以使用其父类需要引用 jquery，及handlebars-v4.0.2.js，extendPagination.js -->
<script id="groupuser-table-template" type="text/x-handlebars-template">
{{#if content}}
{{#each content}}
<tr>
	<td>{{addOne @index}}</td>
	<td class="J_groupuserId" data-userId='{{userId}}'>{{truename}}</td>
	<td>{{rolename}}</td>
	<td>
	{{{forStars starsLevel}}}
	{{namepath}}</td>
	<td><span class="text-zi text-strong">大学生</span></td>
	<td><span class="text-green-s">{{compare userId}}</span></td>
	<td>
	{{{isHasGroup userId}}}
	</td>
</tr>
{{/each}}
{{else}}
<tr>
	<td colspan="100">没有相关数据</td>
</tr>
{{/if}}
</script>
<script id="groupbyname-table-template" type="text/x-handlebars-template">
{{#if this}}
{{#each this}}
	{{{showGroup groupName userName userId}}}
{{/each}}
{{else}}
<li>暂无数据</li>
{{/if}}
</script>
<script type="text/javascript">
	var SearchData = {
		'page' : '0',
		'size' : '20'
	}
</script>
</head>
<body>
	<div class="content tj-body">
	<input id="planId" hidden="hidden" value="${empty planId ? achieve.planId : planId}">
	<input id="groupName" hidden="hidden">
		<div class="clearfix"></div>
		<div class="group-search">
            <select class="ph-select" id="namePath">
                <option value="">选择区域</option>
                <c:forEach var="region" items="${regions}">
                    <option value="${region.name}">${region.name}</option>
                </c:forEach>
            </select>
            <select class="ph-select" id="roleId">
                <option value=''>业务角色</option>
                <option value="262144">服务站经理</option>
                <option value="294914">扩展经理</option>
             </select>
            <select class="ph-select" id="levelName">
                <option value=''>业务等级</option>
                <option value='大学生'>大学生</option>
                <option value='中学生'>中学生</option>
                <option value='小学生'>小学生</option>
            </select>
            <select class="ph-select" id='starsLevel'>
                <option value=''>区域星级</option>
                <option value='1'>一星</option>
                <option value='2'>二星</option>
                <option value='3'>三星</option>
            </select>

            <button class="btn btn-blue btn-sm" style="margin-left: 10px"
                    onclick="goSearch(0)">检索</button>

            <input type="text" id="trueName" class="col-sm-12 big-seach"
                   placeholder="请搜索业务人员姓名">
		</div>

		<div class="tab-content ">
			<!--table-box-->

			<div class=" new-table-box table-overflow ">
				<table class="table table-hover new-table">
					<thead>
						<tr>
							<th>序号</th>
							<th>姓名</th>
							<th>角色</th>
							<th>负责区域</th>
							<th>业务等级</th>
							<th>所属分组</th>
							<th>操作</th>
						</tr>
						<tr>
					</thead>
					<tbody id="groupUserList"> </tbody>
				</table>
				<!--<button class="btn btn-blue col-sm-3" style="margin-left: 40%">确定</button>-->
			</div>
			<!--table-box-->
			<div id="initPager"></div>
			
			<!--待审核账单-->
		</div>

		<!--油补记录-->
	</div>

	<!--右侧添加-->
	<div class="fl-right" style="width: 200px; margin-top: -700px">
		<div class="group-search">

			<div class="text-gery text-strong text-zcy">组成员：</div>
			<ul class="name-list group_ul" id="nameli"> 
				<li>暂无数据</li>
			</ul>

			<div class="page-down">
				<button class="btn btn-red col-sm-5" data-dismiss="modal" style="margin: 5px 10px;">取消</button>
				<button class="btn btn-blue col-sm-5" data-dismiss="modal" onclick="addUserList();" style="margin: 5px;">确定</button>
			</div>
		</div>
	</div>
	<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="static/js/handlebars-v4.0.2.js"
		charset="utf-8"></script>
	<script type="text/javascript"
		src="static/bootStrapPager/js/extendPagination.js"></script>
	<script type="text/javascript" src="/static/achieve/group_user_list.js"> </script>
</body>

</html>
