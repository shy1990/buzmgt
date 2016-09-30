//package com.wangge.buzmgt.BrandIncome;
//
//import com.wangge.buzmgt.BuzmgtApplication;
//import com.wangge.buzmgt.brandincome.entity.BrandIncome;
//import com.wangge.buzmgt.brandincome.service.BrandIncomeService;
//import com.wangge.buzmgt.salesman.repository.MothPunishUpRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import javax.annotation.Resource;
//import java.util.Date;
//
///**
// * Created by 神盾局 on 2016/5/21.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
//@WebAppConfiguration
//public class MianTest {
//  private static final Logger logger = LoggerFactory.getLogger(MianTest.class);
//  @Resource
//  private BrandIncomeService brandIncomeService;
//
//  @Test
//  public void testPay() {
//    int t = 5;
//    BrandIncome brandIncome = brandIncomeService.findById(new Long(t));
//    Boolean flag = brandIncomeService.realTimeBrandIncomePay(brandIncome,3,"20150820192129211","07c40c5e251d4ebabbf506e8d1242c2e","A37019005210",new Date());
//    System.out.println("----------------"+flag);
//  }
//
//  @Test
//  public void testOut() {
//    int t = 5;
//    BrandIncome brandIncome = brandIncomeService.findById((long) t);
//    Boolean flag = brandIncomeService.realTimeBrandIncomeOut(brandIncome,3,"20150820192129211","07c40c5e251d4ebabbf506e8d1242c2e","A37019005210",new Date());
//    System.out.println("----------------"+flag);
//  }
//}
