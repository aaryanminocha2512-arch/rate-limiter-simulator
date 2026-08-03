package com.aryan.ratelimiter;

public class UserWindowTest {
    public static void main(String[] args) {

        // Create a UserWindow starting at time = 0
        UserWindow user = new UserWindow(0);

        int limit = 5;
        int windowSize = 10; // 10 seconds

        // Requests 1 through 5 -> all within Window [0-10], should be ALLOWED
        System.out.println("Request at t=1: " + user.allowRequest(1, limit, windowSize));
        System.out.println("Request at t=2: " + user.allowRequest(2, limit, windowSize));
        System.out.println("Request at t=3: " + user.allowRequest(3, limit, windowSize));
        System.out.println("Request at t=4: " + user.allowRequest(4, limit, windowSize));
        System.out.println("Request at t=5: " + user.allowRequest(5, limit, windowSize));

        // Request 6 -> still within Window [0-10], but limit already hit -> should be BLOCKED
        System.out.println("Request at t=6: " + user.allowRequest(6, limit, windowSize));

        // Another request still in same window -> should still be BLOCKED
        System.out.println("Request at t=9: " + user.allowRequest(9, limit, windowSize));

        // Request at t=11 -> crosses into a NEW window [10-20] -> counter resets -> should be ALLOWED
        System.out.println("Request at t=11: " + user.allowRequest(11, limit, windowSize));

        // A couple more in the new window, should be ALLOWED (only 2 used so far in this window)
        System.out.println("Request at t=12: " + user.allowRequest(12, limit, windowSize));
        System.out.println("Request at t=13: " + user.allowRequest(13, limit, windowSize));
    }
}