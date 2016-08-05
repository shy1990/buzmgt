package com.wangge.buzmgt.rejection.entity;

import com.wangge.buzmgt.teammember.entity.SalesMan;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * ClassName: Rejection <br/>
 * Reason: TODO 拒收entity. <br/>
 * date: 2016年7月26日 下午1:6:09 <br/>
 *
 * @author Administrator
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name="BIZ_REJECTION")
public class Rejection implements Serializable{
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  @Column(name = "REJECT_ID")
  private Long  id;

  private String frontImgUrl;//正面照片

  private String shopName;//店铺名

  private String orderno;//订单号

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "REJECT_TIME")
  private Date createTime = new Date();//拒收时间

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date arriveTime = new Date();//预计到达时间

  @Enumerated(EnumType.ORDINAL)
  private RejectStatusEnum status;
  
  private String remark;//拒收原因
  
  @Column(name="salesman_id",insertable=false,updatable=false)
  private String salesmanId;
  
  @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
  @JoinColumn(name = "salesman_id")
  private SalesMan salesMan;

  private String trackingno;//物流单号

  @Transient
  private boolean view = false;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFrontImgUrl() {
    return frontImgUrl;
  }

  public void setFrontImgUrl(String frontImgUrl) {
    this.frontImgUrl = frontImgUrl;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getOrderno() {
    return orderno;
  }

  public void setOrderno(String orderno) {
    this.orderno = orderno;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getArriveTime() {
    return arriveTime;
  }

  public void setArriveTime(Date arriveTime) {
    this.arriveTime = arriveTime;
  }

  public RejectStatusEnum getStatus() {
    return status;
  }

  public void setStatus(RejectStatusEnum status) {
    this.status = status;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getSalesmanId() {
    return salesmanId;
  }

  public void setSalesmanId(String salesmanId) {
    this.salesmanId = salesmanId;
  }

  public SalesMan getSalesMan() {
    return salesMan;
  }

  public void setSalesMan(SalesMan salesMan) {
    this.salesMan = salesMan;
  }

  public String getTrackingno() {
    return trackingno;
  }

  public void setTrackingno(String trackingno) {
    this.trackingno = trackingno;
  }

  public boolean getView() {
    return view;
  }

  public void setView(boolean view) {
    this.view = view;
  }
}
