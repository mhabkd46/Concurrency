package Barrier;

public class ReusableBarrier {
    private int currentThreadCount;
    private int size;

    public ReusableBarrier(int size) {
        this.currentThreadCount  = 0;
        this.size = size;
    }

    public void approachBarrier() {
        lockBarrier();
        logic();
        unlockBarrier();
    }

    public synchronized void lockBarrier() {
        currentThreadCount++;
        System.out.println("[lockBarrier] Current Thread - " + Thread.currentThread().getName() + "; Count - " + currentThreadCount);
        while (currentThreadCount < size) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("[lockBarrier] released Thread - " + Thread.currentThread().getName());
        notifyAll();
    }

    public synchronized void unlockBarrier() {
        currentThreadCount--;
        System.out.println("[unlockBarrier] Current Thread - " + Thread.currentThread().getName() + "; Count - " + currentThreadCount);
        while (currentThreadCount != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("[unlockBarrier] released Thread - " + Thread.currentThread().getName());
        notifyAll();
    }


    public void logic() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
