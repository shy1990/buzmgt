<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
			<select class="ph-select">
				<option>山东省-德州市-武城县</option>
				<option>山东省-德州市-武城县</option>
				<option>山东省-德州市-武城县</option>
			</select> <select class="ph-select">
				<option>全部角色</option>
				<option>山东省-德州市-武城县</option>
				<option>山东省-德州市-武城县</option>
			</select> <select class="ph-select">
				<option>业务等级</option>
				<option>山东省-德州市-武城县</option>
				<option>山东省-德州市-武城县</option>
			</select> <select class="ph-select">
				<option>区域星级</option>
				<option>山东省-德州市-武城县</option>
				<option>山东省-德州市-武城县</option>
			</select>

			<button class="btn btn-blue btn-sm" style="margin-left: 10px">
				检索</button>

			<input type="text" class="col-sm-12 big-seach"
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
