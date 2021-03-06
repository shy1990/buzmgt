<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"  %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>

<html>
<head>
    <title>菜单授权</title>
    <link rel='stylesheet' type='text/css' href='../static/plugins/css/common_green.css'>
     <link rel='stylesheet' type='text/css' href='../static/plugins/css/page_nav.css'>
    
    <script type="text/javascript" src="../static/js/jquery/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="../static/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="../static/plugins/common.js"></script>
	<script type="text/javascript" src="../static/plugins/jstree/jquery.jstree.js"></script>
    <script type="text/javascript">
    
    	$(function () {
    		//创建一个树的实例
    		$("#menuTree").jstree({  
                "json_data" : {
                    "ajax" : {   
                    	"type": "POST",
                        "url" : "<s:url value='/role/getMenuTree'/>"  
                    }
                },  
             "themes" : {  
                 "icons" : false 
             },             
             "plugins" : [ "themes", "json_data", "checkbox" ]
            });
    		
    		
    		//绑定事件
    		$("#menuTree").bind("loaded.jstree", function (event, data) {
    			//展开所有节点
    			data.inst.open_all(-1);    			
    			//
    			initTree();
    		});


    		
    	});
    	
        function initTree()
        {
         //初始化复选框
         var menuIds = "${menuIds}";
     
         var initMenus = menuIds.split(",");
         $("#menuTree").find("li").each(function()
         {
         	
         	 	var tempid=$(this).children("a").attr("id");
         	 	if(tempid != "")
         	 	{
         	 		for(var i = 0; i<initMenus.length; i++)
         	 		{
 	        	 		
 	        	 		if(tempid == initMenus[i])
 	        	 		{
 	        	 		
 	        	 			$(this).removeClass("jstree-unchecked").addClass("jstree-checked");
 	        	 			$(this).removeClass("jstree-closed").addClass("jstree-open");
 	        	 		}
         	 		}	
         	 	}
         	 	
         	 });
         
         	 $("#menuTree > ul > li").each(function()
         	 {
 	        	var tempid=$(this).children("a").attr("id");
         	 	if(tempid != "")
         	 	{
         	 		for(var i = 0; i<initMenus.length; i++)
         	 		{
 	        	 		if(tempid == initMenus[i])
 	        	 		{
 	        	 			var flag = false;
 	        	 			
 	        	 			$(this).find("li").each(function(){
 	        	 				if($(this).hasClass("jstree-unchecked"))
 	        	 				{
 	        	 					flag = true;
 	        	 				} 
 	        	 			});
 	        	 			
 	        	 			if(flag)
 	        	 			{
 	        	 				$(this).removeClass("jstree-checked").addClass("jstree-undetermined");
 	        	 			}
 	        	 		}
         	 		}	
         	 	}
         	 });
         	 
 		}
    		
 		function getSelect()
 		{
 			//当一个目录下的所有child均被选中时，则get_checked仅可得到parent Item的信息，
 			//否则可以取得每个被选中的child Item的信息
 			//取得结点的名称字符串使用：get_text 方法 取得其他属性使用 .arrr 方法
 			//var nodes = $("#menuTree").jstree("checkbox").get_checked();
 			//$.each(nodes, function(i, n) { 
 				//alert($("#menuTree").jstree("get_text",$(n))+"/"+$(n).attr("id")); 
 			//}); 
 			
 			var menuIds="";	
 			 $("#menuTree").jstree("get_checked").each(function(i, n) {
 			 		
 			 		menuIds += $(this).children("a").attr("id")+",";
 			 		
 			 });
 			 
 			 if(menuIds.indexOf(",")>0){
   				menuIds=menuIds.substring(0,menuIds.length-1);
   			 }
 			 
 			 $("input[name='menuIds']").val(menuIds);
 			 
 			 return true;
 		}
 		
 		function selectAllNode(obj){
 			if($(obj).attr("checked")){
 				$("#menuTree").jstree("checkbox").check_all();
 			}else{
 				$("#menuTree").jstree("checkbox").uncheck_all();
 			}
 		}
    
		$(function(){
			pilicat.title2div('title2div');
			pilicat.keysubmit('form1','submit',true);
		});
	</script>
	
	<script type="text/javascript" >
	    $(document).ready(function() { 
	        $('#form1').ajaxForm( { dataType: 'json' , beforeSubmit : checkForm , success : afterPost } ); 
	    }); 
	    
	    function checkForm(formData, jqForm, options) { 

			
	    }
	    
	    function afterPost(responseText, statusText, xhr, $form)  {
	    	
	    	if(responseText.resultStatus==true){
	    		alert("保存成功");
	    		var url = "roleList";
	    		window.location = encodeURI(url);
	    	}else{
	    		alert("保存失败,请至少选定一个节点");
	    	}
	    	
	    }
	    
	    function submitForm(){
	    	getSelect();
	    }
	    
	</script>

	<style type="text/css">
		.update{margin:0 auto; padding:0; width:98%;}
		.update td{margin:0; height:30px; padding:5px;}
		.update .name{text-align:right;}
		.title_div{width:350px;}
		
		body {
		 background-image: url(../static/img/gray_bg.png);
 		 background-color:  0 0 repeat-y #F6F6F6;
		}
	</style>
    
</head>
<body>

	<div class="rounded table">
		<table width="100%" cellpadding="0" cellspacing="0" border="0" class="box_top">
			<tbody><tr>
				<td class="title"><a href="/role/roleList">返回</a></td>
				<td></td>
			</tr>
		</tbody></table>
		
		<div class="error rounded top_error hide">
		<ol></ol><span></span>
		</div>
		
		<form id="form1" action="<s:url value='/role/saveRoleResource'/>" method="post" onsubmit="">
		<table class="update" cellpadding="0" cellspacing="1" border="0">
			<tbody>
			<tr>
				<td>
				角色名称：
				<span style="color:blue;">${roleEntity.name }</span>
				<input type="hidden"  name="roleId" value="${roleEntity.id}" />
				<input type="hidden"  name="menuIds" value="${menuIds}" />
				</td>
				
			</tr>		
			
			<tr class="even">
				<td>
					赋予的资源：
					
				</td>
				
			</tr>
			<tr class="even">				
				<td>										
					<div id="menuTree"></div>
					
				</td>
				
			</tr>
		</tbody>
		</table>
		
		<table class="table top_line">
			<tbody>
			<tr>
				<td align="center" height="60">
					<a id="submit" class="submit" href="javascript:;" onclick="submitForm();">保存</a>
				</td>
				
			</tr>
			</tbody>
		</table>

		</form>
	</div>
	<script src='/static/js/common.js'></script>
	
</body>
</html>
