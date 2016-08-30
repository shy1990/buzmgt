package com.wangge.buzmgt.oilcost.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.alibaba.fastjson.annotation.JSONField;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesManPart;


@Entity
@Table(name = "BIZ_OIL_COST_RECORD")
public class OilCost implements Serializable{

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;
  
  
  public static enum OilAcountEnum {
    //子账号
    PARENTACCOUNT("主"),CHILDACCOUNT("子");
    
    private String name;
    OilAcountEnum(String name){
      this.name=name;
    }
    
    public String getName(){
      return name;
    }
    
  }
  
  
  @Id
  @GenericGenerator(name = "idgen",strategy = "increment")
  @GeneratedValue(generator="idgen")
  @Column(name = "TRACK_ID")
  private Long id; //主键id
  
  @Column(name = "user_id",insertable=false,updatable=false)
  private String userId;//业务id
  
  @Transient
//  @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
//  @JoinColumn(name= "user_id")
  @JSONField(serialize = false)
  private SalesMan salesMan;
  // private String Coordinates;//坐标集合
  @Temporal(TemporalType.DATE)
  private Date dateTime;//日期
  @Enumerated(EnumType.ORDINAL)
  private OilAcountEnum isPrimaryAccount;//是否主账号
  //private String regionIds;//regionId集合
  private Float oilCost;//油补的费用
  private Float  distance;//里程数
  @Transient
  private Float oilTotalCost;//某一时间段的油补总的费用
  @Transient
  private Float  totalDistance;//某一时间段的总里程数
  @Lob
  @JSONField(serialize = false)//禁止序列化
  private String  oilRecord;//Coordinates,regionIds，shopName等  油补 json串
  
  @Transient
  private List<OilRecord>  oilRecordList;//Coordinates,regionIds，shopName等  油补 json串
  
  @Transient
  private SalesManPart salesManPart;//处理salesMan中数据
  
  @Transient
  private String recordSort;//握手顺序
  
  private String parentId;
  @Column(name="region_ids")
  private String regionIds;
  
  
  
  public String getRecordSort() {
    return recordSort;
  }
  public void setRecordSort(String recordSort) {
    this.recordSort = recordSort;
  }
  public SalesManPart getSalesManPart() {
    return salesManPart;
  }
  public void setSalesManPart(SalesManPart salesManPart) {
    this.salesManPart = salesManPart;
  }
  public Float getOilTotalCost() {
    return oilTotalCost==null?Float.valueOf(0):oilTotalCost;
  }
  public void setOilTotalCost(Float oilTotalCost) {
    this.oilTotalCost = oilTotalCost;
  }
  public Float getTotalDistance() {
    return totalDistance==null?Float.valueOf(0):totalDistance;
  }
  public void setTotalDistance(Float totalDistance) {
    this.totalDistance = totalDistance;
  }
  public SalesMan getSalesMan() {
    return salesMan==null ? new SalesMan() : salesMan;
  }
  public void setSalesMan(SalesMan salesMan) {
    this.salesMan = salesMan;
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
  
  public Date getDateTime() {
    return dateTime;
  }
  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }
  public String getIsPrimaryAccount() {
    return isPrimaryAccount.getName();
  }
  public void setIsPrimaryAccount(OilAcountEnum isPrimaryAccount) {
    this.isPrimaryAccount = isPrimaryAccount;
  }
  public Float getOilCost() {
    return oilCost;
  }
  public void setOilCost(Float oilCost) {
    this.oilCost = oilCost;
  }
  public Float getDistance() {
    return distance;
  }
  public void setDistance(Float distance) {
    this.distance = distance;
  }
  public String getParentId() {
    return parentId;
  }
  public void setParentId(String parentId) {
    this.parentId = parentId;
  }
  public String getOilRecord() {
    return oilRecord;
  }
  public void setOilRecord(String oilRecord) {
    this.oilRecord = oilRecord;
  }
  public String getRegionIds() {
    return regionIds;
  }
  public List<OilRecord> getOilRecordList() {
    return oilRecordList;
  }
  public void setOilRecordList(List<OilRecord> oilRecordList) {
    this.oilRecordList = oilRecordList;
  }
  public void setRegionIds(String regionIds) {
    this.regionIds = regionIds;
  }
  
  
  
}
