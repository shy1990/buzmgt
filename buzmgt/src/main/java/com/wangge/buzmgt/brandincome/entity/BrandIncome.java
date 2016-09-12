package com.wangge.buzmgt.brandincome.entity;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.goods.entity.Brand;
import com.wangge.buzmgt.goods.entity.Goods;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.sys.entity.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
  * ClassName: BrandIncome <br/>
  * Reason: TODO 品牌型号收益entity. <br/>
  * date: 2016年9月1日 下午1:6:09 <br/>
  *
  * @author Administrator
  * @version
  * @since JDK 1.8
 */
@Entity
@Table(name = "SYS_BRAND_INCOME")
public class BrandIncome implements Serializable{

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */

  private static final long serialVersionUID = 1L;

  public static enum BrandIncomeStatus {
    BACK("驳回"), WAIT("待审核"), OVER("已审核");
    private String name;

    BrandIncomeStatus(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }
  }

  @Id
  @GenericGenerator(name = "idgen",strategy = "increment")
  @GeneratedValue(generator="idgen")
  @Column(name = "ID")
  private Long id; //主键id

  @OneToOne
  @JoinColumn(name = "MACHINE_TYPE", updatable = false, insertable = false)
  private MachineType machineType; // 机型类别
  @Column(name = "MACHINE_TYPE")
  private String machineTypeId; // 机型类别
  @OneToOne
  @JoinColumn(name = "BRAND_ID", updatable = false, insertable = false)
  private Brand brand; // 品牌ID
  @Column(name = "BRAND_ID")
  private String brandId; // 品牌ID
  @OneToOne
  @JoinColumn(name = "GOOD_ID", updatable = false, insertable = false)
  private Goods good; // 型号ID
  @Column(name = "GOOD_ID")
  private String goodId; // 型号ID

  private Date startDate; // 开始日期

  private Date endDate; // 结束日期

  @ManyToOne
  @JoinColumn(name = "USER_ID", updatable = false, insertable = false)
  private User user;//审核人
  @Column(name = "USER_ID")
  private String auditor;//审核人

  private Float commissions;//提成金额

  private String planId;//方案ID

  private Date createDate;//创建时间

  @Enumerated(EnumType.STRING)
  private BrandIncomeStatus status = BrandIncomeStatus.WAIT; // 审核状态：BACK-驳回，WAIT-待审核，OVER-已审核

  @Enumerated(EnumType.STRING)
  private FlagEnum flag = FlagEnum.NORMAL; // 是否删除：normal-正常，del-删除

  @Transient
  private String useStatus;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public MachineType getMachineType() {
    return machineType;
  }

  public void setMachineType(MachineType machineType) {
    this.machineType = machineType;
  }

  public String getMachineTypeId() {
    return machineTypeId;
  }

  public void setMachineTypeId(String machineTypeId) {
    this.machineTypeId = machineTypeId;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public String getBrandId() {
    return brandId;
  }

  public void setBrandId(String brandId) {
    this.brandId = brandId;
  }

  public Goods getGood() {
    return good;
  }

  public void setGood(Goods good) {
    this.good = good;
  }

  public String getGoodId() {
    return goodId;
  }

  public void setGoodId(String goodId) {
    this.goodId = goodId;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getAuditor() {
    return auditor;
  }

  public void setAuditor(String auditor) {
    this.auditor = auditor;
  }

  public Float getCommissions() {
    return commissions;
  }

  public void setCommissions(Float commissions) {
    this.commissions = commissions;
  }

  public String getPlanId() {
    return planId;
  }

  public void setPlanId(String planId) {
    this.planId = planId;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public BrandIncomeStatus getStatus() {
    return status;
  }

  public void setStatus(BrandIncomeStatus status) {
    this.status = status;
  }

  public FlagEnum getFlag() {
    return flag;
  }

  public void setFlag(FlagEnum flag) {
    this.flag = flag;
  }

  public String getUseStatus() {
    return useStatus;
  }

  public void setUseStatus(String useStatus) {
    this.useStatus = useStatus;
  }

  @Override
  public String toString() {
    return "BrandIncome{" +
            "id=" + id +
            ", machineTypeId='" + machineTypeId + '\'' +
            ", brandId='" + brandId + '\'' +
            ", goodId='" + goodId + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", commissions=" + commissions +
            ", planId='" + planId + '\'' +
            ", createDate=" + createDate +
            ", status=" + status +
            ", flag=" + flag +
            '}';
  }
}
