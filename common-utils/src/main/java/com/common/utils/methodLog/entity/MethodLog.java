package com.common.utils.methodLog.entity;

import com.common.utils.methodLog.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import java.io.Serializable;
import java.util.Date;

public class MethodLog implements Serializable {
	private static final long serialVersionUID = -2882301789711667633L;
	private String serverName;
	private String methodName;
	private String params;
	private String returnVal ;//返回值
	private int costTime;
	private Date createTime;
	private String exception;
	private String currentDate;

	public MethodLog() {
		this.createTime = new Date();
		if (this.currentDate == null)
			this.currentDate = DateUtils.formatDate(this.createTime, "yyyyMMdd");
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return StringUtils.left(this.params, 1000);
	}

	public void setParams(String params) {
		this.params = params;
	}

	public int getCostTime() {
		return this.costTime;
	}

	public void setCostTime(int costTime) {
		this.costTime = costTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getException() {
		return StringUtils.left(this.exception, 1000);
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getCurrentDate() {
		return this.currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getReturnVal() {
		return returnVal;
	}

	public void setReturnVal(String returnVal) {
		this.returnVal = returnVal;
	}
	
}