package com.wangge.buzmgt.income.main.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 视图实体,只用于查询
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "view_income_main_plan_user")
public class PlanUserVo {
  @Id
  String userId;
  String truename,rolename,levelName,namepath,plantitle;
  Integer roleId,starsLevel,planId;
  Date regdate;
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
  public String getRolename() {
    return rolename;
  }
  public void setRolename(String rolename) {
    this.rolename = rolename;
  }
  public String getLevelName() {
    return levelName;
  }
  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }
  public String getNamepath() {
    return namepath;
  }
  public void setNamepath(String namepath) {
    this.namepath = namepath;
  }
  public String getPlantitle() {
    return plantitle;
  }
  public void setPlantitle(String plantitle) {
    this.plantitle = plantitle;
  }
  public Integer getRoleId() {
    return roleId;
  }
  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }
  public Integer getStarsLevel() {
    return starsLevel;
  }
  public void setStarsLevel(Integer starsLevel) {
    this.starsLevel = starsLevel;
  }
  public Integer getPlanId() {
    return planId;
  }
  public void setPlanId(Integer planId) {
    this.planId = planId;
  }
  public Date getRegdate() {
    return regdate;
  }
  public void setRegdate(Date regdate) {
    this.regdate = regdate;
  }
  
}
