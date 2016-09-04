package com.wangge.buzmgt.section.service;

import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;
import com.wangge.buzmgt.section.repository.PriceRangeRepository;
import com.wangge.buzmgt.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by joe on 16-8-29.
 */
@Service
public class PriceRangeServiceImpl implements PriceRangeService {

    @Autowired
    private PriceRangeRepository priceRangeRepository;

    @Autowired
    private ProductionService productionService;

    /**
     * 修改价格区间,也就是新增一套
     *
     * @param auditorId:审核人id
     * @param percentage
     * @param implDate
     * @param priceRange:数据库中原来的数据
     */
    @Override
    public void modifyPriceRange(Long productionId, String auditorId, Double percentage, String implDate, PriceRange priceRange) {

        //第一种方式使用
//        PriceRange savePriceRange = new PriceRange();
//        priceRange.setOldId(priceRange.getPriceRangeId());
//        priceRange.setPriceRangeStatus("2");//审核中
//        priceRange.setPriceRangeCreateDate(new Date());
//        priceRange.setPercentage(percentage);
//        priceRange.setPriceRangeAuditor(auditorId);
//        priceRange.setImplementationDate(DateUtil.string2Date(implDate));
//        priceRange.setPriceRangeId(null);
//        savePriceRange = priceRange;
//        priceRangeRepository.save(savePriceRange);

        PriceRange savePriceRange = new PriceRange();
        savePriceRange.setImplementationDate(DateUtil.string2Date(implDate));//实施日期
        savePriceRange.setPriceRangeAuditor(auditorId);//指定审核人
        savePriceRange.setPriceRange(priceRange.getPriceRange());//设置价格区间
        savePriceRange.setOldId(priceRange.getPriceRangeId());//原来区间的id
        savePriceRange.setPercentage(percentage);//提成
        savePriceRange.setPriceRangeStatus("1");//审核中
        savePriceRange.setSerialNumber(priceRange.getSerialNumber());
        savePriceRange.setPriceRangeCreateDate(new Date());

        Production production = productionService.findById(productionId);
        if (production != null) {
            production.getPriceRanges().add(savePriceRange);
            productionService.save(production);
        }
    }

    /**
     * 审核修改的小区间
     * @param priceRange
     * @param status:3-通过,2-驳回
     */
    @Override
    public void reviewPriceRange(PriceRange priceRange,String status) {
        //审核通过,需要设置上一个的结束时间
        if("3".equals(status)){
            PriceRange priceRange1 = priceRangeRepository.findByPriceRangeId(priceRange.getOldId());
            Date endTime1 = DateUtil.moveDate(priceRange.getImplementationDate(), -1);//结束时间减去一天
            priceRange1.setEndTime(endTime1);//结束日期
            priceRangeRepository.save(priceRange1);
        }

        priceRange.setPriceRangeStatus(status);//设置状态
        priceRangeRepository.save(priceRange);

    }
}
