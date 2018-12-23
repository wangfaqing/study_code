package com.common.utils.methodLog.mapper;

import com.common.utils.common.constants.DataSourceType;
import com.common.utils.config.aop.Ds;
import com.common.utils.methodLog.entity.MethodStatisticsLog;
import org.springframework.stereotype.Repository;

@Repository
@Ds(DataSourceType.COREDATASOURCE)
public interface MethodStatisticsLogMapper extends MyLogMapper<MethodStatisticsLog> {

}
