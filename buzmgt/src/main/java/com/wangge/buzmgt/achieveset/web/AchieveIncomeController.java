package com.wangge.buzmgt.achieveset.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
import com.wangge.buzmgt.achieveset.service.AchieveIncomeService;
import com.wangge.buzmgt.achieveset.service.AchieveService;
import com.wangge.buzmgt.achieveset.vo.AchieveIncomeVo;
import com.wangge.buzmgt.achieveset.vo.service.AchieveIncomeVoService;
import com.wangge.buzmgt.ordersignfor.entity.OrderItem;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderItemService;
import com.wangge.buzmgt.util.JsonResponse;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenGuop on 2016/9/27.
 */
@Controller
@RequestMapping(value = "/achieveIncome")
public class AchieveIncomeController {

	@Autowired
	private AchieveService achieveService;
	@Autowired
	private OrderItemService orderItemService;
	@Autowired
	private AchieveIncomeService achieveIncomeService;
	@Autowired
	private AchieveIncomeVoService achieveIncomeVoService;

	private static final String SEARCH_OPERTOR = "sc_";

	/**
	 * 达量设置收益页面跳转
	 * /A370121232435
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{userId}")
	public String showList(@PathVariable("userId") String userId, @RequestParam("yearMonth") String yearMonth, Model model) {
		model.addAttribute("userId", userId);
		model.addAttribute("yearMonth", yearMonth);
		return "achieve/achieve_income";
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Page<AchieveIncome> list(@PageableDefault(page = 0, size = 10, sort = {"createDate"}) Pageable pageable, HttpServletRequest request) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
		return achieveIncomeService.findAll(searchParams, pageable);
	}

	@RequestMapping(value = "/total", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public Page<AchieveIncomeVo> findAllAchieveIncomeVo(@PageableDefault(page = 0, size = 10, sort = {"RNID"}) Pageable pageable, HttpServletRequest request) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
		return achieveIncomeVoService.findAll(searchParams, pageable);
	}

	/**
	 * 订单详情的收益
	 * @param model
	 * @param orderSignfor
	 * @param ruleId
	 * @return
	 */
	@RequestMapping(value = "/detail/{orderId}", method = RequestMethod.GET)
	public String incomeOrderDetail(Model model,@PathVariable("orderId") OrderSignfor orderSignfor,String ruleId,String userId) {
		if (orderSignfor != null){
			orderSignfor.getOrderNo();
			//1.处理详情
			orderItemService.disposeOrderSignfor(orderSignfor);
			//2.处理收益（前提条件处理详情）
			achieveIncomeService.disposeIncomeForOrderItem(orderSignfor,ruleId,userId);
			model.addAttribute("order",orderSignfor);
		}
		return "achieve/income_order_detial";
	}
}
