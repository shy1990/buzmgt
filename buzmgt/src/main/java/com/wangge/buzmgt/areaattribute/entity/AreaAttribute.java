package com.wangge.buzmgt.areaattribute.entity;

import com.wangge.buzmgt.region.entity.Region;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 *
  * ClassName: AreaAttribute <br/>
  * Reason: TODO 区域属性entity. <br/>
  * date: 2016年8月30日 下午1:6:09 <br/>
  *
  * @author Administrator
  * @version
  * @since JDK 1.8
 */
@Entity
@Table(name = "SYS_AREA_ATTRIBUTE")
public class AreaAttribute implements Serializable{

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */

  private static final long serialVersionUID = 1L;

  public enum PlanType {
    PRICERANGE("价格区间"),BRANDMODEL("品牌型号");

    private String name;

    private PlanType(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  @Id
  @GenericGenerator(name = "idgen",strategy = "increment")
  @GeneratedValue(generator="idgen")
  @Column(name = "ID")
  private Long id; //主键id

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REGION_ID",updatable = false, insertable = false)
  private Region region;//提成区域

  @Column(name = "REGION_ID")
  private String regionId;//提成区域字段

  private Double commissions;//提成金额

  private Long ruleId;//方案ID

  @Enumerated(EnumType.STRING)
  @Column(name = "PLAN_TYPE")
  private PlanType type;//方案类型

  private int disabled;//0未删除 1已删除

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public Double getCommissions() {
    return commissions;
  }

  public void setCommissions(Double commissions) {
    this.commissions = commissions;
  }

  public Long getRuleId() {
    return ruleId;
  }

  public void setRuleId(Long ruleId) {
    this.ruleId = ruleId;
  }

  public PlanType getType() {
    return type;
  }

  public void setType(PlanType type) {
    this.type = type;
  }

  public int getDisabled() {
    return disabled;
  }

  public void setDisabled(int disabled) {
    this.disabled = disabled;
  }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "AreaAttribute{" +
                "id=" + id +
                ", region=" + region +
                ", regionId='" + regionId + '\'' +
                ", commissions=" + commissions +
                ", ruleId=" + ruleId +
                ", type=" + type +
                ", disabled=" + disabled +
                '}';
    }
}
