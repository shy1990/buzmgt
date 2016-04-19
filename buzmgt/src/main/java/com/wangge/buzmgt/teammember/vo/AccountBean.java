package com.wangge.buzmgt.teammember.vo;

import java.io.Serializable;
// 账号管理bean
public class AccountBean implements Serializable{
  
  private static final long serialVersionUID = -4389885527660615969L;
  private String  position; //职务
  private String  accountNum; //账号
  private String  name;     // 姓名
  private String  areaName; //负责区域名称
  private String  roleName;   //角色权限
  private String  status ;    //账号状态  0正常,1冻结
  private String  userId;     //用户id
  private Integer totalNum;   //总条数
  
  public Integer getTotalNum() {
    return totalNum;
  }
  public void setTotalNum(Integer totalNum) {
    this.totalNum = totalNum;
  }
  public String getPosition() {
    return position;
  }
  public void setPosition(String position) {
    this.position = position;
  }
  public String getAccountNum() {
    return accountNum;
  }
  public void setAccountNum(String accountNum) {
    this.accountNum = accountNum;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getAreaName() {
    return areaName;
  }
  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }
  public String getRoleName() {
    return roleName;
  }
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  
}
