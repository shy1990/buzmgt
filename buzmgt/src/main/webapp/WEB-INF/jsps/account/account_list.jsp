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
		<link rel="stylesheet" type="text/css" href="static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="static/css/menu.css" />
		<link rel="stylesheet" type="text/css" href="static/yw-team-member/team-member.css" />
		<link rel="stylesheet" type="text/css" href="static/account-manage/account-list.css" />
		<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>
		<div class="content main">
			<h4 class="page-header">
				<i class="ico ico-account-manage"></i>账号管理
				<!--区域选择按钮-->
					<div class="area-choose">选择区域：<span>${regionName}</span> <a class="are-line" href="javascript:;" onclick="getRegion(${regionId});">切换</a> </div>
				<!--/区域选择按钮-->
			</h4>
			<div class="row">
				<div class="col-md-12">
					<div class="account-list  box border blue">
						<!-- box-title -->
						<div class="box-title">
							<div class="row">
								<div class="col-sm-12">
									<!--菜单栏-->
									<ul class="nav nav-tabs">
									<li>
									</ul>
									<!--/菜单栏-->
								</div>
							</div>
						</div>
						<!-- box-title -->
						<!--box-body-->
						<div class="box-body tab-content">
							<div class="tab-pane fade in active" id="box_tab1">
								<div class="hr-solid-sm"></div>	
							<ul class="nav nav-job">
								<c:choose>
									<c:when test="${not empty org}">
										<c:if test="${org=='allUsed'}">
											<li class="active"><a href= "javascript:selectByOrg('allUsed','used');"> 全部(在职)</a></li>
											<li ><a href= "javascript:selectByOrg('服务站经理','0');"> 服务站经理(在职)</a></li>
											<li ><a href= "javascript:selectByOrg('大区总监','0');"> 大区总监(在职)</a></li>
											<li ><a href= "javascript:selectByOrg('allDis','2');">已辞退</a></li>
										</c:if>
										<c:if test="${org=='服务站经理'}">
											<li ><a href= "javascript:selectByOrg('allUsed','used');"> 全部(在职)</a></li>
											<li class="active"><a href= "javascript:selectByOrg('服务站经理','0');"> 服务站经理(在职)</a></li>
											<li ><a href= "javascript:selectByOrg('大区总监','0');"> 大区总监(在职)</a></li>
											<li ><a href= "javascript:selectByOrg('allDis','2');">已辞退</a></li>
										</c:if>
										<c:if test="${org=='大区总监'}">
											<li ><a href= "javascript:selectByOrg('allUsed','used');"> 全部(在职)</a></li>
											<li ><a href= "javascript:selectByOrg('服务站经理','0');"> 服务站经理(在职)</a></li>
											<li class="active"><a href= "javascript:selectByOrg('大区总监','0');"> 大区总监(在职)</a></li>
											<li ><a href= "javascript:selectByOrg('allDis','2');">已辞退</a></li>
										</c:if>
										<c:if test="${org=='allDis'}">
											<li ><a href= "javascript:selectByOrg('allUsed','used');"> 全部(在职)</a></li>
											<li ><a href= "javascript:selectByOrg('服务站经理','0');"> 服务站经理(在职)</a></li>
											<li ><a href= "javascript:selectByOrg('大区总监','0');"> 大区总监(在职)</a></li>
											<li class="active"><a href= "javascript:selectByOrg('allDis','2');">已辞退</a></li>
										</c:if>
									</c:when>
									<c:otherwise>
										<li class="active"><a href= "javascript:selectByOrg('allDis','used');"> 全部(在职)</a></li>
										<li ><a href= "javascript:selectByOrg('服务站经理','0');"> 服务站经理(在职)</a></li>
										<li ><a href= "javascript:selectByOrg('大区总监','0');"> 大区总监(在职)</a></li>
										<li ><a href= "javascript:selectByOrg('allDis','2');">已辞退</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
							<script type="text/javascript">
								//	$('.nav-job li').click(function(){
								//	$(this).addClass('active');
								//	$(this).siblings('li').removeClass('active');
								//  });
							</script>
							<div class="hr-solid-sm "></div>
								<div class="table-responsive">
								
								<table id="table_report" class="table-hover table">
									<thead>
										<th width="5%" class="center">序号</th>
										<th width="10%" class="center">职务</th>
										<th width="10%" class="center">账号</th>
										<th width="10%" class="center">姓名</th>
										<th width="10%" class="center">负责区域</th>
										<th width="10%" class="center">角色权限</th>
										<th width="5%" class="center">账号状态</th>
										<th width="20%" class="center">操作</th>
										<th width="20%" class="center">子账号</th>
									</thead>
									<tbody>
										<c:choose>
											<c:when test="${not empty accounts}">
												<c:forEach var="ac" items="${accounts}" varStatus="s">
													<tr class="am-active">
														<td width="5%" class="center">${s.index+1}</td>
														<td width="10%">${ac.position}</td>
														<td width="10%">${ac.accountNum}</td>
														<td width="10%">${ac.name}</td>
														<td width="10%">${ac.areaName}</td>
														<td width="10%">${ac.roleName}</td>
														<td width="10%">
															<div class="switch switch-small"  data-on="info" data-off="success">
															<c:choose>
																<c:when test="${ac.status==2}">
																		<input type="checkbox"	checked="checked" autocomplete="off" name="my-checkbox" id="accountStatus" value="${ac.accountNum}" 
																			data-on-color="info" data-off-color="success" data-size="mini" data-on-text="冻结" data-off-text="正常" readonly/>
																</c:when>
																<c:otherwise>
																	<c:choose>
																		<c:when test="${ac.status==0}">
																			<input type="checkbox"	checked="checked" autocomplete="off" name="my-checkbox" id="accountStatus" value="${ac.accountNum}" 
																				data-on-color="info" data-off-color="success" data-size="mini" data-off-text="冻结" data-on-text="正常" onchange="mofidyAccount('${ac.accountNum}','0')"/>
																		</c:when>
																		<c:otherwise>
																			<input type="checkbox" autocomplete="off" name="my-checkbox" id="accountStatus" value="${ac.status}" 
																				data-on-color="info" data-off-color="success" data-size="mini" data-off-text="冻结" data-on-text="正常" onchange="mofidyAccount('${ac.accountNum}','1')"/>
																		</c:otherwise>
																	</c:choose>
																</c:otherwise>
															</c:choose>
															</div>
														</td>
														<c:choose>
															<c:when test="${ac.status==2}">
															 <td width="20%" class="operation">
																	<a class="text-danger" href="javascript:mofidyAccount('${ac.accountNum}','1');">恢复账号</a>
																	<c:choose>
																		<c:when test="${ac.status==2}">
																			<a class="text-danger" href="javascript:mofidyAccount('${ac.accountNum}','3');">删除</a>
																		</c:when>
																		<c:otherwise>
																			<a class="text-danger" href="javascript:mofidyAccount('${ac.accountNum}','2');">辞退</a>
																		</c:otherwise>
																	</c:choose>
																</td>
															</c:when>
															<c:otherwise>
																<td width="20%" class="operation">
																	<a href="javascript:resetPwd('${ac.accountNum}');" >重置密码</a>
																	<a href= "javascript:modifyAccount('${ac.accountNum}','${ac.position}');"> 修改资料</a>
																	<c:choose>
																		<c:when test="${ac.status==2}">
																			<a class="text-danger" href="javascript:mofidyAccount('${ac.accountNum}','3');">删除</a>
																		</c:when>
																		<c:otherwise>
																			<a class="text-danger" href="javascript:mofidyAccount('${ac.accountNum}','2');">辞退</a>
																		</c:otherwise>
																	</c:choose>
																</td>
																<td>
																	
																	<a class="" href="" data-toggle="modal" onclick="addAccount('${ac.accountNum}');"s><img
									                                        src="static/img/addcode/tj.png" >添加</a>
									                                <a  href="javascript:findChildAccount('${ac.accountNum}');" aria-controls="pofile" role="tab" data-target="tab">${ac.childCount}个子账号<img
									                                        src="static/img/addcode/jl.png"></a>
																</td>
															</c:otherwise>
														</c:choose>
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
							<div id="pageNav" class="scott" align="center">
								<font color="#88af3f">共${totalCount} 条数据，
									共${totalPage} 页</font> 
								<div class="page-link" >${pageNav}</div>
							</div>	
						</div>
						<!-- box-body -->
					</div>
					<!--box-->
						<!-- alert resetPwd html -->
								<div id="resetPwdModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
									<div class="modal-dialog " role="document">
										<div class="modal-content">
											<div class="modal-header">
												<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
												<h3 class="modal-title" id="gridSystemModalLabel">重置密码</h4>
											</div>
											<div class="modal-body">
												<div class="container-fluid">
													<div class="row">
														<div class="col-md-12">
															重置密码后将恢复初始状态！
														</div>
													</div>
												</div>
											</div>
											<div class="modal-footer">
												<button type="button" class="btn btn-success caution" data-dismiss="modal">取消</button>
												<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="resetPwd('${ac.accountNum}')";>确定</button>
											</div>
										</div>
									</div>
								</div>
					<!-- /alert html -->
					
					<!-- alert 辞退 html -->
						<div id="gridSystemModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
							<div class="modal-dialog " role="document">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
										<h3 class="modal-title" id="gridSystemModalLabel">辞退</h4>
									</div>
									<div class="modal-body">
										<div class="container-fluid">
											<div class="row">
												<div class="col-md-12">
													<h4 class="text-danger">您确定要辞退该员工么？</h4>
													<p>1.若辞退该员工，2日后该员工将无法正常使用APP。</p>
													<p>2.区域经理请确保该员工无拖欠贷款，以及辞退后该区域的配送。</p>
													<p>3.辞退后该信息将会推送至人资，请做好月该员工的交接工作。</p>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-danger" data-dismiss="modal" >确定辞退</button>
										<button type="button" class="btn btn-success caution" data-dismiss="modal">以后再说</button>
									</div>
								</div>
							</div>
						</div>
				<!-- /alert html -->
					<!-- alert html -->
					<div id="deleteAccountModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
						<div class="modal-dialog " role="document">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									<h3 class="modal-title" id="gridSystemModalLabel">提示</h4>
								</div>
								<div class="modal-body">
									<div class="container-fluid">
										<div class="row">
											<div class="col-md-12">
												您确定要删除此用户吗？删除后此用户的信息将不在保留！
											</div>
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-success caution" data-dismiss="modal">不删除</button>
									<button type="button" class="btn btn-danger" data-dismiss="modal">确定删除</button>
								</div>
							</div>
						</div>
					</div>
					<!-- /alert html -->
					
					 <!-- /alert html添加子账号 -->
	                  <div id="addAccount" class="modal fade" role="dialog" >
		                <div class="modal-dialog " role="document">
		                    <div class="modal-content modal-blue">
		                        <div class="modal-header">
		                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
		                                    aria-hidden="true">&times;</span></button>
		                             <h3 class="modal-title" id="">添加子账号</h3>
		                        </div>
		                        <div class="modal-body">
		                            <div class="container-fluid">
		                                    <div class="form-group">
		                                        <label for="" class="col-sm-2 control-label">姓名：</label>
		                                        <div class="col-sm-10">
		                                            <div class="input-group are-line">
		                                                <span class="input-group-addon" id=""><i class="icon icon-user"></i></span>
		                                                <input type="text" class="form-control input-h" id="truename" name="truename" placeholder="请输入姓名" aria-describedby="basic-addon1">
		                                                <input type="hidden"  id="userId" name="userId" >
		                                            </div>
		                                        </div>
		                                    </div>
		                                    <div class="form-group">
		                                        <div class="col-sm-offset-4 col-sm-4">
		                                            <button type="button" class="col-sm-12 btn btn-primary" onclick="addChildAccount();">确定</button>
		                                        </div>
		                                    </div>
		                                <div class="col-sm-offset-5 col-sm-7">
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
		            </div>
					
					
					
				</div>
			</div>
		</div>
	</body>
	<script src="static/bootstrap/js/bootstrap-switch.min.js"></script>
	<script src="static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js" type="text/javascript" charset="utf-8"></script>
	
	<script src='/static/bootstrap/js/bootstrap.js'></script>
	<script src='/static/js/common.js'></script>
	 <script src="/static/bootstrap/js/bootstrap-switch.min.js"></script>
        <script src="/static/js/jquery/scroller/jquery.mCustomScrollbar.concat.min.js" type="text/javascript"
                charset="utf-8"></script>
	<script type="text/javascript">
		window.jQuery	|| document.write("<script src='../static/js/jquery.min.js'>\x3C/script>");
	</script>
	<script type="text/javascript">
		$("[name='my-checkbox']").bootstrapSwitch();
		//checkbox点击事件回调函数
		$('input[name="my-checkbox"]').on('switchChange.bootstrapSwitch', function(event, state) {
			console.log(this); // DOM element
			console.log(event); // jQuery event
			console.log(state); // true | false
		//	alert(event.target);
			if(state){
		//		alert(state+"==="+event.target.value);
			}else{
			//	alert(state+"---"+event.target.value);
			}
		});
		//修改资料
		function modifyAccount(accountNum,position){
			var url = "modifyAccount?accountNum=" + accountNum+"&position="+position;
			window.location = encodeURI(url);
		}
		//根据职务查询
		function selectByOrg(orgName,status){
			var url = "accountManage?orgName=" + orgName+"&status="+status;
			window.location = encodeURI(url);
		//	 document.getElementById("all").className="active";
		}
		// 重置密码
		function resetPwd(accountNum){
			if (confirm("确定要重置密码？	初始密码1234567")) {
				var url = "resetPwd?id=" + accountNum;
				$.post(url, function(data) {
					if (data === 'suc') {
						alert("重置成功!");
						setTimeout(function(){
			        		location.reload()
			        		},3000);
					} else {
						alert("系统异常,请重试");
					}
				});
			}
		}
		// 修改账号状态,0正常,1冻结,2辞退,3删除
		function mofidyAccount(accountNum,status){
			if(status=='2'){
				if (confirm("确定要辞退该员工")) {
					var url = "mofidyAccountStatus?id=" + accountNum+"&status="+status;
					$.post(url, function(data) {
						if (data === 'suc') {
							alert("已辞退!");
				        	location.reload();
						} else {
							alert("系统异常,请重试");
						}
					});
				}   
			}else if(status=='3'){
				if (confirm("确定要删除该员工?删除后无法恢复")) {
					var url = "mofidyAccountStatus?id=" + accountNum+"&status="+status;
					$.post(url, function(data) {
						if (data === 'suc') {
							alert("已删除!");
				        	location.reload();
						} else {
							alert("系统异常,请重试");
						}
					});
				}
			}else if(status=='0'){
				if (confirm("确定要冻结改账号?")) {
					var url = "mofidyAccountStatus?id=" + accountNum+"&status="+status;
					$.post(url, function(data) {
						if (data === 'suc') {
							alert("已冻结!");
				        	location.reload();	
						} else {
							alert("系统异常,请重试");
						}
					});
				}
			}else if(status=='1'){
				if (confirm("确定要解封/恢复该账号?")) {
					var url = "mofidyAccountStatus?id=" + accountNum+"&status="+status;
					$.post(url, function(data) {
						if (data === 'suc') {
							alert("已解封/恢复!");
				        	location.reload();
						} else {
							alert("系统异常,请重试");
						}
					});
				}
			}
		}
		
		
		/*区域 */
		function getRegion(id){
			window.location.href='/region/getPersonalRegion?flag='+"account";
		}
		
	     $("[name='my-checkbox']").bootstrapSwitch();
         //checkbox点击事件回调函数
         $('input[name="my-checkbox"]').on('switchChange.bootstrapSwitch', function (event, state) {
             console.log(this); // DOM element
             console.log(event); // jQuery event
             console.log(state); // true | false
             if (state) {
                 alert('冻结');
             } else {
                 alert('正常');
             }
         });
		
		
         
         
         //弹出子账号model 
         function addAccount(id){
        	 $("#userId").val(id);
        	 $('#addAccount').modal({
					keyboard: false
				})
			
         }
         
         
         
         
         //添加子账号
        function addChildAccount(){
        	 var truename=$("#truename").val();
        	 var userId=$("#userId").val();
        	 window.location.href="/addChildAccount?truename="+truename+"&userId="+userId;
        	 
         }
         
         
         //子账号列表展示
         function findChildAccount(userId){
        	 window.location.href="/findChildAccount?userId="+userId;
         }
         
         
	</script>
</html>
