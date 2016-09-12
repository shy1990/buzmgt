var rMenu;
/**
 * 设置zTree树
 */
var setting = {
	async : {
		enable : true,
		url : "region/findRegionByid",
		autoParam : [ "id" ]
	},
	check : {
		enable : false
	},
	edit : {
		drag : {
			autoExpandTrigger : true,
			prev : dropPrev,
			inner : dropInner,
			next : dropNext
		},
		enable : true,
		showRemoveBtn : showRemoveBtn,
		showRenameBtn : showRenameBtn
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	view : {
		expandSpeed : "",
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		fontCss : {'font-weight': "bolder"}
		//addDiyDom : addDiyDom
	},
	callback : {
		beforeExpand : beforeExpand,
		onAsyncSuccess : onAsyncSuccess,
		onAsyncError : onAsyncError,
		onClick : zTreeOnClick,
		beforeDrag : beforeDrag,
		beforeEditName : beforeEditName,
		beforeRemove : beforeRemove,
		beforeRename : beforeRename,
		onRemove : zTreeOnRemove,
		onRename : zTreeOnRename,
		onDrop : onDrop
		
	/*
	 * beforeDrop: beforeDrop, beforeDragOpen: beforeDragOpen, onDrag: onDrag,
	 * onDrop: onDrop, onExpand: onExpand
	 */
	}
};
var zNodes = [ {
	name:"中国", font:{'font-weight':'italic'},
	id : "0",
	isParent : true,
	open : true,
	iconOpen : "static/zTree/css/zTreeStyle/img/diy/zhongguo.png",
	iconClose : "static/zTree/css/zTreeStyle/img/diy/zhongguo.png"
} ];

function getFont(treeId, node) {
	return node.font ? node.font : {};
}


/**
 * @author jiabin 功能：通过NodeId获得节点的孩子节点 调用：当父节点展开时，调用，返回该父节点的子节点 后台数据格式：JSON
 * @param treeId
 *            树控件的Id
 * @param treeNode
 *            树节点对象：包含Id等信息
 * @return
 */
function getUrlByNodeId(treeId, treeNode) {
	return "area/getArea";
}
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
		 BootstrapDialog.alert("Loading...");
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
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	treeNode.icon = "";
	zTree.updateNode(treeNode);// 更新树结构
	zTree.selectNode(treeNode.children[0]);// 设置为第一个子节点为选中状态
}
function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
		errorThrown) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	//alert("Error ! 异步获取数据异常");
	BootstrapDialog.alert("Error ! 异步获取数据异常");
	treeNode.icon = "";
	zTree.updateNode(treeNode);
}
function ajaxGetNodes(treeNode, reloadType) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	if (reloadType == "refresh") {
		treeNode.icon = "static/zTree/css/zTreeStyle/img/loading.gif";
		zTree.updateNode(treeNode);
	}
	zTree.reAsyncChildNodes(treeNode, reloadType, true);
}

/**
 * 功能：当点击树节点时，调用该函数
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function zTreeOnClick(event, treeId, treeNode) {
//	 document.getElementById("iframepage").src = "area/right?id=" +	 treeNode.id;
}


/**
 * 功能：用于捕获节点编辑按钮的 click 事件，并且根据返回值确定是否允许进入名称编辑状态
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
}
/**
 * 功能：用于捕获节点被删除之前的事件回调函数，并且根据返回值确定是否允许删除操作
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function beforeRemove(treeId, treeNode) {

	if (confirm("确认删除 节点 -- " + treeNode.name + " 吗？")) {
		var flag = true;
		$.ajax({
			async : false, // 是否异步
			cache : false, // 是否使用缓存
			type : 'post', // 请求方式,post
			data : {
				id : treeNode.id,
			},
			dataType : "text", // 数据传输格式
			url : "region/deleteRegionbyId", // 请求链接
			success : function(data) {
				if (data === 'true') {
					BootstrapDialog.alert("删除成功");
					flag = true;
				} else {
					BootstrapDialog.alert("有区域、扫街任务、扫街数据、注册数据子节点不能删除");
					flag = false;
				}
			}
		});
		return flag;
	} else {
		return false;
	}

}

function zTreeOnRemove(event, treeId, treeNode) {
	
	return true;
}

/**
 * 功能：用于捕获节点编辑名称结束（Input 失去焦点 或 按下 Enter
 * 键）之后，更新节点名称数据之前的事件回调函数，并且根据返回值确定是否允许更改名称的操作
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "" : "dark");
	if (newName.length == 0) {
		alert("节点名称不能为空");
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		setTimeout(function() {
			zTree.editName(treeNode)
		}, 10);
		return false;
	}
	return true;
}
/**
 * 功能：用于捕获节点编辑名称结束之后的事件回调函数。
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function zTreeOnRename(event, treeId, treeNode, isCancel) {
	$.ajax({
		async : true, // 是否异步
		cache : false, // 是否使用缓存
		type : 'post', // 请求方式,post
		data : {
			 id :treeNode.id,
			pid : treeNode.pId,
			name : treeNode.name,
		},
		dataType : "text", // 数据传输格式
		url : "region/editRegion"
	});
}
/**
 * 功能：设置是否显示删除按钮。[setting.edit.enable = true 时生效]
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function showRemoveBtn(treeId, treeNode) {
	return true;
}
/**
 * 功能：设置是否显示编辑名称按钮。[setting.edit.enable = true 时生效]
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function showRenameBtn(treeId, treeNode) {
	return true;
}


/**
 * 功能：用于当鼠标移动到节点上时，显示用户自定义控件，显示隐藏状态同 zTree 内部的编辑、删除按钮
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
var newCount = 0;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	
	/******************添加区域 start***************************************/
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
	+ "' title='添加区域' onfocus='this.blur();'></span>";
	//sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		$.ajax({
			async : true, // 是否异步
			cache : false, // 是否使用缓存
			type : 'post', // 请求方式,post
			data : {
				pid : treeNode.id,
				name : "新自定义区域"
			},
			dataType : "text", // 数据传输格式
			url : "region/addRegion", // 请求链接
			error : function() {
				BootstrapDialog.alert("访问服务器出错");
			},	
			success : function(data) {
				ztreeNodes = eval("(" + data + ")"); // 将string类型转换成json对象
				if(ztreeNodes.regiontype === 'false'){
					BootstrapDialog.alert("所添加节点超出级别");
					return false;
				}
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				zTree.addNodes(treeNode, {
					id : (ztreeNodes.id),
					pId : ztreeNodes.pId,
					name : ztreeNodes.name
				});
				return false;
			}
		});
		return false;
	});
	/******************添加区域 end***************************************/
	if (treeNode.editNameFlag || $("#mapBtn_"+treeNode.tId).length>0) return;
	var mapStr = "<span class='button map' id='mapBtn_" + treeNode.tId + "' title='绘制子区域地图' onfocus='this.blur();'></span>";
	sObj.after(mapStr);
	var btn = $("#mapBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		window.location.href='/region/initRegionMap?regionName='+treeNode.name+"&parentid="+treeNode.id;
	});

	getStars(treeNode,sObj);


}


  function getStars(treeNode,sObj) {
	  <!--------------------------------区域星级end-------------------------------------->
	  $.ajax({
		  async : true, // 是否异步
		  cache : false, // 是否使用缓存
		  type : 'post', // 请求方式,post
		  data : {
			  id : treeNode.id
		  },
		  dataType : "text", // 数据传输格式
		  url : "region/regionStarsLeave", // 请求链接
		  error : function() {
			  BootstrapDialog.alert("访问服务器出错");
		  },

		  success : function(data) {
			  var regionid=treeNode.id;
			  var starsOneclass="button starstwo";
			  var starstwoclass="button starstwo";
			  var starsthreeclass="button starstwo";

			  var starsLevae = eval("(" + data + ")"); // 将string类型转换成json对象
			  if(starsLevae===1){
				  starsOneclass="button starsone";//一个实心的
			  }
			  if(starsLevae===2){
				  starsOneclass="button starsone";
				  starstwoclass="button starsone";
			  }
			  if(starsLevae===3){
				  starsOneclass="button starsone";
				  starstwoclass="button starsone";
				  starsthreeclass="button starsone";
			  }
			  var classid=treeNode.tId+"";
			  var starsthreeStr = "<span class='" + starsthreeclass + "' id='starsthreeBtn_" + treeNode.id + "' " +
				  "title='三星' onclick='clickStars(3,"+regionid+")'></span>";
			  $("#"+treeNode.tId+"_remove").after(starsthreeStr);

			  var starstwoStr = "<span class='"+starstwoclass+"' id='starstwoBtn_" + treeNode.id
				  + "' title='二星' onclick='clickStars(2,"+regionid+")'></span>";
			  $("#"+treeNode.tId+"_remove").after(starstwoStr);

			  var starsoneStr = "<span class='"+starsOneclass+"' id='starsoneBtn_" + treeNode.id + "' " +
				  "title='一星' onclick='clickStars(1,"+regionid+")'></span>";
			  $("#"+treeNode.tId+"_remove").after(starsoneStr);

			  var whitespace = "<span class='button whitespace' id='whitespace" + treeNode.id
				  + "' ></span>";
			  $("#"+treeNode.tId+"_remove").after(whitespace);
		  }
	  });
	  return false;
  }

  //点击星方法
  function clickStars(flag,id){
	  console.log(flag+"**"+id);
	  var statsLevae;
    if(flag===1){ //点击第一颗星
		var towClass=$("#starstwoBtn_"+id+"").attr("class");
		console.log(towClass);
		if(towClass==='button starsone'){//实心的
			alert("请先点击第二颗或者第三颗星");
			return;
		}else{//正常
			var oneClass=$("#starsoneBtn_"+id+"").attr("class");
			if(oneClass==='button starsone'){//判断1星当前状态 实心
				statsLevae=0;
			}else{
				statsLevae=1;
			}
		}
	}

	  if(flag===2){ //点击第二颗星
		  var oneClass=$("#starsoneBtn_"+id+"").attr("class");
		  var threeClass=$("#starsthreeBtn_"+id+"").attr("class");
		  console.log(towClass);
		  if(oneClass==='button starsone'&&threeClass==='button starsone'){//实心的
			  alert("请点击第三颗星");
			  return;
		  }else if(oneClass==='button starstwo'){//第一颗星是空的
			  alert("请点击第一颗星");
			  return;
		  }else{//正常
			  var twoClass=$("#starstwoBtn_"+id+"").attr("class");
			  if(twoClass==='button starsone'){//判断2星当前状态 实心
				  statsLevae=1;
			  }else{
				  statsLevae=2;
			  }
		  }
	  }

	  if(flag===3) { //点击第三颗星
		  var towClass=$("#starstwoBtn_"+id+"").attr("class");
		  if(towClass==='button starstwo'){//第二个是空的
			  alert("请先点击第一颗或者第二颗星");
			  return;
		  }else{//正常
			  var threeClass=$("#starsthreeBtn_"+id+"").attr("class");
			  if(threeClass==='button starsone'){//判断3星当前状态 实心
				  statsLevae=2;
			  }else{
				  statsLevae=3;
			  }
		  }
	   }
	  updateStarsLeave(statsLevae,id);
  }

/**
 * 后台更改星级
 */
function  updateStarsLeave(statsLevae,id){
	$.ajax({
		async : true, // 是否异步
		cache : false, // 是否使用缓存
		type : 'post', // 请求方式,post
		data : {
			id : id,
			statsLevae : statsLevae

		},
		dataType : "text", // 数据传输格式
		url : "region/updateStarsLeave" // 请求链接
	});
}



/**
 * 功能：用于当鼠标移出节点时，隐藏用户自定义控件，显示隐藏状态同 zTree 内部的编辑、删除按钮
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */

function removeHoverDom(treeId, treeNode) {
	$("#diyBtn_" + treeNode.id).unbind().remove();
	$("#addBtn_" + treeNode.tId).unbind().remove();
	$("#mapBtn_" + treeNode.tId).unbind().remove();
	$("#starsoneBtn_" + treeNode.id).unbind().remove();
	$("#starstwoBtn_" + treeNode.id).unbind().remove();
	$("#starsthreeBtn_" + treeNode.id).unbind().remove();
	$("#whitespace" + treeNode.id).unbind().remove();
	$("#diyBtn_space_" +treeNode.id).unbind().remove();

};

/**
 * 功能：拖拽到目标节点时，设置是否允许移动到目标节点前面的操作。[setting.edit.enable = true 时生效]
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function dropPrev(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++)	 {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode()
					&& curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
/**
 * 功能：拖拽到目标节点时，设置是否允许成为目标节点的子节点。[setting.edit.enable = true 时生效]
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function dropInner(treeId, nodes, targetNode) {
	if (targetNode && targetNode.dropInner === false) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			if (!targetNode && curDragNodes[i].dropRoot === false) {
				return false;
			} else if (curDragNodes[i].parentTId
					&& curDragNodes[i].getParentNode() !== targetNode
					&& curDragNodes[i].getParentNode().childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
/**
 * 功能：拖拽到目标节点时，设置是否允许移动到目标节点后面的操作。[setting.edit.enable = true 时生效]
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function dropNext(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i = 0, l = curDragNodes.length; i < l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode()
					&& curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}

/**
 * 功能：用于捕获节点被拖拽之前的事件回调函数，并且根据返回值确定是否允许开启拖拽操作
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
var log, className = "dark", curDragNodes, autoExpandNode;
function beforeDrag(treeId, treeNodes) {
	className = (className === "dark" ? "" : "dark");
	for (var i = 0, l = treeNodes.length; i < l; i++) {
		if (treeNodes[i].drag === false) {
			curDragNodes = null;
			return false;
		} else if (treeNodes[i].parentTId
				&& treeNodes[i].getParentNode().childDrag === false) {
			curDragNodes = null;
			return false;
		}
	}
	curDragNodes = treeNodes;
	return true;
}

/**
 * 功能：用于捕获拖拽节点移动到折叠状态的父节点后，即将自动展开该父节点之前的事件回调函数，并且根据返回值确定是否允许自动展开操作
 * 
 * @param event
 * @param treeId
 * @param treeNode
 * @return
 */
function beforeDragOpen(treeId, treeNode) {
	autoExpandNode = treeNode;
	return true;
}

function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	$.ajax({
		async : true, // 是否异步
		cache : false, // 是否使用缓存
		type : 'post', // 请求方式,post
		data : {
			id : treeNodes[0].id,
			pid : treeNodes[0].pId,
		},
		dataType : "text", // 数据传输格式
		url : "region/dragRegion" // 请求链接
	});
}

// 初始化方法
function onloadZTree() {
	var ztreeNodes;
	$.ajax({
		async : true, // 是否异步
		cache : false, // 是否使用缓存
		type : 'post', // 请求方式,post
		dataType : "text", // 数据传输格式
		url : "region/findOneRegion", // 请求链接
		data : {
			returnId : $("#returnId").val()
		},
		error : function() {
			BootstrapDialog.alert("访问服务器出错");
		},
		success : function(data) {
			ztreeNodes = eval("(" + data + ")"); // 将string类型转换成json对象
			console.log(data)
			zNodes = zNodes.concat(ztreeNodes);
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			rMenu = $("#rMenu");
		}
	});
}

//function confirm(){
//	var flag=true;
//	if(flag===true){
//		$.confirm({
//			title: 'Confirm!',
//			content: 'Simple confirm!',
//			confirm: function(){
//				flag=true;
//				return flag;
//			},
//			cancel: function(){
//				flag=false;
//				return flag;
//			}
//		});
//	}
//}

$(document).ready(function() {
	onloadZTree();
});

/**
 * 跳到区域类型页面
 */
function toRegionType(){

	window.location.href="/region/initRegionType";
}
