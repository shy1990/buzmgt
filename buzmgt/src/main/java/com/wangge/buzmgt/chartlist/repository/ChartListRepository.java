package com.wangge.buzmgt.chartlist.repository;


import com.wangge.buzmgt.dto.ChartDto;


public interface ChartListRepository {
  
  
  public ChartDto queryByShipStatus(String regionId, String date);

  public ChartDto queryByDeelType(String regionId, String date);

  public ChartDto queryByPayStatus(String regionId, String date);

  public ChartDto queryByReport(String regionId, String date);

  public ChartDto queryByRefused(String regionId, String date);

  public ChartDto queryByStatement(String regionId, String date);

  public ChartDto queryByStatementAndPaid(String regionId, String date);

 // public ChartDto queryByUnReport(String regionId, String date);
  

}
