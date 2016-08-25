package com.wangge.buzmgt.pushmoney.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="SYS_PRICE_SCOPE")
public class PriceScope implements Serializable{

  /**
   * 价格区间
   */
  private static final long serialVersionUID = 1L;
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Integer id;
  private Float min;
  private Float max;
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public Float getMin() {
    return min;
  }
  public void setMin(Float min) {
    this.min = min;
  }
  public Float getMax() {
    return max;
  }
  public void setMax(Float max) {
    this.max = max;
  }
  
  

  
}
