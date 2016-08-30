package com.wangge.buzmgt.pushmoney.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.goods.entity.Category;

@Entity
@Table(name="SYS_PUSH_MONEY")
@NamedEntityGraph(
    name = "graph.PushMoney.category",
    attributeNodes={
        @NamedAttributeNode(value="category"),
        @NamedAttributeNode(value="priceScope"),
        @NamedAttributeNode(value="pushMoneyRegions")
    }
)
public class PushMoney implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Integer id;
  
  private Integer type;//提成类型 0-种类，1-品牌
  
  private Float money;
  
  @Column(name="category")
  private String categoryId;
  
  @JoinColumn(name="category",updatable=false,insertable=false)
  @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.DETACH)
  private Category category;
  
  @JoinColumn(name="price_scope",updatable=false,insertable=false)
  @OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.DETACH)
  private PriceScope priceScope;
  
  @Column(name="price_scope")
  private Integer priceScopeId;
  
  @JoinColumn(name="id")
  @OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL)
  private List<PushMoneyRegion> pushMoneyRegions;
  
  private Date createDate;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Float getMoney() {
    return money;
  }

  public void setMoney(Float money) {
    this.money = money;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(String categoryId) {
    this.categoryId = categoryId;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public PriceScope getPriceScope() {
    return priceScope;
  }

  public void setPriceScope(PriceScope priceScope) {
    this.priceScope = priceScope;
  }

  public Integer getPriceScopeId() {
    return priceScopeId;
  }

  public void setPriceScopeId(Integer priceScopeId) {
    this.priceScopeId = priceScopeId;
  }

  public List<PushMoneyRegion> getPushMoneyRegions() {
    return pushMoneyRegions;
  }

  public void setPushMoneyRegions(List<PushMoneyRegion> pushMoneyRegions) {
    this.pushMoneyRegions = pushMoneyRegions;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
  
  
}
