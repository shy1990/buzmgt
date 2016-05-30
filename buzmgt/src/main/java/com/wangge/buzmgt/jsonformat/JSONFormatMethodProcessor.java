package com.wangge.buzmgt.jsonformat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.HibernateProxyHelper;
import org.hibernate.proxy.LazyInitializer;
import org.hibernate.proxy.map.MapLazyInitializer;
import org.hibernate.proxy.pojo.javassist.JavassistLazyInitializer;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation
        .AbstractMessageConverterMethodProcessor;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * JSONFormatHandler
 * Created by barton on 16-5-23.
 */
public class JSONFormatMethodProcessor extends AbstractMessageConverterMethodProcessor {

    public JSONFormatMethodProcessor(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    private final static String STRING_CLASS = "java.lang.String";

    private final static String DATE_CLASS = "java.util.Date";

    private final static String UTIL_PACKAGE = "java.util";

    private final static String LANG_PACKAGE = "java.lang";

    private final static String SQL_PACKAGE = "java.sql";

    private final static String GETTER_METHOD_PREFIX = "get";

    private final static String PAGE_RESULT = "result";

    private final static String PAGE_LAST = "last";

    private final static String PAGE_TOTAL_PAGES = "totalPages";

    private final static String PAGE_SORT = "sort";

    private final static String PAGE_FIRST = "first";

    private final static String PAGE_NUMBER_OF_ELEMENTS = "numberOfElements";

    private final static String PAGE_SIZE = "size";

    private final static String PAGE_NUMBER = "number";

    private final static String SERIAL_VERSION_UID = "serialVersionUID";

    private JSONFormat jsonFormat = null;

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotationUtils.findAnnotation(returnType.getContainingClass(), JSONFormat.class)
                != null || returnType.getMethodAnnotation(JSONFormat.class) != null);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest
                                          webRequest) throws Exception {
        Object value;

        // 获得这个注解
        jsonFormat = returnType.getMethod().getAnnotation(JSONFormat.class);

        if (ObjectUtils.notEqual(returnValue, null)) {
            int type = getObjectType(returnValue);

            if ((1 == type || 5 == type) && !isProxy(returnValue)) {
                value = returnValue;
            } else {
                value = format(returnValue, type);
            }
        } else {
            value = "";
        }

        mavContainer.setRequestHandled(true);

        System.out.println(value);

        writeWithMessageConverters(value, returnType, webRequest);

    }

    private boolean isProxy(Object obj) {
        if (!ObjectUtils.notEqual(obj, null)) {
            return false;
        }

        Class classz = obj.getClass();// 得到对象的class,
        String path = classz.toString();// 如果是代理对象，肯定会有_$$_这个东西，至少目前看到的都是

        return path.contains("_$$_");
    }

    /**
     * 判断对象类型
     *
     * @param obj 这个应该是returnValue
     * @return returnValue的类型
     */
    private int getObjectType(Object obj) {
        Class<?> classz = obj.getClass();
        // 基本数据类型和String类型返回1
        if (classz.isPrimitive() || classz.isAssignableFrom(String.class)) {
            return 1;
        }
        // 集合类型返回2
        Class<?> classp = Collection.class;
        if (classp.isAssignableFrom(classz)) {
            return 2;
        }
        // 数组类型返回3
        if (classz.isArray()) {
            return 3;
        }
        // 判断分页类型
        Class<?> classPage = Page.class;
        if (classPage.isAssignableFrom(classz)) {
            return 4;
        }
        // 如果本来就是JSON格式
        // 判断fast json类型
        if (classz.isAssignableFrom(JSONObject.class) || classz.isAssignableFrom(JSONArray.class)) {
            return 5;
        }

        if (classz.isAssignableFrom(JavassistLazyInitializer.class)) {
            return 6;
        }
        return 0;
    }

    /**
     * 获得是否要过滤集合
     *
     * @return true false
     */
    private boolean filterCollection() {
        return this.jsonFormat.filterCollection();
    }

    /**
     * 获得是否要将null过滤
     *
     * @return true false
     */
    private boolean nonnull() {
        return this.jsonFormat.nonnull();
    }

    /**
     * 获得要过滤的字段
     *
     * @return true false
     */
    private String[] filterFields() {
        return this.jsonFormat.filterField();
    }

    private String[] enumMethodName() {
        return this.jsonFormat.enumMethodName();
    }

    /**
     * 获得时间的格式化格式
     *
     * @return 时间的格式化格式
     */
    private String dateFormat() {
        return this.jsonFormat.dateFormat();
    }

    private Map<String, Object> processField(Object object) {

        if (!ObjectUtils.notEqual(null, object)) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();

        Method method;
        Object value;
        Class cls = object.getClass();

        if (isProxy(object)) {
            // TODO:
            HibernateProxy proxy = (HibernateProxy) object;
            JavassistLazyInitializer li = (JavassistLazyInitializer) proxy.getHibernateLazyInitializer();
            object = li.getImplementation();
            cls = object.getClass();
        }

        for (Field field : cls.getDeclaredFields()) {

            int fieldType = getFieldType(field);

            String fieldName = field.getName();

            // 如果是private static final long serialVersionUID=... 则直接跳过
            // 如果是一个需要过滤的字段,则直接跳过
            if (StringUtils.equals(fieldName, SERIAL_VERSION_UID) || filter(field, object)) {
                continue;
            }

            try {
                String methodName = getMethodName(fieldName);

                method = cls.getDeclaredMethod(methodName);

//                if (isProxy(object) && !ObjectUtils.notEqual(null, object)) {
////                    HibernateProxy proxy = (HibernateProxy) object;
////                    JavassistLazyInitializer li = (JavassistLazyInitializer) proxy
////                            .getHibernateLazyInitializer();
////                    object = li.getImplementation();
//                    continue;
//                }

                // 通过反射执行相应的getter方法然后取得该字段的值
                value = method.invoke(object);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("没有找到相应的方法");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("通过反射执行方法异常");
            } catch (InvocationTargetException e) {
                throw new RuntimeException("通过反射执行方法异常");
            }

            if (!ObjectUtils.notEqual(null, value)) {
                // 如果要过滤空值 则直接跳过.相当于在json串中的表现形式为,该字段不存在
                if (this.nonnull()) {
                    continue;
                } else {
                    map.put(fieldName, null);
                }
            }

            switch (fieldType) {
                // 基本数据类型或者String或者Date
                case 0:

                    if (StringUtils.equals(field.getType().getName(), DATE_CLASS) && null != value) {
                        value = new SimpleDateFormat(this.dateFormat()).format((Date) value);
                    }

                    map.put(fieldName, value);
                    break;
                // 集合
                case 1:
                    // 不过滤集合
                    if (!this.filterCollection()) {

                        map.put(fieldName, formatByCollection((Collection) value));
                    }
                    break;
                // 枚举
                case 2:

                    try {
                        String enumMethodName = getEnumMethodName(value);

                        if (StringUtils.isNotBlank(enumMethodName)) {
                            Method m = value.getClass().getDeclaredMethod(enumMethodName);

                            value = m.invoke(value);
                        }
                    } catch (Exception e) {
                        // do nothing;
                    }

                    map.put(fieldName, value);
                    break;
                // POJO
                case 3:
                    map.put(fieldName, formatByObject(value));
                    break;
                default:
                    break;
            }
        }
        return map;
    }

    /**
     * 判断field是否需要过滤
     *
     * @param field 字段
     * @return 是否需要过滤
     */
    private boolean filter(Field field, Object entity) {

        String entityName = entity.getClass().getSimpleName();

        String filter = StringUtils.lowerCase(entityName) + "." + field.getName();

        String[] filterFields = this.filterFields();

        for (String filterField : filterFields) {
            String[] tmp = StringUtils.split(filterField, ".");
            if (ArrayUtils.isEmpty(tmp)) {
                throw new RuntimeException("要过滤的字段类型不对," + filterField);
            }
            if (StringUtils.equals(filter, StringUtils.lowerCase(tmp[0]) + "." + tmp[1])) {
                return true;
            }
        }

        return false;
    }

    /**
     * 从JSONFormat注解中调用enumMethodName获取enum的方法名
     *
     * @param entity 注解类
     * @return 方法名
     */
    private String getEnumMethodName(Object entity) {

        String[] names = this.enumMethodName();

        String entityName = StringUtils.lowerCase(entity.getClass().getSimpleName());

        for (String name : names) {
            String[] tmp = StringUtils.split(name, ".");

            if (StringUtils.lowerCase(tmp[0]).equals(entityName)) {
                return tmp[1];
            }
        }

        return null;
    }

    /**
     * 将对象转换成Map.
     *
     * @param object 要转换的对象
     * @return map
     */
    private Map<String, Object> formatByObject(Object object) {

//        if (isProxy(object)) {
//            return formatByProxy(object);
//        }
        return processField(object);
    }

    private Object format(Object object, int objectType) {
        switch (objectType) {
            // 集合类型
            case 2:
                return formatByCollection((Collection) object);
            // 数组类型
            case 3:
                return formatByArray(object);
            // 分页类型
            case 4:
                return formatByPage((Page) object);
        }
        return null;
    }

    /**
     * 格式化分页类型
     *
     * @param object 分页内容
     * @return map
     */
    private Map<String, Object> formatByPage(Page object) {

        Object content = object.getContent();

        Object result = format(content, getObjectType(content));

        Map<String, Object> map = new HashMap<>();

        map.put(PAGE_RESULT, result);
        map.put(PAGE_LAST, object.isLast());
        map.put(PAGE_TOTAL_PAGES, object.getTotalPages());
        map.put(PAGE_SORT, object.getSort());
        map.put(PAGE_FIRST, object.isFirst());
        map.put(PAGE_NUMBER_OF_ELEMENTS, object.getNumberOfElements());
        map.put(PAGE_SIZE, object.getSize());
        map.put(PAGE_NUMBER, object.getNumber());

        return map;

    }

    /**
     * 格式化数组类型
     *
     * @param object 数组类型
     * @return list
     */
    private List<Object> formatByArray(Object object) {
        Object[] arr = (Object[]) object;

        List<Object> objects = new ArrayList<>();

        for (Object o : arr) {
            objects.add(formatByObject(o));
        }

        return objects;
    }

    private List<Object> formatByCollection(Collection collection) {
        List<Object> list = new ArrayList<>();

        collection.forEach(c -> {
            Map<String, Object> map = formatByObject(c);
            list.add(map);
        });

        return list;
    }

    /**
     * 判断字段的类型 0:基本数据类型,String,Date;1:集合;2:普通POJO对象
     *
     * @param field 字段
     * @return 字段的类型
     */
    private int getFieldType(Field field) {

        Class<?> type = field.getType();
        Class<?> collectionType = Collection.class;
        String typeName = type.getName();

        // 非基本数据类型
        if (!typeName.contains(STRING_CLASS) && !type.isPrimitive()) {
            // 集合
            if (collectionType.isAssignableFrom(type)) {
                return 1;
            } else if (type.isEnum()) {
                return 2;
            } else {
                // 基本数据类型
                if (typeName.contains(UTIL_PACKAGE) || typeName.contains(LANG_PACKAGE) || typeName
                        .contains(SQL_PACKAGE)) {
                    return 0;
                }
                // 普通POJO对象
                return 3;
            }
        }
        return 0;// 基本数据类型
    }

    /**
     * 获得这个字段对应的getter方法名称
     *
     * @param filedName 字段名称
     * @return 方法名称
     */
    private String getMethodName(String filedName) {

        String upper = filedName.replaceFirst(filedName.substring(0, 1), filedName.substring(0,
                1).toUpperCase());

        return GETTER_METHOD_PREFIX + upper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory
                                          binderFactory) throws Exception {
        Object arg = readWithMessageConverters(webRequest, parameter, parameter
                .getGenericParameterType());
        String name = Conventions.getVariableNameForParameter(parameter);

        WebDataBinder binder = binderFactory.createBinder(webRequest, arg, name);
        if (arg != null) {
            validateIfApplicable(binder, parameter);
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder,
                    parameter)) {
                throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
            }
        }
        mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());

        return arg;
    }
}
