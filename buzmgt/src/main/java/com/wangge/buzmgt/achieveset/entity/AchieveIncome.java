package com.wangge.buzmgt.achieveset.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;

/**
 * 
* @ClassName: AchieveIncome
* @Description: 达量收益实体
* @author ChenGuop
* @date 2016年9月22日 下午6:24:23
*
 */
@Entity
@Table(name = "SYS_ACHIEVE_AWARD_INCOME" )
public class AchieveIncome implements Serializable{

  private static final long serialVersionUID = 1L;
  
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long  id  ;// 主键
  @Column(name = "USER_ID")
  private String  userId ;// 业务员Id
  @OneToOne
  @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
  private PlanUserVo planUser;// 用户ID
  
  @Column(name = "ACHIEVE_ID")
  private Long  achieveId  ;// 达量设置Id
  @OneToOne
  @JoinColumn(name = "ACHIEVE_ID", insertable = false, updatable = false)
  private Achieve achieve;
  private String  goodId ;// 商品Id
  private String  orderNo  ;// 订单号
  private Integer num ;// 数量
  private Float money ;// 金额
  private String  status  ;// 状态
  private Date  createDate ;// 创建日期
  private FlagEnum  flag = FlagEnum.NORMAL ;// 是否删除：normal-正常，del-删除
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
  public PlanUserVo getPlanUser() {
    return planUser;
  }
  public void setPlanUser(PlanUserVo planUser) {
    this.planUser = planUser;
  }
  public Long getAchieveId() {
    return achieveId;
  }
  public void setAchieveId(Long achieveId) {
    this.achieveId = achieveId;
  }
  public Achieve getAchieve() {
    return achieve;
  }
  public void setAchieve(Achieve achieve) {
    this.achieve = achieve;
  }
  public String getGoodId() {
    return goodId;
  }
  public void setGoodId(String goodId) {
    this.goodId = goodId;
  }
  public String getOrderNo() {
    return orderNo;
  }
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
  public Integer getNum() {
    return num;
  }
  public void setNum(Integer num) {
    this.num = num;
  }
  public Float getMoney() {
    return money;
  }
  public void setMoney(Float money) {
    this.money = money;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public Date getCreateDate() {
    return createDate;
  }
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  public FlagEnum getFlag() {
    return flag;
  }
  public void setFlag(FlagEnum flag) {
    this.flag = flag;
  }
  @Override
  public String toString() {
    return "AchieveIncome [id=" + id + ", userId=" + userId + ", planUser=" + planUser + ", achieveId=" + achieveId
        + ", achieve=" + achieve + ", goodId=" + goodId + ", orderNo=" + orderNo + ", num=" + num + ", money=" + money
        + ", status=" + status + ", createDate=" + createDate + ", flag=" + flag + "]";
  }
  
}
