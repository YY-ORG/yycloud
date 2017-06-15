package com.yy.cloud.core.usermgmt.constant;

public class UserMgmtConstants {

	public static final String RQ_URL_TEST = "/authsec/test/";

	public static final byte STATUS_GLOBAL_INITIAL = 0;
	public static final byte STATUS_GLOBAL_ENABLE = 1;
	public static final byte STATUS_GLOBAL_SUSPENDED = 2;
	public static final byte STATUS_GLOBAL_CANCELLED = 3;
	public static final byte STATUS_GLOBAL_DELETED = 4;

	public static final String USER_ROOT_OWNER = "USER";
	public static final String USER_ROOT_FIELD = "ROOT";
	public static final String USER_PASSWORD_OWNER = "USER";
	public static final String USER_PASSWORD_FIELD = "PASSWORD";
	public static final String USER_PASSWORD_RANDOM_CODE = "RANDOM";

	public static final byte USER_TYPE_LOCAL = 0;
	public static final byte USER_TYPE_AD = 1;

	public static final Byte TENANT_AUTH_AD = 1;

	public static final byte DEFAULT_AUDIT_ENABLE = 1;
	public static final int DEFAULT_AUDIT_TIME = 10;

	public final static String CODE_DEFAULT_ADMIN_DEPARTMENT_OF_ENTERPRISE = "ADMIN_DEPARTMENT";
	public final static String CODE_DEFAULT_NORMAL_DEPARTMENT_OF_ENTERPRISE = "NORMAL_DEPARTMENT";
	public final static String OWNER_ORGANIZATION = "ORGANIZATION";
	public final static String FIELD_DEFAULT_CODE_ORGANIZATION = "DEFAULT_CODE";
	
	
	
	// 0：系统管理员账号  1：系统账号 2：个人账号	 
	 public final static byte ACCOUNT_TYPE_SYSTEMADMIN = 0;
	 public final static byte ACCOUNT_TYPE_INTERNAL_SYSTEM = 1;
	 public final static byte ACCOUNT_TYPE_PERSONAL = 2;
		
	 /**
	  * 默认角色
	  */
	 public final static String ACCOUNT_DEFALUT_ROLE = "DEFAULTROLE";
	 

}
