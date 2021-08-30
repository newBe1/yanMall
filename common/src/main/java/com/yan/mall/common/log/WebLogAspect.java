package com.yan.mall.common.log;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.yan.mall.common.domain.WebLog;
import com.yan.mall.common.util.ServletUtil;
import io.swagger.annotations.ApiOperation;
import net.logstash.logback.marker.Markers;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:日期处理切面
 * User: Ryan
 * Date: 2020-11-25
 * Time: 9:58
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.yan.mall.controller.*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint)throws Throwable{}

    @AfterReturning(value = "webLog()",returning = "ret")
    public void doAfterReturning(Object ret)throws Throwable{}

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        //获取当前请求对象
        HttpServletRequest request = ServletUtil.getRequest();

        //记录请求信息
        Object result = joinPoint.proceed();
        String description = null;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if(method.isAnnotationPresent(ApiOperation.class)){
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            description = apiOperation.value();
        }
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURI();
        WebLog webLog = WebLog.builder()
                .description(description)
                .basePath(StrUtil.removeSuffix((urlStr), URLUtil.url(urlStr).getPath()))
                .ip(request.getRemoteUser())
                .method(request.getMethod())
                .parameter(request.getParameterMap())
                .result(result)
                .spendTime((int) (endTime - startTime))
                .startTime(startTime)
                .uri(request.getRequestURI())
                .url(request.getRequestURL().toString())
                .build();
        Map<String, Object> logMap = new HashMap<>();
        logMap.put("url", webLog.getUrl());
        logMap.put("method", webLog.getMethod());
        logMap.put("parameter", webLog.getParameter());
        logMap.put("spendTime", webLog.getSpendTime());
        logMap.put("description", webLog.getDescription());


        //TODO 将日志信息存入Elasticsearch
        LOGGER.info(Markers.appendEntries(logMap), JSONUtil.parse(webLog).toString());
        return result;
    }

}
