package com.wangge.buzmgt.monthTarget.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * 
  * ClassName: MonthTarget <br/> 
  * Reason: TODO 月指标entity. <br/> 
  * date: 2016年6月25日 下午1:6:09 <br/> 
  * 
  * @author Administrator 
  * @version  
  * @since JDK 1.8
 */
@Entity
@Table(name = "SYS_MONTH_TARGET")
public class MonthTarget implements Serializable{

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GenericGenerator(name = "idgen",strategy = "increment")
  @GeneratedValue(generator="idgen")
  @Column(name = "TARGET_ID")
  private Long id; //主键id
  
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private SalesMan salesman;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "REGION_ID")
  private Region region;
  
  private int orderNum;//提货量
  
  private int merchantNum;//提货商家数量
  
  private int activeNum;//活跃商家
  
  private int matureNum;//成熟商家
  
  @Column(name = "PUBLISH_STATUS")
  private int publishStatus;//0未发布 1已发布
  
  @Column(name = "TARGET_CYCLE")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private Date targetCycle;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SalesMan getSalesman() {
    return salesman;
  }

  public void setSalesman(SalesMan salesman) {
    this.salesman = salesman;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public int getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(int orderNum) {
    this.orderNum = orderNum;
  }

  public int getMerchantNum() {
    return merchantNum;
  }

  public void setMerchantNum(int merchantNum) {
    this.merchantNum = merchantNum;
  }

  public int getActiveNum() {
    return activeNum;
  }

  public void setActiveNum(int activeNum) {
    this.activeNum = activeNum;
  }

  public int getMatureNum() {
    return matureNum;
  }

  public void setMatureNum(int matureNum) {
    this.matureNum = matureNum;
  }

  public int getPublishStatus() {
    return publishStatus;
  }

  public void setPublishStatus(int publishStatus) {
    this.publishStatus = publishStatus;
  }

  public Date getTargetCycle() {
    return targetCycle;
  }

  public void setTargetCycle(Date targetCycle) {
    this.targetCycle = targetCycle;
  }
  
}
