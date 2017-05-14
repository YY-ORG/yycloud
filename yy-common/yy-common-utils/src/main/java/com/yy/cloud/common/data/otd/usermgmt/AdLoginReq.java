package com.yy.cloud.common.data.otd.usermgmt;

import java.io.Serializable;

import lombok.Data;

@Data
public class AdLoginReq implements Serializable {
	
	private String login;
	private String password;
	private String ldapId;

	private static final long serialVersionUID = 7728802898047169805L;

}
