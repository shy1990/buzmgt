package com.wangge.buzmgt.superposition.entity;

/**
 * Created by joe on 16-9-8.
 * 返回类型
 */
public class Result {

    private String msg;

    private Object data;

    private String status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
