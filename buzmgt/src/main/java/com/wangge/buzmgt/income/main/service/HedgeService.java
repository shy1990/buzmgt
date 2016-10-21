package com.wangge.buzmgt.income.main.service;

import com.wangge.buzmgt.achieveaward.entity.Award;
import com.wangge.buzmgt.income.main.vo.HedgeVo;
import com.wangge.buzmgt.region.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * ClassName: 售后冲减服务类 <br/>
 * date: 2016年9月7日 下午3:58:35 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
public interface HedgeService {
  
  /**
   * 保存从excle导出的记录. <br/>
   * @throws Exception 
   * 
   */
  void saveHedgeFromExcle(Map<Integer, String> excelContent) throws Exception;

  /** 
    * 查询视图数据 <br/> 
    */  
  Page<HedgeVo> getVopage(Pageable pageReq, Map<String, Object> searchParams);

  /**
   *
   * @Title: countByGoodId
   * @Description: 根据goodId统计该周期内的提货量
   * @param @param brandIncome
   * @param @return    设定文件
   * @return int    返回类型
   * @throws
   */
  int countByGoodId(String goodId);

  /**
   *
   * @Title: countByGoodId
   * @Description: 根据goodId统计该周期内的提货量
   * @param @param brandIncome
   * @param @return    设定文件
   * @return int    返回类型
   * @throws
   */
  int countByGoodId(List<String> goodIds);

  /**
   *
   * @Title: findAll
   * @Description: 根据条件统计退货冲减订单(品牌型号,达量奖励)
   * @param @param request
   * @param @param region
   * @param @param pageable
   * @param @return    设定文件
   * @return Page<HedgeVo>    返回类型
   * @throws
   */
  Page<HedgeVo> findAll(HttpServletRequest request, Region region, Award award, Pageable pageable);

  /**
   *
   * @Title: findAll
   * @Description: 根据条件统计退货冲减订单(品牌型号)
   * @param @param request
   * @param @param region
   * @param @return    设定文件
   * @return Page<HedgeVo>    返回类型
   * @throws
   */
  List<HedgeVo> findAll(HttpServletRequest request, Region region, Award award);
  
  void calculateHedge();

  /** 
    * 计算当天导入的达量和叠加冲减信息
    *  <br/> 
    */  
  void calculateAchieveHedge(Date exectime);
}
