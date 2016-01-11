$(function(){
	$('.form-group input').focus(function(){
		$(this).parents('.form-group').removeClass('has-error');
	});
});