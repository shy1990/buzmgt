package com.wangge.buzmgt.achieveaward.pojo;/**
 * Created by ChenGuop on 2016/10/20.
 */

import java.io.Serializable;
import java.util.Date;

/**
 * 订单详情商品用户信息
 * OrderItemVo
 *
 * @author ChenGuop
 * @date 2016/10/20
 */
public class OrderItemVo implements Serializable{
	private String goodId;
	private String orderNo;
	private String name;
	private String userId;
	private Integer nums;
	private Date payTime;
}
