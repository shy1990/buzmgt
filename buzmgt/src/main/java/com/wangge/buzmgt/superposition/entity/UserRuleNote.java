package com.wangge.buzmgt.superposition.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by joe on 16-10-10.
 * 判断出每个业务员的规则之后记录在此表中
 * 用于冲减计算
 */
@Entity
@Table(name = "SYS_USER_RULE_NOTE")
public class UserRuleNote {

    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "RULE_NOTE_ID")
    private Long id;//id

    private String userId;//方案人员id

    private Integer min;//区间最小值

    private Integer max;//区间最大值

    private Float percentage;//提成

    @Column(name = "RECORD_ID")
    private Long recordId;//无退货计算保存表id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    @Override
    public String toString() {
        return "UserRuleNote{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", percentage=" + percentage +
                ", recordId=" + recordId +
                '}';
    }
}
