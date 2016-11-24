package com.wangge.buzmgt.chartlist.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.chartlist.repository.ChartListRepository;
import com.wangge.buzmgt.dto.ChartDto;
import com.wangge.buzmgt.util.DateUtil;

@Service
public class ChartListServiceImpl implements ChartListService {
  @Autowired
  private  ChartListRepository chartRepository;

  @Override
  public ChartDto getOutboundChart(String regionId, String date) throws ParseException {
    
    ChartDto dto = chartRepository.queryByShipStatus(regionId, !StringUtils.isEmpty(date) ? date :  DateUtil.date2String(new Date()));
    
    return dto;
  }

  @Override
  public ChartDto getCashChart(String regionId, String date) throws ParseException {
    ChartDto dto = chartRepository.queryByDeelType(regionId, !StringUtils.isEmpty(date) ? date : DateUtil.date2String(new Date()));
    return dto;
  }

  /**
   * 
    * TODO 简单描述该方法的实现功能（获取未付款未报备）. 
    * @see com.wangge.buzmgt.chartlist.service.ChartListService#getReportChart(java.lang.String, java.lang.String)
   */
  @Override//paid 
  public ChartDto getunReportChart(String regionId, String date)
      throws ParseException {
    ChartDto dto = chartRepository.queryByPayStatus(regionId, !StringUtils.isEmpty(date) ? date : DateUtil.date2String(new Date()));
    return dto;
  }
  
  @Override//paid 
  public ChartDto getReportChart(String regionId, String date)
      throws ParseException {
    ChartDto dto = chartRepository.queryByReport(regionId, !StringUtils.isEmpty(date) ? date : DateUtil.date2String(new Date()));
    return dto;
  }
  /**
   * 
    * TODO 简单描述该方法的实现功能（获取拒收图表）. 
    * @see com.wangge.buzmgt.chartlist.service.ChartListService#getRefusedChart(java.lang.String, java.lang.String)
   */
  @Override
  public ChartDto getRefusedChart(String regionId, String date)
      throws ParseException {
    ChartDto dto = chartRepository.queryByRefused(regionId, !StringUtils.isEmpty(date) ? date : DateUtil.date2String(new Date()));
    return dto;
  }

  @Override
  public ChartDto getStatementChart(String regionId, String date)
      throws ParseException {
    ChartDto dto = chartRepository.queryByStatement(regionId, !StringUtils.isEmpty(date) ? date : DateUtil.date2String(new Date()));
    return dto;
  }

  @Override
  public ChartDto getStatementAndPaidChart(String regionId, String date) 
      throws ParseException {
    ChartDto dto = chartRepository.queryByStatementAndPaid(regionId, !StringUtils.isEmpty(date) ? date : DateUtil.date2String(new Date()));
    return dto;
  }
  
  

}
