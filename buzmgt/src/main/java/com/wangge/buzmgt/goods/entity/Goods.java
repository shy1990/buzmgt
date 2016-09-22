package com.wangge.buzmgt.goods.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
* @ClassName: Goods
* @Description: 商品表
* @author ChenGuop
* @date 2016年8月26日 下午2:12:33
*
 */
@Entity
@Table(name="SYS_GOODS")
public class Goods implements Serializable{
  
  private static final long serialVersionUID = 1L;
  @Id
  private String  id  ; //  主键ID
  private String  name  ; //  商品名称
  private String  brandId  ; //  品牌ID，关联SJ_TB_BRAND
  private String  catId  ; //  商品的类别ID，关联SJ_TB_CAT
  private String  machineType  ; //机型
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getBrandId() {
    return brandId;
  }
  public void setBrandId(String brandId) {
    this.brandId = brandId;
  }
  public String getCatId() {
    return catId;
  }
  public void setCatId(String catId) {
    this.catId = catId;
  }
  public String getMachineType() {
    return machineType;
  }
  public void setMachineType(String machineType) {
    this.machineType = machineType;
  }
  @Override
  public String toString() {
    return "Goods [id=" + id + ", name=" + name + ", brandId=" + brandId + ", machineType=" + machineType + ", catId=" + catId + "]";
  }
  
}
