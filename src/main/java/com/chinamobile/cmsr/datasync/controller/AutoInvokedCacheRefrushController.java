package com.chinamobile.cmsr.datasync.controller;

import com.chinamobile.cmsr.datasync.common.pojo.ResultObj;
import com.chinamobile.cmsr.datasync.service.AutoInvokedCacheRefrushService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: shenjian
 * @create: 2020/3/10 16:17
 */
@RestController
public class AutoInvokedCacheRefrushController {

    private AutoInvokedCacheRefrushService autoInvokedCacheRefrushService;

    public AutoInvokedCacheRefrushController(AutoInvokedCacheRefrushService autoInvokedCacheRefrushService) {
        this.autoInvokedCacheRefrushService = autoInvokedCacheRefrushService;
    }

    @PostMapping("/sync/syncCache")
    public ResultObj<String> syncCache(String key) {

        return autoInvokedCacheRefrushService.invoke(key);
    }

}
