//package com.chinamobile.cmsr.datasync.common.aspect;
//
//import com.chinamobile.cmsr.datasync.common.annotation.CacheSync;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
///**
// * @description:
// * @author: shenjian
// * @create: 2020/3/11 11:35
// */
//@Component
//@Aspect
//@Slf4j
//public class CacheSyncAspect {
//
//    /**
//     * 切入点 被@CacheSync注解方法所属的类
//     * */
////    @Pointcut("@annotation(com.chinamobile.cmsr.datasync.common.annotation.CacheSync)")
////    public void pointCut() {
////    }
//
////    @After("pointCut()")
////    public void after(JoinPoint joinPoint) {
////
////    }
//
////    @Around(value = "pointCut()", argNames = "joinPoint, cacheSync")
////    @Around("pointCut()")
//    @Around("@annotation(cacheSync)")
//    public Object methodAround(ProceedingJoinPoint joinPoint, CacheSync cacheSync) throws Throwable {
//        System.out.println("Around method    start.......................");
//        //获得自定义注解的参数
//        System.out.println("Around method  methodLog 的参数，remark："  + " clazz：" );
//        //执行目标方法，并获得对应方法的返回值
//        Object result = joinPoint.proceed();
//        System.out.println("Around method     返回结果：" + result);
//        System.out.println("Around method          end.......................");
//        return result;
//    }
//
//}
