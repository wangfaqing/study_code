package com.common.project.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.common.project.common.constants.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
	public static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);
	@Value("${druid.core.config.file}")
	private String coreConnectionProperties;

//	@Value("${druid.core.config.file}")
//	private String logConnectionProperties;
	
	@Bean(name = "coreDataSource", destroyMethod = "close", initMethod = "init")
	public DataSource coreDataSource() throws Exception {
		DruidDataSource ds = new DruidDataSource();
		ds.setFilters("config");
		ds.setConnectionProperties("config.file="+coreConnectionProperties);
		return ds;
	}


//	@Bean(name = "logDataSource", destroyMethod = "close", initMethod = "init")
//	public DataSource dataDataSource() throws Exception {
//		DruidDataSource ds = new DruidDataSource();
//		ds.setFilters("config");
//		ds.setConnectionProperties("config.file="+logConnectionProperties);
//		return ds;
//	}

	/**
	 * 
	 * dataSource:多数据源配置时在此处添加即可
	 * @param coreDataSource
	 * @return
	 */
	@Bean(name = "dynamicDataSource")
	@Primary
	public DynamicDataSource dataSource(@Autowired DataSource coreDataSource) {
		Map<Object, Object> targetDataSources = new HashMap();
		targetDataSources.put(DataSourceType.COREDATASOURCE.name(), coreDataSource);
		return new DynamicDataSource(coreDataSource, targetDataSources);
	}
}
