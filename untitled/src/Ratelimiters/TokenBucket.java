package Ratelimiters;

import java.util.concurrent.ConcurrentHashMap;

class TBucket {
    long lastUpdatedSecond;
    int count;

    public TBucket(long lastUpdatedSecond, int count) {
        this.lastUpdatedSecond = lastUpdatedSecond;
        this.count = count;
    }
}
public class TokenBucket {
    // token bucket refills every second
    private ConcurrentHashMap<String, TBucket> userIdToBucket;
    private ConcurrentHashMap<String, Object> userIdToLockObjects;

    private int maximumTokens;

    public TokenBucket() {
        this.userIdToBucket = new ConcurrentHashMap<>();
        this.userIdToLockObjects = new ConcurrentHashMap<>();
        this.maximumTokens = 100;
    }

    public boolean canServeRequest(String userId) {
        long currentTimeInMillis = System.currentTimeMillis();
        long currentTimeInSeconds = currentTimeInMillis/1000;

        userIdToLockObjects.putIfAbsent(userId, new Object());
        Object lockObject = userIdToLockObjects.get(userId);

        synchronized (lockObject) {
            TBucket bucket = userIdToBucket.get(userId);

            if (bucket == null) {
                TBucket newBucket = new TBucket(currentTimeInSeconds, maximumTokens - 1);
                userIdToBucket.put(userId, newBucket);

                return true;
            }

            long elapsed = currentTimeInSeconds - bucket.lastUpdatedSecond;
            if (elapsed > 0) {
                int refill = (int) elapsed; // 1 token/sec
                bucket.count = Math.min(maximumTokens, bucket.count + refill);
                bucket.lastUpdatedSecond += elapsed;
            }

            if (bucket.count > 0) {
                bucket.count--;
                return true;
            }

            return false;
        }
    }
}
