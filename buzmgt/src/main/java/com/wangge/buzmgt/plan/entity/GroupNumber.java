package com.wangge.buzmgt.plan.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.common.PlanTypeEnum;

@Entity
@Table(name="SYS_GROUPING_NUMBER")
public class GroupNumber implements Serializable{

  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;//主键
  private Integer numberFirstAdd;//第一阶段增加量
  private Integer numberSecondAdd;//第一阶段增加量
  private Integer numberThirdAdd;//第一阶段增加量
  @Enumerated(EnumType.STRING)
  private PlanTypeEnum type;//type：ACHIEVE-达量，OVERLAY-叠加，REWARD-奖励
  private String groupName;//分组Grouping：A，B
  @Enumerated(EnumType.STRING)
  private FlagEnum flag = FlagEnum.NORMAL;//是否删除：normal-正常，del-删除
  
  @Transient
  @OneToMany(cascade=CascadeType.ALL)
  @JoinColumn(name="id",referencedColumnName="GROUP_ID")
  private List<GroupUser> groupUsers;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getNumberFirstAdd() {
    return numberFirstAdd;
  }

  public void setNumberFirstAdd(Integer numberFirstAdd) {
    this.numberFirstAdd = numberFirstAdd;
  }

  public Integer getNumberSecondAdd() {
    return numberSecondAdd;
  }

  public void setNumberSecondAdd(Integer numberSecondAdd) {
    this.numberSecondAdd = numberSecondAdd;
  }

  public Integer getNumberThirdAdd() {
    return numberThirdAdd;
  }

  public void setNumberThirdAdd(Integer numberThirdAdd) {
    this.numberThirdAdd = numberThirdAdd;
  }

  public PlanTypeEnum getType() {
    return type;
  }

  public void setType(PlanTypeEnum type) {
    this.type = type;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public FlagEnum getFlag() {
    return flag;
  }

  public void setFlag(FlagEnum flag) {
    this.flag = flag;
  }

  public List<GroupUser> getGroupUsers() {
    return groupUsers;
  }

  public void setGroupUsers(List<GroupUser> groupUsers) {
    this.groupUsers = groupUsers;
  }

  @Override
  public String toString() {
    return "GroupingNumber [id=" + id + ", numberFirstAdd=" + numberFirstAdd + ", numberSecondAdd=" + numberSecondAdd
        + ", numberThirdAdd=" + numberThirdAdd + ", type=" + type + ", groupName=" + groupName + "]";
  }
  
}
