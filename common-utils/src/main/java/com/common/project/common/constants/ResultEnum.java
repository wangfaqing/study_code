package com.common.project.common.constants;

/**
 * 返回码定义：
 * 0，正常
 * 10001 - 10999 //用户相关的错误码
 * 11001 - 11099 // 可预料的异常
 * 11101 - 11999  //待定
 * 12001 - 12999  //用户错误
 * 13001 -		  //请求错误
 */
public enum ResultEnum {
	Success(0, "成功"),
	systemError(101, "系统错误");

	private final int code;

	private final String message;

	ResultEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
     
     
}
