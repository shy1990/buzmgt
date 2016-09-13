
package com.wangge.buzmgt.yangqc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class ApplicationTest {
  @Autowired
  MainIncomeRepository mainIncomeRep;
  @Autowired
  JobRepository jobRep;
  

  @Test
  public void testPro() {
    mainIncomeRep.initMonthSalary();
   
  }
  
  @Test
  public void testJob() {
    mainIncomeRep.calculateOilCost();
    MainIncome mian= mainIncomeRep.findOne(6L);
    System.out.println("------------->累计油补为--"+mian.getOilIncome());
  }
}
