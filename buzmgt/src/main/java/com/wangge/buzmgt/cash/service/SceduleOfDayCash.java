package com.wangge.buzmgt.cash.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SceduleOfDayCash {
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private CashService cashService;

	private Logger logger=Logger.getLogger(SceduleOfDayCash.class);
	// 每日23时30分执行 0 30 23 * * ? 
//	@Scheduled(cron = " 0 30 23 * * ? ")
	public void handleCashData() {
	  logger.info("-----------------开启定时结算");
	  List<String> userIds=cashService.findByStatusGroupByUserId();
	  userIds.forEach(userId->{
	    logger.info("userId:"+userId);
	    boolean msg=cashService.createWaterOrderByCash(userId,new Date());
	    logger.info("返回结果:"+msg);
	  });
	  logger.info("-----------------结束定时结算");
	  
	}

}
