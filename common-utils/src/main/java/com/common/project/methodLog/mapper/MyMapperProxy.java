package com.common.project.methodLog.mapper;


import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.common.project.methodLog.executeBuffer.IBatchExecutor;
import com.common.project.methodLog.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class MyMapperProxy<T> implements IBatchExecutor<T> {
	public static final Logger LOGGER = LoggerFactory.getLogger(MyMapperProxy.class);
    
	private MyLogMapper<T> mapper;
	
	public MyMapperProxy(MyLogMapper<T> mapper) {
		this.mapper = mapper;
	}
    
	@Override
	public void execute(List<T> records) {
		if(CollectionUtils.isNotEmpty(records)) {
			mapper.batchInsertLog(records, DateUtils.formatDate(new Date(), "yyyyMMdd"));
		}
	}
}
