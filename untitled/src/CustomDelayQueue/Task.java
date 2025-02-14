package CustomDelayQueue;

public class Task implements Runnable, Comparable<Task>{
    long delayInMillis;
    long createdTime;
    long id;

    public Task(long createdTime, long delayInMillis, long id) {
        this.createdTime = createdTime;
        this.delayInMillis = delayInMillis;
        this.id = id;
    }
    @Override
    public void run() {
        System.out.println("Executing - " + Thread.currentThread().getName());
    }

    public long getTimeForExecution() {
        return createdTime + delayInMillis;
    }

    @Override
    public int compareTo(Task o) {
        return Long.compare(this.getTimeForExecution(), o.getTimeForExecution());
    }
}
