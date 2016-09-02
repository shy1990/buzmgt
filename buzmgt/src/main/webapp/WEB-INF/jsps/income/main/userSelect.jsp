<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/static/income/phone.css">
<link rel="stylesheet" type="text/css"
	href="/static/income/plan_index.css" />
<script id="user-table-template" type="text/x-handlebars-template">
{{#if content}}
	{{#each content}}
     <tr>
									<td>{{addOne @index}}</td>
									<td>{{truename}}</td>
									<td>{{userId}}</td>
									<td>{{rolename}}</td>
									<td><i class="icon-x {{getImg starsLevel 2}}"></i><i
										class="icon-x {{getImg starsLevel 2}}"></i><i class=" icon-x {{getImg starsLevel 2}}"></i>
										{{namepath}}
										</td>
									<td><span class="text-{{getColore levelName}} text-strong">{{levelName}}</span></td>
									<td>{{regdate}}</td>
								{{#if planId}}
									<td><a   href="javascript:openPlan('{{planId}}');"><span class="text-lv text-strong">{{plantitle}}</span></a>
									</td>
									<td><i class="icon icon-un" title="已有所属方案，不可勾选"></i></td>
								{{else}}
									<td><span class="text-gery"></span></td>
									<td><label for="input-2"></label> <input 
										type="checkbox" onclick="addUserArr('{{userId}}','{{truename}}')"></td>
								{{/if}}

	  </tr>
	{{/each}}
{{/if}}

</script>


<div class="modal-dialog" role="document" style="width: 980px;">
	<div class="modal-content modal-blue">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<h3 class="modal-title">添加人员</h3>
		</div>
		<div class="modal-body">



			<div class="content main">
				<div class="clearfix"></div>
				<div class="group-search">
					<select class="ph-select" id="region">
						<option value=''>选择区域</option>
						<c:forEach var="region" items="${regions}">
							<option value="${region.id}">${region.name}</option>
						</c:forEach>
					</select> <select class="ph-select" id="role">
						<option value=''>全部角色</option>
						<c:forEach var="region" items="${roles}">
							<option value="${region.name}">${region.name}</option>
						</c:forEach>
					</select> <select class="ph-select" id="busiLevel">
						<option value=''>业务等级</option>
						<option value='大学生'>大学生</option>
						<option value='中学生'>中学生</option>
						<option value='小学生'>小学生</option>

					</select> <select class="ph-select" id='starLevel'>
						<option value=''>区域星级</option>
						<option value='1'>一星</option>
						<option value='2'>二星</option>
						<option value='3'>三星</option>
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
									<th>业务ID</th>
									<th>角色</th>
									<th>负责区域</th>
									<th>业务等级</th>
									<th>入职时间</th>
									<th>当前所属方案</th>
									<th>操作</th>
								</tr>
								<tr>
							</thead>
							<tbody id="userList">
								<tr>
									<td>01</td>
									<td>李易峰</td>
									<td>A1158584584854151</td>
									<td>服务站经理</td>
									<td><i class=" icon-x ico-xx"></i><i
										class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i>
										山东省济南市天桥区</td>
									<td><span class="text-zi text-strong">大学生</span></td>
									<td>2016.08.19</td>
									<td><span class="text-gery">无</span></td>
									<td><label for="input-2"></label> <input id="input-2"
										type="checkbox" checked></td>
								</tr>

								<tr>
									<td>03</td>
									<td>李易峰</td>
									<td>A1158584584854151</td>
									<td>服务站经理</td>
									<td><i class=" icon-x ico-xx"></i><i
										class=" icon-x ico-xx"></i><i class=" icon-x ico-xx"></i>
										山东省济南市天桥区</td>
									<td><span class="text-lan text-strong">小学生</span></td>
									<td>2016.08.19</td>
									<td><a href=""><span class="text-lv text-strong">代理商C方案</span></a>
									</td>
									<td><i class="icon icon-un" title="已有所属方案，不可勾选"></i></td>
								</tr>







							</tbody>
						</table>

					</div>
					<!--table-box-->

				</div>
				<div id="usersPager"></div>
				<button class="btn btn-blue col-sm-3" style="margin-left: 40%"
					onclick="pushAll();">确定</button>
				<!--待审核账单-->
				<!--油补记录-->
			</div>
		</div>
	</div>
</div>
<script src="/static/income/main/userSelect.js" type="text/javascript"
	charset="utf-8"></script>
<script type="text/javascript">
	var salesAddArr = [];
	//向salesAddArr里添加元素
	function addUserArr(saleId, username) {
		var salesman = {
			"salesmanId" : saleId,
			"salesmanname" : username
		};
		var index = salesAddArr.indexOf(salesman);
		if (index > -1) {
			salesAddArr.splice(index, 1);
		} else {
			salesAddArr.push(salesman);
		}
	}
	//全部添加
	function pushAll() {
		for (var i = 0; i < salesAddArr.length; i++) {
			addUser(salesAddArr[i].salesmanId, salesAddArr[i].salesmanname,
					false);
		}
		$('#user').modal('hide');
		salesAddArr = [];
		$('input:checkbox:checked').each(function(i) {
			$(this).prop('checked', false);
		});
	}
</script>



</html>