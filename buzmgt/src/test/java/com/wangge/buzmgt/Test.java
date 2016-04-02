package com.wangge.buzmgt;

import java.util.Date;


public class Test {

    public static void main(String args[]) {
      String msg=formtUrl("http://localhost:8081/ordersignfor/showrecord/A37010003140#box_tab2");
      System.out.println(msg);
    }
    private static String getAging(Long between){
      Long hour=between/1000/3600;
      System.out.println(hour);
      Long minute=(between%(1000*3600))/(1000*60);
      System.out.println(minute);
      return hour+"小时"+minute+"分钟";
    }
    public static String formtUrl(String url){
//      url="http://localhost:8081/ordersignfor/showrecord/A37010003140#box_tab2";
      int beginIndex=url.indexOf("#");
      System.out.println(beginIndex);
      if(beginIndex == -1){
        return "";
      }else{
        return url.trim().substring(beginIndex+1, url.length());
      }
    }
}