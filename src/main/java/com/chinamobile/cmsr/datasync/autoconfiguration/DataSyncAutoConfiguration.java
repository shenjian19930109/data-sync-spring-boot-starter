package com.chinamobile.cmsr.datasync.autoconfiguration;

import com.chinamobile.cmsr.datasync.common.listener.SyncAnnoFuncListener;
import com.chinamobile.cmsr.datasync.controller.AutoInvokedCacheRefrushController;
import com.chinamobile.cmsr.datasync.properties.DataSyncProperties;
import com.chinamobile.cmsr.datasync.service.AutoInvokedCacheRefrushService;
import com.chinamobile.cmsr.datasync.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: shenjian
 * @create: 2020/3/10 17:10
 */
@Configuration
@EnableConfigurationProperties(DataSyncProperties.class)
public class DataSyncAutoConfiguration {

    @Autowired
    private DataSyncProperties dataSyncProperties;

    @Bean
    @ConditionalOnProperty(prefix = "datasync", value = "enable", havingValue = "true")
    public AutoInvokedCacheRefrushService autoInvokedCacheRefrushService() {
        return new AutoInvokedCacheRefrushService(dataSyncProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "datasync", value = "enable", havingValue = "true")
    public AutoInvokedCacheRefrushController autoInvokedCacheRefrushController(AutoInvokedCacheRefrushService autoInvokedCacheRefrushService) {
        return new AutoInvokedCacheRefrushController(autoInvokedCacheRefrushService);
    }

    @Bean
    @ConditionalOnProperty(prefix = "datasync", value = "enable", havingValue = "true")
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    @ConditionalOnProperty(prefix = "datasync", value = "enable", havingValue = "true")
    public SyncAnnoFuncListener syncAnnoFuncListener() {
        return new SyncAnnoFuncListener();
    }

}
