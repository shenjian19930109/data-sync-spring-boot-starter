package com.chinamobile.cmsr.datasync.common.base;

import com.chinamobile.cmsr.datasync.common.enums.ResponseCode;
import com.chinamobile.cmsr.datasync.common.pojo.ResultObj;
import org.apache.commons.lang3.StringUtils;

/**
 * @author qiantao
 */
public interface BaseResultInterface {

    /**
     * 成功返回方法
     *
     * @param <T> 泛型
     * @return 统一返回值
     */
    default <T> ResultObj<T> success() {
        return success(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), null);
    }

    /**
     * 成功返回方法
     *
     * @param <T>  泛型
     * @param data data
     * @return 统一返回值
     */
    default <T> ResultObj<T> success(T data) {
        return success(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(), data);
    }

    /**
     * 成功返回方法
     *
     * @param data data
     * @param msg msg
     * @param <T>  泛型
     * @return 统一返回值
     */
    default <T> ResultObj<T> success(String msg, T data) {
        return success(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 成功返回方法
     *
     * @param code  结果code
     * @param <T>   泛型
     * @param data  data
     * @param msg 描述
     * @return 统一返回值
     */
    default <T> ResultObj<T> success(String code, String msg, T data) {
        ResultObj<T> resultObj = generateResultObj(code, msg, data);
        return resultObj;
    }

    /**
     * 失败返回方法
     *
     * @param code 结果code
     * @param <T>  泛型
     * @return 统一返回值
     */
    default <T> ResultObj<T> fail(String code) {
        return fail(code, null, null);
    }

    /**
     * 失败返回方法
     *
     * @param msg data
     * @param code 结果code
     * @param <T>  泛型
     * @return 统一返回值
     */
    default <T> ResultObj<T> fail(String code, String msg) {
        return fail(code, msg, null);
    }

    /**
     * 失败返回方法
     *
     * @param code  结果code
     * @param <T>   泛型
     * @param data  data
     * @param msg 描述
     * @return 统一返回值
     */
    default <T> ResultObj<T> fail(String code, String msg, T data) {
        ResultObj<T> resultObj = generateResultObj(code, msg, data);
        return resultObj;
    }

    /**
     * 组装消息返回对象
     * @param code  结果code
     * @param <T>   泛型
     * @param data  data
     * @param msg 描述
     * @return 统一返回值
     */
    default <T> ResultObj<T> generateResultObj(String code, String msg, T data) {
        ResultObj<T> resultObj = new ResultObj<>();
        resultObj.setCode(code);
        resultObj.setMsg(msg);
        resultObj.setData(data);
        return resultObj;
    }

    /**
     * 判断是否成功
     *
     * @param ro ro
     * @return 成功或失败
     */
    default boolean isSuccess(ResultObj ro) {
        if (ro != null) {
            String code = ro.getCode();
            if (StringUtils.isNotBlank(code) && code.equals(ResponseCode.SUCCESS.getCode())) {
                return true;
            }
        }
        return false;
    }

}