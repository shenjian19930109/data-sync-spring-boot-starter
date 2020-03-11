package com.chinamobile.cmsr.datasync.common.enums;

/**
 * @description:
 * @author: shenjian
 * @create: 2020/3/11 16:52
 */
public enum ResponseCode {

    SUCCESS("200", "SUCCESS"),

    // 方法调用失败
    INVOKE_FAIL("701", "INVOKE_FAIL"),

    // 未调用任何方法
    INVOKE_NONE("702", "INVOKE_NONE"),

    // 提示
    HINT("600","PROMPT"),
    // 警告
    WARNING("400","WARNING"),
    ERROR("550", "ERROR");

    private final String code;
    private final String desc;

    ResponseCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
