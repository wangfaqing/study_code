package com.common.utils.methodLog.mapper;


import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MyLogMapper<T> {
    void batchInsertLog(@Param("logs") List<T> logs, @Param("currentDate") String currentDate);
} 
