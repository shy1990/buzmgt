package com.wangge.buzmgt.salesman.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by 神盾局 on 2016/5/21.
 */
@Entity
@Table(name="SYS_MONTH_PUNISH_RECORD_UP")
//以后加上 负载图
@NamedEntityGraph(name = "graph.MonthPunishUp.salesMan",
        attributeNodes = @NamedAttributeNode(value = "salesMan", subgraph = "graph.MonthPunishUp.salesMan" +
                ".region"), subgraphs = {@NamedSubgraph(name = "graph.MonthPunishUp.salesMan.region",
        attributeNodes = @NamedAttributeNode("region"))})
//@JsonIgnoreProperties({"salesMan"})
public class MonthPunishUp implements Serializable {
    @Id
    @GenericGenerator(name = "idgen", strategy = "increment")
    @GeneratedValue(generator = "idgen")
    private Integer id ; //订单id
    @Column(name="WATER_NO")
    private String seriaNo; //流水单号

    @Column(name="DEBT_MONEY")
    private Float debt; //欠款金额

    @Column(name="FINE_MONEY")
    private Float amerce; //扣罚金额

    @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")
    private Date createDate ;//创建日期
//    @OneToOne
//    @JoinColumn(name = "USER_ID")
//    private User user;
//    @OneToOne
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private SalesMan salesMan;

    @Transient
    private String regionName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeriaNo() {
        return seriaNo;
    }

    public void setSeriaNo(String seriaNo) {
        this.seriaNo = seriaNo;
    }

    public Float getDebt() {
        return debt;
    }

    public void setDebt(Float debt) {
        this.debt = debt;
    }

    public Float getAmerce() {
        return amerce;
    }

    public void setAmerce(Float amerce) {
        this.amerce = amerce;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public SalesMan getSalesMan() {
        return salesMan;
    }

    public void setSalesMan(SalesMan salesMan) {
        this.salesMan = salesMan;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}

