package com.wangge.buzmgt.achieve.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.common.PlanTypeEnum;

/**
 * 
* @ClassName: RewardPunishRule
* @Description: 奖罚规则设置
* @author ChenGuop
* @date 2016年8月24日 下午1:52:13
*
 */
@Entity
@Table(name="SYS_REWARD_PUNISH_RULE")
public class RewardPunishRule implements Serializable{

  private static final long serialVersionUID = 1L;
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  private Integer min;
  private Integer max;
  private Float money;
  @Enumerated(EnumType.STRING)
  private PlanTypeEnum type;
  private FlagEnum flag;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Integer getMin() {
    return min;
  }
  public void setMin(Integer min) {
    this.min = min;
  }
  public Integer getMax() {
    return max;
  }
  public void setMax(Integer max) {
    this.max = max;
  }
  public Float getMoney() {
    return money;
  }
  public void setMoney(Float money) {
    this.money = money;
  }
  public PlanTypeEnum getType() {
    return type;
  }
  public void setType(PlanTypeEnum type) {
    this.type = type;
  }
  public FlagEnum getFlag() {
    return flag;
  }
  public void setFlag(FlagEnum flag) {
    this.flag = flag;
  }
  @Override
  public String toString() {
    return "RewardPunishRule [id=" + id + ", min=" + min + ", max=" + max + ", money=" + money + ", type=" + type
        + ", flag=" + flag + "]";
  }
}
