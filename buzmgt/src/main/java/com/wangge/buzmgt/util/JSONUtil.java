package com.wangge.buzmgt.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 将String类型的json数据格式的数组[]
 * 装换成相对应列表list
 * 
 * 
 * @author Thor
 *
 */
public class JSONUtil {

  /**
   * 将String类型的json数据格式的数组[]
   * 装换成相对应列表list
   * 
   * @param String stringJsonArr
   * @param Class<T> clazz
   * @return <T> List<T>
   */
  public static <T> List<T> stringArrtoJsonList(String stringJsonArr,Class<T> clazz){
    List<T> list = new ArrayList<>();
    stringJsonArr = stringJsonArr.replace("[", "").replace("]", "");
    String[] jsonArr = stringJsonArr.split(",\\{");
    for (int n = 0; n < jsonArr.length; n++) {
      if(n!=0){ jsonArr[n] = "{" + jsonArr[n]; }
      T tJson = JSON.parseObject(jsonArr[n], clazz);
      list.add(tJson);
    }
    return list;
  }
  
  
  
}
