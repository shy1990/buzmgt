package com.wangge.buzmgt.achieveset.web;

import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.service.AchieveIncomeService;
import com.wangge.buzmgt.achieveset.service.AchieveService;
import com.wangge.buzmgt.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by ydhl on 2016/9/27.
 */
@Controller
@RequestMapping(value = "/achieveIncome")
public class AchieveIncomeController {

	@Autowired
	private AchieveService achieveService;
	@Autowired
	private AchieveIncomeService achieveIncomeService;

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
				achieveIncomeService.createAchieveIncomeByStock((Achieve) ruleMap.get("rule"), orderNo, userId, 2, (String) ruleMap.get("goodId"), 0);
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
