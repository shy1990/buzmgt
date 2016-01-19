/*$(document).ready(
		function() {
			$.ajax({
				type : "post",
				url : "/organ/getOrganList",
				// data:formValue,
				dataType : "JSON",
				success : function(result) {
					if (result) {
						var organ = $("#organization");
						organ.empty();
						for (var i = 0; i < result.length; i++) {
							organ.append("<option value = '" + result[i].id
									+ "'>" + result[i].name + "</option>");
						}

					} else {
						alert("数据加载异常！");
					}
					;
				}
			});
		});
*/

		
		/**
		 * 设置zTree树
		 */
		var setting = {
			async : {
				enable : true,
				url : "/organ/getOrganById",
				autoParam : [ "id" ],
		        dataFilter: filter
			},
			/*check : {
				enable: true,
				chkboxType: {"Y":"", "N":""}
			},*/
			data : {
				simpleData : {
					enable : true
				}
			},
			view : {
				dblClickExpand: false
			},
			callback : {
				beforeExpand : beforeExpand,
				onAsyncSuccess : onAsyncSuccess,
				onAsyncError : onAsyncError,
				//beforeClick: beforeClick,
				onClick: onClick
				
			}
		};

		function filter(treeId, parentNode, childNodes) {
			if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}
			return childNodes;
		}

		/**
		 * @author jiabin 功能：通过NodeId获得节点的孩子节点 调用：当父节点展开时，调用，返回该父节点的子节点 后台数据格式：JSON
		 * @param treeId
		 *            树控件的Id
		 * @param treeNode
		 *            树节点对象：包含Id等信息
		 * @return
		 */
		/*function getUrlByNodeId(treeId, treeNode) {
			return "area/getArea";
		}*/
		/**
		 * 展开之前执行的函数
		 * 
		 * @param treeId
		 * @param treeNode
		 * @return
		 */
		function beforeExpand(treeId, treeNode) {
			if (!treeNode.isAjaxing) {
				ajaxGetNodes(treeNode, "refresh");
				return true;
			} else {
				alert("Loading...");
				return false;
			}
		}
		/**
		 * 加载成功后执行的函数
		 * 
		 * @param event
		 *            封装了js的事件
		 * @param treeId
		 *            树控件的Id
		 * @param treeNode
		 *            树节点对象
		 * @param msg
		 *            返回的JSON格式的消息
		 * @return
		 */
		function onAsyncSuccess(event, treeId, treeNode, msg) {
			if (!msg || msg.length == 0) {
				return;
			}
			var zTree = $.fn.zTree.getZTreeObj("organTree");
			treeNode.icon = "";
			zTree.updateNode(treeNode);// 更新树结构
			zTree.selectNode(treeNode.children[0]);// 设置为第一个子节点为选中状态
		}
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
				errorThrown) {
			var zTree = $.fn.zTree.getZTreeObj("organTree");
			alert("Error ! 异步获取数据异常");
			treeNode.icon = "";
			zTree.updateNode(treeNode);
		}
		function ajaxGetNodes(treeNode, reloadType) {
			var zTree = $.fn.zTree.getZTreeObj("organTree");
			if (reloadType == "refresh") {
				treeNode.icon = "zTree/css/zTreeStyle/img/loading.gif";
				zTree.updateNode(treeNode);
			}
			zTree.reAsyncChildNodes(treeNode, reloadType, true);
		}

		function beforeClick(treeId, treeNode) {
			/*var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;*/
			/*var check = (treeNode && !treeNode.isParent);
			if (!check) alert("只能选择城市...");  */
			return true;
		}

		function onClick(e, treeId, treeNode) {
			
			var zTree = $.fn.zTree.getZTreeObj("organTree"),
			nodes = zTree.getSelectedNodes(),
			v = ""; name = "";
			nodes.sort(function compare(a,b){return a.id-b.id;});
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes.length <= 1){
					name += nodes[i].name;
					v += nodes[i].id;
				}else{
					name += nodes[i].name + ",";
					v += nodes[i].id + ","; 
				}
			}
			
			if (v.length > 0 ) v = v.substring(0, v.length-1);
			var cityObj = $("#organization");
			cityObj.empty();  
			cityObj.append("<option  selected='selected' value = '"+v+"'>"+name+"</option>");
			hideMenu();
		}

		function showOrganTree() {
			var ztreeNodes;
			var cityObj = $("#organization");
			var cityOffset = $("#organization").offset();
			$.ajax({
				async : true, // 是否异步
				cache : false, // 是否使用缓存
				type : 'post', // 请求方式,post
				dataType : "text", // 数据传输格式
				url : "/organ/getOrganById", // 请求链接
				data :{
					id : $("#organization").val()
				},
				error : function() {
					alert('访问服务器出错');
				},
				success : function(data) {
					ztreeNodes = eval("(" + data + ")"); // 将string类型转换成json对象
					// zNodes = zNodes.concat(ztreeNodes);
					$.fn.zTree.init($("#organTree"), setting, ztreeNodes);
					$("#organMenuContent").slideDown("fast");
				}
			});
			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#organMenuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			
			if (!(event.target.id == "menuBtn" || event.target.id == "organization" || event.target.id == "organMenuContent" || $(event.target).parents("#organMenuContent").length>0)) {
				hideMenu();
			}
		}