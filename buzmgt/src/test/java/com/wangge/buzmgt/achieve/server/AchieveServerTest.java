package com.wangge.buzmgt.achieve.server;

import com.wangge.buzmgt.achieve.entity.PlanTypeEnum;

public class AchieveServerTest {
  public static void main(String[] args) {
    try {
      PlanTypeEnum planTypeEnum = PlanTypeEnum.valueOf("asda");
      System.out.println(planTypeEnum);
      
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}
