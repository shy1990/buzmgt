/***********************************************************************
 * Module:  SysIncomeMain.java
 * Author:  yangqc
 * Purpose: Defines the Class SysIncomeMain
 ***********************************************************************/
package com.wangge.buzmgt.income.main.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * 收入明细表 date: 2016年9月3日 下午4:58:31 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@NamedStoredProcedureQueries({ @NamedStoredProcedureQuery(name = "initMonth", procedureName = "init_Income_EveMonth"),
    @NamedStoredProcedureQuery(name = "initOilCost", procedureName = "oil_daily_calculate_prod") })
@Table(name = "sys_income_main")
public class MainIncome {
  /** @pdOid 08793dc7-7b0c-45cf-9e6e-4cb30870c2f9 */
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  /**
   * 业务员id
   * 
   * @pdOid 00f5a3a0-8082-4edd-84fd-93f1b7aeba43
   */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private SalesMan salesman;
  /**
   * 基本工资
   * 
   */
  private double basicSalary = 0;
  /**
   * 业务佣金
   * 
   */
  private double busiIncome = 0;
  /**
   * 油补
   * 
   */
  private double oilIncome = 0;
  /**
   * 扣罚
   * 
   */
  private double punish = 0;
  /**
   * 达量
   * 
   */
  private double reachIncome = 0;
  /**
   * 叠加收入
   * 
   */
  private double overlyingIncome = 0;
  /**
   * 总收入
   * 
   */
  private double allresult = 0;
  /**
   * 状态(0,未审核,1已审核)
   */
  @Enumerated(EnumType.ORDINAL)
  private FlagEnum state = FlagEnum.NORMAL;
  /**
   * 月份
   * 
   */
  private String month;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public SalesMan getSalesman() {
    return salesman;
  }
  
  public void setSalesman(SalesMan salesman) {
    this.salesman = salesman;
  }
  
  public double getBasicSalary() {
    return basicSalary;
  }
  
  public void setBasicSalary(double basicSalary) {
    this.basicSalary = basicSalary;
  }
  
  public double getBusiIncome() {
    return busiIncome;
  }
  
  public void setBusiIncome(double busiIncome) {
    this.busiIncome = busiIncome;
  }
  
  public double getOilIncome() {
    return oilIncome;
  }
  
  public void setOilIncome(double oilIncome) {
    this.oilIncome = oilIncome;
  }
  
  public double getPunish() {
    return punish;
  }
  
  public void setPunish(double punish) {
    this.punish = punish;
  }
  
  public double getReachIncome() {
    return reachIncome;
  }
  
  public void setReachIncome(double reachIncome) {
    this.reachIncome = reachIncome;
  }
  
  public double getOverlyingIncome() {
    return overlyingIncome;
  }
  
  public void setOverlyingIncome(double overlyingIncome) {
    this.overlyingIncome = overlyingIncome;
  }
  
  public double getAllresult() {
    return allresult;
  }
  
  public void setAllresult(double allresult) {
    this.allresult = allresult;
  }
  
  public FlagEnum getState() {
    return state;
  }
  
  public void setState(FlagEnum state) {
    this.state = state;
  }
  
  public java.lang.String getMonth() {
    return month;
  }
  
  public void setMonth(java.lang.String month) {
    this.month = month;
  }
  
  public MainIncome(SalesMan salesman, String month) {
    super();
    this.salesman = salesman;
    this.month = month;
  }
  
  public MainIncome() {
    super();
  }
  
  /*
   * 重新计算总收入
   */
  public void reSetResult() {
    double result = this.basicSalary + this.busiIncome + this.oilIncome + this.overlyingIncome + this.reachIncome
        - this.punish;
    this.setAllresult(result);
  }
}