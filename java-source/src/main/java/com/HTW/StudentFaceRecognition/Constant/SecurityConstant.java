package com.HTW.StudentFaceRecognition.Constant;

public class SecurityConstant {
    public static final String BEARER_TOKEN_PREFIX = "Bearer ";
    public static final String BASIC_TOKEN_PREFIX =  "Basic ";
    public static final String AUTH_HEADER_BASIC = "AuthorizationBasic";
    public static final String AUTH_HEADER_TOKEN = "AuthorizationToken";

    public static final String JWT_SECRET = "TmdvY0h1eUxlU2VjcmV0S2V5SldUSFRXRHJlc2RlblByb2plY3Q=";
    //public static final String JWT_SECRET = "NgocHuyLeSecretKeyJWTHTWDresdenProject";
    //public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    //public static final long EXPIRATION_TIME = 36_000_000; // 10 hours
    public static final long EXPIRATION_TIME = 3_600_000;// 1 hour
    //public static final long EXPIRATION_TIME = 600_000; // 10 minutes

}
