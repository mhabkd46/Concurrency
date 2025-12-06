package Ratelimiters;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class SlidingWindowState {
    HashMap<Long, Integer> secondWindows;
    int totalCount;

    public SlidingWindowState(HashMap<Long, Integer> secondWindows, int totalCount) {
        this.secondWindows = secondWindows;
        this.totalCount = totalCount;
    }

}



public class SlidingWindow {
    // Rate limiting by 100 requests per user per minute sliding window.

    private ConcurrentHashMap<String, SlidingWindowState> userIdToSlidingWindowState;
    private ConcurrentHashMap<String, Object> userIdToLockObjects;

    private final int allowedRequestsPerMinute;

    public SlidingWindow() {
        this.userIdToLockObjects = new ConcurrentHashMap<>();
        this.userIdToSlidingWindowState = new ConcurrentHashMap<>();
        this.allowedRequestsPerMinute = 100;
    }

    public boolean canServeRequest(String userId) {
        long currentTimeInMillis = System.currentTimeMillis();
        long currentTimeInSeconds = currentTimeInMillis/1000;

        userIdToLockObjects.putIfAbsent(userId, new Object());
        Object lockObject = userIdToLockObjects.get(userId);

        synchronized (lockObject) {
            SlidingWindowState state = userIdToSlidingWindowState.get(userId);

            if (state == null ) {
                SlidingWindowState newState = new SlidingWindowState(new HashMap<>(), 1);
                userIdToSlidingWindowState.put(userId, newState);
                userIdToSlidingWindowState.get(userId).secondWindows.put(currentTimeInSeconds, 1);
                return true;
            }

            expireOutOfWindowRequests(state, currentTimeInSeconds);

            if (state.totalCount < allowedRequestsPerMinute) {
                state.secondWindows.put(currentTimeInSeconds ,state.secondWindows.getOrDefault(currentTimeInSeconds, 0) + 1);
                state.totalCount++;
                return true;
            }

            return false;
        }


    }

    private void expireOutOfWindowRequests(SlidingWindowState state, long currentTimeInSeconds) {

        Iterator<Map.Entry<Long, Integer>> it = state.secondWindows.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Long, Integer> entry = it.next();
            if (entry.getKey() <= currentTimeInSeconds - 60) {
                it.remove();
                state.totalCount -= entry.getValue();
            }
        }
    }
}
