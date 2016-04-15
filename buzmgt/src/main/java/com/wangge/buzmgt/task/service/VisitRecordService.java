package com.wangge.buzmgt.task.service;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.sys.vo.VisitVo;
import com.wangge.buzmgt.task.entity.Visit;

/**
 * 
  * ClassName: VisitRecordService <br/> 
  * date: 2016年3月22日 下午3:25:52 <br/> 
  * @author peter 
  * @version  
  * @since JDK 1.8
 */
public interface VisitRecordService {
	
  Page<VisitVo> getVisitData(int pageNum,int limit,String regionName,String begin,String end);
  
  int getTotalVisit(String regionName,String begin,String end);
  
  Page<Visit> getVisitYWData(int pageNum,int limit,String userId,String begin,String end);
  
  Visit findById(Long visitId);
}
