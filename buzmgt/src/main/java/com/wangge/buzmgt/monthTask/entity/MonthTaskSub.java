package com.wangge.buzmgt.monthTask.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sys_monthtask_sub")
@NamedEntityGraph(name = "monthTaskSub.monthsd", attributeNodes = {
		@NamedAttributeNode(value = "monthsd", subgraph = "monthShopD_Regi"),
		@NamedAttributeNode(value = "monthTask") }, subgraphs = {
				@NamedSubgraph(name = "monthShopD_Regi", attributeNodes = {
						@NamedAttributeNode(value = "registData", subgraph = "region.graph") }) }, subclassSubgraphs = {
								@NamedSubgraph(name = "region.graph", attributeNodes = @NamedAttributeNode(value = "region", subgraph = "regionRe.region")) })
public class MonthTaskSub implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private long id;
	private Integer goal = 0;
	private Integer done = 0;

	private Date lastTime = new Date();
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	MonthTask monthTask;
	private Integer finish = 0;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dataId")
	private MonthshopBasData monthsd;
	// 是否延期提醒
	private Integer delay = 0;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MonthTask getMonthTask() {
		return monthTask;
	}

	public void setMonthTask(MonthTask monthTask) {
		this.monthTask = monthTask;
	}

	public MonthshopBasData getMonthsd() {
		return monthsd;
	}

	public void setMonthsd(MonthshopBasData monthsd) {
		this.monthsd = monthsd;
	}

	public Integer getGoal() {
		return goal;
	}

	public void setGoal(Integer goal) {
		this.goal = goal;
	}

	public Integer getDone() {
		return done;
	}

	public void setDone(Integer done) {
		this.done = done;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getFinish() {
		return finish;
	}

	public void setFinish(Integer finish) {
		this.finish = finish;
	}

	public Integer getDelay() {
		return delay == null ? 0 : delay;
	}

	public void setDelay(Integer delay) {
		this.delay = delay;
	}

	public MonthTaskSub() {
	}

}
