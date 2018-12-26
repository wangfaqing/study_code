package com.common.project.methodLog.mapper;

import com.common.project.common.constants.DataSourceType;
import com.common.project.config.aop.Ds;
import com.common.project.methodLog.entity.MethodLog;
import org.springframework.stereotype.Repository;

@Repository
@Ds(DataSourceType.COREDATASOURCE)
public interface MethodLogMapper extends MyLogMapper<MethodLog> {
	
}
