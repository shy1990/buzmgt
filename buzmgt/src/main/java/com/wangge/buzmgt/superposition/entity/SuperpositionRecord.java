package com.wangge.buzmgt.superposition.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by joe on 16-10-7.
 * 叠加收益记录
 */
@Entity
@Table(name="SYS_SUPERPOSITION_RECORD")
public class SuperpositionRecord {
    @Id
    @GenericGenerator(name = "idgen", strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "RECORD_ID")
    private Long id;//标识

    private Integer record;//提货数量

    private String salesmanId;//业务员id

    private Long planId;//方案id

    private Long superId;//叠加方案id

    private Float amount;//提成

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }


    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getRecord() {
        return record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    @Override
    public String toString() {
        return "SuperpositionRecord{" +
                "id=" + id +
                ", record='" + record + '\'' +
                ", salesmanId='" + salesmanId + '\'' +
                ", planId=" + planId +
                ", superId=" + superId +
                ", amount=" + amount +
                '}';
    }
}
