package com.wangge.buzmgt.ywsalary.repository;

import java.util.List;

import com.wangge.buzmgt.ywsalary.entity.BaseSalaryUser;

public interface BaseSalaryRepositoryCustom {
  //查询待设置业务人员
  public List<BaseSalaryUser> getStaySetSalesMan();
}
