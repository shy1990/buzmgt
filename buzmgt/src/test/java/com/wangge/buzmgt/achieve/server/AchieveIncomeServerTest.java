package com.wangge.buzmgt.achieve.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.service.AchieveIncomeService;
import com.wangge.buzmgt.achieveset.service.AchieveService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class AchieveIncomeServerTest {
  
  @Autowired
  private AchieveIncomeService achieveIncomeService;
  @Autowired
  private AchieveService achieveService;
  @Test
  public void test(){
    Achieve achieve = achieveService.findOne(2L); 
    List<Map<String, Object>> maps = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    map.put("goodId", "88bd9dc743cd4bafad198153024469d3");
    map.put("rule", achieve);
    map.put("num", 2);
    maps.add(map);
    String orderNo = "201608261755031";
    String UserId = "A371121210";
    boolean msg = achieveIncomeService.createAchieveIncomeBy(maps, orderNo, UserId);
    System.out.println(msg);
  }
}
