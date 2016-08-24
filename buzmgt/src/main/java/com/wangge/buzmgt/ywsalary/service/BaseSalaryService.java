package com.wangge.buzmgt.ywsalary.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.ywsalary.entity.BaseSalaryUser;

public interface BaseSalaryService {
  /**
   * 查询基础薪资记录
   * @param searchParams
   * @return
   */
  public  List<BaseSalary> findAll(Map<String, Object> searchParams);
  
  /**
   * 查询分页基础薪资记录
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public  Page<BaseSalary> findAll(Map<String, Object> searchParams, Pageable pageRequest);

  /**
   * 保存数据
   * @param baseSalary
   * @return
   */
  public BaseSalary save(BaseSalary baseSalary);
  
  /**
   * 删除数据
   * @param baseSalary
   * @return
   */

  public void delete(BaseSalary baseSalary);

  /**
   * 查询待添加业务员
   * @return
   */
  public List<BaseSalaryUser> getStaySetSalesMan();
  
}
