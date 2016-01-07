$(function() {
	$('#left-menu a').click(function() {
		$(this).parent('li').addClass('active');
		$(this).parent('li').siblings().removeClass('active');
		$(this).parent('li').siblings().find('.menu-second').hide();
	})
	$('a.menu-second-box').click(function() {
		$(this).siblings('ul.menu-second').show();

	});
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

var timer1 = window.setInterval("reinitIframe()", 500); //定时开始
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
try{
    var bHeight = iframe.contentWindow.document.body.scrollHeight;
    var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
    var height = Math.max(bHeight, dHeight);
    iframe.height = height;
}catch (ex){}
// 停止定时
window.clearInterval(timer1);

}


