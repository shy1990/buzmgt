package com.wangge.buzmgt.region.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SYS_REGION_TYPE")
public class RegionType implements Serializable {
  private static final long serialVersionUID = 1L;



  @Id
  @Column(name = "REGION_TYPE_ID")
  private int id;

  @Column(name = "REGION_TYPE_NAME")
  private String name;


  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PARENT_ID")
  private  RegionType childsType;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RegionType getChildsType() {
    return childsType;
  }

  public void setChildsType(RegionType childsType) {
    this.childsType = childsType;
  }
}
