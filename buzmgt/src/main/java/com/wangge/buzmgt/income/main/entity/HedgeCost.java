package com.wangge.buzmgt.income.main.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 售后冲减金额 <br/>
 * date: 2016年10月11日 上午9:42:20 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "sys_income_shouhou_cost")
public class HedgeCost {
  // 主键
  @SequenceGenerator(name = "idgen", sequenceName = "sys_income_shouhou_seq")
  @GeneratedValue(generator = "idgen", strategy = GenerationType.SEQUENCE)
  @Id
  private Long id;
  // 冲减表的id,外键; 规则id
  private Long hedgeId, ruleId;
  // 收益规则类型0价格区间1:品牌2达量3叠加 4达量奖励;
  private Integer ruletype;
  // 业务员id,商品id
  private String userId, goodsId;
  // 支付时间,售后收货时间
  private Date paytime, accepttime;
  // 冲销金额
  private float cost;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getHedgeId() {
    return hedgeId;
  }
  
  public void setHedgeId(Long hedgeId) {
    this.hedgeId = hedgeId;
  }
  
  public Long getRuleId() {
    return ruleId;
  }
  
  public void setRuleId(Long ruleId) {
    this.ruleId = ruleId;
  }
  
  public Integer getRuletype() {
    return ruletype;
  }
  
  public void setRuletype(Integer ruletype) {
    this.ruletype = ruletype;
  }
  
  public String getUserId() {
    return userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getGoodsId() {
    return goodsId;
  }
  
  public void setGoodsId(String goodsId) {
    this.goodsId = goodsId;
  }
  
  public Date getPaytime() {
    return paytime;
  }
  
  public void setPaytime(Date paytime) {
    this.paytime = paytime;
  }
  
  public Date getAccepttime() {
    return accepttime;
  }
  
  public void setAccepttime(Date accepttime) {
    this.accepttime = accepttime;
  }
  
  public float getCost() {
    return cost;
  }
  
  public void setCost(float cost) {
    this.cost = cost;
  }
  
  public HedgeCost(Long hedgeId, Long ruleId, Integer ruletype, String userId, String goodsId, Date paytime,
      Date accepttime, float cost) {
    super();
    this.hedgeId = hedgeId;
    this.ruleId = ruleId;
    this.ruletype = ruletype;
    this.userId = userId;
    this.goodsId = goodsId;
    this.paytime = paytime;
    this.accepttime = accepttime;
    this.cost = cost;
  }
  
}
