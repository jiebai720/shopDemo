package com.bb.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * Created by admin on 2016/12/12.
 */
@Aspect
@Component
public class WebLogAspect {


    private Logger logger = Logger.getLogger( getClass() );

    ThreadLocal<Long> startTime = new ThreadLocal<>();



    @Pointcut("execution(public * com.bb..*.*(..))")
    public void webLog(){};


    @Before("webLog()")
    @Order(10)
    public void doBefore(JoinPoint joinPoint){

        startTime.set(System.currentTimeMillis());

        ServletRequestAttributes attributes  = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("URL: " +  request.getRequestURL().toString() );
        logger.info("CLASS_METHOD:  " +  joinPoint.getSignature().getDeclaringTypeName()+"." + joinPoint.getSignature().getName() );
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs())  );

    }

    @AfterReturning( returning = "ret" , pointcut = "webLog()")
    @Order(5)
    public void doAfterReturning(Object ret) throws  Throwable{
        logger.info("response: " + ret);
        logger.info("SPEND TIME :  " + ( System.currentTimeMillis()-startTime.get()) + "ms .." );
    }


}
