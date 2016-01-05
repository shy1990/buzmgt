		
		/**
		 * 设置zTree树
		 */
		var regionSetting = {
			async : {
				enable : true,
				url : "/region/getRegionById",
				autoParam : [ "id" ],
		        dataFilter: filter
			},
			check: {
				enable: true
			},
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
			var zTree = $.fn.zTree.getZTreeObj("regionTree");
			treeNode.icon = "";
			zTree.updateNode(treeNode);// 更新树结构
			zTree.selectNode(treeNode.children[0]);// 设置为第一个子节点为选中状态
		}
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
				errorThrown) {
			var zTree = $.fn.zTree.getZTreeObj("regionTree");
			alert("Error ! 异步获取数据异常");
			treeNode.icon = "";
			zTree.updateNode(treeNode);
		}
		function ajaxGetNodes(treeNode, reloadType) {
			var zTree = $.fn.zTree.getZTreeObj("regionTree");
			if (reloadType == "refresh") {
				treeNode.icon = "zTree/css/zTreeStyle/img/loading.gif";
				zTree.updateNode(treeNode);
			}
			zTree.reAsyncChildNodes(treeNode, reloadType, true);
		}

		function beforeClick(treeId, treeNode) {
			/*var zTree = $.fn.zTree.getZTreeObj("regionTree");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;*/
			/*var check = (treeNode && !treeNode.isParent);
			if (!check) alert("只能选择城市...");  */
			return true;
		}
		var v = ""; var name = ""; 
		function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("regionTree"),
			nodes = zTree.getSelectedNodes();
			
			nodes.sort(function compare(a,b){return a.id-b.id;});
			for (var i=0, l=nodes.length; i<l; i++) {
				   
				if(!nodes[i].isParent){
				    //	v = "";name="";isParent = "true";
				    	$("#towns").val(nodes[i].pid);
				    }else{
				    	$("#towns").val("");
				    }
				
				 
				 if(v.contains(nodes[i].id) && name.contains(nodes[i].name)){
					 
					 v = distinct(v,nodes[i].id);
						
						name = distinct(name,nodes[i].name);
						name +=  " ";
						v += " "; 
						break;
				   }
				   
					name += nodes[i].name + " ";
					v += nodes[i].id + " "; 
					
  			}
			
			//if (v.length > 0 ) v = v.substring(0, v.length-1);
			//if (name.length > 0 ) name = name.substring(0, name.length-1);
			var regionObj = $("#region");
			//regionObj.empty();  
			document.getElementById("region").options.length = 0
			regionObj.append("<option  selected='selected' value = '"+v+"'>"+name+"</option>");
		//	hideRegionMenu();
		}
		
		function distinct (str,value){
			
			 var strArr=str.split(" ");//把字符串分割成一个数组  
             
	         //   strArr.sort();//排序  
	            var result=new Array();//创建出一个结果数组  
	            var tempStr = value;  
				for ( var i in strArr) {
					if (strArr[i] != tempStr) {
						result.push(strArr[i]);
					} else {
						continue;
					}
				}
				$("#towns").val("");
				return result.join(" ");
		}

//		function showRegionTree1111() {
//				var ztreeNodes;
//				var regionObj = $("#region");
//				var cityOffset = $("#region").offset();
//				$.ajax({
//					async : true, // 是否异步
//					cache : false, // 是否使用缓存
//					type : 'post', // 请求方式,post
//					dataType : "text", // 数据传输格式
//					url : "/region/getRegionById", // 请求链接
//					data :{
//						id : $("#region").val()
//					},
//					error : function() {
//						alert('访问服务器出错');
//					},
//					success : function(data) {
//						
//						ztreeNodes = eval("(" + data + ")"); // 将string类型转换成json对象
//						// zNodes = zNodes.concat(ztreeNodes);
//						$.fn.zTree.init($("#regionTree"), regionSetting, ztreeNodes);
//						$("#regionMenuContent").slideDown("fast");
//					}
//				});
//				
//				$("body").bind("mousedown", onBodyDown);
//			
//		}
		 $("#region").click(function(){
				var ztreeNodes;
				var regionObj = $("#region");
				var cityOffset = $("#region").offset();
				$.ajax({
					async : true, // 是否异步
					cache : false, // 是否使用缓存
					type : 'post', // 请求方式,post
					dataType : "text", // 数据传输格式
					url : "/region/getRegionById", // 请求链接
					data :{
						id : $("#region").val()
					},
					error : function() {
						alert('访问服务器出错');
					},
					success : function(data) {
						ztreeNodes = eval("(" + data + ")"); // 将string类型转换成json对象
						// zNodes = zNodes.concat(ztreeNodes);
						$.fn.zTree.init($("#regionTree"), regionSetting, ztreeNodes);
						$("#regionMenuContent").slideDown("fast");
					}
				});
				
				$("body").bind("mousedown", onBodyDown);
		 });
		
		function hideRegionMenu() {
			$("#regionMenuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			
			if (!(event.target.id == "menuBtn" || event.target.id == "region" || event.target.id == "regionMenuContent" || $(event.target).parents("#regionMenuContent").length>0)) {
				hideRegionMenu();
			}
		}