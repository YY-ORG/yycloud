package com.yy.cloud.common.utils.reflect.criteria.expression;

import com.yy.cloud.common.utils.reflect.filter.PropertyFilter;

/**
 * @author wejia
 * @create Jul 6, 2016
 */
public interface PropertyExpression {

	public PropertyFilter toFilter();
	
}
