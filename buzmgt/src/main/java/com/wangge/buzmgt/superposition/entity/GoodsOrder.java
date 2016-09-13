package com.wangge.buzmgt.superposition.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by joe on 16-9-12.
 *
 * 每个单品的数量---已经付款的
 */
@Entity
@Table(name = "SYS_GOODS_ORDER")
public class GoodsOrder {

    @Id
    @GeneratedValue
    private String id;//订单详情id

    private String targetType;//订单目标类型   sku（单品）   accessories（配件） point（j积分商品）  gift (赠品)

    private Integer nums;//单品数量

    private String machineType;//商品类型,机型类别

    private String goodsName;//商品型号

    private String brandName;//品牌

    private String orderId;//订单id

    private String truename;//业务员名字

    private String skuId;//

    private String goodsId;//商品型号ID

    private String brandId;//品牌ID

    private String regionId;//业务员区域ID

    private Date payTime;//付款时间

    private String shopName;//商家名称

    private String businessRegionId;//商家名字

    public String getBusinessRegionId() {
        return businessRegionId;
    }

    public void setBusinessRegionId(String businessRegionId) {
        this.businessRegionId = businessRegionId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    @Override
    public String toString() {
        return "GoodsOrder{" +
                "id='" + id + '\'' +
                ", targetType='" + targetType + '\'' +
                ", nums=" + nums +
                ", machineType='" + machineType + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", truename='" + truename + '\'' +
                ", skuId='" + skuId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", brandId='" + brandId + '\'' +
                ", regionId='" + regionId + '\'' +
                ", payTime=" + payTime +
                ", shopName='" + shopName + '\'' +
                ", businessRegionId='" + businessRegionId + '\'' +
                '}';
    }
}
