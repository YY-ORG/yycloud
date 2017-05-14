package com.yy.cloud.common.utils.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import com.yy.cloud.common.utils.reflect.criteria.PropertyCriteria;
import com.yy.cloud.common.utils.reflect.criteria.PropertyCriteriaFactory;
import com.yy.cloud.common.utils.reflect.filter.PropertyFilter;


/**
 * @author wejia
 * @create Jul 6, 2016
 */
public class ReflectUtils {

	public static void copyProperties(Object source, Object target) {
		PropertyCriteria criteria = PropertyCriteriaFactory.create();
		copyProperties(source, target, criteria);
	}

	public static void copyProperties(Object source, Object target,
			PropertyCriteria criteria) {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");
		Assert.notNull(criteria, "Criteria must not be null");

		Class<? extends Object> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = BeanUtils
				.getPropertyDescriptors(actualEditable);

		for (int i = 0; i < targetPds.length; i++) {
			PropertyDescriptor targetPd = targetPds[i];

			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(
						source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method srcReadMethod = sourcePd.getReadMethod();
						Method tgtReadMethod = targetPd.getReadMethod();

						accessMethod(srcReadMethod);
						accessMethod(tgtReadMethod);

						Object sourceValue = srcReadMethod.invoke(source,
								new Object[0]);
						
						if(sourceValue != null){
							boolean permit = isPermittedProperty(sourcePd,
									sourceValue, criteria);
							if (permit) {
								if(isSimpleProperty(sourcePd)) {//复制简单对象类型
									Method writeMethod = targetPd.getWriteMethod();
									accessMethod(writeMethod);
									try {
										writeMethod.invoke(target,
												new Object[] { sourceValue });
									} catch (Exception e) {
										
									}
								}

							}						
						}
					} catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy properties from source to target",
								ex);
					}
				}
			}
		}
	}
	
	private static void accessMethod(Method method) {
		if (!Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
			method.setAccessible(true);
		}
	}
	
	private static boolean isPermittedProperty(PropertyDescriptor pd,
			Object value, PropertyCriteria criteria) {
		boolean retval = true;
		List<PropertyFilter> propertyFilters = criteria.getFilters();
		for (PropertyFilter filter : propertyFilters) {
			if (!filter.isPermit(pd, value)) {
				retval = false;
				break;
			}
		}
		return retval;
	}
	
	private static boolean isSimpleProperty(
			PropertyDescriptor propertyDescriptor) {
		boolean retval = false;

		Class<? extends Object> propertyType = propertyDescriptor
				.getPropertyType();

		for (SimpleClazz clazz : SimpleClazz.values()) {
			if (propertyType == clazz.getClazz()) {
				retval = true;
				break;
			}
		}

		return retval;
	}
}
