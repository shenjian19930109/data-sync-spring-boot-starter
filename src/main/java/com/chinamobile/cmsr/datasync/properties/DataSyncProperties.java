package com.chinamobile.cmsr.datasync.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @description:
 * @author: shenjian
 * @create: 2020/3/10 17:11
 */
@Data
@ConfigurationProperties(prefix = "datasync")
public class DataSyncProperties {

    private boolean enable;

    private boolean invokeOnlyOnce;
}
