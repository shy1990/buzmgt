/**
 * 叠加类设置
 *
 */
//获取组成员信息
var groupList = JSON.parse(window.name);
//页面初始化
$(function () {
    initSelectBrand();
    initSelectMachineType();
    initFunction();
});
function addNumber() {
    var name = $(this).prev("input").attr("name");
    var html = "";
    var html_ = "";
    switch (name) {
        case "numberFirst":
            html += '<input type="text" name="numberSecond" value="" />台';
            break;

        case "numberSecond":
            html += '<input type="text" name="numberThird" value="" />台';
            break;
        default:

            break;
    }
    $(this).before(html);
}
// 动态添加规则
function addRule() {
//	$('.J_RULE').html("");
    rule = new Array();
    var html = '';
    var first = $('.J_numberFirst').val();
    var second = $('.J_numberSecond').val();
    var third = $('.J_numberThird').val();
    var number = first + "," + second + "," + third;
    var numArr = number.split(",");
    //显示数据
    createNumber(numArr);
    //赋值
    var $numberInput = $(".J_number_box input");
    for (var i = 0; i < numArr.length; i++) {
        $numberInput[i].value = numArr[i];
    }
    //1.更改周期量显示
    //2.增加奖罚规则
//	var rule =new Array();
    var leng = 0; //rule = n(*number*)+1;
    if (third != "") {
        leng = 4;
    } else if (second != "") {
        leng = 3;
    } else {
        leng = 2;
    }
    var ave = 0;
    for (var i = 0; i < leng; i++) {
        var min = "";
        var max = "";
        min = ave;
        max = numArr[i] == undefined ? '' : numArr[i];
        ave = max;
        if (i == (leng - 1)) {
            max = 10000000;
        }
        rule[i] = {'num': i, 'min': min, 'max': max, 'serialNumber': i};
    }
    createRules(rule);
    createGroup(leng, first, second, third);
    $('#zzrwul').modal('hide');

}


//动态生成人员分组
function createGroup(leng, first, second, third) {

//这里判断是不是null
    if (third != null) {
        for (i = 0; i < groupList.length; i++) {
            console.log(groupList[i]);
            groupList[i].oneAdd = first;
            groupList[i].twoAdd = second;
            groupList[i].threeAdd = third;
            groupList[i].num = i;

        }

    } else if (second != null) {
        for (i = 0; i < groupList.length; i++) {
            console.log(groupList[i]);
            groupList[i].oneAdd = first;
            groupList[i].twoAdd = second;

        }
    } else {
        for (i = 0; i < groupList.length; i++) {
            console.log(groupList[i]);
            groupList[i].oneAdd = first;

        }
    }

    console.log(groupList);
    var myTemplate = Handlebars.compile($("#ceshi-template").html());
    $('#groupJoe').html(myTemplate(groupList));

}


function addMoney(num, value) {
    rule[num]["percentage"] = Number(value);
    console.info(rule);
}


function addCount1(num, value) {
    groupList[num]["oneAdd"] = Number(groupList[num]["oneAdd"]) + Number(value);
}
function addCount2(num, value) {
    groupList[num]["twoAdd"] = Number(groupList[num]["twoAdd"]) + Number(value);
}
function addCount3(num, value) {
    groupList[num]["threeAdd"] = Number(groupList[num]["threeAdd"]) + Number(value);
}


function initFunction() {
    $('.J_startDate').datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-right",
        forceParse: 0
    }).on('changeDate', function (ev) {
        $('.form_date_start').removeClass('has-error');
        $('.form_date_end').removeClass('has-error');
        var endInputDateStr = $('.J_endDate').val();
        if (endInputDateStr != "" && endInputDateStr != null) {
            var endInputDate = stringToDate(endInputDateStr).valueOf();
            if (ev.date.valueOf() - endInputDate >= 0) {
                $('.form_date_start').addClass('has-error');
                alert("请输入正确的日期");
            }
        }
    });
    $('.J_endDate').datetimepicker({
        format: "yyyy-mm-dd",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        minView: 2,
        pickerPosition: "bottom-right",
        forceParse: 0
    }).on('changeDate', function (ev) {
        $('.form_date_start').removeClass('has-error');
        $('.form_date_end').removeClass('has-error');
        var startInputDateStr = $('.J_startDate').val();
        if (startInputDateStr != "" && startInputDateStr != null) {
            var startInputDate = stringToDate(startInputDateStr).valueOf();
            if (ev.date.valueOf() - startInputDate < 0) {
                $('.form_date_end').addClass('has-error');
                alert("请输入正确的日期");
            }
        }
    });
    $('.J_issuingDate').datetimepicker({
        format: "yyyy-mm-10",
        language: 'zh-CN',
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 3,
        minView: 3,
        pickerPosition: "bottom-right",
        forceParse: 0
    });
}
/**
 * 验证二阶段数量
 * @returns {Boolean}
 */
function chkScendValue() {
    var first = $('.J_numberFirst').val();
    var second = $('.J_numberSecond').val()
    if (first == "") {
        alert("请输入阶段一任务量");
        return false;
    }
    if (second != "" && Number(second) <= Number(first)) {
        alert("请输入正确的阶段二任务量");
        $('.J_numberSecond').val("");
        $('.J_numberSecond').focus();
        return false;
    }
}
/**
 * 验证三阶段数量
 * @returns {Boolean}
 */
function chkThirdValue() {
    var first = $('.J_numberFirst').val();
    var third = $(".J_numberThird").val();
    var second = $('.J_numberSecond').val();
    if (first == "") {
        alert("请输入阶段一任务量");
        return false;
    }
    if (second == "") {
        alert("请输入阶段二任务量");
        return false;
    }

    if (third != "" && Number(third) <= Number(second)) {
        alert("请输入正确的阶段三任务量");
        $('.J_numberThird').val("");
        $('.J_numberThird').focus();
        return false;
    }
}
//初始化选择类别（机型）
function initSelectMachineType() {
    $(".J_machineType").change(function () {
        var machineType = $(this).val();
        if (machineType == "") {
            createBrandType("");
            createGoods("");
            return false;
        }
        findBrandType(machineType);
    });
}
//查询品牌
function findBrandType(machineType) {
    $.ajax({
        url: "/goods/getBrand?code=" + machineType,
        type: 'GET',
        dateType: 'JSON',
        success: function (data) {
            createBrandType(data);
        },
        error: function (data) {
            alert("网络异常，稍后重试！");
        }

    })
}
/**
 * 创建品牌
 * @param data
 */
function createBrandType(data) {
    var myTemplate = Handlebars.compile($("#brands-template").html());
    $('#brandList').html(myTemplate(data));
}
/**
 * 初始化选择品牌
 *
 */
function initSelectBrand() {
    $(".J_brand").change(function () {
        var brandId = $(".J_brand").val();
        if (brandId == "") {
            createGoods("");
            return false;
        }
        findGoods(brandId);
    });
}
/**
 * 查询商品
 * @param brandId
 */
function findGoods(brandId) {
    $.ajax({
        url: base + 'goods/' + brandId,
        type: 'GET',
        dateType: 'JSON',
        success: function (data) {
            createGoods(data);
        },
        error: function (data) {
            alert("网络异常，稍后重试！");
        }
    });
}
/**
 * createGoods
 * @param data
 */
function createGoods(data) {
    var myTemplate = Handlebars.compile($("#goods-template").html());
    $('#goodList').html(myTemplate(data));
}
/**
 * createRules()
 * @param data
 */
function createRules(data) {
    var myTemplate = Handlebars.compile($("#rules-template").html());
    $('#ruleList').html(myTemplate(data));
}
function createNumber(data) {
    var myTemplate = Handlebars.compile($("#numbers-template").html());
    $('#numberList').html(myTemplate(data));
}


/**
 * 添加商品类型
 * @type {Array}
 */
var goodsTypeList = new Array();
var a = 0;
function addGood() {
    if($(".J_machineType").val() == '' || $(".J_machineType").val() == undefined || $(".J_machineType").val() == null){
        alert('请选择类别');
        return;
    }
    if($(".J_brand").val() == '' || $(".J_brand").val() == undefined || $(".J_brand").val() == null){
        alert('请选择品牌');
        return;
    }
    if($(".J_goods").val() == '' || $(".J_goods").val() == undefined || $(".J_goods").val() == null){
        alert('请选择型号');
        return;
    }
    goodsTypeList.push({
        "machineTypeId": $(".J_machineType").val(),
        "brandId": $(".J_brand").val(),
        "goodId": $(".J_goods").val()
    });
    alert('添加成功');
    Refresh();
    console.log(goodsTypeList);

}

function Refresh() {
        $(".J_machineType").val('');
        createBrandType("");
        createGoods("");
}




function toSubmit() {
//	var jsonStr = $("#achieveForm").serialize();
    var jsonStr = {
        // "planId": $(".J_planId").val(),
        // "machineTypeId": $(".J_machineType").val(),
        // "brandId": $(".J_brand").val(),
        // "goodId": $(".J_goods").val(),
        "taskOne": $(".J_numberFirst_").val(),
        "taskTwo": $(".J_numberSecond_").val(),
        "taskThree": $(".J_numberThird_").val(),
        "implDate": $(".J_startDate").val(),
        "endDate": $(".J_endDate").val(),
        "giveDate": $(".J_issuingDate").val(),
        "auditor": $(".J_auditor").val(),
        "remark": $(".J_remark").val()
    };



    jsonStr.ruleList = rule;//添加规则
    jsonStr.goodsTypeList = goodsTypeList;//添加商品类型
    jsonStr.groupList = groupList;//添加组
    var superposition = $.toJSON(jsonStr);
    console.info(jsonStr);
    // $.ajax({
    //     url: 'superposition/add',
    //     type: 'POST',
    //     contentType: 'application/json;charset=utf-8',
    //     data: superposition,
    //     success: function (result) {
    //         if(result.status == 1){
    //             alert('添加成功');
    //             window.location.href = '';
    //         }
    //     },
    //     error: function () {
    //         alert('系统故障,添加失败');
    //     }
    //
    // });

}
