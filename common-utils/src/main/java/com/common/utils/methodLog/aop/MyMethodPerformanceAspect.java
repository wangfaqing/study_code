package com.common.utils.methodLog.aop;

import com.alibaba.fastjson.JSON;
import com.common.utils.methodLog.domain.MethodStatisicParams;
import com.common.utils.methodLog.entity.MethodLog;
import com.common.utils.methodLog.entity.MethodStatisticsLog;
import com.common.utils.methodLog.executeBuffer.MyDelayExecuteBuffer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicLong;

@Component("MyMethodPerformanceAspect")
public class MyMethodPerformanceAspect implements Runnable {
	public static final Logger LOGGER = LoggerFactory.getLogger(MyMethodPerformanceAspect.class);
	@Value("${method.serverName}")
	private String serverName = "common-utils";
	@Value("${method.alertTime}")
	private int alertTime = 3;
	@Value("${method.statisticsInterval}")
	private int statisticsInterval = 10000;

	@Resource(name = "methodLogExecuteBuffer")
	private MyDelayExecuteBuffer<MethodLog> methodLogExecuteBuffer;

	@Resource(name = "methodStatisticsExecuteBuffer")
	private MyDelayExecuteBuffer<MethodStatisticsLog> methodStatisticsExecuteBuffer;

	private MethodStatisicParams controllerParameter = new MethodStatisicParams();

	@PostConstruct
	public void start() {
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(this.statisticsInterval);
				for (String methodName : this.controllerParameter.getMethodCountMap().keySet()) {
					AtomicLong time = (AtomicLong) this.controllerParameter.getMethodTimeMap().get(methodName);
					AtomicLong count = (AtomicLong) this.controllerParameter.getMethodCountMap().get(methodName);
					MethodStatisticsLog methodStatisticsLog = new MethodStatisticsLog();
					methodStatisticsLog.setMethodName(methodName);
					methodStatisticsLog.setServerName(this.serverName);
					methodStatisticsLog.setMethodCount(count.get());
					methodStatisticsLog.setMethodSumTime(time.get());
					methodStatisticsLog.setStatisticsInterval(this.statisticsInterval);
					this.methodStatisticsExecuteBuffer.add(methodStatisticsLog);
				}
				this.controllerParameter.getMethodCountMap().clear();
				this.controllerParameter.getMethodTimeMap().clear();
			} catch (Exception e) {
				LOGGER.error("", e);
			}
		}

	}

	private void addMethodTime(String methodName, long costTime) {
		this.controllerParameter.addMethodTime(methodName, costTime);
		this.controllerParameter.increaseMethodCount(methodName);
	}

	public Object doAround(ProceedingJoinPoint jp) throws Throwable {
		String clazz = jp.getTarget().getClass().getName();
		LOGGER.debug("class name is [{}]", clazz);
		clazz = StringUtils.substringBefore(clazz, "$$EnhancerByCGLIB");
		LOGGER.debug("class name is [{}]", clazz);

		String methodName = clazz + "." + jp.getSignature().getName();
		long costTime = System.currentTimeMillis();
		Object retVal = jp.proceed();
		costTime = System.currentTimeMillis() - costTime;

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("methodName[{}] cost [{}]", new Object[] { methodName, Long.valueOf(costTime) });
		}
		addMethodTime(methodName, costTime);

		if (costTime >= this.alertTime) {
			String params = "";
			for (Object arg : jp.getArgs()) {
				params = params + ToStringBuilder.reflectionToString(arg);
			}
			MethodLog methodLog = new MethodLog();
			methodLog.setCostTime((int) costTime);
			methodLog.setMethodName(methodName);
			methodLog.setParams(params);
			methodLog.setServerName(this.serverName);
			if(retVal != null) {
				methodLog.setReturnVal(JSON.toJSONString(retVal));
			}
			//如果返回的内容过长则不添加数据库记录
			if(retVal != null && methodLog.getReturnVal().length()<2000) {
				this.methodLogExecuteBuffer.add(methodLog);
			}
		}
		return retVal;
	}

	public void doThrowing(JoinPoint jp, Throwable ex) {
		String methodName = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
		String params = "";
		for (Object arg : jp.getArgs()) {
			params = params + ToStringBuilder.reflectionToString(arg);
		}
		LOGGER.error("", ex);
		String exception = ex.toString();

		MethodLog methodLog = new MethodLog();
		methodLog.setMethodName(methodName);
		methodLog.setParams(params);
		methodLog.setException(exception);
		methodLog.setServerName(this.serverName);
		this.methodLogExecuteBuffer.add(methodLog);
	}

}
