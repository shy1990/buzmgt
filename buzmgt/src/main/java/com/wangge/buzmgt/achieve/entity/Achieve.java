package com.wangge.buzmgt.achieve.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.common.PlanTypeEnum;
import com.wangge.buzmgt.goods.entity.Brand;
import com.wangge.buzmgt.goods.entity.Goods;
import com.wangge.buzmgt.plan.entity.GroupNumber;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.entity.RewardPunishRule;

/**
 * 
 * @ClassName: Achieve
 * @Description: 达量设置
 * @author ChenGuop
 * @date 2016年8月23日 下午6:52:22
 *
 */
@Entity
@Table(name = "SYS_ACHIEVE_NUMBER_SET")
//@NamedEntityGraph(
//    name = "graph.Achieve",
//    
//    attributeNodes={
//        @NamedAttributeNode(value="machineType"),
//        @NamedAttributeNode(value="brand"),
//        @NamedAttributeNode(value="good"),
//        @NamedAttributeNode(value="rewardPunishRules"),
//        @NamedAttributeNode(value="groupNumbers")
//    }
//)
public class Achieve implements Serializable {

  private static final long serialVersionUID = 1L;
  
  public static enum AchieveStatusEnum{
    BACK("驳回"),WAIT("待审核"),OVER("已审核");    
    private String name;
    AchieveStatusEnum(String name){
      this.name=name;
    }
    public String getName(){
      return this.name;
    }
  }

  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long achieveId; // 主键
  @OneToOne
  @JoinColumn(name = "MACHINE_TYPE")
  private MachineType machineType; // 机型类别
  
  @OneToOne
  @JoinColumn(name = "BRAND_ID")
  private Brand brand; // 品牌ID
  @OneToOne
  @JoinColumn(name = "GOOD_ID")
  private Goods good; // 型号ID
  private Integer numberFirst; // 任务量一
  private Integer numberSecond; // 任务量2
  private Integer numberThird; // 任务量3
  private Date startDate; // 开始日期
  private Date endDate; // 结束日期
  private Date issuingDate; // 提成发放日期
  private String auditor; // 审核人
  private String remark; // 备注
  private Date createDate; // 创建日期
  @Enumerated(EnumType.STRING)
  private FlagEnum flag = FlagEnum.NORMAL; // 是否删除：normal-正常，del-删除
  @Enumerated(EnumType.STRING)
  private AchieveStatusEnum status; // 审核状态：BACK-驳回，WAIT-待审核，OVER-已审核
  private String planId;
  
  @OneToMany(cascade=CascadeType.ALL)
  @JoinTable(name="SYS_ACHIEVE_SET_RULE",
     joinColumns=@JoinColumn(name="SYS_ACHIEVE_ID"),
     inverseJoinColumns=@JoinColumn(name="RULE_ID"))
  private List<RewardPunishRule> rewardPunishRules;//奖罚规则
  
  @OneToMany(cascade=CascadeType.ALL)
  @JoinTable(name="SYS_ACHIEVE_SET_GROUP",
      joinColumns=@JoinColumn(name="SYS_ACHIEVE_ID"),
      inverseJoinColumns=@JoinColumn(name="GROUPING_ID"))
  private List<GroupNumber> groupNumbers; //人员分组设置

  public Long getAchieveId() {
    return achieveId;
  }

  public void setAchieveId(Long achieveId) {
    this.achieveId = achieveId;
  }

  public MachineType getMachineType() {
    return machineType;
  }

  public void setMachineType(MachineType machineType) {
    this.machineType = machineType;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public Goods getGood() {
    return good;
  }

  public void setGood(Goods good) {
    this.good = good;
  }

  public Integer getNumberFirst() {
    return numberFirst;
  }

  public void setNumberFirst(Integer numberFirst) {
    this.numberFirst = numberFirst;
  }

  public Integer getNumberSecond() {
    return numberSecond;
  }

  public void setNumberSecond(Integer numberSecond) {
    this.numberSecond = numberSecond;
  }

  public Integer getNumberThird() {
    return numberThird;
  }

  public void setNumberThird(Integer numberThird) {
    this.numberThird = numberThird;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Date getIssuingDate() {
    return issuingDate;
  }

  public void setIssuingDate(Date issuingDate) {
    this.issuingDate = issuingDate;
  }

  public String getAuditor() {
    return auditor;
  }

  public void setAuditor(String auditor) {
    this.auditor = auditor;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
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

  public AchieveStatusEnum getStatus() {
    return status;
  }

  public void setStatus(AchieveStatusEnum status) {
    this.status = status;
  }

  public String getPlanId() {
    return planId;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }

  public List<RewardPunishRule> getRewardPunishRules() {
    return rewardPunishRules;
  }

  public void setRewardPunishRules(List<RewardPunishRule> rewardPunishRules) {
    this.rewardPunishRules = rewardPunishRules;
  }

  public List<GroupNumber> getGroupNumbers() {
    return groupNumbers;
  }

  public void setGroupNumbers(List<GroupNumber> groupNumbers) {
    this.groupNumbers = groupNumbers;
  }

  @Override
  public String toString() {
    return "Achieve [achieveId=" + achieveId + ", machineType=" + machineType.getName() + ", numberFirst=" + numberFirst + ", numberSecond=" + numberSecond + ", numberThird=" + numberThird
        + ", startDate=" + startDate + ", endDate=" + endDate + ", issuingDate=" + issuingDate + ", auditor=" + auditor
        + ", remark=" + remark + ", createDate=" + createDate + ", status=" + status + "]";
  }

}