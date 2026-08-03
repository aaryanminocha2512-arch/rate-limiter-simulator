package com.aryan.ratelimiter;

public class UserWindow {
    private int requestCount;
    private long windowStartTime; // in seconds (or milliseconds, your choice)

    public UserWindow(long currentTime) {
       this.windowStartTime=currentTime;
    }
    public boolean allowRequest(long currentTime, int limit, int windowSizeInSeconds) {

       if(currentTime>=windowStartTime+windowSizeInSeconds){
           requestCount=0;
           windowStartTime=currentTime;
       }
        if(requestCount>=limit){
            return false;
        }
        else{
            requestCount++;
            return true;
        }


    }
}