/** <a href="http://www.cpupk.com/decompiler">Eclipse Class Decompiler</a> plugin, Copyright (c) 2017 Chen Chao. **/
package com.common.utils.methodLog.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MethodStatisticParams {
	private AtomicLong requestCount = new AtomicLong();

	private Map<String, AtomicLong> methodCountMap = new ConcurrentHashMap<>();
	private Map<String, AtomicLong> methodTimeMap = new ConcurrentHashMap<>();
	private Map<String, Long> methodAvgTimeMap = new HashMap<>();
	private String onlineLoginDataFilenaname;

	public AtomicLong getRequestCount() {
		return this.requestCount;
	}

	public Map<String, AtomicLong> getMethodCountMap() {
		return this.methodCountMap;
	}

	public Map<String, AtomicLong> getMethodTimeMap() {
		return this.methodTimeMap;
	}

	public long increaseMethodCount(String methodName) {
		AtomicLong count = (AtomicLong) this.methodCountMap.get(methodName);
		if (count == null) {
			count = new AtomicLong();
			this.methodCountMap.put(methodName, count);
		}
		return count.incrementAndGet();
	}

	public long addMethodTime(String methodName, long costTime) {
		AtomicLong timeSum = (AtomicLong) this.methodTimeMap.get(methodName);
		if (timeSum == null) {
			timeSum = new AtomicLong();
			this.methodTimeMap.put(methodName, timeSum);
		}
		return timeSum.addAndGet(costTime);
	}

	public void clearMap() {
		this.methodCountMap.clear();
		this.methodTimeMap.clear();
		this.methodAvgTimeMap.clear();
	}

	public Map<String, Long> getMethodAvgTimeMap() {
		for (String methodName : this.methodCountMap.keySet()) {
			AtomicLong time = (AtomicLong) this.methodTimeMap.get(methodName);
			AtomicLong count = (AtomicLong) this.methodCountMap.get(methodName);
			if (count.get() != 0L) {
				this.methodAvgTimeMap.put(methodName, Long.valueOf(time.get() / count.get()));
			}
		}
		return this.methodAvgTimeMap;
	}

	public String getOnlineLoginDataFilenaname() {
		return this.onlineLoginDataFilenaname;
	}

	public void setOnlineLoginDataFilenaname(String onlineLoginDataFilenaname) {
		this.onlineLoginDataFilenaname = onlineLoginDataFilenaname;
	}
}