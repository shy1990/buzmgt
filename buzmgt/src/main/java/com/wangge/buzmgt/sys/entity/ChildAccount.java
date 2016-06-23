package com.wangge.buzmgt.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
* @ClassName: ChildAccount
* @Description: TODO(*子账号类)
* @author SongBaoZhen
* @date 2016年3月25日 下午5:23:47
*
 */
@Entity
@Table(name = "BIZ_CHILD_ACCOUNT")
@JsonInclude(Include.NON_EMPTY)
public class ChildAccount {

  
  
  /*子账号主键id */
  @Id
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "ID")
  private Long id;
  
  
 /* @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")*/
  @Column(name = "CHILD_ID")
  private String childId;
  /*父类id，关联主账号*/
  private String parentId;
  /*手机sim卡号*/
  private String simId;
  /*子账号使用者的姓名*/
  private String truename;
  
  private int enable;//0 正常 1 禁用
  
  

  public ChildAccount(String childId, String parentId,String truename) {
      this.childId=childId;
      this.parentId=parentId;
      this.truename=truename;
  }

  
  public ChildAccount() {
    super();
  }

  public String getParentId() {
    return parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  public String getSimId() {
    return simId;
  }
  public void setSimId(String simId) {
    this.simId = simId;
  }
  public String getTruename() {
    return truename;
  }
  public void setTruename(String truename) {
    this.truename = truename;
  }
  public int getEnable() {
    return enable;
  }
  public void setEnable(int enable) {
    this.enable = enable;
  }


  public String getChildId() {
    return childId;
  }


  public void setChildId(String childId) {
    this.childId = childId;
  }


  public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "ChildAccount [id=" + id + ", childId=" + childId + ", parentId="
        + parentId + ", simId=" + simId + ", truename=" + truename
        + ", enable=" + enable + "]";
  }

}
