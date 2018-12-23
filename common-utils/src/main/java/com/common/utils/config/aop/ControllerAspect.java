package com.common.utils.config.aop;

import com.alibaba.fastjson.JSON;
import com.common.utils.common.base.entity.CommonException;
import com.common.utils.common.constants.ResultEnum;
import com.common.utils.common.util.ResponseUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class ControllerAspect {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Pointcut("execution(public * com.common.utils.controller.*.*(..))")
	private void controllerAspect() {
	}
	

	@Around(value = "controllerAspect()")
	public Object methodBefore(ProceedingJoinPoint joinPoint) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
//		String methodName = joinPoint.getSignature().toShortString();
		long beginTime = System.currentTimeMillis();
		// 打印请求内容
		log.info("===============请求内容 start ===============");
		log.info("请求IP:" + request.getRemoteAddr().toString());
		log.info("请求地址:" + request.getRequestURL().toString());
		log.info("请求方式:" + request.getMethod());
		log.info("请求类方法:" + joinPoint.getSignature());
		log.info("请求类方法参数:" + Arrays.toString(joinPoint.getArgs()));
		log.info("===============请求内容 end ===============");
		Object result = null;
		try {
			result = joinPoint.proceed();
		} catch (Throwable throwable) {
			if (throwable instanceof CommonException) {
				log.warn("异常捕获:", throwable);
				return ResponseUtil.fail((CommonException) throwable);
			} else {
				log.error("异常捕获:", throwable);
			}
			return ResponseUtil.fail(ResultEnum.systemError);
		}
		long endTime = System.currentTimeMillis();
		log.info("耗时：" + (endTime - beginTime));
		log.info("返回结果:" + JSON.toJSONString(result));
		return result;
	}

}
