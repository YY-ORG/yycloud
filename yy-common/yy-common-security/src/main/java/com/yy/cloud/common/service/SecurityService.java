package com.yy.cloud.common.service;

import com.yy.cloud.common.data.otd.usermgmt.UserDetailsItem;

/**
 * Created by chenxj on 12/10/16.
 */
public interface SecurityService {

    /**
     * getCurrentUser: 获取当前登录用户的详细信息
     *
     * @return
     */
    public UserDetailsItem getCurrentUser();

}
