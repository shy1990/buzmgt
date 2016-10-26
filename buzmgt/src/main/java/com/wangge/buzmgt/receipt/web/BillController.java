package com.wangge.buzmgt.receipt.web;


import com.wangge.buzmgt.ordersignfor.entity.Order;
import com.wangge.buzmgt.ordersignfor.entity.OrderItem;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderItemService;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.receipt.entity.BillSalesmanVo;
import com.wangge.buzmgt.receipt.entity.BillVo;
import com.wangge.buzmgt.receipt.entity.Receipt;
import com.wangge.buzmgt.receipt.service.ReceiptService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bill")
public class BillController {
	private static final String SEARCH_OPERTOR = "sc_";

	@Resource
	private ReceiptService receiptService;

	@Resource
	private OrderSignforService orderSignforService;

	@Resource
	private OrderItemService orderItemService;

	/**
	 * 去到对账单列表页面
   */
	@RequestMapping(value="/show",method= RequestMethod.GET)
	public String show(){
		return  "/receipt/bill_list";
	}

	/**
	 *列表查询
	 * @param page
	 * @param date
	 * @param salesmanName
	 * @param
	 * @return
   */
	@RequestMapping(value="/showBillList",method= RequestMethod.GET)
	@ResponseBody
	public Page<BillVo> showBillList(int page,String date,String salesmanName, HttpServletRequest request){

		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
		Page<BillVo> BillVoPage = receiptService.findAllBill(date,salesmanName,page);

		return  BillVoPage;
	}


	/**
	 *
	 * @param userId
	 * @param todayAllShouldPay
	 * @param todayShouldPay
	 * @param historyShouldPay
	 * @param todayDate
   * @return
   */
	@RequestMapping(value="/persionBillView",method= RequestMethod.GET)
	public String persionBillView(String userId,String todayAllShouldPay,String todayShouldPay,String historyShouldPay,String todayDate,Model model){

		model.addAttribute("todayAllShouldPay",todayAllShouldPay);
		model.addAttribute("todayShouldPay",todayShouldPay);
		model.addAttribute("historyShouldPay",historyShouldPay);
		model.addAttribute("userId",userId);
		model.addAttribute("todayDate",todayDate);
		return  "/receipt/person_bill_view";
	}


	/**
	 *详情列表查询
	 * @param page
	 * @param date
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value="/showOrderPayList",method= RequestMethod.GET)
	@ResponseBody
	public Page<BillSalesmanVo> showOrderPayList(int page, String date, String userId,
																							 @PageableDefault(page = 0,size=10,sort={"createTime"},direction= Sort.Direction.DESC) Pageable pageRequest ,
																							 HttpServletRequest request){

		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
		Page<BillSalesmanVo> orderSignforPage = receiptService.findByUserIdAndCreateTime(userId,date,page);




		return  orderSignforPage;
	}

	/**
	 *
	 * @param orderNum
	 * @param model
	 * @return
   */
	@RequestMapping(value="/shopBillView",method= RequestMethod.GET)
	public String shopBillView(String orderNum,Model model){

	/*	model.addAttribute("todayAllShouldPay",todayAllShouldPay);
		model.addAttribute("todayShouldPay",todayShouldPay);
		model.addAttribute("historyShouldPay",historyShouldPay);
		model.addAttribute("userId",userId);*/

		Order order=orderItemService.findOrderByOrderNum(orderNum);

		List<OrderItem> listOrderItem=orderItemService.findByOrderNum(orderNum);

		int phoneCount=0;
		int accessoryCount=0;
		for(OrderItem orderItem:listOrderItem){
				if(orderItem.getType().equals("sku")){
					phoneCount++;
				}else{
					accessoryCount++;
				}
		}

		OrderSignfor orderSignfor=orderSignforService.findByOrderNo(orderNum);

		List<Receipt> listReceipt=receiptService.findByOrderno(orderNum);

		model.addAttribute("order",order);
		model.addAttribute("listOrderItem",listOrderItem);
		model.addAttribute("phoneCount",phoneCount);
		model.addAttribute("accessoryCount",accessoryCount);
		model.addAttribute("orderSignfor",orderSignfor);
		model.addAttribute("listReceipt",listReceipt);
		return  "/receipt/person_bill_details";
	}
}
