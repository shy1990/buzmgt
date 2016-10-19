package com.wangge.buzmgt.superposition.pojo;

/**
 * Created by joe on 16-10-19.
 * 用户提货量
 */
public class UserGoodsNum {

    private Long superId;//叠加规则id
    private String userId;//用户id
    private String goodsId;//商品id
    private Integer nums;//提货量

    public Long getSuperId() {
        return superId;
    }

    public void setSuperId(Long superId) {
        this.superId = superId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    @Override
    public String toString() {
        return "UserGoodsNum{" +
                "superId=" + superId +
                ", userId='" + userId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", nums=" + nums +
                '}';
    }
}
