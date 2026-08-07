package com.aryan.ratelimiter;

public interface RateLimitStrategy {
    public boolean allowRequest(long currentTime,int limit,int windowSizeInSeconds);
}
