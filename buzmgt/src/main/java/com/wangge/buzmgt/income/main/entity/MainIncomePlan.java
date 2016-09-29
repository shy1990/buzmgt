package com.wangge.buzmgt.income.main.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.common.FlagEnum;

/**
 * ClassName: MainIncomePlan <br/>
 * Function: 收益方案主表 <br/>
 * date: 2016年8月20日 下午5:22:45 <br/>
 * 方案变更的影响: 主方案的规则未完全建立,如何计算;<br/>
 * 情景:1.达量规则缺失.品牌型号规则在;<br/>
 * 2.无任何可用规则; <br/>
 * 解决方案:确定规则生效,开始计算;<br/>
 * 规则优先级相互作用:<br/>
 * 1.修改了品牌型号规则,是否会对价格区间规则产生影响;<br/>
 * 2.修改了达量规则,是否会对品牌型号,价格区间规则产生影响;<br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "sys_income_plan_main")
public class MainIncomePlan {
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  // 主标题
  private String maintitle;
  // 子标题
  private String subtitle;
  // 创建时间
  private Date createtime = new Date();
  // 废弃时间
  private Date fqtime;
  // 状态
  // 是否删除 normal-正常,del-删除
  @Enumerated(EnumType.ORDINAL)
  private FlagEnum state = FlagEnum.NORMAL;
  // 大区
  @Column(name = "region_id")
  private String regionId;
  private String regionname;
  
  // 操作人id,用户名
  private String authorId;
  private String authorName;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainplan", cascade = CascadeType.ALL)
  private List<IncomeMainplanUsers> users;
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getMaintitle() {
    return maintitle;
  }
  
  public void setMaintitle(String maintitle) {
    this.maintitle = maintitle;
  }
  
  public String getSubtitle() {
    return subtitle;
  }
  
  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }
  
  public Date getCreatetime() {
    return createtime;
  }
  
  public void setCreatetime(Date createtime) {
    this.createtime = createtime;
  }
  
  public Date getFqtime() {
    return fqtime;
  }
  
  public void setFqtime(Date fqtime) {
    this.fqtime = fqtime;
  }
  
  public FlagEnum getState() {
    return state;
  }
  
  public void setState(FlagEnum state) {
    this.state = state;
  }
  
  public List<IncomeMainplanUsers> getUsers() {
    return users;
  }
  
  public void setUsers(List<IncomeMainplanUsers> users) {
    this.users = users;
  }
  
  public String getRegionId() {
    return regionId;
  }
  
  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }
  
  public String getRegionname() {
    return regionname;
  }
  
  public void setRegionname(String regionname) {
    this.regionname = regionname;
  }
  
  public MainIncomePlan(String maintitle, String subtitle, Date createtime) {
    super();
    this.maintitle = maintitle;
    this.subtitle = subtitle;
    this.createtime = createtime;
  }
  
  public String getAuthorId() {
    return authorId;
  }
  
  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }
  
  public String getAuthorName() {
    return authorName;
  }
  
  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }
  
  public MainIncomePlan() {
    super();
  }
  
}
