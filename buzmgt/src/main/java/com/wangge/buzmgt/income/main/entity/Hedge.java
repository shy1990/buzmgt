package com.wangge.buzmgt.income.main.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 售后冲减类; 售后计算未定
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "sys_income_shouhou_hedge")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(name = "calculateGood", procedureName = "income_shouhou_good_cal"),
    @NamedStoredProcedureQuery(name = "calculatehedge", procedureName = "income_shouhou_hedge_cal") })
public class Hedge {
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  // 订单号,sku号,唯一码,规则id
  private String orderno, sku, uniquenumber;
  // 商品名称
  private String goodsName;
  // 到货日期,记录生成日期
  @Temporal(TemporalType.DATE)
  private Date shdate, inserttime;
  // 品牌数量
  private Integer sum;
  public String getOrderno() {
    return orderno;
  }
  
  public void setOrderno(String orderno) {
    this.orderno = orderno;
  }
  
  public String getSku() {
    return sku;
  }
  
  public void setSku(String sku) {
    this.sku = sku;
  }
  
  public String getUniquenumber() {
    return uniquenumber;
  }
  
  public void setUniquenumber(String uniquenumber) {
    this.uniquenumber = uniquenumber;
  }
  
  public String getGoodsName() {
    return goodsName;
  }
  
  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }
  
  public Date getShdate() {
    return shdate;
  }
  
  public void setShdate(Date shdate) {
    this.shdate = shdate;
  }
  
  public Integer getSum() {
    return sum;
  }
  
  public void setSum(Integer sum) {
    this.sum = sum;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }

  
  public Hedge(String orderno, String sku, String goodsName, Integer sum, Date shdate, String uniquenumber) {
    super();
    this.orderno = orderno;
    this.sku = sku;
    this.uniquenumber = uniquenumber;
    this.goodsName = goodsName;
    this.shdate = shdate;
    this.sum = sum;
  }
  
  public Hedge() {
    super();
  }
  
  
  public Date getInserttime() {
    return inserttime;
  }
  
  public void setInserttime(Date inserttime) {
    this.inserttime = inserttime;
  }
  
}
