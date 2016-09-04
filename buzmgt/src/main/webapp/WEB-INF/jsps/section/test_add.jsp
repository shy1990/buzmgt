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
    <script src="../static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <script language="JavaScript" src="../static/js/section/jquery.json.js"></script>
    <script src="../static/js/handlebars-v4.0.2.js"
            type="text/javascript" charset="utf-8"></script>
    <script>
        function submitList() {
            var priceRangeArray = new Array();
            var form = $("#ceshi .a ");
            for (var i = 0; i < form.size(); i++) {
                var f = $(form[i]).serializeArray();
                var a = f[0]["value"].trim();
                var b = f[1]["value"].trim();
                var c = f[2]["value"].trim();
                priceRangeArray.push({
                    priceRange: a + "-" + b,
                    percentage: c,
                    priceRangeStatus: "0",
                    serialNumber: i + 1,
                });
            }
            console.log(priceRangeArray);
            $.ajax({
                url: "addPriceRanges?implementationDate=2016-08-01&productionType=znj&status=0",
                type: "POST",
                contentType: 'application/json;charset=utf-8',//设置请求头信息
                dataType: "json",
                data: $.toJSON(priceRangeArray),//将Json对象序列化成Json字符串，toJSON()需要引用jquery.json.min.js
                success: function (data) {

                    console.log(data);
//                    var content = data;
//                    handelerbars_register(content);

                },
                error: function () {
                    alert("程序有误,请打死程序猿");
                }
            });
        }

        //handelerbars填充数据
        function handelerbars_register(content) {
            var driver_template = Handlebars.compile($("#tbody-template").html());//注册
            $("#liebiao").html(driver_template(content));
        }

    </script>
</head>

<body>
<h1>addPriceRanges</h1>
<div id="ceshi">
    <form class="a">
        <input type="text" name="k"
               onblur="if (!(/^[\d]+\.?\d*$/.test(this.value)) ){alert('请您输入数字'); this.value='';this.focus();}">-<input
            type="text" name="k"
            onblur="if (!(/^[\d]+\.?\d*$/.test(this.value)) ){alert('请您输入数字'); this.value='';this.focus();}"> <input
            type="text" name="v"
            onblur="if (!(/^[\d]+\.?\d*$/.test(this.value)) ){alert('请您输入数字'); this.value='';this.focus();}"><br>

    </form>
    <form class="a">
        <input type="text" name="k"
               onblur="if (!(/^[\d]+\.?\d*$/.test(this.value)) ){alert('请您输入数字'); this.value='';this.focus();}">-<input
            type="text" name="k"
            onblur="if (!(/^[\d]+\.?\d*$/.test(this.value)) ){alert('请您输入数字'); this.value='';this.focus();}"> <input
            type="text" name="v"
            onblur="if (!(/^[\d]+\.?\d*$/.test(this.value)) ){alert('请您输入数字'); this.value='';this.focus();}"><br>

    </form>
</div>


<%--<input id="submit" type="button" value="Submit" onclick="submitList();">--%>


<input id="submit" type="button" value="Submit" onclick="submitList();">

<div id="liebiao">


</div>

<script id="tbody-template" type="text/x-handlebars-template">
    {{#this}}
    {{#each this.priceRanges}}
    <p>序列:{{serialNumber}} 价格区间:{{priceRange}} 实施日期:{{implementationDate}}</p>
    {{/each}}
    {{/this}}

</script>
</body>
</html>
