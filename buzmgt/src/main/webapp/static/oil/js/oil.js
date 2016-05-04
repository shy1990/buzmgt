//点击"保存"
$("#button_bocun").click(function() {
	
	var ratio = $("#default_km").val();
	var regionId = '0';
	var kmOilSubsidy =$("#default_money").val() ;
	console.log(ratio+"   "+kmOilSubsidy);
	$.ajax({
		url:'/oil/saveDefault',
		type:'post',
		data:{
			kmRatio : ratio,
			regionId : regionId,
			kmOilSubsidy : kmOilSubsidy
		},
		success : function(result) {
			alert(result);
			location.href = '/oil/toOilSetList';
		}
	});

});

// 当弹出框消失的时候执行的方法
$('#zdyqy').on('hidden.bs.modal', function(e) {
	location.href = '/oil/toOilSetList';
})

/* 设置公里系数 */

// 添加
function addd(toil) {
	console.log($("#addd").serializeArray());
	addCustom($("#addd").serializeArray());
}
var i = 1;
function addCustom(o) {
	var qy = o[0]["value"];// regionId
	var glxs = o[1]["value"];//
	var km_ratio = o[2]["value"];
	console.log(qy.length);
	if (qy.length > 7) {
		alert("请您只选择到：要选择区域的最后一级");
		return location.href = '/oil/toOilSetList';
	}
	console.log(qy + '******' + km_ratio);
	oilForm(km_ratio, qy);// 执行添加

}
/*
 * 添加自定义“里程系数区域”
 */
function oilForm(km_ratio, qy) {
	// 发送请求
	$.ajax({
		url : 'oil/toOilSet',// 添加公里系数设置区域
		type : 'post',
		data : {
			ratio : km_ratio,
			regionId : qy.trim()
		},
		success : function(result) {
			alert(result);
			location.href = '/oil/toOilSetList';
		}
	});

}
/*
 * 修改自定义“里程系数区域”
 */
function modify(id, kmOilSubsidy, regionId) {
	$('#changed').modal('show').on('shown.bs.modal', function() {
		$("#set_a").click(function() {
			var ratio = $("#select_modify").val();
			console.log(id + "  " + kmOilSubsidy + "  " + regionId);
			$.ajax({
				url : 'oil/modifyOilParameter',
				type : 'post',
				data : {
					id : id,
					kmRatio : ratio,
					regionId : regionId,
					kmOilSubsidy : kmOilSubsidy
				},
				success : function(data) {
					alert(data);
					location.href = '/oil/toOilSetList';
				}
			});
		});
	});

}
/*
 * 删除自定义“里程系数区域”
 */
function delete_byId(id, regionId) {
	$.ajax({
		url : 'oil/delteOilParameterByRegionId',
		type : 'post',
		data : {
			regionId : regionId
		},
		success : function(data) {
			alert(data);
			location.href = '/oil/toOilSetList';
		}
	});

}

/* 下面是油补金额设置 */

/*
 * 修改弹出框是金额系数
 */
















function changeTo() {
	$("#xiugai")
			.replaceWith(
					' <div class="form-group"> '
							+ '   <label for="" class="col-sm-4 control-label">每公里油补金额 ：</label>'
							+ '  <div class="col-sm-7">'
							+ '      <div class="input-group are-line">'
							+ '         <span class="input-group-addon"><i class="icon icon-task-lk"></i></span>'
							+ '          <select name="klmt" type="" class="form-control input-h" aria-describedby="basic-addon1" id="modify_money">'
							+ '              <option value="0.0">0.0</option>'
							+ '              <option value="0.1">0.1</option>'
							+ '              <option value="0.15">0.15</option>'
							+ '              <option value="0.2">0.2</option>'
							+ '              <option value="0.25">0.25</option>'
							+ '              <option selected="" value="0.3">0.3</option>'
							+ '              <option value="0.35">0.35</option>'
							+ '              <option value="0.4">0.4</option>'
							+ '              <option value="0.45">0.45</option>'
							+ '              <option value="0.5">0.5</option>'
							+ '              <option value="0.55">0.55</option>'
							+ '              <option value="0.6">0.6</option>'
							+ '              <option value="0.65">0.65</option>'
							+ ' 			 <option value="0.7">0.7</option>'
							+ ' 			 <option value="0.75">0.75</option>'
							+ ' 			 <option value="0.8">0.8</option>'
							+ ' 			 <option value="0.9">0.9</option>'
							+ ' 			 <option value="1.0">1.0</option>'
							+ '         </select>'
							+ '      </div>'
							+ '   </div>'
							+ '   <div class="col-sm-1 control-label"><span>元/km</span></div>'
							+ '  </div>'
							+

							'  <div class="form-group">'
							+ '  <div class="col-sm-offset-4 col-sm-4 ">'
							+ '      <a id="a" herf="javascript:return 0;"  class="Zdy_add  col-sm-12 btn btn-primary" >确定  </a>'
							+ '  </div>' + ' </div>'

			);
	// 执行添加
	$("#a").on("click", function() {
		console.log("999999999999999");
		addoil();
	});
}

/*
 * 添加的方法
 */
function addoil() {

	console.log($("#addd").serializeArray());
	oilCustom($("#addd").serializeArray());
}
var j = 1;
function oilCustom(o) {
	var oqy = o[0]["value"];// regionId
	var okm = o[1]["value"];
	var kmOilSubsidy = o[2]["value"];// 油补金额
	if (oqy.length > 7) {
		alert("请您只选择到：要选择区域的最后一级");
		return location.href = '/oil/toOilSetList';
	}
	$.ajax({
		url : 'oil/toOilMoney',// 添加公里系数设置区域
		type : 'post',
		data : {
			kmOilSubsidy : kmOilSubsidy,
			regionId : oqy.trim()
		},
		success : function(result) {
			alert(result);
			location.href = '/oil/toOilSetList';
		}
	});

};
/*
 * 删除的方法
 */
function delete_byId_money(id, regionId) {
	$.ajax({
		url : 'oil/delteOilParameterByRegionId1',
		type : 'post',
		data : {
			regionId : regionId
		},
		success : function(data) {
			alert(data);
			location.href = '/oil/toOilSetList';
		}
	});
}

/*
 * 修改的方法
 */
function modify_money(id, kmRatio, regionId) {
	$('#changed1').modal('show').on('shown.bs.modal', function() {
		$("#set_b").on("click", function() {
			var kmOilSubsidy = $("#select_kmOilSubsidy").val();
			console.log(id + "  " + kmOilSubsidy + "  " + regionId);
			$.ajax({
				url : 'oil/modifyOilParameter',
				type : 'post',
				data : {
					id : id,
					kmRatio : kmRatio,
					regionId : regionId,
					kmOilSubsidy : kmOilSubsidy
				},
				success : function(data) {
					alert(data);
					location.href = '/oil/toOilSetList';
				}
			});

		});
	});
}