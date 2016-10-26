<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    System.out.print(basePath);
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="static/js/jquery/jquery-1.11.3.min.js"
        type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/static/income/phone.css">
<link rel="stylesheet" type="text/css"
      href="/static/income/plan_index.css"/>
<script src="../static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet" type="text/css" href="/static/bootStrapPager/css/page.css"/>
<script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"
        charset="utf-8"></script>
<script src="<%=basePath%>static/bootStrapPager/js/extendPagination.js"></script>
<script>
    $(function () {
        var page;
        findPlanUserList(page,'${planId}');
    });


</script>

<script id="user-table-template" type="text/x-handlebars-template">
    {{#if content}}
    {{#each content}}
    <tr>
        <td>{{rindex}}</td>
        <td>{{truename}}</td>
        <td>{{userId}}</td>
        <td>{{rolename}}</td>
        <td><i class="icon-x {{getImg starsLevel 2}}"></i><i
                class="icon-x {{getImg starsLevel 2}}"></i><i class=" icon-x {{getImg starsLevel 2}}"></i>
            {{namepath}}
        </td>
        <td><span class="text-{{getColore levelName}} text-strong">{{levelName}}</span></td>
        <td>{{regdate}}</td>
        <%--{{#if planId}}--%>
        <td><a href="javascript:void(0);"><span class="text-lv text-strong">{{plantitle}}</span></a>
        </td>
        <%--<td><i class="icon icon-un" title="已有所属方案，不可勾选"></i></td>--%>
        <%--{{else}}--%>
        <td><span class="text-gery"></span></td>
        <td><label for="input-2"></label> <input
                type="checkbox" onclick="addUserArr('{{userId}}','{{truename}}')"></td>
        <%--{{/if}}--%>

    </tr>
    {{/each}}
    {{/if}}

</script>


<div class="modal-dialog" role="document" style="width: 1080px;">
    <div class="modal-content modal-blue">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
                    aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <h3 class="modal-title">添加人员</h3>
        </div>
        <div class="modal-body" style="padding-right:15px">


            <div class="content main">
                <div class="clearfix"></div>
                <%--<div class="group-search">--%>
                <%--<select class="ph-select" id="namepath">--%>
                <%--<option value=''>选择区域</option>--%>
                <%--<c:forEach var="region" items="${regions}">--%>
                <%--<option value="${region.id}">${region.name}</option>--%>
                <%--</c:forEach>--%>
                <%--</select> --%>
                <%--<select class="ph-select" id="roleId">--%>
                <%--<option value=''>业务角色</option>--%>
                <%--<option value="262144">服务站经理</option>--%>
                <%--<option value="294914">扩展经理</option>--%>
                <%--</select> --%>
                <%--<select class="ph-select" id="levelName">--%>
                <%--<option value=''>业务等级</option>--%>
                <%--<option value='大学生'>大学生</option>--%>
                <%--<option value='中学生'>中学生</option>--%>
                <%--<option value='小学生'>小学生</option>--%>

                <%--</select> --%>
                <%--<select class="ph-select" id='starsLevel'>--%>
                <%--<option value=''>区域星级</option>--%>
                <%--<option value='1'>一星</option>--%>
                <%--<option value='2'>二星</option>--%>
                <%--<option value='3'>三星</option>--%>
                <%--</select>--%>


                <input type="text" id="trueName" class="col-sm-12 big-seach"
                       placeholder="请搜索业务人员姓名" style="width:20%">
                <button class="btn btn-blue btn-sm" style="margin-left: 10px"
                        onclick="findPlanUserList(0,'${planId}')">检索
                </button><br>
                请选择组:
                <select class="form-control demo3 J_auditor" id="groupName">
                    <option selected="" value="0"></option>
                    <option value="A">A组</option>
                    <option value="B">B组</option>
                    <option value="C">C组</option>
                    <option value="D">D组</option>
                    <option value="E">E组</option>
                    <option value="F">F组</option>
                    <option value="G">G组</option>
                </select>
                <%--<input type="text" id="groupName" class="col-sm-12 big-seach"--%>
                       <%--placeholder="请搜索业务人员姓名" style="width:20%">--%>
                <%--<button class="btn btn-blue btn-sm" style="margin-left: 10px"--%>
                        <%--onclick="findPlanUserList(0)">请输入组--%>
                <%--</button><br>--%>
                <%--<select id="groupName" class="col-sm-12 big-seach" style="width:20%">--%>
                    <%--<option selected="" value=""></option>--%>
                    <%--<option value="A">A组</option>--%>
                    <%--<option value="B">B组</option>--%>
                    <%--<option value="C">C组</option>--%>
                    <%--<option value="D">D组</option>--%>
                    <%--<option value="E">E组</option>--%>
                    <%--<option value="F">F组</option>--%>
                    <%--<option value="G">G组</option>--%>
                <%--</select>--%>



                <%--请输入组:<input type="text" id="groupName" >--%>
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
                        </tbody>
                    </table>

                    <div id="usersPager"></div>
                </div>
                <!--table-box-->
                <button class="btn btn-blue col-sm-3" style="margin-left: 40%"
                <%--onclick="pushAll();">确定--%>
                        onclick="addGroup();">确定
                </button>
            </div>


            <!--待审核账单-->
            <!--油补记录-->
        </div>
    </div>
</div>
</div>
<script src="../static/superposition/userSelect.js" type="text/javascript"
        charset="utf-8"></script>


<script type="text/javascript">
    var salesAddArr = [];
    var members = [];
    //向salesAddArr里添加元素
    function addUserArr(saleId, username) {
        var member = {
            "salesmanId": saleId,
            "salesmanName": username
        };
        var index = members.indexOf(member);
        if (index > -1) {
            members.splice(index, 1);
        } else {
            members.push(member);
        }
    }

/**
 *
 * 添加组人员
 * */
    var groupList = [];
    function addGroup(){
        var page = 0;
        var group={};
        var name = $("#groupName").val();
        if(name == null || name == '' || name == undefined || name == '0'){
            alert('请选择组');
            history.go(0);
            return;
        }
        group.name = name+'组'
        group.oneAdd = 0;
        group.twoAdd = 0;
        group.threeAdd = 0;
        group.members = members;
        groupList.push(group);
        members = [];
        console.log(groupList);
        var $addGroup;
        for(i=0;i<groupList.length;i++){
            console.log(groupList[i].name);
            var addGroup = '';
            addGroup +='<li><dl class="dl-horizontal"><dt><span class="text-green">'+groupList[i].name+'</span></dt> <dd>';
            for(j=0;j<groupList[i].members.length;j++){
                addGroup +=  '<span class="notice">'+ groupList[i].members[j].salesmanName+'</span>';
            }
            addGroup += ' </dd> </dl> </li>';
            $addGroup = $(addGroup);
        }
        $("#listGroup").append($addGroup);
        $('#user').modal('hide');
        findPlanUserList(page,'${planId}');
    }




//    '<li><dl class="dl-horizontal"><dt><span class="text-green">'+groupList[i].name+'</span></dt> <dd>'
//
//
//
//    '<span class="notice">'+ groupList[i].members[j].salesmanName+'</span>'
//
//          ' </dd> </dl> </li>'








//    //全部添加
//    function pushAll() {
//        for (var i = 0; i < salesAddArr.length; i++) {
//            addUser(salesAddArr[i].salesmanId, salesAddArr[i].salesmanname,
//                    false);
//        }
//        $('#user').modal('hide');
//        /*salesAddArr = [];
//         $('input:checkbox:checked').each(function(i) {
//         $(this).prop('checked', false);
//         });*/
//    }
</script>
</html>