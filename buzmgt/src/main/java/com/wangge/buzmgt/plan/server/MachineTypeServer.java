package com.wangge.buzmgt.plan.server;

import java.util.List;

import com.wangge.buzmgt.plan.entity.MachineType;

/**
 * 
* @ClassName: MachineTypeServer
* @Description: 处理机型的业务逻辑
* @author ChenGuop
* @date 2016年8月17日 下午3:57:56
*
 */
public interface MachineTypeServer {

  /**
   * 查询所有机型
  * @Title: findAll 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @return    设定文件 
  * @return List<MachineType>    返回类型 
  * @throws
   */
  List<MachineType> findAll();
  
  /**
   * 根据code查询机型
  * @Title: findByCode 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param code
  * @param @return    设定文件 
  * @return MachineType    返回类型 
  * @throws
   */
  MachineType findByCode(String code);
  
  
}
