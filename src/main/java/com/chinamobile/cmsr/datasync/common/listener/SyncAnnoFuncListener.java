package com.chinamobile.cmsr.datasync.common.listener;

import com.chinamobile.cmsr.datasync.common.annotation.CacheSync;
import com.chinamobile.cmsr.datasync.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: shenjian
 * @create: 2020/3/11 15:23
 */
@Component
public class SyncAnnoFuncListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("ApplicationListener：onApplicationEvent");


        if(event.getApplicationContext().getParent() == null){//root application context 没有parent
            //TODO 这里写下将要初始化的内容
            ApplicationContext context = event.getApplicationContext();
            String[] beanNames = context.getBeanDefinitionNames();
            for (String beanName : beanNames) {

                Class<?> beanType = context.getType(beanName);
                System.out.println("BeanName:" + beanName);
                Method[] methods = beanType.getDeclaredMethods();
                List<Method> annotedMethods = new ArrayList<>();

                for (Method method : methods) {
                    if (method == null) {
                        continue;
                    }
                    // TODO 判断是object的方法则跳过
                    CacheSync annotation = method.getDeclaredAnnotation(CacheSync.class);
                    if (annotation != null) {
                        String key = annotation.key();
                        if (StringUtils.isNotBlank(key)) {
                            annotedMethods.add(method);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(annotedMethods)) {
                    SpringUtil.appendSyncAnnoFuncs(beanName, annotedMethods);
                }
            }
        }

        Map<String, List<Method>> syncAnnoFuncContext = SpringUtil.getSyncAnnoFuncContext();

        if(event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")){
            System.out.println("Root WebApplicationContext");
        }
    }
}
