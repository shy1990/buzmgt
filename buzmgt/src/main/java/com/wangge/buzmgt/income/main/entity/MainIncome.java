/***********************************************************************
 * Module:  SysIncomeMain.java
 * Author:  yangqc
 * Purpose: Defines the Class SysIncomeMain
 ***********************************************************************/
package com.wangge.buzmgt.income.main.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.teammember.entity.SalesMan;

/** @pdOid 2df595ee-7322-4363-aad1-9e7cfb79c835 */
@Entity
@Table(name = "sys_income_main")
public class MainIncome {
  /** @pdOid 08793dc7-7b0c-45cf-9e6e-4cb30870c2f9 */
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  public Long id;
  /**
   * 业务员id
   * 
   * @pdOid 00f5a3a0-8082-4edd-84fd-93f1b7aeba43
   */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public SalesMan salesman;
  /**
   * 基本工资
   * 
   * @pdOid d0fb01aa-274f-453b-b882-baf589981587
   */
  public double baeicSalary = 0;
  /**
   * 业务佣金
   * 
   * @pdOid 22050c89-b21f-43dc-ac8b-a342986d2148
   */
  public double busiIncome = 0;
  /**
   * 油补
   * 
   * @pdOid d7e1d4fd-7251-4cc1-be5b-b3d05e239525
   */
  public double oilIcome = 0;
  /**
   * 扣罚
   * 
   * @pdOid 9e89bfeb-5459-4ac2-a393-4c85e827a6c1
   */
  public double punish = 0;
  /**
   * 达量
   * 
   * @pdOid e5adf5b9-78f2-4720-a657-ab951cafc232
   */
  public double reachIncome = 0;
  /**
   * 叠加收入
   * 
   * @pdOid 107d595f-9114-4841-bcfa-9da2c79f4620
   */
  public double overlyingIncome = 0;
  /**
   * 总收入
   * 
   * @pdOid d20b5c01-3f19-44d8-b411-4b475b8fb8e1
   */
  public double allresult = 0;
  /**
   * 状态(0,未审核,1已审核)
   * 
   * @pdOid 4141425b-daa4-487b-8b9a-3e426735dede
   */
  public double state = 0;
  /**
   * 月份
   * 
   * @pdOid e8b1cc12-4b1f-499d-8af1-ec1fafe2202c
   */
  public java.lang.String month;
  
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

  public double getBaeicSalary() {
    return baeicSalary;
  }
  
  public void setBaeicSalary(double baeicSalary) {
    this.baeicSalary = baeicSalary;
  }
  
  public double getBusiIncome() {
    return busiIncome;
  }
  
  public void setBusiIncome(double busiIncome) {
    this.busiIncome = busiIncome;
  }
  
  public double getOilIcome() {
    return oilIcome;
  }
  
  public void setOilIcome(double oilIcome) {
    this.oilIcome = oilIcome;
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
  
  public double getState() {
    return state;
  }
  
  public void setState(double state) {
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
  
}