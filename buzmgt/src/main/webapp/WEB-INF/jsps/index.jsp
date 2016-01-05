<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

	
<link type="text/css" href="static/plugins/css/unical.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="static/css/index.css" />
<link rel="stylesheet" type="text/css"
	href="static/purview-setting/purview-setting.css" />
<script type="text/javascript" src="static/plugins/unical.js"></script>
<script src="static/js/jquery/jquery-1.11.3.min.js"
	type="text/javascript" charset="utf-8"></script>


</head>

<body onload="initial();">
	<%@ include file="top_menu.jsp"%>
	<div class="container-fluid">
		<div id="" class="row">
			<div id="left-menu" class="col-sm-3 col-md-2 sidebar">
				<%@include file="left_menu.jsp"%>
			</div>
			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
					<!-- CALENDAR -->
					<div class="row">
						<div class="col-md-12 index-bg">
							<iframe src="" ></iframe>
							<img alt="背景图片"  src="static/img/index-bg.jpg">
							<div class="index-calender" style="margin-left:50px;">
								<div id="date">
									<p>公历
									<select onchange="changeCld()" id="sy"><option>1900</option>
									<option>1901</option>
									<option>1902</option>
									<option>1903</option>
									<option>1904</option>
									<option>1905</option>
									<option>1906</option>
									<option>1907</option>
									<option>1908</option>
									<option>1909</option>
									<option>1910</option>
									<option>1911</option>
									<option>1912</option>
									<option>1913</option>
									<option>1914</option>
									<option>1915</option>
									<option>1916</option>
									<option>1917</option>
									<option>1918</option>
									<option>1919</option>
									<option>1920</option>
									<option>1921</option>
									<option>1922</option>
									<option>1923</option>
									<option>1924</option>
									<option>1925</option>
									<option>1926</option>
									<option>1927</option>
									<option>1928</option>
									<option>1929</option>
									<option>1930</option>
									<option>1931</option>
									<option>1932</option>
									<option>1933</option>
									<option>1934</option>
									<option>1935</option>
									<option>1936</option>
									<option>1937</option>
									<option>1938</option>
									<option>1939</option>
									<option>1940</option>
									<option>1941</option>
									<option>1942</option>
									<option>1943</option>
									<option>1944</option>
									<option>1945</option>
									<option>1946</option>
									<option>1947</option>
									<option>1948</option>
									<option>1949</option>
									<option>1950</option>
									<option>1951</option>
									<option>1952</option>
									<option>1953</option>
									<option>1954</option>
									<option>1955</option>
									<option>1956</option>
									<option>1957</option>
									<option>1958</option>
									<option>1959</option>
									<option>1960</option>
									<option>1961</option>
									<option>1962</option>
									<option>1963</option>
									<option>1964</option>
									<option>1965</option>
									<option>1966</option>
									<option>1967</option>
									<option>1968</option>
									<option>1969</option>
									<option>1970</option>
									<option>1971</option>
									<option>1972</option>
									<option>1973</option>
									<option>1974</option>
									<option>1975</option>
									<option>1976</option>
									<option>1977</option>
									<option>1978</option>
									<option>1979</option>
									<option>1980</option>
									<option>1981</option>
									<option>1982</option>
									<option>1983</option>
									<option>1984</option>
									<option>1985</option>
									<option>1986</option>
									<option>1987</option>
									<option>1988</option>
									<option>1989</option>
									<option>1990</option>
									<option>1991</option>
									<option>1992</option>
									<option>1993</option>
									<option>1994</option>
									<option>1995</option>
									<option>1996</option>
									<option>1997</option>
									<option>1998</option>
									<option>1999</option>
									<option>2000</option>
									<option>2001</option>
									<option>2002</option>
									<option>2003</option>
									<option>2004</option>
									<option>2005</option>
									<option>2006</option>
									<option>2007</option>
									<option>2008</option>
									<option>2009</option>
									<option>2010</option>
									<option>2011</option>
									<option>2012</option>
									<option>2013</option>
									<option>2014</option>
									<option>2015</option>
									<option>2016</option>
									<option>2017</option>
									<option>2018</option>
									<option>2019</option>
									<option>2020</option>
									<option>2021</option>
									<option>2022</option>
									<option>2023</option>
									<option>2024</option>
									<option>2025</option>
									<option>2026</option>
									<option>2027</option>
									<option>2028</option>
									<option>2029</option>
									<option>2030</option>
									<option>2031</option>
									<option>2032</option>
									<option>2033</option>
									<option>2034</option>
									<option>2035</option>
									<option>2036</option>
									<option>2037</option>
									<option>2038</option>
									<option>2039</option>
									<option>2040</option>
									<option>2041</option>
									<option>2042</option>
									<option>2043</option>
									<option>2044</option>
									<option>2045</option>
									<option>2046</option>
									<option>2047</option>
									<option>2048</option>
									<option>2049</option></select>
										年 
										<select onchange="changeCld()" id="sm"><option>1</option>
									<option>2</option>
									<option>3</option>
									<option>4</option>
									<option>5</option>
									<option>6</option>
									<option>7</option>
									<option>8</option>
									<option>9</option>
									<option>10</option>
									<option>11</option>
									<option>12</option></select>
										月
										<span id="gz">&nbsp;</span></p>
								</div>
								
								<div id="calendar">
									<div id="detail"><div id="datedetail"></div><div id="festival"></div></div>
									<table id="calendarhead">
										<tr> 
											<td>日</td>
											<td>一</td>
											<td>二</td>
											<td>三</td>
											<td>四</td>
											<td>五</td>
											<td>六</td>
										</tr>
									</table>
									<table id="week">
										<tr class="tr1">
											<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(0)" id="sd0"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(1)" id="sd1"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(2)" id="sd2"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(3)" id="sd3"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(4)" id="sd4"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(5)" id="sd5"></td>
											<td class="agreen" onmouseout="mOut()" onmouseover="mOvr(6)" id="sd6"></td>
										</tr>
										<tr class="tr2">
											<td onmouseout="mOut()" onmouseover="mOvr(0)" id="ld0"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(1)" id="ld1"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(2)" id="ld2"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(3)" id="ld3"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(4)" id="ld4"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(5)" id="ld5"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(6)" id="ld6"></td>
										</tr>
										<tr class="tr1">
											<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(7)" id="sd7"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(8)" id="sd8"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(9)" id="sd9"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(10)" id="sd10"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(11)" id="sd11"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(12)" id="sd12"></td>
											<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(13)" id="sd13"></td>
										</tr>
										<tr class="tr2">
											<td onmouseout="mOut()" onmouseover="mOvr(7)" id="ld7"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(8)" id="ld8"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(9)" id="ld9"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(10)" id="ld10"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(11)" id="ld11"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(12)" id="ld12"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(13)" id="ld13"></td>
										</tr>
										<tr class="tr1">
											<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(14)" id="sd14"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(15)" id="sd15"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(16)" id="sd16"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(17)" id="sd17"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(18)" id="sd18"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(19)" id="sd19"></td>
											<td class="agreen" onmouseout="mOut()" onmouseover="mOvr(20)" id="sd20"></td>
										</tr>
										<tr class="tr2">
											<td onmouseout="mOut()" onmouseover="mOvr(14)" id="ld14"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(15)" id="ld15"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(16)" id="ld16"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(17)" id="ld17"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(18)" id="ld18"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(19)" id="ld19"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(20)" id="ld20"></td>
										</tr>
										<tr class="tr1">
											<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(21)" id="sd21"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(22)" id="sd22"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(23)" id="sd23"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(24)" id="sd24"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(25)" id="sd25"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(26)" id="sd26"></td>
											<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(27)" id="sd27"></td>
										</tr>
										<tr class="tr2">
											<td onmouseout="mOut()" onmouseover="mOvr(21)" id="ld21"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(22)" id="ld22"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(23)" id="ld23"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(24)" id="ld24"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(25)" id="ld25"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(26)" id="ld26"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(27)" id="ld27"></td>
										</tr>
										<tr class="tr1">
											<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(28)" id="sd28"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(29)" id="sd29"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(30)" id="sd30"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(31)" id="sd31"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(32)" id="sd32"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(33)" id="sd33"></td>
											<td class="agreen" onmouseout="mOut()" onmouseover="mOvr(34)" id="sd34"></td>
										</tr>
										<tr class="tr2">
											<td onmouseout="mOut()" onmouseover="mOvr(28)" id="ld28"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(29)" id="ld29"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(30)" id="ld30"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(31)" id="ld31"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(32)" id="ld32"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(33)" id="ld33"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(34)" id="ld34"></td>
										</tr>
										<tr class="tr1">
											<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(35)" id="sd35"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(36)" id="sd36"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(37)" id="sd37"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(38)" id="sd38"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(39)" id="sd39"></td>
											<td class="one" onmouseout="mOut()" onmouseover="mOvr(40)" id="sd40"></td>
											<td class="aorange" onmouseout="mOut()" onmouseover="mOvr(41)" id="sd41"></td>
										</tr>
										<tr class="tr2">
											<td onmouseout="mOut()" onmouseover="mOvr(35)" id="ld35"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(36)" id="ld36"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(37)" id="ld37"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(38)" id="ld38"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(39)" id="ld39"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(40)" id="ld40"></td>
											<td onmouseout="mOut()" onmouseover="mOvr(41)" id="ld41"></td>
										</tr>
									</table>
								</div>
						</div>
					</div>
					<!-- /CALENDAR -->
					<iframe id="iframepage" onLoad="iFrameHeight()" scrolling="no" width="100%" src="http://www.3j1688.com/special_160105/index.html"></iframe>
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
	<script src="static/js/jquery/jquery-1.11.3.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="static/bootstrap/js/bootstrap.min.js"></script>
	<script src="static/js/index.js" type="text/javascript" charset="utf-8"></script>
</body>

</html>
