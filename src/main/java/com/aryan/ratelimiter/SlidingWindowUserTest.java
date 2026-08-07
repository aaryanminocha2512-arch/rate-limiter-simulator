package com.aryan.ratelimiter;

public class SlidingWindowUserTest {
    public static void main(String[] args) {
        SlidingWindowUser user =new SlidingWindowUser();
        int limit=3;
        int windowSize=6;

        System.out.println("at t=2 sec: "+user.allowRequest(2,limit,windowSize));
        System.out.println("at t=3 sec: "+user.allowRequest(3,limit,windowSize));
        System.out.println("at t=5 sec: "+user.allowRequest(5,limit,windowSize));
        System.out.println("at t=9 sec: "+user.allowRequest(9,limit,windowSize));
        System.out.println("at t=10 sec: "+user.allowRequest(10,limit,windowSize));
    }
}
