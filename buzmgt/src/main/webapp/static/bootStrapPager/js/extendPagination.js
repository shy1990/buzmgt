/**
 * Created by Hope on 2014/12/28.
 */
(function ($) {
    $.fn.extendPagination = function (options) {
        var defaults = {
            //pageId:'',
            totalCount: options.totalCount,
            showPage: options.showCount,
            limit: options.limit,
            callback: function () {
                return false;
            }
        };
        $.extend(defaults, options || {});
        if (defaults.totalCount == '') {
            //alert('总数不能为空!');
            $(this).empty();
            return false;
        } else if (Number(defaults.totalCount) <= 0) {
            //alert('总数要大于0!');
            $(this).empty();
            return false;
        }
        if (defaults.showPage == '') {
            defaults.showPage = '10';
        } else if (Number(defaults.showPage) <= 0)defaults.showPage = '10';
        if (defaults.limit == '') {
            defaults.limit = '5';
        } else if (Number(defaults.limit) <= 0)defaults.limit = '5';
        var totalCount = Number(defaults.totalCount), showPage = Number(defaults.showPage),
            limit = Number(defaults.limit), totalPage = Math.ceil(totalCount / limit);
        if (totalPage > 0) {//00000000000000000000000000000000
            var html = [];
            html.push('<nav style="text-align: center;"><ul class="pagination">');
            html.push(' <li class="previous"><a href="javascript:;">&laquo;</a></li>');
            html.push('<li class="firstPage hidden"><a href="javascript:;">1</a></li>')
            html.push('<li class="disabled hidden"><span href="javascript:;">...</span></li>');
            if (totalPage <= showPage) {
                for (var i = 1; i <= totalPage; i++) {
                    if (i == 1) html.push(' <li class="active"><a href="javascript:;">' + i + '</a></li>');
                    else html.push(' <li><a href="javascript:;">' + i + '</a></li>');
                }
            } else {
                for (var j = 1; j <= showPage; j++) {
                    if (j == 1) html.push(' <li class="active"><a href="javascript:;">' + j + '</a></li>');
                    else html.push(' <li><a href="javascript:;">' + j + '</a></li>');
                }
            }
            html.push('<li class="disabled hidden"><span >...</span></li>');
            html.push('<li class="lastPage hidden"><a href="javascript:;">' + totalPage + '</a></li>');
            html.push('<li class="next"><a href="javascript:;">&raquo;</a></li></ul>');
            
            //扩展插件
            if(totalPage>showPage){
            	
            	html.push(
            	'<div class="turn-page-box">'+
                '<input id="currtPage" class="form-control currtPage"onkeyup="this.value=this.value.replace(/\\D/g,\'\')"'+
            	'onafterpaste="this.value=this.value.replace(/\\D/g,\'\')"'+
            	'name="currtpage" type="text" value="1" placeholder="跳转">'+
                '<a class="goto" href="javascript:;"><i class="ico icon-search-page"></i></a></div>' );
            			
            }
           	html.push('</nav>');
            
            
            
            
            
            $(this).html(html.join(''));
            if (totalPage > showPage) {
            	$(this).find('ul.pagination li.next').prev().removeClass('hidden');
            	$(this).find('ul.pagination li.next').prev().prev().removeClass('hidden');
            }

            var pageObj = $(this).find('ul.pagination'), preObj = pageObj.find('li.previous'),
                currentObj = pageObj.find('li').not('.previous,.disabled,.next'),
                nextObj = pageObj.find('li.next'),gotoObj=$(this).find('a.goto'),
                firstObj=pageObj.find('li.firstPage'),lastObj=pageObj.find('li.lastPage');
            
            function loopPageElement(minPage, maxPage) {
                var tempObj = preObj.next().next();
                for (var i = minPage; i <= maxPage; i++) {
                    if (minPage == 1 && (preObj.next().attr('class').indexOf('hidden')) < 0){
                    	preObj.next().addClass('hidden');
                    	preObj.next().next().addClass('hidden');
                    }
                    else if (minPage > 1 && (preObj.next().attr('class').indexOf('hidden')) > 0){
                    	preObj.next().removeClass('hidden');
                    	preObj.next().next().removeClass('hidden');
                    }
                    if (maxPage == totalPage && (nextObj.prev().attr('class').indexOf('hidden')) < 0){
                    	nextObj.prev().addClass('hidden');
                    	nextObj.prev().prev().addClass('hidden');
                    }
                    else if (maxPage < totalPage && (nextObj.prev().attr('class').indexOf('hidden')) > 0){
                    	nextObj.prev().removeClass('hidden');
                    	nextObj.prev().prev().removeClass('hidden');
                    }
                    var obj = tempObj.next().find('a');
                    if (!isNaN(obj.html()))obj.html(i);
                    tempObj = tempObj.next();
                }
            }

            function callBack(obj,curr) {
            	$(obj).parents('.pagination').siblings('.turn-page-box').find('input.currtPage').val(curr);
                defaults.callback(curr, defaults.limit, totalCount);
            }
            
            //跳页(拓展)
            gotoObj.click(function(event){
            	event.preventDefault();
            	var currPage = Number($(this).siblings('input.currtPage').val()), activeObj = pageObj.find('li.active'),
                activePage = Number(activeObj.find('a').html());
	            if (currPage == activePage) return false;
	            
	            if (currPage < totalPage) {
	                var maxPage = currPage, minPage = 1;
	                if (totalPage-currPage < showPage) {
	                	if (totalPage - currPage >= 1) maxPage = currPage + 1;
                        else  maxPage = totalPage;
	                	if (maxPage - showPage > 0) minPage = (maxPage - showPage) + 1;
	                    loopPageElement(minPage, maxPage);
	                } else {
	                	minPage = currPage-1 ;
	                	if(currPage == 1) minPage = 1;
	                	maxPage = minPage+showPage-1;
	                    loopPageElement(minPage, maxPage)
	                }
	            }else if(currPage == totalPage){
	            	 var maxPage = currPage, minPage = 1;
	            	if (maxPage - showPage > 0) minPage = (maxPage - showPage) + 1;
	            	loopPageElement(minPage, maxPage);
	            }else{
	            	return false;
	            }
	            activeObj.removeClass('active');
	            $.each(currentObj, function (index, thiz) {
	                if ($(thiz).find('a').html() == currPage && !$(thiz).hasClass("hidden")) {
	                    $(thiz).addClass('active');
	                    callBack(thiz,currPage);
	                }
	            });
            });

            currentObj.click(function (event) {
                event.preventDefault();
                var currPage = Number($(this).find('a').html()), activeObj = pageObj.find('li.active'),
                    activePage = Number(activeObj.find('a').html());
                if (currPage == activePage) return false;
                if (totalPage > showPage) {
                    var maxPage = currPage, minPage = 1;
                    if (currPage == totalPage){
                    	maxPage = totalPage;
                    	minPage = (maxPage - showPage) + 1
                    	loopPageElement(minPage, maxPage)
                    }else if(currPage == 1){
                    	minPage = 1;
                    	maxPage = minPage + showPage - 1;
                    	loopPageElement(minPage, maxPage)
                    }else if (($(this).prev().attr('class'))
                        && ($(this).prev().attr('class').indexOf('disabled')) >= 0) {
                        minPage = currPage - 1;
                        if(currPage == 1) minPage = 1;
                        maxPage = minPage + showPage - 1;
                        loopPageElement(minPage, maxPage);
                    } else if (($(this).next().attr('class'))
                        && ($(this).next().attr('class').indexOf('disabled')) >= 0) {
                        if (totalPage - currPage >= 1) maxPage = currPage + 1;
                        else  maxPage = totalPage;
                        if (maxPage - showPage > 0) minPage = (maxPage - showPage) + 1;
                        loopPageElement(minPage, maxPage)
                    }              
                }
                activeObj.removeClass('active');
                $.each(currentObj, function (index, thiz) {
                    if ($(thiz).find('a').html() == currPage && !$(thiz).hasClass("hidden")) {
                        $(thiz).addClass('active');
                        callBack(thiz,currPage);
                    }
                });
            });
            preObj.click(function (event) {
                event.preventDefault();
                var activeObj = pageObj.find('li.active'), activePage = Number(activeObj.find('a').html());
                if (activePage <= 1) return false;
                if (totalPage > showPage) {
                    var maxPage = activePage, minPage = 1;                  
                    if ((activeObj.prev().prev().attr('class'))
                        && (activeObj.prev().prev().attr('class').indexOf('disabled')) >= 0) {
                        minPage = activePage - 1;
                        if (minPage > 1) minPage = minPage - 1;
                        maxPage = minPage + showPage - 1;
                        loopPageElement(minPage, maxPage);
                    }
                }
                $.each(currentObj, function (index, thiz) {
                    if ($(thiz).find('a').html() == (activePage - 1)) {
                        activeObj.removeClass('active');
                        $(thiz).addClass('active');
                        callBack(thiz,(activePage - 1));
                    }
                });
            });
            nextObj.click(function (event) {
                event.preventDefault();
                var activeObj = pageObj.find('li.active'), activePage = Number(activeObj.find('a').html());
                if (activePage >= totalPage) return false;
                if (totalPage > showPage) {
                    var maxPage = activePage, minPage = 1;                  
                    if ((activeObj.next().next().attr('class'))
                        && (activeObj.next().next().attr('class').indexOf('disabled')) >= 0) {
                        maxPage = activePage + 2;
                        if (maxPage > totalPage) maxPage = totalPage;
                        minPage = maxPage - showPage + 1;
                        loopPageElement(minPage, maxPage);
                    }
                }
                $.each(currentObj, function (index, thiz) {
                    if ($(thiz).find('a').html() == (activePage + 1)) {
                        activeObj.removeClass('active');
                        $(thiz).addClass('active');
                        callBack(thiz,(activePage + 1));
                    }
                });
            });
        }else{
        	//清除原有分页
        	$(this).html("");
        }
    };
})(jQuery);
