package com.wangge.buzmgt.plan.service;

import java.util.List;

import com.wangge.buzmgt.plan.entity.GroupNumber;

public interface GroupNumberService {

  /**
   * 
  * @Title: delete 
  * @Description: 删除分组数据
  * @param @param id    设定文件 
  * @return void    返回类型 
  * @throws
   */
  void delete(Long id);

  /**
   * 
  * @Title: delete 
  * @Description: 删除列表哦
  * @param @param groupNumbers    设定文件 
  * @return void    返回类型 
  * @throws
   */
  void delete(List<GroupNumber> groupNumbers);
  
}
