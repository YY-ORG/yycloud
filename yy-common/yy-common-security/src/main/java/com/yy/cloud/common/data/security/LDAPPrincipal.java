package com.yy.cloud.common.data.security;

import java.io.Serializable;
import java.security.Principal;

/**
 * Created by chenxj on 12/14/16.
 */
public class LDAPPrincipal implements Principal, Serializable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
    private String name;


    public void setName(String _name){
        this.name = _name;
    }
    @Override
    public String getName() {
        return this.name;
    }
}
