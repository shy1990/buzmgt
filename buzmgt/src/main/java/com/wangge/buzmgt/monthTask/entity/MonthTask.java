package com.wangge.buzmgt.monthTask.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sys_monthtask_main")
public class MonthTask implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private long id;
	private String regionid;
	private String month;
	private String agentid;
	private Integer TAL15goal;
	private Integer TAL15Done;
	private Integer TAL10goal;
	private Integer TAL10done;
	private Integer TAL7goal;
	private Integer TAL7done;
	private Integer TAL4goal;
	private Integer TAL4done;
	private Integer TAL20goal;
	private Integer TAL20done;
	private Integer TAL20set;
	private Integer TAL15set;
	private Integer TAL10set;
	private Integer TAL7set;
	private Integer TAL4set;
	// 状态0未发布,1已发布
	private Integer status;
	//罚款数
	private Integer punishrate;
	@Transient
	private Set<MonthTaskSub> subSet = new HashSet<MonthTaskSub>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTown() {
		return regionid;
	}

	public void setTown(String regionid) {
		this.regionid = regionid;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public Integer getTAL15goal() {
		return TAL15goal;
	}

	public void setTAL15goal(Integer tAL15goal) {
		TAL15goal = tAL15goal;
	}

	public Integer getTAL15Done() {
		return TAL15Done;
	}

	public void setTAL15Done(Integer tAL15Done) {
		TAL15Done = tAL15Done;
	}

	public Integer getTAL10goal() {
		return TAL10goal;
	}

	public void setTAL10goal(Integer tAL10goal) {
		TAL10goal = tAL10goal;
	}

	public Integer getTAL10done() {
		return TAL10done;
	}

	public void setTAL10done(Integer tAL10done) {
		TAL10done = tAL10done;
	}

	public Integer getTAL7goal() {
		return TAL7goal;
	}

	public void setTAL7goal(Integer tAL7goal) {
		TAL7goal = tAL7goal;
	}

	public Integer getTAL7done() {
		return TAL7done;
	}

	public void setTAL7done(Integer tAL7done) {
		TAL7done = tAL7done;
	}

	public Integer getTAL4goal() {
		return TAL4goal;
	}

	public void setTAL4goal(Integer tAL4goal) {
		TAL4goal = tAL4goal;
	}

	public Integer getTAL4done() {
		return TAL4done;
	}

	public void setTAL4done(Integer tAL4done) {
		TAL4done = tAL4done;
	}

	public Integer getTAL20goal() {
		return TAL20goal;
	}

	public void setTAL20goal(Integer tAL20goal) {
		TAL20goal = tAL20goal;
	}

	public Integer getTAL20done() {
		return TAL20done;
	}

	public void setTAL20done(Integer tAL20done) {
		TAL20done = tAL20done;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTAL20set() {
		return TAL20set;
	}

	public void setTAL20set(Integer tAL20set) {
		TAL20set = tAL20set;
	}

	public Integer getTAL15set() {
		return TAL15set;
	}

	public void setTAL15set(Integer tAL15set) {
		TAL15set = tAL15set;
	}

	public Integer getTAL10set() {
		return TAL10set;
	}

	public void setTAL10set(Integer tAL10set) {
		TAL10set = tAL10set;
	}

	public Integer getTAL7set() {
		return TAL7set;
	}

	public void setTAL7set(Integer tAL7set) {
		TAL7set = tAL7set;
	}

	public Integer getTAL4set() {
		return TAL4set;
	}

	public void setTAL4set(Integer tAL4set) {
		TAL4set = tAL4set;
	}

	public Integer getPunishrate() {
		return punishrate;
	}

	public void setPunishrate(Integer punishrate) {
		this.punishrate = punishrate;
	}

	public MonthTask(String regionid, String month, String agentid, Integer tAL15goal, Integer tAL10goal, Integer tAL7goal,
			Integer tAL4goal, Integer tAL20goal) {
		super();
		this.regionid = regionid;
		this.month = month;
		this.agentid = agentid;
		TAL15goal = tAL15goal;
		TAL10goal = tAL10goal;
		TAL7goal = tAL7goal;
		TAL4goal = tAL4goal;
		TAL20goal = tAL20goal;
	}

	public MonthTask() {
	}

	public Set<MonthTaskSub> getSubSet() {
		return subSet;
	}

	public void setSubSet(Set<MonthTaskSub> subSet) {
		this.subSet = subSet;
	}

}
