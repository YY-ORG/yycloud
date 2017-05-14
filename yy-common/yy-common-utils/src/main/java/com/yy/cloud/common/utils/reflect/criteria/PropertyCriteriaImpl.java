package com.yy.cloud.common.utils.reflect.criteria;

import java.util.ArrayList;
import java.util.List;

import com.yy.cloud.common.utils.reflect.criteria.expression.PropertyExpression;
import com.yy.cloud.common.utils.reflect.filter.PropertyFilter;


/**
 * @author wejia
 * @create Jul 6, 2016
 */
public class PropertyCriteriaImpl implements PropertyCriteria {

	private List<PropertyFilter> filters = new ArrayList<PropertyFilter>();

	public List<PropertyFilter> getFilters() {
		return filters;
	}
	
	public PropertyCriteria add(PropertyExpression expression) {
		filters.add(expression.toFilter());
		return this;
	}
}
