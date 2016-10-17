package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.superposition.entity.Superposition;
import com.wangge.buzmgt.superposition.entity.SuperpositionRecord;
import com.wangge.buzmgt.superposition.pojo.SuperpositionProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-9-7.
 */
public interface SuperpositonService {

    public void changeStatus(Superposition superposition,String checkStatus);//终止方案(逻辑删除)

    /**
     * 叠加收益计算
     * @param planId
     * @param superId
     * @return
     */

    public List<SuperpositionProgress> compute(Long planId,Long superId) throws Exception;

    /**
     * 用户退后冲减计算
     * @param userId 业务员id
     * @param goodsId 商品id
     * @param payTime 支付时间
     * @param num 退货数量
     * @param planId 主方案id
     * @param receivingTime 售后收货时间
     * @return
     */

    public Superposition computeAfterReturnGoods(String userId,String goodsId,String payTime,Integer num,Long planId,String receivingTime,Long hedgeId);

    /**
     * 一单达量收益计算
     * @param planId
     * @param superId
     */
    public void computeOneSingle(Long planId,Long superId);

    public void computeOneSingleAfterReturnGoods(String userId,Long planId,String orderId,String goodsId,String payTime,String receivingTime,Integer nums);



    public Page<SuperpositionProgress> searchDetail(Long planId,Long superId,String userId,String startDate,String endDate,String name,Integer page,Integer size);

    public Page<SuperpositionProgress> findAll(Long planId,Long superId,String startDate,String endDate,String name,Integer page,Integer size);

    public Superposition save(Superposition superposition);

    public void delete(Long id);

    public Superposition checkMember(Superposition superposition,String memberId);

    public Superposition findById(Long id);

    public Page<Superposition> findAll(Pageable pageable,String type,String sign,Long planId);

    public Page<PlanUserVo> findMainPlanUsers(Pageable pageReq, Map<String, Object> searchParams) throws Exception;

    public String compute(Superposition superposition);//计算收益

    //查找冲减商品
    public SuperpositionRecord getBySalesmanIdAndPlanIdAndSuperIdAndStatus(String userId, Long planId, Long superId, String status);


}
