package com.wangge.buzmgt.income.ywsalary.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.income.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalaryUser;

public interface BaseSalaryService {
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
   * 查询待添加业务员
   * 
   * @return
   */
  public List<BaseSalaryUser> getStaySetSalesMan();

  /** 
    * deleteSalaryByUser:将某业务员的工资作废.<br/> 
    * @author yangqc 
    * @param userId  业务员id
    * @throws ParseException 
    * @since JDK 1.8 
    */  
  void deleteSalaryByUser(String userId) throws ParseException;

  /** 
    * 计算某个业务员本月的工资. <br/> 
    */  
  Double calculateThisMonthBasicSalary(String salesId) throws ParseException;
  /** 
    * update:薪资记录更新处理处理. <br/> 
    * @author yangqc 
    * @param baseSalary 旧工资记录
    * @param salary  新工资
   * @param upDate 
   * @throws Exception 
    * @since JDK 1.8 
    */  
  public void update(BaseSalary baseSalary, Double salary, String upDate) throws Exception;

  public List<Map<String, Object>> findAll1(Map<String, Object> searchParams, Pageable pageRequest);

  void calcuThisMonthSalarys() throws  Exception;
  
  void calux();
}
