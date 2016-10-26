<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>扫街添加</title>
    <script type="text/javascript" src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
    <link rel="stylesheet" href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css"/>
    <script type="text/javascript" src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script language="JavaScript" src="../static/js/section/jquery.json.js"></script>
    <style type="text/css">
        .dropdown-submenu {
            position: relative;
        }

        .dropdown-submenu > .dropdown-menu {
            top: 0;
            left: 100%;
            margin-top: -6px;
            margin-left: -1px;
            -webkit-border-radius: 0 6px 6px 6px;
            -moz-border-radius: 0 6px 6px;
            border-radius: 0 6px 6px 6px;
        }

        .dropdown-submenu:hover > .dropdown-menu {
            display: block;
        }

        .dropdown-submenu > a:after {
            display: block;
            content: " ";
            float: right;
            width: 0;
            height: 0;
            border-color: transparent;
            border-style: solid;
            border-width: 5px 0 5px 5px;
            border-left-color: #ccc;
            margin-top: 5px;
            margin-right: -10px;
        }

        .dropdown-submenu:hover > a:after {
            border-left-color: #fff;
        }

        .dropdown-submenu.pull-left {
            float: none;
        }

        .dropdown-submenu.pull-left > .dropdown-menu {
            left: -100%;
            margin-left: 10px;
            -webkit-border-radius: 6px 0 6px 6px;
            -moz-border-radius: 6px 0 6px 6px;
            border-radius: 6px 0 6px 6px;
        }
    </style>

    <script type="text/javascript">
        function delete1() {
            $.ajax({
                url: 'superposition/delete/1',
                type: 'get',
                data: $.toJSON(superposition),
                success: function (data) {
                    console.log(data)

                },
                error: function () {


                }


            });
        }

        function submit() {
//            模拟叠加类
            var superposition = {
                taskOne: 50,
//                taskTwo: 500,
//                taskThree: 1000,
                production: 'bsufhdj'
            };
//            模拟奖罚规则
            var ruleList = [
                {
                    min: 0,
                    max: 50,
                    percentage: 5
                },
                {

                    min: 50,
                    max: 10000000,
                    percentage: 8
                },
//                {
//
//                    min: 500,
//                    max: 1000,
//                    percentage: 9
//                },
//                {
//
//                    min: 1000,
//                    max: 1000000000,
//                    percentage: 12
//                }
            ];
            superposition['ruleList'] = ruleList;//放入奖罚规则

//            人员分组设置

            var groupList = [
                {
                    name: 'A组',
                    oneAdd: 50 + 10,
//                    twoAdd: 500+50,
//                    threeAdd: 1000+100,
                    members: [{name: 'xiaoming', userId: '4556644'}, {name: 'lidong', userId: '97854345'}]

                },
                {
                    name: 'B组',
                    oneAdd: 50 + 15,
//                    twoAdd: 500+25,
//                    threeAdd: 1000+55,
                    members: [{name: 'dahai', userId: '4556644'}, {name: 'huanghe', userId: '97854345'}]
                },

            ];


//            superposition['ruleList'] = ruleList;//放入奖罚规则
            superposition['groupList'] = groupList;//放入组规则

            $.ajax({
                url: 'superposition/add',
                type: 'POST',
                contentType: 'application/json;charset=utf-8',
                data: $.toJSON(superposition),
                success: function (data) {
                    console.log(data)

                },
                error: function () {


                }


            });


        }
        function ceshi() {
//            var groupList = {
//                "groups": [
//                    {
//                        name: 'A组',
//                        oneAdd: 0,
//                        twoAdd: 0,
//                        threeAdd: 0,
//                        members: [{name: 'xiaoming', userId: '4556644'}, {name: 'lidong', userId: '97854345'}]
//
//                    },
//                    {
//                        name: 'B组',
//                        oneAdd: 0,
//                        twoAdd: 0,
//                        threeAdd: 0,
//                        members: [{name: 'dahai', userId: '4556644'}, {name: 'huanghe', userId: '97854345'}]
//                    }
//
//                ]
//            }


            var groupList =
                    [
                        {
                            name: 'A组',
                            oneAdd: 0,
                            twoAdd: 0,
                            threeAdd: 0,
                            members: [{name: '麦克', userId: '4556644'}, {name: 'joe', userId: '97854345'}]

                        },
                        {
                            name: 'B组',
                            oneAdd: 0,
                            twoAdd: 0,
                            threeAdd: 0,
                            members: [{name: '张学友', userId: '4556644'}, {name: '黄家驹', userId: '97854345'}]
                        },
                        {
                            name: 'C组',
                            oneAdd: 0,
                            twoAdd: 0,
                            threeAdd: 0,
                            members: [{name: '灵动', userId: '4556644'}, {name: '刘德华', userId: '97854345'}]
                        }

                    ]


//            var data = {
//                "student": [
//                    {
//                        "name": "张三",
//                        "sex": "0",
//                        "age": 18
//                    },
//                    {
//                        "name": "李四",
//                        "sex": "0",
//                        "age": 22
//                    },
//                    {
//                        "name": "妞妞",
//                        "sex": "1",
//                        "age": 18
//                    }
//                ]
//            };
            window.name = JSON.stringify(groupList);
            window.location = "/superposition";

        }


        $(function(){
            $.ajax({
                url:'planUsers',
                dataType:'json',
                data:{'sc_EQ_planId':15},
                success:function(data){
                    console.log(data);
                }
            });


        });

    </script>
</head>

<body>
<button onclick="submit()">add</button>
<br><br>

<button onclick="delete1()">delete</button>
<br><br>

<button onclick="ceshi()">ceshi</button>


<div class="container">
    <div class="row">
        <div class="form-group">

            <input type="hidden" name="category_id" id="category_id" value=""/>
            <div class="dropdown">
                <a id="dLabel" role="button" data-toggle="dropdown" class="btn btn-white" data-target="#"
                   href="javascript:;"><span id="select-title">选择分类</span> <span class="caret"></span></a>
                <ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu">
                    <li><a href="javascript:;" data-index="1" data-title="一级菜单">一级菜单</a></li>
                    <li class="dropdown-submenu">
                        <a href="javascript:;" data-index="2" data-title="一级菜单">一级菜单</a>
                        <ul class="dropdown-menu">
                            <li><a data-index="2-1" href="javascript:;" data-title="二级菜单">二级菜单</a></li>
                        </ul>
                    </li>

                    <li class="dropdown-submenu">
                        <a tabindex="3" href="javascript:;" data-title="一级菜单">一级菜单</a>
                        <ul class="dropdown-menu">
                            <li><a tabindex="3-1" href="javascript:;" data-title="二级菜单">二级菜单</a></li>
                            <li class="divider"></li>
                            <li class="dropdown-submenu">
                                <a href="javascript:;" data-index="3-2" data-title="二级菜单">二级菜单</a>
                                <ul class="dropdown-menu">
                                    <li><a href="javascript:;" data-index="3-2-1" data-title="三级菜单">三级菜单</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>

        </div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">


    $('.dropdown li a').click(function () {

        console.log(this);
        title = $(this).attr("data-title");
        id = $(this).attr("data-index");
        $("#select-title").text(title);
        $("#category_id").val(id);
    })

</script>