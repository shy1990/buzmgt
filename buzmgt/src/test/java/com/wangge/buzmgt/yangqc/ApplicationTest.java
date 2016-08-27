
package com.wangge.buzmgt.yangqc;

import com.wangge.buzmgt.BuzmgtApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class ApplicationTest {
//  @Autowired
//  MainPlanUserRepository mainplanUserRep;
//  @Autowired
//  MainPlanRepository mainplanRep;
//  @Autowired
//  SalesManRepository salesRep;
//  
//  @Test
//  @Transactional(rollbackFor = Exception.class)
//  public void testPlan() {
//    MainIncomePlan plan = new MainIncomePlan("济南地区小米5测试", "测试方案1", new Date());
//    SalesMan salesman = salesRep.findOne("A37152604120");
//    IncomeMainplanUsers users = new IncomeMainplanUsers(salesman, plan);
//    mainplanUserRep.save(users);
//    mainplanRep.save(plan);
//  }
  
}
