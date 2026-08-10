package com.aryan.ratelimiter;

public class RateLimiterTest {
    public static void main(String[] args) {

        RateLimiter fixedWindowLimiter = new RateLimiter(StrategyType.FIXED_WINDOW);
        System.out.println("Aryan's Case");

        System.out.println("request at t=0; "+fixedWindowLimiter.allowRequest("Aryan",0,4,10));
        System.out.println("request at t=1; "+fixedWindowLimiter.allowRequest("Aryan",1,4,10));
        System.out.println("request at t=2; "+fixedWindowLimiter.allowRequest("Aryan",2,4,10));
        System.out.println("request at t=3; "+fixedWindowLimiter.allowRequest("Aryan",3,4,10));
        System.out.println("request at t=4; "+fixedWindowLimiter.allowRequest("Aryan",4,4,10));
        System.out.println("request at t=12; "+fixedWindowLimiter.allowRequest("Aryan",12,4,10));
        System.out.println("request at t=14; "+fixedWindowLimiter.allowRequest("Aryan",14,4,10));

        System.out.println("Priya's case");

        System.out.println("request at t=0; "+fixedWindowLimiter.allowRequest("Priya",0,4,10));
        System.out.println("request at t=1; "+fixedWindowLimiter.allowRequest("Priya",1,4,10));
        System.out.println("request at t=2; "+fixedWindowLimiter.allowRequest("Priya",2,4,10));
        System.out.println("request at t=3; "+fixedWindowLimiter.allowRequest("Priya",3,4,10));
        System.out.println("request at t=4; "+fixedWindowLimiter.allowRequest("Priya",4,4,10));
        System.out.println("request at t=12; "+fixedWindowLimiter.allowRequest("Priya",12,4,10));
        System.out.println("request at t=14; "+fixedWindowLimiter.allowRequest("Priya",14,4,10));
    }
}