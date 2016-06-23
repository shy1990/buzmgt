package com.wangge.buzmgt.oilcost.entity;

import java.io.Serializable;

public class OilRecord implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  

  public OilRecord() {
    super();
  }
  


  public OilRecord(String regionType, String regionName, String coordinates, String time, String shopName,
      String missTime, String missName, String exception, String type, String $ref) {
    super();
    this.regionType = regionType;
    this.regionName = regionName;
    this.coordinates = coordinates;
    this.time = time;
    this.shopName = shopName;
    this.missTime = missTime;
    this.missName = missName;
    this.exception = exception;
    this.type = type;
    this.$ref = $ref;
  }





  @Override
  public String toString() {
    return "OilRecord [regionType=" + regionType + ", regionName=" + regionName + ", coordinates=" + coordinates
        + ", time=" + time + ", type=" + type + "]";
  }




  private String regionType;//(0-起点，1-物流点，2-工作区域，3-结束)
  private String regionName;
  private String coordinates;//坐标
  private String time;
  private String shopName;
  private String missTime;
  private String missName;
  private String exception;
  private String type;
  private String $ref;
  
  
  
  public String get$ref() {
    return $ref;
  }
  public void set$ref(String $ref) {
    this.$ref = $ref;
  }
  public String getMissName() {
    return missName;
  }
  public void setMissName(String missName) {
    this.missName = missName;
  }
  public String getRegionType() {
    return regionType;
  }
  public void setRegionType(String regionType) {
    this.regionType = regionType;
  }
  public String getRegionName() {
    return regionName;
  }
  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }
  public String getCoordinates() {
    return coordinates;
  }
  public void setCoordinates(String coordinates) {
    this.coordinates = coordinates;
  }
  public String getTime() {
    return time;
  }
  public void setTime(String time) {
    this.time = time;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getShopName() {
    return shopName;
  }
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  public String getMissTime() {
    return missTime;
  }
  public void setMissTime(String missTime) {
    this.missTime = missTime;
  }
  public String getException() {
    return exception;
  }
  public void setException(String exception) {
    this.exception = exception;
  }
  
}
