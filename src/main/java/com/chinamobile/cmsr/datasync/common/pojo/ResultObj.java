package com.chinamobile.cmsr.datasync.common.pojo;

import lombok.Data;

/**
 * @description:
 * @author: shenjian
 * @create: 2020/3/10 16:21
 */
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
@Data
public class ResultObj<T> {

    /**
     * 返回code
     */
    private String code;

    /**
     * 返回说明
     */
    private String msg;

    /**
     * 待包装的返回值
     */
    private T data;

}
