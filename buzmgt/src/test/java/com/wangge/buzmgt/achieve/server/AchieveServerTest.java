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
    goodIds.add("9b53d1f1ec4c42c7ae31a65944239f19");
    List<Map<String, Object>> hehe = achieveService.findRuleByGoods(goodIds, 20L, "201609291428564");
    System.out.println(hehe);
  }
}
