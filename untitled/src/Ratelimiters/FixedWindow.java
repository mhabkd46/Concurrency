package Ratelimiters;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

class WindowState {
    long minute;
    int count;

    public WindowState(long minute, int count) {
        this.minute = minute;
        this.count = count;
    }
}

public class FixedWindow {
    // Rate limiting by 100 requests per user per minute.
    private final ConcurrentHashMap<String, WindowState> userIdToWindowState;
    private final ConcurrentHashMap<String, Object> userIdToLockObjects;
    private final int allowedRequestsPerMinute;

    public FixedWindow() {
        this.userIdToLockObjects = new ConcurrentHashMap<>();
        this.userIdToWindowState = new ConcurrentHashMap<>();
        this.allowedRequestsPerMinute = 5;
    }

    public boolean canServeRequest(String userId) {
        long currentTimeInMillis = System.currentTimeMillis();
        long currentTimeInMinutes = currentTimeInMillis/1000/60;

        userIdToLockObjects.putIfAbsent(userId, new Object());
        Object lockObject = userIdToLockObjects.get(userId);

        synchronized(lockObject) {

            WindowState state = userIdToWindowState.get(userId);
            // new Window
            if (state == null) {
                userIdToWindowState.put(userId, new WindowState(currentTimeInMinutes, 1));
                System.out.println("Thread = " + Thread.currentThread().getName() + "; CurrentUser = " + userId + "; CurrentMinute = " + currentTimeInMinutes + "; CurrentRequestsCount = " + userIdToWindowState.get(userId).count + "; Intializing new Window");
                return true;
            }

            // in a new Window
            if (state.minute != currentTimeInMinutes) {
                state.minute = currentTimeInMinutes;
                state.count = 1;
                System.out.println("Thread = " + Thread.currentThread().getName() + "; CurrentUser = " + userId + "; CurrentMinute = " + currentTimeInMinutes + "; CurrentRequestsCount = " + userIdToWindowState.get(userId).count + "; Encountered new Window");
                return true;
            }

            if (state.count < allowedRequestsPerMinute) {
                userIdToWindowState.get(userId).count++;
                System.out.println("Thread = " + Thread.currentThread().getName() + "; CurrentUser = " + userId + "; CurrentMinute = " + currentTimeInMinutes + "; CurrentRequestsCount = " + userIdToWindowState.get(userId).count + "; Requests under limit");
                return true;
            }

            System.out.println("Thread = " + Thread.currentThread().getName() + "; CurrentUser = " + userId + "; CurrentMinute = " + currentTimeInMinutes + "; CurrentRequestsCount = " + userIdToWindowState.get(userId).count + "; FAILED");
            return false;
        }

    }
}
