package com.llb.fllbwebsite.security;

public class SecurityConstants {

//    public static final String H2_URL ="/h2-console/**";
//    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX ="Bearer ";
    public static final String HEADER_STRING ="Authorization";
    public static final long EXPIRATION_TIME  = 60_000; //60 seconds


    //          *** List of Roles ***
    public static final String SUPER_ADMIN_ROLE = "SUPER_ADMIN";
    public static final String SUB_ADMIN_ROLE =  "SUB_ADMIN";
    public static final String DEFAULT_USER_ROLE = "USER";



    // *** Endpoints ***

    //          *** H2 console ***
    public static final String H2_URL = "/h2-console/**";

    //          *** User URLS ***
    public static final String DELETE_USER_URL = "/api/users/id/{userId}";   //SuperAdmin
    public static final String USERS_LIST_URL = "/api/users/all";   //SuperAdmin
    public static final String USER_UPDATE_URL = "/api/users/update/{userId}";   //Authenticated
    public static final String SIGN_UP_URL = "/api/users/register";    //PermitAll
    public static final String SIGN_IN_URL = "/api/users/login";    //PermitAll
    public static final String GET_USER_URL = "/api/users/id/{userId}";    //PermitAll

    //          *** POST URLS ***
    public static final String USER_POSTS_LIST_URL = "/api/posts/all/user";    //Super & Sub Admin
    public static final String CREATE_POSTS_URL = "/api/posts";    //Super & Sub Admin    Method POST
    public static final String DELETE_POSTS_URL = "/api/posts/id/{postId}";    //Super & Sub Admin  Method DELETE
    public static final String POST_URLS = "/api/posts/**";  //PermitAll

    //          *** COMMENTS URLS **AND** REACTION URLS ***
    public static final String COMMENTS_AND_LIKES_LIST_URLS = "/api/post/**/all";  //SuperAdmin
    public static final String COMMENT_AND_LIKE_URLS = "/api/post/**";  //Authenticated

    //          *** CATEGORY URLS ***
    public static final String CATEGORY_LIST_URL = "/api/categories/all";    //SuperAdmin
    public static final String CREATE_CATEGORY_URL = "/api/categories";    //SuperAdmin  Method POST
    public static final String DELETE_CATEGORY_URL = "/api/categories/id/{categoryId}";    //SuperAdmin
    public static final String CATEGORY_URLS = "/api/categories/**";   //PermitAll

    //          *** ROLE URLS ***
    public static final String ROLE_URLS = "/api/roles/**";    //SuperAdmin

}
