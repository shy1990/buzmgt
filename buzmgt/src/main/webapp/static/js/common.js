$(function() {
	$('.form-group input,.form-group .form-control').focus(function() {
		$(this).parents('.form-group').removeClass('has-error');
	});
//	$(document).ajaxStart(function() {
//		var loadObj=$(".loading-box")
//		if(loadObj.length > 0){
//			$(".loading-box").show();
//			return ;
//		}
//		$("body").prepend("<div class='loading-box'><img id='loading' class='common-loading' src='/static/img/background/loading.gif' /></div>");
//	});
//	$(document).ajaxSuccess(function(){
//		$(".loading-box").hide();
////		alert("All AJAX requests completed");
//	});

});
// 子页面刷新f5
window.document.onkeydown = childRefresh;
function childRefresh(evt) {
	var iframeSrc = parent.document.getElementById('iframepage').src;
	evt = (evt) ? evt : window.event;
	if (evt.keyCode) {
		if (evt.keyCode == 116) {
			evt.preventDefault();// 阻止系统刷新。
			parent.document.getElementById('iframepage').src = iframeSrc;
		}
	}
}
