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
  public OilRecord(String regionType, String regionName, String coordinates, String time, String type) {
    super();
    this.regionType = regionType;
    this.regionName = regionName;
    this.coordinates = coordinates;
    this.time = time;
    this.type = type;
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
  private String misName;
  private String exception;
  private String type;
  
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
  public String getMisName() {
    return misName;
  }
  public void setMisName(String misName) {
    this.misName = misName;
  }
  public String getException() {
    return exception;
  }
  public void setException(String exception) {
    this.exception = exception;
  }
  
}
