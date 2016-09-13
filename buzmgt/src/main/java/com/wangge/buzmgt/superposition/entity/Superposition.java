package com.wangge.buzmgt.superposition.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wangge.buzmgt.section.util.CustomDateSerializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by joe on 16-9-1.
 *
 * 叠加收益类
 */
@Entity
@Table(name = "SYS_SUPERPOSITION")
public class Superposition {

    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "SU_PO_ID")
    private Long id;//标识

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name = "SU_ID")
    private List<GoodsType> goodsTypeList;

    private Integer taskOne;//任务量一

    private Integer taskTwo;///任务量二

    private Integer taskThree;//任务量三

    private String production;//商品类别

    private String remark;//补充说明

    private String auditor;//审核人id

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date createDate;//创建日期

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date implDate;//实施日期

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date endDate;//结束日期

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date giveDate;//发放日期

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name = "SU_ID")
    private List<SuperpositionRule> ruleList;//规则

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name = "SU_ID")
    private List<Group> groupList;//人员分组

    private String checkStatus;//审核状态:0-创建中,1-审核中,2-驳回,3-审核通过,4-废弃(删除);

    private String planId;//方案id

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTaskOne() {
        return taskOne;
    }

    public void setTaskOne(Integer taskOne) {
        this.taskOne = taskOne;
    }

    public Integer getTaskTwo() {
        return taskTwo;
    }

    public void setTaskTwo(Integer taskTwo) {
        this.taskTwo = taskTwo;
    }

    public Integer getTaskThree() {
        return taskThree;
    }

    public void setTaskThree(Integer taskThree) {
        this.taskThree = taskThree;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getImplDate() {
        return implDate;
    }

    public void setImplDate(Date implDate) {
        this.implDate = implDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getGiveDate() {
        return giveDate;
    }

    public void setGiveDate(Date giveDate) {
        this.giveDate = giveDate;
    }

    public List<SuperpositionRule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<SuperpositionRule> ruleList) {
        this.ruleList = ruleList;
    }

    public List<GoodsType> getGoodsTypeList() {
        return goodsTypeList;
    }

    public void setGoodsTypeList(List<GoodsType> goodsTypeList) {
        this.goodsTypeList = goodsTypeList;
    }

    @Override
    public String toString() {
        return "Superposition{" +
                "id=" + id +
                ", goodsTypeList=" + goodsTypeList +
                ", taskOne=" + taskOne +
                ", taskTwo=" + taskTwo +
                ", taskThree=" + taskThree +
                ", production='" + production + '\'' +
                ", remark='" + remark + '\'' +
                ", auditor='" + auditor + '\'' +
                ", createDate=" + createDate +
                ", implDate=" + implDate +
                ", endDate=" + endDate +
                ", giveDate=" + giveDate +
                ", ruleList=" + ruleList +
                ", groupList=" + groupList +
                ", checkStatus='" + checkStatus + '\'' +
                ", planId='" + planId + '\'' +
                '}';
    }
}
