package com.wangge.buzmgt.brandincome.entity;

import java.util.Date;

/**
 *
 * 品牌型号收益进程
 */
public class BrandIncomeVo {

    private String regionId;//区域ID

    private String truename;//姓名

    private String namepath;//区域

    private String levelName;//等级

    private String brandName;//品牌

    private String goodsId;//商品ID

    private String goodsName;//型号

    private int nums;//销量进程

    private Date startDate;//开始日期

    private Date endDate;//结束日期

    private String status;//使用状态

    private int starsLevel;//区域星级

    private int numberFirst;//任务量一

    private int numberSecond;//任务量二

    private int numberThird;//任务量三

    private String groupName;//分组

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getNamepath() {
        return namepath;
    }

    public void setNamepath(String namepath) {
        this.namepath = namepath;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStarsLevel() {
        return starsLevel;
    }

    public void setStarsLevel(int starsLevel) {
        this.starsLevel = starsLevel;
    }

    public int getNumberFirst() {
        return numberFirst;
    }

    public void setNumberFirst(int numberFirst) {
        this.numberFirst = numberFirst;
    }

    public int getNumberSecond() {
        return numberSecond;
    }

    public void setNumberSecond(int numberSecond) {
        this.numberSecond = numberSecond;
    }

    public int getNumberThird() {
        return numberThird;
    }

    public void setNumberThird(int numberThird) {
        this.numberThird = numberThird;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
