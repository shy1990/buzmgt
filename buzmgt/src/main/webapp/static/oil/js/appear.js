function biankuang(obj){
    $(obj).find('.pic-tx').stop(true).animate({
        height:'305px'
    },300)

}
//边框效果--移出
function biankuang1(obj){

    $(obj).find('.pic-tx').stop(true).delay(100).animate({
        height:'0px'
    },100)

}
//触发
$('.spbq').hover(
    function () {
        var obj = $(this);
        $(obj).find('.text_gobuy').addClass('text_gobuy_show');
        $(obj).find('.search_y').animate({left:'150',opacity:1},300);
        biankuang(obj);
    },
    function () {
        var obj = $(this);
        $(obj).find('.text_gobuy').removeClass('text_gobuy_show');
        $(obj).find('.search_y').animate({left:'100',opacity:0},300);
        biankuang1(obj);
    }
);
