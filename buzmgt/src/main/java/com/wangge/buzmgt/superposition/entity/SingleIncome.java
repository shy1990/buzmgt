package com.wangge.buzmgt.superposition.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by joe on 16-10-15.
 * 一单达量收益记录
 */
@Entity
@Table(name = "SYS_SINGLE_RECORD")
public class SingleIncome {
    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "SINGLE_ID")
    private Long id;
    private String orderId;//订单id
    private String userId;//用户id
    private Date payTime;//支付时间
    private Integer offsetNums;//一单符合规则的提货数量
    private Long superId;//叠加达量设置规则
    private Long planId;//方案id
    private Float amount;//收益
    private String goodsId;//商品id
    private String status;//状态值:0-总收益已计算(原始记录),1-售后冲减数量(没有计算之前的).2-售后冲减数量(已经计算后的).3-计算后冲减之后提成(4-表示已过期)
    private Integer record;//一单符合规则的提货数量

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getOffsetNums() {
        return offsetNums;
    }

    public void setOffsetNums(Integer offsetNums) {
        this.offsetNums = offsetNums;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "SingleIncome{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", payTime=" + payTime +
                ", offsetNums=" + offsetNums +
                ", superId=" + superId +
                ", planId=" + planId +
                ", amount=" + amount +
                ", goodsId='" + goodsId + '\'' +
                ", status='" + status + '\'' +
                ", record=" + record +
                '}';
    }
}
