package com.wangge.buzmgt.customTask.server;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.customTask.entity.CustomTask;


public interface CustomTaskServer {
	/**
	 * 获取分页数据
	 * 
	 * @param salesmanId
	 * @param page
	 * @return
	 */
	public Map<String, Object> getList(String salesmanId, Pageable page);

	/**
	 * 保存自定义任务
	 * 
	 * @param customTask
	 * @throws Exception 
	 */
	public void save(CustomTask customTask) throws Exception;

	/**取得查询列表
	 * @param page
	 * @param searchParams
	 * @return
	 */
	public List<Map<String, Object>> findAll(Pageable page, Map<String, Object> searchParams);

	/**通过自定义任务获取相关的业务员ids
	 * @param customTask
	 * @return
	 */
	public Set<String> getSaleSet(CustomTask customTask);
}
