package com.wangge.buzmgt.assess.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.assess.repository.AssessRepository;

/**
 * 
  * ClassName: RoleServiceImpl <br/> 
  * Function: TODO ADD FUNCTION. <br/> 
  * Reason: TODO ADD REASON(可选). <br/> 
  * date: 2016年2月19日 上午10:58:40 <br/> 
  * 
  * @author peter 
  * @version  1.1
  * @since JDK 1.8
 */
@Service
public class AssessServiceImpl implements AssessService {
  @Resource
  private AssessRepository assessRepository;
  
  @Override
  public void saveAssess(Assess assess) {
    assessRepository.save(assess);
  }
	
}
