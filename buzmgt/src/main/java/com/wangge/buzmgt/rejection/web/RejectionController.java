package com.wangge.buzmgt.rejection.web;

import com.wangge.buzmgt.ordersignfor.entity.OrderItem;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderItemService;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.rejection.entity.RejectStatusEnum;
import com.wangge.buzmgt.rejection.entity.Rejection;
import com.wangge.buzmgt.rejection.service.RejectionServive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rejection")
public class RejectionController {
	private static final String SEARCH_OPERTOR = "sc_";

	@Resource
	private RejectionServive rejectionServive;
	@Resource
	private OrderSignforService orderSignforService;
	@Resource
	private OrderItemService orderItemService;

	@RequestMapping(value = "/rejectedList", method = RequestMethod.GET)
	@ResponseBody
	public Page<Rejection> rejectedList(HttpServletRequest request,
														 @PageableDefault(page = 0,size=10,sort={"createTime"},direction= Sort.Direction.DESC) Pageable pageRequest) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
		Page<Rejection> rejectionPage = rejectionServive.findAll(searchParams,pageRequest);
		return rejectionPage;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getRejection(@PathVariable("id") Rejection rejection, Model model) {
		OrderSignfor orderSignfor = orderSignforService.findByOrderNo(rejection.getOrderno());
		List<OrderItem> orderItems = orderItemService.findByOrderNum(rejection.getOrderno());
		model.addAttribute("orderItems",orderItems);
		model.addAttribute("order",orderSignfor);
		model.addAttribute("rejection",rejection);
		return "/rejection/reject_det";
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String confirm(@PathVariable("id") Rejection rejection) {
		rejection.setStatus(RejectStatusEnum.recGoods);
		rejectionServive.save(rejection);
		return "ok";
	}
}
