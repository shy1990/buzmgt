
package com.wangge.buzmgt.oil.entity;

import com.wangge.buzmgt.region.entity.Region;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 油补系数
 * @author jiaoyue
 *
 */
@Entity
@Table(name="SYS_OIL_PARAMETERS")
public class OilParameters implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GenericGenerator(name="idgen",strategy="increment")
	@GeneratedValue(generator="idgen")   //自定义生成主键策略
	@Column(name="ID")
	private Long id;//主键
	
	@Column(name="KM_RATIO")
	private Float kmRatio;//公里系数
	
	@Column(name="KM_OIL_SUBSIDY")
	private Float kmOilSubsidy; //每公里油补补助
	
//	@Column(name="REGION_ID")
//	private String regionId;//区域id
	//@OneToOne(optional = false, cascade = CascadeType.ALL)
	@OneToOne
	@JoinColumn(name = "region_id")//与数据库对应的表的字段名
//	@Transient
	private Region region = new Region();
	
	
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Float getKmRatio() {
		return kmRatio;
	}
	public void setKmRatio(Float kmRatio) {
		this.kmRatio = kmRatio;
	}
	public Float getKmOilSubsidy() {
		return kmOilSubsidy;
	}
	public void setKmOilSubsidy(Float kmOilSubsidy) {
		this.kmOilSubsidy = kmOilSubsidy;
	}
//	public String getRegionId() {
//		return regionId;
//	}
//	public void setRegionId(String regionId) {
//		this.regionId = regionId;
//	}
	@Override
	public String toString() {
		return "OilParameters [id=" + id + ", kmRatio=" + kmRatio + ", kmOilSubsidy=" + kmOilSubsidy + ", regionId="
				+  ", region=" + region + "]";
	}
	
	
}
