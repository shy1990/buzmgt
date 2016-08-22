package com.wangge.buzmgt.income.ywsalary.entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * ClassName: BaseSalary <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 1.新增时,2.修改时3.删除时 <br/>
 * date: 2016年8月20日 下午5:18:35 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "sys_income_basicsalay")
public class BaseSalary implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  @Column(name = "USER_ID")
  private String userId;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
  private SalesMan user;
  private Float salary;// 薪资
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date updateDate;// 修改日期
  
  @Enumerated(EnumType.STRING)
  private FlagEnum flag = FlagEnum.NORMAL;// 是否删除 normal-正常,del-删除
  
  public SalesMan getUser() {
    return user;
  }
  
  public void setUser(SalesMan user) {
    this.user = user;
  }
  
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
  
  public Float getSalary() {
    return salary;
  }
  
  public void setSalary(Float salary) {
    this.salary = salary;
  }
  
  public Date getUpdateDate() {
    return updateDate;
  }
  
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
  
  public FlagEnum getFlag() {
    return flag;
  }
  
  public void setFlag(FlagEnum flag) {
    this.flag = flag;
  }
  
  @Override
  public String toString() {
    return "BaseSalary [id=" + id + ", userId=" + userId + ", user=" + user + ", salary=" + salary + ", updateDate="
        + updateDate + "]";
  }
  
}
