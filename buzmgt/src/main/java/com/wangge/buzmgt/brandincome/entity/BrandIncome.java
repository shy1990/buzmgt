package com.wangge.buzmgt.brandincome.entity;

import com.wangge.buzmgt.region.entity.Region;
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

  @Id
  @GenericGenerator(name = "idgen",strategy = "increment")
  @GeneratedValue(generator="idgen")
  @Column(name = "ID")
  private Long id; //主键id

  private String brand;//品牌

  private String model;//型号

  private Date beginTime;//开始时间

  private Date endTime;//结束时间

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;//审核人

  private Double commissions;//提成金额

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Date getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Double getCommissions() {
    return commissions;
  }

  public void setCommissions(Double commissions) {
    this.commissions = commissions;
  }
}
