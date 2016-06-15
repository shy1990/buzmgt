package com.wangge.buzmgt.salesman.entity;


import org.springframework.data.domain.Page;

/**
 * 月扣罚 返回类型 result
 * Created by 神盾局 on 2016/6/14.
 */
public class MonthPunishUpResult {
    private Page<MonthPunishUp> page;
    private Float sum;

    public Page<MonthPunishUp> getPage() {
        return page;
    }

    public void setPage(Page<MonthPunishUp> page) {
        this.page = page;
    }

    public Float getSum() {
        return sum;
    }
    public void setSum(Float sum) {
        this.sum = sum;
    }
}
