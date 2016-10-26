package com.wangge.buzmgt.superposition.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by joe on 16-9-7.
 * 成员
 */
@Entity
@Table(name = "SYS_SUPER_MEMBER")
public class Member {
    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "MEMBER_ID")
    private Long id;//标识

    @Column(name = "name")
    private String salesmanName;//用户名

    @Column(name = "USER_ID")
    private String salesmanId;//用户的id


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", salesmanName='" + salesmanName + '\'' +
                ", salesmanId='" + salesmanId + '\'' +
                '}';
    }
}
