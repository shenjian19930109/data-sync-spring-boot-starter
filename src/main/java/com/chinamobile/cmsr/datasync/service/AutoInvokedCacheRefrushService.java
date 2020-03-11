package com.chinamobile.cmsr.datasync.service;

import com.chinamobile.cmsr.datasync.common.annotation.CacheSync;
import com.chinamobile.cmsr.datasync.common.base.BaseResultInterface;
import com.chinamobile.cmsr.datasync.common.enums.ResponseCode;
import com.chinamobile.cmsr.datasync.common.pojo.ResultObj;
import com.chinamobile.cmsr.datasync.properties.DataSyncProperties;
import com.chinamobile.cmsr.datasync.util.SpringUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description:
 * @author: shenjian
 * @create: 2020/3/10 16:36
 */
@Service
@NoArgsConstructor
@Slf4j
public class AutoInvokedCacheRefrushService implements BaseResultInterface {

    private DataSyncProperties dataSyncProperties;

    public AutoInvokedCacheRefrushService(DataSyncProperties dataSyncProperties) {
        this.dataSyncProperties = dataSyncProperties;
    }

    /**
     * 根据key调用被@CacheSync修饰的方法
     * @param key key
     * @return
     * */
    public ResultObj<String> invoke(String key) {

        boolean invokeOnlyOnce = dataSyncProperties.isInvokeOnlyOnce();
        Map<String, List<Method>> syncAnnoFuncContext = SpringUtil.getSyncAnnoFuncContext();

        if (syncAnnoFuncContext == null || syncAnnoFuncContext.size() == 0) {
            return fail(ResponseCode.INVOKE_NONE.getCode(), ResponseCode.INVOKE_NONE.getDesc());
        }

        for (Map.Entry<String, List<Method>> entry : syncAnnoFuncContext.entrySet()) {
            String beanName = entry.getKey();
            List<Method> methods = entry.getValue();
            for (Method method : methods) {
                CacheSync annotation = method.getAnnotation(CacheSync.class);
                String annoKey = annotation.key();
                if (StringUtils.isNotBlank(annoKey) && annoKey.equals(key)) {
                    try {
                        Object response = method.invoke(SpringUtil.getBean(beanName), method.getParameters());
                        log.info("beanName:[{}], methodName:[{}], key:[{}], method invoke success!, response:[{}]", beanName,
                                method.getName(), key, response);
                    }catch (Exception e) {
                        log.error("beanName:[{}], methodName:[{}], key:[{}], method invoke fail! detail error info:[{}]", beanName,
                                method.getName(), key, e.getMessage());
                        // 无论invokeOnlyOnce为何值，只要发生异常，直接返回！
                        return fail(ResponseCode.INVOKE_FAIL.getCode(), "beanName:" + beanName + ", methodName:" + method.getName() + ", key:" + key + " invoke fail!");
                    }
                    if (invokeOnlyOnce) {
                        return success();
                    }
                }
            }
        }
        return success();
    }

    /**
     * 根据keys调用被@CacheSync修饰的方法
     * @param keys keys
     * @return
     * */
    public ResultObj<String> invoke(Set<String> keys) {

        return null;
    }

}
