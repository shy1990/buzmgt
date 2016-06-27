package com.wangge.buzmgt.monthTask;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import com.wangge.buzmgt.monthTask.entity.AppServer;
import com.wangge.buzmgt.monthTask.entity.MonthOdersData;
import com.wangge.buzmgt.monthTask.entity.MonthTask;
import com.wangge.buzmgt.monthTask.repository.MonthOrdersDataRepository;
import com.wangge.buzmgt.util.HttpUtil;

@RepositoryEventHandler
public class DataEventHandler {
	@Autowired
	private MonthOrdersDataRepository monthRep;

	private Log log = LogFactory.getLog(DataEventHandler.class);

	/*
	 * TODO 目前将信息推动事项关闭,以后需要会开启
	 */
	@HandleBeforeCreate
	public void handlePersonCreate(MonthTask monthTask) throws Exception {
		try {
			MonthOdersData orda = monthRep.findFirst1bySalesmanOrRegionId(monthTask.getAgentid(), null,
					monthTask.getMonth());
			if (null != orda) {
				orda.setUsed(1);
				monthRep.save(orda);
				monthTask.setMonthData(orda);
			}
			handlePush(monthTask, orda);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实现月任务发布推送
	 * 
	 * @param monthTask
	 * @param orda
	 * @throws Exception
	 */
	private void handlePush(MonthTask monthTask, MonthOdersData orda) {
		if (monthTask.getStatus() == 1) {
			String phone = orda.getSalesman().getMobile();
			Map<String, Object> talMap = new HashMap<String, Object>();
			talMap.put("mobiles", phone);
			talMap.put("msg", "下月的月任务已生成");
			String result = HttpUtil.sendPostJson(AppServer.URL+"mainMonthTask", talMap);
			if (!result.contains("true")) {
				log.debug("手机推送月任务出错!!" + monthTask);
			}
		}
	}

	@HandleBeforeSave
	public void handleProfileSave(MonthTask monthTask) {
//		MonthOdersData orda = monthRep.findFirst1bySalesmanOrRegionId( monthTask.getAgentid(),null,
//				monthTask.getMonth());
//		try {
//			// handlePush(monthTask, orda);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}