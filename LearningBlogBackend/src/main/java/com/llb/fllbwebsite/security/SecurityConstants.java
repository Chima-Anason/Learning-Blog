package com.llb.fllbwebsite.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS ="/api/users/**";
    public static final String H2_URL ="/h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX ="Bearer ";
    public static final String HEADER_STRING ="Authorization";
    public static final long EXPIRATION_TIME  = 60_000; //60 seconds


    //          *** List of Roles ***
    public static final String SUPER_ADMIN_ROLE = "SUPER_ADMIN";
    public static final String SUB_ADMIN_ROLE =  "SUB_ADMIN";
    public static final String DEFAULT_USER_ROLE = "USER";

}
