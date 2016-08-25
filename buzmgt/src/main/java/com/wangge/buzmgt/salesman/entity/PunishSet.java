package com.wangge.buzmgt.salesman.entity;

import com.wangge.buzmgt.region.entity.Region;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 设置奖罚系数
 */
@Entity
@Table(name="SYS_PUNISHSET")
public class PunishSet implements Serializable {
	/** 
   * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
   * @since JDK 1.8 
   */ 
  private static final long serialVersionUID = 8232246050592021842L;
  @Id 
	@GenericGenerator(name="idgen",strategy="increment")
	@GeneratedValue(generator="idgen")//自定义生成主键策略
	@Column(name="ID")
	private Long id;//奖罚id
	@Column(name="PUNISH_NUMBER ")
	private Float punishNumber;//系数
	@OneToOne
	//@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID")//与数据库对应的表的字段名
	private Region region = new Region();//区域
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Float getPunishNumber() {
		return punishNumber;
	}
	public void setPunishNumber(Float punishNumber) {
		this.punishNumber = punishNumber;
	}
	
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	
	@Override
	public String toString() {
		return "PunishSet [id=" + id + ", punishNumber=" + punishNumber + ", region=" + region + "]";
	}
	
	
}
