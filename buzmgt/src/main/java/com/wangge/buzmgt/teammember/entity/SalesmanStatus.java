package com.wangge.buzmgt.teammember.entity;
/**
 * �Ա�
 * @author dell
 *
 */
public enum SalesmanStatus {
	saojie("扫街中"), kaifa("开发中"), weihu("维护中"),zhuanzheng("已转正"),shenhe("考核中");
	
	private String name;

  SalesmanStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
