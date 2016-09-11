package com.wangge.buzmgt.superposition.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * Created by joe on 16-9-6.
 *
 * 组成员设置
 */
@Entity
@Table(name = "sys_super_group")
public class Group {
    @Id
    @GenericGenerator(name = "idgen",strategy = "increment")
    @GeneratedValue(generator = "idgen")
    @Column(name = "GROUP_ID")
    private Long id;//标识

    private String name;//组名

    private Integer oneAdd;//任务量一 增加之后的

    private Integer twoAdd;//任务量二 增加之后的

    private Integer threeAdd;//任务量三 增加之后的

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name = "GROUP_ID")
    private List<Member> members ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOneAdd() {
        return oneAdd;
    }

    public void setOneAdd(Integer oneAdd) {
        this.oneAdd = oneAdd;
    }

    public Integer getTwoAdd() {
        return twoAdd;
    }

    public void setTwoAdd(Integer twoAdd) {
        this.twoAdd = twoAdd;
    }

    public Integer getThreeAdd() {
        return threeAdd;
    }

    public void setThreeAdd(Integer threeAdd) {
        this.threeAdd = threeAdd;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", oneAdd=" + oneAdd +
                ", twoAdd=" + twoAdd +
                ", threeAdd=" + threeAdd +
                ", members=" + members +
                '}';
    }
}
