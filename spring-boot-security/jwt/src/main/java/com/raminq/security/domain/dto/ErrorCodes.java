package com.raminq.security.domain.dto;

public class ErrorCodes {

    //Error
    public static final int INTERNAL_SERVER_ERROR = 1000;
    public static final int ACCESS_DENIED = 1001;
    public static final int UN_AUTHORIZED = 1002;
    public static final int USER_NOT_FOUND_IN_SECURITY_CONTEXT = 1003;
    public static final int USER_HAS_NO_PERMISSION_TO_VIEW_THIS_RESOURCE = 1004;
    public static final int REFRESH_TOKEN_IS_NOT_IN_DATABASE = 1005;
    public static final int REFRESH_TOKEN_WAS_EXPIRED = 1006;




    //User(Prefix = 201)
    public static final int USER_NOT_FOUND_BY_ID = 2011;
    public static final int USER_NOT_FOUND_BY_NAME = 2012;
    public static final int USER_DISABLED = 2013;

    //Role(Prefix = 202)
    public static final int ROLE_NOT_FOUND_BY_NAME = 2021;
}
