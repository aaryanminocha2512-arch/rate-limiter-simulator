package com.aryan.ratelimiter;

import java.util.ArrayList;
import java.util.List;

public class RequestSimulator {

    public static void main(String[] args) {

        String[] userIds = { "Aryan", "Aryan", "Aryan", "Aryan", "Aryan", "Aryan" };
        long[] timestamps = { 7, 8, 9, 10, 11, 12 };

        RateLimiter fixedWindow=new RateLimiter(StrategyType.FIXED_WINDOW);
        RateLimiter slidingWindow=new RateLimiter(StrategyType.SLIDING_WINDOW);
        RateLimiter tokenBucket=new RateLimiter(StrategyType.TOKEN_BUCKET);

        for (int i = 0; i < userIds.length; i++) {

            String currentUser =userIds[i];
            long currentTime=timestamps[i];

            boolean fixedResult = fixedWindow.allowRequest(currentUser, currentTime, 4, 10);
            boolean slidingResult = slidingWindow.allowRequest(currentUser, currentTime, 4, 10);
            boolean tokenResult = tokenBucket.allowRequest(currentUser, currentTime, 4, 10);

            System.out.println("t=" + currentTime + " | Fixed=" + fixedResult + " | Sliding=" + slidingResult + " | Token=" + tokenResult);
        }
    }
}