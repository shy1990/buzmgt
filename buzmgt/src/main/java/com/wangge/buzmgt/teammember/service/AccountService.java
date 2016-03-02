package com.wangge.buzmgt.teammember.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.wangge.buzmgt.teammember.vo.AccountBean;

public interface AccountService {
  /**
   * 
   * @Description: 通过职务和账号状态查询账号列表
   * @param @param position
   * @param @param status
   * @param @return   
   * @return List<AccountBean>  
   * @throws
   * @author changjun
   * @date 2016年2月23日
   */
  List<AccountBean> selectAccountByPositionAndStatus(String position,String status,String regionName,PageRequest page);
  
  boolean mofidyPwd(String id);
}
