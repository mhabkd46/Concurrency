package CustomDelayQueue;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
https://silhding.github.io/2021/03/13/How-to-design-a-delayed-scheduler-in-Java/
1. Polling:
    Define polling frequency and polling time
2. Use Leader Follower architecture.
    Elect a leader thread. Leader thread will keep on waiting on the smallest Task delay of the Priority Queue. If a new element is added and if it is
    now the smallest delay, then the current leader is demoted and a new leader is reelected.
*/

public class CustomDelayQueue {
    private PriorityQueue<Task> pq;
    private ReentrantLock lock ;
    private Condition condition ;
    private Thread leader;

    public CustomDelayQueue() {
        pq = new PriorityQueue<Task>();
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public void add(Task t) {

        lock.lock();

        try {
            pq.offer(t);
            if (t.id == pq.peek().id) {
                leader = null;
                condition.signalAll();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
        }

    }

    public Task poll() {

        while(true) {
            lock.lock();

            try {
                Task t = pq.peek();
                if (t == null) {
                    condition.await();
                }
                else {
                    long delay = t.getTimeForExecution() - System.currentTimeMillis();

                    if (delay <= 0) {
                        t = null;
                        return pq.poll();
                    }
                    if (leader == null) {
                        Thread thread = Thread.currentThread();
                        leader = thread;
                        try {
                            condition.await(delay, TimeUnit.MILLISECONDS);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        finally {
                            if (leader == thread) {
                                leader = null;
                            }
                        }
                    }
                    else {
                        condition.await();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if (leader == null && pq.peek() != null) {
                    condition.signalAll();
                }
                lock.unlock();
            }


        }
    }
}
