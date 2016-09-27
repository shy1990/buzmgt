package com.wangge.buzmgt.achieveaward.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.goods.entity.Brand;
import com.wangge.buzmgt.goods.entity.Goods;
import com.wangge.buzmgt.plan.entity.MachineType;

/**
 * 
* @ClassName: AwardGood
* @Description: 达量奖励商品关联表
* @author ChenGuop
* @date 2016年9月14日 上午11:10:46
*
 */
@Entity
@Table(name = "SYS_AWARD_SET_GOODS")
public class AwardGood implements Serializable{

  private static final long serialVersionUID = 1L;
  

  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  @Column(name = "AWARD_ID")
  private Long awardId;
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

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public Long getAwardId() {
    return awardId;
  }
  public void setAwardId(Long awardId) {
    this.awardId = awardId;
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
  @Override
  public String toString() {
    return machineType.getName() + " " +brand.getName() + " " + good.getName();
  }
  
}
