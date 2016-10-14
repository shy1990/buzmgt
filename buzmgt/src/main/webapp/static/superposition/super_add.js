/**
 * 达量设置添加 2016/08/30 ChenGuop
 *
 */
$(function () {
    initSelectBrand();
    initSelectMachineType();
    initFunction();
    creategroupAll();

});
var goodsTypeList = new Array();
var showGoodsTypeList = new Array();
/**
 * 添加商品类型
 */
function addGood() {
    if ($(".J_machineType").val() == '' || $(".J_machineType").val() == undefined || $(".J_machineType").val() == null) {
        alert('请选择类别');
        return;
    }
    if ($(".J_brand").val() == '' || $(".J_brand").val() == undefined || $(".J_brand").val() == null) {
        alert('请选择品牌');
        return;
    }
    if ($(".J_goods").val() == '' || $(".J_goods").val() == undefined || $(".J_goods").val() == null) {
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
    showGoodsTypeList.push({
        "machineType": $(".J_machineType").find("option:selected").text().trim(),
        "brand": $(".J_brand").find("option:selected").text().trim(),
        "good": $(".J_goods").find("option:selected").text().trim()
    });
    console.log(showGoodsTypeList);
    $(".J_machineType").val('');
    createBrandType("");
    createGoods("");
}
/**
 * 展示已经添加的产品
 */
function showGoods() {
    var myTemplate = Handlebars.compile($("#show-goods-template").html());
    $('#showGoods').html(myTemplate(showGoodsTypeList));
    $('#show_goods').modal('show').on('shown.bs.modal', function () {

    });
}


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
            max = 10000;
        }
        rule[i] = {'num': i, 'min': min, 'max': max, 'serialNumber': i + 1};
    }
    console.info(rule);
    createRules(rule);
    $('#zzrwul').modal('hide');
    createNumberAdd();

}

// 动态添加一单达量规则
function addRuleOne() {
//	$('.J_RULE').html("");
//     rule = new Array();
    var html = '';
    var first = $('.O_numberFirst').val();
    var second = $('.O_numberSecond').val();
    var third = $('.O_numberThird').val();
    var number = first + "," + second + "," + third;
    var numArr = number.split(",");
    console.log(numArr)
    //显示数据
    createNumberOne(numArr);
//     //赋值
    var $numberInput = $(".O_number_box input");
    for (var i = 0; i < numArr.length; i++) {
        $numberInput[i].value = numArr[i];
    }
    //1.更改周期量显示
    //2.增加奖罚规则
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
            max = 10000;
        }
        singleRules[i] = {'num': i, 'min': min, 'max': max, 'serialNumber': i};
    }
    console.log(singleRules)
    createRulesOne(singleRules);
    $('#o_zzrwul').modal('hide');

}

function addMoney(num, value) {
    rule[num]["percentage"] = Number(value);
    console.info(rule);
}
function addMoneyOne(num, value) {
    singleRules[num]["reward"] = Number(value);
    console.info(rule);
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
        format: "yyyy-mm-02",
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
    var groupName = "";
    $('#tjry').on('shown.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        groupName = button.data('groupname') // Extract info from data-* attributes
        var modal = $(this);
        modal.find('#groupName').val(groupName);
        //初始化分组
        createGroupSonByNameTable(userList);
        groupList = new Array();
        groupList = groupList.concat(userList);
    })
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



/**
 * 验证一单达量中二阶段数量
 * @returns {Boolean}
 */
function chkScendValueOne() {
    var first = $('.O_numberFirst').val();
    var second = $('.O_numberSecond').val()
    if (first == "") {
        alert("请输入阶段一任务量");
        return false;
    }
    if (second != "" && Number(second) <= Number(first)) {
        alert("请输入正确的阶段二任务量");
        $('.O_numberSecond').val("");
        $('.O_numberSecond').focus();
        return false;
    }
}
/**
 * 验证一单达量中三阶段数量
 * @returns {Boolean}
 */
function chkThirdValueOne() {
    var first = $('.O_numberFirst').val();
    var third = $(".O_numberThird").val();
    var second = $('.O_numberSecond').val();
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
        $('.O_numberThird').val("");
        $('.O_numberThird').focus();
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
        createGoods("");
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
    var machineType = $('.J_machineType').val();
    $.ajax({
        url: base + 'goods/' + machineType + '/' + brandId,
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

function createNumberOne(data) {
    var myTemplate = Handlebars.compile($("#numbers-template").html());
    $('#numberListOne').html(myTemplate(data));
}
function createRulesOne(data) {
    var myTemplate = Handlebars.compile($("#rules-template-one").html());
    $('#ruleListOne').html(myTemplate(data));
}
function addMoneyOne(num, value) {
    singleRules[num]["reward"] = Number(value);
    console.info(rule);
}
/**
 * 创建父窗口中的人员分组
 * @param data
 */
function creategroupAll() {
    var data = disposeGroupNumbers();
    var myTemplate = Handlebars.compile($("#group-all-template").html());
    $('#groupAllList').html(myTemplate(data));
}


function toSubmit() {
//	var jsonStr = $("#achieveForm").serialize();
    var jsonStr = {
        "planId": $(".J_planId").val(),
        // "machineTypeId" : $(".J_machineType").val(),
        // "brandId" : $(".J_brand").val(),
        // "goodId" : $(".J_goods").val(),
        // "numberFirst": $(".J_numberFirst_").val(),
        // "numberSecond": $(".J_numberSecond_").val(),
        // "numberThird": $(".J_numberThird_").val(),
        // "startDate": $(".J_startDate").val(),
        // "endDate": $(".J_endDate").val(),
        // "issuingDate": $(".J_issuingDate").val(),
        // "auditor":  $(".J_auditor").val(),
        // "remark":  $(".J_remark").val()
        "taskOne": $(".J_numberFirst_").val(),
        "taskTwo": $(".J_numberSecond_").val(),
        "taskThree": $(".J_numberThird_").val(),
        "singleOne": $(".O_numberFirst_").val(),
        "singleTwo": $(".O_numberSecond_").val(),
        "singleThree": $(".O_numberThird_").val(),
        "implDate": $(".J_startDate").val(),
        "endDate": $(".J_endDate").val(),
        "giveDate": $(".J_issuingDate").val(),
        "auditor": $(".J_auditor").val(),
        "remark": $(".J_remark").val()
    };

    /*
     *转换
     *
     */
    var groupList = new Array();
    var groupList1 = disposePostGroup();
    for (var i = 0; i < groupList1.length; i++) {
        var oneAdd = parseInt((groupList1[i].numberFirstAdd).trim()) + parseInt($(".J_numberFirst_").val().trim());
        var twoAdd = null;
        var threeAdd = null;
        if(groupList1[i].numberSecondAdd != null){
            twoAdd = parseInt((groupList1[i].numberSecondAdd).trim())+ parseInt($(".J_numberSecond_").val().trim());
        }
        if(groupList1[i].numberThirdAdd != null){
            threeAdd = parseInt((groupList1[i].numberThirdAdd).trim())+ parseInt($(".J_numberThird_").val().trim());
        }
        var group = {
            "name": groupList1[i].groupName+'组',
            "num": i,
            "oneAdd": oneAdd,
            "twoAdd": twoAdd,
            "threeAdd": threeAdd
        };
        var members = new Array();
        var users = groupList1[i].groupUsers;
        for (var j = 0; j < users.length; j++) {
            var member = {"salesmanId": users[j].userId, "salesmanName": users[j].userName};
            members.push(member);
        }
        group.members = members;
        groupList.push(group);
    }
    jsonStr.ruleList = rule;//添加规则
    jsonStr.groupList = groupList;//添加组
    jsonStr.goodsTypeList = goodsTypeList;//添加商品列表
    jsonStr.singleRules = singleRules;//添加一单达量规则

    console.log(jsonStr);
    var superposition = $.toJSON(jsonStr);
    $.ajax({
        url: 'superposition/add',
        type: 'POST',
        contentType: 'application/json;charset=utf-8',
        data: superposition,
        success: function (result) {
            if (result.status == 1) {
                alert('添加成功');
                window.location.href = 'superposition/findAll?planId=' + planId;
            }
        },
        error: function () {
            alert('系统故障,添加失败');
        }
    });
}
//下一步
function nextGroup() {
    $(".J_groupUser_show").hide();
    $("#achieveForm").show();
    createNumberAdd();
}
/**
 * 每组人数
 */
Handlebars.registerHelper('countUser', function (groupUsers) {
    return groupUsers.length;
});
/**
 * 增强 if-else使用
 * 比较长度
 */
Handlebars.registerHelper('compareCount', function (groupUsers, long, options) {
    if (groupUsers.length > long) {
        //满足添加继续执行
        return options.fn(this);
    }
    //不满足条件执行{{else}}部分
    return options.inverse(this);
});

//注册索引+1的helper
var handleHelper = Handlebars.registerHelper("addOne", function (index) {
    // 返回+1之后的结果
    return (index + 1) + " ";
});
function createNumberAdd() {
    var pratGroupNum = new Array();
    pratGroupNum =
    {
        "nums": [$(".J_numberFirst_").val(), $(".J_numberSecond_").val(), $(".J_numberThird_").val()],
        "groupNumbers": groupNumbers
    }
    var myTemplate = Handlebars.compile($("#number-add-template").html());
    $('#numberAddList').html(myTemplate(pratGroupNum));
}
/**
 * 处理父窗口中使用的分组信息
 */
function disposePostGroup() {
    var newGroup = new Array();
    for (var i = 0; i < groupNumbers.length; i++) {
        var groupUsers = groupNumbers[i]["groupUsers"];
        if (groupUsers.length > 0) {
            newGroup.push(groupNumbers[i]);
        }
        continue;
    }
    return newGroup;
}

function numberAdd(opt, num) {
    var x = $(opt).siblings('a.J_group').attr('data-index');
    var y = "";
    switch (num) {
        case 0:
            y = "numberFirstAdd";
            break;
        case 1:
            y = "numberSecondAdd";
            break;
        case 2:
            y = "numberThirdAdd";
            break;

        default:
            break;
    }
    groupNumbers[x][y] = opt.value;
    console.info(groupNumbers[x][y]);// = opt.value;
    console.info(groupNumbers[x]);// = opt.value;
}