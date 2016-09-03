/***********************************************************************
 * Module:  SysIncomeMain.java
 * Author:  yangqc
 * Purpose: Defines the Class SysIncomeMain
 ***********************************************************************/
package com.wangge.buzmgt.income.main.vo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/** @pdOid 2df595ee-7322-4363-aad1-9e7cfb79c835 */
@Entity
@Table(name = "view_income_main")
public class MainIncomeVo {
  /** @pdOid 08793dc7-7b0c-45cf-9e6e-4cb30870c2f9 */
  @Id
  private Long id;
  private Long roleId;
  /**
   * 业务员id
   * 
   * @pdOid 00f5a3a0-8082-4edd-84fd-93f1b7aeba43
   */
  //用户id,业务名称,区域路径,角色名称
  private String  userId,truename,namepath,rolename;
  /**
   * 基本工资
   * 
   * @pdOid d0fb01aa-274f-453b-b882-baf589981587
   */
  private double basicSalary = 0;
  /**
   * 业务佣金
   * 
   * @pdOid 22050c89-b21f-43dc-ac8b-a342986d2148
   */
  private double busiIncome = 0;
  /**
   * 油补
   * 
   * @pdOid d7e1d4fd-7251-4cc1-be5b-b3d05e239525
   */
  private double oilIncome = 0;
  /**
   * 扣罚
   * 
   * @pdOid 9e89bfeb-5459-4ac2-a393-4c85e827a6c1
   */
  private double punish = 0;
  /**
   * 达量
   * 
   * @pdOid e5adf5b9-78f2-4720-a657-ab951cafc232
   */
  private double reachIncome = 0;
  /**
   * 叠加收入
   * 
   * @pdOid 107d595f-9114-4841-bcfa-9da2c79f4620
   */
  private double overlyingIncome = 0;
  /**
   * 总收入
   * 
   * @pdOid d20b5c01-3f19-44d8-b411-4b475b8fb8e1
   */
  private double allresult = 0;
  /**
   * 状态(0,未审核,1已审核)
   * 
   * @pdOid 4141425b-daa4-487b-8b9a-3e426735dede
   */
  @Enumerated(EnumType.ORDINAL)
  private FlagEnum state = FlagEnum.NORMAL;
  
  /**
   * 月份
   * 
   * @pdOid e8b1cc12-4b1f-499d-8af1-ec1fafe2202c
   */
  private String month;
  
  

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTruename() {
    return truename;
  }

  public void setTruename(String truename) {
    this.truename = truename;
  }

  public String getNamepath() {
    return namepath;
  }

  public void setNamepath(String namepath) {
    this.namepath = namepath;
  }

  public String getRolename() {
    return rolename;
  }

  public void setRolename(String rolename) {
    this.rolename = rolename;
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

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public void setMonth(java.lang.String month) {
    this.month = month;
  }
  
}