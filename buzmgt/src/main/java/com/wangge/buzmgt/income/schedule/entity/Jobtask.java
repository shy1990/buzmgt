package com.wangge.buzmgt.income.schedule.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 要执行的定义任务表
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "sys_income_job")
public class Jobtask {
  
  @SequenceGenerator(name = "idgen", sequenceName = "sys_income_job_seq")
  @GeneratedValue(generator = "idgen", strategy = GenerationType.SEQUENCE)
  @Id
  private long id;
  // flag:0(未执行),1(已执行);
  /**
   * 0:主方案:0:删除主方案:不在计算收益;TODO 达量叠加是否计算<br/>
   * 1:主方案用户:10:删除主方案用户(需计算),11:删除一个收益主方案的用户(无需重新计算);12:添加一个主方案用户(需重新计算)<br/>
   */
  private Integer flag = 0, type;
  // 受影响的业务id(多个)
  private String salesmanId;
  // 计划ID
  private Long planId, keyid;
  // 执行时间,小于等于今天的任务执行
  @Temporal(TemporalType.DATE)
  private Date exectime;
  @Temporal(TemporalType.TIMESTAMP)
  private Date inserttime = new Date();
  
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  public Integer getFlag() {
    return flag;
  }
  
  public Date getInserttime() {
    return inserttime;
  }
  
  public void setInserttime(Date inserttime) {
    this.inserttime = inserttime;
  }
  
  public Long getKeyid() {
    return keyid;
  }
  
  public void setKeyid(Long keyid) {
    this.keyid = keyid;
  }
  
  public Date getExectime() {
    return exectime;
  }
  
  public void setExectime(Date exectime) {
    this.exectime = exectime;
  }
  
  public void setFlag(Integer flag) {
    this.flag = flag;
  }
  
  public String getSalesmanId() {
    return salesmanId;
  }
  
  public void setSalesmanId(String salesmanId) {
    this.salesmanId = salesmanId;
  }
  
  public Long getPlanId() {
    return planId;
  }
  
  public void setPlanId(Long planId) {
    this.planId = planId;
  }
  
  public Jobtask(Integer type, String salesmanId, Long planId, Date exectime) {
    super();
    this.type = type;
    this.salesmanId = salesmanId;
    this.planId = planId;
    this.exectime = exectime;
  }
  
  public Integer getType() {
    return type;
  }
  
  public void setType(Integer type) {
    this.type = type;
  }
  
  public Jobtask() {
    super();
  }
  
  public Jobtask(Integer type, Long planId, Date exectime) {
    super();
    this.type = type;
    this.planId = planId;
    this.exectime = exectime;
  }

 
  
}
