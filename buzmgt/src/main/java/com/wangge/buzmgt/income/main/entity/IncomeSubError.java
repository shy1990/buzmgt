package com.wangge.buzmgt.income.main.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 记录订单收益计算错误的信息,已备查询处理: <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "sys_income_error_sub")
public class IncomeSubError {
  @SequenceGenerator(name = "idgen", sequenceName = "sys_income_job_seq")
  @GeneratedValue(generator = "idgen", strategy = GenerationType.SEQUENCE)
  @Id
  private Long id;
  // 订单id;业务员id;错误信息;商品ID
  private String orderno, userId, errorInfo, goodId;
  // 错误类型:0 达量设置 1 品牌型号 2 价格区间 3.达量奖励 4叠加奖励
  private Integer type;
  
  private Date newdate;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getOrderno() {
    return orderno;
  }
  
  public void setOrderno(String orderno) {
    this.orderno = orderno;
  }
  
  public String getUserId() {
    return userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getErrorInfo() {
    return errorInfo;
  }
  
  public void setErrorInfo(String errorInfo) {
    this.errorInfo = errorInfo;
  }
  
  public String getGoodId() {
    return goodId;
  }
  
  public void setGoodId(String goodId) {
    this.goodId = goodId;
  }
  
  public Integer getType() {
    return type;
  }
  
  public void setType(Integer type) {
    this.type = type;
  }
  
  public Date getNewdate() {
    return newdate;
  }
  
  public void setNewdate(Date newdate) {
    this.newdate = newdate;
  }
  
  public IncomeSubError(String orderno, String userId, String errorInfo, String goodId, Integer type) {
    super();
    this.orderno = orderno;
    this.userId = userId;
    this.errorInfo = errorInfo;
    this.goodId = goodId;
    this.type = type;
  }
  
}