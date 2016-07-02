package com.wangge.buzmgt.monthTask.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**月任务扣罚表
 * @author yangqc
 *
 */
@Entity
@Table(name = "sys_month_task_punish")
public class MonthTaskPunish implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 102L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private long id;
	// 区域
	private String regionId;
	// 扣罚系数
	private int rate;

	private String regionName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public MonthTaskPunish() {
	}

	public MonthTaskPunish(String regionId, int rate) {
		super();
		this.regionId = regionId;
		this.rate = rate;
	}

}
