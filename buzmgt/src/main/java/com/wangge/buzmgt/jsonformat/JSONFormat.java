package com.wangge.buzmgt.jsonformat;

import java.lang.annotation.*;

/**
 * Created by barton on 16-5-23.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JSONFormat {
    String dateFormat() default "yyyy-MM-dd HH:ss:mm"; // 默认日期转换格式

    boolean filterCollection() default false; // 默认关闭集合和对象过滤,true:过滤;false:不过滤

    String[] filterField() default ""; // 过滤字段,写实体类名(首字母大小写均可)+字段名:Entity.attribute/entity.attribute

    boolean nonnull() default false;// 字段的值为null时是否不显示,true:不显示,false:显示null

    String[] enumMethodName() default ""; // 填写要取得的字段的getter方法名称.写枚举类名(首字母大小写均可)+方法名:Enum
    // .getName/enum.getName
}
