var reg = /^(\d{16}|\d{19})$/;
var total =0;
var totalCount = 0;
var limit = 0;
var searchData = {
    "size": "2",
    "page": "0",
    "name": ''
}
list(searchData);
//查询全部
function list(searchData) {
    $.ajax({
        url: 'salesmanData/salesmanDatas',
        data: searchData,
//            async: false,
        type: 'get',
        success: function (data) {
            if (data.status == 'success') {
                var content = data.result.content;
                totalCount = data.result.totalElements;
                limit = data.result.size;
                handelerbars_register(content);//向模版填充数据
                if (totalCount != total || totalCount == 0) {
                    total = totalCount;
                    initPaging();
                }
            } else {
                console.log("系统异常");
                alert('系统异常');
            }
        }

    });
}
//handelerbars填充数据
function handelerbars_register(content) {
    var driver_template = Handlebars.compile($("#tbody-template").html());//注册
    $("#tbody").html(driver_template(content));
}

//分页
function initPaging() {
    $('#callBackPager').extendPagination({
        totalCount: totalCount,//总条数
        showCount: 5,//下面小图标显示的个数
        limit: limit,//每页显示的条数
        callback: function (curr, limit, totalCount) {
            console.log("call back")
            searchData['page'] = curr - 1;
            searchData['size'] = limit;
            list(searchData);
        }
    });
}


//新增业务
function addSalesmanData() {
    //新增业务的内容
    var o = $("#addd").serializeArray();
    var name = o[0]["value"];
    var userId = o[1]["value"];
    var cardNumber = o[2]["value"];
    var bankName = o[3]["value"];
    if (name == null || name == undefined || name == '') {
        alert('请输入名字');
        return ;
    }
    if (userId == null || userId == undefined || userId == '') {
        alert('请输入业务id');
        return ;
    }
    if (cardNumber == null || cardNumber == undefined || cardNumber == '') {
        alert('请输入银行卡号');
        return ;
    }
    if (bankName == null || bankName == undefined || bankName == '') {
        alert('请输入开户行');
        return ;
    }
    if (!reg.test(cardNumber)) {
        alert('请输入正确的银行卡号');
        return;
    }
    $.ajax({
        url: 'salesmanData/salesmanDatas',
        type: 'post',
        data: {
            name: name,
            userId: userId,
            cardNumber: cardNumber,
            bankName: bankName
        },
        success: function (data) {
            if (data.status == 'success') {
                alert("添加成功");
//                    window.location.href = "salesmanData/list1";
                list(searchData);
            } else {
                alert("系统异常");
//                    window.location.href = "salesmanData/list1";
                list(searchData);
            }
        }
    });
}

//弹出修改业务信息消息框
function modify(id,bankId,name,cardNumber,bankName) {
    $("#xgywxx").modal('show').on('shown.bs.modal', function () {
//
        $('#modify_name').val(name);
        $('#modify_bankNumber').val(cardNumber);
        $('#modify_bankName').html(bankName);
        $('#modify_id').val(id);
        $('#modify_bankId').val(bankId);
    })
}
//点击确定修改按钮
function modify_sure() {
    $("#modify_form").serializeArray();
    var d = $("#modify_form").serializeArray();
    var id = d[0]["value"];
    var bankId = d[1]["value"];
    var name = d[2]["value"];
    var cardNumber = d[3]["value"];
    var bankName = d[4]["value"];
    if (!reg.test(cardNumber)) {
        alert('请输入正确的银行卡号');
        return;
    }
    $.ajax({
        url: 'salesmanData/salesmanDatas/' + id + '/' + bankId,
        type: 'put',
        data: {
//                id: id,
//                bankId: bankId,
            name: name,
            cardNumber: cardNumber,
            bankName: bankName
        },
        success: function (data) {
            if(data.status == 'success'){
                alert("修改成功");
//                    window.location.href = "salesmanData/list1";
                list(searchData);
            }else {
                alert("系统异常");
//                    window.location.href = "salesmanData/list1";
                list(searchData);
            }
        }

    });

}

//删除的操作
function deleteSalesmanData(id, bankId, name, cardNumber, bankName) {
    $("#cardNumber1").html(cardNumber);
    $("#name1").html(name);
    $("#bankName1").html(bankName);
    $("#salesmanId").val(id);
    $("#bankId").val(bankId);
    //弹出窗口
    $("#del").modal('show').on('shown.bs.model', function () {

    });
}
//点击确定删除按钮
$("#sure_delete").on('click', function () {
    var id = $("#salesmanId").val();
    var bankId = $("#bankId").val();
    $.ajax({
        url: 'salesmanData/salesmanDatas/' + id + '/' + bankId,
        type: 'DELETE',
        data: {
            id: id,
            bankId: bankId
        },
        success: function (data) {
            if(data.status=="success"){
                list(searchData);
            }else {
                alert("系统异常");
                list(searchData);
            }
        }

    });

});
//弹出添加银行卡框
function add_card(id) {
    $("#addCardId").val(id);
    //弹出
    $("#xzyhk").modal('show').on('shown.bs.model', function () {
    });
}

//点击确定按钮
function sure_add_card() {
    var s = $("#addCard").serializeArray();
    console.log(s);
    var id = s[0]['value'];
    var bankName = s[1]['value'];
    var cardNumber = s[2]['value'];
    if (!reg.test(cardNumber)) {
        alert('请输入正确的银行卡号');
        return;
    }
    $.ajax({
        url:'salesmanData/salesmanDatas/'+id+'/bankCard',
        data: {
            bankName: bankName,
            cardNumber: cardNumber,
        },
        type:'post',
        success:function(data){
            console.log(data);
            if(data.status == "success"){
                alert("添加成功");
//                    window.location.href = 'salesmanData/list1';
                list(searchData);
            }else {
                alert('系统异常');
//                    window.location.href = 'salesmanData/list1';
                list(searchData);
            }
        }
    });
}

//模糊查询
function goSearch(){
    var name = $("#search").val();
    searchData['name'] = name;
    list(searchData);
}