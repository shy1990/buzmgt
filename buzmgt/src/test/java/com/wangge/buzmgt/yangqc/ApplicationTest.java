
package com.wangge.buzmgt.yangqc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.income.main.repository.IncomeMainplanUsersRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomePlanRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.schedule.IncomeSchedule;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;
import com.wangge.buzmgt.util.DateUtil;

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
  @Autowired
  MainIncomeService mainIncomeService;
  @Autowired
  MainPlanService planService;
  @Autowired
  MainIncomePlanRepository planRep;
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
  
  @Test
  public void testQt() {
    mainIncomeService.caculateOutedOrder("20160620174259875", "c178c6ff92334a379b9c75af8720b4ac", "1",
        DateUtil.string2Date("2016-09-29 15:30:16"));
  }
  @Test
  public void testDate() {
    planUserRep.findsaleByDateAndMemberId(DateUtil.string2Date("2016-09-01"), "sfsdfa");
    planUserRep.findBysalesmanAndDate(DateUtil.string2Date("2016-09-01"), "B37028206200");
//    List<Map<String, Object>> userList = planService.findEffectUserDateList(20L, DateUtil.string2Date("2016-09-01"),
//        DateUtil.string2Date("2016-09-30"));
//    for (Map<String, Object> usr : userList) {
//      System.out
//          .println(usr.get("userId").toString() + usr.get("startDate").toString() + usr.get("endDate").toString());
//    }
  }
}
