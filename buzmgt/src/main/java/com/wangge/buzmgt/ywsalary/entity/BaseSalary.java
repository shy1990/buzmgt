package com.wangge.buzmgt.ywsalary.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangge.buzmgt.teammember.entity.SalesMan;



/**
 * 
 * @author thor
 *
 */
@Entity
@Table(name="SYS_BASE_SALARY")
public class BaseSalary implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  

  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Integer id ; //订单id
  @Column(name="USER_ID")
  private String userId;
  @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
  @JoinColumn(name="USER_ID",insertable=false,updatable=false)
  private SalesMan user;
  private String name ; //姓名
  private Float salary;//薪资

  @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
  private Date updateDate;//修改日期

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
 
  
  

}
