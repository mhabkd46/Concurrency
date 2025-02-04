package UnisexBathroom;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnisexBathroomWithReentrantLock {
    int people = 0;
    String occupyingGender = "None";
    int size = 3;
    Lock lock = new ReentrantLock(true);
    Condition condition = lock.newCondition();

    public UnisexBathroomWithReentrantLock() {}

    public void acquireMaleBathroom() {
        lock.lock();

        while (occupyingGender.equals("Female") || people >= size) {
            try {
                System.out.println(Thread.currentThread().getName() + " Waiting for Male Bathroom");
                condition.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        occupyingGender = "Male";
        people++;
        System.out.println(Thread.currentThread().getName() + " acquired Male Bathroom");
        System.out.println("Total people - " + people);

        lock.unlock();

        performBathroom();
        releaseBathroom();
    }

    public void acquireFemaleBathroom() {
        lock.lock();

        while (occupyingGender.equals("Male") || people >= size) {
            try {
                System.out.println(Thread.currentThread().getName() + " Waiting for Female Bathroom ");
                condition.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        occupyingGender = "Female";
        people++;
        System.out.println(Thread.currentThread().getName() + " acquired Female Bathroom");
        System.out.println("Total people - " + people);

        lock.unlock();

        performBathroom();
        releaseBathroom();
    }

    public void releaseBathroom() {
        lock.lock();
        people--;
        System.out.println(Thread.currentThread().getName() + " releasing Bathroom");
        System.out.println("Total people - " + people);
        if (people == 0) {
            System.out.println(Thread.currentThread().getName() + " empty bathroom");
            occupyingGender = "None";
        }
        condition.signalAll();
        lock.unlock();
    }

    private void performBathroom() {
        try {
            System.out.println(Thread.currentThread().getName()  + " performing bathroom");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
