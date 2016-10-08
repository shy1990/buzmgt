package com.wangge.buzmgt.section.service;

import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-8-19.
 */
public interface ProductionService {


    public Production addProduction(List<PriceRange> priceRanges, String productType, String implementationDate, Long planId);

    public Production review(Long id, String status);//改变状态(渠道审核:通过/驳回)

    //渠道审核
    public void toReview(Long id, String status, String auditorId);

    public Production findById(Long id);//根据id查找

    public List<Production> findNotExpired(Long planId,String type);//查询未过期的(使用中,审核中,被驳回)

    public List<Production> findNotExpiredQd(Long planId,String type);//查询未过期的(使用中,审核中,被驳回)

    public Page<Production> findAll(Long planId,String type, Pageable pageable);

    public Map<String,Object> findNowCW(String type,Long planId);

    public Production save(Production production);

    public List<PriceRange> findReview(Long planId,String type);//查询正在审核中的小区间

    public Production findNow(String type, String time,Long planId);//查询下订单日期使用的方案

    /**
     * @Description: 已支付计算
     * @param orderNo:订单号
     * @param payTime:支付时间("yyyy-MM-dd")
     * @param price:产品价格
     * @param userId:业务员id
     * @param goodsId:产品id
     * @param type:产品类型
     * @param planId:方案id
     * @param num:单品数量
     * @return
     */
    public String compute(String orderNo,Date payTime,
      Double price, String userId,String goodsId,String type,Long planId,Integer num,String regionId);

    /**
     * @Description: 价格区间出库计算
     * @param orderNo:订单id/单品详情id
     * @param price:产品价格
     * @param userId:业务员id
     * @param goodsId:产品id
     * @param type:产品类型
     * @param planId:方案id
     * @param num:单品数量
     * @return
     */
    public String compute(String orderNo,Double price,
        String userId,String goodsId,String type,Long planId,Integer num,String regionId);



    public Production delete(Production production);
}
