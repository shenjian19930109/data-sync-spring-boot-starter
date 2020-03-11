package com.chinamobile.cmsr.datasync.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: shenjian
 * @create: 2020/3/10 16:53
 */
@Component
public class SpringUtil extends ApplicationObjectSupport {

    private static ApplicationContext context;

    private static Map<String, List<Method>> syncAnnoFuncContext = new HashMap<>();

    public static ApplicationContext getContext() {
        return context;
    }

    public static Map<String, List<Method>> getSyncAnnoFuncContext() {
        return syncAnnoFuncContext;
    }

    public static Object getBean(String serviceName){
        return context.getBean(serviceName);
    }
    @Override
    protected void initApplicationContext(ApplicationContext context) throws BeansException {
        super.initApplicationContext(context);
        SpringUtil.context = context;
        //System.out.println("=============================================="+SpringUtil.context+"==============================================");
    }

    public static void appendSyncAnnoFuncs(String beanName, List<Method> annotedMethods) {
        List<Method> methods = syncAnnoFuncContext.get(beanName);
        if (CollectionUtils.isEmpty(methods)) {
            syncAnnoFuncContext.put(beanName, annotedMethods);
        }else {
            throw new RuntimeException("beanName : " + beanName + ", which is annoted by @CacheSync is duplicated!");
        }
    }

    public static void appendSyncAnnoFuncs(Map<String, List<Method>> methodsMap) {

    }

    public static void appendSyncAnnoFunc(Map<String, Method> methodMap) {

    }

    public static Map<String, Integer> getKeyCountMap(String key) {
        Set<String> set = new HashSet<>();
        set.add(key);
        return getKeysCountMap(set);
    }

    public static Map<String, Integer> getKeysCountMap(Set<String> keys) {
        Map<String, Integer> result = new HashMap<>();
        for (String key : keys) {
            result.put(key, 0);
        }
        return result;
    }

}
