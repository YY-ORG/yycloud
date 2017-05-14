package com.yy.cloud.common.utils;

/**
 * Created by guof on 2016/10/31.
 */
public class PageUtils {

    public static int getPageNo(Integer _page) {
        int tempPage = _page - 1;
        return tempPage >= 0 ? tempPage : 0;
    }

}
