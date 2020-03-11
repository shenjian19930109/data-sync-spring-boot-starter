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
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.HashSet;
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
        if (StringUtils.isBlank(key)) {
            return fail(ResponseCode.ILLEGAL_PARAMS.getCode(), ResponseCode.ILLEGAL_PARAMS.getDesc());
        }
        Set<String> keys = new HashSet<>(1);
        keys.add(key);
        ResultObj<String> result = invoke(keys);
        return result;
    }

    /**
     * 根据keys调用被@CacheSync修饰的方法
     * @param keys keys
     * @return
     * */
    public ResultObj<String> invoke(Set<String> keys) {

        if (CollectionUtils.isEmpty(keys)) {
            return fail(ResponseCode.ILLEGAL_PARAMS.getCode(), ResponseCode.ILLEGAL_PARAMS.getDesc());
        }

        Map<String, Integer> keysCountMap = SpringUtil.getKeysCountMap(keys);

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
                if (StringUtils.isNotBlank(annoKey) && keys.contains(annoKey) && (!invokeOnlyOnce || keysCountMap.get(annoKey) == 0)) {
                    try {
                        Object response = method.invoke(SpringUtil.getBean(beanName), method.getParameters());
                        keysCountMap.put(annoKey, keysCountMap.get(annoKey) + 1);
                        log.info("beanName:[{}], methodName:[{}], key:[{}], method invoke success!, response:[{}]", beanName,
                                method.getName(), annoKey, response);
                    }catch (Exception e) {
                        log.error("beanName:[{}], methodName:[{}], key:[{}], method invoke fail! detail error info:[{}]", beanName,
                                method.getName(), annoKey, e.getMessage());
                        // 无论invokeOnlyOnce为何值，只要发生异常，直接返回！
                        return fail(ResponseCode.INVOKE_FAIL.getCode(), "beanName:" + beanName + ", methodName:" + method.getName() + ", key:" + annoKey + " invoke fail!");
                    }
//                    if (invokeOnlyOnce) {
//                        return success();
//                    }
                }
            }
        }
        return success();
    }

}
