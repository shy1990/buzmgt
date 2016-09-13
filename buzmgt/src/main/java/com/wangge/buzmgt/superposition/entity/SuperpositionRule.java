package com.wangge.buzmgt.superposition.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;


/**
 * Created by joe on 16-9-6.
 * 叠加规则
 */
@Entity
@Table(name = "SYS_SUPERPOSITION_RULE")
public class SuperpositionRule {
    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "RULE_ID")
    private Long ruleId;//标识

    private Integer min;//小值

    private Integer max;//大值

    private Float percentage;//提成

    private Integer serialNumber;//序列号

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "SuperpositionRule{" +
                "ruleId=" + ruleId +
                ", min=" + min +
                ", max=" + max +
                ", percentage=" + percentage +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
