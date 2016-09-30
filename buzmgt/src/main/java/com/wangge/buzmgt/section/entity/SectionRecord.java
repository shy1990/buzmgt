package com.wangge.buzmgt.section.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by joe on 16-9-28.
 * 区间收益记录
 */
@Entity
@Table(name = "SYS_SECTION_RECORD")
public class SectionRecord {

    @Id
    @GenericGenerator(name = "idgen", strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "RECORD_ID")
    private Long id;//标识

    private String orderNo;//订单id(订单详情)

    private String salesmanId;//业务员id

    private String goodsId;//业务员区域id

    private Date payTime;//付款时间

    @Temporal(TemporalType.DATE)
    private Date computeTime = new Date();//计算时间

    private Double percentage;//提成

    private String planId;//方案id

    private Long sectionId;//叠加方案id

    private Long priceRangeId;//小区间id

    private Integer orderflag = 0,// 订单状态(已出库:0,已付款:1);
    // 记录状态(默认有效;0,1);
    flag = 0,
    // 已被使用(默认没被使用0,1)
    used = 0;

    private Integer num;//订单号

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getComputeTime() {
        return computeTime;
    }

    public void setComputeTime(Date computeTime) {
        this.computeTime = computeTime;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getPriceRangeId() {
        return priceRangeId;
    }

    public void setPriceRangeId(Long priceRangeId) {
        this.priceRangeId = priceRangeId;
    }

    public Integer getOrderflag() {
        return orderflag;
    }

    public void setOrderflag(Integer orderflag) {
        this.orderflag = orderflag;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "SectionRecord{" +
                "id=" + id +
                ", orderNo='" + orderNo + '\'' +
                ", salesmanId='" + salesmanId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", payTime=" + payTime +
                ", computeTime=" + computeTime +
                ", percentage=" + percentage +
                ", planId='" + planId + '\'' +
                ", sectionId=" + sectionId +
                ", priceRangeId=" + priceRangeId +
                ", orderflag=" + orderflag +
                ", flag=" + flag +
                ", used=" + used +
                ", num=" + num +
                '}';
    }
}
