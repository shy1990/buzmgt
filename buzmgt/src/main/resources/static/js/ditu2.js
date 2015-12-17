// 基于准备好的dom，初始化echarts图表
var ditu2 = echarts.init(document.getElementById('ditu2'));
var option = {
	tooltip : {
		formatter : "{a} <br/>{b} : {c}%"
	},
	toolbox : {
		show : true,
		feature : {
			mark : {
				show : true
			},
			restore : {
				show : true
			},
			saveAsImage : {
				show : true
			}
		}
	},
	series : [ {
		name : "业务指标",
		type : "gauge",
		detail : {
			formatter : "{value}%"
		},
		data : [ {
			value : 50,
			name : "完成率"
		} ]
	} ]
};

// 为echarts对象加载数据
ditu2.setOption(option);