package com.wangge.buzmgt.sys.vo;

/**
 * 
  * ClassName: VisitVo <br/> 
  * date: 2016年3月22日 下午2:49:19 <br/> 
  * @author peter 
  * @version  
  * @since JDK 1.8
 */
public class VisitVo {
  private String name; //业务名
  private String area; //地区
  private String roleName; //角色
  private String lastVisit; //上次拜访
  private int visitTimes; //拜访次数
  private int overTimes; //超时次数
  private String userId;
  
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getArea() {
    return area;
  }
  public void setArea(String area) {
    this.area = area;
  }
  public String getRoleName() {
    return roleName;
  }
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
  public String getLastVisit() {
    return lastVisit;
  }
  public void setLastVisit(String lastVisit) {
    this.lastVisit = lastVisit;
  }
  public int getVisitTimes() {
    return visitTimes;
  }
  public void setVisitTimes(int visitTimes) {
    this.visitTimes = visitTimes;
  }
  public int getOverTimes() {
    return overTimes;
  }
  public void setOverTimes(int overTimes) {
    this.overTimes = overTimes;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
	
}
