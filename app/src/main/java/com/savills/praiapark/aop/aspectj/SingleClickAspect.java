package com.savills.praiapark.aop.aspectj;

import android.util.Log;
import android.view.View;

import com.savills.praiapark.R;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

@Aspect
public class SingleClickAspect {
    public static final long MIN_CLICK_DELAY_TIME = 500L;
    public static int TIME_TAG = R.id.click_time;
    public static final String TAG = "SingleClickAspect";


    @Pointcut("execution(@com.savills.praiapark.aop.annotation.SingleClick * *(..))")//方法切入点
    public void methodAnnotated() {

    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) view = ((View) arg);
        }
        if (view != null) {
            Object tag = view.getTag(TIME_TAG);
            long lastClickTime = (tag != null) ? (long) tag : 0;
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {//过滤掉600毫秒内的连续点击
                view.setTag(TIME_TAG, currentTime);
                Log.d(TAG,"SingleClickAspect 點擊攔截 执行原方法");
                joinPoint.proceed();//执行原方法
            }else{
                Log.d(TAG,"SingleClickAspect 點擊攔截 重复点击");
            }
        }
    }
}
