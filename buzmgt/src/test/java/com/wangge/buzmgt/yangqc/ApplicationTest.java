
package com.wangge.buzmgt.yangqc;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.income.main.repository.IncomeMainplanUsersRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.schedule.IncomeSchedule;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class ApplicationTest {
  @Autowired
  MainIncomeRepository mainIncomeRep;
  @Autowired
  JobRepository jobRep;
  @Autowired
  IncomeSchedule incomeSchedule;
  @Autowired
  IncomeMainplanUsersRepository planUserRep;
  
  @Test
  public void testPro() {
    mainIncomeRep.initMonthSalary();
    
  }
  
  @Test
  public void testJob() {
    try {
      incomeSchedule.calculateMonthIncome();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void testFqsj() {
    Optional<Date> date = planUserRep.findMaxFqtimeBySalesmanId("C370113210");
    date.ifPresent(fqtime -> System.out.println(fqtime));
  }
}
