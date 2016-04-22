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




  private String regionType;
  private String regionName;
  private String coordinates;
  private String time;
  
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
  private String type;
}
