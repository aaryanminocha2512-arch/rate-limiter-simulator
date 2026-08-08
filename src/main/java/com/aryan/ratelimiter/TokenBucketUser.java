package com.aryan.ratelimiter;

public class TokenBucketUser implements RateLimitStrategy {

    private double tokens;//no of tokens at last seen
    private long lastRefillTime;//time at last seen
    private double maxCapacity;//max capacity of tokens
    private double refillRatePerSecond; // tokens added per second

    public TokenBucketUser(double maxCapacity, double refillRatePerSecond, long currentTime) {
        this.tokens=maxCapacity;
        this.lastRefillTime=currentTime;
        this.refillRatePerSecond=refillRatePerSecond;
        this.maxCapacity=maxCapacity;
    }

    @Override
    public boolean allowRequest(long currentTime, int limit, int windowSizeInSeconds) {
        // Note: 'limit' and 'windowSizeInSeconds' aren't really used here,
        // since Token Bucket has its own rules (maxCapacity, refillRatePerSecond)
        // We keep them in the signature only because the interface requires it

         long timePassed = currentTime - lastRefillTime;
         // calculating the passed time

         double tokensToAdd = timePassed * refillRatePerSecond;
         //adding tokens based on time passed


         tokens=Math.min(maxCapacity, tokens + tokensToAdd);

        lastRefillTime=currentTime;

       if(tokens>=1){
           tokens--;
           return true;
       }
       else{
           return false;
       }
    }
}