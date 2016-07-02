package com.wangge.buzmgt.customTask.server;

import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import com.wangge.buzmgt.customTask.entity.CustomTask;


public interface CustomTaskServer {
	/**
	 * 查询自定义分页数据
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
	public Map<String, Object> findAll(Pageable page, Map<String, Object> searchParams);

	/**通过自定义任务查询有回执和无回执的业务员id
	 * @param customTask
	 * @param model 
	 * @return
	 */
	public void getSaleSet(CustomTask customTask, Model model);

	/**通过customid来获取消息列表
	 * @param customTask
	 * @param pageReq 
	 */
	public Map<String,Object> getMessage(CustomTask customTask, Pageable pageReq);

	/**保存message消息列表
	 * @param messages
	 */
	public void saveMessage(Map<String, Object> messages);

	/**查找最新的消息id
	 * @param taskId
	 * @return
	 */
	public Object findlastId(Long taskId);
}
