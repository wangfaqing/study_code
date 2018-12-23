package com.common.utils.config.aop;

import com.common.utils.common.constants.DataSourceType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ds {

	DataSourceType value() default DataSourceType.COREDATASOURCE;
}
