package com.yy.cloud.baseplatform.authserver.constant;

public class AuthServerConstants {

    public static final String SITEURL = "http://localhost:8080";

    public static final String SESSION_CURRENT_LOGIN_USER = "CURRENT_LOGIN_USER";

    public static final String MAIL_RECEIVE = "mail_receive";
    public static final String MAIL_SUBJECT = "mail_subject";
    public static final String MAIL_SEND = "mail_subject";

    public static final String RQ_URL_USER_LOGIN = "/noauth/login";
    public static final String RQ_URL_USER = "/authsec/userinfo";
    public static final String RQ_URL_USERS_URL = "/authsec/admin/users";
    public static final String RQ_URL_CHANGE_PASSWORD = "/noauth/user/change-password";
    public static final String RQ_URL_REGISTER = "/noauth/register";
    public static final String RQ_URL_REGISTER_SUCCESS = "/noauth/register-success";
    public static final String RQ_URL_REGISTER_FAILED = "/noauth/register-failed";
    public static final String RQ_URL_ACTIVATE = "/noauth/activate";
    public static final String RQ_URL_REGISTER_RESENDMAIL = "/noauth/regstration/resendmail";


    public static final String ACTIVATION_TOKEN_APPROVED = "1";

    public static final short USER_STATUS_INITIAL = 0;
    public static final short USER_STATUS_ACTIVE = 1;
    public static final short USER_STATUS_SUSPENDED = 2;
    public static final short USER_STATUS_CANCELLED = 3;

    public static final short ACTIVATIONCODE_STATUS_INITIAL = 0;
    public static final short ACTIVATIONCODE_STATUS_ACTIVE = 1;
    public static final short ACTIVATIONCODE_STATUS_EXPIRED = 2;
    public static final short ACTIVATIONCODE_STATUS_DISCARD = 3;

    public static final short ACTIVATIONCODE_TYPE_ACTIVATION = 0;
    public static final short ACTIVATIONCODE_TYPE_RETRIEVEPWD = 1;


    public static final String FT_ACTIVATION_RESULT = "content/user/activate-result";
    public static final String FT_USER_LOGIN = "content/user/login";
    public static final String FT_REGISTER = "content/user/register";
    public static final String FT_REGISTER_SUCCESS = "content/user/register-success";
    public static final String FT_REGISTER_FAILED = "content/user/register-failed";

    public static final String SYSDIC_SEC_QUE_OWNER = "SECQUE";

    public static final String CURRENT_USER_LOGINNAME = "USER_LOGINNAME";
}
