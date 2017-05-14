package com.yy.cloud.common.utils.reflect;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author wejia
 * @create Jul 6, 2016
 */
public enum SimpleClazz {

	STRING(String.class),
	
	LONG(Long.class),
	
	INTEGER(Integer.class),
	
	DOUBLE(Double.class),
	
	DATE(Date.class),
	
	TIMESTAMP(Timestamp.class),
	
	BOOLEAN(Boolean.class),
	
	BIGDECIMAL(BigDecimal.class);
	
	Class<?> clazz;

	private SimpleClazz(Class<?> clazz) {
		this.clazz = clazz;
		
	}

	public Class<?> getClazz() {
		return clazz;
	}

}
