package com.wangge.buzmgt.sys.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortUtil {
	/**
     * 创建分页请求.
     */
    public static PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
        Sort sort = null;
        //根据下单时间降序排序
        if ("role".equals(sortType)) {
            sort = new Sort(Direction.ASC, "id");
        }else if("res".equals(sortType)){
        	 sort = new Sort(Direction.ASC, "createTime");
        }
        return new PageRequest(pageNumber - 1, pagzSize, sort);
    }
}
