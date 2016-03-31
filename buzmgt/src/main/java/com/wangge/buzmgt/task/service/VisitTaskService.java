package com.wangge.buzmgt.task.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.sys.vo.CustomerVo;
import com.wangge.buzmgt.task.entity.Visit;

/**
 * 
  * ClassName: VisitTaskService <br/> 
  * date: 2016年3月17日 下午4:16:54 <br/> 
  * @author peter 
  * @version  
  * @since JDK 1.8
 */
public interface VisitTaskService {
	
  Page<CustomerVo> getshopList(int pageNum,int limit,String regionName,int status,int condition);
  
  void addVisit(Visit visit);
  
  String findMaxLastVisit(Long registId);
  
  Page<Visit> getVisitData(int pageNum,int limit,String regionName);
  
  List<CustomerVo> getshopMap(String regionName,int status,int condition);
}
