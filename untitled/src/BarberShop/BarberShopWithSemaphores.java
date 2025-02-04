package BarberShop;

import java.util.concurrent.Semaphore;

public class BarberShopWithSemaphores {
    Semaphore lock;
    int n;

    public BarberShopWithSemaphores(int n) {
        this.n = n;
        lock = new Semaphore(n);
    }

    public void takeSeat() {
        boolean isLockAcquired = lock.tryAcquire();
        if (!isLockAcquired) {
            System.out.println(Thread.currentThread().getName() + " - Cannot find empty seat");
            return;
        }
        System.out.println(Thread.currentThread().getName() + " - Acquired Seat");
        System.out.println("Available Permits - " + lock.availablePermits());
    }

    public void hairCut(){

        while (lock.availablePermits() == n) {

        }

        lock.release();
        System.out.println(Thread.currentThread().getName() + " - Releasing Seat");
        System.out.println("Available Permits - " + lock.availablePermits());

    }

}
