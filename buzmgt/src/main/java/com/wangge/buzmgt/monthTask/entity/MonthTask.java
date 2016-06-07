package com.wangge.buzmgt.monthTask.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "sys_monthtask_main")

@NamedEntityGraph(name = "monthData", attributeNodes = {
		@NamedAttributeNode(value = "monthData", subgraph = "salesman") }, subgraphs = {
				@NamedSubgraph(name = "salesman", attributeNodes = {
						@NamedAttributeNode(value = "salesman", subgraph = "user") }) }, subclassSubgraphs = {
								@NamedSubgraph(name = "user", attributeNodes = @NamedAttributeNode(value = "user")),
								@NamedSubgraph(name = "region", attributeNodes = @NamedAttributeNode(value = "region")) })

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
	// 业务员任务目标达为15的店铺数
	private int tal15goal;
	// 业务员任务中已达到15目标的完成数
	private int tal15done;

	private int tal10goal;
	private int tal10done;
	private int tal7goal;
	private int tal7done;
	private int tal4goal;
	private int tal4done;
	private int tal20goal;
	private int tal20done;
	// 20标准子任务的设置数
	private int tal20set;
	private int tal15set;
	private int tal10set;
	private int tal7set;
	private int tal4set;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basdataid")
	private MonthOdersData monthData;
	// 状态0未发布,1已发布
	private int status;
	// 罚款数
	private int punishrate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRegionid() {
		return regionid;
	}

	public void setRegionid(String regionid) {
		this.regionid = regionid;
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

	public int getTal15goal() {
		return tal15goal;
	}

	public void setTal15goal(int tal15goal) {
		this.tal15goal = tal15goal;
	}

	public int getTal15done() {
		return tal15done;
	}

	public void setTal15done(int tal15done) {
		this.tal15done = tal15done;
	}

	public int getTal10goal() {
		return tal10goal;
	}

	public void setTal10goal(int tal10goal) {
		this.tal10goal = tal10goal;
	}

	public int getTal10done() {
		return tal10done;
	}

	public void setTal10done(int tal10done) {
		this.tal10done = tal10done;
	}

	public int getTal7goal() {
		return tal7goal;
	}

	public void setTal7goal(int tal7goal) {
		this.tal7goal = tal7goal;
	}

	public int getTal7done() {
		return tal7done;
	}

	public void setTal7done(int tal7done) {
		this.tal7done = tal7done;
	}

	public int getTal4goal() {
		return tal4goal;
	}

	public void setTal4goal(int tal4goal) {
		this.tal4goal = tal4goal;
	}

	public int getTal4done() {
		return tal4done;
	}

	public void setTal4done(int tal4done) {
		this.tal4done = tal4done;
	}

	public int getTal20goal() {
		return tal20goal;
	}

	public void setTal20goal(int tal20goal) {
		this.tal20goal = tal20goal;
	}

	public int getTal20done() {
		return tal20done;
	}

	public void setTal20done(int tal20done) {
		this.tal20done = tal20done;
	}

	public int getTal20set() {
		return tal20set;
	}

	public void setTal20set(int tal20set) {
		this.tal20set = tal20set;
	}

	public int getTal15set() {
		return tal15set;
	}

	public void setTal15set(int tal15set) {
		this.tal15set = tal15set;
	}

	public int getTal10set() {
		return tal10set;
	}

	public void setTal10set(int tal10set) {
		this.tal10set = tal10set;
	}

	public int getTal7set() {
		return tal7set;
	}

	public void setTal7set(int tal7set) {
		this.tal7set = tal7set;
	}

	public int getTal4set() {
		return tal4set;
	}

	public void setTal4set(int tal4set) {
		this.tal4set = tal4set;
	}

	public MonthOdersData getMonthData() {
		return monthData;
	}

	public void setMonthData(MonthOdersData monthData) {
		this.monthData = monthData;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPunishrate() {
		return punishrate;
	}

	public void setPunishrate(int punishrate) {
		this.punishrate = punishrate;
	}
	public MonthTask() {
		super();
	}

	public MonthTask(String regionid, String month, int tAL15goal, int tAL10goal, int tAL7goal, int tAL4goal,
			int tAL20goal) {
		super();
		this.regionid = regionid;
		this.month = month;
		tal15goal = tAL15goal;
		tal10goal = tAL10goal;
		tal7goal = tAL7goal;
		tal4goal = tAL4goal;
		tal20goal = tAL20goal;
	}

	

}
