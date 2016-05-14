package com.wangge.buzmgt;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.wangge.buzmgt.monthTask.entity.MonthTask;
import com.wangge.buzmgt.oilcost.entity.OilRecord;


public class Test {

    public static void main(String args[]) {
//      String msg=formtUrl("http://localhost:8081/ordersignfor/showrecord/A37010003140#box_tab2");
//      String str="[{\"regionType\":0,\"regionName\":\"家\",\"coordinates\":\"116.99656722742283-36.73533570586392\",\"time\":\"18:46:12\",\"type\":\"上班\"}"
//          + ",{\"regionType\":0,\"regionName\":\"家\",\"coordinates\":\"116.99656722742283-36.73533570586392\",\"time\":\"18:58:22\",\"type\":\"下班\"}"
//          + ",{\"regionType\":0,\"regionName\":\"家\",\"coordinates\":\"116.99656722742283-36.73533570586392\",\"time\":\"18:58:22\",\"type\":\"下班\"}"
//          + ",{\"regionType\":0,\"regionName\":\"家\",\"coordinates\":\"116.99656722742283-36.73533570586392\",\"time\":\"18:58:22\",\"type\":\"下班\"}]";
//      str=str.replace("[", "").replace("]", "");
//      System.out.println(str);
//      String[] arr =str.split(",\\{");
//      for(int n=1;n<arr.length;n++){
//        arr[n]="{"+arr[n];
//      }
//      List<OilRecord> list=new ArrayList<>();
//      for(String s:arr){
//        OilRecord OilRecordJson= JSON.parseObject(s,OilRecord.class);
//        list.add(OilRecordJson);
//        System.out.println(OilRecordJson);
//      }
//      String regionId="3701,3703,723432,34";
//      String sql=formtStr(regionId);
//      System.out.println(sql);
    	MonthTask t1=new MonthTask(); 
    	Class<MonthTask> c=MonthTask.class;
    	Field[] fields=c.getDeclaredFields();
    	try {
			c.getDeclaredField("month").getInt(t1);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       for(Field f:fields){
    	   
       }
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
    
    public static String formtStr(String regionId){
      String sql="";
      if(StringUtils.isNotEmpty(regionId)){
        sql+="and (";
        String[] regionIdarr=regionId.split(",");
        for(int i=0;i<regionIdarr.length;i++){
          if(i==0){
            sql+="a.salesman_id like '%s' ";
          }else{
            sql+=" or a.salesman_id like '%s'";
          }
        }
        sql+=")";
        System.out.println(sql);
        sql =String.format(sql, regionIdarr);
      }
      return sql;
      
    }
  
}