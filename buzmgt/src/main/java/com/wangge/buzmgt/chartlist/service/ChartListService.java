package com.wangge.buzmgt.chartlist.service;

import java.text.ParseException;
import java.util.Date;

import com.wangge.buzmgt.dto.ChartDto;

public interface ChartListService {

  public ChartDto getOutboundChart(String regionId, String date)throws ParseException;

  public ChartDto getCashChart(String regionId, String date)throws ParseException;

  public ChartDto getRefusedChart(String regionId, String date)throws ParseException;

  public ChartDto getunReportChart(String regionId, String date) throws ParseException;

  public ChartDto getReportChart(String regionId, String date)throws ParseException;

  public ChartDto getStatementChart(String regionId, String date)throws ParseException;

  public ChartDto getStatementAndPaidChart(String regionId, String date)throws ParseException;

}
