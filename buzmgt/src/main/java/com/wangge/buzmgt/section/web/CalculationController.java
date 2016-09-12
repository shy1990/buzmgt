package com.wangge.buzmgt.section.web;

import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;
import com.wangge.buzmgt.section.service.ProductionService;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 16-8-25.
 */
@Controller
@RequestMapping("calculation")
public class CalculationController {
    @Autowired
    private ProductionService productionService;

    /**
     * 计算提成是多少
     *
     * @param type
     * @param price
     * @param time
     * @return
     */
    @RequestMapping(value = "ceshi", method = RequestMethod.GET)
    @ResponseBody
    public List<PriceRange> calculation(@RequestParam String type, @RequestParam Double price, @RequestParam String time) {

        Production p = productionService.findNow(type, time);//订单日期正在使用的
        List<PriceRange> priceRanges = null;
        //判断price是属于那个区间的
        if (ObjectUtils.notEqual(p, null)) {
            priceRanges = p.getPriceRanges();
        }
        List<PriceRange> priceRanges1 = new ArrayList<PriceRange>();
        //查询出正在用的区间
        if (CollectionUtils.isNotEmpty(priceRanges)) {
            priceRanges.forEach(priceRange -> {
                String impl = DateUtil.date2String(priceRange.getImplementationDate());
                String endTime = DateUtil.date2String(priceRange.getEndTime());
                try {
                    //结束日期不是null,审核通过,并且当前时间比结束时间小
                    if (priceRange.getEndTime() != null && "3".equals(priceRange.getPriceRangeStatus()) && !DateUtil.compareDate(endTime, time)) {
                        priceRanges1.add(priceRange);//保留个区间
                    }

                    //结束时间不是null,并且当前时间在开始时间之后
                    if (priceRange.getEndTime() == null && "3".equals(priceRange.getPriceRangeStatus()) && DateUtil.compareDate(impl, time)) {
                        priceRanges1.add(priceRange);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });

        }

        //判断价格属于哪个区间
        if (CollectionUtils.isNotEmpty(priceRanges1)) {
            priceRanges1.forEach(priceRange -> {
                String[] prices = priceRange.getPriceRange().split("-");
                if (Double.parseDouble(prices[0]) <= price && price < Double.parseDouble(prices[1])) {
                    System.out.println("价格是: " + priceRange.getPriceRange());
                }

            });
        }


        System.out.println(priceRanges1);
        return priceRanges1;
    }


}
