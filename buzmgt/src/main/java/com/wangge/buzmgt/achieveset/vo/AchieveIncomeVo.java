package com.wangge.buzmgt.achieveset.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * AchieveIncome统计
 * AchieveIncomeVo
 *
 * @author ChenGuop
 * @date 2016/10/9
 */
@Entity
@Table(name = "VIEW_INCOME_ACHIEVE")
public class AchieveIncomeVo implements Serializable{

	@Id
	private Integer RNID;
	@Column(name = "ACHIEVE_ID")
	private Long achieveId;
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "ACHIEVE_ID", updatable = false, insertable = false)
//	private Achieve achieve;
	@Column(name = "USER_ID")
	private String userId;
	@OneToOne
	@JoinColumn(name = "USER_ID", updatable = false, insertable = false)
	private PlanUserVo userVo;
	private Integer num;
	private Float money;

	public Integer getRNID() {
		return RNID;
	}

	public Long getAchieveId() {
		return achieveId;
	}

//	public Achieve getAchieve() {
//		return achieve;
//	}

	public String getUserId() {
		return userId;
	}

	public PlanUserVo getUserVo() {
		return userVo;
	}

	public Integer getNum() {
		return num;
	}

	public Float getMoney() {
		return money;
	}

	public void setRNID(Integer RNID) {
		this.RNID = RNID;
	}

	public void setAchieveId(Long achieveId) {
		this.achieveId = achieveId;
	}

//	public void setAchieve(Achieve achieve) {
//		this.achieve = achieve;
//	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserVo(PlanUserVo userVo) {
		this.userVo = userVo;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "AchieveIncomeVo:[ RNID = " + RNID + ", achieveId =" + achieveId + ", userId=" + userId + ",num= " + num + ", money=" + money + "]";
	}
}
