package com.wangge.buzmgt.plan.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.income.main.vo.PlanUserVo;

/**
 * 
 * @ClassName: GroupUser
 * @Description: 分组用户关联分组
 * @author ChenGuop
 * @date 2016年8月24日 下午1:35:29
 *
 */
@Entity
@Table(name = "SYS_GROUPING_USER")
public class GroupUser implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;// 主键
  @Column(name = "GROUP_ID")
  private Long groupId;// 分组Id
  @Column(name = "USER_ID")
  private String userId;// 用户ID
  @OneToOne
  @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
  private PlanUserVo planUser;// 用户ID

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getGroupId() {
    return groupId;
  }

  public void setGroupId(Long groupId) {
    this.groupId = groupId;
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
  
}
