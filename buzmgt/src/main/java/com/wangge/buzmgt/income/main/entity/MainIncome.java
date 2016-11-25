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

import com.wangge.buzmgt.common.CheckedEnum;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * 收入明细表 date: 2016年9月3日 下午4:58:31 <br/>
 * 实时计算:收现金和刷pos都是单人;<br/>
 * 如何避免并发<br/>
 * 收益计算顺序:5号导完上个月售后,6号计算叠加,7号计算达量,8整合上月售后,9.整合所有工资并展示.<br/>
 * 9号到15号,主方案无法删除不能修改,人员无法修改;7-15期间,可以进行审核工作.<br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@NamedStoredProcedureQueries({ @NamedStoredProcedureQuery(name = "initMonth", procedureName = "init_Income_EveMonth"),
    @NamedStoredProcedureQuery(name = "initOilCost", procedureName = "oil_daily_calculate_prod"),
    @NamedStoredProcedureQuery(name = "basicSalayinitMonth", procedureName = "income_month_busisal"),
    @NamedStoredProcedureQuery(name = "basicSalayinitdaily", procedureName = "income_daily_busiSal")})
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
  private Double basicSalary = 0D;
  /**
   * 业务佣金
   * 
   */
  private Double busiIncome = 0D;
  /**
   * 油补
   * 
   */
  private Double oilIncome = 0D;
  /**
   * 扣罚
   * 
   */
  private Double punish = 0D;
  /**
   * 达量
   * 
   */
  private Double reachIncome = 0D;
  /**
   * 叠加收入
   * 
   */
  private Double overlyingIncome = 0D;
  // 售后冲减
  private Double hedgecut = 0D;
  /**
   * 总收入
   */
  private Double allresult = 0D;
  //实际业务收益
  private Double  rbusiSal=0D;
  private Integer busiShouNums =0;
  private Integer busiNums =0;
  /**
   * 状态(0,未审核,1已审核)
   */
  @Enumerated(EnumType.ORDINAL)
  private CheckedEnum state = CheckedEnum.UNCHECKED;
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
  
  public Double getBasicSalary() {
    return basicSalary;
  }
  
  public void setBasicSalary(Double basicSalary) {
    this.basicSalary = basicSalary;
  }
  
  public Double getBusiIncome() {
    return busiIncome;
  }
  
  public void setBusiIncome(Double busiIncome) {
    this.busiIncome = busiIncome;
  }
  
  public Double getOilIncome() {
    return oilIncome;
  }
  
  public void setOilIncome(Double oilIncome) {
    this.oilIncome = oilIncome;
  }
  
  public Double getPunish() {
    return punish;
  }
  
  public void setPunish(Double punish) {
    this.punish = punish;
  }
  
  public Double getReachIncome() {
    return reachIncome;
  }
  
  public void setReachIncome(Double reachIncome) {
    this.reachIncome = reachIncome;
  }
  
  public Double getOverlyingIncome() {
    return overlyingIncome;
  }
  
  public void setOverlyingIncome(Double overlyingIncome) {
    this.overlyingIncome = overlyingIncome;
  }
  
  public Double getAllresult() {
    return allresult;
  }
  
  public Double getHedgecut() {
    return hedgecut;
  }
  
  public void setHedgecut(Double hedgecut) {
    this.hedgecut = hedgecut;
  }
  
  public void setAllresult(Double allresult) {
    this.allresult = allresult;
  }
  
  public CheckedEnum getState() {
    return state;
  }
  
  public void setState(CheckedEnum state) {
    this.state = state;
  }
  
  public java.lang.String getMonth() {
    return month;
  }
  
  public void setMonth(java.lang.String month) {
    this.month = month;
  }
  
  public Double getRbusiSal() {
    return rbusiSal;
  }

  public void setRbusiSal(Double rbusiSal) {
    this.rbusiSal = rbusiSal;
  }

  public MainIncome(SalesMan salesman, String month, Double basicSalaray) {
    super();
    this.salesman = salesman;
    this.month = month;
    this.basicSalary = basicSalaray;
  }
  
  public Integer getBusiShouNums() {
    return busiShouNums;
  }

  public void setBusiShouNums(Integer busiShouNums) {
    this.busiShouNums = busiShouNums;
  }

  public Integer getBusiNums() {
    return busiNums;
  }

  public void setBusiNums(Integer busiNums) {
    this.busiNums = busiNums;
  }

  public MainIncome() {
    super();
  }
  
  /*
   * 重新计算总收入
   */
  public void reSetResult() {
    Double result = this.basicSalary + this.busiIncome + this.oilIncome + this.overlyingIncome + this.reachIncome
        - this.punish;
    this.setAllresult(result);
  }

  @Override
  public String toString() {
    return "MainIncome [id=" + id + ", salesman=" + salesman + ", basicSalary=" + basicSalary + ", busiIncome="
        + busiIncome + ", oilIncome=" + oilIncome + ", punish=" + punish + ", reachIncome=" + reachIncome
        + ", overlyingIncome=" + overlyingIncome + ", hedgecut=" + hedgecut + ", allresult=" + allresult + ", rbusiSal="
        + rbusiSal + ", busiShouNums=" + busiShouNums + ", busiNums=" + busiNums + ", state=" + state + ", month="
        + month + "]";
  }
  
 
}
