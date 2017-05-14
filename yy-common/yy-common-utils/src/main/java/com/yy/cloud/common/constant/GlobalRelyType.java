package com.yy.cloud.common.constant;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: GlobalRelyType <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Oct 20, 2016 01:54:21 PM <br/>
 *
 * @author Huanfeng, cai
 * @version
 * @since JDK 1.8
 * @see
 */
public enum GlobalRelyType {

    NoRely(0, "无依赖"),

    MountRely(1, "挂载依赖"),

    ServiceRely(2, "服务依赖"),

    BundleRely(3, "捆绑依赖");

    /**
     * LOOKUP VALUE.
     */
    private static final Map<Integer, GlobalRelyType> LOOKUP_VALUE;

    static {
        final Map<Integer, GlobalRelyType> map = new HashMap<Integer, GlobalRelyType>();
        for (GlobalRelyType s : EnumSet.allOf(GlobalRelyType.class)) {
            map.put(s.getValue(), s);
        }
        LOOKUP_VALUE = Collections.unmodifiableMap(map);
    }

    private Integer value;
    private String text;

    private GlobalRelyType(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }

    public static GlobalRelyType getEnumByValue(Integer value) {
        if (!LOOKUP_VALUE.containsKey(value)) {
            throw new IllegalArgumentException();
        }
        return LOOKUP_VALUE.get(value);
    }
}
