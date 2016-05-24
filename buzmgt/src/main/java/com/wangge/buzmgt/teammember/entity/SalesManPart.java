package com.wangge.buzmgt.teammember.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.sys.entity.User;

/**
 * 
* @ClassName: SalesManPart
* @Description: 从SalesMan中获取一部分数据
*       用于显示组织结构+区域名称+用户名
* @author Thor
* @date 2016年4月22日 上午11:33:20
*
 */
public class SalesManPart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String id;
	
	private String truename;
	
	private String regionName;

	private String organizeName;
	
	
	public SalesManPart() {
		super();
	}

	

  public SalesManPart(String id, String truename, String regionName, String organizeName) {
    super();
    this.id = id;
    this.truename = truename;
    this.regionName = regionName;
    this.organizeName = organizeName;
  }

  

  @Override
  public String toString() {
    return "SalesManPart [id=" + id + ", truename=" + truename + ", regionName=" + regionName + ", organizeName="
        + organizeName + "]";
  }



  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public String getTruename() {
    return truename.trim();
  }


  public void setTruename(String truename) {
    this.truename = truename;
  }


  public String getRegionName() {
    return regionName;
  }


  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }


  public String getOrganizeName() {
    return organizeName;
  }


  public void setOrganizeName(String organizeName) {
    this.organizeName = organizeName;
  }
	
	

  
}
