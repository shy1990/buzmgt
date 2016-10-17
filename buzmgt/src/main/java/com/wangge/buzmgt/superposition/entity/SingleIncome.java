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
    private Integer nums;//一单符合规则的提货数量
    private Long superId;//叠加达量设置规则
    private Long planId;//方案id
    private Float amount;//收益

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

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
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

    @Override
    public String toString() {
        return "SingleIncome{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", payTime=" + payTime +
                ", nums=" + nums +
                ", superId=" + superId +
                ", planId=" + planId +
                ", amount=" + amount +
                '}';
    }
}
