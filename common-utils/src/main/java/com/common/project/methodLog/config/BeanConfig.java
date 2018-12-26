package com.common.project.methodLog.config;

import com.common.project.methodLog.entity.MethodLog;
import com.common.project.methodLog.entity.MethodStatisticsLog;
import com.common.project.methodLog.executeBuffer.MyDelayExecuteBuffer;
import com.common.project.methodLog.mapper.MethodLogMapper;
import com.common.project.methodLog.mapper.MethodStatisticsLogMapper;
import com.common.project.methodLog.mapper.MyLogMapper;
import com.common.project.methodLog.mapper.MyMapperProxy;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.common.project.methodLog.mapper")
public class BeanConfig {
	public static final Logger LOGGER = LoggerFactory.getLogger(BeanConfig.class);

	@Autowired
	private MethodStatisticsLogMapper methodStatisticsLogMapper;

	@Autowired
	private MethodLogMapper methodLogMapper;
	

	@Value("${data.record.cached.count}")
	private int poolSize;
	
	@Value("${data.record.check.interval}")
	private int checkInterval;
	
	@Value("${data.record.threads}")
	private int threads;
	
	@Value("${data.record.batch.size}")
	private int bacthSize;


	@Bean(name = "methodLogExecuteBuffer", initMethod = "start", destroyMethod = "flush")
	public MyDelayExecuteBuffer<MethodLog> methodLogExecuteBuffer() {
		return initMyDelayExecuteBuffer(methodLogMapper);
	}

	@Bean(name = "methodStatisticsExecuteBuffer", initMethod = "start", destroyMethod = "flush")
	public MyDelayExecuteBuffer<MethodStatisticsLog> methodStatisticsExecuteBuffer() {
		return initMyDelayExecuteBuffer(methodStatisticsLogMapper);
	}

	private <T> MyDelayExecuteBuffer<T> initMyDelayExecuteBuffer(MyLogMapper<T> mapper) {
		MyDelayExecuteBuffer<T> buffer = new MyDelayExecuteBuffer<T>();
		buffer.setBatchExecutor(myMapperProxy(mapper));
		buffer.setName(mapper.getClass().getName());
		buffer.setPoolSize(poolSize);
		buffer.setCheckInterval(checkInterval);
		buffer.setThreads(threads);
		buffer.setBatchSize(bacthSize);
		return buffer;
	}

	private <T> MyMapperProxy<T> myMapperProxy(MyLogMapper<T> mapper) {
		MyMapperProxy<T> myMapperProxy = new MyMapperProxy<T>(mapper);
		return myMapperProxy;
	}

}
