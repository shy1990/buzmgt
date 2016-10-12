package com.wangge.buzmgt.superposition.pojo;

import java.util.Date;

/**
 * Created by joe on 16-9-27.
 * 叠加收益进程显示
 */
public class SuperpositionProgress {
    private String trueName;//业务员名
    private String regionId;//业务员区域
    private String userId;//业务员id
    private String namePath;//业务员区域地址
    private String planId;//方案id
    private String shopName;//商铺名字
    private String payTime;//付款时间
    private String shopAddress;//商铺地址
    private Long superId;//叠加方案id
    private String taskOne;//指标1
    private String taskTwo;//指标2
    private String taskThree;//指标3
    private String implDate;//开始日期
    private String endDate;//结束日期
    private String oneAdd;//组中指标1
    private String twoAdd;//组中指标2
    private String threeAdd;//组中指标3
    private String name;//用户组
    private String nums;//提货数量
    private String orderNum;//订单好号
    private String goodsName;//商品名字
    private Integer min;//小值
    private Integer max;//大值
    private Float percentage;//提成
    private String sign;//标记(用于设置组提成规则)

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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getNums() {
        return nums;
    }

    public void setNums(String nums) {
        this.nums = nums;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public String getTaskOne() {
        return taskOne;
    }

    public void setTaskOne(String taskOne) {
        this.taskOne = taskOne;
    }

    public String getTaskTwo() {
        return taskTwo;
    }

    public void setTaskTwo(String taskTwo) {
        this.taskTwo = taskTwo;
    }

    public String getTaskThree() {
        return taskThree;
    }

    public void setTaskThree(String taskThree) {
        this.taskThree = taskThree;
    }

    public String getImplDate() {
        return implDate;
    }

    public void setImplDate(String implDate) {
        this.implDate = implDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getOneAdd() {
        return oneAdd;
    }

    public void setOneAdd(String oneAdd) {
        this.oneAdd = oneAdd;
    }

    public String getTwoAdd() {
        return twoAdd;
    }

    public void setTwoAdd(String twoAdd) {
        this.twoAdd = twoAdd;
    }

    public String getThreeAdd() {
        return threeAdd;
    }

    public void setThreeAdd(String threeAdd) {
        this.threeAdd = threeAdd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "SuperpositionProgress{" +
                "trueName='" + trueName + '\'' +
                ", regionId='" + regionId + '\'' +
                ", userId='" + userId + '\'' +
                ", namePath='" + namePath + '\'' +
                ", planId='" + planId + '\'' +
                ", shopName='" + shopName + '\'' +
                ", payTime='" + payTime + '\'' +
                ", shopAddress='" + shopAddress + '\'' +
                ", superId=" + superId +
                ", taskOne='" + taskOne + '\'' +
                ", taskTwo='" + taskTwo + '\'' +
                ", taskThree='" + taskThree + '\'' +
                ", implDate='" + implDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", oneAdd='" + oneAdd + '\'' +
                ", twoAdd='" + twoAdd + '\'' +
                ", threeAdd='" + threeAdd + '\'' +
                ", name='" + name + '\'' +
                ", nums='" + nums + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", percentage=" + percentage +
                ", sign='" + sign + '\'' +
                '}';
    }
}
