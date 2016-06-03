<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>" />
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>业务员基础表</title>

<link href="static/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="static/bootstrap/css/bootstrap-switch.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css" href="static/css/common.css">
<link rel="stylesheet" type="text/css"
	href="static/incomeCash/css/income-cash.css">
<style>
* {
	margin: 0;
	padding: 0;
	list-style: none;
}

a {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}

.tcdPageCode {
	padding: 15px 20px;
	text-align: left;
	color: #ccc;
	margin-left: 600px;
}

.tcdPageCode a {
	display: inline-block;
	color: #428bca;
	display: inline-block;
	height: 25px;
	line-height: 25px;
	padding: 0 10px;
	border: 1px solid #ddd;
	margin: 0 2px;
	border-radius: 4px;
	vertical-align: middle;
}

.tcdPageCode a:hover {
	text-decoration: none;
	border: 1px solid #428bca;
}

.tcdPageCode span.current {
	display: inline-block;
	height: 25px;
	line-height: 25px;
	padding: 0 10px;
	margin: 0 2px;
	color: #fff;
	background-color: #428bca;
	border: 1px solid #428bca;
	border-radius: 4px;
	vertical-align: middle;
}

.tcdPageCode span.disabled {
	display: inline-block;
	height: 25px;
	line-height: 25px;
	padding: 0 10px;
	margin: 0 2px;
	color: #bfbfbf;
	background: #f2f2f2;
	border: 1px solid #bfbfbf;
	border-radius: 4px;
	vertical-align: middle;
}
</style>
<script src="../static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>

</head>
<body>
	<div class="content main">
		<h4 class="page-header ">
			<i class="ico icon-yw"></i>业务员基础数据
			<!--区域选择按钮-->
			<div class="area-choose"></div>
			<!--/区域选择按钮-->
			<a href="" class="btn btn-blue" data-toggle="modal"
				data-target="#xzyw" data-whatever="@mdo"> <i
				class="ico icon-add"></i>新增业务
			</a>
		</h4>
		<div class="row text-time">

			<div class="salesman">
				<input id="search"  class="cs-select  text-gery-hs" placeholder="  请输入业务员名称">
				<button class="btn btn-blue btn-sm"
					onclick="zhixing()">检索</button>
			</div>
			<!-- 
			<div class="link-posit-t pull-right export">
				<a class="table-export" href="javascript:void(0);">导出excel</a>
			</div>   -->

		</div>

		<div class="tab-content">

			<div class="table-task-list new-table-box table-overflow">
				<table class="table table-hover new-table tb-basin">
					<thead>
						<tr>
							<th>业务ID</th>
							<th>姓名</th>
							<th>卡号</th>
							<th>发卡行</th>
							<th>操作</th>
						</tr>
					</thead>

					<tbody id="tbody">

					</tbody>
				</table>
				<!-- 分页样式 -->
				<div class="wait-page-index">
					<ul class="pagination">
						<!-- 
						<li><a href="#" aria-label="Previous"> <span
								aria-hidden="true">&laquo;</span>
						</a></li>
						<li><a href="#">1</a></li>

						<li><a href="#">2</a></li>

						<li><a href="#">3</a></li>

						<li><a href="#">4</a></li>

						<li><a href="#">5</a></li>

						<li><a href="#" aria-label="Next"> <span
								aria-hidden="true">&raquo;</span>
						</a></li>
					 -->
					</ul>
				</div>
				<div class="tcdPageCode"></div>

				<!--table-box-->
				<!--油补记录-->

			</div>
			<!---alert删除--->
			<div id="del" class="modal fade" role="dialog">
				<div class="modal-dialog " role="document">
					<div class="modal-content modal-blue">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">是否删除</h3>
						</div>

						<div class="modal-body">
							<div class="container-fluid">
								<form class="form-horizontal">
									<input id="salesmanId" type="hidden" value=""/>
									<input id="bankId" type="hidden" value=""/>
									<div class="form-group">
										<label class="col-sm-4 control-label text-dk">姓 &nbsp;
											&nbsp; 名：</label>
										<div class="col-sm-8">
											<p class="form-control-static text-bg" id="name1"></p>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-4 control-label text-dk">发卡银行：</label>
										<div class="col-sm-8">
											<p class="form-control-static text-bg" id="bankName1"></p>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-4 control-label text-dk">银行卡号：</label>
										<div class="col-sm-8">
											<p class="form-control-static text-bg"  id="cardNumber1"></p>
										</div>
									</div>

									<div class="btn-qx">
										<a id="sure_delete" href="javascript:void(0);" class="btn btn-danger btn-d" >删除</a>
									</div>

									<div class="btn-dd">
										<button  data-dismiss="modal"
											class="btn btn-primary btn-d">取消</button>
									</div>

								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!---alert删除--->

			<!---alert新增业务--->
			<div id="xzyw" class="modal fade" role="dialog">
				<div class="modal-dialog " role="document">
					<div class="modal-content modal-blue">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">新增业务</h3>
						</div>
						<div class="modal-body">
							<div class="container-fluid">
								<form id="addd" class="form-horizontal">
									<div class="form-group">
										<label class="col-sm-4 control-label">姓 &nbsp;名：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">

													<span class="input-group-addon"><i
														class="icon icon-xm"></i></span> <input name="name" type="text"
														class="form-control input-h"
														aria-describedby="basic-addon1"> </input>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-4 control-label">业务ID：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-id"></i></span> <input name="userId" type="text"
													class="form-control input-h"
													aria-describedby="basic-addon1"> </input>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-4 control-label">银行卡号：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-yh"></i></span> <input name="cardNumber"
													type="text" class="form-control input-h"
													aria-describedby="basic-addon1"> </input>
											</div>
										</div>
									</div>


									<div class="form-group">
										<label class="col-sm-4 control-label">发卡银行：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-fk"></i></span> <select name="bankName" type=""
													class="form-control input-h "
													aria-describedby="basic-addon1">
													<option></option>
													<option>中国银行</option>
													<option>农业银行</option>
													<option>工商银行</option>
													<option>亚细亚银行</option>
												</select>
												<!-- /btn-group -->
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-4 ">
											<a herf="javascript:return 0;"
												onclick="addSalesmanData(this)"
												class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!---alert新增业务--->

			<!---alert新增银行卡--->
			<div id="xzyhk" class="modal fade" role="dialog">
				<div class="modal-dialog " role="document">
					<div class="modal-content modal-blue">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">新增银行卡业务</h3>
						</div>
						<div class="modal-body">
							<div class="container-fluid">
								<form id="addCard" class="form-horizontal">

									<div class="form-group">
										<label class="col-sm-4 control-label">发卡银行：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-fk"></i></span> <select name="bankName" type=""
													class="form-control input-h"
													aria-describedby="basic-addon1">
													<option></option>
													<option>中国银行</option>
													<option>农业银行</option>
													<option>工商银行</option>
													<option>亚细亚银行</option>
												</select>
												<!-- /btn-group -->
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-4 control-label">银行卡号：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-yh"></i></span> <input name="bankNumber" type=""
													class="form-control input-h"
													aria-describedby="basic-addon1"> </input>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-4 ">
											<a herf="javascript:return 0;" id="sure_addCard"
												class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!---alert新增银行卡--->

			<!---alert修改业务信息--->
			<div id="xgywxx" class="modal fade" role="dialog">
				<!-- 
				<div class="modal-dialog " role="document">
					<div class="modal-content modal-blue">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h3 class="modal-title">新增业务</h3>
						</div>
						<div class="modal-body">
							<div class="container-fluid">
								<form id="addd5" class="form-horizontal">
									<div class="form-group">
										<label class="col-sm-4 control-label">姓 &nbsp;名：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-xm"></i></span> <select name="a" type=""
													class="form-control input-h"
													aria-describedby="basic-addon1">
													<option></option>
													<option>花千骨</option>
													<option>白子画</option>
													<option>杀阡陌</option>
												</select>
											</div>
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-4 control-label">银行卡号：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-yh"></i></span> <input name="a" type=""
													class="form-control input-h"
													aria-describedby="basic-addon1"> </input>
											</div>
										</div>
									</div>


									<div class="form-group">
										<label class="col-sm-4 control-label">发卡银行：</label>
										<div class="col-sm-7">
											<div class="input-group are-line">
												<span class="input-group-addon"><i
													class="icon icon-fk"></i></span> <select name="b" type=""
													class="form-control input-h"
													aria-describedby="basic-addon1">
													<option></option>
													<option>中国银行</option>
													<option>农业银行</option>
													<option>工商银行</option>
													<option>亚细亚银行</option>
												</select>
											</div>
										</div>
									</div>

									<div class="form-group">
										<div class="col-sm-offset-4 col-sm-4 ">
											<a herf="javascript:return 0;" onclick="sure_xiugai(this)"
												class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				-->
			</div>
			<!---alert修改业务信息--->
		</div>

		<script src="static/salesmanData/jquery.min.js"></script>
		<script src="static/salesmanData/jquery.page.js"></script>
		<script src="static/salesmanData/jquery.min.js"></script>
		<script src="static/salesmanData/jquery.page.js"></script>
		<script src="static/bootstrap/js/bootstrap.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script src="static/bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script type="text/javascript">
		$(function(){
			//页面进来加载全部的数据
			zhixing();
		});
			//模糊查询
			function zhixing(){
				$("#tbody").empty();
				var p = 0;//当前页数
				var totalPages1 = 0;//总页数
				list(p);
				function list(p) {
				var name = $("#search").val();
				console.log("**"+name);
					$.ajax({
						url : "salesmanData/listByPage?page=" + p+"&name="+name,
						type : "post",
						async : false,//加载完毕再往下执行，不然也是还没有获得
						success : function(data) {
							totalPages1 = data.totalPages;
							var listSalesData = data.content;//所有的SalesDate
							//循环输出所有数据
							for (var i = 0; i < listSalesData.length; i++) {
								var id = listSalesData[i].id;
								var userId = listSalesData[i].userId;
								var name = listSalesData[i].name;
								var tel = listSalesData[i].tel;
								var addTime = listSalesData[i].addTime;
								var cards = listSalesData[i].card;
								add(id, userId, name, tel, addTime, cards);
							}

						}

					});
				}
				//获取所有的列表数据
				function add(id, userId, name, tel, addTime, cards) {
					var s_tr = '<tr>';
					s_tr += '<td>' + userId + '</td>';
					s_tr += '<td class="name-posit">'
							+ name
							+ '<a class="btn btn-blue btn-sm btn-card-more" data-toggle="modal" data-target="" onclick="add_card('
							+ id + ')"><i class="ico icon-add"></i>卡</a></td>';
					s_tr += '<td class="bg-style">';
					for (var j = 0; j < cards.length; j++) {
						s_tr += '<p>' + cards[j].cardNumber + '</p>';
					}
					s_tr += '</td>';
					s_tr += '<td>';
					for (var j = 0; j < cards.length; j++) {
						s_tr += '<p>' + cards[j].bankName + '</p>';
					}
					s_tr += '</td>';
					s_tr += '<td>';
					for (var j = 0; j < cards.length; j++) {
						var card = cards[j];
						var bankId = card.bankId;
						var cardNumber = card.cardNumber;
						var bankName = card.bankName;
						s_tr += '<p>';
						s_tr += '<button  class="modify btn btn-green btn-sm btn-w" data-toggle="modal" data-target="" onclick="xiugai(&quot;'
								+ name
								+ '&quot;,'
								+ id
								+ ','
								+ userId
								+ ','
								+ tel
								+ ','
								+ addTime
								+ ','
								+ bankId
								+ ','
								+ cardNumber
								+ ',&quot;' + bankName + '&quot;);">修改</button>';
						s_tr += '<button class="btn btn-warning btn-sm" data-toggle="modal"data-target="" onclick="deleteSalesmanData(&quot;'
								+ name
								+ '&quot;,'
								+ id
								+ ','
								+ bankId
								+ ','
								+ cardNumber
								+ ',&quot;' + bankName + '&quot;);">删除</button>';
						s_tr += '</p>';
					}
					s_tr += '</td>';
					s_tr += '</tr>';
					$s_tr = $(s_tr);//将tr字符串转成jquery对象

					$("#tbody").append($s_tr);
				}
				//分页
				$(".tcdPageCode").createPage({
					pageCount : totalPages1,//总页数
					current : 1,//当前页数
					backFn : function(p) {
						$("#tbody").empty();
						list(p - 1);
					}
				});
				
			}
			
			
			
			
			
			
			
			
			

			//添加新业务
			function addSalesmanData() {
				var o = $("#addd").serializeArray();
				console.log(o);
				var name = o[0]["value"];
				var userId = o[1]["value"];
				var cardNumber = o[2]["value"];
				var bankName = o[3]["value"];
				$.ajax({
					url : 'salesmanData/save',
					type : 'post',
					data : {
						name : name,
						userId : userId,
						cardNumber : cardNumber,
						bankName : bankName
					},
					success : function(data) {
						console.log(data);
						window.location.href = "salesmanData/list";
					}
				});
			}
			function xiugai(name, id, userId, tel, addTime, bankId, cardNumber,
					bankName) {

				console.log(name + '  ' + id + '  ' + userId + '  ' + tel
						+ '  ' + addTime + '  ' + bankId + '  ' + cardNumber
						+ '  ' + bankName);
				// alert($("table #tbody tr:eq("+k+") td:eq(0)").html())
				//alert($("table #tbody tr:eq("+k+") td:eq(1)").html())
				//alert($("table #tbody tr:eq("+k+") td:eq(2)").html())
				//alert($("table #tbody tr:eq("+k+") td:eq(3)").html())
				modify(name, id, userId, tel, addTime, bankId, cardNumber,
						bankName);
				$('#xgywxx').modal('show').on('shown.bs.modal', function() {
					var a = $("#addd5").serializeArray();
					console.log(a);
				});
			}
			/*
			$(".modify").on("click",function(){
				console.log(4655654);
				var data = 1;
				modify(data);
				$('#xgywxx').modal('show').on('shown.bs.modal', function() {
					
				});
			});  */
			//修改业务
			function modify(name, id, userId, tel, addTime, bankId, cardNumber,
					bankName) {
				console.log(name + '  ' + id + '  ' + userId + '  ' + tel
						+ '  ' + addTime + '  ' + bankId + '  ' + cardNumber
						+ '  ' + bankName);
				var modify_div = '			<div class="modal-dialog " role="document">'
						+ '				<div class="modal-content modal-blue">'
						+ '					<div class="modal-header">'
						+ '						<button type="button" class="close" data-dismiss="modal" aria-label="Close">'
						+ '							<span aria-hidden="true">&times;</span>'
						+ '						</button>'
						+ '						<h3 class="modal-title">新增业务</h3>'
						+ '					</div>'
						+ '					<div class="modal-body">'
						+ '						<div class="container-fluid">'
						+ '							<form id="addd5" class="form-horizontal">'
						+ '                          <input type="text" name="id" value="'+id+'"/>'
						+ '                          <input type="text" name="bankId" value="'+bankId+'"/>'
						+ '								<div class="form-group">'
						+ '									<label class="col-sm-4 control-label">姓 &nbsp;名：</label>'
						+ '									<div class="col-sm-7">'
						+ '										<div class="input-group are-line">'
						+ '											<span class="input-group-addon"><i class="icon icon-xm"></i></span> <input name="name" value="'+name+'" type="" class="form-control input-h" aria-describedby="basic-addon1">'
						+ '											</input>'
						+ '										</div>'
						+ '									</div>'
						+ '								</div>'
						+ '								<div class="form-group">'
						+ '									<label class="col-sm-4 control-label">银行卡号：</label>'
						+ '									<div class="col-sm-7">'
						+ '										<div class="input-group are-line">'
						+ '											<span class="input-group-addon"><i class="icon icon-yh"></i></span> <input name="cardNumber" value="'+cardNumber+'" type="" class="form-control input-h" aria-describedby="basic-addon1"> </input>'
						+ '										</div>'
						+ '									</div>'
						+ '								</div>'
						+ '								<div class="form-group">'
						+ '									<label class="col-sm-4 control-label">发卡银行：</label>'
						+ '									<div class="col-sm-7">'
						+ '										<div class="input-group are-line">'
						+ '											<span class="input-group-addon"><i class="icon icon-fk"></i></span> <select name="bankName" type="" class="form-control input-h" aria-describedby="basic-addon1">'
						+ '												<option>'
						+ bankName
						+ '</option>'
						+ '												<option>中国银行</option>'
						+ '												<option>农业银行</option>'
						+ '												<option>工商银行</option>'
						+ '												<option>亚细亚银行</option>'
						+ '											</select>'
						+ '										</div>'
						+ '									</div>'
						+ '								</div>'
						+ '								<div class="form-group">'
						+ '									<div class="col-sm-offset-4 col-sm-4 ">'
						+ '										<a herf="javascript:return 0;" onclick="sure_xiugai()" class="Zdy_add  col-sm-12 btn btn-primary">确定 </a>'
						+ '									</div>'
						+ '								</div>'
						+ '							</form>'
						+ '						</div>'
						+ '					</div>'
						+ '				</div>' + '			</div>';

				var $modify_div = $(modify_div);
				$("#xgywxx").append($modify_div);

			}
			//弹窗消失的时候执行的操作
			$('#xgywxx').on('hidden.bs.modal', function(e) {
				location.href = 'salesmanData/list';
			})
			//点击修改按钮执行的操作
			function sure_xiugai() {
				var d = $("#addd5").serializeArray();
				var id = d[0]["value"];
				var bankId = d[1]["value"];
				var name = d[2]["value"];
				var cardNumber = d[3]["value"];
				var bankName = d[4]["value"];

				console.log(d);
				$.ajax({
					url : 'salesmanData/modify',
					type : 'post',
					data : {
						id : id,
						bankId : bankId,
						name : name,
						cardNumber : cardNumber,
						bankName : bankName
					},
					success : function(data) {
						window.location.href = "salesmanData/list";
					}

				});
			}
			//增加银行卡
			function add_card(id) {
				console.log('****' + id);
				$("#xzyhk").modal('show').on('shown.bs.modal', function() {
					$("#sure_addCard").on('click', function() {
						var s = $("#addCard").serializeArray();
						var bankName = s[0]['value'];
						var cardNumber = s[1]['value'];
						console.log(id + "  " + bankName + "  " + cardNumber);
						$.ajax({
							url : 'salesmanData/saveBankCard',
							data : {
								bankName : bankName,
								cardNumber : cardNumber,
								id : id
							},
							type : 'post',
							success : function(data) {
								window.location.href = "salesmanData/list";
							}

						});
					});

				});

			}
			//删除的操作
			function deleteSalesmanData(name,id,bankId,cardNumber,bankName) {
				console.log(name+'   '+id+'   '+bankId+'   '+cardNumber+'   '+bankName);
				$("#cardNumber1").html(cardNumber);
				$("#name1").html(name);
				$("#bankName1").html(bankName);
				$("#salesmanId").val(id);
				$("#bankId").val(bankId);
				//弹出窗口
				$("#del").modal('show').on('shown.bs.model', function() {
					
				});
			}
			//点击确定删除按钮
			$("#sure_delete").on('click',function(){
				var id = $("#salesmanId").val();
				var bankId = $("#bankId").val();
				console.log(5454545);
				$.ajax({
					url:'salesmanData/delete/salesman/'+id+'/bankCard/'+bankId+'',
					type : 'DELETE',
					data:{id:id,
						bankId:bankId
					},
					success:function(data){
						window.location.href='salesmanData/list';
					}
					
				});
				
			});
			
		
			
		</script>
</body>
</html>


