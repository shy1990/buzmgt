package com.wangge.buzmgt.achieve.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.achieveset.service.AchieveService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class AchieveServerTest {
  
  @Autowired
  private AchieveService achieveService;
  @Test
  public void test(){
    List<String> goodIds = new ArrayList<>();
    goodIds.add("436c68ee8d884ab5a92a7ccda5f13405");
    goodIds.add("88bd9dc743cd4bafad198153024469d3");
    List<Map<String, Object>> hehe = achieveService.findRuleByGoods(goodIds, 12L, "123123412");
    System.out.println(hehe);
  }
}
