package ProducerConsumer;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class SharedResource {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    int size = 10;
    Lock lock;
    Condition condition;
    public SharedResource(Lock lock) {
        this.lock = lock;
        this.condition = lock.newCondition();
    }

    public void add(int item) {
        try {
            lock.lock();
            while (pq.size() >= size) {
                System.out.println("Queue is full. Waiting - " + Thread.currentThread().getName());
                condition.await();
            }
            System.out.println("Item Produced :"+ item + " - Added by " + Thread.currentThread().getName());
            pq.offer(item);
            condition.signalAll();
        }
        catch (Exception e) {

        }
        finally {
            lock.unlock();
        }

    }

    public void poll(){
        try {
            lock.lock();
            while (pq.isEmpty()) {
                System.out.println("Queue is Empty. Waiting - " + Thread.currentThread().getName());
                condition.await();
            }
            Integer item = pq.poll();
            System.out.println("Item Consumed : "+ item + " - Consumed by " + Thread.currentThread().getName());
            condition.signalAll();
        }
        catch (Exception e) {

        }
        finally {
            lock.unlock();
        }

    }
}
