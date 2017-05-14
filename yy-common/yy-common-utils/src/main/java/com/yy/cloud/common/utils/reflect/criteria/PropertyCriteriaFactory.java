package com.yy.cloud.common.utils.reflect.criteria;

/**
 * @author wejia
 * @create Jul 6, 2016
 */
public class PropertyCriteriaFactory {

	public static PropertyCriteria create() {
		return new PropertyCriteriaImpl();
	}
	
}
