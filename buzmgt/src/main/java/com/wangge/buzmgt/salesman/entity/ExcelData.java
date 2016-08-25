package com.wangge.buzmgt.salesman.entity;

/**
 * Created by joe on 16-7-15.
 */
public class ExcelData {

    private String userId;//业务id
    private String name;//姓名
    private String cardNumber;//银行卡号
    private String bankName;//开户行

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
