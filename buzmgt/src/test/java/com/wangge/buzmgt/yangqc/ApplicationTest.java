
package com.wangge.buzmgt.yangqc;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.schedule.entity.Jobtask;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class ApplicationTest {
  @Autowired
  MainIncomeRepository mainIncomeRep;
  @Autowired
  JobRepository jobRep;
  //
  // @Test
  // @Transactional(rollbackFor = Exception.class)
  // public void testPlan() {
  // MainIncomePlan plan = new MainIncomePlan("济南地区小米5测试", "测试方案1", new Date());
  // SalesMan salesman = salesRep.findOne("A37152604120");
  // IncomeMainplanUsers users = new IncomeMainplanUsers(salesman, plan);
  // mainplanUserRep.save(users);
  // mainplanRep.save(plan);
  // }
  @Test
  public void testPro() {
    mainIncomeRep.initMonthSalary();
  }
  @Test
  public void testJob(){
    List<Jobtask> jobList=  jobRep.defaltfindAll(new Date()); 
  }
}
