package com.wangge.buzmgt.achieveset.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private Date issuingDate;//发放日期
	private String goodName;
	private Integer shNum;//售后冲减数量

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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getIssuingDate() {
		return issuingDate;
	}

	public void setIssuingDate(Date issuingDate) {
		this.issuingDate = issuingDate;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public Integer getShNum() {
		return shNum;
	}

	public void setShNum(Integer shNum) {
		this.shNum = shNum;
	}

	@Override
	public String toString() {
		return "AchieveIncomeVo{" +
						"RNID=" + RNID +
						", achieveId=" + achieveId +
						", userId='" + userId + '\'' +
						", num=" + num +
						", money=" + money +
						", status=" + status +
						", startDate=" + startDate +
						", endDate=" + endDate +
						", issuingDate=" + issuingDate +
						", goodName='" + goodName + '\'' +
						", shNum=" + shNum +
						'}';
	}
}
