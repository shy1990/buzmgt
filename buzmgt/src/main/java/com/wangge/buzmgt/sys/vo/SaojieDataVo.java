package com.wangge.buzmgt.sys.vo;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.saojie.entity.SaojieData;

public class SaojieDataVo {
  
  private  String percent;
  
  private  int shopNum;
  
  private  int  taskNum;
  
  private Page<SaojieData> page;
  
  private List<SaojieData> list = new ArrayList<SaojieData>();
  
  private String areaName;

  public String getPercent() {
    return percent;
  }

  public void addPercent(double baiy, double baiz) {
    this.shopNum = (int) baiy;
    this.taskNum = (int) baiz;
    if(baiy > 0 && baiz > 0){
      NumberFormat nf = NumberFormat.getPercentInstance();
      this.percent = nf.format(baiy / baiz);
    }else{
      this.percent = "0%";
    }
   
  }
  public void setPercent(String percent) {
    this.percent = percent;
  }

  public int getShopNum() {
    return shopNum;
  }

  public void setShopNum(int shopNum) {
    this.shopNum = shopNum;
  }

  public int getTaskNum() {
    return taskNum;
  }

  public void setTaskNum(int taskNum) {
    this.taskNum = taskNum;
  }

  public Page<SaojieData> getPage() {
    return page;
  }

  public void setPage(Page<SaojieData> page) {
    this.page = page;
  }

  public List<SaojieData> getList() {
    return list;
  }

  public void setList(List<SaojieData> list) {
    this.list = list;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

}
