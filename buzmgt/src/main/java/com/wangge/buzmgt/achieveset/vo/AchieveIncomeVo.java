package com.wangge.buzmgt.achieveset.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
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
	@Enumerated
	private AchieveIncome.PayStatusEnum status;

	public Integer getRNID() {
		return RNID;
	}

	public void setRNID(Integer RNID) {
		this.RNID = RNID;
	}

	public Long getAchieveId() {
		return achieveId;
	}

	public void setAchieveId(Long achieveId) {
		this.achieveId = achieveId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public PlanUserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(PlanUserVo userVo) {
		this.userVo = userVo;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public AchieveIncome.PayStatusEnum getStatus() {
		return status;
	}

	public void setStatus(AchieveIncome.PayStatusEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AchieveIncomeVo:[ RNID = " + RNID + ", achieveId =" + achieveId + ", userId=" + userId + ",num= " + num + ", money=" + money + "]";
	}
}
