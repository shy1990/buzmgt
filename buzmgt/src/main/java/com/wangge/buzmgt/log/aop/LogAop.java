package com.wangge.buzmgt.log.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.util.EnvironmentUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by barton on 16-6-7.
 */
@Aspect
@Configuration
public class LogAop {

    /**
     * 切入点 声明
     * 对应所有web目录下的所有方法
     */
    @Pointcut("execution(* com.wangge.buzmgt.*.web.*.*(..))")
    private void pointCut() {
    }

    /**
     * 前置通知
     * 打印Controller中方法的参数
     */
    @Before("pointCut()")
    private void before(JoinPoint point) {
        Object[] args = point.getArgs();

        User user = EnvironmentUtils.getUser();

        String userName = "";

        if (ObjectUtils.notEqual(null, user)) {
            userName = user.getUsername();
        }

        String methodName = point.getSignature().getName();// 方法名

        StringBuilder sb = new StringBuilder("");

        sb.append(userName);
        sb.append("调用");
        sb.append(point.getSignature().getDeclaringTypeName());
        sb.append("的");
        sb.append(methodName);
        sb.append("方法,参数列表(按顺序):");

        for (Object arg : args) {
            if (ObjectUtils.notEqual(arg, null)) {
                if (HttpServletRequest.class.isAssignableFrom(arg.getClass())) {
                    HttpServletRequest request = (HttpServletRequest) arg;
                    sb.append("url参数:");
                    sb.append(request.getQueryString());
                } else {
                    sb.append(arg.toString() + " ");
                }
            }
        }

        LogUtil.info(sb.toString());
    }

    /**
     * 后置通知
     */
    @After("pointCut()")
    private void after(JoinPoint point) throws JsonProcessingException {
    }

    /**
     * 结果返回之后执行
     * 打印Controller中方法的返回值
     *
     * @param result
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    private void afterReturning(Object result) throws JsonProcessingException {
    }

    /**
     * 抛出异常后执行
     *
     * @param e
     */
    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    private void afterThrowing(Exception e) {
        LogUtil.error(e.getMessage());
    }

//    /**
//     * 环绕通知
//     *
//     * @param joinPoint
//     * @return
//     * @throws Throwable
//     */
//    @Around("pointCut()")
//    private Object aroud(ProceedingJoinPoint joinPoint) throws Throwable {
////        Object object = joinPoint.proceed();
////        return object;
//        return null;
//    }
}