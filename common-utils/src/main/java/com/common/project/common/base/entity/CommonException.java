package com.common.project.common.base.entity;

import com.common.project.common.constants.ResultEnum;

/**
 * 抛出异常
 */
public class CommonException extends RuntimeException {

    
	private static final long serialVersionUID = 1030643008892262130L;
	private int code;
    private ResultEnum resultEnum;
    public CommonException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CommonException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
        this.code = resultEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public ResultEnum getResultEnum(){
        return resultEnum;
    }
    @Override
    public String toString() {
        return "CommonException code: " + code + " " + super.toString();
    }
}