package com.wangge.buzmgt.monthTarget.entity;

/**
 * Created by joe on 16-7-2.
 */
public class Count {
    private int order;//实际提货量

    private int active;//实际活跃商家

    private int register;//注册商家

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

}
