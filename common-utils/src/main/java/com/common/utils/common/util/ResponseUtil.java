package com.common.utils.common.util;

import com.common.utils.common.base.entity.CommonException;
import com.common.utils.common.base.entity.ResponseBean;
import com.common.utils.common.constants.ResultEnum;

/**
 * 返回格式
 */
public class ResponseUtil {

    /**
     * 封装ResponseBean
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> success(T result) {
        return fail(0, "ok", result);
    }


    /**
     * 封装ResponseBean,无内容
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> success() {
        return fail(0, "ok", null);
    }

    /**
     * 返回ResultEnum
     *
     * @param resultEnum
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> fail(ResultEnum resultEnum) {
        return fail(resultEnum.getCode(), resultEnum.getMessage(), null);
    }

    /**
     * 返回CommonException作为ResponseBean错误信息
     *
     * @param exception
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> fail(CommonException exception) {
        return fail(exception.getCode(), exception.getMessage(), null);
    }

    /**
     * 封装ResponseBean
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> fail(int code, String message, T result) {
        ResponseBean<T> responseBean = new ResponseBean<T>();
        responseBean.setCode(code);
        responseBean.setMessage(message);
        if(result!=null){
        	 responseBean.setResult(result);
        }
        return responseBean;
    }

}
