package com.wangge.buzmgt.section.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wangge.buzmgt.section.util.CustomDateSerializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 产品类别管理区间
 * Created by joe on 16-8-19.
 */
@Entity
@Table(name = "SYS_PRODUCTION")
public class Production {
    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "PRODUCTION_ID")
    private Long productionId;//标识

    private String productionType;//类型:znj:智能机,hyj:合约机

    private String productStatus = "0" ;//状态:0-创建中,1-审核中,2-驳回,3-审核通过,4-废弃(删除);

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date createTime = new Date();//创建日期

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date implDate;//实施日期

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date endTime;//结束日期（下一个开始执行的时间就是其结束时间）

    private String productionAuditor;//审核人id(保存的是用户的id)

    private String status = "0";//用于记录删除:0:未删除,1:已删除

    public String getProductionAuditor() {
        return productionAuditor;
    }

    public void setProductionAuditor(String productionAuditor) {
        this.productionAuditor = productionAuditor;
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "PRODUCTION_ID")
    private List<PriceRange> priceRanges;

//    关联方案的id
    @Column(name = "plan_id")
    private Long planId;

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PriceRange> getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(List<PriceRange> priceRanges) {
        this.priceRanges = priceRanges;
    }

    public Long getProductionId() {
        return productionId;
    }

    public void setProductionId(Long productionId) {
        this.productionId = productionId;
    }

    public String getProductionType() {
        return productionType;
    }

    public void setProductionType(String productionType) {
        this.productionType = productionType;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getImplDate() {
        return implDate;
    }

    public void setImplDate(Date implDate) {
        this.implDate = implDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Production{" +
                "productionId=" + productionId +
                ", productionType='" + productionType + '\'' +
                ", productStatus='" + productStatus + '\'' +
                ", createTime=" + createTime +
                ", implDate=" + implDate +
                ", endTime=" + endTime +
                ", productionAuditor='" + productionAuditor + '\'' +
                ", status='" + status + '\'' +
                ", priceRanges=" + priceRanges +
                ", planId='" + planId + '\'' +
                '}';
    }
}
