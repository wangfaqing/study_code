package com.common.project.methodLog.entity;

import com.common.project.methodLog.util.DateUtils;

import java.io.Serializable;
import java.util.Date;

public class MethodStatisticsLog implements Serializable {

	private static final long serialVersionUID = 758491068137067908L;

	private String methodName;
	private String serverName;
	private long methodCount;
	private long methodSumTime;
	private int statisticsInterval;
	private Date createTime;
	private String currentDate;

	public MethodStatisticsLog() {
		this.createTime = new Date();
		if (this.currentDate == null)
			this.currentDate = DateUtils.formatDate(this.createTime, "yyyyMMdd");
	}

	public String getMethodName() {
		return this.methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getServerName() {
		return this.serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public long getMethodCount() {
		return this.methodCount;
	}

	public void setMethodCount(long methodCount) {
		this.methodCount = methodCount;
	}

	public long getMethodSumTime() {
		return this.methodSumTime;
	}

	public void setMethodSumTime(long methodSumTime) {
		this.methodSumTime = methodSumTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCurrentDate() {
		return this.currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public int getStatisticsInterval() {
		return this.statisticsInterval;
	}

	public void setStatisticsInterval(int statisticsInterval) {
		this.statisticsInterval = statisticsInterval;
	}
}