package com.wangge.buzmgt.cash.entity;

import javax.persistence.*;
import java.io.Serializable;



/**
 * 
 * @author ydhl
 *
 */
@Entity
@Table(name="SYS_WATER_ORDER_DETAILS")
public class WaterOrderDetail implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Id
  @SequenceGenerator(name = "idgen")
  @GeneratedValue(generator = "idgen", strategy = GenerationType.SEQUENCE)
  private Integer id;
  @Column(name="SERIAL_NO")
  private String serialNo ; //流水单号
  
  @Column(name = "cash_id")
  private Long cashId; //订单id
  
  @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
  @JoinColumn(name = "cash_id",insertable=false,updatable=false)
  private Cash cash; //订单号
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getSerialNo() {
    return serialNo;
  }
  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
  }
  public Long getCashId() {
    return cashId;
  }
  public void setCashId(Long cashId) {
    this.cashId = cashId;
  }
  public Cash getCash() {
    return cash;
  }
  public void setCash(Cash cash) {
    this.cash = cash;
  }
  
  

  

}
