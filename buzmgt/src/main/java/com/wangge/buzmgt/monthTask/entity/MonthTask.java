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
	private String town;
	private String month;
	private String agentid;
	private int TAL15goal;
	private int TAL15Done;
	private int TAL10goal;
	private int TAL10done;
	private int TAL7goal;
	private int TAL7done;
	private int TAL4goal;
	private int TAL4done;
	private int TAL20goal;
	private int TAL20done;
	@Transient
	private Set<MonthTaskSub> subSet=new HashSet<MonthTaskSub>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public int getTAL15goal() {
		return TAL15goal;
	}
	public void setTAL15goal(int tAL15goal) {
		TAL15goal = tAL15goal;
	}
	public int getTAL15Done() {
		return TAL15Done;
	}
	public void setTAL15Done(int tAL15Done) {
		TAL15Done = tAL15Done;
	}
	public int getTAL10goal() {
		return TAL10goal;
	}
	public void setTAL10goal(int tAL10goal) {
		TAL10goal = tAL10goal;
	}
	public int getTAL10done() {
		return TAL10done;
	}
	public void setTAL10done(int tAL10done) {
		TAL10done = tAL10done;
	}
	public int getTAL7goal() {
		return TAL7goal;
	}
	public void setTAL7goal(int tAL7goal) {
		TAL7goal = tAL7goal;
	}
	public int getTAL7done() {
		return TAL7done;
	}
	public void setTAL7done(int tAL7done) {
		TAL7done = tAL7done;
	}
	public int getTAL4goal() {
		return TAL4goal;
	}
	public void setTAL4goal(int tAL4goal) {
		TAL4goal = tAL4goal;
	}
	public int getTAL4done() {
		return TAL4done;
	}
	public void setTAL4done(int tAL4done) {
		TAL4done = tAL4done;
	}
	public int getTAL20goal() {
		return TAL20goal;
	}
	public void setTAL20goal(int tAL20goal) {
		TAL20goal = tAL20goal;
	}
	public int getTAL20done() {
		return TAL20done;
	}
	public void setTAL20done(int tAL20done) {
		TAL20done = tAL20done;
	}
	public MonthTask(String town, String month, String agentid, int tAL15goal, int tAL10goal, int tAL7goal,
			int tAL4goal, int tAL20goal) {
		super();
		this.town = town;
		this.month = month;
		this.agentid = agentid;
		TAL15goal = tAL15goal;
		TAL10goal = tAL10goal;
		TAL7goal = tAL7goal;
		TAL4goal = tAL4goal;
		TAL20goal = tAL20goal;
	}
	public MonthTask() {
		super();
	}
	public Set<MonthTaskSub> getSubSet() {
		return subSet;
	}
	public void setSubSet(Set<MonthTaskSub> subSet) {
		this.subSet = subSet;
	}
	

}
