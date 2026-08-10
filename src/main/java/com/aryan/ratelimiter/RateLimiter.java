package com.aryan.ratelimiter;

import java.util.HashMap;
import java.util.Map;

public class RateLimiter {

    private Map<String, RateLimitStrategy> userLimiters;
    private StrategyType strategyType;

    public RateLimiter(StrategyType strategyType) {
        this.userLimiters = new HashMap<>();
        this.strategyType = strategyType;
    }

    public boolean allowRequest(String userId, long currentTime, int limit, int windowSizeInSeconds) {

        if (!userLimiters.containsKey(userId)) {
            RateLimitStrategy newUser;

            if (strategyType == StrategyType.FIXED_WINDOW) {
                newUser = new UserWindow(currentTime);
            } else if (strategyType == StrategyType.SLIDING_WINDOW) {
                newUser=new SlidingWindowUser();
            } else {
                newUser=new TokenBucketUser(limit,2,currentTime);
            }

            userLimiters.put(userId, newUser);
        }

        RateLimitStrategy userTracker = userLimiters.get(userId);
        return userTracker.allowRequest(currentTime, limit, windowSizeInSeconds);
    }
}