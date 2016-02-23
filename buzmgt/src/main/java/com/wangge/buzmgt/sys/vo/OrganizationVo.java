package com.wangge.buzmgt.sys.vo;

public class OrganizationVo {
	
	private int id;
	
	private String pId;
	
	private String isParent;
	
	private String open;
	
	private String icon;
	
	private String name;
	
	private String iconOpen;
	private String iconClose;
	
	
	
	public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
  public String getpId() {
    return pId;
  }
  public void setpId(String pId) {
    this.pId = pId;
  }
  public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
  public String getIconOpen() {
    return iconOpen;
  }
  public void setIconOpen(String iconOpen) {
    this.iconOpen = iconOpen;
  }
  public String getIconClose() {
    return iconClose;
  }
  public void setIconClose(String iconClose) {
    this.iconClose = iconClose;
  }
	

}
