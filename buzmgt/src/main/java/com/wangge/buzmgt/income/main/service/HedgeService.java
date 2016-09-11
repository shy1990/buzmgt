package com.wangge.buzmgt.income.main.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.income.main.vo.HedgeVo;

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
}
