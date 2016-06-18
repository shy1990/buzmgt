package com.wangge.buzmgt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.wangge.buzmgt.oilcost.entity.OilRecord;

/**
 * 将String类型的json数据格式的数组[]
 * 装换成相对应列表list
 * 
 * 
 * @author Thor
 *
 */
public class JSONUtilTest {

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
  public static void main(String[] args) {

   String key="ab.asd";
   System.out.println(key.matches("^[a-zA-Z]*.[a-zA-Z]*.[a-zA-Z]*$"));
  }
}
