<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<title>控制台</title>
		<!-- Bootstrap -->
		<link href="static/bootstrap/css/bootstrap.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" href="static/css/index.css" />
		<link rel="stylesheet" type="text/css" href="static/purview-setting/purview-setting.css" />
		<script src="static/js/jquery/jquery-2.1.4.min.js" type="text/javascript" charset="utf-8"></script>
	</head>

	<body>

		<body>
			<nav class="navbar navbar-inverse navbar-fixed-top">
				<div class="container-fluid">
					<div class="row">
						<div class="navbar-header col-sm-3 col-md-2">
							<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							</button>
							<a class="navbar-brand logo " href="#"><img src="static/img/logo.png" /></a>
						</div>
						<div id="navbar" class="navbar-collapse collapse ">
							<div class="pull-left msg-alert">
								<p>
									<span class="msg-head"><i class="msg-alert-icon"></i>消息提醒</span>
									<span class="msg-content">李雅静申请山东省滨州市邹平县扫街审核</span>
								</p>
							</div>
							<ul class="nav navbar-nav navbar-right">
								<li role="presentation" class="dropdown">
									<a class="msg-icon-box" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
										<i class="msg-icon"></i>
										<span class="mark">4</span>
									</a>
									<ul class="dropdown-menu msg" aria-labelledby="dropdownMenu1">
										<li><a><i class="msg-count-icon"></i>四条消息提醒</a></li>
										<li><a href=""><i></i>曾志伟申请扫街审核<span class="pull-right">2015.12.19</span></a></li>
										<li><a href=""><i></i>退出登录</a></li>
									</ul>
								</li>
								<li role="presentation" class="dropdown">
									<a class="time-icon-box">
										<i class="time-icon"></i>
										<span class="mark-red">6</span>
									</a>
									<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
										<li><a href="">账户设置</a></li>
										<li><a href="">退出登录</a></li>
									</ul>
								</li>
								<li role="presentation" class="dropdown marg-r-40">
									<a class="user-icon-box" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
     							我叫李小龙<span class="caret"></span>
   								 </a>
									<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
										<li><a class="user upd-icon" href="">账户设置</a></li>
										<li><a class="user logout-icon" href="">退出登录</a></li>
									</ul>
								</li>
							</ul>
							<form class="navbar-form navbar-right">
								<div class="search-box">
									<input class="search-input" type="text" class="form-control" placeholder="Search...">
									<i class="search-icon"></i>
								</div>
							</form>
						</div>
					</div>
				</div>
			</nav>

			<div class="container-fluid">
				<div id="" class="row">
					<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
						<ul class="nav nav-sidebar menu">
							<li class="active"><a class="console" href="#">控制台 <span class="sr-only"></span></a></li>
							<li class="" role="presentation">
								<!--二级菜单添加menu-second-box-->
								<a class="menu-second-box management" href="" data-toggle="dropdown">业务管理<span class="pull-right down-icon"></span></a>
								<ul class="menu-second">
									<li class="active"><a class="team" href="/salesman/salesManList">团队成员</a></li>
									<li><a class="street-settings">扫街设置</a></li>
									<li><a href="" class="client-exploit">客户开发</a></li>
									<li><a href="" class="yw-examine">业务考核</a></li>
									<li><a href="" class="mission">任务</a></li>
								</ul>
							</li>
							<li class="">
								<a class="menu-second-box cli-manage" data-toggle="dropdown">客户管理<span class="pull-right down-icon"></span></a>
								<ul class="menu-second">
									<li class=""><a class="merchants-distribution" href="">商家分布</a></li>
									<li><a href="" class="liveness">活跃度</a></li>
									<li><a href="" class="deposit-customer">沉积客户</a></li>
									<li><a href="" class="second-pick-goods">二次提货</a></li>
									<li><a href="" class="correlated-subquery">关联查询</a></li>
								</ul>
							</li>
							<li><a href="" class="product-sales">产品销量</a></li>
							<li><a href="" class="inventory">库存</a></li>
							<li><a href="" class="active-note">活动通知</a></li>
							<li>
								<a class="menu-second-box statistics" data-toggle="dropdown">统计<span class="pull-right down-icon"></span></a>
								<ul class="menu-second">
									<li class=""><a class="business-analysis" href="">商家分析</a></li>
									<li><a href="" class="product-analysis">产品分析</a></li>
								</ul>
							</li>
							<li>
								<a href="" class="menu-second-box basic-settings" data-toggle="dropdown">基础设置<span class="pull-right down-icon"></span></a>
								<ul class="menu-second">
									<li class="active"><a href="region/initRegion" class="regionalism">区域划分</a></li>
									<li><a href="" class="organiz-structure">组织结构</a></li>
									<li><a href="" class="account-manage">账号管理</a></li>
									<li><a href="/role/roleList" class="permis-setting">权限设置</a></li>
								</ul>
							</li>
							<li><a href="/role/roleList" class="permis-setting">权限设置</a></li>
							<li><a href="/res/menuList" class="permis-setting">菜单设置</a></li>
						</ul>
					</div>
					<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
						<div id="main">
							<h4 class="page-header">权限设置</h1>
							<div class="row">
							<div class="col-md-12">
								<div class="box border blue">
									<div class="row">
										<div class="col-xs-4 col-md-5 re-padd-right">
											<div class="role">
												<div class="role-title"><i class="title-icon"></i>角色</div>
												<div class="role-list">
													<div style="width: 100%;height: 32px; border-right: 1px solid rgb(221, 221, 221);"></div>
													<ul class="nav nav-tabs">
													   <li class="active">
													   	<a href="#tab_1_1" data-toggle="tab">区域总监 
													   		<i class="icon delete-icon">
													   			<div class="">
													   	
													   			</div>
													   		</i>
													   		<i class="icon query-icon"></i> 
													   	</a>
													   </li>
													   <li><a href="#tab_1_2" data-toggle="tab">财务总监</a></li>
													   <li><a href="#tab_1_3" data-toggle="tab">售后部</a></li>
													   <li><a href="#tab_1_3" data-toggle="tab">人资部</a></li>
													</ul>
												</div>
													<a class="btn btn-danger">创建角色</a>
											</div>
										</div>
										<div class="col-xs-8 col-md-7 re-padd-left">
											<div class="setting">
												<div class="setting-title">
													<i class="title-icon"></i> 权限设置 <i class="visible-no pull-right">不可见</i> <i class="visible pull-right">可见</i>
												</div>
												<!--<div class="alert alert-success">
												  <h4>Tabs on Left, Right and Bottom</h4>
												  <p>Support for left, right and bottom is removed from Bootstrap 3.</p> 
												  <p>But with <strong>Cloud Admin</strong> you still cal use <code>.tabbable .tabs-left</code>, <code>.tabbable .tabs-right</code> and <code>.tabbable .tabs-below</code> as you did with Bootstrap 2.</p>
												</div>-->
											</div>
										</div>
									</div>
									<div class="box-body big">
										<div class="alert alert-success">
										  <h4>Tabs on Left, Right and Bottom</h4>
										  <p>Support for left, right and bottom is removed from Bootstrap 3.</p> 
										  <p>But with <strong>Cloud Admin</strong> you still cal use <code>.tabbable .tabs-left</code>, <code>.tabbable .tabs-right</code> and <code>.tabbable .tabs-below</code> as you did with Bootstrap 2.</p>
										</div>
										<div class="row">
										  <div class="col-md-6">
											 <div class="panel panel-default">
												<div class="panel-body">
													 <div class="tabbable">
														<ul class="nav nav-tabs">
														   <li class="active"><a href="#tab_1_1" data-toggle="tab"><i class="fa fa-home"></i> Home</a></li>
														   <li><a href="#tab_1_2" data-toggle="tab"><i class="fa fa-envelope"></i> Messages</a></li>
														   <li><a href="#tab_1_3" data-toggle="tab">Profile</a></li>
														</ul>
														<div class="tab-content">
														   <div class="tab-pane fade in active" id="tab_1_1">
															  <div class="divide-10"></div>
															  <p> There were flying cantaloupes, rainbows and songs of happiness near by, I mean I was a little frightened by the flying fruit but I'll take this any day over prison inmates. I skipped closer and closer to the festivities and when I arrived I seen all my friends I had went to high school with there were holding hands and singing Kumbayah around the camp ice.. Yes It was a giant block of ice situated on three wood logs. </p>
														   </div>
														   <div class="tab-pane fade" id="tab_1_2">
																<div class="divide-10"></div>
															  <p> Everyone turned toward me and gave me the death stare and I knew I had screwed up once again, they all walked in slow motion towards me saying the same familiar chant I had heard earlier, before anyone could reach me I awoke in a frantic sweaty rush in my bed. My legs were no longer on fire and I felt slightly normal again. I noticed that my mom, a preacher, and several other family members were standing around me. </p>
														   </div>
														   <div class="tab-pane fade" id="tab_1_3">
																<div class="divide-10"></div>
															  <p> Yesterday I was on my way to class, when a black cat fell from the sky. I didn't really know what that nonsense was about so I asked him if I could step around him because he was bad luck, but he simply meowed and then disappeared. I was a bit worried that maybe he'd teleported to somewhere dangerous, but a wizard came and assured me that it was alright. I threw my Zune at him because I was 78% sure he was lying. The wizard roared at me and sentenced my mother to thirty five years of chain smoking. I was sad. [do] </p>
														   </div>
														</div>
													 </div>
												 </div>
											 </div>
										  </div>
										  <div class="col-md-6">
											  <div class="panel panel-default">
													<div class="panel-body">
														 <div class="tabbable  tabs-below">
															<div class="tab-content">
															   <div class="tab-pane fade in active" id="tab_2_1">
																  <p><span class="label label-success arrow-in">Offer</span> Title with stickers.</p>
																  <p> blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammeled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business</p>
															   </div>
															   <div class="tab-pane fade" id="tab_2_2">
																  <p> first competitive browser, which was complete with its own features and tags. It was also the first browser to support style sheets, which at the time was seen as an obscure authoring technique.[5] The HTML markup for tables was originally intended for displaying tabular data. However designers quickly realized the potential of using HTML tables for creating the complex, multi-column layouts that were otherwise not possible. At this time, as design and good aesthetics seemed to take precedence over</p>
															   </div>
															   <div class="tab-pane fade" id="tab_2_3">
																  <p> Netscape Communicator code under an open source licence, enabling thousands of developers to participate in improving the software. However, they decided to stop and start from the beginning, which guided the development of the open source browser and soon expanded to a complete application platform.[5] The Web Standards Project was formed, and promoted browser compliance with HTML and CSS standards by creating Acid1, Acid2, and Acid3 tests. 2000 was a big year for Microsoft</p>
															   </div>
															</div>
															<ul class="nav nav-tabs">
															   <li class="active"><a href="#tab_2_1" data-toggle="tab"><i class="fa fa-bullhorn"></i> Discounts</a></li>
															   <li><a href="#tab_2_2" data-toggle="tab">Section 2</a></li>
															   <li><a href="#tab_2_3" data-toggle="tab">Section 3</a></li>
															</ul>
														 </div>
													</div>
												</div>
										  </div>
									   </div>
									   <div class="row">
										  <div class="col-md-6">
											<div class="panel panel-default">
												<div class="panel-body">
													 <div class="tabbable tabs-left">
														<ul class="nav nav-tabs">
														   <li class="active"><a href="#tab_3_1" data-toggle="tab">Desktop</a></li>
														   <li><a href="#tab_3_2" data-toggle="tab">Laptop</a></li>
														   <li><a href="#tab_3_3" data-toggle="tab">Mobile</a></li>
														</ul>
														<div class="tab-content">
														   <div class="tab-pane fade in active" id="tab_3_1">
															  <p>Accordingly, a design may be broken down into units design on a website may identify what works for its target market. This can be an age group or particular strand of culture; thus the designer may understand the trends of its audience. Designers may also understand the type of website they are designing, meaning, for example, that (B2B) business-to-business website design considerations might differ greatly from a consumer targeted website such as a retail or entertainment website. Careful consideration might be made to ensure that the </p>
														   </div>
														   <div class="tab-pane fade" id="tab_3_2">
															  <p>Motion graphics may be expected or at least better received with an entertainment-oriented website. Alternative to HTML-table-based layouts and grid-based design in both page layout design principle, and in coding technique, but were very slow to be adopted.[note 1] This was due to considerations of screen reading devices and windows varying in sizes which designers have no control over. Accordingly, a design may be broken down into units (sidebars, content blocks, embedded advertising areas, navigation areas) that are sent to the browser and which will be fitted into the display window b</p>
														   </div>
														   <div class="tab-pane fade" id="tab_3_3">
															  <p>The choice of whether or not to use motion graphics may depend on the target market for the website. Motion graphics may be expected or at least better received with an entertainment-oriented website. However, a website target audience with a more serious or formal interest (such as business, community, or government) might find animations unnecessary and distracting if only for entertainment or decoration purposes. This doesn't mean that more serious content couldn't be enh</p>
														   </div>
														</div>
													 </div>
													</div>
												</div>
										  </div>
										  <div class="col-md-6">
											<div class="panel panel-default">
												<div class="panel-body">
													 <div class="tabbable  tabs-right">
														<ul class="nav nav-tabs tabs-right">
														   <li class="active"><a href="#tab_4_1" data-toggle="tab"><span class="badge badge-red font-11">4</span> Section 1</a></li>
														   <li><a href="#tab_4_2" data-toggle="tab">Section 2</a></li>
														   <li><a href="#tab_4_3" data-toggle="tab">Section 3</a></li>
														</ul>
														<div class="tab-content">
														   <div class="tab-pane fade in active" id="tab_4_1">
															  <p>I want to eat the cheese of candy unicorns web presence through strategic solutions on targeting viewers to the site, by using marketing and promotional techniques on the internet
																SEO writers to research and recommend the correct words to be incorporated into a particular website and make the website more accessible and found on numerous search engines
																Internet copywriter to create the written content of the page to appeal to the </p>
														   </div>
														   <div class="tab-pane fade" id="tab_4_2">
															  <p>That is a heaven sent inaction to failure!  Accordingly, a design may be broken down into units (sidebars, content blocks, embedded advertising areas, navigation areas) that are sent to the browser and which will be fitted into the display window by the browser, as best it can. As the browser does recognize the details of the reader's screen (window size, font size relative to window etc.) the browser can make user-specific layout adjustments to fluid layouts, but not</p>
														   </div>
														   <div class="tab-pane fade" id="tab_4_3">
															  <p> After eating that pie you may go smell the roses of the divine heaven whether or not to use interactivity that requires plug-ins is a critical decision in user experience design. If the plug-in doesn't come pre-installed with most browsers, there's a risk that the user will have neither the know how, nor the patience to install a plug-in just to access the content. If the function requires advanced coding language skills, it may be too costly in either time or money to code compared to the amount of enhancement the function will add to the user experien</p>
														   </div>
														</div>
													 </div>
													</div>
												</div>
										  </div>
									   </div>
									<!--/row -->
									</div>
								</div>
							</div>
						</div>
						</div>
						<!-- row end -->
					</div>
				</div>
			</div>
			<!-- Bootstrap core JavaScript
    ================================================== -->
			<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
			<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
			<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
			<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
			<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
			<!-- <script src="static/js/jquery/jquery-1.11.3.min.js"></script> -->
			<!-- Include all compiled plugins (below), or include individual files as needed -->
			<script src="static/bootstrap/js/bootstrap.min.js"></script>
			<script src="http://echarts.baidu.com/build/dist/echarts-all.js"></script>
			<script src="static/js/index.js" type="text/javascript" charset="utf-8"></script>
		</body>

</html>
