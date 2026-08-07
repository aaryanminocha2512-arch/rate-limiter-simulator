package com.aryan.ratelimiter;

import java.util.Queue;
import java.util.LinkedList;

public class SlidingWindowUser implements RateLimitStrategy {

    private Queue<Long> requestTimestamps;
// queue follows fifo rule and here elements are added from back and are removed from front.
    public SlidingWindowUser() {
        this.requestTimestamps=new LinkedList<>();
    }

    @Override
    public boolean allowRequest(long currentTime, int limit, int windowSizeInSeconds) {
        while(requestTimestamps.peek()!=null) {
            // peek return the first element of the queue without removing it
            if (requestTimestamps.peek() < currentTime - windowSizeInSeconds) {
                requestTimestamps.poll();
            }
            else{
                break;
            }

        }
        if(requestTimestamps.size()>=limit){
                return false;
        }
        else{
            requestTimestamps.add(currentTime);
            return true;
        }
    }
}