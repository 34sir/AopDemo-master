package com.example.libaop;


import com.example.libaop.annotation.timing.EndTime;
import com.example.libaop.annotation.timing.StartTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class TimeAspect {
    
    public long startTime;
    public long endTime;

    @Around("execution(!synthetic * *(..)) && onTimeMethod()")
    public void doTimeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        startTimeMethod(joinPoint);
    }

    @Pointcut("@within(com.example.libaop.annotation.timing.StartTime)||@annotation(com.example.libaop.annotation.timing.StartTime)")
    public void onTimeMethod() {
    }
    @Around("execution(!synthetic * *(..)) && onEndTimeMethod()")
    public void doEndTimeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        endTimeMethod(joinPoint);
    }

    @Pointcut("@within(com.example.libaop.annotation.timing.EndTime)||@annotation(com.example.libaop.annotation.timing.EndTime)")
    public void onEndTimeMethod() {
    }


    private void startTimeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        StartTime time = method.getAnnotation(StartTime.class);

        if (time != null) {
            startTime=System.currentTimeMillis();
            joinPoint.proceed();
        } else {
            // 不影响原来的流程
            joinPoint.proceed();
        }
    }
    private void endTimeMethod(final ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        EndTime time = method.getAnnotation(EndTime.class);

        if (time != null) {
            endTime=System.currentTimeMillis();
            System.out.println("时间间隔="+(endTime-startTime));
            joinPoint.proceed();
        } else {
            // 不影响原来的流程
            joinPoint.proceed();
        }
    }
}
