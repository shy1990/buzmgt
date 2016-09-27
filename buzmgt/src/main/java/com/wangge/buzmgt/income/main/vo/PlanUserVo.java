package com.wangge.buzmgt.income.main.vo;

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
  private String userId;
  private String truename, rolename, levelName, namepath, plantitle;
  private Integer roleId, starsLevel, rindex;
  private Date regdate;
  private Long planId,regionId;
  
  public String getUserId() {
    return userId;
  }
  
  public Integer getRindex() {
    return rindex;
  }
  
  public void setRindex(Integer rindex) {
    this.rindex = rindex;
  }
  
  public Long getRegionId() {
    return regionId;
  }

  public void setRegionId(Long regionId) {
    this.regionId = regionId;
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
  
  public Long getPlanId() {
    return planId;
  }
  
  public void setPlanId(Long planId) {
    this.planId = planId;
  }
  
  public Date getRegdate() {
    return regdate;
  }
  
  public void setRegdate(Date regdate) {
    this.regdate = regdate;
  }
  
}
