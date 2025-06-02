package TaskScheduler;

import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TaskSchedulerImpl implements TaskScheduler {
    private int threadPoolSize;
    private int taskSize;
    private PriorityQueue<Task> pq;
    private ReentrantLock lock;
    private Condition condition;
    private ThreadPoolExecutor executor;

    public TaskSchedulerImpl(int threadPoolSize, int taskSize) {
        this.threadPoolSize = threadPoolSize;
        this.pq = new PriorityQueue<Task>(
            (Task a, Task b) -> Long.compare(a.scheduledTime, b.scheduledTime)
            );
        this.lock = new ReentrantLock();
        this.condition = this.lock.newCondition();
        this.taskSize = taskSize;
        this.executor = Executors.newCachedThreadPool(threadPoolSize);
    }

    public void execute() {
        lock.lock();
        try {
            while(true) {
                while (pq.size() == 0) {
                    condition.await();
                }
                while (pq.size() != 0 && pq.peek().scheduledTime >= System.currentTimeMillis()) {
                    condition.await();
                }

                Task task = pq.poll();
                if (task instanceof OneTimeTask) {
                    executeOneTimeTask(task);
                }
                else if (task instanceof RecurringTask) {
                    executeRecurringTask(task);
                }
                else {
                    executeRecurringTaskWithDelay(task);
                }
                
                condition.signalAll();
            }
        }
        finally {
            lock.unlock();
        }
    }

    public void scheduleTask(Task task) {
        lock.lock();
        try {
            while (pq.size() == taskSize) {
                condition.await();
            }

            pq.offer(task);
            condition.signalAll();
        }
        finally {
            lock.unlock();
        }
    }

    private void executeOneTimeTask(OneTimeTask task) {
        executor.submit(task);
    }

    private void executeRecurringTask(RecurringTask task) {
        executor.submit(task);
        task.recurringTime += System.currentTimeMillis();
        pq.offer(task);
    }

    private void executeRecurringTaskWithDelay(RecurringTaskWithDelay task) {
        executor.submit(task);
        task.recurringTime += System.currentTimeMillis();
        pq.offer(task);
    }
    
}