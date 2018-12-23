package com.common.utils.common.base.entity;

import com.common.utils.common.constants.ResultEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 包装Json响应的Bean
 * Created by guofe on 2015/9/29.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBean<T> {

    /**
     * 返回代码，0 正确，负数 程序异常，正数 校验错误
     */
    private int code;

    /**
     * 额外信息
     */
    private String message;

    /**
     * 返回结果
     */
    private T result;

    public ResponseBean() {
    }

    public ResponseBean(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public ResponseBean(ResultEnum resultEnum, T result) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.result = result;
    }

    public ResponseBean(int code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ResponseBean(int code, String message, boolean registered) {
        this.code = code;
        this.message = message;
    }
    public ResponseBean(ResultEnum resultEnum, boolean registered) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
    }

    public ResponseBean(int code, String message, String token, T result) {
        this.code = code;
        this.message = message;
        this.result = result;

    }

    public ResponseBean(ResultEnum resultEnum, String token, T result) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessage();
        this.result = result;
    }

    public ResponseBean(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


    @Override
    public String toString() {
        if (code == 0) {
            StringBuilder _string = new StringBuilder();
            if(message != null){
                _string.append("message:" + message);
            }
            if (result != null) {
                _string.append("| " + result.toString());
            }
            return _string.toString();
        } else {
            return code + " on " + message;
        }
    }
}
