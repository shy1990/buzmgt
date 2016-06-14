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
    <title>工资</title>
    <script type="text/javascript" src="<%=basePath%>static/js/handlebars-v4.0.2.js"></script>
    <script type="text/javascript" src="<%=basePath%>static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript">
        var salary = {
            _count:{
                startTime:'2016-05-05',
                endTime:'2016-05-06'
            },
            url:{
                all:function(){
                    return '/salary/salarys';
                },
                one:function(id){
                    return '/salary/salarys/'+id;
                }
            },
            findAll:function(startTime,endTime){
                $.ajax({
                    url:salary.url.all(),
                    type:'get',
                    data:{
                        startTime:startTime,
                        endTime:endTime
                    },
                    success:function(data){
                        console.log(data);
                    },
                    error:function(){
                        alrt('系统错误');
                    }

                });
            },
            detail:{
                init:function(args){
                    /**
                     *  console.log(args["time"]);
                        console.log(args.time); 效果一样
                     * @type {salary._count|{startTime, endTime}|*}
                     */
//                    console.log(args["time"]);
//                    console.log(args.time);
                    var time = args.time;
                    var startTime = time["startTime"];
                    var endTime = time["endTime"];
//                    console.log(startTime+":   "+endTime);
                    salary.findAll(startTime,endTime);
                }
            }
        };
        $(function(){
            var time= salary._count;
            salary.detail.init({
                time
            });
        });
    </script>
</head>
<body>
<form method="POST" enctype="multipart/form-data"
      action="/salary/upload">
    File to upload: <input type="file" name="file"><br /> Name: <input
        type="text" name="name"><br /> <br /> <input type="submit"
                                                     value="Upload"> Press here to upload the file!
</form>
</body>
</html>



