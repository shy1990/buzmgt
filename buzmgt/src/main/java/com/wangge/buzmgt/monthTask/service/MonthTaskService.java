package com.wangge.buzmgt.monthTask.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

import com.wangge.buzmgt.monthTask.entity.MonthTask;
import com.wangge.buzmgt.region.entity.Region;

public interface MonthTaskService {

	/**
	 * 查找任务列表
	 * 
	 * @param page
	 * @param month
	 * @param parameters 
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * 
	 */
	Map<String, Object> getMainTaskList(Pageable page, String month, MultiValueMap<String, String> parameters) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException;

	/**
	 * 得到一个可供解析的MonthTsak类
	 * 
	 * @param id
	 * @return
	 */
	Map<String, Object> getMainTaskForUpdate(Long id);

	/**
	 * 通过简单的查询条件和分页实现查询
	 * 
	 * @param parentId
	 * @param goal
	 * @param pageable
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> findTaskSub(Long parentId, Integer goal, Pageable pageable,
			MultiValueMap<String, String> parameters) throws Exception;

	/**
	 * 查找拜访任务执行明细
	 * 
	 * @param shopId
	 * @param month
	 * @param subTaskId
	 * @param model
	 * @return
	 */
	void findTaskExecut(Long shopId, String month, Long subTaskId, Model model);

	/**
	 * task通过MonthTsk查找业务员信息
	 * 
	 * @param task
	 * @param model 
	 */
	void findSalesMan(MonthTask task, Model model);

	/**根据用户权限获取用户负责区域
	 * @param regoinId
	 * @return
	 */
	Region getRegion(String regoinId);

}
