$(function() {
	//二级菜单栏
	$('.menu-second>li>a').click(function(){
		$(this).parent('li').addClass('active'); 		
		$(this).parent('li').siblings().removeClass('active');
	})
	//一级菜单栏
	$('.menu>li>a').click(function(){
		$(this).parent('li').toggleClass('active'); 		
		$(this).parent('li').siblings().removeClass('active');
	})
	//菜单a href 加载在iframe
	$("#left-menu ul li a").click(function(event) {
		event.preventDefault();
		var url=$(this).attr("href");
		if(url!=""&&url!=null){
			var iframe = document.getElementById("iframepage");
			iframe.src=url;
			reinitIframe();
		}
	});


});

//var timer1 = window.setInterval("reinitIframe()", 500); //定时开始
function reinitIframe(){
var iframe = document.getElementById("iframepage");
try{
    var bHeight = iframe.contentWindow.document.body.scrollHeight;
    var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
    var height = Math.max(bHeight, dHeight);
    iframe.height = height;
}catch (ex){}
}


function reinitIframeEND(){
var iframe = document.getElementById("iframepage");
var topMenu = document.getElementById("top_menu");
var leftMenuHeight = $('#left-menu').outerHeight();
var $_topMenuHeight=$(topMenu).outerHeight();
    var bHeight = iframe.contentWindow.document.body.clientHeight;
//        var bHeight = document.body.clientHeight;
//var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
//    var height = Math.max(bHeight, dHeight);
//    iframe.height = height-$_topMenuHeight;
    iframe.height = leftMenuHeight-5;
    console.info(leftMenuHeight);

}


