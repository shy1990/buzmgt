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
    <script src="../static/js/jquery/jquery-1.11.3.min.js"
            type="text/javascript" charset="utf-8"></script>
    <script language="JavaScript" src="../static/js/section/jquery.json.js"></script>
    <script src="../static/js/handlebars-v4.0.2.js"
            type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript">
        $(function () {
            $("#list").click(function () {
                $.ajax({
                    url: 'findNow?type=znj',
                    type: 'POST',
                    success: function (data) {
                        console.log(data);
                        handelerbars_register(data);
                    }
                });

            });
        });

        //handelerbars填充数据
        function handelerbars_register(content) {
            var driver_template = Handlebars.compile($("#tbody-template").html());//注册
            $("#liebiao").html(driver_template(content));
        }

        function modify(id,priceRange,percentage,serialNumber) {
            console.log(id + '  '+priceRange+'   '+percentage);

            $.ajax({
                url:'modifyPriceRange',
                type:'POST',
                contentType:'application/json;charset=utf-8',
                dataType:'json',
                data:$.toJSON({'priceRangeId':id,'priceRange':priceRange,'percentage':percentage,'serialNumber':serialNumber}),
                success:function (data) {
                    console.log(data);
                },
                error:function (data) {
                    console.log(data)
                }

            })

        }


    </script>

</head>

<body>
<button id="list">正在使用</button>
<div id="liebiao">
</div>

<script id="tbody-template" type="text/x-handlebars-template">
    {{#each this.priceRanges}}
    <div>
        <input value="{{priceRangeId}}" class="pId">
        序列:{{serialNumber}} &nbsp;&nbsp;&nbsp;
        价格区间:<input value="{{priceRange}}"/>&nbsp;&nbsp;&nbsp;
        提成额度:<input value="{{percentage}}"/>&nbsp;&nbsp;&nbsp;
        实施日期:<input value="{{implementationDate}}"/>&nbsp;&nbsp;&nbsp;
        <button onclick="modify('{{priceRangeId}}','{{priceRange}}','{{percentage}}','{{serialNumber}}')">修改</button>
    </div>
    {{/each}}
</script>
</body>
</html>
