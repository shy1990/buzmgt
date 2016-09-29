package com.wangge.buzmgt.brandincome.util;

import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import org.springframework.context.annotation.Configuration;

import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: IncomeCalUtil
* @Description: 品牌型号收益计算类
* @author peter
* @date 2016年9月26日
*
 */
@Configuration
public class IncomeCalUtil extends TagSupport {

  private static final long serialVersionUID = 1L;

  /**
   * findRuleByGood:(这里用一句话描述这个方法的作用). <br/>
   * 通过商品id查询其对应的规则
   *
   * @author yangqc
   * @param goodIds
   * @param mainPlanId
   * @param userId
   * @return
   * @since JDK 1.8
   */
  List<Map<String,Object>> findRuleByGoods(List<String> goodIds, Long mainPlanId, String userId){
    List<Map<String,Object>> list = new ArrayList<>();
    return list;
  }


  public static String realTimeIncome(Object obj) {
    return "";
  }
  
}
