package com.wangge.buzmgt.achieveset.web;

import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
import com.wangge.buzmgt.achieveset.service.AchieveIncomeService;
import com.wangge.buzmgt.achieveset.service.AchieveService;
import com.wangge.buzmgt.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
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
	private AchieveIncomeService achieveIncomeService;

	private static final String SEARCH_OPERTOR = "sc_";

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Page<AchieveIncome> list(@PageableDefault(page = 0, size = 10, sort = {"createDate"}) Pageable pageable, HttpServletRequest request) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
		return achieveIncomeService.findAll(searchParams, pageable);
	}

	@RequestMapping("/test")
	@ResponseBody
	public JsonResponse<List<Map<String, Object>>> test(@RequestParam String goodId, @RequestParam Long planId, @RequestParam String userId, @RequestParam String orderNo) {
		JsonResponse<List<Map<String, Object>>> jsonResponse = new JsonResponse<>();
		List<String> goodIds = Arrays.asList(goodId.split(","));
		List<Map<String, Object>> achieveRule = achieveService.findRuleByGoods(goodIds, planId, userId);
		if (achieveRule.size() <= 0) {
			jsonResponse.setErrorMsg("没有此商品的规则！");
			return jsonResponse;
		}
		try {
			achieveRule.forEach(ruleMap -> {
				achieveIncomeService.createAchieveIncomeByStock((Achieve) ruleMap.get("rule"), orderNo, userId, 2, (String) ruleMap.get("goodId"), 0, planId);
			});
		} catch (Exception e) {
			jsonResponse.setErrorCode("500");
			jsonResponse.setErrorMsg("网络异常，稍后重试！");
			return jsonResponse;
		}
		jsonResponse.setResult(achieveRule);
		jsonResponse.setSuccessMsg("操作成功");
		return jsonResponse;
	}
}
