package com.wangge.buzmgt.income.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.sys.entity.User;

/** 
  * ClassName: BasicSalary <br/> 
  * Function: 业务员基础薪资表. <br/> 
  * Reason:  1.入职时间计算工资.
  * 2.离职时间计算工资 
  * 3.修改工资时如何计算 
  * <br/> 
  * date: 2016年8月20日 下午3:13:57 <br/> 
  * 
  * @author yangqc 
  * @version  
  * @since JDK 1.8 
  */  
@Entity
@Table(name = "sys_income_basicsalay")
public class BasicSalary {
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private long id;
  
  @JoinColumn(name = "user_id")
  @OneToOne(fetch = FetchType.LAZY)
  private User user;
  
  private Double salary;
  //状态(0在用,1废弃)
  private Integer state;
  private Date mtime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Double getSalary() {
    return salary;
  }

  public void setSalary(Double salary) {
    this.salary = salary;
  }

  public Date getMtime() {
    return mtime;
  }

  public void setMtime(Date mtime) {
    this.mtime = mtime;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }
  
}
