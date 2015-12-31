package com.wangge.buzmgt.region.vo;

/**
 * 
 * 功能: 
 * 详细： 	
 * 类名：  树型相关
 * 作者： 	jiabin
 * 版本：  1.0
 * 日期：  2015年11月5日下午4:06:40
 */
public class RegionTree {
	private String id;
	private String pId;//父id
	private String isParent;//是否是父节点isParent
	private String icon;//图标
	private String iconOpen;
	private String iconClose;
	private String name;//节点名字
	
	private String regiontype;//节点类型
	
	private boolean open;//默认打开

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}


	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
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

	public String getRegiontype() {
		return regiontype;
	}

	public void setRegiontype(String regiontype) {
		this.regiontype = regiontype;
	}
	
	
	
}	
