package com.wangge.buzmgt.achieve.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.plan.entity.MachineType;

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
public class Achieve implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long achieveId; // 主键
  @JoinColumn(name = "MARCH_TYPE")
  @OneToOne
  private MachineType machineType; // 机型类别
  private String brandId; // 品牌ID
  private String goodId; // 型号ID
  private Integer numberFirst; // 任务量一
  private Integer numberSecond; // 任务量2
  private Integer numberThird; // 任务量3
  private Date startDate; // 开始日期
  private Date endDate; // 结束日期
  private Date issuingDate; // 提成发放日期
  private String auditor; // 审核人
  private String remark; // 备注
  private Date createDate; // 创建日期
  private String flag; // 是否删除：normal-正常，del-删除
  private String status; // 审核状态：BACK-驳回，WAIT-待审核，OVER-已审核

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

  public String getBrandId() {
    return brandId;
  }

  public void setBrandId(String brandId) {
    this.brandId = brandId;
  }

  public String getGoodId() {
    return goodId;
  }

  public void setGoodId(String goodId) {
    this.goodId = goodId;
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

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return "Achieve [achieveId=" + achieveId + ", machineType=" + machineType.getName() + ", brandId=" + brandId + ", goodId="
        + goodId + ", numberFirst=" + numberFirst + ", numberSecond=" + numberSecond + ", numberThird=" + numberThird
        + ", startDate=" + startDate + ", endDate=" + endDate + ", issuingDate=" + issuingDate + ", auditor=" + auditor
        + ", remark=" + remark + ", createDate=" + createDate + ", status=" + status + "]";
  }

}
