package com.wangge.buzmgt.monthtask.entity;

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.wangge.buzmgt.assess.entity.RegistData;

/**月任务执行记录表
 * @author yangqc
 *
 */
@Entity
@Table(name = "sys_monthtask_execution")
@NamedEntityGraph(name = "monthExecution.graph", attributeNodes = @NamedAttributeNode(value = "registData", subgraph = "registData.graph"), subgraphs = {
		@NamedSubgraph(attributeNodes = { @NamedAttributeNode("region")}, name = "registData.graph") })
public class MonthTaskExecution implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 101L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "memberid")
	private RegistData registData;
	// 任务月份
	private String taskmonth;
	// 任务时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	private Date time = new Date();
	// 动作
	private String action;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RegistData getRegistData() {
		return registData;
	}

	public void setRegistData(RegistData registData) {
		this.registData = registData;
	}

	public String getTaskmonth() {
		return taskmonth;
	}

	public void setTaskmonth(String taskmonth) {
		this.taskmonth = taskmonth;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public MonthTaskExecution() {
	}

	public MonthTaskExecution(RegistData registData, String taskmonth, Date time, String action) {
		super();
		this.registData = registData;
		this.taskmonth = taskmonth;
		this.time = time;
		this.action = action;
	}

}
