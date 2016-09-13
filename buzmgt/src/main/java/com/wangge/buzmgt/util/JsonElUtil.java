package com.wangge.buzmgt.util;

import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.annotation.Configuration;

import net.sf.json.JSONObject;
/**
 * 
* @ClassName: JsonElUtil
* @Description: 自定义el表达式
* @author ChenGuop
* @date 2016年9月12日 下午5:40:50
*
 */
@Configuration 
public class JsonElUtil extends TagSupport {

  private static final long serialVersionUID = 1L;

  public static String toJsonString(Object obj) {
    // 将java对象转换为json对象
    JSONObject json = JSONObject.fromObject(obj);
    String str = json.toString();
    return str;
  }
  
}
