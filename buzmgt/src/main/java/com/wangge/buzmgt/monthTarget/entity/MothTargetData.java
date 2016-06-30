package com.wangge.buzmgt.monthTarget.entity;

import com.wangge.buzmgt.region.entity.Region;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by joe on 16-6-27.
 *
 * 视图实体类（MothTargetData）
 */
//@Entity
//@Immutable
//@Subselect("select * from mothtargetdata")
public class MothTargetData implements Serializable {

    private String orderId;//订单id

    private String memberId;//订单项id

    private Integer nums;//商品数量

    private Date createTime;//订单时间

    private String regionId;//所属区域

    private String phoneNmu;//商家电话

    private String shopName;//商家名称

    private BigDecimal count;//提货次数

    private BigDecimal numsOne;//单品数量

    private Region region;//区域

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public BigDecimal getNumsOne() {
        return numsOne;
    }

    public void setNumsOne(BigDecimal numsOne) {
        this.numsOne = numsOne;
    }

    private String regionName;//区域

    private String time;//用于在页面显示月份


    public MothTargetData(){};

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getPhoneNmu() {
        return phoneNmu;
    }

    public void setPhoneNmu(String phoneNmu) {
        this.phoneNmu = phoneNmu;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "MothTargetData{" +
                "orderId='" + orderId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", nums=" + nums +
                ", createTime=" + createTime +
                ", regionId='" + regionId + '\'' +
                ", phoneNmu='" + phoneNmu + '\'' +
                ", shopName='" + shopName + '\'' +
                ", count=" + count +
                ", numsOne=" + numsOne +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
