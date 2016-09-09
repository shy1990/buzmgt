package com.wangge.buzmgt.income.ywsalary.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * ClassName: BaseSalary <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason: 1.新增时,2.修改时3.删除时 <br/>
 * date: 2016年8月20日 下午5:18:35 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@NamedEntityGraph(name = "user", attributeNodes = {
    @NamedAttributeNode(value = "user", subgraph = "user.region") }, subgraphs = {
        @NamedSubgraph(name = "user.region", attributeNodes = @NamedAttributeNode(value = "region")) })
@Entity
@Table(name = "sys_income_basicsalay")
public class BaseSalary implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  @Column(name = "USER_ID")
  private String userId;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
  private SalesMan user;
  private Double salary;// 薪资
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date deldate;// 修改日期
  @Temporal(TemporalType.DATE)
  private Date newdate;
  @Enumerated(EnumType.ORDINAL)
  private FlagEnum flag = FlagEnum.NORMAL;// 是否删除 normal-正常,del-删除
  
  //操作人id,用户名
  private String  authorId;
  private String  authorName;
  public SalesMan getUser() {
    return user;
  }
  
  public void setUser(SalesMan user) {
    this.user = user;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  

  public String getUserId() {
    return userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public Double getSalary() {
    return salary;
  }
  
  public void setSalary(Double salary) {
    this.salary = salary;
  }
  
  public Date getDeldate() {
    return deldate;
  }
  
  public void setDeldate(Date deldate) {
    this.deldate = deldate;
  }
  
  public FlagEnum getFlag() {
    return flag;
  }
  
  public void setFlag(FlagEnum flag) {
    this.flag = flag;
  }
  
  public Date getNewdate() {
    return newdate;
  }
  
  public void setNewdate(Date newdate) {
    this.newdate = newdate;
  }
  
  public BaseSalary(String userId, SalesMan user, Double salary, Date newdate) {
    super();
    this.userId = userId;
    this.user = user;
    this.salary = salary;
    this.newdate = newdate;
  }

  public BaseSalary() {
    super();
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

  @Override
  public String toString() {
    return "BaseSalary [id=" + id + ", userId=" + userId + ", user=" + user + ", salary=" + salary + ", updateDate="
        + deldate + "]";
  }
  
}
