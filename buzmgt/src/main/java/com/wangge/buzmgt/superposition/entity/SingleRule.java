package com.wangge.buzmgt.superposition.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by joe on 16-9-22.
 * 一单达量设置规则
 */
@Entity
@Table(name = "SYS_SINGLE_RULE")
public class SingleRule {
    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "SINGLE_RULE_ID")
    private Long id;//标识

    private Integer min;//小值

    private Integer max;//大值

    private Float reward;//奖励

    private Integer serialNumber;//序列号

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getReward() {
        return reward;
    }

    public void setReward(Float reward) {
        this.reward = reward;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "SingleRule{" +
                "id=" + id +
                ", min=" + min +
                ", max=" + max +
                ", reward=" + reward +
                ", serialNumber=" + serialNumber +
                '}';
    }
}
