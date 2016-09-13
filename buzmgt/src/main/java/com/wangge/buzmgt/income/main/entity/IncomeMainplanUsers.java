package com.wangge.buzmgt.income.main.entity;
/***********************************************************************
 * Module:  SysIncomeMainplanUsers.java
 * Author:  yangqc
 * Purpose: Defines the Class SysIncomeMainplanUsers
 ***********************************************************************/

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * 收益主计划关联人员表
 * 
 * 2016-08 关联人员 杨其才 1.1
 * 
 * @pdOid 2c228c95-6e4c-41b6-9e25-69ee67125183
 */
@Entity
@Table(name = "sys_income_mainplan_users")
public class IncomeMainplanUsers {
  /**
   * 主键
   * 
   * @pdOid 38eeef3c-e38b-4872-aaeb-b95510eb0eb2
   */
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  /**
   * 业务员id
   * 
   * @pdOid 507b5d8b-02c9-4fbe-87d7-bb09d4daa8df
   */
  @Column(name = "salesman_id")
  private String salesmanId;
  
  private String salesmanname;
  /**
   * 生成时间
   * 
   * @pdOid f28804c6-d09a-462d-a382-1fa3d687a2e2
   */
  private Date createtime = new Date();
  /**
   * 状态(0正常,1移除)
   * 
   * @pdOid d9096e17-fd13-43ed-a892-1941ba9bc56f
   */
  @Enumerated(EnumType.ORDINAL)
  private FlagEnum state = FlagEnum.NORMAL;// 是否删除 normal-正常,del-删除
  
  /**
   * 移除时间
   * 
   * @pdOid 7705ac90-3651-447c-b62c-42bbd736b2c0
   */
  @Temporal(TemporalType.DATE)
  private Date fqtime;
  @JoinColumn( name = "PLAIN_ID", insertable = false, updatable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private MainIncomePlan mainplan;
  @Column(name = "PLAIN_ID")
  private Long planId;
  // 操作人id
  private String authorId;
  @Temporal(TemporalType.TIMESTAMP)
  private Date uptime = new Date();
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getAuthorId() {
    return authorId;
  }
  
  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }
  
  public Date getUptime() {
    return uptime;
  }
  
  public void setUptime(Date uptime) {
    this.uptime = uptime;
  }
  
  public String getSalesmanname() {
    return salesmanname;
  }
  
  public void setSalesmanname(String salesmanname) {
    this.salesmanname = salesmanname;
  }
  
  public FlagEnum getState() {
    return state;
  }
  
  public void setState(FlagEnum state) {
    this.state = state;
  }
  
  public Long getPlanId() {
    return planId;
  }

  public void setPlanId(Long planId) {
    this.planId = planId;
  }

  public java.util.Date getFqtime() {
    return fqtime;
  }
  
  public void setFqtime(java.util.Date fqtime) {
    this.fqtime = fqtime;
  }
  
  public MainIncomePlan getMainplan() {
    return mainplan;
  }
  
  public void setMainplan(MainIncomePlan mainplan) {
    this.mainplan = mainplan;
  }
  
  public Date getCreatetime() {
    return createtime;
  }
  
  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }
  
  public String getSalesmanId() {
    return salesmanId;
  }
  
  public void setSalesmanId(String salesmanId) {
    this.salesmanId = salesmanId;
  }
  
  public IncomeMainplanUsers(SalesMan salesman, MainIncomePlan mainplan) {
    super();
    this.mainplan = mainplan;
  }
  
  public IncomeMainplanUsers(String salesmanId, MainIncomePlan mainplan) {
    super();
    this.salesmanId = salesmanId;
    this.mainplan = mainplan;
  }
  
  public IncomeMainplanUsers() {
    super();
  }
  
}