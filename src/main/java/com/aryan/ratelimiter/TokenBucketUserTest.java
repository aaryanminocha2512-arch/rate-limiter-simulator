package com.aryan.ratelimiter;
public class TokenBucketUserTest {
    public static void main(String[] args) {
        TokenBucketUser user =new TokenBucketUser(5,2,0);
        System.out.println("request 1 : "+user.allowRequest(0,0,0));
        System.out.println("request 2 : "+user.allowRequest(0,0,0));
        System.out.println("request 3 : "+user.allowRequest(0,0,0));
        System.out.println("request 4 : "+user.allowRequest(0,0,0));
        System.out.println("request 5 : "+user.allowRequest(0,0,0));
        System.out.println("request 6 : "+user.allowRequest(0,0,0));
        System.out.println("request 7 : "+user.allowRequest(3,0,0));

    }
}
