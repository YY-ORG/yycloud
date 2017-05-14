package com.yy.cloud.common.utils.reflect.criteria;

import java.util.List;

import com.yy.cloud.common.utils.reflect.criteria.expression.PropertyExpression;
import com.yy.cloud.common.utils.reflect.filter.PropertyFilter;

/**
 * @author wejia
 * @create Jul 6, 2016
 */
public interface PropertyCriteria {

	public PropertyCriteria add(PropertyExpression expression);
	
	public List<PropertyFilter> getFilters();
	
}
