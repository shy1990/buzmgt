package com.wangge.buzmgt.section.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wangge.buzmgt.section.util.CustomDateSerializer;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 小区间值
 * Created by joe on 16-8-19.
 */
@Entity
@Table(name = "SYS_PRICE_RANGE")
public class PriceRange {
    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "PRICE_RANGE_ID")
    private Long priceRangeId;//标识

    private String serialNumber;//序列号

    private String priceRange;//价格区间 0-50,50-100

    private Double percentage;//提成

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date priceRangeCreateDate;//提成设置日期（小区间设置日期）,首次设置是产品设置传过来

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date implementationDate;//提成实施日期（小区间实施日期）,首次设置是产品设置传过来

    private String priceRangeStatus;//状态:0-创建中,1-审核中,2-驳回,3-审核通过,4-废弃(删除);

    @JsonSerialize(using = CustomDateSerializer.class)
    @Temporal(TemporalType.DATE)
    private Date endTime;//提成结束日期

    private String priceRangeAuditor;//审核人id(保存的是用户的id)

    private String status = "0";//用于记录删除:0:为删除,1:已删除

    //记录上次id(是修改的哪个区间),当通过的时候可以根据他来查找,然后增加结束时间
    private Long oldId;

    public Long getOldId() {
        return oldId;
    }

    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }
    //TODO 以后加上区域属性

//    private List<?> ps ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriceRangeAuditor() {
        return priceRangeAuditor;
    }

    public void setPriceRangeAuditor(String priceRangeAuditor) {
        this.priceRangeAuditor = priceRangeAuditor;
    }

    public Long getPriceRangeId() {
        return priceRangeId;
    }

    public void setPriceRangeId(Long priceRangeId) {
        this.priceRangeId = priceRangeId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Date getPriceRangeCreateDate() {
        return priceRangeCreateDate;
    }

    public void setPriceRangeCreateDate(Date priceRangeCreateDate) {
        this.priceRangeCreateDate = priceRangeCreateDate;
    }

    public Date getImplementationDate() {
        return implementationDate;
    }

    public void setImplementationDate(Date implementationDate) {
        this.implementationDate = implementationDate;
    }

    public String getPriceRangeStatus() {
        return priceRangeStatus;
    }

    public void setPriceRangeStatus(String priceRangeStatus) {
        this.priceRangeStatus = priceRangeStatus;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "PriceRange{" +
                "priceRangeId=" + priceRangeId +
                ", serialNumber='" + serialNumber + '\'' +
                ", priceRange='" + priceRange + '\'' +
                ", percentage=" + percentage +
                ", priceRangeCreateDate=" + priceRangeCreateDate +
                ", implementationDate=" + implementationDate +
                ", priceRangeStatus='" + priceRangeStatus + '\'' +
                ", endTime=" + endTime +
                '}';
    }
}
