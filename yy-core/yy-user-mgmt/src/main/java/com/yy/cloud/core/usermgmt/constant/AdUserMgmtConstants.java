package com.yy.cloud.core.usermgmt.constant;

/**
 * ClassName: LdapMgmtConstants <br/>
 * Function: TODO <br/>
 * Reason: TODO <br/>
 * date: 2016年11月4日 <br/>
 *
 * @author chenxj
 * @version
 * @since JDK 1.8
 */
public class AdUserMgmtConstants {

	public static final String RQ_URL_TEST = "/authsec/test/";

	public static final byte STATUS_GLOBAL_INITIAL = 0;
	public static final byte STATUS_GLOBAL_ENABLE = 1;
	public static final byte STATUS_GLOBAL_SUSPENDED = 2;
	public static final byte STATUS_GLOBAL_CANCELLED = 3;
	public static final byte STATUS_GLOBAL_DELETED = 4;
	
	public static final byte USER_TYPE_LOCAL = 0;
	public static final byte USER_TYPE_AD = 1;

	public static final Byte TENANT_AUTH_AD=1;

}
