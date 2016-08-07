package com.wangge.buzmgt.monthTarget.entity;

import com.wangge.buzmgt.region.entity.Region;

import java.util.Date;

/**
 * Created by joe on 16-6-27.
 * <p>
 * 视图实体类（MothTargetData）
 */
public class MothTargetData implements Comparable<MothTargetData> {

    private String orderId;//订单id

    private String memberId;//订单项id

    private Integer nums;//商品数量

    private Date createTime;//订单时间

    private String regionId;//所属区域

    private String parentId;//业务员regionid

    private String phoneNmu;//商家电话

    private String shopName;//商家名称

    private int count;//提货次数

    private int numsOne;//单品数量

    private Region region;//商家区域

    private String regionName;//商家区域

    private String time;//用于在页面显示月份

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getNumsOne() {
        return numsOne;
    }

    public void setNumsOne(Integer numsOne) {
        this.numsOne = numsOne;
    }

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

    public String getParentId() {
        return parentId;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setNumsOne(int numsOne) {
        this.numsOne = numsOne;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "MothTargetData{" +
                "orderId='" + orderId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", nums=" + nums +
                ", createTime=" + createTime +
                ", regionId='" + regionId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", phoneNmu='" + phoneNmu + '\'' +
                ", shopName='" + shopName + '\'' +
                ", count=" + count +
                ", numsOne=" + numsOne +
                ", region=" + region +
                ", regionName='" + regionName + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    @Override
    public int compareTo(MothTargetData o) {
//        return this.getNumsOne() - o.getNumsOne();
        return o.getNumsOne()-this.getNumsOne();
    }
}
