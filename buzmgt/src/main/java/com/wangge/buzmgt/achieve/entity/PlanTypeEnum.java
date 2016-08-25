package com.wangge.buzmgt.achieve.entity;

/**
 * 
* @ClassName: PlanTypeEnum
* @Description: 方案类型枚举
* @author ChenGuop
* @date 2016年8月24日 下午2:02:11
*
 */
public enum PlanTypeEnum {

  ACHIEVE("达量"),OVERLAY("叠加"),REWARD("奖励");
  private String name;
  PlanTypeEnum(String name){
    this.name=name;
  }
  public String getName(){
    return name;
  }
}
