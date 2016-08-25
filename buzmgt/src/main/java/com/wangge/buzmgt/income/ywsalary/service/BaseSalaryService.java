package com.wangge.buzmgt.income.ywsalary.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.income.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalaryUser;

public interface BaseSalaryService {
  /**
   * 查询基础薪资记录
   * 
   * @param searchParams
   * @return
   */
  public List<BaseSalary> findAll(Map<String, Object> searchParams);
  
  /**
   * 查询分页基础薪资记录(只查询姓名,区域)
   * 
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public Map<String, Object> findAll(Map<String, Object> searchParams, Pageable pageRequest);
  
  /**
   * 保存数据
   * 
   * @param baseSalary
   * @return
   * @throws Exception
   */
  public BaseSalary save(BaseSalary baseSalary) throws Exception;
  
  /**
   * 删除数据
   * 
   * @param baseSalary
   * @return
   * @throws ParseException
   */
  
  public void delete(BaseSalary baseSalary) throws ParseException;
  

  
  /**
   * 查询待添加业务员
   * 
   * @return
   */
  public List<BaseSalaryUser> getStaySetSalesMan();

  /** 
    * deleteSalaryByUser:将某业务员的工资作废.<br/> 
    * @author yangqc 
    * @param userId
    * @throws ParseException 
    * @since JDK 1.8 
    */  
  void deleteSalaryByUser(String userId) throws ParseException;
  
}
