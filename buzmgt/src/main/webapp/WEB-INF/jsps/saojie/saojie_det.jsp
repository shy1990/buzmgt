<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>扫街明细</title>
		<!-- Bootstrap -->
		<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="static/css/common.css" />
		<link rel="stylesheet" type="text/css" href="static/saojie/saojie-det.css" />
		<link rel="stylesheet" type="text/css" href="static/yw-team-member/ywmember.css" />
		<script src="static/js/jquery/jquery-1.11.3.min.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=sxIvKHAtqdjggD4rK07WnHUT"></script>
	</head>

	<body>
		<div class="content main">
			<h4 class="team-member-header page-header ">
				<i class="icon icon-ywdet"></i>扫街明细
				<a href="/saojie/saojieList" class="btn btn-blue member-add-btn"
				type="button"> <i class="icon glyphicon glyphicon-share-alt"></i>
				返回列表
			</a>
			</h4>
			<div class="row">
				<div class="col-md-9">
					<!--box-->
					<div class="saojie-det-body box border blue">
						<!--title-->
						<div class="box-title">
							<!--区域选择按钮-->
							<div class="btn-group btn-group-xs">
								<button type="button" class="btn btn-default ">全部区域</button>
								<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									<span class="caret"></span>
									<span class="sr-only">Toggle Dropdown</span>
								</button>
								<ul class="dropdown-menu">
									<li><a href="#">Action</a></li>
									<li><a href="#">Another action</a></li>
									<li><a href="#">Something else here</a></li>
									<li role="separator" class="divider"></li>
									<li><a href="#">Separated link</a></li>
								</ul>
							</div>
							<!--/区域选择按钮-->
							<div class="det-msg">
								<span>邹县扫街商家<span class="num">256</span>家</span>
								<span>扫街已完成<span class="num">80%</span></span>
							</div>
							<!--/row-->
							<div class="btn-group title-page">
								<a class="title-btn active" href="javascript:;"><i class="icon icon-map"></i>地图</a>
								<a class="title-btn" href="javascript:;"><i class="icon icon-list"></i>列表</a>
							</div>
						</div>
						<!--title-->
						<!--box-body-->
						<div class="box-body">
							<!--地图-->
							<div class="saojie-map ">
								<!--地图位置-->
								<div style="height: 600px;" class="body-map" id="allmap">
<!-- 									<img src="static/img/saojie-map.png" /> -->
								</div>
								<button class="btn btn-approve col-sm-2 col-sm-offset-5">审核通过</button>
							</div>
							<!--/地图-->
							<!--列表-->
							<div class="saojie-list active">
								<!--tr-->
								<div class="list-tr">
									<img class="shop-img" src="static/img/saojie-img.png" />
									<div style="display: inline-block;" class="list-conter">
										<h4>大鹏十年手机品质专卖店</h4>
										<p>备注：百度和携程两个难兄难弟，一个是市值580亿美元， 制霸行业十余年的国内搜索龙头的老大，一个是国内OTA绝对龙头， 市值120亿美元的旅行老大，在这一周都过得水深火热......
										</p>
										<span class="pull-right">2015.11.12 15:22</span>
									</div>
								</div>
								<!--/tr-->
								<!--tr-->
								<div class="list-tr">
									<img class="shop-img" src="static/img/saojie-img.png" />
									<div style="display: inline-block;" class="list-conter">
										<h4>大鹏十年手机品质专卖店</h4>
										<p>备注：百度和携程两个难兄难弟，一个是市值580亿美元， 制霸行业十余年的国内搜索龙头的老大，一个是国内OTA绝对龙头， 市值120亿美元的旅行老大，在这一周都过得水深火热......
										</p>
										<span class="pull-right">2015.11.12 15:22</span>
									</div>
								</div>
								<!--/tr-->
								<!--tr-->
								<div class="list-tr">
									<img class="shop-img" src="static/img/saojie-img.png" />
									<div style="display: inline-block;" class="list-conter">
										<h4>大鹏十年手机品质专卖店</h4>
										<p>备注：百度和携程两个难兄难弟，一个是市值580亿美元， 制霸行业十余年的国内搜索龙头的老大，一个是国内OTA绝对龙头， 市值120亿美元的旅行老大，在这一周都过得水深火热......
										</p>
										<span class="pull-right">2015.11.12 15:22</span>
									</div>
								</div>
								<!--/tr-->
							</div>
							<!--/列表-->
						</div>
						<!--/box-body-->
					</div>
					<!--/box-->
				</div>

				<!--team-map-->
				<div class="col-md-3">
					<!--box-->
					<!--不同阶段颜色不同1：pink 2：yellow 3:violet 4:-->
					<div class="ywmamber-msg box border pink">
						<!--title-->
						<div class="box-title">
							<i class="icon icon-time"></i>考核中
						</div>
						<div class="box-body">
							<!--ywmamber-body-->
							<div class="ywmamber-body">
								<img width="80" src="static/img/user-head.png" alt="..." class="img-circle">
								<div class="msg-text">
									<h4>易小星</h4>
									<p>ID: A236743252</p>
									<p>电话: 12547346455</p>
								</div>
							</div>
							<!--/ywmamber-body-->
							<div class="stage">
								<span class="">第一阶段:60%      </span>
							</div>
							<div class="progress progress-sm">
								<div style="width: 60%;" class="progress-bar bar-stage"></div>
							</div>
							<div class="operation">
								<a href="javascript:;" class="">考核设置</a>
								<a href="javascript:;">辞退</a>
								<a href="javascript:;" class="pull-right">查看</a>
							</div>
							<div class="yw-text">
								入职时间:<span> 2015.09.21</span><br />
								负责区域:<span>山东省滨州市邹平县</span>
							</div>
							<!--拜访任务-->
							<div class="visit">
								<button class="col-xs-12 btn btn-visit" href="javascript:;"><i class="icon icon-add"></i>拜访</button>
							</div>
							<!--拜访任务-->
							<!--操作-->
							<div class="operation">
								<a href="javascript:;" class="">账户设置</a>
								<a href="javascript:;">冻结账户</a>
							</div>
							<!--操作-->
							<!--虚线-->
							<div class="hr"></div>
							<!--虚线-->
							<!--业务外部链接-->
							<div class="yw-link">
								<a class="link-oper" href="javascript:;"><i class="icon icon-user"></i>个人资料</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-income"></i>收益</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-task"></i>任务</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-log"></i>日志</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-footprint"></i>足迹</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-signin"></i>签收记录</a>
								<a class="link-oper" href="javascript:;"><i class="icon icon-saojie"></i>扫街记录</a>
							</div>


						</div>
					</div>
					<!--/team-map-->
				</div>
			</div>
			<!-- Bootstrap core JavaScript================================================== -->
			<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
			<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
			<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
			<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
			<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
			<script src="static/js/jquery/jquery-1.11.3.min.js"></script>
			<!-- Include all compiled plugins (below), or include individual files as needed -->
			<script src="static/bootstrap/js/bootstrap.min.js"></script>
			<script src="static/saojie/saojie-det.js" type="text/javascript" charset="utf-8"></script>
			<script type="text/javascript">
			// 百度地图API功能
			var map = new BMap.Map("allmap");    // 创建Map实例
			map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);  // 初始化地图,设置中心点坐标和地图级别
			map.addControl(new BMap.MapTypeControl());   //添加地图类型控件
			map.setCurrentCity("北京");          // 设置地图显示的城市 此项是必须设置的
			map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
		</script>
			
	</body>

</html>