package com.example.libaop;


import com.example.libaop.annotation.Polling;
import com.example.libaop.annotation.StopPolling;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Aspect
public class PollingAspect {

    @Around("execution(!synthetic * *(..)) && onPollingMethod()")
    public void doPollingMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        pollingMethod(joinPoint);
    }

    @Pointcut("@within(com.example.libaop.annotation.Polling)||@annotation(com.example.libaop.annotation.Polling)")
    public void onPollingMethod() {
    }
    @Around("execution(!synthetic * *(..)) && onStopPollingMethod()")
    public void doStopPollingMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        stopPollingMethod(joinPoint);
    }

    @Pointcut("@within(com.example.libaop.annotation.StopPolling)||@annotation(com.example.libaop.annotation.StopPolling)")
    public void onStopPollingMethod() {
    }

    private ScheduledExecutorService mScheduledExecutorService;
    private Future mFuture;
    private void pollingMethod(final ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Polling polling = method.getAnnotation(Polling.class);

        if (polling != null) {
            int expiry = polling.expiry();
            if (mScheduledExecutorService == null || mScheduledExecutorService.isShutdown()) {
                mScheduledExecutorService = Executors.newScheduledThreadPool(1);
            }
            if (mFuture == null || mFuture.isCancelled()) {
                mFuture = mScheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
                    public void run() {
                        try {
                            joinPoint.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                }, 0, expiry, TimeUnit.SECONDS);
            }
        } else {
            // 不影响原来的流程
            joinPoint.proceed();
        }
    }
    private void stopPollingMethod(final ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        StopPolling stopPolling = method.getAnnotation(StopPolling.class);

        if (stopPolling != null) {
            if (this.mFuture != null) {
                this.mFuture.cancel(true);
                this.mFuture = null;
                if (mScheduledExecutorService != null && !mScheduledExecutorService.isShutdown()) {
                    this.mScheduledExecutorService.shutdownNow();
                    this.mScheduledExecutorService = null;
                }
            }
            joinPoint.proceed();
        } else {
            // 不影响原来的流程
            joinPoint.proceed();
        }
    }
}
