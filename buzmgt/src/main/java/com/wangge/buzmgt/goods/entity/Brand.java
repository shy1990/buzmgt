package com.wangge.buzmgt.goods.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SYS_BRAND")
public class Brand implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  private String  id  ; //  主键
  private String  pid ; //  父ID
  private String  name  ; //  名称
  private String  remark  ; //  备注，详细信息
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getPid() {
    return pid;
  }
  public void setPid(String pid) {
    this.pid = pid;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getRemark() {
    return remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  @Override
  public String toString() {
    return "Brand [id=" + id + ", pid=" + pid + ", name=" + name + ", remark=" + remark + "]";
  }

}
