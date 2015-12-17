// 基于准备好的dom，初始化echarts图表
var myChart = echarts.init(document.getElementById('ditu'));
var option = {
	title : {
		text : "手机销量",
		subtext : "纯属虚构",
		x : "center"
	},
	tooltip : {
		trigger : "item"
	},
	legend : {
		orient : "vertical",
		x : "left",
		data : [ "iphone3", "iphone4", "iphone5" ]
	},
	dataRange : {
		min : 0,
		max : 2500,
		x : "left",
		y : "bottom",
		text : [ "高", "低" ],
		calculable : true,
		color : [ "#006edd", "#e0ffff" ]
	},
	toolbox : {
		show : true,
		orient : "vertical",
		x : "right",
		y : "bottom",
		feature : {
			mark : {
				show : true
			},
			dataView : {
				show : true,
				readOnly : false
			},
			restore : {
				show : true
			},
			saveAsImage : {
				show : true
			}
		}
	},
	roamController : {
		show : true,
		x : "right",
		mapTypeControl : {
			china : true
		}
	},
	series : [ {
		name : "iphone3",
		type : "map",
		mapType : "china",
		roam : false,
		mapValueCalculation : "sum",
		itemStyle : {
			normal : {
				label : {
					show : true
				}
			},
			emphasis : {
				label : {
					show : true
				}
			}
		},
		data : [ {
			name : "北京",
			value : 234
		}, {
			name : "天津",
			value : 532
		}, {
			name : "上海",
			value : 134
		}, {
			name : "重庆",
			value : 983
		}, {
			name : "河北",
			value : 783
		}, {
			name : "河南",
			value : 345
		}, {
			name : "云南",
			value : 872
		}, {
			name : "辽宁",
			value : 94
		}, {
			name : "黑龙江",
			value : 342
		}, {
			name : "湖南",
			value : 989
		}, {
			name : "安徽",
			value : 767
		}, {
			name : "山东",
			value : 675
		}, {
			name : "新疆",
			value : 874
		}, {
			name : "江苏",
			value : 874
		}, {
			name : "浙江",
			value : 878
		}, {
			name : "江西",
			value : 928
		}, {
			name : "湖北",
			value : 44
		}, {
			name : "广西",
			value : 448
		}, {
			name : "甘肃",
			value : 887
		}, {
			name : "山西",
			value : 903
		}, {
			name : "内蒙古",
			value : 673
		}, {
			name : "陕西",
			value : 563
		}, {
			name : "吉林",
			value : 747
		}, {
			name : "福建",
			value : 112
		}, {
			name : "贵州",
			value : 473
		}, {
			name : "广东",
			value : 647
		}, {
			name : "青海",
			value : 838
		}, {
			name : "西藏",
			value : 626
		}, {
			name : "四川",
			value : 515
		}, {
			name : "宁夏",
			value : 172
		}, {
			name : "海南",
			value : 77
		}, {
			name : "台湾",
			value : 837
		}, {
			name : "香港",
			value : 677
		}, {
			name : "澳门",
			value : 43
		} ]
	}, {
		name : "iphone4",
		type : "map",
		mapType : "china",
		roam : false,
		mapValueCalculation : "sum",
		itemStyle : {
			normal : {
				label : {
					show : true
				}
			},
			emphasis : {
				label : {
					show : true
				}
			}
		},
		data : [ {
			name : "北京",
			value : 623
		}, {
			name : "天津",
			value : 322
		}, {
			name : "上海",
			value : 57
		}, {
			name : "重庆",
			value : 566
		}, {
			name : "河北",
			value : 737
		}, {
			name : "安徽",
			value : 846
		}, {
			name : "新疆",
			value : 748
		}, {
			name : "浙江",
			value : 949
		}, {
			name : "江西",
			value : 747
		}, {
			name : "山西",
			value : 848
		}, {
			name : "内蒙古",
			value : 737
		}, {
			name : "吉林",
			value : 848
		}, {
			name : "福建",
			value : 727
		}, {
			name : "广东",
			value : 626
		}, {
			name : "西藏",
			value : 747
		}, {
			name : "四川",
			value : 844
		}, {
			name : "宁夏",
			value : 737
		}, {
			name : "香港",
			value : 325
		}, {
			name : "澳门",
			value : 509
		} ]
	}, {
		name : "iphone5",
		type : "map",
		mapType : "china",
		roam : false,
		mapValueCalculation : "sum",
		itemStyle : {
			normal : {
				label : {
					show : true
				}
			},
			emphasis : {
				label : {
					show : true
				}
			}
		},
		data : [ {
			name : "北京",
			value : 673
		}, {
			name : "天津",
			value : 636
		}, {
			name : "上海",
			value : 633
		}, {
			name : "广东",
			value : 99
		}, {
			name : "台湾",
			value : 998
		}, {
			name : "香港",
			value : 222
		}, {
			name : "澳门",
			value : 666
		} ]
	} ]
};

// 为echarts对象加载数据
myChart.setOption(option);