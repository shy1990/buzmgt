package com.wangge.buzmgt.achieveaward.entity;/**
 * Created by ChenGuop on 2016/10/19.
 */

import com.wangge.buzmgt.achieveset.vo.OrderVo;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.goods.entity.Goods;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 达量奖励收益
 * AwardIncome
 *
 * @author ChenGuop
 * @date 2016/10/19
 */
@Entity
@Table(name = "SYS_INCOME_AWARD")
public class AwardIncome implements Serializable{



	public enum PayStatusEnum{
		STOCK("已出库"),PAY("已支付");
		private String name;
		PayStatusEnum(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}

	@Id
	@SequenceGenerator(name = "idgen", sequenceName = "sys_income_job_seq")
	@GeneratedValue(generator = "idgen", strategy = GenerationType.SEQUENCE)
	private Long  id  ;// 主键
	@Column(name = "USER_ID")
	private String  userId ;// 业务员Id
	@OneToOne
	@JoinColumn(name = "USER_ID", insertable = false, updatable = false)
	private PlanUserVo planUser;// 用户ID

	private Long  awardId  ;// 规则Id
	@Column(name = "GOOD_ID")
	private String  goodId ;// 商品Id
	@OneToOne
	@JoinColumn(name = "GOOD_ID", insertable = false, updatable = false)
	private Goods good ;// 商Id
	@Column(name = "ORDER_NO")
	private String  orderNo  ;// 订单号
	@OneToOne
	@JoinColumn(name = "ORDER_NO",referencedColumnName = "ORDER_NO",insertable = false, updatable = false)
	private OrderVo order;
	private Integer num ;// 数量
	private Float money ;// 金额
	@Enumerated(EnumType.ORDINAL)
	private PayStatusEnum status;//	订单状态（STOCK-已出库，PAY-已支付）
	private Date createDate ;// 创建日期
	@Enumerated(EnumType.STRING)
	private FlagEnum flag = FlagEnum.NORMAL ;// 是否删除：NORMAL-正常，DEL-删除
	private Long planId;//主方案ID
	private Float price;//商品价格

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public PlanUserVo getPlanUser() {
		return planUser;
	}

	public void setPlanUser(PlanUserVo planUser) {
		this.planUser = planUser;
	}

	public Long getAwardId() {
		return awardId;
	}

	public void setAwardId(Long awardId) {
		this.awardId = awardId;
	}

	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public Goods getGood() {
		return good;
	}

	public void setGood(Goods good) {
		this.good = good;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public OrderVo getOrder() {
		return order;
	}

	public void setOrder(OrderVo order) {
		this.order = order;
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

	public PayStatusEnum getStatus() {
		return status;
	}

	public void setStatus(PayStatusEnum status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public FlagEnum getFlag() {
		return flag;
	}

	public void setFlag(FlagEnum flag) {
		this.flag = flag;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "AwardIncome{" +
						"id=" + id +
						", userId='" + userId + '\'' +
						", awardId=" + awardId +
						", goodId='" + goodId + '\'' +
						", orderNo='" + orderNo + '\'' +
						", num=" + num +
						", money=" + money +
						", status=" + status +
						", createDate=" + createDate +
						", flag=" + flag +
						", planId=" + planId +
						", price=" + price +
						'}';
	}
}
